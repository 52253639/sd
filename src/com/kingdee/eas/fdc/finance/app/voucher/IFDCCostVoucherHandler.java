package com.kingdee.eas.fdc.finance.app.voucher;

import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.common.EASBizException;

public interface IFDCCostVoucherHandler {
	/**
	 * 生成成本分录
	 * @param dataMap
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public IObjectCollection createCostEntrys(Map dataMap) throws EASBizException, BOSException;
	/**
	 * 保存分录
	 * @param param
	 * @param costEntrys
	 * @throws BOSException
	 */
	public void save(Map param,IObjectCollection costEntrys) throws BOSException;
	/**
	 * 保存之前的初始化工作
	 * @param param
	 */
	public void beforeSave(Map param)  throws BOSException ;
	/**保存之后的操作
	 * @param param
	 */
	public void afterSave(Map param) throws BOSException ;
}
