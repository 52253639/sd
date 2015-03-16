package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.sellhouse.CluesManageFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.CluesSourceEnum;
import com.kingdee.eas.fdc.sellhouse.CluesStatusEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.ISincerityPurchase;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.TrackPhaseEnum;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.util.app.ContextUtil;
public class SincerityPurchaseControllerBean extends
		AbstractSincerityPurchaseControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.SincerityPurchaseControllerBean");

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		SincerityPurchaseInfo sin = (SincerityPurchaseInfo) model;
		sin.setSincerityState(SincerityPurchaseStateEnum.ArrangeNum);
		//更新房间
		if(null!=sin.getRoom()){
			RoomInfo room = (RoomInfo)sin.getRoom();
			sin.setRoom(room);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",room.getId()));
			filter.getFilterItems().add(new FilterItemInfo("sellState",RoomSellStateEnum.INIT_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("sellState",RoomSellStateEnum.ONSHOW_VALUE));
			filter.setMaskString("#0 and (#1 or #2)");
			IRoom roomfac = (IRoom)RoomFactory.getLocalInstance(ctx);
			if(roomfac.exists(filter)){
//				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//				builder.appendSql(" update t_she_room set fsellstate = 'SincerPurchase' where fid ='"+room.getId().toString()+"'");
//				builder.execute();
				SelectorItemCollection selecotr = new SelectorItemCollection();
				selecotr.add("sellState");
				room.setSellState(RoomSellStateEnum.SincerPurchase);				
				roomfac.updatePartial(room, selecotr);
			}
		}
		
//		return super._submit(ctx, model);
		
		
		//生成线索客户
		if(null!=sin.getCustomer()&&sin.getCustomer().size()<=0){
			
			CluesManageInfo cluesManage = new CluesManageInfo();
	    	cluesManage.setName(sin.getAppointmentPeople());
	    	ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
			.getLocalInstance(ctx);
			OrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
			boolean existCodingRule = iCodingRuleManager.isExist(
					new FDCOrgCustomerInfo(), orgUnit.getId().toString());
			if (existCodingRule) {
				String retNumber = iCodingRuleManager.getNumber(cluesManage, orgUnit
						.getId().toString());
				cluesManage.setNumber(retNumber);
			} else {
				Timestamp time = new Timestamp(new Date().getTime());
				String  timeStr = String.valueOf(time).replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").replaceAll(".", "");
				String number = String.valueOf(timeStr)+"001";
				cluesManage.setNumber(number);
			}
//	    	cluesManage.setNumber(sin.getAppointmentPeople()+sin.getAppointmentPhone());
	    	cluesManage.setPhone(sin.getAppointmentPhone());
	    	cluesManage.setSellProject(SHEManageHelper.getParentSellProject(ctx, sin.getSellProject()));
	    	cluesManage.setSource(CluesSourceEnum.SINCERITYPRUCHASE);
	    	if(null!=sin.getSaleMansEntry()&&sin.getSaleMansEntry().size()>0){
	    		cluesManage.setPropertyConsultant(sin.getSaleMansEntry().get(0).getUser());
	    	}else{
	    		cluesManage.setPropertyConsultant(SysContext.getSysContext().getCurrentUserInfo());
	    	}
	    
	    	cluesManage.setCluesStatus(CluesStatusEnum.CUSTOMER);
	    	if(null!=sin.getId()){//修改
	    		SelectorItemCollection selec = new SelectorItemCollection();
	    		selec.add("cluesCus.name");
	    		selec.add("cluesCus.sellProject");
	    		selec.add("cluesCus.number");
	    		selec.add("cluesCus.phone");
	    		selec.add("cluesCus.id");
	    		selec.add("cluesCus.source");
	    		selec.add("cluesCus.*");
	    		selec.add("propertyConsultant.*");
				SincerityPurchaseInfo sinPurInfo = null;
				sinPurInfo =SincerityPurchaseFactory.getLocalInstance(ctx).getSincerityPurchaseInfo(new ObjectUuidPK(BOSUuid.read(sin.getId().toString())), selec);
				if(null!=sinPurInfo.getCluesCus()){

					SelectorItemCollection selecClus = new SelectorItemCollection();
					selecClus.add("name");
					selecClus.add("number");
					selecClus.add("phone");
					selecClus.add("source");
					selecClus.add("propertyConsultant");
					cluesManage.setId(sinPurInfo.getCluesCus().getId());
					CluesManageFactory.getLocalInstance(ctx).updatePartial(cluesManage, selec);
					sin.setCluesCus(cluesManage);
				}
			
	    	}else{//新增
				IObjectPK cluesId = CluesManageFactory.getLocalInstance(ctx).addnew(cluesManage);
		    	cluesManage.setId((BOSUuid)cluesId.getKeyValue(null));
		    	sin.setCluesCus(cluesManage);
			}
		}
		//业务状态
		sin.setBizState(TransactionStateEnum.BAYNUMBER);
		
		IObjectPK sinId =  super._submit(ctx, sin);
		
    	
		//交易主线
		SHEManageHelper.updateTransaction(ctx, sin,RoomSellStateEnum.SincerPurchase,true);
		
		return sinId ;
		//return ((IObjectPK)new ObjectUuidPK(sin.getId()));
	}

	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		SincerityPurchaseInfo sin = (SincerityPurchaseInfo) model;
