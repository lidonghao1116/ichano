package cn.ichano.common.db.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象. <br>

 */
public class Pager<T> {

	/**
	 * 当前页
	 */
	private int pageNo = 1;

	/**
	 * 返回指定的页
	 */
	private int indexPageNo;

	/**
	 * 每页记录数
	 */
	private int pageSize = 10;

	/**
	 * 记录总数
	 */
	private int totalRows = 0;

	/**
	 * 总页数
	 */
	private int totalPages = 0;

	/**
	 * 记录集合
	 */
	private List<T> resultList;

	public Pager(int pageNo, int pageSize, List<T> resultList, int totalRows) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.resultList = resultList;
		this.totalRows = totalRows;
		this.totalPages = this.totalRows / pageSize + this.totalRows % pageSize == 0 ? 0
				: 1;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getIndexPageNo() {
		return indexPageNo;
	}

	public void setIndexPageNo(int indexPageNo) {
		this.indexPageNo = indexPageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public Pager() {
		this.resultList = new ArrayList<T>();
	}
}
