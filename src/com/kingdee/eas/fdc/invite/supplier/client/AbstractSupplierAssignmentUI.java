/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

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
public abstract class AbstractSupplierAssignmentUI extends com.kingdee.eas.basedata.framework.client.DataBaseDAssignmentUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSupplierAssignmentUI.class);
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIncAssignAssist;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAssign;
    protected ActionBatchAssign actionBatchAssign = null;
    /**
     * output class constructor
     */
    public AbstractSupplierAssignmentUI() throws Exception
    {
        super();
        this.defaultObjectName = "ctrlUnitQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractSupplierAssignmentUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        ctrlUnitQueryPK = new MetaDataPK("com.kingdee.eas.basedata.framework.app", "CtrlUnitQuery");
        //actionBatchAssign
        this.actionBatchAssign = new ActionBatchAssign(this);
        getActionManager().registerAction("actionBatchAssign", actionBatchAssign);
         this.actionBatchAssign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.chkIncAssignAssist = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.btnAssign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.chkIncAssignAssist.setName("chkIncAssignAssist");
        this.btnAssign.setName("btnAssign");
        // CoreUI
		String tblCUStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"sel\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"number\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"simpleName\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"level\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"isLeaf\" t:width=\"130\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{sel}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{simpleName}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{isLeaf}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";

                this.tblCU.putBindContents("ctrlUnitQuery",new String[] {"","number","name","simpleName","level","idLeaf","id"});



        
		
        this.btnQuery.setVisible(false);		
        this.btnCUQuery.setVisible(false);		
        this.btnBDQuery.setVisible(false);
        // chkIncAssignAssist		
        this.chkIncAssignAssist.setText(resHelper.getString("chkIncAssignAssist.text"));		
        this.chkIncAssignAssist.setSelected(true);		
        this.chkIncAssignAssist.setVisible(false);
        // btnAssign
        this.btnAssign.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchAssign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAssign.setText(resHelper.getString("btnAssign.text"));		
        this.btnAssign.setVisible(false);
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
        this.setBounds(new Rectangle(10, 10, 900, 553));
        this.setLayout(null);
        kDLabel1.setBounds(new Rectangle(10, 10, 104, 19));
        this.add(kDLabel1, null);
        rbShowDetailCU.setBounds(new Rectangle(124, 10, 179, 19));
        this.add(rbShowDetailCU, null);
        rbShowAllCU.setBounds(new Rectangle(300, 10, 146, 19));
        this.add(rbShowAllCU, null);
        tblCU.setBounds(new Rectangle(10, 35, 879, 133));
        this.add(tblCU, null);
        kDSeparator2.setBounds(new Rectangle(4, 179, 897, 8));
        this.add(kDSeparator2, null);
        kDLabel2.setBounds(new Rectangle(10, 189, 80, 19));
        this.add(kDLabel2, null);
        rbAll.setBounds(new Rectangle(239, 189, 55, 19));
        this.add(rbAll, null);
        rbAssigned.setBounds(new Rectangle(315, 189, 65, 19));
        this.add(rbAssigned, null);
        rbUnassigned.setBounds(new Rectangle(403, 189, 65, 19));
        this.add(rbUnassigned, null);
        btnAllSel.setBounds(new Rectangle(760, 189, 61, 19));
        this.add(btnAllSel, null);
        btnAllClr.setBounds(new Rectangle(828, 189, 61, 19));
        this.add(btnAllClr, null);
        tblData.setBounds(new Rectangle(10, 218, 879, 325));
        this.add(tblData, null);
        rbNone.setBounds(new Rectangle(320, 189, 60, 19));
        this.add(rbNone, null);
        btnCUAllSlt.setBounds(new Rectangle(760, 10, 61, 19));
        this.add(btnCUAllSlt, null);
        btnCUAllClr.setBounds(new Rectangle(828, 10, 61, 19));
        this.add(btnCUAllClr, null);
        dataSearchField.setBounds(new Rectangle(484, 189, 87, 19));
        this.add(dataSearchField, null);
        dataTextField.setBounds(new Rectangle(576, 189, 106, 19));
        this.add(dataTextField, null);
        btnDataSearch.setBounds(new Rectangle(689, 189, 65, 19));
        this.add(btnDataSearch, null);
        cuSearchField.setBounds(new Rectangle(484, 10, 87, 19));
        this.add(cuSearchField, null);
        cuTextField.setBounds(new Rectangle(576, 10, 106, 19));
        this.add(cuTextField, null);
        btnCUSearch.setBounds(new Rectangle(689, 10, 65, 19));
        this.add(btnCUSearch, null);
        chkIncAssignAssist.setBounds(new Rectangle(87, 189, 144, 19));
        this.add(chkIncAssignAssist, null);

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
        this.toolBar.add(btnAssign);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnCUQuery);
        this.toolBar.add(btnBDQuery);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.app.SupplierAssignmentUIHandler";
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        
    	

    /**
     * output actionBatchAssign_actionPerformed method
     */
    public void actionBatchAssign_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionBatchAssign(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatchAssign() {
    	return false;
    }

    /**
     * output ActionBatchAssign class
     */     
    protected class ActionBatchAssign extends ItemAction {     
    
        public ActionBatchAssign()
        {
            this(null);
        }

        public ActionBatchAssign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBatchAssign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchAssign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchAssign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierAssignmentUI.this, "ActionBatchAssign", "actionBatchAssign_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "SupplierAssignmentUI");
    }




}