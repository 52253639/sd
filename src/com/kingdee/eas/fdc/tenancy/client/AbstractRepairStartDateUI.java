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
public abstract class AbstractRepairStartDateUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRepairStartDateUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conRepairStartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFirstLeaseEndDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkRepairStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFirstLeaseEndDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnNoConfirm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFirstLeaseType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboFirstLeaseType;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRepairStartDateUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRepairStartDateUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.conRepairStartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFirstLeaseEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkRepairStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkFirstLeaseEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnNoConfirm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contFirstLeaseType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboFirstLeaseType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.conRepairStartDate.setName("conRepairStartDate");
        this.contFirstLeaseEndDate.setName("contFirstLeaseEndDate");
        this.pkRepairStartDate.setName("pkRepairStartDate");
        this.pkFirstLeaseEndDate.setName("pkFirstLeaseEndDate");
        this.btnConfirm.setName("btnConfirm");
        this.btnNoConfirm.setName("btnNoConfirm");
        this.contFirstLeaseType.setName("contFirstLeaseType");
        this.comboFirstLeaseType.setName("comboFirstLeaseType");
        // CoreUI
        // conRepairStartDate		
        this.conRepairStartDate.setBoundLabelText(resHelper.getString("conRepairStartDate.boundLabelText"));		
        this.conRepairStartDate.setBoundLabelLength(100);		
        this.conRepairStartDate.setBoundLabelUnderline(true);
        // contFirstLeaseEndDate		
        this.contFirstLeaseEndDate.setBoundLabelText(resHelper.getString("contFirstLeaseEndDate.boundLabelText"));		
        this.contFirstLeaseEndDate.setBoundLabelLength(100);		
        this.contFirstLeaseEndDate.setBoundLabelUnderline(true);
        // pkRepairStartDate
        // pkFirstLeaseEndDate
        // btnConfirm		
        this.btnConfirm.setText(resHelper.getString("btnConfirm.text"));
        this.btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnConfirm_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnNoConfirm		
        this.btnNoConfirm.setText(resHelper.getString("btnNoConfirm.text"));
        this.btnNoConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnNoConfirm_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contFirstLeaseType		
        this.contFirstLeaseType.setBoundLabelText(resHelper.getString("contFirstLeaseType.boundLabelText"));		
        this.contFirstLeaseType.setBoundLabelLength(100);		
        this.contFirstLeaseType.setBoundLabelUnderline(true);
        // comboFirstLeaseType		
        this.comboFirstLeaseType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum").toArray());
        this.comboFirstLeaseType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboFirstLeaseType_itemStateChanged(e);
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

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 600, 150));
        this.setLayout(null);
        conRepairStartDate.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(conRepairStartDate, null);
        contFirstLeaseEndDate.setBounds(new Rectangle(311, 50, 270, 19));
        this.add(contFirstLeaseEndDate, null);
        btnConfirm.setBounds(new Rectangle(260, 108, 70, 19));
        this.add(btnConfirm, null);
        btnNoConfirm.setBounds(new Rectangle(340, 107, 63, 19));
        this.add(btnNoConfirm, null);
        contFirstLeaseType.setBounds(new Rectangle(8, 50, 270, 19));
        this.add(contFirstLeaseType, null);
        //conRepairStartDate
        conRepairStartDate.setBoundEditor(pkRepairStartDate);
        //contFirstLeaseEndDate
        contFirstLeaseEndDate.setBoundEditor(pkFirstLeaseEndDate);
        //contFirstLeaseType
        contFirstLeaseType.setBoundEditor(comboFirstLeaseType);

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

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.RepairStartDateUIHandler";
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
     * output btnConfirm_actionPerformed method
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnNoConfirm_actionPerformed method
     */
    protected void btnNoConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output comboFirstLeaseType_itemStateChanged method
     */
    protected void comboFirstLeaseType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
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
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "RepairStartDateUI");
    }




}