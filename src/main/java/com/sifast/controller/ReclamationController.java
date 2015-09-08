package com.sifast.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sifast.model.Client;
import com.sifast.model.Institution;
import com.sifast.model.Reclamation;
import com.sifast.model.TypeReclamation;
import com.sifast.service.IClientService;
import com.sifast.service.IInstitutionService;
import com.sifast.service.IReclamationService;
import com.sifast.service.ITypeReclamationService;
import com.sifast.service.filter.pattern.AndCritere;
import com.sifast.service.filter.pattern.Critere;
import com.sifast.service.filter.pattern.CritereInstitution;
import com.sifast.service.filter.pattern.CritereLongLat;
import com.sifast.service.filter.pattern.CritereType;
import com.sifast.service.impl.NotifierClientService;

@Controller
@RequestMapping("enregReclamation")
public class ReclamationController {

	@Autowired
	private IInstitutionService institutionService;

	@Autowired
	private IReclamationService reclamationService;

	@Autowired
	private IClientService clientService;

	@Autowired
	private ITypeReclamationService typeReclamationService;

	private Properties properties = new Properties();

	static final Logger logger = Logger.getLogger(ReclamationController.class);
	
	private static final int NBR_DAY = -2;

	@RequestMapping(method = RequestMethod.POST)
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) {

		// Configure logger
		BasicConfigurator.configure();

		// Il faut l'appeler avant de faire un premier request.getParameter() pour forcer la lecture en UTF-8.
		// request.setCharacterEncoding("UTF-8");

		// -------------Récupération des informations de la réclamation-------------
		String nomInstitution = request.getParameter("institution");
		String typeReclamation = request.getParameter("typeReclamation");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String numTelString = request.getParameter("numTel");
		double longitude = Double.parseDouble(request.getParameter("longitude"));
		double latitude = Double.parseDouble(request.getParameter("latitude"));

		// -------ces deux variables ne sont pas obligatoire donc il peuvent être null------
		String adresseAnomalie = request.getParameter("adresseAnomalie");
		String autresPrecisions = request.getParameter("autresPrecisions");

		int numTel = numTelString != null ? Integer.parseInt(numTelString) : 0;

		// -----------------------------------------------------------------------------------
		logger.debug("nom institution: " + nomInstitution + "   type réclamation: " + typeReclamation);
		Institution institution = institutionService.getInstitutionByName(nomInstitution);
		TypeReclamation type = typeReclamationService.getTypeReclamationByTypeAndInstitution(typeReclamation, institution);
		Client client = clientService.getClientByEmail(email);

		logger.debug("institution: " + institution);
		logger.debug("type reclam: " + type);
		logger.debug("client: " + client);

		if (client == null) {
			client = new Client();
			logger.debug("--------");
			client.setNom(nom);
			client.setPrenom(prenom);
			client.setEmail(email);
			client.setNumTel(numTel);
			clientService.insertClient(client);
		}

		Reclamation reclamation = new Reclamation();
		reclamation.setRefReclam(institution.getNomInstit() + "-00" + (reclamationService.getNumberReclamation() + 1));
		reclamation.setEtatReclam(false);
		reclamation.setLongitude(longitude);
		reclamation.setLatitude(latitude);
		reclamation.setDateReclam(new Date());
		reclamation.setTypeReclamation(type);
		reclamation.setClient(client);

		if (adresseAnomalie != null) {
			reclamation.setAdresseAnomalie(adresseAnomalie);
		}
		if (autresPrecisions != null)
		{
			reclamation.setAutresPrecisions(autresPrecisions);
		}

		// charger le fichier de configuration config.properties pour vérifier si le filtrage des requêtes redondante avant l'enregistrement et activer ou non
		InputStream input;
		boolean filterPatternEnabled = false;
		try {
			input = new FileInputStream("config.properties");
			properties.load(input);
			filterPatternEnabled = Boolean.valueOf(properties.getProperty("filterPatternEnabled"));
			input.close();
		} catch (FileNotFoundException e) {
			logger.debug("FileNotFoundException: "+e.getMessage());
		} catch (IOException e) {
			logger.debug("IOException: "+e.getMessage());
		}

		if (filterPatternEnabled)
		{
			logger.debug("Filter pattern activé");
			List<Reclamation> listReclamations = reclamationService.getListReclamationLastXDays(NBR_DAY);
			logger.debug("Size list for the x Last days: " + listReclamations.size());
			Critere critereInstitution = new CritereInstitution(nomInstitution);
			Critere critereType = new CritereType(typeReclamation);
			Critere critereLongLat = new CritereLongLat(longitude, latitude);
			Critere institutionTypeLongLat = new AndCritere(critereInstitution, critereType, critereLongLat);
			List<Reclamation> listFiltred = institutionTypeLongLat.execute(listReclamations);
			logger.debug("Size list filtred: " + listFiltred.size());
			System.out.println("Filter Activé + Size: "+listFiltred.size());
			/*if (listFiltred.size() == 1) {
				NotifierClientService.sendRefReclamationToClient(reclamation, client, false);
			} else {
				reclamationService.insertReclamation(reclamation);
				NotifierClientService.sendRefReclamationToClient(reclamation, client, true);
			}*/
			
			if (listFiltred.size() != 0) {
				System.out.println("List filtred non vide : "+listFiltred.size());
				NotifierClientService.sendRefReclamationToClient(listFiltred.get(listFiltred.size()-1), client, false);
			} else {
				System.out.println("List filtred vide 0");
				reclamationService.insertReclamation(reclamation);
				NotifierClientService.sendRefReclamationToClient(reclamation, client, true);
			}
		}
		else
		{
			logger.debug("Filter pattern désactivé");
			reclamationService.insertReclamation(reclamation);
			NotifierClientService.sendRefReclamationToClient(reclamation, client, true);
		}

		// TODO Faire quelque test Unitaire et MOCK
	}
}