package com.kingdee.eas.fdc.sellhouse.client;

import java.sql.SQLException;

import org.apache.log4j.Logger;

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
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;

public class CommerceChanceDataProvider extends FDCBillDataProvider {
	

	private static Logger logger = Logger
			.getLogger(CommerceChanceDataProvider.class);
	protected boolean ishasNext = true;
	private final static String MAIN = "CommerceChanceForPrintQuery";
	private final static String ROOMENTRY = "CommerceChanceRoomEntryQuery";
	private final static String TRACKENTRY = "CommerceChanceTrackEntryQuery";
	private final static String ROOMENTRYQUERY = "com.kingdee.eas.fdc.sellhouse.app.CommerceChanceRoomEntryQuery";
	private final static String TRACKENTRYQUERY = "com.kingdee.eas.fdc.sellhouse.app.CommerceChanceTrackEntryQuery";
	protected String ids = null;
	protected IMetaDataPK qpk = null;

	public CommerceChanceDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
		this.ids = billId;
		this.qpk = mainQuery;
	}



	public IRowSet execute(BOSQueryDataSource ds) {
		return super.execute(ds);

	}

	private IRowSet getMainCommerceChanceRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(qpk);
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", ids,
							CompareType.INCLUDE));
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

	private IRowSet getRoomEntryRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory
					.getRemoteInstance(new MetaDataPK(ROOMENTRYQUERY));
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("head.id", ids,
							CompareType.EQUALS));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			iRowSet = exec.executeQuery();
			iRowSet.beforeFirst();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return iRowSet;
	}

	private IRowSet getTranckEntryRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory
					.getRemoteInstance(new MetaDataPK(TRACKENTRYQUERY));
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("commerceChance.id", ids,
							CompareType.EQUALS));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			iRowSet = exec.executeQuery();
			iRowSet.beforeFirst();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return iRowSet;
	}
	
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		String dsId = ds.getID();
		IRowSet iRowSet = null;
		// 主数据源
		if (MAIN.equals(dsId)) {
			iRowSet = getMainCommerceChanceRowSet(ds);
		} else if (ROOMENTRY.equals(ds.getID())) {
			// 房间分录信息
			iRowSet = getRoomEntryRowSet(ds);
			return iRowSet;
		} else if (TRACKENTRY.equals(ds.getID())) {
			// 跟进分录信息
			iRowSet = getTranckEntryRowSet(ds);
			return iRowSet;
		}
		
		return getMainBillRowSet(ds);
	}
	
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds){
		return getMainCommerceChanceRowSet(ds);
	}


}
