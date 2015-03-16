/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.sellhouse.RptRoomFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;
import com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class RptRoomMainUI extends AbstractRptRoomMainUI
{
    private static final Logger logger = CoreUIObject.getLogger(RptRoomMainUI.class);
    
    /**
     * output class constructor
     */
    public RptRoomMainUI() throws Exception
    {
        super();
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
    	handlePermissionForItemAction(actionExport);
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
    	handlePermissionForItemAction(actionExportSelected);
        super.actionExportSelected_actionPerformed(e);
    }

	protected IBireportBaseFacade getRemoteInstance() throws BOSException
	{
		// TODO 自动生成方法存根
		return RptRoomFacadeFactory.getRemoteInstance();
	}

	protected RptParams getParamsForInit()
	{
		// TODO 自动生成方法存根
		return null;
	}

	protected RptParams getParamsForRequest()
	{
		// TODO 自动生成方法存根
		return this.params;
	}

	protected void onBeforeQuery() throws Exception
	{
		// TODO 自动生成方法存根
		
	}

	protected void onAfterQuery() throws Exception
	{
		// TODO 自动生成方法存根
		
	}

	protected KDTable getTableForPrintSetting()
	{
		// TODO 自动生成方法存根
		return this.kDTable1;
	}

	protected void preparePrintPageHeader(HeadFootModel header)
	{
		// TODO 自动生成方法存根
		
	}

	protected Map preparePrintVariantMap()
	{
		// TODO 自动生成方法存根
		return null;
	}
	
	protected BireportBaseFilterUI getQueryDialogUserPanel() throws Exception
	{
		// TODO 自动生成方法存根
		return new RptRoomFilterUI();
	}

}