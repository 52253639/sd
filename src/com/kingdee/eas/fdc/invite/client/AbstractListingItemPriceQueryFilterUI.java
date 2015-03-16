/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

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
public abstract class AbstractListingItemPriceQueryFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractListingItemPriceQueryFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainerOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainerHeadType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainerDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainerDateTo;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDateTo;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Org;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7HeadType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainerItemName;
    protected com.kingdee.bos.ctrl.swing.KDTextField kDTextFieldItemName;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBoxHeadTypeNull;
    /**
     * output class constructor
     */
    public AbstractListingItemPriceQueryFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractListingItemPriceQueryFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainerOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainerHeadType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainerDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainerDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Org = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7HeadType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainerItemName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTextFieldItemName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDCheckBoxHeadTypeNull = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainerOrg.setName("kDLabelContainerOrg");
        this.kDLabelContainerHeadType.setName("kDLabelContainerHeadType");
        this.kDLabelContainerDateFrom.setName("kDLabelContainerDateFrom");
        this.pkDateFrom.setName("pkDateFrom");
        this.kDLabelContainerDateTo.setName("kDLabelContainerDateTo");
        this.pkDateTo.setName("pkDateTo");
        this.f7Org.setName("f7Org");
        this.f7HeadType.setName("f7HeadType");
        this.kDLabelContainerItemName.setName("kDLabelContainerItemName");
        this.kDTextFieldItemName.setName("kDTextFieldItemName");
        this.kDLabel1.setName("kDLabel1");
        this.kDCheckBoxHeadTypeNull.setName("kDCheckBoxHeadTypeNull");
        // CustomerQueryPanel
        // kDLabelContainerOrg		
        this.kDLabelContainerOrg.setBoundLabelText(resHelper.getString("kDLabelContainerOrg.boundLabelText"));		
        this.kDLabelContainerOrg.setBoundLabelLength(60);
        // kDLabelContainerHeadType		
        this.kDLabelContainerHeadType.setBoundLabelText(resHelper.getString("kDLabelContainerHeadType.boundLabelText"));		
        this.kDLabelContainerHeadType.setBoundLabelLength(60);
        // kDLabelContainerDateFrom		
        this.kDLabelContainerDateFrom.setBoundLabelText(resHelper.getString("kDLabelContainerDateFrom.boundLabelText"));		
        this.kDLabelContainerDateFrom.setBoundLabelLength(60);
        // pkDateFrom
        // kDLabelContainerDateTo		
        this.kDLabelContainerDateTo.setBoundLabelText(resHelper.getString("kDLabelContainerDateTo.boundLabelText"));		
        this.kDLabelContainerDateTo.setBoundLabelLength(60);
        // pkDateTo
        // f7Org
        // f7HeadType		
        this.f7HeadType.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7HeadTypeQuery");
        this.f7HeadType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7HeadType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDLabelContainerItemName		
        this.kDLabelContainerItemName.setBoundLabelText(resHelper.getString("kDLabelContainerItemName.boundLabelText"));		
        this.kDLabelContainerItemName.setBoundLabelLength(60);
        // kDTextFieldItemName
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDCheckBoxHeadTypeNull		
        this.kDCheckBoxHeadTypeNull.setText(resHelper.getString("kDCheckBoxHeadTypeNull.text"));
        this.kDCheckBoxHeadTypeNull.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDCheckBox1_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
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
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(null);
        kDLabelContainerOrg.setBounds(new Rectangle(42, 65, 250, 19));
        this.add(kDLabelContainerOrg, null);
        kDLabelContainerHeadType.setBounds(new Rectangle(42, 30, 250, 19));
        this.add(kDLabelContainerHeadType, null);
        kDLabelContainerDateFrom.setBounds(new Rectangle(42, 135, 250, 19));
        this.add(kDLabelContainerDateFrom, null);
        kDLabelContainerDateTo.setBounds(new Rectangle(42, 172, 250, 19));
        this.add(kDLabelContainerDateTo, null);
        kDLabelContainerItemName.setBounds(new Rectangle(42, 100, 250, 19));
        this.add(kDLabelContainerItemName, null);
        kDLabel1.setBounds(new Rectangle(42, 239, 270, 19));
        this.add(kDLabel1, null);
        kDCheckBoxHeadTypeNull.setBounds(new Rectangle(42, 215, 242, 19));
        this.add(kDCheckBoxHeadTypeNull, null);
        //kDLabelContainerOrg
        kDLabelContainerOrg.setBoundEditor(f7Org);
        //kDLabelContainerHeadType
        kDLabelContainerHeadType.setBoundEditor(f7HeadType);
        //kDLabelContainerDateFrom
        kDLabelContainerDateFrom.setBoundEditor(pkDateFrom);
        //kDLabelContainerDateTo
        kDLabelContainerDateTo.setBoundEditor(pkDateTo);
        //kDLabelContainerItemName
        kDLabelContainerItemName.setBoundEditor(kDTextFieldItemName);

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
	    return "com.kingdee.eas.fdc.invite.app.ListingItemPriceQueryFilterUIHandler";
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
     * output f7HeadType_dataChanged method
     */
    protected void f7HeadType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kDCheckBox1_actionPerformed method
     */
    protected void kDCheckBox1_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "ListingItemPriceQueryFilterUI");
    }




}