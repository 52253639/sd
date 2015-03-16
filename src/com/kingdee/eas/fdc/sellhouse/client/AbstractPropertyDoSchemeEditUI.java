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
public abstract class AbstractPropertyDoSchemeEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPropertyDoSchemeEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField remarkField;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtApprEntries;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnApproachDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnApproachAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnApproachInsertRow;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtDataEntries;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDataDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDataAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDataInsertRow;
    protected com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo editData = null;
    protected ActionApprAddRow actionApprAddRow = null;
    protected ActionApprInsertRow actionApprInsertRow = null;
    protected ActionApprDeleteRow actionApprDeleteRow = null;
    protected ActionDataAddRow actionDataAddRow = null;
    protected ActionDataInsertRow actionDataInsertRow = null;
    protected ActionDataDeleteRow actionDataDeleteRow = null;
    /**
     * output class constructor
     */
    public AbstractPropertyDoSchemeEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPropertyDoSchemeEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionApprAddRow
        this.actionApprAddRow = new ActionApprAddRow(this);
        getActionManager().registerAction("actionApprAddRow", actionApprAddRow);
         this.actionApprAddRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionApprInsertRow
        this.actionApprInsertRow = new ActionApprInsertRow(this);
        getActionManager().registerAction("actionApprInsertRow", actionApprInsertRow);
         this.actionApprInsertRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionApprDeleteRow
        this.actionApprDeleteRow = new ActionApprDeleteRow(this);
        getActionManager().registerAction("actionApprDeleteRow", actionApprDeleteRow);
         this.actionApprDeleteRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
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
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.remarkField = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtApprEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnApproachDeleteRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnApproachAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnApproachInsertRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtDataEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnDataDeleteRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDataAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDataInsertRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.remarkField.setName("remarkField");
        this.kDContainer1.setName("kDContainer1");
        this.kdtApprEntries.setName("kdtApprEntries");
        this.btnApproachDeleteRow.setName("btnApproachDeleteRow");
        this.btnApproachAddRow.setName("btnApproachAddRow");
        this.btnApproachInsertRow.setName("btnApproachInsertRow");
        this.kDContainer2.setName("kDContainer2");
        this.kdtDataEntries.setName("kdtDataEntries");
        this.btnDataDeleteRow.setName("btnDataDeleteRow");
        this.btnDataAddRow.setName("btnDataAddRow");
        this.btnDataInsertRow.setName("btnDataInsertRow");
        // CoreUI		
        this.menuBar.setVisible(false);		
        this.btnSave.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(80);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.setVisible(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(80);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelAlignment(7);		
        this.kDLabelContainer2.setVisible(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(80);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDPanel1
        // kDPanel2
        // txtName		
        this.txtName.setVisible(true);		
        this.txtName.setEnabled(true);		
        this.txtName.setRequired(true);		
        this.txtName.setMaxLength(30);
        // txtNumber		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(30);
        // remarkField		
        this.remarkField.setMaxLength(100);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kdtApprEntries
		String kdtApprEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"refDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"intervalMon\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"intervalDay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appointedDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isFinish\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"head\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{refDate}</t:Cell><t:Cell>$Resource{intervalMon}</t:Cell><t:Cell>$Resource{intervalDay}</t:Cell><t:Cell>$Resource{appointedDate}</t:Cell><t:Cell>$Resource{isFinish}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtApprEntries.setFormatXml(resHelper.translateString("kdtApprEntries",kdtApprEntriesStrXML));

                this.kdtApprEntries.putBindContents("editData",new String[] {"name","description","referenceTime","intervalMonth","intervalDays","scheduledDate","isFinish","id","number"});


        this.kdtApprEntries.checkParsed();
        KDTextField kdtApprEntries_name_TextField = new KDTextField();
        kdtApprEntries_name_TextField.setName("kdtApprEntries_name_TextField");
        kdtApprEntries_name_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtApprEntries_name_CellEditor = new KDTDefaultCellEditor(kdtApprEntries_name_TextField);
        this.kdtApprEntries.getColumn("name").setEditor(kdtApprEntries_name_CellEditor);
        KDTextField kdtApprEntries_remark_TextField = new KDTextField();
        kdtApprEntries_remark_TextField.setName("kdtApprEntries_remark_TextField");
        kdtApprEntries_remark_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtApprEntries_remark_CellEditor = new KDTDefaultCellEditor(kdtApprEntries_remark_TextField);
        this.kdtApprEntries.getColumn("remark").setEditor(kdtApprEntries_remark_CellEditor);
        KDTextField kdtApprEntries_refDate_TextField = new KDTextField();
        kdtApprEntries_refDate_TextField.setName("kdtApprEntries_refDate_TextField");
        kdtApprEntries_refDate_TextField.setMaxLength(500);
        KDTDefaultCellEditor kdtApprEntries_refDate_CellEditor = new KDTDefaultCellEditor(kdtApprEntries_refDate_TextField);
        this.kdtApprEntries.getColumn("refDate").setEditor(kdtApprEntries_refDate_CellEditor);
        KDFormattedTextField kdtApprEntries_intervalMon_TextField = new KDFormattedTextField();
        kdtApprEntries_intervalMon_TextField.setName("kdtApprEntries_intervalMon_TextField");
        kdtApprEntries_intervalMon_TextField.setVisible(true);
        kdtApprEntries_intervalMon_TextField.setEditable(true);
        kdtApprEntries_intervalMon_TextField.setHorizontalAlignment(2);
        kdtApprEntries_intervalMon_TextField.setDataType(0);
        KDTDefaultCellEditor kdtApprEntries_intervalMon_CellEditor = new KDTDefaultCellEditor(kdtApprEntries_intervalMon_TextField);
        this.kdtApprEntries.getColumn("intervalMon").setEditor(kdtApprEntries_intervalMon_CellEditor);
        KDFormattedTextField kdtApprEntries_intervalDay_TextField = new KDFormattedTextField();
        kdtApprEntries_intervalDay_TextField.setName("kdtApprEntries_intervalDay_TextField");
        kdtApprEntries_intervalDay_TextField.setVisible(true);
        kdtApprEntries_intervalDay_TextField.setEditable(true);
        kdtApprEntries_intervalDay_TextField.setHorizontalAlignment(2);
        kdtApprEntries_intervalDay_TextField.setDataType(0);
        KDTDefaultCellEditor kdtApprEntries_intervalDay_CellEditor = new KDTDefaultCellEditor(kdtApprEntries_intervalDay_TextField);
        this.kdtApprEntries.getColumn("intervalDay").setEditor(kdtApprEntries_intervalDay_CellEditor);
        KDDatePicker kdtApprEntries_appointedDate_DatePicker = new KDDatePicker();
        kdtApprEntries_appointedDate_DatePicker.setName("kdtApprEntries_appointedDate_DatePicker");
        kdtApprEntries_appointedDate_DatePicker.setVisible(true);
        kdtApprEntries_appointedDate_DatePicker.setEditable(true);
        KDTDefaultCellEditor kdtApprEntries_appointedDate_CellEditor = new KDTDefaultCellEditor(kdtApprEntries_appointedDate_DatePicker);
        this.kdtApprEntries.getColumn("appointedDate").setEditor(kdtApprEntries_appointedDate_CellEditor);
        KDCheckBox kdtApprEntries_isFinish_CheckBox = new KDCheckBox();
        kdtApprEntries_isFinish_CheckBox.setName("kdtApprEntries_isFinish_CheckBox");
        KDTDefaultCellEditor kdtApprEntries_isFinish_CellEditor = new KDTDefaultCellEditor(kdtApprEntries_isFinish_CheckBox);
        this.kdtApprEntries.getColumn("isFinish").setEditor(kdtApprEntries_isFinish_CellEditor);
        KDTextField kdtApprEntries_number_TextField = new KDTextField();
        kdtApprEntries_number_TextField.setName("kdtApprEntries_number_TextField");
        kdtApprEntries_number_TextField.setMaxLength(30);
        KDTDefaultCellEditor kdtApprEntries_number_CellEditor = new KDTDefaultCellEditor(kdtApprEntries_number_TextField);
        this.kdtApprEntries.getColumn("number").setEditor(kdtApprEntries_number_CellEditor);
        // btnApproachDeleteRow
        this.btnApproachDeleteRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionApprDeleteRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnApproachDeleteRow.setText(resHelper.getString("btnApproachDeleteRow.text"));
        // btnApproachAddRow
        this.btnApproachAddRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionApprAddRow, new Class[] { IItemAction.class }, getServiceContext()));		
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
        this.btnApproachInsertRow.setAction((IItemAction)ActionProxyFactory.getProxy(actionApprInsertRow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnApproachInsertRow.setText(resHelper.getString("btnApproachInsertRow.text"));
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // kdtDataEntries
		String kdtDataEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"head\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{number}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtDataEntries.setFormatXml(resHelper.translateString("kdtDataEntries",kdtDataEntriesStrXML));

                this.kdtDataEntries.putBindContents("editData",new String[] {"id","name","description","number"});


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
        kdtDataEntries_number_TextField.setMaxLength(30);
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
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtNumber,txtName}));
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
        this.setBounds(new Rectangle(0, 0, 700, 510));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(415, 6, 230, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(10, 10, 230, 19));
        this.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(9, 43, 643, 19));
        this.add(kDLabelContainer3, null);
        kDPanel1.setBounds(new Rectangle(10, 78, 680, 202));
        this.add(kDPanel1, null);
        kDPanel2.setBounds(new Rectangle(10, 292, 680, 202));
        this.add(kDPanel2, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtName);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtNumber);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(remarkField);
        //kDPanel1
        kDPanel1.setLayout(null);        kDContainer1.setBounds(new Rectangle(-1, 0, 115, 18));
        kDPanel1.add(kDContainer1, null);
        kdtApprEntries.setBounds(new Rectangle(-2, 28, 680, 170));
        kDPanel1.add(kdtApprEntries, null);
        btnApproachDeleteRow.setBounds(new Rectangle(538, 0, 76, 18));
        kDPanel1.add(btnApproachDeleteRow, null);
        btnApproachAddRow.setBounds(new Rectangle(348, 0, 76, 18));
        kDPanel1.add(btnApproachAddRow, null);
        btnApproachInsertRow.setBounds(new Rectangle(443, 0, 76, 18));
        kDPanel1.add(btnApproachInsertRow, null);
        kDContainer1.getContentPane().setLayout(null);        //kDPanel2
        kDPanel2.setLayout(null);        kDContainer2.setBounds(new Rectangle(-1, 0, 113, 18));
        kDPanel2.add(kDContainer2, null);
        kdtDataEntries.setBounds(new Rectangle(-2, 28, 680, 173));
        kDPanel2.add(kdtDataEntries, null);
        btnDataDeleteRow.setBounds(new Rectangle(538, 0, 76, 18));
        kDPanel2.add(btnDataDeleteRow, null);
        btnDataAddRow.setBounds(new Rectangle(348, 0, 76, 18));
        kDPanel2.add(btnDataAddRow, null);
        btnDataInsertRow.setBounds(new Rectangle(443, 0, 76, 18));
        kDPanel2.add(btnDataInsertRow, null);
        kDContainer2.getContentPane().setLayout(null);
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
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.remarkField, "text");
		dataBinder.registerBinding("Entry", com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryInfo.class, this.kdtApprEntries, "userObject");
		dataBinder.registerBinding("Entry.name", String.class, this.kdtApprEntries, "name.text");
		dataBinder.registerBinding("Entry.description", String.class, this.kdtApprEntries, "remark.text");
		dataBinder.registerBinding("Entry.referenceTime", String.class, this.kdtApprEntries, "refDate.text");
		dataBinder.registerBinding("Entry.intervalMonth", int.class, this.kdtApprEntries, "intervalMon.text");
		dataBinder.registerBinding("Entry.intervalDays", int.class, this.kdtApprEntries, "intervalDay.text");
		dataBinder.registerBinding("Entry.scheduledDate", java.util.Date.class, this.kdtApprEntries, "appointedDate.text");
		dataBinder.registerBinding("Entry.isFinish", boolean.class, this.kdtApprEntries, "isFinish.text");
		dataBinder.registerBinding("Entry.id", com.kingdee.bos.util.BOSUuid.class, this.kdtApprEntries, "id.text");
		dataBinder.registerBinding("Entry.number", String.class, this.kdtApprEntries, "number.text");
		dataBinder.registerBinding("EntryTwo.name", String.class, this.kdtDataEntries, "name.text");
		dataBinder.registerBinding("EntryTwo.description", String.class, this.kdtDataEntries, "remark.text");
		dataBinder.registerBinding("EntryTwo.number", String.class, this.kdtDataEntries, "number.text");
		dataBinder.registerBinding("EntryTwo.id", com.kingdee.bos.util.BOSUuid.class, this.kdtDataEntries, "id.text");
		dataBinder.registerBinding("EntryTwo", com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryTwoInfo.class, this.kdtDataEntries, "userObject");		
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
	    return "com.kingdee.eas.fdc.sellhouse.app.PropertyDoSchemeEditUIHandler";
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
        this.txtNumber.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo)ov;
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
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.referenceTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.intervalMonth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.intervalDays", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.scheduledDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.isFinish", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entry.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryTwo.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryTwo.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryTwo.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryTwo.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EntryTwo", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("Entry.*"));
//        sic.add(new SelectorItemInfo("Entry.number"));
    sic.add(new SelectorItemInfo("Entry.name"));
    sic.add(new SelectorItemInfo("Entry.description"));
    sic.add(new SelectorItemInfo("Entry.referenceTime"));
    sic.add(new SelectorItemInfo("Entry.intervalMonth"));
    sic.add(new SelectorItemInfo("Entry.intervalDays"));
    sic.add(new SelectorItemInfo("Entry.scheduledDate"));
    sic.add(new SelectorItemInfo("Entry.isFinish"));
    sic.add(new SelectorItemInfo("Entry.id"));
    sic.add(new SelectorItemInfo("Entry.number"));
    sic.add(new SelectorItemInfo("EntryTwo.name"));
    sic.add(new SelectorItemInfo("EntryTwo.description"));
    sic.add(new SelectorItemInfo("EntryTwo.number"));
    sic.add(new SelectorItemInfo("EntryTwo.id"));
        sic.add(new SelectorItemInfo("EntryTwo.*"));
