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
public abstract class AbstractSellStatRoomRptUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSellStatRoomRptUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected ActionViewPurchase actionViewPurchase = null;
    /**
     * output class constructor
     */
    public AbstractSellStatRoomRptUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractSellStatRoomRptUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "SellStatRoomRptQuery");
        //actionViewPurchase
        this.actionViewPurchase = new ActionViewPurchase(this);
        getActionManager().registerAction("actionViewPurchase", actionViewPurchase);
         this.actionViewPurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDTreeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.kDSplitPane.setName("kDSplitPane");
        this.kDTreeView.setName("kDTreeView");
        this.treeMain.setName("treeMain");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol13\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol18\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol19\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol20\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol21\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol22\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol23\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol24\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol25\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol27\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol28\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol29\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol30\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol31\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol32\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol33\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol34\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol46\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol47\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol48\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol49\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol50\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol51\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol52\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol56\"><c:Protection hidden=\"true\" /><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"sellProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"unit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"building.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"roomNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"lastPurchase.customerNames\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"phones\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"sellState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"payType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"salesman.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"toPrePurchaseDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"toPurchaseDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"toSignDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"baseBuildingPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"baseRoomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"isBasePriceAudited\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"basePrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"buildPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"isCalByRoomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"roomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"standardTotalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"dealBuildPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"dealRoomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol22\" /><t:Column t:key=\"dealTotalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"finalAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol24\" /><t:Column t:key=\"lastPurchase.contractTotalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" t:styleID=\"sCol25\" /><t:Column t:key=\"sellType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" /><t:Column t:key=\"areaCompensateAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"contactComeBackCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol28\" /><t:Column t:key=\"arrearageCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"otherShouldBackCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" t:styleID=\"sCol31\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" t:styleID=\"sCol32\" /><t:Column t:key=\"actualBuildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" t:styleID=\"sCol33\" /><t:Column t:key=\"actualRoomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" t:styleID=\"sCol34\" /><t:Column t:key=\"roomJoinState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" /><t:Column t:key=\"roomBookState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" /><t:Column t:key=\"roomCompensateState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" /><t:Column t:key=\"roomLoanState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"38\" /><t:Column t:key=\"sellOrder.orderDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"39\" /><t:Column t:key=\"buildingProperty.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"40\" /><t:Column t:key=\"productType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"41\" /><t:Column t:key=\"direction.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"42\" /><t:Column t:key=\"roomModel.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"43\" /><t:Column t:key=\"roomModelType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"44\" /><t:Column t:key=\"sight.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"45\" /><t:Column t:key=\"apportionArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"46\" t:styleID=\"sCol46\" /><t:Column t:key=\"balconyArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"47\" t:styleID=\"sCol47\" /><t:Column t:key=\"guardenArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"48\" t:styleID=\"sCol48\" /><t:Column t:key=\"carbarnArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"49\" t:styleID=\"sCol49\" /><t:Column t:key=\"useArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"50\" t:styleID=\"sCol50\" /><t:Column t:key=\"flatArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"51\" t:styleID=\"sCol51\" /><t:Column t:key=\"serialNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"52\" t:styleID=\"sCol52\" /><t:Column t:key=\"houseProperty\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"53\" /><t:Column t:key=\"roomForm\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"54\" /><t:Column t:key=\"productDetial\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"55\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"56\" t:styleID=\"sCol56\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{sellProject.name}</t:Cell><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{building.name}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{roomNo}</t:Cell><t:Cell>$Resource{lastPurchase.customerNames}</t:Cell><t:Cell>$Resource{phones}</t:Cell><t:Cell>$Resource{sellState}</t:Cell><t:Cell>$Resource{payType.name}</t:Cell><t:Cell>$Resource{salesman.name}</t:Cell><t:Cell>$Resource{toPrePurchaseDate}</t:Cell><t:Cell>$Resource{toPurchaseDate}</t:Cell><t:Cell>$Resource{toSignDate}</t:Cell><t:Cell>$Resource{baseBuildingPrice}</t:Cell><t:Cell>$Resource{baseRoomPrice}</t:Cell><t:Cell>$Resource{isBasePriceAudited}</t:Cell><t:Cell>$Resource{basePrice}</t:Cell><t:Cell>$Resource{buildPrice}</t:Cell><t:Cell>$Resource{isCalByRoomArea}</t:Cell><t:Cell>$Resource{roomPrice}</t:Cell><t:Cell>$Resource{standardTotalAmount}</t:Cell><t:Cell>$Resource{dealBuildPrice}</t:Cell><t:Cell>$Resource{dealRoomPrice}</t:Cell><t:Cell>$Resource{dealTotalAmount}</t:Cell><t:Cell>$Resource{finalAgio}</t:Cell><t:Cell>$Resource{lastPurchase.contractTotalAmount}</t:Cell><t:Cell>$Resource{sellType}</t:Cell><t:Cell>$Resource{areaCompensateAmount}</t:Cell><t:Cell>$Resource{contactComeBackCount}</t:Cell><t:Cell>$Resource{arrearageCount}</t:Cell><t:Cell>$Resource{otherShouldBackCount}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{actualBuildingArea}</t:Cell><t:Cell>$Resource{actualRoomArea}</t:Cell><t:Cell>$Resource{roomJoinState}</t:Cell><t:Cell>$Resource{roomBookState}</t:Cell><t:Cell>$Resource{roomCompensateState}</t:Cell><t:Cell>$Resource{roomLoanState}</t:Cell><t:Cell>$Resource{sellOrder.orderDate}</t:Cell><t:Cell>$Resource{buildingProperty.name}</t:Cell><t:Cell>$Resource{productType}</t:Cell><t:Cell>$Resource{direction.name}</t:Cell><t:Cell>$Resource{roomModel.name}</t:Cell><t:Cell>$Resource{roomModelType}</t:Cell><t:Cell>$Resource{sight.name}</t:Cell><t:Cell>$Resource{apportionArea}</t:Cell><t:Cell>$Resource{balconyArea}</t:Cell><t:Cell>$Resource{guardenArea}</t:Cell><t:Cell>$Resource{carbarnArea}</t:Cell><t:Cell>$Resource{useArea}</t:Cell><t:Cell>$Resource{flatArea}</t:Cell><t:Cell>$Resource{serialNumber}</t:Cell><t:Cell>$Resource{houseProperty}</t:Cell><t:Cell>$Resource{roomForm}</t:Cell><t:Cell>$Resource{productDetial}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{sellProject.name_Row2}</t:Cell><t:Cell>$Resource{unit_Row2}</t:Cell><t:Cell>$Resource{building.name_Row2}</t:Cell><t:Cell>$Resource{number_Row2}</t:Cell><t:Cell>$Resource{roomNo_Row2}</t:Cell><t:Cell>$Resource{lastPurchase.customerNames_Row2}</t:Cell><t:Cell>$Resource{phones_Row2}</t:Cell><t:Cell>$Resource{sellState_Row2}</t:Cell><t:Cell>$Resource{payType.name_Row2}</t:Cell><t:Cell>$Resource{salesman.name_Row2}</t:Cell><t:Cell>$Resource{toPrePurchaseDate_Row2}</t:Cell><t:Cell>$Resource{toPurchaseDate_Row2}</t:Cell><t:Cell>$Resource{toSignDate_Row2}</t:Cell><t:Cell>$Resource{baseBuildingPrice_Row2}</t:Cell><t:Cell>$Resource{baseRoomPrice_Row2}</t:Cell><t:Cell>$Resource{isBasePriceAudited_Row2}</t:Cell><t:Cell>$Resource{basePrice_Row2}</t:Cell><t:Cell>$Resource{buildPrice_Row2}</t:Cell><t:Cell>$Resource{isCalByRoomArea_Row2}</t:Cell><t:Cell>$Resource{roomPrice_Row2}</t:Cell><t:Cell>$Resource{standardTotalAmount_Row2}</t:Cell><t:Cell>$Resource{dealBuildPrice_Row2}</t:Cell><t:Cell>$Resource{dealRoomPrice_Row2}</t:Cell><t:Cell>$Resource{dealTotalAmount_Row2}</t:Cell><t:Cell>$Resource{finalAgio_Row2}</t:Cell><t:Cell>$Resource{lastPurchase.contractTotalAmount_Row2}</t:Cell><t:Cell>$Resource{sellType_Row2}</t:Cell><t:Cell>$Resource{areaCompensateAmount_Row2}</t:Cell><t:Cell>$Resource{contactComeBackCount_Row2}</t:Cell><t:Cell>$Resource{arrearageCount_Row2}</t:Cell><t:Cell>$Resource{otherShouldBackCount_Row2}</t:Cell><t:Cell>$Resource{buildingArea_Row2}</t:Cell><t:Cell>$Resource{roomArea_Row2}</t:Cell><t:Cell>$Resource{actualBuildingArea_Row2}</t:Cell><t:Cell>$Resource{actualRoomArea_Row2}</t:Cell><t:Cell>$Resource{roomJoinState_Row2}</t:Cell><t:Cell>$Resource{roomBookState_Row2}</t:Cell><t:Cell>$Resource{roomCompensateState_Row2}</t:Cell><t:Cell>$Resource{roomLoanState_Row2}</t:Cell><t:Cell>$Resource{sellOrder.orderDate_Row2}</t:Cell><t:Cell>$Resource{buildingProperty.name_Row2}</t:Cell><t:Cell>$Resource{productType_Row2}</t:Cell><t:Cell>$Resource{direction.name_Row2}</t:Cell><t:Cell>$Resource{roomModel.name_Row2}</t:Cell><t:Cell>$Resource{roomModelType_Row2}</t:Cell><t:Cell>$Resource{sight.name_Row2}</t:Cell><t:Cell>$Resource{apportionArea_Row2}</t:Cell><t:Cell>$Resource{balconyArea_Row2}</t:Cell><t:Cell>$Resource{guardenArea_Row2}</t:Cell><t:Cell>$Resource{carbarnArea_Row2}</t:Cell><t:Cell>$Resource{useArea_Row2}</t:Cell><t:Cell>$Resource{flatArea_Row2}</t:Cell><t:Cell>$Resource{serialNumber_Row2}</t:Cell><t:Cell>$Resource{houseProperty_Row2}</t:Cell><t:Cell>$Resource{roomForm_Row2}</t:Cell><t:Cell>$Resource{productDetial_Row2}</t:Cell><t:Cell>$Resource{id_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"1\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"1\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"1\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"0\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"0\" t:right=\"34\" /><t:Block t:top=\"0\" t:left=\"35\" t:bottom=\"0\" t:right=\"38\" /><t:Block t:top=\"0\" t:left=\"39\" t:bottom=\"0\" t:right=\"55\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"sellProject.name","buildingUnit.name","building.name","number","roomNo","lastPurchase.customerNames","lastPurchase.customerPhones","sellState","payType.name","salesman.name","toPrePurchaseDate","toPurchaseDate","toSignDate","baseBuildingPrice","baseRoomPrice","","basePrice","buildPrice","isCalByRoomArea","roomPrice","standardTotalAmount","lastPurchase.dealBuildPrice","lastPurchase.dealRoomPrice","lastPurchase.dealAmount","finalAgio","lastPurchase.conTotalAmount","lastPurchase.sellType","lastPurchase.areaCompensateAmount","","","","buildingArea","roomArea","actualBuildingArea","actualRoomArea","roomJoinState","roomBookState","roomCompensateState","roomLoanState","sellOrder.orderDate","buildingProperty.name","productType.name","direction.name","roomModel.name","roomModelType.name","sight.name","apportionArea","balconyArea","guardenArea","carbarnArea","useArea","flatArea","serialNumber","houseProperty","roomForm.name","productDetial.name","id"});


        // kDSplitPane		
        this.kDSplitPane.setOneTouchExpandable(true);		
        this.kDSplitPane.setDividerLocation(250);
        // kDTreeView		
        this.kDTreeView.setShowButton(false);		
        this.kDTreeView.setShowControlPanel(false);
        // treeMain
        this.treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        kDSplitPane.setBounds(new Rectangle(10, 10, 994, 607));
        this.add(kDSplitPane, new KDLayout.Constraints(10, 10, 994, 607, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane
        kDSplitPane.add(tblMain, "right");
        kDSplitPane.add(kDTreeView, "left");
        //kDTreeView
        kDTreeView.setTree(treeMain);

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
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
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
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SellStatRoomRptUIHandler";
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
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("sellProject.name"));
        sic.add(new SelectorItemInfo("building.name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("roomNo"));
        sic.add(new SelectorItemInfo("lastPurchase.customerNames"));
        sic.add(new SelectorItemInfo("lastPurchase.customerPhones"));
        sic.add(new SelectorItemInfo("sellState"));
        sic.add(new SelectorItemInfo("payType.name"));
        sic.add(new SelectorItemInfo("salesman.name"));
        sic.add(new SelectorItemInfo("toPrePurchaseDate"));
        sic.add(new SelectorItemInfo("toPurchaseDate"));
        sic.add(new SelectorItemInfo("toSignDate"));
        sic.add(new SelectorItemInfo("baseBuildingPrice"));
        sic.add(new SelectorItemInfo("baseRoomPrice"));
        sic.add(new SelectorItemInfo("basePrice"));
        sic.add(new SelectorItemInfo("buildPrice"));
        sic.add(new SelectorItemInfo("isCalByRoomArea"));
        sic.add(new SelectorItemInfo("roomPrice"));
        sic.add(new SelectorItemInfo("standardTotalAmount"));
        sic.add(new SelectorItemInfo("lastPurchase.dealBuildPrice"));
        sic.add(new SelectorItemInfo("lastPurchase.dealRoomPrice"));
        sic.add(new SelectorItemInfo("lastPurchase.dealAmount"));
        sic.add(new SelectorItemInfo("finalAgio"));
        sic.add(new SelectorItemInfo("lastPurchase.conTotalAmount"));
        sic.add(new SelectorItemInfo("lastPurchase.sellType"));
        sic.add(new SelectorItemInfo("lastPurchase.areaCompensateAmount"));
        sic.add(new SelectorItemInfo("buildingArea"));
        sic.add(new SelectorItemInfo("roomArea"));
        sic.add(new SelectorItemInfo("actualBuildingArea"));
        sic.add(new SelectorItemInfo("actualRoomArea"));
        sic.add(new SelectorItemInfo("roomJoinState"));
        sic.add(new SelectorItemInfo("roomBookState"));
        sic.add(new SelectorItemInfo("roomLoanState"));
        sic.add(new SelectorItemInfo("roomCompensateState"));
        sic.add(new SelectorItemInfo("sellOrder.orderDate"));
        sic.add(new SelectorItemInfo("buildingProperty.name"));
        sic.add(new SelectorItemInfo("productType.name"));
        sic.add(new SelectorItemInfo("direction.name"));
        sic.add(new SelectorItemInfo("roomModel.name"));
        sic.add(new SelectorItemInfo("roomModelType.name"));
        sic.add(new SelectorItemInfo("sight.name"));
        sic.add(new SelectorItemInfo("apportionArea"));
        sic.add(new SelectorItemInfo("balconyArea"));
        sic.add(new SelectorItemInfo("guardenArea"));
        sic.add(new SelectorItemInfo("carbarnArea"));
        sic.add(new SelectorItemInfo("useArea"));
        sic.add(new SelectorItemInfo("flatArea"));
        sic.add(new SelectorItemInfo("serialNumber"));
        sic.add(new SelectorItemInfo("houseProperty"));
        sic.add(new SelectorItemInfo("roomForm.name"));
        sic.add(new SelectorItemInfo("productDetial.name"));
        sic.add(new SelectorItemInfo("buildingUnit.name"));
        return sic;
    }        
    	

    /**
     * output actionViewPurchase_actionPerformed method
     */
    public void actionViewPurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionViewPurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewPurchase() {
    	return false;
    }

    /**
     * output ActionViewPurchase class
     */     
    protected class ActionViewPurchase extends ItemAction {     
    
        public ActionViewPurchase()
        {
            this(null);
        }

        public ActionViewPurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewPurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewPurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewPurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellStatRoomRptUI.this, "ActionViewPurchase", "actionViewPurchase_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SellStatRoomRptUI");
    }




}