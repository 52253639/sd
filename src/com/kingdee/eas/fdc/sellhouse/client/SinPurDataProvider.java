package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;

public class SinPurDataProvider extends FDCBillDataProvider {
	private static final Logger logger = Logger.getLogger(SinPurDataProvider.class);
	
	//更名分录 数据源于数据标签
	private static String ChangeRecordEntryPrintQuery = "com.kingdee.eas.fdc.sellhouse.app.ChangeRecordEntryTwoPrintQuery";
	private static String ChangeRecordEntryDataTitle = "ChangeRecordEntryTwoPrintQuery";
	
	//款项分录 数据源于数据标签
	private static String SinReceivePrintQuery = "com.kingdee.eas.fdc.sellhouse.app.SincerReceiveEntryPrintQuery";
	private static String SinReceiveEntryDataTitle = "SincerReceiveEntryPrintQuery";
	
	//客户分录 数据源于数据标签
	private static String SinPurCusEntryPrintQuery = "com.kingdee.eas.fdc.sellhouse.app.SinPurCustomerPrintEntryQuery";
	private static String SinPurCusEntryDataTitle = "SinPurCustomerPrintEntryQuery";
	
	//置业顾问分录 数据源于数据标签
	private static String SinPurSaleMansEntryPrintQuery = "com.kingdee.eas.fdc.sellhouse.app.SinPurSaleMansEntryPrintQuery";
	private static String SinPurSaleMansEntryDataTitle = "SinPurSaleMansEntryPrintQuery";
	public SinPurDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
		// TODO Auto-generated constructor stub
	}
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		if (ds.getID().equalsIgnoreCase(ChangeRecordEntryDataTitle)) {
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK(ChangeRecordEntryPrintQuery));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("head.id", billId));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
		} else if (ds.getID().equalsIgnoreCase(SinReceiveEntryDataTitle)) {
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK(SinReceivePrintQuery));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("head.id", billId));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}
			

			return iRowSet;
		}
		 else if (ds.getID().equalsIgnoreCase(SinPurCusEntryDataTitle)) {
				IRowSet iRowSet = null;
				try {
					IQueryExecutor exec = QueryExecutorFactory
							.getRemoteInstance(new MetaDataPK(SinPurCusEntryPrintQuery));
					exec.option().isAutoTranslateEnum = true;
					EntityViewInfo ev = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("head.id", billId));
					ev.setFilter(filter);
					exec.setObjectView(ev);
					iRowSet = exec.executeQuery();
					iRowSet.beforeFirst();
				} catch (Exception e) {
					logger.debug(e.getMessage(), e);
					e.printStackTrace();
				}
				

				return iRowSet;
			}
		
		 else if (ds.getID().equalsIgnoreCase(SinPurSaleMansEntryDataTitle)) {
				IRowSet iRowSet = null;
				try {
					IQueryExecutor exec = QueryExecutorFactory
							.getRemoteInstance(new MetaDataPK(SinPurSaleMansEntryPrintQuery));
					exec.option().isAutoTranslateEnum = true;
					EntityViewInfo ev = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("sinPur.id", billId));
					ev.setFilter(filter);
					exec.setObjectView(ev);
					iRowSet = exec.executeQuery();
					iRowSet.beforeFirst();
				} catch (Exception e) {
					logger.debug(e.getMessage(), e);
					e.printStackTrace();
				}
				

				return iRowSet;
			}
		return getMainBillRowSet(ds);
	}
}
