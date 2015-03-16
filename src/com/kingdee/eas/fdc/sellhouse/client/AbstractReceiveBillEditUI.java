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
public abstract class AbstractReceiveBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractReceiveBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurchase;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReceiveAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Purchase;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtReceiveAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Currency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBillDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBillDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Customer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Creator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Auditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellOrder;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSalesman;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCheque;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Salesman;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDes;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellOrder;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRec;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReceiptNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtReceiptNumber;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChoose;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBelongSys;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcTenancyContract;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7TenancyContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Room;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDComboBox ComboGatheringOjbect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcAccessorialResource;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Accessorial;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Cheque;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsSettlement;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelete;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtInvoiceAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInvoice;
    protected com.kingdee.eas.framework.CoreBillBaseInfo editData = null;
    protected ActionRec actionRec = null;
    /**
     * output class constructor
     */
    public AbstractReceiveBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractReceiveBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionRec
        this.actionRec = new ActionRec(this);
        getActionManager().registerAction("actionRec", actionRec);
         this.actionRec.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contPurchase = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReceiveAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Purchase = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtReceiveAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7Currency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBillDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBillDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Customer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Creator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Auditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellOrder = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSalesman = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCheque = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Salesman = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDes = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7SellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7SellOrder = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnRec = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contReceiptNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtReceiptNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnChoose = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboBelongSys = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.lcTenancyContract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7TenancyContract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.lcRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Room = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.ComboGatheringOjbect = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.lcAccessorialResource = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Accessorial = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Cheque = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cbIsSettlement = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.btnDelete = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtInvoiceAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtInvoice = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contPurchase.setName("contPurchase");
        this.contReceiveAmount.setName("contReceiveAmount");
        this.contCurrency.setName("contCurrency");
        this.f7Purchase.setName("f7Purchase");
        this.txtReceiveAmount.setName("txtReceiveAmount");
        this.f7Currency.setName("f7Currency");
        this.contNumber.setName("contNumber");
        this.contCustomer.setName("contCustomer");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.contCreateTime.setName("contCreateTime");
        this.contBillDate.setName("contBillDate");
        this.txtNumber.setName("txtNumber");
        this.pkBillDate.setName("pkBillDate");
        this.f7Customer.setName("f7Customer");
        this.contCreator.setName("contCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.f7Creator.setName("f7Creator");
        this.pkAuditTime.setName("pkAuditTime");
        this.f7Auditor.setName("f7Auditor");
        this.contSellProject.setName("contSellProject");
        this.contSellOrder.setName("contSellOrder");
        this.contSalesman.setName("contSalesman");
        this.contDes.setName("contDes");
        this.contCheque.setName("contCheque");
        this.f7Salesman.setName("f7Salesman");
        this.txtDes.setName("txtDes");
        this.f7SellProject.setName("f7SellProject");
        this.f7SellOrder.setName("f7SellOrder");
        this.btnRec.setName("btnRec");
        this.contReceiptNumber.setName("contReceiptNumber");
        this.txtReceiptNumber.setName("txtReceiptNumber");
        this.kDTable1.setName("kDTable1");
        this.btnChoose.setName("btnChoose");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.comboBelongSys.setName("comboBelongSys");
        this.lcTenancyContract.setName("lcTenancyContract");
        this.f7TenancyContract.setName("f7TenancyContract");
        this.lcRoom.setName("lcRoom");
        this.f7Room.setName("f7Room");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.ComboGatheringOjbect.setName("ComboGatheringOjbect");
        this.lcAccessorialResource.setName("lcAccessorialResource");
        this.f7Accessorial.setName("f7Accessorial");
        this.f7Cheque.setName("f7Cheque");
        this.cbIsSettlement.setName("cbIsSettlement");
        this.btnDelete.setName("btnDelete");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.txtInvoiceAmount.setName("txtInvoiceAmount");
        this.txtInvoice.setName("txtInvoice");
        // CoreUI		
        this.setPreferredSize(new Dimension(960,500));		
        this.btnVoucher.setVisible(true);		
        this.MenuItemVoucher.setVisible(true);		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // contPurchase		
        this.contPurchase.setBoundLabelText(resHelper.getString("contPurchase.boundLabelText"));		
        this.contPurchase.setBoundLabelLength(100);		
        this.contPurchase.setBoundLabelUnderline(true);
        // contReceiveAmount		
        this.contReceiveAmount.setBoundLabelText(resHelper.getString("contReceiveAmount.boundLabelText"));		
        this.contReceiveAmount.setBoundLabelLength(100);		
        this.contReceiveAmount.setBoundLabelUnderline(true);
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);
        // f7Purchase		
        this.f7Purchase.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.PurchaseQuery");		
        this.f7Purchase.setDisplayFormat("$number$");		
        this.f7Purchase.setEditFormat("$number$");		
        this.f7Purchase.setCommitFormat("$number$");
        this.f7Purchase.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Purchase_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtReceiveAmount		
        this.txtReceiveAmount.setDataType(1);		
        this.txtReceiveAmount.setEnabled(false);
        // f7Currency		
        this.f7Currency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.f7Currency.setDisplayFormat("$name$");		
        this.f7Currency.setEditFormat("$number$");		
        this.f7Currency.setCommitFormat("$number$");
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contBillDate		
        this.contBillDate.setBoundLabelText(resHelper.getString("contBillDate.boundLabelText"));		
        this.contBillDate.setBoundLabelLength(100);		
        this.contBillDate.setBoundLabelUnderline(true);
        // txtNumber
        // pkBillDate
        // f7Customer		
        this.f7Customer.setDisplayFormat("$name$");		
        this.f7Customer.setEditFormat("$number$");		
        this.f7Customer.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7CustomerQuery");		
        this.f7Customer.setCommitFormat("$number$");		
        this.f7Customer.setEnabledMultiSelection(true);
        this.f7Customer.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Customer_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // f7Creator		
        this.f7Creator.setEnabled(false);		
        this.f7Creator.setDisplayFormat("$name$");
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // f7Auditor		
        this.f7Auditor.setEnabled(false);		
        this.f7Auditor.setDisplayFormat("$name$");
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelUnderline(true);		
        this.contSellProject.setBoundLabelLength(100);
        // contSellOrder		
        this.contSellOrder.setBoundLabelText(resHelper.getString("contSellOrder.boundLabelText"));		
        this.contSellOrder.setBoundLabelLength(100);		
        this.contSellOrder.setBoundLabelUnderline(true);
        // contSalesman		
        this.contSalesman.setBoundLabelText(resHelper.getString("contSalesman.boundLabelText"));		
        this.contSalesman.setBoundLabelLength(100);		
        this.contSalesman.setBoundLabelUnderline(true);
        // contDes		
        this.contDes.setBoundLabelText(resHelper.getString("contDes.boundLabelText"));		
        this.contDes.setBoundLabelUnderline(true);		
        this.contDes.setBoundLabelLength(100);
        // contCheque		
        this.contCheque.setBoundLabelText(resHelper.getString("contCheque.boundLabelText"));		
        this.contCheque.setBoundLabelUnderline(true);		
        this.contCheque.setBoundLabelLength(100);
        // f7Salesman		
        this.f7Salesman.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7Salesman.setDisplayFormat("$name$");		
        this.f7Salesman.setEditFormat("$number$");		
        this.f7Salesman.setCommitFormat("$number$");
        // txtDes		
        this.txtDes.setMaxLength(80);
        // f7SellProject		
        this.f7SellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.f7SellProject.setEnabled(false);
        this.f7SellProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7SellProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7SellOrder		
        this.f7SellOrder.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellOrderQuery");
        // btnRec
        this.btnRec.setAction((IItemAction)ActionProxyFactory.getProxy(actionRec, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRec.setText(resHelper.getString("btnRec.text"));		
        this.btnRec.setToolTipText(resHelper.getString("btnRec.toolTipText"));		
        this.btnRec.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_recieversetting"));
        // contReceiptNumber		
        this.contReceiptNumber.setBoundLabelText(resHelper.getString("contReceiptNumber.boundLabelText"));		
        this.contReceiptNumber.setBoundLabelLength(100);		
        this.contReceiptNumber.setBoundLabelUnderline(true);
        // txtReceiptNumber		
        this.txtReceiptNumber.setMaxLength(80);
        // kDTable1		
        this.kDTable1.setFormatXml(resHelper.getString("kDTable1.formatXml"));
        this.kDTable1.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    kDTable1_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnChoose		
        this.btnChoose.setText(resHelper.getString("btnChoose.text"));
        this.btnChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnChoose_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // comboBelongSys		
        this.comboBelongSys.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.MoneySysTypeEnum").toArray());
        this.comboBelongSys.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    comboBelongSys_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.comboBelongSys.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboBelongSys_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // lcTenancyContract		
        this.lcTenancyContract.setBoundLabelText(resHelper.getString("lcTenancyContract.boundLabelText"));		
        this.lcTenancyContract.setBoundLabelLength(100);		
        this.lcTenancyContract.setBoundLabelUnderline(true);
        // f7TenancyContract		
        this.f7TenancyContract.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillQuery");		
        this.f7TenancyContract.setDisplayFormat("$name$");		
        this.f7TenancyContract.setEditFormat("$number$");		
        this.f7TenancyContract.setCommitFormat("$number$");		
        this.f7TenancyContract.setRequired(true);
        this.f7TenancyContract.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7TenancyContract_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // lcRoom		
        this.lcRoom.setBoundLabelText(resHelper.getString("lcRoom.boundLabelText"));		
        this.lcRoom.setBoundLabelLength(100);		
        this.lcRoom.setBoundLabelUnderline(true);
        // f7Room		
        this.f7Room.setRequired(true);
        this.f7Room.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Room_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.f7Room.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    f7Room_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.f7Room.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    f7Room_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // ComboGatheringOjbect		
        this.ComboGatheringOjbect.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum").toArray());		
        this.ComboGatheringOjbect.setRequired(true);
        this.ComboGatheringOjbect.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    ComboGatheringOjbect_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // lcAccessorialResource		
        this.lcAccessorialResource.setBoundLabelText(resHelper.getString("lcAccessorialResource.boundLabelText"));		
        this.lcAccessorialResource.setBoundLabelLength(100);		
        this.lcAccessorialResource.setBoundLabelUnderline(true);
        // f7Accessorial		
        this.f7Accessorial.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.AttachResourceQuery");
        // f7Cheque		
        this.f7Cheque.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ChequeQuery");		
        this.f7Cheque.setCommitFormat("$number$");		
        this.f7Cheque.setDisplayFormat("$number$");		
        this.f7Cheque.setEditFormat("$number$");
        // cbIsSettlement		
        this.cbIsSettlement.setText(resHelper.getString("cbIsSettlement.text"));		
        this.cbIsSettlement.setEnabled(false);		
        this.cbIsSettlement.setBackground(new java.awt.Color(255,0,0));		
        this.cbIsSettlement.setForeground(new java.awt.Color(255,0,0));
        // btnDelete		
        this.btnDelete.setText(resHelper.getString("btnDelete.text"));
        this.btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelete_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // txtInvoiceAmount		
        this.txtInvoiceAmount.setEnabled(false);
        // txtInvoice		
        this.txtInvoice.setEnabled(false);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 960, 500));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 960, 500));
        contPurchase.setBounds(new Rectangle(344, 79, 270, 19));
        this.add(contPurchase, new KDLayout.Constraints(344, 79, 270, 19, 0));
        contReceiveAmount.setBounds(new Rectangle(12, 108, 270, 19));
        this.add(contReceiveAmount, new KDLayout.Constraints(12, 108, 270, 19, 0));
        contCurrency.setBounds(new Rectangle(344, 109, 270, 19));
        this.add(contCurrency, new KDLayout.Constraints(344, 109, 270, 19, 0));
        contNumber.setBounds(new Rectangle(12, 12, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(12, 12, 270, 19, 0));
        contCustomer.setBounds(new Rectangle(675, 83, 270, 19));
        this.add(contCustomer, new KDLayout.Constraints(675, 83, 270, 19, 0));
        contAuditor.setBounds(new Rectangle(343, 204, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(343, 204, 270, 19, 0));
        contAuditTime.setBounds(new Rectangle(12, 204, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(12, 204, 270, 19, 0));
        contCreateTime.setBounds(new Rectangle(11, 237, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(11, 237, 270, 19, 0));
        contBillDate.setBounds(new Rectangle(344, 12, 270, 19));
        this.add(contBillDate, new KDLayout.Constraints(344, 12, 270, 19, 0));
        contCreator.setBounds(new Rectangle(342, 237, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(342, 237, 270, 19, 0));
        contSellProject.setBounds(new Rectangle(12, 44, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(12, 44, 270, 19, 0));
        contSellOrder.setBounds(new Rectangle(675, 46, 270, 19));
        this.add(contSellOrder, new KDLayout.Constraints(675, 46, 270, 19, 0));
        contSalesman.setBounds(new Rectangle(677, 204, 270, 19));
        this.add(contSalesman, new KDLayout.Constraints(677, 204, 270, 19, 0));
        contDes.setBounds(new Rectangle(11, 172, 934, 19));
        this.add(contDes, new KDLayout.Constraints(11, 172, 934, 19, 0));
        contCheque.setBounds(new Rectangle(12, 140, 270, 19));
        this.add(contCheque, new KDLayout.Constraints(12, 140, 270, 19, 0));
        contReceiptNumber.setBounds(new Rectangle(12, 140, 270, 19));
        this.add(contReceiptNumber, new KDLayout.Constraints(12, 140, 270, 19, 0));
        kDTable1.setBounds(new Rectangle(2, 265, 956, 231));
        this.add(kDTable1, new KDLayout.Constraints(2, 265, 956, 231, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnChoose.setBounds(new Rectangle(781, 236, 70, 19));
        this.add(btnChoose, new KDLayout.Constraints(781, 236, 70, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(675, 12, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(675, 12, 270, 19, 0));
        lcTenancyContract.setBounds(new Rectangle(344, 79, 270, 19));
        this.add(lcTenancyContract, new KDLayout.Constraints(344, 79, 270, 19, 0));
        lcRoom.setBounds(new Rectangle(11, 75, 270, 19));
        this.add(lcRoom, new KDLayout.Constraints(11, 75, 270, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(344, 46, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(344, 46, 270, 19, 0));
        lcAccessorialResource.setBounds(new Rectangle(11, 75, 270, 19));
        this.add(lcAccessorialResource, new KDLayout.Constraints(11, 75, 270, 19, 0));
        cbIsSettlement.setBounds(new Rectangle(677, 238, 92, 19));
        this.add(cbIsSettlement, new KDLayout.Constraints(677, 238, 92, 19, 0));
        btnDelete.setBounds(new Rectangle(877, 237, 70, 19));
        this.add(btnDelete, new KDLayout.Constraints(877, 237, 70, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(343, 141, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(343, 141, 270, 19, 0));
        kDLabelContainer4.setBounds(new Rectangle(673, 139, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(673, 139, 270, 19, 0));
        //contPurchase
        contPurchase.setBoundEditor(f7Purchase);
        //contReceiveAmount
        contReceiveAmount.setBoundEditor(txtReceiveAmount);
        //contCurrency
        contCurrency.setBoundEditor(f7Currency);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contCustomer
        contCustomer.setBoundEditor(f7Customer);
        //contAuditor
        contAuditor.setBoundEditor(f7Auditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contBillDate
        contBillDate.setBoundEditor(pkBillDate);
        //contCreator
        contCreator.setBoundEditor(f7Creator);
        //contSellProject
        contSellProject.setBoundEditor(f7SellProject);
        //contSellOrder
        contSellOrder.setBoundEditor(f7SellOrder);
        //contSalesman
        contSalesman.setBoundEditor(f7Salesman);
        //contDes
        contDes.setBoundEditor(txtDes);
        //contCheque
        contCheque.setBoundEditor(f7Cheque);
        //contReceiptNumber
        contReceiptNumber.setBoundEditor(txtReceiptNumber);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(comboBelongSys);
        //lcTenancyContract
        lcTenancyContract.setBoundEditor(f7TenancyContract);
        //lcRoom
        lcRoom.setBoundEditor(f7Room);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(ComboGatheringOjbect);
        //lcAccessorialResource
        lcAccessorialResource.setBoundEditor(f7Accessorial);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtInvoice);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtInvoiceAmount);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        //menuTable1
        menuTable1.add(menuItemAddLine);
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
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnRec);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnCalculator);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_ADDNEW, this.btnChoose, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.btnChoose, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.btnChoose, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.btnChoose, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.ReceiveBillEditUIHandler";
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
		            this.btnChoose.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.btnChoose.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.btnChoose.setEnabled(false);
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
		            this.btnChoose.setEnabled(false);
        }
    }

    /**
     * output f7Purchase_dataChanged method
     */
    protected void f7Purchase_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7Customer_dataChanged method
     */
    protected void f7Customer_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7SellProject_dataChanged method
     */
    protected void f7SellProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kDTable1_activeCellChanged method
     */
    protected void kDTable1_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output btnChoose_actionPerformed method
     */
    protected void btnChoose_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboBelongSys_actionPerformed method
     */
    protected void comboBelongSys_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboBelongSys_itemStateChanged method
     */
    protected void comboBelongSys_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output f7TenancyContract_dataChanged method
     */
    protected void f7TenancyContract_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7Room_dataChanged method
     */
    protected void f7Room_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7Room_willCommit method
     */
    protected void f7Room_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output f7Room_willShow method
     */
    protected void f7Room_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output ComboGatheringOjbect_itemStateChanged method
     */
    protected void ComboGatheringOjbect_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output btnDelete_actionPerformed method
     */
    protected void btnDelete_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
     * output actionRec_actionPerformed method
     */
    public void actionRec_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionRec(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRec() {
    	return false;
    }

    /**
     * output ActionRec class
     */
    protected class ActionRec extends ItemAction
    {
        public ActionRec()
        {
            this(null);
        }

        public ActionRec(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRec.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRec.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRec.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReceiveBillEditUI.this, "ActionRec", "actionRec_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ReceiveBillEditUI");
    }




}