package com.sherwin.ebook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		WebMvcConfigurer.super.addViewControllers(registry);
//        registry.addViewController("/").setViewName("index");
        registry.addViewController("/main").setViewName("layout/main");
        registry.addViewController("/photo").setViewName("photo");
        registry.addViewController("/news").setViewName("news");
        registry.addViewController("/account/3").setViewName("account/3");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}
	
}
