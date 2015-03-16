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
public abstract class AbstractSellProjectEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSellProjectEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlBaseInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTerraNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTerraNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTerraArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTerraLicence;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTerraLicence;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTermBegin;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTermEnd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalYear;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTotalYear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTerraUse;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTerraUse;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSideEntironment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCubageRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRealtyPaperNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRealtyPaperNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPresellLicence;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPresellLicence;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contExportLicence;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtExportLicence;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Project;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator6;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlSubarea;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpTermBegin;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kdpTermEnd;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTerraArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCubageRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSubarea;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddSubarea;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveSubarea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProAdress;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProAdress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFrameProperty;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFrameProperty;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtSideEntironment;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsForSHE;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsForTen;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsForPPM;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlShareOrgUnit;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblShareOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectResource;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboProjecResource;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvestorHouse;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7InvestorHouse;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractSellProjectEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSellProjectEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.pnlBaseInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contTerraNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtTerraNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contTerraArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTerraLicence = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtTerraLicence = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contTermBegin = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTermEnd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtTotalYear = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contTerraUse = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtTerraUse = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7BuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contSideEntironment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCubageRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRealtyPaperNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRealtyPaperNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contPresellLicence = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPresellLicence = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contExportLicence = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtExportLicence = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Project = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator6 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.pnlSubarea = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdpTermBegin = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdpTermEnd = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtTerraArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtCubageRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tblSubarea = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddSubarea = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveSubarea = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contProAdress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtProAdress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contFrameProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtFrameProperty = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtSideEntironment = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.chkIsForSHE = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsForTen = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsForPPM = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.pnlShareOrgUnit = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblShareOrgUnit = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddOrgUnit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelOrgUnit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contProjectResource = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboProjecResource = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contInvestorHouse = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7InvestorHouse = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.pnlBaseInfo.setName("pnlBaseInfo");
        this.contTerraNumber.setName("contTerraNumber");
        this.txtTerraNumber.setName("txtTerraNumber");
        this.contTerraArea.setName("contTerraArea");
        this.contTerraLicence.setName("contTerraLicence");
        this.txtTerraLicence.setName("txtTerraLicence");
        this.contTermBegin.setName("contTermBegin");
        this.contTermEnd.setName("contTermEnd");
        this.contTotalYear.setName("contTotalYear");
        this.txtTotalYear.setName("txtTotalYear");
        this.contTerraUse.setName("contTerraUse");
        this.txtTerraUse.setName("txtTerraUse");
        this.contBuildingProperty.setName("contBuildingProperty");
        this.f7BuildingProperty.setName("f7BuildingProperty");
        this.contSideEntironment.setName("contSideEntironment");
        this.contCubageRate.setName("contCubageRate");
        this.contRealtyPaperNumber.setName("contRealtyPaperNumber");
        this.txtRealtyPaperNumber.setName("txtRealtyPaperNumber");
        this.contPresellLicence.setName("contPresellLicence");
        this.txtPresellLicence.setName("txtPresellLicence");
        this.contExportLicence.setName("contExportLicence");
        this.txtExportLicence.setName("txtExportLicence");
        this.contProject.setName("contProject");
        this.f7Project.setName("f7Project");
        this.kDLabel1.setName("kDLabel1");
        this.kDSeparator5.setName("kDSeparator5");
        this.kDSeparator6.setName("kDSeparator6");
        this.pnlSubarea.setName("pnlSubarea");
        this.kdpTermBegin.setName("kdpTermBegin");
        this.kdpTermEnd.setName("kdpTermEnd");
        this.txtTerraArea.setName("txtTerraArea");
        this.txtCubageRate.setName("txtCubageRate");
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.tblSubarea.setName("tblSubarea");
        this.btnAddSubarea.setName("btnAddSubarea");
        this.btnRemoveSubarea.setName("btnRemoveSubarea");
        this.contProAdress.setName("contProAdress");
        this.txtProAdress.setName("txtProAdress");
        this.contFrameProperty.setName("contFrameProperty");
        this.txtFrameProperty.setName("txtFrameProperty");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtSideEntironment.setName("txtSideEntironment");
        this.chkIsForSHE.setName("chkIsForSHE");
        this.chkIsForTen.setName("chkIsForTen");
        this.chkIsForPPM.setName("chkIsForPPM");
        this.pnlShareOrgUnit.setName("pnlShareOrgUnit");
        this.tblShareOrgUnit.setName("tblShareOrgUnit");
        this.btnAddOrgUnit.setName("btnAddOrgUnit");
        this.btnDelOrgUnit.setName("btnDelOrgUnit");
        this.contProjectResource.setName("contProjectResource");
        this.comboProjecResource.setName("comboProjecResource");
        this.contInvestorHouse.setName("contInvestorHouse");
        this.f7InvestorHouse.setName("f7InvestorHouse");
        // CoreUI		
        this.setPreferredSize(new Dimension(700,400));
        // kDTabbedPane1		
        this.kDTabbedPane1.setPreferredSize(new Dimension(700,400));
        // pnlBaseInfo
        // contTerraNumber		
        this.contTerraNumber.setBoundLabelText(resHelper.getString("contTerraNumber.boundLabelText"));		
        this.contTerraNumber.setBoundLabelLength(100);		
        this.contTerraNumber.setBoundLabelUnderline(true);
        // txtTerraNumber		
        this.txtTerraNumber.setMaxLength(80);
        // contTerraArea		
        this.contTerraArea.setBoundLabelText(resHelper.getString("contTerraArea.boundLabelText"));		
        this.contTerraArea.setBoundLabelUnderline(true);		
        this.contTerraArea.setBoundLabelLength(100);
        // contTerraLicence		
        this.contTerraLicence.setBoundLabelText(resHelper.getString("contTerraLicence.boundLabelText"));		
        this.contTerraLicence.setBoundLabelLength(100);		
        this.contTerraLicence.setBoundLabelUnderline(true);
        // txtTerraLicence		
        this.txtTerraLicence.setMaxLength(80);
        // contTermBegin		
        this.contTermBegin.setBoundLabelText(resHelper.getString("contTermBegin.boundLabelText"));		
        this.contTermBegin.setBoundLabelLength(100);		
        this.contTermBegin.setBoundLabelUnderline(true);
        // contTermEnd		
        this.contTermEnd.setBoundLabelText(resHelper.getString("contTermEnd.boundLabelText"));		
        this.contTermEnd.setBoundLabelLength(50);		
        this.contTermEnd.setBoundLabelUnderline(true);
        // contTotalYear		
        this.contTotalYear.setBoundLabelText(resHelper.getString("contTotalYear.boundLabelText"));		
        this.contTotalYear.setBoundLabelUnderline(true);		
        this.contTotalYear.setBoundLabelLength(50);
        // txtTotalYear		
        this.txtTotalYear.setEnabled(false);
        // contTerraUse		
        this.contTerraUse.setBoundLabelText(resHelper.getString("contTerraUse.boundLabelText"));		
        this.contTerraUse.setBoundLabelLength(100);		
        this.contTerraUse.setBoundLabelUnderline(true);
        // txtTerraUse		
        this.txtTerraUse.setMaxLength(80);
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelLength(100);		
        this.contBuildingProperty.setBoundLabelUnderline(true);		
        this.contBuildingProperty.setVisible(false);
        // f7BuildingProperty		
        this.f7BuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");		
        this.f7BuildingProperty.setDisplayFormat("$name$");		
        this.f7BuildingProperty.setEditFormat("$number$");		
        this.f7BuildingProperty.setCommitFormat("$name$");		
        this.f7BuildingProperty.setVisible(false);
        // contSideEntironment		
        this.contSideEntironment.setBoundLabelText(resHelper.getString("contSideEntironment.boundLabelText"));		
        this.contSideEntironment.setBoundLabelLength(100);		
        this.contSideEntironment.setBoundLabelUnderline(true);
        // contCubageRate		
        this.contCubageRate.setBoundLabelText(resHelper.getString("contCubageRate.boundLabelText"));		
        this.contCubageRate.setBoundLabelLength(100);		
        this.contCubageRate.setBoundLabelUnderline(true);
        // contRealtyPaperNumber		
        this.contRealtyPaperNumber.setBoundLabelText(resHelper.getString("contRealtyPaperNumber.boundLabelText"));		
        this.contRealtyPaperNumber.setBoundLabelUnderline(true);		
        this.contRealtyPaperNumber.setBoundLabelLength(100);
        // txtRealtyPaperNumber		
        this.txtRealtyPaperNumber.setMaxLength(80);
        // contPresellLicence		
        this.contPresellLicence.setBoundLabelText(resHelper.getString("contPresellLicence.boundLabelText"));		
        this.contPresellLicence.setBoundLabelLength(100);		
        this.contPresellLicence.setBoundLabelUnderline(true);
        // txtPresellLicence		
        this.txtPresellLicence.setMaxLength(80);
        // contExportLicence		
        this.contExportLicence.setBoundLabelText(resHelper.getString("contExportLicence.boundLabelText"));		
        this.contExportLicence.setBoundLabelUnderline(true);		
        this.contExportLicence.setBoundLabelLength(100);
        // txtExportLicence		
        this.txtExportLicence.setMaxLength(50);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // f7Project		
        this.f7Project.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.f7Project.setDisplayFormat("$name$");		
        this.f7Project.setEditFormat("$number$");		
        this.f7Project.setCommitFormat("$number$");
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDSeparator5
        // kDSeparator6
        // pnlSubarea
        // kdpTermBegin
        this.kdpTermBegin.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    kdpTermBegin_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdpTermEnd
        this.kdpTermEnd.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    kdpTermEnd_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtTerraArea		
        this.txtTerraArea.setDataType(1);
        // txtCubageRate		
        this.txtCubageRate.setDataType(1);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // txtName		
        this.txtName.setMaxLength(80);
        // tblSubarea
		String tblSubareaStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblSubarea.setFormatXml(resHelper.translateString("tblSubarea",tblSubareaStrXML));

        

        // btnAddSubarea		
        this.btnAddSubarea.setText(resHelper.getString("btnAddSubarea.text"));
        this.btnAddSubarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddSubarea_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnRemoveSubarea		
        this.btnRemoveSubarea.setText(resHelper.getString("btnRemoveSubarea.text"));
        this.btnRemoveSubarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDeleteSubarea_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contProAdress		
        this.contProAdress.setBoundLabelText(resHelper.getString("contProAdress.boundLabelText"));		
        this.contProAdress.setBoundLabelLength(100);		
        this.contProAdress.setBoundLabelUnderline(true);
        // txtProAdress		
        this.txtProAdress.setMaxLength(50);
        // contFrameProperty		
        this.contFrameProperty.setBoundLabelText(resHelper.getString("contFrameProperty.boundLabelText"));		
        this.contFrameProperty.setBoundLabelLength(100);		
        this.contFrameProperty.setBoundLabelUnderline(true);
        // txtFrameProperty
        // kDScrollPane1
        // txtSideEntironment		
        this.txtSideEntironment.setMaxLength(300);
        // chkIsForSHE		
        this.chkIsForSHE.setText(resHelper.getString("chkIsForSHE.text"));
        // chkIsForTen		
        this.chkIsForTen.setText(resHelper.getString("chkIsForTen.text"));
        // chkIsForPPM		
        this.chkIsForPPM.setText(resHelper.getString("chkIsForPPM.text"));
        // pnlShareOrgUnit
        // tblShareOrgUnit
		String tblShareOrgUnitStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"orgUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"operationPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shareDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createOrgUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{orgUnit}</t:Cell><t:Cell>$Resource{operationPerson}</t:Cell><t:Cell>$Resource{shareDate}</t:Cell><t:Cell>$Resource{createOrgUnit}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblShareOrgUnit.setFormatXml(resHelper.translateString("tblShareOrgUnit",tblShareOrgUnitStrXML));

        

        // btnAddOrgUnit		
        this.btnAddOrgUnit.setText(resHelper.getString("btnAddOrgUnit.text"));
        this.btnAddOrgUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddOrgUnit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDelOrgUnit		
        this.btnDelOrgUnit.setText(resHelper.getString("btnDelOrgUnit.text"));
        this.btnDelOrgUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelOrgUnit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contProjectResource		
        this.contProjectResource.setBoundLabelText(resHelper.getString("contProjectResource.boundLabelText"));		
        this.contProjectResource.setBoundLabelLength(100);		
        this.contProjectResource.setBoundLabelUnderline(true);
        // comboProjecResource		
        this.comboProjecResource.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SellProjectResourceEnum").toArray());
        this.comboProjecResource.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboProjecResource_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contInvestorHouse		
        this.contInvestorHouse.setBoundLabelText(resHelper.getString("contInvestorHouse.boundLabelText"));		
        this.contInvestorHouse.setBoundLabelLength(100);		
        this.contInvestorHouse.setBoundLabelUnderline(true);
        // f7InvestorHouse		
        this.f7InvestorHouse.setCommitFormat("$number$");		
        this.f7InvestorHouse.setEditFormat("$number$");		
        this.f7InvestorHouse.setDisplayFormat("$name$");		
        this.f7InvestorHouse.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.InvestorHouseQuery");
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
        this.setBounds(new Rectangle(10, 10, 700, 400));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 700, 400));
        kDTabbedPane1.setBounds(new Rectangle(11, 10, 683, 380));
        this.add(kDTabbedPane1, new KDLayout.Constraints(11, 10, 683, 380, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDTabbedPane1
        kDTabbedPane1.add(pnlBaseInfo, resHelper.getString("pnlBaseInfo.constraints"));
        kDTabbedPane1.add(pnlSubarea, resHelper.getString("pnlSubarea.constraints"));
        kDTabbedPane1.add(pnlShareOrgUnit, resHelper.getString("pnlShareOrgUnit.constraints"));
        //pnlBaseInfo
        pnlBaseInfo.setLayout(new KDLayout());
        pnlBaseInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 682, 347));        contTerraNumber.setBounds(new Rectangle(10, 70, 270, 19));
        pnlBaseInfo.add(contTerraNumber, new KDLayout.Constraints(10, 70, 270, 19, 0));
        contTerraArea.setBounds(new Rectangle(10, 96, 270, 19));
        pnlBaseInfo.add(contTerraArea, new KDLayout.Constraints(10, 96, 270, 19, 0));
        contTerraLicence.setBounds(new Rectangle(10, 123, 270, 19));
        pnlBaseInfo.add(contTerraLicence, new KDLayout.Constraints(10, 123, 270, 19, 0));
        contTermBegin.setBounds(new Rectangle(10, 151, 210, 19));
        pnlBaseInfo.add(contTermBegin, new KDLayout.Constraints(10, 151, 210, 19, 0));
        contTermEnd.setBounds(new Rectangle(226, 151, 162, 19));
        pnlBaseInfo.add(contTermEnd, new KDLayout.Constraints(226, 151, 162, 19, 0));
        contTotalYear.setBounds(new Rectangle(396, 151, 103, 19));
        pnlBaseInfo.add(contTotalYear, new KDLayout.Constraints(396, 151, 103, 19, 0));
        contTerraUse.setBounds(new Rectangle(10, 179, 270, 19));
        pnlBaseInfo.add(contTerraUse, new KDLayout.Constraints(10, 179, 270, 19, 0));
        contBuildingProperty.setBounds(new Rectangle(336, 176, 270, 19));
        pnlBaseInfo.add(contBuildingProperty, new KDLayout.Constraints(336, 176, 270, 19, 0));
        contSideEntironment.setBounds(new Rectangle(10, 230, 598, 51));
        pnlBaseInfo.add(contSideEntironment, new KDLayout.Constraints(10, 230, 598, 51, 0));
        contCubageRate.setBounds(new Rectangle(335, 201, 270, 19));
        pnlBaseInfo.add(contCubageRate, new KDLayout.Constraints(335, 201, 270, 19, 0));
        contRealtyPaperNumber.setBounds(new Rectangle(10, 298, 270, 19));
        pnlBaseInfo.add(contRealtyPaperNumber, new KDLayout.Constraints(10, 298, 270, 19, 0));
        contPresellLicence.setBounds(new Rectangle(338, 298, 270, 19));
        pnlBaseInfo.add(contPresellLicence, new KDLayout.Constraints(338, 298, 270, 19, 0));
        contExportLicence.setBounds(new Rectangle(10, 324, 270, 19));
        pnlBaseInfo.add(contExportLicence, new KDLayout.Constraints(10, 324, 270, 19, 0));
        contProject.setBounds(new Rectangle(337, 37, 270, 19));
        pnlBaseInfo.add(contProject, new KDLayout.Constraints(337, 37, 270, 19, 0));
        kDLabel1.setBounds(new Rectangle(507, 152, 27, 19));
        pnlBaseInfo.add(kDLabel1, new KDLayout.Constraints(507, 152, 27, 19, 0));
        kDSeparator5.setBounds(new Rectangle(4, 61, 688, 8));
        pnlBaseInfo.add(kDSeparator5, new KDLayout.Constraints(4, 61, 688, 8, 0));
        kDSeparator6.setBounds(new Rectangle(-1, 287, 681, 9));
        pnlBaseInfo.add(kDSeparator6, new KDLayout.Constraints(-1, 287, 681, 9, 0));
        contNumber.setBounds(new Rectangle(10, 8, 270, 19));
        pnlBaseInfo.add(contNumber, new KDLayout.Constraints(10, 8, 270, 19, 0));
        contName.setBounds(new Rectangle(338, 8, 270, 19));
        pnlBaseInfo.add(contName, new KDLayout.Constraints(338, 8, 270, 19, 0));
        contProAdress.setBounds(new Rectangle(338, 324, 270, 19));
        pnlBaseInfo.add(contProAdress, new KDLayout.Constraints(338, 324, 270, 19, 0));
        contFrameProperty.setBounds(new Rectangle(10, 204, 270, 19));
        pnlBaseInfo.add(contFrameProperty, new KDLayout.Constraints(10, 204, 270, 19, 0));
        chkIsForSHE.setBounds(new Rectangle(338, 70, 140, 19));
        pnlBaseInfo.add(chkIsForSHE, new KDLayout.Constraints(338, 70, 140, 19, 0));
        chkIsForTen.setBounds(new Rectangle(338, 96, 140, 19));
        pnlBaseInfo.add(chkIsForTen, new KDLayout.Constraints(338, 96, 140, 19, 0));
        chkIsForPPM.setBounds(new Rectangle(338, 123, 140, 19));
        pnlBaseInfo.add(chkIsForPPM, new KDLayout.Constraints(338, 123, 140, 19, 0));
        contProjectResource.setBounds(new Rectangle(11, 38, 270, 19));
        pnlBaseInfo.add(contProjectResource, new KDLayout.Constraints(11, 38, 270, 19, 0));
        contInvestorHouse.setBounds(new Rectangle(340, 38, 266, 19));
        pnlBaseInfo.add(contInvestorHouse, new KDLayout.Constraints(340, 38, 266, 19, 0));
        //contTerraNumber
        contTerraNumber.setBoundEditor(txtTerraNumber);
        //contTerraArea
        contTerraArea.setBoundEditor(txtTerraArea);
        //contTerraLicence
        contTerraLicence.setBoundEditor(txtTerraLicence);
        //contTermBegin
        contTermBegin.setBoundEditor(kdpTermBegin);
        //contTermEnd
        contTermEnd.setBoundEditor(kdpTermEnd);
        //contTotalYear
        contTotalYear.setBoundEditor(txtTotalYear);
        //contTerraUse
        contTerraUse.setBoundEditor(txtTerraUse);
        //contBuildingProperty
        contBuildingProperty.setBoundEditor(f7BuildingProperty);
        //contSideEntironment
        contSideEntironment.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtSideEntironment, null);
        //contCubageRate
        contCubageRate.setBoundEditor(txtCubageRate);
        //contRealtyPaperNumber
        contRealtyPaperNumber.setBoundEditor(txtRealtyPaperNumber);
        //contPresellLicence
        contPresellLicence.setBoundEditor(txtPresellLicence);
        //contExportLicence
        contExportLicence.setBoundEditor(txtExportLicence);
        //contProject
        contProject.setBoundEditor(f7Project);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contProAdress
        contProAdress.setBoundEditor(txtProAdress);
        //contFrameProperty
        contFrameProperty.setBoundEditor(txtFrameProperty);
        //contProjectResource
        contProjectResource.setBoundEditor(comboProjecResource);
        //contInvestorHouse
        contInvestorHouse.setBoundEditor(f7InvestorHouse);
        //pnlSubarea
        pnlSubarea.setLayout(new KDLayout());
        pnlSubarea.putClientProperty("OriginalBounds", new Rectangle(0, 0, 682, 347));        tblSubarea.setBounds(new Rectangle(1, 41, 677, 306));
        pnlSubarea.add(tblSubarea, new KDLayout.Constraints(1, 41, 677, 306, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        btnAddSubarea.setBounds(new Rectangle(432, 8, 88, 25));
        pnlSubarea.add(btnAddSubarea, new KDLayout.Constraints(432, 8, 88, 25, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        btnRemoveSubarea.setBounds(new Rectangle(554, 7, 88, 26));
        pnlSubarea.add(btnRemoveSubarea, new KDLayout.Constraints(554, 7, 88, 26, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        //pnlShareOrgUnit
        pnlShareOrgUnit.setLayout(new KDLayout());
        pnlShareOrgUnit.putClientProperty("OriginalBounds", new Rectangle(0, 0, 682, 347));        tblShareOrgUnit.setBounds(new Rectangle(10, 34, 661, 305));
        pnlShareOrgUnit.add(tblShareOrgUnit, new KDLayout.Constraints(10, 34, 661, 305, 0));
        btnAddOrgUnit.setBounds(new Rectangle(485, 10, 94, 19));
        pnlShareOrgUnit.add(btnAddOrgUnit, new KDLayout.Constraints(485, 10, 94, 19, 0));
        btnDelOrgUnit.setBounds(new Rectangle(587, 10, 82, 19));
        pnlShareOrgUnit.add(btnDelOrgUnit, new KDLayout.Constraints(587, 10, 82, 19, 0));

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
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SellProjectEditUIHandler";
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
        this.editData = (com.kingdee.eas.framework.CoreBaseInfo)ov;
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
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output kdpTermBegin_dataChanged method
     */
    protected void kdpTermBegin_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output kdpTermEnd_dataChanged method
     */
    protected void kdpTermEnd_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnAddSubarea_actionPerformed method
     */
    protected void btnAddSubarea_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnDeleteSubarea_actionPerformed method
     */
    protected void btnDeleteSubarea_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnAddOrgUnit_actionPerformed method
     */
    protected void btnAddOrgUnit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnDelOrgUnit_actionPerformed method
     */
    protected void btnDelOrgUnit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output comboProjecResource_itemStateChanged method
     */
    protected void comboProjecResource_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SellProjectEditUI");
    }




}