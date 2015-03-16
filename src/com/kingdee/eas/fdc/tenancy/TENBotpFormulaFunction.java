package com.kingdee.eas.fdc.tenancy;

import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.kscript.KScriptException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMNewReceiveHelper;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryInfo;
import com.kingdee.eas.fm.common.AbstractFuncInfo;
import com.kingdee.eas.fm.common.BOTPBaseFunctions;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rptclient.newrpt.util.ExceptionHandler;

public class TENBotpFormulaFunction extends BOTPBaseFunctions {
	private final class BOTGetPayListStartDate extends AbstractFuncInfo {
		private BOTGetPayListStartDate() {
			super("BOTGetPayListStartDate", "租赁取数函数",
					"获取付款明细中费用开始日期");
		}
		public Object evalFunction(List args) throws KScriptException {
			Context ctx = getContext(args);
			Object obj = args.get(1);
			return getStratDate(ctx, obj);
		}
		
		/*
		 * 获取费用开始日期
		 */
		public Object getStratDate(Context ctx,Object obj)
		{
			if (obj == null) {
				return null;
			}
			//租赁合同房间付款明细
			if (obj instanceof TenancyRoomPayListEntryInfo) {
				TenancyRoomPayListEntryInfo info = (TenancyRoomPayListEntryInfo)obj;
				return info.getStartDate();
			}
			//其他应收付款明细
			if(obj instanceof TenBillOtherPayInfo)
			{
				TenBillOtherPayInfo info = (TenBillOtherPayInfo)obj;
				return info.getStartDate();
			}
			//物业常规应收费用
			if(obj instanceof PPMGeneralARInfo)
			{
				PPMGeneralARInfo info = (PPMGeneralARInfo)obj;
				return info.getStartFeeDate();
			}
			//物业应收计量费用
			if(obj instanceof PPMMeasureARInfo)
			{
				PPMMeasureARInfo info = (PPMMeasureARInfo)obj;
				return info.getStartFeeDate();
			}
			//物业临时应收费用
			if(obj instanceof PPMTemporaryInfo)
			{
				PPMTemporaryInfo info = (PPMTemporaryInfo)obj;
				return info.getStartFeeDate();
			}
			if(obj instanceof FDCReceivingBillEntryInfo)
			{
				FDCReceivingBillEntryInfo entry = (FDCReceivingBillEntryInfo)obj;
				try {
					entry = FDCReceivingBillEntryFactory.getLocalInstance(ctx).getFDCReceivingBillEntryInfo(new ObjectUuidPK(entry.getId()));
				} catch (Exception e) {
					ExceptionHandler.handle("没找到对应的收款单分录！", e);
				} 
				String revListId = entry.getRevListId();
				RevListTypeEnum revListType =  entry.getRevListType();
				//如果是租赁系统收款明细
				try {
				if(RevListTypeEnum.tenRoomRev.equals(revListType) || RevListTypeEnum.tenOtherRev.equals(revListType)
						|| RevListTypeEnum.sincerobligate.equals(revListType))
				{				
						ICoreBase iface = TENRevHelper.getRevListBizInterface(ctx, revListType);
						IRevListInfo revList = (IRevListInfo) iface.getValue(new ObjectUuidPK(revListId));
						if(revList instanceof TenancyRoomPayListEntryInfo)
						{
							TenancyRoomPayListEntryInfo info = (TenancyRoomPayListEntryInfo)revList;
							return info.getStartDate();
						}else if(revList instanceof TenBillOtherPayInfo)
						{
							TenBillOtherPayInfo info = (TenBillOtherPayInfo)revList;
							return info.getStartDate();
						}else
						{
							return null;
						}
					
					//如果是物业系统的收款明细
				}else if(RevListTypeEnum.generalar.equals(revListType) || RevListTypeEnum.temporary.equals(revListType)
						|| RevListTypeEnum.measureAR.equals(revListType) || RevListTypeEnum.depositRev.equals(revListType)
						|| RevListTypeEnum.depositWithdraw.equals(revListType))
				{
					ICoreBase iface = PPMNewReceiveHelper.getRevListBizInterface(ctx, revListType);
					IRevListInfo revList = (IRevListInfo) iface.getValue(new ObjectUuidPK(revListId));
					if(revList instanceof PPMGeneralARInfo)
					{
						PPMGeneralARInfo info = (PPMGeneralARInfo)revList;
						return info.getStartFeeDate();
					}else if(revList instanceof PPMMeasureARInfo)
					{
						PPMMeasureARInfo info = (PPMMeasureARInfo)revList;
						return info.getStartFeeDate();
					}else if(revList instanceof PPMTemporaryInfo)
					{
						PPMTemporaryInfo info = (PPMTemporaryInfo)revList;
						return info.getStartFeeDate();
					}else
					{
						return null;
					}
				}
				} catch (BOSException e) {
					e.printStackTrace();
				} catch (EASBizException e) {
					e.printStackTrace();
				}											
			}
			return null;
		}
	}
	
