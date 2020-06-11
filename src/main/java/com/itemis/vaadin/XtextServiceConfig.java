package com.itemis.vaadin;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xtext.example.mydsl.web.MyDslServlet;

@Configuration
class XtextServiceConfig {
	public final static String SERVICE_URL = "/xtext-service";
	
	@Bean
	public ServletRegistrationBean<MyDslServlet> exampleServletBean() {
	    ServletRegistrationBean<MyDslServlet> bean = new ServletRegistrationBean<MyDslServlet>(
	      new MyDslServlet(), SERVICE_URL + "/*");
	    bean.setLoadOnStartup(1);
	    return bean;
	}

}


