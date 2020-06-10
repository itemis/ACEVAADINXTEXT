package com.itemis.vaadin;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xtext.example.mydsl.web.MyDslServlet;

@Configuration
class MyConfig {
	@Bean
	public ServletRegistrationBean<MyDslServlet> exampleServletBean() {
	    ServletRegistrationBean<MyDslServlet> bean = new ServletRegistrationBean<MyDslServlet>(
	      new MyDslServlet(), "/xtext-service/*");
	    bean.setLoadOnStartup(1);
	    return bean;
	}

}


