package com.sifast.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sifast.model.Admin;
import com.sifast.model.Agent;
import com.sifast.service.IAdminService;
import com.sifast.service.IAgentService;
import com.sifast.service.util.StringConstant;

@Controller
@RequestMapping("authenticationUser")
public class AuthentificationController {

	@Autowired
	private IAdminService adminService;

	@Autowired
	private IAgentService agentService;

	static final Logger logger = Logger.getLogger(AuthentificationController.class);

	@RequestMapping(method = RequestMethod.POST)
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) {

		// Configure logger
		BasicConfigurator.configure();

		logger.info("slouma louati Sifast");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String typeUser = request.getParameter("typeUser");

		HttpSession session = request.getSession(true);

		if (typeUser.equals(StringConstant.admin))
		{
			Admin admin = adminService.getAdminByEmailAndPassword(email, password);
			if (admin != null)
			{
				session.setAttribute("login", true);
				session.setAttribute("email", email);
				session.setAttribute("user", admin);
				try {
					response.sendRedirect(StringConstant.urlAccueil);
				} catch (IOException e) {
					logger.debug("sendRedirect IOException: " + e.getMessage());
				}
				logger.info("admin not null");
			}
			else
			{
				RequestDispatcher rd = request.getRequestDispatcher(StringConstant.index);
				request.setAttribute("erreurAuthentification", true);
				try {
					rd.forward(request, response);
				} catch (ServletException e) {
					logger.debug("ServletException: " + e.getMessage());
				} catch (IOException e) {
					logger.debug("IOException: " + e.getMessage());
				}
			}
			logger.info("Admin: " + admin);
		} else
		{
			Agent agent = agentService.getAgentByEmailAndPassword(email, password);
			if (agent != null)
			{
				session.setAttribute("login", true);
				session.setAttribute("email", email);
				session.setAttribute("user", agent);
				try {
					response.sendRedirect(StringConstant.urlAccueil);
				} catch (IOException e) {
					logger.debug("IOException sendRedirect: " + e.getMessage());
				}
			}
			else
			{
				RequestDispatcher rd = request.getRequestDispatcher(StringConstant.index);
				request.setAttribute("erreurAuthentification", true);
				try {
					rd.forward(request, response);
				} catch (ServletException e) {
					logger.debug("ServletException forward: " + e.getMessage());
				} catch (IOException e) {
					logger.debug("IOException forward: " + e.getMessage());
				}
			}
			logger.info("Agent: " + agent);
		}
		logger.info("Bien !!");
	}
}