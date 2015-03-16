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
public abstract class AbstractBatchsPurchaseUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBatchsPurchaseUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellOrder;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellOrder;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBookDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dptBookDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Currency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSalesman;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Salesman;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contValidDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dptValidDate;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tabCusPurchase;
    protected com.kingdee.bos.ctrl.swing.KDButton btnAddPurchase;
    protected com.kingdee.bos.ctrl.swing.KDButton btnDeletePurchase;
    protected com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo editData = null;

    /**
     * output class constructor
     */
    public AbstractBatchsPurchaseUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBatchsPurchaseUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contSellOrder = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7SellOrder = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contBookDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dptBookDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Currency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contSalesman = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Salesman = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contValidDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dptValidDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tabCusPurchase = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddPurchase = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnDeletePurchase = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contSellOrder.setName("contSellOrder");
        this.f7SellOrder.setName("f7SellOrder");
        this.contBookDate.setName("contBookDate");
        this.dptBookDate.setName("dptBookDate");
        this.contCurrency.setName("contCurrency");
        this.f7Currency.setName("f7Currency");
        this.contSalesman.setName("contSalesman");
        this.f7Salesman.setName("f7Salesman");
        this.contValidDate.setName("contValidDate");
        this.dptValidDate.setName("dptValidDate");
        this.kDPanel1.setName("kDPanel1");
        this.tabCusPurchase.setName("tabCusPurchase");
        this.btnAddPurchase.setName("btnAddPurchase");
        this.btnDeletePurchase.setName("btnDeletePurchase");
        // CoreUI
        // contSellOrder
        this.contSellOrder.setBoundLabelText(resHelper.getString("contSellOrder.boundLabelText"));
        this.contSellOrder.setBoundLabelLength(100);
        this.contSellOrder.setBoundLabelUnderline(true);
        // f7SellOrder
        this.f7SellOrder.setDisplayFormat("$name$");
        this.f7SellOrder.setEditFormat("$number$");
        this.f7SellOrder.setCommitFormat("$number$");
        this.f7SellOrder.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellOrderQuery");
        // contBookDate
        this.contBookDate.setBoundLabelText(resHelper.getString("contBookDate.boundLabelText"));
        this.contBookDate.setBoundLabelLength(100);
        this.contBookDate.setBoundLabelUnderline(true);
        // dptBookDate
        // contCurrency
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));
        this.contCurrency.setBoundLabelLength(100);
        this.contCurrency.setBoundLabelUnderline(true);
        // f7Currency
        this.f7Currency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");
        this.f7Currency.setDisplayFormat("$name$");
        this.f7Currency.setEditFormat("$number$");
        this.f7Currency.setCommitFormat("$number$");
        // contSalesman
        this.contSalesman.setBoundLabelText(resHelper.getString("contSalesman.boundLabelText"));
        this.contSalesman.setBoundLabelLength(100);
        this.contSalesman.setBoundLabelUnderline(true);
        // f7Salesman
        this.f7Salesman.setQueryInfo("com.kingdee.eas.base.forewarn.UserQuery");
        this.f7Salesman.setCommitFormat("$number$");
        this.f7Salesman.setEditFormat("$number$");
        this.f7Salesman.setDisplayFormat("$name$");
        // contValidDate
        this.contValidDate.setBoundLabelText(resHelper.getString("contValidDate.boundLabelText"));
        this.contValidDate.setBoundLabelLength(100);
        this.contValidDate.setBoundLabelUnderline(true);
        // dptValidDate
        // kDPanel1
        // tabCusPurchase
        this.tabCusPurchase.setFormatXml(resHelper.getString("tabCusPurchase.formatXml"));

        

        // btnAddPurchase
        this.btnAddPurchase.setText(resHelper.getString("btnAddPurchase.text"));
        this.btnAddPurchase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddPurchase_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDeletePurchase
        this.btnDeletePurchase.setText(resHelper.getString("btnDeletePurchase.text"));
        this.btnDeletePurchase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDeletePurchase_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
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
        this.setLayout(null);
        contSellOrder.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contSellOrder, null);
        contBookDate.setBounds(new Rectangle(9, 40, 270, 19));
        this.add(contBookDate, null);
        contCurrency.setBounds(new Rectangle(369, 71, 270, 19));
        this.add(contCurrency, null);
        contSalesman.setBounds(new Rectangle(9, 74, 270, 19));
        this.add(contSalesman, null);
        contValidDate.setBounds(new Rectangle(367, 38, 270, 19));
        this.add(contValidDate, null);
        kDPanel1.setBounds(new Rectangle(9, 99, 639, 232));
        this.add(kDPanel1, null);
        //contSellOrder
        contSellOrder.setBoundEditor(f7SellOrder);
        //contBookDate
        contBookDate.setBoundEditor(dptBookDate);
        //contCurrency
        contCurrency.setBoundEditor(f7Currency);
        //contSalesman
        contSalesman.setBoundEditor(f7Salesman);
        //contValidDate
        contValidDate.setBoundEditor(dptValidDate);
        //kDPanel1
        kDPanel1.setLayout(null);        tabCusPurchase.setBounds(new Rectangle(8, 33, 626, 186));
        kDPanel1.add(tabCusPurchase, null);
        btnAddPurchase.setBounds(new Rectangle(425, 8, 102, 21));
        kDPanel1.add(btnAddPurchase, null);
        btnDeletePurchase.setBounds(new Rectangle(533, 7, 97, 21));
        kDPanel1.add(btnDeletePurchase, null);

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
		dataBinder.registerBinding("sellOrder", com.kingdee.eas.fdc.sellhouse.SellOrderInfo.class, this.f7SellOrder, "data");
		dataBinder.registerBinding("bookDate", java.util.Date.class, this.dptBookDate, "value");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.f7Currency, "data");
		dataBinder.registerBinding("salesman", com.kingdee.eas.base.permission.UserInfo.class, this.f7Salesman, "data");
		dataBinder.registerBinding("validDate", java.util.Date.class, this.dptValidDate, "value");		
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo)ov;
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
		getValidateHelper().registerBindProperty("sellOrder", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("salesman", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("validDate", ValidateHelper.ON_SAVE);    		
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
     * output btnAddPurchase_actionPerformed method
     */
    protected void btnAddPurchase_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnDeletePurchase_actionPerformed method
     */
    protected void btnDeletePurchase_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("sellOrder.*"));
        sic.add(new SelectorItemInfo("bookDate"));
        sic.add(new SelectorItemInfo("currency.*"));
        sic.add(new SelectorItemInfo("salesman.*"));
        sic.add(new SelectorItemInfo("validDate"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "BatchsPurchaseUI");
    }




}