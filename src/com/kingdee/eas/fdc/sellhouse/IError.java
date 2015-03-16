package com.kingdee.eas.fdc.sellhouse;

import java.sql.SQLException;
import java.util.List;

import com.kingdee.bos.BOSException;

public interface IError {
	
	/**
	 * 匹配该类错误的纪录,返回纪录集合
	 * @return List<IErrorData>
	 * @throws BOSException 
	 * @throws SQLException 
	 * */
	List matchErrorDatas() throws BOSException, SQLException;
	
	/**
	 * 返回该类错误的处理方案集合
	 * @return List<IHandleErrorData>
	 * */
	List getHandles();
	
	/**
	 * 获得该类错误的描述信息
	 * */
	String getDes();
}
