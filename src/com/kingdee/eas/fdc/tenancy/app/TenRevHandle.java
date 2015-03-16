package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.app.AbstractRevHandle;
import com.kingdee.eas.fdc.tenancy.TENRevHelper;
import com.kingdee.eas.framework.ICoreBase;

public class TenRevHandle extends AbstractRevHandle{

	public void doAfterRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException {
		// 租赁目前没有收款后的需要反写业务单据的

	}

	public void doBeforeRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException {
		//租赁判断是否可以收款
	}

	protected String[] getNeededFieldNames(RevListTypeEnum revListType){
		//租赁这边统一了各字段的名称
		return new String[]{"actRevAmount", "hasRefundmentAmount", "hasToFeeAmount", "hasTransferredAmount", "appAmount","actRevDate"};
		
		/*
		if(RevListTypeEnum.tenRoomRev.equals(revListType)){
			return new String[]{"", "", "", ""};
		}else if(RevListTypeEnum.tenOtherRev.equals(revListType)){
			//TODO 租赁其他收款明细
			
		}
		return null;
		*/
	}
	
	public String getActRevDateFieldName(RevListTypeEnum revListType) {
		return super.getActRevDateFieldName(revListType);
	}
	
	public ICoreBase getRevListBizInterface(Context ctx, RevListTypeEnum revListType) throws BOSException {
		return TENRevHelper.getRevListBizInterface(ctx, revListType);
	}

}
