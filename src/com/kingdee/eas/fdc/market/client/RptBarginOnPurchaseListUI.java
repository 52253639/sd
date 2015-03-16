/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseEditUI;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class RptBarginOnPurchaseListUI extends AbstractRptBarginOnPurchaseListUI
{
    private static final Logger logger = CoreUIObject.getLogger(RptBarginOnPurchaseListUI.class);
    
    /**
     * output class constructor
     */
    public RptBarginOnPurchaseListUI() throws Exception
    {
        super();
    }

	protected ICoreBase getBizInterface() throws Exception {
		return PurchaseFactory.getRemoteInstance();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
	protected String getEditUIName() {
		return PurchaseEditUI.class.getName();
	}
	
	protected String getKeyFieldName() {
		return "head.id";
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		//this.actionQuery.setVisible(false);
		
	}
	
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		
		FilterInfo filter = (FilterInfo)this.getUIContext().get("PurchaseFilter");
		if(filter!=null) {
			viewInfo = (EntityViewInfo)this.mainQuery.clone();
			try{
				if (viewInfo.getFilter() != null) {
					viewInfo.getFilter().mergeFilter(filter, "and");
				} else {
					viewInfo.setFilter(filter);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	
	

}