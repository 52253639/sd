package com.kingdee.eas.fdc.finance.app.voucher;

import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;

/**
 * 项目流失
 * @author xiaohong_shi
 *
 */
public class FDCFlowVoucherEntryCreator extends
		AbstractFDCVoucherEntryCreator {

	public FDCFlowVoucherEntryCreator(Context ctx) {
		super(ctx);
	}
	
	
	/***
	 * 项目流失
	 * 1）进度款
	 * 借：管理费用/营销费用（拆分到4301.408.03营销推广费下的）
	 */
	public AccountViewInfo getProgressAccountView(CostAccountInfo key) {
		
		// TODO Auto-generated method stub
		if(key.getFlag()==1)
			return beforeAccountViewInfo.getMarketFees();
		else
			return beforeAccountViewInfo.getAdminFees();
	}
	
	/***
	 * 项目流失
	 * 2）结算款（第一笔）
	 * 借：预付账款_开发成本_其他成本
	 */
	public AccountViewInfo getFirstSettleAccountView(CostAccountInfo key) {
		// TODO 自动生成方法存根
		return beforeAccountViewInfo.getBeforeOtherAccount();
	}
	
	/***
	 * 项目流失
	 * 3) 结算款（第二笔）
	 * 应付账款_开发成本_进度款（一体化科目设置对应应付进度款）
	 */
	public AccountViewInfo getSettleAccountView(CostAccountInfo key) {
		// TODO Auto-generated method stub
		return beforeAccountViewInfo.getProAccount();
	}
	
	/***
	 * 项目流失
	 * 4）保修款
	 * 借：应付账款_开发成本_保修款
	 */
	public AccountViewInfo getGrtAccountView(CostAccountInfo key) {
		return beforeAccountViewInfo.getSettAccount();
	}

}
