/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class SettleMentRsvBillListUI extends AbstractSettleMentRsvBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SettleMentRsvBillListUI.class);
    

    public SettleMentRsvBillListUI() throws Exception
    {
        super();
    }


	protected ICoreBase getBizInterface() throws Exception {
		return ReceivingBillFactory.getRemoteInstance();
	}


	protected String getEditUIName() {
		return ReceiveBillEidtUI.class.getName();
	}


	protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}
	
	
	public void onLoad() throws Exception {	
		super.onLoad();
		this.toolBar.setVisible(false);
			
		this.tblMain.getGroupManager().setGroup(true);
		for(int i=0;i<9;i++)
			this.tblMain.getColumn(i).setGroup(true);
		this.tblMain.getGroupManager().group();
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,	EntityViewInfo viewInfo) {
		Set rsvIdSet = (Set)this.getUIContext().get("RsvIdSet");
		if(rsvIdSet==null) 
			rsvIdSet = new HashSet();
		rsvIdSet.add("idnull");
		
		viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",rsvIdSet,CompareType.INCLUDE));
		viewInfo.setFilter(filter);
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	
	protected void execQuery() {
		super.execQuery();
		

		
		
	}
	
	
	
	
	

}