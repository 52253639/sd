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
public abstract class AbstractQuitRoomChangeEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractQuitRoomChangeEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuitRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtQuitRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuitChangeReason;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlOldQuitRoom;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlNewQuitChange;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldTolActAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldCanRefundment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldFeeAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldFeeAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewTolActAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewCanRefundment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewFeeAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewFeeAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7OldFeeMoneyDefine;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7OldFeeAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7NewFeeAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7NewFeeMoneyDefine;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable2;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtQuitChangeReason;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOldTolActAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOldCanRefundment;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOldFeeAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNewTolActAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNewCanRefundment;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNewFeeAmount;
    protected com.kingdee.eas.fdc.sellhouse.QuitRoomChangeInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractQuitRoomChangeEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractQuitRoomChangeEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contQuitRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtQuitRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contQuitChangeReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pnlOldQuitRoom = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlNewQuitChange = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contOldTolActAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldCanRefundment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldFeeAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldFeeAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNewTolActAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNewCanRefundment = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNewFeeAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNewMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNewFeeAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7OldFeeMoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7OldFeeAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7NewFeeAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7NewFeeMoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTable2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtQuitChangeReason = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtOldTolActAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOldCanRefundment = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOldFeeAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNewTolActAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNewCanRefundment = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNewFeeAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contCreator.setName("contCreator");
        this.prmtCreator.setName("prmtCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contAuditor.setName("contAuditor");
        this.prmtAuditor.setName("prmtAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.pkAuditTime.setName("pkAuditTime");
        this.contQuitRoom.setName("contQuitRoom");
        this.prmtQuitRoom.setName("prmtQuitRoom");
        this.contRoom.setName("contRoom");
        this.prmtRoom.setName("prmtRoom");
        this.contQuitChangeReason.setName("contQuitChangeReason");
        this.pnlOldQuitRoom.setName("pnlOldQuitRoom");
        this.pnlNewQuitChange.setName("pnlNewQuitChange");
        this.contOldTolActAmount.setName("contOldTolActAmount");
        this.contOldCanRefundment.setName("contOldCanRefundment");
        this.contOldFeeAmount.setName("contOldFeeAmount");
        this.contOldMoneyDefine.setName("contOldMoneyDefine");
        this.contOldFeeAccount.setName("contOldFeeAccount");
        this.contNewTolActAmount.setName("contNewTolActAmount");
        this.contNewCanRefundment.setName("contNewCanRefundment");
        this.contNewFeeAmount.setName("contNewFeeAmount");
        this.contNewMoneyDefine.setName("contNewMoneyDefine");
        this.contNewFeeAccount.setName("contNewFeeAccount");
        this.f7OldFeeMoneyDefine.setName("f7OldFeeMoneyDefine");
        this.f7OldFeeAccount.setName("f7OldFeeAccount");
        this.f7NewFeeAccount.setName("f7NewFeeAccount");
        this.f7NewFeeMoneyDefine.setName("f7NewFeeMoneyDefine");
        this.kDTable1.setName("kDTable1");
        this.kDTable2.setName("kDTable2");
        this.pkCreateTime.setName("pkCreateTime");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtQuitChangeReason.setName("txtQuitChangeReason");
        this.txtOldTolActAmount.setName("txtOldTolActAmount");
        this.txtOldCanRefundment.setName("txtOldCanRefundment");
        this.txtOldFeeAmount.setName("txtOldFeeAmount");
        this.txtNewTolActAmount.setName("txtNewTolActAmount");
        this.txtNewCanRefundment.setName("txtNewCanRefundment");
        this.txtNewFeeAmount.setName("txtNewFeeAmount");
        // CoreUI
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // prmtCreator		
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
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // prmtAuditor		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$number$");		
        this.prmtAuditor.setCommitFormat("$number$");
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // pkAuditTime
        // contQuitRoom		
        this.contQuitRoom.setBoundLabelText(resHelper.getString("contQuitRoom.boundLabelText"));		
        this.contQuitRoom.setBoundLabelLength(100);		
        this.contQuitRoom.setBoundLabelUnderline(true);
        // prmtQuitRoom		
        this.prmtQuitRoom.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.QuitRoomQuery");		
        this.prmtQuitRoom.setDisplayFormat("$number$");		
        this.prmtQuitRoom.setEditFormat("$number$");		
        this.prmtQuitRoom.setCommitFormat("$number$");
        this.prmtQuitRoom.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtQuitRoom_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // prmtRoom
        this.prmtRoom.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRoom_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contQuitChangeReason		
        this.contQuitChangeReason.setBoundLabelText(resHelper.getString("contQuitChangeReason.boundLabelText"));		
        this.contQuitChangeReason.setBoundLabelLength(100);		
        this.contQuitChangeReason.setBoundLabelUnderline(true);
        // pnlOldQuitRoom		
        this.pnlOldQuitRoom.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,0),1), resHelper.getString("pnlOldQuitRoom.border.title")));
        // pnlNewQuitChange		
        this.pnlNewQuitChange.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,0),1), resHelper.getString("pnlNewQuitChange.border.title")));
        // contOldTolActAmount		
        this.contOldTolActAmount.setBoundLabelText(resHelper.getString("contOldTolActAmount.boundLabelText"));		
        this.contOldTolActAmount.setBoundLabelLength(100);		
        this.contOldTolActAmount.setBoundLabelUnderline(true);
        // contOldCanRefundment		
        this.contOldCanRefundment.setBoundLabelText(resHelper.getString("contOldCanRefundment.boundLabelText"));		
        this.contOldCanRefundment.setBoundLabelLength(100);		
        this.contOldCanRefundment.setBoundLabelUnderline(true);
        // contOldFeeAmount		
        this.contOldFeeAmount.setBoundLabelText(resHelper.getString("contOldFeeAmount.boundLabelText"));		
        this.contOldFeeAmount.setBoundLabelLength(100);		
        this.contOldFeeAmount.setBoundLabelUnderline(true);
        // contOldMoneyDefine		
        this.contOldMoneyDefine.setBoundLabelText(resHelper.getString("contOldMoneyDefine.boundLabelText"));		
        this.contOldMoneyDefine.setBoundLabelLength(100);		
        this.contOldMoneyDefine.setBoundLabelUnderline(true);
        // contOldFeeAccount		
        this.contOldFeeAccount.setBoundLabelText(resHelper.getString("contOldFeeAccount.boundLabelText"));		
        this.contOldFeeAccount.setBoundLabelLength(100);		
        this.contOldFeeAccount.setBoundLabelUnderline(true);
        // contNewTolActAmount		
        this.contNewTolActAmount.setBoundLabelText(resHelper.getString("contNewTolActAmount.boundLabelText"));		
        this.contNewTolActAmount.setBoundLabelUnderline(true);		
        this.contNewTolActAmount.setBoundLabelLength(80);
        // contNewCanRefundment		
        this.contNewCanRefundment.setBoundLabelText(resHelper.getString("contNewCanRefundment.boundLabelText"));		
        this.contNewCanRefundment.setBoundLabelUnderline(true);		
        this.contNewCanRefundment.setBoundLabelLength(100);
        // contNewFeeAmount		
        this.contNewFeeAmount.setBoundLabelText(resHelper.getString("contNewFeeAmount.boundLabelText"));		
        this.contNewFeeAmount.setBoundLabelLength(80);		
        this.contNewFeeAmount.setBoundLabelUnderline(true);
        // contNewMoneyDefine		
        this.contNewMoneyDefine.setBoundLabelText(resHelper.getString("contNewMoneyDefine.boundLabelText"));		
        this.contNewMoneyDefine.setBoundLabelLength(80);		
        this.contNewMoneyDefine.setBoundLabelUnderline(true);
        // contNewFeeAccount		
        this.contNewFeeAccount.setBoundLabelText(resHelper.getString("contNewFeeAccount.boundLabelText"));		
        this.contNewFeeAccount.setBoundLabelLength(80);		
        this.contNewFeeAccount.setBoundLabelUnderline(true);
        // f7OldFeeMoneyDefine		
        this.f7OldFeeMoneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
        this.f7OldFeeMoneyDefine.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7OldFeeMoneyDefine_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7OldFeeAccount		
        this.f7OldFeeAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
        // f7NewFeeAccount		
        this.f7NewFeeAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
        // f7NewFeeMoneyDefine		
        this.f7NewFeeMoneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
        this.f7NewFeeMoneyDefine.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7NewFeeMoneyDefine_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"canRefundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{canRefundmentAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));

        

        // kDTable2
		String kDTable2StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"newMoneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"newAppAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"newActAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"newCanRefundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{newMoneyDefine}</t:Cell><t:Cell>$Resource{newAppAmount}</t:Cell><t:Cell>$Resource{newActAmount}</t:Cell><t:Cell>$Resource{newCanRefundmentAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTable2.setFormatXml(resHelper.translateString("kDTable2",kDTable2StrXML));
        this.kDTable2.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kDTable2_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // pkCreateTime
        // kDScrollPane1
        // txtQuitChangeReason		
        this.txtQuitChangeReason.setMaxLength(255);
        // txtOldTolActAmount		
        this.txtOldTolActAmount.setDataType(1);
        // txtOldCanRefundment		
        this.txtOldCanRefundment.setDataType(1);
        // txtOldFeeAmount		
        this.txtOldFeeAmount.setDataType(1);
        // txtNewTolActAmount		
        this.txtNewTolActAmount.setDataType(1);
        // txtNewCanRefundment		
        this.txtNewCanRefundment.setDataType(1);
        // txtNewFeeAmount		
        this.txtNewFeeAmount.setDataType(1);
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
        this.setBounds(new Rectangle(10, 10, 1030, 629));
        this.setLayout(null);
        contCreator.setBounds(new Rectangle(9, 463, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(315, 462, 270, 19));
        this.add(contCreateTime, null);
        contNumber.setBounds(new Rectangle(11, 9, 270, 19));
        this.add(contNumber, null);
        contAuditor.setBounds(new Rectangle(7, 502, 270, 19));
        this.add(contAuditor, null);
        contAuditTime.setBounds(new Rectangle(314, 500, 270, 19));
        this.add(contAuditTime, null);
        contQuitRoom.setBounds(new Rectangle(368, 9, 270, 19));
        this.add(contQuitRoom, null);
        contRoom.setBounds(new Rectangle(741, 8, 270, 19));
        this.add(contRoom, null);
        contQuitChangeReason.setBounds(new Rectangle(10, 41, 850, 74));
        this.add(contQuitChangeReason, null);
        pnlOldQuitRoom.setBounds(new Rectangle(2, 123, 526, 319));
        this.add(pnlOldQuitRoom, null);
        pnlNewQuitChange.setBounds(new Rectangle(535, 122, 483, 318));
        this.add(pnlNewQuitChange, null);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contQuitRoom
        contQuitRoom.setBoundEditor(prmtQuitRoom);
        //contRoom
        contRoom.setBoundEditor(prmtRoom);
        //contQuitChangeReason
        contQuitChangeReason.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtQuitChangeReason, null);
        //pnlOldQuitRoom
        pnlOldQuitRoom.setLayout(null);        contOldTolActAmount.setBounds(new Rectangle(14, 17, 235, 19));
        pnlOldQuitRoom.add(contOldTolActAmount, null);
        contOldCanRefundment.setBounds(new Rectangle(273, 16, 241, 19));
        pnlOldQuitRoom.add(contOldCanRefundment, null);
        contOldFeeAmount.setBounds(new Rectangle(13, 50, 235, 19));
        pnlOldQuitRoom.add(contOldFeeAmount, null);
        contOldMoneyDefine.setBounds(new Rectangle(14, 83, 235, 19));
        pnlOldQuitRoom.add(contOldMoneyDefine, null);
        contOldFeeAccount.setBounds(new Rectangle(273, 81, 241, 19));
        pnlOldQuitRoom.add(contOldFeeAccount, null);
        kDTable1.setBounds(new Rectangle(13, 115, 499, 184));
        pnlOldQuitRoom.add(kDTable1, null);
        //contOldTolActAmount
        contOldTolActAmount.setBoundEditor(txtOldTolActAmount);
        //contOldCanRefundment
        contOldCanRefundment.setBoundEditor(txtOldCanRefundment);
        //contOldFeeAmount
        contOldFeeAmount.setBoundEditor(txtOldFeeAmount);
        //contOldMoneyDefine
        contOldMoneyDefine.setBoundEditor(f7OldFeeMoneyDefine);
        //contOldFeeAccount
        contOldFeeAccount.setBoundEditor(f7OldFeeAccount);
        //pnlNewQuitChange
        pnlNewQuitChange.setLayout(null);        contNewTolActAmount.setBounds(new Rectangle(13, 16, 220, 19));
        pnlNewQuitChange.add(contNewTolActAmount, null);
        contNewCanRefundment.setBounds(new Rectangle(250, 14, 220, 19));
        pnlNewQuitChange.add(contNewCanRefundment, null);
        contNewFeeAmount.setBounds(new Rectangle(13, 46, 220, 19));
        pnlNewQuitChange.add(contNewFeeAmount, null);
        contNewMoneyDefine.setBounds(new Rectangle(13, 80, 220, 19));
        pnlNewQuitChange.add(contNewMoneyDefine, null);
        contNewFeeAccount.setBounds(new Rectangle(249, 78, 220, 19));
        pnlNewQuitChange.add(contNewFeeAccount, null);
        kDTable2.setBounds(new Rectangle(13, 119, 457, 181));
        pnlNewQuitChange.add(kDTable2, null);
        //contNewTolActAmount
        contNewTolActAmount.setBoundEditor(txtNewTolActAmount);
        //contNewCanRefundment
        contNewCanRefundment.setBoundEditor(txtNewCanRefundment);
        //contNewFeeAmount
        contNewFeeAmount.setBoundEditor(txtNewFeeAmount);
        //contNewMoneyDefine
        contNewMoneyDefine.setBoundEditor(f7NewFeeMoneyDefine);
        //contNewFeeAccount
        contNewFeeAccount.setBoundEditor(f7NewFeeAccount);

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
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.QuitRoomChangeEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.QuitRoomChangeInfo)ov;
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
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    		
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
     * output prmtQuitRoom_dataChanged method
     */
    protected void prmtQuitRoom_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output prmtRoom_dataChanged method
     */
    protected void prmtRoom_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7OldFeeMoneyDefine_dataChanged method
     */
    protected void f7OldFeeMoneyDefine_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7NewFeeMoneyDefine_dataChanged method
     */
    protected void f7NewFeeMoneyDefine_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output kDTable2_editStopped method
     */
    protected void kDTable2_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "QuitRoomChangeEditUI");
    }




}