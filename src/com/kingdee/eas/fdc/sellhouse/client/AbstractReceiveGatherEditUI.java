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
public abstract class AbstractReceiveGatherEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractReceiveGatherEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAccountBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGatherType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSumAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettlementType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettlementNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRevAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOppAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRevBillType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAccountBank;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboGatherType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSumAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSettlementType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSettlementNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRevAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOppAccount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboRevBillType;
    protected com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractReceiveGatherEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractReceiveGatherEditUI.class.getName());
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
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAccountBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGatherType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSumAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSettlementType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSettlementNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRevAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOppAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRevBillType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAccountBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboGatherType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtSumAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtSettlementType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSettlementNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtRevAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtOppAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboRevBillType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.kdtEntry.setName("kdtEntry");
        this.contProject.setName("contProject");
        this.contBank.setName("contBank");
        this.contAccountBank.setName("contAccountBank");
        this.contGatherType.setName("contGatherType");
        this.contSumAmount.setName("contSumAmount");
        this.contSettlementType.setName("contSettlementType");
        this.contSettlementNumber.setName("contSettlementNumber");
        this.contRevAccount.setName("contRevAccount");
        this.contOppAccount.setName("contOppAccount");
        this.contRevBillType.setName("contRevBillType");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtProject.setName("prmtProject");
        this.prmtBank.setName("prmtBank");
        this.prmtAccountBank.setName("prmtAccountBank");
        this.comboGatherType.setName("comboGatherType");
        this.txtSumAmount.setName("txtSumAmount");
        this.prmtSettlementType.setName("prmtSettlementType");
        this.txtSettlementNumber.setName("txtSettlementNumber");
        this.prmtRevAccount.setName("prmtRevAccount");
        this.prmtOppAccount.setName("prmtOppAccount");
        this.comboRevBillType.setName("comboRevBillType");
        // CoreUI
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"customerDisName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"settlementType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"settlementNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"revAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"cusAccountBankNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"sheRevBill\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"receiptNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"invoiceNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"oppAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customerDisName}</t:Cell><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{settlementType}</t:Cell><t:Cell>$Resource{settlementNumber}</t:Cell><t:Cell>$Resource{revAmount}</t:Cell><t:Cell>$Resource{cusAccountBankNumber}</t:Cell><t:Cell>$Resource{sheRevBill}</t:Cell><t:Cell>$Resource{receiptNumber}</t:Cell><t:Cell>$Resource{invoiceNumber}</t:Cell><t:Cell>$Resource{oppAccount}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));

                this.kdtEntry.putBindContents("editData",new String[] {"room.name","customerDisName","moneyDefine.name","settlementType.name","settlementNumber","revAmount","cusAccountBankNumber","sheRevBill.id","receiptNumber","invoiceNumber","oppAccount","remark"});


        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);
        // contBank		
        this.contBank.setBoundLabelText(resHelper.getString("contBank.boundLabelText"));		
        this.contBank.setBoundLabelLength(100);		
        this.contBank.setBoundLabelUnderline(true);
        // contAccountBank		
        this.contAccountBank.setBoundLabelText(resHelper.getString("contAccountBank.boundLabelText"));		
        this.contAccountBank.setBoundLabelLength(100);		
        this.contAccountBank.setBoundLabelUnderline(true);
        // contGatherType		
        this.contGatherType.setBoundLabelText(resHelper.getString("contGatherType.boundLabelText"));		
        this.contGatherType.setBoundLabelLength(100);		
        this.contGatherType.setBoundLabelUnderline(true);
        // contSumAmount		
        this.contSumAmount.setBoundLabelText(resHelper.getString("contSumAmount.boundLabelText"));		
        this.contSumAmount.setBoundLabelLength(100);		
        this.contSumAmount.setBoundLabelUnderline(true);
        // contSettlementType		
        this.contSettlementType.setBoundLabelText(resHelper.getString("contSettlementType.boundLabelText"));		
        this.contSettlementType.setBoundLabelLength(100);		
        this.contSettlementType.setBoundLabelUnderline(true);
        // contSettlementNumber		
        this.contSettlementNumber.setBoundLabelText(resHelper.getString("contSettlementNumber.boundLabelText"));		
        this.contSettlementNumber.setBoundLabelLength(100);		
        this.contSettlementNumber.setBoundLabelUnderline(true);
        // contRevAccount		
        this.contRevAccount.setBoundLabelText(resHelper.getString("contRevAccount.boundLabelText"));		
        this.contRevAccount.setBoundLabelLength(100);		
        this.contRevAccount.setBoundLabelUnderline(true);
        // contOppAccount		
        this.contOppAccount.setBoundLabelText(resHelper.getString("contOppAccount.boundLabelText"));		
        this.contOppAccount.setBoundLabelLength(100);		
        this.contOppAccount.setBoundLabelUnderline(true);		
        this.contOppAccount.setEnabled(false);		
        this.contOppAccount.setVisible(false);
        // contRevBillType		
        this.contRevBillType.setBoundLabelText(resHelper.getString("contRevBillType.boundLabelText"));		
        this.contRevBillType.setBoundLabelLength(100);		
        this.contRevBillType.setBoundLabelUnderline(true);
        // prmtCreator
        // pkCreateTime
        // txtNumber		
        this.txtNumber.setRequired(true);		
        this.txtNumber.setMaxLength(80);
        // pkBizDate		
        this.pkBizDate.setRequired(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // prmtAuditor
        // pkAuditTime
        // prmtProject
        // prmtBank		
        this.prmtBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.BankQuery");
        this.prmtBank.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtBank_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtAccountBank		
        this.prmtAccountBank.setQueryInfo("com.kingdee.eas.basedata.assistant.app.AccountBankQuery");
        this.prmtAccountBank.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtAccountBank_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboGatherType		
        this.comboGatherType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.GatherTypeEnum").toArray());
        // txtSumAmount
        // prmtSettlementType		
        this.prmtSettlementType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");
        // txtSettlementNumber		
        this.txtSettlementNumber.setMaxLength(44);
        // prmtRevAccount		
        this.prmtRevAccount.setRequired(true);		
        this.prmtRevAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
        // prmtOppAccount		
        this.prmtOppAccount.setRequired(true);		
        this.prmtOppAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
        // comboRevBillType		
        this.comboRevBillType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basecrm.RevBillTypeEnum").toArray());
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
        this.setLayout(null);
        contCreator.setBounds(new Rectangle(11, 572, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(357, 572, 270, 19));
        this.add(contCreateTime, null);
        contNumber.setBounds(new Rectangle(10, 11, 270, 19));
        this.add(contNumber, null);
        contBizDate.setBounds(new Rectangle(357, 11, 270, 19));
        this.add(contBizDate, null);
        contDescription.setBounds(new Rectangle(11, 100, 965, 19));
        this.add(contDescription, null);
        contAuditor.setBounds(new Rectangle(11, 595, 270, 19));
        this.add(contAuditor, null);
        contAuditTime.setBounds(new Rectangle(357, 595, 270, 19));
        this.add(contAuditTime, null);
        kdtEntry.setBounds(new Rectangle(11, 131, 965, 425));
        this.add(kdtEntry, null);
        contProject.setBounds(new Rectangle(704, 11, 270, 19));
        this.add(contProject, null);
        contBank.setBounds(new Rectangle(704, 55, 270, 19));
        this.add(contBank, null);
        contAccountBank.setBounds(new Rectangle(10, 55, 270, 19));
        this.add(contAccountBank, null);
        contGatherType.setBounds(new Rectangle(10, 33, 270, 19));
        this.add(contGatherType, null);
        contSumAmount.setBounds(new Rectangle(357, 33, 270, 19));
        this.add(contSumAmount, null);
        contSettlementType.setBounds(new Rectangle(10, 77, 270, 19));
        this.add(contSettlementType, null);
        contSettlementNumber.setBounds(new Rectangle(357, 77, 270, 19));
        this.add(contSettlementNumber, null);
        contRevAccount.setBounds(new Rectangle(357, 55, 270, 19));
        this.add(contRevAccount, null);
        contOppAccount.setBounds(new Rectangle(700, 79, 270, 19));
        this.add(contOppAccount, null);
        contRevBillType.setBounds(new Rectangle(704, 33, 270, 19));
        this.add(contRevBillType, null);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //contBank
        contBank.setBoundEditor(prmtBank);
        //contAccountBank
        contAccountBank.setBoundEditor(prmtAccountBank);
        //contGatherType
        contGatherType.setBoundEditor(comboGatherType);
        //contSumAmount
        contSumAmount.setBoundEditor(txtSumAmount);
        //contSettlementType
        contSettlementType.setBoundEditor(prmtSettlementType);
        //contSettlementNumber
        contSettlementNumber.setBoundEditor(txtSettlementNumber);
        //contRevAccount
        contRevAccount.setBoundEditor(prmtRevAccount);
        //contOppAccount
        contOppAccount.setBoundEditor(prmtOppAccount);
        //contRevBillType
        contRevBillType.setBoundEditor(comboRevBillType);

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
		dataBinder.registerBinding("entry", com.kingdee.eas.fdc.sellhouse.ReceiveGatherEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entry.room.name", String.class, this.kdtEntry, "room.text");
		dataBinder.registerBinding("entry.customerDisName", String.class, this.kdtEntry, "customerDisName.text");
		dataBinder.registerBinding("entry.moneyDefine.name", String.class, this.kdtEntry, "moneyDefine.text");
		dataBinder.registerBinding("entry.settlementType.name", String.class, this.kdtEntry, "settlementType.text");
		dataBinder.registerBinding("entry.sheRevBill.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntry, "sheRevBill.text");
		dataBinder.registerBinding("entry.revAmount", java.math.BigDecimal.class, this.kdtEntry, "revAmount.text");
		dataBinder.registerBinding("entry.oppAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.kdtEntry, "oppAccount.text");
		dataBinder.registerBinding("entry.settlementNumber", String.class, this.kdtEntry, "settlementNumber.text");
		dataBinder.registerBinding("entry.cusAccountBankNumber", String.class, this.kdtEntry, "cusAccountBankNumber.text");
		dataBinder.registerBinding("entry.receiptNumber", String.class, this.kdtEntry, "receiptNumber.text");
		dataBinder.registerBinding("entry.invoiceNumber", String.class, this.kdtEntry, "invoiceNumber.text");
		dataBinder.registerBinding("entry.remark", String.class, this.kdtEntry, "remark.text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtProject, "data");
		dataBinder.registerBinding("bank", com.kingdee.eas.basedata.assistant.BankInfo.class, this.prmtBank, "data");
		dataBinder.registerBinding("accountBank", com.kingdee.eas.basedata.assistant.AccountBankInfo.class, this.prmtAccountBank, "data");
		dataBinder.registerBinding("gatherType", com.kingdee.eas.fdc.sellhouse.GatherTypeEnum.class, this.comboGatherType, "selectedItem");
		dataBinder.registerBinding("sumAmount", java.math.BigDecimal.class, this.txtSumAmount, "value");
		dataBinder.registerBinding("settlementType", com.kingdee.eas.basedata.assistant.SettlementTypeInfo.class, this.prmtSettlementType, "data");
		dataBinder.registerBinding("settlementNumber", String.class, this.txtSettlementNumber, "text");
		dataBinder.registerBinding("revAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtRevAccount, "data");
		dataBinder.registerBinding("oppAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtOppAccount, "data");
		dataBinder.registerBinding("revBillType", com.kingdee.eas.fdc.basecrm.BizBillType.class, this.comboRevBillType, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.ReceiveGatherEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo)ov;
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
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.room.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.customerDisName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.moneyDefine.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.settlementType.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.sheRevBill.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.revAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.oppAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.settlementNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.cusAccountBankNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.receiptNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.invoiceNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accountBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("gatherType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sumAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settlementNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oppAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revBillType", ValidateHelper.ON_SAVE);    		
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
     * output prmtBank_dataChanged method
     */
    protected void prmtBank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtAccountBank_dataChanged method
     */
    protected void prmtAccountBank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("entry.*"));
//        sic.add(new SelectorItemInfo("entry.number"));
    sic.add(new SelectorItemInfo("entry.room.name"));
    sic.add(new SelectorItemInfo("entry.customerDisName"));
    sic.add(new SelectorItemInfo("entry.moneyDefine.name"));
    sic.add(new SelectorItemInfo("entry.settlementType.name"));
    sic.add(new SelectorItemInfo("entry.sheRevBill.id"));
    sic.add(new SelectorItemInfo("entry.revAmount"));
        sic.add(new SelectorItemInfo("entry.oppAccount.*"));
//        sic.add(new SelectorItemInfo("entry.oppAccount.number"));
    sic.add(new SelectorItemInfo("entry.settlementNumber"));
    sic.add(new SelectorItemInfo("entry.cusAccountBankNumber"));
    sic.add(new SelectorItemInfo("entry.receiptNumber"));
    sic.add(new SelectorItemInfo("entry.invoiceNumber"));
    sic.add(new SelectorItemInfo("entry.remark"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("project.*"));
        sic.add(new SelectorItemInfo("bank.*"));
        sic.add(new SelectorItemInfo("accountBank.*"));
        sic.add(new SelectorItemInfo("gatherType"));
        sic.add(new SelectorItemInfo("sumAmount"));
        sic.add(new SelectorItemInfo("settlementType.*"));
        sic.add(new SelectorItemInfo("settlementNumber"));
        sic.add(new SelectorItemInfo("revAccount.*"));
        sic.add(new SelectorItemInfo("oppAccount.*"));
        sic.add(new SelectorItemInfo("revBillType"));
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ReceiveGatherEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}