package com.sifast.bean.managed;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sifast.service.util.StringConstant;

@ManagedBean(name = "authenticatMB")
@SessionScoped
public class AuthentificationManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8903213146298144960L;
	static final Logger logger = Logger.getLogger(AuthentificationManagedBean.class);

	public void isAuthenticated(ComponentSystemEvent event) throws IOException {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		if(session.getAttribute("login") == null)
		{
			facesContext.getExternalContext().redirect(StringConstant.urlIndex);
		}
	}
}
