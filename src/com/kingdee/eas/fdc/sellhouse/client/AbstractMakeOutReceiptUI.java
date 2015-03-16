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
public abstract class AbstractMakeOutReceiptUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMakeOutReceiptUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcReceiptTXT;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcReceiptF7;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCustomer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtReciving;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField tntRecAmt;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtReceipt;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRcpStatus;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtReceipt;
    protected ActionConfirm actionConfirm = null;
    protected ActionCancel actionCancel = null;
    /**
     * output class constructor
     */
    public AbstractMakeOutReceiptUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMakeOutReceiptUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionConfirm
        this.actionConfirm = new ActionConfirm(this);
        getActionManager().registerAction("actionConfirm", actionConfirm);
         this.actionConfirm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancel
        this.actionCancel = new ActionCancel(this);
        getActionManager().registerAction("actionCancel", actionCancel);
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcReceiptTXT = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.lcReceiptF7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtReciving = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tntRecAmt = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtReceipt = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRcpStatus = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtReceipt = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.lcReceiptTXT.setName("lcReceiptTXT");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.btnConfirm.setName("btnConfirm");
        this.btnCancel.setName("btnCancel");
        this.lcReceiptF7.setName("lcReceiptF7");
        this.prmtRoom.setName("prmtRoom");
        this.prmtCustomer.setName("prmtCustomer");
        this.prmtReciving.setName("prmtReciving");
        this.tntRecAmt.setName("tntRecAmt");
        this.txtReceipt.setName("txtReceipt");
        this.txtRcpStatus.setName("txtRcpStatus");
        this.txtDescription.setName("txtDescription");
        this.prmtReceipt.setName("prmtReceipt");
        // CoreUI		
        this.setPreferredSize(new Dimension(670,170));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // lcReceiptTXT		
        this.lcReceiptTXT.setBoundLabelText(resHelper.getString("lcReceiptTXT.boundLabelText"));		
        this.lcReceiptTXT.setBoundLabelLength(100);		
        this.lcReceiptTXT.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // btnConfirm
        this.btnConfirm.setAction((IItemAction)ActionProxyFactory.getProxy(actionConfirm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnConfirm.setText(resHelper.getString("btnConfirm.text"));
        // btnCancel
        this.btnCancel.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));
        // lcReceiptF7		
        this.lcReceiptF7.setBoundLabelText(resHelper.getString("lcReceiptF7.boundLabelText"));		
        this.lcReceiptF7.setBoundLabelLength(100);		
        this.lcReceiptF7.setBoundLabelUnderline(true);		
        this.lcReceiptF7.setVisible(false);
        // prmtRoom		
        this.prmtRoom.setEnabled(false);
        // prmtCustomer		
        this.prmtCustomer.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7CustomerQuery");		
        this.prmtCustomer.setDisplayFormat("$name$");		
        this.prmtCustomer.setEditFormat("$number$");		
        this.prmtCustomer.setCommitFormat("$number$");		
        this.prmtCustomer.setEnabled(false);		
        this.prmtCustomer.setEnabledMultiSelection(true);
        // prmtReciving		
        this.prmtReciving.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCReceivingBillQuery");		
        this.prmtReciving.setCommitFormat("$number$");		
        this.prmtReciving.setDisplayFormat("$number$");		
        this.prmtReciving.setEditFormat("$number$");
        this.prmtReciving.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtReciving_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tntRecAmt		
        this.tntRecAmt.setEnabled(false);
        // txtReceipt		
        this.txtReceipt.setMaxLength(80);		
        this.txtReceipt.setRequired(true);
        // txtRcpStatus		
        this.txtRcpStatus.setEnabled(false);
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // prmtReceipt		
        this.prmtReceipt.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ChequeQuery");		
        this.prmtReceipt.setDisplayFormat("$number$");		
        this.prmtReceipt.setEditFormat("$number$");		
        this.prmtReceipt.setCommitFormat("$number$");
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
        this.setBounds(new Rectangle(10, 10, 670, 170));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 670, 170));
        kDLabelContainer1.setBounds(new Rectangle(30, 10, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(30, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer2.setBounds(new Rectangle(370, 10, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(370, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer3.setBounds(new Rectangle(30, 33, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(30, 33, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer4.setBounds(new Rectangle(370, 33, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(370, 33, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        lcReceiptTXT.setBounds(new Rectangle(30, 56, 270, 19));
        this.add(lcReceiptTXT, new KDLayout.Constraints(30, 56, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer6.setBounds(new Rectangle(370, 56, 270, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(370, 56, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer7.setBounds(new Rectangle(30, 79, 610, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(30, 79, 610, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        btnConfirm.setBounds(new Rectangle(400, 119, 80, 21));
        this.add(btnConfirm, new KDLayout.Constraints(400, 119, 80, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        btnCancel.setBounds(new Rectangle(500, 119, 80, 21));
        this.add(btnCancel, new KDLayout.Constraints(500, 119, 80, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        lcReceiptF7.setBounds(new Rectangle(30, 56, 270, 19));
        this.add(lcReceiptF7, new KDLayout.Constraints(30, 56, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(prmtRoom);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(prmtCustomer);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(prmtReciving);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(tntRecAmt);
        //lcReceiptTXT
        lcReceiptTXT.setBoundEditor(txtReceipt);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtRcpStatus);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(txtDescription);
        //lcReceiptF7
        lcReceiptF7.setBoundEditor(prmtReceipt);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.MakeOutReceiptUIHandler";
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
     * output prmtReciving_dataChanged method
     */
    protected void prmtReciving_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    	

    /**
     * output actionConfirm_actionPerformed method
     */
    public void actionConfirm_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCancel_actionPerformed method
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionConfirm(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionConfirm() {
    	return false;
    }
	public RequestContext prepareActionCancel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancel() {
    	return false;
    }

    /**
     * output ActionConfirm class
     */     
    protected class ActionConfirm extends ItemAction {     
    
        public ActionConfirm()
        {
            this(null);
        }

        public ActionConfirm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionConfirm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMakeOutReceiptUI.this, "ActionConfirm", "actionConfirm_actionPerformed", e);
        }
    }

    /**
     * output ActionCancel class
     */     
    protected class ActionCancel extends ItemAction {     
    
        public ActionCancel()
        {
            this(null);
        }

        public ActionCancel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCancel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMakeOutReceiptUI.this, "ActionCancel", "actionCancel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "MakeOutReceiptUI");
    }




}