/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

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
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractPaymentBillFullFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPaymentBillFullFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContract;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompany;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayeeUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Contract;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PayeeUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateTo;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDateTo;
    protected com.kingdee.bos.ctrl.swing.KDPanel plPaymentBillState;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioSave;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioSubmit;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioAuditing;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioAudited;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioStateAll;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCompanySelect;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProjectSelect;

    /**
     * output class constructor
     */
    public AbstractPaymentBillFullFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPaymentBillFullFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contPayeeUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Contract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7PayeeUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.plPaymentBillState = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDButtonGroup = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.radioSave = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioSubmit = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioAuditing = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioAudited = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioStateAll = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.btnCompanySelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnProjectSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCompany.setName("contCompany");
        this.contProject.setName("contProject");
        this.contContract.setName("contContract");
        this.txtCompany.setName("txtCompany");
        this.txtProject.setName("txtProject");
        this.contPayeeUnit.setName("contPayeeUnit");
        this.f7Contract.setName("f7Contract");
        this.f7PayeeUnit.setName("f7PayeeUnit");
        this.contDateFrom.setName("contDateFrom");
        this.pkDateFrom.setName("pkDateFrom");
        this.contDateTo.setName("contDateTo");
        this.pkDateTo.setName("pkDateTo");
        this.plPaymentBillState.setName("plPaymentBillState");
        this.radioSave.setName("radioSave");
        this.radioSubmit.setName("radioSubmit");
        this.radioAuditing.setName("radioAuditing");
        this.radioAudited.setName("radioAudited");
        this.radioStateAll.setName("radioStateAll");
        this.btnCompanySelect.setName("btnCompanySelect");
        this.btnProjectSelect.setName("btnProjectSelect");
        // CustomerQueryPanel
        // contCompany
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));
        this.contCompany.setBoundLabelLength(100);
        // contProject
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));
        this.contProject.setBoundLabelLength(100);
        // contContract
        this.contContract.setBoundLabelText(resHelper.getString("contContract.boundLabelText"));
        this.contContract.setBoundLabelLength(100);
        // txtCompany
        // txtProject
        // contPayeeUnit
        this.contPayeeUnit.setBoundLabelText(resHelper.getString("contPayeeUnit.boundLabelText"));
        this.contPayeeUnit.setBoundLabelLength(100);
        // f7Contract
        this.f7Contract.setDisplayFormat("$name$");
        this.f7Contract.setEditFormat("$number$");
        this.f7Contract.setCommitFormat("$number$");
        // f7PayeeUnit
        this.f7PayeeUnit.setCommitFormat("$number$");
        this.f7PayeeUnit.setDisplayFormat("$name$");
        this.f7PayeeUnit.setEditFormat("$number$");
        // contDateFrom
        this.contDateFrom.setBoundLabelText(resHelper.getString("contDateFrom.boundLabelText"));
        this.contDateFrom.setBoundLabelLength(100);
        // pkDateFrom
        // contDateTo
        this.contDateTo.setBoundLabelText(resHelper.getString("contDateTo.boundLabelText"));
        this.contDateTo.setBoundLabelLength(100);
        // pkDateTo
        // plPaymentBillState
        this.plPaymentBillState.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("plPaymentBillState.border.title")));
        // kDButtonGroup
        this.kDButtonGroup.add(this.radioSave);
        this.kDButtonGroup.add(this.radioSubmit);
        this.kDButtonGroup.add(this.radioAuditing);
        this.kDButtonGroup.add(this.radioAudited);
        this.kDButtonGroup.add(this.radioStateAll);
        // radioSave
        this.radioSave.setText(resHelper.getString("radioSave.text"));
        // radioSubmit
        this.radioSubmit.setText(resHelper.getString("radioSubmit.text"));
        // radioAuditing
        this.radioAuditing.setText(resHelper.getString("radioAuditing.text"));
        // radioAudited
        this.radioAudited.setText(resHelper.getString("radioAudited.text"));
        // radioStateAll
        this.radioStateAll.setText(resHelper.getString("radioStateAll.text"));
        // btnCompanySelect
        this.btnCompanySelect.setText(resHelper.getString("btnCompanySelect.text"));
        this.btnCompanySelect.setMargin(new java.awt.Insets(2,14,2,14));
        this.btnCompanySelect.setOpaque(true);
        this.btnCompanySelect.setBackground(new java.awt.Color(212,208,200));
        // btnProjectSelect
        this.btnProjectSelect.setText(resHelper.getString("btnProjectSelect.text"));
        this.btnProjectSelect.setBackground(new java.awt.Color(212,208,200));
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 400, 400));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 400, 400));
        contCompany.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contCompany, new KDLayout.Constraints(10, 10, 270, 19, 0));
        contProject.setBounds(new Rectangle(10, 32, 270, 19));
        this.add(contProject, new KDLayout.Constraints(10, 32, 270, 19, 0));
        contContract.setBounds(new Rectangle(10, 54, 270, 19));
        this.add(contContract, new KDLayout.Constraints(10, 54, 270, 19, 0));
        contPayeeUnit.setBounds(new Rectangle(10, 76, 270, 19));
        this.add(contPayeeUnit, new KDLayout.Constraints(10, 76, 270, 19, 0));
        contDateFrom.setBounds(new Rectangle(10, 98, 270, 19));
        this.add(contDateFrom, new KDLayout.Constraints(10, 98, 270, 19, 0));
        contDateTo.setBounds(new Rectangle(10, 120, 270, 19));
        this.add(contDateTo, new KDLayout.Constraints(10, 120, 270, 19, 0));
        plPaymentBillState.setBounds(new Rectangle(10, 150, 111, 146));
        this.add(plPaymentBillState, new KDLayout.Constraints(10, 150, 111, 146, 0));
        btnCompanySelect.setBounds(new Rectangle(285, 10, 60, 19));
        this.add(btnCompanySelect, new KDLayout.Constraints(285, 10, 60, 19, 0));
        btnProjectSelect.setBounds(new Rectangle(285, 32, 60, 19));
        this.add(btnProjectSelect, new KDLayout.Constraints(285, 32, 60, 19, 0));
        //contCompany
        contCompany.setBoundEditor(txtCompany);
        //contProject
        contProject.setBoundEditor(txtProject);
        //contContract
        contContract.setBoundEditor(f7Contract);
        //contPayeeUnit
        contPayeeUnit.setBoundEditor(f7PayeeUnit);
        //contDateFrom
        contDateFrom.setBoundEditor(pkDateFrom);
        //contDateTo
        contDateTo.setBoundEditor(pkDateTo);
        //plPaymentBillState
        plPaymentBillState.setLayout(null);        radioSave.setBounds(new Rectangle(24, 18, 64, 19));
        plPaymentBillState.add(radioSave, null);
        radioSubmit.setBounds(new Rectangle(24, 42, 64, 19));
        plPaymentBillState.add(radioSubmit, null);
        radioAuditing.setBounds(new Rectangle(24, 65, 64, 19));
        plPaymentBillState.add(radioAuditing, null);
        radioAudited.setBounds(new Rectangle(24, 90, 64, 19));
        plPaymentBillState.add(radioAudited, null);
        radioStateAll.setBounds(new Rectangle(24, 115, 64, 19));
        plPaymentBillState.add(radioStateAll, null);

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
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "PaymentBillFullFilterUI");
    }




}