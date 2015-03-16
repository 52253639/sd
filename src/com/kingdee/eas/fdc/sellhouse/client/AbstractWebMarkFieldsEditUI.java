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
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractWebMarkFieldsEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractWebMarkFieldsEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbFunc;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbMetaType;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdpWebMeta;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView1;
    protected com.kingdee.bos.ctrl.swing.KDTree kDTreeWeb;
    protected com.kingdee.bos.ctrl.swing.KDButton btnRefreshWeb;
    protected com.kingdee.bos.ctrl.swing.KDButton btnRefreshMate;
    protected com.kingdee.bos.ctrl.swing.KDButton btnDelMeta;
    protected com.kingdee.bos.ctrl.swing.KDButton btnAddMeta;
    protected com.kingdee.bos.ctrl.swing.KDButton btnDownMeta;
    protected com.kingdee.bos.ctrl.swing.KDButton btnUpMeta;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblEntrys;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbProc;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMsg;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdPWebBowser;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane sclPanel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtURL;
    protected com.kingdee.eas.fdc.sellhouse.WebMarkFieldInfo editData = null;
    protected ActionAddMeta actionAddMeta = null;
    protected ActionDelMeta actionDelMeta = null;
    protected ActionUpMeta actionUpMeta = null;
    protected ActionDownMeta actionDownMeta = null;
    protected ActionRefreshMate actionRefreshMate = null;
    protected ActionRefreshWeb actionRefreshWeb = null;
    protected ActionAddRow actionAddRow = null;
    protected ActionInsertRow actionInsertRow = null;
    protected ActionDelRow actionDelRow = null;
    /**
     * output class constructor
     */
    public AbstractWebMarkFieldsEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractWebMarkFieldsEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddMeta
        this.actionAddMeta = new ActionAddMeta(this);
        getActionManager().registerAction("actionAddMeta", actionAddMeta);
         this.actionAddMeta.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelMeta
        this.actionDelMeta = new ActionDelMeta(this);
        getActionManager().registerAction("actionDelMeta", actionDelMeta);
         this.actionDelMeta.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpMeta
        this.actionUpMeta = new ActionUpMeta(this);
        getActionManager().registerAction("actionUpMeta", actionUpMeta);
         this.actionUpMeta.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDownMeta
        this.actionDownMeta = new ActionDownMeta(this);
        getActionManager().registerAction("actionDownMeta", actionDownMeta);
         this.actionDownMeta.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRefreshMate
        this.actionRefreshMate = new ActionRefreshMate(this);
        getActionManager().registerAction("actionRefreshMate", actionRefreshMate);
         this.actionRefreshMate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRefreshWeb
        this.actionRefreshWeb = new ActionRefreshWeb(this);
        getActionManager().registerAction("actionRefreshWeb", actionRefreshWeb);
         this.actionRefreshWeb.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddRow
        this.actionAddRow = new ActionAddRow(this);
        getActionManager().registerAction("actionAddRow", actionAddRow);
         this.actionAddRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInsertRow
        this.actionInsertRow = new ActionInsertRow(this);
        getActionManager().registerAction("actionInsertRow", actionInsertRow);
         this.actionInsertRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelRow
        this.actionDelRow = new ActionDelRow(this);
        getActionManager().registerAction("actionDelRow", actionDelRow);
         this.actionDelRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cmbFunc = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cmbMetaType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kdpWebMeta = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTreeView1 = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.kDTreeWeb = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnRefreshWeb = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnRefreshMate = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnDelMeta = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnAddMeta = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnDownMeta = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnUpMeta = new com.kingdee.bos.ctrl.swing.KDButton();
        this.tblEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.cmbProc = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtMsg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdPWebBowser = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.sclPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtURL = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.cmbFunc.setName("cmbFunc");
        this.cmbMetaType.setName("cmbMetaType");
        this.kdpWebMeta.setName("kdpWebMeta");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDTreeView1.setName("kDTreeView1");
        this.kDTreeWeb.setName("kDTreeWeb");
        this.btnRefreshWeb.setName("btnRefreshWeb");
        this.btnRefreshMate.setName("btnRefreshMate");
        this.btnDelMeta.setName("btnDelMeta");
        this.btnAddMeta.setName("btnAddMeta");
        this.btnDownMeta.setName("btnDownMeta");
        this.btnUpMeta.setName("btnUpMeta");
        this.tblEntrys.setName("tblEntrys");
        this.cmbProc.setName("cmbProc");
        this.txtMsg.setName("txtMsg");
        this.kdPWebBowser.setName("kdPWebBowser");
        this.sclPanel.setName("sclPanel");
        this.txtURL.setName("txtURL");
        // CoreUI
        // kDLabelContainer1
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));
        this.kDLabelContainer1.setBoundLabelLength(50);
        // kDLabelContainer2
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));
        this.kDLabelContainer2.setBoundLabelLength(50);
        // cmbFunc
        // cmbMetaType
        // kdpWebMeta
        this.kdpWebMeta.setBorder(BorderFactory.createLineBorder(Color.black));
        // kDLabelContainer3
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));
        this.kDLabelContainer3.setBoundLabelLength(50);
        // kDTreeView1
        // kDTreeWeb
        // btnRefreshWeb
        this.btnRefreshWeb.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefreshWeb, new Class[] { IItemAction.class }, getServiceContext()));
        this.btnRefreshWeb.setText(resHelper.getString("btnRefreshWeb.text"));
        // btnRefreshMate
        this.btnRefreshMate.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefreshMate, new Class[] { IItemAction.class }, getServiceContext()));
        this.btnRefreshMate.setText(resHelper.getString("btnRefreshMate.text"));
        // btnDelMeta
        this.btnDelMeta.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelMeta, new Class[] { IItemAction.class }, getServiceContext()));
        this.btnDelMeta.setText(resHelper.getString("btnDelMeta.text"));
        // btnAddMeta
        this.btnAddMeta.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddMeta, new Class[] { IItemAction.class }, getServiceContext()));
        this.btnAddMeta.setText(resHelper.getString("btnAddMeta.text"));
        // btnDownMeta
        this.btnDownMeta.setAction((IItemAction)ActionProxyFactory.getProxy(actionDownMeta, new Class[] { IItemAction.class }, getServiceContext()));
        this.btnDownMeta.setText(resHelper.getString("btnDownMeta.text"));
        // btnUpMeta
        this.btnUpMeta.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpMeta, new Class[] { IItemAction.class }, getServiceContext()));
        this.btnUpMeta.setText(resHelper.getString("btnUpMeta.text"));
        // tblEntrys
        this.tblEntrys.setFormatXml(resHelper.getString("tblEntrys.formatXml"));

        

        // cmbProc
        // txtMsg
        this.txtMsg.setEnabled(false);
        // kdPWebBowser
        this.kdPWebBowser.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kdPWebBowser.border.title")));
        // sclPanel
        // txtURL
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 750));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(13, 5, 268, 20));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(13, 34, 268, 20));
        this.add(kDLabelContainer2, null);
        kdpWebMeta.setBounds(new Rectangle(10, 62, 277, 651));
        this.add(kdpWebMeta, null);
        btnRefreshWeb.setBounds(new Rectangle(292, 187, 85, 21));
        this.add(btnRefreshWeb, null);
        btnRefreshMate.setBounds(new Rectangle(292, 153, 85, 21));
        this.add(btnRefreshMate, null);
        btnDelMeta.setBounds(new Rectangle(292, 98, 85, 21));
        this.add(btnDelMeta, null);
        btnAddMeta.setBounds(new Rectangle(292, 68, 85, 21));
        this.add(btnAddMeta, null);
        btnDownMeta.setBounds(new Rectangle(292, 35, 85, 21));
        this.add(btnDownMeta, null);
        btnUpMeta.setBounds(new Rectangle(292, 5, 85, 21));
        this.add(btnUpMeta, null);
        tblEntrys.setBounds(new Rectangle(383, 6, 620, 168));
        this.add(tblEntrys, null);
        txtMsg.setBounds(new Rectangle(12, 719, 991, 27));
        this.add(txtMsg, null);
        kdPWebBowser.setBounds(new Rectangle(297, 216, 705, 497));
        this.add(kdPWebBowser, null);
        txtURL.setBounds(new Rectangle(383, 186, 620, 19));
        this.add(txtURL, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(cmbFunc);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(cmbProc);
        //kdpWebMeta
        kdpWebMeta.setLayout(new KDLayout());
        kdpWebMeta.putClientProperty("OriginalBounds", new Rectangle(10, 62, 277, 651));        kDLabelContainer3.setBounds(new Rectangle(3, 8, 268, 20));
        kdpWebMeta.add(kDLabelContainer3, new KDLayout.Constraints(3, 8, 268, 20, 0));
        kDTreeView1.setBounds(new Rectangle(3, 32, 270, 613));
        kdpWebMeta.add(kDTreeView1, new KDLayout.Constraints(3, 32, 270, 613, 0));
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(cmbMetaType);
        //kDTreeView1
        kDTreeView1.setTree(kDTreeWeb);
        //kdPWebBowser
kdPWebBowser.setLayout(new BorderLayout(0, 0));        kdPWebBowser.add(sclPanel, BorderLayout.CENTER);

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
        //menuTool
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.WebMarkFieldInfo)ov;
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
     * output actionAddMeta_actionPerformed method
     */
    public void actionAddMeta_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output actionDelMeta_actionPerformed method
     */
    public void actionDelMeta_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output actionUpMeta_actionPerformed method
     */
    public void actionUpMeta_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output actionDownMeta_actionPerformed method
     */
    public void actionDownMeta_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output actionRefreshMate_actionPerformed method
     */
    public void actionRefreshMate_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output actionRefreshWeb_actionPerformed method
     */
    public void actionRefreshWeb_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output actionAddRow_actionPerformed method
     */
    public void actionAddRow_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output actionInsertRow_actionPerformed method
     */
    public void actionInsertRow_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output actionDelRow_actionPerformed method
     */
    public void actionDelRow_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionAddMeta class
     */
    protected class ActionAddMeta extends ItemAction
    {
        public ActionAddMeta()
        {
            this(null);
        }

        public ActionAddMeta(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddMeta.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddMeta.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddMeta.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWebMarkFieldsEditUI.this, "ActionAddMeta", "actionAddMeta_actionPerformed", e);
        }
    }

    /**
     * output ActionDelMeta class
     */
    protected class ActionDelMeta extends ItemAction
    {
        public ActionDelMeta()
        {
            this(null);
        }

        public ActionDelMeta(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDelMeta.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelMeta.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelMeta.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWebMarkFieldsEditUI.this, "ActionDelMeta", "actionDelMeta_actionPerformed", e);
        }
    }

    /**
     * output ActionUpMeta class
     */
    protected class ActionUpMeta extends ItemAction
    {
        public ActionUpMeta()
        {
            this(null);
        }

        public ActionUpMeta(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUpMeta.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpMeta.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpMeta.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWebMarkFieldsEditUI.this, "ActionUpMeta", "actionUpMeta_actionPerformed", e);
        }
    }

    /**
     * output ActionDownMeta class
     */
    protected class ActionDownMeta extends ItemAction
    {
        public ActionDownMeta()
        {
            this(null);
        }

        public ActionDownMeta(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDownMeta.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDownMeta.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDownMeta.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWebMarkFieldsEditUI.this, "ActionDownMeta", "actionDownMeta_actionPerformed", e);
        }
    }

    /**
     * output ActionRefreshMate class
     */
    protected class ActionRefreshMate extends ItemAction
    {
        public ActionRefreshMate()
        {
            this(null);
        }

        public ActionRefreshMate(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRefreshMate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefreshMate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefreshMate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWebMarkFieldsEditUI.this, "ActionRefreshMate", "actionRefreshMate_actionPerformed", e);
        }
    }

    /**
     * output ActionRefreshWeb class
     */
    protected class ActionRefreshWeb extends ItemAction
    {
        public ActionRefreshWeb()
        {
            this(null);
        }

        public ActionRefreshWeb(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRefreshWeb.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefreshWeb.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefreshWeb.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWebMarkFieldsEditUI.this, "ActionRefreshWeb", "actionRefreshWeb_actionPerformed", e);
        }
    }

    /**
     * output ActionAddRow class
     */
    protected class ActionAddRow extends ItemAction
    {
        public ActionAddRow()
        {
            this(null);
        }

        public ActionAddRow(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWebMarkFieldsEditUI.this, "ActionAddRow", "actionAddRow_actionPerformed", e);
        }
    }

    /**
     * output ActionInsertRow class
     */
    protected class ActionInsertRow extends ItemAction
    {
        public ActionInsertRow()
        {
            this(null);
        }

        public ActionInsertRow(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionInsertRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInsertRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInsertRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWebMarkFieldsEditUI.this, "ActionInsertRow", "actionInsertRow_actionPerformed", e);
        }
    }

    /**
     * output ActionDelRow class
     */
    protected class ActionDelRow extends ItemAction
    {
        public ActionDelRow()
        {
            this(null);
        }

        public ActionDelRow(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDelRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWebMarkFieldsEditUI.this, "ActionDelRow", "actionDelRow_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "WebMarkFieldsEditUI");
    }




}