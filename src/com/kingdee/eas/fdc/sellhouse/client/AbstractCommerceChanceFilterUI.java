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
public abstract class AbstractCommerceChanceFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCommerceChanceFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWorkArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStayArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIntentionType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTimeFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTimeTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtWorkArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtStayArea;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtIntentionType;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTimeFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTimeTo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCustomer;
    /**
     * output class constructor
     */
    public AbstractCommerceChanceFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCommerceChanceFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contWorkArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStayArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIntentionType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTimeFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTimeTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtWorkArea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtStayArea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtIntentionType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTimeFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkCreateTimeTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCustomer = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contWorkArea.setName("contWorkArea");
        this.contStayArea.setName("contStayArea");
        this.contIntentionType.setName("contIntentionType");
        this.contCreateTimeFrom.setName("contCreateTimeFrom");
        this.contCreateTimeTo.setName("contCreateTimeTo");
        this.contPhone.setName("contPhone");
        this.contCustomer.setName("contCustomer");
        this.prmtWorkArea.setName("prmtWorkArea");
        this.prmtStayArea.setName("prmtStayArea");
        this.prmtIntentionType.setName("prmtIntentionType");
        this.pkCreateTimeFrom.setName("pkCreateTimeFrom");
        this.pkCreateTimeTo.setName("pkCreateTimeTo");
        this.txtPhone.setName("txtPhone");
        this.txtCustomer.setName("txtCustomer");
        // CustomerQueryPanel
        // contWorkArea		
        this.contWorkArea.setBoundLabelText(resHelper.getString("contWorkArea.boundLabelText"));		
        this.contWorkArea.setBoundLabelLength(100);		
        this.contWorkArea.setBoundLabelUnderline(true);
        // contStayArea		
        this.contStayArea.setBoundLabelText(resHelper.getString("contStayArea.boundLabelText"));		
        this.contStayArea.setBoundLabelLength(100);		
        this.contStayArea.setBoundLabelUnderline(true);
        // contIntentionType		
        this.contIntentionType.setBoundLabelText(resHelper.getString("contIntentionType.boundLabelText"));		
        this.contIntentionType.setBoundLabelLength(100);		
        this.contIntentionType.setBoundLabelUnderline(true);
        // contCreateTimeFrom		
        this.contCreateTimeFrom.setBoundLabelText(resHelper.getString("contCreateTimeFrom.boundLabelText"));		
        this.contCreateTimeFrom.setBoundLabelUnderline(true);		
        this.contCreateTimeFrom.setBoundLabelLength(100);
        // contCreateTimeTo		
        this.contCreateTimeTo.setBoundLabelText(resHelper.getString("contCreateTimeTo.boundLabelText"));		
        this.contCreateTimeTo.setBoundLabelLength(100);		
        this.contCreateTimeTo.setBoundLabelUnderline(true);
        // contPhone		
        this.contPhone.setBoundLabelText(resHelper.getString("contPhone.boundLabelText"));		
        this.contPhone.setBoundLabelLength(100);		
        this.contPhone.setBoundLabelUnderline(true);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);
        // prmtWorkArea		
        this.prmtWorkArea.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECusAssistantDataQuery");
        // prmtStayArea		
        this.prmtStayArea.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECusAssistantDataQuery");
        // prmtIntentionType		
        this.prmtIntentionType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelForSHEQuery");
        // pkCreateTimeFrom
        // pkCreateTimeTo
        // txtPhone
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
        this.setBounds(new Rectangle(10, 10, 400, 300));
        this.setLayout(null);
        contWorkArea.setBounds(new Rectangle(63, 101, 270, 19));
        this.add(contWorkArea, null);
        contStayArea.setBounds(new Rectangle(63, 139, 270, 19));
        this.add(contStayArea, null);
        contIntentionType.setBounds(new Rectangle(63, 177, 270, 19));
        this.add(contIntentionType, null);
        contCreateTimeFrom.setBounds(new Rectangle(63, 215, 270, 19));
        this.add(contCreateTimeFrom, null);
        contCreateTimeTo.setBounds(new Rectangle(63, 255, 270, 19));
        this.add(contCreateTimeTo, null);
        contPhone.setBounds(new Rectangle(63, 63, 270, 19));
        this.add(contPhone, null);
        contCustomer.setBounds(new Rectangle(63, 25, 270, 19));
        this.add(contCustomer, null);
        //contWorkArea
        contWorkArea.setBoundEditor(prmtWorkArea);
        //contStayArea
        contStayArea.setBoundEditor(prmtStayArea);
        //contIntentionType
        contIntentionType.setBoundEditor(prmtIntentionType);
        //contCreateTimeFrom
        contCreateTimeFrom.setBoundEditor(pkCreateTimeFrom);
        //contCreateTimeTo
        contCreateTimeTo.setBoundEditor(pkCreateTimeTo);
        //contPhone
        contPhone.setBoundEditor(txtPhone);
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
	    return "com.kingdee.eas.fdc.sellhouse.app.CommerceChanceFilterUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CommerceChanceFilterUI");
    }




}