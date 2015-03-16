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
public abstract class AbstractCostAccountWithAccountBalanceFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCostAccountWithAccountBalanceFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainerSpiMonth;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainerSpiYear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainerPrj;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonth;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYear;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCostCenter;
    /**
     * output class constructor
     */
    public AbstractCostAccountWithAccountBalanceFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCostAccountWithAccountBalanceFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainerSpiMonth = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabelContainerSpiYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainerPrj = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.spiMonth = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCostCenter = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainerSpiMonth.setName("kDLabelContainerSpiMonth");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabelContainerSpiYear.setName("kDLabelContainerSpiYear");
        this.kDLabelContainerPrj.setName("kDLabelContainerPrj");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabel2.setName("kDLabel2");
        this.kDLabel3.setName("kDLabel3");
        this.spiMonth.setName("spiMonth");
        this.spiYear.setName("spiYear");
        this.prmtProject.setName("prmtProject");
        this.prmtCostCenter.setName("prmtCostCenter");
        // CustomerQueryPanel
        // kDLabelContainerSpiMonth		
        this.kDLabelContainerSpiMonth.setBoundLabelText(resHelper.getString("kDLabelContainerSpiMonth.boundLabelText"));		
        this.kDLabelContainerSpiMonth.setBoundLabelLength(30);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDLabelContainerSpiYear		
        this.kDLabelContainerSpiYear.setBoundLabelText(resHelper.getString("kDLabelContainerSpiYear.boundLabelText"));		
        this.kDLabelContainerSpiYear.setBoundLabelLength(80);		
        this.kDLabelContainerSpiYear.setBoundLabelUnderline(true);
        // kDLabelContainerPrj		
        this.kDLabelContainerPrj.setBoundLabelText(resHelper.getString("kDLabelContainerPrj.boundLabelText"));		
        this.kDLabelContainerPrj.setBoundLabelLength(80);		
        this.kDLabelContainerPrj.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(80);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));		
        this.kDLabel2.setForeground(new java.awt.Color(128,128,128));
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));		
        this.kDLabel3.setForeground(new java.awt.Color(128,128,128));
        // spiMonth
        // spiYear
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtProject.setCommitFormat("$longNumber$");		
        this.prmtProject.setEditFormat("$longNumber$");		
        this.prmtProject.setDisplayFormat("$name$");
        // prmtCostCenter		
        this.prmtCostCenter.setDisplayFormat("$name$");		
        this.prmtCostCenter.setEditFormat("$number$");		
        this.prmtCostCenter.setCommitFormat("$number$");		
        this.prmtCostCenter.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
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
        kDLabelContainerSpiMonth.setBounds(new Rectangle(173, 29, 69, 19));
        this.add(kDLabelContainerSpiMonth, null);
        kDLabel1.setBounds(new Rectangle(248, 31, 26, 19));
        this.add(kDLabel1, null);
        kDLabelContainerSpiYear.setBounds(new Rectangle(32, 30, 134, 19));
        this.add(kDLabelContainerSpiYear, null);
        kDLabelContainerPrj.setBounds(new Rectangle(32, 60, 228, 19));
        this.add(kDLabelContainerPrj, null);
        kDLabelContainer1.setBounds(new Rectangle(32, 90, 228, 19));
        this.add(kDLabelContainer1, null);
        kDLabel2.setBounds(new Rectangle(56, 131, 328, 21));
        this.add(kDLabel2, null);
        kDLabel3.setBounds(new Rectangle(32, 149, 293, 19));
        this.add(kDLabel3, null);
        //kDLabelContainerSpiMonth
        kDLabelContainerSpiMonth.setBoundEditor(spiMonth);
        //kDLabelContainerSpiYear
        kDLabelContainerSpiYear.setBoundEditor(spiYear);
        //kDLabelContainerPrj
        kDLabelContainerPrj.setBoundEditor(prmtProject);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(prmtCostCenter);

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
	    return "com.kingdee.eas.fdc.finance.app.CostAccountWithAccountBalanceFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "CostAccountWithAccountBalanceFilterUI");
    }




}