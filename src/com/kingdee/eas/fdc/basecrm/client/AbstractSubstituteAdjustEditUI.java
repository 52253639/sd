/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

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
public abstract class AbstractSubstituteAdjustEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSubstituteAdjustEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtEntrys_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contsellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttransfAdjustDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbuilding;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contcollectFunction;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChooseRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteRoom;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtmoneyDefine;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsellProject;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pktransfAdjustDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtbuilding;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtcollectFunction;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCalculate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTransfTo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuCalculate;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuTransfTo;
    protected com.kingdee.eas.fdc.basecrm.SubstituteAdjustInfo editData = null;
    protected ActionCalculate actinoCalculate = null;
    protected ActionTransfTo actionTransfTo = null;
    /**
     * output class constructor
     */
    public AbstractSubstituteAdjustEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractSubstituteAdjustEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrint
        actionPrint.setEnabled(true);
        actionPrint.setDaemonRun(false);

        actionPrint.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
        actionPrint.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
        actionPrint.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.NAME");
        actionPrint.putValue(ItemAction.NAME, _tempStr);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrintPreview
        actionPrintPreview.setEnabled(true);
        actionPrintPreview.setDaemonRun(false);

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actinoCalculate
        this.actinoCalculate = new ActionCalculate(this);
        getActionManager().registerAction("actinoCalculate", actinoCalculate);
         this.actinoCalculate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTransfTo
        this.actionTransfTo = new ActionTransfTo(this);
        getActionManager().registerAction("actionTransfTo", actionTransfTo);
         this.actionTransfTo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contmoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contsellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttransfAdjustDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contcollectFunction = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnChooseRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtmoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtsellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pktransfAdjustDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtbuilding = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtcollectFunction = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnCalculate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTransfTo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuCalculate = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuTransfTo = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.kdtEntrys.setName("kdtEntrys");
        this.contmoneyDefine.setName("contmoneyDefine");
        this.contsellProject.setName("contsellProject");
        this.conttransfAdjustDate.setName("conttransfAdjustDate");
        this.contbuilding.setName("contbuilding");
        this.contcollectFunction.setName("contcollectFunction");
        this.btnChooseRoom.setName("btnChooseRoom");
        this.btnDeleteRoom.setName("btnDeleteRoom");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.prmtmoneyDefine.setName("prmtmoneyDefine");
        this.prmtsellProject.setName("prmtsellProject");
        this.pktransfAdjustDate.setName("pktransfAdjustDate");
        this.prmtbuilding.setName("prmtbuilding");
        this.prmtcollectFunction.setName("prmtcollectFunction");
        this.btnCalculate.setName("btnCalculate");
        this.btnTransfTo.setName("btnTransfTo");
        this.menuCalculate.setName("menuCalculate");
        this.menuTransfTo.setName("menuTransfTo");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnAddLine.setVisible(false);		
        this.btnCopyLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.separator1.setVisible(false);		
        this.separator3.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuItemViewSubmitProccess.setVisible(false);		
        this.menuItemViewDoProccess.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuItemRemoveLine.setVisible(false);		
        this.btnCreateTo.setVisible(true);		
        this.menuItemCreateTo.setVisible(true);		
        this.menuItemCopyLine.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>&amp;date</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"relateBizType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"relateBizNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"relateBizEntryId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"building\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"contactAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"payAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"relateBizId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"newPayAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"modifyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{relateBizType}</t:Cell><t:Cell>$Resource{relateBizNumber}</t:Cell><t:Cell>$Resource{relateBizEntryId}</t:Cell><t:Cell>$Resource{building}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{contactAmount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{payAmount}</t:Cell><t:Cell>$Resource{relateBizId}</t:Cell><t:Cell>$Resource{newPayAmount}</t:Cell><t:Cell>$Resource{modifyType}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));
        this.kdtEntrys.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtEntrys_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

                this.kdtEntrys.putBindContents("editData",new String[] {"id","relateBizType","relateBizNumber","relateBizEntryId","building","room","customer","bizDate","contactAmount","","payAmount","relateBizId","newPayAmount","modifyType"});


        this.kdtEntrys.checkParsed();
        KDComboBox kdtEntrys_relateBizType_ComboBox = new KDComboBox();
        kdtEntrys_relateBizType_ComboBox.setName("kdtEntrys_relateBizType_ComboBox");
        kdtEntrys_relateBizType_ComboBox.setVisible(true);
        kdtEntrys_relateBizType_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basecrm.RelatBizType").toArray());
        KDTDefaultCellEditor kdtEntrys_relateBizType_CellEditor = new KDTDefaultCellEditor(kdtEntrys_relateBizType_ComboBox);
        this.kdtEntrys.getColumn("relateBizType").setEditor(kdtEntrys_relateBizType_CellEditor);
        KDTextField kdtEntrys_relateBizNumber_TextField = new KDTextField();
        kdtEntrys_relateBizNumber_TextField.setName("kdtEntrys_relateBizNumber_TextField");
        kdtEntrys_relateBizNumber_TextField.setMaxLength(44);
        KDTDefaultCellEditor kdtEntrys_relateBizNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_relateBizNumber_TextField);
        this.kdtEntrys.getColumn("relateBizNumber").setEditor(kdtEntrys_relateBizNumber_CellEditor);
        KDTextField kdtEntrys_relateBizEntryId_TextField = new KDTextField();
        kdtEntrys_relateBizEntryId_TextField.setName("kdtEntrys_relateBizEntryId_TextField");
        kdtEntrys_relateBizEntryId_TextField.setMaxLength(44);
        KDTDefaultCellEditor kdtEntrys_relateBizEntryId_CellEditor = new KDTDefaultCellEditor(kdtEntrys_relateBizEntryId_TextField);
        this.kdtEntrys.getColumn("relateBizEntryId").setEditor(kdtEntrys_relateBizEntryId_CellEditor);
        final KDBizPromptBox kdtEntrys_building_PromptBox = new KDBizPromptBox();
        kdtEntrys_building_PromptBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7BuildingQuery");
        kdtEntrys_building_PromptBox.setVisible(true);
        kdtEntrys_building_PromptBox.setEditable(true);
        kdtEntrys_building_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_building_PromptBox.setEditFormat("$number$");
        kdtEntrys_building_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_building_CellEditor = new KDTDefaultCellEditor(kdtEntrys_building_PromptBox);
        this.kdtEntrys.getColumn("building").setEditor(kdtEntrys_building_CellEditor);
        ObjectValueRender kdtEntrys_building_OVR = new ObjectValueRender();
        kdtEntrys_building_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("building").setRenderer(kdtEntrys_building_OVR);
        final KDBizPromptBox kdtEntrys_room_PromptBox = new KDBizPromptBox();
        kdtEntrys_room_PromptBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomQuery");
        kdtEntrys_room_PromptBox.setVisible(true);
        kdtEntrys_room_PromptBox.setEditable(true);
        kdtEntrys_room_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_room_PromptBox.setEditFormat("$number$");
        kdtEntrys_room_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_room_CellEditor = new KDTDefaultCellEditor(kdtEntrys_room_PromptBox);
        this.kdtEntrys.getColumn("room").setEditor(kdtEntrys_room_CellEditor);
        ObjectValueRender kdtEntrys_room_OVR = new ObjectValueRender();
        kdtEntrys_room_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntrys.getColumn("room").setRenderer(kdtEntrys_room_OVR);
        KDTextField kdtEntrys_customer_TextField = new KDTextField();
        kdtEntrys_customer_TextField.setName("kdtEntrys_customer_TextField");
        kdtEntrys_customer_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtEntrys_customer_CellEditor = new KDTDefaultCellEditor(kdtEntrys_customer_TextField);
        this.kdtEntrys.getColumn("customer").setEditor(kdtEntrys_customer_CellEditor);
        KDDatePicker kdtEntrys_bizDate_DatePicker = new KDDatePicker();
        kdtEntrys_bizDate_DatePicker.setName("kdtEntrys_bizDate_DatePicker");
        kdtEntrys_bizDate_DatePicker.setVisible(true);
        kdtEntrys_bizDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtEntrys_bizDate_CellEditor = new KDTDefaultCellEditor(kdtEntrys_bizDate_DatePicker);
        this.kdtEntrys.getColumn("bizDate").setEditor(kdtEntrys_bizDate_CellEditor);
        KDFormattedTextField kdtEntrys_contactAmount_TextField = new KDFormattedTextField();
        kdtEntrys_contactAmount_TextField.setName("kdtEntrys_contactAmount_TextField");
        kdtEntrys_contactAmount_TextField.setVisible(true);
        kdtEntrys_contactAmount_TextField.setEditable(true);
        kdtEntrys_contactAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_contactAmount_TextField.setDataType(1);
        	kdtEntrys_contactAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_contactAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_contactAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_contactAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_contactAmount_TextField);
        this.kdtEntrys.getColumn("contactAmount").setEditor(kdtEntrys_contactAmount_CellEditor);
        KDFormattedTextField kdtEntrys_payAmount_TextField = new KDFormattedTextField();
        kdtEntrys_payAmount_TextField.setName("kdtEntrys_payAmount_TextField");
        kdtEntrys_payAmount_TextField.setVisible(true);
        kdtEntrys_payAmount_TextField.setEditable(true);
        kdtEntrys_payAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_payAmount_TextField.setDataType(1);
        	kdtEntrys_payAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_payAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_payAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_payAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_payAmount_TextField);
        this.kdtEntrys.getColumn("payAmount").setEditor(kdtEntrys_payAmount_CellEditor);
        KDTextField kdtEntrys_relateBizId_TextField = new KDTextField();
        kdtEntrys_relateBizId_TextField.setName("kdtEntrys_relateBizId_TextField");
        kdtEntrys_relateBizId_TextField.setMaxLength(44);
        KDTDefaultCellEditor kdtEntrys_relateBizId_CellEditor = new KDTDefaultCellEditor(kdtEntrys_relateBizId_TextField);
        this.kdtEntrys.getColumn("relateBizId").setEditor(kdtEntrys_relateBizId_CellEditor);
        KDFormattedTextField kdtEntrys_newPayAmount_TextField = new KDFormattedTextField();
        kdtEntrys_newPayAmount_TextField.setName("kdtEntrys_newPayAmount_TextField");
        kdtEntrys_newPayAmount_TextField.setVisible(true);
        kdtEntrys_newPayAmount_TextField.setEditable(true);
        kdtEntrys_newPayAmount_TextField.setHorizontalAlignment(2);
        kdtEntrys_newPayAmount_TextField.setDataType(1);
        	kdtEntrys_newPayAmount_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        	kdtEntrys_newPayAmount_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntrys_newPayAmount_TextField.setPrecision(10);
        KDTDefaultCellEditor kdtEntrys_newPayAmount_CellEditor = new KDTDefaultCellEditor(kdtEntrys_newPayAmount_TextField);
        this.kdtEntrys.getColumn("newPayAmount").setEditor(kdtEntrys_newPayAmount_CellEditor);
        KDTextField kdtEntrys_modifyType_TextField = new KDTextField();
        kdtEntrys_modifyType_TextField.setName("kdtEntrys_modifyType_TextField");
        kdtEntrys_modifyType_TextField.setMaxLength(44);
        KDTDefaultCellEditor kdtEntrys_modifyType_CellEditor = new KDTDefaultCellEditor(kdtEntrys_modifyType_TextField);
        this.kdtEntrys.getColumn("modifyType").setEditor(kdtEntrys_modifyType_CellEditor);
        // contmoneyDefine		
        this.contmoneyDefine.setBoundLabelText(resHelper.getString("contmoneyDefine.boundLabelText"));		
        this.contmoneyDefine.setBoundLabelLength(100);		
        this.contmoneyDefine.setBoundLabelUnderline(true);		
        this.contmoneyDefine.setVisible(true);
        // contsellProject		
        this.contsellProject.setBoundLabelText(resHelper.getString("contsellProject.boundLabelText"));		
        this.contsellProject.setBoundLabelLength(100);		
        this.contsellProject.setBoundLabelUnderline(true);		
        this.contsellProject.setVisible(true);
        // conttransfAdjustDate		
        this.conttransfAdjustDate.setBoundLabelText(resHelper.getString("conttransfAdjustDate.boundLabelText"));		
        this.conttransfAdjustDate.setBoundLabelLength(100);		
        this.conttransfAdjustDate.setBoundLabelUnderline(true);		
        this.conttransfAdjustDate.setVisible(true);
        // contbuilding		
        this.contbuilding.setBoundLabelText(resHelper.getString("contbuilding.boundLabelText"));		
        this.contbuilding.setBoundLabelLength(100);		
        this.contbuilding.setBoundLabelUnderline(true);		
        this.contbuilding.setVisible(true);
        // contcollectFunction		
        this.contcollectFunction.setBoundLabelText(resHelper.getString("contcollectFunction.boundLabelText"));		
        this.contcollectFunction.setBoundLabelLength(100);		
        this.contcollectFunction.setBoundLabelUnderline(true);		
        this.contcollectFunction.setVisible(true);
        // btnChooseRoom		
        this.btnChooseRoom.setText(resHelper.getString("btnChooseRoom.text"));
        this.btnChooseRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnChooseRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDeleteRoom		
        this.btnDeleteRoom.setText(resHelper.getString("btnDeleteRoom.text"));
        this.btnDeleteRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDeleteRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // pkBizDate		
        this.pkBizDate.setVisible(true);		
        this.pkBizDate.setEnabled(true);		
        this.pkBizDate.setRequired(true);
        // prmtmoneyDefine		
        this.prmtmoneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.prmtmoneyDefine.setVisible(true);		
        this.prmtmoneyDefine.setEditable(true);		
        this.prmtmoneyDefine.setDisplayFormat("$name$");		
        this.prmtmoneyDefine.setEditFormat("$number$");		
        this.prmtmoneyDefine.setCommitFormat("$number$");		
        this.prmtmoneyDefine.setRequired(true);
        // prmtsellProject		
        this.prmtsellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtsellProject.setVisible(true);		
        this.prmtsellProject.setEditable(true);		
        this.prmtsellProject.setDisplayFormat("$name$");		
        this.prmtsellProject.setEditFormat("$number$");		
        this.prmtsellProject.setCommitFormat("$number$");		
        this.prmtsellProject.setRequired(true);		
        this.prmtsellProject.setEnabled(false);
        // pktransfAdjustDate		
        this.pktransfAdjustDate.setVisible(true);		
        this.pktransfAdjustDate.setRequired(false);		
        this.pktransfAdjustDate.setEnabled(false);
        // prmtbuilding		
        this.prmtbuilding.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingQuery");		
        this.prmtbuilding.setVisible(true);		
        this.prmtbuilding.setEditable(true);		
        this.prmtbuilding.setDisplayFormat("$name$");		
        this.prmtbuilding.setEditFormat("$number$");		
        this.prmtbuilding.setCommitFormat("$number$");		
        this.prmtbuilding.setRequired(false);
        // prmtcollectFunction		
        this.prmtcollectFunction.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CollectionQuery");		
        this.prmtcollectFunction.setVisible(true);		
        this.prmtcollectFunction.setEditable(true);		
        this.prmtcollectFunction.setDisplayFormat("$number$");		
        this.prmtcollectFunction.setEditFormat("$number$");		
        this.prmtcollectFunction.setCommitFormat("$number$");		
        this.prmtcollectFunction.setRequired(true);
        // btnCalculate
        this.btnCalculate.setAction((IItemAction)ActionProxyFactory.getProxy(actinoCalculate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCalculate.setText(resHelper.getString("btnCalculate.text"));		
        this.btnCalculate.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_compute"));
        // btnTransfTo
        this.btnTransfTo.setAction((IItemAction)ActionProxyFactory.getProxy(actionTransfTo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTransfTo.setText(resHelper.getString("btnTransfTo.text"));		
        this.btnTransfTo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_impress"));
        // menuCalculate
        this.menuCalculate.setAction((IItemAction)ActionProxyFactory.getProxy(actinoCalculate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuCalculate.setText(resHelper.getString("menuCalculate.text"));		
        this.menuCalculate.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_compute"));
        // menuTransfTo
        this.menuTransfTo.setAction((IItemAction)ActionProxyFactory.getProxy(actionTransfTo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuTransfTo.setText(resHelper.getString("menuTransfTo.text"));		
        this.menuTransfTo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_impress"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtmoneyDefine,prmtsellProject,pktransfAdjustDate,prmtbuilding,prmtcollectFunction,pkBizDate,txtNumber,kdtEntrys}));
        this.setFocusCycleRoot(true);
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
        this.setBounds(new Rectangle(0, 0, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 629));
        contNumber.setBounds(new Rectangle(14, 6, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(14, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(14, 54, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(14, 54, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtEntrys.setBounds(new Rectangle(12, 111, 990, 506));
        kdtEntrys_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtEntrys,new com.kingdee.eas.fdc.basecrm.SubstituteAdjustEntryInfo(),null,false);
        this.add(kdtEntrys_detailPanel, new KDLayout.Constraints(12, 111, 990, 506, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
		kdtEntrys_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
			public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
				IObjectValue vo = event.getObjectValue();
vo.put("relateBizType","PreOrder");
			}
			public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
			}
		});
        contmoneyDefine.setBounds(new Rectangle(372, 6, 270, 19));
        this.add(contmoneyDefine, new KDLayout.Constraints(372, 6, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP));
        contsellProject.setBounds(new Rectangle(14, 30, 270, 19));
        this.add(contsellProject, new KDLayout.Constraints(14, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conttransfAdjustDate.setBounds(new Rectangle(730, 54, 270, 19));
        this.add(conttransfAdjustDate, new KDLayout.Constraints(730, 54, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contbuilding.setBounds(new Rectangle(372, 30, 270, 19));
        this.add(contbuilding, new KDLayout.Constraints(372, 30, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP));
        contcollectFunction.setBounds(new Rectangle(372, 54, 270, 19));
        this.add(contcollectFunction, new KDLayout.Constraints(372, 54, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP));
        btnChooseRoom.setBounds(new Rectangle(807, 82, 95, 22));
        this.add(btnChooseRoom, new KDLayout.Constraints(807, 82, 95, 22, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnDeleteRoom.setBounds(new Rectangle(906, 82, 95, 22));
        this.add(btnDeleteRoom, new KDLayout.Constraints(906, 82, 95, 22, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contmoneyDefine
        contmoneyDefine.setBoundEditor(prmtmoneyDefine);
        //contsellProject
        contsellProject.setBoundEditor(prmtsellProject);
        //conttransfAdjustDate
        conttransfAdjustDate.setBoundEditor(pktransfAdjustDate);
        //contbuilding
        contbuilding.setBoundEditor(prmtbuilding);
        //contcollectFunction
        contcollectFunction.setBoundEditor(prmtcollectFunction);

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
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuCalculate);
        menuBiz.add(menuTransfTo);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
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
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnCalculate);
        this.toolBar.add(btnTransfTo);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.basecrm.SubstituteAdjustEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.relateBizType", com.kingdee.util.enums.Enum.class, this.kdtEntrys, "relateBizType.text");
		dataBinder.registerBinding("entrys.relateBizNumber", String.class, this.kdtEntrys, "relateBizNumber.text");
		dataBinder.registerBinding("entrys.relateBizEntryId", String.class, this.kdtEntrys, "relateBizEntryId.text");
		dataBinder.registerBinding("entrys.room", java.lang.Object.class, this.kdtEntrys, "room.text");
		dataBinder.registerBinding("entrys.bizDate", java.util.Date.class, this.kdtEntrys, "bizDate.text");
		dataBinder.registerBinding("entrys.contactAmount", java.math.BigDecimal.class, this.kdtEntrys, "contactAmount.text");
		dataBinder.registerBinding("entrys.payAmount", java.math.BigDecimal.class, this.kdtEntrys, "payAmount.text");
		dataBinder.registerBinding("entrys.customer", String.class, this.kdtEntrys, "customer.text");
		dataBinder.registerBinding("entrys.relateBizId", String.class, this.kdtEntrys, "relateBizId.text");
		dataBinder.registerBinding("entrys.building", java.lang.Object.class, this.kdtEntrys, "building.text");
		dataBinder.registerBinding("entrys.newPayAmount", java.math.BigDecimal.class, this.kdtEntrys, "newPayAmount.text");
		dataBinder.registerBinding("entrys.modifyType", String.class, this.kdtEntrys, "modifyType.text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("moneyDefine", com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo.class, this.prmtmoneyDefine, "data");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtsellProject, "data");
		dataBinder.registerBinding("transfAdjustDate", java.util.Date.class, this.pktransfAdjustDate, "value");
		dataBinder.registerBinding("building", com.kingdee.eas.fdc.sellhouse.BuildingInfo.class, this.prmtbuilding, "data");
		dataBinder.registerBinding("collectFunction", com.kingdee.eas.fdc.sellhouse.CollectionInfo.class, this.prmtcollectFunction, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basecrm.app.SubstituteAdjustEditUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.prmtmoneyDefine.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.basecrm.SubstituteAdjustInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"Sale",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Sale");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("Sale");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer oufip=(com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer)com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
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
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.relateBizType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.relateBizNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.relateBizEntryId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.room", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.contactAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.payAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.customer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.relateBizId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.building", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.newPayAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.modifyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("moneyDefine", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("transfAdjustDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("building", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("collectFunction", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output kdtEntrys_tableClicked method
     */
    protected void kdtEntrys_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output btnChooseRoom_actionPerformed method
     */
    protected void btnChooseRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDeleteRoom_actionPerformed method
     */
    protected void btnDeleteRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
    sic.add(new SelectorItemInfo("entrys.id"));
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
    sic.add(new SelectorItemInfo("entrys.relateBizType"));
    sic.add(new SelectorItemInfo("entrys.relateBizNumber"));
    sic.add(new SelectorItemInfo("entrys.relateBizEntryId"));
        sic.add(new SelectorItemInfo("entrys.room.*"));
//        sic.add(new SelectorItemInfo("entrys.room.number"));
    sic.add(new SelectorItemInfo("entrys.bizDate"));
    sic.add(new SelectorItemInfo("entrys.contactAmount"));
    sic.add(new SelectorItemInfo("entrys.payAmount"));
    sic.add(new SelectorItemInfo("entrys.customer"));
    sic.add(new SelectorItemInfo("entrys.relateBizId"));
        sic.add(new SelectorItemInfo("entrys.building.*"));
//        sic.add(new SelectorItemInfo("entrys.building.number"));
    sic.add(new SelectorItemInfo("entrys.newPayAmount"));
    sic.add(new SelectorItemInfo("entrys.modifyType"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("moneyDefine.*"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("transfAdjustDate"));
        sic.add(new SelectorItemInfo("building.*"));
        sic.add(new SelectorItemInfo("collectFunction.*"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionCalculate_actionPerformed method
     */
    public void actionCalculate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTransfTo_actionPerformed method
     */
    public void actionTransfTo_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrint(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrintPreview(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }
	public RequestContext prepareActionCalculate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCalculate() {
    	return false;
    }
	public RequestContext prepareActionTransfTo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTransfTo() {
    	return false;
    }

    /**
     * output ActionCalculate class
     */     
    protected class ActionCalculate extends ItemAction {     
    
        public ActionCalculate()
        {
            this(null);
        }

        public ActionCalculate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.setDaemonRun(true);
            _tempStr = resHelper.getString("ActionCalculate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalculate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalculate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSubstituteAdjustEditUI.this, "ActionCalculate", "actionCalculate_actionPerformed", e);
        }
    }

    /**
     * output ActionTransfTo class
     */     
    protected class ActionTransfTo extends ItemAction {     
    
        public ActionTransfTo()
        {
            this(null);
        }

        public ActionTransfTo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.setDaemonRun(true);
            _tempStr = resHelper.getString("ActionTransfTo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTransfTo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTransfTo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSubstituteAdjustEditUI.this, "ActionTransfTo", "actionTransfTo_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basecrm.client", "SubstituteAdjustEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.basecrm.client.SubstituteAdjustEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.basecrm.SubstituteAdjustFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.basecrm.SubstituteAdjustInfo objectValue = new com.kingdee.eas.fdc.basecrm.SubstituteAdjustInfo();
				if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Sale")) != null && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Sale")).getBoolean("isBizUnit"))
			objectValue.put("saleOrgUnit",com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Sale")));
 
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/basecrm/SubstituteAdjust";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.basecrm.app.SubstituteAdjustQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntrys;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}