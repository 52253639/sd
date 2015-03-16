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
public abstract class AbstractProofOfPaymentEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProofOfPaymentEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurchaseBill;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPurchaseBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCustomerName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractTotalPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractTotalPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomDesc;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomDesc;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPaymentPrintPreview;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPaymentPrint;
    protected com.kingdee.eas.fdc.sellhouse.ProofOfPaymentInfo editData = null;
    protected ActionPaymentPrintPreview actionPaymentPrintPreview = null;
    protected ActionPaymentPrint actionPaymentPrint = null;
    /**
     * output class constructor
     */
    public AbstractProofOfPaymentEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProofOfPaymentEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionPaymentPrintPreview
        this.actionPaymentPrintPreview = new ActionPaymentPrintPreview(this);
        getActionManager().registerAction("actionPaymentPrintPreview", actionPaymentPrintPreview);
         this.actionPaymentPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPaymentPrint
        this.actionPaymentPrint = new ActionPaymentPrint(this);
        getActionManager().registerAction("actionPaymentPrint", actionPaymentPrint);
         this.actionPaymentPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contPurchaseBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtPurchaseBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCustomerName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCustomerName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contContractNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtContractNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contContractTotalPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtContractTotalPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contRoomDesc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRoomDesc = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnPaymentPrintPreview = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPaymentPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contPurchaseBill.setName("contPurchaseBill");
        this.prmtPurchaseBill.setName("prmtPurchaseBill");
        this.contCustomerName.setName("contCustomerName");
        this.txtCustomerName.setName("txtCustomerName");
        this.contContractNumber.setName("contContractNumber");
        this.txtContractNumber.setName("txtContractNumber");
        this.contContractTotalPrice.setName("contContractTotalPrice");
        this.txtContractTotalPrice.setName("txtContractTotalPrice");
        this.contRoomDesc.setName("contRoomDesc");
        this.txtRoomDesc.setName("txtRoomDesc");
        this.btnPaymentPrintPreview.setName("btnPaymentPrintPreview");
        this.btnPaymentPrint.setName("btnPaymentPrint");
        // CoreUI
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(44);		
        this.txtNumber.setRequired(true);
        // contPurchaseBill		
        this.contPurchaseBill.setBoundLabelText(resHelper.getString("contPurchaseBill.boundLabelText"));		
        this.contPurchaseBill.setBoundLabelLength(100);		
        this.contPurchaseBill.setBoundLabelUnderline(true);
        // prmtPurchaseBill		
        this.prmtPurchaseBill.setEditFormat("$number$");		
        this.prmtPurchaseBill.setDisplayFormat("$number$");		
        this.prmtPurchaseBill.setCommitFormat("$number$");		
        this.prmtPurchaseBill.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.PurchaseQuery");
        this.prmtPurchaseBill.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtPurchaseBill_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contCustomerName		
        this.contCustomerName.setBoundLabelText(resHelper.getString("contCustomerName.boundLabelText"));		
        this.contCustomerName.setBoundLabelLength(100);		
        this.contCustomerName.setBoundLabelUnderline(true);
        // txtCustomerName		
        this.txtCustomerName.setMaxLength(80);		
        this.txtCustomerName.setEnabled(false);
        // contContractNumber		
        this.contContractNumber.setBoundLabelText(resHelper.getString("contContractNumber.boundLabelText"));		
        this.contContractNumber.setBoundLabelLength(100);		
        this.contContractNumber.setBoundLabelUnderline(true);
        // txtContractNumber		
        this.txtContractNumber.setMaxLength(255);		
        this.txtContractNumber.setEnabled(false);
        // contContractTotalPrice		
        this.contContractTotalPrice.setBoundLabelText(resHelper.getString("contContractTotalPrice.boundLabelText"));		
        this.contContractTotalPrice.setBoundLabelLength(100);		
        this.contContractTotalPrice.setBoundLabelUnderline(true);
        // txtContractTotalPrice		
        this.txtContractTotalPrice.setEnabled(false);
        // contRoomDesc		
        this.contRoomDesc.setBoundLabelText(resHelper.getString("contRoomDesc.boundLabelText"));		
        this.contRoomDesc.setBoundLabelLength(100);		
        this.contRoomDesc.setBoundLabelUnderline(true);
        // txtRoomDesc		
        this.txtRoomDesc.setMaxLength(255);		
        this.txtRoomDesc.setEnabled(false);
        // btnPaymentPrintPreview
        this.btnPaymentPrintPreview.setAction((IItemAction)ActionProxyFactory.getProxy(actionPaymentPrintPreview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPaymentPrintPreview.setText(resHelper.getString("btnPaymentPrintPreview.text"));		
        this.btnPaymentPrintPreview.setToolTipText(resHelper.getString("btnPaymentPrintPreview.toolTipText"));		
        this.btnPaymentPrintPreview.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));
        // btnPaymentPrint
        this.btnPaymentPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPaymentPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPaymentPrint.setText(resHelper.getString("btnPaymentPrint.text"));		
        this.btnPaymentPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));		
        this.btnPaymentPrint.setToolTipText(resHelper.getString("btnPaymentPrint.toolTipText"));
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
        this.setBounds(new Rectangle(10, 10, 580, 130));
        this.setLayout(null);
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, null);
        contPurchaseBill.setBounds(new Rectangle(300, 10, 270, 19));
        this.add(contPurchaseBill, null);
        contCustomerName.setBounds(new Rectangle(10, 68, 560, 19));
        this.add(contCustomerName, null);
        contContractNumber.setBounds(new Rectangle(10, 39, 270, 19));
        this.add(contContractNumber, null);
        contContractTotalPrice.setBounds(new Rectangle(300, 39, 270, 19));
        this.add(contContractTotalPrice, null);
        contRoomDesc.setBounds(new Rectangle(10, 97, 560, 19));
        this.add(contRoomDesc, null);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contPurchaseBill
        contPurchaseBill.setBoundEditor(prmtPurchaseBill);
        //contCustomerName
        contCustomerName.setBoundEditor(txtCustomerName);
        //contContractNumber
        contContractNumber.setBoundEditor(txtContractNumber);
        //contContractTotalPrice
        contContractTotalPrice.setBoundEditor(txtContractTotalPrice);
        //contRoomDesc
        contRoomDesc.setBoundEditor(txtRoomDesc);

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
        this.toolBar.add(btnReset);
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
        this.toolBar.add(btnPaymentPrintPreview);
        this.toolBar.add(btnPaymentPrint);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("purchaseBill", com.kingdee.eas.fdc.sellhouse.PurchaseInfo.class, this.prmtPurchaseBill, "data");
		dataBinder.registerBinding("customerName", String.class, this.txtCustomerName, "text");
		dataBinder.registerBinding("contractNumber", String.class, this.txtContractNumber, "text");
		dataBinder.registerBinding("contractTotalPrice", java.math.BigDecimal.class, this.txtContractTotalPrice, "value");
		dataBinder.registerBinding("roomDesc", String.class, this.txtRoomDesc, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.ProofOfPaymentEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.ProofOfPaymentInfo)ov;
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
		getValidateHelper().registerBindProperty("purchaseBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("customerName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractTotalPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomDesc", ValidateHelper.ON_SAVE);    		
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
     * output prmtPurchaseBill_dataChanged method
     */
    protected void prmtPurchaseBill_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("purchaseBill.*"));
        sic.add(new SelectorItemInfo("customerName"));
        sic.add(new SelectorItemInfo("contractNumber"));
        sic.add(new SelectorItemInfo("contractTotalPrice"));
        sic.add(new SelectorItemInfo("roomDesc"));
        return sic;
    }        
    	

    /**
     * output actionPaymentPrintPreview_actionPerformed method
     */
    public void actionPaymentPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPaymentPrint_actionPerformed method
     */
    public void actionPaymentPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionPaymentPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPaymentPrintPreview() {
    	return false;
    }
	public RequestContext prepareActionPaymentPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPaymentPrint() {
    	return false;
    }

    /**
     * output ActionPaymentPrintPreview class
     */     
    protected class ActionPaymentPrintPreview extends ItemAction {     
    
        public ActionPaymentPrintPreview()
        {
            this(null);
        }

        public ActionPaymentPrintPreview(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPaymentPrintPreview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPaymentPrintPreview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPaymentPrintPreview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProofOfPaymentEditUI.this, "ActionPaymentPrintPreview", "actionPaymentPrintPreview_actionPerformed", e);
        }
    }

    /**
     * output ActionPaymentPrint class
     */     
    protected class ActionPaymentPrint extends ItemAction {     
    
        public ActionPaymentPrint()
        {
            this(null);
        }

        public ActionPaymentPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPaymentPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPaymentPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPaymentPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProofOfPaymentEditUI.this, "ActionPaymentPrint", "actionPaymentPrint_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ProofOfPaymentEditUI");
    }




}