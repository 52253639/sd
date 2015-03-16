package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;

public class SolidSideInfo {
	/**
	 * 单元格绑定的对象
	 */
	private Object bizObj;
	/**
	 * 单元格绑定的对象ID
	 */
	private String id;	
	/**
	 * 所在行 行高
	 */
	private BigDecimal rowhigh;	
	/**
	 * 单元格长
	 */
	private BigDecimal extent;
	/**
	 * 所在行索引
	 */
	private int rowIndex;
	/**
	 * 所在列索引
	 */
	private int colIndex;
	/**
	 * 单元格显示文字
	 */
	private String text;
	/**
	 * 所在行名称 用于在索引列显示
	 */
	private String rowName;
	/**
	 * 状态 决定显示颜色
	 */
	private String state;
	/**
	 *  名称 立面图基本单位名称
	 */
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRowName() {
		return rowName;
	}
	public void setRowName(String rowName) {
		this.rowName = rowName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Object getBizObj() {
		return bizObj;
	}
	public void setBizObj(Object bizObj) {
		this.bizObj = bizObj;
	}
	public BigDecimal getExtent() {
		return extent;
	}
	public void setExtent(BigDecimal extent) {
		this.extent = extent;
	}
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public int getColIndex() {
		return colIndex;
	}
	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}
	public BigDecimal getRowhigh() {
		return rowhigh;
	}
	public void setRowhigh(BigDecimal rowhigh) {
		this.rowhigh = rowhigh;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
