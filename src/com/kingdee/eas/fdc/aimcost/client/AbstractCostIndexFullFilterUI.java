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
public abstract class AbstractCostIndexFullFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCostIndexFullFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPrjoect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildPriceFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildPriceTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSalePriceTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSalePriceFrom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildPriceFrom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildPriceTo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProductType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSalePriceTo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSalePriceFrom;
    /**
     * output class constructor
     */
    public AbstractCostIndexFullFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCostIndexFullFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contPrjoect = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFromDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildPriceFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildPriceTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSalePriceTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSalePriceFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkFromDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtInviteType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtBuildPriceFrom = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBuildPriceTo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtProductType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSalePriceTo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtSalePriceFrom = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contPrjoect.setName("contPrjoect");
        this.contFromDate.setName("contFromDate");
        this.contToDate.setName("contToDate");
        this.contInviteType.setName("contInviteType");
        this.contBuildPriceFrom.setName("contBuildPriceFrom");
        this.contBuildPriceTo.setName("contBuildPriceTo");
        this.contProductType.setName("contProductType");
        this.contSalePriceTo.setName("contSalePriceTo");
        this.contSalePriceFrom.setName("contSalePriceFrom");
        this.prmtProject.setName("prmtProject");
        this.pkFromDate.setName("pkFromDate");
        this.pkToDate.setName("pkToDate");
        this.prmtInviteType.setName("prmtInviteType");
        this.txtBuildPriceFrom.setName("txtBuildPriceFrom");
        this.txtBuildPriceTo.setName("txtBuildPriceTo");
        this.txtProductType.setName("txtProductType");
        this.txtSalePriceTo.setName("txtSalePriceTo");
        this.txtSalePriceFrom.setName("txtSalePriceFrom");
        // CustomerQueryPanel
        // contPrjoect		
        this.contPrjoect.setBoundLabelText(resHelper.getString("contPrjoect.boundLabelText"));		
        this.contPrjoect.setBoundLabelLength(100);		
        this.contPrjoect.setBoundLabelUnderline(true);
        // contFromDate		
        this.contFromDate.setBoundLabelText(resHelper.getString("contFromDate.boundLabelText"));		
        this.contFromDate.setBoundLabelLength(100);		
        this.contFromDate.setBoundLabelUnderline(true);
        // contToDate		
        this.contToDate.setBoundLabelText(resHelper.getString("contToDate.boundLabelText"));		
        this.contToDate.setBoundLabelLength(100);		
        this.contToDate.setBoundLabelUnderline(true);
        // contInviteType		
        this.contInviteType.setBoundLabelText(resHelper.getString("contInviteType.boundLabelText"));		
        this.contInviteType.setBoundLabelLength(100);		
        this.contInviteType.setBoundLabelUnderline(true);
        // contBuildPriceFrom		
        this.contBuildPriceFrom.setBoundLabelText(resHelper.getString("contBuildPriceFrom.boundLabelText"));		
        this.contBuildPriceFrom.setBoundLabelLength(100);		
        this.contBuildPriceFrom.setBoundLabelUnderline(true);
        // contBuildPriceTo		
        this.contBuildPriceTo.setBoundLabelText(resHelper.getString("contBuildPriceTo.boundLabelText"));		
        this.contBuildPriceTo.setBoundLabelLength(100);		
        this.contBuildPriceTo.setBoundLabelUnderline(true);
        // contProductType		
        this.contProductType.setBoundLabelText(resHelper.getString("contProductType.boundLabelText"));		
        this.contProductType.setBoundLabelLength(100);		
        this.contProductType.setBoundLabelUnderline(true);
        // contSalePriceTo		
        this.contSalePriceTo.setBoundLabelText(resHelper.getString("contSalePriceTo.boundLabelText"));		
        this.contSalePriceTo.setBoundLabelLength(100);		
        this.contSalePriceTo.setBoundLabelUnderline(true);
        // contSalePriceFrom		
        this.contSalePriceFrom.setBoundLabelText(resHelper.getString("contSalePriceFrom.boundLabelText"));		
        this.contSalePriceFrom.setBoundLabelLength(100);		
        this.contSalePriceFrom.setBoundLabelUnderline(true);
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtProject.setDisplayFormat("$name$");		
        this.prmtProject.setEditFormat("$number$");		
        this.prmtProject.setCommitFormat("$number$");		
        this.prmtProject.setEnabledMultiSelection(true);		
        this.prmtProject.setRequired(true);
        this.prmtProject.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtProject_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkFromDate
        // pkToDate
        // prmtInviteType		
        this.prmtInviteType.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteTypeQuery");		
        this.prmtInviteType.setEditFormat("$number$");		
        this.prmtInviteType.setDisplayFormat("$name$");		
        this.prmtInviteType.setCommitFormat("$number$");		
        this.prmtInviteType.setEnabledMultiSelection(true);
        this.prmtInviteType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtInviteType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtBuildPriceFrom		
        this.txtBuildPriceFrom.setDataType(1);		
        this.txtBuildPriceFrom.setPrecision(2);
        // txtBuildPriceTo		
        this.txtBuildPriceTo.setDataType(1);		
        this.txtBuildPriceTo.setPrecision(2);
        // txtProductType
        // txtSalePriceTo		
        this.txtSalePriceTo.setDataType(1);		
        this.txtSalePriceTo.setPrecision(2);
        // txtSalePriceFrom		
        this.txtSalePriceFrom.setDataType(1);		
        this.txtSalePriceFrom.setPrecision(2);
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
        this.setBounds(new Rectangle(10, 10, 600, 130));
        this.setLayout(null);
        contPrjoect.setBounds(new Rectangle(16, 10, 567, 19));
        this.add(contPrjoect, null);
        contFromDate.setBounds(new Rectangle(16, 98, 270, 19));
        this.add(contFromDate, null);
        contToDate.setBounds(new Rectangle(313, 98, 270, 19));
        this.add(contToDate, null);
        contInviteType.setBounds(new Rectangle(16, 32, 270, 19));
        this.add(contInviteType, null);
        contBuildPriceFrom.setBounds(new Rectangle(16, 54, 270, 19));
        this.add(contBuildPriceFrom, null);
        contBuildPriceTo.setBounds(new Rectangle(313, 54, 270, 19));
        this.add(contBuildPriceTo, null);
        contProductType.setBounds(new Rectangle(313, 32, 270, 19));
        this.add(contProductType, null);
        contSalePriceTo.setBounds(new Rectangle(313, 76, 270, 19));
        this.add(contSalePriceTo, null);
        contSalePriceFrom.setBounds(new Rectangle(16, 76, 270, 19));
        this.add(contSalePriceFrom, null);
        //contPrjoect
        contPrjoect.setBoundEditor(prmtProject);
        //contFromDate
        contFromDate.setBoundEditor(pkFromDate);
        //contToDate
        contToDate.setBoundEditor(pkToDate);
        //contInviteType
        contInviteType.setBoundEditor(prmtInviteType);
        //contBuildPriceFrom
        contBuildPriceFrom.setBoundEditor(txtBuildPriceFrom);
        //contBuildPriceTo
        contBuildPriceTo.setBoundEditor(txtBuildPriceTo);
        //contProductType
        contProductType.setBoundEditor(txtProductType);
        //contSalePriceTo
        contSalePriceTo.setBoundEditor(txtSalePriceTo);
        //contSalePriceFrom
        contSalePriceFrom.setBoundEditor(txtSalePriceFrom);

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
	    return "com.kingdee.eas.fdc.aimcost.app.CostIndexFullFilterUIHandler";
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
     * output prmtProject_willShow method
     */
    protected void prmtProject_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtInviteType_dataChanged method
     */
    protected void prmtInviteType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.aimcost.client", "CostIndexFullFilterUI");
    }




}