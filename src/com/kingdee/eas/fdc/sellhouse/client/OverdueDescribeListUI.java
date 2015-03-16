/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.sellhouse.OverdueDescribeFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class OverdueDescribeListUI extends AbstractOverdueDescribeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(OverdueDescribeListUI.class);
    
    /**
     * output class constructor
     */
    public OverdueDescribeListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
		super.onLoad();
		if (( this.getUIContext().get("tranID") != null) && ( this.getUIContext().get("tranID") != "")) {
    		String tranID =   (String) this.getUIContext().get("tranID");
    		EntityViewInfo view = new EntityViewInfo();
    		FilterInfo  filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("transOviewId",tranID));
    		view.setFilter(filter);
    		mainQuery=view;
		}
		
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.menuBiz.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionWorkflowList.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionQuery.setVisible(false);
	}
    protected String getEditUIName() {
    	return OverdueDescribeEditUI.class.getName();
    }
    protected ICoreBase getBizInterface() throws Exception {
    	return OverdueDescribeFactory.getRemoteInstance();
    }
    
}