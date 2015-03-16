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
public abstract class AbstractChequeDistributeUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChequeDistributeUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewKeeper;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7NewKeeper;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewKeepOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7NewKeepOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDistributeCheques;
    protected com.kingdee.bos.ctrl.swing.KDButton btnYes;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtToDistributeCheques;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractChequeDistributeUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractChequeDistributeUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contNewKeeper = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7NewKeeper = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contNewKeepOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7NewKeepOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contToDistributeCheques = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnYes = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnNo = new com.kingdee.bos.ctrl.swing.KDButton();
        this.txtToDistributeCheques = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contNewKeeper.setName("contNewKeeper");
        this.f7NewKeeper.setName("f7NewKeeper");
        this.contNewKeepOrgUnit.setName("contNewKeepOrgUnit");
        this.f7NewKeepOrgUnit.setName("f7NewKeepOrgUnit");
        this.contToDistributeCheques.setName("contToDistributeCheques");
        this.btnYes.setName("btnYes");
        this.btnNo.setName("btnNo");
        this.txtToDistributeCheques.setName("txtToDistributeCheques");
        // CoreUI
        // contNewKeeper		
        this.contNewKeeper.setBoundLabelText(resHelper.getString("contNewKeeper.boundLabelText"));		
        this.contNewKeeper.setBoundLabelLength(100);		
        this.contNewKeeper.setBoundLabelUnderline(true);
        // f7NewKeeper		
        this.f7NewKeeper.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7UserQuery");		
        this.f7NewKeeper.setCommitFormat("$number$");		
        this.f7NewKeeper.setEditFormat("$number$");		
        this.f7NewKeeper.setDisplayFormat("$name$");
        // contNewKeepOrgUnit		
        this.contNewKeepOrgUnit.setBoundLabelText(resHelper.getString("contNewKeepOrgUnit.boundLabelText"));		
        this.contNewKeepOrgUnit.setBoundLabelLength(100);		
        this.contNewKeepOrgUnit.setBoundLabelUnderline(true);
        // f7NewKeepOrgUnit		
        this.f7NewKeepOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.SaleOrgUnitQuery");		
        this.f7NewKeepOrgUnit.setCommitFormat("$number$");		
        this.f7NewKeepOrgUnit.setDisplayFormat("$name$");		
        this.f7NewKeepOrgUnit.setEditFormat("$number$");
        // contToDistributeCheques		
        this.contToDistributeCheques.setBoundLabelText(resHelper.getString("contToDistributeCheques.boundLabelText"));		
        this.contToDistributeCheques.setBoundLabelLength(100);		
        this.contToDistributeCheques.setBoundLabelUnderline(true);
        // btnYes		
        this.btnYes.setText(resHelper.getString("btnYes.text"));
        this.btnYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnYes_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnNo		
        this.btnNo.setText(resHelper.getString("btnNo.text"));
        this.btnNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnNo_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtToDistributeCheques
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
        this.setBounds(new Rectangle(10, 10, 700, 210));
        this.setLayout(null);
        contNewKeeper.setBounds(new Rectangle(30, 40, 270, 19));
        this.add(contNewKeeper, null);
        contNewKeepOrgUnit.setBounds(new Rectangle(340, 40, 270, 19));
        this.add(contNewKeepOrgUnit, null);
        contToDistributeCheques.setBounds(new Rectangle(30, 70, 581, 19));
        this.add(contToDistributeCheques, null);
        btnYes.setBounds(new Rectangle(350, 130, 73, 21));
        this.add(btnYes, null);
        btnNo.setBounds(new Rectangle(460, 130, 73, 21));
        this.add(btnNo, null);
        //contNewKeeper
        contNewKeeper.setBoundEditor(f7NewKeeper);
        //contNewKeepOrgUnit
        contNewKeepOrgUnit.setBoundEditor(f7NewKeepOrgUnit);
        //contToDistributeCheques
        contToDistributeCheques.setBoundEditor(txtToDistributeCheques);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.ChequeDistributeUIHandler";
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
     * output btnYes_actionPerformed method
     */
    protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnNo_actionPerformed method
     */
    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ChequeDistributeUI");
    }




}