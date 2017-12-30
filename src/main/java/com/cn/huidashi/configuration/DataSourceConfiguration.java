package com.cn.huidashi.configuration;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * huidash数据源配置
 */
@Configuration
@MapperScan(basePackages = "com.cn.huidashi.mapper", sqlSessionTemplateRef = "SqlSessionTemplate")
public class DataSourceConfiguration {

    @Autowired
    private Environment env;

    @Bean(name = "DataSource")
    public DataSource dataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("huidashi.driver", String.class));
        props.put("url", env.getProperty("huidashi.url", String.class));
        props.put("username", env.getProperty("huidashi.username", String.class));
        props.put("password", env.getProperty("huidashi.password", String.class));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("dialect", "postgresql");
        pageHelper.setProperties(p);
        return pageHelper;
    }

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("DataSource") DataSource dataSource,PageHelper pageHelper) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        bean.setPlugins(new Interceptor[] { pageHelper });
        return bean.getObject();
    }

    @Bean(name = "TransactionManager")
    public DataSourceTransactionManager getTransactionManager(@Qualifier("DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "SqlSessionTemplate")
    public SqlSessionTemplate getSqlSessionTemplate(@Qualifier("SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