	private final class BOTGetPayListEndDate extends AbstractFuncInfo {
		private BOTGetPayListEndDate() {
			super("BOTGetPayListEndDate","租赁取数函数",
					"获取付款明细中费用结束日期");
		}	
		public Object evalFunction(List args) throws KScriptException {
			Context ctx = getContext(args);
			Object obj = args.get(1);
			return getEndDate(ctx, obj);
	}		
	
	/*
	 * 获取费用结束日期
	 */
	public Object getEndDate(Context ctx,Object obj)
	{
		if (obj == null) {
			return null;
		}
		//租赁合同房间付款明细
		if (obj instanceof TenancyRoomPayListEntryInfo) {
			TenancyRoomPayListEntryInfo info = (TenancyRoomPayListEntryInfo)obj;
			return info.getEndDate();
		}
		//其他应收付款明细
		if(obj instanceof TenBillOtherPayInfo)
		{
			TenBillOtherPayInfo info = (TenBillOtherPayInfo)obj;
			return info.getEndDate();
		}
		//物业常规应收费用
		if(obj instanceof PPMGeneralARInfo)
		{
			PPMGeneralARInfo info = (PPMGeneralARInfo)obj;
			return info.getEndFeeDate();
		}
		//物业应收计量费用
		if(obj instanceof PPMMeasureARInfo)
		{
			PPMMeasureARInfo info = (PPMMeasureARInfo)obj;
			return info.getEndFeeDate();
		}
		//物业临时应收费用
		if(obj instanceof PPMTemporaryInfo)
		{
			PPMTemporaryInfo info = (PPMTemporaryInfo)obj;
			return info.getEndFeeDate();
		}
		if(obj instanceof FDCReceivingBillEntryInfo)
		{
			FDCReceivingBillEntryInfo entry = (FDCReceivingBillEntryInfo)obj;
			try {
				entry = FDCReceivingBillEntryFactory.getLocalInstance(ctx).getFDCReceivingBillEntryInfo(new ObjectUuidPK(entry.getId()));
			} catch (Exception e) {
				ExceptionHandler.handle("没找到对应的收款单分录！", e);
			} 
			String revListId = entry.getRevListId();
			RevListTypeEnum revListType =  entry.getRevListType();
			//如果是租赁系统收款明细
			try {
			if(RevListTypeEnum.tenRoomRev.equals(revListType) || RevListTypeEnum.tenOtherRev.equals(revListType)
					|| RevListTypeEnum.sincerobligate.equals(revListType))
			{				
					ICoreBase iface = TENRevHelper.getRevListBizInterface(ctx, revListType);
					IRevListInfo revList = (IRevListInfo) iface.getValue(new ObjectUuidPK(revListId));
					if(revList instanceof TenancyRoomPayListEntryInfo)
					{
						TenancyRoomPayListEntryInfo info = (TenancyRoomPayListEntryInfo)revList;
						return info.getEndDate();
					}else if(revList instanceof TenBillOtherPayInfo)
					{
						TenBillOtherPayInfo info = (TenBillOtherPayInfo)revList;
						return info.getEndDate();
					}else
					{
						return null;
					}
				
				//如果是物业系统的收款明细
			}else if(RevListTypeEnum.generalar.equals(revListType) || RevListTypeEnum.temporary.equals(revListType)
					|| RevListTypeEnum.measureAR.equals(revListType) || RevListTypeEnum.depositRev.equals(revListType)
					|| RevListTypeEnum.depositWithdraw.equals(revListType))
			{
				ICoreBase iface = PPMNewReceiveHelper.getRevListBizInterface(ctx, revListType);
				IRevListInfo revList = (IRevListInfo) iface.getValue(new ObjectUuidPK(revListId));
				if(revList instanceof PPMGeneralARInfo)
				{
					PPMGeneralARInfo info = (PPMGeneralARInfo)revList;
					return info.getEndFeeDate();
				}else if(revList instanceof PPMMeasureARInfo)
				{
					PPMMeasureARInfo info = (PPMMeasureARInfo)revList;
					return info.getEndFeeDate();
				}else if(revList instanceof PPMTemporaryInfo)
				{
					PPMTemporaryInfo info = (PPMTemporaryInfo)revList;
					return info.getEndFeeDate();
				}else
				{
					return null;
				}
			}
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			}											
		}
		return null;
	  }
    }
	
	public TENBotpFormulaFunction() {
		super();
		AbstractFuncInfo funcInfo1 = new BOTGetPayListStartDate();
		funcInfos.put(funcInfo1.getFuncName(), funcInfo1);
		AbstractFuncInfo funcInfo2 = new BOTGetPayListEndDate();
		funcInfos.put(funcInfo2.getFuncName(), funcInfo2);
	}
}
