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
public abstract class AbstractTENBatchReceivingUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTENBatchReceivingUI.class);
    protected com.kingdee.bos.ctrl.swing.KDContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDContainer contSelectFund;
    protected com.kingdee.bos.ctrl.swing.KDContainer contSelectedFund;
    protected com.kingdee.bos.ctrl.swing.KDContainer contReceiveNote;
    protected com.kingdee.bos.ctrl.swing.KDButton confirmBtn;
    protected com.kingdee.bos.ctrl.swing.KDButton cancelBtn;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRoom;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSelectFund;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSelectedFund;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblReceiveNote;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    protected ActionCancelBtn actionCancelBtn = null;
    protected ActionConfirmBtn actionConfirmBtn = null;
    protected ActionRoomAddLine actionRoomAddLine = null;
    protected ActionRoomDeleteLine actionRoomDeleteLine = null;
    protected ActionSelectFundConfirm actionSelectFundConfirm = null;
    protected ActionReceiveAddLine actionReceiveAddLine = null;
    protected ActionReceiveDeleteLine actionReceiveDeleteLine = null;
    protected ActionSelectedFundDeleteLine actionSelectedFundDeleteLine = null;
    /**
     * output class constructor
     */
    public AbstractTENBatchReceivingUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTENBatchReceivingUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionCancelBtn
        this.actionCancelBtn = new ActionCancelBtn(this);
        getActionManager().registerAction("actionCancelBtn", actionCancelBtn);
         this.actionCancelBtn.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionConfirmBtn
        this.actionConfirmBtn = new ActionConfirmBtn(this);
        getActionManager().registerAction("actionConfirmBtn", actionConfirmBtn);
         this.actionConfirmBtn.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRoomAddLine
        this.actionRoomAddLine = new ActionRoomAddLine(this);
        getActionManager().registerAction("actionRoomAddLine", actionRoomAddLine);
         this.actionRoomAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRoomDeleteLine
        this.actionRoomDeleteLine = new ActionRoomDeleteLine(this);
        getActionManager().registerAction("actionRoomDeleteLine", actionRoomDeleteLine);
         this.actionRoomDeleteLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSelectFundConfirm
        this.actionSelectFundConfirm = new ActionSelectFundConfirm(this);
        getActionManager().registerAction("actionSelectFundConfirm", actionSelectFundConfirm);
         this.actionSelectFundConfirm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReceiveAddLine
        this.actionReceiveAddLine = new ActionReceiveAddLine(this);
        getActionManager().registerAction("actionReceiveAddLine", actionReceiveAddLine);
         this.actionReceiveAddLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReceiveDeleteLine
        this.actionReceiveDeleteLine = new ActionReceiveDeleteLine(this);
        getActionManager().registerAction("actionReceiveDeleteLine", actionReceiveDeleteLine);
         this.actionReceiveDeleteLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSelectedFundDeleteLine
        this.actionSelectedFundDeleteLine = new ActionSelectedFundDeleteLine(this);
        getActionManager().registerAction("actionSelectedFundDeleteLine", actionSelectedFundDeleteLine);
         this.actionSelectedFundDeleteLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contSelectFund = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contSelectedFund = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contReceiveNote = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.confirmBtn = new com.kingdee.bos.ctrl.swing.KDButton();
        this.cancelBtn = new com.kingdee.bos.ctrl.swing.KDButton();
        this.tblRoom = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblSelectFund = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblSelectedFund = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblReceiveNote = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contRoom.setName("contRoom");
        this.contSelectFund.setName("contSelectFund");
        this.contSelectedFund.setName("contSelectedFund");
        this.contReceiveNote.setName("contReceiveNote");
        this.confirmBtn.setName("confirmBtn");
        this.cancelBtn.setName("cancelBtn");
        this.tblRoom.setName("tblRoom");
        this.tblSelectFund.setName("tblSelectFund");
        this.tblSelectedFund.setName("tblSelectedFund");
        this.tblReceiveNote.setName("tblReceiveNote");
        // CoreUI
        // contRoom		
        this.contRoom.setTitle(resHelper.getString("contRoom.title"));
        // contSelectFund		
        this.contSelectFund.setTitle(resHelper.getString("contSelectFund.title"));
        // contSelectedFund		
        this.contSelectedFund.setTitle(resHelper.getString("contSelectedFund.title"));
        // contReceiveNote		
        this.contReceiveNote.setTitle(resHelper.getString("contReceiveNote.title"));
        // confirmBtn
        this.confirmBtn.setAction((IItemAction)ActionProxyFactory.getProxy(actionConfirmBtn, new Class[] { IItemAction.class }, getServiceContext()));		
        this.confirmBtn.setText(resHelper.getString("confirmBtn.text"));		
        this.confirmBtn.setToolTipText(resHelper.getString("confirmBtn.toolTipText"));
        // cancelBtn
        this.cancelBtn.setAction((IItemAction)ActionProxyFactory.getProxy(actionCancelBtn, new Class[] { IItemAction.class }, getServiceContext()));		
        this.cancelBtn.setText(resHelper.getString("cancelBtn.text"));		
        this.cancelBtn.setToolTipText(resHelper.getString("cancelBtn.toolTipText"));
        // tblRoom
		String tblRoomStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"roomState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildingFloor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tenancyBill\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{roomState}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{buildingFloor}</t:Cell><t:Cell>$Resource{tenancyBill}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{tel}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblRoom.setFormatXml(resHelper.translateString("tblRoom",tblRoomStrXML));
        this.tblRoom.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblRoom_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblRoom.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblRoom_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // tblSelectFund
		String tblSelectFundStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol8\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"select\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tenancyBill\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"moneyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"deratePeriod\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"derateAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"revListId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{select}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{tenancyBill}</t:Cell><t:Cell>$Resource{moneyType}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{deratePeriod}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{derateAmount}</t:Cell><t:Cell>$Resource{revListId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblSelectFund.setFormatXml(resHelper.translateString("tblSelectFund",tblSelectFundStrXML));

        

        // tblSelectedFund
		String tblSelectedFundStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol8\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tenancyBill\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"moneyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"deratePeriod\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"derateAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"settlementType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"settlementNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"revListId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{tenancyBill}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{moneyType}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{deratePeriod}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{derateAmount}</t:Cell><t:Cell>$Resource{settlementType}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{settlementNumber}</t:Cell><t:Cell>$Resource{revListId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblSelectedFund.setFormatXml(resHelper.translateString("tblSelectedFund",tblSelectedFundStrXML));

        

        // tblReceiveNote
		String tblReceiveNoteStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"settlementType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"settlementNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"receiveAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"receiver\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{settlementType}</t:Cell><t:Cell>$Resource{settlementNumber}</t:Cell><t:Cell>$Resource{receiveAmount}</t:Cell><t:Cell>$Resource{receiver}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblReceiveNote.setFormatXml(resHelper.translateString("tblReceiveNote",tblReceiveNoteStrXML));
        this.tblReceiveNote.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblReceiveNote_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

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
        this.setBounds(new Rectangle(10, 10, 800, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 800, 600));
        contRoom.setBounds(new Rectangle(10, 11, 780, 125));
        this.add(contRoom, new KDLayout.Constraints(10, 11, 780, 125, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contSelectFund.setBounds(new Rectangle(10, 149, 780, 125));
        this.add(contSelectFund, new KDLayout.Constraints(10, 149, 780, 125, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contSelectedFund.setBounds(new Rectangle(10, 287, 780, 125));
        this.add(contSelectedFund, new KDLayout.Constraints(10, 287, 780, 125, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contReceiveNote.setBounds(new Rectangle(10, 425, 780, 125));
        this.add(contReceiveNote, new KDLayout.Constraints(10, 425, 780, 125, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        confirmBtn.setBounds(new Rectangle(607, 565, 73, 21));
        this.add(confirmBtn, new KDLayout.Constraints(607, 565, 73, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT));
        cancelBtn.setBounds(new Rectangle(717, 565, 73, 21));
        this.add(cancelBtn, new KDLayout.Constraints(717, 565, 73, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT));
        //contRoom
contRoom.getContentPane().setLayout(new BorderLayout(0, 0));        contRoom.getContentPane().add(tblRoom, BorderLayout.CENTER);
        //contSelectFund
contSelectFund.getContentPane().setLayout(new BorderLayout(0, 0));        contSelectFund.getContentPane().add(tblSelectFund, BorderLayout.CENTER);
        //contSelectedFund
contSelectedFund.getContentPane().setLayout(new BorderLayout(0, 0));        contSelectedFund.getContentPane().add(tblSelectedFund, BorderLayout.CENTER);
        //contReceiveNote
contReceiveNote.getContentPane().setLayout(new BorderLayout(0, 0));        contReceiveNote.getContentPane().add(tblReceiveNote, BorderLayout.CENTER);

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
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.TENBatchReceivingUIHandler";
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
        this.editData = (com.kingdee.eas.framework.CoreBaseInfo)ov;
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
        }
    }

    /**
     * output tblRoom_editStopped method
     */
    protected void tblRoom_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblRoom_tableSelectChanged method
     */
    protected void tblRoom_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output tblReceiveNote_editStopped method
     */
    protected void tblReceiveNote_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
     * output actionCancelBtn_actionPerformed method
     */
    public void actionCancelBtn_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionConfirmBtn_actionPerformed method
     */
    public void actionConfirmBtn_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRoomAddLine_actionPerformed method
     */
    public void actionRoomAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRoomDeleteLine_actionPerformed method
     */
    public void actionRoomDeleteLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSelectFundConfirm_actionPerformed method
     */
    public void actionSelectFundConfirm_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReceiveAddLine_actionPerformed method
     */
    public void actionReceiveAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReceiveDeleteLine_actionPerformed method
     */
    public void actionReceiveDeleteLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSelectedFundDeleteLine_actionPerformed method
     */
    public void actionSelectedFundDeleteLine_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionCancelBtn class
     */     
    protected class ActionCancelBtn extends ItemAction {     
    
        public ActionCancelBtn()
        {
            this(null);
        }

        public ActionCancelBtn(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCancelBtn.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelBtn.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCancelBtn.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTENBatchReceivingUI.this, "ActionCancelBtn", "actionCancelBtn_actionPerformed", e);
        }
    }

    /**
     * output ActionConfirmBtn class
     */     
    protected class ActionConfirmBtn extends ItemAction {     
    
        public ActionConfirmBtn()
        {
            this(null);
        }

        public ActionConfirmBtn(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionConfirmBtn.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirmBtn.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConfirmBtn.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTENBatchReceivingUI.this, "ActionConfirmBtn", "actionConfirmBtn_actionPerformed", e);
        }
    }

    /**
     * output ActionRoomAddLine class
     */     
    protected class ActionRoomAddLine extends ItemAction {     
    
        public ActionRoomAddLine()
        {
            this(null);
        }

        public ActionRoomAddLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRoomAddLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRoomAddLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRoomAddLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTENBatchReceivingUI.this, "ActionRoomAddLine", "actionRoomAddLine_actionPerformed", e);
        }
    }

    /**
     * output ActionRoomDeleteLine class
     */     
    protected class ActionRoomDeleteLine extends ItemAction {     
    
        public ActionRoomDeleteLine()
        {
            this(null);
        }

        public ActionRoomDeleteLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRoomDeleteLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRoomDeleteLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRoomDeleteLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTENBatchReceivingUI.this, "ActionRoomDeleteLine", "actionRoomDeleteLine_actionPerformed", e);
        }
    }

    /**
     * output ActionSelectFundConfirm class
     */     
    protected class ActionSelectFundConfirm extends ItemAction {     
    
        public ActionSelectFundConfirm()
        {
            this(null);
        }

        public ActionSelectFundConfirm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSelectFundConfirm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectFundConfirm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectFundConfirm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTENBatchReceivingUI.this, "ActionSelectFundConfirm", "actionSelectFundConfirm_actionPerformed", e);
        }
    }

    /**
     * output ActionReceiveAddLine class
     */     
    protected class ActionReceiveAddLine extends ItemAction {     
    
        public ActionReceiveAddLine()
        {
            this(null);
        }

        public ActionReceiveAddLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionReceiveAddLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveAddLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveAddLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTENBatchReceivingUI.this, "ActionReceiveAddLine", "actionReceiveAddLine_actionPerformed", e);
        }
    }

    /**
     * output ActionReceiveDeleteLine class
     */     
    protected class ActionReceiveDeleteLine extends ItemAction {     
    
        public ActionReceiveDeleteLine()
        {
            this(null);
        }

        public ActionReceiveDeleteLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionReceiveDeleteLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveDeleteLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReceiveDeleteLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTENBatchReceivingUI.this, "ActionReceiveDeleteLine", "actionReceiveDeleteLine_actionPerformed", e);
        }
    }

    /**
     * output ActionSelectedFundDeleteLine class
     */     
    protected class ActionSelectedFundDeleteLine extends ItemAction {     
    
        public ActionSelectedFundDeleteLine()
        {
            this(null);
        }

        public ActionSelectedFundDeleteLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSelectedFundDeleteLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectedFundDeleteLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelectedFundDeleteLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTENBatchReceivingUI.this, "ActionSelectedFundDeleteLine", "actionSelectedFundDeleteLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "TENBatchReceivingUI");
    }




}