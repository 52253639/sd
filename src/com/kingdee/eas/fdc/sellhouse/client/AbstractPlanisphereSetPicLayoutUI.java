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
public abstract class AbstractPlanisphereSetPicLayoutUI extends CoreUIObject
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPlanisphereSetPicLayoutUI.class);
    protected ResourceBundleHelper resHelper = null;
    protected com.kingdee.bos.ctrl.swing.KDToolBar PlanisphereSetPicLayoutUI_toolbar;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCellHeigth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCellWidth;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCellHorizCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCellVertiCount;
    protected com.kingdee.bos.ctrl.swing.KDSpinner kDSpinCellHeight;
    protected com.kingdee.bos.ctrl.swing.KDSpinner kDSpinCellWidth;
    protected com.kingdee.bos.ctrl.swing.KDSpinner kDSpinCellHorizCount;
    protected com.kingdee.bos.ctrl.swing.KDSpinner kDSpinCellVertiCount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSubmit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancel;
    /**
     * output class constructor
     */
    public AbstractPlanisphereSetPicLayoutUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPlanisphereSetPicLayoutUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.toolBar = new com.kingdee.bos.ctrl.swing.KDToolBar();
        this.menuBar = new com.kingdee.bos.ctrl.swing.KDMenuBar();
        this.contCellHeigth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCellWidth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCellHorizCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCellVertiCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSpinCellHeight = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDSpinCellWidth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDSpinCellHorizCount = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDSpinCellVertiCount = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.btnSubmit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.setName("PlanisphereSetPicLayoutUI");
        this.toolBar.setName("PlanisphereSetPicLayoutUI_toolbar");
        this.menuBar.setName("PlanisphereSetPicLayoutUI_menubar");
        this.contCellHeigth.setName("contCellHeigth");
        this.contCellWidth.setName("contCellWidth");
        this.contCellHorizCount.setName("contCellHorizCount");
        this.contCellVertiCount.setName("contCellVertiCount");
        this.kDSpinCellHeight.setName("kDSpinCellHeight");
        this.kDSpinCellWidth.setName("kDSpinCellWidth");
        this.kDSpinCellHorizCount.setName("kDSpinCellHorizCount");
        this.kDSpinCellVertiCount.setName("kDSpinCellVertiCount");
        this.btnSubmit.setName("btnSubmit");
        this.btnCancel.setName("btnCancel");
        // PlanisphereSetPicLayoutUI
        // PlanisphereSetPicLayoutUI_toolbar
        // PlanisphereSetPicLayoutUI_menubar
        // contCellHeigth		
        this.contCellHeigth.setBoundLabelText(resHelper.getString("contCellHeigth.boundLabelText"));		
        this.contCellHeigth.setBoundLabelLength(100);		
        this.contCellHeigth.setBoundLabelUnderline(true);
        // contCellWidth		
        this.contCellWidth.setBoundLabelText(resHelper.getString("contCellWidth.boundLabelText"));		
        this.contCellWidth.setBoundLabelLength(100);		
        this.contCellWidth.setBoundLabelUnderline(true);
        // contCellHorizCount		
        this.contCellHorizCount.setBoundLabelText(resHelper.getString("contCellHorizCount.boundLabelText"));		
        this.contCellHorizCount.setBoundLabelLength(100);		
        this.contCellHorizCount.setBoundLabelUnderline(true);
        // contCellVertiCount		
        this.contCellVertiCount.setBoundLabelText(resHelper.getString("contCellVertiCount.boundLabelText"));		
        this.contCellVertiCount.setBoundLabelLength(100);		
        this.contCellVertiCount.setBoundLabelUnderline(true);
        // kDSpinCellHeight
        // kDSpinCellWidth
        // kDSpinCellHorizCount
        // kDSpinCellVertiCount
        // btnSubmit		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));
        this.btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSubmit_actionPerformed(e);
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
        this.setBounds(new Rectangle(10, 10, 300, 200));
        this.setLayout(null);
        contCellHeigth.setBounds(new Rectangle(46, 15, 203, 19));
        this.add(contCellHeigth, null);
        contCellWidth.setBounds(new Rectangle(46, 43, 203, 19));
        this.add(contCellWidth, null);
        contCellHorizCount.setBounds(new Rectangle(46, 71, 203, 19));
        this.add(contCellHorizCount, null);
        contCellVertiCount.setBounds(new Rectangle(46, 101, 203, 19));
        this.add(contCellVertiCount, null);
        btnSubmit.setBounds(new Rectangle(61, 144, 61, 19));
        this.add(btnSubmit, null);
        btnCancel.setBounds(new Rectangle(161, 144, 61, 19));
        this.add(btnCancel, null);
        //contCellHeigth
        contCellHeigth.setBoundEditor(kDSpinCellHeight);
        //contCellWidth
        contCellWidth.setBoundEditor(kDSpinCellWidth);
        //contCellHorizCount
        contCellHorizCount.setBoundEditor(kDSpinCellHorizCount);
        //contCellVertiCount
        contCellVertiCount.setBoundEditor(kDSpinCellVertiCount);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.PlanisphereSetPicLayoutUIHandler";
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
     * output btnSubmit_actionPerformed method
     */
    protected void btnSubmit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnCancel_actionPerformed method
     */
    protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "PlanisphereSetPicLayoutUI");
    }




}