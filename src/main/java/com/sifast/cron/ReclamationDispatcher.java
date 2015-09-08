package com.sifast.cron;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
import com.sifast.service.IReclamationService;

@Controller
public class ReclamationDispatcher implements ApplicationListener<ContextRefreshedEvent> {

	static final Logger logger = Logger.getLogger(ReclamationDispatcher.class);
	
	@Autowired
	private IReclamationService reclamationService;

	private int counter = -1;

	private Properties properties = new Properties();

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		counter++;
		if (counter == 1)
		{
			logger.debug("salem listener: " + (counter));
			// charger le fichier de configuration config.properties pour récupérer les valeurs "delay" et "période" d'execution de la tâche
			InputStream input;
			long delay = 0, period = 0;
			try {
				input = new FileInputStream("config.properties");
				properties.load(input);
				delay = Long.parseLong(properties.getProperty("delaiAttente"));
				period = Long.parseLong(properties.getProperty("periode"));
				logger.debug("delay: "+delay+"   period: "+period);
				input.close();
			} catch (FileNotFoundException e) {
				logger.debug("FileNotFoundException: "+e.getMessage());
			} catch (IOException e) {
				logger.debug("IOException: "+e.getMessage());
			}

			Timer timer = new Timer();
			TimerTask reclamationTask = new ReclamationTask(reclamationService);
			timer.schedule(reclamationTask, delay, period);
		}
	}
}