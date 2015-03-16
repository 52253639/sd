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
public abstract class AbstractChooseRoomUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChooseRoomUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conFdcCustomer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox promFdcCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conChooseDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateChooseDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox promCreator;
    protected com.kingdee.bos.ctrl.swing.KDButton btnYes;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conDescription;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    /**
     * output class constructor
     */
    public AbstractChooseRoomUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractChooseRoomUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.conNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.conFdcCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.promFdcCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.conChooseDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dateChooseDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.conCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.promCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnYes = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnNo = new com.kingdee.bos.ctrl.swing.KDButton();
        this.conDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.conNumber.setName("conNumber");
        this.txtNumber.setName("txtNumber");
        this.conFdcCustomer.setName("conFdcCustomer");
        this.promFdcCustomer.setName("promFdcCustomer");
        this.conChooseDate.setName("conChooseDate");
        this.dateChooseDate.setName("dateChooseDate");
        this.conCreator.setName("conCreator");
        this.promCreator.setName("promCreator");
        this.btnYes.setName("btnYes");
        this.btnNo.setName("btnNo");
        this.conDescription.setName("conDescription");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        // CoreUI
        // conNumber		
        this.conNumber.setBoundLabelText(resHelper.getString("conNumber.boundLabelText"));		
        this.conNumber.setBoundLabelLength(100);		
        this.conNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // conFdcCustomer		
        this.conFdcCustomer.setBoundLabelText(resHelper.getString("conFdcCustomer.boundLabelText"));		
        this.conFdcCustomer.setBoundLabelLength(100);		
        this.conFdcCustomer.setBoundLabelUnderline(true);
        // promFdcCustomer		
        this.promFdcCustomer.setDisplayFormat("$name$");		
        this.promFdcCustomer.setEditFormat("$number$");		
        this.promFdcCustomer.setCommitFormat("$number$");		
        this.promFdcCustomer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerF7Query");		
        this.promFdcCustomer.setEnabledMultiSelection(true);
        // conChooseDate		
        this.conChooseDate.setBoundLabelText(resHelper.getString("conChooseDate.boundLabelText"));		
        this.conChooseDate.setBoundLabelLength(100);		
        this.conChooseDate.setBoundLabelUnderline(true);
        // dateChooseDate
        // conCreator		
        this.conCreator.setBoundLabelText(resHelper.getString("conCreator.boundLabelText"));		
        this.conCreator.setBoundLabelLength(100);		
        this.conCreator.setBoundLabelUnderline(true);
        // promCreator		
        this.promCreator.setDisplayFormat("$name$");		
        this.promCreator.setEditFormat("$number$");		
        this.promCreator.setCommitFormat("$number$");		
        this.promCreator.setQueryInfo("com.kingdee.eas.base.forewarn.UserQuery");
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
        // conDescription		
        this.conDescription.setBoundLabelText(resHelper.getString("conDescription.boundLabelText"));		
        this.conDescription.setBoundLabelLength(100);		
        this.conDescription.setBoundLabelUnderline(true);
        // kDScrollPane1
        // txtDescription		
        this.txtDescription.setMaxLength(255);
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
        this.setBounds(new Rectangle(10, 10, 300, 300));
        this.setLayout(null);
        conNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(conNumber, null);
        conFdcCustomer.setBounds(new Rectangle(10, 39, 270, 19));
        this.add(conFdcCustomer, null);
        conChooseDate.setBounds(new Rectangle(10, 69, 270, 19));
        this.add(conChooseDate, null);
        conCreator.setBounds(new Rectangle(10, 100, 270, 19));
        this.add(conCreator, null);
        btnYes.setBounds(new Rectangle(10, 264, 85, 21));
        this.add(btnYes, null);
        btnNo.setBounds(new Rectangle(187, 264, 85, 21));
        this.add(btnNo, null);
        conDescription.setBounds(new Rectangle(10, 130, 269, 119));
        this.add(conDescription, null);
        //conNumber
        conNumber.setBoundEditor(txtNumber);
        //conFdcCustomer
        conFdcCustomer.setBoundEditor(promFdcCustomer);
        //conChooseDate
        conChooseDate.setBoundEditor(dateChooseDate);
        //conCreator
        conCreator.setBoundEditor(promCreator);
        //conDescription
        conDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
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
        this.toolBar.add(btnPageSetup);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.ChooseRoomUIHandler";
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
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ChooseRoomUI");
    }




}