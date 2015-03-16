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
public abstract class AbstractBatchPurchaseUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBatchPurchaseUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlCusPurchase;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contValidDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSalesman;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBookDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellOrder;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDButton btnAscertain;
    protected com.kingdee.bos.ctrl.swing.KDButton btnExit;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tabCusPurchase;
    protected com.kingdee.bos.ctrl.swing.KDButton btnAddPurchase;
    protected com.kingdee.bos.ctrl.swing.KDButton btnDeletePurchase;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Currency;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dptValidDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Salesman;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dptBookDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellOrder;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellProject;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractBatchPurchaseUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBatchPurchaseUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.pnlCusPurchase = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contValidDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSalesman = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBookDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellOrder = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnAscertain = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnExit = new com.kingdee.bos.ctrl.swing.KDButton();
        this.tabCusPurchase = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddPurchase = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnDeletePurchase = new com.kingdee.bos.ctrl.swing.KDButton();
        this.f7Currency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dptValidDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Salesman = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dptBookDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7SellOrder = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7SellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pnlCusPurchase.setName("pnlCusPurchase");
        this.contCurrency.setName("contCurrency");
        this.contValidDate.setName("contValidDate");
        this.contSalesman.setName("contSalesman");
        this.contBookDate.setName("contBookDate");
        this.contSellOrder.setName("contSellOrder");
        this.contProject.setName("contProject");
        this.btnAscertain.setName("btnAscertain");
        this.btnExit.setName("btnExit");
        this.tabCusPurchase.setName("tabCusPurchase");
        this.btnAddPurchase.setName("btnAddPurchase");
        this.btnDeletePurchase.setName("btnDeletePurchase");
        this.f7Currency.setName("f7Currency");
        this.dptValidDate.setName("dptValidDate");
        this.f7Salesman.setName("f7Salesman");
        this.dptBookDate.setName("dptBookDate");
        this.f7SellOrder.setName("f7SellOrder");
        this.f7SellProject.setName("f7SellProject");
        // CoreUI		
        this.setPreferredSize(new Dimension(700,420));
        // pnlCusPurchase		
        this.pnlCusPurchase.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlCusPurchase.border.title")));
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelUnderline(true);		
        this.contCurrency.setBoundLabelLength(100);
        // contValidDate		
        this.contValidDate.setBoundLabelText(resHelper.getString("contValidDate.boundLabelText"));		
        this.contValidDate.setBoundLabelUnderline(true);		
        this.contValidDate.setBoundLabelLength(100);
        // contSalesman		
        this.contSalesman.setBoundLabelText(resHelper.getString("contSalesman.boundLabelText"));		
        this.contSalesman.setBoundLabelUnderline(true);		
        this.contSalesman.setBoundLabelLength(100);
        // contBookDate		
        this.contBookDate.setBoundLabelText(resHelper.getString("contBookDate.boundLabelText"));		
        this.contBookDate.setBoundLabelLength(100);		
        this.contBookDate.setBoundLabelUnderline(true);
        // contSellOrder		
        this.contSellOrder.setBoundLabelText(resHelper.getString("contSellOrder.boundLabelText"));		
        this.contSellOrder.setBoundLabelUnderline(true);		
        this.contSellOrder.setBoundLabelLength(100);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // btnAscertain
        this.btnAscertain.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAscertain.setText(resHelper.getString("btnAscertain.text"));
        // btnExit
        this.btnExit.setAction((IItemAction)ActionProxyFactory.getProxy(actionExitCurrent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExit.setText(resHelper.getString("btnExit.text"));
        // tabCusPurchase
		String tabCusPurchaseStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"contNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contCustomer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sellorderNum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"contSincerityAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contRoommodelType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contDescription\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{contNumber}</t:Cell><t:Cell>$Resource{contCustomer}</t:Cell><t:Cell>$Resource{sellorderNum}</t:Cell><t:Cell>$Resource{contSincerityAmount}</t:Cell><t:Cell>$Resource{contRoommodelType}</t:Cell><t:Cell>$Resource{contDescription}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tabCusPurchase.setFormatXml(resHelper.translateString("tabCusPurchase",tabCusPurchaseStrXML));

        

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
        // f7Currency		
        this.f7Currency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.f7Currency.setDisplayFormat("$name$");		
        this.f7Currency.setEditFormat("$number$");		
        this.f7Currency.setCommitFormat("$number$");
        // dptValidDate
        // f7Salesman		
        this.f7Salesman.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7Salesman.setCommitFormat("$number$");		
        this.f7Salesman.setEditFormat("$number$");		
        this.f7Salesman.setDisplayFormat("$name$");
        this.f7Salesman.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Salesman_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // dptBookDate
        // f7SellOrder		
        this.f7SellOrder.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellOrderQuery");		
        this.f7SellOrder.setDisplayFormat("$name$");		
        this.f7SellOrder.setEditFormat("$number$");		
        this.f7SellOrder.setCommitFormat("$number$");
        this.f7SellOrder.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7SellOrder_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7SellProject		
        this.f7SellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");
        this.f7SellProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7SellProject_dataChanged(e);
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
        this.setBounds(new Rectangle(10, 10, 700, 420));
        this.setLayout(null);
        pnlCusPurchase.setBounds(new Rectangle(0, 154, 673, 238));
        this.add(pnlCusPurchase, null);
        contCurrency.setBounds(new Rectangle(354, 115, 270, 19));
        this.add(contCurrency, null);
        contValidDate.setBounds(new Rectangle(353, 63, 270, 19));
        this.add(contValidDate, null);
        contSalesman.setBounds(new Rectangle(9, 116, 270, 19));
        this.add(contSalesman, null);
        contBookDate.setBounds(new Rectangle(10, 65, 270, 19));
        this.add(contBookDate, null);
        contSellOrder.setBounds(new Rectangle(355, 16, 270, 19));
        this.add(contSellOrder, null);
        contProject.setBounds(new Rectangle(10, 20, 270, 19));
        this.add(contProject, null);
        btnAscertain.setBounds(new Rectangle(462, 398, 100, 21));
        this.add(btnAscertain, null);
        btnExit.setBounds(new Rectangle(574, 398, 100, 21));
        this.add(btnExit, null);
        //pnlCusPurchase
        pnlCusPurchase.setLayout(null);        tabCusPurchase.setBounds(new Rectangle(9, 38, 654, 183));
        pnlCusPurchase.add(tabCusPurchase, null);
        btnAddPurchase.setBounds(new Rectangle(458, 13, 100, 21));
        pnlCusPurchase.add(btnAddPurchase, null);
        btnDeletePurchase.setBounds(new Rectangle(559, 13, 104, 21));
        pnlCusPurchase.add(btnDeletePurchase, null);
        //contCurrency
        contCurrency.setBoundEditor(f7Currency);
        //contValidDate
        contValidDate.setBoundEditor(dptValidDate);
        //contSalesman
        contSalesman.setBoundEditor(f7Salesman);
        //contBookDate
        contBookDate.setBoundEditor(dptBookDate);
        //contSellOrder
        contSellOrder.setBoundEditor(f7SellOrder);
        //contProject
        contProject.setBoundEditor(f7SellProject);

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


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.BatchPurchaseUIHandler";
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
     * output f7Salesman_dataChanged method
     */
    protected void f7Salesman_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7SellOrder_dataChanged method
     */
    protected void f7SellOrder_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7SellProject_dataChanged method
     */
    protected void f7SellProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
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
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "BatchPurchaseUI");
    }




}