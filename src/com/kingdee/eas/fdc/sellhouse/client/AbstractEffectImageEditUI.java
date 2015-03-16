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
public abstract class AbstractEffectImageEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractEffectImageEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuilding;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuildingFloor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuildingUnit;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilding;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingFloor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingUnit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsShowUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer conElementEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblElementEntry;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImage;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImage;
    protected com.kingdee.bos.ctrl.swing.KDContainer conImage;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane backgroundPanel;
    protected com.kingdee.eas.fdc.sellhouse.EffectImageInfo editData = null;
    protected ActionImage actionImage = null;
    protected ActionEditHotspot actionEditHotspot = null;
    protected ActionSaveHotspot actionSaveHotspot = null;
    /**
     * output class constructor
     */
    public AbstractEffectImageEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractEffectImageEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionImage
        this.actionImage = new ActionImage(this);
        getActionManager().registerAction("actionImage", actionImage);
         this.actionImage.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEditHotspot
        this.actionEditHotspot = new ActionEditHotspot(this);
        getActionManager().registerAction("actionEditHotspot", actionEditHotspot);
         this.actionEditHotspot.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSaveHotspot
        this.actionSaveHotspot = new ActionSaveHotspot(this);
        getActionManager().registerAction("actionSaveHotspot", actionSaveHotspot);
         this.actionSaveHotspot.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBuilding = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBuildingFloor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBuildingUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingFloor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsShowUnit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.conElementEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblElementEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.menuItemImage = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnImage = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.conImage = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.backgroundPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtNumber.setName("txtNumber");
        this.prmtOrgUnit.setName("prmtOrgUnit");
        this.txtName.setName("txtName");
        this.comboType.setName("comboType");
        this.prmtSellProject.setName("prmtSellProject");
        this.prmtBuilding.setName("prmtBuilding");
        this.prmtBuildingFloor.setName("prmtBuildingFloor");
        this.prmtBuildingUnit.setName("prmtBuildingUnit");
        this.kDContainer1.setName("kDContainer1");
        this.contName.setName("contName");
        this.contType.setName("contType");
        this.contBuilding.setName("contBuilding");
        this.contBuildingFloor.setName("contBuildingFloor");
        this.contSellProject.setName("contSellProject");
        this.contBuildingUnit.setName("contBuildingUnit");
        this.chkIsShowUnit.setName("chkIsShowUnit");
        this.contOrgUnit.setName("contOrgUnit");
        this.contNumber.setName("contNumber");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.conElementEntry.setName("conElementEntry");
        this.tblElementEntry.setName("tblElementEntry");
        this.menuItemImage.setName("menuItemImage");
        this.btnImage.setName("btnImage");
        this.conImage.setName("conImage");
        this.kDPanel1.setName("kDPanel1");
        this.backgroundPanel.setName("backgroundPanel");
        // CoreUI		
        this.btnPageSetup.setEnabled(false);		
        this.btnSubmit.setVisible(false);		
        this.btnSubmit.setEnabled(false);		
        this.btnCopy.setVisible(false);		
        this.btnCopy.setEnabled(false);		
        this.btnFirst.setVisible(false);		
        this.btnFirst.setEnabled(false);		
        this.btnPre.setVisible(false);		
        this.btnPre.setEnabled(false);		
        this.btnNext.setVisible(false);		
        this.btnNext.setEnabled(false);		
        this.btnLast.setVisible(false);		
        this.btnLast.setEnabled(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrint.setEnabled(false);		
        this.btnPrintPreview.setVisible(false);		
        this.btnPrintPreview.setEnabled(false);		
        this.menuItemSubmit.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setVisible(false);		
        this.menuView.setEnabled(false);		
        this.btnAttachment.setVisible(false);		
        this.btnAttachment.setEnabled(false);		
        this.menuSubmitOption.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.separatorFile1.setVisible(false);		
        this.menuBiz.setVisible(false);		
        this.menuBiz.setEnabled(false);		
        this.separatorFW1.setVisible(false);		
        this.separatorFW1.setEnabled(false);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW2.setEnabled(false);		
        this.separatorFW3.setVisible(false);		
        this.separatorFW3.setEnabled(false);		
        this.btnAddLine.setVisible(false);		
        this.btnAddLine.setEnabled(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnInsertLine.setEnabled(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnRemoveLine.setEnabled(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnCreateFrom.setEnabled(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceUp.setEnabled(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnTraceDown.setEnabled(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnAuditResult.setEnabled(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.separator1.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnMultiapprove.setEnabled(false);		
        this.btnNextPerson.setVisible(false);		
        this.btnNextPerson.setEnabled(false);		
        this.btnVoucher.setEnabled(false);		
        this.btnDelVoucher.setEnabled(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.btnWorkFlowG.setEnabled(false);		
        this.menuTable1.setVisible(false);		
        this.menuTable1.setEnabled(false);		
        this.menuWorkflow.setVisible(false);		
        this.menuWorkflow.setEnabled(false);		
        this.separatorFW8.setVisible(false);		
        this.separatorFW8.setEnabled(false);		
        this.separatorFW9.setVisible(false);		
        this.separatorFW9.setEnabled(false);		
        this.separatorFW7.setVisible(false);		
        this.separatorFW7.setEnabled(false);		
        this.btnCreateTo.setEnabled(false);		
        this.btnAudit.setVisible(false);		
        this.btnAudit.setEnabled(false);		
        this.btnUnAudit.setVisible(false);		
        this.btnUnAudit.setEnabled(false);		
        this.btnCalculator.setVisible(false);		
        this.btnCalculator.setEnabled(false);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // prmtOrgUnit		
        this.prmtOrgUnit.setEnabled(false);
        // txtName		
        this.txtName.setRequired(true);
        // comboType		
        this.comboType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.EffectImageEnum").toArray());
        this.comboType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtSellProject		
        this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");
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
        // prmtBuildingFloor		
        this.prmtBuildingFloor.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingFloorEntryQuery");
        this.prmtBuildingFloor.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtBuildingFloor_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtBuildingUnit		
        this.prmtBuildingUnit.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingUnitQuery");
        this.prmtBuildingUnit.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtBuildingUnit_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contType		
        this.contType.setBoundLabelText(resHelper.getString("contType.boundLabelText"));		
        this.contType.setBoundLabelLength(100);		
        this.contType.setBoundLabelUnderline(true);
        // contBuilding		
        this.contBuilding.setBoundLabelText(resHelper.getString("contBuilding.boundLabelText"));		
        this.contBuilding.setBoundLabelLength(100);		
        this.contBuilding.setBoundLabelUnderline(true);
        // contBuildingFloor		
        this.contBuildingFloor.setBoundLabelText(resHelper.getString("contBuildingFloor.boundLabelText"));		
        this.contBuildingFloor.setBoundLabelLength(100);		
        this.contBuildingFloor.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contBuildingUnit		
        this.contBuildingUnit.setBoundLabelText(resHelper.getString("contBuildingUnit.boundLabelText"));		
        this.contBuildingUnit.setBoundLabelLength(100);		
        this.contBuildingUnit.setBoundLabelUnderline(true);
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
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(100);		
        this.contOrgUnit.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(160);
        // conElementEntry		
        this.conElementEntry.setTitle(resHelper.getString("conElementEntry.title"));
        // tblElementEntry
		String tblElementEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"elementName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"hotspot1\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"hotspot2\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"elementID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{elementName}</t:Cell><t:Cell>$Resource{hotspot1}</t:Cell><t:Cell>$Resource{hotspot2}</t:Cell><t:Cell>$Resource{elementID}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblElementEntry.setFormatXml(resHelper.translateString("tblElementEntry",tblElementEntryStrXML));
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

                this.tblElementEntry.putBindContents("editData",new String[] {"type","elementName","hotspot1","hotspot2","elementID"});


        // menuItemImage
        this.menuItemImage.setAction((IItemAction)ActionProxyFactory.getProxy(actionImage, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImage.setText(resHelper.getString("menuItemImage.text"));		
        this.menuItemImage.setToolTipText(resHelper.getString("menuItemImage.toolTipText"));		
        this.menuItemImage.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));
        // btnImage
        this.btnImage.setAction((IItemAction)ActionProxyFactory.getProxy(actionImage, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImage.setText(resHelper.getString("btnImage.text"));		
        this.btnImage.setToolTipText(resHelper.getString("btnImage.toolTipText"));		
        this.btnImage.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));
        // conImage		
        this.conImage.setTitle(resHelper.getString("conImage.title"));
        // kDPanel1
        // backgroundPanel
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
        kDContainer1.setBounds(new Rectangle(14, 7, 985, 142));
        this.add(kDContainer1, new KDLayout.Constraints(14, 7, 985, 142, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSplitPane1.setBounds(new Rectangle(14, 154, 985, 464));
        this.add(kDSplitPane1, new KDLayout.Constraints(14, 154, 985, 464, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(14, 7, 985, 142));        contName.setBounds(new Rectangle(353, 12, 270, 19));
        kDContainer1.getContentPane().add(contName, new KDLayout.Constraints(353, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contType.setBounds(new Rectangle(704, 12, 270, 19));
        kDContainer1.getContentPane().add(contType, new KDLayout.Constraints(704, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBuilding.setBounds(new Rectangle(703, 44, 270, 19));
        kDContainer1.getContentPane().add(contBuilding, new KDLayout.Constraints(703, 44, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBuildingFloor.setBounds(new Rectangle(704, 81, 270, 19));
        kDContainer1.getContentPane().add(contBuildingFloor, new KDLayout.Constraints(704, 81, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSellProject.setBounds(new Rectangle(353, 44, 270, 19));
        kDContainer1.getContentPane().add(contSellProject, new KDLayout.Constraints(353, 44, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBuildingUnit.setBounds(new Rectangle(354, 81, 270, 19));
        kDContainer1.getContentPane().add(contBuildingUnit, new KDLayout.Constraints(354, 81, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkIsShowUnit.setBounds(new Rectangle(13, 81, 271, 19));
        kDContainer1.getContentPane().add(chkIsShowUnit, new KDLayout.Constraints(13, 81, 271, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrgUnit.setBounds(new Rectangle(13, 44, 270, 19));
        kDContainer1.getContentPane().add(contOrgUnit, new KDLayout.Constraints(13, 44, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(13, 12, 270, 19));
        kDContainer1.getContentPane().add(contNumber, new KDLayout.Constraints(13, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contName
        contName.setBoundEditor(txtName);
        //contType
        contType.setBoundEditor(comboType);
        //contBuilding
        contBuilding.setBoundEditor(prmtBuilding);
        //contBuildingFloor
        contBuildingFloor.setBoundEditor(prmtBuildingFloor);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contBuildingUnit
        contBuildingUnit.setBoundEditor(prmtBuildingUnit);
        //contOrgUnit
        contOrgUnit.setBoundEditor(prmtOrgUnit);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //kDSplitPane1
        kDSplitPane1.add(conElementEntry, "left");
        kDSplitPane1.add(conImage, "right");
        //conElementEntry
conElementEntry.getContentPane().setLayout(new BorderLayout(0, 0));        conElementEntry.getContentPane().add(tblElementEntry, BorderLayout.CENTER);
        //conImage
conImage.getContentPane().setLayout(new BorderLayout(0, 0));        conImage.getContentPane().add(kDPanel1, BorderLayout.CENTER);
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(backgroundPanel, BorderLayout.CENTER);

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
        menuFile.add(menuItemImage);
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
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemSendMail);
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
        this.toolBar.add(btnImage);
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
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnCreateTo);
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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtOrgUnit, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("type", com.kingdee.eas.fdc.sellhouse.EffectImageEnum.class, this.comboType, "selectedItem");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("building", com.kingdee.eas.fdc.sellhouse.BuildingInfo.class, this.prmtBuilding, "data");
		dataBinder.registerBinding("buildingFloor", com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo.class, this.prmtBuildingFloor, "data");
		dataBinder.registerBinding("buildingUnit", com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo.class, this.prmtBuildingUnit, "data");
		dataBinder.registerBinding("isShowUnit", boolean.class, this.chkIsShowUnit, "selected");
		dataBinder.registerBinding("elementEntry.type", com.kingdee.eas.fdc.sellhouse.EffectImageElementEnum.class, this.tblElementEntry, "type.text");
		dataBinder.registerBinding("elementEntry.elementName", String.class, this.tblElementEntry, "elementName.text");
		dataBinder.registerBinding("elementEntry.hotspot1", String.class, this.tblElementEntry, "hotspot1.text");
		dataBinder.registerBinding("elementEntry.hotspot2", String.class, this.tblElementEntry, "hotspot2.text");
		dataBinder.registerBinding("elementEntry.elementID", String.class, this.tblElementEntry, "elementID.text");
		dataBinder.registerBinding("elementEntry", com.kingdee.eas.fdc.sellhouse.EffectImageElementEntryInfo.class, this.tblElementEntry, "userObject");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.EffectImageEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.EffectImageInfo)ov;
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
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("type", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("building", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buildingFloor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("buildingUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isShowUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("elementEntry.type", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("elementEntry.elementName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("elementEntry.hotspot1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("elementEntry.hotspot2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("elementEntry.elementID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("elementEntry", ValidateHelper.ON_SAVE);    		
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
     * output comboType_itemStateChanged method
     */
    protected void comboType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
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
     * output prmtBuildingFloor_dataChanged method
     */
    protected void prmtBuildingFloor_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtBuildingUnit_dataChanged method
     */
    protected void prmtBuildingUnit_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output chkIsShowUnit_actionPerformed method
     */
    protected void chkIsShowUnit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblElementEntry_tableSelectChanged method
     */
    protected void tblElementEntry_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("orgUnit.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("type"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("building.*"));
        sic.add(new SelectorItemInfo("buildingFloor.*"));
        sic.add(new SelectorItemInfo("buildingUnit.*"));
        sic.add(new SelectorItemInfo("isShowUnit"));
    sic.add(new SelectorItemInfo("elementEntry.type"));
    sic.add(new SelectorItemInfo("elementEntry.elementName"));
    sic.add(new SelectorItemInfo("elementEntry.hotspot1"));
    sic.add(new SelectorItemInfo("elementEntry.hotspot2"));
    sic.add(new SelectorItemInfo("elementEntry.elementID"));
        sic.add(new SelectorItemInfo("elementEntry.*"));
//        sic.add(new SelectorItemInfo("elementEntry.number"));
        return sic;
    }        
    	

    /**
     * output actionImage_actionPerformed method
     */
    public void actionImage_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEditHotspot_actionPerformed method
     */
    public void actionEditHotspot_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSaveHotspot_actionPerformed method
     */
    public void actionSaveHotspot_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionImage(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImage() {
    	return false;
    }
	public RequestContext prepareActionEditHotspot(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditHotspot() {
    	return false;
    }
	public RequestContext prepareActionSaveHotspot(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSaveHotspot() {
    	return false;
    }

    /**
     * output ActionImage class
     */     
    protected class ActionImage extends ItemAction {     
    
        public ActionImage()
        {
            this(null);
        }

        public ActionImage(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImage.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImage.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImage.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEffectImageEditUI.this, "ActionImage", "actionImage_actionPerformed", e);
        }
    }

    /**
     * output ActionEditHotspot class
     */     
    protected class ActionEditHotspot extends ItemAction {     
    
        public ActionEditHotspot()
        {
            this(null);
        }

        public ActionEditHotspot(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEditHotspot.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditHotspot.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditHotspot.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEffectImageEditUI.this, "ActionEditHotspot", "actionEditHotspot_actionPerformed", e);
        }
    }

    /**
     * output ActionSaveHotspot class
     */     
    protected class ActionSaveHotspot extends ItemAction {     
    
        public ActionSaveHotspot()
        {
            this(null);
        }

        public ActionSaveHotspot(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSaveHotspot.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveHotspot.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveHotspot.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractEffectImageEditUI.this, "ActionSaveHotspot", "actionSaveHotspot_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "EffectImageEditUI");
    }




}