package com.kingdee.eas.fdc.sellhouse.client;

/**
 * 常量存储
 * */
public class FDCCustomerConstant {
	public static final String RELATED_BY_CUSTOMER_DES = "已被房地产客户关联,不可删除";
	
	public static final String RELATED_BY_TRACK_RECORD_DES = "已被客户跟进记录关联,不可删除";
	
	public static final String CAN_NOT_DEL_DES = "保留基础资料,不可删除";

	public static final String CAN_NOT_EDIT_DES = "保留基础资料,不可修改";
	
	/**
	 * 预设的编码为10-15的事件类型基础资料不可删除
	 * */
	public static final String[] CAN_NOT_DEL_NUMBERS = new String[]{"10","11","12","13","14","15"};
}
