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
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractListingTempletEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractListingTempletEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Type;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnColumnSetting;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUpLevel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDownLevel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportExcel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemColumnSetting;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUpLevel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDownLevel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportExcel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExportExcel;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    protected ActionColumnSetting actionColumnSetting = null;
    protected ActionUpLevel actionUpLevel = null;
    protected ActionDownLevel actionDownLevel = null;
    protected ActionImportExcel actionImportExcel = null;
    protected ActionExportExcel actionExportExcel = null;

    /**
     * output class constructor
     */
    public AbstractListingTempletEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractListingTempletEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionColumnSetting
        this.actionColumnSetting = new ActionColumnSetting(this);
        getActionManager().registerAction("actionColumnSetting", actionColumnSetting);
         this.actionColumnSetting.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpLevel
        this.actionUpLevel = new ActionUpLevel(this);
        getActionManager().registerAction("actionUpLevel", actionUpLevel);
         this.actionUpLevel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDownLevel
        this.actionDownLevel = new ActionDownLevel(this);
        getActionManager().registerAction("actionDownLevel", actionDownLevel);
         this.actionDownLevel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportExcel
        this.actionImportExcel = new ActionImportExcel(this);
        getActionManager().registerAction("actionImportExcel", actionImportExcel);
         this.actionImportExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
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
        this.btnUpLevel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDownLevel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExportExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemColumnSetting = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUpLevel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDownLevel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemImportExcel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExportExcel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contType.setName("contType");
        this.contDescription.setName("contDescription");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.f7Type.setName("f7Type");
        this.txtDescription.setName("txtDescription");
        this.btnColumnSetting.setName("btnColumnSetting");
        this.btnUpLevel.setName("btnUpLevel");
        this.btnDownLevel.setName("btnDownLevel");
        this.btnImport.setName("btnImport");
        this.btnExportExcel.setName("btnExportExcel");
        this.menuItemColumnSetting.setName("menuItemColumnSetting");
        this.menuItemUpLevel.setName("menuItemUpLevel");
        this.menuItemDownLevel.setName("menuItemDownLevel");
        this.menuItemImportExcel.setName("menuItemImportExcel");
        this.menuItemExportExcel.setName("menuItemExportExcel");
        // CoreUI
        this.setPreferredSize(new Dimension(1013,629));
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
        this.txtNumber.setMaxLength(66);
        this.txtNumber.setRequired(true);
        // txtName
        this.txtName.setRequired(true);
        this.txtName.setMaxLength(66);
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
        // btnUpLevel
        this.btnUpLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpLevel, new Class[] { IItemAction.class }, getServiceContext()));
        this.btnUpLevel.setText(resHelper.getString("btnUpLevel.text"));
        // btnDownLevel
        this.btnDownLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionDownLevel, new Class[] { IItemAction.class }, getServiceContext()));
        this.btnDownLevel.setText(resHelper.getString("btnDownLevel.text"));
        // btnImport
        this.btnImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportExcel, new Class[] { IItemAction.class }, getServiceContext()));
        this.btnImport.setText(resHelper.getString("btnImport.text"));
        this.btnImport.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));
        // btnExportExcel
        this.btnExportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportExcel, new Class[] { IItemAction.class }, getServiceContext()));
        this.btnExportExcel.setText(resHelper.getString("btnExportExcel.text"));
        this.btnExportExcel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
        // menuItemColumnSetting
        this.menuItemColumnSetting.setAction((IItemAction)ActionProxyFactory.getProxy(actionColumnSetting, new Class[] { IItemAction.class }, getServiceContext()));
        this.menuItemColumnSetting.setText(resHelper.getString("menuItemColumnSetting.text"));
        // menuItemUpLevel
        this.menuItemUpLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpLevel, new Class[] { IItemAction.class }, getServiceContext()));
        this.menuItemUpLevel.setText(resHelper.getString("menuItemUpLevel.text"));
        // menuItemDownLevel
        this.menuItemDownLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionDownLevel, new Class[] { IItemAction.class }, getServiceContext()));
        this.menuItemDownLevel.setText(resHelper.getString("menuItemDownLevel.text"));
        // menuItemImportExcel
        this.menuItemImportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportExcel, new Class[] { IItemAction.class }, getServiceContext()));
        this.menuItemImportExcel.setText(resHelper.getString("menuItemImportExcel.text"));
        // menuItemExportExcel
        this.menuItemExportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportExcel, new Class[] { IItemAction.class }, getServiceContext()));
        this.menuItemExportExcel.setText(resHelper.getString("menuItemExportExcel.text"));
		//Register control's property binding
		registerBindings();
		registerUIState();


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
        contDescription.setBounds(new Rectangle(12, 36, 939, 19));
        this.add(contDescription, new KDLayout.Constraints(12, 36, 939, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contType
        contType.setBoundEditor(f7Type);
        //contDescription
        contDescription.setBoundEditor(txtDescription);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
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
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemColumnSetting);
        menuBiz.add(menuItemUpLevel);
        menuBiz.add(menuItemDownLevel);
        menuBiz.add(menuItemImportExcel);
        menuBiz.add(menuItemExportExcel);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
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
        this.toolBar.add(btnUpLevel);
        this.toolBar.add(btnDownLevel);
        this.toolBar.add(btnImport);
        this.toolBar.add(btnExportExcel);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
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
     * output actionColumnSetting_actionPerformed method
     */
    public void actionColumnSetting_actionPerformed(ActionEvent e) throws Exception
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
     * output actionImportExcel_actionPerformed method
     */
    public void actionImportExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output actionExportExcel_actionPerformed method
     */
    public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionColumnSetting class
     */
    protected class ActionColumnSetting extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractListingTempletEditUI.this, "ActionColumnSetting", "actionColumnSetting_actionPerformed", e);
        }
    }

    /**
     * output ActionUpLevel class
     */
    protected class ActionUpLevel extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractListingTempletEditUI.this, "ActionUpLevel", "actionUpLevel_actionPerformed", e);
        }
    }

    /**
     * output ActionDownLevel class
     */
    protected class ActionDownLevel extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractListingTempletEditUI.this, "ActionDownLevel", "actionDownLevel_actionPerformed", e);
        }
    }

    /**
     * output ActionImportExcel class
     */
    protected class ActionImportExcel extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractListingTempletEditUI.this, "ActionImportExcel", "actionImportExcel_actionPerformed", e);
        }
    }

    /**
     * output ActionExportExcel class
     */
    protected class ActionExportExcel extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractListingTempletEditUI.this, "ActionExportExcel", "actionExportExcel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "ListingTempletEditUI");
    }




}