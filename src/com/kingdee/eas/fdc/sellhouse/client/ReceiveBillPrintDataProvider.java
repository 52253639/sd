package com.kingdee.eas.fdc.sellhouse.client;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.jdbc.rowset.IRowSet;

public class ReceiveBillPrintDataProvider implements BOSQueryDelegate{
	
	private IMetaDataPK qpk = null;
	private String id = null;
	
	public ReceiveBillPrintDataProvider(String id,IMetaDataPK qpk)
	{
		this.id = id;
		this.qpk = qpk;
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
		}catch (Exception e) {
			ExceptionHandler.handle((CoreUI) null,e);
		}
		return iRowSet;
	}
}
