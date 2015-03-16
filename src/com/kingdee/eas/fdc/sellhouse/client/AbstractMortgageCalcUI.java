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
public abstract class AbstractMortgageCalcUI extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMortgageCalcUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer txtType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblloanType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblMoange;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblrate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblare;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblMoageYear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblreserveRate;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCalc;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClear;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblMonthTwo;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblLoanTotalMoney;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblShangYeDaikuan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblCalcType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lbibenchek;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer blifl;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbcType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbcLoanType;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtPrice;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbcLoan;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtRate;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtTotal;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtTotalLoan;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtReturn;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtLixi;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtShouqi;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtLoanMonth;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtMonthReturn;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtArea;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbcMonageYear;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtreserveRate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tbMain;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtLoanTotalMoney;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtShangyeDaikuan;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtGongjiJinDaikuan;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbcCalcType;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField texInterest;
    protected com.kingdee.bos.ctrl.swing.KDSpinner numfloating;
    protected com.kingdee.eas.framework.CoreBillBaseInfo editData = null;
    protected ActionCalc actionCalc = null;
    protected ActionClear actionClear = null;
    /**
     * output class constructor
     */
    public AbstractMortgageCalcUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMortgageCalcUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionCalc
        this.actionCalc = new ActionCalc(this);
        getActionManager().registerAction("actionCalc", actionCalc);
         this.actionCalc.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClear
        this.actionClear = new ActionClear(this);
        getActionManager().registerAction("actionClear", actionClear);
         this.actionClear.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.txtType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblloanType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblMoange = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblrate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblare = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblMoageYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblreserveRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.btnCalc = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClear = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblMonthTwo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblLoanTotalMoney = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblShangYeDaikuan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblCalcType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lbibenchek = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.blifl = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbcType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cbcLoanType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtPrice = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.cbcLoan = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtRate = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtTotal = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtTotalLoan = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtReturn = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtLixi = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtShouqi = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtLoanMonth = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtMonthReturn = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtArea = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.cbcMonageYear = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtreserveRate = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.tbMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtLoanTotalMoney = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtShangyeDaikuan = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtGongjiJinDaikuan = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.cbcCalcType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.texInterest = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.numfloating = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtType.setName("txtType");
        this.lblloanType.setName("lblloanType");
        this.lblPrice.setName("lblPrice");
        this.lblMoange.setName("lblMoange");
        this.lblrate.setName("lblrate");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.lblare.setName("lblare");
        this.lblMoageYear.setName("lblMoageYear");
        this.lblreserveRate.setName("lblreserveRate");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.btnCalc.setName("btnCalc");
        this.btnClear.setName("btnClear");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.lblMonthTwo.setName("lblMonthTwo");
        this.kDLabel3.setName("kDLabel3");
        this.lblLoanTotalMoney.setName("lblLoanTotalMoney");
        this.lblShangYeDaikuan.setName("lblShangYeDaikuan");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.lblCalcType.setName("lblCalcType");
        this.lbibenchek.setName("lbibenchek");
        this.blifl.setName("blifl");
        this.cbcType.setName("cbcType");
        this.cbcLoanType.setName("cbcLoanType");
        this.txtPrice.setName("txtPrice");
        this.cbcLoan.setName("cbcLoan");
        this.txtRate.setName("txtRate");
        this.txtTotal.setName("txtTotal");
        this.txtTotalLoan.setName("txtTotalLoan");
        this.txtReturn.setName("txtReturn");
        this.txtLixi.setName("txtLixi");
        this.txtShouqi.setName("txtShouqi");
        this.txtLoanMonth.setName("txtLoanMonth");
        this.txtMonthReturn.setName("txtMonthReturn");
        this.txtArea.setName("txtArea");
        this.cbcMonageYear.setName("cbcMonageYear");
        this.txtreserveRate.setName("txtreserveRate");
        this.tbMain.setName("tbMain");
        this.txtLoanTotalMoney.setName("txtLoanTotalMoney");
        this.txtShangyeDaikuan.setName("txtShangyeDaikuan");
        this.txtGongjiJinDaikuan.setName("txtGongjiJinDaikuan");
        this.cbcCalcType.setName("cbcCalcType");
        this.texInterest.setName("texInterest");
        this.numfloating.setName("numfloating");
        // CoreUI
        // txtType		
        this.txtType.setBoundLabelText(resHelper.getString("txtType.boundLabelText"));		
        this.txtType.setBoundLabelLength(100);		
        this.txtType.setBoundLabelUnderline(true);
        // lblloanType		
        this.lblloanType.setBoundLabelText(resHelper.getString("lblloanType.boundLabelText"));		
        this.lblloanType.setBoundLabelLength(100);		
        this.lblloanType.setBoundLabelUnderline(true);
        // lblPrice		
        this.lblPrice.setBoundLabelText(resHelper.getString("lblPrice.boundLabelText"));		
        this.lblPrice.setBoundLabelLength(100);		
        this.lblPrice.setBoundLabelUnderline(true);
        // lblMoange		
        this.lblMoange.setBoundLabelText(resHelper.getString("lblMoange.boundLabelText"));		
        this.lblMoange.setBoundLabelLength(100);		
        this.lblMoange.setBoundLabelUnderline(true);
        // lblrate		
        this.lblrate.setBoundLabelText(resHelper.getString("lblrate.boundLabelText"));		
        this.lblrate.setBoundLabelLength(100);		
        this.lblrate.setBoundLabelUnderline(true);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(100);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);
        // lblare		
        this.lblare.setBoundLabelText(resHelper.getString("lblare.boundLabelText"));		
        this.lblare.setBoundLabelLength(100);		
        this.lblare.setBoundLabelUnderline(true);
        // lblMoageYear		
        this.lblMoageYear.setBoundLabelText(resHelper.getString("lblMoageYear.boundLabelText"));		
        this.lblMoageYear.setBoundLabelLength(100);		
        this.lblMoageYear.setBoundLabelUnderline(true);
        // lblreserveRate		
        this.lblreserveRate.setBoundLabelText(resHelper.getString("lblreserveRate.boundLabelText"));		
        this.lblreserveRate.setBoundLabelLength(100);		
        this.lblreserveRate.setBoundLabelUnderline(true);
        // kDScrollPane1
        // btnCalc
        this.btnCalc.setAction((IItemAction)ActionProxyFactory.getProxy(actionCalc, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCalc.setText(resHelper.getString("btnCalc.text"));
        // btnClear
        this.btnClear.setAction((IItemAction)ActionProxyFactory.getProxy(actionClear, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClear.setText(resHelper.getString("btnClear.text"));
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // lblMonthTwo		
        this.lblMonthTwo.setBoundLabelText(resHelper.getString("lblMonthTwo.boundLabelText"));		
        this.lblMonthTwo.setBoundLabelUnderline(true);		
        this.lblMonthTwo.setBoundLabelLength(300);
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // lblLoanTotalMoney		
        this.lblLoanTotalMoney.setBoundLabelText(resHelper.getString("lblLoanTotalMoney.boundLabelText"));		
        this.lblLoanTotalMoney.setBoundLabelLength(100);		
        this.lblLoanTotalMoney.setBoundLabelUnderline(true);
        // lblShangYeDaikuan		
        this.lblShangYeDaikuan.setBoundLabelText(resHelper.getString("lblShangYeDaikuan.boundLabelText"));		
        this.lblShangYeDaikuan.setBoundLabelLength(100);		
        this.lblShangYeDaikuan.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // lblCalcType		
        this.lblCalcType.setBoundLabelText(resHelper.getString("lblCalcType.boundLabelText"));		
        this.lblCalcType.setBoundLabelLength(100);		
        this.lblCalcType.setBoundLabelUnderline(true);
        // lbibenchek		
        this.lbibenchek.setBoundLabelText(resHelper.getString("lbibenchek.boundLabelText"));		
        this.lbibenchek.setBoundLabelUnderline(true);		
        this.lbibenchek.setBoundLabelLength(100);
        // blifl		
        this.blifl.setBoundLabelText(resHelper.getString("blifl.boundLabelText"));		
        this.blifl.setBoundLabelLength(100);		
        this.blifl.setBoundLabelUnderline(true);
        // cbcType		
        this.cbcType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.PaymentMethodEnum").toArray());
        this.cbcType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cbcType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cbcLoanType		
        this.cbcLoanType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.LoanTypeEnum").toArray());
        this.cbcLoanType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cbcLoanType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtPrice		
        this.txtPrice.setDataType(6);		
        this.txtPrice.setPrecision(2);		
        this.txtPrice.setRoundingMode(2);
        // cbcLoan		
        this.cbcLoan.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.PercentageEnum").toArray());
        // txtRate		
        this.txtRate.setDataType(6);		
        this.txtRate.setRoundingMode(2);		
        this.txtRate.setPrecision(2);
        this.txtRate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtRe_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtTotal		
        this.txtTotal.setDataType(6);		
        this.txtTotal.setRoundingMode(2);		
        this.txtTotal.setPrecision(2);
        // txtTotalLoan		
        this.txtTotalLoan.setPrecision(2);		
        this.txtTotalLoan.setRoundingMode(2);		
        this.txtTotalLoan.setDataType(6);
        // txtReturn		
        this.txtReturn.setDataType(6);		
        this.txtReturn.setPrecision(2);		
        this.txtReturn.setRoundingMode(2);
        // txtLixi		
        this.txtLixi.setRoundingMode(2);		
        this.txtLixi.setPrecision(2);		
        this.txtLixi.setDataType(6);
        // txtShouqi		
        this.txtShouqi.setRoundingMode(2);		
        this.txtShouqi.setPrecision(2);		
        this.txtShouqi.setDataType(6);
        // txtLoanMonth		
        this.txtLoanMonth.setDataType(6);		
        this.txtLoanMonth.setPrecision(2);		
        this.txtLoanMonth.setRoundingMode(2);
        // txtMonthReturn		
        this.txtMonthReturn.setRoundingMode(2);		
        this.txtMonthReturn.setPrecision(2);		
        this.txtMonthReturn.setDataType(6);
        // txtArea		
        this.txtArea.setDataType(6);		
        this.txtArea.setRoundingMode(2);		
        this.txtArea.setPrecision(2);
        // cbcMonageYear		
        this.cbcMonageYear.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.MortgageYearEnum").toArray());
        // txtreserveRate		
        this.txtreserveRate.setRoundingMode(2);		
        this.txtreserveRate.setPrecision(2);		
        this.txtreserveRate.setDataType(6);
        // tbMain
		String tbMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>####.##</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"qishu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{qishu}</t:Cell><t:Cell>$Resource{price}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tbMain.setFormatXml(resHelper.translateString("tbMain",tbMainStrXML));

        

        // txtLoanTotalMoney		
        this.txtLoanTotalMoney.setDataType(6);		
        this.txtLoanTotalMoney.setRoundingMode(2);		
        this.txtLoanTotalMoney.setPrecision(2);
        // txtShangyeDaikuan		
        this.txtShangyeDaikuan.setDataType(6);		
        this.txtShangyeDaikuan.setRoundingMode(2);		
        this.txtShangyeDaikuan.setPrecision(2);
        // txtGongjiJinDaikuan		
        this.txtGongjiJinDaikuan.setDataType(6);		
        this.txtGongjiJinDaikuan.setRoundingMode(2);		
        this.txtGongjiJinDaikuan.setPrecision(2);
        // cbcCalcType		
        this.cbcCalcType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.LoanCalcTypeEnum").toArray());
        this.cbcCalcType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cbcCalcType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // texInterest
        // numfloating
        this.numfloating.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    numfloatingstate_Changed(e);
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
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 630, 630));
        this.setLayout(null);
        kDSeparator6.setBounds(new Rectangle(20, 236, 591, 10));
        this.add(kDSeparator6, null);
        txtType.setBounds(new Rectangle(21, 32, 270, 19));
        this.add(txtType, null);
        lblloanType.setBounds(new Rectangle(335, 32, 270, 19));
        this.add(lblloanType, null);
        lblPrice.setBounds(new Rectangle(21, 189, 226, 19));
        this.add(lblPrice, null);
        lblMoange.setBounds(new Rectangle(21, 107, 270, 19));
        this.add(lblMoange, null);
        lblrate.setBounds(new Rectangle(21, 147, 270, 19));
        this.add(lblrate, null);
        kDLabelContainer7.setBounds(new Rectangle(21, 266, 270, 19));
        this.add(kDLabelContainer7, null);
        kDLabelContainer8.setBounds(new Rectangle(21, 320, 270, 19));
        this.add(kDLabelContainer8, null);
        kDLabelContainer9.setBounds(new Rectangle(21, 362, 270, 19));
        this.add(kDLabelContainer9, null);
        kDLabelContainer10.setBounds(new Rectangle(21, 410, 270, 19));
        this.add(kDLabelContainer10, null);
        kDLabelContainer11.setBounds(new Rectangle(21, 461, 270, 19));
        this.add(kDLabelContainer11, null);
        kDLabelContainer12.setBounds(new Rectangle(21, 504, 270, 19));
        this.add(kDLabelContainer12, null);
        kDLabelContainer13.setBounds(new Rectangle(21, 542, 270, 19));
        this.add(kDLabelContainer13, null);
        lblare.setBounds(new Rectangle(21, 189, 230, 19));
        this.add(lblare, null);
        lblMoageYear.setBounds(new Rectangle(335, 69, 270, 19));
        this.add(lblMoageYear, null);
        lblreserveRate.setBounds(new Rectangle(21, 107, 270, 19));
        this.add(lblreserveRate, null);
        kDScrollPane1.setBounds(new Rectangle(312, 264, 295, 299));
        this.add(kDScrollPane1, null);
        btnCalc.setBounds(new Rectangle(136, 588, 93, 26));
        this.add(btnCalc, null);
        btnClear.setBounds(new Rectangle(354, 588, 93, 26));
        this.add(btnClear, null);
        kDLabel1.setBounds(new Rectangle(254, 189, 46, 19));
        this.add(kDLabel1, null);
        kDLabel2.setBounds(new Rectangle(21, 189, 40, 19));
        this.add(kDLabel2, null);
        lblMonthTwo.setBounds(new Rectangle(21, 534, 293, 27));
        this.add(lblMonthTwo, null);
        kDLabel3.setBounds(new Rectangle(293, 221, 100, 19));
        this.add(kDLabel3, null);
        lblLoanTotalMoney.setBounds(new Rectangle(21, 69, 270, 19));
        this.add(lblLoanTotalMoney, null);
        lblShangYeDaikuan.setBounds(new Rectangle(21, 69, 270, 19));
        this.add(lblShangYeDaikuan, null);
        kDLabelContainer1.setBounds(new Rectangle(21, 107, 270, 19));
        this.add(kDLabelContainer1, null);
        lblCalcType.setBounds(new Rectangle(21, 189, 270, 19));
        this.add(lblCalcType, null);
        lbibenchek.setBounds(new Rectangle(335, 107, 270, 19));
        this.add(lbibenchek, null);
        blifl.setBounds(new Rectangle(335, 147, 270, 19));
        this.add(blifl, null);
        //txtType
        txtType.setBoundEditor(cbcType);
        //lblloanType
        lblloanType.setBoundEditor(cbcLoanType);
        //lblPrice
        lblPrice.setBoundEditor(txtPrice);
        //lblMoange
        lblMoange.setBoundEditor(cbcLoan);
        //lblrate
        lblrate.setBoundEditor(txtRate);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(txtTotal);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtTotalLoan);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(txtReturn);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(txtLixi);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(txtShouqi);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(txtLoanMonth);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(txtMonthReturn);
        //lblare
        lblare.setBoundEditor(txtArea);
        //lblMoageYear
        lblMoageYear.setBoundEditor(cbcMonageYear);
        //lblreserveRate
        lblreserveRate.setBoundEditor(txtreserveRate);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(tbMain, null);
        //lblLoanTotalMoney
        lblLoanTotalMoney.setBoundEditor(txtLoanTotalMoney);
        //lblShangYeDaikuan
        lblShangYeDaikuan.setBoundEditor(txtShangyeDaikuan);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtGongjiJinDaikuan);
        //lblCalcType
        lblCalcType.setBoundEditor(cbcCalcType);
        //lbibenchek
        lbibenchek.setBoundEditor(texInterest);
        //blifl
        blifl.setBoundEditor(numfloating);

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
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.MortgageCalcUIHandler";
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
        this.editData = (com.kingdee.eas.framework.CoreBillBaseInfo)ov;
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
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output cbcType_itemStateChanged method
     */
    protected void cbcType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output cbcLoanType_itemStateChanged method
     */
    protected void cbcLoanType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output txtRe_dataChanged method
     */
    protected void txtRe_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output cbcCalcType_itemStateChanged method
     */
    protected void cbcCalcType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output numfloatingstate_Changed method
     */
    protected void numfloatingstate_Changed(javax.swing.event.ChangeEvent e) throws Exception
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
     * output actionCalc_actionPerformed method
     */
    public void actionCalc_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClear_actionPerformed method
     */
    public void actionClear_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionCalc(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCalc() {
    	return false;
    }
	public RequestContext prepareActionClear(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClear() {
    	return false;
    }

    /**
     * output ActionCalc class
     */     
    protected class ActionCalc extends ItemAction {     
    
        public ActionCalc()
        {
            this(null);
        }

        public ActionCalc(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCalc.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalc.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCalc.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMortgageCalcUI.this, "ActionCalc", "actionCalc_actionPerformed", e);
        }
    }

    /**
     * output ActionClear class
     */     
    protected class ActionClear extends ItemAction {     
    
        public ActionClear()
        {
            this(null);
        }

        public ActionClear(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionClear.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClear.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClear.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMortgageCalcUI.this, "ActionClear", "actionClear_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "MortgageCalcUI");
    }




}