package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.framework.ICoreBase;

public class TENRevHelper {
	
	/**
	 * 根据收款明细类型和id获得收款明细的值对象 
	 * */
	public static IRevListInfo getRevListInfo(RevListTypeEnum revListType, String revListId) throws BOSException, EASBizException {
		return getRevListInfo(null, revListType, revListId, getSelectorItemCollection(revListType));
	}
	
	public static IRevListInfo getRevListInfo(Context ctx, RevListTypeEnum revListType, String revListId, SelectorItemCollection sels) throws BOSException, EASBizException{
		ICoreBase bizI = getRevListBizInterface(ctx, revListType);
		if(bizI != null  &&  revListId != null){
			return (IRevListInfo) bizI.getValue(new ObjectUuidPK(revListId), sels);
		}
		return null;
	}
	
	public static ICoreBase getRevListBizInterface(Context ctx, RevListTypeEnum revListType) throws BOSException {
		//目前针对租赁,主要是这几种类型:租赁房间收款分录; TODO 租赁其他收款分录林; TODO 诚意预留收款分录; TODO 客户收款
		if(RevListTypeEnum.tenRoomRev.equals(revListType)){
			return ctx == null ? TenancyRoomPayListEntryFactory.getRemoteInstance() : TenancyRoomPayListEntryFactory.getLocalInstance(ctx);
		}else if(RevListTypeEnum.tenOtherRev.equals(revListType)){
			//TODO 租赁其他收款明细
			return ctx == null ? TenBillOtherPayFactory.getRemoteInstance() : TenBillOtherPayFactory.getLocalInstance(ctx);
		}else if(RevListTypeEnum.sincerobligate.equals(revListType))
		{
			return ctx == null ? SincerPaylistEntrysFactory.getRemoteInstance() : SincerPaylistEntrysFactory.getLocalInstance(ctx);
		}
		return null;
	}
	
	public static SelectorItemCollection getSelectorItemCollection(RevListTypeEnum revListType){
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("moneyDefine.*");
		if (RevListTypeEnum.tenRoomRev.equals(revListType)) {
			sels.add("tenRoom.roomLongNum");
			//by tim_gao 加入合同显示
			sels.add("tenBill.tenancyName");
			sels.add("tenBill.number");
		} else if (RevListTypeEnum.tenOtherRev.equals(revListType)) {
			
		} else if (RevListTypeEnum.sincerobligate.equals(revListType)) {
		
		}
		
		return sels;
	}
}
