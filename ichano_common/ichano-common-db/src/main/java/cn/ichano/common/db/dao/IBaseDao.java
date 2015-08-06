package cn.ichano.common.db.dao;

import java.util.List;
import java.util.Map;



/**
 * 开放接口数据库底层支持接口. <br>
 * SpringJdbcTemplate
 * <p>
 * Copyright: Copyright (c) 2012-8-29 下午7:31:24
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * 
 * @author wenjp@c-platform.com
 * @version 1.0.0
 */
public interface IBaseDao {

	/**
	 * 列表查询
	 * 
	 * @param sql
	 *            查询SQL(?占位符)
	 * @param clz
	 *            返回结果封装类型
	 * @param objs
	 *            查询参数：<br>
	 *            如果objs的长度为1并且sql不包含"?"，则是为带有属性值的bean.<br>
	 *            如果objs的长度大于1，则是对应SQL中的?的字段值<br>
	 * @return 查询封装集
	 */
	public <T> List<T> queryList(String sql, Class<T> clz, Object... objs);

	// /**
	// * 单表按bean中的属性查询，返回部分属性值
	// *
	// * @param bean
	// * 属性带有值的bean
	// * @param nuIncludeProps
	// * 不包含的属性字段
	// * @return
	// */
	// public <T> List<T> singleQuery(T bean, String... nuIncludeProps);

	/**
	 * 查询单个属性值
	 * 
	 * @param sql
	 * @param clz
	 * @param objs
	 * @return
	 */
	public <T> T querySingle(String sql, Class<T> clz, Object... objs);

	/**
	 * 列表查询
	 * 
	 * @param sql
	 *            sql 查询SQL(命名占位符)
	 * @param bean
	 *            带有查询参数的bean
	 * @return 查询封装集
	 */
	public <T> List<T> queryList(String sql, T bean);
	
	public <T> List<T> queryList(String sql, Class<T> clz);
	
	/**
	 * 将查询数据封装成map对象,结果的第一位值为key,第二列为value
	 * 
	 * @param sql
	 *            sql 查询SQL(命名占位符)
	 * @param objs
	 *            与占位符一致的参数
	 * @return
	 */
	public Map<String, Object> queryResultToMap(String sql, Object... objs);

	/**
	 * 执行增删改操作<br>
	 * 可以同时处理带有属性值的bean对象和sql语句中带有?的objs数组<br>
	 * 
	 * @param sql
	 *            SQL语句
	 * @param objs
	 *            ?形式占位符参数值
	 * @param object
	 *            命名形式占位符参数值
	 * @return 执行后影响的行数
	 */
	public int update(String sql, Object[] objs, Object bean);
	
	/**
	 * 执行增删改操作<br>
	 * 可以同时处理带有属性值的bean对象和sql语句中带有?的objs数组<br>
	 * 此方法表示远程调用时会等待远程的结果
	 * @param sql
	 *            SQL语句
	 * @param objs
	 *            ?形式占位符参数值
	 * @param object
	 *            命名形式占位符参数值
	 * @return 执行后影响的行数
	 */
	public int updateImmediately(String sql, Object[] objs, Object bean);

	
//	public <T> int updates(String sql, List<T> list, BatchPreparedStatementSetter pss);
	

	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 *            当前页
	 * @param pageSize
	 *            每页记录数
	 * @param selSql
	 *            查询SQL
	 * @param clazz
	 *            记录封装成的Bean类
	 * @param args
	 *            参数
	 * @return 分页封装对象
	 */
	public <T> Pager<T> query4Pager(int pageNo, int pageSize, String selSql,
			Class<T> clazz, Object... args);
	
	/**
	 * 不查询总数，直接查询指定页的数据，因此总数和总页数不准，其他参看query4Pager
	 * @param pageNo
	 * @param pageSize
	 * @param selSql
	 * @param clazz
	 * @param args
	 * @return
	 */
	public <T> Pager<T> query4PagerUnCount(int pageNo, int pageSize, String selSql,
			Class<T> clazz, Object[] args);

	/**
	 * 转换分页对象<br>
	 * 根据pager当前多少页，每页大小，总共多少行对resultList进行分页
	 * 
	 * @param pager
	 * @param resultList
	 * @return
	 */
	public <T, U> Pager<T> convertPager(Pager<U> pager, List<T> resultList);

	/**
	 * 批量更新数据
	 * 
	 * @param sql
	 * @param beanList
	 *            object为map或者bean
	 * @return
	 */
	public abstract int[] batchUpdate(String sql, List<?> beanList);



}
