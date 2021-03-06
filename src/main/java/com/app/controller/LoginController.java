package com.app.controller;

import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

@ManagedBean
public class LoginController {

	String username;
	String password;
	boolean rememberMe = false;

	private static final Logger log = Logger.getLogger(LoginController.class.getName());

	public String authenticate() {

		// Example using most common scenario of username/password pair:
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);

		// "Remember Me" built-in:
		token.setRememberMe(rememberMe);

		Subject currentUser = SecurityUtils.getSubject();

		log.info("Submitting login with username of " + username + " and password of " + password);

		try {
			currentUser.login(token);
		} catch (AuthenticationException e) {
			// Could catch a subclass of AuthenticationException if you like
			log.warning(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login Failed: " + e.getMessage(), e.toString()));
			return "login";
		}
		return "home";

	}

	public String logout() {

		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.logout();
		} catch (Exception e) {
			log.warning(e.toString());
		}
		return "home";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

}
