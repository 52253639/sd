package com.kingdee.eas.fdc.finance.app.voucher;

import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;

/**
 * 项目未获取
 * 
 * @author xiaohong_shi
 * 
 */
public class FDCNotGetVoucherEntryCreator extends
		AbstractFDCVoucherEntryCreator {

	public FDCNotGetVoucherEntryCreator(Context ctx) {
		super(ctx);
	}

	/****
	 * 项目未获取
	 * 1）进度款 
	 * 借：预付账款_开发成本_其他成本 
	 * @throws EASBizException 
	 */
	public AccountViewInfo getProgressAccountView(CostAccountInfo key) throws EASBizException {
		return beforeAccountViewInfo.getBeforeOtherAccount();
	}

	/***
	 * 项目未获取
	 * 2）结算款（第一笔）
	 * * 借：预付账款_开发成本_其他成本 
	 * @throws EASBizException 
	 */
	public AccountViewInfo getFirstSettleAccountView(CostAccountInfo key) throws EASBizException {
		return beforeAccountViewInfo.getBeforeOtherAccount();
	}
	
	/***
	 * 项目未获取
	 * 3) 结算款（第二笔）
	 * * 借：应付账款_开发成本_进度款（一体化科目设置对应应付进度款） 
	 */
	public AccountViewInfo getSettleAccountView(CostAccountInfo key) {
		// TODO Auto-generated method stub
		return beforeAccountViewInfo.getProAccount();
	}
	
	/***
	 * 项目未获取
	 * 4）保修款
	 * 借：应付账款_开发成本_保修款
	 * 周勇
	 */
	public AccountViewInfo getGrtAccountView(CostAccountInfo key) {
		return beforeAccountViewInfo.getSettAccount();
	}

}