//        sic.add(new SelectorItemInfo("EntryTwo.number"));
        return sic;
    }        
    	

    /**
     * output actionApprAddRow_actionPerformed method
     */
    public void actionApprAddRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionApprInsertRow_actionPerformed method
     */
    public void actionApprInsertRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionApprDeleteRow_actionPerformed method
     */
    public void actionApprDeleteRow_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionApprAddRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionApprAddRow() {
    	return false;
    }
	public RequestContext prepareActionApprInsertRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionApprInsertRow() {
    	return false;
    }
	public RequestContext prepareActionApprDeleteRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionApprDeleteRow() {
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
     * output ActionApprAddRow class
     */     
    protected class ActionApprAddRow extends ItemAction {     
    
        public ActionApprAddRow()
        {
            this(null);
        }

        public ActionApprAddRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionApprAddRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApprAddRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApprAddRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPropertyDoSchemeEditUI.this, "ActionApprAddRow", "actionApprAddRow_actionPerformed", e);
        }
    }

    /**
     * output ActionApprInsertRow class
     */     
    protected class ActionApprInsertRow extends ItemAction {     
    
        public ActionApprInsertRow()
        {
            this(null);
        }

        public ActionApprInsertRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionApprInsertRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApprInsertRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApprInsertRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPropertyDoSchemeEditUI.this, "ActionApprInsertRow", "actionApprInsertRow_actionPerformed", e);
        }
    }

    /**
     * output ActionApprDeleteRow class
     */     
    protected class ActionApprDeleteRow extends ItemAction {     
    
        public ActionApprDeleteRow()
        {
            this(null);
        }

        public ActionApprDeleteRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionApprDeleteRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApprDeleteRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionApprDeleteRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPropertyDoSchemeEditUI.this, "ActionApprDeleteRow", "actionApprDeleteRow_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractPropertyDoSchemeEditUI.this, "ActionDataAddRow", "actionDataAddRow_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractPropertyDoSchemeEditUI.this, "ActionDataInsertRow", "actionDataInsertRow_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractPropertyDoSchemeEditUI.this, "ActionDataDeleteRow", "actionDataDeleteRow_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "PropertyDoSchemeEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.sellhouse.client.PropertyDoSchemeEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo objectValue = new com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtApprEntries;
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