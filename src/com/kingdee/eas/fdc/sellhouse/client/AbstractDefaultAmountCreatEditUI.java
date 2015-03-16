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
public abstract class AbstractDefaultAmountCreatEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDefaultAmountCreatEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDefCalFormula;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDefCalDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contExplain;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDefCalFormula;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDefCalDate;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtExplain;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtParent;
    protected com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractDefaultAmountCreatEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractDefaultAmountCreatEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDefCalFormula = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDefCalDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contExplain = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtDefCalFormula = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkDefCalDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtExplain = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kdtParent = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contCreator.setName("contCreator");
        this.contNumber.setName("contNumber");
        this.contOrgUnit.setName("contOrgUnit");
        this.contName.setName("contName");
        this.contProject.setName("contProject");
        this.contDefCalFormula.setName("contDefCalFormula");
        this.contDefCalDate.setName("contDefCalDate");
        this.contExplain.setName("contExplain");
        this.kDContainer1.setName("kDContainer1");
        this.prmtCreator.setName("prmtCreator");
        this.txtNumber.setName("txtNumber");
        this.prmtOrgUnit.setName("prmtOrgUnit");
        this.txtName.setName("txtName");
        this.prmtProject.setName("prmtProject");
        this.prmtDefCalFormula.setName("prmtDefCalFormula");
        this.pkDefCalDate.setName("pkDefCalDate");
        this.txtExplain.setName("txtExplain");
        this.kdtParent.setName("kdtParent");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.separatorFW3.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnMultiapprove.setVisible(false);		
        this.btnNextPerson.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);		
        this.separatorFW8.setVisible(false);		
        this.btnAudit.setVisible(false);		
        this.btnUnAudit.setVisible(false);		
        this.btnCalculator.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setEnabled(false);		
        this.contNumber.setVisible(false);
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(100);		
        this.contOrgUnit.setBoundLabelUnderline(true);		
        this.contOrgUnit.setEnabled(false);		
        this.contOrgUnit.setVisible(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);		
        this.contName.setEnabled(false);		
        this.contName.setVisible(false);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // contDefCalFormula		
        this.contDefCalFormula.setBoundLabelText(resHelper.getString("contDefCalFormula.boundLabelText"));		
        this.contDefCalFormula.setBoundLabelLength(100);		
        this.contDefCalFormula.setBoundLabelUnderline(true);
        // contDefCalDate		
        this.contDefCalDate.setBoundLabelText(resHelper.getString("contDefCalDate.boundLabelText"));		
        this.contDefCalDate.setBoundLabelLength(100);		
        this.contDefCalDate.setBoundLabelUnderline(true);
        // contExplain		
        this.contExplain.setBoundLabelText(resHelper.getString("contExplain.boundLabelText"));		
        this.contExplain.setBoundLabelLength(100);		
        this.contExplain.setBoundLabelUnderline(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // txtNumber		
        this.txtNumber.setEnabled(false);		
        this.txtNumber.setVisible(false);
        // prmtOrgUnit		
        this.prmtOrgUnit.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FullOrgUnitQuery");		
        this.prmtOrgUnit.setEnabled(false);		
        this.prmtOrgUnit.setVisible(false);
        // txtName		
        this.txtName.setEnabled(false);		
        this.txtName.setVisible(false);
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtProject.setEnabled(false);
        // prmtDefCalFormula		
        this.prmtDefCalFormula.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.DefaultCollectionQuery");
        this.prmtDefCalFormula.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    prmtDefCalFormula_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtDefCalFormula.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtDefCalFormula_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkDefCalDate
        // txtExplain
        // kdtParent
		String kdtParentStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol10\"><c:NumberFormat>0</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"customerNames\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"telephone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"contractAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"argAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"busType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"saleManNames\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"refDeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"subDeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"carryAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"remak\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customerNames}</t:Cell><t:Cell>$Resource{telephone}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{contractAmount}</t:Cell><t:Cell>$Resource{argAmount}</t:Cell><t:Cell>$Resource{busType}</t:Cell><t:Cell>$Resource{saleManNames}</t:Cell><t:Cell>$Resource{refDeAmount}</t:Cell><t:Cell>$Resource{subDeAmount}</t:Cell><t:Cell>$Resource{carryAmount}</t:Cell><t:Cell>$Resource{remak}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtParent.setFormatXml(resHelper.translateString("kdtParent",kdtParentStrXML));
        this.kdtParent.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtParent_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtParent.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtParent_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtParent_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtParent.putBindContents("editData",new String[] {"room","customerNames","telephone","bizDate","number","contractAmount","argAmount","busType","saleManNames","refDeAmount","subDeAmount","carryAmount","remak"});


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
        contCreator.setBounds(new Rectangle(720, 34, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(720, 34, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(24, 599, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(24, 599, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrgUnit.setBounds(new Rectangle(574, 600, 270, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(574, 600, 270, 19, 0));
        contName.setBounds(new Rectangle(297, 604, 270, 19));
        this.add(contName, new KDLayout.Constraints(297, 604, 270, 19, 0));
        contProject.setBounds(new Rectangle(21, 12, 270, 19));
        this.add(contProject, new KDLayout.Constraints(21, 12, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDefCalFormula.setBounds(new Rectangle(21, 34, 270, 19));
        this.add(contDefCalFormula, new KDLayout.Constraints(21, 34, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDefCalDate.setBounds(new Rectangle(370, 34, 270, 19));
        this.add(contDefCalDate, new KDLayout.Constraints(370, 34, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contExplain.setBounds(new Rectangle(21, 56, 619, 77));
        this.add(contExplain, new KDLayout.Constraints(21, 56, 619, 77, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(21, 146, 965, 451));
        this.add(kDContainer1, new KDLayout.Constraints(21, 146, 965, 451, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contOrgUnit
        contOrgUnit.setBoundEditor(prmtOrgUnit);
        //contName
        contName.setBoundEditor(txtName);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //contDefCalFormula
        contDefCalFormula.setBoundEditor(prmtDefCalFormula);
        //contDefCalDate
        contDefCalDate.setBoundEditor(pkDefCalDate);
        //contExplain
        contExplain.setBoundEditor(txtExplain);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(21, 146, 965, 451));        kdtParent.setBounds(new Rectangle(6, 4, 954, 416));
        kDContainer1.getContentPane().add(kdtParent, new KDLayout.Constraints(6, 4, 954, 416, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

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
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtOrgUnit, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtProject, "data");
		dataBinder.registerBinding("defCalFormula", com.kingdee.eas.fdc.sellhouse.DefaultCollectionInfo.class, this.prmtDefCalFormula, "data");
		dataBinder.registerBinding("defCalDate", java.util.Date.class, this.pkDefCalDate, "value");
		dataBinder.registerBinding("explain", String.class, this.txtExplain, "text");
		dataBinder.registerBinding("parent.customerNames", String.class, this.kdtParent, "customerNames.text");
		dataBinder.registerBinding("parent.telephone", String.class, this.kdtParent, "telephone.text");
		dataBinder.registerBinding("parent.bizDate", java.util.Date.class, this.kdtParent, "bizDate.text");
		dataBinder.registerBinding("parent.number", String.class, this.kdtParent, "number.text");
		dataBinder.registerBinding("parent.contractAmount", java.math.BigDecimal.class, this.kdtParent, "contractAmount.text");
		dataBinder.registerBinding("parent.argAmount", java.math.BigDecimal.class, this.kdtParent, "argAmount.text");
		dataBinder.registerBinding("parent.busType", String.class, this.kdtParent, "busType.text");
		dataBinder.registerBinding("parent.saleManNames", String.class, this.kdtParent, "saleManNames.text");
		dataBinder.registerBinding("parent.carryAmount", java.math.BigDecimal.class, this.kdtParent, "carryAmount.text");
		dataBinder.registerBinding("parent.refDeAmount", java.math.BigDecimal.class, this.kdtParent, "refDeAmount.text");
		dataBinder.registerBinding("parent.subDeAmount", java.math.BigDecimal.class, this.kdtParent, "subDeAmount.text");
		dataBinder.registerBinding("parent.remak", String.class, this.kdtParent, "remak.text");
		dataBinder.registerBinding("parent", com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatEntryInfo.class, this.kdtParent, "userObject");
		dataBinder.registerBinding("parent.room", com.kingdee.eas.fdc.sellhouse.RoomInfo.class, this.kdtParent, "room.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.DefaultAmountCreatEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatInfo)ov;
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
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("defCalFormula", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("defCalDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("explain", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.customerNames", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.telephone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.contractAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.argAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.busType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.saleManNames", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.carryAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.refDeAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.subDeAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.remak", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.room", ValidateHelper.ON_SAVE);    		
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
     * output prmtDefCalFormula_propertyChange method
     */
    protected void prmtDefCalFormula_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtDefCalFormula_dataChanged method
     */
    protected void prmtDefCalFormula_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtParent_tableClicked method
     */
    protected void kdtParent_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kdtParent_editValueChanged method
     */
    protected void kdtParent_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtParent_editStopped method
     */
    protected void kdtParent_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("orgUnit.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("project.*"));
        sic.add(new SelectorItemInfo("defCalFormula.*"));
        sic.add(new SelectorItemInfo("defCalDate"));
        sic.add(new SelectorItemInfo("explain"));
    sic.add(new SelectorItemInfo("parent.customerNames"));
    sic.add(new SelectorItemInfo("parent.telephone"));
    sic.add(new SelectorItemInfo("parent.bizDate"));
    sic.add(new SelectorItemInfo("parent.number"));
    sic.add(new SelectorItemInfo("parent.contractAmount"));
    sic.add(new SelectorItemInfo("parent.argAmount"));
    sic.add(new SelectorItemInfo("parent.busType"));
    sic.add(new SelectorItemInfo("parent.saleManNames"));
    sic.add(new SelectorItemInfo("parent.carryAmount"));
    sic.add(new SelectorItemInfo("parent.refDeAmount"));
    sic.add(new SelectorItemInfo("parent.subDeAmount"));
    sic.add(new SelectorItemInfo("parent.remak"));
        sic.add(new SelectorItemInfo("parent.*"));
//        sic.add(new SelectorItemInfo("parent.number"));
        sic.add(new SelectorItemInfo("parent.room.*"));
//        sic.add(new SelectorItemInfo("parent.room.number"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "DefaultAmountCreatEditUI");
    }




}