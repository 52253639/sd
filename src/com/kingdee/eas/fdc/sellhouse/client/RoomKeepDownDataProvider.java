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

public class RoomKeepDownDataProvider extends FDCBillDataProvider {
	private static final Logger logger = Logger.getLogger(RoomKeepDownDataProvider.class);
	
	
	
	public RoomKeepDownDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
		// TODO Auto-generated constructor stub
	}
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
	 if (ds.getID().equalsIgnoreCase("CUSENT")) {
				IRowSet iRowSet = null;
				try {
					IQueryExecutor exec = QueryExecutorFactory
							.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.RoomKeepDownCusEntryQuery"));
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
		return getMainBillRowSet(ds);
	}
}
