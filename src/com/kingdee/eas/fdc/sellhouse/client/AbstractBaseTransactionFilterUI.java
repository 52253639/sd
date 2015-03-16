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
public abstract class AbstractBaseTransactionFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBaseTransactionFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizAdscriptionDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizAdscriptionDateTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeDateTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSeller;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingAreaFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingAreaTo;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelBizState;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoomModel;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizAdscriptionDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizAdscriptionDateTo;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCustomer;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkChangeDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkChangeDateTo;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProductType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSeller;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingAreaFrom;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingAreaTo;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbNullify;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbAuditted;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbSaved;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbSubmitted;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbAuditting;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbCNAuditting;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbQRAuditting;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbCRAuditting;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbCRNullify;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbQRNullify;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbCNNullify;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbToPur;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rbToSign;
    /**
     * output class constructor
     */
    public AbstractBaseTransactionFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBaseTransactionFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contRoomModel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizAdscriptionDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizAdscriptionDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProductType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSeller = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingAreaFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingAreaTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.panelBizState = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.prmtRoomModel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkBizAdscriptionDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkBizAdscriptionDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtCustomer = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRoom = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkChangeDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkChangeDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSeller = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtBuildingAreaFrom = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBuildingAreaTo = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.rbNullify = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbAuditted = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbSaved = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbSubmitted = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbAuditting = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbCNAuditting = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbQRAuditting = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbCRAuditting = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbCRNullify = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbQRNullify = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbCNNullify = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbToPur = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rbToSign = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.contRoomModel.setName("contRoomModel");
        this.contBizAdscriptionDateFrom.setName("contBizAdscriptionDateFrom");
        this.contBizAdscriptionDateTo.setName("contBizAdscriptionDateTo");
        this.contCustomer.setName("contCustomer");
        this.contPhone.setName("contPhone");
        this.contRoom.setName("contRoom");
        this.contChangeDateFrom.setName("contChangeDateFrom");
        this.contChangeDateTo.setName("contChangeDateTo");
        this.contProductType.setName("contProductType");
        this.contSeller.setName("contSeller");
        this.contBuildingAreaFrom.setName("contBuildingAreaFrom");
        this.contBuildingAreaTo.setName("contBuildingAreaTo");
        this.panelBizState.setName("panelBizState");
        this.prmtRoomModel.setName("prmtRoomModel");
        this.pkBizAdscriptionDateFrom.setName("pkBizAdscriptionDateFrom");
        this.pkBizAdscriptionDateTo.setName("pkBizAdscriptionDateTo");
        this.txtCustomer.setName("txtCustomer");
        this.txtPhone.setName("txtPhone");
        this.txtRoom.setName("txtRoom");
        this.pkChangeDateFrom.setName("pkChangeDateFrom");
        this.pkChangeDateTo.setName("pkChangeDateTo");
        this.prmtProductType.setName("prmtProductType");
        this.prmtSeller.setName("prmtSeller");
        this.txtBuildingAreaFrom.setName("txtBuildingAreaFrom");
        this.txtBuildingAreaTo.setName("txtBuildingAreaTo");
        this.rbNullify.setName("rbNullify");
        this.rbAuditted.setName("rbAuditted");
        this.rbSaved.setName("rbSaved");
        this.rbSubmitted.setName("rbSubmitted");
        this.rbAuditting.setName("rbAuditting");
        this.rbCNAuditting.setName("rbCNAuditting");
        this.rbQRAuditting.setName("rbQRAuditting");
        this.rbCRAuditting.setName("rbCRAuditting");
        this.rbCRNullify.setName("rbCRNullify");
        this.rbQRNullify.setName("rbQRNullify");
        this.rbCNNullify.setName("rbCNNullify");
        this.rbToPur.setName("rbToPur");
        this.rbToSign.setName("rbToSign");
        // CustomerQueryPanel
        // contRoomModel		
        this.contRoomModel.setBoundLabelText(resHelper.getString("contRoomModel.boundLabelText"));		
        this.contRoomModel.setBoundLabelLength(100);		
        this.contRoomModel.setBoundLabelUnderline(true);
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
        // contProductType		
        this.contProductType.setBoundLabelText(resHelper.getString("contProductType.boundLabelText"));		
        this.contProductType.setBoundLabelLength(100);		
        this.contProductType.setBoundLabelUnderline(true);
        // contSeller		
        this.contSeller.setBoundLabelText(resHelper.getString("contSeller.boundLabelText"));		
        this.contSeller.setBoundLabelLength(100);		
        this.contSeller.setBoundLabelUnderline(true);
        // contBuildingAreaFrom		
        this.contBuildingAreaFrom.setBoundLabelText(resHelper.getString("contBuildingAreaFrom.boundLabelText"));		
        this.contBuildingAreaFrom.setBoundLabelLength(100);		
        this.contBuildingAreaFrom.setBoundLabelUnderline(true);		
        this.contBuildingAreaFrom.setEnabled(false);
        // contBuildingAreaTo		
        this.contBuildingAreaTo.setBoundLabelText(resHelper.getString("contBuildingAreaTo.boundLabelText"));		
        this.contBuildingAreaTo.setBoundLabelLength(100);		
        this.contBuildingAreaTo.setBoundLabelUnderline(true);		
        this.contBuildingAreaTo.setEnabled(false);
        // panelBizState		
        this.panelBizState.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("panelBizState.border.title")));
        // prmtRoomModel		
        this.prmtRoomModel.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelForSHEQuery");		
        this.prmtRoomModel.setEnabledMultiSelection(true);		
        this.prmtRoomModel.setDisplayFormat("$name$");		
        this.prmtRoomModel.setEditFormat("$number$");		
        this.prmtRoomModel.setCommitFormat("$number$");
        // pkBizAdscriptionDateFrom
        // pkBizAdscriptionDateTo
        // txtCustomer
        // txtPhone
        // txtRoom
        // pkChangeDateFrom
        // pkChangeDateTo
        // prmtProductType		
        this.prmtProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");		
        this.prmtProductType.setDisplayFormat("$name$");		
        this.prmtProductType.setEditFormat("$number$");		
        this.prmtProductType.setCommitFormat("$number$");		
        this.prmtProductType.setEnabledMultiSelection(true);
        // prmtSeller		
        this.prmtSeller.setEditFormat("$number$");		
        this.prmtSeller.setDisplayFormat("$name$");		
        this.prmtSeller.setCommitFormat("$number$");		
        this.prmtSeller.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7PropertyConsultantQuery");		
        this.prmtSeller.setEnabledMultiSelection(true);
        // txtBuildingAreaFrom		
        this.txtBuildingAreaFrom.setDataType(1);		
        this.txtBuildingAreaFrom.setPrecision(2);
        // txtBuildingAreaTo		
        this.txtBuildingAreaTo.setDataType(1);		
        this.txtBuildingAreaTo.setPrecision(2);
        // rbNullify		
        this.rbNullify.setText(resHelper.getString("rbNullify.text"));
        // rbAuditted		
        this.rbAuditted.setText(resHelper.getString("rbAuditted.text"));
        // rbSaved		
        this.rbSaved.setText(resHelper.getString("rbSaved.text"));
        // rbSubmitted		
        this.rbSubmitted.setText(resHelper.getString("rbSubmitted.text"));
        // rbAuditting		
        this.rbAuditting.setText(resHelper.getString("rbAuditting.text"));
        // rbCNAuditting		
        this.rbCNAuditting.setText(resHelper.getString("rbCNAuditting.text"));
        // rbQRAuditting		
        this.rbQRAuditting.setText(resHelper.getString("rbQRAuditting.text"));
        // rbCRAuditting		
        this.rbCRAuditting.setText(resHelper.getString("rbCRAuditting.text"));
        // rbCRNullify		
        this.rbCRNullify.setText(resHelper.getString("rbCRNullify.text"));
        // rbQRNullify		
        this.rbQRNullify.setText(resHelper.getString("rbQRNullify.text"));
        // rbCNNullify		
        this.rbCNNullify.setText(resHelper.getString("rbCNNullify.text"));
        // rbToPur		
        this.rbToPur.setText(resHelper.getString("rbToPur.text"));
        // rbToSign		
        this.rbToSign.setText(resHelper.getString("rbToSign.text"));
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
        this.setBounds(new Rectangle(10, 10, 600, 280));
        this.setLayout(null);
        contRoomModel.setBounds(new Rectangle(310, 58, 270, 19));
        this.add(contRoomModel, null);
        contBizAdscriptionDateFrom.setBounds(new Rectangle(20, 124, 270, 19));
        this.add(contBizAdscriptionDateFrom, null);
        contBizAdscriptionDateTo.setBounds(new Rectangle(310, 124, 270, 19));
        this.add(contBizAdscriptionDateTo, null);
        contCustomer.setBounds(new Rectangle(20, 36, 270, 19));
        this.add(contCustomer, null);
        contPhone.setBounds(new Rectangle(310, 36, 270, 19));
        this.add(contPhone, null);
        contRoom.setBounds(new Rectangle(20, 14, 270, 19));
        this.add(contRoom, null);
        contChangeDateFrom.setBounds(new Rectangle(20, 146, 270, 19));
        this.add(contChangeDateFrom, null);
        contChangeDateTo.setBounds(new Rectangle(310, 146, 270, 19));
        this.add(contChangeDateTo, null);
        contProductType.setBounds(new Rectangle(20, 58, 270, 19));
        this.add(contProductType, null);
        contSeller.setBounds(new Rectangle(20, 102, 270, 19));
        this.add(contSeller, null);
        contBuildingAreaFrom.setBounds(new Rectangle(20, 80, 270, 19));
        this.add(contBuildingAreaFrom, null);
        contBuildingAreaTo.setBounds(new Rectangle(310, 80, 270, 19));
        this.add(contBuildingAreaTo, null);
        panelBizState.setBounds(new Rectangle(20, 170, 465, 103));
        this.add(panelBizState, null);
        //contRoomModel
        contRoomModel.setBoundEditor(prmtRoomModel);
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
        //contProductType
        contProductType.setBoundEditor(prmtProductType);
        //contSeller
        contSeller.setBoundEditor(prmtSeller);
        //contBuildingAreaFrom
        contBuildingAreaFrom.setBoundEditor(txtBuildingAreaFrom);
        //contBuildingAreaTo
        contBuildingAreaTo.setBoundEditor(txtBuildingAreaTo);
        //panelBizState
        panelBizState.setLayout(null);        rbNullify.setBounds(new Rectangle(17, 67, 55, 19));
        panelBizState.add(rbNullify, null);
        rbAuditted.setBounds(new Rectangle(88, 43, 55, 19));
        panelBizState.add(rbAuditted, null);
        rbSaved.setBounds(new Rectangle(17, 21, 55, 19));
        panelBizState.add(rbSaved, null);
        rbSubmitted.setBounds(new Rectangle(88, 21, 55, 19));
        panelBizState.add(rbSubmitted, null);
        rbAuditting.setBounds(new Rectangle(17, 44, 66, 19));
        panelBizState.add(rbAuditting, null);
        rbCNAuditting.setBounds(new Rectangle(168, 21, 84, 19));
        panelBizState.add(rbCNAuditting, null);
        rbQRAuditting.setBounds(new Rectangle(168, 44, 84, 19));
        panelBizState.add(rbQRAuditting, null);
        rbCRAuditting.setBounds(new Rectangle(168, 67, 84, 19));
        panelBizState.add(rbCRAuditting, null);
        rbCRNullify.setBounds(new Rectangle(271, 67, 84, 19));
        panelBizState.add(rbCRNullify, null);
        rbQRNullify.setBounds(new Rectangle(271, 44, 84, 19));
        panelBizState.add(rbQRNullify, null);
        rbCNNullify.setBounds(new Rectangle(271, 21, 84, 19));
        panelBizState.add(rbCNNullify, null);
        rbToPur.setBounds(new Rectangle(376, 23, 66, 19));
        panelBizState.add(rbToPur, null);
        rbToSign.setBounds(new Rectangle(376, 44, 66, 19));
        panelBizState.add(rbToSign, null);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.BaseTransactionFilterUIHandler";
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
     * output f7Seller_dataChanged method
     */
    protected void f7Seller_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "BaseTransactionFilterUI");
    }




}