package com.cetiti.ddapv2.process.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfiguration {
	
	@Bean
	@Primary
	@ConfigurationProperties("ddap.jdbc")
	public DataSourceProperties ddapDataSourceProperties() {
	    return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("ddap.jdbc")
	public DataSource ddapDataSource() {
	    return ddapDataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean
	@ConfigurationProperties("hk82.jdbc")
	public DataSourceProperties hk82DataSourceProperties() {
	    return new DataSourceProperties();
	}

	@Bean(name="hik82DataSource")
	@ConfigurationProperties("hk82.jdbc")
	public DataSource hk82DataSource() {
	    return hk82DataSourceProperties().initializeDataSourceBuilder().build();
	}
	
	@Bean(name = "ddapJdbcTemplate")
	@Primary
	public JdbcTemplate primaryJdbcTemplate(DataSource dataSource) {
		return new PaginationJdbcTemplate(dataSource, PaginationJdbcTemplate.MYSQL);
	}

	/**
	 * Create second JdbcTemplate from second DataSource.
	 */
	@Bean(name = "hik82JdbcTemplate")
	public JdbcTemplate hik82JdbcTemplate(@Qualifier("hik82DataSource") DataSource dataSource) {
		return new PaginationJdbcTemplate(dataSource, PaginationJdbcTemplate.ORACLE);
	}

}
