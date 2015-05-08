/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractScheduleExpertPlanTreeRateUI extends com.kingdee.eas.fdc.schedule.report.client.ScheduleReportTreeBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractScheduleExpertPlanTreeRateUI.class);
    /**
     * output class constructor
     */
    public AbstractScheduleExpertPlanTreeRateUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractScheduleExpertPlanTreeRateUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSearch
        String _tempStr = null;
        actionSearch.setEnabled(true);
        actionSearch.setDaemonRun(false);

        _tempStr = resHelper.getString("actionSearch.SHORT_DESCRIPTION");
        actionSearch.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionSearch.LONG_DESCRIPTION");
        actionSearch.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionSearch.NAME");
        actionSearch.putValue(ItemAction.NAME, _tempStr);
         this.actionSearch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        // CoreUI		
        this.chartContainer.setTitle(resHelper.getString("chartContainer.title"));		
        this.tableContainer.setTitle(resHelper.getString("tableContainer.title"));		
        this.chartScrollPanel.setAutoscrolls(true);
		String chartTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"projectName\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"plannedComp\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"timedComp\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fifInComp\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fifOutComp\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"unComp\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"confirmComp\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"timedCompRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"delayedCompRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{projectName}</t:Cell><t:Cell>$Resource{plannedComp}</t:Cell><t:Cell>$Resource{timedComp}</t:Cell><t:Cell>$Resource{fifInComp}</t:Cell><t:Cell>$Resource{fifOutComp}</t:Cell><t:Cell>$Resource{unComp}</t:Cell><t:Cell>$Resource{confirmComp}</t:Cell><t:Cell>$Resource{timedCompRate}</t:Cell><t:Cell>$Resource{delayedCompRate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";


        

		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        allSplitPanel.setBounds(new Rectangle(0, 1, 1013, 629));
        this.add(allSplitPanel, new KDLayout.Constraints(0, 1, 1013, 629, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //allSplitPanel
        allSplitPanel.add(kDSplitPane2, "right");
        allSplitPanel.add(treeContainer, "left");
        //kDSplitPane2
        kDSplitPane2.add(chartContainer, "top");
        kDSplitPane2.add(tableContainer, "bottom");
        //chartContainer
        chartContainer.getContentPane().setLayout(new KDLayout());
        chartContainer.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 751, 499));        searchPanel.setBounds(new Rectangle(-1, 0, 754, 36));
        chartContainer.getContentPane().add(searchPanel, new KDLayout.Constraints(-1, 0, 754, 36, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        chartScrollPanel.setBounds(new Rectangle(1, 37, 750, 462));
        chartContainer.getContentPane().add(chartScrollPanel, new KDLayout.Constraints(1, 37, 750, 462, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //searchPanel
        searchPanel.setLayout(new KDLayout());
        searchPanel.putClientProperty("OriginalBounds", new Rectangle(-1, 0, 754, 36));        startDateLabel.setBounds(new Rectangle(7, 9, 100, 19));
        searchPanel.add(startDateLabel, new KDLayout.Constraints(7, 9, 100, 19, 0));
        endDateLabel.setBounds(new Rectangle(298, 9, 35, 19));
        searchPanel.add(endDateLabel, new KDLayout.Constraints(298, 9, 35, 19, 0));
        searchBtn.setBounds(new Rectangle(553, 9, 63, 19));
        searchPanel.add(searchBtn, new KDLayout.Constraints(553, 9, 63, 19, 0));
        startDatePicker.setBounds(new Rectangle(116, 9, 140, 19));
        searchPanel.add(startDatePicker, new KDLayout.Constraints(116, 9, 140, 19, 0));
        endDatePicker.setBounds(new Rectangle(360, 9, 144, 19));
        searchPanel.add(endDatePicker, new KDLayout.Constraints(360, 9, 144, 19, 0));
        //chartScrollPanel
        chartScrollPanel.getViewport().add(chartPanel, null);
        chartPanel.setLayout(new KDLayout());
        chartPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1, 1));        //tableContainer
tableContainer.getContentPane().setLayout(new BorderLayout(0, 0));        tableContainer.getContentPane().add(chartTable, BorderLayout.CENTER);
        //treeContainer
treeContainer.getContentPane().setLayout(new BorderLayout(0, 0));        treeContainer.getContentPane().add(treeViewer, BorderLayout.CENTER);
        //treeViewer
        treeViewer.setTree(orgTree);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(exportToExcel);
        this.toolBar.add(print);
        this.toolBar.add(prePrint);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.report.app.ScheduleExpertPlanTreeRateUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    	

    /**
     * output actionSearch_actionPerformed method
     */
    public void actionSearch_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSearch_actionPerformed(e);
    }
	public RequestContext prepareactionSearch(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareactionSearch(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionSearch() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.report.client", "ScheduleExpertPlanTreeRateUI");
    }




}