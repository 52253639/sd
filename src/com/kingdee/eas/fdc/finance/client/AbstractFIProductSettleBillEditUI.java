/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

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
public abstract class AbstractFIProductSettleBillEditUI extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFIProductSettleBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCompArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCompDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalCost;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHappenCost;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtHappenCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastCompArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLastCompArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastTotalCost;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLastTotalCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastHappenCost;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLastHappenCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSaleArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDrawingCostRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReason;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox ckcSelfDefined;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDrawingCostRate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtReason;
    protected com.kingdee.eas.fdc.finance.FIProductSettleBillInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractFIProductSettleBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFIProductSettleBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionWorkflowList
        String _tempStr = null;
        actionWorkflowList.setEnabled(false);
        actionWorkflowList.setDaemonRun(false);

        _tempStr = resHelper.getString("actionWorkflowList.SHORT_DESCRIPTION");
        actionWorkflowList.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionWorkflowList.LONG_DESCRIPTION");
        actionWorkflowList.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionWorkflowList.NAME");
        actionWorkflowList.putValue(ItemAction.NAME, _tempStr);
         this.actionWorkflowList.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCompArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCompArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contCompDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkCompDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contTotalCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtTotalCost = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contHappenCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtHappenCost = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contLastCompArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtLastCompArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contLastTotalCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtLastTotalCost = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contLastHappenCost = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtLastHappenCost = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contSaleArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSaleArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contDrawingCostRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.ckcSelfDefined = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtDrawingCostRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtReason = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCompArea.setName("contCompArea");
        this.txtCompArea.setName("txtCompArea");
        this.contCompDate.setName("contCompDate");
        this.pkCompDate.setName("pkCompDate");
        this.contTotalCost.setName("contTotalCost");
        this.txtTotalCost.setName("txtTotalCost");
        this.contHappenCost.setName("contHappenCost");
        this.txtHappenCost.setName("txtHappenCost");
        this.contLastCompArea.setName("contLastCompArea");
        this.txtLastCompArea.setName("txtLastCompArea");
        this.contLastTotalCost.setName("contLastTotalCost");
        this.txtLastTotalCost.setName("txtLastTotalCost");
        this.contLastHappenCost.setName("contLastHappenCost");
        this.txtLastHappenCost.setName("txtLastHappenCost");
        this.contSaleArea.setName("contSaleArea");
        this.txtSaleArea.setName("txtSaleArea");
        this.contDrawingCostRate.setName("contDrawingCostRate");
        this.contReason.setName("contReason");
        this.ckcSelfDefined.setName("ckcSelfDefined");
        this.txtDrawingCostRate.setName("txtDrawingCostRate");
        this.txtReason.setName("txtReason");
        // CoreUI
        // contCompArea		
        this.contCompArea.setBoundLabelText(resHelper.getString("contCompArea.boundLabelText"));		
        this.contCompArea.setBoundLabelLength(100);		
        this.contCompArea.setBoundLabelUnderline(true);
        // txtCompArea		
        this.txtCompArea.setEnabled(false);		
        this.txtCompArea.setPrecision(2);		
        this.txtCompArea.setDataType(1);
        // contCompDate		
        this.contCompDate.setBoundLabelText(resHelper.getString("contCompDate.boundLabelText"));		
        this.contCompDate.setBoundLabelLength(100);		
        this.contCompDate.setBoundLabelUnderline(true);
        // pkCompDate		
        this.pkCompDate.setEnabled(false);
        // contTotalCost		
        this.contTotalCost.setBoundLabelText(resHelper.getString("contTotalCost.boundLabelText"));		
        this.contTotalCost.setBoundLabelLength(100);		
        this.contTotalCost.setBoundLabelUnderline(true);
        // txtTotalCost		
        this.txtTotalCost.setEnabled(false);		
        this.txtTotalCost.setPrecision(2);		
        this.txtTotalCost.setDataType(1);
        // contHappenCost		
        this.contHappenCost.setBoundLabelText(resHelper.getString("contHappenCost.boundLabelText"));		
        this.contHappenCost.setBoundLabelLength(100);		
        this.contHappenCost.setBoundLabelUnderline(true);
        // txtHappenCost		
        this.txtHappenCost.setEnabled(false);		
        this.txtHappenCost.setPrecision(2);		
        this.txtHappenCost.setDataType(1);
        // contLastCompArea		
        this.contLastCompArea.setBoundLabelText(resHelper.getString("contLastCompArea.boundLabelText"));		
        this.contLastCompArea.setBoundLabelLength(100);		
        this.contLastCompArea.setBoundLabelUnderline(true);
        // txtLastCompArea		
        this.txtLastCompArea.setEnabled(false);		
        this.txtLastCompArea.setPrecision(2);		
        this.txtLastCompArea.setDataType(1);
        // contLastTotalCost		
        this.contLastTotalCost.setBoundLabelText(resHelper.getString("contLastTotalCost.boundLabelText"));		
        this.contLastTotalCost.setBoundLabelLength(100);		
        this.contLastTotalCost.setBoundLabelUnderline(true);
        // txtLastTotalCost		
        this.txtLastTotalCost.setEnabled(false);		
        this.txtLastTotalCost.setPrecision(2);		
        this.txtLastTotalCost.setDataType(1);
        // contLastHappenCost		
        this.contLastHappenCost.setBoundLabelText(resHelper.getString("contLastHappenCost.boundLabelText"));		
        this.contLastHappenCost.setBoundLabelLength(100);		
        this.contLastHappenCost.setBoundLabelUnderline(true);
        // txtLastHappenCost		
        this.txtLastHappenCost.setEnabled(false);		
        this.txtLastHappenCost.setPrecision(2);		
        this.txtLastHappenCost.setDataType(1);
        // contSaleArea		
        this.contSaleArea.setBoundLabelText(resHelper.getString("contSaleArea.boundLabelText"));		
        this.contSaleArea.setBoundLabelLength(100);		
        this.contSaleArea.setBoundLabelUnderline(true);
        // txtSaleArea		
        this.txtSaleArea.setDataType(1);		
        this.txtSaleArea.setPrecision(2);		
        this.txtSaleArea.setEnabled(false);
        // contDrawingCostRate		
        this.contDrawingCostRate.setBoundLabelText(resHelper.getString("contDrawingCostRate.boundLabelText"));		
        this.contDrawingCostRate.setBoundLabelLength(100);		
        this.contDrawingCostRate.setBoundLabelUnderline(true);		
        this.contDrawingCostRate.setEnabled(false);
        // contReason		
        this.contReason.setBoundLabelText(resHelper.getString("contReason.boundLabelText"));		
        this.contReason.setBoundLabelLength(100);		
        this.contReason.setBoundLabelUnderline(true);		
        this.contReason.setEnabled(false);
        // ckcSelfDefined		
        this.ckcSelfDefined.setText(resHelper.getString("ckcSelfDefined.text"));
        this.ckcSelfDefined.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    ckcSelfDefined_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtDrawingCostRate		
        this.txtDrawingCostRate.setDataType(1);		
        this.txtDrawingCostRate.setPrecision(2);		
        this.txtDrawingCostRate.setEnabled(false);
        // txtReason		
        this.txtReason.setMaxLength(80);		
        this.txtReason.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 290, 280));
        this.setLayout(null);
        contCompArea.setBounds(new Rectangle(10, 35, 270, 19));
        this.add(contCompArea, null);
        contCompDate.setBounds(new Rectangle(10, 85, 270, 19));
        this.add(contCompDate, null);
        contTotalCost.setBounds(new Rectangle(10, 60, 270, 19));
        this.add(contTotalCost, null);
        contHappenCost.setBounds(new Rectangle(10, 110, 270, 19));
        this.add(contHappenCost, null);
        contLastCompArea.setBounds(new Rectangle(10, 203, 270, 19));
        this.add(contLastCompArea, null);
        contLastTotalCost.setBounds(new Rectangle(10, 228, 270, 19));
        this.add(contLastTotalCost, null);
        contLastHappenCost.setBounds(new Rectangle(10, 253, 270, 19));
        this.add(contLastHappenCost, null);
        contSaleArea.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contSaleArea, null);
        contDrawingCostRate.setBounds(new Rectangle(10, 153, 270, 19));
        this.add(contDrawingCostRate, null);
        contReason.setBounds(new Rectangle(10, 178, 270, 19));
        this.add(contReason, null);
        ckcSelfDefined.setBounds(new Rectangle(10, 134, 270, 19));
        this.add(ckcSelfDefined, null);
        //contCompArea
        contCompArea.setBoundEditor(txtCompArea);
        //contCompDate
        contCompDate.setBoundEditor(pkCompDate);
        //contTotalCost
        contTotalCost.setBoundEditor(txtTotalCost);
        //contHappenCost
        contHappenCost.setBoundEditor(txtHappenCost);
        //contLastCompArea
        contLastCompArea.setBoundEditor(txtLastCompArea);
        //contLastTotalCost
        contLastTotalCost.setBoundEditor(txtLastTotalCost);
        //contLastHappenCost
        contLastHappenCost.setBoundEditor(txtLastHappenCost);
        //contSaleArea
        contSaleArea.setBoundEditor(txtSaleArea);
        //contDrawingCostRate
        contDrawingCostRate.setBoundEditor(txtDrawingCostRate);
        //contReason
        contReason.setBoundEditor(txtReason);

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
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
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
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
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
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
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
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
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
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("compArea", java.math.BigDecimal.class, this.txtCompArea, "value");
		dataBinder.registerBinding("compDate", java.util.Date.class, this.pkCompDate, "value");
		dataBinder.registerBinding("totalCost", java.math.BigDecimal.class, this.txtTotalCost, "value");
		dataBinder.registerBinding("happenCost", java.math.BigDecimal.class, this.txtHappenCost, "value");
		dataBinder.registerBinding("lastCompArea", java.math.BigDecimal.class, this.txtLastCompArea, "value");
		dataBinder.registerBinding("lastTotalCost", java.math.BigDecimal.class, this.txtLastTotalCost, "value");
		dataBinder.registerBinding("lastHappenCost", java.math.BigDecimal.class, this.txtLastHappenCost, "value");
		dataBinder.registerBinding("saleArea", java.math.BigDecimal.class, this.txtSaleArea, "value");
		dataBinder.registerBinding("isSelfDefine", boolean.class, this.ckcSelfDefined, "selected");
		dataBinder.registerBinding("drawingCostRate", java.math.BigDecimal.class, this.txtDrawingCostRate, "value");
		dataBinder.registerBinding("description", String.class, this.txtReason, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.FIProductSettleBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.finance.FIProductSettleBillInfo)ov;
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
		getValidateHelper().registerBindProperty("compArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("compDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("happenCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastCompArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastTotalCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastHappenCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("saleArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isSelfDefine", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("drawingCostRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    		
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
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output ckcSelfDefined_stateChanged method
     */
    protected void ckcSelfDefined_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("compArea"));
        sic.add(new SelectorItemInfo("compDate"));
        sic.add(new SelectorItemInfo("totalCost"));
        sic.add(new SelectorItemInfo("happenCost"));
        sic.add(new SelectorItemInfo("lastCompArea"));
        sic.add(new SelectorItemInfo("lastTotalCost"));
        sic.add(new SelectorItemInfo("lastHappenCost"));
        sic.add(new SelectorItemInfo("saleArea"));
        sic.add(new SelectorItemInfo("isSelfDefine"));
        sic.add(new SelectorItemInfo("drawingCostRate"));
        sic.add(new SelectorItemInfo("description"));
        return sic;
    }        
    	

    /**
     * output actionWorkflowList_actionPerformed method
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }
	public RequestContext prepareactionWorkflowList(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareactionWorkflowList(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionWorkflowList() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "FIProductSettleBillEditUI");
    }




}