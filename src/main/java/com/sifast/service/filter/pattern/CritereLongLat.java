package com.sifast.service.filter.pattern;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.sifast.model.Reclamation;

public class CritereLongLat implements Critere {
	
	static final Logger logger = Logger.getLogger(CritereLongLat.class);

	private double longitude, latitude;

	public CritereLongLat(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	@Override
	public List<Reclamation> execute(List<Reclamation> reclamations)
	{
		List<Reclamation> lonLatReclamations = new ArrayList<>();
		for (Reclamation reclamation : reclamations) {
			if (distance(longitude, latitude, reclamation.getLongitude(), reclamation.getLatitude()) < 50)
			{
				lonLatReclamations.add(reclamation);
			}
		}
		return lonLatReclamations;
	}

	/**
	 * c'est une méthode qui permet de calculer la distance en mètres entre deux points à partir des latitudes et des longitudes
	 * NB: il faut que les valeurs "longitude" et "latitude" soient en radians
	 * */
	private double distance(double long1, double lat1, double long2, double lat2)
	{
		double rlat1 = Math.PI * lat1 / 180;
		double rlat2 = Math.PI * lat2 / 180;
		double theta = long1 - long2;
		double rtheta = Math.PI * theta / 180;

		double distance = Math.sin(rlat1) * Math.sin(rlat2) + Math.cos(rlat1) * Math.cos(rlat2) * Math.cos(rtheta);
		distance = Math.acos(distance);
		distance = distance * 180 / Math.PI;
		distance = distance * 60 * 1.1515 * 1000;
		logger.debug("Distance :" + distance);
		return distance;
	}
}