/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

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
public abstract class AbstractF7RelatedTaskSelectUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractF7RelatedTaskSelectUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnOK;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancel;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkShowAllTask;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDown;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUp;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain;
    protected ActionDown actionDown = null;
    protected ActionUpMove actionUpMove = null;
    protected ActionUpAll actionUpAll = null;
    protected ActionDownAll actionDownAll = null;
    protected ActionOK actionOK = null;
    protected ActionCancle actionCancle = null;
    protected ActionShowAllTask actionShowAllTask = null;
    /**
     * output class constructor
     */
    public AbstractF7RelatedTaskSelectUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractF7RelatedTaskSelectUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionDown
        this.actionDown = new ActionDown(this);
        getActionManager().registerAction("actionDown", actionDown);
         this.actionDown.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpMove
        this.actionUpMove = new ActionUpMove(this);
        getActionManager().registerAction("actionUpMove", actionUpMove);
         this.actionUpMove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpAll
        this.actionUpAll = new ActionUpAll(this);
        getActionManager().registerAction("actionUpAll", actionUpAll);
         this.actionUpAll.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDownAll
        this.actionDownAll = new ActionDownAll(this);
        getActionManager().registerAction("actionDownAll", actionDownAll);
         this.actionDownAll.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionOK
        this.actionOK = new ActionOK(this);
        getActionManager().registerAction("actionOK", actionOK);
         this.actionOK.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancle
        this.actionCancle = new ActionCancle(this);
        getActionManager().registerAction("actionCancle", actionCancle);
         this.actionCancle.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowAllTask
        this.actionShowAllTask = new ActionShowAllTask(this);
        getActionManager().registerAction("actionShowAllTask", actionShowAllTask);
         this.actionShowAllTask.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.btnOK = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.chkShowAllTask = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDTreeView1 = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnDown = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUp = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.btnOK.setName("btnOK");
        this.btnCancel.setName("btnCancel");
        this.chkShowAllTask.setName("chkShowAllTask");
        this.kDTreeView1.setName("kDTreeView1");
        this.kDPanel1.setName("kDPanel1");
        this.treeMain.setName("treeMain");
        this.kDContainer1.setName("kDContainer1");
        this.btnDown.setName("btnDown");
        this.btnUp.setName("btnUp");
        this.tblMain.setName("tblMain");
        // CoreUI		
        this.setPreferredSize(new Dimension(800,600));
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(250);
        // btnOK
        this.btnOK.setAction((IItemAction)ActionProxyFactory.getProxy(actionOK, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnOK.setText(resHelper.getString("btnOK.text"));
        this.btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnOK_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnCancel
        this.btnCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancle, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));
        // chkShowAllTask
        this.chkShowAllTask.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowAllTask, new Class[] { IItemAction.class }, getServiceContext()));		
        this.chkShowAllTask.setText(resHelper.getString("chkShowAllTask.text"));
        // kDTreeView1		
        this.kDTreeView1.setShowButton(false);		
        this.kDTreeView1.setTitle(resHelper.getString("kDTreeView1.title"));
        // kDPanel1
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
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // btnDown
        this.btnDown.setAction((IItemAction)ActionProxyFactory.getProxy(actionDown, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDown.setText(resHelper.getString("btnDown.text"));		
        this.btnDown.setVisible(false);
        this.btnDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDown_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnUp
        this.btnUp.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpMove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUp.setText(resHelper.getString("btnUp.text"));		
        this.btnUp.setVisible(false);
        this.btnUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnUp_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tblMain
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"planStartData\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"planEndData\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"adminDept\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"adminPerson\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"isLeaf\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"complete\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{planStartData}</t:Cell><t:Cell>$Resource{planEndData}</t:Cell><t:Cell>$Resource{adminDept}</t:Cell><t:Cell>$Resource{adminPerson}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{isLeaf}</t:Cell><t:Cell>$Resource{complete}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblMain_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

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
        this.setBounds(new Rectangle(10, 10, 800, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 800, 600));
        kDSplitPane1.setBounds(new Rectangle(10, 10, 780, 550));
        this.add(kDSplitPane1, new KDLayout.Constraints(10, 10, 780, 550, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnOK.setBounds(new Rectangle(570, 570, 80, 19));
        this.add(btnOK, new KDLayout.Constraints(570, 570, 80, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT));
        btnCancel.setBounds(new Rectangle(660, 570, 80, 19));
        this.add(btnCancel, new KDLayout.Constraints(660, 570, 80, 19, 0));
        chkShowAllTask.setBounds(new Rectangle(272, 566, 140, 19));
        this.add(chkShowAllTask, new KDLayout.Constraints(272, 566, 140, 19, 0));
        //kDSplitPane1
        kDSplitPane1.add(kDTreeView1, "left");
        kDSplitPane1.add(kDPanel1, "right");
        //kDTreeView1
        kDTreeView1.setTree(treeMain);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 519, 549));        kDContainer1.setBounds(new Rectangle(0, 0, 520, 550));
        kDPanel1.add(kDContainer1, new KDLayout.Constraints(0, 0, 520, 550, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnDown.setBounds(new Rectangle(15, 527, 60, 19));
        kDPanel1.add(btnDown, new KDLayout.Constraints(15, 527, 60, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        btnUp.setBounds(new Rectangle(86, 527, 60, 19));
        kDPanel1.add(btnUp, new KDLayout.Constraints(86, 527, 60, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(tblMain, BorderLayout.CENTER);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
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
        this.toolBar.add(btnPageSetup);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.F7RelatedTaskSelectUIHandler";
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
     * output btnOK_actionPerformed method
     */
    protected void btnOK_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output btnDown_actionPerformed method
     */
    protected void btnDown_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnUp_actionPerformed method
     */
    protected void btnUp_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    	

    /**
     * output actionDown_actionPerformed method
     */
    public void actionDown_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUpMove_actionPerformed method
     */
    public void actionUpMove_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUpAll_actionPerformed method
     */
    public void actionUpAll_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDownAll_actionPerformed method
     */
    public void actionDownAll_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionOK_actionPerformed method
     */
    public void actionOK_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancle_actionPerformed method
     */
    public void actionCancle_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowAllTask_actionPerformed method
     */
    public void actionShowAllTask_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionDown(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDown() {
    	return false;
    }
	public RequestContext prepareActionUpMove(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpMove() {
    	return false;
    }
	public RequestContext prepareActionUpAll(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpAll() {
    	return false;
    }
	public RequestContext prepareActionDownAll(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDownAll() {
    	return false;
    }
	public RequestContext prepareActionOK(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOK() {
    	return false;
    }
	public RequestContext prepareActionCancle(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancle() {
    	return false;
    }
	public RequestContext prepareActionShowAllTask(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowAllTask() {
    	return false;
    }

    /**
     * output ActionDown class
     */     
    protected class ActionDown extends ItemAction {     
    
        public ActionDown()
        {
            this(null);
        }

        public ActionDown(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDown.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDown.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDown.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7RelatedTaskSelectUI.this, "ActionDown", "actionDown_actionPerformed", e);
        }
    }

    /**
     * output ActionUpMove class
     */     
    protected class ActionUpMove extends ItemAction {     
    
        public ActionUpMove()
        {
            this(null);
        }

        public ActionUpMove(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUpMove.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpMove.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpMove.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7RelatedTaskSelectUI.this, "ActionUpMove", "actionUpMove_actionPerformed", e);
        }
    }

    /**
     * output ActionUpAll class
     */     
    protected class ActionUpAll extends ItemAction {     
    
        public ActionUpAll()
        {
            this(null);
        }

        public ActionUpAll(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUpAll.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpAll.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpAll.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7RelatedTaskSelectUI.this, "ActionUpAll", "actionUpAll_actionPerformed", e);
        }
    }

    /**
     * output ActionDownAll class
     */     
    protected class ActionDownAll extends ItemAction {     
    
        public ActionDownAll()
        {
            this(null);
        }

        public ActionDownAll(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDownAll.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDownAll.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDownAll.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7RelatedTaskSelectUI.this, "ActionDownAll", "actionDownAll_actionPerformed", e);
        }
    }

    /**
     * output ActionOK class
     */     
    protected class ActionOK extends ItemAction {     
    
        public ActionOK()
        {
            this(null);
        }

        public ActionOK(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionOK.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOK.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOK.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7RelatedTaskSelectUI.this, "ActionOK", "actionOK_actionPerformed", e);
        }
    }

    /**
     * output ActionCancle class
     */     
    protected class ActionCancle extends ItemAction {     
    
        public ActionCancle()
        {
            this(null);
        }

        public ActionCancle(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCancle.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancle.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancle.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7RelatedTaskSelectUI.this, "ActionCancle", "actionCancle_actionPerformed", e);
        }
    }

    /**
     * output ActionShowAllTask class
     */     
    protected class ActionShowAllTask extends ItemAction {     
    
        public ActionShowAllTask()
        {
            this(null);
        }

        public ActionShowAllTask(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionShowAllTask.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowAllTask.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowAllTask.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractF7RelatedTaskSelectUI.this, "ActionShowAllTask", "actionShowAllTask_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "F7RelatedTaskSelectUI");
    }




}