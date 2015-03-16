package com.kingdee.eas.fdc.sellhouse.client;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.ICrossPrintDataProvider;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.jdbc.rowset.IRowSet;

public class CommerceChanceTrackDataProvider implements ICrossPrintDataProvider{


	private static Logger logger = Logger.getLogger(CommerceChanceTrackDataProvider.class);
	
	protected boolean ishasNext = true;
	protected List ids = null;
	protected IMetaDataPK qpk = null;
	
	public CommerceChanceTrackDataProvider(List id, IMetaDataPK qpk) {
		this.ids = id;
		this.qpk = qpk;
	}

	public boolean hasNext() {
		if (ishasNext) {
			ishasNext = false;
			return true;
		} else {
			return false;
		}
	}

	public IRowSet execute(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(qpk);
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", ids.get(0).toString(), CompareType.INCLUDE));
			ev.setFilter(filter);            
			exec.setObjectView(ev);
			iRowSet = exec.executeQuery();
			iRowSet.beforeFirst();
		} catch (BOSException e) {
			logger.error(e.getMessage()+"获得主数据失败！");	
		} catch (SQLException e) {
			logger.error(e.getMessage()+"获得主数据失败！");
		} catch(Exception e){
			logger.error(e.getMessage()+"获得主数据失败！");
		}
		return iRowSet;
		
		
	}
}

