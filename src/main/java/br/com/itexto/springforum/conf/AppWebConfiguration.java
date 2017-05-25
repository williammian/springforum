package br.com.itexto.springforum.conf;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@EnableWebMvc
@ComponentScan(basePackages={"br.com.itexto.springforum.entidades", "br.com.itexto.springforum.controladores", "br.com.itexto.springforum.dao.hibernate", "br.com.itexto.springforum.dao.jdbc"})
public class AppWebConfiguration extends WebMvcConfigurerAdapter{
	
	/**
     * Configure TilesConfigurer.
     */
    @Bean
    public TilesConfigurer tilesConfigurer(){
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[] {"/WEB-INF/tiles/**/tiles-config.xml"});
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }
 
    /**
     * Configure ViewResolvers to deliver preferred views.
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        TilesViewResolver viewResolver = new TilesViewResolver();
        registry.viewResolver(viewResolver);
    }
	
//	@Bean
//	public InternalResourceViewResolver internalResourceViewResolver() {
//		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//		resolver.setPrefix("/WEB-INF/views/");
//		resolver.setSuffix(".jsp");
//		
//		return resolver;
//	}
	
	/**
     * Configure ResourceHandlers to serve static resources like CSS/ Javascript etc...
     */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/recursos/**").addResourceLocations("/recursos/").setCachePeriod(120);
	}
	
	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
		bundle.setBasename("/WEB-INF/messages");
		bundle.setDefaultEncoding("UTF-8");
		bundle.setCacheSeconds(1);
		return bundle;
	}
	
	@Bean
	public MultipartResolver multipartResolver(){
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(1048576);
		return commonsMultipartResolver;
	}

}
