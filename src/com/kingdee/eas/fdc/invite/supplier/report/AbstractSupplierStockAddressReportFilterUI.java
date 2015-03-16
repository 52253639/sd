/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.report;

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
public abstract class AbstractSupplierStockAddressReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSupplierStockAddressReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox combIsAll;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsPass;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractorPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contManager;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPersonName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contManagerPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPersonPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combIsPass;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractorPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtManager;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPersonName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtManagerPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPersonPhone;
    /**
     * output class constructor
     */
    public AbstractSupplierStockAddressReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSupplierStockAddressReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.combIsAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contIsPass = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractorPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contManager = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPersonName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contManagerPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPersonPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.combIsPass = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtContractor = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractorPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtManager = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPersonName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtManagerPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPersonPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contName.setName("contName");
        this.combIsAll.setName("combIsAll");
        this.contIsPass.setName("contIsPass");
        this.contContractor.setName("contContractor");
        this.contContractorPhone.setName("contContractorPhone");
        this.contManager.setName("contManager");
        this.contPersonName.setName("contPersonName");
        this.contManagerPhone.setName("contManagerPhone");
        this.contPersonPhone.setName("contPersonPhone");
        this.txtName.setName("txtName");
        this.combIsPass.setName("combIsPass");
        this.txtContractor.setName("txtContractor");
        this.txtContractorPhone.setName("txtContractorPhone");
        this.txtManager.setName("txtManager");
        this.txtPersonName.setName("txtPersonName");
        this.txtManagerPhone.setName("txtManagerPhone");
        this.txtPersonPhone.setName("txtPersonPhone");
        // CustomerQueryPanel
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // combIsAll		
        this.combIsAll.setText(resHelper.getString("combIsAll.text"));
        this.combIsAll.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    combIsAll_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contIsPass		
        this.contIsPass.setBoundLabelText(resHelper.getString("contIsPass.boundLabelText"));		
        this.contIsPass.setBoundLabelLength(100);		
        this.contIsPass.setBoundLabelUnderline(true);
        // contContractor		
        this.contContractor.setBoundLabelText(resHelper.getString("contContractor.boundLabelText"));		
        this.contContractor.setBoundLabelLength(100);		
        this.contContractor.setBoundLabelUnderline(true);
        // contContractorPhone		
        this.contContractorPhone.setBoundLabelText(resHelper.getString("contContractorPhone.boundLabelText"));		
        this.contContractorPhone.setBoundLabelLength(100);		
        this.contContractorPhone.setBoundLabelUnderline(true);
        // contManager		
        this.contManager.setBoundLabelText(resHelper.getString("contManager.boundLabelText"));		
        this.contManager.setBoundLabelLength(100);		
        this.contManager.setBoundLabelUnderline(true);
        // contPersonName		
        this.contPersonName.setBoundLabelText(resHelper.getString("contPersonName.boundLabelText"));		
        this.contPersonName.setBoundLabelLength(100);		
        this.contPersonName.setBoundLabelUnderline(true);
        // contManagerPhone		
        this.contManagerPhone.setBoundLabelText(resHelper.getString("contManagerPhone.boundLabelText"));		
        this.contManagerPhone.setBoundLabelLength(100);		
        this.contManagerPhone.setBoundLabelUnderline(true);
        // contPersonPhone		
        this.contPersonPhone.setBoundLabelText(resHelper.getString("contPersonPhone.boundLabelText"));		
        this.contPersonPhone.setBoundLabelLength(100);		
        this.contPersonPhone.setBoundLabelUnderline(true);
        // txtName
        // combIsPass		
        this.combIsPass.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.supplier.IsGradeEnum").toArray());
        // txtContractor
        // txtContractorPhone
        // txtManager
        // txtPersonName
        // txtManagerPhone
        // txtPersonPhone
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
        this.setBounds(new Rectangle(10, 10, 600, 150));
        this.setLayout(null);
        contName.setBounds(new Rectangle(14, 10, 270, 19));
        this.add(contName, null);
        combIsAll.setBounds(new Rectangle(14, 100, 140, 19));
        this.add(combIsAll, null);
        contIsPass.setBounds(new Rectangle(14, 119, 270, 19));
        this.add(contIsPass, null);
        contContractor.setBounds(new Rectangle(14, 32, 270, 19));
        this.add(contContractor, null);
        contContractorPhone.setBounds(new Rectangle(319, 32, 270, 19));
        this.add(contContractorPhone, null);
        contManager.setBounds(new Rectangle(14, 54, 270, 19));
        this.add(contManager, null);
        contPersonName.setBounds(new Rectangle(14, 76, 270, 19));
        this.add(contPersonName, null);
        contManagerPhone.setBounds(new Rectangle(319, 54, 270, 19));
        this.add(contManagerPhone, null);
        contPersonPhone.setBounds(new Rectangle(319, 76, 270, 19));
        this.add(contPersonPhone, null);
        //contName
        contName.setBoundEditor(txtName);
        //contIsPass
        contIsPass.setBoundEditor(combIsPass);
        //contContractor
        contContractor.setBoundEditor(txtContractor);
        //contContractorPhone
        contContractorPhone.setBoundEditor(txtContractorPhone);
        //contManager
        contManager.setBoundEditor(txtManager);
        //contPersonName
        contPersonName.setBoundEditor(txtPersonName);
        //contManagerPhone
        contManagerPhone.setBoundEditor(txtManagerPhone);
        //contPersonPhone
        contPersonPhone.setBoundEditor(txtPersonPhone);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.report.SupplierStockAddressReportFilterUIHandler";
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
     * output combIsAll_itemStateChanged method
     */
    protected void combIsAll_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.report", "SupplierStockAddressReportFilterUI");
    }




}