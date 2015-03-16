/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

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
public abstract class AbstractSHERevBillFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSHERevBillFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isGenerate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox unGenerate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isSubmit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isAudit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox gathering;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox transfer;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox refundment;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker bizDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker bizDateTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox moneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoom;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCustomer;
    /**
     * output class constructor
     */
    public AbstractSHERevBillFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSHERevBillFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.isGenerate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.unGenerate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.isSubmit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.isAudit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.gathering = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.transfer = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.refundment = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.bizDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.bizDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.moneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRoom = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCustomer = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.isGenerate.setName("isGenerate");
        this.unGenerate.setName("unGenerate");
        this.isSubmit.setName("isSubmit");
        this.isAudit.setName("isAudit");
        this.gathering.setName("gathering");
        this.transfer.setName("transfer");
        this.refundment.setName("refundment");
        this.bizDateFrom.setName("bizDateFrom");
        this.bizDateTo.setName("bizDateTo");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.moneyDefine.setName("moneyDefine");
        this.contRoom.setName("contRoom");
        this.contCustomer.setName("contCustomer");
        this.txtRoom.setName("txtRoom");
        this.txtCustomer.setName("txtCustomer");
        // CustomerQueryPanel
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(120);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(120);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelLength(120);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(120);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(120);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // isGenerate		
        this.isGenerate.setText(resHelper.getString("isGenerate.text"));
        // unGenerate		
        this.unGenerate.setText(resHelper.getString("unGenerate.text"));
        // isSubmit		
        this.isSubmit.setText(resHelper.getString("isSubmit.text"));
        // isAudit		
        this.isAudit.setText(resHelper.getString("isAudit.text"));
        // gathering		
        this.gathering.setText(resHelper.getString("gathering.text"));
        // transfer		
        this.transfer.setText(resHelper.getString("transfer.text"));
        // refundment		
        this.refundment.setText(resHelper.getString("refundment.text"));
        // bizDateFrom
        // bizDateTo
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setBoundLabelLength(120);
        // moneyDefine
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);
        // txtRoom
        // txtCustomer
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
        this.setBounds(new Rectangle(10, 10, 450, 230));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(14, 80, 67, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(14, 114, 124, 19));
        this.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(14, 182, 124, 19));
        this.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(14, 148, 124, 19));
        this.add(kDLabelContainer4, null);
        kDLabelContainer5.setBounds(new Rectangle(326, 204, 124, 19));
        this.add(kDLabelContainer5, null);
        isGenerate.setBounds(new Rectangle(134, 148, 69, 19));
        this.add(isGenerate, null);
        unGenerate.setBounds(new Rectangle(211, 148, 69, 19));
        this.add(unGenerate, null);
        isSubmit.setBounds(new Rectangle(134, 114, 69, 19));
        this.add(isSubmit, null);
        isAudit.setBounds(new Rectangle(211, 114, 69, 19));
        this.add(isAudit, null);
        gathering.setBounds(new Rectangle(134, 182, 61, 19));
        this.add(gathering, null);
        transfer.setBounds(new Rectangle(288, 182, 61, 19));
        this.add(transfer, null);
        refundment.setBounds(new Rectangle(211, 182, 61, 19));
        this.add(refundment, null);
        bizDateFrom.setBounds(new Rectangle(81, 80, 122, 19));
        this.add(bizDateFrom, null);
        bizDateTo.setBounds(new Rectangle(246, 80, 122, 19));
        this.add(bizDateTo, null);
        kDLabelContainer6.setBounds(new Rectangle(209, 80, 35, 19));
        this.add(kDLabelContainer6, null);
        moneyDefine.setBounds(new Rectangle(329, 198, 188, 19));
        this.add(moneyDefine, null);
        contRoom.setBounds(new Rectangle(14, 12, 270, 19));
        this.add(contRoom, null);
        contCustomer.setBounds(new Rectangle(14, 46, 270, 19));
        this.add(contCustomer, null);
        //contRoom
        contRoom.setBoundEditor(txtRoom);
        //contCustomer
        contCustomer.setBoundEditor(txtCustomer);

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
	    return "com.kingdee.eas.fdc.basecrm.app.SHERevBillFilterUIHandler";
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
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basecrm.client", "SHERevBillFilterUI");
    }




}