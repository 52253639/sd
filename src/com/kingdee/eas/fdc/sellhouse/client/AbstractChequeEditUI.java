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
public abstract class AbstractChequeEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChequeEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChequeType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboChequeType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contKeeper;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtKeeper;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contKeepOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtKeepOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayer;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPayer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPayTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCapitalization;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCapitalization;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLimitAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLimitAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWrittenOffer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtWrittenOffer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contWrittenOffTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVerifyStatus;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboVerifyStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVerifyOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtVerifyOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVerifier;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtVerifier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVerifyTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contResume;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkWrittenOffTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkVerifyTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPayTime;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtResume;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.eas.fdc.sellhouse.ChequeInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractChequeEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractChequeEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChequeType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboChequeType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contKeeper = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtKeeper = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contKeepOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtKeepOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contPayer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtPayer = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contPayTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contCapitalization = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCapitalization = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contLimitAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtLimitAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contWrittenOffer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtWrittenOffer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contWrittenOffTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contVerifyStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboVerifyStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contVerifyOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtVerifyOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contVerifier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtVerifier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contVerifyTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contResume = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkWrittenOffTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkVerifyTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkPayTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtResume = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contCreator.setName("contCreator");
        this.prmtCreator.setName("prmtCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contDescription.setName("contDescription");
        this.contChequeType.setName("contChequeType");
        this.comboChequeType.setName("comboChequeType");
        this.contCurrency.setName("contCurrency");
        this.prmtCurrency.setName("prmtCurrency");
        this.contKeeper.setName("contKeeper");
        this.prmtKeeper.setName("prmtKeeper");
        this.contKeepOrgUnit.setName("contKeepOrgUnit");
        this.prmtKeepOrgUnit.setName("prmtKeepOrgUnit");
        this.contStatus.setName("contStatus");
        this.comboStatus.setName("comboStatus");
        this.contPayer.setName("contPayer");
        this.txtPayer.setName("txtPayer");
        this.contPayTime.setName("contPayTime");
        this.contAmount.setName("contAmount");
        this.txtAmount.setName("txtAmount");
        this.contCapitalization.setName("contCapitalization");
        this.txtCapitalization.setName("txtCapitalization");
        this.contLimitAmount.setName("contLimitAmount");
        this.txtLimitAmount.setName("txtLimitAmount");
        this.contWrittenOffer.setName("contWrittenOffer");
        this.prmtWrittenOffer.setName("prmtWrittenOffer");
        this.contWrittenOffTime.setName("contWrittenOffTime");
        this.contVerifyStatus.setName("contVerifyStatus");
        this.comboVerifyStatus.setName("comboVerifyStatus");
        this.contVerifyOrgUnit.setName("contVerifyOrgUnit");
        this.prmtVerifyOrgUnit.setName("prmtVerifyOrgUnit");
        this.contVerifier.setName("contVerifier");
        this.prmtVerifier.setName("prmtVerifier");
        this.contVerifyTime.setName("contVerifyTime");
        this.contResume.setName("contResume");
        this.pkWrittenOffTime.setName("pkWrittenOffTime");
        this.pkVerifyTime.setName("pkVerifyTime");
        this.pkCreateTime.setName("pkCreateTime");
        this.pkPayTime.setName("pkPayTime");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtResume.setName("txtResume");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.txtDescription.setName("txtDescription");
        // CoreUI
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtCreator.setCommitFormat("$number$");		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contChequeType		
        this.contChequeType.setBoundLabelText(resHelper.getString("contChequeType.boundLabelText"));		
        this.contChequeType.setBoundLabelLength(100);		
        this.contChequeType.setBoundLabelUnderline(true);
        // comboChequeType		
        this.comboChequeType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum").toArray());
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);
        // prmtCurrency		
        this.prmtCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.prmtCurrency.setCommitFormat("$number$");		
        this.prmtCurrency.setDisplayFormat("$name$");		
        this.prmtCurrency.setEditFormat("$number$");
        // contKeeper		
        this.contKeeper.setBoundLabelText(resHelper.getString("contKeeper.boundLabelText"));		
        this.contKeeper.setBoundLabelLength(100);		
        this.contKeeper.setBoundLabelUnderline(true);
        // prmtKeeper		
        this.prmtKeeper.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtKeeper.setCommitFormat("$number$");		
        this.prmtKeeper.setDisplayFormat("$name$");		
        this.prmtKeeper.setEditFormat("$number$");
        // contKeepOrgUnit		
        this.contKeepOrgUnit.setBoundLabelText(resHelper.getString("contKeepOrgUnit.boundLabelText"));		
        this.contKeepOrgUnit.setBoundLabelLength(100);		
        this.contKeepOrgUnit.setBoundLabelUnderline(true);
        // prmtKeepOrgUnit		
        this.prmtKeepOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.SaleOrgUnitQuery");		
        this.prmtKeepOrgUnit.setCommitFormat("$number$");		
        this.prmtKeepOrgUnit.setEditFormat("$number$");		
        this.prmtKeepOrgUnit.setDisplayFormat("$name$");
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum").toArray());
        // contPayer		
        this.contPayer.setBoundLabelText(resHelper.getString("contPayer.boundLabelText"));		
        this.contPayer.setBoundLabelLength(100);		
        this.contPayer.setBoundLabelUnderline(true);
        // txtPayer		
        this.txtPayer.setMaxLength(80);
        // contPayTime		
        this.contPayTime.setBoundLabelText(resHelper.getString("contPayTime.boundLabelText"));		
        this.contPayTime.setBoundLabelLength(100);		
        this.contPayTime.setBoundLabelUnderline(true);
        // contAmount		
        this.contAmount.setBoundLabelText(resHelper.getString("contAmount.boundLabelText"));		
        this.contAmount.setBoundLabelLength(100);		
        this.contAmount.setBoundLabelUnderline(true);
        // txtAmount		
        this.txtAmount.setDataType(1);
        // contCapitalization		
        this.contCapitalization.setBoundLabelText(resHelper.getString("contCapitalization.boundLabelText"));		
        this.contCapitalization.setBoundLabelLength(100);		
        this.contCapitalization.setBoundLabelUnderline(true);
        // txtCapitalization		
        this.txtCapitalization.setMaxLength(80);
        // contLimitAmount		
        this.contLimitAmount.setBoundLabelText(resHelper.getString("contLimitAmount.boundLabelText"));		
        this.contLimitAmount.setBoundLabelLength(100);		
        this.contLimitAmount.setBoundLabelUnderline(true);
        // txtLimitAmount		
        this.txtLimitAmount.setDataType(1);
        // contWrittenOffer		
        this.contWrittenOffer.setBoundLabelText(resHelper.getString("contWrittenOffer.boundLabelText"));		
        this.contWrittenOffer.setBoundLabelLength(100);		
        this.contWrittenOffer.setBoundLabelUnderline(true);
        // prmtWrittenOffer		
        this.prmtWrittenOffer.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtWrittenOffer.setCommitFormat("$number$");		
        this.prmtWrittenOffer.setDisplayFormat("$name$");		
        this.prmtWrittenOffer.setEditFormat("$number$");
        // contWrittenOffTime		
        this.contWrittenOffTime.setBoundLabelText(resHelper.getString("contWrittenOffTime.boundLabelText"));		
        this.contWrittenOffTime.setBoundLabelLength(100);		
        this.contWrittenOffTime.setBoundLabelUnderline(true);
        // contVerifyStatus		
        this.contVerifyStatus.setBoundLabelText(resHelper.getString("contVerifyStatus.boundLabelText"));		
        this.contVerifyStatus.setBoundLabelLength(100);		
        this.contVerifyStatus.setBoundLabelUnderline(true);
        // comboVerifyStatus		
        this.comboVerifyStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.VerifyStatusEnum").toArray());
        // contVerifyOrgUnit		
        this.contVerifyOrgUnit.setBoundLabelText(resHelper.getString("contVerifyOrgUnit.boundLabelText"));		
        this.contVerifyOrgUnit.setBoundLabelLength(100);		
        this.contVerifyOrgUnit.setBoundLabelUnderline(true);
        // prmtVerifyOrgUnit		
        this.prmtVerifyOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.SaleOrgUnitQuery");		
        this.prmtVerifyOrgUnit.setCommitFormat("$number$");		
        this.prmtVerifyOrgUnit.setDisplayFormat("$name$");		
        this.prmtVerifyOrgUnit.setEditFormat("$number$");
        // contVerifier		
        this.contVerifier.setBoundLabelText(resHelper.getString("contVerifier.boundLabelText"));		
        this.contVerifier.setBoundLabelLength(100);		
        this.contVerifier.setBoundLabelUnderline(true);
        // prmtVerifier		
        this.prmtVerifier.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtVerifier.setCommitFormat("$number$");		
        this.prmtVerifier.setEditFormat("$number$");		
        this.prmtVerifier.setDisplayFormat("$name$");
        // contVerifyTime		
        this.contVerifyTime.setBoundLabelText(resHelper.getString("contVerifyTime.boundLabelText"));		
        this.contVerifyTime.setBoundLabelLength(100);		
        this.contVerifyTime.setBoundLabelUnderline(true);
        // contResume		
        this.contResume.setBoundLabelText(resHelper.getString("contResume.boundLabelText"));		
        this.contResume.setBoundLabelLength(100);		
        this.contResume.setBoundLabelUnderline(true);
        // pkWrittenOffTime
        // pkVerifyTime
        // pkCreateTime
        // pkPayTime
        // kDScrollPane1
        // txtResume		
        this.txtResume.setMaxLength(300);
        // kDScrollPane2
        // txtDescription		
        this.txtDescription.setMaxLength(300);
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
        this.setBounds(new Rectangle(10, 10, 860, 629));
        this.setLayout(null);
        contCreator.setBounds(new Rectangle(578, 239, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(8, 332, 270, 19));
        this.add(contCreateTime, null);
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, null);
        contDescription.setBounds(new Rectangle(8, 269, 840, 52));
        this.add(contDescription, null);
        contChequeType.setBounds(new Rectangle(580, 10, 270, 19));
        this.add(contChequeType, null);
        contCurrency.setBounds(new Rectangle(292, 149, 270, 19));
        this.add(contCurrency, null);
        contKeeper.setBounds(new Rectangle(578, 179, 270, 19));
        this.add(contKeeper, null);
        contKeepOrgUnit.setBounds(new Rectangle(8, 209, 270, 19));
        this.add(contKeepOrgUnit, null);
        contStatus.setBounds(new Rectangle(294, 10, 270, 19));
        this.add(contStatus, null);
        contPayer.setBounds(new Rectangle(8, 119, 270, 19));
        this.add(contPayer, null);
        contPayTime.setBounds(new Rectangle(292, 119, 270, 19));
        this.add(contPayTime, null);
        contAmount.setBounds(new Rectangle(578, 119, 270, 19));
        this.add(contAmount, null);
        contCapitalization.setBounds(new Rectangle(8, 149, 270, 19));
        this.add(contCapitalization, null);
        contLimitAmount.setBounds(new Rectangle(578, 149, 270, 19));
        this.add(contLimitAmount, null);
        contWrittenOffer.setBounds(new Rectangle(8, 179, 270, 19));
        this.add(contWrittenOffer, null);
        contWrittenOffTime.setBounds(new Rectangle(292, 179, 270, 19));
        this.add(contWrittenOffTime, null);
        contVerifyStatus.setBounds(new Rectangle(292, 209, 270, 19));
        this.add(contVerifyStatus, null);
        contVerifyOrgUnit.setBounds(new Rectangle(578, 209, 270, 19));
        this.add(contVerifyOrgUnit, null);
        contVerifier.setBounds(new Rectangle(8, 239, 270, 19));
        this.add(contVerifier, null);
        contVerifyTime.setBounds(new Rectangle(292, 239, 270, 19));
        this.add(contVerifyTime, null);
        contResume.setBounds(new Rectangle(10, 40, 840, 69));
        this.add(contResume, null);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtDescription, null);
        //contChequeType
        contChequeType.setBoundEditor(comboChequeType);
        //contCurrency
        contCurrency.setBoundEditor(prmtCurrency);
        //contKeeper
        contKeeper.setBoundEditor(prmtKeeper);
        //contKeepOrgUnit
        contKeepOrgUnit.setBoundEditor(prmtKeepOrgUnit);
        //contStatus
        contStatus.setBoundEditor(comboStatus);
        //contPayer
        contPayer.setBoundEditor(txtPayer);
        //contPayTime
        contPayTime.setBoundEditor(pkPayTime);
        //contAmount
        contAmount.setBoundEditor(txtAmount);
        //contCapitalization
        contCapitalization.setBoundEditor(txtCapitalization);
        //contLimitAmount
        contLimitAmount.setBoundEditor(txtLimitAmount);
        //contWrittenOffer
        contWrittenOffer.setBoundEditor(prmtWrittenOffer);
        //contWrittenOffTime
        contWrittenOffTime.setBoundEditor(pkWrittenOffTime);
        //contVerifyStatus
        contVerifyStatus.setBoundEditor(comboVerifyStatus);
        //contVerifyOrgUnit
        contVerifyOrgUnit.setBoundEditor(prmtVerifyOrgUnit);
        //contVerifier
        contVerifier.setBoundEditor(prmtVerifier);
        //contVerifyTime
        contVerifyTime.setBoundEditor(pkVerifyTime);
        //contResume
        contResume.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtResume, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("chequeType", com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum.class, this.comboChequeType, "selectedItem");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.prmtCurrency, "data");
		dataBinder.registerBinding("keeper", com.kingdee.eas.base.permission.UserInfo.class, this.prmtKeeper, "data");
		dataBinder.registerBinding("keepOrgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtKeepOrgUnit, "data");
		dataBinder.registerBinding("status", com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("payer", String.class, this.txtPayer, "text");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtAmount, "value");
		dataBinder.registerBinding("capitalization", String.class, this.txtCapitalization, "text");
		dataBinder.registerBinding("limitAmount", java.math.BigDecimal.class, this.txtLimitAmount, "value");
		dataBinder.registerBinding("writtenOffer", com.kingdee.eas.base.permission.UserInfo.class, this.prmtWrittenOffer, "data");
		dataBinder.registerBinding("verifyStatus", com.kingdee.eas.fdc.sellhouse.VerifyStatusEnum.class, this.comboVerifyStatus, "selectedItem");
		dataBinder.registerBinding("verifyOrgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtVerifyOrgUnit, "data");
		dataBinder.registerBinding("verifier", com.kingdee.eas.base.permission.UserInfo.class, this.prmtVerifier, "data");
		dataBinder.registerBinding("writtenOffTime", java.sql.Timestamp.class, this.pkWrittenOffTime, "value");
		dataBinder.registerBinding("verifyTime", java.sql.Timestamp.class, this.pkVerifyTime, "value");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("payTime", java.sql.Timestamp.class, this.pkPayTime, "value");
		dataBinder.registerBinding("resume", String.class, this.txtResume, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.ChequeEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.ChequeInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chequeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("keeper", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("keepOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("capitalization", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("limitAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("writtenOffer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("verifyStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("verifyOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("verifier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("writtenOffTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("verifyTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("resume", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("chequeType"));
        sic.add(new SelectorItemInfo("currency.*"));
        sic.add(new SelectorItemInfo("keeper.*"));
        sic.add(new SelectorItemInfo("keepOrgUnit.*"));
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("payer"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("capitalization"));
        sic.add(new SelectorItemInfo("limitAmount"));
        sic.add(new SelectorItemInfo("writtenOffer.*"));
        sic.add(new SelectorItemInfo("verifyStatus"));
        sic.add(new SelectorItemInfo("verifyOrgUnit.*"));
        sic.add(new SelectorItemInfo("verifier.*"));
        sic.add(new SelectorItemInfo("writtenOffTime"));
        sic.add(new SelectorItemInfo("verifyTime"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("payTime"));
        sic.add(new SelectorItemInfo("resume"));
        sic.add(new SelectorItemInfo("description"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ChequeEditUI");
    }




}