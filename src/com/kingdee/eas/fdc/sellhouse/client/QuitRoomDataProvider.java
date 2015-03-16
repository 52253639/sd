package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.jdbc.rowset.IRowSet;

public class QuitRoomDataProvider  implements BOSQueryDelegate{
	private IMetaDataPK qpk = null;
	private String id = null;
	private HashMap map = null;
	
	public QuitRoomDataProvider(String id,IMetaDataPK qpk)
	{
		this.id = id;
		this.qpk = qpk;
	}
	
	public QuitRoomDataProvider(String id,IMetaDataPK qpk,HashMap map)
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
			iRowSet = exec.executeQuery();
			if (iRowSet.next()) {
				String canRefundmentAmountStr=getCanRefundmentAmount(id);
				iRowSet.updateString("canRefundmentAmountStr",canRefundmentAmountStr);
			}
			iRowSet.beforeFirst();
		} catch (Exception e) {
			ExceptionHandler.handle((CoreUI) null,e);
		}
		return iRowSet;
	}   
	
	private String getCanRefundmentAmount(String id){
		QuitPayListEntryCollection col = null;
		try {
			col = QuitPayListEntryFactory.getRemoteInstance().getQuitPayListEntryCollection("select canRefundmentAmount" +
					" where head.id='" +id+ "'");
		} catch (BOSException e) {
			ExceptionHandler.handle((CoreUI) null,e);
		} 
		BigDecimal allRemain = FDCHelper.ZERO;
		if(col!=null){
			for(int i=0; i<col.size(); i++){
				QuitPayListEntryInfo info=(QuitPayListEntryInfo)col.get(i);
				BigDecimal canRefundmentAmount=(BigDecimal)info.get("canRefundmentAmount");
				allRemain = FDCHelper.add(allRemain, canRefundmentAmount);
			}
		}
		return allRemain.toString();
	}
}
