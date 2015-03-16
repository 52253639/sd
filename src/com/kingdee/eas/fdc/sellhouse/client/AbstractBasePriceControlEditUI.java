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
public abstract class AbstractBasePriceControlEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBasePriceControlEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBaseType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilding;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAvgBuildingBasePrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMaxBuildingBasePrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMinBuildingBasePrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAvgRoomBasePrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMaxRoomBasePrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMinRoomBasePrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChooseRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportPrice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBaseType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuilding;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAvgBuildingBasePrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMaxBuildingBasePrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMinBuildingBasePrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAvgRoomBasePrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMaxRoomBasePrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMinRoomBasePrice;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExceuted;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnWorkFlow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCalc;
    protected com.kingdee.eas.fdc.sellhouse.BasePriceControlInfo editData = null;
    protected ActionAudit actionAudit = null;
    protected ActionExceuted actionExceuted = null;
    protected ActionWorkFlow actionWorkFlow = null;
    protected ActionCalc actionCalc = null;
    protected ActionChooseRoom actionChooseRoom = null;
    protected ActionImportPrice actionImportPrice = null;
    protected ActionDelRoom actionDelRoom = null;
    /**
     * output class constructor
     */
    public AbstractBasePriceControlEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBasePriceControlEditUI.class.getName());
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
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExceuted
        this.actionExceuted = new ActionExceuted(this);
        getActionManager().registerAction("actionExceuted", actionExceuted);
         this.actionExceuted.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionWorkFlow
        this.actionWorkFlow = new ActionWorkFlow(this);
        getActionManager().registerAction("actionWorkFlow", actionWorkFlow);
         this.actionWorkFlow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCalc
        this.actionCalc = new ActionCalc(this);
        getActionManager().registerAction("actionCalc", actionCalc);
         this.actionCalc.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionChooseRoom
        this.actionChooseRoom = new ActionChooseRoom(this);
        getActionManager().registerAction("actionChooseRoom", actionChooseRoom);
         this.actionChooseRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportPrice
        this.actionImportPrice = new ActionImportPrice(this);
        getActionManager().registerAction("actionImportPrice", actionImportPrice);
         this.actionImportPrice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelRoom
        this.actionDelRoom = new ActionDelRoom(this);
        getActionManager().registerAction("actionDelRoom", actionDelRoom);
         this.actionDelRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBaseType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAvgBuildingBasePrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMaxBuildingBasePrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMinBuildingBasePrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAvgRoomBasePrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMaxRoomBasePrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMinRoomBasePrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnChooseRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportPrice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboBaseType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtBuilding = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAvgBuildingBasePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtMaxBuildingBasePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtMinBuildingBasePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAvgRoomBasePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtMaxRoomBasePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtMinRoomBasePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExceuted = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnWorkFlow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCalc = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCreator.setName("contCreator");
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contName.setName("contName");
        this.contAuditTime.setName("contAuditTime");
        this.contProject.setName("contProject");
        this.contBaseType.setName("contBaseType");
        this.contBuilding.setName("contBuilding");
        this.contAvgBuildingBasePrice.setName("contAvgBuildingBasePrice");
        this.contMaxBuildingBasePrice.setName("contMaxBuildingBasePrice");
        this.contMinBuildingBasePrice.setName("contMinBuildingBasePrice");
        this.contAvgRoomBasePrice.setName("contAvgRoomBasePrice");
        this.contMaxRoomBasePrice.setName("contMaxRoomBasePrice");
        this.contMinRoomBasePrice.setName("contMinRoomBasePrice");
        this.contBizDate.setName("contBizDate");
        this.kDTable1.setName("kDTable1");
        this.btnChooseRoom.setName("btnChooseRoom");
        this.btnImportPrice.setName("btnImportPrice");
        this.btnDelRoom.setName("btnDelRoom");
        this.prmtCreator.setName("prmtCreator");
        this.txtNumber.setName("txtNumber");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtName.setName("txtName");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtProject.setName("prmtProject");
        this.comboBaseType.setName("comboBaseType");
        this.prmtBuilding.setName("prmtBuilding");
        this.txtAvgBuildingBasePrice.setName("txtAvgBuildingBasePrice");
        this.txtMaxBuildingBasePrice.setName("txtMaxBuildingBasePrice");
        this.txtMinBuildingBasePrice.setName("txtMinBuildingBasePrice");
        this.txtAvgRoomBasePrice.setName("txtAvgRoomBasePrice");
        this.txtMaxRoomBasePrice.setName("txtMaxRoomBasePrice");
        this.txtMinRoomBasePrice.setName("txtMinRoomBasePrice");
        this.pkBizDate.setName("pkBizDate");
        this.btnAudit.setName("btnAudit");
        this.btnExceuted.setName("btnExceuted");
        this.btnWorkFlow.setName("btnWorkFlow");
        this.btnCalc.setName("btnCalc");
        // CoreUI		
        this.setPreferredSize(new Dimension(1024,768));
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // contBaseType		
        this.contBaseType.setBoundLabelText(resHelper.getString("contBaseType.boundLabelText"));		
        this.contBaseType.setBoundLabelLength(100);		
        this.contBaseType.setBoundLabelUnderline(true);
        // contBuilding		
        this.contBuilding.setBoundLabelText(resHelper.getString("contBuilding.boundLabelText"));		
        this.contBuilding.setBoundLabelLength(100);		
        this.contBuilding.setBoundLabelUnderline(true);
        // contAvgBuildingBasePrice		
        this.contAvgBuildingBasePrice.setBoundLabelText(resHelper.getString("contAvgBuildingBasePrice.boundLabelText"));		
        this.contAvgBuildingBasePrice.setBoundLabelLength(100);		
        this.contAvgBuildingBasePrice.setBoundLabelUnderline(true);
        // contMaxBuildingBasePrice		
        this.contMaxBuildingBasePrice.setBoundLabelText(resHelper.getString("contMaxBuildingBasePrice.boundLabelText"));		
        this.contMaxBuildingBasePrice.setBoundLabelLength(120);		
        this.contMaxBuildingBasePrice.setBoundLabelUnderline(true);
        // contMinBuildingBasePrice		
        this.contMinBuildingBasePrice.setBoundLabelText(resHelper.getString("contMinBuildingBasePrice.boundLabelText"));		
        this.contMinBuildingBasePrice.setBoundLabelLength(120);		
        this.contMinBuildingBasePrice.setBoundLabelUnderline(true);
        // contAvgRoomBasePrice		
        this.contAvgRoomBasePrice.setBoundLabelText(resHelper.getString("contAvgRoomBasePrice.boundLabelText"));		
        this.contAvgRoomBasePrice.setBoundLabelLength(100);		
        this.contAvgRoomBasePrice.setBoundLabelUnderline(true);
        // contMaxRoomBasePrice		
        this.contMaxRoomBasePrice.setBoundLabelText(resHelper.getString("contMaxRoomBasePrice.boundLabelText"));		
        this.contMaxRoomBasePrice.setBoundLabelLength(120);		
        this.contMaxRoomBasePrice.setBoundLabelUnderline(true);
        // contMinRoomBasePrice		
        this.contMinRoomBasePrice.setBoundLabelText(resHelper.getString("contMinRoomBasePrice.boundLabelText"));		
        this.contMinRoomBasePrice.setBoundLabelLength(120);		
        this.contMinRoomBasePrice.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"roomNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"saleType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"stanardTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"stanardBasePriece\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildBasePrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomBasePrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{roomNumber}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{saleType}</t:Cell><t:Cell>$Resource{stanardTotal}</t:Cell><t:Cell>$Resource{stanardBasePriece}</t:Cell><t:Cell>$Resource{buildArea}</t:Cell><t:Cell>$Resource{buildPrice}</t:Cell><t:Cell>$Resource{buildBasePrice}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{roomPrice}</t:Cell><t:Cell>$Resource{roomBasePrice}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));

        

        // btnChooseRoom
        this.btnChooseRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionChooseRoom, new Class[] { IItemAction.class }, getServiceContext()));		
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
        // btnImportPrice
        this.btnImportPrice.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportPrice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportPrice.setText(resHelper.getString("btnImportPrice.text"));
        this.btnImportPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnImportPrice_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDelRoom
        this.btnDelRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelRoom.setText(resHelper.getString("btnDelRoom.text"));
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // kDScrollPane1
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // prmtProject		
        this.prmtProject.setEnabled(false);
        // comboBaseType		
        this.comboBaseType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.BaseTypeEnum").toArray());		
        this.comboBaseType.setRequired(true);		
        this.comboBaseType.setSelectedIndex(0);
        this.comboBaseType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboBaseType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtBuilding		
        this.prmtBuilding.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingQuery");		
        this.prmtBuilding.setEnabledMultiSelection(true);		
        this.prmtBuilding.setCommitFormat("$number$");		
        this.prmtBuilding.setDisplayFormat("$name$");		
        this.prmtBuilding.setEditFormat("$number$");
        // txtAvgBuildingBasePrice		
        this.txtAvgBuildingBasePrice.setEnabled(false);
        // txtMaxBuildingBasePrice		
        this.txtMaxBuildingBasePrice.setEnabled(false);
        // txtMinBuildingBasePrice		
        this.txtMinBuildingBasePrice.setEnabled(false);
        // txtAvgRoomBasePrice		
        this.txtAvgRoomBasePrice.setEnabled(false);
        // txtMaxRoomBasePrice		
        this.txtMaxRoomBasePrice.setEnabled(false);
        // txtMinRoomBasePrice		
        this.txtMinRoomBasePrice.setEnabled(false);
        // pkBizDate		
        this.pkBizDate.setEnabled(false);
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));
        // btnExceuted
        this.btnExceuted.setAction((IItemAction)ActionProxyFactory.getProxy(actionExceuted, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExceuted.setText(resHelper.getString("btnExceuted.text"));
        // btnWorkFlow
        this.btnWorkFlow.setAction((IItemAction)ActionProxyFactory.getProxy(actionWorkFlow, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnWorkFlow.setText(resHelper.getString("btnWorkFlow.text"));
        // btnCalc
        this.btnCalc.setAction((IItemAction)ActionProxyFactory.getProxy(actionCalc, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCalc.setText(resHelper.getString("btnCalc.text"));
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
        this.setBounds(new Rectangle(10, 10, 1024, 768));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1024, 768));
        contCreator.setBounds(new Rectangle(347, 630, 266, 19));
        this.add(contCreator, new KDLayout.Constraints(347, 630, 266, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(33, 23, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(33, 23, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(33, 416, 959, 89));
        this.add(contDescription, new KDLayout.Constraints(33, 416, 959, 89, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(347, 684, 266, 19));
        this.add(contAuditor, new KDLayout.Constraints(347, 684, 266, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(347, 23, 270, 19));
        this.add(contName, new KDLayout.Constraints(347, 23, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(33, 684, 240, 19));
        this.add(contAuditTime, new KDLayout.Constraints(33, 684, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProject.setBounds(new Rectangle(695, 23, 270, 19));
        this.add(contProject, new KDLayout.Constraints(695, 23, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBaseType.setBounds(new Rectangle(33, 83, 270, 19));
        this.add(contBaseType, new KDLayout.Constraints(33, 83, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBuilding.setBounds(new Rectangle(347, 83, 270, 19));
        this.add(contBuilding, new KDLayout.Constraints(347, 83, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAvgBuildingBasePrice.setBounds(new Rectangle(33, 542, 240, 19));
        this.add(contAvgBuildingBasePrice, new KDLayout.Constraints(33, 542, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMaxBuildingBasePrice.setBounds(new Rectangle(347, 542, 270, 19));
        this.add(contMaxBuildingBasePrice, new KDLayout.Constraints(347, 542, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMinBuildingBasePrice.setBounds(new Rectangle(695, 542, 270, 19));
        this.add(contMinBuildingBasePrice, new KDLayout.Constraints(695, 542, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAvgRoomBasePrice.setBounds(new Rectangle(33, 586, 240, 19));
        this.add(contAvgRoomBasePrice, new KDLayout.Constraints(33, 586, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMaxRoomBasePrice.setBounds(new Rectangle(347, 586, 270, 19));
        this.add(contMaxRoomBasePrice, new KDLayout.Constraints(347, 586, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMinRoomBasePrice.setBounds(new Rectangle(695, 586, 270, 19));
        this.add(contMinRoomBasePrice, new KDLayout.Constraints(695, 586, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizDate.setBounds(new Rectangle(33, 630, 240, 19));
        this.add(contBizDate, new KDLayout.Constraints(33, 630, 240, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTable1.setBounds(new Rectangle(33, 128, 961, 260));
        this.add(kDTable1, new KDLayout.Constraints(33, 128, 961, 260, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnChooseRoom.setBounds(new Rectangle(671, 77, 87, 25));
        this.add(btnChooseRoom, new KDLayout.Constraints(671, 77, 87, 25, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnImportPrice.setBounds(new Rectangle(874, 77, 87, 25));
        this.add(btnImportPrice, new KDLayout.Constraints(874, 77, 87, 25, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnDelRoom.setBounds(new Rectangle(775, 77, 87, 25));
        this.add(btnDelRoom, new KDLayout.Constraints(775, 77, 87, 25, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contName
        contName.setBoundEditor(txtName);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //contBaseType
        contBaseType.setBoundEditor(comboBaseType);
        //contBuilding
        contBuilding.setBoundEditor(prmtBuilding);
        //contAvgBuildingBasePrice
        contAvgBuildingBasePrice.setBoundEditor(txtAvgBuildingBasePrice);
        //contMaxBuildingBasePrice
        contMaxBuildingBasePrice.setBoundEditor(txtMaxBuildingBasePrice);
        //contMinBuildingBasePrice
        contMinBuildingBasePrice.setBoundEditor(txtMinBuildingBasePrice);
        //contAvgRoomBasePrice
        contAvgRoomBasePrice.setBoundEditor(txtAvgRoomBasePrice);
        //contMaxRoomBasePrice
        contMaxRoomBasePrice.setBoundEditor(txtMaxRoomBasePrice);
        //contMinRoomBasePrice
        contMinRoomBasePrice.setBoundEditor(txtMinRoomBasePrice);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);

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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnExceuted);
        this.toolBar.add(btnWorkFlow);
        this.toolBar.add(btnCalc);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtProject, "data");
		dataBinder.registerBinding("baseType", com.kingdee.eas.fdc.sellhouse.BaseTypeEnum.class, this.comboBaseType, "selectedItem");
		dataBinder.registerBinding("avgBuildingBasePrice", java.math.BigDecimal.class, this.txtAvgBuildingBasePrice, "value");
		dataBinder.registerBinding("maxBuildingBasePrice", java.math.BigDecimal.class, this.txtMaxBuildingBasePrice, "value");
		dataBinder.registerBinding("minBuildingBasePrice", java.math.BigDecimal.class, this.txtMinBuildingBasePrice, "value");
		dataBinder.registerBinding("avgRoomBasePrice", java.math.BigDecimal.class, this.txtAvgRoomBasePrice, "value");
		dataBinder.registerBinding("maxRoomBasePrice", java.math.BigDecimal.class, this.txtMaxRoomBasePrice, "value");
		dataBinder.registerBinding("minRoomBasePrice", java.math.BigDecimal.class, this.txtMinRoomBasePrice, "value");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.BasePriceControlEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.BasePriceControlInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("baseType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("avgBuildingBasePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("maxBuildingBasePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("minBuildingBasePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("avgRoomBasePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("maxRoomBasePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("minRoomBasePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    		
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
        }
    }

    /**
     * output btnChooseRoom_actionPerformed method
     */
    protected void btnChooseRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnImportPrice_actionPerformed method
     */
    protected void btnImportPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboBaseType_itemStateChanged method
     */
    protected void comboBaseType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("project.*"));
        sic.add(new SelectorItemInfo("baseType"));
        sic.add(new SelectorItemInfo("avgBuildingBasePrice"));
        sic.add(new SelectorItemInfo("maxBuildingBasePrice"));
        sic.add(new SelectorItemInfo("minBuildingBasePrice"));
        sic.add(new SelectorItemInfo("avgRoomBasePrice"));
        sic.add(new SelectorItemInfo("maxRoomBasePrice"));
        sic.add(new SelectorItemInfo("minRoomBasePrice"));
        sic.add(new SelectorItemInfo("bizDate"));
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
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExceuted_actionPerformed method
     */
    public void actionExceuted_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionWorkFlow_actionPerformed method
     */
    public void actionWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCalc_actionPerformed method
     */
    public void actionCalc_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionChooseRoom_actionPerformed method
     */
    public void actionChooseRoom_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportPrice_actionPerformed method
     */
    public void actionImportPrice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelRoom_actionPerformed method
     */
    public void actionDelRoom_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionExceuted(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExceuted() {
    	return false;
    }
	public RequestContext prepareActionWorkFlow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionWorkFlow() {
    	return false;
    }
	public RequestContext prepareActionCalc(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCalc() {
    	return false;
    }
	public RequestContext prepareActionChooseRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChooseRoom() {
    	return false;
    }
	public RequestContext prepareActionImportPrice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportPrice() {
    	return false;
    }
	public RequestContext prepareActionDelRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelRoom() {
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractBasePriceControlEditUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionExceuted class
     */     
    protected class ActionExceuted extends ItemAction {     
    
        public ActionExceuted()
        {
            this(null);
        }

        public ActionExceuted(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionExceuted.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExceuted.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExceuted.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBasePriceControlEditUI.this, "ActionExceuted", "actionExceuted_actionPerformed", e);
        }
    }

    /**
     * output ActionWorkFlow class
     */     
    protected class ActionWorkFlow extends ItemAction {     
    
        public ActionWorkFlow()
        {
            this(null);
        }

        public ActionWorkFlow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionWorkFlow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWorkFlow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWorkFlow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBasePriceControlEditUI.this, "ActionWorkFlow", "actionWorkFlow_actionPerformed", e);
        }
    }

    /**
     * output ActionCalc class
     */     
    protected class ActionCalc extends ItemAction {     
    
        public ActionCalc()
        {
            this(null);
        }

        public ActionCalc(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCalc.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalc.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalc.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBasePriceControlEditUI.this, "ActionCalc", "actionCalc_actionPerformed", e);
        }
    }

    /**
     * output ActionChooseRoom class
     */     
    protected class ActionChooseRoom extends ItemAction {     
    
        public ActionChooseRoom()
        {
            this(null);
        }

        public ActionChooseRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionChooseRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChooseRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChooseRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBasePriceControlEditUI.this, "ActionChooseRoom", "actionChooseRoom_actionPerformed", e);
        }
    }

    /**
     * output ActionImportPrice class
     */     
    protected class ActionImportPrice extends ItemAction {     
    
        public ActionImportPrice()
        {
            this(null);
        }

        public ActionImportPrice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImportPrice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportPrice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportPrice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBasePriceControlEditUI.this, "ActionImportPrice", "actionImportPrice_actionPerformed", e);
        }
    }

    /**
     * output ActionDelRoom class
     */     
    protected class ActionDelRoom extends ItemAction {     
    
        public ActionDelRoom()
        {
            this(null);
        }

        public ActionDelRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDelRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBasePriceControlEditUI.this, "ActionDelRoom", "actionDelRoom_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "BasePriceControlEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}