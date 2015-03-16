package com.kingdee.eas.fdc.invite.client;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.invite.InviteFormInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class InviteMonthPlanPrintDataProvider extends FDCBillDataProvider{
	private HashMap map = null;
	
	public InviteMonthPlanPrintDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
		// TODO Auto-generated constructor stub
	}
	
	public InviteMonthPlanPrintDataProvider(String billId,IMetaDataPK mainQuery,HashMap map)
	{
		super(billId, mainQuery);
		this.map = map;
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
            while(iRowSet.next()){
            	String longNumber = iRowSet.getString("ENTRY.LONGNUMBER");
            	HashMap<String,Object> actMap = (HashMap<String, Object>) map.get(longNumber);
            	if(actMap != null) {
            		iRowSet.updateString(InviteMonthPlanEditUI.ACTINVITEFORM, (String) actMap.get(InviteMonthPlanEditUI.ACTINVITEFORM));
            		iRowSet.updateString(InviteMonthPlanEditUI.ACTPURCHASEMODE, (String) actMap.get(InviteMonthPlanEditUI.ACTPURCHASEMODE));
            		iRowSet.updateDate(InviteMonthPlanEditUI.ACTCONTRACTAUDITDATE, (Date) actMap.get(InviteMonthPlanEditUI.ACTCONTRACTAUDITDATE));
            		iRowSet.updateDate(InviteMonthPlanEditUI.ACTDOCUMENTSAUDITDATE, (Date) actMap.get(InviteMonthPlanEditUI.ACTDOCUMENTSAUDITDATE));
            		iRowSet.updateDate(InviteMonthPlanEditUI.ACTRESULTAUDITDATE, (Date) actMap.get(InviteMonthPlanEditUI.ACTRESULTAUDITDATE));
            	}
            	
			}
            iRowSet.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iRowSet;
	}
}
