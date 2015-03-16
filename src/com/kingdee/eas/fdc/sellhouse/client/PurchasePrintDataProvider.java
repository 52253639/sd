package com.kingdee.eas.fdc.sellhouse.client;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.CertificateInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.jdbc.rowset.IRowSet;

public class PurchasePrintDataProvider  implements BOSQueryDelegate{
	private static Logger log = Logger.getLogger(PurchasePrintDataProvider.class);
	private IMetaDataPK qpk = null;
	private String id = null;
	private HashMap map = null;
	
	public PurchasePrintDataProvider(String id,IMetaDataPK qpk)
	{
		this.id = id;
		this.qpk = qpk;
	}
	
	public PurchasePrintDataProvider(String id,IMetaDataPK qpk,HashMap map)
	{
		this.id = id;
		this.qpk = qpk;
		this.map = map;
	}
	
	public IRowSet execute(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {            
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(qpk);
			exec.option().isAutoTranslateEnum= true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.INCLUDE));
			ev.setFilter(filter);            
			exec.setObjectView(ev);
			//System.out.println(exec.getSQL());
			iRowSet = exec.executeQuery();
			
			RoomInfo room = (RoomInfo)map.get("room");
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("*");
			sel.add("building.*");
			RoomInfo roomInfo = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(room.getId().toString())),sel);
			String buildName = roomInfo.getBuilding().getName();
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("sellProject.*");
			sels.add("subarea.*");
			BuildingInfo buildInfo = BuildingFactory.getRemoteInstance().getBuildingInfo(new ObjectUuidPK(BOSUuid.read(roomInfo.getBuilding().getId().toString())),sels);
			String projectName = buildInfo.getSellProject().getName();
			//String subareaName = buildInfo.getSubarea().getName();
			String proAdress = buildInfo.getSellProject().getProAddress();
			ArrayList customerList = (ArrayList)map.get("customerList");
			FDCCustomerInfo customer = (FDCCustomerInfo)customerList.get(0);
			String name1 = customer.getName();
			//证件名称
			//CertifacateNameEnum certificateName = customer.getCertificateName();
			//update by renliang
			CertificateInfo certificateName = customer.getCertificateName();
			//证件号码
			String certificateNumber = customer.getCertificateNumber();
			String adress = customer.getMailAddress();
			String phone = customer.getPhone();
			String postalcode = customer.getPostalcode();
			String sex =null;
			if( customer.getSex()!=null){
				sex = customer.getSex().toString();
			}
			if(iRowSet.next())
			{
				iRowSet.updateString("proAdress",proAdress);
				iRowSet.updateString("purchase.upAmount","aaa");
				iRowSet.updateString("sellProject",projectName);
				//iRowSet.updateString("subarea",subareaName);
				iRowSet.updateString("building",buildName);
				iRowSet.updateString("customer.phone",phone);
				iRowSet.updateString("customer.mailAddress",adress);
				iRowSet.updateString("customer.postalcode",postalcode);
				if(sex!=null){
					iRowSet.updateString("customer.sex",sex);
				}
				if(certificateName!=null && certificateName.getName()!=null){
					iRowSet.updateString("customer.certificateName",certificateName.getName().toString());
				}
				iRowSet.updateString("customer.certificateNumber",certificateNumber);
				if(customerList.size()>1){
					String name2 = ((FDCCustomerInfo)customerList.get(1)).getName();
					iRowSet.updateString("customer.name1",name1);
					iRowSet.updateString("customer.name2",name2);
				}else
				{
					iRowSet.updateString("customer.name1",name1);
				}
			}
			iRowSet.beforeFirst();
		} catch (Exception e) {
			log.error(e.getMessage());
			ExceptionHandler.handle((CoreUI) null,e);
		}
		return iRowSet;
	}   
	
}
