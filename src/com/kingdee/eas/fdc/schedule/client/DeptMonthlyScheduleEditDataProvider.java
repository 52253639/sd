/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.sql.ResultSetMetaData;
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

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 描述：
 * 
 * @author 杜红明
 * @version EAS7.0
 * @createDate 2011-9-6
 * @see
 */

public class DeptMonthlyScheduleEditDataProvider extends FDCBillDataProvider {
	private String bailId = ""; // 部门月度计划的ID

	public DeptMonthlyScheduleEditDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}

	public void setBailId(String bailId) {
		this.bailId = bailId;
	}

	public static void insertRows(IRowSet iRowSet, ResultSetMetaData metas, IRowSet copy, int insertRowCount) {
		try {
			for (int i = 0; i < insertRowCount; i++) {
				iRowSet.moveToInsertRow();
				for (int j = 1; j <= metas.getColumnCount(); j++) {
					iRowSet.updateObject(metas.getColumnName(j), copy.getObject(metas.getColumnName(j)));
				}
				iRowSet.insertRow();
			}

		} catch (SQLException e) {
			logger.debug(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		if (ds.getID().equalsIgnoreCase("DeptMonthlyScheduleQuery")) {

			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK(
						"com.kingdee.eas.fdc.schedule.app.DeptMonthlyScheduleQuery"));
				exec.option().isAutoTranslateEnum = true;

				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", billId));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

		}
		return iRowSet;
	}

	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(billId);
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", billId.toString(), CompareType.INCLUDE));
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

	static Class _mthclass$(String x0) throws Throwable {
		try {
			return Class.forName(x0);
		} catch (ClassNotFoundException x1) {
			throw (new NoClassDefFoundError()).initCause(x1);
		}
	}

	private static final Logger logger;

	static {
		logger = Logger.getLogger(com.kingdee.eas.fdc.contract.client.ContractBillEditDataProvider.class);
	}
}
