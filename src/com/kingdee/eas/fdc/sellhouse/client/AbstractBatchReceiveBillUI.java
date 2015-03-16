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
public abstract class AbstractBatchReceiveBillUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBatchReceiveBillUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcReceiveSubject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer txtBalanceNum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcReceiveBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcBalanceType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcReceiveAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcOppSubject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcPayBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcPayAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcSummary;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcAuditPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcCreateDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcPayType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcAuditDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Creator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ReceiveSubject;
    protected com.kingdee.bos.ctrl.swing.KDTextField kDTextField1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ReceiveAccount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtReceiveBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BalanceType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSummary;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPayBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7OppSubject;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbPayType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPayAccountNum;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpCreateDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Project;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpAuditDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7AuditPerson;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton wbAddRoom;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.eas.framework.CoreBillBaseInfo editData = null;

    /**
     * output class constructor
     */
    public AbstractBatchReceiveBillUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBatchReceiveBillUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.lcReceiveSubject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBalanceNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcReceiveBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcBalanceType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcReceiveAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcOppSubject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcPayBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcPayAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcSummary = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcAuditPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcCreateDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcPayType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcAuditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Creator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7ReceiveSubject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTextField1 = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7ReceiveAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtReceiveBank = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7BalanceType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSummary = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPayBank = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7OppSubject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cbPayType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtPayAccountNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.dpCreateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Project = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dpAuditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7AuditPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.wbAddRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.lcReceiveSubject.setName("lcReceiveSubject");
        this.txtBalanceNum.setName("txtBalanceNum");
        this.lcReceiveBank.setName("lcReceiveBank");
        this.lcBalanceType.setName("lcBalanceType");
        this.lcReceiveAccount.setName("lcReceiveAccount");
        this.lcOppSubject.setName("lcOppSubject");
        this.lcPayBank.setName("lcPayBank");
        this.lcPayAccount.setName("lcPayAccount");
        this.lcSummary.setName("lcSummary");
        this.lcAuditPerson.setName("lcAuditPerson");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.lcCreateDate.setName("lcCreateDate");
        this.lcPayType.setName("lcPayType");
        this.lcProject.setName("lcProject");
        this.lcAuditDate.setName("lcAuditDate");
        this.lcCreator.setName("lcCreator");
        this.f7Creator.setName("f7Creator");
        this.f7ReceiveSubject.setName("f7ReceiveSubject");
        this.kDTextField1.setName("kDTextField1");
        this.f7ReceiveAccount.setName("f7ReceiveAccount");
        this.txtReceiveBank.setName("txtReceiveBank");
        this.f7BalanceType.setName("f7BalanceType");
        this.txtSummary.setName("txtSummary");
        this.txtPayBank.setName("txtPayBank");
        this.f7OppSubject.setName("f7OppSubject");
        this.cbPayType.setName("cbPayType");
        this.txtPayAccountNum.setName("txtPayAccountNum");
        this.dpCreateDate.setName("dpCreateDate");
        this.f7Project.setName("f7Project");
        this.dpAuditDate.setName("dpAuditDate");
        this.f7AuditPerson.setName("f7AuditPerson");
        this.wbAddRoom.setName("wbAddRoom");
        this.kDTable1.setName("kDTable1");
        // CoreUI
        this.setPreferredSize(new Dimension(900,600));
        // lcReceiveSubject
        this.lcReceiveSubject.setBoundLabelText(resHelper.getString("lcReceiveSubject.boundLabelText"));
        this.lcReceiveSubject.setBoundLabelUnderline(true);
        this.lcReceiveSubject.setBoundLabelLength(100);
        // txtBalanceNum
        this.txtBalanceNum.setBoundLabelText(resHelper.getString("txtBalanceNum.boundLabelText"));
        this.txtBalanceNum.setBoundLabelLength(100);
        this.txtBalanceNum.setBoundLabelUnderline(true);
        // lcReceiveBank
        this.lcReceiveBank.setBoundLabelText(resHelper.getString("lcReceiveBank.boundLabelText"));
        this.lcReceiveBank.setBoundLabelLength(100);
        this.lcReceiveBank.setBoundLabelUnderline(true);
        // lcBalanceType
        this.lcBalanceType.setBoundLabelText(resHelper.getString("lcBalanceType.boundLabelText"));
        this.lcBalanceType.setBoundLabelLength(100);
        this.lcBalanceType.setBoundLabelUnderline(true);
        // lcReceiveAccount
        this.lcReceiveAccount.setBoundLabelText(resHelper.getString("lcReceiveAccount.boundLabelText"));
        this.lcReceiveAccount.setBoundLabelLength(100);
        this.lcReceiveAccount.setBoundLabelUnderline(true);
        // lcOppSubject
        this.lcOppSubject.setBoundLabelText(resHelper.getString("lcOppSubject.boundLabelText"));
        this.lcOppSubject.setBoundLabelLength(100);
        this.lcOppSubject.setBoundLabelUnderline(true);
        // lcPayBank
        this.lcPayBank.setBoundLabelText(resHelper.getString("lcPayBank.boundLabelText"));
        this.lcPayBank.setBoundLabelLength(100);
        this.lcPayBank.setBoundLabelUnderline(true);
        // lcPayAccount
        this.lcPayAccount.setBoundLabelText(resHelper.getString("lcPayAccount.boundLabelText"));
        this.lcPayAccount.setBoundLabelLength(100);
        this.lcPayAccount.setBoundLabelUnderline(true);
        // lcSummary
        this.lcSummary.setBoundLabelText(resHelper.getString("lcSummary.boundLabelText"));
        this.lcSummary.setBoundLabelLength(100);
        this.lcSummary.setBoundLabelUnderline(true);
        // lcAuditPerson
        this.lcAuditPerson.setBoundLabelText(resHelper.getString("lcAuditPerson.boundLabelText"));
        this.lcAuditPerson.setBoundLabelLength(100);
        this.lcAuditPerson.setBoundLabelUnderline(true);
        // kDLabelContainer11
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));
        this.kDLabelContainer11.setBoundLabelLength(100);
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // lcCreateDate
        this.lcCreateDate.setBoundLabelText(resHelper.getString("lcCreateDate.boundLabelText"));
        this.lcCreateDate.setBoundLabelLength(100);
        this.lcCreateDate.setBoundLabelUnderline(true);
        // lcPayType
        this.lcPayType.setBoundLabelText(resHelper.getString("lcPayType.boundLabelText"));
        this.lcPayType.setBoundLabelLength(100);
        this.lcPayType.setBoundLabelUnderline(true);
        // lcProject
        this.lcProject.setBoundLabelText(resHelper.getString("lcProject.boundLabelText"));
        this.lcProject.setBoundLabelLength(100);
        this.lcProject.setBoundLabelUnderline(true);
        // lcAuditDate
        this.lcAuditDate.setBoundLabelText(resHelper.getString("lcAuditDate.boundLabelText"));
        this.lcAuditDate.setBoundLabelLength(100);
        this.lcAuditDate.setBoundLabelUnderline(true);
        // lcCreator
        this.lcCreator.setBoundLabelText(resHelper.getString("lcCreator.boundLabelText"));
        this.lcCreator.setBoundLabelLength(100);
        this.lcCreator.setBoundLabelUnderline(true);
        // f7Creator
        // f7ReceiveSubject
        // kDTextField1
        // f7ReceiveAccount
        // txtReceiveBank
        // f7BalanceType
        // txtSummary
        // txtPayBank
        // f7OppSubject
        // cbPayType
        // txtPayAccountNum
        // dpCreateDate
        // f7Project
        // dpAuditDate
        // f7AuditPerson
        // wbAddRoom
        this.wbAddRoom.setText(resHelper.getString("wbAddRoom.text"));
        // kDTable1

        

		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 900, 600));
        this.setLayout(null);
        lcReceiveSubject.setBounds(new Rectangle(22, 18, 270, 19));
        this.add(lcReceiveSubject, null);
        txtBalanceNum.setBounds(new Rectangle(317, 49, 270, 19));
        this.add(txtBalanceNum, null);
        lcReceiveBank.setBounds(new Rectangle(611, 15, 270, 19));
        this.add(lcReceiveBank, null);
        lcBalanceType.setBounds(new Rectangle(22, 49, 270, 19));
        this.add(lcBalanceType, null);
        lcReceiveAccount.setBounds(new Rectangle(317, 17, 270, 19));
        this.add(lcReceiveAccount, null);
        lcOppSubject.setBounds(new Rectangle(612, 47, 270, 19));
        this.add(lcOppSubject, null);
        lcPayBank.setBounds(new Rectangle(22, 81, 270, 19));
        this.add(lcPayBank, null);
        lcPayAccount.setBounds(new Rectangle(315, 81, 270, 19));
        this.add(lcPayAccount, null);
        lcSummary.setBounds(new Rectangle(314, 114, 569, 19));
        this.add(lcSummary, null);
        lcAuditPerson.setBounds(new Rectangle(314, 144, 270, 19));
        this.add(lcAuditPerson, null);
        kDLabelContainer11.setBounds(new Rectangle(19, 207, 270, 19));
        this.add(kDLabelContainer11, null);
        lcCreateDate.setBounds(new Rectangle(21, 173, 270, 19));
        this.add(lcCreateDate, null);
        lcPayType.setBounds(new Rectangle(21, 112, 270, 19));
        this.add(lcPayType, null);
        lcProject.setBounds(new Rectangle(612, 79, 270, 19));
        this.add(lcProject, null);
        lcAuditDate.setBounds(new Rectangle(22, 143, 270, 19));
        this.add(lcAuditDate, null);
        lcCreator.setBounds(new Rectangle(314, 176, 270, 19));
        this.add(lcCreator, null);
        wbAddRoom.setBounds(new Rectangle(673, 216, 89, 19));
        this.add(wbAddRoom, null);
        kDTable1.setBounds(new Rectangle(17, 241, 869, 344));
        this.add(kDTable1, null);
        //lcReceiveSubject
        lcReceiveSubject.setBoundEditor(f7ReceiveSubject);
        //txtBalanceNum
        txtBalanceNum.setBoundEditor(kDTextField1);
        //lcReceiveBank
        lcReceiveBank.setBoundEditor(txtReceiveBank);
        //lcBalanceType
        lcBalanceType.setBoundEditor(f7BalanceType);
        //lcReceiveAccount
        lcReceiveAccount.setBoundEditor(f7ReceiveAccount);
        //lcOppSubject
        lcOppSubject.setBoundEditor(f7OppSubject);
        //lcPayBank
        lcPayBank.setBoundEditor(txtPayBank);
        //lcPayAccount
        lcPayAccount.setBoundEditor(txtPayAccountNum);
        //lcSummary
        lcSummary.setBoundEditor(txtSummary);
        //lcAuditPerson
        lcAuditPerson.setBoundEditor(f7AuditPerson);
        //lcCreateDate
        lcCreateDate.setBoundEditor(dpCreateDate);
        //lcPayType
        lcPayType.setBoundEditor(cbPayType);
        //lcProject
        lcProject.setBoundEditor(f7Project);
        //lcAuditDate
        lcAuditDate.setBoundEditor(dpAuditDate);
        //lcCreator
        lcCreator.setBoundEditor(f7Creator);

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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
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
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnAddLine);
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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
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
        this.editData = (com.kingdee.eas.framework.CoreBillBaseInfo)ov;
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
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "BatchReceiveBillUI");
    }




}