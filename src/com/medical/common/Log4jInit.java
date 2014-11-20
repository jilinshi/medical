package com.medical.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;

public class Log4jInit extends HttpServlet {

 
	private static final long serialVersionUID = 1L;
 
	public Log4jInit() {
		super();
	}

	public void init() throws ServletException {
		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter("log4j");
		if (file != null) {
			PropertyConfigurator.configure(prefix + "\\"+file);
		}

	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	}

}
