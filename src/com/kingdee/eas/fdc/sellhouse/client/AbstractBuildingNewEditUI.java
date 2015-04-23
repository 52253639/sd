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
public abstract class AbstractBuildingNewEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBuildingNewEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel tabBuildingInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjectName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSubarea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contJoinInDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompleteDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contConstructPart;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingHeight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingTerraArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingStructure;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdministrativeNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFloorHeight;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUseRate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isGetCertificated;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellCertifiNum;
    protected com.kingdee.bos.ctrl.swing.KDLabel lbUnits;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellCertifiDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStructureDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBanBasedataEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProjectName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Subarea;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiFloorCount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkJoinInDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCompleteDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ConstructPart;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingHeight;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingTerraArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox comboBuildingStructure;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ProductType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAdministrativeNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BuildingProperty;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spSubFloor;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCodingType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDStartDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField tfBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField tfFloorHeight;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField tfUserRate;
    protected com.kingdee.bos.ctrl.swing.KDTextField kDSellCertifiNum;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkSellCertifiDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStructureDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBanBasedataEntry;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractBuildingNewEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBuildingNewEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.tabPanel = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.tabBuildingInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contProjectNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjectName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSubarea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloorCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contJoinInDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompleteDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contConstructPart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingHeight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingTerraArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingStructure = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdministrativeNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingProperty = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFloorHeight = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUseRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.isGetCertificated = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contSellCertifiNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lbUnits = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contSellCertifiDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStructureDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBanBasedataEntry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtProjectNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProjectName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Subarea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.spiFloorCount = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.pkJoinInDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkCompleteDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7ConstructPart = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtBuildingHeight = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBuildingTerraArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboBuildingStructure = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7ProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAdministrativeNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7BuildingProperty = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.spSubFloor = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.comboCodingType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.tfBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.tfFloorHeight = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.tfUserRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDSellCertifiNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkSellCertifiDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkStructureDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtBanBasedataEntry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tabPanel.setName("tabPanel");
        this.tabBuildingInfo.setName("tabBuildingInfo");
        this.contProjectNumber.setName("contProjectNumber");
        this.contNumber.setName("contNumber");
        this.contProjectName.setName("contProjectName");
        this.contName.setName("contName");
        this.contSubarea.setName("contSubarea");
        this.contFloorCount.setName("contFloorCount");
        this.contJoinInDate.setName("contJoinInDate");
        this.contCompleteDate.setName("contCompleteDate");
        this.contConstructPart.setName("contConstructPart");
        this.contBuildingHeight.setName("contBuildingHeight");
        this.contBuildingTerraArea.setName("contBuildingTerraArea");
        this.contBuildingStructure.setName("contBuildingStructure");
        this.contProductType.setName("contProductType");
        this.contAdministrativeNumber.setName("contAdministrativeNumber");
        this.contBuildingProperty.setName("contBuildingProperty");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contStartDate.setName("contStartDate");
        this.contBuildingArea.setName("contBuildingArea");
        this.contFloorHeight.setName("contFloorHeight");
        this.contUseRate.setName("contUseRate");
        this.isGetCertificated.setName("isGetCertificated");
        this.contSellCertifiNum.setName("contSellCertifiNum");
        this.lbUnits.setName("lbUnits");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.contSellCertifiDate.setName("contSellCertifiDate");
        this.contStructureDate.setName("contStructureDate");
        this.contBanBasedataEntry.setName("contBanBasedataEntry");
        this.kdtEntry.setName("kdtEntry");
        this.txtProjectNumber.setName("txtProjectNumber");
        this.txtNumber.setName("txtNumber");
        this.txtProjectName.setName("txtProjectName");
        this.txtName.setName("txtName");
        this.f7Subarea.setName("f7Subarea");
        this.spiFloorCount.setName("spiFloorCount");
        this.pkJoinInDate.setName("pkJoinInDate");
        this.pkCompleteDate.setName("pkCompleteDate");
        this.f7ConstructPart.setName("f7ConstructPart");
        this.txtBuildingHeight.setName("txtBuildingHeight");
        this.txtBuildingTerraArea.setName("txtBuildingTerraArea");
        this.comboBuildingStructure.setName("comboBuildingStructure");
        this.f7ProductType.setName("f7ProductType");
        this.txtAdministrativeNumber.setName("txtAdministrativeNumber");
        this.f7BuildingProperty.setName("f7BuildingProperty");
        this.spSubFloor.setName("spSubFloor");
        this.comboCodingType.setName("comboCodingType");
        this.kDStartDate.setName("kDStartDate");
        this.tfBuildingArea.setName("tfBuildingArea");
        this.tfFloorHeight.setName("tfFloorHeight");
        this.tfUserRate.setName("tfUserRate");
        this.kDSellCertifiNum.setName("kDSellCertifiNum");
        this.pkSellCertifiDate.setName("pkSellCertifiDate");
        this.pkStructureDate.setName("pkStructureDate");
        this.prmtBanBasedataEntry.setName("prmtBanBasedataEntry");
        // CoreUI		
        this.setPreferredSize(new Dimension(650,500));		
        this.btnCopy.setEnabled(false);		
        this.btnCopy.setVisible(false);
        // tabPanel
        // tabBuildingInfo		
        this.tabBuildingInfo.setBorder(null);
        // contProjectNumber		
        this.contProjectNumber.setBoundLabelText(resHelper.getString("contProjectNumber.boundLabelText"));		
        this.contProjectNumber.setBoundLabelLength(100);		
        this.contProjectNumber.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contProjectName		
        this.contProjectName.setBoundLabelText(resHelper.getString("contProjectName.boundLabelText"));		
        this.contProjectName.setBoundLabelLength(100);		
        this.contProjectName.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contSubarea		
        this.contSubarea.setBoundLabelText(resHelper.getString("contSubarea.boundLabelText"));		
        this.contSubarea.setBoundLabelLength(100);		
        this.contSubarea.setBoundLabelUnderline(true);		
        this.contSubarea.setVisible(false);		
        this.contSubarea.setEnabled(false);
        // contFloorCount		
        this.contFloorCount.setBoundLabelText(resHelper.getString("contFloorCount.boundLabelText"));		
        this.contFloorCount.setBoundLabelLength(100);		
        this.contFloorCount.setBoundLabelUnderline(true);
        // contJoinInDate		
        this.contJoinInDate.setBoundLabelText(resHelper.getString("contJoinInDate.boundLabelText"));		
        this.contJoinInDate.setBoundLabelLength(100);		
        this.contJoinInDate.setBoundLabelUnderline(true);
        // contCompleteDate		
        this.contCompleteDate.setBoundLabelText(resHelper.getString("contCompleteDate.boundLabelText"));		
        this.contCompleteDate.setBoundLabelLength(100);		
        this.contCompleteDate.setBoundLabelUnderline(true);
        // contConstructPart		
        this.contConstructPart.setBoundLabelText(resHelper.getString("contConstructPart.boundLabelText"));		
        this.contConstructPart.setBoundLabelLength(100);		
        this.contConstructPart.setBoundLabelUnderline(true);		
        this.contConstructPart.setVisible(false);		
        this.contConstructPart.setEnabled(false);
        // contBuildingHeight		
        this.contBuildingHeight.setBoundLabelText(resHelper.getString("contBuildingHeight.boundLabelText"));		
        this.contBuildingHeight.setBoundLabelLength(100);		
        this.contBuildingHeight.setBoundLabelUnderline(true);
        // contBuildingTerraArea		
        this.contBuildingTerraArea.setBoundLabelText(resHelper.getString("contBuildingTerraArea.boundLabelText"));		
        this.contBuildingTerraArea.setBoundLabelLength(100);		
        this.contBuildingTerraArea.setBoundLabelUnderline(true);		
        this.contBuildingTerraArea.setEnabled(false);		
        this.contBuildingTerraArea.setVisible(false);
        // contBuildingStructure		
        this.contBuildingStructure.setBoundLabelText(resHelper.getString("contBuildingStructure.boundLabelText"));		
        this.contBuildingStructure.setBoundLabelLength(100);		
        this.contBuildingStructure.setBoundLabelUnderline(true);
        // contProductType		
        this.contProductType.setBoundLabelText(resHelper.getString("contProductType.boundLabelText"));		
        this.contProductType.setBoundLabelLength(100);		
        this.contProductType.setBoundLabelUnderline(true);
        // contAdministrativeNumber		
        this.contAdministrativeNumber.setBoundLabelText(resHelper.getString("contAdministrativeNumber.boundLabelText"));		
        this.contAdministrativeNumber.setBoundLabelLength(100);		
        this.contAdministrativeNumber.setBoundLabelUnderline(true);		
        this.contAdministrativeNumber.setVisible(false);		
        this.contAdministrativeNumber.setEnabled(false);
        // contBuildingProperty		
        this.contBuildingProperty.setBoundLabelText(resHelper.getString("contBuildingProperty.boundLabelText"));		
        this.contBuildingProperty.setBoundLabelLength(100);		
        this.contBuildingProperty.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setVisible(false);		
        this.kDLabelContainer1.setEnabled(false);
        // contStartDate		
        this.contStartDate.setBoundLabelText(resHelper.getString("contStartDate.boundLabelText"));		
        this.contStartDate.setBoundLabelLength(100);		
        this.contStartDate.setBoundLabelUnderline(true);
        // contBuildingArea		
        this.contBuildingArea.setBoundLabelText(resHelper.getString("contBuildingArea.boundLabelText"));		
        this.contBuildingArea.setBoundLabelLength(100);		
        this.contBuildingArea.setBoundLabelUnderline(true);
        // contFloorHeight		
        this.contFloorHeight.setBoundLabelText(resHelper.getString("contFloorHeight.boundLabelText"));		
        this.contFloorHeight.setBoundLabelLength(100);		
        this.contFloorHeight.setBoundLabelUnderline(true);
        // contUseRate		
        this.contUseRate.setBoundLabelText(resHelper.getString("contUseRate.boundLabelText"));		
        this.contUseRate.setBoundLabelLength(100);		
        this.contUseRate.setBoundLabelUnderline(true);
        // isGetCertificated		
        this.isGetCertificated.setText(resHelper.getString("isGetCertificated.text"));
        this.isGetCertificated.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    isGetCertificated_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contSellCertifiNum		
        this.contSellCertifiNum.setBoundLabelText(resHelper.getString("contSellCertifiNum.boundLabelText"));		
        this.contSellCertifiNum.setBoundLabelLength(100);		
        this.contSellCertifiNum.setBoundLabelUnderline(true);		
        this.contSellCertifiNum.setVisible(false);
        // lbUnits		
        this.lbUnits.setText(resHelper.getString("lbUnits.text"));
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));		
        this.kDLabel1.setEnabled(false);		
        this.kDLabel1.setVisible(false);
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // contSellCertifiDate		
        this.contSellCertifiDate.setBoundLabelText(resHelper.getString("contSellCertifiDate.boundLabelText"));		
        this.contSellCertifiDate.setBoundLabelLength(100);		
        this.contSellCertifiDate.setBoundLabelUnderline(true);
        // contStructureDate		
        this.contStructureDate.setBoundLabelText(resHelper.getString("contStructureDate.boundLabelText"));		
        this.contStructureDate.setBoundLabelLength(100);		
        this.contStructureDate.setBoundLabelUnderline(true);
        // contBanBasedataEntry		
        this.contBanBasedataEntry.setBoundLabelText(resHelper.getString("contBanBasedataEntry.boundLabelText"));		
        this.contBanBasedataEntry.setBoundLabelLength(100);		
        this.contBanBasedataEntry.setBoundLabelUnderline(true);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"floor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"area\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{floor}</t:Cell><t:Cell>$Resource{area}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));

        

        // txtProjectNumber
        // txtNumber
        // txtProjectName
        // txtName
        // f7Subarea		
        this.f7Subarea.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SubareaQuery");		
        this.f7Subarea.setCommitFormat("$number$");		
        this.f7Subarea.setEditFormat("$number$");		
        this.f7Subarea.setDisplayFormat("$name$");
        // spiFloorCount
        this.spiFloorCount.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spiFloorCount_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.spiFloorCount.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    spiFloorCount_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkJoinInDate
        // pkCompleteDate
        // f7ConstructPart		
        this.f7ConstructPart.setDisplayFormat("$name$");		
        this.f7ConstructPart.setEditFormat("$number$");		
        this.f7ConstructPart.setCommitFormat("$number$");		
        this.f7ConstructPart.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");
        // txtBuildingHeight		
        this.txtBuildingHeight.setDataType(4);
        // txtBuildingTerraArea
        // comboBuildingStructure		
        this.comboBuildingStructure.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingStructureQuery");		
        this.comboBuildingStructure.setCommitFormat("$number$");		
        this.comboBuildingStructure.setEditFormat("$number$");		
        this.comboBuildingStructure.setDisplayFormat("$name$");
        // f7ProductType		
        this.f7ProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");		
        this.f7ProductType.setCommitFormat("$number$");		
        this.f7ProductType.setDisplayFormat("$name$");		
        this.f7ProductType.setEditFormat("$number$");		
        this.f7ProductType.setRequired(true);
        // txtAdministrativeNumber		
        this.txtAdministrativeNumber.setMaxLength(80);
        // f7BuildingProperty		
        this.f7BuildingProperty.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery");		
        this.f7BuildingProperty.setCommitFormat("$number$");		
        this.f7BuildingProperty.setEditFormat("$number$");		
        this.f7BuildingProperty.setDisplayFormat("$name$");
        // spSubFloor		
        this.spSubFloor.setRequired(true);
        this.spSubFloor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    spSubFloor_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.spSubFloor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spSubFloor_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboCodingType		
        this.comboCodingType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CodingTypeEnum").toArray());
        this.comboCodingType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    comboCodingType_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDStartDate
        // tfBuildingArea		
        this.tfBuildingArea.setPrecision(2);
        // tfFloorHeight		
        this.tfFloorHeight.setDataType(4);
        // tfUserRate		
        this.tfUserRate.setDataType(1);
        this.tfUserRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    tfUserRate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDSellCertifiNum
        // pkSellCertifiDate		
        this.pkSellCertifiDate.setRequired(true);
        // pkStructureDate
        // prmtBanBasedataEntry		
        this.prmtBanBasedataEntry.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BanBasedataEntryQuery");		
        this.prmtBanBasedataEntry.setCommitFormat("$BanNumber$");		
        this.prmtBanBasedataEntry.setDisplayFormat("$BanNumber$");		
        this.prmtBanBasedataEntry.setEditFormat("$BanNumber$");
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
        this.setBounds(new Rectangle(10, 10, 650, 500));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 650, 500));
        tabPanel.setBounds(new Rectangle(8, 5, 629, 487));
        this.add(tabPanel, new KDLayout.Constraints(8, 5, 629, 487, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //tabPanel
        tabPanel.add(tabBuildingInfo, resHelper.getString("tabBuildingInfo.constraints"));
        //tabBuildingInfo
        tabBuildingInfo.setLayout(null);        contProjectNumber.setBounds(new Rectangle(7, 10, 270, 19));
        tabBuildingInfo.add(contProjectNumber, null);
        contNumber.setBounds(new Rectangle(7, 32, 270, 19));
        tabBuildingInfo.add(contNumber, null);
        contProjectName.setBounds(new Rectangle(341, 10, 270, 19));
        tabBuildingInfo.add(contProjectName, null);
        contName.setBounds(new Rectangle(341, 32, 270, 19));
        tabBuildingInfo.add(contName, null);
        contSubarea.setBounds(new Rectangle(532, 415, 270, 19));
        tabBuildingInfo.add(contSubarea, null);
        contFloorCount.setBounds(new Rectangle(7, 186, 270, 19));
        tabBuildingInfo.add(contFloorCount, null);
        contJoinInDate.setBounds(new Rectangle(341, 98, 270, 19));
        tabBuildingInfo.add(contJoinInDate, null);
        contCompleteDate.setBounds(new Rectangle(341, 120, 270, 19));
        tabBuildingInfo.add(contCompleteDate, null);
        contConstructPart.setBounds(new Rectangle(534, 354, 270, 19));
        tabBuildingInfo.add(contConstructPart, null);
        contBuildingHeight.setBounds(new Rectangle(7, 164, 270, 19));
        tabBuildingInfo.add(contBuildingHeight, null);
        contBuildingTerraArea.setBounds(new Rectangle(542, 430, 230, 19));
        tabBuildingInfo.add(contBuildingTerraArea, null);
        contBuildingStructure.setBounds(new Rectangle(7, 76, 270, 19));
        tabBuildingInfo.add(contBuildingStructure, null);
        contProductType.setBounds(new Rectangle(341, 54, 270, 19));
        tabBuildingInfo.add(contProductType, null);
        contAdministrativeNumber.setBounds(new Rectangle(480, 399, 270, 19));
        tabBuildingInfo.add(contAdministrativeNumber, null);
        contBuildingProperty.setBounds(new Rectangle(341, 76, 270, 19));
        tabBuildingInfo.add(contBuildingProperty, null);
        kDLabelContainer2.setBounds(new Rectangle(341, 186, 270, 19));
        tabBuildingInfo.add(kDLabelContainer2, null);
        kDLabelContainer1.setBounds(new Rectangle(577, 369, 270, 19));
        tabBuildingInfo.add(kDLabelContainer1, null);
        contStartDate.setBounds(new Rectangle(7, 98, 270, 19));
        tabBuildingInfo.add(contStartDate, null);
        contBuildingArea.setBounds(new Rectangle(7, 142, 230, 19));
        tabBuildingInfo.add(contBuildingArea, null);
        contFloorHeight.setBounds(new Rectangle(341, 164, 270, 19));
        tabBuildingInfo.add(contFloorHeight, null);
        contUseRate.setBounds(new Rectangle(341, 142, 230, 19));
        tabBuildingInfo.add(contUseRate, null);
        isGetCertificated.setBounds(new Rectangle(7, 217, 169, 19));
        tabBuildingInfo.add(isGetCertificated, null);
        contSellCertifiNum.setBounds(new Rectangle(7, 240, 270, 19));
        tabBuildingInfo.add(contSellCertifiNum, null);
        lbUnits.setBounds(new Rectangle(242, 142, 39, 19));
        tabBuildingInfo.add(lbUnits, null);
        kDLabel1.setBounds(new Rectangle(496, 343, 37, 19));
        tabBuildingInfo.add(kDLabel1, null);
        kDLabel2.setBounds(new Rectangle(578, 142, 39, 19));
        tabBuildingInfo.add(kDLabel2, null);
        contSellCertifiDate.setBounds(new Rectangle(341, 240, 270, 19));
        tabBuildingInfo.add(contSellCertifiDate, null);
        contStructureDate.setBounds(new Rectangle(7, 120, 270, 19));
        tabBuildingInfo.add(contStructureDate, null);
        contBanBasedataEntry.setBounds(new Rectangle(7, 54, 270, 19));
        tabBuildingInfo.add(contBanBasedataEntry, null);
        kdtEntry.setBounds(new Rectangle(6, 265, 605, 186));
        tabBuildingInfo.add(kdtEntry, null);
        //contProjectNumber
        contProjectNumber.setBoundEditor(txtProjectNumber);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contProjectName
        contProjectName.setBoundEditor(txtProjectName);
        //contName
        contName.setBoundEditor(txtName);
        //contSubarea
        contSubarea.setBoundEditor(f7Subarea);
        //contFloorCount
        contFloorCount.setBoundEditor(spiFloorCount);
        //contJoinInDate
        contJoinInDate.setBoundEditor(pkJoinInDate);
        //contCompleteDate
        contCompleteDate.setBoundEditor(pkCompleteDate);
        //contConstructPart
        contConstructPart.setBoundEditor(f7ConstructPart);
        //contBuildingHeight
        contBuildingHeight.setBoundEditor(txtBuildingHeight);
        //contBuildingTerraArea
        contBuildingTerraArea.setBoundEditor(txtBuildingTerraArea);
        //contBuildingStructure
        contBuildingStructure.setBoundEditor(comboBuildingStructure);
        //contProductType
        contProductType.setBoundEditor(f7ProductType);
        //contAdministrativeNumber
        contAdministrativeNumber.setBoundEditor(txtAdministrativeNumber);
        //contBuildingProperty
        contBuildingProperty.setBoundEditor(f7BuildingProperty);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(spSubFloor);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(comboCodingType);
        //contStartDate
        contStartDate.setBoundEditor(kDStartDate);
        //contBuildingArea
        contBuildingArea.setBoundEditor(tfBuildingArea);
        //contFloorHeight
        contFloorHeight.setBoundEditor(tfFloorHeight);
        //contUseRate
        contUseRate.setBoundEditor(tfUserRate);
        //contSellCertifiNum
        contSellCertifiNum.setBoundEditor(kDSellCertifiNum);
        //contSellCertifiDate
        contSellCertifiDate.setBoundEditor(pkSellCertifiDate);
        //contStructureDate
        contStructureDate.setBoundEditor(pkStructureDate);
        //contBanBasedataEntry
        contBanBasedataEntry.setBoundEditor(prmtBanBasedataEntry);

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
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
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
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnReset);
        this.toolBar.add(kDSeparatorCloud);
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
	    return "com.kingdee.eas.fdc.sellhouse.app.BuildingNewEditUIHandler";
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
     * output isGetCertificated_actionPerformed method
     */
    protected void isGetCertificated_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output spiFloorCount_stateChanged method
     */
    protected void spiFloorCount_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output spiFloorCount_propertyChange method
     */
    protected void spiFloorCount_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output spSubFloor_propertyChange method
     */
    protected void spSubFloor_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output spSubFloor_stateChanged method
     */
    protected void spSubFloor_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output comboCodingType_actionPerformed method
     */
    protected void comboCodingType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tfUserRate_dataChanged method
     */
    protected void tfUserRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "BuildingNewEditUI");
    }




}