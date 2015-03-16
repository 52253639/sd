package com.kingdee.eas.fdc.sellhouse.client;

import java.sql.SQLException;

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

public class PropertyNoticePrintDataProvider implements ICrossPrintDataProvider {

	private static Logger logger = Logger
			.getLogger(PropertyNoticePrintDataProvider.class);
	protected boolean ishasNext = true;
	private final static String MAIN = "PropertyNoticePrintQuery";
	protected String ids = null;
	protected IMetaDataPK qpk = null;

	public PropertyNoticePrintDataProvider(String id, IMetaDataPK qpk) {
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
		String dsId = ds.getID();
		IRowSet iRowSet = null;
		// 主数据源
		if (MAIN.equals(dsId)) {
			iRowSet = getMainPropertyNoticeRowSet(ds);
		}
		return iRowSet;
	}

	private IRowSet getMainPropertyNoticeRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(qpk);
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", ids, CompareType.INCLUDE));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			iRowSet = exec.executeQuery();
			iRowSet.beforeFirst();
		} catch (BOSException e) {
			logger.error(e.getMessage() + "获得主数据失败！");
		} catch (SQLException e) {
			logger.error(e.getMessage() + "获得主数据失败！");
		} catch (Exception e) {
			logger.error(e.getMessage() + "获得主数据失败！");
		}
		return iRowSet;
	}

}
