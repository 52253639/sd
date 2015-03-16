package com.kingdee.eas.fdc.sellhouse.client;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;

public class DefaultAmountMangerPrintDataProvider extends FDCBillDataProvider  {
	private static Logger logger = Logger
	.getLogger(DefaultAmountMangerPrintDataProvider.class);
	
	protected boolean ishasNext = true;
	private final static String MAIN = "DefaultAmountMangerQuery";
	private final static String ENTRY = "DefaultAmountMangerEntryQuery";
	private final static String ENTRYQUERY = "com.kingdee.eas.fdc.sellhouse.app.DefaultAmountMangerEntryQuery";
	protected IMetaDataPK qpk = null;
	protected String ids = null;
	
	
	public  DefaultAmountMangerPrintDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
		this.ids = billId;
		this.qpk = mainQuery;
	}
	public boolean hasNext() {
		return true;
	}
	public IRowSet execute(BOSQueryDataSource ds) {
	    return super.execute(ds);
	}
	private IRowSet getDefaultAmountMangerPrintRowSet(BOSQueryDataSource ds) {
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

	private IRowSet getDefaultAmountMangerPrintEntryRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory
					.getRemoteInstance(new MetaDataPK(ENTRYQUERY));
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
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		String dsId = ds.getID();
		IRowSet iRowSet = null;
		if (MAIN.equals(dsId)) {
			iRowSet = getDefaultAmountMangerPrintRowSet(ds);
			return iRowSet;
		} else if (ENTRY.equals(ds.getID())) {
			iRowSet = getDefaultAmountMangerPrintEntryRowSet(ds);
			return iRowSet;
		}
		return getMainBillRowSet(ds);
	}
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds){
		return getDefaultAmountMangerPrintRowSet(ds);
	}

}
