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
public abstract class AbstractInviteListingEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractInviteListingEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Type;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnColumnSetting;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportTemplet;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportExcel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Project;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUpLevel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDownLevel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportRefPrice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportRefPrice;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportExcel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemColumnSetting;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportTemplet;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportExcel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExportExcel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUpLevel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDownLevel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportRef;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    protected ActionColumnSetting actionColumnSetting = null;
    protected ActionImportTemplet actionImportTemplet = null;
    protected ActionImportExcel actionImportExcel = null;
    protected ActionUpLevel actionUpLevel = null;
    protected ActionDownLevel actionDownLevel = null;
    protected ActionImportRefPrice actionImportRefPrice = null;
    protected ActionExportRefPrice actionExportRefPrice = null;
    protected ActionExportExcel actionExportExcel = null;
    /**
     * output class constructor
     */
    public AbstractInviteListingEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractInviteListingEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        String _tempStr = null;
        actionSave.setEnabled(true);
        actionSave.setDaemonRun(false);

        actionSave.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl S"));
        _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
        actionSave.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
        actionSave.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.NAME");
        actionSave.putValue(ItemAction.NAME, _tempStr);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSubmit
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionColumnSetting
        this.actionColumnSetting = new ActionColumnSetting(this);
        getActionManager().registerAction("actionColumnSetting", actionColumnSetting);
         this.actionColumnSetting.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportTemplet
        this.actionImportTemplet = new ActionImportTemplet(this);
        getActionManager().registerAction("actionImportTemplet", actionImportTemplet);
         this.actionImportTemplet.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportExcel
        this.actionImportExcel = new ActionImportExcel(this);
        getActionManager().registerAction("actionImportExcel", actionImportExcel);
         this.actionImportExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpLevel
        this.actionUpLevel = new ActionUpLevel(this);
        getActionManager().registerAction("actionUpLevel", actionUpLevel);
         this.actionUpLevel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDownLevel
        this.actionDownLevel = new ActionDownLevel(this);
        getActionManager().registerAction("actionDownLevel", actionDownLevel);
         this.actionDownLevel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportRefPrice
        this.actionImportRefPrice = new ActionImportRefPrice(this);
        getActionManager().registerAction("actionImportRefPrice", actionImportRefPrice);
         this.actionImportRefPrice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportRefPrice
        this.actionExportRefPrice = new ActionExportRefPrice(this);
        getActionManager().registerAction("actionExportRefPrice", actionExportRefPrice);
         this.actionExportRefPrice.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportExcel
        this.actionExportExcel = new ActionExportExcel(this);
        getActionManager().registerAction("actionExportExcel", actionExportExcel);
         this.actionExportExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Type = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnColumnSetting = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportTemplet = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Project = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnUpLevel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDownLevel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportRefPrice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExportRefPrice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExportExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemColumnSetting = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImportTemplet = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImportExcel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExportExcel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUpLevel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDownLevel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImportRef = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contType.setName("contType");
        this.contDescription.setName("contDescription");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.f7Type.setName("f7Type");
        this.txtDescription.setName("txtDescription");
        this.btnColumnSetting.setName("btnColumnSetting");
        this.btnImportTemplet.setName("btnImportTemplet");
        this.btnImportExcel.setName("btnImportExcel");
        this.contProject.setName("contProject");
        this.f7Project.setName("f7Project");
        this.btnUpLevel.setName("btnUpLevel");
        this.btnDownLevel.setName("btnDownLevel");
        this.btnImportRefPrice.setName("btnImportRefPrice");
        this.btnExportRefPrice.setName("btnExportRefPrice");
        this.btnExportExcel.setName("btnExportExcel");
        this.menuItemColumnSetting.setName("menuItemColumnSetting");
        this.menuItemImportTemplet.setName("menuItemImportTemplet");
        this.menuItemImportExcel.setName("menuItemImportExcel");
        this.menuItemExportExcel.setName("menuItemExportExcel");
        this.menuItemUpLevel.setName("menuItemUpLevel");
        this.menuItemDownLevel.setName("menuItemDownLevel");
        this.menuItemImportRef.setName("menuItemImportRef");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnSave.setToolTipText(resHelper.getString("btnSave.toolTipText"));		
        this.btnSubmit.setToolTipText(resHelper.getString("btnSubmit.toolTipText"));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contType		
        this.contType.setBoundLabelText(resHelper.getString("contType.boundLabelText"));		
        this.contType.setBoundLabelLength(100);		
        this.contType.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(66);
        // txtName		
        this.txtName.setMaxLength(66);		
        this.txtName.setRequired(true);
        // f7Type		
        this.f7Type.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteTypeQuery");		
        this.f7Type.setDisplayFormat("$name$");		
        this.f7Type.setEditFormat("$number$");		
        this.f7Type.setRequired(true);		
        this.f7Type.setDefaultF7UIName("com.kingdee.eas.fdc.basedata.client.InviteTypeF7UI");		
        this.f7Type.setCommitFormat("$number$");
        // txtDescription
        // btnColumnSetting
        this.btnColumnSetting.setAction((IItemAction)ActionProxyFactory.getProxy(actionColumnSetting, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnColumnSetting.setText(resHelper.getString("btnColumnSetting.text"));
        // btnImportTemplet
        this.btnImportTemplet.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportTemplet, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportTemplet.setText(resHelper.getString("btnImportTemplet.text"));
        // btnImportExcel
        this.btnImportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportExcel.setText(resHelper.getString("btnImportExcel.text"));		
        this.btnImportExcel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // f7Project		
        this.f7Project.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.f7Project.setCommitFormat("$number$");		
        this.f7Project.setEditFormat("$number$");		
        this.f7Project.setDisplayFormat("$name$");
        // btnUpLevel
        this.btnUpLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpLevel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUpLevel.setText(resHelper.getString("btnUpLevel.text"));
        // btnDownLevel
        this.btnDownLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionDownLevel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDownLevel.setText(resHelper.getString("btnDownLevel.text"));
        // btnImportRefPrice
        this.btnImportRefPrice.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportRefPrice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportRefPrice.setText(resHelper.getString("btnImportRefPrice.text"));
        // btnExportRefPrice
        this.btnExportRefPrice.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportRefPrice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportRefPrice.setText(resHelper.getString("btnExportRefPrice.text"));
        // btnExportExcel
        this.btnExportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportExcel.setText(resHelper.getString("btnExportExcel.text"));		
        this.btnExportExcel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
        // menuItemColumnSetting
        this.menuItemColumnSetting.setAction((IItemAction)ActionProxyFactory.getProxy(actionColumnSetting, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemColumnSetting.setText(resHelper.getString("menuItemColumnSetting.text"));
        // menuItemImportTemplet
        this.menuItemImportTemplet.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportTemplet, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportTemplet.setText(resHelper.getString("menuItemImportTemplet.text"));
        // menuItemImportExcel
        this.menuItemImportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportExcel.setText(resHelper.getString("menuItemImportExcel.text"));
        // menuItemExportExcel
        this.menuItemExportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExportExcel.setText(resHelper.getString("menuItemExportExcel.text"));
        // menuItemUpLevel
        this.menuItemUpLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpLevel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUpLevel.setText(resHelper.getString("menuItemUpLevel.text"));
        // menuItemDownLevel
        this.menuItemDownLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionDownLevel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDownLevel.setText(resHelper.getString("menuItemDownLevel.text"));
        // menuItemImportRef
        this.menuItemImportRef.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportRefPrice, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportRef.setText(resHelper.getString("menuItemImportRef.text"));
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
        contNumber.setBounds(new Rectangle(12, 9, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(12, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(355, 9, 270, 19));
        this.add(contName, new KDLayout.Constraints(355, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contType.setBounds(new Rectangle(682, 9, 270, 19));
        this.add(contType, new KDLayout.Constraints(682, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(355, 36, 596, 19));
        this.add(contDescription, new KDLayout.Constraints(355, 36, 596, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contProject.setBounds(new Rectangle(12, 36, 270, 19));
        this.add(contProject, new KDLayout.Constraints(12, 36, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contType
        contType.setBoundEditor(f7Type);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contProject
        contProject.setBoundEditor(f7Project);

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
        menuBiz.add(menuItemColumnSetting);
        menuBiz.add(menuItemImportTemplet);
        menuBiz.add(menuItemImportExcel);
        menuBiz.add(menuItemExportExcel);
        menuBiz.add(menuItemUpLevel);
        menuBiz.add(menuItemDownLevel);
        menuBiz.add(menuItemImportRef);
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
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
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
        this.toolBar.add(btnColumnSetting);
        this.toolBar.add(btnImportTemplet);
        this.toolBar.add(btnImportExcel);
        this.toolBar.add(btnExportExcel);
        this.toolBar.add(btnUpLevel);
        this.toolBar.add(btnDownLevel);
        this.toolBar.add(btnImportRefPrice);
        this.toolBar.add(btnExportRefPrice);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.InviteListingEditUIHandler";
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionColumnSetting_actionPerformed method
     */
    public void actionColumnSetting_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportTemplet_actionPerformed method
     */
    public void actionImportTemplet_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportExcel_actionPerformed method
     */
    public void actionImportExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUpLevel_actionPerformed method
     */
    public void actionUpLevel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDownLevel_actionPerformed method
     */
    public void actionDownLevel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportRefPrice_actionPerformed method
     */
    public void actionImportRefPrice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportRefPrice_actionPerformed method
     */
    public void actionExportRefPrice_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportExcel_actionPerformed method
     */
    public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSave(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
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
	public RequestContext prepareActionColumnSetting(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionColumnSetting() {
    	return false;
    }
	public RequestContext prepareActionImportTemplet(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportTemplet() {
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
	public RequestContext prepareActionUpLevel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpLevel() {
    	return false;
    }
	public RequestContext prepareActionDownLevel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDownLevel() {
    	return false;
    }
	public RequestContext prepareActionImportRefPrice(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportRefPrice() {
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
	public RequestContext prepareActionExportExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportExcel() {
    	return false;
    }

    /**
     * output ActionColumnSetting class
     */     
    protected class ActionColumnSetting extends ItemAction {     
    
        public ActionColumnSetting()
        {
            this(null);
        }

        public ActionColumnSetting(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionColumnSetting.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionColumnSetting.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionColumnSetting.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteListingEditUI.this, "ActionColumnSetting", "actionColumnSetting_actionPerformed", e);
        }
    }

    /**
     * output ActionImportTemplet class
     */     
    protected class ActionImportTemplet extends ItemAction {     
    
        public ActionImportTemplet()
        {
            this(null);
        }

        public ActionImportTemplet(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImportTemplet.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportTemplet.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportTemplet.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteListingEditUI.this, "ActionImportTemplet", "actionImportTemplet_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractInviteListingEditUI.this, "ActionImportExcel", "actionImportExcel_actionPerformed", e);
        }
    }

    /**
     * output ActionUpLevel class
     */     
    protected class ActionUpLevel extends ItemAction {     
    
        public ActionUpLevel()
        {
            this(null);
        }

        public ActionUpLevel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUpLevel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpLevel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpLevel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteListingEditUI.this, "ActionUpLevel", "actionUpLevel_actionPerformed", e);
        }
    }

    /**
     * output ActionDownLevel class
     */     
    protected class ActionDownLevel extends ItemAction {     
    
        public ActionDownLevel()
        {
            this(null);
        }

        public ActionDownLevel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDownLevel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDownLevel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDownLevel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteListingEditUI.this, "ActionDownLevel", "actionDownLevel_actionPerformed", e);
        }
    }

    /**
     * output ActionImportRefPrice class
     */     
    protected class ActionImportRefPrice extends ItemAction {     
    
        public ActionImportRefPrice()
        {
            this(null);
        }

        public ActionImportRefPrice(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionImportRefPrice.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportRefPrice.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportRefPrice.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteListingEditUI.this, "ActionImportRefPrice", "actionImportRefPrice_actionPerformed", e);
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
            innerActionPerformed("eas", AbstractInviteListingEditUI.this, "ActionExportRefPrice", "actionExportRefPrice_actionPerformed", e);
        }
    }

    /**
     * output ActionExportExcel class
     */     
    protected class ActionExportExcel extends ItemAction {     
    
        public ActionExportExcel()
        {
            this(null);
        }

        public ActionExportExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionExportExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteListingEditUI.this, "ActionExportExcel", "actionExportExcel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "InviteListingEditUI");
    }




}