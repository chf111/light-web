package up.light.web.config;

import java.io.IOException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

import up.light.web.common.PageInterceptor;

@Configuration
@ComponentScan("up.light.web.service.impl")
@MapperScan("up.light.web.dao")
@PropertySource("classpath:jdbc.properties")
public class MybatisConfig {

	@Resource
	private Environment env;

	MybatisConfig() {
		LogFactory.useLog4JLogging();
	}

	/*
	 * 连接池
	 */
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(env.getProperty("driver"));
		dataSource.setUrl(env.getProperty("url"));
		dataSource.setUsername(env.getProperty("user"));
		dataSource.setPassword(env.getProperty("password"));
		dataSource.setInitialSize(Integer.valueOf(env.getProperty("initialSize")));
		dataSource.setMaxActive(Integer.valueOf(env.getProperty("maxActive")));
		return dataSource;
	}

	/*
	 * 自动扫描映射文件
	 */
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws IOException {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		factory.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath:up/light/web/mapping/*.xml"));
		factory.setPlugins(new Interceptor[] { new PageInterceptor() });
		factory.setTypeHandlersPackage("up.light.web.entity.handler");
		return factory;
	}

	/*
	 * 事务管理器
	 */
	@Bean
	public PlatformTransactionManager txManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
