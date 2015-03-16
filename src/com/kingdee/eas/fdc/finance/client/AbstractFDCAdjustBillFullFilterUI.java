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
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractFDCAdjustBillFullFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCAdjustBillFullFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompany;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCompanySelect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPrjoect;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProject;
    protected com.kingdee.bos.ctrl.swing.KDButton btnProjectSelect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContract;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Contract;
    protected com.kingdee.bos.ctrl.swing.KDPanel plContractType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioContract;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioConWithoutText;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioConAll;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup conBtnGroup;
    /**
     * output class constructor
     */
    public AbstractFDCAdjustBillFullFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCAdjustBillFullFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnCompanySelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contPrjoect = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnProjectSelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contContract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Contract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.plContractType = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.radioContract = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioConWithoutText = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioConAll = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.conBtnGroup = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.contCompany.setName("contCompany");
        this.txtCompany.setName("txtCompany");
        this.btnCompanySelect.setName("btnCompanySelect");
        this.contPrjoect.setName("contPrjoect");
        this.txtProject.setName("txtProject");
        this.btnProjectSelect.setName("btnProjectSelect");
        this.contContract.setName("contContract");
        this.f7Contract.setName("f7Contract");
        this.plContractType.setName("plContractType");
        this.radioContract.setName("radioContract");
        this.radioConWithoutText.setName("radioConWithoutText");
        this.radioConAll.setName("radioConAll");
        // CustomerQueryPanel
        // contCompany		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.contCompany.setBoundLabelLength(100);		
        this.contCompany.setBoundLabelUnderline(true);
        // txtCompany		
        this.txtCompany.setEnabled(false);
        // btnCompanySelect		
        this.btnCompanySelect.setText(resHelper.getString("btnCompanySelect.text"));
        this.btnCompanySelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCompanySelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contPrjoect		
        this.contPrjoect.setBoundLabelText(resHelper.getString("contPrjoect.boundLabelText"));		
        this.contPrjoect.setBoundLabelLength(100);		
        this.contPrjoect.setBoundLabelUnderline(true);
        // txtProject		
        this.txtProject.setEnabled(false);
        // btnProjectSelect		
        this.btnProjectSelect.setText(resHelper.getString("btnProjectSelect.text"));
        this.btnProjectSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnProjectSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contContract		
        this.contContract.setBoundLabelText(resHelper.getString("contContract.boundLabelText"));		
        this.contContract.setBoundLabelLength(100);		
        this.contContract.setBoundLabelUnderline(true);
        // f7Contract		
        this.f7Contract.setEditable(true);		
        this.f7Contract.setEnabledMultiSelection(true);		
        this.f7Contract.setDisplayFormat("$name$");		
        this.f7Contract.setEditFormat("$number$");		
        this.f7Contract.setCommitFormat("$number$");
        // plContractType		
        this.plContractType.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("plContractType.border.title")));
        // radioContract		
        this.radioContract.setText(resHelper.getString("radioContract.text"));
        // radioConWithoutText		
        this.radioConWithoutText.setText(resHelper.getString("radioConWithoutText.text"));
        // radioConAll		
        this.radioConAll.setText(resHelper.getString("radioConAll.text"));
        // conBtnGroup
        this.conBtnGroup.add(this.radioContract);
        this.conBtnGroup.add(this.radioConWithoutText);
        this.conBtnGroup.add(this.radioConAll);
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
        this.setBounds(new Rectangle(10, 10, 380, 250));
        this.setLayout(null);
        contCompany.setBounds(new Rectangle(18, 10, 270, 21));
        this.add(contCompany, null);
        btnCompanySelect.setBounds(new Rectangle(294, 10, 69, 21));
        this.add(btnCompanySelect, null);
        contPrjoect.setBounds(new Rectangle(18, 38, 270, 19));
        this.add(contPrjoect, null);
        btnProjectSelect.setBounds(new Rectangle(294, 39, 69, 19));
        this.add(btnProjectSelect, null);
        contContract.setBounds(new Rectangle(18, 64, 270, 19));
        this.add(contContract, null);
        plContractType.setBounds(new Rectangle(18, 92, 128, 95));
        this.add(plContractType, null);
        //contCompany
        contCompany.setBoundEditor(txtCompany);
        //contPrjoect
        contPrjoect.setBoundEditor(txtProject);
        //contContract
        contContract.setBoundEditor(f7Contract);
        //plContractType
        plContractType.setLayout(null);        radioContract.setBounds(new Rectangle(24, 18, 64, 19));
        plContractType.add(radioContract, null);
        radioConWithoutText.setBounds(new Rectangle(24, 42, 80, 19));
        plContractType.add(radioConWithoutText, null);
        radioConAll.setBounds(new Rectangle(24, 65, 64, 19));
        plContractType.add(radioConAll, null);

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
	    return "com.kingdee.eas.fdc.finance.app.FDCAdjustBillFullFilterUIHandler";
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
     * output btnCompanySelect_actionPerformed method
     */
    protected void btnCompanySelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnProjectSelect_actionPerformed method
     */
    protected void btnProjectSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "FDCAdjustBillFullFilterUI");
    }




}