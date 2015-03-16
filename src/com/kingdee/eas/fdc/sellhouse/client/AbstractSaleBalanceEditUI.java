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
public abstract class AbstractSaleBalanceEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSaleBalanceEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastBalancDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEndDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemark;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRemark;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBalanceDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBalanceDate;
    protected com.kingdee.bos.ctrl.swing.KDButton btnBalanceCheck;
    protected com.kingdee.bos.ctrl.swing.KDButton btnBalanceConfirm;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBalanceCheck;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBalanceConfirm;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblReport;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPeriod;
    protected com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo editData = null;
    protected ActionBalanceCheck actionBalanceCheck = null;
    protected ActionBalanceConfirm actionBalanceConfirm = null;
    /**
     * output class constructor
     */
    public AbstractSaleBalanceEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSaleBalanceEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionBalanceCheck
        this.actionBalanceCheck = new ActionBalanceCheck(this);
        getActionManager().registerAction("actionBalanceCheck", actionBalanceCheck);
         this.actionBalanceCheck.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBalanceConfirm
        this.actionBalanceConfirm = new ActionBalanceConfirm(this);
        getActionManager().registerAction("actionBalanceConfirm", actionBalanceConfirm);
         this.actionBalanceConfirm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contLastBalancDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contRemark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBalanceDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkBalanceDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.btnBalanceCheck = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnBalanceConfirm = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.menuItemBalanceCheck = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemBalanceConfirm = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.tblReport = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreator.setName("contCreator");
        this.prmtCreator.setName("prmtCreator");
        this.contSellProject.setName("contSellProject");
        this.prmtSellProject.setName("prmtSellProject");
        this.contLastBalancDate.setName("contLastBalancDate");
        this.contStartDate.setName("contStartDate");
        this.pkStartDate.setName("pkStartDate");
        this.contEndDate.setName("contEndDate");
        this.pkEndDate.setName("pkEndDate");
        this.contRemark.setName("contRemark");
        this.txtRemark.setName("txtRemark");
        this.contBalanceDate.setName("contBalanceDate");
        this.pkBalanceDate.setName("pkBalanceDate");
        this.btnBalanceCheck.setName("btnBalanceCheck");
        this.btnBalanceConfirm.setName("btnBalanceConfirm");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.menuItemBalanceCheck.setName("menuItemBalanceCheck");
        this.menuItemBalanceConfirm.setName("menuItemBalanceConfirm");
        this.tblReport.setName("tblReport");
        this.prmtPeriod.setName("prmtPeriod");
        // CoreUI		
        this.btnAddNew.setVisible(false);		
        this.btnAddNew.setEnabled(false);		
        this.btnEdit.setEnabled(false);		
        this.btnEdit.setVisible(false);		
        this.btnSave.setEnabled(false);		
        this.btnSave.setVisible(false);		
        this.btnCopy.setEnabled(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setEnabled(false);		
        this.btnRemove.setVisible(false);		
        this.btnCancelCancel.setEnabled(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setEnabled(false);		
        this.btnCancel.setVisible(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemAddNew.setEnabled(false);		
        this.menuItemSave.setEnabled(false);		
        this.menuItemSave.setVisible(false);		
        this.btnAttachment.setEnabled(false);		
        this.btnAttachment.setVisible(false);		
        this.menuItemEdit.setEnabled(false);		
        this.menuItemEdit.setVisible(false);		
        this.menuItemRemove.setEnabled(false);		
        this.menuItemRemove.setVisible(false);		
        this.MenuItemAttachment.setEnabled(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancelCancel.setEnabled(false);		
        this.menuItemCancel.setVisible(false);		
        this.menuItemCancel.setEnabled(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$number$");		
        this.prmtCreator.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7UserQuery");
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // prmtSellProject		
        this.prmtSellProject.setCommitFormat("$number$");		
        this.prmtSellProject.setDisplayFormat("$name$");		
        this.prmtSellProject.setEditFormat("$number$");		
        this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtSellProject.setRequired(true);
        this.prmtSellProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSellProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contLastBalancDate		
        this.contLastBalancDate.setBoundLabelText(resHelper.getString("contLastBalancDate.boundLabelText"));		
        this.contLastBalancDate.setBoundLabelLength(100);		
        this.contLastBalancDate.setBoundLabelUnderline(true);
        // contStartDate		
        this.contStartDate.setBoundLabelText(resHelper.getString("contStartDate.boundLabelText"));		
        this.contStartDate.setBoundLabelLength(100);		
        this.contStartDate.setBoundLabelUnderline(true);
        // pkStartDate		
        this.pkStartDate.setRequired(true);		
        this.pkStartDate.setEnabled(false);
        // contEndDate		
        this.contEndDate.setBoundLabelText(resHelper.getString("contEndDate.boundLabelText"));		
        this.contEndDate.setBoundLabelLength(100);		
        this.contEndDate.setBoundLabelUnderline(true);
        // pkEndDate		
        this.pkEndDate.setRequired(true);		
        this.pkEndDate.setEnabled(false);
        // contRemark		
        this.contRemark.setBoundLabelText(resHelper.getString("contRemark.boundLabelText"));		
        this.contRemark.setBoundLabelLength(100);		
        this.contRemark.setBoundLabelUnderline(true);
        // txtRemark		
        this.txtRemark.setMaxLength(255);
        // contBalanceDate		
        this.contBalanceDate.setBoundLabelText(resHelper.getString("contBalanceDate.boundLabelText"));		
        this.contBalanceDate.setBoundLabelLength(100);		
        this.contBalanceDate.setBoundLabelUnderline(true);		
        this.contBalanceDate.setEnabled(false);
        // pkBalanceDate		
        this.pkBalanceDate.setEnabled(false);
        // btnBalanceCheck
        this.btnBalanceCheck.setAction((IItemAction)ActionProxyFactory.getProxy(actionBalanceCheck, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBalanceCheck.setText(resHelper.getString("btnBalanceCheck.text"));		
        this.btnBalanceCheck.setToolTipText(resHelper.getString("btnBalanceCheck.toolTipText"));
        this.btnBalanceCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnBalanceCheck_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnBalanceConfirm
        this.btnBalanceConfirm.setAction((IItemAction)ActionProxyFactory.getProxy(actionBalanceConfirm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBalanceConfirm.setText(resHelper.getString("btnBalanceConfirm.text"));		
        this.btnBalanceConfirm.setToolTipText(resHelper.getString("btnBalanceConfirm.toolTipText"));
        // kDTabbedPane1
        // menuItemBalanceCheck
        this.menuItemBalanceCheck.setAction((IItemAction)ActionProxyFactory.getProxy(actionBalanceCheck, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBalanceCheck.setText(resHelper.getString("menuItemBalanceCheck.text"));		
        this.menuItemBalanceCheck.setToolTipText(resHelper.getString("menuItemBalanceCheck.toolTipText"));		
        this.menuItemBalanceCheck.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_check"));
        // menuItemBalanceConfirm
        this.menuItemBalanceConfirm.setAction((IItemAction)ActionProxyFactory.getProxy(actionBalanceConfirm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBalanceConfirm.setText(resHelper.getString("menuItemBalanceConfirm.text"));		
        this.menuItemBalanceConfirm.setToolTipText(resHelper.getString("menuItemBalanceConfirm.toolTipText"));		
        this.menuItemBalanceConfirm.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_compute"));
        // tblReport
		String tblReportStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"problemType\" t:width=\"130\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"problemDesc\" t:width=\"450\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{problemType}</t:Cell><t:Cell>$Resource{problemDesc}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblReport.setFormatXml(resHelper.translateString("tblReport",tblReportStrXML));

        

        // prmtPeriod		
        this.prmtPeriod.setDisplayFormat("$number$");		
        this.prmtPeriod.setEditFormat("$number$");		
        this.prmtPeriod.setCommitFormat("$number$");		
        this.prmtPeriod.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7BalancePeriodQuery");		
        this.prmtPeriod.setRequired(true);
        this.prmtPeriod.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtPeriod_dataChanged(e);
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
        this.setBounds(new Rectangle(10, 10, 650, 600));
        this.setLayout(null);
        contCreator.setBounds(new Rectangle(10, 100, 270, 19));
        this.add(contCreator, null);
        contSellProject.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contSellProject, null);
        contLastBalancDate.setBounds(new Rectangle(357, 10, 270, 19));
        this.add(contLastBalancDate, null);
        contStartDate.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contStartDate, null);
        contEndDate.setBounds(new Rectangle(357, 40, 270, 19));
        this.add(contEndDate, null);
        contRemark.setBounds(new Rectangle(10, 70, 617, 19));
        this.add(contRemark, null);
        contBalanceDate.setBounds(new Rectangle(357, 100, 270, 19));
        this.add(contBalanceDate, null);
        btnBalanceCheck.setBounds(new Rectangle(357, 130, 100, 21));
        this.add(btnBalanceCheck, null);
        btnBalanceConfirm.setBounds(new Rectangle(526, 130, 100, 21));
        this.add(btnBalanceConfirm, null);
        kDTabbedPane1.setBounds(new Rectangle(10, 160, 617, 420));
        this.add(kDTabbedPane1, null);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contLastBalancDate
        contLastBalancDate.setBoundEditor(prmtPeriod);
        //contStartDate
        contStartDate.setBoundEditor(pkStartDate);
        //contEndDate
        contEndDate.setBoundEditor(pkEndDate);
        //contRemark
        contRemark.setBoundEditor(txtRemark);
        //contBalanceDate
        contBalanceDate.setBoundEditor(pkBalanceDate);
        //kDTabbedPane1
        kDTabbedPane1.add(tblReport, resHelper.getString("tblReport.constraints"));

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
        menuBiz.add(menuItemBalanceCheck);
        menuBiz.add(menuItemBalanceConfirm);
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
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("startDate", java.util.Date.class, this.pkStartDate, "value");
		dataBinder.registerBinding("endDate", java.util.Date.class, this.pkEndDate, "value");
		dataBinder.registerBinding("remark", String.class, this.txtRemark, "text");
		dataBinder.registerBinding("balanceDate", java.util.Date.class, this.pkBalanceDate, "value");
		dataBinder.registerBinding("period", com.kingdee.eas.basedata.assistant.PeriodCollection.class, this.prmtPeriod, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SaleBalanceEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("balanceDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("period", ValidateHelper.ON_SAVE);    		
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
     * output prmtSellProject_dataChanged method
     */
    protected void prmtSellProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output btnBalanceCheck_actionPerformed method
     */
    protected void btnBalanceCheck_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output prmtPeriod_dataChanged method
     */
    protected void prmtPeriod_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("startDate"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("remark"));
        sic.add(new SelectorItemInfo("balanceDate"));
        sic.add(new SelectorItemInfo("period.*"));
        return sic;
    }        
    	

    /**
     * output actionBalanceCheck_actionPerformed method
     */
    public void actionBalanceCheck_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBalanceConfirm_actionPerformed method
     */
    public void actionBalanceConfirm_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionBalanceCheck(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBalanceCheck() {
    	return false;
    }
	public RequestContext prepareActionBalanceConfirm(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBalanceConfirm() {
    	return false;
    }

    /**
     * output ActionBalanceCheck class
     */     
    protected class ActionBalanceCheck extends ItemAction {     
    
        public ActionBalanceCheck()
        {
            this(null);
        }

        public ActionBalanceCheck(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBalanceCheck.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBalanceCheck.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBalanceCheck.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSaleBalanceEditUI.this, "ActionBalanceCheck", "actionBalanceCheck_actionPerformed", e);
        }
    }

    /**
     * output ActionBalanceConfirm class
     */     
    protected class ActionBalanceConfirm extends ItemAction {     
    
        public ActionBalanceConfirm()
        {
            this(null);
        }

        public ActionBalanceConfirm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBalanceConfirm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBalanceConfirm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBalanceConfirm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSaleBalanceEditUI.this, "ActionBalanceConfirm", "actionBalanceConfirm_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SaleBalanceEditUI");
    }




}