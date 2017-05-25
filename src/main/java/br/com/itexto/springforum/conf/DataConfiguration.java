package br.com.itexto.springforum.conf;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@EnableTransactionManagement
public class DataConfiguration {
	
	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
	    LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
	    factory.setDataSource(dataSource);
	    factory.setPackagesToScan(new String[] { "br.com.itexto.springforum.entidades" });
	    
	    factory.setHibernateProperties(additionalProperties());
	    
	    return factory;
	}
	
//	@Bean
//	public DataSource dataSource(){
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//		dataSource.setUrl("jdbc:mysql://localhost:3306/spring_forum");
//		dataSource.setUsername( "root" );
//		dataSource.setPassword( "root" );
//		return dataSource;
//	}
	
	@Bean
	public ComboPooledDataSource dataSource() throws PropertyVetoException{
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/spring_forum");
		dataSource.setUser("root");
		dataSource.setPassword("root");
		
		dataSource.setMaxPoolSize(10);
		dataSource.setAcquireIncrement(1);
		dataSource.setMaxIdleTime(120);
		dataSource.setAcquireRetryAttempts(10);
		dataSource.setInitialPoolSize(1);
		
		return dataSource;
	}
	
	private Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.setProperty("hibernate.show_sql", "true");
		return properties;
	}
	
	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
	    HibernateTransactionManager tm = new HibernateTransactionManager(sessionFactory);
	    return tm;
	}
	
}
