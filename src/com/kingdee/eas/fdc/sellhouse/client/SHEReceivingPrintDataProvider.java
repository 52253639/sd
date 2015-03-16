package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBill;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryCollection;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEComHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

public class SHEReceivingPrintDataProvider extends FDCBillDataProvider{
	private static Logger log = Logger
	.getLogger(PurchaseBookPrintDataProvider.class);
	private IMetaDataPK qpk = null;
	private String id = null;

	public SHEReceivingPrintDataProvider(String id, IMetaDataPK qpk) {
		super(id, qpk);
		this.id = id;
		this.qpk = qpk;
	}

	public IRowSet execute(BOSQueryDataSource ds) {
		Variant paramVal = null;
		ArrayList ps = ds.getParams();
		IRowSet iRowSet = null;
		if (ps.size() > 0) {
			DSParam param = (DSParam) ps.get(0);
			paramVal = param.getValue();
		}
		if ("MAINBILL".equals(ds.getID().toUpperCase()))// 假设主数据源名称为mainbill
		{
			// 返加主数据源的结果集
			iRowSet = getMainBillRowSet(ds);
		} else if ("BILLENTRY".equals(ds.getID().toUpperCase())) {
			return getOtherSubRowSet(ds);
		} 		
		return iRowSet;
	}

	// 查询主元数据
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(qpk);
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.INCLUDE));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			/*
			 * 修改了主数据查询时得到主客户名称和证件号码以及缴费客户 xiaoa_liu
			 */

			iRowSet = exec.executeQuery();
			String fcdcustomername = null;
			String fcdcustomercertificateNumber = null;

			String[] sb = getCustomer(id).split(";");
			fcdcustomername = sb[0];
			fcdcustomercertificateNumber = sb[1];

			if (iRowSet.next()) {

				String certificateNumber = iRowSet.getString("purchaseObj.customerIDCards");
				String customerNames = iRowSet.getString("purchaseObj.customerNames");

				String[] idArr = null;
				String[] nameArr = null;
				if (certificateNumber != null && certificateNumber.length() > 0) {
					idArr = certificateNumber.split(";");
				}
				if (customerNames != null && customerNames.length() > 0) {
					nameArr = customerNames.split(";");
				}
				String customerIDCard = null;
				String customerName = null;
				if (idArr != null && idArr.length > 0) {
					customerIDCard = String.valueOf(idArr[0]);
				}
				if (nameArr != null && nameArr.length > 0) {
					customerName = String.valueOf(nameArr[0]);
				}

				if (fcdcustomername != null && fcdcustomername.length() > 0) {
					iRowSet.updateString("fdccustomer.name", fcdcustomername);
				}
				if (fcdcustomercertificateNumber != null && fcdcustomercertificateNumber.length() > 0) {
					iRowSet.updateString("fdccustomer.IDCard", fcdcustomercertificateNumber);
				}
				if (customerIDCard != null && customerIDCard.length() > 0) {
					iRowSet.updateString("customerIDCard", customerIDCard);
				}
				if (customerName != null && customerName.length() > 0) {
					iRowSet.updateString("mainCustomer", customerName);
				}
			}

			iRowSet.beforeFirst();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return iRowSet;
	}
	
	private String getCustomer(String id){
		
		String sb  = new String();
		
		try {
			IFDCReceivingBill bill = FDCReceivingBillFactory.getRemoteInstance();

			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("id", id, CompareType.EQUALS));
			evi.setFilter(filterInfo);

			SelectorItemCollection coll = new SelectorItemCollection();
			coll.add(new SelectorItemInfo("id"));
			coll.add(new SelectorItemInfo("fdcCustomers.fdcCustomer.id"));
			coll.add(new SelectorItemInfo("fdcCustomers.fdcCustomer.name"));
			coll.add(new SelectorItemInfo("fdcCustomers.fdcCustomer.certificateNumber"));
		
			evi.setSelector(coll);

			FDCReceivingBillCollection collection = bill.getFDCReceivingBillCollection(evi);

			if (collection != null && collection.size() > 0) {
				for (int i = 0; i < collection.size(); i++) {
					FDCReceivingBillInfo info = collection.get(i);
					if(info!=null){
						RevFDCCustomerEntryCollection entry = info.getFdcCustomers();
						for (int j = 0; j < entry.size(); j++) {
							RevFDCCustomerEntryInfo entryInfo = entry.get(j);
							if(entryInfo!=null){
								FDCCustomerInfo customerInfo= entryInfo.getFdcCustomer();
								if(customerInfo!=null){
									sb =  customerInfo.getName();
									sb = sb + ";";
									sb = sb + customerInfo.getCertificateNumber();
								}
							}
						}
					}
				}
			} 
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return sb;
	}

	// 查询收款单分录数据
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
		IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.SHEReceivingBillEntryTDQuery"));
		exec.option().isAutoTranslateEnum = true;
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", id, CompareType.EQUALS));
		ev.setFilter(filter);
		exec.setObjectView(ev);
		iRowSet = exec.executeQuery();
		while (iRowSet.next()) {
			String revListId = iRowSet.getString("revListId");
			BizEnumValueInfo ob= (BizEnumValueInfo)iRowSet.getObject("revListType");
			String stra = (String)ob.getValue();
			RevListTypeEnum revListType = RevListTypeEnum.getEnum(stra);
			ICoreBase iface = SHEComHelper.getRevListBizInterface(null, revListType);
			IRevListInfo revList = (IRevListInfo) iface.getValue(new ObjectUuidPK(revListId));
			BigDecimal appAmount = revList.getAppAmount();
			BigDecimal actRevAmount = revList.getActRevAmount();
			BigDecimal hasTransferredAmount = revList.getHasTransferredAmount();
			BigDecimal hasRefundmentAmount = revList.getHasRefundmentAmount();
			BigDecimal hasAdjustedAmount = revList.getHasAdjustedAmount();
			BigDecimal revAmount = actRevAmount.subtract(hasTransferredAmount).subtract(hasRefundmentAmount).subtract(hasAdjustedAmount);
			iRowSet.updateBigDecimal("appAmount", appAmount);
			iRowSet.updateBigDecimal("actRevAmount", revAmount);
			iRowSet.updateBigDecimal("hasTransferredAmount", hasTransferredAmount);
			iRowSet.updateBigDecimal("hasRefundmentAmount", hasRefundmentAmount);
			iRowSet.updateBigDecimal("hasAdjustedAmount", hasAdjustedAmount);
		}
		iRowSet.beforeFirst();
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return iRowSet;
	}
}
