package com.kingdee.eas.fdc.finance.app.voucher;

import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitException;
import com.kingdee.eas.fi.cas.PaymentBillInfo;

public interface IFDCVoucherEntryCreator {
	/**
	 * 客户类传入参数，创建付款单分录以生成凭证
	 * @param parma param内包含付款单集合PaymentBillCollection coll= param.get("PaymentBillCollection");
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @throws Exception 
	 */
	public void createEntrys(Map parma) throws BOSException, EASBizException;
	
	
	
	/**
	 * 进度款对应科目
	 * @param acctId
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	abstract AccountViewInfo getProgressAccountView(CostAccountInfo key) throws BOSException, EASBizException;
	/**
	 * 第一笔结算款对应科目
	 * @param acctId
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	abstract AccountViewInfo getFirstSettleAccountView(CostAccountInfo key) throws EASBizException, BOSException;
	/**
	 * 第二笔结算款对应科目
	 * @param acctId
	 * @return
	 */
	abstract AccountViewInfo getSettleAccountView(CostAccountInfo key);
	/**
	 * 保修款对应科目
	 * @param acctId
	 * @return
	 */
	abstract AccountViewInfo getGrtAccountView(CostAccountInfo key);
	
	abstract AccountViewInfo getAccountView(CostAccountInfo costAccount,PaymentBillInfo paymentBillInfo,boolean isProgress,boolean isConWithoutText) throws BOSException, EASBizException;
	/**
	 * 通过成本科目查找对应的发票科目会计
	 * 发票科目与项目状态无关
	 * @param key
	 * @return
	 * @throws EASBizException 
	 */
	abstract AccountViewInfo getInvoiceAccountView(CostAccountInfo costAccount) throws EASBizException;
	
	/**
	 * 不管项目状态通过成本科目查找对应的会计科目
	 * @param key
	 * @return
	 * @throws EASBizException 
	 */
	abstract AccountViewInfo getAccountWithCostAccount(CostAccountInfo costAccountInfo) throws EASBizException;
	abstract Map getCacheMap();
}
