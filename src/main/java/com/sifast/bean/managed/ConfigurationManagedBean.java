package com.sifast.bean.managed;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;

@ManagedBean(name = "confMB")
public class ConfigurationManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1341893838272406037L;
	static final Logger logger = Logger.getLogger(ConfigurationManagedBean.class);

	private boolean filterPatternEnabled;
	private int delaiAttente;
	private int periode;
	private Properties properties = new Properties();

	public boolean isFilterPatternEnabled() {
		return filterPatternEnabled;
	}

	public void setFilterPatternEnabled(boolean filterPatternEnabled) {
		this.filterPatternEnabled = filterPatternEnabled;
	}

	public int getDelaiAttente() {
		return delaiAttente;
	}

	public void setDelaiAttente(int delaiAttente) {
		this.delaiAttente = delaiAttente;
	}

	public int getPeriode() {
		return periode;
	}

	public void setPeriode(int periode) {
		this.periode = periode;
	}

	@PostConstruct
	public void init() {
		try {
			InputStream input = new FileInputStream("config.properties");
			properties.load(input);
			setFilterPatternEnabled(Boolean.valueOf(properties.getProperty("filterPatternEnabled")));
			setDelaiAttente(Integer.parseInt(properties.getProperty("delaiAttente")) / 1000);
			setPeriode(Integer.parseInt(properties.getProperty("periode")) / 1000);
			input.close();
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
	}

	public void saveFileProperties()
	{
		try {
			String filename = "config.properties";
			OutputStream output = new FileOutputStream(filename);

			// set the properties value
			properties.setProperty("filterPatternEnabled", String.valueOf(isFilterPatternEnabled()));
			properties.setProperty("delaiAttente", String.valueOf(getDelaiAttente() * 1000));
			properties.setProperty("periode", String.valueOf(getPeriode() * 1000));
			logger.debug("save file 1");
			// save properties to project root folder
			properties.store(output, null);
			logger.debug("save file 2");
			output.close();
		} catch (IOException ex) {
			logger.debug(ex.getMessage());
		}
	}
}