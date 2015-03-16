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
public abstract class AbstractRoomPropertyBookBatchEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomPropertyBookBatchEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPropertyState;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPropertyState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTransactor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTransactor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contScheme;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtScheme;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBookState;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBookState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUpdateDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkUpdateDate;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdConBooks;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdContStep;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdContMaterial;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtBook;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtStep;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtMaterial;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveRoom;
    protected com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo editData = null;
    protected ActionAddRoom actionAddRoom = null;
    protected ActionRemoveRoom actionRemoveRoom = null;
    /**
     * output class constructor
     */
    public AbstractRoomPropertyBookBatchEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomPropertyBookBatchEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddRoom
        this.actionAddRoom = new ActionAddRoom(this);
        getActionManager().registerAction("actionAddRoom", actionAddRoom);
         this.actionAddRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveRoom
        this.actionRemoveRoom = new ActionRemoveRoom(this);
        getActionManager().registerAction("actionRemoveRoom", actionRemoveRoom);
         this.actionRemoveRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contPropertyState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboPropertyState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contTransactor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtTransactor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contScheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtScheme = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contBookState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboBookState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contUpdateDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkUpdateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kdConBooks = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdContStep = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdContMaterial = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtBook = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtStep = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtMaterial = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contPropertyState.setName("contPropertyState");
        this.comboPropertyState.setName("comboPropertyState");
        this.contTransactor.setName("contTransactor");
        this.prmtTransactor.setName("prmtTransactor");
        this.contScheme.setName("contScheme");
        this.prmtScheme.setName("prmtScheme");
        this.contBookState.setName("contBookState");
        this.comboBookState.setName("comboBookState");
        this.contUpdateDate.setName("contUpdateDate");
        this.pkUpdateDate.setName("pkUpdateDate");
        this.kdConBooks.setName("kdConBooks");
        this.kdContStep.setName("kdContStep");
        this.kdContMaterial.setName("kdContMaterial");
        this.kdtBook.setName("kdtBook");
        this.kdtStep.setName("kdtStep");
        this.kdtMaterial.setName("kdtMaterial");
        this.btnAddRoom.setName("btnAddRoom");
        this.btnRemoveRoom.setName("btnRemoveRoom");
        // CoreUI
        // contNumber		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));
        // txtNumber
        // contPropertyState		
        this.contPropertyState.setBoundLabelText(resHelper.getString("contPropertyState.boundLabelText"));		
        this.contPropertyState.setBoundLabelLength(100);		
        this.contPropertyState.setBoundLabelUnderline(true);
        // comboPropertyState		
        this.comboPropertyState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.PropertyStateEnum").toArray());
        // contTransactor		
        this.contTransactor.setBoundLabelText(resHelper.getString("contTransactor.boundLabelText"));		
        this.contTransactor.setBoundLabelLength(100);		
        this.contTransactor.setBoundLabelUnderline(true);
        // prmtTransactor
        // contScheme		
        this.contScheme.setBoundLabelText(resHelper.getString("contScheme.boundLabelText"));		
        this.contScheme.setBoundLabelLength(100);		
        this.contScheme.setBoundLabelUnderline(true);
        // prmtScheme
        // contBookState		
        this.contBookState.setBoundLabelText(resHelper.getString("contBookState.boundLabelText"));		
        this.contBookState.setBoundLabelLength(100);		
        this.contBookState.setBoundLabelUnderline(true);
        // comboBookState		
        this.comboBookState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum").toArray());
        // contUpdateDate		
        this.contUpdateDate.setBoundLabelText(resHelper.getString("contUpdateDate.boundLabelText"));		
        this.contUpdateDate.setBoundLabelLength(100);		
        this.contUpdateDate.setBoundLabelUnderline(true);
        // pkUpdateDate
        // kdConBooks		
        this.kdConBooks.setTitle(resHelper.getString("kdConBooks.title"));
        // kdContStep		
        this.kdContStep.setTitle(resHelper.getString("kdContStep.title"));
        // kdContMaterial		
        this.kdContMaterial.setTitle(resHelper.getString("kdContMaterial.title"));
        // kdtBook		
        this.kdtBook.setFormatXml(resHelper.getString("kdtBook.formatXml"));

        

        // kdtStep		
        this.kdtStep.setFormatXml(resHelper.getString("kdtStep.formatXml"));

                this.kdtStep.putBindContents("editData",new String[] {"number","name","description","isFinish","date"});


        // kdtMaterial		
        this.kdtMaterial.setFormatXml(resHelper.getString("kdtMaterial.formatXml"));

                this.kdtMaterial.putBindContents("editData",new String[] {"number","name","description","isFinish","date"});


        // btnAddRoom
        this.btnAddRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRoom.setText(resHelper.getString("btnAddRoom.text"));		
        this.btnAddRoom.setToolTipText(resHelper.getString("btnAddRoom.toolTipText"));		
        this.btnAddRoom.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        // btnRemoveRoom
        this.btnRemoveRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRemoveRoom.setText(resHelper.getString("btnRemoveRoom.text"));		
        this.btnRemoveRoom.setToolTipText(resHelper.getString("btnRemoveRoom.toolTipText"));		
        this.btnRemoveRoom.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1000, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1000, 600));
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, 0));
        contPropertyState.setBounds(new Rectangle(365, 40, 270, 19));
        this.add(contPropertyState, new KDLayout.Constraints(365, 40, 270, 19, 0));
        contTransactor.setBounds(new Rectangle(365, 10, 270, 19));
        this.add(contTransactor, new KDLayout.Constraints(365, 10, 270, 19, 0));
        contScheme.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contScheme, new KDLayout.Constraints(10, 40, 270, 19, 0));
        contBookState.setBounds(new Rectangle(720, 10, 270, 19));
        this.add(contBookState, new KDLayout.Constraints(720, 10, 270, 19, 0));
        contUpdateDate.setBounds(new Rectangle(720, 40, 270, 19));
        this.add(contUpdateDate, new KDLayout.Constraints(720, 40, 270, 19, 0));
        kdConBooks.setBounds(new Rectangle(10, 70, 980, 250));
        this.add(kdConBooks, new KDLayout.Constraints(10, 70, 980, 250, 0));
        kdContStep.setBounds(new Rectangle(10, 330, 461, 230));
        this.add(kdContStep, new KDLayout.Constraints(10, 330, 461, 230, 0));
        kdContMaterial.setBounds(new Rectangle(497, 330, 493, 230));
        this.add(kdContMaterial, new KDLayout.Constraints(497, 330, 493, 230, 0));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contPropertyState
        contPropertyState.setBoundEditor(comboPropertyState);
        //contTransactor
        contTransactor.setBoundEditor(prmtTransactor);
        //contScheme
        contScheme.setBoundEditor(prmtScheme);
        //contBookState
        contBookState.setBoundEditor(comboBookState);
        //contUpdateDate
        contUpdateDate.setBoundEditor(pkUpdateDate);
        //kdConBooks
