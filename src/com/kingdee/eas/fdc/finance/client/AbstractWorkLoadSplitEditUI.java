/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

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
public abstract class AbstractWorkLoadSplitEditUI extends com.kingdee.eas.fdc.basedata.client.FDCSplitBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractWorkLoadSplitEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContrName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContrName;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAutoMatchSplit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAutoMatchSplit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewWorkLoadBill;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewContract;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewWorkLoadBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContract;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSplitBaseOnProp;
    protected com.kingdee.eas.fdc.finance.PaymentSplitInfo editData = null;
    protected ActionSplit actionSplit = null;
    protected ActionAutoMatchSplit actionAutoMatchSplit = null;
    protected ActionViewWorkLoadBill actionViewWorkLoadBill = null;
    protected ActionViewContract actionViewContract = null;
    protected ActionSplitBaseOnProp actionSplitBaseOnProp = null;
    /**
     * output class constructor
     */
    public AbstractWorkLoadSplitEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractWorkLoadSplitEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSplit
        this.actionSplit = new ActionSplit(this);
        getActionManager().registerAction("actionSplit", actionSplit);
         this.actionSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAutoMatchSplit
        this.actionAutoMatchSplit = new ActionAutoMatchSplit(this);
        getActionManager().registerAction("actionAutoMatchSplit", actionAutoMatchSplit);
         this.actionAutoMatchSplit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewWorkLoadBill
        this.actionViewWorkLoadBill = new ActionViewWorkLoadBill(this);
        getActionManager().registerAction("actionViewWorkLoadBill", actionViewWorkLoadBill);
         this.actionViewWorkLoadBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContract
        this.actionViewContract = new ActionViewContract(this);
        getActionManager().registerAction("actionViewContract", actionViewContract);
         this.actionViewContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSplitBaseOnProp
        this.actionSplitBaseOnProp = new ActionSplitBaseOnProp(this);
        getActionManager().registerAction("actionSplitBaseOnProp", actionSplitBaseOnProp);
         this.actionSplitBaseOnProp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contContrName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtContrName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAutoMatchSplit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnAutoMatchSplit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewWorkLoadBill = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewContract = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnViewWorkLoadBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSplitBaseOnProp = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contContrName.setName("contContrName");
        this.txtContrName.setName("txtContrName");
        this.btnSplit.setName("btnSplit");
        this.menuItemSplit.setName("menuItemSplit");
        this.menuItemAutoMatchSplit.setName("menuItemAutoMatchSplit");
        this.btnAutoMatchSplit.setName("btnAutoMatchSplit");
        this.menuItemViewWorkLoadBill.setName("menuItemViewWorkLoadBill");
        this.menuItemViewContract.setName("menuItemViewContract");
        this.btnViewWorkLoadBill.setName("btnViewWorkLoadBill");
        this.btnViewContract.setName("btnViewContract");
        this.btnSplitBaseOnProp.setName("btnSplitBaseOnProp");
        // CoreUI		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemSubmit.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setEnabled(true);		
        this.menuView.setVisible(true);		
        this.menuItemFirst.setVisible(false);		
        this.menuItemPre.setVisible(false);		
        this.menuItemNext.setVisible(false);		
        this.menuItemLast.setVisible(false);		
        this.menuItemEdit.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.btnAddLine.setVisible(true);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuItemMultiapprove.setVisible(false);		
        this.menuItemNextPerson.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);		
        this.MenuItemWFG.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuTable1.setEnabled(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setEnabled(false);		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:Protection locked=\"true\" hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol20\"><c:Protection hidden=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol21\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol22\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol23\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"costAccount.curProject.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"costAccount.curProject.name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"costAccount.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"costAccount.name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"contractAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"changeAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"costAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"splitedCostAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"splitScale\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"standard\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"product\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"directAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"costAccount.curProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"costAccount.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"splitType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"apportionType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"apportionValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"directAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"apportionValueTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"directAmountTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol22\" /><t:Column t:key=\"otherRatioTotal\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"workLoad\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{costAccount.curProject.number}</t:Cell><t:Cell>$Resource{costAccount.curProject.name}</t:Cell><t:Cell>$Resource{costAccount.number}</t:Cell><t:Cell>$Resource{costAccount.name}</t:Cell><t:Cell>$Resource{contractAmt}</t:Cell><t:Cell>$Resource{changeAmt}</t:Cell><t:Cell>$Resource{costAmt}</t:Cell><t:Cell>$Resource{splitedCostAmt}</t:Cell><t:Cell>$Resource{splitScale}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{standard}</t:Cell><t:Cell>$Resource{product}</t:Cell><t:Cell>$Resource{directAmt}</t:Cell><t:Cell>$Resource{costAccount.curProject.id}</t:Cell><t:Cell>$Resource{costAccount.id}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{splitType}</t:Cell><t:Cell>$Resource{apportionType.name}</t:Cell><t:Cell>$Resource{apportionValue}</t:Cell><t:Cell>$Resource{directAmount}</t:Cell><t:Cell>$Resource{apportionValueTotal}</t:Cell><t:Cell>$Resource{directAmountTotal}</t:Cell><t:Cell>$Resource{otherRatioTotal}</t:Cell><t:Cell>$Resource{workLoad}</t:Cell><t:Cell>$Resource{price}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));
        this.kdtEntrys.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
        });

                this.kdtEntrys.putBindContents("editData",new String[] {"id","","","","","contractAmt","changeAmt","costAmt","splitedCostAmt","","amount","","product","directAmt","costAccount.curProject.id","costAccount.id","level","splitType","apportionType","apportionValue","directAmount","apportionValueTotal","directAmountTotal","otherRatioTotal","workLoad","price"});


        // contContrName		
        this.contContrName.setBoundLabelText(resHelper.getString("contContrName.boundLabelText"));		
        this.contContrName.setBoundLabelLength(100);		
        this.contContrName.setBoundLabelUnderline(true);		
        this.contContrName.setEnabled(false);
        // txtContrName		
        this.txtContrName.setEnabled(false);
        // btnSplit
        this.btnSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplit.setText(resHelper.getString("btnSplit.text"));		
        this.btnSplit.setToolTipText(resHelper.getString("btnSplit.toolTipText"));
        // menuItemSplit
        this.menuItemSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemSplit.setText(resHelper.getString("menuItemSplit.text"));
        // menuItemAutoMatchSplit
        this.menuItemAutoMatchSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAutoMatchSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAutoMatchSplit.setText(resHelper.getString("menuItemAutoMatchSplit.text"));		
        this.menuItemAutoMatchSplit.setToolTipText(resHelper.getString("menuItemAutoMatchSplit.toolTipText"));
        // btnAutoMatchSplit
        this.btnAutoMatchSplit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAutoMatchSplit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAutoMatchSplit.setText(resHelper.getString("btnAutoMatchSplit.text"));		
        this.btnAutoMatchSplit.setToolTipText(resHelper.getString("btnAutoMatchSplit.toolTipText"));
        // menuItemViewWorkLoadBill
        this.menuItemViewWorkLoadBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewWorkLoadBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewWorkLoadBill.setText(resHelper.getString("menuItemViewWorkLoadBill.text"));
        // menuItemViewContract
        this.menuItemViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewContract.setText(resHelper.getString("menuItemViewContract.text"));
        // btnViewWorkLoadBill
        this.btnViewWorkLoadBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewWorkLoadBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewWorkLoadBill.setText(resHelper.getString("btnViewWorkLoadBill.text"));		
        this.btnViewWorkLoadBill.setToolTipText(resHelper.getString("btnViewWorkLoadBill.toolTipText"));
        // btnViewContract
        this.btnViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContract.setText(resHelper.getString("btnViewContract.text"));		
        this.btnViewContract.setToolTipText(resHelper.getString("btnViewContract.toolTipText"));
        // btnSplitBaseOnProp
        this.btnSplitBaseOnProp.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplitBaseOnProp, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSplitBaseOnProp.setText(resHelper.getString("btnSplitBaseOnProp.text"));
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
        kDLabelContainer3.setBounds(new Rectangle(10, 41, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(10, 41, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer4.setBounds(new Rectangle(733, 41, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(733, 41, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer5.setBounds(new Rectangle(371, 41, 270, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(371, 41, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer6.setBounds(new Rectangle(371, 10, 270, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(371, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtEntrys.setBounds(new Rectangle(10, 71, 993, 470));
        this.add(kdtEntrys, new KDLayout.Constraints(10, 71, 993, 470, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
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
        contContrName.setBounds(new Rectangle(733, 10, 270, 19));
        this.add(contContrName, new KDLayout.Constraints(733, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
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
        //contContrName
        contContrName.setBoundEditor(txtContrName);

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
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(menuItemAcctSelect);
        menuEdit.add(menuItemSplitProj);
        menuEdit.add(menuItemSplitBotUp);
        menuEdit.add(menuItemSplitProd);
        menuEdit.add(menuItemImpContrSplit);
        menuEdit.add(menuItemSplit);
        menuEdit.add(menuItemAutoMatchSplit);
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
        menuView.add(menuItemViewWorkLoadBill);
        menuView.add(menuItemLocate);
        menuView.add(menuItemViewContract);
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
        this.toolBar.add(btnAcctSelect);
        this.toolBar.add(separator4);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSplitProj);
        this.toolBar.add(btnSplitBotUp);
        this.toolBar.add(btnSplitProd);
        this.toolBar.add(separator5);
        this.toolBar.add(btnImpContrSplit);
        this.toolBar.add(separator6);
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
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
        this.toolBar.add(btnSignature);
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
        this.toolBar.add(btnSplit);
        this.toolBar.add(btnAutoMatchSplit);
        this.toolBar.add(btnSplitBaseOnProp);
        this.toolBar.add(btnViewWorkLoadBill);
        this.toolBar.add(btnViewContract);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.dateAuditTime, "value");
		dataBinder.registerBinding("workLoadConfirmBill.number", String.class, this.txtCostBillNumber, "text");
		dataBinder.registerBinding("workLoadConfirmBill.workLoad", java.math.BigDecimal.class, this.txtAmount, "value");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtSplitedAmount, "value");
		dataBinder.registerBinding("contractBill.number", String.class, this.txtCostBillName, "text");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.product", com.kingdee.eas.fdc.basedata.ProductTypeInfo.class, this.kdtEntrys, "product.text");
		dataBinder.registerBinding("entrys.costAccount.curProject.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "costAccount.curProject.id.text");
		dataBinder.registerBinding("entrys.costAccount.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "costAccount.id.text");
		dataBinder.registerBinding("entrys.level", int.class, this.kdtEntrys, "level.text");
		dataBinder.registerBinding("entrys.apportionType", com.kingdee.eas.fdc.basedata.ApportionTypeInfo.class, this.kdtEntrys, "apportionType.name.text");
		dataBinder.registerBinding("entrys.apportionValue", java.math.BigDecimal.class, this.kdtEntrys, "apportionValue.text");
		dataBinder.registerBinding("entrys.directAmount", java.math.BigDecimal.class, this.kdtEntrys, "directAmount.text");
		dataBinder.registerBinding("entrys.apportionValueTotal", java.math.BigDecimal.class, this.kdtEntrys, "apportionValueTotal.text");
		dataBinder.registerBinding("entrys.directAmountTotal", java.math.BigDecimal.class, this.kdtEntrys, "directAmountTotal.text");
		dataBinder.registerBinding("entrys.otherRatioTotal", java.math.BigDecimal.class, this.kdtEntrys, "otherRatioTotal.text");
		dataBinder.registerBinding("entrys.amount", java.math.BigDecimal.class, this.kdtEntrys, "amount.text");
		dataBinder.registerBinding("entrys.contractAmt", java.math.BigDecimal.class, this.kdtEntrys, "contractAmt.text");
		dataBinder.registerBinding("entrys.changeAmt", java.math.BigDecimal.class, this.kdtEntrys, "changeAmt.text");
		dataBinder.registerBinding("entrys.costAmt", java.math.BigDecimal.class, this.kdtEntrys, "costAmt.text");
		dataBinder.registerBinding("entrys.splitedCostAmt", java.math.BigDecimal.class, this.kdtEntrys, "splitedCostAmt.text");
		dataBinder.registerBinding("entrys.directAmt", java.math.BigDecimal.class, this.kdtEntrys, "directAmt.text");
		dataBinder.registerBinding("entrys.splitType", com.kingdee.eas.fdc.basedata.CostSplitTypeEnum.class, this.kdtEntrys, "splitType.text");
		dataBinder.registerBinding("entrys.workLoad", java.math.BigDecimal.class, this.kdtEntrys, "workLoad.text");
		dataBinder.registerBinding("entrys.price", java.math.BigDecimal.class, this.kdtEntrys, "price.text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptCreator, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptAuditor, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.dateCreateTime, "value");
		dataBinder.registerBinding("contractBill.name", String.class, this.txtContrName, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.WorkLoadSplitEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.finance.PaymentSplitInfo)ov;
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
		getValidateHelper().registerBindProperty("workLoadConfirmBill.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("workLoadConfirmBill.workLoad", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.product", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.costAccount.curProject.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.costAccount.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.level", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.apportionType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.apportionValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.directAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.apportionValueTotal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.directAmountTotal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.otherRatioTotal", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.contractAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.changeAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.costAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.splitedCostAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.directAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.splitType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.workLoad", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill.name", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("workLoadConfirmBill.number"));
        sic.add(new SelectorItemInfo("workLoadConfirmBill.workLoad"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("contractBill.number"));
    sic.add(new SelectorItemInfo("entrys.id"));
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
        sic.add(new SelectorItemInfo("entrys.product.*"));
//        sic.add(new SelectorItemInfo("entrys.product.number"));
    sic.add(new SelectorItemInfo("entrys.costAccount.curProject.id"));
    sic.add(new SelectorItemInfo("entrys.costAccount.id"));
    sic.add(new SelectorItemInfo("entrys.level"));
        sic.add(new SelectorItemInfo("entrys.apportionType.*"));
//        sic.add(new SelectorItemInfo("entrys.apportionType.number"));
    sic.add(new SelectorItemInfo("entrys.apportionValue"));
    sic.add(new SelectorItemInfo("entrys.directAmount"));
    sic.add(new SelectorItemInfo("entrys.apportionValueTotal"));
    sic.add(new SelectorItemInfo("entrys.directAmountTotal"));
    sic.add(new SelectorItemInfo("entrys.otherRatioTotal"));
    sic.add(new SelectorItemInfo("entrys.amount"));
    sic.add(new SelectorItemInfo("entrys.contractAmt"));
    sic.add(new SelectorItemInfo("entrys.changeAmt"));
    sic.add(new SelectorItemInfo("entrys.costAmt"));
    sic.add(new SelectorItemInfo("entrys.splitedCostAmt"));
    sic.add(new SelectorItemInfo("entrys.directAmt"));
    sic.add(new SelectorItemInfo("entrys.splitType"));
    sic.add(new SelectorItemInfo("entrys.workLoad"));
    sic.add(new SelectorItemInfo("entrys.price"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("contractBill.name"));
        return sic;
    }        
    	

    /**
     * output actionSplit_actionPerformed method
     */
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAutoMatchSplit_actionPerformed method
     */
    public void actionAutoMatchSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewWorkLoadBill_actionPerformed method
     */
    public void actionViewWorkLoadBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContract_actionPerformed method
     */
    public void actionViewContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSplitBaseOnProp_actionPerformed method
     */
    public void actionSplitBaseOnProp_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSplit() {
    	return false;
    }
	public RequestContext prepareActionAutoMatchSplit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAutoMatchSplit() {
    	return false;
    }
	public RequestContext prepareActionViewWorkLoadBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewWorkLoadBill() {
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
	public RequestContext prepareActionSplitBaseOnProp(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSplitBaseOnProp() {
    	return false;
    }

    /**
     * output ActionSplit class
     */     
    protected class ActionSplit extends ItemAction {     
    
        public ActionSplit()
        {
            this(null);
        }

        public ActionSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWorkLoadSplitEditUI.this, "ActionSplit", "actionSplit_actionPerformed", e);
        }
    }

    /**
     * output ActionAutoMatchSplit class
     */     
    protected class ActionAutoMatchSplit extends ItemAction {     
    
        public ActionAutoMatchSplit()
        {
            this(null);
        }

        public ActionAutoMatchSplit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAutoMatchSplit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAutoMatchSplit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAutoMatchSplit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWorkLoadSplitEditUI.this, "ActionAutoMatchSplit", "actionAutoMatchSplit_actionPerformed", e);
        }
    }

    /**
     * output ActionViewWorkLoadBill class
     */     
    protected class ActionViewWorkLoadBill extends ItemAction {     
    
        public ActionViewWorkLoadBill()
        {
            this(null);
        }

        public ActionViewWorkLoadBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewWorkLoadBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewWorkLoadBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewWorkLoadBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWorkLoadSplitEditUI.this, "ActionViewWorkLoadBill", "actionViewWorkLoadBill_actionPerformed", e);
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
            this.setEnabled(false);
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
            innerActionPerformed("eas", AbstractWorkLoadSplitEditUI.this, "ActionViewContract", "actionViewContract_actionPerformed", e);
        }
    }

    /**
     * output ActionSplitBaseOnProp class
     */     
    protected class ActionSplitBaseOnProp extends ItemAction {     
    
        public ActionSplitBaseOnProp()
        {
            this(null);
        }

        public ActionSplitBaseOnProp(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSplitBaseOnProp.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplitBaseOnProp.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSplitBaseOnProp.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWorkLoadSplitEditUI.this, "ActionSplitBaseOnProp", "actionSplitBaseOnProp_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "WorkLoadSplitEditUI");
    }




}