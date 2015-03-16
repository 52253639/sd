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
public abstract class AbstractBuyingRoomPlanUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBuyingRoomPlanUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAgios;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conFinalAgio;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conLoanAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAFundAmount;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCalc;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChooseAgio;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPrintPlan;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PayType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAgioDes;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAgio;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLoanAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAFundAmount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable payListTable;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    protected ActionCalc actionCalc = null;
    /**
     * output class constructor
     */
    public AbstractBuyingRoomPlanUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBuyingRoomPlanUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionCalc
        this.actionCalc = new ActionCalc(this);
        getActionManager().registerAction("actionCalc", actionCalc);
         this.actionCalc.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contPayType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAgios = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conFinalAgio = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conLoanAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAFundAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.btnCalc = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnChooseAgio = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPrintPlan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.f7PayType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAgioDes = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAgio = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLoanAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAFundAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.payListTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contPayType.setName("contPayType");
        this.contAgios.setName("contAgios");
        this.contTotalAmount.setName("contTotalAmount");
        this.conFinalAgio.setName("conFinalAgio");
        this.conLoanAmount.setName("conLoanAmount");
        this.contAFundAmount.setName("contAFundAmount");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.btnCalc.setName("btnCalc");
        this.btnChooseAgio.setName("btnChooseAgio");
        this.btnPrintPlan.setName("btnPrintPlan");
        this.f7PayType.setName("f7PayType");
        this.txtAgioDes.setName("txtAgioDes");
        this.txtTotalAmount.setName("txtTotalAmount");
        this.txtAgio.setName("txtAgio");
        this.txtLoanAmount.setName("txtLoanAmount");
        this.txtAFundAmount.setName("txtAFundAmount");
        this.payListTable.setName("payListTable");
        // CoreUI
        // contPayType		
        this.contPayType.setBoundLabelText(resHelper.getString("contPayType.boundLabelText"));		
        this.contPayType.setBoundLabelUnderline(true);		
        this.contPayType.setBoundLabelLength(85);
        // contAgios		
        this.contAgios.setBoundLabelText(resHelper.getString("contAgios.boundLabelText"));		
        this.contAgios.setBoundLabelUnderline(true);		
        this.contAgios.setBoundLabelLength(85);
        // contTotalAmount		
        this.contTotalAmount.setBoundLabelText(resHelper.getString("contTotalAmount.boundLabelText"));		
        this.contTotalAmount.setBoundLabelUnderline(true);		
        this.contTotalAmount.setBoundLabelLength(85);
        // conFinalAgio		
        this.conFinalAgio.setBoundLabelText(resHelper.getString("conFinalAgio.boundLabelText"));		
        this.conFinalAgio.setBoundLabelUnderline(true);		
        this.conFinalAgio.setBoundLabelLength(85);
        // conLoanAmount		
        this.conLoanAmount.setBoundLabelText(resHelper.getString("conLoanAmount.boundLabelText"));		
        this.conLoanAmount.setBoundLabelUnderline(true);		
        this.conLoanAmount.setBoundLabelLength(85);
        // contAFundAmount		
        this.contAFundAmount.setBoundLabelText(resHelper.getString("contAFundAmount.boundLabelText"));		
        this.contAFundAmount.setBoundLabelUnderline(true);		
        this.contAFundAmount.setBoundLabelLength(85);
        // kDTabbedPane1
        // btnCalc
        this.btnCalc.setAction((IItemAction)ActionProxyFactory.getProxy(actionCalc, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCalc.setText(resHelper.getString("btnCalc.text"));
        // btnChooseAgio		
        this.btnChooseAgio.setText(resHelper.getString("btnChooseAgio.text"));
        this.btnChooseAgio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnChooseAgio_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnPrintPlan		
        this.btnPrintPlan.setText(resHelper.getString("btnPrintPlan.text"));
        this.btnPrintPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnPrintPlan_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // f7PayType		
        this.f7PayType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHEPayTypeQuery");		
        this.f7PayType.setCommitFormat("$number$");		
        this.f7PayType.setDisplayFormat("$name$");		
        this.f7PayType.setEditFormat("$number$");
        this.f7PayType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7PayType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtAgioDes		
        this.txtAgioDes.setEnabled(false);
        // txtTotalAmount
        this.txtTotalAmount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtTotalAmount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtAgio		
        this.txtAgio.setEnabled(false);
        // txtLoanAmount		
        this.txtLoanAmount.setEnabled(false);
        // txtAFundAmount		
        this.txtAFundAmount.setEnabled(false);
        // payListTable
		String payListTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"recDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyName}</t:Cell><t:Cell>$Resource{recDate}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{amount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.payListTable.setFormatXml(resHelper.translateString("payListTable",payListTableStrXML));		
        this.payListTable.setMinimumSize(new Dimension(10,10));

        

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
        this.setBounds(new Rectangle(10, 10, 500, 500));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 500, 500));
        contPayType.setBounds(new Rectangle(6, 10, 226, 19));
        this.add(contPayType, new KDLayout.Constraints(6, 10, 226, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAgios.setBounds(new Rectangle(6, 35, 226, 19));
        this.add(contAgios, new KDLayout.Constraints(6, 35, 226, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTotalAmount.setBounds(new Rectangle(6, 60, 226, 19));
        this.add(contTotalAmount, new KDLayout.Constraints(6, 60, 226, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conFinalAgio.setBounds(new Rectangle(262, 60, 226, 19));
        this.add(conFinalAgio, new KDLayout.Constraints(262, 60, 226, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conLoanAmount.setBounds(new Rectangle(6, 85, 226, 19));
        this.add(conLoanAmount, new KDLayout.Constraints(6, 85, 226, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAFundAmount.setBounds(new Rectangle(262, 85, 226, 19));
        this.add(contAFundAmount, new KDLayout.Constraints(262, 85, 226, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTabbedPane1.setBounds(new Rectangle(6, 121, 482, 368));
        this.add(kDTabbedPane1, new KDLayout.Constraints(6, 121, 482, 368, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnCalc.setBounds(new Rectangle(262, 10, 110, 19));
        this.add(btnCalc, new KDLayout.Constraints(262, 10, 110, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnChooseAgio.setBounds(new Rectangle(262, 35, 110, 19));
        this.add(btnChooseAgio, new KDLayout.Constraints(262, 35, 110, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnPrintPlan.setBounds(new Rectangle(378, 10, 110, 19));
        this.add(btnPrintPlan, new KDLayout.Constraints(378, 10, 110, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contPayType
        contPayType.setBoundEditor(f7PayType);
        //contAgios
        contAgios.setBoundEditor(txtAgioDes);
        //contTotalAmount
        contTotalAmount.setBoundEditor(txtTotalAmount);
        //conFinalAgio
        conFinalAgio.setBoundEditor(txtAgio);
        //conLoanAmount
        conLoanAmount.setBoundEditor(txtLoanAmount);
        //contAFundAmount
        contAFundAmount.setBoundEditor(txtAFundAmount);
        //kDTabbedPane1
        kDTabbedPane1.add(payListTable, resHelper.getString("payListTable.constraints"));

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
	    return "com.kingdee.eas.fdc.sellhouse.app.BuyingRoomPlanUIHandler";
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
     * output btnChooseAgio_actionPerformed method
     */
    protected void btnChooseAgio_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnPrintPlan_actionPerformed method
     */
    protected void btnPrintPlan_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output f7PayType_dataChanged method
     */
    protected void f7PayType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtTotalAmount_dataChanged method
     */
    protected void txtTotalAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
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
     * output actionCalc_actionPerformed method
     */
    public void actionCalc_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionCalc(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCalc() {
    	return false;
    }

    /**
     * output ActionCalc class
     */     
    protected class ActionCalc extends ItemAction {     
    
        public ActionCalc()
        {
            this(null);
        }

        public ActionCalc(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCalc.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalc.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalc.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBuyingRoomPlanUI.this, "ActionCalc", "actionCalc_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "BuyingRoomPlanUI");
    }




}