kdConBooks.getContentPane().setLayout(new BorderLayout(0, 0));        kdConBooks.getContentPane().add(kdtBook, BorderLayout.CENTER);
        //kdContStep
kdContStep.getContentPane().setLayout(new BorderLayout(0, 0));        kdContStep.getContentPane().add(kdtStep, BorderLayout.CENTER);
        //kdContMaterial
kdContMaterial.getContentPane().setLayout(new BorderLayout(0, 0));        kdContMaterial.getContentPane().add(kdtMaterial, BorderLayout.CENTER);

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
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
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
        this.toolBar.add(btnAddRoom);
        this.toolBar.add(btnRemoveRoom);

    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("propertyState", com.kingdee.eas.fdc.sellhouse.PropertyStateEnum.class, this.comboPropertyState, "selectedItem");
		dataBinder.registerBinding("transactor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtTransactor, "data");
		dataBinder.registerBinding("scheme", com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo.class, this.prmtScheme, "data");
		dataBinder.registerBinding("bookState", com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum.class, this.comboBookState, "selectedItem");
		dataBinder.registerBinding("updateDate", java.util.Date.class, this.pkUpdateDate, "value");
		dataBinder.registerBinding("book", com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchBooksInfo.class, this.kdtBook, "userObject");
		dataBinder.registerBinding("step.number", String.class, this.kdtStep, "number.text");
		dataBinder.registerBinding("step.name", String.class, this.kdtStep, "name.text");
		dataBinder.registerBinding("step.description", String.class, this.kdtStep, "description.text");
		dataBinder.registerBinding("step.isFinish", boolean.class, this.kdtStep, "isFinish.text");
		dataBinder.registerBinding("step.date", java.util.Date.class, this.kdtStep, "date.text");
		dataBinder.registerBinding("step", com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchStepInfo.class, this.kdtStep, "userObject");
		dataBinder.registerBinding("material.number", String.class, this.kdtMaterial, "number.text");
		dataBinder.registerBinding("material.name", String.class, this.kdtMaterial, "name.text");
		dataBinder.registerBinding("material.description", String.class, this.kdtMaterial, "description.text");
		dataBinder.registerBinding("material.isFinish", boolean.class, this.kdtMaterial, "isFinish.text");
		dataBinder.registerBinding("material.date", java.util.Date.class, this.kdtMaterial, "date.text");
		dataBinder.registerBinding("material", com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchMaterialsInfo.class, this.kdtMaterial, "userObject");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomPropertyBookBatchEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo)ov;
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
		getValidateHelper().registerBindProperty("propertyState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("transactor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("scheme", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("updateDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("book", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("step.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("step.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("step.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("step.isFinish", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("step.date", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("step", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("material.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("material.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("material.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("material.isFinish", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("material.date", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("material", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("propertyState"));
        sic.add(new SelectorItemInfo("transactor.*"));
        sic.add(new SelectorItemInfo("scheme.*"));
        sic.add(new SelectorItemInfo("bookState"));
        sic.add(new SelectorItemInfo("updateDate"));
        sic.add(new SelectorItemInfo("book.*"));
//        sic.add(new SelectorItemInfo("book.number"));
    sic.add(new SelectorItemInfo("step.number"));
    sic.add(new SelectorItemInfo("step.name"));
    sic.add(new SelectorItemInfo("step.description"));
    sic.add(new SelectorItemInfo("step.isFinish"));
    sic.add(new SelectorItemInfo("step.date"));
        sic.add(new SelectorItemInfo("step.*"));
//        sic.add(new SelectorItemInfo("step.number"));
    sic.add(new SelectorItemInfo("material.number"));
    sic.add(new SelectorItemInfo("material.name"));
    sic.add(new SelectorItemInfo("material.description"));
    sic.add(new SelectorItemInfo("material.isFinish"));
    sic.add(new SelectorItemInfo("material.date"));
        sic.add(new SelectorItemInfo("material.*"));
//        sic.add(new SelectorItemInfo("material.number"));
        return sic;
    }        
    	

    /**
     * output actionAddRoom_actionPerformed method
     */
    public void actionAddRoom_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveRoom_actionPerformed method
     */
    public void actionRemoveRoom_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionAddRoom class
     */
    protected class ActionAddRoom extends ItemAction
    {
        public ActionAddRoom()
        {
            this(null);
        }

        public ActionAddRoom(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomPropertyBookBatchEditUI.this, "ActionAddRoom", "actionAddRoom_actionPerformed", e);
        }
    }

    /**
     * output ActionRemoveRoom class
     */
    protected class ActionRemoveRoom extends ItemAction
    {
        public ActionRemoveRoom()
        {
            this(null);
        }

        public ActionRemoveRoom(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRemoveRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRemoveRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomPropertyBookBatchEditUI.this, "ActionRemoveRoom", "actionRemoveRoom_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomPropertyBookBatchEditUI");
    }




}