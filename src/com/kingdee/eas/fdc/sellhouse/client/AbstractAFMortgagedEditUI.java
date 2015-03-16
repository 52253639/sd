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
public abstract class AbstractAFMortgagedEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAFMortgagedEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDPanel ApproachPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel DataPanel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combommType;
    protected com.kingdee.bos.ctrl.swing.KDContainer conApproachEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtApproachEntries;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnApproachDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnApproachAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnApproachInsertRow;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtDataEntries;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDataDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDataAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDataInsertRow;
    protected com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo editData = null;
    protected ActionApproachAddRow actionApproachAddRow = null;
    protected ActionApproachInsertRow actionApproachInsertRow = null;
    protected ActionApproachDeleteRow actionApproachDeleteRow = null;
    protected ActionDataAddRow actionDataAddRow = null;
    protected ActionDataInsertRow actionDataInsertRow = null;
    protected ActionDataDeleteRow actionDataDeleteRow = null;
    /**
     * output class constructor
     */
    public AbstractAFMortgagedEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAFMortgagedEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionApproachAddRow
        this.actionApproachAddRow = new ActionApproachAddRow(this);
        getActionManager().registerAction("actionApproachAddRow", actionApproachAddRow);
         this.actionApproachAddRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionApproachInsertRow
        this.actionApproachInsertRow = new ActionApproachInsertRow(this);
        getActionManager().registerAction("actionApproachInsertRow", actionApproachInsertRow);
         this.actionApproachInsertRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionApproachDeleteRow
        this.actionApproachDeleteRow = new ActionApproachDeleteRow(this);
        getActionManager().registerAction("actionApproachDeleteRow", actionApproachDeleteRow);
         this.actionApproachDeleteRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDataAddRow
        this.actionDataAddRow = new ActionDataAddRow(this);
        getActionManager().registerAction("actionDataAddRow", actionDataAddRow);
         this.actionDataAddRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDataInsertRow
        this.actionDataInsertRow = new ActionDataInsertRow(this);
        getActionManager().registerAction("actionDataInsertRow", actionDataInsertRow);
         this.actionDataInsertRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDataDeleteRow
        this.actionDataDeleteRow = new ActionDataDeleteRow(this);
        getActionManager().registerAction("actionDataDeleteRow", actionDataDeleteRow);
         this.actionDataDeleteRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.ApproachPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.DataPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.combommType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.conApproachEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtApproachEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnApproachDeleteRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnApproachAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnApproachInsertRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtDataEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnDataDeleteRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDataAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDataInsertRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.ApproachPanel.setName("ApproachPanel");
        this.DataPanel.setName("DataPanel");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.combommType.setName("combommType");
        this.conApproachEntry.setName("conApproachEntry");
        this.kdtApproachEntries.setName("kdtApproachEntries");
        this.btnApproachDeleteRow.setName("btnApproachDeleteRow");
        this.btnApproachAddRow.setName("btnApproachAddRow");
        this.btnApproachInsertRow.setName("btnApproachInsertRow");
        this.kDContainer1.setName("kDContainer1");
        this.kdtDataEntries.setName("kdtDataEntries");
        this.btnDataDeleteRow.setName("btnDataDeleteRow");
        this.btnDataAddRow.setName("btnDataAddRow");
        this.btnDataInsertRow.setName("btnDataInsertRow");
        // CoreUI		
        this.menuBar.setVisible(false);		
        this.btnPageSetup.setEnabled(false);		
        this.btnSave.setEnabled(false);		
        this.btnSave.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnCopy.setEnabled(false);		
        this.btnCancelCancel.setEnabled(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setEnabled(false);		
        this.btnCancel.setVisible(false);		
        this.btnFirst.setEnabled(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setEnabled(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setEnabled(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setEnabled(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrint.setEnabled(false);		
        this.btnPrintPreview.setVisible(false);		
        this.btnPrintPreview.setEnabled(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.btnAttachment.setEnabled(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // ApproachPanel
        // DataPanel
        // txtNumber		
        this.txtNumber.setMaxLength(30);		
        this.txtNumber.setRequired(true);
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setMaxLength(30);
        // combommType		
        this.combommType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.AFMmmTypeEnum").toArray());		
        this.combommType.setRequired(true);
        // conApproachEntry		
        this.conApproachEntry.setTitle(resHelper.getString("conApproachEntry.title"));
        // kdtApproachEntries
		String kdtApproachEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"refDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"intervalMon\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"intervalDay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appointedDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isFinish\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"head\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{refDate}</t:Cell><t:Cell>$Resource{intervalMon}</t:Cell><t:Cell>$Resource{intervalDay}</t:Cell><t:Cell>$Resource{appointedDate}</t:Cell><t:Cell>$Resource{isFinish}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtApproachEntries.setFormatXml(resHelper.translateString("kdtApproachEntries",kdtApproachEntriesStrXML));

                this.kdtApproachEntries.putBindContents("editData",new String[] {"name","remark","referenceTime","intervalMonth","intervalDays","scheduledDate","isFinish","id","number"});


        this.kdtApproachEntries.checkParsed();
        KDTextField kdtApproachEntries_name_TextField = new KDTextField();
        kdtApproachEntries_name_TextField.setName("kdtApproachEntries_name_TextField");
        kdtApproachEntries_name_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtApproachEntries_name_CellEditor = new KDTDefaultCellEditor(kdtApproachEntries_name_TextField);
        this.kdtApproachEntries.getColumn("name").setEditor(kdtApproachEntries_name_CellEditor);
        KDTextField kdtApproachEntries_remark_TextField = new KDTextField();
        kdtApproachEntries_remark_TextField.setName("kdtApproachEntries_remark_TextField");
        kdtApproachEntries_remark_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtApproachEntries_remark_CellEditor = new KDTDefaultCellEditor(kdtApproachEntries_remark_TextField);
        this.kdtApproachEntries.getColumn("remark").setEditor(kdtApproachEntries_remark_CellEditor);
        KDTextField kdtApproachEntries_refDate_TextField = new KDTextField();
        kdtApproachEntries_refDate_TextField.setName("kdtApproachEntries_refDate_TextField");
        kdtApproachEntries_refDate_TextField.setMaxLength(300);
        KDTDefaultCellEditor kdtApproachEntries_refDate_CellEditor = new KDTDefaultCellEditor(kdtApproachEntries_refDate_TextField);
        this.kdtApproachEntries.getColumn("refDate").setEditor(kdtApproachEntries_refDate_CellEditor);
        KDFormattedTextField kdtApproachEntries_intervalMon_TextField = new KDFormattedTextField();
        kdtApproachEntries_intervalMon_TextField.setName("kdtApproachEntries_intervalMon_TextField");
        kdtApproachEntries_intervalMon_TextField.setVisible(true);
        kdtApproachEntries_intervalMon_TextField.setEditable(true);
        kdtApproachEntries_intervalMon_TextField.setHorizontalAlignment(2);
        kdtApproachEntries_intervalMon_TextField.setDataType(0);
        KDTDefaultCellEditor kdtApproachEntries_intervalMon_CellEditor = new KDTDefaultCellEditor(kdtApproachEntries_intervalMon_TextField);
        this.kdtApproachEntries.getColumn("intervalMon").setEditor(kdtApproachEntries_intervalMon_CellEditor);
        KDFormattedTextField kdtApproachEntries_intervalDay_TextField = new KDFormattedTextField();
        kdtApproachEntries_intervalDay_TextField.setName("kdtApproachEntries_intervalDay_TextField");
        kdtApproachEntries_intervalDay_TextField.setVisible(true);
        kdtApproachEntries_intervalDay_TextField.setEditable(true);
        kdtApproachEntries_intervalDay_TextField.setHorizontalAlignment(2);
        kdtApproachEntries_intervalDay_TextField.setDataType(0);
        KDTDefaultCellEditor kdtApproachEntries_intervalDay_CellEditor = new KDTDefaultCellEditor(kdtApproachEntries_intervalDay_TextField);
        this.kdtApproachEntries.getColumn("intervalDay").setEditor(kdtApproachEntries_intervalDay_CellEditor);
        KDDatePicker kdtApproachEntries_appointedDate_DatePicker = new KDDatePicker();
        kdtApproachEntries_appointedDate_DatePicker.setName("kdtApproachEntries_appointedDate_DatePicker");
        kdtApproachEntries_appointedDate_DatePicker.setVisible(true);
        kdtApproachEntries_appointedDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtApproachEntries_appointedDate_CellEditor = new KDTDefaultCellEditor(kdtApproachEntries_appointedDate_DatePicker);
        this.kdtApproachEntries.getColumn("appointedDate").setEditor(kdtApproachEntries_appointedDate_CellEditor);
        KDCheckBox kdtApproachEntries_isFinish_CheckBox = new KDCheckBox();
        kdtApproachEntries_isFinish_CheckBox.setName("kdtApproachEntries_isFinish_CheckBox");
        KDTDefaultCellEditor kdtApproachEntries_isFinish_CellEditor = new KDTDefaultCellEditor(kdtApproachEntries_isFinish_CheckBox);
        this.kdtApproachEntries.getColumn("isFinish").setEditor(kdtApproachEntries_isFinish_CellEditor);
        KDTextField kdtApproachEntries_number_TextField = new KDTextField();
        kdtApproachEntries_number_TextField.setName("kdtApproachEntries_number_TextField");
        kdtApproachEntries_number_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtApproachEntries_number_CellEditor = new KDTDefaultCellEditor(kdtApproachEntries_number_TextField);
        this.kdtApproachEntries.getColumn("number").setEditor(kdtApproachEntries_number_CellEditor);
        // btnApproachDeleteRow
        this.btnApproachDeleteRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionApproachDeleteRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnApproachDeleteRow.setText(resHelper.getString("btnApproachDeleteRow.text"));
        // btnApproachAddRow
        this.btnApproachAddRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionApproachAddRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnApproachAddRow.setText(resHelper.getString("btnApproachAddRow.text"));
        this.btnApproachAddRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnApproachAddRow_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnApproachInsertRow
        this.btnApproachInsertRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionApproachInsertRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnApproachInsertRow.setText(resHelper.getString("btnApproachInsertRow.text"));
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kdtDataEntries
		String kdtDataEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"head\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{number}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtDataEntries.setFormatXml(resHelper.translateString("kdtDataEntries",kdtDataEntriesStrXML));

                this.kdtDataEntries.putBindContents("editData",new String[] {"id","name","remark","number"});


        this.kdtDataEntries.checkParsed();
        KDTextField kdtDataEntries_name_TextField = new KDTextField();
        kdtDataEntries_name_TextField.setName("kdtDataEntries_name_TextField");
        kdtDataEntries_name_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtDataEntries_name_CellEditor = new KDTDefaultCellEditor(kdtDataEntries_name_TextField);
        this.kdtDataEntries.getColumn("name").setEditor(kdtDataEntries_name_CellEditor);
        KDTextField kdtDataEntries_remark_TextField = new KDTextField();
        kdtDataEntries_remark_TextField.setName("kdtDataEntries_remark_TextField");
        kdtDataEntries_remark_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtDataEntries_remark_CellEditor = new KDTDefaultCellEditor(kdtDataEntries_remark_TextField);
        this.kdtDataEntries.getColumn("remark").setEditor(kdtDataEntries_remark_CellEditor);
        KDTextField kdtDataEntries_number_TextField = new KDTextField();
        kdtDataEntries_number_TextField.setName("kdtDataEntries_number_TextField");
        kdtDataEntries_number_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtDataEntries_number_CellEditor = new KDTDefaultCellEditor(kdtDataEntries_number_TextField);
        this.kdtDataEntries.getColumn("number").setEditor(kdtDataEntries_number_CellEditor);
        // btnDataDeleteRow
        this.btnDataDeleteRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionDataDeleteRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDataDeleteRow.setText(resHelper.getString("btnDataDeleteRow.text"));
        // btnDataAddRow
        this.btnDataAddRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionDataAddRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDataAddRow.setText(resHelper.getString("btnDataAddRow.text"));
        this.btnDataAddRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDataAddRow_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDataInsertRow
        this.btnDataInsertRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionDataInsertRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDataInsertRow.setText(resHelper.getString("btnDataInsertRow.text"));
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
        this.setBounds(new Rectangle(0, 0, 600, 520));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(314, 10, 270, 19));
        this.add(kDLabelContainer2, null);
        kDLabelContainer6.setBounds(new Rectangle(10, 37, 270, 19));
        this.add(kDLabelContainer6, null);
        ApproachPanel.setBounds(new Rectangle(10, 87, 580, 202));
        this.add(ApproachPanel, null);
        DataPanel.setBounds(new Rectangle(10, 301, 580, 202));
        this.add(DataPanel, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtName);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(combommType);
        //ApproachPanel
        ApproachPanel.setLayout(null);        conApproachEntry.setBounds(new Rectangle(-1, 0, 115, 18));
        ApproachPanel.add(conApproachEntry, null);
        kdtApproachEntries.setBounds(new Rectangle(-1, 28, 580, 173));
        ApproachPanel.add(kdtApproachEntries, null);
        btnApproachDeleteRow.setBounds(new Rectangle(489, 0, 76, 18));
        ApproachPanel.add(btnApproachDeleteRow, null);
        btnApproachAddRow.setBounds(new Rectangle(307, 0, 76, 18));
        ApproachPanel.add(btnApproachAddRow, null);
        btnApproachInsertRow.setBounds(new Rectangle(399, 0, 76, 18));
        ApproachPanel.add(btnApproachInsertRow, null);
        conApproachEntry.getContentPane().setLayout(null);        //DataPanel
        DataPanel.setLayout(null);        kDContainer1.setBounds(new Rectangle(-1, 0, 113, 18));
        DataPanel.add(kDContainer1, null);
        kdtDataEntries.setBounds(new Rectangle(-2, 28, 580, 173));
        DataPanel.add(kdtDataEntries, null);
        btnDataDeleteRow.setBounds(new Rectangle(489, 0, 76, 18));
        DataPanel.add(btnDataDeleteRow, null);
        btnDataAddRow.setBounds(new Rectangle(307, 0, 76, 18));
        DataPanel.add(btnDataAddRow, null);
        btnDataInsertRow.setBounds(new Rectangle(399, 0, 76, 18));
        DataPanel.add(btnDataInsertRow, null);
        kDContainer1.getContentPane().setLayout(null);
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
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
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
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("mmType", com.kingdee.eas.fdc.sellhouse.AFMmmTypeEnum.class, this.combommType, "selectedItem");
		dataBinder.registerBinding("ApproachEntrys.name", String.class, this.kdtApproachEntries, "name.text");
		dataBinder.registerBinding("ApproachEntrys.remark", String.class, this.kdtApproachEntries, "remark.text");
		dataBinder.registerBinding("ApproachEntrys.referenceTime", com.kingdee.eas.fdc.sellhouse.ReferenceTimeEnum.class, this.kdtApproachEntries, "refDate.text");
		dataBinder.registerBinding("ApproachEntrys.intervalMonth", int.class, this.kdtApproachEntries, "intervalMon.text");
		dataBinder.registerBinding("ApproachEntrys.intervalDays", int.class, this.kdtApproachEntries, "intervalDay.text");
		dataBinder.registerBinding("ApproachEntrys.scheduledDate", java.util.Date.class, this.kdtApproachEntries, "appointedDate.text");
		dataBinder.registerBinding("ApproachEntrys.isFinish", boolean.class, this.kdtApproachEntries, "isFinish.text");
		dataBinder.registerBinding("ApproachEntrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtApproachEntries, "id.text");
		dataBinder.registerBinding("ApproachEntrys", com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryInfo.class, this.kdtApproachEntries, "userObject");
		dataBinder.registerBinding("ApproachEntrys.number", String.class, this.kdtApproachEntries, "number.text");
		dataBinder.registerBinding("DataEntrys.name", String.class, this.kdtDataEntries, "name.text");
		dataBinder.registerBinding("DataEntrys.remark", String.class, this.kdtDataEntries, "remark.text");
		dataBinder.registerBinding("DataEntrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtDataEntries, "id.text");
		dataBinder.registerBinding("DataEntrys", com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryInfo.class, this.kdtDataEntries, "userObject");
		dataBinder.registerBinding("DataEntrys.number", String.class, this.kdtDataEntries, "number.text");		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtNumber, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtNumber, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.AFMortgagedEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"NONE",editData.getString("number"));
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

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("NONE");
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mmType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ApproachEntrys.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ApproachEntrys.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ApproachEntrys.referenceTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ApproachEntrys.intervalMonth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ApproachEntrys.intervalDays", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ApproachEntrys.scheduledDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ApproachEntrys.isFinish", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ApproachEntrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ApproachEntrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ApproachEntrys.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("DataEntrys.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("DataEntrys.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("DataEntrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("DataEntrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("DataEntrys.number", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtNumber.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtNumber.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(false);
		            this.txtNumber.setEnabled(false);
        }
    }

    /**
     * output btnApproachAddRow_actionPerformed method
     */
    protected void btnApproachAddRow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDataAddRow_actionPerformed method
     */
    protected void btnDataAddRow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("mmType"));
    sic.add(new SelectorItemInfo("ApproachEntrys.name"));
    sic.add(new SelectorItemInfo("ApproachEntrys.remark"));
    sic.add(new SelectorItemInfo("ApproachEntrys.referenceTime"));
    sic.add(new SelectorItemInfo("ApproachEntrys.intervalMonth"));
    sic.add(new SelectorItemInfo("ApproachEntrys.intervalDays"));
    sic.add(new SelectorItemInfo("ApproachEntrys.scheduledDate"));
    sic.add(new SelectorItemInfo("ApproachEntrys.isFinish"));
    sic.add(new SelectorItemInfo("ApproachEntrys.id"));
        sic.add(new SelectorItemInfo("ApproachEntrys.*"));
//        sic.add(new SelectorItemInfo("ApproachEntrys.number"));
    sic.add(new SelectorItemInfo("ApproachEntrys.number"));
    sic.add(new SelectorItemInfo("DataEntrys.name"));
    sic.add(new SelectorItemInfo("DataEntrys.remark"));
    sic.add(new SelectorItemInfo("DataEntrys.id"));
        sic.add(new SelectorItemInfo("DataEntrys.*"));
//        sic.add(new SelectorItemInfo("DataEntrys.number"));
    sic.add(new SelectorItemInfo("DataEntrys.number"));
        return sic;
    }        
    	

    /**
     * output actionApproachAddRow_actionPerformed method
     */
    public void actionApproachAddRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionApproachInsertRow_actionPerformed method
     */
    public void actionApproachInsertRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionApproachDeleteRow_actionPerformed method
     */
    public void actionApproachDeleteRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDataAddRow_actionPerformed method
     */
    public void actionDataAddRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDataInsertRow_actionPerformed method
     */
    public void actionDataInsertRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDataDeleteRow_actionPerformed method
     */
    public void actionDataDeleteRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionApproachAddRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionApproachAddRow() {
    	return false;
    }
	public RequestContext prepareActionApproachInsertRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionApproachInsertRow() {
    	return false;
    }
	public RequestContext prepareActionApproachDeleteRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionApproachDeleteRow() {
    	return false;
    }
	public RequestContext prepareActionDataAddRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDataAddRow() {
    	return false;
    }
	public RequestContext prepareActionDataInsertRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDataInsertRow() {
    	return false;
    }
	public RequestContext prepareActionDataDeleteRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDataDeleteRow() {
    	return false;
    }

    /**
     * output ActionApproachAddRow class
     */     
    protected class ActionApproachAddRow extends ItemAction {     
    
        public ActionApproachAddRow()
        {
            this(null);
        }

        public ActionApproachAddRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionApproachAddRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApproachAddRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApproachAddRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAFMortgagedEditUI.this, "ActionApproachAddRow", "actionApproachAddRow_actionPerformed", e);
        }
    }

    /**
     * output ActionApproachInsertRow class
     */     
    protected class ActionApproachInsertRow extends ItemAction {     
    
        public ActionApproachInsertRow()
        {
            this(null);
        }

        public ActionApproachInsertRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionApproachInsertRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApproachInsertRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApproachInsertRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAFMortgagedEditUI.this, "ActionApproachInsertRow", "actionApproachInsertRow_actionPerformed", e);
        }
    }

    /**
     * output ActionApproachDeleteRow class
     */     
    protected class ActionApproachDeleteRow extends ItemAction {     
    
        public ActionApproachDeleteRow()
        {
            this(null);
        }

        public ActionApproachDeleteRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionApproachDeleteRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApproachDeleteRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApproachDeleteRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAFMortgagedEditUI.this, "ActionApproachDeleteRow", "actionApproachDeleteRow_actionPerformed", e);
        }
    }

    /**
     * output ActionDataAddRow class
     */     
    protected class ActionDataAddRow extends ItemAction {     
    
        public ActionDataAddRow()
        {
            this(null);
        }

        public ActionDataAddRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDataAddRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDataAddRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDataAddRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAFMortgagedEditUI.this, "ActionDataAddRow", "actionDataAddRow_actionPerformed", e);
        }
    }

    /**
     * output ActionDataInsertRow class
     */     
    protected class ActionDataInsertRow extends ItemAction {     
    
        public ActionDataInsertRow()
        {
            this(null);
        }

        public ActionDataInsertRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDataInsertRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDataInsertRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDataInsertRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAFMortgagedEditUI.this, "ActionDataInsertRow", "actionDataInsertRow_actionPerformed", e);
        }
    }

    /**
     * output ActionDataDeleteRow class
     */     
    protected class ActionDataDeleteRow extends ItemAction {     
    
        public ActionDataDeleteRow()
        {
            this(null);
        }

        public ActionDataDeleteRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDataDeleteRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDataDeleteRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDataDeleteRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractAFMortgagedEditUI.this, "ActionDataDeleteRow", "actionDataDeleteRow_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "AFMortgagedEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.sellhouse.client.AFMortgagedEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.sellhouse.AFMortgagedFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo objectValue = new com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtApproachEntries;
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