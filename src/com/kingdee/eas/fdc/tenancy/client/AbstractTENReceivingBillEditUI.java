/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

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
public abstract class AbstractTENReceivingBillEditUI extends com.kingdee.eas.fdc.basecrm.client.FDCReceivingBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTENReceivingBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyBill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSincerObligate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTransContract;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7TenancyBill;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SincerObligate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7TenancyUser;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7TransContract;
    protected com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractTENReceivingBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTENReceivingBillEditUI.class.getName());
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
        this.contTenancyBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSincerObligate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTransContract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7TenancyBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7SincerObligate = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7TenancyUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7TransContract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contTenancyBill.setName("contTenancyBill");
        this.contSincerObligate.setName("contSincerObligate");
        this.contTenancyUser.setName("contTenancyUser");
        this.contTransContract.setName("contTransContract");
        this.f7TenancyBill.setName("f7TenancyBill");
        this.f7SincerObligate.setName("f7SincerObligate");
        this.f7TenancyUser.setName("f7TenancyUser");
        this.f7TransContract.setName("f7TransContract");
        // CoreUI		
        this.contBizDate.setVisible(false);		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contOrgUnit.setVisible(false);		
        this.contRecAmount.setBoundLabelText(resHelper.getString("contRecAmount.boundLabelText"));		
        this.contRevBillType.setVisible(false);
		String tblEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fromMoneyDefine\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"fromAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"stleCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"stleType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"stleNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"revAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"revBankAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"custAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"revAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"locAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"actRevAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"remissionAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"limitAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"hasRefundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"oppAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"supplyOrg\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"supplyDes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyDefine}</t:Cell><t:Cell>$Resource{fromMoneyDefine}</t:Cell><t:Cell>$Resource{fromAccount}</t:Cell><t:Cell>$Resource{stleCount}</t:Cell><t:Cell>$Resource{stleType}</t:Cell><t:Cell>$Resource{stleNumber}</t:Cell><t:Cell>$Resource{revAccount}</t:Cell><t:Cell>$Resource{revBankAccount}</t:Cell><t:Cell>$Resource{custAccount}</t:Cell><t:Cell>$Resource{revAmount}</t:Cell><t:Cell>$Resource{locAmount}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actRevAmount}</t:Cell><t:Cell>$Resource{remissionAmount}</t:Cell><t:Cell>$Resource{limitAmount}</t:Cell><t:Cell>$Resource{hasRefundmentAmount}</t:Cell><t:Cell>$Resource{oppAccount}</t:Cell><t:Cell>$Resource{supplyOrg}</t:Cell><t:Cell>$Resource{supplyDes}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";

        this.tblEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
        });

        
		
        this.contFdcCustomers.setBoundLabelText(resHelper.getString("contFdcCustomers.boundLabelText"));		
        this.contCustomer.setVisible(false);		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoomLongNumber.setVisible(false);		
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
        this.comboRevBillType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboRevBillType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });		
        this.f7FdcCustomers.setEditFormat("$name$");		
        this.f7FdcCustomers.setEnabledMultiSelection(true);		
        this.f7FdcCustomers.setEnabled(false);		
        this.f7Customer.setEnabledMultiSelection(true);		
        this.f7Room.setQueryInfo("");		
        this.f7Room.setDisplayFormat("$name$");		
        this.f7Room.setEditFormat("$number$");		
        this.f7Room.setCommitFormat("$number$");		
        this.f7Room.setEnabled(false);
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
        // contTenancyBill		
        this.contTenancyBill.setBoundLabelText(resHelper.getString("contTenancyBill.boundLabelText"));		
        this.contTenancyBill.setBoundLabelLength(100);		
        this.contTenancyBill.setBoundLabelUnderline(true);
        // contSincerObligate		
        this.contSincerObligate.setBoundLabelText(resHelper.getString("contSincerObligate.boundLabelText"));		
        this.contSincerObligate.setBoundLabelLength(100);		
        this.contSincerObligate.setBoundLabelUnderline(true);
        // contTenancyUser		
        this.contTenancyUser.setBoundLabelText(resHelper.getString("contTenancyUser.boundLabelText"));		
        this.contTenancyUser.setBoundLabelLength(100);		
        this.contTenancyUser.setBoundLabelUnderline(true);
        // contTransContract		
        this.contTransContract.setBoundLabelText(resHelper.getString("contTransContract.boundLabelText"));		
        this.contTransContract.setBoundLabelLength(100);		
        this.contTransContract.setBoundLabelUnderline(true);		
        this.contTransContract.setVisible(false);
        // f7TenancyBill		
        this.f7TenancyBill.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillQuery");		
        this.f7TenancyBill.setCommitFormat("$number$");		
        this.f7TenancyBill.setDisplayFormat("$name$");		
        this.f7TenancyBill.setEditFormat("$number$");		
        this.f7TenancyBill.setEnabled(false);
        this.f7TenancyBill.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7TenancyBill_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7SincerObligate		
        this.f7SincerObligate.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.SincerObligateQuery");		
        this.f7SincerObligate.setDisplayFormat("$name$");		
        this.f7SincerObligate.setEditFormat("$number$");		
        this.f7SincerObligate.setCommitFormat("$number$");
        this.f7SincerObligate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7SincerObligate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7TenancyUser		
        this.f7TenancyUser.setQueryInfo("com.kingdee.eas.base.permission.UserInfo");		
        this.f7TenancyUser.setDisplayFormat("$name$");		
        this.f7TenancyUser.setEditFormat("$number$");		
        this.f7TenancyUser.setCommitFormat("$number$");		
        this.f7TenancyUser.setEnabled(false);
        // f7TransContract		
        this.f7TransContract.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillQuery");		
        this.f7TransContract.setDisplayFormat("$name$");		
        this.f7TransContract.setEditFormat("$number$");		
        this.f7TransContract.setCommitFormat("$number$");
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
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1016, 600));
        contCreator.setBounds(new Rectangle(11, 548, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(11, 548, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        contCreateTime.setBounds(new Rectangle(371, 548, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(371, 548, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        contNumber.setBounds(new Rectangle(734, 8, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(734, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizDate.setBounds(new Rectangle(609, 268, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(609, 268, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contDescription.setBounds(new Rectangle(13, 129, 991, 86));
        this.add(contDescription, new KDLayout.Constraints(13, 129, 991, 86, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(11, 573, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(11, 573, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        contOrgUnit.setBounds(new Rectangle(599, 249, 270, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(599, 249, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contAuditTime.setBounds(new Rectangle(371, 573, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(371, 573, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        contCompany.setBounds(new Rectangle(13, 8, 270, 19));
        this.add(contCompany, new KDLayout.Constraints(13, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellProject.setBounds(new Rectangle(373, 8, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(373, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrency.setBounds(new Rectangle(373, 79, 270, 19));
        this.add(contCurrency, new KDLayout.Constraints(373, 79, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRecAmount.setBounds(new Rectangle(13, 79, 270, 19));
        this.add(contRecAmount, new KDLayout.Constraints(13, 79, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contExchangeRate.setBounds(new Rectangle(734, 79, 270, 19));
        this.add(contExchangeRate, new KDLayout.Constraints(734, 79, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contRecLocAmount.setBounds(new Rectangle(13, 103, 270, 19));
        this.add(contRecLocAmount, new KDLayout.Constraints(13, 103, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRevBillType.setBounds(new Rectangle(732, 135, 270, 19));
        this.add(contRevBillType, new KDLayout.Constraints(732, 135, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRevBizType.setBounds(new Rectangle(13, 31, 270, 19));
        this.add(contRevBizType, new KDLayout.Constraints(13, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        tblEntry.setBounds(new Rectangle(11, 265, 992, 275));
        this.add(tblEntry, new KDLayout.Constraints(11, 265, 992, 275, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnSelectRevList.setBounds(new Rectangle(922, 231, 73, 19));
        this.add(btnSelectRevList, new KDLayout.Constraints(922, 231, 73, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        contFdcCustomers.setBounds(new Rectangle(373, 55, 270, 19));
        this.add(contFdcCustomers, new KDLayout.Constraints(373, 55, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCustomer.setBounds(new Rectangle(737, 162, 270, 19));
        this.add(contCustomer, new KDLayout.Constraints(737, 162, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contRoom.setBounds(new Rectangle(13, 55, 270, 19));
        this.add(contRoom, new KDLayout.Constraints(13, 55, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomLongNumber.setBounds(new Rectangle(691, 154, 270, 19));
        this.add(contRoomLongNumber, new KDLayout.Constraints(691, 154, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contTenancyBill.setBounds(new Rectangle(373, 31, 270, 19));
        this.add(contTenancyBill, new KDLayout.Constraints(373, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSincerObligate.setBounds(new Rectangle(734, 31, 270, 19));
        this.add(contSincerObligate, new KDLayout.Constraints(734, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contTenancyUser.setBounds(new Rectangle(734, 55, 270, 19));
        this.add(contTenancyUser, new KDLayout.Constraints(734, 55, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contTransContract.setBounds(new Rectangle(725, 115, 270, 19));
        this.add(contTransContract, new KDLayout.Constraints(725, 115, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        //contCreator
        contCreator.setBoundEditor(f7Creator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contAuditor
        contAuditor.setBoundEditor(f7Auditor);
        //contOrgUnit
        contOrgUnit.setBoundEditor(f7OrgUnit);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contCompany
        contCompany.setBoundEditor(f7Company);
        //contSellProject
        contSellProject.setBoundEditor(f7SellProject);
        //contCurrency
        contCurrency.setBoundEditor(f7Currency);
        //contRecAmount
        contRecAmount.setBoundEditor(txtRecAmount);
        //contExchangeRate
        contExchangeRate.setBoundEditor(txtExchangeRate);
        //contRecLocAmount
        contRecLocAmount.setBoundEditor(txtRecLocAmount);
        //contRevBillType
        contRevBillType.setBoundEditor(comboRevBillType);
        //contRevBizType
        contRevBizType.setBoundEditor(comboRevBizType);
        //contFdcCustomers
        contFdcCustomers.setBoundEditor(f7FdcCustomers);
        //contCustomer
        contCustomer.setBoundEditor(f7Customer);
        //contRoom
        contRoom.setBoundEditor(f7Room);
        //contRoomLongNumber
        contRoomLongNumber.setBoundEditor(txtRoomLongNumber);
        //contTenancyBill
        contTenancyBill.setBoundEditor(f7TenancyBill);
        //contSincerObligate
        contSincerObligate.setBoundEditor(f7SincerObligate);
        //contTenancyUser
        contTenancyUser.setBoundEditor(f7TenancyUser);
        //contTransContract
        contTransContract.setBoundEditor(f7TransContract);

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
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
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
        menuBiz.add(menuReceive);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
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
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnReceive);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("room", com.kingdee.eas.fdc.sellhouse.RoomInfo.class, this.f7Room, "data");
		dataBinder.registerBinding("tenancyObj", com.kingdee.eas.fdc.tenancy.TenancyBillInfo.class, this.f7TenancyBill, "data");
		dataBinder.registerBinding("obligateObj", com.kingdee.eas.fdc.tenancy.SincerObligateInfo.class, this.f7SincerObligate, "data");
		dataBinder.registerBinding("tenancyUser", com.kingdee.eas.base.permission.UserInfo.class, this.f7TenancyUser, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.TENReceivingBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo)ov;
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
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("exchangeRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revBillType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("revBizType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("room", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenancyObj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("obligateObj", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenancyUser", ValidateHelper.ON_SAVE);    		
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
     * output f7SellProject_dataChanged method
     */
    protected void f7SellProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output comboRevBillType_itemStateChanged method
     */
    protected void comboRevBillType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7Room_dataChanged method
     */
    protected void f7Room_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7TenancyBill_dataChanged method
     */
    protected void f7TenancyBill_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7SincerObligate_dataChanged method
     */
    protected void f7SincerObligate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("room.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("room.id"));
        	sic.add(new SelectorItemInfo("room.number"));
        	sic.add(new SelectorItemInfo("room.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("tenancyObj.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("tenancyObj.id"));
        	sic.add(new SelectorItemInfo("tenancyObj.number"));
        	sic.add(new SelectorItemInfo("tenancyObj.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("obligateObj.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("obligateObj.id"));
        	sic.add(new SelectorItemInfo("obligateObj.number"));
        	sic.add(new SelectorItemInfo("obligateObj.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("tenancyUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("tenancyUser.id"));
        	sic.add(new SelectorItemInfo("tenancyUser.number"));
        	sic.add(new SelectorItemInfo("tenancyUser.name"));
		}
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
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "TENReceivingBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}