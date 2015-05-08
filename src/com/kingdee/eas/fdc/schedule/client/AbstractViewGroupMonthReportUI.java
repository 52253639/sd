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
public abstract class AbstractViewGroupMonthReportUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractViewGroupMonthReportUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane sptTop;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane sptDown;
    protected com.kingdee.bos.ctrl.swing.KDPanel cbPanel;
    protected com.kingdee.bos.ctrl.swing.KDContainer contThis;
    protected com.kingdee.bos.ctrl.swing.KDContainer contNext;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable thisTable;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable nextTable;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlChart;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbMonth;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbYear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPrint;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPrintPreview;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportExcel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRefresh;
    protected com.kingdee.bos.ctrl.swing.KDMenu kDMenu1;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPrint;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPrintPreview;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menueItemExportExcel;
    protected ActionPrint actionPrint = null;
    protected ActionPrintpreview actionPrintpreview = null;
    protected ActionExportExcel actionExportExcel = null;
    protected ActionRefresh actionRefresh = null;
    /**
     * output class constructor
     */
    public AbstractViewGroupMonthReportUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractViewGroupMonthReportUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionPrint
        this.actionPrint = new ActionPrint(this);
        getActionManager().registerAction("actionPrint", actionPrint);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrintpreview
        this.actionPrintpreview = new ActionPrintpreview(this);
        getActionManager().registerAction("actionPrintpreview", actionPrintpreview);
         this.actionPrintpreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportExcel
        this.actionExportExcel = new ActionExportExcel(this);
        getActionManager().registerAction("actionExportExcel", actionExportExcel);
         this.actionExportExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRefresh
        this.actionRefresh = new ActionRefresh(this);
        getActionManager().registerAction("actionRefresh", actionRefresh);
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.sptTop = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.sptDown = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.cbPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contThis = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contNext = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.thisTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.nextTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.pnlChart = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.cbMonth = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cbYear = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPrintPreview = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExportExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRefresh = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDMenu1 = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemPrint = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPrintPreview = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menueItemExportExcel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.sptTop.setName("sptTop");
        this.sptDown.setName("sptDown");
        this.cbPanel.setName("cbPanel");
        this.contThis.setName("contThis");
        this.contNext.setName("contNext");
        this.thisTable.setName("thisTable");
        this.nextTable.setName("nextTable");
        this.pnlChart.setName("pnlChart");
        this.kDPanel1.setName("kDPanel1");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.cbMonth.setName("cbMonth");
        this.cbYear.setName("cbYear");
        this.contProject.setName("contProject");
        this.prmtProject.setName("prmtProject");
        this.btnPrint.setName("btnPrint");
        this.btnPrintPreview.setName("btnPrintPreview");
        this.btnExportExcel.setName("btnExportExcel");
        this.btnRefresh.setName("btnRefresh");
        this.kDMenu1.setName("kDMenu1");
        this.menuItemPrint.setName("menuItemPrint");
        this.menuItemPrintPreview.setName("menuItemPrintPreview");
        this.kDSeparator2.setName("kDSeparator2");
        this.menueItemExportExcel.setName("menueItemExportExcel");
        // CoreUI
        // sptTop		
        this.sptTop.setOrientation(0);		
        this.sptTop.setDividerLocation(290);
        // sptDown		
        this.sptDown.setOrientation(0);		
        this.sptDown.setDividerLocation(151);
        // cbPanel
        // contThis		
        this.contThis.setTitle(resHelper.getString("contThis.title"));		
        this.contThis.setEnableActive(false);
        // contNext		
        this.contNext.setTitle(resHelper.getString("contNext.title"));		
        this.contNext.setEnableActive(false);
        // thisTable
		String thisTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:NumberFormat>####.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"state\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" /><t:Column t:key=\"company\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"taskName\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" /><t:Column t:key=\"completePercent\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"planEndDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"checkDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" t:styleID=\"sCol6\" /><t:Column t:key=\"realEndDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" t:styleID=\"sCol7\" /><t:Column t:key=\"intendEndDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:configured=\"false\" t:styleID=\"sCol8\" /><t:Column t:key=\"respPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:configured=\"false\" /><t:Column t:key=\"respDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:configured=\"false\" /><t:Column t:key=\"description\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:configured=\"false\" /><t:Column t:key=\"isComplete\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:configured=\"false\" t:styleID=\"sCol12\" /><t:Column t:key=\"entryID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:configured=\"false\" t:styleID=\"sCol13\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">状态</t:Cell><t:Cell t:configured=\"false\">所属公司</t:Cell><t:Cell t:configured=\"false\">所属项目</t:Cell><t:Cell t:configured=\"false\">任务名称</t:Cell><t:Cell t:configured=\"false\">完成进度(%)</t:Cell><t:Cell t:configured=\"false\">计划完成日期</t:Cell><t:Cell t:configured=\"false\">考核日期</t:Cell><t:Cell t:configured=\"false\">实际完成日期</t:Cell><t:Cell t:configured=\"false\">预计完成日期</t:Cell><t:Cell t:configured=\"false\">责任人</t:Cell><t:Cell t:configured=\"false\">责任部门</t:Cell><t:Cell t:configured=\"false\">完成情况说明</t:Cell><t:Cell t:configured=\"false\">是否完成</t:Cell><t:Cell t:configured=\"false\">分录ID</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.thisTable.setFormatXml(resHelper.translateString("thisTable",thisTableStrXML));

        

        // nextTable
		String nextTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"company\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"taskName\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"planStartDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"planEndDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"checkDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"respPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" /><t:Column t:key=\"respDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" /><t:Column t:key=\"entryID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:configured=\"false\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">所属公司</t:Cell><t:Cell t:configured=\"false\">所属项目</t:Cell><t:Cell t:configured=\"false\">任务名称</t:Cell><t:Cell t:configured=\"false\">计划开始日期</t:Cell><t:Cell t:configured=\"false\">计划完成日期</t:Cell><t:Cell t:configured=\"false\">考核日期</t:Cell><t:Cell t:configured=\"false\">责任人</t:Cell><t:Cell t:configured=\"false\">责任部门</t:Cell><t:Cell t:configured=\"false\">分录ID</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.nextTable.setFormatXml(resHelper.translateString("nextTable",nextTableStrXML));

        

        // pnlChart
        // kDPanel1
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // cbMonth
        this.cbMonth.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cbMonth_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cbYear
        this.cbYear.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cbYear_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelUnderline(true);		
        this.contProject.setBoundLabelLength(60);
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");
        this.prmtProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnPrint
        this.btnPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrint.setText(resHelper.getString("btnPrint.text"));
        // btnPrintPreview
        this.btnPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintpreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrintPreview.setText(resHelper.getString("btnPrintPreview.text"));
        // btnExportExcel
        this.btnExportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportExcel.setText(resHelper.getString("btnExportExcel.text"));
        // btnRefresh
        this.btnRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRefresh.setText(resHelper.getString("btnRefresh.text"));
        // kDMenu1		
        this.kDMenu1.setText(resHelper.getString("kDMenu1.text"));		
        this.kDMenu1.setToolTipText(resHelper.getString("kDMenu1.toolTipText"));		
        this.kDMenu1.setMnemonic(86);
        // menuItemPrint
        this.menuItemPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrint.setText(resHelper.getString("menuItemPrint.text"));		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrint.setToolTipText(resHelper.getString("menuItemPrint.toolTipText"));		
        this.menuItemPrint.setMnemonic(80);
        // menuItemPrintPreview
        this.menuItemPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintpreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrintPreview.setText(resHelper.getString("menuItemPrintPreview.text"));		
        this.menuItemPrintPreview.setVisible(false);		
        this.menuItemPrintPreview.setToolTipText(resHelper.getString("menuItemPrintPreview.toolTipText"));		
        this.menuItemPrintPreview.setMnemonic(86);
        // kDSeparator2
        // menueItemExportExcel
        this.menueItemExportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menueItemExportExcel.setText(resHelper.getString("menueItemExportExcel.text"));		
        this.menueItemExportExcel.setToolTipText(resHelper.getString("menueItemExportExcel.toolTipText"));		
        this.menueItemExportExcel.setMnemonic(69);
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
        sptTop.setBounds(new Rectangle(5, 6, 1002, 619));
        this.add(sptTop, new KDLayout.Constraints(5, 6, 1002, 619, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //sptTop
        sptTop.add(sptDown, "bottom");
        sptTop.add(cbPanel, "top");
        //sptDown
        sptDown.add(contThis, "top");
        sptDown.add(contNext, "bottom");
        //contThis
contThis.getContentPane().setLayout(new BorderLayout(0, 0));        contThis.getContentPane().add(thisTable, BorderLayout.CENTER);
        //contNext
contNext.getContentPane().setLayout(new BorderLayout(0, 0));        contNext.getContentPane().add(nextTable, BorderLayout.CENTER);
        //cbPanel
        cbPanel.setLayout(new KDLayout());
        cbPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1001, 289));        pnlChart.setBounds(new Rectangle(2, 49, 1002, 240));
        cbPanel.add(pnlChart, new KDLayout.Constraints(2, 49, 1002, 240, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel1.setBounds(new Rectangle(3, 1, 737, 46));
        cbPanel.add(kDPanel1, new KDLayout.Constraints(3, 1, 737, 46, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
pnlChart.setLayout(new BorderLayout(0, 0));        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(3, 1, 737, 46));        kDLabel1.setBounds(new Rectangle(118, 4, 13, 28));
        kDPanel1.add(kDLabel1, new KDLayout.Constraints(118, 4, 13, 28, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel2.setBounds(new Rectangle(6, 3, 36, 30));
        kDPanel1.add(kDLabel2, new KDLayout.Constraints(6, 3, 36, 30, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cbMonth.setBounds(new Rectangle(135, 8, 63, 19));
        kDPanel1.add(cbMonth, new KDLayout.Constraints(135, 8, 63, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cbYear.setBounds(new Rectangle(45, 8, 63, 19));
        kDPanel1.add(cbYear, new KDLayout.Constraints(45, 8, 63, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProject.setBounds(new Rectangle(236, 8, 451, 19));
        kDPanel1.add(contProject, new KDLayout.Constraints(236, 8, 451, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contProject
        contProject.setBoundEditor(prmtProject);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(kDMenu1);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //kDMenu1
        kDMenu1.add(menuItemPrint);
        kDMenu1.add(menuItemPrintPreview);
        kDMenu1.add(kDSeparator2);
        kDMenu1.add(menueItemExportExcel);
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
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnExportExcel);
        this.toolBar.add(btnRefresh);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.ViewGroupMonthReportUIHandler";
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
	 * ????????У??
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
     * output cbMonth_itemStateChanged method
     */
    protected void cbMonth_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output cbYear_itemStateChanged method
     */
    protected void cbYear_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtProject_dataChanged method
     */
    protected void prmtProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    	

    /**
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrintpreview_actionPerformed method
     */
    public void actionPrintpreview_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportExcel_actionPerformed method
     */
    public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRefresh_actionPerformed method
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
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
	public RequestContext prepareActionPrintpreview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintpreview() {
    	return false;
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
	public RequestContext prepareActionRefresh(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRefresh() {
    	return false;
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
            innerActionPerformed("eas", AbstractViewGroupMonthReportUI.this, "ActionPrint", "actionPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionPrintpreview class
     */     
    protected class ActionPrintpreview extends ItemAction {     
    
        public ActionPrintpreview()
        {
            this(null);
        }

        public ActionPrintpreview(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPrintpreview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintpreview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintpreview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractViewGroupMonthReportUI.this, "ActionPrintpreview", "actionPrintpreview_actionPerformed", e);
        }
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
            innerActionPerformed("eas", AbstractViewGroupMonthReportUI.this, "ActionExportExcel", "actionExportExcel_actionPerformed", e);
        }
    }

    /**
     * output ActionRefresh class
     */     
    protected class ActionRefresh extends ItemAction {     
    
        public ActionRefresh()
        {
            this(null);
        }

        public ActionRefresh(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRefresh.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefresh.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefresh.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractViewGroupMonthReportUI.this, "ActionRefresh", "actionRefresh_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "ViewGroupMonthReportUI");
    }




}