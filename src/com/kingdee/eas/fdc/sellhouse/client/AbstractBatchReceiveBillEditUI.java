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
public abstract class AbstractBatchReceiveBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBatchReceiveBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcReceiveSubject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcBalanceNum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcReceiveBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcBalanceType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcReceiveAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcPayBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcPayAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcSummary;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton wbAddRoom;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRooms;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton wbDeleteRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcBillDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcReceiveOppSubject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPrmtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contf7Currency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ReceiveSubject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtBalanceNum;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtReceiveBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7BalanceType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ReceiveAccount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPayBank;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPayAccountNum;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSummary;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpBillDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField receiveAmountTotal;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PayeeAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Currency;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRec;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRec;
    protected com.kingdee.eas.framework.CoreBillBaseInfo editData = null;
    protected ActionRec actionRec = null;
    /**
     * output class constructor
     */
    public AbstractBatchReceiveBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBatchReceiveBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionRec
        this.actionRec = new ActionRec(this);
        getActionManager().registerAction("actionRec", actionRec);
         this.actionRec.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.lcReceiveSubject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcBalanceNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcReceiveBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcBalanceType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcReceiveAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcPayBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcPayAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcSummary = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.wbAddRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblRooms = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.wbDeleteRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.lcBillDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcReceiveOppSubject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPrmtSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contf7Currency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7ReceiveSubject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtBalanceNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtReceiveBank = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7BalanceType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7ReceiveAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtPayBank = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPayAccountNum = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSummary = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.dpBillDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.receiveAmountTotal = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7PayeeAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Currency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnRec = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemRec = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.lcReceiveSubject.setName("lcReceiveSubject");
        this.lcBalanceNum.setName("lcBalanceNum");
        this.lcReceiveBank.setName("lcReceiveBank");
        this.lcBalanceType.setName("lcBalanceType");
        this.lcReceiveAccount.setName("lcReceiveAccount");
        this.lcPayBank.setName("lcPayBank");
        this.lcPayAccount.setName("lcPayAccount");
        this.lcSummary.setName("lcSummary");
        this.wbAddRoom.setName("wbAddRoom");
        this.tblRooms.setName("tblRooms");
        this.wbDeleteRoom.setName("wbDeleteRoom");
        this.lcBillDate.setName("lcBillDate");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.lcReceiveOppSubject.setName("lcReceiveOppSubject");
        this.contPrmtSellProject.setName("contPrmtSellProject");
        this.contf7Currency.setName("contf7Currency");
        this.f7ReceiveSubject.setName("f7ReceiveSubject");
        this.txtBalanceNum.setName("txtBalanceNum");
        this.txtReceiveBank.setName("txtReceiveBank");
        this.f7BalanceType.setName("f7BalanceType");
        this.f7ReceiveAccount.setName("f7ReceiveAccount");
        this.txtPayBank.setName("txtPayBank");
        this.txtPayAccountNum.setName("txtPayAccountNum");
        this.txtSummary.setName("txtSummary");
        this.dpBillDate.setName("dpBillDate");
        this.receiveAmountTotal.setName("receiveAmountTotal");
        this.f7PayeeAccount.setName("f7PayeeAccount");
        this.prmtSellProject.setName("prmtSellProject");
        this.f7Currency.setName("f7Currency");
        this.btnRec.setName("btnRec");
        this.menuItemRec.setName("menuItemRec");
        // CoreUI		
        this.setPreferredSize(new Dimension(900,600));		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setVisible(false);		
        this.menuView.setEnabled(false);		
        this.menuSubmitOption.setVisible(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.menuTable1.setEnabled(false);		
        this.menuTable1.setVisible(false);		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // lcReceiveSubject		
        this.lcReceiveSubject.setBoundLabelText(resHelper.getString("lcReceiveSubject.boundLabelText"));		
        this.lcReceiveSubject.setBoundLabelUnderline(true);		
        this.lcReceiveSubject.setBoundLabelLength(100);
        // lcBalanceNum		
        this.lcBalanceNum.setBoundLabelText(resHelper.getString("lcBalanceNum.boundLabelText"));		
        this.lcBalanceNum.setBoundLabelLength(100);		
        this.lcBalanceNum.setBoundLabelUnderline(true);
        // lcReceiveBank		
        this.lcReceiveBank.setBoundLabelText(resHelper.getString("lcReceiveBank.boundLabelText"));		
        this.lcReceiveBank.setBoundLabelLength(100);		
        this.lcReceiveBank.setBoundLabelUnderline(true);
        // lcBalanceType		
        this.lcBalanceType.setBoundLabelText(resHelper.getString("lcBalanceType.boundLabelText"));		
        this.lcBalanceType.setBoundLabelLength(100);		
        this.lcBalanceType.setBoundLabelUnderline(true);
        // lcReceiveAccount		
        this.lcReceiveAccount.setBoundLabelText(resHelper.getString("lcReceiveAccount.boundLabelText"));		
        this.lcReceiveAccount.setBoundLabelLength(100);		
        this.lcReceiveAccount.setBoundLabelUnderline(true);
        // lcPayBank		
        this.lcPayBank.setBoundLabelText(resHelper.getString("lcPayBank.boundLabelText"));		
        this.lcPayBank.setBoundLabelLength(100);		
        this.lcPayBank.setBoundLabelUnderline(true);
        // lcPayAccount		
        this.lcPayAccount.setBoundLabelText(resHelper.getString("lcPayAccount.boundLabelText"));		
        this.lcPayAccount.setBoundLabelLength(100);		
        this.lcPayAccount.setBoundLabelUnderline(true);
        // lcSummary		
        this.lcSummary.setBoundLabelText(resHelper.getString("lcSummary.boundLabelText"));		
        this.lcSummary.setBoundLabelLength(100);		
        this.lcSummary.setBoundLabelUnderline(true);
        // wbAddRoom		
        this.wbAddRoom.setText(resHelper.getString("wbAddRoom.text"));		
        this.wbAddRoom.setVisible(false);
        this.wbAddRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    wbAddRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tblRooms
		String tblRoomsStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"building\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"room\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"f7Customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"f7Purchase\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"f7MoneyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"receiveAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"actReceiveAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"appReceiveAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{building}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{f7Customer}</t:Cell><t:Cell>$Resource{f7Purchase}</t:Cell><t:Cell>$Resource{f7MoneyName}</t:Cell><t:Cell>$Resource{receiveAmount}</t:Cell><t:Cell>$Resource{actReceiveAmount}</t:Cell><t:Cell>$Resource{appReceiveAmount}</t:Cell><t:Cell>$Resource{seq}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblRooms.setFormatXml(resHelper.translateString("tblRooms",tblRoomsStrXML));
        this.tblRooms.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblRooms_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblRooms.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    tblRooms_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // wbDeleteRoom		
        this.wbDeleteRoom.setText(resHelper.getString("wbDeleteRoom.text"));
        this.wbDeleteRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    wbDeleteRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // lcBillDate		
        this.lcBillDate.setBoundLabelText(resHelper.getString("lcBillDate.boundLabelText"));		
        this.lcBillDate.setBoundLabelLength(100);		
        this.lcBillDate.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // lcReceiveOppSubject		
        this.lcReceiveOppSubject.setBoundLabelText(resHelper.getString("lcReceiveOppSubject.boundLabelText"));		
        this.lcReceiveOppSubject.setBoundLabelLength(100);		
        this.lcReceiveOppSubject.setBoundLabelUnderline(true);
        // contPrmtSellProject		
        this.contPrmtSellProject.setBoundLabelText(resHelper.getString("contPrmtSellProject.boundLabelText"));		
        this.contPrmtSellProject.setBoundLabelLength(100);		
        this.contPrmtSellProject.setBoundLabelUnderline(true);
        // contf7Currency		
        this.contf7Currency.setBoundLabelText(resHelper.getString("contf7Currency.boundLabelText"));		
        this.contf7Currency.setBoundLabelUnderline(true);		
        this.contf7Currency.setBoundLabelLength(100);
        // f7ReceiveSubject		
        this.f7ReceiveSubject.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");		
        this.f7ReceiveSubject.setDisplayFormat("$name$");		
        this.f7ReceiveSubject.setEditFormat("$number$");		
        this.f7ReceiveSubject.setCommitFormat("$number$");
        // txtBalanceNum
        // txtReceiveBank
        // f7BalanceType		
        this.f7BalanceType.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");		
        this.f7BalanceType.setDisplayFormat("$name$");		
        this.f7BalanceType.setEditFormat("$number$");		
        this.f7BalanceType.setCommitFormat("$number$");		
        this.f7BalanceType.setRequired(true);
        // f7ReceiveAccount		
        this.f7ReceiveAccount.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7AccountBankQuery");		
        this.f7ReceiveAccount.setCommitFormat("$number$");		
        this.f7ReceiveAccount.setDisplayFormat("$name$");		
        this.f7ReceiveAccount.setEditFormat("$number$");
        this.f7ReceiveAccount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7ReceiveAccount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtPayBank
        // txtPayAccountNum
        // txtSummary
        // dpBillDate		
        this.dpBillDate.setRequired(true);
        // receiveAmountTotal		
        this.receiveAmountTotal.setEnabled(false);
        // f7PayeeAccount		
        this.f7PayeeAccount.setDisplayFormat("$name$");		
        this.f7PayeeAccount.setEditFormat("$number$");		
        this.f7PayeeAccount.setCommitFormat("$number$");		
        this.f7PayeeAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
        // prmtSellProject		
        this.prmtSellProject.setDisplayFormat("$name$");		
        this.prmtSellProject.setEditFormat("$number$");		
        this.prmtSellProject.setCommitFormat("$number$");		
        this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtSellProject.setEnabled(false);
        // f7Currency		
        this.f7Currency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.f7Currency.setDisplayFormat("$name$");		
        this.f7Currency.setEditFormat("$number$");		
        this.f7Currency.setCommitFormat("$number$");
        // btnRec
        this.btnRec.setAction((IItemAction)ActionProxyFactory.getProxy(actionRec, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRec.setText(resHelper.getString("btnRec.text"));		
        this.btnRec.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_recieversetting"));
        // menuItemRec
        this.menuItemRec.setAction((IItemAction)ActionProxyFactory.getProxy(actionRec, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRec.setText(resHelper.getString("menuItemRec.text"));		
        this.menuItemRec.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_recieversetting"));
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
        this.setBounds(new Rectangle(10, 10, 900, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 900, 600));
        lcReceiveSubject.setBounds(new Rectangle(15, 7, 270, 19));
        this.add(lcReceiveSubject, new KDLayout.Constraints(15, 7, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcBalanceNum.setBounds(new Rectangle(315, 39, 270, 19));
        this.add(lcBalanceNum, new KDLayout.Constraints(315, 39, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        lcReceiveBank.setBounds(new Rectangle(615, 7, 270, 19));
        this.add(lcReceiveBank, new KDLayout.Constraints(615, 7, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        lcBalanceType.setBounds(new Rectangle(15, 39, 270, 19));
        this.add(lcBalanceType, new KDLayout.Constraints(15, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcReceiveAccount.setBounds(new Rectangle(315, 7, 270, 19));
        this.add(lcReceiveAccount, new KDLayout.Constraints(315, 7, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        lcPayBank.setBounds(new Rectangle(15, 71, 270, 19));
        this.add(lcPayBank, new KDLayout.Constraints(15, 71, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcPayAccount.setBounds(new Rectangle(315, 71, 270, 19));
        this.add(lcPayAccount, new KDLayout.Constraints(315, 71, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        lcSummary.setBounds(new Rectangle(16, 137, 871, 19));
        this.add(lcSummary, new KDLayout.Constraints(16, 137, 871, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        wbAddRoom.setBounds(new Rectangle(695, 170, 89, 19));
        this.add(wbAddRoom, new KDLayout.Constraints(695, 170, 89, 19, 0));
        tblRooms.setBounds(new Rectangle(17, 194, 869, 391));
        this.add(tblRooms, new KDLayout.Constraints(17, 194, 869, 391, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        wbDeleteRoom.setBounds(new Rectangle(799, 170, 85, 19));
        this.add(wbDeleteRoom, new KDLayout.Constraints(799, 170, 85, 19, 0));
        lcBillDate.setBounds(new Rectangle(315, 105, 270, 19));
        this.add(lcBillDate, new KDLayout.Constraints(315, 105, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        kDLabelContainer1.setBounds(new Rectangle(15, 105, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(15, 105, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lcReceiveOppSubject.setBounds(new Rectangle(615, 39, 270, 19));
        this.add(lcReceiveOppSubject, new KDLayout.Constraints(615, 39, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPrmtSellProject.setBounds(new Rectangle(615, 71, 270, 19));
        this.add(contPrmtSellProject, new KDLayout.Constraints(615, 71, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contf7Currency.setBounds(new Rectangle(617, 105, 270, 19));
        this.add(contf7Currency, new KDLayout.Constraints(617, 105, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //lcReceiveSubject
        lcReceiveSubject.setBoundEditor(f7ReceiveSubject);
        //lcBalanceNum
        lcBalanceNum.setBoundEditor(txtBalanceNum);
        //lcReceiveBank
        lcReceiveBank.setBoundEditor(txtReceiveBank);
        //lcBalanceType
        lcBalanceType.setBoundEditor(f7BalanceType);
        //lcReceiveAccount
        lcReceiveAccount.setBoundEditor(f7ReceiveAccount);
        //lcPayBank
        lcPayBank.setBoundEditor(txtPayBank);
        //lcPayAccount
        lcPayAccount.setBoundEditor(txtPayAccountNum);
        //lcSummary
        lcSummary.setBoundEditor(txtSummary);
        //lcBillDate
        lcBillDate.setBoundEditor(dpBillDate);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(receiveAmountTotal);
        //lcReceiveOppSubject
        lcReceiveOppSubject.setBoundEditor(f7PayeeAccount);
        //contPrmtSellProject
        contPrmtSellProject.setBoundEditor(prmtSellProject);
        //contf7Currency
        contf7Currency.setBoundEditor(f7Currency);

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
        menuBiz.add(menuItemRec);
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
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnRec);
        this.toolBar.add(btnVoucher);
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
	    return "com.kingdee.eas.fdc.sellhouse.app.BatchReceiveBillEditUIHandler";
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
     * output wbAddRoom_actionPerformed method
     */
    protected void wbAddRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblRooms_tableClicked method
     */
    protected void tblRooms_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblRooms_activeCellChanged method
     */
    protected void tblRooms_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output wbDeleteRoom_actionPerformed method
     */
    protected void wbDeleteRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output f7ReceiveAccount_dataChanged method
     */
    protected void f7ReceiveAccount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
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
    protected class ActionRec extends ItemAction {     
    
        public ActionRec()
        {
            this(null);
        }

        public ActionRec(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractBatchReceiveBillEditUI.this, "ActionRec", "actionRec_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "BatchReceiveBillEditUI");
    }




}