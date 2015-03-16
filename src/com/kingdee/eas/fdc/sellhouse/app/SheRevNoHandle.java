package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;


/**
 * SheRevHandle 的扩展
 * 也就是不需要考虑收款单收款或删除的反写的情况
 * 
 * 场景： 1.定金转房款时生成的转款单，不用考虑房间状态的变化
 * 		  2.换房结算时 转为预收款，
 * 		  3.退房结算时 写可退金额和转入手续费 
 * 
 * 
 * @author jeegan_wang *
 */
public class SheRevNoHandle extends SheRevHandle {

	public void doBeforeRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException{
	}
	
	public void doAfterRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException {
	}
	
	
	public void doOfDelRev(Context ctx, FDCReceivingBillInfo oldFdcRev)	throws BOSException, EASBizException {
	}
	
}
