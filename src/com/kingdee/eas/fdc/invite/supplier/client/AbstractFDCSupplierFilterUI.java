/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

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
public abstract class AbstractFDCSupplierFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCSupplierFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabel contType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplierType;
    protected com.kingdee.bos.ctrl.swing.KDLabel contName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabel contKind;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtKind;
    protected com.kingdee.bos.ctrl.swing.KDLabel contLevel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLevel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtResult;
    protected com.kingdee.bos.ctrl.swing.KDLabel contResult;
    protected com.kingdee.bos.ctrl.swing.KDLabel contOrg;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmitOrg;
    /**
     * output class constructor
     */
    public AbstractFDCSupplierFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCSupplierFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contType = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.prmtSupplierType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contKind = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.prmtKind = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contLevel = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.prmtLevel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtResult = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contResult = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contOrg = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.prmitOrg = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contType.setName("contType");
        this.prmtSupplierType.setName("prmtSupplierType");
        this.contName.setName("contName");
        this.txtName.setName("txtName");
        this.contKind.setName("contKind");
        this.prmtKind.setName("prmtKind");
        this.contLevel.setName("contLevel");
        this.prmtLevel.setName("prmtLevel");
        this.prmtResult.setName("prmtResult");
        this.contResult.setName("contResult");
        this.contOrg.setName("contOrg");
        this.prmitOrg.setName("prmitOrg");
        // CustomerQueryPanel
        // contType		
        this.contType.setText(resHelper.getString("contType.text"));
        // prmtSupplierType		
        this.prmtSupplierType.setQueryInfo("com.kingdee.eas.fdc.supply.app.F7SupplierTypeQuery");		
        this.prmtSupplierType.setCommitFormat("$number$;$name$");		
        this.prmtSupplierType.setEditFormat("$number$");		
        this.prmtSupplierType.setDisplayFormat("$number$;$name$");
        // contName		
        this.contName.setText(resHelper.getString("contName.text"));
        // txtName
        // contKind		
        this.contKind.setText(resHelper.getString("contKind.text"));
        // prmtKind		
        this.prmtKind.setQueryInfo("com.kingdee.eas.fdc.supply.app.F7EnterpriseKindQuery");		
        this.prmtKind.setCommitFormat("$number$;$name$");		
        this.prmtKind.setDisplayFormat("$number$;$name$");		
        this.prmtKind.setEditFormat("$number$");
        // contLevel		
        this.contLevel.setText(resHelper.getString("contLevel.text"));
        // prmtLevel		
        this.prmtLevel.setQueryInfo("com.kingdee.eas.fdc.supply.app.F7SupplierLevelQuery");		
        this.prmtLevel.setCommitFormat("$number$;$name$");		
        this.prmtLevel.setDisplayFormat("$number$;$name$");		
        this.prmtLevel.setEditFormat("$number$");
        // prmtResult		
        this.prmtResult.setQueryInfo("com.kingdee.eas.fdc.supply.app.F7EstimateResultQuery");		
        this.prmtResult.setCommitFormat("$number$;$name$");		
        this.prmtResult.setDisplayFormat("$number$;$name$");		
        this.prmtResult.setEditFormat("$number$");
        // contResult		
        this.contResult.setText(resHelper.getString("contResult.text"));
        // contOrg		
        this.contOrg.setText(resHelper.getString("contOrg.text"));
        // prmitOrg		
        this.prmitOrg.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");		
        this.prmitOrg.setCommitFormat("$number$;$name$");		
        this.prmitOrg.setDisplayFormat("$number$;$name$");		
        this.prmitOrg.setEditFormat("$number$");
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
        contType.setBounds(new Rectangle(33, 33, 100, 19));
        this.add(contType, null);
        prmtSupplierType.setBounds(new Rectangle(139, 33, 170, 19));
        this.add(prmtSupplierType, null);
        contName.setBounds(new Rectangle(33, 59, 100, 19));
        this.add(contName, null);
        txtName.setBounds(new Rectangle(139, 59, 170, 19));
        this.add(txtName, null);
        contKind.setBounds(new Rectangle(33, 86, 100, 19));
        this.add(contKind, null);
        prmtKind.setBounds(new Rectangle(139, 86, 170, 19));
        this.add(prmtKind, null);
        contLevel.setBounds(new Rectangle(33, 113, 100, 19));
        this.add(contLevel, null);
        prmtLevel.setBounds(new Rectangle(139, 113, 170, 19));
        this.add(prmtLevel, null);
        prmtResult.setBounds(new Rectangle(139, 139, 170, 19));
        this.add(prmtResult, null);
        contResult.setBounds(new Rectangle(33, 139, 100, 19));
        this.add(contResult, null);
        contOrg.setBounds(new Rectangle(33, 164, 100, 19));
        this.add(contOrg, null);
        prmitOrg.setBounds(new Rectangle(139, 164, 170, 19));
        this.add(prmitOrg, null);

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
	    return "com.kingdee.eas.fdc.invite.supplier.app.FDCSupplierFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "FDCSupplierFilterUI");
    }




}