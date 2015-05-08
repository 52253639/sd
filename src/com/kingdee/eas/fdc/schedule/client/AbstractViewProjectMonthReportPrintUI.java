/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

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
public abstract class AbstractViewProjectMonthReportPrintUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractViewProjectMonthReportPrintUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelImage;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDthisMonth;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDNextMonth;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtThisEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtNextEntrys;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportExcel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPrint;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPrintPreview;
    protected com.kingdee.eas.fdc.schedule.ProjectMonthReportInfo entityProjectMonthReport = null;
    protected ActionExportExcel actionExportExcel = null;
    protected ActionPrint ActionPrint = null;
    protected ActionPrintPreview actionPrintPreview = null;
    protected ActionCancel actionCancel = null;
    /**
     * output class constructor
     */
    public AbstractViewProjectMonthReportPrintUI() throws Exception
    {
        super();
        this.defaultObjectName = "entityProjectMonthReport";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractViewProjectMonthReportPrintUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionExportExcel
        this.actionExportExcel = new ActionExportExcel(this);
        getActionManager().registerAction("actionExportExcel", actionExportExcel);
         this.actionExportExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //ActionPrint
        this.ActionPrint = new ActionPrint(this);
        getActionManager().registerAction("ActionPrint", ActionPrint);
         this.ActionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrintPreview
        this.actionPrintPreview = new ActionPrintPreview(this);
        getActionManager().registerAction("actionPrintPreview", actionPrintPreview);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancel
        this.actionCancel = new ActionCancel(this);
        getActionManager().registerAction("actionCancel", actionCancel);
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDPanelImage = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDSplitPane2 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDthisMonth = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDNextMonth = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtThisEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtNextEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnExportExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPrintPreview = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDPanelImage.setName("kDPanelImage");
        this.kDSplitPane2.setName("kDSplitPane2");
        this.kDthisMonth.setName("kDthisMonth");
        this.kDNextMonth.setName("kDNextMonth");
        this.kdtThisEntry.setName("kdtThisEntry");
        this.kdtNextEntrys.setName("kdtNextEntrys");
        this.btnExportExcel.setName("btnExportExcel");
        this.btnPrint.setName("btnPrint");
        this.btnPrintPreview.setName("btnPrintPreview");
        // CoreUI
        // kDSplitPane1		
        this.kDSplitPane1.setOrientation(0);		
        this.kDSplitPane1.setDividerLocation(250);
        // kDPanelImage
        // kDSplitPane2		
        this.kDSplitPane2.setOrientation(0);		
        this.kDSplitPane2.setDividerLocation(250);
        // kDthisMonth		
        this.kDthisMonth.setTitle(resHelper.getString("kDthisMonth.title"));		
        this.kDthisMonth.setEnabled(false);		
        this.kDthisMonth.setEnableActive(false);
        // kDNextMonth		
        this.kDNextMonth.setTitle(resHelper.getString("kDNextMonth.title"));		
        this.kDNextMonth.setEnabled(false);		
        this.kDNextMonth.setEnableActive(false);
        // kdtThisEntry
		String kdtThisEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"state\" t:width=\"40\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"taskName\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"completePercent\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" t:styleID=\"sCol2\" /><t:Column t:key=\"planEndDate\" t:width=\"140\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"realEndDate\" t:width=\"140\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"intendEndDate\" t:width=\"140\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"respPerson\" t:width=\"140\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" /><t:Column t:key=\"respDept\" t:width=\"140\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" /><t:Column t:key=\"entryID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:configured=\"false\" t:styleID=\"sCol8\" /><t:Column t:key=\"description\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">状态</t:Cell><t:Cell t:configured=\"false\">任务名称</t:Cell><t:Cell t:configured=\"false\">当前进度(%)</t:Cell><t:Cell t:configured=\"false\">计划完成日期</t:Cell><t:Cell t:configured=\"false\">实际完成日期</t:Cell><t:Cell t:configured=\"false\">预计完成日期</t:Cell><t:Cell t:configured=\"false\">责任人</t:Cell><t:Cell t:configured=\"false\">责任部门</t:Cell><t:Cell t:configured=\"false\">分录ID</t:Cell><t:Cell t:configured=\"false\">完成情况说明</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtThisEntry.setFormatXml(resHelper.translateString("kdtThisEntry",kdtThisEntryStrXML));
        this.kdtThisEntry.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtThisEntry_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kdtNextEntrys
		String kdtNextEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"taskName\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"planStartDate\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol1\" /><t:Column t:key=\"planEndDate\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol2\" /><t:Column t:key=\"respPerson\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"respDept\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"entryID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">任务名称</t:Cell><t:Cell t:configured=\"false\">计划开始日期</t:Cell><t:Cell t:configured=\"false\">计划完成日期</t:Cell><t:Cell t:configured=\"false\">责任人</t:Cell><t:Cell t:configured=\"false\">责任部门</t:Cell><t:Cell t:configured=\"false\">分录ID</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtNextEntrys.setFormatXml(resHelper.translateString("kdtNextEntrys",kdtNextEntrysStrXML));
        this.kdtNextEntrys.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtNextEntrys_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnExportExcel
        this.btnExportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportExcel.setText(resHelper.getString("btnExportExcel.text"));
        // btnPrint
        this.btnPrint.setAction((IItemAction)ActionProxyFactory.getProxy(ActionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrint.setText(resHelper.getString("btnPrint.text"));
        // btnPrintPreview
        this.btnPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrintPreview.setText(resHelper.getString("btnPrintPreview.text"));
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
        kDSplitPane1.setBounds(new Rectangle(10, 10, 993, 593));
        this.add(kDSplitPane1, new KDLayout.Constraints(10, 10, 993, 593, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(kDPanelImage, "top");
        kDSplitPane1.add(kDSplitPane2, "bottom");
kDPanelImage.setLayout(new BorderLayout(0, 0));        //kDSplitPane2
        kDSplitPane2.add(kDthisMonth, "top");
        kDSplitPane2.add(kDNextMonth, "bottom");
        //kDthisMonth
kDthisMonth.getContentPane().setLayout(new BorderLayout(0, 0));        kDthisMonth.getContentPane().add(kdtThisEntry, BorderLayout.CENTER);
        //kDNextMonth
kDNextMonth.getContentPane().setLayout(new BorderLayout(0, 0));        kDNextMonth.getContentPane().add(kdtNextEntrys, BorderLayout.CENTER);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
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
        this.toolBar.add(btnExportExcel);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryInfo.class, this.kdtNextEntrys, "userObject");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.ViewProjectMonthReportPrintUIHandler";
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
        this.entityProjectMonthReport = (com.kingdee.eas.fdc.schedule.ProjectMonthReportInfo)ov;
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
	 * ????????У??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output kdtThisEntry_tableClicked method
     */
    protected void kdtThisEntry_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kdtNextEntrys_tableClicked method
     */
    protected void kdtNextEntrys_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
        return sic;
    }        
    	

    /**
     * output actionExportExcel_actionPerformed method
     */
    public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancel_actionPerformed method
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionExportExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportExcel() {
    	return false;
    }
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }
	public RequestContext prepareActionCancel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancel() {
    	return false;
    }

    /**
     * output ActionExportExcel class
     */     
    protected class ActionExportExcel extends ItemAction {     
    
        public ActionExportExcel()
        {
            this(null);
        }

        public ActionExportExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionExportExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractViewProjectMonthReportPrintUI.this, "ActionExportExcel", "actionExportExcel_actionPerformed", e);
        }
    }

    /**
     * output ActionPrint class
     */     
    protected class ActionPrint extends ItemAction {     
    
        public ActionPrint()
        {
            this(null);
        }

        public ActionPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractViewProjectMonthReportPrintUI.this, "ActionPrint", "actionPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionPrintPreview class
     */     
    protected class ActionPrintPreview extends ItemAction {     
    
        public ActionPrintPreview()
        {
            this(null);
        }

        public ActionPrintPreview(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintPreview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractViewProjectMonthReportPrintUI.this, "ActionPrintPreview", "actionPrintPreview_actionPerformed", e);
        }
    }

    /**
     * output ActionCancel class
     */     
    protected class ActionCancel extends ItemAction {     
    
        public ActionCancel()
        {
            this(null);
        }

        public ActionCancel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCancel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractViewProjectMonthReportPrintUI.this, "ActionCancel", "actionCancel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "ViewProjectMonthReportPrintUI");
    }




}