package com.kingdee.eas.fdc.finance.app.voucher;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;

/**
 * 项目已获取
 * 对应"进行中"状态
 * ProjectStatusInfo.proceedingID
 * @author xiaohong_shi
 *
 */
public class FDCGetVoucherEntryCreator extends
		AbstractFDCVoucherEntryCreator {

	public FDCGetVoucherEntryCreator(Context ctx) {
		super(ctx);
	}	
	

	/***
	 * 项目进行中
	 * 1) 进度款
	 * 借：开发成本
	 * 周勇
	 */
	public AccountViewInfo getProgressAccountView(CostAccountInfo key) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		return getAccountWithCostAccount(key);
	}
	
	/***
	 * 项目进行中
	 * 2) 结算款 第一笔 
	 * 借：开发成本
	 * 周勇
	 */
	public AccountViewInfo getFirstSettleAccountView(CostAccountInfo key) throws EASBizException, BOSException {
		// TODO 自动生成方法存根
		return getAccountWithCostAccount(key);
	}
	
	/***
	 * 项目进行中
	 * 3) 结算款第二笔
	 * 借：应付账款_开发成本_进度款（一体化科目设置对应应付进度款）
	 * 周勇
	 */
	public AccountViewInfo getSettleAccountView(CostAccountInfo key) {		
		return beforeAccountViewInfo.getProAccount();
	}
	
	/***
	 * 项目进行中
	 * 4) 保修款
	 * 借：应付账款_开发成本_保修款
	 * 周勇
	 */
	public AccountViewInfo getGrtAccountView(CostAccountInfo key) {
		return beforeAccountViewInfo.getSettAccount();
	}

}
