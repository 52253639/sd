/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

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
public abstract class AbstractRoomPriceAdjustListUI extends com.kingdee.eas.fdc.basedata.client.FDCBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomPriceAdjustListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel roomTab;
    protected com.kingdee.bos.ctrl.swing.KDPanel billTab;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDRoomSplitPanel;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDRoomTreeView;
    protected com.kingdee.bos.ctrl.swing.KDPanel overViewTab;
    protected com.kingdee.bos.ctrl.swing.KDTree kdRoomTree;
    protected com.kingdee.bos.ctrl.swing.KDPanel sumInfoPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel roomPanel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer sumAmoutCtnr;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer avgSumCntr;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer avgBuildPriceCtnr;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer avgRoomPriceCtnr;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer buildAreaCtnr;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer sumRoomAreaCtnr;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtSumAmount;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtAvgAmount;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtAvgBuild;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtAvgRoom;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtBuildArea;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDBillSplitPanel;
    protected com.kingdee.bos.ctrl.swing.KDTreeView billTreeView;
    protected com.kingdee.bos.ctrl.swing.KDPanel priceListTab;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewPriceHis;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExecute;
    protected ActionAudit actionAudit = null;
    protected ActionExecute actionExecute = null;
    protected ActionViewPriceHis actionViewPriceHis = null;
    protected ActionUnAudit actionUnAudit = null;
    /**
     * output class constructor
     */
    public AbstractRoomPriceAdjustListUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractRoomPriceAdjustListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "RoomPriceAdjustQuery");
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExecute
        this.actionExecute = new ActionExecute(this);
        getActionManager().registerAction("actionExecute", actionExecute);
         this.actionExecute.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewPriceHis
        this.actionViewPriceHis = new ActionViewPriceHis(this);
        getActionManager().registerAction("actionViewPriceHis", actionViewPriceHis);
         this.actionViewPriceHis.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.roomTab = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.billTab = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDRoomSplitPanel = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDRoomTreeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.overViewTab = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdRoomTree = new com.kingdee.bos.ctrl.swing.KDTree();
        this.sumInfoPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.roomPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.sumAmoutCtnr = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.avgSumCntr = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.avgBuildPriceCtnr = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.avgRoomPriceCtnr = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.buildAreaCtnr = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.sumRoomAreaCtnr = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSumAmount = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtAvgAmount = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtAvgBuild = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtAvgRoom = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtBuildArea = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.tblRoomPrice = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDBillSplitPanel = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.billTreeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.priceListTab = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnViewPriceHis = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExecute = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.roomTab.setName("roomTab");
        this.billTab.setName("billTab");
        this.kDRoomSplitPanel.setName("kDRoomSplitPanel");
        this.kDRoomTreeView.setName("kDRoomTreeView");
        this.overViewTab.setName("overViewTab");
        this.kdRoomTree.setName("kdRoomTree");
        this.sumInfoPanel.setName("sumInfoPanel");
        this.roomPanel.setName("roomPanel");
        this.sumAmoutCtnr.setName("sumAmoutCtnr");
        this.avgSumCntr.setName("avgSumCntr");
        this.avgBuildPriceCtnr.setName("avgBuildPriceCtnr");
        this.avgRoomPriceCtnr.setName("avgRoomPriceCtnr");
        this.buildAreaCtnr.setName("buildAreaCtnr");
        this.sumRoomAreaCtnr.setName("sumRoomAreaCtnr");
        this.txtSumAmount.setName("txtSumAmount");
        this.txtAvgAmount.setName("txtAvgAmount");
        this.txtAvgBuild.setName("txtAvgBuild");
        this.txtAvgRoom.setName("txtAvgRoom");
        this.txtBuildArea.setName("txtBuildArea");
        this.txtRoomArea.setName("txtRoomArea");
        this.tblRoomPrice.setName("tblRoomPrice");
        this.kDBillSplitPanel.setName("kDBillSplitPanel");
        this.billTreeView.setName("billTreeView");
        this.priceListTab.setName("priceListTab");
        this.treeMain.setName("treeMain");
        this.btnViewPriceHis.setName("btnViewPriceHis");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        this.btnExecute.setName("btnExecute");
        // CoreUI		
        this.btnPageSetup.setEnabled(false);
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol0\" /><t:Column t:key=\"orgName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"billNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"billName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"priceMode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"priceType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isExecute\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"auditTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{orgName}</t:Cell><t:Cell>$Resource{billNo}</t:Cell><t:Cell>$Resource{billName}</t:Cell><t:Cell>$Resource{priceMode}</t:Cell><t:Cell>$Resource{priceType}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{isExecute}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{auditTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","orgUnit.name","number","name","priceBillMode","priceBillType","state","isExecuted","description","creator.name","createTime","auditor.name","auditDate"});

		
        this.btnLocate.setEnabled(false);		
        this.btnLocate.setVisible(false);		
        this.btnAttachment.setEnabled(false);		
        this.btnAttachment.setVisible(false);		
        this.btnCancel.setEnabled(false);		
        this.btnCancelCancel.setEnabled(false);		
        this.btnQueryScheme.setEnabled(false);		
        this.btnQueryScheme.setVisible(false);		
        this.btnVoucher.setEnabled(false);		
        this.btnVoucher.setVisible(false);		
        this.btnDelVoucher.setEnabled(false);		
        this.btnDelVoucher.setVisible(false);		
        this.btnCreateTo.setEnabled(false);		
        this.btnCreateTo.setVisible(false);		
        this.btnCopyTo.setEnabled(false);		
        this.btnTraceUp.setEnabled(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setEnabled(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setEnabled(false);		
        this.btnAuditResult.setVisible(false);		
        this.menuItemCreateTo.setEnabled(false);		
        this.menuItemCreateTo.setVisible(false);		
        this.menuItemCopyTo.setEnabled(false);		
        this.menuItemCopyTo.setVisible(false);		
        this.btnMultiapprove.setEnabled(false);		
        this.btnNextPerson.setEnabled(false);		
        this.btnWorkFlowG.setEnabled(false);		
        this.btnWorkFlowG.setVisible(false);
        // kDTabbedPane1
        this.kDTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kDTabbedPane1_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // roomTab
        // billTab
        // kDRoomSplitPanel		
        this.kDRoomSplitPanel.setDividerLocation(240);
        // kDRoomTreeView
        // overViewTab		
        this.overViewTab.setPreferredSize(new Dimension(763,528));
        // kdRoomTree
        this.kdRoomTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    kdRoomTree_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // sumInfoPanel		
        this.sumInfoPanel.setPreferredSize(new Dimension(763,100));		
        this.sumInfoPanel.setMinimumSize(new Dimension(763,100));
        // roomPanel		
        this.roomPanel.setMinimumSize(new Dimension(763,528));		
        this.roomPanel.setPreferredSize(new Dimension(763,528));
        // sumAmoutCtnr		
        this.sumAmoutCtnr.setBoundLabelText(resHelper.getString("sumAmoutCtnr.boundLabelText"));		
        this.sumAmoutCtnr.setBoundLabelLength(100);		
        this.sumAmoutCtnr.setBoundLabelUnderline(true);
        // avgSumCntr		
        this.avgSumCntr.setBoundLabelText(resHelper.getString("avgSumCntr.boundLabelText"));		
        this.avgSumCntr.setBoundLabelLength(100);		
        this.avgSumCntr.setBoundLabelUnderline(true);
        // avgBuildPriceCtnr		
        this.avgBuildPriceCtnr.setBoundLabelText(resHelper.getString("avgBuildPriceCtnr.boundLabelText"));		
        this.avgBuildPriceCtnr.setBoundLabelLength(100);		
        this.avgBuildPriceCtnr.setBoundLabelUnderline(true);
        // avgRoomPriceCtnr		
        this.avgRoomPriceCtnr.setBoundLabelText(resHelper.getString("avgRoomPriceCtnr.boundLabelText"));		
        this.avgRoomPriceCtnr.setBoundLabelLength(100);		
        this.avgRoomPriceCtnr.setBoundLabelUnderline(true);
        // buildAreaCtnr		
        this.buildAreaCtnr.setBoundLabelText(resHelper.getString("buildAreaCtnr.boundLabelText"));		
        this.buildAreaCtnr.setBoundLabelLength(100);		
        this.buildAreaCtnr.setBoundLabelUnderline(true);
        // sumRoomAreaCtnr		
        this.sumRoomAreaCtnr.setBoundLabelText(resHelper.getString("sumRoomAreaCtnr.boundLabelText"));		
        this.sumRoomAreaCtnr.setBoundLabelLength(100);		
        this.sumRoomAreaCtnr.setBoundLabelUnderline(true);
        // txtSumAmount		
        this.txtSumAmount.setDataType(6);		
        this.txtSumAmount.setEnabled(false);		
        this.txtSumAmount.setEditable(false);		
        this.txtSumAmount.setPrecision(2);
        // txtAvgAmount		
        this.txtAvgAmount.setDataType(6);		
        this.txtAvgAmount.setEnabled(false);		
        this.txtAvgAmount.setEditable(false);		
        this.txtAvgAmount.setPrecision(2);
        // txtAvgBuild		
        this.txtAvgBuild.setDataType(6);		
        this.txtAvgBuild.setEnabled(false);		
        this.txtAvgBuild.setPrecision(2);		
        this.txtAvgBuild.setEditable(false);
        // txtAvgRoom		
        this.txtAvgRoom.setDataType(6);		
        this.txtAvgRoom.setEnabled(false);		
        this.txtAvgRoom.setEditable(false);		
        this.txtAvgRoom.setPrecision(2);
        // txtBuildArea		
        this.txtBuildArea.setDataType(6);		
        this.txtBuildArea.setEnabled(false);		
        this.txtBuildArea.setEditable(false);		
        this.txtBuildArea.setPrecision(2);
        // txtRoomArea		
        this.txtRoomArea.setDataType(6);		
        this.txtRoomArea.setEnabled(false);		
        this.txtRoomArea.setEditable(false);		
        this.txtRoomArea.setPrecision(2);
        // tblRoomPrice
		String tblRoomPriceStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"building\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"unit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"roomNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"sellType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"priceType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"sumAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"buildArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"buildPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"roomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"sellState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"changeState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"areaState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"head\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{building}</t:Cell><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{roomNo}</t:Cell><t:Cell>$Resource{sellType}</t:Cell><t:Cell>$Resource{priceType}</t:Cell><t:Cell>$Resource{sumAmount}</t:Cell><t:Cell>$Resource{buildArea}</t:Cell><t:Cell>$Resource{buildPrice}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{roomPrice}</t:Cell><t:Cell>$Resource{sellState}</t:Cell><t:Cell>$Resource{changeState}</t:Cell><t:Cell>$Resource{areaState}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblRoomPrice.setFormatXml(resHelper.translateString("tblRoomPrice",tblRoomPriceStrXML));		
        this.tblRoomPrice.setMinimumSize(new Dimension(763,628));		
        this.tblRoomPrice.setPreferredSize(new Dimension(763,628));
        this.tblRoomPrice.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblRoomPrice_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDBillSplitPanel		
        this.kDBillSplitPanel.setDividerLocation(240);
        // billTreeView
        // priceListTab		
        this.priceListTab.setPreferredSize(new Dimension(763,628));		
        this.priceListTab.setMinimumSize(new Dimension(763,628));
        // treeMain
        this.treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnViewPriceHis
        this.btnViewPriceHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewPriceHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewPriceHis.setText(resHelper.getString("btnViewPriceHis.text"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));
        // btnExecute
        this.btnExecute.setAction((IItemAction)ActionProxyFactory.getProxy(actionExecute, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExecute.setText(resHelper.getString("btnExecute.text"));
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
        this.setBounds(new Rectangle(10, 10, 1023, 639));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1023, 639));
        kDTabbedPane1.setBounds(new Rectangle(11, 9, 1008, 629));
        this.add(kDTabbedPane1, new KDLayout.Constraints(11, 9, 1008, 629, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDTabbedPane1
        kDTabbedPane1.add(roomTab, resHelper.getString("roomTab.constraints"));
        kDTabbedPane1.add(billTab, resHelper.getString("billTab.constraints"));
        //roomTab
        roomTab.setLayout(new KDLayout());
        roomTab.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1007, 596));        kDRoomSplitPanel.setBounds(new Rectangle(5, 5, 1000, 592));
        roomTab.add(kDRoomSplitPanel, new KDLayout.Constraints(5, 5, 1000, 592, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDRoomSplitPanel
        kDRoomSplitPanel.add(kDRoomTreeView, "left");
        kDRoomSplitPanel.add(overViewTab, "right");
        //kDRoomTreeView
        kDRoomTreeView.setTree(kdRoomTree);
        //overViewTab
        overViewTab.setLayout(new KDLayout());
        overViewTab.putClientProperty("OriginalBounds", new Rectangle(0, 0, 749, 591));        sumInfoPanel.setBounds(new Rectangle(0, 495, 747, 88));
        overViewTab.add(sumInfoPanel, new KDLayout.Constraints(0, 495, 747, 88, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        roomPanel.setBounds(new Rectangle(0, 0, 747, 492));
        overViewTab.add(roomPanel, new KDLayout.Constraints(0, 0, 747, 492, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //sumInfoPanel
        sumInfoPanel.setLayout(null);        sumAmoutCtnr.setBounds(new Rectangle(16, 16, 212, 19));
        sumInfoPanel.add(sumAmoutCtnr, null);
        avgSumCntr.setBounds(new Rectangle(16, 56, 212, 19));
        sumInfoPanel.add(avgSumCntr, null);
        avgBuildPriceCtnr.setBounds(new Rectangle(521, 56, 212, 19));
        sumInfoPanel.add(avgBuildPriceCtnr, null);
        avgRoomPriceCtnr.setBounds(new Rectangle(268, 56, 212, 19));
        sumInfoPanel.add(avgRoomPriceCtnr, null);
        buildAreaCtnr.setBounds(new Rectangle(268, 16, 212, 19));
        sumInfoPanel.add(buildAreaCtnr, null);
        sumRoomAreaCtnr.setBounds(new Rectangle(521, 16, 212, 19));
        sumInfoPanel.add(sumRoomAreaCtnr, null);
        //sumAmoutCtnr
        sumAmoutCtnr.setBoundEditor(txtSumAmount);
        //avgSumCntr
        avgSumCntr.setBoundEditor(txtAvgAmount);
        //avgBuildPriceCtnr
        avgBuildPriceCtnr.setBoundEditor(txtAvgBuild);
        //avgRoomPriceCtnr
        avgRoomPriceCtnr.setBoundEditor(txtAvgRoom);
        //buildAreaCtnr
        buildAreaCtnr.setBoundEditor(txtBuildArea);
        //sumRoomAreaCtnr
        sumRoomAreaCtnr.setBoundEditor(txtRoomArea);
        //roomPanel
        roomPanel.setLayout(new KDLayout());
        roomPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 747, 492));        tblRoomPrice.setBounds(new Rectangle(1, 1, 745, 488));
        roomPanel.add(tblRoomPrice, new KDLayout.Constraints(1, 1, 745, 488, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //billTab
        billTab.setLayout(new KDLayout());
        billTab.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1007, 596));        kDBillSplitPanel.setBounds(new Rectangle(5, 5, 1000, 592));
        billTab.add(kDBillSplitPanel, new KDLayout.Constraints(5, 5, 1000, 592, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDBillSplitPanel
        kDBillSplitPanel.add(billTreeView, "left");
        kDBillSplitPanel.add(priceListTab, "right");
        //billTreeView
        billTreeView.setTree(treeMain);
        //priceListTab
        priceListTab.setLayout(new KDLayout());
        priceListTab.putClientProperty("OriginalBounds", new Rectangle(0, 0, 749, 591));        tblMain.setBounds(new Rectangle(0, 0, 747, 595));
        priceListTab.add(tblMain, new KDLayout.Constraints(0, 0, 747, 595, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkFlow);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemSwitchView);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuWorkFlow
        menuWorkFlow.add(menuItemViewDoProccess);
        menuWorkFlow.add(menuItemMultiapprove);
        menuWorkFlow.add(menuItemWorkFlowG);
        menuWorkFlow.add(menuItemWorkFlowList);
        menuWorkFlow.add(separatorWF1);
        menuWorkFlow.add(menuItemNextPerson);
        menuWorkFlow.add(menuItemAuditResult);
        menuWorkFlow.add(kDSeparator7);
        menuWorkFlow.add(menuItemSendSmsMessage);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
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
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnViewPriceHis);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnExecute);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomPriceAdjustListUIHandler";
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
     * output kDTabbedPane1_stateChanged method
     */
    protected void kDTabbedPane1_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output kdRoomTree_valueChanged method
     */
    protected void kdRoomTree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output tblRoomPrice_tableClicked method
     */
    protected void tblRoomPrice_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("orgUnit.name"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("priceBillMode"));
        sic.add(new SelectorItemInfo("priceBillType"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("isExecuted"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditDate"));
        return sic;
    }        
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExecute_actionPerformed method
     */
    public void actionExecute_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewPriceHis_actionPerformed method
     */
    public void actionViewPriceHis_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionExecute(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExecute() {
    	return false;
    }
	public RequestContext prepareActionViewPriceHis(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewPriceHis() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }

    /**
     * output ActionAudit class
     */     
    protected class ActionAudit extends ItemAction {     
    
        public ActionAudit()
        {
            this(null);
        }

        public ActionAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomPriceAdjustListUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionExecute class
     */     
    protected class ActionExecute extends ItemAction {     
    
        public ActionExecute()
        {
            this(null);
        }

        public ActionExecute(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionExecute.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExecute.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExecute.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomPriceAdjustListUI.this, "ActionExecute", "actionExecute_actionPerformed", e);
        }
    }

    /**
     * output ActionViewPriceHis class
     */     
    protected class ActionViewPriceHis extends ItemAction {     
    
        public ActionViewPriceHis()
        {
            this(null);
        }

        public ActionViewPriceHis(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionViewPriceHis.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewPriceHis.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewPriceHis.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomPriceAdjustListUI.this, "ActionViewPriceHis", "actionViewPriceHis_actionPerformed", e);
        }
    }

    /**
     * output ActionUnAudit class
     */     
    protected class ActionUnAudit extends ItemAction {     
    
        public ActionUnAudit()
        {
            this(null);
        }

        public ActionUnAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomPriceAdjustListUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomPriceAdjustListUI");
    }




}