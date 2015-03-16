/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

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
public abstract class AbstractHandleRoomTenancyEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractHandleRoomTenancyEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel tenancyBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyBillName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyBillType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyBillState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleMan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyTerm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStratDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEndDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7tenancyBill;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTenancyBillType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTenancyBillState;
    protected com.kingdee.bos.ctrl.swing.KDTextField f7SaleMan;
    protected com.kingdee.bos.ctrl.swing.KDTextField f7Creator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTenancyTerm;
    protected com.kingdee.bos.ctrl.swing.KDPanel tenancyBillCustomer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable customerTable;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tenancyRoom;
    protected com.kingdee.bos.ctrl.swing.KDPanel roomPanle;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable roomTable;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelectRoom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEndDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnHandleTenancy;
    protected com.kingdee.bos.ctrl.swing.KDPanel attachPanle;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable attachTable;
    protected com.kingdee.eas.fdc.tenancy.HandleTenancyInfo editData = null;
    protected ActionHandleTenancy actionHandleTenancy = null;
    /**
     * output class constructor
     */
    public AbstractHandleRoomTenancyEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractHandleRoomTenancyEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionHandleTenancy
        this.actionHandleTenancy = new ActionHandleTenancy(this);
        getActionManager().registerAction("actionHandleTenancy", actionHandleTenancy);
         this.actionHandleTenancy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tenancyBill = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contTenancyBillName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyBillType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyBillState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSaleMan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyTerm = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStratDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7tenancyBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtTenancyBillType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTenancyBillState = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7SaleMan = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Creator = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTenancyTerm = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tenancyBillCustomer = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.customerTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tenancyRoom = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.roomPanle = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.roomTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnSelectRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.pkStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.btnHandleTenancy = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.attachPanle = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.attachTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tenancyBill.setName("tenancyBill");
        this.contTenancyBillName.setName("contTenancyBillName");
        this.contTenancyBillType.setName("contTenancyBillType");
        this.contTenancyBillState.setName("contTenancyBillState");
        this.contSaleMan.setName("contSaleMan");
        this.contCreator.setName("contCreator");
        this.contTenancyTerm.setName("contTenancyTerm");
        this.contStratDate.setName("contStratDate");
        this.contEndDate.setName("contEndDate");
        this.f7tenancyBill.setName("f7tenancyBill");
        this.txtTenancyBillType.setName("txtTenancyBillType");
        this.txtTenancyBillState.setName("txtTenancyBillState");
        this.f7SaleMan.setName("f7SaleMan");
        this.f7Creator.setName("f7Creator");
        this.txtTenancyTerm.setName("txtTenancyTerm");
        this.tenancyBillCustomer.setName("tenancyBillCustomer");
        this.customerTable.setName("customerTable");
        this.tenancyRoom.setName("tenancyRoom");
        this.roomPanle.setName("roomPanle");
        this.roomTable.setName("roomTable");
        this.btnSelectRoom.setName("btnSelectRoom");
        this.pkStartDate.setName("pkStartDate");
        this.pkEndDate.setName("pkEndDate");
        this.btnHandleTenancy.setName("btnHandleTenancy");
        this.attachPanle.setName("attachPanle");
        this.attachTable.setName("attachTable");
        // CoreUI
        // tenancyBill		
        this.tenancyBill.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("tenancyBill.border.title")));
        // contTenancyBillName		
        this.contTenancyBillName.setBoundLabelText(resHelper.getString("contTenancyBillName.boundLabelText"));		
        this.contTenancyBillName.setBoundLabelLength(100);		
        this.contTenancyBillName.setBoundLabelUnderline(true);
        // contTenancyBillType		
        this.contTenancyBillType.setBoundLabelText(resHelper.getString("contTenancyBillType.boundLabelText"));		
        this.contTenancyBillType.setBoundLabelLength(100);		
        this.contTenancyBillType.setBoundLabelUnderline(true);
        // contTenancyBillState		
        this.contTenancyBillState.setBoundLabelText(resHelper.getString("contTenancyBillState.boundLabelText"));		
        this.contTenancyBillState.setBoundLabelLength(100);		
        this.contTenancyBillState.setBoundLabelUnderline(true);
        // contSaleMan		
        this.contSaleMan.setBoundLabelText(resHelper.getString("contSaleMan.boundLabelText"));		
        this.contSaleMan.setBoundLabelUnderline(true);		
        this.contSaleMan.setBoundLabelLength(100);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contTenancyTerm		
        this.contTenancyTerm.setBoundLabelText(resHelper.getString("contTenancyTerm.boundLabelText"));		
        this.contTenancyTerm.setBoundLabelUnderline(true);		
        this.contTenancyTerm.setBoundLabelLength(100);
        // contStratDate		
        this.contStratDate.setBoundLabelText(resHelper.getString("contStratDate.boundLabelText"));		
        this.contStratDate.setBoundLabelLength(100);		
        this.contStratDate.setBoundLabelUnderline(true);
        // contEndDate		
        this.contEndDate.setBoundLabelText(resHelper.getString("contEndDate.boundLabelText"));		
        this.contEndDate.setBoundLabelUnderline(true);		
        this.contEndDate.setBoundLabelLength(100);
        // f7tenancyBill		
        this.f7tenancyBill.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillQuery");		
        this.f7tenancyBill.setDisplayFormat("$name$");		
        this.f7tenancyBill.setEditFormat("$number$");		
        this.f7tenancyBill.setCommitFormat("$number$");
        this.f7tenancyBill.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7tenancyBill_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtTenancyBillType		
        this.txtTenancyBillType.setEnabled(false);
        // txtTenancyBillState		
        this.txtTenancyBillState.setEnabled(false);
        // f7SaleMan		
        this.f7SaleMan.setEnabled(false);
        // f7Creator		
        this.f7Creator.setEnabled(false);
        // txtTenancyTerm		
        this.txtTenancyTerm.setEnabled(false);
        // tenancyBillCustomer		
        this.tenancyBillCustomer.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("tenancyBillCustomer.border.title")));
        // customerTable		
        this.customerTable.setFormatXml(resHelper.getString("customerTable.formatXml"));

        

        // tenancyRoom
        // roomPanle		
        this.roomPanle.setBorder(null);
        // roomTable		
        this.roomTable.setFormatXml(resHelper.getString("roomTable.formatXml"));

        

        // btnSelectRoom		
        this.btnSelectRoom.setText(resHelper.getString("btnSelectRoom.text"));
        this.btnSelectRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSelectRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // pkStartDate		
        this.pkStartDate.setEnabled(false);
        // pkEndDate		
        this.pkEndDate.setEnabled(false);
        // btnHandleTenancy
        this.btnHandleTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionHandleTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnHandleTenancy.setText(resHelper.getString("btnHandleTenancy.text"));		
        this.btnHandleTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_persondistribute"));
        // attachPanle
        // attachTable		
        this.attachTable.setFormatXml(resHelper.getString("attachTable.formatXml"));

        

		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 920, 520));
        this.setLayout(null);
        tenancyBill.setBounds(new Rectangle(11, 4, 902, 138));
        this.add(tenancyBill, null);
        tenancyBillCustomer.setBounds(new Rectangle(11, 153, 903, 158));
        this.add(tenancyBillCustomer, null);
        tenancyRoom.setBounds(new Rectangle(16, 345, 890, 165));
        this.add(tenancyRoom, null);
        btnSelectRoom.setBounds(new Rectangle(19, 320, 98, 19));
        this.add(btnSelectRoom, null);
        //tenancyBill
        tenancyBill.setLayout(null);        contTenancyBillName.setBounds(new Rectangle(14, 18, 270, 19));
        tenancyBill.add(contTenancyBillName, null);
        contTenancyBillType.setBounds(new Rectangle(321, 18, 270, 19));
        tenancyBill.add(contTenancyBillType, null);
        contTenancyBillState.setBounds(new Rectangle(611, 17, 270, 19));
        tenancyBill.add(contTenancyBillState, null);
        contSaleMan.setBounds(new Rectangle(15, 58, 270, 19));
        tenancyBill.add(contSaleMan, null);
        contCreator.setBounds(new Rectangle(320, 54, 270, 19));
        tenancyBill.add(contCreator, null);
        contTenancyTerm.setBounds(new Rectangle(614, 52, 270, 19));
        tenancyBill.add(contTenancyTerm, null);
        contStratDate.setBounds(new Rectangle(16, 96, 270, 19));
        tenancyBill.add(contStratDate, null);
        contEndDate.setBounds(new Rectangle(319, 90, 270, 19));
        tenancyBill.add(contEndDate, null);
        //contTenancyBillName
        contTenancyBillName.setBoundEditor(f7tenancyBill);
        //contTenancyBillType
        contTenancyBillType.setBoundEditor(txtTenancyBillType);
        //contTenancyBillState
        contTenancyBillState.setBoundEditor(txtTenancyBillState);
        //contSaleMan
        contSaleMan.setBoundEditor(f7SaleMan);
        //contCreator
        contCreator.setBoundEditor(f7Creator);
        //contTenancyTerm
        contTenancyTerm.setBoundEditor(txtTenancyTerm);
        //contStratDate
        contStratDate.setBoundEditor(pkStartDate);
        //contEndDate
        contEndDate.setBoundEditor(pkEndDate);
        //tenancyBillCustomer
        tenancyBillCustomer.setLayout(null);        customerTable.setBounds(new Rectangle(13, 18, 879, 120));
        tenancyBillCustomer.add(customerTable, null);
        //tenancyRoom
        tenancyRoom.add(roomPanle, resHelper.getString("roomPanle.constraints"));
        tenancyRoom.add(attachPanle, resHelper.getString("attachPanle.constraints"));
        //roomPanle
        roomPanle.setLayout(null);        roomTable.setBounds(new Rectangle(4, 9, 883, 119));
        roomPanle.add(roomTable, null);
        //attachPanle
        attachPanle.setLayout(null);        attachTable.setBounds(new Rectangle(6, 10, 877, 125));
        attachPanle.add(attachTable, null);

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
        this.toolBar.add(btnHandleTenancy);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.HandleRoomTenancyEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.tenancy.HandleTenancyInfo)ov;
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
     * output f7tenancyBill_dataChanged method
     */
    protected void f7tenancyBill_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnSelectRoom_actionPerformed method
     */
    protected void btnSelectRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
     * output actionHandleTenancy_actionPerformed method
     */
    public void actionHandleTenancy_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionHandleTenancy class
     */
    protected class ActionHandleTenancy extends ItemAction
    {
        public ActionHandleTenancy()
        {
            this(null);
        }

        public ActionHandleTenancy(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionHandleTenancy.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHandleTenancy.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHandleTenancy.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractHandleRoomTenancyEditUI.this, "ActionHandleTenancy", "actionHandleTenancy_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "HandleRoomTenancyEditUI");
    }




}