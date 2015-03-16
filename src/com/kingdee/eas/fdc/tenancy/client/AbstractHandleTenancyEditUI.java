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
public abstract class AbstractHandleTenancyEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractHandleTenancyEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel roomPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel attachPanel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable roomTable;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btHandleTenancy;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable customerTable;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBillNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBillName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBillType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBillAdviser;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAgencyCompany;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBillCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpEndDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpStartDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBillLease;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    protected ActionHandleTenancy actionHandleTenancy = null;
    /**
     * output class constructor
     */
    public AbstractHandleTenancyEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractHandleTenancyEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionHandleTenancy
        this.actionHandleTenancy = new ActionHandleTenancy(this);
        getActionManager().registerAction("actionHandleTenancy", actionHandleTenancy);
         this.actionHandleTenancy.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.roomPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.attachPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.roomTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btHandleTenancy = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.customerTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBillNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBillName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBillType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBillAdviser = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAgencyCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBillCreator = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dpEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dpStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtBillLease = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.roomPanel.setName("roomPanel");
        this.attachPanel.setName("attachPanel");
        this.roomTable.setName("roomTable");
        this.btHandleTenancy.setName("btHandleTenancy");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.customerTable.setName("customerTable");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.txtBillNumber.setName("txtBillNumber");
        this.txtBillName.setName("txtBillName");
        this.txtBillType.setName("txtBillType");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.txtBillAdviser.setName("txtBillAdviser");
        this.txtAgencyCompany.setName("txtAgencyCompany");
        this.txtBillCreator.setName("txtBillCreator");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.dpEndDate.setName("dpEndDate");
        this.dpStartDate.setName("dpStartDate");
        this.txtBillLease.setName("txtBillLease");
        // CoreUI		
        this.setPreferredSize(new Dimension(921,480));
        // kDTabbedPane1
        // roomPanel
        // attachPanel
        // roomTable		
        this.roomTable.setFormatXml(resHelper.getString("roomTable.formatXml"));
        this.roomTable.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    roomTable_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.roomTable.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    roomTable_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btHandleTenancy
        this.btHandleTenancy.setAction((IItemAction)ActionProxyFactory.getProxy(actionHandleTenancy, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btHandleTenancy.setText(resHelper.getString("btHandleTenancy.text"));		
        this.btHandleTenancy.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_persondistribute"));
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
        // customerTable		
        this.customerTable.setFormatXml(resHelper.getString("customerTable.formatXml"));

        

        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // txtBillNumber		
        this.txtBillNumber.setEnabled(false);
        // txtBillName		
        this.txtBillName.setEnabled(false);
        // txtBillType		
        this.txtBillType.setEnabled(false);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // txtBillAdviser		
        this.txtBillAdviser.setEnabled(false);
        // txtAgencyCompany		
        this.txtAgencyCompany.setEnabled(false);
        // txtBillCreator		
        this.txtBillCreator.setEnabled(false);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelUnderline(true);		
        this.kDLabelContainer9.setBoundLabelLength(100);
        // dpEndDate		
        this.dpEndDate.setEnabled(false);
        // dpStartDate		
        this.dpStartDate.setEnabled(false);
        // txtBillLease		
        this.txtBillLease.setEnabled(false);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 921, 480));
        this.setLayout(null);
        kDTabbedPane1.setBounds(new Rectangle(1, 270, 920, 200));
        this.add(kDTabbedPane1, null);
        kDPanel1.setBounds(new Rectangle(1, 5, 920, 118));
        this.add(kDPanel1, null);
        kDPanel2.setBounds(new Rectangle(1, 130, 920, 140));
        this.add(kDPanel2, null);
        //kDTabbedPane1
        kDTabbedPane1.add(roomPanel, resHelper.getString("roomPanel.constraints"));
        kDTabbedPane1.add(attachPanel, resHelper.getString("attachPanel.constraints"));
        //roomPanel
        roomPanel.setLayout(new KDLayout());
        //TODO 由于该容器采用KDLayout布局，请在下面一条语句中修正该容器的初始大小：
        roomPanel.putClientProperty("OriginalBounds", new Rectangle(0,0,1,1));        roomTable.setBounds(new Rectangle(2, 5, 990, 235));
        roomPanel.add(roomTable, new KDLayout.Constraints(2, 5, 990, 235, 0));
        attachPanel.setLayout(new KDLayout());
        //TODO 由于该容器采用KDLayout布局，请在下面一条语句中修正该容器的初始大小：
        attachPanel.putClientProperty("OriginalBounds", new Rectangle(0,0,1,1));        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(1, 5, 920, 118));        kDLabelContainer1.setBounds(new Rectangle(15, 20, 270, 19));
        kDPanel1.add(kDLabelContainer1, new KDLayout.Constraints(15, 20, 270, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(320, 20, 270, 19));
        kDPanel1.add(kDLabelContainer2, new KDLayout.Constraints(320, 20, 270, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(626, 20, 270, 19));
        kDPanel1.add(kDLabelContainer3, new KDLayout.Constraints(626, 20, 270, 19, 0));
        kDLabelContainer4.setBounds(new Rectangle(15, 50, 270, 19));
        kDPanel1.add(kDLabelContainer4, new KDLayout.Constraints(15, 50, 270, 19, 0));
        kDLabelContainer5.setBounds(new Rectangle(320, 50, 270, 19));
        kDPanel1.add(kDLabelContainer5, new KDLayout.Constraints(320, 50, 270, 19, 0));
        kDLabelContainer6.setBounds(new Rectangle(626, 50, 270, 19));
        kDPanel1.add(kDLabelContainer6, new KDLayout.Constraints(626, 50, 270, 19, 0));
        kDLabelContainer7.setBounds(new Rectangle(15, 80, 270, 19));
        kDPanel1.add(kDLabelContainer7, new KDLayout.Constraints(15, 80, 270, 19, 0));
        kDLabelContainer8.setBounds(new Rectangle(320, 80, 270, 19));
        kDPanel1.add(kDLabelContainer8, new KDLayout.Constraints(320, 80, 270, 19, 0));
        kDLabelContainer9.setBounds(new Rectangle(626, 80, 270, 19));
        kDPanel1.add(kDLabelContainer9, new KDLayout.Constraints(626, 80, 270, 19, 0));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtBillNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtBillName);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtBillType);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtBillAdviser);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtAgencyCompany);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtBillCreator);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(txtBillLease);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(dpStartDate);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(dpEndDate);
        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(1, 130, 920, 140));        customerTable.setBounds(new Rectangle(12, 19, 896, 110));
        kDPanel2.add(customerTable, new KDLayout.Constraints(12, 19, 896, 110, 0));

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
        this.toolBar.add(btHandleTenancy);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.HandleTenancyEditUIHandler";
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
	 * ????????У??
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
     * output roomTable_tableSelectChanged method
     */
    protected void roomTable_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output roomTable_tableClicked method
     */
    protected void roomTable_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
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
     * output actionHandleTenancy_actionPerformed method
     */
    public void actionHandleTenancy_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionHandleTenancy(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionHandleTenancy() {
    	return false;
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
            innerActionPerformed("eas", AbstractHandleTenancyEditUI.this, "ActionHandleTenancy", "actionHandleTenancy_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "HandleTenancyEditUI");
    }




}