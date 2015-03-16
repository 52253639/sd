package com.kingdee.eas.fdc.invite.client;

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
public class InviteDocumentsDataProvider  extends FDCBillDataProvider{
	private IMetaDataPK queryDataPK = null ;
	public InviteDocumentsDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
        try {
        	if(queryDataPK == null){
        		queryDataPK = MetaDataPK.create("com.kingdee.eas.fdc.invite.app.InviteDocumentsEntryQuery") ;
            }
        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(queryDataPK);
            exec.option().isAutoTranslateEnum= true;
            EntityViewInfo ev = new EntityViewInfo();
            FilterInfo filter = new FilterInfo();
            filter.getFilterItems().add(new FilterItemInfo("InviteDocuments.id", billId));
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
