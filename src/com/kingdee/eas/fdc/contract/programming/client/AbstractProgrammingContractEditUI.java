/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

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
public abstract class AbstractProgrammingContractEditUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProgrammingContractEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEstimateAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtParentLongName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtControlAmount;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtWorkContent;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAttachment;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtSupMaterial;
    protected com.kingdee.bos.ctrl.swing.KDComboBox kdcInviteWay;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteOrg;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtEstimateAmount;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdContainerCost;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainerEconomy;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtCost;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEconomy;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSave;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAttachment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEdit;
    protected com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo editData = null;
    protected ActionSubmit actionSubmit = null;
    protected ActionAttachment actionAttachment = null;
    protected ActionSelect actionSelect = null;
    protected ActionEdit actionEdit = null;
    /**
     * output class constructor
     */
    public AbstractProgrammingContractEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProgrammingContractEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        this.actionSubmit = new ActionSubmit(this);
        getActionManager().registerAction("actionSubmit", actionSubmit);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAttachment
        this.actionAttachment = new ActionAttachment(this);
        getActionManager().registerAction("actionAttachment", actionAttachment);
         this.actionAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSelect
        this.actionSelect = new ActionSelect(this);
        getActionManager().registerAction("actionSelect", actionSelect);
         this.actionSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEdit
        this.actionEdit = new ActionEdit(this);
        getActionManager().registerAction("actionEdit", actionEdit);
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEstimateAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtParentLongName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtControlAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtWorkContent = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAttachment = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtSupMaterial = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kdcInviteWay = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtInviteOrg = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtEstimateAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kdContainerCost = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainerEconomy = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtCost = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtEconomy = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEdit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDPanel1.setName("kDPanel1");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.contEstimateAmount.setName("contEstimateAmount");
        this.txtParentLongName.setName("txtParentLongName");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.txtAmount.setName("txtAmount");
        this.txtControlAmount.setName("txtControlAmount");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtWorkContent.setName("txtWorkContent");
        this.txtDescription.setName("txtDescription");
        this.txtAttachment.setName("txtAttachment");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.txtSupMaterial.setName("txtSupMaterial");
        this.kdcInviteWay.setName("kdcInviteWay");
        this.prmtInviteOrg.setName("prmtInviteOrg");
        this.txtEstimateAmount.setName("txtEstimateAmount");
        this.kdContainerCost.setName("kdContainerCost");
        this.kDContainerEconomy.setName("kDContainerEconomy");
        this.kdtCost.setName("kdtCost");
        this.kdtEconomy.setName("kdtEconomy");
        this.btnSave.setName("btnSave");
        this.btnAttachment.setName("btnAttachment");
        this.btnEdit.setName("btnEdit");
        // CoreUI		
        this.setPreferredSize(new Dimension(950,605));
        // kDPanel1
        // kDTabbedPane1
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setVisible(false);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelUnderline(true);		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setEnabled(false);		
        this.kDLabelContainer9.setVisible(false);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setEnabled(false);		
        this.kDLabelContainer7.setVisible(false);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);		
        this.kDLabelContainer10.setEnabled(false);		
        this.kDLabelContainer10.setVisible(false);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);		
        this.kDLabelContainer11.setEnabled(false);		
        this.kDLabelContainer11.setVisible(false);
        // contEstimateAmount		
        this.contEstimateAmount.setBoundLabelText(resHelper.getString("contEstimateAmount.boundLabelText"));		
        this.contEstimateAmount.setBoundLabelLength(100);		
        this.contEstimateAmount.setBoundLabelUnderline(true);		
        this.contEstimateAmount.setVisible(false);
        // txtParentLongName		
        this.txtParentLongName.setEditable(false);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setMaxLength(80);
        // txtAmount		
        this.txtAmount.setPrecision(2);		
        this.txtAmount.setDataType(1);		
        this.txtAmount.setSupportedEmpty(true);		
        this.txtAmount.setEnabled(false);
        // txtControlAmount		
        this.txtControlAmount.setPrecision(2);		
        this.txtControlAmount.setSupportedEmpty(true);		
        this.txtControlAmount.setDataType(1);
        // kDScrollPane1
        // txtWorkContent		
        this.txtWorkContent.setMaxLength(1024);
        // txtDescription		
        this.txtDescription.setMaxLength(1024);
        // txtAttachment		
        this.txtAttachment.setEditable(false);		
        this.txtAttachment.setHorizontalAlignment(2);		
        this.txtAttachment.setMaxLength(80);		
        this.txtAttachment.setEnabled(false);		
        this.txtAttachment.setVisible(false);
        // kDScrollPane2
        // txtSupMaterial		
        this.txtSupMaterial.setMaxLength(1024);		
        this.txtSupMaterial.setEnabled(false);		
        this.txtSupMaterial.setVisible(false);
        // kdcInviteWay		
        this.kdcInviteWay.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.InviteFormEnum").toArray());		
        this.kdcInviteWay.setEnabled(false);		
        this.kdcInviteWay.setVisible(false);
        // prmtInviteOrg		
        this.prmtInviteOrg.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");		
        this.prmtInviteOrg.setDisplayFormat("$name$");		
        this.prmtInviteOrg.setCommitFormat("$number$");		
        this.prmtInviteOrg.setEditFormat("$number$");		
        this.prmtInviteOrg.setEnabled(false);		
        this.prmtInviteOrg.setVisible(false);
        // txtEstimateAmount		
        this.txtEstimateAmount.setDataType(1);		
        this.txtEstimateAmount.setEnabled(false);
        // kdContainerCost
        // kDContainerEconomy
        // kdtCost
		String kdtCostStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"3\" /><t:Column t:key=\"goalCost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"assigned\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"assigning\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"scale\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"contractAssign\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"8\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{goalCost}</t:Cell><t:Cell>$Resource{assigned}</t:Cell><t:Cell>$Resource{assigning}</t:Cell><t:Cell>$Resource{scale}</t:Cell><t:Cell>$Resource{contractAssign}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtCost.setFormatXml(resHelper.translateString("kdtCost",kdtCostStrXML));
        this.kdtCost.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtCost_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtCost.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    kdtCost_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtCost.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtCost_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtCost_editStarting(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtCost_editStarted(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // kdtEconomy
		String kdtEconomyStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Alignment horizontal=\"center\" /></c:Style><c:Style id=\"sCol2\"><c:Alignment horizontal=\"center\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>###</c:NumberFormat><c:Alignment horizontal=\"center\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>###,##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"paymentDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"paymentType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"scale\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"condition\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{paymentDate}</t:Cell><t:Cell>$Resource{paymentType}</t:Cell><t:Cell>$Resource{scale}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{condition}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEconomy.setFormatXml(resHelper.translateString("kdtEconomy",kdtEconomyStrXML));
        this.kdtEconomy.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtEconomy_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEconomy.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    kdtEconomy_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEconomy.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    kdtEconomy_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEconomy.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEconomy_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEconomy_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSave.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));
        // btnAttachment
        this.btnAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));		
        this.btnAttachment.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_affixmanage"));
        // btnEdit
        this.btnEdit.setAction((IItemAction)ActionProxyFactory.getProxy(actionEdit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEdit.setText(resHelper.getString("btnEdit.text"));
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
        this.setBounds(new Rectangle(10, 10, 950, 605));
        this.setLayout(null);
        kDPanel1.setBounds(new Rectangle(5, 5, 939, 187));
        this.add(kDPanel1, null);
        kDTabbedPane1.setBounds(new Rectangle(5, 195, 943, 408));
        this.add(kDTabbedPane1, null);
        //kDPanel1
        kDPanel1.setLayout(null);        kDLabelContainer1.setBounds(new Rectangle(5, 5, 927, 20));
        kDPanel1.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(5, 28, 927, 20));
        kDPanel1.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(5, 51, 927, 20));
        kDPanel1.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(5, 74, 927, 19));
        kDPanel1.add(kDLabelContainer4, null);
        kDLabelContainer5.setBounds(new Rectangle(681, 54, 300, 20));
        kDPanel1.add(kDLabelContainer5, null);
        kDLabelContainer6.setBounds(new Rectangle(5, 96, 927, 60));
        kDPanel1.add(kDLabelContainer6, null);
        kDLabelContainer8.setBounds(new Rectangle(5, 160, 927, 20));
        kDPanel1.add(kDLabelContainer8, null);
        kDLabelContainer9.setBounds(new Rectangle(5, 295, 680, 20));
        kDPanel1.add(kDLabelContainer9, null);
        kDLabelContainer7.setBounds(new Rectangle(5, 195, 680, 60));
        kDPanel1.add(kDLabelContainer7, null);
        kDLabelContainer10.setBounds(new Rectangle(623, 35, 300, 19));
        kDPanel1.add(kDLabelContainer10, null);
        kDLabelContainer11.setBounds(new Rectangle(709, 112, 300, 19));
        kDPanel1.add(kDLabelContainer11, null);
        contEstimateAmount.setBounds(new Rectangle(683, 83, 300, 19));
        kDPanel1.add(contEstimateAmount, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtParentLongName);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtNumber);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtName);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtAmount);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtControlAmount);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtWorkContent, null);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtDescription);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(txtAttachment);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtSupMaterial, null);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(kdcInviteWay);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(prmtInviteOrg);
        //contEstimateAmount
        contEstimateAmount.setBoundEditor(txtEstimateAmount);
        //kDTabbedPane1
        kDTabbedPane1.add(kdContainerCost, resHelper.getString("kdContainerCost.constraints"));
        kDTabbedPane1.add(kDContainerEconomy, resHelper.getString("kDContainerEconomy.constraints"));
        //kdContainerCost
