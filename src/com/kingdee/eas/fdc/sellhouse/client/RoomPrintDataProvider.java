package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;

import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.BizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

public class RoomPrintDataProvider implements BOSQueryDelegate {
	private String id ;
	private IMetaDataPK qpk;
	Map map ;
	
//	public RoomPrintDataProvider(List idList,IMetaDataPK qpk)
//	{
//	super(idList,qpk);
//	}
	
	public RoomPrintDataProvider(String id,IMetaDataPK qpk,Map map)
	{
		this.id = id;
		this.qpk = qpk;
		this.map = map;
		
	}
	
	public IRowSet execute(BOSQueryDataSource ds) {
		//IRowSet iRowSet = super.execute(ds);
		IRowSet iRowSet = null;
		DynamicRowSet drs = null;
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
			
			
			BigDecimal areaComAmount = (BigDecimal)map.get("areaComAmount");
			if(areaComAmount==null)
			{
				areaComAmount = FDCHelper.ZERO;
			}
			BigDecimal attachAmount = (BigDecimal)map.get("attachAmount");
			if(attachAmount==null)
			{
				attachAmount = FDCHelper.ZERO;
			}
			BigDecimal fitmentAmount = (BigDecimal)map.get("fitmentAmount");
			if(fitmentAmount==null)
			{
				fitmentAmount = FDCHelper.ZERO;
			}
			BigDecimal contractAmount = (BigDecimal)map.get("contractAmount");
			if(contractAmount ==null)
			{
				contractAmount = FDCHelper.ZERO;
			}
			iRowSet.beforeFirst();
			//DynamicRowSet drs = null;
			if(iRowSet.next()){
				iRowSet.updateBigDecimal("areaComAmount",areaComAmount);
				iRowSet.updateBigDecimal("attachAmount",attachAmount);
				iRowSet.updateBigDecimal("fitmentAmount",fitmentAmount);
				iRowSet.updateBigDecimal("contractAmount",contractAmount);
				
//				drs =  new DynamicRowSet(9);
//				drs.moveToInsertRow();
//				drs.beforeFirst();
//				drs.updateBigDecimal("areaComAmount",areaComAmount);
//				drs.updateBigDecimal("areaComAmount",areaComAmount);
//				drs.updateBigDecimal("areaComAmount",areaComAmount);
//				drs.updateBigDecimal("areaComAmount",areaComAmount);
//				drs.insertRow();
			}
			
			iRowSet.beforeFirst();
			String[] str = new String[]{"payment.date","payment.name","payment.amount","payment.currency"};
			drs = new DynamicRowSet(str.length);
			for (int i = 0; i < str.length; i++) {
				ColInfo ci = new ColInfo();
				ci.colType = Types.VARCHAR;
				ci.columnName = str[i];
				ci.nullable = 1;
				drs.setColInfo(i + 1, ci);
			}
			drs.beforeFirst();
			for(int j=0;j<5;j++){
		    drs.moveToInsertRow();
			drs.updateString("payment.date","aaaa");
			drs.updateString("payment.name","bbbb");
			drs.updateString("payment.amount","cccc");
			drs.updateString("payment.currency","dddd");
			drs.insertRow();
			}
			drs.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return drs;
	}   	
	
}
