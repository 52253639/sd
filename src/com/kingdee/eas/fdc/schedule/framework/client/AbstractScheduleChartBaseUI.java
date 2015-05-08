/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework.client;

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
public abstract class AbstractScheduleChartBaseUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractScheduleChartBaseUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane sptLeft;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeView;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane sptTop;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDPanel ctChart1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYear;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlChart;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbYear;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPrint;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPrintPreview;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExportIMG;
    protected com.kingdee.bos.ctrl.swing.KDMenu meueView;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPrint;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPrintPre;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExpIMG;
    protected ActionPrint actionPrint = null;
    protected ActionPrintPreview actionPrintPreview = null;
    protected ActionExportIMG actionExportIMG = null;
    /**
     * output class constructor
     */
    public AbstractScheduleChartBaseUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractScheduleChartBaseUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionPrint
        this.actionPrint = new ActionPrint(this);
        getActionManager().registerAction("actionPrint", actionPrint);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrintPreview
        this.actionPrintPreview = new ActionPrintPreview(this);
        getActionManager().registerAction("actionPrintPreview", actionPrintPreview);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExportIMG
        this.actionExportIMG = new ActionExportIMG(this);
        getActionManager().registerAction("actionExportIMG", actionExportIMG);
         this.actionExportIMG.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.sptLeft = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.treeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.sptTop = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.ctChart1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pnlChart = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.cbYear = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPrintPreview = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExportIMG = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.meueView = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.menuItemPrint = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPrintPre = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.menuItemExpIMG = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.sptLeft.setName("sptLeft");
        this.treeView.setName("treeView");
        this.sptTop.setName("sptTop");
        this.treeMain.setName("treeMain");
        this.ctChart1.setName("ctChart1");
        this.contYear.setName("contYear");
        this.pnlChart.setName("pnlChart");
        this.cbYear.setName("cbYear");
        this.btnPrint.setName("btnPrint");
        this.btnPrintPreview.setName("btnPrintPreview");
        this.btnExportIMG.setName("btnExportIMG");
        this.meueView.setName("meueView");
        this.menuItemPrint.setName("menuItemPrint");
        this.menuItemPrintPre.setName("menuItemPrintPre");
        this.kDSeparator2.setName("kDSeparator2");
        this.menuItemExpIMG.setName("menuItemExpIMG");
        // CoreUI
        // sptLeft		
        this.sptLeft.setDividerLocation(250);
        // treeView		
        this.treeView.setShowButton(false);		
        this.treeView.setShowControlPanel(false);
        // sptTop		
        this.sptTop.setOrientation(0);		
        this.sptTop.setDividerLocation(500);
        // treeMain
        this.treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // ctChart1
        // contYear		
        this.contYear.setBoundLabelText(resHelper.getString("contYear.boundLabelText"));		
        this.contYear.setBoundLabelLength(100);		
        this.contYear.setBoundLabelUnderline(true);
        // pnlChart
        // cbYear
        this.cbYear.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cbYear_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnPrint
        this.btnPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrint.setText(resHelper.getString("btnPrint.text"));
        // btnPrintPreview
        this.btnPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrintPreview.setText(resHelper.getString("btnPrintPreview.text"));
        // btnExportIMG
        this.btnExportIMG.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportIMG, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExportIMG.setText(resHelper.getString("btnExportIMG.text"));
        // meueView		
        this.meueView.setText(resHelper.getString("meueView.text"));		
        this.meueView.setToolTipText(resHelper.getString("meueView.toolTipText"));		
        this.meueView.setMnemonic(86);
        // menuItemPrint
        this.menuItemPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrint.setText(resHelper.getString("menuItemPrint.text"));		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrint.setToolTipText(resHelper.getString("menuItemPrint.toolTipText"));		
        this.menuItemPrint.setMnemonic(80);
        // menuItemPrintPre
        this.menuItemPrintPre.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPrintPre.setText(resHelper.getString("menuItemPrintPre.text"));		
        this.menuItemPrintPre.setVisible(false);		
        this.menuItemPrintPre.setToolTipText(resHelper.getString("menuItemPrintPre.toolTipText"));		
        this.menuItemPrintPre.setMnemonic(86);
        // kDSeparator2
        // menuItemExpIMG
        this.menuItemExpIMG.setAction((IItemAction)ActionProxyFactory.getProxy(actionExportIMG, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExpIMG.setText(resHelper.getString("menuItemExpIMG.text"));		
        this.menuItemExpIMG.setToolTipText(resHelper.getString("menuItemExpIMG.toolTipText"));		
        this.menuItemExpIMG.setMnemonic(69);
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
        sptLeft.setBounds(new Rectangle(10, 10, 993, 609));
        this.add(sptLeft, new KDLayout.Constraints(10, 10, 993, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //sptLeft
        sptLeft.add(treeView, "left");
        sptLeft.add(sptTop, "right");
        //treeView
        treeView.setTree(treeMain);
        //sptTop
        sptTop.add(ctChart1, "top");
        //ctChart1
        ctChart1.setLayout(new KDLayout());
        ctChart1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 731, 499));        contYear.setBounds(new Rectangle(10, 10, 220, 19));
        ctChart1.add(contYear, new KDLayout.Constraints(10, 10, 220, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        pnlChart.setBounds(new Rectangle(10, 40, 713, 431));
        ctChart1.add(pnlChart, new KDLayout.Constraints(10, 40, 713, 431, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contYear
        contYear.setBoundEditor(cbYear);
pnlChart.setLayout(new BorderLayout(0, 0));
    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(meueView);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //meueView
        meueView.add(menuItemPrint);
        meueView.add(menuItemPrintPre);
        meueView.add(kDSeparator2);
        meueView.add(menuItemExpIMG);
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
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnExportIMG);
        this.toolBar.add(btnPageSetup);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.framework.app.ScheduleChartBaseUIHandler";
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
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output cbYear_itemStateChanged method
     */
    protected void cbYear_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    	

    /**
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExportIMG_actionPerformed method
     */
    public void actionExportIMG_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }
	public RequestContext prepareActionExportIMG(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExportIMG() {
    	return false;
    }

    /**
     * output ActionPrint class
     */     
    protected class ActionPrint extends ItemAction {     
    
        public ActionPrint()
        {
            this(null);
        }

        public ActionPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleChartBaseUI.this, "ActionPrint", "actionPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionPrintPreview class
     */     
    protected class ActionPrintPreview extends ItemAction {     
    
        public ActionPrintPreview()
        {
            this(null);
        }

        public ActionPrintPreview(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrintPreview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleChartBaseUI.this, "ActionPrintPreview", "actionPrintPreview_actionPerformed", e);
        }
    }

    /**
     * output ActionExportIMG class
     */     
    protected class ActionExportIMG extends ItemAction {     
    
        public ActionExportIMG()
        {
            this(null);
        }

        public ActionExportIMG(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionExportIMG.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportIMG.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExportIMG.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleChartBaseUI.this, "ActionExportIMG", "actionExportIMG_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.framework.client", "ScheduleChartBaseUI");
    }




}