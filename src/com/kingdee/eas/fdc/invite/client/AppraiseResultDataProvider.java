package com.kingdee.eas.fdc.invite.client;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.jdbc.rowset.IRowSet;

public class AppraiseResultDataProvider extends InvitePrintDataProvider {
	private IMetaDataPK attchQuery;

	public AppraiseResultDataProvider(String billId, IMetaDataPK mainQuery,IMetaDataPK attchQuery) {
		super(billId, mainQuery);
		this.attchQuery=attchQuery;
	}

	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds){
		if ("ATTCH".equals(ds.getID().toUpperCase()))//假设主数据源名称为mainbill
        {
            //返加主数据源的结果集
        	return  getAttchRowSet(ds);
        }
		return super.getMainBillRowSet(ds);
	}
	
	public IRowSet getAttchRowSet(BOSQueryDataSource ds){
		IRowSet iRowSet = null;
        try {
        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(attchQuery);
            exec.option().isAutoTranslateEnum= true;
            EntityViewInfo ev = new EntityViewInfo();
            FilterInfo filter = new FilterInfo();
            filter.getFilterItems().add(new FilterItemInfo("boAttchAsso.boID", billId, CompareType.EQUALS));
            ev.setFilter(filter);
            exec.setObjectView(ev);
            iRowSet = exec.executeQuery();	
            iRowSet.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iRowSet;
	}
}
