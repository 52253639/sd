package com.kingdee.eas.fdc.basecrm.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ObjectNotFoundException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.framework.ICoreBase;


public abstract class AbstractRevHandle implements IRevHandle {
	public void doOfDelRev(Context ctx, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException{
		// TODO Auto-generated method stub
	}
	
	public void doBeforeRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException{
		// TODO Auto-generated method stub
	}

	public void doAfterRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException{
		// TODO Auto-generated method stub
	}

	/**
	 * 根据类型，返回相应的字段名
	 * @return new String[]{收款金额,已退金额,已转费用金额,已转金额,应收金额,实收日期};
	 * */
	protected abstract String[] getNeededFieldNames(RevListTypeEnum revListType);
	
	public String getActRevFieldName(RevListTypeEnum revListType) {
		return getFieldNameFromArray(revListType, 0);
	}
	
	private String getFieldNameFromArray(RevListTypeEnum revListType, int index){
		String[] tmps = this.getNeededFieldNames(revListType);
		//增加数组越界的判断
		if(tmps != null  &&  tmps.length > index){
			return tmps[index];
		}
		return null;
	}
	
	public String getHasRefundmentFieldName(RevListTypeEnum revListType) {
		return getFieldNameFromArray(revListType, 1);
	}

	public String getHasToFeeFieldName(RevListTypeEnum revListType) {
		return getFieldNameFromArray(revListType, 2);
	}

	public String getHasTransferredFieldName(RevListTypeEnum revListType) {
		return getFieldNameFromArray(revListType, 3);
	}

	public String getAppRevFieldName(RevListTypeEnum revListType) {
		return getFieldNameFromArray(revListType, 4);
	}
	
	public String getActRevDateFieldName(RevListTypeEnum revListType) {
		return getFieldNameFromArray(revListType, 5);
	}
	
	public IRevListInfo getRevListInfoObj(Context ctx, String id, RevListTypeEnum revListType)throws EASBizException, BOSException {
		ICoreBase bizI = this.getRevListBizInterface(ctx, revListType);
		if(bizI != null  &&  id != null){
			try{
				return (IRevListInfo) bizI.getValue(new ObjectUuidPK(id));
			}catch(ObjectNotFoundException e){
				return null;
			}
		}
		return null;
	}
	
}
