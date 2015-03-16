/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

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
public abstract class AbstractVersionInputUI extends CoreUIObject
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractVersionInputUI.class);
    protected ResourceBundleHelper resHelper = null;
    protected com.kingdee.bos.ctrl.swing.KDToolBar VersionInputUI_toolbar;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRecenseDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersionNumner;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersionName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkRecenseDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVersionNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVersionName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDButton btnCancel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMeasureStage;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboMeasureStage;
    /**
     * output class constructor
     */
    public AbstractVersionInputUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractVersionInputUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.toolBar = new com.kingdee.bos.ctrl.swing.KDToolBar();
        this.menuBar = new com.kingdee.bos.ctrl.swing.KDMenuBar();
        this.contRecenseDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVersionNumner = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVersionName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkRecenseDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtVersionNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtVersionName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contMeasureStage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboMeasureStage = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.setName("VersionInputUI");
        this.toolBar.setName("VersionInputUI_toolbar");
        this.menuBar.setName("VersionInputUI_menubar");
        this.contRecenseDate.setName("contRecenseDate");
        this.contVersionNumner.setName("contVersionNumner");
        this.contVersionName.setName("contVersionName");
        this.contDescription.setName("contDescription");
        this.pkRecenseDate.setName("pkRecenseDate");
        this.txtVersionNumber.setName("txtVersionNumber");
        this.txtVersionName.setName("txtVersionName");
        this.txtDescription.setName("txtDescription");
        this.btnConfirm.setName("btnConfirm");
        this.btnCancel.setName("btnCancel");
        this.contMeasureStage.setName("contMeasureStage");
        this.comboMeasureStage.setName("comboMeasureStage");
        // VersionInputUI
        // VersionInputUI_toolbar
        // VersionInputUI_menubar
        // contRecenseDate		
        this.contRecenseDate.setBoundLabelText(resHelper.getString("contRecenseDate.boundLabelText"));		
        this.contRecenseDate.setBoundLabelLength(100);		
        this.contRecenseDate.setBoundLabelUnderline(true);
        // contVersionNumner		
        this.contVersionNumner.setBoundLabelText(resHelper.getString("contVersionNumner.boundLabelText"));		
        this.contVersionNumner.setBoundLabelLength(100);		
        this.contVersionNumner.setBoundLabelUnderline(true);
        // contVersionName		
        this.contVersionName.setBoundLabelText(resHelper.getString("contVersionName.boundLabelText"));		
        this.contVersionName.setBoundLabelLength(100);		
        this.contVersionName.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // pkRecenseDate		
        this.pkRecenseDate.setEnabled(false);
        // txtVersionNumber		
        this.txtVersionNumber.setEnabled(false);
        // txtVersionName		
        this.txtVersionName.setRequired(true);
        // txtDescription
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
        // contMeasureStage		
        this.contMeasureStage.setBoundLabelText(resHelper.getString("contMeasureStage.boundLabelText"));		
        this.contMeasureStage.setBoundLabelLength(100);		
        this.contMeasureStage.setBoundLabelUnderline(true);
        // comboMeasureStage		
        this.comboMeasureStage.setEnabled(false);
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
		list.add(this.toolBar);
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 270, 200));
        this.setLayout(null);
        contRecenseDate.setBounds(new Rectangle(14, 13, 235, 19));
        this.add(contRecenseDate, null);
        contVersionNumner.setBounds(new Rectangle(14, 39, 235, 19));
        this.add(contVersionNumner, null);
        contVersionName.setBounds(new Rectangle(14, 70, 235, 19));
        this.add(contVersionName, null);
        contDescription.setBounds(new Rectangle(14, 102, 235, 19));
        this.add(contDescription, null);
        btnConfirm.setBounds(new Rectangle(51, 163, 70, 26));
        this.add(btnConfirm, null);
        btnCancel.setBounds(new Rectangle(139, 163, 70, 26));
        this.add(btnCancel, null);
        contMeasureStage.setBounds(new Rectangle(14, 133, 235, 19));
        this.add(contMeasureStage, null);
        //contRecenseDate
        contRecenseDate.setBoundEditor(pkRecenseDate);
        //contVersionNumner
        contVersionNumner.setBoundEditor(txtVersionNumber);
        //contVersionName
        contVersionName.setBoundEditor(txtVersionName);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contMeasureStage
        contMeasureStage.setBoundEditor(comboMeasureStage);

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
	    return "com.kingdee.eas.fdc.aimcost.app.VersionInputUIHandler";
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
     * output btnConfirm_actionPerformed method
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnCancel_actionPerformed method
     */
    protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        return new MetaDataPK("com.kingdee.eas.fdc.aimcost.client", "VersionInputUI");
    }




}