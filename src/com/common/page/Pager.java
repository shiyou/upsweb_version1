package com.common.page;

import java.util.List;

public class Pager {
	public final static int PAGESIZE =10;
	
	private int pageSize = PAGESIZE; //每页显示的记录数
	
	private int totalCount; //总记录数
	
	private List items; //
	
	private int pageNumber; // 当前页码

	public Pager(int totalCount, List items) {
		super();
		this.totalCount = totalCount;
		this.items = items;
	}

	public Pager(int pageSize, int totalCount, List items) {
		super();
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.items = items;
	}
	
	
	public Pager(int pageSize, int totalCount, List items, int pageNumber) {
		super();
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.items = items;
		this.pageNumber = pageNumber;
	}
	/**
	 * 第一条记录
	 */
	public int getFirstIndex(){
		return pageSize*(pageNumber-1);
	}

	/**
	 * 当前页码
	 * @return
	 */
	public int getThisPageNumber(){
		return pageNumber;
	}
	
	/**
	 * 最后页码
	 * @return
	 */
	public int getLastPageNumber(){
		int result= totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
		if(result<=1) 
			return 1;
		return result;
	}

	/**
	 * 判断第一页
	 * @return
	 */
	public  boolean isFirstPage(){
		return getThisPageNumber()==1;
	}
	
	/**
	 * 判断最后一页
	 */
	public boolean isLastPage(){
		return getThisPageNumber()==getLastPageNumber();
	}
	
	/**
	 * 判断是否有下一页
	 * @return
	 */
	public boolean isHasNextPage(){
		return getLastPageNumber()>getThisPageNumber();
	}
	
	/**
	 * 判断是否有上一页
	 * @return
	 */
	public boolean isHasPreviousPage(){
		return getThisPageNumber()>1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	

}
