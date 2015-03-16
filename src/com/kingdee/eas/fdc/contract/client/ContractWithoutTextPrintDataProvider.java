package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractWithoutTextPrintDataProvider extends FDCBillDataProvider {
	String agentPayCompany="";
	public ContractWithoutTextPrintDataProvider(String billId,IMetaDataPK mainQuery) {
		super(billId,mainQuery);
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select distinct company.fname_l2 name from T_CAS_PaymentBill pay left join T_ORG_Company company on pay.FAgentPayCompanyID=company.fid ");
		_builder.appendSql(" where pay.fbillstatus=15 and company.fid is not null and pay.fcontractBillId='"+billId+"'");
		try {
			IRowSet rowSet = _builder.executeQuery();
			if(rowSet.size()==1){
				while (rowSet.next()) {
					agentPayCompany=rowSet.getString("name");
				}
			}else if(rowSet.size()>1){
				while (rowSet.next()) {
					agentPayCompany=rowSet.getString("name")+","+agentPayCompany;
				}
				agentPayCompany=agentPayCompany.substring(0,agentPayCompany.lastIndexOf(","));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds){
		if (ds.getID().equalsIgnoreCase("BGENTRY")) {
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractWithoutTextBgEntryPrintQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("head.id", billId));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return iRowSet;
		}else if (ds.getID().equalsIgnoreCase("AttachmentQuery")) {
			// 合同履约保证金及返还部分
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.basedata.app.AttachmentQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("boAttchAsso.boID",billId));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return iRowSet;
		}
		return getMainBillRowSet(ds);
	}
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds){
		IRowSet iRowSet = null;
        try {
        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(mainQuery);
            exec.option().isAutoTranslateEnum= true;
            EntityViewInfo ev = new EntityViewInfo();
            FilterInfo filter = new FilterInfo();
            filter.getFilterItems().add(new FilterItemInfo("id", billId, CompareType.EQUALS));
            ev.setFilter(filter);            
            exec.setObjectView(ev);
            iRowSet = exec.executeQuery();	
            if(iRowSet.next()){
            	iRowSet.updateString("agentCompany.name", agentPayCompany);
			}
            iRowSet.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iRowSet;
	}
}
