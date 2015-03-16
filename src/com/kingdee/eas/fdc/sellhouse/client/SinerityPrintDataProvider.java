package com.kingdee.eas.fdc.sellhouse.client;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;


public class SinerityPrintDataProvider extends FDCBillDataProvider {
	private static Logger log = Logger.getLogger(PaymentPrintDataProvider.class);

	private IMetaDataPK qpk = null;
	private String id = null;

	public SinerityPrintDataProvider(String id,IMetaDataPK qpk)
	{
		super(id,qpk);
		this.id = id;
		this.qpk = qpk;
	}

	public IRowSet execute(BOSQueryDataSource ds) {
		Variant paramVal = null;
		ArrayList ps = ds.getParams();
		IRowSet iRowSet = null;
		if (ps.size() > 0)
		{
			DSParam param = (DSParam)ps.get(0); 
			paramVal = param.getValue();	
		}    
		if ("SINCERITYPRINTQUERY".equals(ds.getID().toUpperCase()))//假设主数据源名称为mainbill
		{
			//返加主数据源的结果集
			iRowSet = getMainBillRowSet(ds);
		}
		return iRowSet;
	}

	//查询主元数据
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds){
		IRowSet iRowSet = null;
        try {
        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(qpk);
            exec.option().isAutoTranslateEnum= true;
            EntityViewInfo ev = new EntityViewInfo();
            FilterInfo filter = new FilterInfo();
            filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.EQUALS));
            ev.setFilter(filter);            
            exec.setObjectView(ev);
            iRowSet = exec.executeQuery();
            iRowSet.beforeFirst();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}       
		return iRowSet;
	}
}
