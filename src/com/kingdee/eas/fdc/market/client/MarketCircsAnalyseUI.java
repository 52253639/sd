/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.market.MarketCircsAnalyseFacadeFactory;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;
import com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class MarketCircsAnalyseUI extends AbstractMarketCircsAnalyseUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketCircsAnalyseUI.class);
    
    /**
     * output class constructor
     */
    public MarketCircsAnalyseUI() throws Exception
    {
        super();
    }
    
    
    protected BireportBaseFilterUI getQueryDialogUserPanel() throws Exception {
    	return new MarketCircsAnalyseFilterUI();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
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

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionFilt_actionPerformed
     */
    public void actionFilt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFilt_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionDisplayConfig_actionPerformed
     */
    public void actionDisplayConfig_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDisplayConfig_actionPerformed(e);
    }

    /**
     * output actionSwapAxes_actionPerformed
     */
    public void actionSwapAxes_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSwapAxes_actionPerformed(e);
    }

    /**
     * output actionShowChart_actionPerformed
     */
    public void actionShowChart_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionShowChart_actionPerformed(e);
    }

    /**
     * output actionShowSlice_actionPerformed
     */
    public void actionShowSlice_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionShowSlice_actionPerformed(e);
    }

    /**
     * output actionCustomChart_actionPerformed
     */
    public void actionCustomChart_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCustomChart_actionPerformed(e);
    }

    /**
     * output actionShowSortRank_actionPerformed
     */
    public void actionShowSortRank_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionShowSortRank_actionPerformed(e);
    }

    /**
     * output actionChartAnalysis_actionPerformed
     */
    public void actionChartAnalysis_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionChartAnalysis_actionPerformed(e);
    }

    /**
     * output actionSizerData_actionPerformed
     */
    public void actionSizerData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSizerData_actionPerformed(e);
    }

    /**
     * output actionShowCustomStyle_actionPerformed
     */
    public void actionShowCustomStyle_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionShowCustomStyle_actionPerformed(e);
    }

	protected RptParams getParamsForInit() {
		// TODO Auto-generated method stub
		return null;
	}

	protected RptParams getParamsForRequest() {
		// TODO Auto-generated method stub
		return this.params;
	}

	protected IBireportBaseFacade getRemoteInstance() throws BOSException {
		return MarketCircsAnalyseFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return this.kDTable1;
	}

	protected void onAfterQuery() throws Exception {
		// TODO Auto-generated method stub
		
	}

	protected void onBeforeQuery() throws Exception {
		// TODO Auto-generated method stub
		
	}

	protected void preparePrintPageHeader(HeadFootModel header) {
		// TODO Auto-generated method stub
		
	}

	protected Map preparePrintVariantMap() {
		// TODO Auto-generated method stub
		return null;
	}

}