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
public abstract class AbstractFDCCustomerFullFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCCustomerFullFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkAllCustomer;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkBlankOut;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToBookedDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpToBookedDate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkImportant;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBookedDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpBookedDate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkShare;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCustomerType;
    /**
     * output class constructor
     */
    public AbstractFDCCustomerFullFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCCustomerFullFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.chkAllCustomer = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkBlankOut = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contToBookedDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dpToBookedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.chkImportant = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contBookedDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.dpBookedDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.chkShare = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contCustomerType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboCustomerType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.chkAllCustomer.setName("chkAllCustomer");
        this.chkBlankOut.setName("chkBlankOut");
        this.contToBookedDate.setName("contToBookedDate");
        this.dpToBookedDate.setName("dpToBookedDate");
        this.chkImportant.setName("chkImportant");
        this.contBookedDate.setName("contBookedDate");
        this.dpBookedDate.setName("dpBookedDate");
        this.chkShare.setName("chkShare");
        this.contCustomerType.setName("contCustomerType");
        this.comboCustomerType.setName("comboCustomerType");
        // CustomerQueryPanel		
        this.setPreferredSize(new Dimension(400,400));
        // chkAllCustomer		
        this.chkAllCustomer.setText(resHelper.getString("chkAllCustomer.text"));
        this.chkAllCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkAllCustomer_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkBlankOut		
        this.chkBlankOut.setText(resHelper.getString("chkBlankOut.text"));
        // contToBookedDate		
        this.contToBookedDate.setBoundLabelText(resHelper.getString("contToBookedDate.boundLabelText"));		
        this.contToBookedDate.setBoundLabelUnderline(true);		
        this.contToBookedDate.setBoundLabelLength(100);
        // dpToBookedDate
        // chkImportant		
        this.chkImportant.setText(resHelper.getString("chkImportant.text"));
        // contBookedDate		
        this.contBookedDate.setBoundLabelText(resHelper.getString("contBookedDate.boundLabelText"));		
        this.contBookedDate.setBoundLabelLength(100);		
        this.contBookedDate.setBoundLabelUnderline(true);
        // dpBookedDate
        // chkShare		
        this.chkShare.setText(resHelper.getString("chkShare.text"));
        // contCustomerType		
        this.contCustomerType.setBoundLabelText(resHelper.getString("contCustomerType.boundLabelText"));		
        this.contCustomerType.setBoundLabelLength(100);		
        this.contCustomerType.setBoundLabelUnderline(true);
        // comboCustomerType		
        this.comboCustomerType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum").toArray());		
        this.comboCustomerType.setSelectedIndex(0);
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
        this.setBounds(new Rectangle(10, 10, 300, 220));
        this.setLayout(null);
        chkAllCustomer.setBounds(new Rectangle(10, 183, 264, 19));
        this.add(chkAllCustomer, null);
        chkBlankOut.setBounds(new Rectangle(10, 130, 265, 19));
        this.add(chkBlankOut, null);
        contToBookedDate.setBounds(new Rectangle(10, 37, 270, 19));
        this.add(contToBookedDate, null);
        chkImportant.setBounds(new Rectangle(10, 102, 271, 19));
        this.add(chkImportant, null);
        contBookedDate.setBounds(new Rectangle(10, 8, 270, 19));
        this.add(contBookedDate, null);
        chkShare.setBounds(new Rectangle(10, 158, 221, 19));
        this.add(chkShare, null);
        contCustomerType.setBounds(new Rectangle(10, 68, 270, 19));
        this.add(contCustomerType, null);
        //contToBookedDate
        contToBookedDate.setBoundEditor(dpToBookedDate);
        //contBookedDate
        contBookedDate.setBoundEditor(dpBookedDate);
        //contCustomerType
        contCustomerType.setBoundEditor(comboCustomerType);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.FDCCustomerFullFilterUIHandler";
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
     * output chkAllCustomer_actionPerformed method
     */
    protected void chkAllCustomer_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "FDCCustomerFullFilterUI");
    }




}