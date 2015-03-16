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
public abstract class AbstractHeadTypeChooseUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractHeadTypeChooseUI.class);
    protected com.kingdee.bos.ctrl.swing.KDButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCancel2;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTree treeHeadType;
    protected com.kingdee.bos.ctrl.swing.KDContainer contSelected;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSelected;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRight;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLeft;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblHeadColumn;
    protected com.kingdee.bos.ctrl.swing.KDContainer contHeadType;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane splHeadType;
    protected ActionAllSelect actionAllSelect = null;
    protected ActionNoneSelect actionNoneSelect = null;
    protected ActionMoveUp actionMoveUp = null;
    protected ActionMoveDown actionMoveDown = null;
    /**
     * output class constructor
     */
    public AbstractHeadTypeChooseUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractHeadTypeChooseUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAllSelect
        this.actionAllSelect = new ActionAllSelect(this);
        getActionManager().registerAction("actionAllSelect", actionAllSelect);
         this.actionAllSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionNoneSelect
        this.actionNoneSelect = new ActionNoneSelect(this);
        getActionManager().registerAction("actionNoneSelect", actionNoneSelect);
         this.actionNoneSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMoveUp
        this.actionMoveUp = new ActionMoveUp(this);
        getActionManager().registerAction("actionMoveUp", actionMoveUp);
         this.actionMoveUp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMoveDown
        this.actionMoveDown = new ActionMoveDown(this);
        getActionManager().registerAction("actionMoveDown", actionMoveDown);
         this.actionMoveDown.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCancel2 = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDSeparator2 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.treeHeadType = new com.kingdee.bos.ctrl.swing.KDTree();
        this.contSelected = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblSelected = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnRight = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnLeft = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblHeadColumn = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contHeadType = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.splHeadType = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.btnConfirm.setName("btnConfirm");
        this.btnCancel2.setName("btnCancel2");
        this.kDSeparator2.setName("kDSeparator2");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.treeHeadType.setName("treeHeadType");
        this.contSelected.setName("contSelected");
        this.tblSelected.setName("tblSelected");
        this.btnRight.setName("btnRight");
        this.btnLeft.setName("btnLeft");
        this.tblHeadColumn.setName("tblHeadColumn");
        this.contHeadType.setName("contHeadType");
        this.splHeadType.setName("splHeadType");
        // CoreUI
        // btnConfirm		
        this.btnConfirm.setText(resHelper.getString("btnConfirm.text"));
        this.btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnConfirm_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnCancel2		
        this.btnCancel2.setText(resHelper.getString("btnCancel2.text"));
        this.btnCancel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCancel2_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDSeparator2
        // kDScrollPane1		
        this.kDScrollPane1.setDoubleBuffered(true);
        // treeHeadType
        this.treeHeadType.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeHeadType_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contSelected		
        this.contSelected.setTitle(resHelper.getString("contSelected.title"));
        // tblSelected
		String tblSelectedStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isQuoting\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"description\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{isQuoting}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblSelected.setFormatXml(resHelper.translateString("tblSelected",tblSelectedStrXML));
        this.tblSelected.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblSelected_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnRight		
        this.btnRight.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_move_right"));
        this.btnRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRight_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnLeft		
        this.btnLeft.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_move_left"));
        this.btnLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnLeft_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tblHeadColumn
		String tblHeadColumnStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"selected\" t:width=\"40\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isQuoting\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"description\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{selected}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{isQuoting}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblHeadColumn.setFormatXml(resHelper.translateString("tblHeadColumn",tblHeadColumnStrXML));
        this.tblHeadColumn.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblHeadColumn_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // contHeadType		
        this.contHeadType.setTitle(resHelper.getString("contHeadType.title"));
        // splHeadType		
        this.splHeadType.setDividerLocation(200);
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
        this.setBounds(new Rectangle(10, 10, 792, 556));
        this.setLayout(null);
        btnConfirm.setBounds(new Rectangle(606, 524, 73, 21));
        this.add(btnConfirm, null);
        btnCancel2.setBounds(new Rectangle(709, 524, 73, 21));
        this.add(btnCancel2, null);
        kDSeparator2.setBounds(new Rectangle(10, 515, 772, 2));
        this.add(kDSeparator2, null);
        contSelected.setBounds(new Rectangle(532, 10, 250, 500));
        this.add(contSelected, null);
        btnRight.setBounds(new Rectangle(495, 251, 25, 19));
        this.add(btnRight, null);
        btnLeft.setBounds(new Rectangle(495, 286, 25, 19));
        this.add(btnLeft, null);
        contHeadType.setBounds(new Rectangle(10, 10, 475, 500));
        this.add(contHeadType, null);
        //contSelected
