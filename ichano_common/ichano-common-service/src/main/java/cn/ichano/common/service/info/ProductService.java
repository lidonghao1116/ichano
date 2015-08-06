package cn.ichano.common.service.info;

import java.util.List;
import java.util.Map;

import cn.ichano.common.entity.info.Product;



/**
 * 产品相关服务
 * 
 * @author wenjp
 *
 */
public interface ProductService {

	/**
	 * 导入license
	 * 
	 * @param productId
	 * @param licenseList
	 * @return
	 */
	public boolean addLicense(String productId, List<String> licenseList);

	/**
	 * 查询指定范围的license
	 * 
	 * @param productId
	 * @param from
	 * @param to
	 * @return
	 */
	public Map<String, Integer> queryLicense(String productId, int from, int to);

	/**
	 * 查询产品
	 * 
	 * @param productId
	 * @return
	 */
	public Product query(String productId);
	
	/**
	 * 保存产品信息
	 * 
	 * @param product
	 * @return
	 */
	public boolean save(Product product);

	/**
	 * 查询企业的产品
	 * 
	 * @param companyId
	 * @return
	 */
	public abstract List<Product> queryByCompany(String companyId);

	/**
	 * 查询product对应的license及状态，最多返回1W 条记录
	 * 
	 * @param productId
	 * @return
	 */
	public abstract Map<String, Integer> queryLicense(String productId);


}
