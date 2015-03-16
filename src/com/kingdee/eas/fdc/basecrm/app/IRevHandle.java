package com.kingdee.eas.fdc.basecrm.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 服务器端收款辅助处理接口，供各业务系统扩展
 * */
public interface IRevHandle {
	/**
	 * 删除收款单时对业务系统的反写
	 * */
	void doOfDelRev(Context ctx, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException;
	
	/**
	 * 收款之前的校验及操作
	 * @param oldFdcRev 
	 * */
	void doBeforeRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException;
	
	/**
	 * 收款之后的操作，主要是对各业务系统的反写
	 * @param oldFdcRev 
	 * */
	void doAfterRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException;
	
	/**
	 * 根据收款明细的ID和类型,查询处收款明细对象
	 * */
	IRevListInfo getRevListInfoObj(Context ctx, String id, RevListTypeEnum revListType)throws EASBizException, BOSException;

	/**
	 * 根据收款明细的类型,返回操作收款明细的业务接口
	 * @throws BOSException 
	 * */
	ICoreBase getRevListBizInterface(Context ctx, RevListTypeEnum revListType) throws BOSException;

	/**
	 * 根据类型,获得实收金额字段名
	 * */
	String getActRevFieldName(RevListTypeEnum revListType);

	/**
	 * 根据类型,获得已退金额字段名
	 * */
	String getHasRefundmentFieldName(RevListTypeEnum revListType);

	/**
	 * 根据类型,获得已入费用金额字段名
	 * */
	String getHasToFeeFieldName(RevListTypeEnum revListType);

	/**
	 * 根据类型,获得已转金额字段名
	 * */
	String getHasTransferredFieldName(RevListTypeEnum revListType);
	
	/**
	 * 根据类型,获得应收金额字段名
	 * */
	String getAppRevFieldName(RevListTypeEnum revListType);
	
	/**
	 * 根据类型,获得实收日期字段名
	 * */
	String getActRevDateFieldName(RevListTypeEnum revListType);
	
}
