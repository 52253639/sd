/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

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
public abstract class AbstractNewListingRefPriceUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractNewListingRefPriceUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProject;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabbedPnlTable;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurProject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEditRefPrice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportRefPrice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportExcel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRefPrice;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemColumnSetting;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportExcel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportTemplet;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExportPrice;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUpLevel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDownLevel;
    protected com.kingdee.eas.fdc.invite.NewListingInfo editData = null;
    protected ActionRefPrice actionRefPrice = null;
    protected ActionExportRefPrice actionExportRefPrice = null;
    protected ActionImportExcel actionImportExcel = null;
    /**
     * output class constructor
     */
    public AbstractNewListingRefPriceUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractNewListingRefPriceUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionRefPrice
        this.actionRefPrice = new ActionRefPrice(this);
        getActionManager().registerAction("actionRefPrice", actionRefPrice);
         this.actionRefPrice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportRefPrice
        this.actionExportRefPrice = new ActionExportRefPrice(this);
        getActionManager().registerAction("actionExportRefPrice", actionExportRefPrice);
         this.actionExportRefPrice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportExcel
        this.actionImportExcel = new ActionImportExcel(this);
        getActionManager().registerAction("actionImportExcel", actionImportExcel);
         this.actionImportExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tabbedPnlTable = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtInviteType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnEditRefPrice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExportRefPrice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemRefPrice = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemColumnSetting = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImportExcel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImportTemplet = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExportPrice = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUpLevel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDownLevel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contName.setName("contName");
        this.contInviteType.setName("contInviteType");
        this.contCurProject.setName("contCurProject");
        this.tabbedPnlTable.setName("tabbedPnlTable");
        this.txtNumber.setName("txtNumber");
        this.txtDescription.setName("txtDescription");
        this.txtName.setName("txtName");
        this.prmtInviteType.setName("prmtInviteType");
        this.prmtCurProject.setName("prmtCurProject");
        this.btnEditRefPrice.setName("btnEditRefPrice");
        this.btnExportRefPrice.setName("btnExportRefPrice");
        this.btnImportExcel.setName("btnImportExcel");
        this.menuItemRefPrice.setName("menuItemRefPrice");
        this.menuItemColumnSetting.setName("menuItemColumnSetting");
        this.menuItemImportExcel.setName("menuItemImportExcel");
        this.menuItemImportTemplet.setName("menuItemImportTemplet");
        this.menuItemExportPrice.setName("menuItemExportPrice");
        this.menuItemUpLevel.setName("menuItemUpLevel");
        this.menuItemDownLevel.setName("menuItemDownLevel");
        // CoreUI		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.menuEdit.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contInviteType		
        this.contInviteType.setBoundLabelText(resHelper.getString("contInviteType.boundLabelText"));		
        this.contInviteType.setBoundLabelLength(100);		
        this.contInviteType.setBoundLabelUnderline(true);
        // contCurProject		
        this.contCurProject.setBoundLabelText(resHelper.getString("contCurProject.boundLabelText"));		
        this.contCurProject.setBoundLabelLength(100);		
        this.contCurProject.setBoundLabelUnderline(true);
        // tabbedPnlTable
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);
        // prmtInviteType		
        this.prmtInviteType.setDisplayFormat("$name$");		
        this.prmtInviteType.setEditFormat("$number$");		
        this.prmtInviteType.setCommitFormat("$number$");		
        this.prmtInviteType.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteTypeQuery");		
        this.prmtInviteType.setDefaultF7UIName("com.kingdee.eas.fdc.basedata.client.InviteTypeF7UI");		
        this.prmtInviteType.setRequired(true);
        // prmtCurProject		
        this.prmtCurProject.setEditFormat("$number$");		
        this.prmtCurProject.setDisplayFormat("$name$");		
        this.prmtCurProject.setCommitFormat("$number$");		
        this.prmtCurProject.setDefaultF7UIName("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtCurProject.setRequired(true);
        // btnEditRefPrice
        this.btnEditRefPrice.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefPrice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEditRefPrice.setText(resHelper.getString("btnEditRefPrice.text"));
        // btnExportRefPrice
        this.btnExportRefPrice.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportRefPrice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportRefPrice.setText(resHelper.getString("btnExportRefPrice.text"));
        // btnImportExcel
        this.btnImportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportExcel.setText(resHelper.getString("btnImportExcel.text"));		
        this.btnImportExcel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));
        // menuItemRefPrice
        this.menuItemRefPrice.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefPrice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRefPrice.setText(resHelper.getString("menuItemRefPrice.text"));
        // menuItemColumnSetting		
        this.menuItemColumnSetting.setText(resHelper.getString("menuItemColumnSetting.text"));		
        this.menuItemColumnSetting.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_organiselist"));		
        this.menuItemColumnSetting.setToolTipText(resHelper.getString("menuItemColumnSetting.toolTipText"));
        // menuItemImportExcel		
        this.menuItemImportExcel.setText(resHelper.getString("menuItemImportExcel.text"));		
        this.menuItemImportExcel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));		
        this.menuItemImportExcel.setToolTipText(resHelper.getString("menuItemImportExcel.toolTipText"));
        // menuItemImportTemplet		
        this.menuItemImportTemplet.setText(resHelper.getString("menuItemImportTemplet.text"));		
        this.menuItemImportTemplet.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importcyclostyle"));		
        this.menuItemImportTemplet.setToolTipText(resHelper.getString("menuItemImportTemplet.toolTipText"));
        // menuItemExportPrice		
        this.menuItemExportPrice.setText(resHelper.getString("menuItemExportPrice.text"));		
        this.menuItemExportPrice.setToolTipText(resHelper.getString("menuItemExportPrice.toolTipText"));
        // menuItemUpLevel		
        this.menuItemUpLevel.setText(resHelper.getString("menuItemUpLevel.text"));		
        this.menuItemUpLevel.setToolTipText(resHelper.getString("menuItemUpLevel.toolTipText"));
        // menuItemDownLevel		
        this.menuItemDownLevel.setText(resHelper.getString("menuItemDownLevel.text"));		
        this.menuItemDownLevel.setToolTipText(resHelper.getString("menuItemDownLevel.toolTipText"));		
        this.menuItemDownLevel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_degrade"));
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
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(371, 39, 632, 19));
        this.add(contDescription, new KDLayout.Constraints(371, 39, 632, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contName.setBounds(new Rectangle(371, 10, 270, 19));
        this.add(contName, new KDLayout.Constraints(371, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteType.setBounds(new Rectangle(733, 10, 270, 19));
        this.add(contInviteType, new KDLayout.Constraints(733, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCurProject.setBounds(new Rectangle(10, 39, 270, 19));
        this.add(contCurProject, new KDLayout.Constraints(10, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        tabbedPnlTable.setBounds(new Rectangle(10, 72, 993, 547));
        this.add(tabbedPnlTable, new KDLayout.Constraints(10, 72, 993, 547, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contName
        contName.setBoundEditor(txtName);
        //contInviteType
        contInviteType.setBoundEditor(prmtInviteType);
        //contCurProject
        contCurProject.setBoundEditor(prmtCurProject);

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
        menuEdit.add(separator2);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(menuItemRefPrice);
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
        menuBiz.add(menuItemColumnSetting);
        menuBiz.add(menuItemImportExcel);
        menuBiz.add(menuItemImportTemplet);
        menuBiz.add(menuItemExportPrice);
        menuBiz.add(menuItemUpLevel);
        menuBiz.add(menuItemDownLevel);
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
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(btnCopyLine);
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
        this.toolBar.add(btnEditRefPrice);
        this.toolBar.add(btnExportRefPrice);
        this.toolBar.add(btnImportExcel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("inviteType", com.kingdee.eas.fdc.invite.InviteTypeInfo.class, this.prmtInviteType, "data");
		dataBinder.registerBinding("curProject", com.kingdee.eas.fdc.basedata.CurProjectInfo.class, this.prmtCurProject, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.NewListingRefPriceUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.NewListingInfo)ov;
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
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("curProject", ValidateHelper.ON_SAVE);    		
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("inviteType.*"));
        sic.add(new SelectorItemInfo("curProject.*"));
        return sic;
    }        
    	

    /**
     * output actionRefPrice_actionPerformed method
     */
    public void actionRefPrice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportRefPrice_actionPerformed method
     */
    public void actionExportRefPrice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportExcel_actionPerformed method
     */
    public void actionImportExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionRefPrice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRefPrice() {
    	return false;
    }
	public RequestContext prepareActionExportRefPrice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportRefPrice() {
    	return false;
    }
	public RequestContext prepareActionImportExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportExcel() {
    	return false;
    }

    /**
     * output ActionRefPrice class
     */     
    protected class ActionRefPrice extends ItemAction {     
    
        public ActionRefPrice()
        {
            this(null);
        }

        public ActionRefPrice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl R"));
            _tempStr = resHelper.getString("ActionRefPrice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefPrice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefPrice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListingRefPriceUI.this, "ActionRefPrice", "actionRefPrice_actionPerformed", e);
        }
    }

    /**
     * output ActionExportRefPrice class
     */     
    protected class ActionExportRefPrice extends ItemAction {     
    
        public ActionExportRefPrice()
        {
            this(null);
        }

        public ActionExportRefPrice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl X"));
            _tempStr = resHelper.getString("ActionExportRefPrice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportRefPrice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportRefPrice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListingRefPriceUI.this, "ActionExportRefPrice", "actionExportRefPrice_actionPerformed", e);
        }
    }

    /**
     * output ActionImportExcel class
     */     
    protected class ActionImportExcel extends ItemAction {     
    
        public ActionImportExcel()
        {
            this(null);
        }

        public ActionImportExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl I"));
            _tempStr = resHelper.getString("ActionImportExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListingRefPriceUI.this, "ActionImportExcel", "actionImportExcel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "NewListingRefPriceUI");
    }




}