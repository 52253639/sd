package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
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

import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.ChangeNameNewCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeNameNewCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeNameOldCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.CluesSourceEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoInfo;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;

public class ChangeRecordEntryTwoControllerBean extends AbstractChangeRecordEntryTwoControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.ChangeRecordEntryTwoControllerBean");

	protected boolean _changeRecordSave(Context ctx, IObjectValue sinPurInfo, String appoinmentPeople, String appoimentPhone, List sheCusCol, IObjectValue model) throws BOSException, EASBizException {
		// TODO Auto-generated method stub

		SincerityPurchaseInfo sincPurInfo  =(SincerityPurchaseInfo)sinPurInfo;
		
		sincPurInfo.getCustomer().clear();
		for(int i=0;i<sheCusCol.size();i++){
			SincerityPurchaseCustomerEntryInfo entry=new SincerityPurchaseCustomerEntryInfo();
			SHEManageHelper.setCustomerListEntry(entry, ((TranCustomerEntryInfo)sheCusCol.get(i)));
			sincPurInfo.getCustomer().add(entry);
		}
		
		ChangeRecordEntryTwoInfo changeRecord = (ChangeRecordEntryTwoInfo)model;
		//得到预约排号单客户信息
		EntityViewInfo view = new EntityViewInfo ();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head",sincPurInfo.getId().toString()));
		view.setFilter(filter);
//		SincerityPurchaseCustomerEntryCollection sincerityPurchaseCustomerEntryCol = SincerityPurchaseCustomerEntryFactory.getLocalInstance(ctx).getSincerityPurchaseCustomerEntryCollection(view);
		changeRecord.setHead(sincPurInfo);
		ChangeNameOldCustomerEntryInfo colOldInfo = null;
		ChangeNameNewCustomerEntryInfo colNewInfo = null;
//		String newCusStr = "";
		//当旧客户为空,填入的预约人信息生成新的线索客户，客户信息成为新客户
		
		for(int i = 0 ; i< sincPurInfo.getCustomer().size() ; i++){
			colOldInfo = new ChangeNameOldCustomerEntryInfo();
			colOldInfo.setCustomer(sincPurInfo.getCustomer().get(i).getCustomer());
			//旧客户
			changeRecord.getOldCustomer().add(colOldInfo);
		}
		//得到旧客户字段
		//这个字段在前面已经处理过了，所以用另一个
//		changeRecord.setOldCusStr(sincPurInfo.getCustomerNames());
		changeRecord.setOldCusStr(sincPurInfo.getCusStr());
		//得到旧线索客户
		if(null!=sincPurInfo.getCluesCus()&&appoinmentPeople.length()<1&&appoinmentPeople.length()<1){
			changeRecord.setCluesCus(sincPurInfo.getCluesCus());
		}
		
		
		for(int i=0;i<sincPurInfo.getCustomer().size();i++){
			colNewInfo = new ChangeNameNewCustomerEntryInfo();
			colNewInfo.setCustomer(sincPurInfo.getCustomer().get(i).getCustomer());
			changeRecord.getNewCustomer().add(colNewInfo);
		}
		
		changeRecord.setNewCusStr(sincPurInfo.getCustomerNames());
		
		//预约单更新新客户字段
		sincPurInfo.setCusStr(sincPurInfo.getCustomerNames());
		
		if(appoinmentPeople.length()>=1&&appoimentPhone.length()>=1){
				//生成新线索客户
				if(null!=appoinmentPeople&&(!("").equals(appoinmentPeople)&&null!=appoimentPhone&&(!("").equals(appoimentPhone)))){//都不为null
					CluesManageInfo newCluesMa = new CluesManageInfo();
					newCluesMa.setName(appoinmentPeople);
					ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
					.getLocalInstance(ctx);
					OrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
					boolean existCodingRule = iCodingRuleManager.isExist(
							new FDCOrgCustomerInfo(), orgUnit.getId().toString());
					if (existCodingRule) {
						String retNumber = iCodingRuleManager.getNumber(	newCluesMa, orgUnit
								.getId().toString());
						newCluesMa.setNumber(retNumber);
					} else {
						Timestamp time = new Timestamp(new Date().getTime());
						String  timeStr = String.valueOf(time).replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").replaceAll(".", "");
						String number = String.valueOf(timeStr)+"01";
						newCluesMa.setNumber(number);
					}
//					newCluesMa.setNumber(appoinmentPeople+appoimentPhone);
					newCluesMa.setPhone(appoimentPhone);
					newCluesMa.setSource(CluesSourceEnum.VISIT);
					
					newCluesMa.setSellProject(sincPurInfo.getCluesCus()!=null?
							sincPurInfo.getCluesCus().getSellProject():sincPurInfo.getSellProject());
					newCluesMa.setPropertyConsultant(sincPurInfo.getSalesman());
					sincPurInfo.setAppointmentPeople(appoinmentPeople);
					sincPurInfo.setAppointmentPhone(appoimentPhone);
					IObjectPK newCluId = CluesManageFactory.getLocalInstance(ctx).submit(newCluesMa);
					newCluesMa.setId((BOSUuid)newCluId.getKeyValue(null));
					sincPurInfo.setCluesCus(newCluesMa);
				}
		}	
//		SelectorItemCollection sel = new SelectorItemCollection ();
////		sel.add("customer.customer.*");
//		sel.add("customer.*");
//		sel.add("cluesCus.*");
//		sel.add("appointmentPhone");
//		sel.add("appointmentPeople");
//		sel.add("customerPhone");
//		sel.add("customerNames");
//		sel.add("cusStr");
		//更新预约单
		SincerityPurchaseFactory.getLocalInstance(ctx).update(new ObjectUuidPK(sincPurInfo.getId()), sincPurInfo);
	
		IObjectPK changeRecordId =this._submit(ctx,changeRecord);
		
		changeRecord.setId(BOSUuid.read(changeRecordId.getKeyValue(null).toString()));
		
//		//找到后删除之前客户信息
//		for(int s = 0 ; s< sincerityPurchaseCustomerEntryCol.size() ; s++){
//			SincerityPurchaseCustomerEntryFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(sincerityPurchaseCustomerEntryCol.get(s).getId().toString()));
//		}
	
		return true;
	}

	
}