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
public abstract class AbstractPlanisphereEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPlanisphereEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPtype;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuilding;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPtype;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilding;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsShowUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloor;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelPlanInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCellHeigth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCellWidth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCellHorizCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCellVertiCount;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPaneBaseInfo;
    protected com.kingdee.bos.ctrl.swing.KDSpinner kDSpiUnit;
    protected com.kingdee.bos.ctrl.swing.KDSpinner kDSpiFloor;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSetPicLayOut;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPaneBottom;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainerElement;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainerPicShow;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelElementBtn;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddElement;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveElement;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanelPicViewBtn;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLocationName;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLocationOutLine;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblElementEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPicView;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLocationSure;
    protected com.kingdee.bos.ctrl.swing.KDSpinner txtCellHeigth;
    protected com.kingdee.bos.ctrl.swing.KDSpinner txtCellWidth;
    protected com.kingdee.bos.ctrl.swing.KDSpinner txtCellHorizCount;
    protected com.kingdee.bos.ctrl.swing.KDSpinner txtCellVertiCount;
    protected com.kingdee.eas.fdc.sellhouse.PlanisphereInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractPlanisphereEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPlanisphereEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboPtype = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBuilding = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPtype = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsShowUnit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanelPlanInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contCellHeigth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCellWidth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCellHorizCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCellVertiCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPaneBaseInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDSpiUnit = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDSpiFloor = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.btnSetPicLayOut = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSplitPaneBottom = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDContainerElement = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainerPicShow = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDPanelElementBtn = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnAddElement = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveElement = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDPanelPicViewBtn = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnLocationName = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnLocationOutLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblElementEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblPicView = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnLocationSure = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtCellHeigth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtCellWidth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtCellHorizCount = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtCellVertiCount = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.comboPtype.setName("comboPtype");
        this.prmtOrgUnit.setName("prmtOrgUnit");
        this.prmtSellProject.setName("prmtSellProject");
        this.prmtBuilding.setName("prmtBuilding");
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contPtype.setName("contPtype");
        this.contOrgUnit.setName("contOrgUnit");
        this.contSellProject.setName("contSellProject");
        this.contBuilding.setName("contBuilding");
        this.chkIsShowUnit.setName("chkIsShowUnit");
        this.contUnit.setName("contUnit");
        this.contFloor.setName("contFloor");
        this.kDPanelPlanInfo.setName("kDPanelPlanInfo");
        this.contCellHeigth.setName("contCellHeigth");
        this.contCellWidth.setName("contCellWidth");
        this.contCellHorizCount.setName("contCellHorizCount");
        this.contCellVertiCount.setName("contCellVertiCount");
        this.kDPaneBaseInfo.setName("kDPaneBaseInfo");
        this.kDSpiUnit.setName("kDSpiUnit");
        this.kDSpiFloor.setName("kDSpiFloor");
        this.btnSetPicLayOut.setName("btnSetPicLayOut");
        this.kDSplitPaneBottom.setName("kDSplitPaneBottom");
        this.kDContainerElement.setName("kDContainerElement");
        this.kDContainerPicShow.setName("kDContainerPicShow");
        this.kDPanelElementBtn.setName("kDPanelElementBtn");
        this.btnAddElement.setName("btnAddElement");
        this.btnRemoveElement.setName("btnRemoveElement");
        this.kDPanelPicViewBtn.setName("kDPanelPicViewBtn");
        this.btnLocationName.setName("btnLocationName");
        this.btnLocationOutLine.setName("btnLocationOutLine");
        this.tblElementEntry.setName("tblElementEntry");
        this.tblPicView.setName("tblPicView");
        this.btnLocationSure.setName("btnLocationSure");
        this.txtCellHeigth.setName("txtCellHeigth");
        this.txtCellWidth.setName("txtCellWidth");
        this.txtCellHorizCount.setName("txtCellHorizCount");
        this.txtCellVertiCount.setName("txtCellVertiCount");
        // CoreUI		
        this.btnAttachment.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.btnAudit.setVisible(false);		
        this.menuItemAudit.setVisible(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);
        // comboPtype		
        this.comboPtype.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum").toArray());		
        this.comboPtype.setRequired(true);
        this.comboPtype.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboPtype_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtOrgUnit		
        this.prmtOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.FullOrgUnitQuery");		
        this.prmtOrgUnit.setDisplayFormat("$name$");		
        this.prmtOrgUnit.setEditFormat("$number$");		
        this.prmtOrgUnit.setCommitFormat("$number$");		
        this.prmtOrgUnit.setRequired(true);
        this.prmtOrgUnit.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtOrgUnit_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtSellProject		
        this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtSellProject.setDisplayFormat("$name$");		
        this.prmtSellProject.setEditFormat("$number$");		
        this.prmtSellProject.setCommitFormat("$number$");
        this.prmtSellProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSellProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtBuilding		
        this.prmtBuilding.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingQuery");		
        this.prmtBuilding.setDisplayFormat("$name$");		
        this.prmtBuilding.setEditFormat("$number$");		
        this.prmtBuilding.setCommitFormat("$number$");
        this.prmtBuilding.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtBuilding_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contPtype		
        this.contPtype.setBoundLabelText(resHelper.getString("contPtype.boundLabelText"));		
        this.contPtype.setBoundLabelLength(100);		
        this.contPtype.setBoundLabelUnderline(true);
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(100);		
        this.contOrgUnit.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contBuilding		
        this.contBuilding.setBoundLabelText(resHelper.getString("contBuilding.boundLabelText"));		
        this.contBuilding.setBoundLabelLength(100);		
        this.contBuilding.setBoundLabelUnderline(true);
        // chkIsShowUnit		
        this.chkIsShowUnit.setText(resHelper.getString("chkIsShowUnit.text"));
        this.chkIsShowUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsShowUnit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contUnit		
        this.contUnit.setBoundLabelText(resHelper.getString("contUnit.boundLabelText"));		
        this.contUnit.setBoundLabelLength(100);		
        this.contUnit.setBoundLabelUnderline(true);
        // contFloor		
        this.contFloor.setBoundLabelText(resHelper.getString("contFloor.boundLabelText"));		
        this.contFloor.setBoundLabelLength(100);		
        this.contFloor.setBoundLabelUnderline(true);
        // kDPanelPlanInfo		
        this.kDPanelPlanInfo.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanelPlanInfo.border.title")));
        // contCellHeigth		
        this.contCellHeigth.setBoundLabelText(resHelper.getString("contCellHeigth.boundLabelText"));		
        this.contCellHeigth.setBoundLabelLength(60);		
        this.contCellHeigth.setBoundLabelUnderline(true);		
        this.contCellHeigth.setEnabled(false);
        // contCellWidth		
        this.contCellWidth.setBoundLabelText(resHelper.getString("contCellWidth.boundLabelText"));		
        this.contCellWidth.setBoundLabelLength(60);		
        this.contCellWidth.setBoundLabelUnderline(true);		
        this.contCellWidth.setEnabled(false);
        // contCellHorizCount		
        this.contCellHorizCount.setBoundLabelText(resHelper.getString("contCellHorizCount.boundLabelText"));		
        this.contCellHorizCount.setBoundLabelLength(80);		
        this.contCellHorizCount.setBoundLabelUnderline(true);		
        this.contCellHorizCount.setEnabled(false);
        // contCellVertiCount		
        this.contCellVertiCount.setBoundLabelText(resHelper.getString("contCellVertiCount.boundLabelText"));		
        this.contCellVertiCount.setBoundLabelLength(80);		
        this.contCellVertiCount.setBoundLabelUnderline(true);		
        this.contCellVertiCount.setEnabled(false);
        // kDPaneBaseInfo		
        this.kDPaneBaseInfo.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPaneBaseInfo.border.title")));
        // kDSpiUnit
        this.kDSpiUnit.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kDSpiUnit_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDSpiFloor
        this.kDSpiFloor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    kDSpiFloor_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnSetPicLayOut		
        this.btnSetPicLayOut.setText(resHelper.getString("btnSetPicLayOut.text"));
        this.btnSetPicLayOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSetPicLayOut_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDSplitPaneBottom		
        this.kDSplitPaneBottom.setOneTouchExpandable(true);		
        this.kDSplitPaneBottom.setDividerLocation(200);
        // kDContainerElement		
        this.kDContainerElement.setTitle(resHelper.getString("kDContainerElement.title"));
        // kDContainerPicShow		
        this.kDContainerPicShow.setTitle(resHelper.getString("kDContainerPicShow.title"));
        // kDPanelElementBtn		
        this.kDPanelElementBtn.setPreferredSize(new Dimension(10,25));
        // btnAddElement		
        this.btnAddElement.setText(resHelper.getString("btnAddElement.text"));
        this.btnAddElement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddElement_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnRemoveElement		
        this.btnRemoveElement.setText(resHelper.getString("btnRemoveElement.text"));
        this.btnRemoveElement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRemoveElement_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDPanelPicViewBtn		
        this.kDPanelPicViewBtn.setPreferredSize(new Dimension(10,25));
        // btnLocationName		
        this.btnLocationName.setText(resHelper.getString("btnLocationName.text"));
        this.btnLocationName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnLocationName_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnLocationOutLine		
        this.btnLocationOutLine.setText(resHelper.getString("btnLocationOutLine.text"));
        this.btnLocationOutLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnLocationOutLine_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tblElementEntry
		String tblElementEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"outLineLocationData\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"nameLocationData\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"outLineBackColor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"outLineColor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"nameBackColor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"nameColor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{outLineLocationData}</t:Cell><t:Cell>$Resource{nameLocationData}</t:Cell><t:Cell>$Resource{outLineBackColor}</t:Cell><t:Cell>$Resource{outLineColor}</t:Cell><t:Cell>$Resource{nameBackColor}</t:Cell><t:Cell>$Resource{nameColor}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblElementEntry.setFormatXml(resHelper.translateString("tblElementEntry",tblElementEntryStrXML));
        this.tblElementEntry.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblElementEntry_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblElementEntry.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblElementEntry_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblElementEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblElementEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblElementEntry.putBindContents("editData",new String[] {"type","name","outLineLocationData","nameLocationData","","","",""});


        // tblPicView
		String tblPicViewStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"cell1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{cell1}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblPicView.setFormatXml(resHelper.translateString("tblPicView",tblPicViewStrXML));

        

        // btnLocationSure		
        this.btnLocationSure.setText(resHelper.getString("btnLocationSure.text"));
        this.btnLocationSure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnLocationSure_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtCellHeigth		
        this.txtCellHeigth.setEnabled(false);
        // txtCellWidth		
        this.txtCellWidth.setEnabled(false);
        // txtCellHorizCount		
        this.txtCellHorizCount.setEnabled(false);
        // txtCellVertiCount		
        this.txtCellVertiCount.setEnabled(false);
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
        kDPanelPlanInfo.setBounds(new Rectangle(3, 110, 1007, 47));
        this.add(kDPanelPlanInfo, new KDLayout.Constraints(3, 110, 1007, 47, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPaneBaseInfo.setBounds(new Rectangle(3, 7, 1007, 102));
        this.add(kDPaneBaseInfo, new KDLayout.Constraints(3, 7, 1007, 102, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSplitPaneBottom.setBounds(new Rectangle(8, 158, 995, 465));
        this.add(kDSplitPaneBottom, new KDLayout.Constraints(8, 158, 995, 465, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDPanelPlanInfo
        kDPanelPlanInfo.setLayout(new KDLayout());
        kDPanelPlanInfo.putClientProperty("OriginalBounds", new Rectangle(3, 110, 1007, 47));        contCellHeigth.setBounds(new Rectangle(19, 16, 136, 19));
        kDPanelPlanInfo.add(contCellHeigth, new KDLayout.Constraints(19, 16, 136, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contCellWidth.setBounds(new Rectangle(185, 16, 136, 19));
        kDPanelPlanInfo.add(contCellWidth, new KDLayout.Constraints(185, 16, 136, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contCellHorizCount.setBounds(new Rectangle(351, 16, 155, 19));
        kDPanelPlanInfo.add(contCellHorizCount, new KDLayout.Constraints(351, 16, 155, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contCellVertiCount.setBounds(new Rectangle(537, 16, 155, 19));
        kDPanelPlanInfo.add(contCellVertiCount, new KDLayout.Constraints(537, 16, 155, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        btnSetPicLayOut.setBounds(new Rectangle(708, 15, 84, 19));
        kDPanelPlanInfo.add(btnSetPicLayOut, new KDLayout.Constraints(708, 15, 84, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        //contCellHeigth
        contCellHeigth.setBoundEditor(txtCellHeigth);
        //contCellWidth
        contCellWidth.setBoundEditor(txtCellWidth);
        //contCellHorizCount
        contCellHorizCount.setBoundEditor(txtCellHorizCount);
        //contCellVertiCount
        contCellVertiCount.setBoundEditor(txtCellVertiCount);
        //kDPaneBaseInfo
        kDPaneBaseInfo.setLayout(new KDLayout());
        kDPaneBaseInfo.putClientProperty("OriginalBounds", new Rectangle(3, 7, 1007, 102));        contNumber.setBounds(new Rectangle(18, 17, 270, 19));
        kDPaneBaseInfo.add(contNumber, new KDLayout.Constraints(18, 17, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(366, 17, 270, 19));
        kDPaneBaseInfo.add(contName, new KDLayout.Constraints(366, 17, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contPtype.setBounds(new Rectangle(715, 17, 270, 19));
        kDPaneBaseInfo.add(contPtype, new KDLayout.Constraints(715, 17, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrgUnit.setBounds(new Rectangle(18, 42, 270, 19));
        kDPaneBaseInfo.add(contOrgUnit, new KDLayout.Constraints(18, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellProject.setBounds(new Rectangle(366, 42, 270, 19));
        kDPaneBaseInfo.add(contSellProject, new KDLayout.Constraints(366, 42, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contBuilding.setBounds(new Rectangle(715, 42, 270, 19));
        kDPaneBaseInfo.add(contBuilding, new KDLayout.Constraints(715, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkIsShowUnit.setBounds(new Rectangle(18, 68, 140, 19));
        kDPaneBaseInfo.add(chkIsShowUnit, new KDLayout.Constraints(18, 68, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contUnit.setBounds(new Rectangle(366, 68, 270, 19));
        kDPaneBaseInfo.add(contUnit, new KDLayout.Constraints(366, 68, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contFloor.setBounds(new Rectangle(715, 68, 270, 19));
        kDPaneBaseInfo.add(contFloor, new KDLayout.Constraints(715, 68, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contPtype
        contPtype.setBoundEditor(comboPtype);
        //contOrgUnit
        contOrgUnit.setBoundEditor(prmtOrgUnit);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contBuilding
        contBuilding.setBoundEditor(prmtBuilding);
        //contUnit
        contUnit.setBoundEditor(kDSpiUnit);
        //contFloor
        contFloor.setBoundEditor(kDSpiFloor);
        //kDSplitPaneBottom
        kDSplitPaneBottom.add(kDContainerElement, "left");
        kDSplitPaneBottom.add(kDContainerPicShow, "right");
        //kDContainerElement
kDContainerElement.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainerElement.getContentPane().add(kDPanelElementBtn, BorderLayout.NORTH);
        kDContainerElement.getContentPane().add(tblElementEntry, BorderLayout.CENTER);
        //kDPanelElementBtn
        kDPanelElementBtn.setLayout(new KDLayout());
        kDPanelElementBtn.putClientProperty("OriginalBounds", new Rectangle(0, 0, 199, 464));        btnAddElement.setBounds(new Rectangle(17, 3, 60, 19));
        kDPanelElementBtn.add(btnAddElement, new KDLayout.Constraints(17, 3, 60, 19, 0));
        btnRemoveElement.setBounds(new Rectangle(99, 3, 60, 19));
        kDPanelElementBtn.add(btnRemoveElement, new KDLayout.Constraints(99, 3, 60, 19, 0));
        //kDContainerPicShow
kDContainerPicShow.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainerPicShow.getContentPane().add(kDPanelPicViewBtn, BorderLayout.NORTH);
        kDContainerPicShow.getContentPane().add(tblPicView, BorderLayout.CENTER);
        //kDPanelPicViewBtn
        kDPanelPicViewBtn.setLayout(new KDLayout());
        kDPanelPicViewBtn.putClientProperty("OriginalBounds", new Rectangle(0, 0, 784, 464));        btnLocationName.setBounds(new Rectangle(121, 4, 96, 19));
        kDPanelPicViewBtn.add(btnLocationName, new KDLayout.Constraints(121, 4, 96, 19, 0));
        btnLocationOutLine.setBounds(new Rectangle(16, 4, 96, 19));
        kDPanelPicViewBtn.add(btnLocationOutLine, new KDLayout.Constraints(16, 4, 96, 19, 0));
        btnLocationSure.setBounds(new Rectangle(226, 4, 96, 19));
        kDPanelPicViewBtn.add(btnLocationSure, new KDLayout.Constraints(226, 4, 96, 19, 0));

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
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
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
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
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
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("ptype", com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum.class, this.comboPtype, "selectedItem");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtOrgUnit, "data");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("building", com.kingdee.eas.fdc.sellhouse.BuildingInfo.class, this.prmtBuilding, "data");
		dataBinder.registerBinding("isShowUnit", boolean.class, this.chkIsShowUnit, "selected");
		dataBinder.registerBinding("unit", int.class, this.kDSpiUnit, "value");
		dataBinder.registerBinding("floor", int.class, this.kDSpiFloor, "value");
		dataBinder.registerBinding("elementEntry.type", com.kingdee.util.enums.Enum.class, this.tblElementEntry, "type.text");
		dataBinder.registerBinding("elementEntry.name", String.class, this.tblElementEntry, "name.text");
		dataBinder.registerBinding("elementEntry", com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryInfo.class, this.tblElementEntry, "userObject");
		dataBinder.registerBinding("elementEntry.outLineLocationData", byte[].class, this.tblElementEntry, "outLineLocationData.text");
		dataBinder.registerBinding("elementEntry.nameLocationData", byte[].class, this.tblElementEntry, "nameLocationData.text");
		dataBinder.registerBinding("cellHeigth", int.class, this.txtCellHeigth, "value");
		dataBinder.registerBinding("cellWidth", int.class, this.txtCellWidth, "value");
		dataBinder.registerBinding("cellHorizCount", int.class, this.txtCellHorizCount, "value");
		dataBinder.registerBinding("cellVertiCount", int.class, this.txtCellVertiCount, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.PlanisphereEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.PlanisphereInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ptype", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("building", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isShowUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("unit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("floor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("elementEntry.type", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("elementEntry.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("elementEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("elementEntry.outLineLocationData", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("elementEntry.nameLocationData", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cellHeigth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cellWidth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cellHorizCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cellVertiCount", ValidateHelper.ON_SAVE);    		
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
     * output comboPtype_itemStateChanged method
     */
    protected void comboPtype_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtOrgUnit_dataChanged method
     */
    protected void prmtOrgUnit_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtSellProject_dataChanged method
     */
    protected void prmtSellProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtBuilding_dataChanged method
     */
    protected void prmtBuilding_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output chkIsShowUnit_actionPerformed method
     */
    protected void chkIsShowUnit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kDSpiUnit_stateChanged method
     */
    protected void kDSpiUnit_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output kDSpiFloor_stateChanged method
     */
    protected void kDSpiFloor_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output btnSetPicLayOut_actionPerformed method
     */
    protected void btnSetPicLayOut_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAddElement_actionPerformed method
     */
    protected void btnAddElement_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnRemoveElement_actionPerformed method
     */
    protected void btnRemoveElement_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnLocationName_actionPerformed method
     */
    protected void btnLocationName_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnLocationOutLine_actionPerformed method
     */
    protected void btnLocationOutLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblElementEntry_editStopped method
     */
    protected void tblElementEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblElementEntry_tableClicked method
     */
    protected void tblElementEntry_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblElementEntry_tableSelectChanged method
     */
    protected void tblElementEntry_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output btnLocationSure_actionPerformed method
     */
    protected void btnLocationSure_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("ptype"));
        sic.add(new SelectorItemInfo("orgUnit.*"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("building.*"));
        sic.add(new SelectorItemInfo("isShowUnit"));
        sic.add(new SelectorItemInfo("unit"));
        sic.add(new SelectorItemInfo("floor"));
    sic.add(new SelectorItemInfo("elementEntry.type"));
    sic.add(new SelectorItemInfo("elementEntry.name"));
        sic.add(new SelectorItemInfo("elementEntry.*"));
//        sic.add(new SelectorItemInfo("elementEntry.number"));
    sic.add(new SelectorItemInfo("elementEntry.outLineLocationData"));
    sic.add(new SelectorItemInfo("elementEntry.nameLocationData"));
        sic.add(new SelectorItemInfo("cellHeigth"));
        sic.add(new SelectorItemInfo("cellWidth"));
        sic.add(new SelectorItemInfo("cellHorizCount"));
        sic.add(new SelectorItemInfo("cellVertiCount"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "PlanisphereEditUI");
    }




}