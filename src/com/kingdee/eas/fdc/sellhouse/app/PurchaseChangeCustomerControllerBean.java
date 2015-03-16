package com.kingdee.eas.fdc.sellhouse.app;

import java.util.Date;
import java.util.Enumeration;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.PurCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCusomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCusomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;

public class PurchaseChangeCustomerControllerBean extends
		AbstractPurchaseChangeCustomerControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.PurchaseChangeCustomerControllerBean");

	protected void _setState(Context ctx, BOSUuid id, FDCBillStateEnum state)
			throws BOSException, EASBizException {
		PurchaseChangeCustomerInfo changeCustomerInfo = new PurchaseChangeCustomerInfo();
		changeCustomerInfo.setId(id);
		changeCustomerInfo.setState(state);
		SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
		selectorItemCollection.add("state");
		PurchaseChangeCustomerFactory.getLocalInstance(ctx).updatePartial(changeCustomerInfo, selectorItemCollection);
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		String id  = pk.toString();
		//同步更新交易客户表  by zgy 
		String sql  = "delete T_SHE_PurchaseCustomer where fpurchaseid ='" + id + "' or FOldPurCustomer = '"+ id +"'";
		DbUtil.execute(ctx, sql);
		super._delete(ctx, pk);
	}
	// base1是有数据的 base2是空的
	void changeObjectValue(CoreBaseInfo base1, CoreBaseInfo base2) {
		Enumeration enumeration = base1.keys();
		while (enumeration.hasMoreElements()) {
			String obj = enumeration.nextElement().toString();
			if (!"id".equals(obj)) {
				base2.put(obj, base1.get(obj));
			}
		}
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		_setState(ctx, billId, FDCBillStateEnum.AUDITTED);
		
		PurchaseChangeCustomerInfo changeCustomerInfo = new PurchaseChangeCustomerInfo();
		changeCustomerInfo.setId(billId);
		changeCustomerInfo.setAuditTime(new Date());
		changeCustomerInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
		selectorItemCollection.add("auditTime");
		selectorItemCollection.add("auditor");
		_updatePartial(ctx, changeCustomerInfo, selectorItemCollection);
		
		_purchaseChangeCustomerToPurchase(ctx, billId);
	}
	
	protected void _purchaseChangeCustomerToPurchase(Context ctx, BOSUuid id)
			throws BOSException, EASBizException {
		PurchaseChangeCusomerEntryCollection changeCustColl = PurchaseChangeCusomerEntryFactory.getLocalInstance(ctx)
				.getPurchaseChangeCusomerEntryCollection("select head.id,head.purchase,propertyPercent,seq,customer.name,customer.phone,customer.tel,salesman.id  where head.id='"+id.toString()+"' order by seq");
		if(changeCustColl.size()==0) return;
		PurchaseInfo purchaseInfo = new PurchaseInfo();
		purchaseInfo.setId(changeCustColl.get(0).getHead().getPurchase().getId());
		//需要反写customerphones eric_wang 2010.08.18
		String customerPhones="";
		String customerNames = "";
		CoreBaseCollection purchaseCustomerEntryCollection = new CoreBaseCollection();
		for (int i = 0; i < changeCustColl.size(); i++) {
			PurchaseCustomerInfoInfo customerInfo = new PurchaseCustomerInfoInfo();
			changeObjectValue(changeCustColl.get(i),customerInfo);
			customerInfo.setHead(purchaseInfo);
			purchaseCustomerEntryCollection.add(customerInfo);
			if (i > 0) {
				customerNames += ",";
			}
			customerNames += customerInfo.getCustomer().getName();
			//客户电话处理
			if(i>0){
				customerPhones+=";";
			}
			customerPhones +=customerInfo.getCustomer().getPhone();
			customerPhones +=",";
			if(customerInfo.getCustomer().getTel()!=null){
				customerPhones +=customerInfo.getCustomer().getTel();
			}
			
//			PurCustomerInfo purCusinfo = new PurCustomerInfo();   //by zgy   
//			purCusinfo.setParent(purchaseInfo);
//			purCusinfo.setSeq(i);
//			purCusinfo.setPropertyPercent(customerInfo.getPropertyPercent());
//			purCusinfo.setCustomerName(customerInfo.getCustomer().getName());
//			purCusinfo.setCustomerID(customerInfo.getCustomer().getId());
//			purCusinfo.setTel(customerInfo.getCustomer().getTel());
//			purCusinfo.setPostalcode(customerInfo.getCustomer().getPostalcode());
//			purCusinfo.setMailAddress(customerInfo.getCustomer().getMailAddress());
//			purCusinfo.setDescription(customerInfo.getCustomer().getDescription());
//			purCusinfo.setCertificateName(customerInfo.getCustomer().getCertificateName());
//			purCusinfo.setCertificateNumber(customerInfo.getCustomer().getCertificateNumber());
//			purCusinfo.setSex(customerInfo.getCustomer().getSex());
//			purCutomerCollection.add(purCusinfo);
		}
		PurchaseCustomerInfoFactory.getLocalInstance(ctx).delete(
				"where head = '" + purchaseInfo.getId().toString() + "'");
		PurchaseCustomerInfoFactory.getLocalInstance(ctx).save(purchaseCustomerEntryCollection);
		//同步更新交易客户表  by zgy 
		PurCustomerFactory.getLocalInstance(ctx).delete("where parent = '" + purchaseInfo.getId().toString() + "'");
		String sql  = "update T_SHE_PurchaseCustomer set FParentID = '"+ purchaseInfo.getId().toString()+"' where fpurchaseid ='" + id.toString() + "'";
		DbUtil.execute(ctx, sql);
	
		

		// 更新变更单的客户String字段
		purchaseInfo.setCustomerNames(customerNames);
		purchaseInfo.setCustomerPhones(customerPhones);
		SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
		selectorItemCollection.add("customerNames");
		selectorItemCollection.add("customerPhones");
		PurchaseFactory.getLocalInstance(ctx).updatePartial(purchaseInfo,
				selectorItemCollection);
		// 添加到系统用户
		PurchaseCustomerInfoCollection purchaseCustomerInfoCollection = PurchaseCustomerInfoFactory
				.getLocalInstance(ctx).getPurchaseCustomerInfoCollection(
						"where head='" + purchaseInfo.getId().toString() + "'");
		for (int i = 0; i < purchaseCustomerInfoCollection.size(); i++) {
			PurchaseCustomerInfoInfo customer = purchaseCustomerInfoCollection
					.get(i);
			FDCCustomerFactory.getLocalInstance(ctx).addToSysCustomer(
					customer.getCustomer().getId().toString());
		}

	}
	//覆盖此方法 放开 让名称可以重复
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
			throws BOSException, EASBizException {
	
	}
}