kdContainerCost.getContentPane().setLayout(new BorderLayout(0, 0));        kdContainerCost.getContentPane().add(kdtCost, BorderLayout.CENTER);
        //kDContainerEconomy
kDContainerEconomy.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainerEconomy.getContentPane().add(kdtEconomy, BorderLayout.CENTER);

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
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemCloudShare);
        menuFile.add(kdSeparatorFWFile1);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnEdit);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.programming.app.ProgrammingContractEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)ov;
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
     * output kdtCost_editStopped method
     */
    protected void kdtCost_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtCost_tableClicked method
     */
    protected void kdtCost_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kdtCost_activeCellChanged method
     */
    protected void kdtCost_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output kdtCost_editStarting method
     */
    protected void kdtCost_editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtCost_editStarted method
     */
    protected void kdtCost_editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEconomy_editStopped method
     */
    protected void kdtEconomy_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEconomy_tableClicked method
     */
    protected void kdtEconomy_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kdtEconomy_activeCellChanged method
     */
    protected void kdtEconomy_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output kdtEconomy_editValueChanged method
     */
    protected void kdtEconomy_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEconomy_tableSelectChanged method
     */
    protected void kdtEconomy_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAttachment_actionPerformed method
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSelect_actionPerformed method
     */
    public void actionSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEdit_actionPerformed method
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAttachment() {
    	return false;
    }
	public RequestContext prepareActionSelect(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSelect() {
    	return false;
    }
	public RequestContext prepareActionEdit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEdit() {
    	return false;
    }

    /**
     * output ActionSubmit class
     */     
    protected class ActionSubmit extends ItemAction {     
    
        public ActionSubmit()
        {
            this(null);
        }

        public ActionSubmit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingContractEditUI.this, "ActionSubmit", "actionSubmit_actionPerformed", e);
        }
    }

    /**
     * output ActionAttachment class
     */     
    protected class ActionAttachment extends ItemAction {     
    
        public ActionAttachment()
        {
            this(null);
        }

        public ActionAttachment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingContractEditUI.this, "ActionAttachment", "actionAttachment_actionPerformed", e);
        }
    }

    /**
     * output ActionSelect class
     */     
    protected class ActionSelect extends ItemAction {     
    
        public ActionSelect()
        {
            this(null);
        }

        public ActionSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingContractEditUI.this, "ActionSelect", "actionSelect_actionPerformed", e);
        }
    }

    /**
     * output ActionEdit class
     */     
    protected class ActionEdit extends ItemAction {     
    
        public ActionEdit()
        {
            this(null);
        }

        public ActionEdit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEdit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEdit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEdit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingContractEditUI.this, "ActionEdit", "actionEdit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.programming.client", "ProgrammingContractEditUI");
    }




}