contSelected.getContentPane().setLayout(new BorderLayout(0, 0));        contSelected.getContentPane().add(tblSelected, BorderLayout.CENTER);
        //contHeadType
contHeadType.getContentPane().setLayout(new BorderLayout(0, 0));        contHeadType.getContentPane().add(splHeadType, BorderLayout.CENTER);
        //splHeadType
        splHeadType.add(kDScrollPane1, "left");
        splHeadType.add(tblHeadColumn, "right");
        //kDScrollPane1
        kDScrollPane1.getViewport().add(treeHeadType, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.HeadTypeChooseUIHandler";
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
     * output btnConfirm_actionPerformed method
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnCancel2_actionPerformed method
     */
    protected void btnCancel2_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output treeHeadType_valueChanged method
     */
    protected void treeHeadType_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output tblSelected_tableSelectChanged method
     */
    protected void tblSelected_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output btnRight_actionPerformed method
     */
    protected void btnRight_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnLeft_actionPerformed method
     */
    protected void btnLeft_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblHeadColumn_editValueChanged method
     */
    protected void tblHeadColumn_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    	

    /**
     * output actionAllSelect_actionPerformed method
     */
    public void actionAllSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionNoneSelect_actionPerformed method
     */
    public void actionNoneSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMoveUp_actionPerformed method
     */
    public void actionMoveUp_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMoveDown_actionPerformed method
     */
    public void actionMoveDown_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAllSelect(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAllSelect() {
    	return false;
    }
	public RequestContext prepareActionNoneSelect(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionNoneSelect() {
    	return false;
    }
	public RequestContext prepareActionMoveUp(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMoveUp() {
    	return false;
    }
	public RequestContext prepareActionMoveDown(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMoveDown() {
    	return false;
    }

    /**
     * output ActionAllSelect class
     */     
    protected class ActionAllSelect extends ItemAction {     
    
        public ActionAllSelect()
        {
            this(null);
        }

        public ActionAllSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAllSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractHeadTypeChooseUI.this, "ActionAllSelect", "actionAllSelect_actionPerformed", e);
        }
    }

    /**
     * output ActionNoneSelect class
     */     
    protected class ActionNoneSelect extends ItemAction {     
    
        public ActionNoneSelect()
        {
            this(null);
        }

        public ActionNoneSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionNoneSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNoneSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNoneSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractHeadTypeChooseUI.this, "ActionNoneSelect", "actionNoneSelect_actionPerformed", e);
        }
    }

    /**
     * output ActionMoveUp class
     */     
    protected class ActionMoveUp extends ItemAction {     
    
        public ActionMoveUp()
        {
            this(null);
        }

        public ActionMoveUp(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMoveUp.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMoveUp.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMoveUp.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractHeadTypeChooseUI.this, "ActionMoveUp", "actionMoveUp_actionPerformed", e);
        }
    }

    /**
     * output ActionMoveDown class
     */     
    protected class ActionMoveDown extends ItemAction {     
    
        public ActionMoveDown()
        {
            this(null);
        }

        public ActionMoveDown(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionMoveDown.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMoveDown.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMoveDown.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractHeadTypeChooseUI.this, "ActionMoveDown", "actionMoveDown_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "HeadTypeChooseUI");
    }




}