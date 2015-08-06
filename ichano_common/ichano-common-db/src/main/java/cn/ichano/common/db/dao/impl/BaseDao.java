package cn.ichano.common.db.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ichano.common.config.Env;
import cn.ichano.common.db.dao.IBaseDao;
import cn.ichano.common.db.dao.Pager;

/**
 * 开放接口数据库底层支持实现类. <br>
 * 对数据库的操作交由第三方框架Spring 的SimpleJdbcTemplate操作，在此基础上做一些个性化的封装<br>
 * 提供基础的增、删、改、查，操作之外，还进行了分页查询，分页转换，自动增长列的查询.
 * <p>
 * Copyright: Copyright (c) 2012-8-30 上午10:47:55
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * 
 * @author wenjp@c-platform.com
 * @version 1.0.0
 */
@Service("baseDao")
@Transactional
public class BaseDao implements IBaseDao {

	private final static Logger logger = LoggerFactory.getLogger(BaseDao.class);

	@Autowired
	private NamedParameterJdbcTemplate nameParameterJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 分页中每页数据最大条数
	 */
	private static final int MAX_PAGE_SIZE = Env.getConfig().getInteger(
			"page.maxPageSize", 20);

	@Override
	public <T> T querySingle(String sql, Class<T> clz, Object... objs) {
		List<T> list = queryList(sql, clz, objs);

		if (list == null || list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public <T> List<T> queryList(String sql, Class<T> clz, Object... objs) {
		List<T> list = new ArrayList<T>();

		if (null != sql) {
			// 如果sql中没有带"?"并且objs含有仅含一个值，则认为是objs[0]是带有部分属性有的bean对象,需要进行分装处理
			if (!sql.contains("?") && null != objs && 1 == objs.length) {
				list = this.nameParameterJdbcTemplate.query(sql,
						new BeanPropertySqlParameterSource(objs[0]),
						this.simpleRowMapper(clz));
			}// 否则认为objs是对应sql中的"?"的数据值
			else {
				if (objs != null) {
					list = this.jdbcTemplate.query(sql,
							this.simpleRowMapper(clz), objs);
				} else {
					list = this.jdbcTemplate.query(sql,
							this.simpleRowMapper(clz));
				}
			}
		}
		return list;
	}

	@Override
	public Map<String, Object> queryResultToMap(String sql, Object... objs) {
		List<Object[]> result = this.jdbcTemplate.query(sql,
				arrayRowMapper(Object[].class), objs);

		if (result != null && result.size() > 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询到数据总数为：" + result.size() + ",sql:" + sql);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			for (Object[] o : result) {
				Object value = null;
				if (o.length > 1) {
					value = o[1];
				}
				map.put(String.valueOf(o[0]), value);

			}
			return map;
		}
		logger.debug("没有查询到数据,sql:" + sql);
		return null;
	}

	/**
	 * 将结果集转换为rs
	 * 
	 * @param clz
	 *            需要转换的类型
	 * @return 转换后的RowMapper
	 */
	private RowMapper<Object[]> arrayRowMapper(final Class<Object[]> clz) {
		return new RowMapper<Object[]>() {

			@Override
			public Object[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				try {
					// logger.info("rowNum: " + rowNum);
					Object[] result = new Object[rs.getMetaData()
							.getColumnCount()];
					for (int i = 0; i < result.length; i++) {
						result[i] = rs.getObject(i + 1);
					}
					return result;
				} catch (Exception e) {
					throw new SQLException(e);
				}
			}
		};

	}

	/**
	 * 对class进行包装
	 * 
	 * @param clz
	 *            需要转化的类型
	 * @return 可用的RowMapper
	 */
	private <T> RowMapper<T> simpleRowMapper(final Class<T> clz) {
		if (clz == String.class || clz == Boolean.class || clz == Byte.class
				|| clz == Short.class || clz == Integer.class
				|| clz == Long.class || clz == Float.class
				|| clz == Double.class) {
			return new RowMapper<T>() {

				@Override
				public T mapRow(ResultSet rs, int rowNum) throws SQLException {
					try {
						return clz.getConstructor(String.class).newInstance(
								rs.getString(1));
					} catch (Exception e) {
						throw new SQLException(e);
					}
				}
			};
		} else if (clz == Character.class) {
			return new RowMapper<T>() {

				@Override
				public T mapRow(ResultSet rs, int rowNum) throws SQLException {
					try {
						String str = rs.getString(1);
						return clz.getConstructor(char.class).newInstance(
								(str == null || str.length() == 0) ? null : str
										.charAt(0));
					} catch (Exception e) {
						throw new SQLException(e);
					}
				}
			};
		} else if (clz == Map.class) {
			return (RowMapper<T>) new ColumnMapRowMapper();
		} else {
			return new BeanPropertyRowMapper<T>(clz);
		}
	}

	@Override
	public <T> List<T> queryList(String sql, T bean) {

		Class<T> clz = (Class<T>) bean.getClass();
		return this.nameParameterJdbcTemplate.query(sql,
				new BeanPropertySqlParameterSource(bean),
				new BeanPropertyRowMapper<T>(clz));
	}

	@Override
	public <T> List<T> queryList(String sql, Class<T> clz) {
		return this.jdbcTemplate.query(sql, this.simpleRowMapper(clz));
	}

	@Override
	public int update(String sql, Object[] objs, Object bean) {
		// 如果bean为空的话则处理objs
		if (null == bean && null != objs) {
			return this.jdbcTemplate.update(sql, objs);
		} else if (null == objs && null != bean) {
			if (bean instanceof Map) {
				return this.nameParameterJdbcTemplate.update(sql, (Map) bean);
			} else {
				return this.nameParameterJdbcTemplate.update(sql,
						new BeanPropertySqlParameterSource(bean));
			}
		} else {
			logger.error("execute sql can't bind params, is an error? sql:",
					sql);
			return jdbcTemplate.update(sql);
		}
	}

	@Override
	public int[] batchUpdate(String sql, List<?> beanList) {
		if(beanList == null || beanList.size() == 0){
			throw new RuntimeException("batch update list is empty");
		}
		
		Object bean0 = beanList.get(0);
		// 根据第一个值来决定是map还是Bean
		if(bean0 instanceof Map){
			int size = beanList.size();
			Map<String, ?>[] params = new HashMap[size];
			
			for (int i = 0;i< size;i++) {
				Object bean = beanList.get(i);
				params[i] = (Map)bean;
			}
			
			return nameParameterJdbcTemplate.batchUpdate(sql, params);
		} else {

			int size = beanList.size();
			SqlParameterSource[] params = new BeanPropertySqlParameterSource[size];
			
			for (int i = 0; i < size; i++) {
				Object bean = beanList.get(i);
				params[i] = new BeanPropertySqlParameterSource(bean);
			}

			return nameParameterJdbcTemplate.batchUpdate(sql, params);

		}

	}

	// @Override
	// public <T> int updates(String sql, final List<T> list,
	// BatchPreparedStatementSetter pss) {
	// return jdbcTemplate.batchUpdate(sql, pss).length;
	// }

	@Override
	public int updateImmediately(String sql, Object[] objs, Object bean) {
		return update(sql, objs, bean);
	}

	@Override
	public <T> Pager<T> query4Pager(int pageNo, int pageSize, String selSql,
			Class<T> clazz, Object... args) {

		
		int totalRow = getTotalRows(selSql, args);
		pageSize = getPageSize(pageSize);

		
		List<T> resultList = querySinglePage(pageNo, pageSize, selSql, clazz,
				args);

		Pager<T> pager = new Pager<T>();
		pager.setPageNo(pageNo);
		pager.setPageSize(pageSize);
		pager.setTotalRows(totalRow);
		pager.setTotalPages((totalRow + pageSize - 1) / pageSize);
		pager.setResultList(resultList);
		return pager;

	}

	/**
	 * 查询指定页的记录数
	 * @param pageNo
	 * @param pageSize
	 * @param selSql
	 * @param clazz
	 * @param args
	 * @return
	 */
	private <T> List<T> querySinglePage(int pageNo, int pageSize,
			String selSql, Class<T> clazz, Object... args) {
		int v_endrownum = pageNo * pageSize;
		int v_startrownum = v_endrownum - pageSize;
		
		
		List<T> resultList = null;
		String selSqlend = "";

		// selSqlend = " SELECT * FROM (SELECT vtb.*, rownum rn FROM  " +
		// v_table + " WHERE rownum <= to_char(" + v_endrownum
		// + "))  WHERE rn >= to_char(" + v_startrownum + ")";

		selSqlend = selSql + " limit " + v_startrownum + ", " + pageSize;

		resultList = this.queryList(selSqlend, clazz, args);
		return resultList;
	}

	/**
	 * 查询sql总记录数
	 * @param selSql
	 * @param args
	 * @return
	 */
	private int getTotalRows(String selSql, Object... args) {
		String v_table = "(" + selSql + ") vtb";
		String countsql = "SELECT COUNT(*) FROM " + v_table;

		int totalRow = 0;
		if (args.length == 0) {
			totalRow = this.jdbcTemplate.queryForInt(countsql);
		} else if (selSql.contains("?")) {
			totalRow = this.jdbcTemplate.queryForInt(countsql, args);
		} else {
			totalRow = this.nameParameterJdbcTemplate.queryForInt(countsql,
					new BeanPropertySqlParameterSource(args));
		}
		return totalRow;
	}
	
	
	@Override
	public <T> Pager<T> query4PagerUnCount(int pageNo, int pageSize, String selSql,
			Class<T> clazz, Object... args) {

		int totalRow = Integer.MAX_VALUE;
		pageSize = getPageSize(pageSize);

		
		List<T> resultList = querySinglePage(pageNo, pageSize, selSql, clazz,
				args);

		Pager<T> pager = new Pager<T>();
		pager.setPageNo(pageNo);
		pager.setPageSize(pageSize);
		pager.setTotalRows(totalRow);
		pager.setTotalPages((totalRow + pageSize - 1) / pageSize);
		pager.setResultList(resultList);
		return pager;
	}

	@Override
	public <T, U> Pager<T> convertPager(Pager<U> pager, List<T> resultList) {
		Pager<T> rs = new Pager<T>(pager.getPageNo(), pager.getPageSize(),
				resultList, pager.getTotalRows());
		return rs;
	}

	/**
	 * 获取分页中每页最大条数<br>
	 * 如果传入的参数大于系统配置的值，则返回系统配置的值，否则返回出入值
	 * 
	 * @param pageSize
	 * @return
	 */
	private int getPageSize(int pageSize) {
		if (pageSize > MAX_PAGE_SIZE) {
			logger.info("The System setting Max page size is [" + MAX_PAGE_SIZE
					+ "] the Client need Page size[" + pageSize
					+ "],return page size[" + MAX_PAGE_SIZE + "]");
			return MAX_PAGE_SIZE;
		}
		return pageSize;
	}

}