//		sin.setSincerityState(SincerityPurchaseStateEnum.ArrangeNum);
		//更新房间
		if(null!=sin.getRoom()){
			SelectorItemCollection selecotr = new SelectorItemCollection();
			selecotr.add("sellState");
			RoomInfo room = (RoomInfo)sin.getRoom();
			room.setSellState(RoomSellStateEnum.SincerPurchase);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",room.getId()));
			filter.getFilterItems().add(new FilterItemInfo("sellState",RoomSellStateEnum.INIT_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("sellState",RoomSellStateEnum.ONSHOW_VALUE));
			filter.setMaskString("#0 and (#1 or #2)");
			IRoom roomfac = (IRoom)RoomFactory.getLocalInstance(ctx);
			if(roomfac.exists(filter)){
				roomfac.updatePartial(room, selecotr);
				
			}
		}
		sin.setBizState(TransactionStateEnum.BAYNUMBER);
		IObjectPK sinId = super._save(ctx, model);
		
		//交易主线
		
		return sinId;
	}
	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		FDCBillInfo billInfo = ((FDCBillInfo) model);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("number", billInfo.getNumber()));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit()
								.getId()));
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NUMBER_DUP);
		}

	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		SincerityPurchaseInfo info = (SincerityPurchaseInfo) getValue(ctx, pk);
		FilterInfo filter = new FilterInfo();
		this.exists(ctx, filter);
//		SHEManageHelper.updateCommerceChanceStatus(ctx,info.getTransactionID().toString());
		SHEManageHelper.toOldTransaction(ctx,info.getTransactionID());
		super._delete(ctx, pk);
		
	}
	protected void _submitSincer(Context ctx, IObjectCollection sincerityColl)
			throws BOSException, EASBizException {		
		SincerityPurchaseCollection sinColl = (SincerityPurchaseCollection)sincerityColl;
		for(int i=0;i<sinColl.size();i++)
		{
				this._submit(ctx, sinColl.getObject(i));		
		}
	}

	protected void _quitNum(Context ctx, IObjectValue sinPurInfo, IObjectCollection selectorCollection) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		SincerityPurchaseInfo sin = (SincerityPurchaseInfo)sinPurInfo;
		SelectorItemCollection selc = (SelectorItemCollection)selectorCollection;
		SincerityPurchaseFactory.getLocalInstance(ctx).updatePartial(sin,
				selc);
//		SHEManageHelper.updateCommerceChanceStatus(ctx,sin.getTransactionID().toString());
		SHEManageHelper.toOldTransaction(ctx,sin.getTransactionID());
	}

}