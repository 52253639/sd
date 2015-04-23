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
public abstract class AbstractSincerObligateEditUI extends com.kingdee.eas.fdc.tenancy.client.TenBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSincerObligateEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabbedPaneRoom;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelCustomer;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane pnlSincerObligate;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelRoom;
    protected com.kingdee.bos.ctrl.swing.KDPanel panelAttachRes;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRooms;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalRoomStandardRent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlightRoomDealRent;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRooms;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelRooms;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalRoomStandardRent;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPlightRoomDealRent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalAttachResStandardRent;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlightAttachRent;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddAttach;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelAttach;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAttach;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalAttachResStandardRent;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPlightAttachRent;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddNewCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelCustomer;
    protected com.kingdee.bos.ctrl.swing.KDPanel paneSincer;
    protected com.kingdee.bos.ctrl.swing.KDPanel paneSinReceive;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProjectNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contObligateDateCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyTermLength;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlightFreeDays;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlightStrartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLeaseCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlightEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsRecSincer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSatisfaction;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellProjectNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinObligateDateCount;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinTermLength;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinFreeDays;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPlightStrartDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLeaseCount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPlightEndDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDatePicker1;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField satisfaction;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSinReceive;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddPayList;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btndelPayList;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExecute;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBlankOut;
    protected com.kingdee.eas.fdc.tenancy.SincerObligateInfo editData = null;
    protected ActionExecute actionExecute = null;
    protected ActionBlankOut actionBlankOut = null;
    /**
     * output class constructor
     */
    public AbstractSincerObligateEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSincerObligateEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionRemove
        String _tempStr = null;
        actionRemove.setEnabled(false);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        _tempStr = resHelper.getString("ActionRemove.SHORT_DESCRIPTION");
        actionRemove.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.LONG_DESCRIPTION");
        actionRemove.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.NAME");
        actionRemove.putValue(ItemAction.NAME, _tempStr);
        this.actionRemove.setBindWorkFlow(true);
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExecute
        this.actionExecute = new ActionExecute(this);
        getActionManager().registerAction("actionExecute", actionExecute);
         this.actionExecute.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBlankOut
        this.actionBlankOut = new ActionBlankOut(this);
        getActionManager().registerAction("actionBlankOut", actionBlankOut);
         this.actionBlankOut.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.tabbedPaneRoom = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.panelCustomer = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlSincerObligate = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.panelRoom = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.panelAttachRes = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblRooms = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contTotalBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalRoomStandardRent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlightRoomDealRent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnAddRooms = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelRooms = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtTotalBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalRoomStandardRent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPlightRoomDealRent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contTotalAttachResStandardRent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlightAttachRent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnAddAttach = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelAttach = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tblAttach = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtTotalAttachResStandardRent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPlightAttachRent = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.tblCustomer = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddNewCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.paneSincer = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.paneSinReceive = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contSellProjectNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contObligateDateCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyTermLength = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlightFreeDays = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlightStrartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLeaseCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlightEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsRecSincer = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contSatisfaction = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSellProjectNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7SellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.spinObligateDateCount = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spinTermLength = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spinFreeDays = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.pkPlightStrartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtLeaseCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkPlightEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDatePicker1 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.satisfaction = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.tblSinReceive = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddPayList = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btndelPayList = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExecute = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBlankOut = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.tabbedPaneRoom.setName("tabbedPaneRoom");
        this.panelCustomer.setName("panelCustomer");
        this.pnlSincerObligate.setName("pnlSincerObligate");
        this.panelRoom.setName("panelRoom");
        this.panelAttachRes.setName("panelAttachRes");
        this.tblRooms.setName("tblRooms");
        this.contTotalBuildingArea.setName("contTotalBuildingArea");
        this.contTotalRoomStandardRent.setName("contTotalRoomStandardRent");
        this.contPlightRoomDealRent.setName("contPlightRoomDealRent");
        this.btnAddRooms.setName("btnAddRooms");
        this.btnDelRooms.setName("btnDelRooms");
        this.txtTotalBuildingArea.setName("txtTotalBuildingArea");
        this.txtTotalRoomStandardRent.setName("txtTotalRoomStandardRent");
        this.txtPlightRoomDealRent.setName("txtPlightRoomDealRent");
        this.contTotalAttachResStandardRent.setName("contTotalAttachResStandardRent");
        this.contPlightAttachRent.setName("contPlightAttachRent");
        this.btnAddAttach.setName("btnAddAttach");
        this.btnDelAttach.setName("btnDelAttach");
        this.tblAttach.setName("tblAttach");
        this.txtTotalAttachResStandardRent.setName("txtTotalAttachResStandardRent");
        this.txtPlightAttachRent.setName("txtPlightAttachRent");
        this.tblCustomer.setName("tblCustomer");
        this.btnAddNewCustomer.setName("btnAddNewCustomer");
        this.btnAddCustomer.setName("btnAddCustomer");
        this.btnDelCustomer.setName("btnDelCustomer");
        this.paneSincer.setName("paneSincer");
        this.paneSinReceive.setName("paneSinReceive");
        this.contSellProjectNumber.setName("contSellProjectNumber");
        this.contSellProject.setName("contSellProject");
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contBizDate.setName("contBizDate");
        this.contObligateDateCount.setName("contObligateDateCount");
        this.contTenancyTermLength.setName("contTenancyTermLength");
        this.contPlightFreeDays.setName("contPlightFreeDays");
        this.contPlightStrartDate.setName("contPlightStrartDate");
        this.contLeaseCount.setName("contLeaseCount");
        this.contPlightEndDate.setName("contPlightEndDate");
        this.contDescription.setName("contDescription");
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.chkIsRecSincer.setName("chkIsRecSincer");
        this.contSatisfaction.setName("contSatisfaction");
        this.txtSellProjectNumber.setName("txtSellProjectNumber");
        this.f7SellProject.setName("f7SellProject");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.pkBizDate.setName("pkBizDate");
        this.spinObligateDateCount.setName("spinObligateDateCount");
        this.spinTermLength.setName("spinTermLength");
        this.spinFreeDays.setName("spinFreeDays");
        this.pkPlightStrartDate.setName("pkPlightStrartDate");
        this.txtLeaseCount.setName("txtLeaseCount");
        this.pkPlightEndDate.setName("pkPlightEndDate");
        this.txtDescription.setName("txtDescription");
        this.prmtCreator.setName("prmtCreator");
        this.kDDatePicker1.setName("kDDatePicker1");
        this.satisfaction.setName("satisfaction");
        this.tblSinReceive.setName("tblSinReceive");
        this.btnAddPayList.setName("btnAddPayList");
        this.btndelPayList.setName("btndelPayList");
        this.btnExecute.setName("btnExecute");
        this.btnBlankOut.setName("btnBlankOut");
        // CoreUI
        // tabbedPaneRoom
        // panelCustomer		
        this.panelCustomer.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,0),1), resHelper.getString("panelCustomer.border.title")));
        // pnlSincerObligate
        // panelRoom
        // panelAttachRes
        // tblRooms
		String tblRoomsStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol1\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"roomState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"floor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"standardRent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"standardRentType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"standardRentPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"plightRent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"plightRentType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"plightRentPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"tenancyArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"fitment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"roomModel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"direction\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"actDeliverDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{roomState}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{floor}</t:Cell><t:Cell>$Resource{standardRent}</t:Cell><t:Cell>$Resource{standardRentType}</t:Cell><t:Cell>$Resource{standardRentPrice}</t:Cell><t:Cell>$Resource{plightRent}</t:Cell><t:Cell>$Resource{plightRentType}</t:Cell><t:Cell>$Resource{plightRentPrice}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{tenancyArea}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{fitment}</t:Cell><t:Cell>$Resource{roomModel}</t:Cell><t:Cell>$Resource{direction}</t:Cell><t:Cell>$Resource{actDeliverDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblRooms.setFormatXml(resHelper.translateString("tblRooms",tblRoomsStrXML));
        this.tblRooms.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblRooms_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // contTotalBuildingArea		
        this.contTotalBuildingArea.setBoundLabelText(resHelper.getString("contTotalBuildingArea.boundLabelText"));		
        this.contTotalBuildingArea.setBoundLabelLength(100);		
        this.contTotalBuildingArea.setBoundLabelUnderline(true);
        // contTotalRoomStandardRent		
        this.contTotalRoomStandardRent.setBoundLabelText(resHelper.getString("contTotalRoomStandardRent.boundLabelText"));		
        this.contTotalRoomStandardRent.setBoundLabelLength(100);		
        this.contTotalRoomStandardRent.setBoundLabelUnderline(true);
        // contPlightRoomDealRent		
        this.contPlightRoomDealRent.setBoundLabelText(resHelper.getString("contPlightRoomDealRent.boundLabelText"));		
        this.contPlightRoomDealRent.setBoundLabelLength(100);		
        this.contPlightRoomDealRent.setBoundLabelUnderline(true);
        // btnAddRooms		
        this.btnAddRooms.setText(resHelper.getString("btnAddRooms.text"));
        this.btnAddRooms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddRooms_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDelRooms		
        this.btnDelRooms.setText(resHelper.getString("btnDelRooms.text"));
        this.btnDelRooms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelRooms_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtTotalBuildingArea
        // txtTotalRoomStandardRent
        // txtPlightRoomDealRent
        // contTotalAttachResStandardRent		
        this.contTotalAttachResStandardRent.setBoundLabelText(resHelper.getString("contTotalAttachResStandardRent.boundLabelText"));		
        this.contTotalAttachResStandardRent.setBoundLabelLength(100);		
        this.contTotalAttachResStandardRent.setBoundLabelUnderline(true);
        // contPlightAttachRent		
        this.contPlightAttachRent.setBoundLabelText(resHelper.getString("contPlightAttachRent.boundLabelText"));		
        this.contPlightAttachRent.setBoundLabelLength(100);		
        this.contPlightAttachRent.setBoundLabelUnderline(true);
        // btnAddAttach		
        this.btnAddAttach.setText(resHelper.getString("btnAddAttach.text"));
        this.btnAddAttach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddAttach_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDelAttach		
        this.btnDelAttach.setText(resHelper.getString("btnDelAttach.text"));
        this.btnDelAttach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelAttach_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tblAttach
		String tblAttachStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"sellProject\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"area\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"building\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"attachName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"attachType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"attachDes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"standardRentType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"standardRent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"standardRentPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"plightRentType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"plightRentPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"plightRent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actDeliverDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{sellProject}</t:Cell><t:Cell>$Resource{area}</t:Cell><t:Cell>$Resource{building}</t:Cell><t:Cell>$Resource{attachName}</t:Cell><t:Cell>$Resource{attachType}</t:Cell><t:Cell>$Resource{attachDes}</t:Cell><t:Cell>$Resource{standardRentType}</t:Cell><t:Cell>$Resource{standardRent}</t:Cell><t:Cell>$Resource{standardRentPrice}</t:Cell><t:Cell>$Resource{plightRentType}</t:Cell><t:Cell>$Resource{plightRentPrice}</t:Cell><t:Cell>$Resource{plightRent}</t:Cell><t:Cell>$Resource{actDeliverDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblAttach.setFormatXml(resHelper.translateString("tblAttach",tblAttachStrXML));
        this.tblAttach.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblAttach_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // txtTotalAttachResStandardRent
        // txtPlightAttachRent
        this.txtPlightAttachRent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtPlightAttachRent_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tblCustomer
		String tblCustomerStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol1\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"propertyPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"postalcode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"certificateName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"certificateNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"mailAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"bookDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{propertyPercent}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{postalcode}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{certificateName}</t:Cell><t:Cell>$Resource{certificateNumber}</t:Cell><t:Cell>$Resource{mailAddress}</t:Cell><t:Cell>$Resource{bookDate}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblCustomer.setFormatXml(resHelper.translateString("tblCustomer",tblCustomerStrXML));
        this.tblCustomer.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblCustomer_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnAddNewCustomer		
        this.btnAddNewCustomer.setText(resHelper.getString("btnAddNewCustomer.text"));		
        this.btnAddNewCustomer.setVisible(false);
        this.btnAddNewCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddNewCustomer_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnAddCustomer		
        this.btnAddCustomer.setText(resHelper.getString("btnAddCustomer.text"));
        this.btnAddCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddCustomer_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDelCustomer		
        this.btnDelCustomer.setText(resHelper.getString("btnDelCustomer.text"));
        this.btnDelCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelCustomer_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // paneSincer
        // paneSinReceive		
        this.paneSinReceive.setVisible(false);
        // contSellProjectNumber		
        this.contSellProjectNumber.setBoundLabelText(resHelper.getString("contSellProjectNumber.boundLabelText"));		
        this.contSellProjectNumber.setBoundLabelLength(100);		
        this.contSellProjectNumber.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contObligateDateCount		
        this.contObligateDateCount.setBoundLabelText(resHelper.getString("contObligateDateCount.boundLabelText"));		
        this.contObligateDateCount.setBoundLabelLength(100);		
        this.contObligateDateCount.setBoundLabelUnderline(true);
        // contTenancyTermLength		
        this.contTenancyTermLength.setBoundLabelText(resHelper.getString("contTenancyTermLength.boundLabelText"));		
        this.contTenancyTermLength.setBoundLabelLength(100);		
        this.contTenancyTermLength.setBoundLabelUnderline(true);
        // contPlightFreeDays		
        this.contPlightFreeDays.setBoundLabelText(resHelper.getString("contPlightFreeDays.boundLabelText"));		
        this.contPlightFreeDays.setBoundLabelLength(100);		
        this.contPlightFreeDays.setBoundLabelUnderline(true);
        // contPlightStrartDate		
        this.contPlightStrartDate.setBoundLabelText(resHelper.getString("contPlightStrartDate.boundLabelText"));		
        this.contPlightStrartDate.setBoundLabelLength(100);		
        this.contPlightStrartDate.setBoundLabelUnderline(true);
        // contLeaseCount		
        this.contLeaseCount.setBoundLabelText(resHelper.getString("contLeaseCount.boundLabelText"));		
        this.contLeaseCount.setBoundLabelLength(100);		
        this.contLeaseCount.setBoundLabelUnderline(true);
        // contPlightEndDate		
        this.contPlightEndDate.setBoundLabelText(resHelper.getString("contPlightEndDate.boundLabelText"));		
        this.contPlightEndDate.setBoundLabelLength(100);		
        this.contPlightEndDate.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // chkIsRecSincer		
        this.chkIsRecSincer.setText(resHelper.getString("chkIsRecSincer.text"));		
        this.chkIsRecSincer.setEnabled(false);
        this.chkIsRecSincer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsRecSincer_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contSatisfaction		
        this.contSatisfaction.setBoundLabelText(resHelper.getString("contSatisfaction.boundLabelText"));		
        this.contSatisfaction.setBoundLabelLength(100);		
        this.contSatisfaction.setBoundLabelUnderline(true);
        // txtSellProjectNumber
        // f7SellProject		
        this.f7SellProject.setDisplayFormat("$name$");		
        this.f7SellProject.setEditFormat("$number$");		
        this.f7SellProject.setCommitFormat("$number$");
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);
        // pkBizDate		
        this.pkBizDate.setEnabled(false);
        // spinObligateDateCount
        // spinTermLength
        this.spinTermLength.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spinTermLength_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // spinFreeDays
        // pkPlightStrartDate
        this.pkPlightStrartDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkPlightStrartDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtLeaseCount
        this.txtLeaseCount.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    txtLeaseCount_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtLeaseCount.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent e) {
                try {
                    txtLeaseCount_mouseMoved(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // pkPlightEndDate
        this.pkPlightEndDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkPlightEndDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // prmtCreator		
        this.prmtCreator.setDoubleBuffered(true);		
        this.prmtCreator.setEnabled(false);
        // kDDatePicker1		
        this.kDDatePicker1.setEnabled(false);
        // satisfaction
        this.satisfaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    satisfaction_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // tblSinReceive
		String tblSinReceiveStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyTypeName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"refundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"canRefundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"hasTransferredAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyTypeName}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{refundmentAmount}</t:Cell><t:Cell>$Resource{canRefundmentAmount}</t:Cell><t:Cell>$Resource{hasTransferredAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblSinReceive.setFormatXml(resHelper.translateString("tblSinReceive",tblSinReceiveStrXML));

        

        // btnAddPayList		
        this.btnAddPayList.setText(resHelper.getString("btnAddPayList.text"));
        this.btnAddPayList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddPayList_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btndelPayList		
        this.btndelPayList.setText(resHelper.getString("btndelPayList.text"));
        this.btndelPayList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btndelPayList_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnExecute
        this.btnExecute.setAction((IItemAction)ActionProxyFactory.getProxy(actionExecute, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExecute.setText(resHelper.getString("btnExecute.text"));
        // btnBlankOut
        this.btnBlankOut.setAction((IItemAction)ActionProxyFactory.getProxy(actionBlankOut, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBlankOut.setText(resHelper.getString("btnBlankOut.text"));
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
        tabbedPaneRoom.setBounds(new Rectangle(7, 231, 999, 188));
        this.add(tabbedPaneRoom, new KDLayout.Constraints(7, 231, 999, 188, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        panelCustomer.setBounds(new Rectangle(8, 430, 997, 182));
        this.add(panelCustomer, new KDLayout.Constraints(8, 430, 997, 182, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        pnlSincerObligate.setBounds(new Rectangle(5, 6, 1002, 218));
        this.add(pnlSincerObligate, new KDLayout.Constraints(5, 6, 1002, 218, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //tabbedPaneRoom
        tabbedPaneRoom.add(panelRoom, resHelper.getString("panelRoom.constraints"));
        tabbedPaneRoom.add(panelAttachRes, resHelper.getString("panelAttachRes.constraints"));
        //panelRoom
        panelRoom.setLayout(new KDLayout());
        panelRoom.putClientProperty("OriginalBounds", new Rectangle(0, 0, 998, 155));        tblRooms.setBounds(new Rectangle(3, 35, 989, 117));
        panelRoom.add(tblRooms, new KDLayout.Constraints(3, 35, 989, 117, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contTotalBuildingArea.setBounds(new Rectangle(4, 9, 236, 19));
        panelRoom.add(contTotalBuildingArea, new KDLayout.Constraints(4, 9, 236, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTotalRoomStandardRent.setBounds(new Rectangle(254, 9, 247, 19));
        panelRoom.add(contTotalRoomStandardRent, new KDLayout.Constraints(254, 9, 247, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPlightRoomDealRent.setBounds(new Rectangle(519, 10, 247, 19));
        panelRoom.add(contPlightRoomDealRent, new KDLayout.Constraints(519, 10, 247, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAddRooms.setBounds(new Rectangle(788, 10, 76, 19));
        panelRoom.add(btnAddRooms, new KDLayout.Constraints(788, 10, 76, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnDelRooms.setBounds(new Rectangle(870, 10, 70, 19));
        panelRoom.add(btnDelRooms, new KDLayout.Constraints(870, 10, 70, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contTotalBuildingArea
        contTotalBuildingArea.setBoundEditor(txtTotalBuildingArea);
        //contTotalRoomStandardRent
        contTotalRoomStandardRent.setBoundEditor(txtTotalRoomStandardRent);
        //contPlightRoomDealRent
        contPlightRoomDealRent.setBoundEditor(txtPlightRoomDealRent);
        //panelAttachRes
        panelAttachRes.setLayout(null);        contTotalAttachResStandardRent.setBounds(new Rectangle(4, 13, 247, 19));
        panelAttachRes.add(contTotalAttachResStandardRent, null);
        contPlightAttachRent.setBounds(new Rectangle(266, 12, 242, 19));
        panelAttachRes.add(contPlightAttachRent, null);
        btnAddAttach.setBounds(new Rectangle(530, 12, 86, 19));
        panelAttachRes.add(btnAddAttach, null);
        btnDelAttach.setBounds(new Rectangle(637, 12, 85, 19));
        panelAttachRes.add(btnDelAttach, null);
        tblAttach.setBounds(new Rectangle(1, 40, 923, 114));
        panelAttachRes.add(tblAttach, null);
        //contTotalAttachResStandardRent
        contTotalAttachResStandardRent.setBoundEditor(txtTotalAttachResStandardRent);
        //contPlightAttachRent
        contPlightAttachRent.setBoundEditor(txtPlightAttachRent);
        //panelCustomer
        panelCustomer.setLayout(new KDLayout());
        panelCustomer.putClientProperty("OriginalBounds", new Rectangle(8, 430, 997, 182));        tblCustomer.setBounds(new Rectangle(11, 49, 918, 120));
        panelCustomer.add(tblCustomer, new KDLayout.Constraints(11, 49, 918, 120, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnAddNewCustomer.setBounds(new Rectangle(636, 23, 94, 19));
        panelCustomer.add(btnAddNewCustomer, new KDLayout.Constraints(636, 23, 94, 19, 0));
        btnAddCustomer.setBounds(new Rectangle(736, 23, 84, 19));
        panelCustomer.add(btnAddCustomer, new KDLayout.Constraints(736, 23, 84, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnDelCustomer.setBounds(new Rectangle(825, 23, 81, 19));
        panelCustomer.add(btnDelCustomer, new KDLayout.Constraints(825, 23, 81, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlSincerObligate
        pnlSincerObligate.add(paneSincer, resHelper.getString("paneSincer.constraints"));
        pnlSincerObligate.add(paneSinReceive, resHelper.getString("paneSinReceive.constraints"));
        //paneSincer
        paneSincer.setLayout(new KDLayout());
        paneSincer.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1001, 185));        contSellProjectNumber.setBounds(new Rectangle(3, 9, 270, 19));
        paneSincer.add(contSellProjectNumber, new KDLayout.Constraints(3, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellProject.setBounds(new Rectangle(298, 7, 270, 19));
        paneSincer.add(contSellProject, new KDLayout.Constraints(298, 7, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(4, 36, 270, 19));
        paneSincer.add(contNumber, new KDLayout.Constraints(4, 36, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(299, 34, 270, 19));
        paneSincer.add(contName, new KDLayout.Constraints(299, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(602, 32, 270, 19));
        paneSincer.add(contBizDate, new KDLayout.Constraints(602, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contObligateDateCount.setBounds(new Rectangle(4, 63, 270, 19));
        paneSincer.add(contObligateDateCount, new KDLayout.Constraints(4, 63, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTenancyTermLength.setBounds(new Rectangle(300, 61, 270, 19));
        paneSincer.add(contTenancyTermLength, new KDLayout.Constraints(300, 61, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPlightFreeDays.setBounds(new Rectangle(602, 59, 270, 19));
        paneSincer.add(contPlightFreeDays, new KDLayout.Constraints(602, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPlightStrartDate.setBounds(new Rectangle(5, 90, 270, 19));
        paneSincer.add(contPlightStrartDate, new KDLayout.Constraints(5, 90, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLeaseCount.setBounds(new Rectangle(302, 88, 270, 19));
        paneSincer.add(contLeaseCount, new KDLayout.Constraints(302, 88, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPlightEndDate.setBounds(new Rectangle(603, 86, 270, 19));
        paneSincer.add(contPlightEndDate, new KDLayout.Constraints(603, 86, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(6, 116, 913, 30));
        paneSincer.add(contDescription, new KDLayout.Constraints(6, 116, 913, 30, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(302, 156, 270, 19));
        paneSincer.add(contCreator, new KDLayout.Constraints(302, 156, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(603, 154, 270, 19));
        paneSincer.add(contCreateTime, new KDLayout.Constraints(603, 154, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkIsRecSincer.setBounds(new Rectangle(602, 8, 271, 19));
        paneSincer.add(chkIsRecSincer, new KDLayout.Constraints(602, 8, 271, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSatisfaction.setBounds(new Rectangle(6, 156, 270, 19));
        paneSincer.add(contSatisfaction, new KDLayout.Constraints(6, 156, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contSellProjectNumber
        contSellProjectNumber.setBoundEditor(txtSellProjectNumber);
        //contSellProject
        contSellProject.setBoundEditor(f7SellProject);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contObligateDateCount
        contObligateDateCount.setBoundEditor(spinObligateDateCount);
        //contTenancyTermLength
        contTenancyTermLength.setBoundEditor(spinTermLength);
        //contPlightFreeDays
        contPlightFreeDays.setBoundEditor(spinFreeDays);
        //contPlightStrartDate
        contPlightStrartDate.setBoundEditor(pkPlightStrartDate);
        //contLeaseCount
        contLeaseCount.setBoundEditor(txtLeaseCount);
        //contPlightEndDate
        contPlightEndDate.setBoundEditor(pkPlightEndDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDatePicker1);
        //contSatisfaction
        contSatisfaction.setBoundEditor(satisfaction);
        //paneSinReceive
        paneSinReceive.setLayout(null);        tblSinReceive.setBounds(new Rectangle(5, 38, 974, 145));
        paneSinReceive.add(tblSinReceive, null);
        btnAddPayList.setBounds(new Rectangle(744, 16, 72, 19));
        paneSinReceive.add(btnAddPayList, null);
        btndelPayList.setBounds(new Rectangle(827, 16, 74, 19));
        paneSinReceive.add(btndelPayList, null);

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
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
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
        this.toolBar.add(btnExecute);
        this.toolBar.add(btnBlankOut);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("obligateDateCount", int.class, this.spinObligateDateCount, "value");
		dataBinder.registerBinding("tenancyTermLength", int.class, this.spinTermLength, "value");
		dataBinder.registerBinding("plightFreeDays", int.class, this.spinFreeDays, "value");
		dataBinder.registerBinding("plightStrartDate", java.util.Date.class, this.pkPlightStrartDate, "value");
		dataBinder.registerBinding("leaseCount", java.math.BigDecimal.class, this.txtLeaseCount, "value");
		dataBinder.registerBinding("plightEndDate", java.util.Date.class, this.pkPlightEndDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("satisfaction", int.class, this.satisfaction, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.SincerObligateEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.tenancy.SincerObligateInfo)ov;
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
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("obligateDateCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenancyTermLength", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("plightFreeDays", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("plightStrartDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("leaseCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("plightEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("satisfaction", ValidateHelper.ON_SAVE);    		
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
     * output tblRooms_editStopped method
     */
    protected void tblRooms_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnAddRooms_actionPerformed method
     */
    protected void btnAddRooms_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnDelRooms_actionPerformed method
     */
    protected void btnDelRooms_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnAddAttach_actionPerformed method
     */
    protected void btnAddAttach_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnDelAttach_actionPerformed method
     */
    protected void btnDelAttach_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblAttach_editStopped method
     */
    protected void tblAttach_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output txtPlightAttachRent_actionPerformed method
     */
    protected void txtPlightAttachRent_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblCustomer_editStopped method
     */
    protected void tblCustomer_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output btnAddNewCustomer_actionPerformed method
     */
    protected void btnAddNewCustomer_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnAddCustomer_actionPerformed method
     */
    protected void btnAddCustomer_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnDelCustomer_actionPerformed method
     */
    protected void btnDelCustomer_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output chkIsRecSincer_actionPerformed method
     */
    protected void chkIsRecSincer_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output spinTermLength_stateChanged method
     */
    protected void spinTermLength_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output pkPlightStrartDate_dataChanged method
     */
    protected void pkPlightStrartDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtLeaseCount_dataChanged method
     */
    protected void txtLeaseCount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtLeaseCount_mouseMoved method
     */
    protected void txtLeaseCount_mouseMoved(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output pkPlightEndDate_dataChanged method
     */
    protected void pkPlightEndDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output satisfaction_actionPerformed method
     */
    protected void satisfaction_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAddPayList_actionPerformed method
     */
    protected void btnAddPayList_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btndelPayList_actionPerformed method
     */
    protected void btndelPayList_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("obligateDateCount"));
        sic.add(new SelectorItemInfo("tenancyTermLength"));
        sic.add(new SelectorItemInfo("plightFreeDays"));
        sic.add(new SelectorItemInfo("plightStrartDate"));
        sic.add(new SelectorItemInfo("leaseCount"));
        sic.add(new SelectorItemInfo("plightEndDate"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("satisfaction"));
        return sic;
    }        
    	

    /**
     * output actionRemove_actionPerformed method
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
    	

    /**
     * output actionExecute_actionPerformed method
     */
    public void actionExecute_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBlankOut_actionPerformed method
     */
    public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionRemove(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRemove(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemove() {
    	return false;
    }
	public RequestContext prepareActionExecute(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExecute() {
    	return false;
    }
	public RequestContext prepareActionBlankOut(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBlankOut() {
    	return false;
    }

    /**
     * output ActionExecute class
     */     
    protected class ActionExecute extends ItemAction {     
    
        public ActionExecute()
        {
            this(null);
        }

        public ActionExecute(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setDaemonRun(true);
            _tempStr = resHelper.getString("ActionExecute.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExecute.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExecute.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerObligateEditUI.this, "ActionExecute", "actionExecute_actionPerformed", e);
        }
    }

    /**
     * output ActionBlankOut class
     */     
    protected class ActionBlankOut extends ItemAction {     
    
        public ActionBlankOut()
        {
            this(null);
        }

        public ActionBlankOut(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBlankOut.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBlankOut.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBlankOut.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerObligateEditUI.this, "ActionBlankOut", "actionBlankOut_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "SincerObligateEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}