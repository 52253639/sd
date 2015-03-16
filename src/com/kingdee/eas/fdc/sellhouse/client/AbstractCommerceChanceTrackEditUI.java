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
public abstract class AbstractCommerceChanceTrackEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCommerceChanceTrackEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCommerceChance;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTrackDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInteractionType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTrackEvent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleMan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTrackContent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCommerceChanceStage;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCommerceLevel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCommerceChance;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkTrackDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboInteractionType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboTrackEvent;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSaleMan;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtTrackContent;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCommerceChanceStage;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtClassify;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCommerceLevel;
    protected com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractCommerceChanceTrackEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCommerceChanceTrackEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCommerceChance = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTrackDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInteractionType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTrackEvent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSaleMan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTrackContent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCommerceChanceStage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCommerceLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCommerceChance = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkTrackDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboInteractionType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboTrackEvent = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtSaleMan = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtTrackContent = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtCommerceChanceStage = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtClassify = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCommerceLevel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contNumber.setName("contNumber");
        this.contSellProject.setName("contSellProject");
        this.contCommerceChance.setName("contCommerceChance");
        this.contTrackDate.setName("contTrackDate");
        this.contInteractionType.setName("contInteractionType");
        this.contTrackEvent.setName("contTrackEvent");
        this.contSaleMan.setName("contSaleMan");
        this.contTrackContent.setName("contTrackContent");
        this.contCommerceChanceStage.setName("contCommerceChanceStage");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contCommerceLevel.setName("contCommerceLevel");
        this.txtNumber.setName("txtNumber");
        this.prmtSellProject.setName("prmtSellProject");
        this.prmtCommerceChance.setName("prmtCommerceChance");
        this.pkTrackDate.setName("pkTrackDate");
        this.comboInteractionType.setName("comboInteractionType");
        this.comboTrackEvent.setName("comboTrackEvent");
        this.prmtSaleMan.setName("prmtSaleMan");
        this.txtTrackContent.setName("txtTrackContent");
        this.prmtCommerceChanceStage.setName("prmtCommerceChanceStage");
        this.prmtClassify.setName("prmtClassify");
        this.prmtCommerceLevel.setName("prmtCommerceLevel");
        // CoreUI
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contCommerceChance		
        this.contCommerceChance.setBoundLabelText(resHelper.getString("contCommerceChance.boundLabelText"));		
        this.contCommerceChance.setBoundLabelLength(100);		
        this.contCommerceChance.setBoundLabelUnderline(true);
        // contTrackDate		
        this.contTrackDate.setBoundLabelText(resHelper.getString("contTrackDate.boundLabelText"));		
        this.contTrackDate.setBoundLabelLength(100);		
        this.contTrackDate.setBoundLabelUnderline(true);
        // contInteractionType		
        this.contInteractionType.setBoundLabelText(resHelper.getString("contInteractionType.boundLabelText"));		
        this.contInteractionType.setBoundLabelLength(100);		
        this.contInteractionType.setBoundLabelUnderline(true);
        // contTrackEvent		
        this.contTrackEvent.setBoundLabelText(resHelper.getString("contTrackEvent.boundLabelText"));		
        this.contTrackEvent.setBoundLabelLength(100);		
        this.contTrackEvent.setBoundLabelUnderline(true);
        // contSaleMan		
        this.contSaleMan.setBoundLabelText(resHelper.getString("contSaleMan.boundLabelText"));		
        this.contSaleMan.setBoundLabelLength(100);		
        this.contSaleMan.setBoundLabelUnderline(true);
        // contTrackContent		
        this.contTrackContent.setBoundLabelText(resHelper.getString("contTrackContent.boundLabelText"));		
        this.contTrackContent.setBoundLabelLength(100);		
        this.contTrackContent.setBoundLabelUnderline(true);
        // contCommerceChanceStage		
        this.contCommerceChanceStage.setBoundLabelText(resHelper.getString("contCommerceChanceStage.boundLabelText"));		
        this.contCommerceChanceStage.setBoundLabelLength(100);		
        this.contCommerceChanceStage.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // contCommerceLevel		
        this.contCommerceLevel.setBoundLabelText(resHelper.getString("contCommerceLevel.boundLabelText"));		
        this.contCommerceLevel.setBoundLabelLength(100);		
        this.contCommerceLevel.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // prmtSellProject		
        this.prmtSellProject.setRequired(true);		
        this.prmtSellProject.setEnabled(false);		
        this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtSellProject.setDisplayFormat("$name$");		
        this.prmtSellProject.setEditFormat("$number$");		
        this.prmtSellProject.setCommitFormat("$number$");
        // prmtCommerceChance		
        this.prmtCommerceChance.setRequired(true);		
        this.prmtCommerceChance.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CommerceChanceQuery");		
        this.prmtCommerceChance.setDisplayFormat("$name$");		
        this.prmtCommerceChance.setEditFormat("$number$");		
        this.prmtCommerceChance.setCommitFormat("$number$");
        // pkTrackDate		
        this.pkTrackDate.setRequired(true);
        // comboInteractionType		
        this.comboInteractionType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.InteractionTypeEnum").toArray());		
        this.comboInteractionType.setRequired(true);		
        this.comboInteractionType.setSelectedIndex(0);
        this.comboInteractionType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboInteractionType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboTrackEvent		
        this.comboTrackEvent.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.TrackEventEnum").toArray());		
        this.comboTrackEvent.setEnabled(false);
        // prmtSaleMan		
        this.prmtSaleMan.setRequired(true);		
        this.prmtSaleMan.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtSaleMan.setDisplayFormat("$name$");		
        this.prmtSaleMan.setEditFormat("$number$");		
        this.prmtSaleMan.setCommitFormat("$number$");
        // txtTrackContent		
        this.txtTrackContent.setMaxLength(80);
        // prmtCommerceChanceStage		
        this.prmtCommerceChanceStage.setRequired(true);		
        this.prmtCommerceChanceStage.setEnabled(false);
        // prmtClassify		
        this.prmtClassify.setQueryInfo("com.kingdee.eas.fdc.market.app.ChannelTypeTreeQuery");		
        this.prmtClassify.setRequired(true);
        // prmtCommerceLevel		
        this.prmtCommerceLevel.setRequired(true);
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
        this.setBounds(new Rectangle(10, 10, 600, 300));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 600, 300));
        contNumber.setBounds(new Rectangle(10, 41, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 41, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellProject.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCommerceChance.setBounds(new Rectangle(300, 10, 270, 19));
        this.add(contCommerceChance, new KDLayout.Constraints(300, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contTrackDate.setBounds(new Rectangle(300, 41, 270, 19));
        this.add(contTrackDate, new KDLayout.Constraints(300, 41, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contInteractionType.setBounds(new Rectangle(10, 72, 270, 19));
        this.add(contInteractionType, new KDLayout.Constraints(10, 72, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTrackEvent.setBounds(new Rectangle(10, 103, 270, 19));
        this.add(contTrackEvent, new KDLayout.Constraints(10, 103, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSaleMan.setBounds(new Rectangle(300, 134, 270, 19));
        this.add(contSaleMan, new KDLayout.Constraints(300, 134, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contTrackContent.setBounds(new Rectangle(10, 168, 561, 110));
        this.add(contTrackContent, new KDLayout.Constraints(10, 168, 561, 110, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCommerceChanceStage.setBounds(new Rectangle(300, 72, 270, 19));
        this.add(contCommerceChanceStage, new KDLayout.Constraints(300, 72, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(10, 134, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(10, 134, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCommerceLevel.setBounds(new Rectangle(300, 103, 270, 19));
        this.add(contCommerceLevel, new KDLayout.Constraints(300, 103, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contCommerceChance
        contCommerceChance.setBoundEditor(prmtCommerceChance);
        //contTrackDate
        contTrackDate.setBoundEditor(pkTrackDate);
        //contInteractionType
        contInteractionType.setBoundEditor(comboInteractionType);
        //contTrackEvent
        contTrackEvent.setBoundEditor(comboTrackEvent);
        //contSaleMan
        contSaleMan.setBoundEditor(prmtSaleMan);
        //contTrackContent
        contTrackContent.setBoundEditor(txtTrackContent);
        //contCommerceChanceStage
        contCommerceChanceStage.setBoundEditor(prmtCommerceChanceStage);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(prmtClassify);
        //contCommerceLevel
        contCommerceLevel.setBoundEditor(prmtCommerceLevel);

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
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("commerceChance", com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo.class, this.prmtCommerceChance, "data");
		dataBinder.registerBinding("trackDate", java.util.Date.class, this.pkTrackDate, "value");
		dataBinder.registerBinding("interactionType", com.kingdee.eas.fdc.sellhouse.InteractionTypeEnum.class, this.comboInteractionType, "selectedItem");
		dataBinder.registerBinding("trackEvent", com.kingdee.eas.fdc.sellhouse.TrackEventEnum.class, this.comboTrackEvent, "selectedItem");
		dataBinder.registerBinding("saleMan", com.kingdee.eas.base.permission.UserInfo.class, this.prmtSaleMan, "data");
		dataBinder.registerBinding("trackContent", String.class, this.txtTrackContent, "text");
		dataBinder.registerBinding("commerceChanceStage", com.kingdee.eas.fdc.sellhouse.CommerceChanceStageEnum.class, this.prmtCommerceChanceStage, "data");
		dataBinder.registerBinding("classify", com.kingdee.eas.fdc.market.ChannelTypeInfo.class, this.prmtClassify, "data");
		dataBinder.registerBinding("commerceLevel", com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo.class, this.prmtCommerceLevel, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CommerceChanceTrackEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo)ov;
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
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("commerceChance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("trackDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("interactionType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("trackEvent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("saleMan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("trackContent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("commerceChanceStage", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("classify", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("commerceLevel", ValidateHelper.ON_SAVE);    		
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
     * output comboInteractionType_itemStateChanged method
     */
    protected void comboInteractionType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("commerceChance.*"));
        sic.add(new SelectorItemInfo("trackDate"));
        sic.add(new SelectorItemInfo("interactionType"));
        sic.add(new SelectorItemInfo("trackEvent"));
        sic.add(new SelectorItemInfo("saleMan.*"));
        sic.add(new SelectorItemInfo("trackContent"));
        sic.add(new SelectorItemInfo("commerceChanceStage.*"));
        sic.add(new SelectorItemInfo("classify.*"));
        sic.add(new SelectorItemInfo("commerceLevel.*"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CommerceChanceTrackEditUI");
    }




}