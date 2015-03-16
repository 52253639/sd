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
public abstract class AbstractQuitRoomEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractQuitRoomEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuitDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuitReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuitRoomReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuitRoomState;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkQuitDate;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtQuitReason;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDPanel tabFee;
    protected com.kingdee.bos.ctrl.swing.KDPanel tabAdjust;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSet;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCanRefundment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFeeAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFeeMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFeeAccount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTolActAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCanRefundment;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFeeAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7FeeMoneyDefine;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7FeeAccount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsAdjust;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsSellByRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtRemark;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOldBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOldRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNewRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNewBuildingPrice;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Room;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomNo;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7QuitRoomReason;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comQuitRoomState;
    protected com.kingdee.eas.framework.CoreBillBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractQuitRoomEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractQuitRoomEditUI.class.getName());
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
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contQuitDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQuitReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane2 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQuitRoomReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQuitRoomState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkQuitDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtQuitReason = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tabFee = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tabAdjust = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tblSet = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnCanRefundment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contFeeAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFeeMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFeeAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtTolActAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtCanRefundment = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFeeAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7FeeMoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7FeeAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.chkIsAdjust = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contOldBuildingPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsSellByRoom = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contNewRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNewBuildingPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtOldBuildingPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOldRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNewRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNewBuildingPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7Room = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtRoomNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7QuitRoomReason = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comQuitRoomState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contQuitDate.setName("contQuitDate");
        this.contQuitReason.setName("contQuitReason");
        this.contNumber.setName("contNumber");
        this.kDTabbedPane2.setName("kDTabbedPane2");
        this.contRoom.setName("contRoom");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.contQuitRoomReason.setName("contQuitRoomReason");
        this.contQuitRoomState.setName("contQuitRoomState");
        this.pkQuitDate.setName("pkQuitDate");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtQuitReason.setName("txtQuitReason");
        this.txtNumber.setName("txtNumber");
        this.tabFee.setName("tabFee");
        this.tabAdjust.setName("tabAdjust");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.tblSet.setName("tblSet");
        this.btnCanRefundment.setName("btnCanRefundment");
        this.contFeeAmount.setName("contFeeAmount");
        this.contFeeMoneyDefine.setName("contFeeMoneyDefine");
        this.contFeeAccount.setName("contFeeAccount");
        this.txtTolActAmount.setName("txtTolActAmount");
        this.txtCanRefundment.setName("txtCanRefundment");
        this.txtFeeAmount.setName("txtFeeAmount");
        this.f7FeeMoneyDefine.setName("f7FeeMoneyDefine");
        this.f7FeeAccount.setName("f7FeeAccount");
        this.chkIsAdjust.setName("chkIsAdjust");
        this.contOldBuildingPrice.setName("contOldBuildingPrice");
        this.contOldRoomPrice.setName("contOldRoomPrice");
        this.chkIsSellByRoom.setName("chkIsSellByRoom");
        this.contNewRoomPrice.setName("contNewRoomPrice");
        this.contNewBuildingPrice.setName("contNewBuildingPrice");
        this.txtRemark.setName("txtRemark");
        this.txtOldBuildingPrice.setName("txtOldBuildingPrice");
        this.txtOldRoomPrice.setName("txtOldRoomPrice");
        this.txtNewRoomPrice.setName("txtNewRoomPrice");
        this.txtNewBuildingPrice.setName("txtNewBuildingPrice");
        this.f7Room.setName("f7Room");
        this.txtRoomNo.setName("txtRoomNo");
        this.f7QuitRoomReason.setName("f7QuitRoomReason");
        this.comQuitRoomState.setName("comQuitRoomState");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,700));
        // kDTabbedPane1
        // contQuitDate		
        this.contQuitDate.setBoundLabelText(resHelper.getString("contQuitDate.boundLabelText"));		
        this.contQuitDate.setBoundLabelLength(100);		
        this.contQuitDate.setBoundLabelUnderline(true);
        // contQuitReason		
        this.contQuitReason.setBoundLabelText(resHelper.getString("contQuitReason.boundLabelText"));		
        this.contQuitReason.setBoundLabelUnderline(true);		
        this.contQuitReason.setBoundLabelLength(100);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // kDTabbedPane2
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelLength(100);
        // contQuitRoomReason		
        this.contQuitRoomReason.setBoundLabelText(resHelper.getString("contQuitRoomReason.boundLabelText"));		
        this.contQuitRoomReason.setBoundLabelLength(100);		
        this.contQuitRoomReason.setBoundLabelUnderline(true);
        // contQuitRoomState		
        this.contQuitRoomState.setBoundLabelText(resHelper.getString("contQuitRoomState.boundLabelText"));		
        this.contQuitRoomState.setBoundLabelLength(100);		
        this.contQuitRoomState.setBoundLabelUnderline(true);
        // pkQuitDate
        // kDScrollPane1
        // txtQuitReason		
        this.txtQuitReason.setMaxLength(300);
        // txtNumber
        // tabFee
        // tabAdjust
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // tblSet
		String tblSetStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"hasRemitAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"canRefundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{hasRemitAmount}</t:Cell><t:Cell>$Resource{canRefundmentAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblSet.setFormatXml(resHelper.translateString("tblSet",tblSetStrXML));
        this.tblSet.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblSet_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnCanRefundment		
        this.btnCanRefundment.setText(resHelper.getString("btnCanRefundment.text"));
        this.btnCanRefundment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCanRefundment_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contFeeAmount		
        this.contFeeAmount.setBoundLabelText(resHelper.getString("contFeeAmount.boundLabelText"));		
        this.contFeeAmount.setBoundLabelLength(100);		
        this.contFeeAmount.setBoundLabelUnderline(true);
        // contFeeMoneyDefine		
        this.contFeeMoneyDefine.setBoundLabelText(resHelper.getString("contFeeMoneyDefine.boundLabelText"));		
        this.contFeeMoneyDefine.setBoundLabelLength(100);		
        this.contFeeMoneyDefine.setBoundLabelUnderline(true);
        // contFeeAccount		
        this.contFeeAccount.setBoundLabelText(resHelper.getString("contFeeAccount.boundLabelText"));		
        this.contFeeAccount.setBoundLabelLength(100);		
        this.contFeeAccount.setBoundLabelUnderline(true);
        // txtTolActAmount		
        this.txtTolActAmount.setDataType(1);
        // txtCanRefundment		
        this.txtCanRefundment.setDataType(1);
        // txtFeeAmount		
        this.txtFeeAmount.setDataType(1);
        // f7FeeMoneyDefine		
        this.f7FeeMoneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.f7FeeMoneyDefine.setCommitFormat("$number$");		
        this.f7FeeMoneyDefine.setDisplayFormat("$name$");		
        this.f7FeeMoneyDefine.setEditFormat("$number$");
        this.f7FeeMoneyDefine.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7FeeMoneyDefine_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7FeeAccount		
        this.f7FeeAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");		
        this.f7FeeAccount.setEditFormat("$number$");		
        this.f7FeeAccount.setCommitFormat("$number$");		
        this.f7FeeAccount.setDisplayFormat("$name$");
        // chkIsAdjust		
        this.chkIsAdjust.setText(resHelper.getString("chkIsAdjust.text"));
        this.chkIsAdjust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsAdjust_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contOldBuildingPrice		
        this.contOldBuildingPrice.setBoundLabelText(resHelper.getString("contOldBuildingPrice.boundLabelText"));		
        this.contOldBuildingPrice.setBoundLabelLength(100);		
        this.contOldBuildingPrice.setBoundLabelUnderline(true);
        // contOldRoomPrice		
        this.contOldRoomPrice.setBoundLabelText(resHelper.getString("contOldRoomPrice.boundLabelText"));		
        this.contOldRoomPrice.setBoundLabelUnderline(true);		
        this.contOldRoomPrice.setBoundLabelLength(100);
        // chkIsSellByRoom		
        this.chkIsSellByRoom.setText(resHelper.getString("chkIsSellByRoom.text"));		
        this.chkIsSellByRoom.setEnabled(false);
        // contNewRoomPrice		
        this.contNewRoomPrice.setBoundLabelText(resHelper.getString("contNewRoomPrice.boundLabelText"));		
        this.contNewRoomPrice.setBoundLabelLength(100);		
        this.contNewRoomPrice.setBoundLabelUnderline(true);
        // contNewBuildingPrice		
        this.contNewBuildingPrice.setBoundLabelText(resHelper.getString("contNewBuildingPrice.boundLabelText"));		
        this.contNewBuildingPrice.setBoundLabelUnderline(true);		
        this.contNewBuildingPrice.setBoundLabelLength(100);
        // txtRemark		
        this.txtRemark.setWrapStyleWord(true);		
        this.txtRemark.setLineWrap(true);		
        this.txtRemark.setText(resHelper.getString("txtRemark.text"));		
        this.txtRemark.setMaxLength(255);		
        this.txtRemark.setForeground(new java.awt.Color(255,0,0));		
        this.txtRemark.setVisible(false);		
        this.txtRemark.setEnabled(false);
        // txtOldBuildingPrice		
        this.txtOldBuildingPrice.setEnabled(false);		
        this.txtOldBuildingPrice.setDataType(1);
        // txtOldRoomPrice		
        this.txtOldRoomPrice.setEnabled(false);		
        this.txtOldRoomPrice.setDataType(1);
        // txtNewRoomPrice		
        this.txtNewRoomPrice.setDataType(1);
        // txtNewBuildingPrice		
        this.txtNewBuildingPrice.setDataType(1);
        // f7Room		
        this.f7Room.setDisplayFormat("$displayName$");		
        this.f7Room.setEditFormat("$number$");		
        this.f7Room.setCommitFormat("$number$");
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
        // txtRoomNo		
        this.txtRoomNo.setEnabled(false);
        // f7QuitRoomReason		
        this.f7QuitRoomReason.setDisplayFormat("$name$");		
        this.f7QuitRoomReason.setEditFormat("$number$");		
        this.f7QuitRoomReason.setCommitFormat("$number$");		
        this.f7QuitRoomReason.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.QuitRoomReasonQuery");
        // comQuitRoomState		
        this.comQuitRoomState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.QuitRoomStateEnum").toArray());
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
        this.setBounds(new Rectangle(10, 10, 1013, 700));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 700));
        kDTabbedPane1.setBounds(new Rectangle(6, 364, 1000, 328));
        this.add(kDTabbedPane1, new KDLayout.Constraints(6, 364, 1000, 328, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contQuitDate.setBounds(new Rectangle(359, 10, 270, 19));
        this.add(contQuitDate, new KDLayout.Constraints(359, 10, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contQuitReason.setBounds(new Rectangle(9, 76, 996, 68));
        this.add(contQuitReason, new KDLayout.Constraints(9, 76, 996, 68, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTabbedPane2.setBounds(new Rectangle(7, 150, 998, 206));
        this.add(kDTabbedPane2, new KDLayout.Constraints(7, 150, 998, 206, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contRoom.setBounds(new Rectangle(716, 10, 270, 19));
        this.add(contRoom, new KDLayout.Constraints(716, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer3.setBounds(new Rectangle(10, 43, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(10, 43, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contQuitRoomReason.setBounds(new Rectangle(359, 43, 270, 19));
        this.add(contQuitRoomReason, new KDLayout.Constraints(359, 43, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contQuitRoomState.setBounds(new Rectangle(716, 43, 270, 19));
        this.add(contQuitRoomState, new KDLayout.Constraints(716, 43, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contQuitDate
        contQuitDate.setBoundEditor(pkQuitDate);
        //contQuitReason
        contQuitReason.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtQuitReason, null);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //kDTabbedPane2
        kDTabbedPane2.add(tabFee, resHelper.getString("tabFee.constraints"));
        kDTabbedPane2.add(tabAdjust, resHelper.getString("tabAdjust.constraints"));
        //tabFee
        tabFee.setLayout(null);        kDLabelContainer1.setBounds(new Rectangle(10, 10, 270, 19));
        tabFee.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(351, 10, 270, 19));
        tabFee.add(kDLabelContainer2, null);
        tblSet.setBounds(new Rectangle(10, 70, 953, 112));
        tabFee.add(tblSet, null);
        btnCanRefundment.setBounds(new Rectangle(691, 40, 58, 19));
        tabFee.add(btnCanRefundment, null);
        contFeeAmount.setBounds(new Rectangle(691, 8, 270, 19));
        tabFee.add(contFeeAmount, null);
        contFeeMoneyDefine.setBounds(new Rectangle(10, 40, 270, 19));
        tabFee.add(contFeeMoneyDefine, null);
        contFeeAccount.setBounds(new Rectangle(351, 40, 270, 19));
        tabFee.add(contFeeAccount, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtTolActAmount);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtCanRefundment);
        //contFeeAmount
        contFeeAmount.setBoundEditor(txtFeeAmount);
        //contFeeMoneyDefine
        contFeeMoneyDefine.setBoundEditor(f7FeeMoneyDefine);
        //contFeeAccount
        contFeeAccount.setBoundEditor(f7FeeAccount);
        //tabAdjust
        tabAdjust.setLayout(null);        chkIsAdjust.setBounds(new Rectangle(10, 10, 140, 19));
        tabAdjust.add(chkIsAdjust, null);
        contOldBuildingPrice.setBounds(new Rectangle(10, 35, 270, 19));
        tabAdjust.add(contOldBuildingPrice, null);
        contOldRoomPrice.setBounds(new Rectangle(376, 35, 270, 19));
        tabAdjust.add(contOldRoomPrice, null);
        chkIsSellByRoom.setBounds(new Rectangle(708, 35, 140, 19));
        tabAdjust.add(chkIsSellByRoom, null);
        contNewRoomPrice.setBounds(new Rectangle(376, 65, 270, 19));
        tabAdjust.add(contNewRoomPrice, null);
        contNewBuildingPrice.setBounds(new Rectangle(10, 65, 270, 19));
        tabAdjust.add(contNewBuildingPrice, null);
        txtRemark.setBounds(new Rectangle(10, 94, 637, 73));
        tabAdjust.add(txtRemark, null);
        //contOldBuildingPrice
        contOldBuildingPrice.setBoundEditor(txtOldBuildingPrice);
        //contOldRoomPrice
        contOldRoomPrice.setBoundEditor(txtOldRoomPrice);
        //contNewRoomPrice
        contNewRoomPrice.setBoundEditor(txtNewRoomPrice);
        //contNewBuildingPrice
        contNewBuildingPrice.setBoundEditor(txtNewBuildingPrice);
        //contRoom
        contRoom.setBoundEditor(f7Room);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtRoomNo);
        //contQuitRoomReason
        contQuitRoomReason.setBoundEditor(f7QuitRoomReason);
        //contQuitRoomState
        contQuitRoomState.setBoundEditor(comQuitRoomState);

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
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
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
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCopyLine);
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
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.QuitRoomEditUIHandler";
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
     * output tblSet_editStopped method
     */
    protected void tblSet_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnCanRefundment_actionPerformed method
     */
    protected void btnCanRefundment_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output f7FeeMoneyDefine_dataChanged method
     */
    protected void f7FeeMoneyDefine_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output chkIsAdjust_actionPerformed method
     */
    protected void chkIsAdjust_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7Room_dataChanged method
     */
    protected void f7Room_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "QuitRoomEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}