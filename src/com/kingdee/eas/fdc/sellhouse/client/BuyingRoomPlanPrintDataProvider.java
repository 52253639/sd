package com.kingdee.eas.fdc.sellhouse.client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.jdbc.rowset.IRowSet;

public class BuyingRoomPlanPrintDataProvider implements BOSQueryDelegate{

	private static Logger logger = Logger.getLogger(BuyingRoomPlanPrintDataProvider.class);
	private IMetaDataPK qpk = null;
	private HashMap map = null;
	
	private final String BUYINGROOMPLANQUERY = "BuyingRoomPlanQuery";
	
	private final String BUYINGROOMPLANENTRYQUERY = "BuyingRoomPlanEntryQuery";
	
	public BuyingRoomPlanPrintDataProvider(IMetaDataPK qpk,HashMap map){
		this.qpk = qpk;
		this.map = map;
	}

	public IRowSet execute(BOSQueryDataSource ds) {
		String dsId = ds.getID();
		IRowSet iRowSet = null;
		if (BUYINGROOMPLANQUERY.equals(dsId)) {
			iRowSet = getBuyingRoomPlanRowSet(ds);
		} else if (BUYINGROOMPLANENTRYQUERY.equals(dsId)) {
			iRowSet = getBuyingRoomEntryPlanRowSet(ds);
		} 
		return iRowSet;
	}
		
	protected IRowSet getBuyingRoomPlanRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {            
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(qpk);
			exec.option().isAutoTranslateEnum= true;
			iRowSet = exec.executeQuery();
			if (iRowSet.next()) {
				if (map.get("payType").toString() != null) {
					iRowSet.updateString("payType", map.get("payType").toString());
				}
				if (map.get("agioDes").toString() != null) {
					iRowSet.updateString("agioDes", map.get("agioDes").toString());
				}
				if (map.get("totalAmount").toString() != null) {
					iRowSet.updateString("totalAmount", map.get("totalAmount").toString());
				}
				if (map.get("agio").toString() != null) {
					iRowSet.updateString("agio", map.get("agio").toString());
				}
				if (map.get("loanAmount").toString() != null) {
					iRowSet.updateString("loanAmount", map.get("loanAmount").toString());
				}
				if (map.get("aFundAmount").toString() != null) {
					iRowSet.updateString("aFundAmount", map.get("aFundAmount").toString());
				}
			}
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
	
	
	
	protected IRowSet getBuyingRoomEntryPlanRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {            
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.BuyingRoomPlanEntryQuery"));
			exec.option().isAutoTranslateEnum= true;
			iRowSet = exec.executeQuery();
			ArrayList list = (ArrayList)map.get("payList");
			if(list != null && list.size()>0){
				for(int i=0;i<list.size();i++){
					ArrayList payList = (ArrayList)list.get(i);
					if(payList != null && payList.size()>0){
						if(iRowSet.next()){
							iRowSet.updateString("moneyName",(String)payList.get(0));
							iRowSet.updateString("recDate",(String)payList.get(1));
							iRowSet.updateString("currency",(String)payList.get(2));
							iRowSet.updateString("amount",(String)payList.get(3));
						}
					}
				}
			}
			iRowSet.beforeFirst();
		}  catch (Exception e) {
			logger.error(e.getMessage());
		}	
		return iRowSet;
	}
	
}
