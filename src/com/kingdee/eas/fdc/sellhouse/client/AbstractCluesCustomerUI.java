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
public abstract class AbstractCluesCustomerUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCluesCustomerUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCertificateNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFirstLinkMan;
    protected com.kingdee.bos.ctrl.swing.KDButton btnOK;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCancel;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCusType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCusName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCertificateNum;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFirstLinkman;
    /**
     * output class constructor
     */
    public AbstractCluesCustomerUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCluesCustomerUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCustomerType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCertificateNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFirstLinkMan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnOK = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDButton();
        this.comboCusType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtCusName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCertificateNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtFirstLinkman = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCustomerType.setName("contCustomerType");
        this.contName.setName("contName");
        this.contPhone.setName("contPhone");
        this.contCertificateNumber.setName("contCertificateNumber");
        this.contFirstLinkMan.setName("contFirstLinkMan");
        this.btnOK.setName("btnOK");
        this.btnCancel.setName("btnCancel");
        this.comboCusType.setName("comboCusType");
        this.txtCusName.setName("txtCusName");
        this.txtPhone.setName("txtPhone");
        this.txtCertificateNum.setName("txtCertificateNum");
        this.txtFirstLinkman.setName("txtFirstLinkman");
        // CoreUI
        // contCustomerType		
        this.contCustomerType.setBoundLabelText(resHelper.getString("contCustomerType.boundLabelText"));		
        this.contCustomerType.setBoundLabelLength(100);		
        this.contCustomerType.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contPhone		
        this.contPhone.setBoundLabelText(resHelper.getString("contPhone.boundLabelText"));		
        this.contPhone.setBoundLabelLength(100);		
        this.contPhone.setBoundLabelUnderline(true);
        // contCertificateNumber		
        this.contCertificateNumber.setBoundLabelText(resHelper.getString("contCertificateNumber.boundLabelText"));		
        this.contCertificateNumber.setBoundLabelLength(100);		
        this.contCertificateNumber.setBoundLabelUnderline(true);
        // contFirstLinkMan		
        this.contFirstLinkMan.setBoundLabelText(resHelper.getString("contFirstLinkMan.boundLabelText"));		
        this.contFirstLinkMan.setBoundLabelLength(100);		
        this.contFirstLinkMan.setBoundLabelUnderline(true);
        // btnOK		
        this.btnOK.setText(resHelper.getString("btnOK.text"));
        this.btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnOK_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnCancel		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));
        this.btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCancel_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // comboCusType
        this.comboCusType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboCusType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtCusName		
        this.txtCusName.setMaxLength(80);
        // txtPhone		
        this.txtPhone.setMaxLength(80);		
        this.txtPhone.setEnabled(false);
        // txtCertificateNum		
        this.txtCertificateNum.setMaxLength(80);
        // txtFirstLinkman		
        this.txtFirstLinkman.setMaxLength(80);		
        this.txtFirstLinkman.setRequired(true);
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
        this.setBounds(new Rectangle(10, 10, 350, 300));
        this.setLayout(null);
        contCustomerType.setBounds(new Rectangle(30, 30, 270, 19));
        this.add(contCustomerType, null);
        contName.setBounds(new Rectangle(30, 70, 270, 19));
        this.add(contName, null);
        contPhone.setBounds(new Rectangle(30, 110, 270, 19));
        this.add(contPhone, null);
        contCertificateNumber.setBounds(new Rectangle(30, 150, 270, 19));
        this.add(contCertificateNumber, null);
        contFirstLinkMan.setBounds(new Rectangle(30, 190, 270, 19));
        this.add(contFirstLinkMan, null);
        btnOK.setBounds(new Rectangle(115, 239, 73, 21));
        this.add(btnOK, null);
        btnCancel.setBounds(new Rectangle(225, 239, 73, 21));
        this.add(btnCancel, null);
        //contCustomerType
        contCustomerType.setBoundEditor(comboCusType);
        //contName
        contName.setBoundEditor(txtCusName);
        //contPhone
        contPhone.setBoundEditor(txtPhone);
        //contCertificateNumber
        contCertificateNumber.setBoundEditor(txtCertificateNum);
        //contFirstLinkMan
        contFirstLinkMan.setBoundEditor(txtFirstLinkman);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.CluesCustomerUIHandler";
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
     * output btnOK_actionPerformed method
     */
    protected void btnOK_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnCancel_actionPerformed method
     */
    protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboCusType_itemStateChanged method
     */
    protected void comboCusType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CluesCustomerUI");
    }




}