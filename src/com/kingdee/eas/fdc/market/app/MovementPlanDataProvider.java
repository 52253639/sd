package com.kingdee.eas.fdc.market.app;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.ICrossPrintDataProvider;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.customerservice.InWorkerEntryCollection;
import com.kingdee.eas.fdc.customerservice.InWorkerEntryFactory;
import com.kingdee.eas.fdc.customerservice.InWorkerEntryInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class MovementPlanDataProvider implements ICrossPrintDataProvider{

	protected List ids = null;
	protected IMetaDataPK qpk = null;
	protected boolean ishasNext = true;
	private static String MOVEMENTPLAN = "movementPlanMain";
	private static String MOVEMENTPLANOTHER = "movementPlanOther";
	private static String MOVEMENTPLANOTHERQUERY = "com.kingdee.eas.fdc.market.app.MovementPlanEntryQuery";
	private static Logger logger = Logger
		.getLogger(MovementPlanDataProvider.class);
	
	public MovementPlanDataProvider(List id, IMetaDataPK qpk) {
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
		if (MOVEMENTPLAN.equals(dsId)) {
			iRowSet = getMainMovementPlanRowSet(ds);
		} else if (MOVEMENTPLANOTHER.equals(ds.getID())) {
			 //分录
			iRowSet = getMovementPlanOtherRowSet(ds);
		}
		return iRowSet;
	}
	
	public IRowSet getMainMovementPlanRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(qpk);
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", ids.get(0).toString(), CompareType.INCLUDE));
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

	public IRowSet getMovementPlanOtherRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory
					.getRemoteInstance(new MetaDataPK(MOVEMENTPLANOTHERQUERY));
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", ids.get(0).toString()));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			System.out.println("the sql is:"+exec.getSQL());
			iRowSet = exec.executeQuery();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return iRowSet;
	}

}
