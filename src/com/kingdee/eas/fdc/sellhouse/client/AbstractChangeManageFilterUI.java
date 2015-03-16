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
public abstract class AbstractChangeManageFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChangeManageFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizAdscriptionDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizAdscriptionDateTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeDateTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contboBizState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeReson;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizAdscriptionDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizAdscriptionDateTo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCustomer;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkChangeDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkChangeDateTo;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizState;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtChangeReson;
    /**
     * output class constructor
     */
    public AbstractChangeManageFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractChangeManageFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contBizAdscriptionDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizAdscriptionDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contboBizState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeReson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkBizAdscriptionDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkBizAdscriptionDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtCustomer = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRoom = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkChangeDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkChangeDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboBizState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboBizType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtChangeReson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contBizAdscriptionDateFrom.setName("contBizAdscriptionDateFrom");
        this.contBizAdscriptionDateTo.setName("contBizAdscriptionDateTo");
        this.contCustomer.setName("contCustomer");
        this.contPhone.setName("contPhone");
        this.contRoom.setName("contRoom");
        this.contChangeDateFrom.setName("contChangeDateFrom");
        this.contChangeDateTo.setName("contChangeDateTo");
        this.contboBizState.setName("contboBizState");
        this.contBizType.setName("contBizType");
        this.contChangeReson.setName("contChangeReson");
        this.pkBizAdscriptionDateFrom.setName("pkBizAdscriptionDateFrom");
        this.pkBizAdscriptionDateTo.setName("pkBizAdscriptionDateTo");
        this.txtCustomer.setName("txtCustomer");
        this.txtPhone.setName("txtPhone");
        this.txtRoom.setName("txtRoom");
        this.pkChangeDateFrom.setName("pkChangeDateFrom");
        this.pkChangeDateTo.setName("pkChangeDateTo");
        this.comboBizState.setName("comboBizState");
        this.comboBizType.setName("comboBizType");
        this.prmtChangeReson.setName("prmtChangeReson");
        // CustomerQueryPanel
        // contBizAdscriptionDateFrom		
        this.contBizAdscriptionDateFrom.setBoundLabelText(resHelper.getString("contBizAdscriptionDateFrom.boundLabelText"));		
        this.contBizAdscriptionDateFrom.setBoundLabelUnderline(true);		
        this.contBizAdscriptionDateFrom.setBoundLabelLength(100);
        // contBizAdscriptionDateTo		
        this.contBizAdscriptionDateTo.setBoundLabelText(resHelper.getString("contBizAdscriptionDateTo.boundLabelText"));		
        this.contBizAdscriptionDateTo.setBoundLabelLength(100);		
        this.contBizAdscriptionDateTo.setBoundLabelUnderline(true);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);
        // contPhone		
        this.contPhone.setBoundLabelText(resHelper.getString("contPhone.boundLabelText"));		
        this.contPhone.setBoundLabelLength(100);		
        this.contPhone.setBoundLabelUnderline(true);
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // contChangeDateFrom		
        this.contChangeDateFrom.setBoundLabelText(resHelper.getString("contChangeDateFrom.boundLabelText"));		
        this.contChangeDateFrom.setBoundLabelUnderline(true);		
        this.contChangeDateFrom.setBoundLabelLength(100);
        // contChangeDateTo		
        this.contChangeDateTo.setBoundLabelText(resHelper.getString("contChangeDateTo.boundLabelText"));		
        this.contChangeDateTo.setBoundLabelLength(100);		
        this.contChangeDateTo.setBoundLabelUnderline(true);
        // contboBizState		
        this.contboBizState.setBoundLabelText(resHelper.getString("contboBizState.boundLabelText"));		
        this.contboBizState.setBoundLabelLength(100);		
        this.contboBizState.setBoundLabelUnderline(true);
        // contBizType		
        this.contBizType.setBoundLabelText(resHelper.getString("contBizType.boundLabelText"));		
        this.contBizType.setBoundLabelUnderline(true);		
        this.contBizType.setBoundLabelLength(100);
        // contChangeReson		
        this.contChangeReson.setBoundLabelText(resHelper.getString("contChangeReson.boundLabelText"));		
        this.contChangeReson.setBoundLabelLength(100);		
        this.contChangeReson.setBoundLabelUnderline(true);
        // pkBizAdscriptionDateFrom
        // pkBizAdscriptionDateTo
        // txtCustomer
        // txtPhone
        // txtRoom
        // pkChangeDateFrom
        // pkChangeDateTo
        // comboBizState
        // comboBizType
        this.comboBizType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboBizType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtChangeReson		
        this.prmtChangeReson.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ChangeReasonQuery");		
        this.prmtChangeReson.setCommitFormat("$number$");		
        this.prmtChangeReson.setEditFormat("$number$");		
        this.prmtChangeReson.setDisplayFormat("$name$");		
        this.prmtChangeReson.setEnabledMultiSelection(true);
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
        this.setBounds(new Rectangle(10, 10, 600, 150));
        this.setLayout(null);
        contBizAdscriptionDateFrom.setBounds(new Rectangle(20, 80, 270, 19));
        this.add(contBizAdscriptionDateFrom, null);
        contBizAdscriptionDateTo.setBounds(new Rectangle(310, 80, 270, 19));
        this.add(contBizAdscriptionDateTo, null);
        contCustomer.setBounds(new Rectangle(20, 36, 270, 19));
        this.add(contCustomer, null);
        contPhone.setBounds(new Rectangle(310, 36, 270, 19));
        this.add(contPhone, null);
        contRoom.setBounds(new Rectangle(20, 14, 270, 19));
        this.add(contRoom, null);
        contChangeDateFrom.setBounds(new Rectangle(20, 102, 270, 19));
        this.add(contChangeDateFrom, null);
        contChangeDateTo.setBounds(new Rectangle(310, 102, 270, 19));
        this.add(contChangeDateTo, null);
        contboBizState.setBounds(new Rectangle(20, 58, 270, 19));
        this.add(contboBizState, null);
        contBizType.setBounds(new Rectangle(310, 58, 270, 19));
        this.add(contBizType, null);
        contChangeReson.setBounds(new Rectangle(20, 124, 270, 19));
        this.add(contChangeReson, null);
        //contBizAdscriptionDateFrom
        contBizAdscriptionDateFrom.setBoundEditor(pkBizAdscriptionDateFrom);
        //contBizAdscriptionDateTo
        contBizAdscriptionDateTo.setBoundEditor(pkBizAdscriptionDateTo);
        //contCustomer
        contCustomer.setBoundEditor(txtCustomer);
        //contPhone
        contPhone.setBoundEditor(txtPhone);
        //contRoom
        contRoom.setBoundEditor(txtRoom);
        //contChangeDateFrom
        contChangeDateFrom.setBoundEditor(pkChangeDateFrom);
        //contChangeDateTo
        contChangeDateTo.setBoundEditor(pkChangeDateTo);
        //contboBizState
        contboBizState.setBoundEditor(comboBizState);
        //contBizType
        contBizType.setBoundEditor(comboBizType);
        //contChangeReson
        contChangeReson.setBoundEditor(prmtChangeReson);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.ChangeManageFilterUIHandler";
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
     * output comboBizType_itemStateChanged method
     */
    protected void comboBizType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ChangeManageFilterUI");
    }




}