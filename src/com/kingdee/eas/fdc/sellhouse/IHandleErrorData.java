package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;

public interface IHandleErrorData {
	
	/**
	 * 针对错误数据进行处理,返回处理报告
	 * @param errorData 待处理的错误数据
	 * @return IHandleReport
	 * */
	IHandleReport handleErrorData(IErrorData errorData) throws EASBizException, BOSException;
	
	String getDes();
}
