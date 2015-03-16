package com.kingdee.eas.fdc.basecrm.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.jdbc.rowset.IRowSet;

public class CRMDataProvider implements BOSQueryDelegate {
    private String idStr = null;
    private IMetaDataPK qpk = null;				//默认主表的查询
    private IMetaDataPK entryQpk = null;		//默认明细表的查询
    private String parentIdName = "parent.id";	//分录里与主表的关联字段名,  (query中一定要包含此字段，否则查询不到)
    
    /**
     * @param idStr  主表数据id
     * @param qpkString 默认主表的查询 ，不可给空值
     * @param entryQpkString 默认明细表的查询  ，不可给空值
     * @param parentIdName 分录里与主表的关联字段名,  (query中一定要包含此字段，否则查询不到), 可为空
     * */
    public CRMDataProvider(String idStr,String qpkString,String entryQpkString,String parentIdName) {
        this.idStr = idStr;
        if(qpkString!=null)        
        	this.qpk = new MetaDataPK(qpkString);
        if(entryQpkString!=null)
        	this.entryQpk = new MetaDataPK(entryQpkString);
        if(parentIdName!=null)
        	this.parentIdName = parentIdName;
    }

    public IRowSet execute(BOSQueryDataSource ds) {
        IRowSet iRowSet = null;
        try {    
        	IQueryExecutor exec = null;
            EntityViewInfo ev = new EntityViewInfo();
            FilterInfo filter = new FilterInfo();
            if(qpk.getName().equals(ds.getID())){
        		exec = QueryExecutorFactory.getRemoteInstance(qpk);
        		filter.getFilterItems().add(new FilterItemInfo("id", idStr));
            }else if(entryQpk.getName().equals(ds.getID())) {
        		exec = QueryExecutorFactory.getRemoteInstance(entryQpk);
        		filter.getFilterItems().add(new FilterItemInfo(parentIdName, idStr));
            }else{
            	String queryName = ds.getID().toLowerCase();
            	if(queryName.indexOf("entry")>=0) {	//针对明细的查询
            		exec = QueryExecutorFactory.getRemoteInstance(entryQpk.getPackage()+"."+ds.getID());
            		filter.getFilterItems().add(new FilterItemInfo(parentIdName, idStr));
            	}else{								//针对表头数据的查询
            		exec = QueryExecutorFactory.getRemoteInstance(qpk.getPackage()+"."+ds.getID());
            		filter.getFilterItems().add(new FilterItemInfo("id", idStr));
            	}            	
            }
            

            exec.option().isAutoTranslateEnum= true;
            ev.setFilter(filter);            
            exec.setObjectView(ev);
            iRowSet = exec.executeQuery();
            iRowSet.beforeFirst();
        } catch (Exception e) {
            ExceptionHandler.handle((CoreUI) null,e);
        }
        return iRowSet;
    }   

}