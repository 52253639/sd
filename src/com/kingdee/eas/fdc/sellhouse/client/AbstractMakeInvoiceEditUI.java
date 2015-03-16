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
public abstract class AbstractMakeInvoiceEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMakeInvoiceEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCheque;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomSignContractNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer chequeType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBatchNumber;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Cheque;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtUser;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoom;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomSignContractNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCustomer;
    protected com.kingdee.bos.ctrl.swing.KDComboBox KDchequeType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBatchNumber;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblReceive;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCustomer;
    protected com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractMakeInvoiceEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMakeInvoiceEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.contCheque = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomSignContractNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chequeType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBatchNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Cheque = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtRoomSignContractNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.KDchequeType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtBatchNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tblReceive = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtCustomer = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCheque.setName("contCheque");
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contAmount.setName("contAmount");
        this.contDate.setName("contDate");
        this.contUser.setName("contUser");
        this.contRoomNumber.setName("contRoomNumber");
        this.contRoomSignContractNumber.setName("contRoomSignContractNumber");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.chequeType.setName("chequeType");
        this.conStatus.setName("conStatus");
        this.contBatchNumber.setName("contBatchNumber");
        this.kDContainer1.setName("kDContainer1");
        this.contCustomer.setName("contCustomer");
        this.f7Cheque.setName("f7Cheque");
        this.txtNumber.setName("txtNumber");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.txtAmount.setName("txtAmount");
        this.pkDate.setName("pkDate");
        this.prmtUser.setName("prmtUser");
        this.prmtRoom.setName("prmtRoom");
        this.txtRoomSignContractNumber.setName("txtRoomSignContractNumber");
        this.prmtCustomer.setName("prmtCustomer");
        this.KDchequeType.setName("KDchequeType");
        this.comboStatus.setName("comboStatus");
        this.txtBatchNumber.setName("txtBatchNumber");
        this.tblReceive.setName("tblReceive");
        this.txtCustomer.setName("txtCustomer");
        // CoreUI		
        this.setPreferredSize(new Dimension(800,540));		
        this.btnAddNew.setVisible(false);		
        this.btnEdit.setVisible(false);		
        this.btnSave.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnAttachment.setVisible(false);
        // contCheque		
        this.contCheque.setBoundLabelText(resHelper.getString("contCheque.boundLabelText"));		
        this.contCheque.setBoundLabelLength(100);		
        this.contCheque.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAmount		
        this.contAmount.setBoundLabelText(resHelper.getString("contAmount.boundLabelText"));		
        this.contAmount.setBoundLabelLength(100);		
        this.contAmount.setBoundLabelUnderline(true);
        // contDate		
        this.contDate.setBoundLabelText(resHelper.getString("contDate.boundLabelText"));		
        this.contDate.setBoundLabelLength(100);		
        this.contDate.setBoundLabelUnderline(true);
        // contUser		
        this.contUser.setBoundLabelText(resHelper.getString("contUser.boundLabelText"));		
        this.contUser.setBoundLabelLength(100);		
        this.contUser.setBoundLabelUnderline(true);
        // contRoomNumber		
        this.contRoomNumber.setBoundLabelText(resHelper.getString("contRoomNumber.boundLabelText"));		
        this.contRoomNumber.setBoundLabelLength(100);		
        this.contRoomNumber.setBoundLabelUnderline(true);
        // contRoomSignContractNumber		
        this.contRoomSignContractNumber.setBoundLabelText(resHelper.getString("contRoomSignContractNumber.boundLabelText"));		
        this.contRoomSignContractNumber.setBoundLabelLength(100);		
        this.contRoomSignContractNumber.setBoundLabelUnderline(true);		
        this.contRoomSignContractNumber.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // chequeType		
        this.chequeType.setBoundLabelText(resHelper.getString("chequeType.boundLabelText"));		
        this.chequeType.setBoundLabelLength(100);		
        this.chequeType.setBoundLabelUnderline(true);
        // conStatus		
        this.conStatus.setBoundLabelText(resHelper.getString("conStatus.boundLabelText"));		
        this.conStatus.setBoundLabelLength(100);		
        this.conStatus.setBoundLabelUnderline(true);
        // contBatchNumber		
        this.contBatchNumber.setBoundLabelText(resHelper.getString("contBatchNumber.boundLabelText"));		
        this.contBatchNumber.setBoundLabelLength(100);		
        this.contBatchNumber.setBoundLabelUnderline(true);
        // kDContainer1
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);
        // f7Cheque		
        this.f7Cheque.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ChequeDetailEntryQuery");		
        this.f7Cheque.setDisplayFormat("$number$");		
        this.f7Cheque.setEditFormat("$number$");		
        this.f7Cheque.setCommitFormat("$number$");
        this.f7Cheque.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Cheque_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // kDScrollPane1
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // txtAmount		
        this.txtAmount.setDataType(1);		
        this.txtAmount.setEnabled(false);
        // pkDate
        // prmtUser		
        this.prmtUser.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtUser.setCommitFormat("$number$");		
        this.prmtUser.setEditFormat("$number$");		
        this.prmtUser.setDisplayFormat("$name$");		
        this.prmtUser.setEditable(true);
        // prmtRoom		
        this.prmtRoom.setEnabled(false);		
        this.prmtRoom.setDisplayFormat("$name$");		
        this.prmtRoom.setEditFormat("$number$");		
        this.prmtRoom.setCommitFormat("$number$");		
        this.prmtRoom.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7RoomQuery");
        // txtRoomSignContractNumber		
        this.txtRoomSignContractNumber.setEnabled(false);
        // prmtCustomer		
        this.prmtCustomer.setDisplayFormat("$name$");		
        this.prmtCustomer.setEditFormat("$number$");		
        this.prmtCustomer.setCommitFormat("$number$");		
        this.prmtCustomer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");		
        this.prmtCustomer.setEnabledMultiSelection(true);
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
        // KDchequeType		
        this.KDchequeType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum").toArray());
        this.KDchequeType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    KDchequeType_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum").toArray());		
        this.comboStatus.setEnabled(false);
        // txtBatchNumber
        // tblReceive
		String tblReceiveStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"isSelected\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"revNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"moneyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" /><t:Column t:key=\"billDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" /><t:Column t:key=\"makeInvoiceAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" /><t:Column t:key=\"receipt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" /><t:Column t:key=\"invoiceNum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" /><t:Column t:key=\"hasTransferredAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:configured=\"false\" /><t:Column t:key=\"hasRefundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:configured=\"false\" /><t:Column t:key=\"remainAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:configured=\"false\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:configured=\"false\" /><t:Column t:key=\"hasMakeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">选择</t:Cell><t:Cell t:configured=\"false\">收款单号</t:Cell><t:Cell t:configured=\"false\">款项类型</t:Cell><t:Cell t:configured=\"false\">收款金额</t:Cell><t:Cell t:configured=\"false\">收款日期</t:Cell><t:Cell t:configured=\"false\">开票金额</t:Cell><t:Cell t:configured=\"false\">收据号</t:Cell><t:Cell t:configured=\"false\">发票号</t:Cell><t:Cell t:configured=\"false\">已转金额</t:Cell><t:Cell t:configured=\"false\">已退金额</t:Cell><t:Cell t:configured=\"false\">剩余金额</t:Cell><t:Cell t:configured=\"false\">备注</t:Cell><t:Cell t:configured=\"false\">已开票金额</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblReceive.setFormatXml(resHelper.translateString("tblReceive",tblReceiveStrXML));
        this.tblReceive.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblReceive_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblReceive.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblReceive_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

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
        this.setBounds(new Rectangle(10, 10, 920, 450));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 920, 450));
        contCheque.setBounds(new Rectangle(630, 10, 270, 19));
        this.add(contCheque, new KDLayout.Constraints(630, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(630, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(630, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(321, 108, 582, 21));
        this.add(contDescription, new KDLayout.Constraints(321, 108, 582, 21, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAmount.setBounds(new Rectangle(630, 42, 270, 19));
        this.add(contAmount, new KDLayout.Constraints(630, 42, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDate.setBounds(new Rectangle(322, 42, 270, 19));
        this.add(contDate, new KDLayout.Constraints(322, 42, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contUser.setBounds(new Rectangle(14, 42, 270, 19));
        this.add(contUser, new KDLayout.Constraints(14, 42, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomNumber.setBounds(new Rectangle(14, 74, 270, 19));
        this.add(contRoomNumber, new KDLayout.Constraints(14, 74, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomSignContractNumber.setBounds(new Rectangle(39, 384, 270, 19));
        this.add(contRoomSignContractNumber, new KDLayout.Constraints(39, 384, 270, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(322, 74, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(322, 74, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chequeType.setBounds(new Rectangle(14, 10, 270, 19));
        this.add(chequeType, new KDLayout.Constraints(14, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conStatus.setBounds(new Rectangle(630, 74, 270, 19));
        this.add(conStatus, new KDLayout.Constraints(630, 74, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBatchNumber.setBounds(new Rectangle(322, 10, 270, 19));
        this.add(contBatchNumber, new KDLayout.Constraints(322, 10, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(10, 143, 899, 290));
        this.add(kDContainer1, new KDLayout.Constraints(10, 143, 899, 290, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCustomer.setBounds(new Rectangle(14, 108, 270, 19));
        this.add(contCustomer, new KDLayout.Constraints(14, 108, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCheque
        contCheque.setBoundEditor(f7Cheque);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contAmount
        contAmount.setBoundEditor(txtAmount);
        //contDate
        contDate.setBoundEditor(pkDate);
        //contUser
        contUser.setBoundEditor(prmtUser);
        //contRoomNumber
        contRoomNumber.setBoundEditor(prmtRoom);
        //contRoomSignContractNumber
        contRoomSignContractNumber.setBoundEditor(txtRoomSignContractNumber);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(prmtCustomer);
        //chequeType
        chequeType.setBoundEditor(KDchequeType);
        //conStatus
        conStatus.setBoundEditor(comboStatus);
        //contBatchNumber
        contBatchNumber.setBoundEditor(txtBatchNumber);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 143, 899, 290));        tblReceive.setBounds(new Rectangle(6, 3, 878, 253));
        kDContainer1.getContentPane().add(tblReceive, new KDLayout.Constraints(6, 3, 878, 253, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCustomer
        contCustomer.setBoundEditor(txtCustomer);

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
        this.menuBar.add(menuTool);
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
        menuEdit.add(menuItemReset);
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
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
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
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("des", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtAmount, "value");
		dataBinder.registerBinding("writtenOffTime", java.sql.Timestamp.class, this.pkDate, "value");
		dataBinder.registerBinding("writtenOffer", com.kingdee.eas.base.permission.UserInfo.class, this.prmtUser, "data");
		dataBinder.registerBinding("room", com.kingdee.eas.fdc.sellhouse.RoomInfo.class, this.prmtRoom, "data");
		dataBinder.registerBinding("cheque.chequeType", com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum.class, this.KDchequeType, "selectedItem");
		dataBinder.registerBinding("status", com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("cheque.chequeBathNumber", String.class, this.txtBatchNumber, "text");
		dataBinder.registerBinding("chequeCustomer", String.class, this.txtCustomer, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.MakeInvoiceEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo)ov;
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
	 * ????????У??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("des", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("writtenOffTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("writtenOffer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("room", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cheque.chequeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cheque.chequeBathNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chequeCustomer", ValidateHelper.ON_SAVE);    		
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
        }
    }

    /**
     * output f7Cheque_dataChanged method
     */
    protected void f7Cheque_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtCustomer_dataChanged method
     */
    protected void prmtCustomer_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your c ode here
    }

    /**
     * output KDchequeType_actionPerformed method
     */
    protected void KDchequeType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblReceive_tableClicked method
     */
    protected void tblReceive_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblReceive_editStopped method
     */
    protected void tblReceive_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("des"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("writtenOffTime"));
        sic.add(new SelectorItemInfo("writtenOffer.*"));
        sic.add(new SelectorItemInfo("room.*"));
        sic.add(new SelectorItemInfo("cheque.chequeType"));
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("cheque.chequeBathNumber"));
        sic.add(new SelectorItemInfo("chequeCustomer"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "MakeInvoiceEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}