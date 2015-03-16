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
public abstract class AbstractQuoteSetUI extends CoreUIObject
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractQuoteSetUI.class);
    protected ResourceBundleHelper resHelper = null;
    protected com.kingdee.bos.ctrl.swing.KDToolBar QuoteSetUI_toolbar;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox selectStand;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDComboBox selectPeopleDisplay;
    protected com.kingdee.bos.ctrl.swing.KDButton btnok;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtHighLimit;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLowLimit;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDComboColor colorLow;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel5;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel7;
    protected com.kingdee.bos.ctrl.swing.KDComboColor colorHigh;
    /**
     * output class constructor
     */
    public AbstractQuoteSetUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractQuoteSetUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.toolBar = new com.kingdee.bos.ctrl.swing.KDToolBar();
        this.menuBar = new com.kingdee.bos.ctrl.swing.KDMenuBar();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.selectStand = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.selectPeopleDisplay = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnok = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtHighLimit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtLowLimit = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.colorLow = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.kDLabel5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel7 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.colorHigh = new com.kingdee.bos.ctrl.swing.KDComboColor();
        this.setName("QuoteSetUI");
        this.toolBar.setName("QuoteSetUI_toolbar");
        this.menuBar.setName("QuoteSetUI_menubar");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.selectStand.setName("selectStand");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.selectPeopleDisplay.setName("selectPeopleDisplay");
        this.btnok.setName("btnok");
        this.kDPanel1.setName("kDPanel1");
        this.kDLabel1.setName("kDLabel1");
        this.txtHighLimit.setName("txtHighLimit");
        this.kDLabel2.setName("kDLabel2");
        this.txtLowLimit.setName("txtLowLimit");
        this.kDLabel3.setName("kDLabel3");
        this.kDLabel4.setName("kDLabel4");
        this.kDPanel2.setName("kDPanel2");
        this.colorLow.setName("colorLow");
        this.kDLabel5.setName("kDLabel5");
        this.kDLabel7.setName("kDLabel7");
        this.colorHigh.setName("colorHigh");
        // QuoteSetUI		
        this.setBorder(null);
        // QuoteSetUI_toolbar
        // QuoteSetUI_menubar
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(80);
        // selectStand		
        this.selectStand.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.QuoteStandEnum").toArray());
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(80);
        // selectPeopleDisplay		
        this.selectPeopleDisplay.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.QuotePeopleDisplayEnum").toArray());
        this.selectPeopleDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    selectPeopleDisplay_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnok		
        this.btnok.setText(resHelper.getString("btnok.text"));
        this.btnok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDButton1_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(192,192,192),1), resHelper.getString("kDPanel1.border.title")));
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // txtHighLimit		
        this.txtHighLimit.setText(resHelper.getString("txtHighLimit.text"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // txtLowLimit		
        this.txtLowLimit.setText(resHelper.getString("txtLowLimit.text"));
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(192,192,192),1), resHelper.getString("kDPanel2.border.title")));
        // colorLow
        this.colorLow.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    colorLow_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDLabel5		
        this.kDLabel5.setText(resHelper.getString("kDLabel5.text"));
        // kDLabel7		
        this.kDLabel7.setText(resHelper.getString("kDLabel7.text"));
        // colorHigh
        this.colorHigh.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    colorHigh_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
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
		list.add(this.toolBar);
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 400, 260));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(15, 13, 365, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(14, 45, 366, 19));
        this.add(kDLabelContainer2, null);
        btnok.setBounds(new Rectangle(303, 223, 75, 25));
        this.add(btnok, null);
        kDPanel1.setBounds(new Rectangle(8, 71, 378, 57));
        this.add(kDPanel1, null);
        kDPanel2.setBounds(new Rectangle(8, 132, 378, 84));
        this.add(kDPanel2, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(selectStand);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(selectPeopleDisplay);
        //kDPanel1
        kDPanel1.setLayout(null);        kDLabel1.setBounds(new Rectangle(13, 20, 33, 24));
        kDPanel1.add(kDLabel1, null);
        txtHighLimit.setBounds(new Rectangle(42, 21, 61, 22));
        kDPanel1.add(txtHighLimit, null);
        kDLabel2.setBounds(new Rectangle(128, 20, 33, 24));
        kDPanel1.add(kDLabel2, null);
        txtLowLimit.setBounds(new Rectangle(156, 21, 61, 22));
        kDPanel1.add(txtLowLimit, null);
        kDLabel3.setBounds(new Rectangle(104, 21, 15, 24));
        kDPanel1.add(kDLabel3, null);
        kDLabel4.setBounds(new Rectangle(218, 20, 15, 24));
        kDPanel1.add(kDLabel4, null);
        //kDPanel2
        kDPanel2.setLayout(null);        colorLow.setBounds(new Rectangle(59, 16, 40, 20));
        kDPanel2.add(colorLow, null);
        kDLabel5.setBounds(new Rectangle(15, 23, 31, 17));
        kDPanel2.add(kDLabel5, null);
        kDLabel7.setBounds(new Rectangle(15, 51, 31, 17));
        kDPanel2.add(kDLabel7, null);
        colorHigh.setBounds(new Rectangle(59, 46, 40, 20));
        kDPanel2.add(colorHigh, null);

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
	    return "com.kingdee.eas.fdc.invite.app.QuoteSetUIHandler";
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
     * output selectPeopleDisplay_actionPerformed method
     */
    protected void selectPeopleDisplay_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kDButton1_actionPerformed method
     */
    protected void kDButton1_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output colorLow_dataChanged method
     */
    protected void colorLow_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output colorHigh_dataChanged method
     */
    protected void colorHigh_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
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
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "QuoteSetUI");
    }




}