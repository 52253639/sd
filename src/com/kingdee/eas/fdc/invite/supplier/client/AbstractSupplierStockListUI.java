/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

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
public abstract class AbstractSupplierStockListUI extends com.kingdee.eas.fdc.basedata.client.FDCBaseDataListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSupplierStockListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDSupplierCont;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDSupplierTypeTreeView;
    protected com.kingdee.bos.ctrl.swing.KDTree kDSupplierTypeTree;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSupplierState;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton review;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton constract;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton appraise;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton infoChange;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton enterHis;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton constractHis;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton appraiseHis;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton changeHis;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton materialInfo;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuReview;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuConstract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuAppraise;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator4;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuInfoChange;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuEnterHis;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuConstractHis;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuAppraiseHis;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuChangeHis;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator6;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem kDMenuMaterialInfo;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator3;
    protected com.kingdee.bos.ctrl.swing.KDMenu kDMenuState;
    protected ActionReview actionReview = null;
    protected ActionConstract actionConstract = null;
    protected ActionAppraise actionAppraise = null;
    protected ActionEnterHis actionEnterHis = null;
    protected ActionConstractHis actionConstractHis = null;
    protected ActionAppraiseHis actionAppraiseHis = null;
    protected ActionMaterialInfo actionMaterialInfo = null;
    protected ActionInfoChange actionInfoChange = null;
    protected ActionChangeHis actionChangeHis = null;
    /**
     * output class constructor
     */
    public AbstractSupplierStockListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSupplierStockListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.app", "SupplierStockQuery");
        //actionReview
        this.actionReview = new ActionReview(this);
        getActionManager().registerAction("actionReview", actionReview);
         this.actionReview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionConstract
        this.actionConstract = new ActionConstract(this);
        getActionManager().registerAction("actionConstract", actionConstract);
         this.actionConstract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAppraise
        this.actionAppraise = new ActionAppraise(this);
        getActionManager().registerAction("actionAppraise", actionAppraise);
         this.actionAppraise.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEnterHis
        this.actionEnterHis = new ActionEnterHis(this);
        getActionManager().registerAction("actionEnterHis", actionEnterHis);
         this.actionEnterHis.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionConstractHis
        this.actionConstractHis = new ActionConstractHis(this);
        getActionManager().registerAction("actionConstractHis", actionConstractHis);
         this.actionConstractHis.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAppraiseHis
        this.actionAppraiseHis = new ActionAppraiseHis(this);
        getActionManager().registerAction("actionAppraiseHis", actionAppraiseHis);
         this.actionAppraiseHis.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMaterialInfo
        this.actionMaterialInfo = new ActionMaterialInfo(this);
        getActionManager().registerAction("actionMaterialInfo", actionMaterialInfo);
         this.actionMaterialInfo.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionInfoChange
        this.actionInfoChange = new ActionInfoChange(this);
        getActionManager().registerAction("actionInfoChange", actionInfoChange);
         this.actionInfoChange.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionChangeHis
        this.actionChangeHis = new ActionChangeHis(this);
        getActionManager().registerAction("actionChangeHis", actionChangeHis);
         this.actionChangeHis.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDSupplierCont = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDSupplierTypeTreeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.kDSupplierTypeTree = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnSupplierState = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.review = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.constract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.appraise = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.infoChange = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.enterHis = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.constractHis = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.appraiseHis = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.changeHis = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.materialInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDMenuReview = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuConstract = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuAppraise = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator4 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDMenuInfoChange = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDMenuEnterHis = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuConstractHis = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuAppraiseHis = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDMenuChangeHis = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator6 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDMenuMaterialInfo = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSeparator3 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDMenuState = new com.kingdee.bos.ctrl.swing.KDMenu();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDSupplierCont.setName("kDSupplierCont");
        this.kDSupplierTypeTreeView.setName("kDSupplierTypeTreeView");
        this.kDSupplierTypeTree.setName("kDSupplierTypeTree");
        this.btnSupplierState.setName("btnSupplierState");
        this.review.setName("review");
        this.constract.setName("constract");
        this.appraise.setName("appraise");
        this.infoChange.setName("infoChange");
        this.enterHis.setName("enterHis");
        this.constractHis.setName("constractHis");
        this.appraiseHis.setName("appraiseHis");
        this.changeHis.setName("changeHis");
        this.materialInfo.setName("materialInfo");
        this.kDMenuReview.setName("kDMenuReview");
        this.kDMenuConstract.setName("kDMenuConstract");
        this.kDMenuAppraise.setName("kDMenuAppraise");
        this.kDSeparator4.setName("kDSeparator4");
        this.kDMenuInfoChange.setName("kDMenuInfoChange");
        this.kDSeparator5.setName("kDSeparator5");
        this.kDMenuEnterHis.setName("kDMenuEnterHis");
        this.kDMenuConstractHis.setName("kDMenuConstractHis");
        this.kDMenuAppraiseHis.setName("kDMenuAppraiseHis");
        this.kDMenuChangeHis.setName("kDMenuChangeHis");
        this.kDSeparator6.setName("kDSeparator6");
        this.kDMenuMaterialInfo.setName("kDMenuMaterialInfo");
        this.kDSeparator3.setName("kDSeparator3");
        this.kDMenuState.setName("kDMenuState");
        // CoreUI		
        this.menuTool.setVisible(false);
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"serviceType.name\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"city\" t:width=\"65\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"serviceArea\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"stockStata\" t:width=\"65\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"linkPerson\" t:width=\"65\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"linkPhone\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"enterHis\" t:width=\"65\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"constractHis\" t:width=\"65\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"appraiseHis\" t:width=\"65\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol10\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol11\" /><t:Column t:key=\"cuID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol12\" /><t:Column t:key=\"typeId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol13\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{serviceType.name}</t:Cell><t:Cell>$Resource{city}</t:Cell><t:Cell>$Resource{serviceArea}</t:Cell><t:Cell>$Resource{stockStata}</t:Cell><t:Cell>$Resource{linkPerson}</t:Cell><t:Cell>$Resource{linkPhone}</t:Cell><t:Cell>$Resource{enterHis}</t:Cell><t:Cell>$Resource{constractHis}</t:Cell><t:Cell>$Resource{appraiseHis}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{cuID}</t:Cell><t:Cell>$Resource{typeId}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMain_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
                this.tblMain.putBindContents("mainQuery",new String[] {"name","serviceType.name","city.name","serviceArea","supplierServiceType.state","linkPerson.personName","linkPerson.contact","enterHis","constractHis","appraiseHis","state","id","CU.id","supplierType.id"});

		
        this.btnAddNew.setVisible(false);		
        this.btnView.setVisible(false);		
        this.btnEdit.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnLocate.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.menuEdit.setVisible(false);		
        this.menuItemView.setVisible(false);		
        this.menuItemLocate.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.btnQueryScheme.setVisible(false);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(200);		
        this.kDSplitPane1.setOneTouchExpandable(true);
        // kDSupplierCont		
        this.kDSupplierCont.setTitle(resHelper.getString("kDSupplierCont.title"));		
        this.kDSupplierCont.setEnableActive(false);
        // kDSupplierTypeTreeView		
        this.kDSupplierTypeTreeView.setShowButton(false);		
        this.kDSupplierTypeTreeView.setTitle(resHelper.getString("kDSupplierTypeTreeView.title"));
        // kDSupplierTypeTree
        this.kDSupplierTypeTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    kDSupplierTypeTree_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnSupplierState		
        this.btnSupplierState.setText(resHelper.getString("btnSupplierState.text"));		
        this.btnSupplierState.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_hiderule"));
        // review
        this.review.setAction((IItemAction)ActionProxyFactory.getProxy(actionReview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.review.setText(resHelper.getString("review.text"));		
        this.review.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_demandcollateresult"));
        // constract
        this.constract.setAction((IItemAction)ActionProxyFactory.getProxy(actionConstract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.constract.setText(resHelper.getString("constract.text"));		
        this.constract.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_execute"));
        // appraise
        this.appraise.setAction((IItemAction)ActionProxyFactory.getProxy(actionAppraise, new Class[] { IItemAction.class }, getServiceContext()));		
        this.appraise.setText(resHelper.getString("appraise.text"));		
        this.appraise.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_alreadycollate"));
        // infoChange
        this.infoChange.setAction((IItemAction)ActionProxyFactory.getProxy(actionInfoChange, new Class[] { IItemAction.class }, getServiceContext()));		
        this.infoChange.setText(resHelper.getString("infoChange.text"));		
        this.infoChange.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cancelcollocate"));
        // enterHis
        this.enterHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionEnterHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.enterHis.setText(resHelper.getString("enterHis.text"));		
        this.enterHis.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_duizsetting"));
        // constractHis
        this.constractHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionConstractHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.constractHis.setText(resHelper.getString("constractHis.text"));		
        this.constractHis.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_estatesetting"));
        // appraiseHis
        this.appraiseHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionAppraiseHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.appraiseHis.setText(resHelper.getString("appraiseHis.text"));		
        this.appraiseHis.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_makeknown"));
        // changeHis
        this.changeHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.changeHis.setText(resHelper.getString("changeHis.text"));		
        this.changeHis.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_listaccount"));
        // materialInfo
        this.materialInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionMaterialInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.materialInfo.setText(resHelper.getString("materialInfo.text"));		
        this.materialInfo.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_copyline"));
        // kDMenuReview
        this.kDMenuReview.setAction((IItemAction)ActionProxyFactory.getProxy(actionReview, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuReview.setText(resHelper.getString("kDMenuReview.text"));
        // kDMenuConstract
        this.kDMenuConstract.setAction((IItemAction)ActionProxyFactory.getProxy(actionConstract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuConstract.setText(resHelper.getString("kDMenuConstract.text"));
        // kDMenuAppraise
        this.kDMenuAppraise.setAction((IItemAction)ActionProxyFactory.getProxy(actionAppraise, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuAppraise.setText(resHelper.getString("kDMenuAppraise.text"));
        // kDSeparator4
        // kDMenuInfoChange
        this.kDMenuInfoChange.setAction((IItemAction)ActionProxyFactory.getProxy(actionInfoChange, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuInfoChange.setText(resHelper.getString("kDMenuInfoChange.text"));
        // kDSeparator5
        // kDMenuEnterHis
        this.kDMenuEnterHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionEnterHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuEnterHis.setText(resHelper.getString("kDMenuEnterHis.text"));
        // kDMenuConstractHis
        this.kDMenuConstractHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionConstractHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuConstractHis.setText(resHelper.getString("kDMenuConstractHis.text"));
        // kDMenuAppraiseHis
        this.kDMenuAppraiseHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionAppraiseHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuAppraiseHis.setText(resHelper.getString("kDMenuAppraiseHis.text"));
        // kDMenuChangeHis
        this.kDMenuChangeHis.setAction((IItemAction)ActionProxyFactory.getProxy(actionChangeHis, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuChangeHis.setText(resHelper.getString("kDMenuChangeHis.text"));
        // kDSeparator6
        // kDMenuMaterialInfo
        this.kDMenuMaterialInfo.setAction((IItemAction)ActionProxyFactory.getProxy(actionMaterialInfo, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDMenuMaterialInfo.setText(resHelper.getString("kDMenuMaterialInfo.text"));
        // kDSeparator3
        // kDMenuState		
        this.kDMenuState.setText(resHelper.getString("kDMenuState.text"));
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
        kDSplitPane1.setBounds(new Rectangle(6, 8, 1000, 614));
        this.add(kDSplitPane1, new KDLayout.Constraints(6, 8, 1000, 614, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(kDSupplierCont, "right");
        kDSplitPane1.add(kDSupplierTypeTreeView, "left");
        //kDSupplierCont
kDSupplierCont.getContentPane().setLayout(new BorderLayout(0, 0));        kDSupplierCont.getContentPane().add(tblMain, BorderLayout.CENTER);
        //kDSupplierTypeTreeView
        kDSupplierTypeTreeView.setTree(kDSupplierTypeTree);

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
        menuFile.add(kDMenuReview);
        menuFile.add(kDMenuConstract);
        menuFile.add(kDMenuAppraise);
        menuFile.add(kDSeparator4);
        menuFile.add(kDMenuInfoChange);
        menuFile.add(kDSeparator5);
        menuFile.add(kDMenuEnterHis);
        menuFile.add(kDMenuConstractHis);
        menuFile.add(kDMenuAppraiseHis);
        menuFile.add(kDMenuChangeHis);
        menuFile.add(kDSeparator6);
        menuFile.add(kDMenuMaterialInfo);
        menuFile.add(kDSeparator3);
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
        menuView.add(kDMenuState);
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
        this.toolBar.add(btnSupplierState);
        this.toolBar.add(review);
        this.toolBar.add(constract);
        this.toolBar.add(appraise);
        this.toolBar.add(infoChange);
        this.toolBar.add(enterHis);
        this.toolBar.add(constractHis);
        this.toolBar.add(appraiseHis);
        this.toolBar.add(changeHis);
        this.toolBar.add(materialInfo);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.app.SupplierStockListUIHandler";
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
     * output tblMain_editValueChanged method
     */
    protected void tblMain_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kDSupplierTypeTree_valueChanged method
     */
    protected void kDSupplierTypeTree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("serviceArea"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("CU.id"));
        sic.add(new SelectorItemInfo("city.name"));
        sic.add(new SelectorItemInfo("supplierType.id"));
        sic.add(new SelectorItemInfo("linkPerson.personName"));
        sic.add(new SelectorItemInfo("enterHis"));
        sic.add(new SelectorItemInfo("constractHis"));
        sic.add(new SelectorItemInfo("appraiseHis"));
        sic.add(new SelectorItemInfo("serviceType.name"));
        sic.add(new SelectorItemInfo("supplierServiceType.state"));
        sic.add(new SelectorItemInfo("linkPerson.contact"));
        return sic;
    }        
    	

    /**
     * output actionReview_actionPerformed method
     */
    public void actionReview_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionConstract_actionPerformed method
     */
    public void actionConstract_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAppraise_actionPerformed method
     */
    public void actionAppraise_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEnterHis_actionPerformed method
     */
    public void actionEnterHis_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionConstractHis_actionPerformed method
     */
    public void actionConstractHis_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAppraiseHis_actionPerformed method
     */
    public void actionAppraiseHis_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMaterialInfo_actionPerformed method
     */
    public void actionMaterialInfo_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionInfoChange_actionPerformed method
     */
    public void actionInfoChange_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionChangeHis_actionPerformed method
     */
    public void actionChangeHis_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionReview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReview() {
    	return false;
    }
	public RequestContext prepareActionConstract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionConstract() {
    	return false;
    }
	public RequestContext prepareActionAppraise(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAppraise() {
    	return false;
    }
	public RequestContext prepareActionEnterHis(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEnterHis() {
    	return false;
    }
	public RequestContext prepareActionConstractHis(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionConstractHis() {
    	return false;
    }
	public RequestContext prepareActionAppraiseHis(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAppraiseHis() {
    	return false;
    }
	public RequestContext prepareActionMaterialInfo(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMaterialInfo() {
    	return false;
    }
	public RequestContext prepareActionInfoChange(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionInfoChange() {
    	return false;
    }
	public RequestContext prepareActionChangeHis(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChangeHis() {
    	return false;
    }

    /**
     * output ActionReview class
     */     
    protected class ActionReview extends ItemAction {     
    
        public ActionReview()
        {
            this(null);
        }

        public ActionReview(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionReview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockListUI.this, "ActionReview", "actionReview_actionPerformed", e);
        }
    }

    /**
     * output ActionConstract class
     */     
    protected class ActionConstract extends ItemAction {     
    
        public ActionConstract()
        {
            this(null);
        }

        public ActionConstract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionConstract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConstract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConstract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockListUI.this, "ActionConstract", "actionConstract_actionPerformed", e);
        }
    }

    /**
     * output ActionAppraise class
     */     
    protected class ActionAppraise extends ItemAction {     
    
        public ActionAppraise()
        {
            this(null);
        }

        public ActionAppraise(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAppraise.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAppraise.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAppraise.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockListUI.this, "ActionAppraise", "actionAppraise_actionPerformed", e);
        }
    }

    /**
     * output ActionEnterHis class
     */     
    protected class ActionEnterHis extends ItemAction {     
    
        public ActionEnterHis()
        {
            this(null);
        }

        public ActionEnterHis(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionEnterHis.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEnterHis.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEnterHis.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockListUI.this, "ActionEnterHis", "actionEnterHis_actionPerformed", e);
        }
    }

    /**
     * output ActionConstractHis class
     */     
    protected class ActionConstractHis extends ItemAction {     
    
        public ActionConstractHis()
        {
            this(null);
        }

        public ActionConstractHis(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionConstractHis.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConstractHis.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConstractHis.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockListUI.this, "ActionConstractHis", "actionConstractHis_actionPerformed", e);
        }
    }

    /**
     * output ActionAppraiseHis class
     */     
    protected class ActionAppraiseHis extends ItemAction {     
    
        public ActionAppraiseHis()
        {
            this(null);
        }

        public ActionAppraiseHis(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAppraiseHis.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAppraiseHis.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAppraiseHis.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockListUI.this, "ActionAppraiseHis", "actionAppraiseHis_actionPerformed", e);
        }
    }

    /**
     * output ActionMaterialInfo class
     */     
    protected class ActionMaterialInfo extends ItemAction {     
    
        public ActionMaterialInfo()
        {
            this(null);
        }

        public ActionMaterialInfo(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionMaterialInfo.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialInfo.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMaterialInfo.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockListUI.this, "ActionMaterialInfo", "actionMaterialInfo_actionPerformed", e);
        }
    }

    /**
     * output ActionInfoChange class
     */     
    protected class ActionInfoChange extends ItemAction {     
    
        public ActionInfoChange()
        {
            this(null);
        }

        public ActionInfoChange(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionInfoChange.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInfoChange.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionInfoChange.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockListUI.this, "ActionInfoChange", "actionInfoChange_actionPerformed", e);
        }
    }

    /**
     * output ActionChangeHis class
     */     
    protected class ActionChangeHis extends ItemAction {     
    
        public ActionChangeHis()
        {
            this(null);
        }

        public ActionChangeHis(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionChangeHis.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeHis.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChangeHis.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierStockListUI.this, "ActionChangeHis", "actionChangeHis_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.client", "SupplierStockListUI");
    }




}