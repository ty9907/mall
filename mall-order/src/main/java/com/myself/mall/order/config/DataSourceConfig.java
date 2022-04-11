package com.myself.mall.order.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DruidDataSource druidDataSource() {
//        return new DruidDataSource();
//    }

    /**
     * 需要将 DataSourceProxy 设置为主数据源，否则事务无法回滚
     *
     * @param druidDataSource The DruidDataSource
     * @return The default datasource
     */
//    @Primary
//    @Bean("dataSource")
//    public DataSource dataSource(DruidDataSource druidDataSource) {
//        return new DataSourceProxy(druidDataSource);
//    }

//    @Bean
//	public DataSource dataSource(DataSourceProperties dataSourceProperties){
//		HikariDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//		if(StringUtils.hasText(dataSourceProperties.getName())){
//			dataSource.setPoolName(dataSourceProperties.getName());
//		}
//		return new DataSourceProxy(dataSource);
//	}
}