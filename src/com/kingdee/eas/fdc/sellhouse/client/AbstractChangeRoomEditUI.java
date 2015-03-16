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
public abstract class AbstractChangeRoomEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChangeRoomEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeDate;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDPurchase;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeReason;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtQitReason;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboState;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkChangeDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTrsfAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFeeAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFeeDealObject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTrsfMonDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTransferAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPreActTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFeeMonDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFeeAccount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtChangeEntrys;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTrsfAccount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFeeAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboFeeDealObject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTrsfMonDefine;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTransferAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPreActTotalAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtNewRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOldRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFeeMonDefine;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFeeAccount;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kdOldScrollPane;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDNewScrollPane;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtChangeReason;
    protected com.kingdee.eas.fdc.sellhouse.ChangeRoomInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractChangeRoomEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractChangeRoomEditUI.class.getName());
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
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDPurchase = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contChangeReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtQitReason = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.comboState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkChangeDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contTrsfAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFeeAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFeeDealObject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTrsfMonDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTransferAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPreActTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNewRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFeeMonDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFeeAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtChangeEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtTrsfAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtFeeAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboFeeDealObject = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtTrsfMonDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtTransferAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPreActTotalAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtNewRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtOldRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtFeeMonDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtFeeAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdOldScrollPane = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDNewScrollPane = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.prmtChangeReason = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contState.setName("contState");
        this.contChangeDate.setName("contChangeDate");
        this.kDContainer1.setName("kDContainer1");
        this.kDPurchase.setName("kDPurchase");
        this.contChangeReason.setName("contChangeReason");
        this.txtNumber.setName("txtNumber");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtQitReason.setName("txtQitReason");
        this.comboState.setName("comboState");
        this.pkChangeDate.setName("pkChangeDate");
        this.contTrsfAccount.setName("contTrsfAccount");
        this.contFeeAmount.setName("contFeeAmount");
        this.contFeeDealObject.setName("contFeeDealObject");
        this.contTrsfMonDefine.setName("contTrsfMonDefine");
        this.contTransferAmount.setName("contTransferAmount");
        this.contPreActTotalAmount.setName("contPreActTotalAmount");
        this.contNewRoom.setName("contNewRoom");
        this.contOldRoom.setName("contOldRoom");
        this.contFeeMonDefine.setName("contFeeMonDefine");
        this.contFeeAccount.setName("contFeeAccount");
        this.kdtChangeEntrys.setName("kdtChangeEntrys");
        this.prmtTrsfAccount.setName("prmtTrsfAccount");
        this.txtFeeAmount.setName("txtFeeAmount");
        this.comboFeeDealObject.setName("comboFeeDealObject");
        this.prmtTrsfMonDefine.setName("prmtTrsfMonDefine");
        this.txtTransferAmount.setName("txtTransferAmount");
        this.txtPreActTotalAmount.setName("txtPreActTotalAmount");
        this.prmtNewRoom.setName("prmtNewRoom");
        this.prmtOldRoom.setName("prmtOldRoom");
        this.prmtFeeMonDefine.setName("prmtFeeMonDefine");
        this.prmtFeeAccount.setName("prmtFeeAccount");
        this.kdOldScrollPane.setName("kdOldScrollPane");
        this.kDNewScrollPane.setName("kDNewScrollPane");
        this.prmtChangeReason.setName("prmtChangeReason");
        // CoreUI
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contState		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contState.setBoundLabelLength(100);		
        this.contState.setBoundLabelUnderline(true);
        // contChangeDate		
        this.contChangeDate.setBoundLabelText(resHelper.getString("contChangeDate.boundLabelText"));		
        this.contChangeDate.setBoundLabelLength(100);		
        this.contChangeDate.setBoundLabelUnderline(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDPurchase
        // contChangeReason		
        this.contChangeReason.setBoundLabelText(resHelper.getString("contChangeReason.boundLabelText"));		
        this.contChangeReason.setBoundLabelLength(100);		
        this.contChangeReason.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(44);		
        this.txtNumber.setRequired(true);
        // kDScrollPane1
        // txtQitReason		
        this.txtQitReason.setMaxLength(250);		
        this.txtQitReason.setRequired(true);
        // comboState		
        this.comboState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.comboState.setEnabled(false);
        // pkChangeDate		
        this.pkChangeDate.setRequired(true);
        // contTrsfAccount		
        this.contTrsfAccount.setBoundLabelText(resHelper.getString("contTrsfAccount.boundLabelText"));		
        this.contTrsfAccount.setBoundLabelLength(100);		
        this.contTrsfAccount.setBoundLabelUnderline(true);
        // contFeeAmount		
        this.contFeeAmount.setBoundLabelText(resHelper.getString("contFeeAmount.boundLabelText"));		
        this.contFeeAmount.setBoundLabelLength(100);		
        this.contFeeAmount.setBoundLabelUnderline(true);
        // contFeeDealObject		
        this.contFeeDealObject.setBoundLabelText(resHelper.getString("contFeeDealObject.boundLabelText"));		
        this.contFeeDealObject.setBoundLabelLength(100);		
        this.contFeeDealObject.setBoundLabelUnderline(true);
        // contTrsfMonDefine		
        this.contTrsfMonDefine.setBoundLabelText(resHelper.getString("contTrsfMonDefine.boundLabelText"));		
        this.contTrsfMonDefine.setBoundLabelLength(100);		
        this.contTrsfMonDefine.setBoundLabelUnderline(true);
        // contTransferAmount		
        this.contTransferAmount.setBoundLabelText(resHelper.getString("contTransferAmount.boundLabelText"));		
        this.contTransferAmount.setBoundLabelLength(100);		
        this.contTransferAmount.setBoundLabelUnderline(true);
        // contPreActTotalAmount		
        this.contPreActTotalAmount.setBoundLabelText(resHelper.getString("contPreActTotalAmount.boundLabelText"));		
        this.contPreActTotalAmount.setBoundLabelLength(100);		
        this.contPreActTotalAmount.setBoundLabelUnderline(true);
        // contNewRoom		
        this.contNewRoom.setBoundLabelText(resHelper.getString("contNewRoom.boundLabelText"));		
        this.contNewRoom.setBoundLabelLength(100);		
        this.contNewRoom.setBoundLabelUnderline(true);
        // contOldRoom		
        this.contOldRoom.setBoundLabelText(resHelper.getString("contOldRoom.boundLabelText"));		
        this.contOldRoom.setBoundLabelLength(100);		
        this.contOldRoom.setBoundLabelUnderline(true);
        // contFeeMonDefine		
        this.contFeeMonDefine.setBoundLabelText(resHelper.getString("contFeeMonDefine.boundLabelText"));		
        this.contFeeMonDefine.setBoundLabelLength(100);		
        this.contFeeMonDefine.setBoundLabelUnderline(true);
        // contFeeAccount		
        this.contFeeAccount.setBoundLabelText(resHelper.getString("contFeeAccount.boundLabelText"));		
        this.contFeeAccount.setBoundLabelLength(100);		
        this.contFeeAccount.setBoundLabelUnderline(true);
        // kdtChangeEntrys
		String kdtChangeEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"apAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"actPayAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"hasRemitAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"canChangeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"feeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{apAmount}</t:Cell><t:Cell>$Resource{actPayAmount}</t:Cell><t:Cell>$Resource{hasRemitAmount}</t:Cell><t:Cell>$Resource{canChangeAmount}</t:Cell><t:Cell>$Resource{feeAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtChangeEntrys.setFormatXml(resHelper.translateString("kdtChangeEntrys",kdtChangeEntrysStrXML));
        this.kdtChangeEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtChangeEntrys_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtChangeEntrys.putBindContents("editData",new String[] {"moneyDefine","apAmount","actPayAmount","hasRemitAmount","canChangeAmount","feeAmount"});


        // prmtTrsfAccount		
        this.prmtTrsfAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");		
        this.prmtTrsfAccount.setDisplayFormat("$name$");		
        this.prmtTrsfAccount.setCommitFormat("$number$");		
        this.prmtTrsfAccount.setEditFormat("$number$");
        // txtFeeAmount		
        this.txtFeeAmount.setEnabled(false);		
        this.txtFeeAmount.setDataType(1);		
        this.txtFeeAmount.setPrecision(2);
        // comboFeeDealObject		
        this.comboFeeDealObject.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ChangeBalanceObjectEnum").toArray());		
        this.comboFeeDealObject.setRequired(true);
        // prmtTrsfMonDefine		
        this.prmtTrsfMonDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.prmtTrsfMonDefine.setCommitFormat("$number$");		
        this.prmtTrsfMonDefine.setEditFormat("$number$");		
        this.prmtTrsfMonDefine.setDisplayFormat("$name$");		
        this.prmtTrsfMonDefine.setRequired(true);
        this.prmtTrsfMonDefine.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtTrsfMonDefine_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtTransferAmount		
        this.txtTransferAmount.setDataType(1);		
        this.txtTransferAmount.setPrecision(2);		
        this.txtTransferAmount.setEnabled(false);
        // txtPreActTotalAmount		
        this.txtPreActTotalAmount.setDataType(1);		
        this.txtPreActTotalAmount.setPrecision(2);		
        this.txtPreActTotalAmount.setEnabled(false);
        // prmtNewRoom		
        this.prmtNewRoom.setEnabled(false);		
        this.prmtNewRoom.setDisplayFormat("$displayName$");		
        this.prmtNewRoom.setEditFormat("$number$");		
        this.prmtNewRoom.setCommitFormat("$number$");		
        this.prmtNewRoom.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomQuery");
        // prmtOldRoom		
        this.prmtOldRoom.setRequired(true);		
        this.prmtOldRoom.setDisplayFormat("$displayName$");		
        this.prmtOldRoom.setEditFormat("$number$");		
        this.prmtOldRoom.setCommitFormat("$number$");		
        this.prmtOldRoom.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomQuery");
        this.prmtOldRoom.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtOldRoom_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtFeeMonDefine		
        this.prmtFeeMonDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.prmtFeeMonDefine.setDisplayFormat("$name$");		
        this.prmtFeeMonDefine.setEditFormat("$number$");		
        this.prmtFeeMonDefine.setCommitFormat("$number$");		
        this.prmtFeeMonDefine.setRequired(true);
        this.prmtFeeMonDefine.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtFeeMonDefine_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtFeeAccount		
        this.prmtFeeAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");		
        this.prmtFeeAccount.setDisplayFormat("$name$");		
        this.prmtFeeAccount.setEditFormat("$number$");		
        this.prmtFeeAccount.setCommitFormat("$number$");		
        this.prmtFeeAccount.setRequired(true);
        // kdOldScrollPane
        // kDNewScrollPane
        // prmtChangeReason		
        this.prmtChangeReason.setDisplayFormat("$name$");		
        this.prmtChangeReason.setEditFormat("$name$");		
        this.prmtChangeReason.setCommitFormat("$name$");		
        this.prmtChangeReason.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7SwapRoomReasonQuery");
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        contNumber.setBounds(new Rectangle(18, 12, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(18, 12, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(368, 35, 631, 48));
        this.add(contDescription, new KDLayout.Constraints(368, 35, 631, 48, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contState.setBounds(new Rectangle(729, 10, 270, 19));
        this.add(contState, new KDLayout.Constraints(729, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contChangeDate.setBounds(new Rectangle(369, 10, 270, 19));
        this.add(contChangeDate, new KDLayout.Constraints(369, 10, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(9, 90, 990, 202));
        this.add(kDContainer1, new KDLayout.Constraints(9, 90, 990, 202, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPurchase.setBounds(new Rectangle(12, 297, 987, 323));
        this.add(kDPurchase, new KDLayout.Constraints(12, 297, 987, 323, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contChangeReason.setBounds(new Rectangle(18, 61, 270, 19));
        this.add(contChangeReason, new KDLayout.Constraints(18, 61, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtQitReason, null);
        //contState
        contState.setBoundEditor(comboState);
        //contChangeDate
        contChangeDate.setBoundEditor(pkChangeDate);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(9, 90, 990, 202));        kDSeparator6.setBounds(new Rectangle(4, 60, 978, 10));
        kDContainer1.getContentPane().add(kDSeparator6, new KDLayout.Constraints(4, 60, 978, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contTrsfAccount.setBounds(new Rectangle(362, 31, 270, 19));
        kDContainer1.getContentPane().add(contTrsfAccount, new KDLayout.Constraints(362, 31, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFeeAmount.setBounds(new Rectangle(7, 153, 270, 19));
        kDContainer1.getContentPane().add(contFeeAmount, new KDLayout.Constraints(7, 153, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFeeDealObject.setBounds(new Rectangle(7, 72, 270, 19));
        kDContainer1.getContentPane().add(contFeeDealObject, new KDLayout.Constraints(7, 72, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTrsfMonDefine.setBounds(new Rectangle(7, 32, 270, 19));
        kDContainer1.getContentPane().add(contTrsfMonDefine, new KDLayout.Constraints(7, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTransferAmount.setBounds(new Rectangle(713, 31, 270, 19));
        kDContainer1.getContentPane().add(contTransferAmount, new KDLayout.Constraints(713, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPreActTotalAmount.setBounds(new Rectangle(713, 3, 270, 19));
        kDContainer1.getContentPane().add(contPreActTotalAmount, new KDLayout.Constraints(713, 3, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNewRoom.setBounds(new Rectangle(362, 5, 270, 19));
        kDContainer1.getContentPane().add(contNewRoom, new KDLayout.Constraints(362, 5, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOldRoom.setBounds(new Rectangle(7, 5, 270, 19));
        kDContainer1.getContentPane().add(contOldRoom, new KDLayout.Constraints(7, 5, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFeeMonDefine.setBounds(new Rectangle(7, 99, 270, 19));
        kDContainer1.getContentPane().add(contFeeMonDefine, new KDLayout.Constraints(7, 99, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFeeAccount.setBounds(new Rectangle(7, 126, 270, 19));
        kDContainer1.getContentPane().add(contFeeAccount, new KDLayout.Constraints(7, 126, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtChangeEntrys.setBounds(new Rectangle(362, 67, 621, 105));
        kDContainer1.getContentPane().add(kdtChangeEntrys, new KDLayout.Constraints(362, 67, 621, 105, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contTrsfAccount
        contTrsfAccount.setBoundEditor(prmtTrsfAccount);
        //contFeeAmount
        contFeeAmount.setBoundEditor(txtFeeAmount);
        //contFeeDealObject
        contFeeDealObject.setBoundEditor(comboFeeDealObject);
        //contTrsfMonDefine
        contTrsfMonDefine.setBoundEditor(prmtTrsfMonDefine);
        //contTransferAmount
        contTransferAmount.setBoundEditor(txtTransferAmount);
        //contPreActTotalAmount
        contPreActTotalAmount.setBoundEditor(txtPreActTotalAmount);
        //contNewRoom
        contNewRoom.setBoundEditor(prmtNewRoom);
        //contOldRoom
        contOldRoom.setBoundEditor(prmtOldRoom);
        //contFeeMonDefine
        contFeeMonDefine.setBoundEditor(prmtFeeMonDefine);
        //contFeeAccount
        contFeeAccount.setBoundEditor(prmtFeeAccount);
        //kDPurchase
        kDPurchase.add(kdOldScrollPane, resHelper.getString("kdOldScrollPane.constraints"));
        kDPurchase.add(kDNewScrollPane, resHelper.getString("kDNewScrollPane.constraints"));
        //contChangeReason
        contChangeReason.setBoundEditor(prmtChangeReason);

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
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtQitReason, "text");
		dataBinder.registerBinding("state", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.comboState, "selectedItem");
		dataBinder.registerBinding("changeDate", java.util.Date.class, this.pkChangeDate, "value");
		dataBinder.registerBinding("changeEntrys.apAmount", java.math.BigDecimal.class, this.kdtChangeEntrys, "apAmount.text");
		dataBinder.registerBinding("changeEntrys.actPayAmount", java.math.BigDecimal.class, this.kdtChangeEntrys, "actPayAmount.text");
		dataBinder.registerBinding("changeEntrys.canChangeAmount", java.math.BigDecimal.class, this.kdtChangeEntrys, "canChangeAmount.text");
		dataBinder.registerBinding("changeEntrys", com.kingdee.eas.fdc.sellhouse.ChangeEntryInfo.class, this.kdtChangeEntrys, "userObject");
		dataBinder.registerBinding("changeEntrys.moneyDefine", com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo.class, this.kdtChangeEntrys, "moneyDefine.text");
		dataBinder.registerBinding("changeEntrys.feeAmount", java.math.BigDecimal.class, this.kdtChangeEntrys, "feeAmount.text");
		dataBinder.registerBinding("changeEntrys.hasRemitAmount", java.math.BigDecimal.class, this.kdtChangeEntrys, "hasRemitAmount.text");
		dataBinder.registerBinding("trsfAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtTrsfAccount, "data");
		dataBinder.registerBinding("feeAmount", java.math.BigDecimal.class, this.txtFeeAmount, "value");
		dataBinder.registerBinding("feeDealObject", com.kingdee.eas.fdc.sellhouse.ChangeBalanceObjectEnum.class, this.comboFeeDealObject, "selectedItem");
		dataBinder.registerBinding("trsfMonDefine", com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo.class, this.prmtTrsfMonDefine, "data");
		dataBinder.registerBinding("transferAmount", java.math.BigDecimal.class, this.txtTransferAmount, "value");
		dataBinder.registerBinding("preActTotalAmount", java.math.BigDecimal.class, this.txtPreActTotalAmount, "value");
		dataBinder.registerBinding("newRoom", com.kingdee.eas.fdc.sellhouse.RoomInfo.class, this.prmtNewRoom, "data");
		dataBinder.registerBinding("oldRoom", com.kingdee.eas.fdc.sellhouse.RoomInfo.class, this.prmtOldRoom, "data");
		dataBinder.registerBinding("feeMonDefine", com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo.class, this.prmtFeeMonDefine, "data");
		dataBinder.registerBinding("feeAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtFeeAccount, "data");
		dataBinder.registerBinding("swapReason", com.kingdee.eas.fdc.sellhouse.SwapRoomReasonInfo.class, this.prmtChangeReason, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.ChangeRoomEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.ChangeRoomInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeEntrys.apAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeEntrys.actPayAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeEntrys.canChangeAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeEntrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeEntrys.moneyDefine", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeEntrys.feeAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeEntrys.hasRemitAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("trsfAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("feeAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("feeDealObject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("trsfMonDefine", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("transferAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("preActTotalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newRoom", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oldRoom", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("feeMonDefine", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("feeAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("swapReason", ValidateHelper.ON_SAVE);    		
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
     * output kdtChangeEntrys_editStopped method
     */
    protected void kdtChangeEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output prmtTrsfMonDefine_dataChanged method
     */
    protected void prmtTrsfMonDefine_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtOldRoom_dataChanged method
     */
    protected void prmtOldRoom_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtFeeMonDefine_dataChanged method
     */
    protected void prmtFeeMonDefine_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("changeDate"));
    sic.add(new SelectorItemInfo("changeEntrys.apAmount"));
    sic.add(new SelectorItemInfo("changeEntrys.actPayAmount"));
    sic.add(new SelectorItemInfo("changeEntrys.canChangeAmount"));
        sic.add(new SelectorItemInfo("changeEntrys.*"));
//        sic.add(new SelectorItemInfo("changeEntrys.number"));
        sic.add(new SelectorItemInfo("changeEntrys.moneyDefine.*"));
//        sic.add(new SelectorItemInfo("changeEntrys.moneyDefine.number"));
    sic.add(new SelectorItemInfo("changeEntrys.feeAmount"));
    sic.add(new SelectorItemInfo("changeEntrys.hasRemitAmount"));
        sic.add(new SelectorItemInfo("trsfAccount.*"));
        sic.add(new SelectorItemInfo("feeAmount"));
        sic.add(new SelectorItemInfo("feeDealObject"));
        sic.add(new SelectorItemInfo("trsfMonDefine.*"));
        sic.add(new SelectorItemInfo("transferAmount"));
        sic.add(new SelectorItemInfo("preActTotalAmount"));
        sic.add(new SelectorItemInfo("newRoom.*"));
        sic.add(new SelectorItemInfo("oldRoom.*"));
        sic.add(new SelectorItemInfo("feeMonDefine.*"));
        sic.add(new SelectorItemInfo("feeAccount.*"));
        sic.add(new SelectorItemInfo("swapReason.*"));
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ChangeRoomEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}