package com.kingdee.eas.fdc.finance.app.voucher;

import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;

public class VoucherCreatorFactory {
	public static IFDCVoucherEntryCreator getPaymentVoucherCreator(Context ctx,String projectStatusId){
		if(projectStatusId==null){
			return emptVoucherCreator;
		}
		AbstractFDCVoucherEntryCreator creator =null;
		if(projectStatusId.equals(ProjectStatusInfo.notGetID)){
			creator = new FDCNotGetVoucherEntryCreator(ctx);
		}else if(projectStatusId.equals(ProjectStatusInfo.flowID)){
			creator = new FDCFlowVoucherEntryCreator(ctx);
		}else{
			creator = new FDCGetVoucherEntryCreator(ctx);
		}
				
		creator.setCostHandler(new PaymentCostVoucherHandler(ctx,creator));
		creator.setPayHandler(new PaymentPayVoucherHandler(ctx,creator));
		return creator;
	}
	
	public static IFDCVoucherEntryCreator getWorkLoadVoucherCreator(Context ctx,String projectStatusId){
		if(projectStatusId==null){
			return emptVoucherCreator;
		}
		AbstractFDCVoucherEntryCreator creator =null;
		if(projectStatusId.equals(ProjectStatusInfo.notGetID)){
			creator = new FDCNotGetVoucherEntryCreator(ctx);
		}else if(projectStatusId.equals(ProjectStatusInfo.flowID)){
			creator = new FDCFlowVoucherEntryCreator(ctx);
		}else{
			creator = new FDCGetVoucherEntryCreator(ctx);
		}
				
		creator.setCostHandler(new WorkLoadCostVoucherHandler(ctx,creator));
		creator.setPayHandler(new WorkLoadPayVoucherHandler(ctx,creator));
		return creator;
	}
	
	private static IFDCVoucherEntryCreator emptVoucherCreator=new EmptVoucherCreator(null);
	private static class EmptVoucherCreator extends AbstractFDCVoucherEntryCreator{
		public EmptVoucherCreator(Context ctx) {
			super(ctx);
		}

		public AccountViewInfo getFirstSettleAccountView(CostAccountInfo key) throws EASBizException, BOSException {
			return null;
		}

		public AccountViewInfo getGrtAccountView(CostAccountInfo key) {
			return null;
		}

		public AccountViewInfo getProgressAccountView(CostAccountInfo key) throws BOSException, EASBizException {
			return null;
		}

		public AccountViewInfo getSettleAccountView(CostAccountInfo key) {
			return null;
		}
		public void createEntrys(Map param) throws BOSException, EASBizException {
			return;
		}
	}
}
