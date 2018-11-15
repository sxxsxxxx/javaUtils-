package com.sunUtils.commos.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.com.cdv.yuntu.common.DataSourceKey;
import io.swagger.annotations.ApiModel;

/**
 * Multiple DataSource Aspect
 *
 */
@Aspect
@Component
@ApiModel(value = "DynamicDataSourceAspect", description = "动态数据源切换的切面，切 DAO 层，通过 DAO 层方法名判断使用哪个数据源，实现数据源切换 关于切面的 Order 可以可以不设，因为 @Transactional 是最低的，取决于其他切面的设置，并且在 org.springframework.core.annotation.AnnotationAwareOrderComparator 会重新排序")
public class DynamicDataSourceAspect {
	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

	// private final String[] QUERY_PREFIX = {"select"};
	// 改变切入点，在查询数据库时在进行源切换，避免多次切换
	@Pointcut("execution(* cn.com.cdv.yuntu.mapper.DbGoodMapper.*(..))")
	public void daoAspect() {
	}

	/**
	 * 切点做切换换数据库日志打印
	 */
	@Before("daoAspect()")
	public void loggerPrint() {
		if (!DynamicDataSourceContextHolder.getDataSourceKey().equals(DataSourceKey.master.name())) {
			DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.Alpha.name());
		}
		logger.debug("开始切换数据,切换的数据源为：{}",DynamicDataSourceContextHolder.getDataSourceKey());
	}

	/**
	 * 
	 */
	@After("daoAspect()")
	public void loggerAfter() {
		logger.debug("切换主数据完成");
		if (!DynamicDataSourceContextHolder.getDataSourceKey().equals(DataSourceKey.master.name())){
			DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.master.name());
		}
	}

	/**
	 * Switch DataSource
	 *
	 * @param point
	 * @param targetDataSource 切点为service层
	 */
	// @Before("@annotation(targetDataSource))")
	public void switchDataSource(JoinPoint point, TargetDataSource targetDataSource) {
		if (!DynamicDataSourceContextHolder.containDataSourceKey(targetDataSource.value())) {
			logger.error("DataSource [{}] doesn't exist, use default DataSource [{}]", targetDataSource.value());
		} else {
			DynamicDataSourceContextHolder.setDataSourceKey(targetDataSource.value());
			logger.info("Switch DataSource to [{}] in Method [{}]", DynamicDataSourceContextHolder.getDataSourceKey(),
					point.getSignature());
		}
	}

	/**
	 * Restore DataSource
	 *
	 * @param point
	 * @param targetDataSource
	 */
	// @After("@annotation(targetDataSource))")
	public void restoreDataSource(JoinPoint point, TargetDataSource targetDataSource) {
		DynamicDataSourceContextHolder.clearDataSourceKey();
		logger.info("Restore DataSource to [{}] in Method [{}]", DynamicDataSourceContextHolder.getDataSourceKey(),
				point.getSignature());
	}

}
