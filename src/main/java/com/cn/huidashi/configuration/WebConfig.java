package com.cn.huidashi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;  
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

/**
 * @author Alvin Du
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    @Override  
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/");
        //场地图片静态资源配置
        registry.addResourceHandler(env.getProperty("image.site.reqRoute", String.class) + "/**").addResourceLocations("file:" + env.getProperty("image.site.path", String.class) + File.separator);
        registry.addResourceHandler(env.getProperty("image.siteBriefFile.reqRoute", String.class) + "/**").addResourceLocations("file:" + env.getProperty("image.siteBriefFile.path", String.class) + File.separator);

        //TODO 广告图片静态资源配置

        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        super.addResourceHandlers(registry);

    }

}