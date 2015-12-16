/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
public abstract class AbstractContractCostSplitEditUI extends com.kingdee.eas.fdc.basedata.client.FDCSplitBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractCostSplitEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBoxIsConfirm;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProgrAcctSelect;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemProgrAcctSelect;
    protected com.kingdee.eas.fdc.contract.ContractCostSplitInfo editData = null;
    protected ActionProgrAcctSelect actionProgrAcctSelect = null;
    protected ActionViewContract actionViewContract = null;
    /**
     * output class constructor
     */
    public AbstractContractCostSplitEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractCostSplitEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        String _tempStr = null;
        actionSave.setEnabled(true);
        actionSave.setDaemonRun(false);

        actionSave.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl S"));
        _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
        actionSave.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
        actionSave.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSave.NAME");
        actionSave.putValue(ItemAction.NAME, _tempStr);
        this.actionSave.setBindWorkFlow(true);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionSubmit
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
        //actionProgrAcctSelect
        this.actionProgrAcctSelect = new ActionProgrAcctSelect(this);
        getActionManager().registerAction("actionProgrAcctSelect", actionProgrAcctSelect);
         this.actionProgrAcctSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContract
        this.actionViewContract = new ActionViewContract(this);
        getActionManager().registerAction("actionViewContract", actionViewContract);
         this.actionViewContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDCheckBoxIsConfirm = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.btnProgrAcctSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemProgrAcctSelect = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDCheckBoxIsConfirm.setName("kDCheckBoxIsConfirm");
        this.btnProgrAcctSelect.setName("btnProgrAcctSelect");
        this.btnViewContract.setName("btnViewContract");
        this.menuItemProgrAcctSelect.setName("menuItemProgrAcctSelect");
        // CoreUI		
        this.setMinimumSize(new Dimension(1013,600));		
        this.setPreferredSize(new Dimension(1013,600));		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol20\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"costAccount.curProject.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"costAccount.curProject.name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"costAccount.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"costAccount.name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"workLoad\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"splitScale\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"standard\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"product\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"costAccount.curProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"costAccount.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"splitType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"apportionType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"apportionValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"directAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"apportionValueTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"directAmountTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"otherRatioTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{costAccount.curProject.number}</t:Cell><t:Cell>$Resource{costAccount.curProject.name}</t:Cell><t:Cell>$Resource{costAccount.number}</t:Cell><t:Cell>$Resource{costAccount.name}</t:Cell><t:Cell>$Resource{workLoad}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{splitScale}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{standard}</t:Cell><t:Cell>$Resource{product}</t:Cell><t:Cell>$Resource{costAccount.curProject.id}</t:Cell><t:Cell>$Resource{costAccount.id}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{splitType}</t:Cell><t:Cell>$Resource{apportionType.name}</t:Cell><t:Cell>$Resource{apportionValue}</t:Cell><t:Cell>$Resource{directAmount}</t:Cell><t:Cell>$Resource{apportionValueTotal}</t:Cell><t:Cell>$Resource{directAmountTotal}</t:Cell><t:Cell>$Resource{otherRatioTotal}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));
        this.kdtEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
        });

                this.kdtEntrys.putBindContents("editData",new String[] {"id","","","","","workLoad","price","splitScale","amount","","product","costAccount.curProject.id","costAccount.id","level","splitType","apportionType","apportionValue","","apportionValueTotal","directAmountTotal","otherRatioTotal"});


        // kDCheckBoxIsConfirm		
        this.kDCheckBoxIsConfirm.setText(resHelper.getString("kDCheckBoxIsConfirm.text"));
        // btnProgrAcctSelect
        this.btnProgrAcctSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionProgrAcctSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProgrAcctSelect.setText(resHelper.getString("btnProgrAcctSelect.text"));		
        this.btnProgrAcctSelect.setToolTipText(resHelper.getString("btnProgrAcctSelect.toolTipText"));
        // btnViewContract
        this.btnViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContract.setText(resHelper.getString("btnViewContract.text"));
        // menuItemProgrAcctSelect
        this.menuItemProgrAcctSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionProgrAcctSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemProgrAcctSelect.setText(resHelper.getString("menuItemProgrAcctSelect.text"));		
        this.menuItemProgrAcctSelect.setToolTipText(resHelper.getString("menuItemProgrAcctSelect.toolTipText"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 600));
        kDLabelContainer1.setBounds(new Rectangle(733, 571, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(733, 571, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer2.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(10, 32, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(10, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(602, 32, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(602, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer5.setBounds(new Rectangle(311, 32, 270, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(311, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(311, 10, 692, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(311, 10, 692, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kdtEntrys.setBounds(new Rectangle(10, 59, 993, 482));
        this.add(kdtEntrys, new KDLayout.Constraints(10, 59, 993, 482, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer7.setBounds(new Rectangle(729, 527, 225, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(729, 527, 225, 19, 0));
        kDLabelContainer8.setBounds(new Rectangle(10, 549, 270, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(10, 549, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer9.setBounds(new Rectangle(723, 490, 270, 19));
        this.add(kDLabelContainer9, new KDLayout.Constraints(723, 490, 270, 19, 0));
        kDLabelContainer10.setBounds(new Rectangle(10, 571, 270, 19));
        this.add(kDLabelContainer10, new KDLayout.Constraints(10, 571, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer11.setBounds(new Rectangle(733, 549, 270, 19));
        this.add(kDLabelContainer11, new KDLayout.Constraints(733, 549, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer12.setBounds(new Rectangle(446, 477, 224, 19));
        this.add(kDLabelContainer12, new KDLayout.Constraints(446, 477, 224, 19, 0));
        kDLabelContainer13.setBounds(new Rectangle(372, 551, 270, 19));
        this.add(kDLabelContainer13, new KDLayout.Constraints(372, 551, 270, 19, 0));
        kDLabelContainer14.setBounds(new Rectangle(10, 490, 270, 19));
        this.add(kDLabelContainer14, new KDLayout.Constraints(10, 490, 270, 19, 0));
        kDCheckBoxIsConfirm.setBounds(new Rectangle(896, 32, 107, 19));
        this.add(kDCheckBoxIsConfirm, new KDLayout.Constraints(896, 32, 107, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(dateAuditTime);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtCostBillNumber);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtAmount);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtUnSplitAmount);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtSplitedAmount);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtCostBillName);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(bizPromptLastUpdateUser);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(bizPromptCreator);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(txtDescription);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(bizPromptAuditor);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(dateCreateTime);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(dateLastUpdateTime);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(txtCompany);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(txtNumber);

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
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        menuEdit.add(menuItemAcctSelect);
        menuEdit.add(menuItemProgrAcctSelect);
        menuEdit.add(menuItemSplitProj);
        menuEdit.add(menuItemSplitBotUp);
        menuEdit.add(menuItemSplitProd);
        menuEdit.add(menuItemImpContrSplit);
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
        menuBiz.add(menuItemViewCostInfo);
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
        this.toolBar.add(btnAcctSelect);
        this.toolBar.add(btnProgrAcctSelect);
        this.toolBar.add(separator4);
        this.toolBar.add(btnSplitProj);
        this.toolBar.add(btnSplitBotUp);
        this.toolBar.add(btnSplitProd);
        this.toolBar.add(separator5);
        this.toolBar.add(btnImpContrSplit);
        this.toolBar.add(separator6);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
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
        this.toolBar.add(btnViewCostInfo);
        this.toolBar.add(btnViewContract);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.dateAuditTime, "value");
		dataBinder.registerBinding("contractBill.number", String.class, this.txtCostBillNumber, "text");
		dataBinder.registerBinding("contractBill.amount", java.math.BigDecimal.class, this.txtAmount, "value");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtSplitedAmount, "value");
		dataBinder.registerBinding("contractBill.name", String.class, this.txtCostBillName, "text");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.product", com.kingdee.eas.fdc.basedata.ProductTypeInfo.class, this.kdtEntrys, "product.text");
		dataBinder.registerBinding("entrys.costAccount.curProject.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "costAccount.curProject.id.text");
		dataBinder.registerBinding("entrys.costAccount.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "costAccount.id.text");
		dataBinder.registerBinding("entrys.level", int.class, this.kdtEntrys, "level.text");
		dataBinder.registerBinding("entrys.apportionType", com.kingdee.eas.fdc.basedata.ApportionTypeInfo.class, this.kdtEntrys, "apportionType.name.text");
		dataBinder.registerBinding("entrys.apportionValue", java.math.BigDecimal.class, this.kdtEntrys, "apportionValue.text");
		dataBinder.registerBinding("entrys.directAmountTotal", java.math.BigDecimal.class, this.kdtEntrys, "directAmountTotal.text");
		dataBinder.registerBinding("entrys.apportionValueTotal", java.math.BigDecimal.class, this.kdtEntrys, "apportionValueTotal.text");
		dataBinder.registerBinding("entrys.otherRatioTotal", java.math.BigDecimal.class, this.kdtEntrys, "otherRatioTotal.text");
		dataBinder.registerBinding("entrys.amount", java.math.BigDecimal.class, this.kdtEntrys, "amount.text");
		dataBinder.registerBinding("entrys.splitType", com.kingdee.eas.fdc.basedata.CostSplitTypeEnum.class, this.kdtEntrys, "splitType.text");
		dataBinder.registerBinding("entrys.workLoad", java.math.BigDecimal.class, this.kdtEntrys, "workLoad.text");
		dataBinder.registerBinding("entrys.price", java.math.BigDecimal.class, this.kdtEntrys, "price.text");
		dataBinder.registerBinding("entrys.splitScale", java.math.BigDecimal.class, this.kdtEntrys, "splitScale.text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptCreator, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptAuditor, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.dateCreateTime, "value");
		dataBinder.registerBinding("isConfirm", boolean.class, this.kDCheckBoxIsConfirm, "selected");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractCostSplitEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.ContractCostSplitInfo)ov;
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
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.product", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.costAccount.curProject.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.costAccount.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.level", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.apportionType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.apportionValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.directAmountTotal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.apportionValueTotal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.otherRatioTotal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.splitType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.workLoad", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.splitScale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isConfirm", ValidateHelper.ON_SAVE);    		
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("contractBill.number"));
        sic.add(new SelectorItemInfo("contractBill.amount"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("contractBill.name"));
    	sic.add(new SelectorItemInfo("entrys.id"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.product.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.product.id"));
			sic.add(new SelectorItemInfo("entrys.product.name"));
        	sic.add(new SelectorItemInfo("entrys.product.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.costAccount.curProject.id"));
    	sic.add(new SelectorItemInfo("entrys.costAccount.id"));
    	sic.add(new SelectorItemInfo("entrys.level"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entrys.apportionType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entrys.apportionType.id"));
			sic.add(new SelectorItemInfo("entrys.apportionType.name"));
        	sic.add(new SelectorItemInfo("entrys.apportionType.number"));
		}
    	sic.add(new SelectorItemInfo("entrys.apportionValue"));
    	sic.add(new SelectorItemInfo("entrys.directAmountTotal"));
    	sic.add(new SelectorItemInfo("entrys.apportionValueTotal"));
    	sic.add(new SelectorItemInfo("entrys.otherRatioTotal"));
    	sic.add(new SelectorItemInfo("entrys.amount"));
    	sic.add(new SelectorItemInfo("entrys.splitType"));
    	sic.add(new SelectorItemInfo("entrys.workLoad"));
    	sic.add(new SelectorItemInfo("entrys.price"));
    	sic.add(new SelectorItemInfo("entrys.splitScale"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("isConfirm"));
        return sic;
    }        
    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionProgrAcctSelect_actionPerformed method
     */
    public void actionProgrAcctSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContract_actionPerformed method
     */
    public void actionViewContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSave(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
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
	public RequestContext prepareActionProgrAcctSelect(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProgrAcctSelect() {
    	return false;
    }
	public RequestContext prepareActionViewContract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContract() {
    	return false;
    }

    /**
     * output ActionProgrAcctSelect class
     */     
    protected class ActionProgrAcctSelect extends ItemAction {     
    
        public ActionProgrAcctSelect()
        {
            this(null);
        }

        public ActionProgrAcctSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionProgrAcctSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProgrAcctSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProgrAcctSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractCostSplitEditUI.this, "ActionProgrAcctSelect", "actionProgrAcctSelect_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContract class
     */     
    protected class ActionViewContract extends ItemAction {     
    
        public ActionViewContract()
        {
            this(null);
        }

        public ActionViewContract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewContract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractCostSplitEditUI.this, "ActionViewContract", "actionViewContract_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractCostSplitEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}