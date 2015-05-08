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
public abstract class AbstractScheduleReportTreeBaseUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractScheduleReportTreeBaseUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane allSplitPanel;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane2;
    protected com.kingdee.bos.ctrl.swing.KDContainer treeContainer;
    protected com.kingdee.bos.ctrl.swing.KDContainer chartContainer;
    protected com.kingdee.bos.ctrl.swing.KDContainer tableContainer;
    protected com.kingdee.bos.ctrl.swing.KDPanel searchPanel;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane chartScrollPanel;
    protected com.kingdee.bos.ctrl.swing.KDLabel startDateLabel;
    protected com.kingdee.bos.ctrl.swing.KDLabel endDateLabel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton searchBtn;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker startDatePicker;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker endDatePicker;
    protected com.kingdee.bos.ctrl.swing.KDPanel chartPanel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable chartTable;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeViewer;
    protected com.kingdee.bos.ctrl.swing.KDTree orgTree;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton exportToExcel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton print;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton prePrint;
    protected actionExportToExcel actionExportToExcel = null;
    protected actionPrint actionPrint = null;
    protected actionPrePrint actionPrePrint = null;
    protected actionSearch actionSearch = null;
    /**
     * output class constructor
     */
    public AbstractScheduleReportTreeBaseUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractScheduleReportTreeBaseUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionExportToExcel
        this.actionExportToExcel = new actionExportToExcel(this);
        getActionManager().registerAction("actionExportToExcel", actionExportToExcel);
         this.actionExportToExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrint
        this.actionPrint = new actionPrint(this);
        getActionManager().registerAction("actionPrint", actionPrint);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrePrint
        this.actionPrePrint = new actionPrePrint(this);
        getActionManager().registerAction("actionPrePrint", actionPrePrint);
         this.actionPrePrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSearch
        this.actionSearch = new actionSearch(this);
        getActionManager().registerAction("actionSearch", actionSearch);
         this.actionSearch.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.allSplitPanel = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDSplitPane2 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.treeContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.chartContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tableContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.searchPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.chartScrollPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.startDateLabel = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.endDateLabel = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.searchBtn = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.startDatePicker = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.endDatePicker = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.chartPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.chartTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.treeViewer = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.orgTree = new com.kingdee.bos.ctrl.swing.KDTree();
        this.exportToExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.print = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.prePrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.allSplitPanel.setName("allSplitPanel");
        this.kDSplitPane2.setName("kDSplitPane2");
        this.treeContainer.setName("treeContainer");
        this.chartContainer.setName("chartContainer");
        this.tableContainer.setName("tableContainer");
        this.searchPanel.setName("searchPanel");
        this.chartScrollPanel.setName("chartScrollPanel");
        this.startDateLabel.setName("startDateLabel");
        this.endDateLabel.setName("endDateLabel");
        this.searchBtn.setName("searchBtn");
        this.startDatePicker.setName("startDatePicker");
        this.endDatePicker.setName("endDatePicker");
        this.chartPanel.setName("chartPanel");
        this.chartTable.setName("chartTable");
        this.treeViewer.setName("treeViewer");
        this.orgTree.setName("orgTree");
        this.exportToExcel.setName("exportToExcel");
        this.print.setName("print");
        this.prePrint.setName("prePrint");
        // CoreUI
        // allSplitPanel		
        this.allSplitPanel.setDividerLocation(250);
        // kDSplitPane2		
        this.kDSplitPane2.setOrientation(0);		
        this.kDSplitPane2.setDividerLocation(500);
        // treeContainer		
        this.treeContainer.setTitle(resHelper.getString("treeContainer.title"));
        // chartContainer		
        this.chartContainer.setTitle(resHelper.getString("chartContainer.title"));
        // tableContainer		
        this.tableContainer.setTitle(resHelper.getString("tableContainer.title"));
        // searchPanel
        // chartScrollPanel
        // startDateLabel		
        this.startDateLabel.setText(resHelper.getString("startDateLabel.text"));
        // endDateLabel		
        this.endDateLabel.setText(resHelper.getString("endDateLabel.text"));
        // searchBtn
        this.searchBtn.setAction((IItemAction)ActionProxyFactory.getProxy(actionSearch, new Class[] { IItemAction.class }, getServiceContext()));		
        this.searchBtn.setText(resHelper.getString("searchBtn.text"));
        // startDatePicker
        // endDatePicker
        // chartPanel		
        this.chartPanel.setAutoscrolls(true);
        // chartTable
		String chartTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"projectName\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"plannedComp\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"timedComp\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fifInComp\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fifOutComp\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"unComp\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"confirmComp\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"timedCompRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"delayedCompRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{projectName}</t:Cell><t:Cell>$Resource{plannedComp}</t:Cell><t:Cell>$Resource{timedComp}</t:Cell><t:Cell>$Resource{fifInComp}</t:Cell><t:Cell>$Resource{fifOutComp}</t:Cell><t:Cell>$Resource{unComp}</t:Cell><t:Cell>$Resource{confirmComp}</t:Cell><t:Cell>$Resource{timedCompRate}</t:Cell><t:Cell>$Resource{delayedCompRate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.chartTable.setFormatXml(resHelper.translateString("chartTable",chartTableStrXML));
        this.chartTable.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    chartTable_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // treeViewer
        // orgTree
        this.orgTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    orgTree_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // exportToExcel
        this.exportToExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportToExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.exportToExcel.setText(resHelper.getString("exportToExcel.text"));
        // print
        this.print.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.print.setText(resHelper.getString("print.text"));
        // prePrint
        this.prePrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrePrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.prePrint.setText(resHelper.getString("prePrint.text"));
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
        chartContainer.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 751, 499));        searchPanel.setBounds(new Rectangle(0, 0, 752, 44));
        chartContainer.getContentPane().add(searchPanel, new KDLayout.Constraints(0, 0, 752, 44, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        chartScrollPanel.setBounds(new Rectangle(0, 45, 753, 436));
        chartContainer.getContentPane().add(chartScrollPanel, new KDLayout.Constraints(0, 45, 753, 436, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //searchPanel
        searchPanel.setLayout(new KDLayout());
        searchPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 752, 44));        startDateLabel.setBounds(new Rectangle(7, 9, 100, 19));
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
	    return "com.kingdee.eas.fdc.schedule.report.app.ScheduleReportTreeBaseUIHandler";
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
	 * ????????��??
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
     * output chartTable_tableClicked method
     */
    protected void chartTable_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output orgTree_valueChanged method
     */
    protected void orgTree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    	

    /**
     * output actionExportToExcel_actionPerformed method
     */
    public void actionExportToExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrePrint_actionPerformed method
     */
    public void actionPrePrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSearch_actionPerformed method
     */
    public void actionSearch_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareactionExportToExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionExportToExcel() {
    	return false;
    }
	public RequestContext prepareactionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionPrint() {
    	return false;
    }
	public RequestContext prepareactionPrePrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionPrePrint() {
    	return false;
    }
	public RequestContext prepareactionSearch(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionSearch() {
    	return false;
    }

    /**
     * output actionExportToExcel class
     */     
    protected class actionExportToExcel extends ItemAction {     
    
        public actionExportToExcel()
        {
            this(null);
        }

        public actionExportToExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionExportToExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionExportToExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionExportToExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleReportTreeBaseUI.this, "actionExportToExcel", "actionExportToExcel_actionPerformed", e);
        }
    }

    /**
     * output actionPrint class
     */     
    protected class actionPrint extends ItemAction {     
    
        public actionPrint()
        {
            this(null);
        }

        public actionPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleReportTreeBaseUI.this, "actionPrint", "actionPrint_actionPerformed", e);
        }
    }

    /**
     * output actionPrePrint class
     */     
    protected class actionPrePrint extends ItemAction {     
    
        public actionPrePrint()
        {
            this(null);
        }

        public actionPrePrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionPrePrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionPrePrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionPrePrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleReportTreeBaseUI.this, "actionPrePrint", "actionPrePrint_actionPerformed", e);
        }
    }

    /**
     * output actionSearch class
     */     
    protected class actionSearch extends ItemAction {     
    
        public actionSearch()
        {
            this(null);
        }

        public actionSearch(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionSearch.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSearch.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSearch.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleReportTreeBaseUI.this, "actionSearch", "actionSearch_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.report.client", "ScheduleReportTreeBaseUI");
    }




}