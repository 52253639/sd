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
public abstract class AbstractTrackRecordEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTrackRecordEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEventDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEventDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEventType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtEventType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReceptionType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtReceptionType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTrackResult;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboTrackResult;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRelationContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCommerceChance;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCustomer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCommerceChance;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTrackDes;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTrackTxt;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddNewBill;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRetionBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRelationId;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRelationContract;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleMan;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSaleMan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSysType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSysType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTrackType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboTrackType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMarketManage;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMarketManage;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuestionPrint;
    protected com.kingdee.eas.fdc.sellhouse.TrackRecordInfo editData = null;
    protected ActionQuestionPrint actionQuestionPrint = null;
    /**
     * output class constructor
     */
    public AbstractTrackRecordEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTrackRecordEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionQuestionPrint
        this.actionQuestionPrint = new ActionQuestionPrint(this);
        getActionManager().registerAction("actionQuestionPrint", actionQuestionPrint);
         this.actionQuestionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEventDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkEventDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contEventType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtEventType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contReceptionType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtReceptionType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contTrackResult = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboTrackResult = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contRelationContract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCommerceChance = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCommerceChance = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTrackDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtTrackTxt = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.btnAddNewBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.prmtRetionBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contRelationId = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRelationContract = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contSaleMan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtSaleMan = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contSysType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboSysType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contTrackType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboTrackType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contMarketManage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtMarketManage = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnQuestionPrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCreator.setName("contCreator");
        this.prmtCreator.setName("prmtCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contDescription.setName("contDescription");
        this.contEventDate.setName("contEventDate");
        this.pkEventDate.setName("pkEventDate");
        this.contEventType.setName("contEventType");
        this.prmtEventType.setName("prmtEventType");
        this.contReceptionType.setName("contReceptionType");
        this.prmtReceptionType.setName("prmtReceptionType");
        this.contTrackResult.setName("contTrackResult");
        this.comboTrackResult.setName("comboTrackResult");
        this.contRelationContract.setName("contRelationContract");
        this.contCustomer.setName("contCustomer");
        this.contCommerceChance.setName("contCommerceChance");
        this.prmtCustomer.setName("prmtCustomer");
        this.prmtCommerceChance.setName("prmtCommerceChance");
        this.contSellProject.setName("contSellProject");
        this.contTrackDes.setName("contTrackDes");
        this.prmtSellProject.setName("prmtSellProject");
        this.txtTrackTxt.setName("txtTrackTxt");
        this.pkCreateTime.setName("pkCreateTime");
        this.btnAddNewBill.setName("btnAddNewBill");
        this.prmtRetionBill.setName("prmtRetionBill");
        this.contRelationId.setName("contRelationId");
        this.txtRelationContract.setName("txtRelationContract");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.contSaleMan.setName("contSaleMan");
        this.prmtSaleMan.setName("prmtSaleMan");
        this.contSysType.setName("contSysType");
        this.comboSysType.setName("comboSysType");
        this.contTrackType.setName("contTrackType");
        this.comboTrackType.setName("comboTrackType");
        this.contMarketManage.setName("contMarketManage");
        this.prmtMarketManage.setName("prmtMarketManage");
        this.btnQuestionPrint.setName("btnQuestionPrint");
        // CoreUI
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$number$");
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contEventDate		
        this.contEventDate.setBoundLabelText(resHelper.getString("contEventDate.boundLabelText"));		
        this.contEventDate.setBoundLabelLength(100);		
        this.contEventDate.setBoundLabelUnderline(true);
        // pkEventDate		
        this.pkEventDate.setRequired(true);
        // contEventType		
        this.contEventType.setBoundLabelText(resHelper.getString("contEventType.boundLabelText"));		
        this.contEventType.setBoundLabelLength(100);		
        this.contEventType.setBoundLabelUnderline(true);
        // prmtEventType		
        this.prmtEventType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.EventTypeQuery");		
        this.prmtEventType.setCommitFormat("$number$");		
        this.prmtEventType.setEditFormat("$number$");		
        this.prmtEventType.setDisplayFormat("$name$");
        // contReceptionType		
        this.contReceptionType.setBoundLabelText(resHelper.getString("contReceptionType.boundLabelText"));		
        this.contReceptionType.setBoundLabelLength(100);		
        this.contReceptionType.setBoundLabelUnderline(true);
        // prmtReceptionType		
        this.prmtReceptionType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ReceptionTypeQuery");		
        this.prmtReceptionType.setCommitFormat("$number$");		
        this.prmtReceptionType.setEditFormat("$number$");		
        this.prmtReceptionType.setDisplayFormat("$name$");		
        this.prmtReceptionType.setRequired(true);
        // contTrackResult		
        this.contTrackResult.setBoundLabelText(resHelper.getString("contTrackResult.boundLabelText"));		
        this.contTrackResult.setBoundLabelLength(100);		
        this.contTrackResult.setBoundLabelUnderline(true);
        // comboTrackResult		
        this.comboTrackResult.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.TrackRecordEnum").toArray());
        this.comboTrackResult.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboTrackResult_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contRelationContract		
        this.contRelationContract.setBoundLabelText(resHelper.getString("contRelationContract.boundLabelText"));		
        this.contRelationContract.setBoundLabelLength(100);		
        this.contRelationContract.setBoundLabelUnderline(true);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);
        // contCommerceChance		
        this.contCommerceChance.setBoundLabelText(resHelper.getString("contCommerceChance.boundLabelText"));		
        this.contCommerceChance.setBoundLabelLength(100);		
        this.contCommerceChance.setBoundLabelUnderline(true);
        // prmtCustomer		
        this.prmtCustomer.setRequired(true);		
        this.prmtCustomer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");		
        this.prmtCustomer.setCommitFormat("$number$");		
        this.prmtCustomer.setEditFormat("$number$");		
        this.prmtCustomer.setDisplayFormat("$name$");
        this.prmtCustomer.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCustomer_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCommerceChance		
        this.prmtCommerceChance.setDisplayFormat("$name$");		
        this.prmtCommerceChance.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CommerceChanceQuery");		
        this.prmtCommerceChance.setCommitFormat("$number$");		
        this.prmtCommerceChance.setEditFormat("$number$");
        this.prmtCommerceChance.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCommerceChance_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelUnderline(true);		
        this.contSellProject.setBoundLabelLength(100);
        // contTrackDes		
        this.contTrackDes.setBoundLabelText(resHelper.getString("contTrackDes.boundLabelText"));		
        this.contTrackDes.setBoundLabelLength(100);		
        this.contTrackDes.setBoundLabelUnderline(true);
        // prmtSellProject		
        this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtSellProject.setCommitFormat("$number$");		
        this.prmtSellProject.setEditFormat("$number$");		
        this.prmtSellProject.setDisplayFormat("$name$");
        this.prmtSellProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSellProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtTrackTxt		
        this.txtTrackTxt.setMaxLength(80);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // btnAddNewBill		
        this.btnAddNewBill.setText(resHelper.getString("btnAddNewBill.text"));
        this.btnAddNewBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddNewBill_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // prmtRetionBill		
        this.prmtRetionBill.setDisplayFormat("$number$");		
        this.prmtRetionBill.setEditFormat("$number$");		
        this.prmtRetionBill.setCommitFormat("$number$");
        this.prmtRetionBill.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRetionBill_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contRelationId		
        this.contRelationId.setBoundLabelText(resHelper.getString("contRelationId.boundLabelText"));		
        this.contRelationId.setBoundLabelLength(100);		
        this.contRelationId.setBoundLabelUnderline(true);		
        this.contRelationId.setVisible(false);
        // txtRelationContract		
        this.txtRelationContract.setEnabled(false);
        // kDScrollPane1
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // contSaleMan		
        this.contSaleMan.setBoundLabelText(resHelper.getString("contSaleMan.boundLabelText"));		
        this.contSaleMan.setBoundLabelLength(100);		
        this.contSaleMan.setBoundLabelUnderline(true);
        // prmtSaleMan		
        this.prmtSaleMan.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtSaleMan.setDisplayFormat("$name$");		
        this.prmtSaleMan.setEditFormat("$number$");		
        this.prmtSaleMan.setCommitFormat("$number$");		
        this.prmtSaleMan.setEditable(true);
        // contSysType		
        this.contSysType.setBoundLabelText(resHelper.getString("contSysType.boundLabelText"));		
        this.contSysType.setBoundLabelLength(100);		
        this.contSysType.setBoundLabelUnderline(true);
        // comboSysType		
        this.comboSysType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.MoneySysTypeEnum").toArray());
        this.comboSysType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboSysType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contTrackType		
        this.contTrackType.setBoundLabelText(resHelper.getString("contTrackType.boundLabelText"));		
        this.contTrackType.setBoundLabelLength(100);		
        this.contTrackType.setBoundLabelUnderline(true);
        // comboTrackType		
        this.comboTrackType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.TrackRecordTypeEnum").toArray());
        this.comboTrackType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboTrackType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contMarketManage		
        this.contMarketManage.setBoundLabelText(resHelper.getString("contMarketManage.boundLabelText"));		
        this.contMarketManage.setBoundLabelLength(100);		
        this.contMarketManage.setBoundLabelUnderline(true);
        // prmtMarketManage		
        this.prmtMarketManage.setQueryInfo("com.kingdee.eas.fdc.market.app.f7MarketManageQuery");		
        this.prmtMarketManage.setDisplayFormat("$name$");		
        this.prmtMarketManage.setEditFormat("$number$");		
        this.prmtMarketManage.setCommitFormat("$number$");
        // btnQuestionPrint
        this.btnQuestionPrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuestionPrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuestionPrint.setText(resHelper.getString("btnQuestionPrint.text"));
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
        this.setBounds(new Rectangle(10, 10, 580, 280));
        this.setLayout(null);
        contCreator.setBounds(new Rectangle(10, 233, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(302, 233, 270, 19));
        this.add(contCreateTime, null);
        contNumber.setBounds(new Rectangle(10, 15, 270, 19));
        this.add(contNumber, null);
        contDescription.setBounds(new Rectangle(302, 178, 270, 46));
        this.add(contDescription, null);
        contEventDate.setBounds(new Rectangle(302, 15, 270, 19));
        this.add(contEventDate, null);
        contEventType.setBounds(new Rectangle(300, 123, 270, 19));
        this.add(contEventType, null);
        contReceptionType.setBounds(new Rectangle(10, 123, 270, 19));
        this.add(contReceptionType, null);
        contTrackResult.setBounds(new Rectangle(10, 150, 270, 19));
        this.add(contTrackResult, null);
        contRelationContract.setBounds(new Rectangle(302, 150, 270, 19));
        this.add(contRelationContract, null);
        contCustomer.setBounds(new Rectangle(10, 42, 270, 19));
        this.add(contCustomer, null);
        contCommerceChance.setBounds(new Rectangle(10, 96, 270, 19));
        this.add(contCommerceChance, null);
        contSellProject.setBounds(new Rectangle(302, 42, 270, 19));
        this.add(contSellProject, null);
        contTrackDes.setBounds(new Rectangle(10, 204, 270, 19));
        this.add(contTrackDes, null);
        btnAddNewBill.setBounds(new Rectangle(303, 177, 84, 19));
        this.add(btnAddNewBill, null);
        contRelationId.setBounds(new Rectangle(412, 255, 270, 19));
        this.add(contRelationId, null);
        contSaleMan.setBounds(new Rectangle(302, 96, 270, 19));
        this.add(contSaleMan, null);
        contSysType.setBounds(new Rectangle(10, 69, 270, 19));
        this.add(contSysType, null);
        contTrackType.setBounds(new Rectangle(302, 69, 270, 19));
        this.add(contTrackType, null);
        contMarketManage.setBounds(new Rectangle(10, 177, 270, 19));
        this.add(contMarketManage, null);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contEventDate
        contEventDate.setBoundEditor(pkEventDate);
        //contEventType
        contEventType.setBoundEditor(prmtEventType);
        //contReceptionType
        contReceptionType.setBoundEditor(prmtReceptionType);
        //contTrackResult
        contTrackResult.setBoundEditor(comboTrackResult);
        //contRelationContract
        contRelationContract.setBoundEditor(prmtRetionBill);
        //contCustomer
        contCustomer.setBoundEditor(prmtCustomer);
        //contCommerceChance
        contCommerceChance.setBoundEditor(prmtCommerceChance);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contTrackDes
        contTrackDes.setBoundEditor(txtTrackTxt);
        //contRelationId
        contRelationId.setBoundEditor(txtRelationContract);
        //contSaleMan
        contSaleMan.setBoundEditor(prmtSaleMan);
        //contSysType
        contSysType.setBoundEditor(comboSysType);
        //contTrackType
        contTrackType.setBoundEditor(comboTrackType);
        //contMarketManage
        contMarketManage.setBoundEditor(prmtMarketManage);

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
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
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
        this.toolBar.add(btnQuestionPrint);
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
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnCreateTo);
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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("eventDate", java.util.Date.class, this.pkEventDate, "value");
		dataBinder.registerBinding("eventType", com.kingdee.eas.fdc.sellhouse.EventTypeInfo.class, this.prmtEventType, "data");
		dataBinder.registerBinding("receptionType", com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo.class, this.prmtReceptionType, "data");
		dataBinder.registerBinding("trackResult", com.kingdee.eas.fdc.sellhouse.TrackRecordEnum.class, this.comboTrackResult, "selectedItem");
		dataBinder.registerBinding("head", com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo.class, this.prmtCustomer, "data");
		dataBinder.registerBinding("commerceChance", com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo.class, this.prmtCommerceChance, "data");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("trackDes", String.class, this.txtTrackTxt, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("relationContract", String.class, this.txtRelationContract, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("saleMan", com.kingdee.eas.base.permission.UserInfo.class, this.prmtSaleMan, "data");
		dataBinder.registerBinding("sysType", com.kingdee.eas.fdc.basedata.MoneySysTypeEnum.class, this.comboSysType, "selectedItem");
		dataBinder.registerBinding("trackType", com.kingdee.eas.fdc.sellhouse.TrackRecordTypeEnum.class, this.comboTrackType, "selectedItem");
		dataBinder.registerBinding("marketManage", com.kingdee.eas.fdc.market.MarketManageInfo.class, this.prmtMarketManage, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.TrackRecordEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.TrackRecordInfo)ov;
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
		getValidateHelper().registerBindProperty("eventDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("eventType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("receptionType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("trackResult", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("head", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("commerceChance", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("trackDes", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relationContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("saleMan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sysType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("trackType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("marketManage", ValidateHelper.ON_SAVE);    		
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
     * output comboTrackResult_itemStateChanged method
     */
    protected void comboTrackResult_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtCustomer_dataChanged method
     */
    protected void prmtCustomer_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtCommerceChance_dataChanged method
     */
    protected void prmtCommerceChance_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtSellProject_dataChanged method
     */
    protected void prmtSellProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output btnAddNewBill_actionPerformed method
     */
    protected void btnAddNewBill_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtRetionBill_dataChanged method
     */
    protected void prmtRetionBill_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboSysType_itemStateChanged method
     */
    protected void comboSysType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output comboTrackType_itemStateChanged method
     */
    protected void comboTrackType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("eventDate"));
        sic.add(new SelectorItemInfo("eventType.*"));
        sic.add(new SelectorItemInfo("receptionType.*"));
        sic.add(new SelectorItemInfo("trackResult"));
        sic.add(new SelectorItemInfo("head.*"));
        sic.add(new SelectorItemInfo("commerceChance.*"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("trackDes"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("relationContract"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("saleMan.*"));
        sic.add(new SelectorItemInfo("sysType"));
        sic.add(new SelectorItemInfo("trackType"));
        sic.add(new SelectorItemInfo("marketManage.*"));
        return sic;
    }        
    	

    /**
     * output actionQuestionPrint_actionPerformed method
     */
    public void actionQuestionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionQuestionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuestionPrint() {
    	return false;
    }

    /**
     * output ActionQuestionPrint class
     */     
    protected class ActionQuestionPrint extends ItemAction {     
    
        public ActionQuestionPrint()
        {
            this(null);
        }

        public ActionQuestionPrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionQuestionPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuestionPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuestionPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTrackRecordEditUI.this, "ActionQuestionPrint", "actionQuestionPrint_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "TrackRecordEditUI");
    }




}