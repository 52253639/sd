/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.util.HashSet;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;

/**
 * output class name
 */
public class TENReceivingBillListPartUI extends TENReceivingBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(TENReceivingBillListPartUI.class);
    private HashSet list=null;
    /**
     * output class constructor
     */
    public TENReceivingBillListPartUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	
    	super.onLoad();
    	this.treeView.setVisible(false);
    	this.actionAddNew.setVisible(false);
    	this.actionView.setVisible(false);
    	this.actionRemove.setVisible(false);
    	this.actionEdit.setVisible(false);
    	this.actionRefresh.setVisible(false);
    	this.actionQuery.setVisible(false);
    	this.actionLocate.setVisible(false);
    	this.actionAudit.setVisible(false);
    	this.actionReceive.setVisible(false);
    	this.actionVoucher.setVisible(false);
    	this.actionBatchReceiving.setVisible(false);
    	this.menuEdit.setVisible(false);
    	this.menuBiz.setVisible(false);
    	this.menuView.setVisible(false);
    	this.actionTraceDown.setVisible(false);
		this.actionDelVoucher.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionCanceReceive.setVisible(false);
//		this.actionTDPrintPreview.setVisible(false);
//		this.actionTDPrint.setVisible(false);
		this.actionUpdateSubject.setVisible(false);
    }
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	this.refresh(null);
    }
    protected void execQuery() {
    	super.execQuery();
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
    		EntityViewInfo viewInfo) {
    	
    	IQueryExecutor ex = super.getQueryExecutor(queryPK, viewInfo);
    	FilterInfo filter=new FilterInfo();
    	if(getUIContext().get("keyID")!=null){  //当批量收款结束时 会传递此参数
			list=(HashSet)getUIContext().get("keyID");
		}
    	if(list!=null){
    		filter.getFilterItems().add(new FilterItemInfo("id",list,CompareType.INCLUDE));
    	}
    	EntityViewInfo view = (EntityViewInfo) mainQuery.clone();
    	view.setFilter(filter);
    	ex.setObjectView(view);
    	return ex;
    }
//    protected void setColGroups() {
//    	// TODO Auto-generated method stub
//    	super.setColGroups();
//    }
//   

}