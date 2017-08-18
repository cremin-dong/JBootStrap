package com.dmm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;

@EnableTransactionManagement
@SpringBootApplication
@MapperScan(basePackages = "com.dmm.module.dao")

public class JbootstrapApplication {

	public static void main(String[] args) {
		SpringApplication.run(JbootstrapApplication.class, args);
	}


	/**
	 * Spring Boot默认使用嵌入式Tomcat，默认没有页面来处理404等常见错误，自定义常见错误页面
	 * @return
     **/
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		System.out.println(11);
		return (container -> {
			ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
			ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
			ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

			container.addErrorPages(error401Page, error404Page, error500Page);
		});
	}


	@Bean(name = "characterEncodingFilter")
	public FilterRegistrationBean characterEncodingFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.addInitParameter("encoding", "UTF-8");
		bean.addInitParameter("forceEncoding", "true");
		bean.setFilter(new CharacterEncodingFilter());
		bean.addUrlPatterns("/*");
		return bean;
	}

}
