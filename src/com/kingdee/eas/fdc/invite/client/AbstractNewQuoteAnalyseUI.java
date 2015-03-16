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
public abstract class AbstractNewQuoteAnalyseUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractNewQuoteAnalyseUI.class);
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsShowDetail;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsShowPercent;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsShowCompositor;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabbedPnlTable;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuoteSet;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnItemFilter;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSubmit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnWorkFlowG;
    protected com.kingdee.bos.ctrl.swing.KDMenu menuBiz;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuQuoteSet;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemItemFilter;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSubmit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemWorkFlowG;
    protected ActionQuoteSet actionQuoteSet = null;
    protected ActionItemFilter actionItemFilter = null;
    protected ActionSubmit actionSubmit = null;
    protected ActionWorkFlowG actionWorkFlowG = null;
    public final static String STATUS_VIEW = "VIEW";
    /**
     * output class constructor
     */
    public AbstractNewQuoteAnalyseUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractNewQuoteAnalyseUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionQuoteSet
        this.actionQuoteSet = new ActionQuoteSet(this);
        getActionManager().registerAction("actionQuoteSet", actionQuoteSet);
         this.actionQuoteSet.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionItemFilter
        this.actionItemFilter = new ActionItemFilter(this);
        getActionManager().registerAction("actionItemFilter", actionItemFilter);
         this.actionItemFilter.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSubmit
        this.actionSubmit = new ActionSubmit(this);
        getActionManager().registerAction("actionSubmit", actionSubmit);
        this.actionSubmit.setBindWorkFlow(true);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionWorkFlowG
        this.actionWorkFlowG = new ActionWorkFlowG(this);
        getActionManager().registerAction("actionWorkFlowG", actionWorkFlowG);
         this.actionWorkFlowG.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.chkIsShowDetail = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsShowPercent = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsShowCompositor = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.tabbedPnlTable = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.btnQuoteSet = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnItemFilter = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSubmit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnWorkFlowG = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuBiz = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuQuoteSet = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemItemFilter = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSubmit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemWorkFlowG = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.chkIsShowDetail.setName("chkIsShowDetail");
        this.chkIsShowPercent.setName("chkIsShowPercent");
        this.chkIsShowCompositor.setName("chkIsShowCompositor");
        this.tabbedPnlTable.setName("tabbedPnlTable");
        this.btnQuoteSet.setName("btnQuoteSet");
        this.btnItemFilter.setName("btnItemFilter");
        this.btnSubmit.setName("btnSubmit");
        this.btnWorkFlowG.setName("btnWorkFlowG");
        this.menuBiz.setName("menuBiz");
        this.menuQuoteSet.setName("menuQuoteSet");
        this.menuItemItemFilter.setName("menuItemItemFilter");
        this.menuItemSubmit.setName("menuItemSubmit");
        this.menuItemWorkFlowG.setName("menuItemWorkFlowG");
        // CoreUI
        // chkIsShowDetail		
        this.chkIsShowDetail.setText(resHelper.getString("chkIsShowDetail.text"));
        this.chkIsShowDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsShowDetail_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkIsShowPercent		
        this.chkIsShowPercent.setText(resHelper.getString("chkIsShowPercent.text"));
        this.chkIsShowPercent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsShowPercent_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkIsShowCompositor		
        this.chkIsShowCompositor.setText(resHelper.getString("chkIsShowCompositor.text"));
        this.chkIsShowCompositor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsShowCompositor_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tabbedPnlTable
        // btnQuoteSet
        this.btnQuoteSet.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuoteSet, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuoteSet.setText(resHelper.getString("btnQuoteSet.text"));		
        this.btnQuoteSet.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_pagesetting"));
        // btnItemFilter
        this.btnItemFilter.setAction((IItemAction)ActionProxyFactory.getProxy(actionItemFilter, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnItemFilter.setText(resHelper.getString("btnItemFilter.text"));
        // btnSubmit
        this.btnSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));
        // btnWorkFlowG
        this.btnWorkFlowG.setAction((IItemAction)ActionProxyFactory.getProxy(actionWorkFlowG, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnWorkFlowG.setText(resHelper.getString("btnWorkFlowG.text"));		
        this.btnWorkFlowG.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_flowchart"));
        // menuBiz		
        this.menuBiz.setText(resHelper.getString("menuBiz.text"));		
        this.menuBiz.setMnemonic(79);
        // menuQuoteSet
        this.menuQuoteSet.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuoteSet, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuQuoteSet.setText(resHelper.getString("menuQuoteSet.text"));		
        this.menuQuoteSet.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_pagesetting"));
        this.menuQuoteSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    menuQuoteSet_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // menuItemItemFilter
        this.menuItemItemFilter.setAction((IItemAction)ActionProxyFactory.getProxy(actionItemFilter, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemItemFilter.setText(resHelper.getString("menuItemItemFilter.text"));
        // menuItemSubmit
        this.menuItemSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSubmit.setText(resHelper.getString("menuItemSubmit.text"));
        // menuItemWorkFlowG
        this.menuItemWorkFlowG.setAction((IItemAction)ActionProxyFactory.getProxy(actionWorkFlowG, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemWorkFlowG.setText(resHelper.getString("menuItemWorkFlowG.text"));
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
        chkIsShowDetail.setBounds(new Rectangle(10, 10, 140, 19));
        this.add(chkIsShowDetail, new KDLayout.Constraints(10, 10, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        chkIsShowPercent.setBounds(new Rectangle(162, 10, 140, 19));
        this.add(chkIsShowPercent, new KDLayout.Constraints(162, 10, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        chkIsShowCompositor.setBounds(new Rectangle(313, 10, 140, 19));
        this.add(chkIsShowCompositor, new KDLayout.Constraints(313, 10, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        tabbedPnlTable.setBounds(new Rectangle(9, 40, 993, 585));
        this.add(tabbedPnlTable, new KDLayout.Constraints(9, 40, 993, 585, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuBiz);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuBiz
        menuBiz.add(menuQuoteSet);
        menuBiz.add(menuItemItemFilter);
        menuBiz.add(menuItemSubmit);
        menuBiz.add(menuItemWorkFlowG);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
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
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnQuoteSet);
        this.toolBar.add(btnItemFilter);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnWorkFlowG);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.NewQuoteAnalyseUIHandler";
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
    }

    /**
     * output chkIsShowDetail_actionPerformed method
     */
    protected void chkIsShowDetail_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output chkIsShowPercent_actionPerformed method
     */
    protected void chkIsShowPercent_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output chkIsShowCompositor_actionPerformed method
     */
    protected void chkIsShowCompositor_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output menuQuoteSet_actionPerformed method
     */
    protected void menuQuoteSet_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    	

    /**
     * output actionQuoteSet_actionPerformed method
     */
    public void actionQuoteSet_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionItemFilter_actionPerformed method
     */
    public void actionItemFilter_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionWorkFlowG_actionPerformed method
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionQuoteSet(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuoteSet() {
    	return false;
    }
	public RequestContext prepareActionItemFilter(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionItemFilter() {
    	return false;
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionWorkFlowG(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionWorkFlowG() {
    	return false;
    }

    /**
     * output ActionQuoteSet class
     */     
    protected class ActionQuoteSet extends ItemAction {     
    
        public ActionQuoteSet()
        {
            this(null);
        }

        public ActionQuoteSet(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQuoteSet.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuoteSet.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuoteSet.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewQuoteAnalyseUI.this, "ActionQuoteSet", "actionQuoteSet_actionPerformed", e);
        }
    }

    /**
     * output ActionItemFilter class
     */     
    protected class ActionItemFilter extends ItemAction {     
    
        public ActionItemFilter()
        {
            this(null);
        }

        public ActionItemFilter(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionItemFilter.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionItemFilter.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionItemFilter.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewQuoteAnalyseUI.this, "ActionItemFilter", "actionItemFilter_actionPerformed", e);
        }
    }

    /**
     * output ActionSubmit class
     */     
    protected class ActionSubmit extends ItemAction {     
    
        public ActionSubmit()
        {
            this(null);
        }

        public ActionSubmit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewQuoteAnalyseUI.this, "ActionSubmit", "actionSubmit_actionPerformed", e);
        }
    }

    /**
     * output ActionWorkFlowG class
     */     
    protected class ActionWorkFlowG extends ItemAction {     
    
        public ActionWorkFlowG()
        {
            this(null);
        }

        public ActionWorkFlowG(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionWorkFlowG.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWorkFlowG.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWorkFlowG.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewQuoteAnalyseUI.this, "ActionWorkFlowG", "actionWorkFlowG_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "NewQuoteAnalyseUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}