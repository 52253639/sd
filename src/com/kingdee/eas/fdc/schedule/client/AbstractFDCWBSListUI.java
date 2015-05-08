/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

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
public abstract class AbstractFDCWBSListUI extends com.kingdee.eas.fdc.basedata.client.FDCTreeBaseDataListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCWBSListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportFromTemplate;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemImportFromTemplate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatChangeTaskPro;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatChangeRespDept;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBatChangeTaskPro;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuITemBatChangeRespDept;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnOutputToTemplate;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemOutputToTemplate;
    protected com.kingdee.bos.ctrl.swing.KDContainer conTable;
    protected ActionImportFromTemplate actionImportFromTemplate = null;
    protected ActionBatChangeTaskPro actionBatChangeTaskPro = null;
    protected ActionBatChangeRespDept actionBatChangeRespDept = null;
    protected ActionOutputToTemplate actionOutputToTemplate = null;
    protected ActionUp actionUp = null;
    protected ActionDown actionDown = null;
    protected ActionForward actionForward = null;
    protected ActionBackward actionBackward = null;
    protected ActionReCalcuNumber actionReCalcuNumber = null;
    /**
     * output class constructor
     */
    public AbstractFDCWBSListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCWBSListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.schedule.app", "FDCWBSQuery");
        //actionImportFromTemplate
        this.actionImportFromTemplate = new ActionImportFromTemplate(this);
        getActionManager().registerAction("actionImportFromTemplate", actionImportFromTemplate);
         this.actionImportFromTemplate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBatChangeTaskPro
        this.actionBatChangeTaskPro = new ActionBatChangeTaskPro(this);
        getActionManager().registerAction("actionBatChangeTaskPro", actionBatChangeTaskPro);
         this.actionBatChangeTaskPro.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBatChangeRespDept
        this.actionBatChangeRespDept = new ActionBatChangeRespDept(this);
        getActionManager().registerAction("actionBatChangeRespDept", actionBatChangeRespDept);
         this.actionBatChangeRespDept.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionOutputToTemplate
        this.actionOutputToTemplate = new ActionOutputToTemplate(this);
        getActionManager().registerAction("actionOutputToTemplate", actionOutputToTemplate);
         this.actionOutputToTemplate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUp
        this.actionUp = new ActionUp(this);
        getActionManager().registerAction("actionUp", actionUp);
         this.actionUp.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDown
        this.actionDown = new ActionDown(this);
        getActionManager().registerAction("actionDown", actionDown);
         this.actionDown.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionForward
        this.actionForward = new ActionForward(this);
        getActionManager().registerAction("actionForward", actionForward);
         this.actionForward.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBackward
        this.actionBackward = new ActionBackward(this);
        getActionManager().registerAction("actionBackward", actionBackward);
         this.actionBackward.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReCalcuNumber
        this.actionReCalcuNumber = new ActionReCalcuNumber(this);
        getActionManager().registerAction("actionReCalcuNumber", actionReCalcuNumber);
         this.actionReCalcuNumber.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnImportFromTemplate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemImportFromTemplate = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnBatChangeTaskPro = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBatChangeRespDept = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemBatChangeTaskPro = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuITemBatChangeRespDept = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnOutputToTemplate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemOutputToTemplate = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.conTable = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnImportFromTemplate.setName("btnImportFromTemplate");
        this.menuItemImportFromTemplate.setName("menuItemImportFromTemplate");
        this.btnBatChangeTaskPro.setName("btnBatChangeTaskPro");
        this.btnBatChangeRespDept.setName("btnBatChangeRespDept");
        this.menuItemBatChangeTaskPro.setName("menuItemBatChangeTaskPro");
        this.menuITemBatChangeRespDept.setName("menuITemBatChangeRespDept");
        this.btnOutputToTemplate.setName("btnOutputToTemplate");
        this.menuItemOutputToTemplate.setName("menuItemOutputToTemplate");
        this.conTable.setName("conTable");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol17\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol18\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol19\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol20\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol21\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol22\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol23\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol24\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol25\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol26\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol27\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol28\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol29\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol30\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol31\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol32\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol33\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol34\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol35\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"longNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"parent.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"taskType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"adminDept.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"respDept.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"adminPerson.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"estimateDays\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"isLocked\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"isEnabled\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"isLeaf\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"displayName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"simpleName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"lastUpdateTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"parent.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"parent.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"taskType.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"taskType.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"curProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol22\" /><t:Column t:key=\"curProject.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"curProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol24\" /><t:Column t:key=\"creator.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" t:styleID=\"sCol25\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /><t:Column t:key=\"creator.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"adminPerson.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol28\" /><t:Column t:key=\"adminPerson.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"adminDept.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"adminDept.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" t:styleID=\"sCol31\" /><t:Column t:key=\"lastUpdateUser.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" t:styleID=\"sCol32\" /><t:Column t:key=\"lastUpdateUser.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" t:styleID=\"sCol33\" /><t:Column t:key=\"lastUpdateUser.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" t:styleID=\"sCol34\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" t:styleID=\"sCol35\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{longNumber}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{parent.name}</t:Cell><t:Cell>$Resource{taskType.name}</t:Cell><t:Cell>$Resource{adminDept.name}</t:Cell><t:Cell>$Resource{respDept.name}</t:Cell><t:Cell>$Resource{adminPerson.name}</t:Cell><t:Cell>$Resource{estimateDays}</t:Cell><t:Cell>$Resource{isLocked}</t:Cell><t:Cell>$Resource{isEnabled}</t:Cell><t:Cell>$Resource{isLeaf}</t:Cell><t:Cell>$Resource{displayName}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{simpleName}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{lastUpdateTime}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{parent.number}</t:Cell><t:Cell>$Resource{parent.id}</t:Cell><t:Cell>$Resource{taskType.number}</t:Cell><t:Cell>$Resource{taskType.id}</t:Cell><t:Cell>$Resource{curProject.name}</t:Cell><t:Cell>$Resource{curProject.number}</t:Cell><t:Cell>$Resource{curProject.id}</t:Cell><t:Cell>$Resource{creator.number}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{creator.id}</t:Cell><t:Cell>$Resource{adminPerson.number}</t:Cell><t:Cell>$Resource{adminPerson.id}</t:Cell><t:Cell>$Resource{adminDept.number}</t:Cell><t:Cell>$Resource{adminDept.id}</t:Cell><t:Cell>$Resource{lastUpdateUser.number}</t:Cell><t:Cell>$Resource{lastUpdateUser.name}</t:Cell><t:Cell>$Resource{lastUpdateUser.id}</t:Cell><t:Cell>$Resource{number}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"longNumber","name","level","parent.name","taskType.name","adminDept.name","respDept.name","adminPerson.name","estimateDays","isLocked","isEnabled","isLeaf","displayName","description","simpleName","createTime","lastUpdateTime","id","parent.number","parent.id","taskType.number","taskType.id","curProject.name","curProject.number","curProject.id","creator.number","creator.name","creator.id","adminPerson.number","adminPerson.id","adminDept.number","adminDept.id","lastUpdateUser.number","lastUpdateUser.name","lastUpdateUser.id","number"});

		
        this.menuItemImportData.setText(resHelper.getString("menuItemImportData.text"));		
        this.menuItemImportData.setToolTipText(resHelper.getString("menuItemImportData.toolTipText"));		
        this.menuItemImportData.setVisible(true);
        // btnImportFromTemplate
        this.btnImportFromTemplate.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportFromTemplate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportFromTemplate.setToolTipText(resHelper.getString("btnImportFromTemplate.toolTipText"));		
        this.btnImportFromTemplate.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));		
        this.btnImportFromTemplate.setText(resHelper.getString("btnImportFromTemplate.text"));
        // menuItemImportFromTemplate
        this.menuItemImportFromTemplate.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportFromTemplate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemImportFromTemplate.setText(resHelper.getString("menuItemImportFromTemplate.text"));		
        this.menuItemImportFromTemplate.setToolTipText(resHelper.getString("menuItemImportFromTemplate.toolTipText"));		
        this.menuItemImportFromTemplate.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_input"));
        // btnBatChangeTaskPro
        this.btnBatChangeTaskPro.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatChangeTaskPro, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatChangeTaskPro.setText(resHelper.getString("btnBatChangeTaskPro.text"));		
        this.btnBatChangeTaskPro.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));		
        this.btnBatChangeTaskPro.setToolTipText(resHelper.getString("btnBatChangeTaskPro.toolTipText"));
        // btnBatChangeRespDept
        this.btnBatChangeRespDept.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatChangeRespDept, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatChangeRespDept.setText(resHelper.getString("btnBatChangeRespDept.text"));		
        this.btnBatChangeRespDept.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));		
        this.btnBatChangeRespDept.setToolTipText(resHelper.getString("btnBatChangeRespDept.toolTipText"));
        // menuItemBatChangeTaskPro
        this.menuItemBatChangeTaskPro.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatChangeTaskPro, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBatChangeTaskPro.setText(resHelper.getString("menuItemBatChangeTaskPro.text"));		
        this.menuItemBatChangeTaskPro.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));
        // menuITemBatChangeRespDept
        this.menuITemBatChangeRespDept.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatChangeRespDept, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuITemBatChangeRespDept.setText(resHelper.getString("menuITemBatChangeRespDept.text"));		
        this.menuITemBatChangeRespDept.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));
        // btnOutputToTemplate
        this.btnOutputToTemplate.setAction((IItemAction)ActionProxyFactory.getProxy(actionOutputToTemplate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnOutputToTemplate.setText(resHelper.getString("btnOutputToTemplate.text"));		
        this.btnOutputToTemplate.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
        // menuItemOutputToTemplate
        this.menuItemOutputToTemplate.setAction((IItemAction)ActionProxyFactory.getProxy(actionOutputToTemplate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemOutputToTemplate.setText(resHelper.getString("menuItemOutputToTemplate.text"));		
        this.menuItemOutputToTemplate.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_output"));
        // conTable		
        this.conTable.setEnableActive(false);		
        this.conTable.setTitle(resHelper.getString("conTable.title"));
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
        pnlMain.setBounds(new Rectangle(10, 10, 996, 580));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 996, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(treeView, "left");
        pnlMain.add(conTable, "right");
        //treeView
        treeView.setTree(treeMain);
        //conTable
conTable.getContentPane().setLayout(new BorderLayout(0, 0));        conTable.getContentPane().add(tblMain, BorderLayout.CENTER);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
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
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemMoveTree);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemImportData);
        menuBiz.add(menuItemImportFromTemplate);
        menuBiz.add(menuItemOutputToTemplate);
        menuBiz.add(menuItemBatChangeTaskPro);
        menuBiz.add(menuITemBatChangeRespDept);
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
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnImportFromTemplate);
        this.toolBar.add(btnOutputToTemplate);
        this.toolBar.add(btnBatChangeTaskPro);
        this.toolBar.add(btnBatChangeRespDept);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.FDCWBSListUIHandler";
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("estimateDays"));
        sic.add(new SelectorItemInfo("isLocked"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("isLeaf"));
        sic.add(new SelectorItemInfo("level"));
        sic.add(new SelectorItemInfo("longNumber"));
        sic.add(new SelectorItemInfo("displayName"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("parent.name"));
        sic.add(new SelectorItemInfo("parent.number"));
        sic.add(new SelectorItemInfo("parent.id"));
        sic.add(new SelectorItemInfo("taskType.name"));
        sic.add(new SelectorItemInfo("taskType.number"));
        sic.add(new SelectorItemInfo("taskType.id"));
        sic.add(new SelectorItemInfo("curProject.name"));
        sic.add(new SelectorItemInfo("curProject.number"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("creator.number"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("creator.id"));
        sic.add(new SelectorItemInfo("adminPerson.name"));
        sic.add(new SelectorItemInfo("adminPerson.number"));
        sic.add(new SelectorItemInfo("adminPerson.id"));
        sic.add(new SelectorItemInfo("adminDept.name"));
        sic.add(new SelectorItemInfo("adminDept.number"));
        sic.add(new SelectorItemInfo("adminDept.id"));
        sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        sic.add(new SelectorItemInfo("lastUpdateUser.name"));
        sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        sic.add(new SelectorItemInfo("respDept.name"));
        return sic;
    }        
    	

    /**
     * output actionImportFromTemplate_actionPerformed method
     */
    public void actionImportFromTemplate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBatChangeTaskPro_actionPerformed method
     */
    public void actionBatChangeTaskPro_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBatChangeRespDept_actionPerformed method
     */
    public void actionBatChangeRespDept_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionOutputToTemplate_actionPerformed method
     */
    public void actionOutputToTemplate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUp_actionPerformed method
     */
    public void actionUp_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDown_actionPerformed method
     */
    public void actionDown_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionForward_actionPerformed method
     */
    public void actionForward_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBackward_actionPerformed method
     */
    public void actionBackward_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReCalcuNumber_actionPerformed method
     */
    public void actionReCalcuNumber_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionImportFromTemplate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportFromTemplate() {
    	return false;
    }
	public RequestContext prepareActionBatChangeTaskPro(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatChangeTaskPro() {
    	return false;
    }
	public RequestContext prepareActionBatChangeRespDept(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatChangeRespDept() {
    	return false;
    }
	public RequestContext prepareActionOutputToTemplate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOutputToTemplate() {
    	return false;
    }
	public RequestContext prepareActionUp(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUp() {
    	return false;
    }
	public RequestContext prepareActionDown(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDown() {
    	return false;
    }
	public RequestContext prepareActionForward(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionForward() {
    	return false;
    }
	public RequestContext prepareActionBackward(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBackward() {
    	return false;
    }
	public RequestContext prepareActionReCalcuNumber(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReCalcuNumber() {
    	return false;
    }

    /**
     * output ActionImportFromTemplate class
     */     
    protected class ActionImportFromTemplate extends ItemAction {     
    
        public ActionImportFromTemplate()
        {
            this(null);
        }

        public ActionImportFromTemplate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImportFromTemplate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportFromTemplate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportFromTemplate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCWBSListUI.this, "ActionImportFromTemplate", "actionImportFromTemplate_actionPerformed", e);
        }
    }

    /**
     * output ActionBatChangeTaskPro class
     */     
    protected class ActionBatChangeTaskPro extends ItemAction {     
    
        public ActionBatChangeTaskPro()
        {
            this(null);
        }

        public ActionBatChangeTaskPro(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBatChangeTaskPro.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatChangeTaskPro.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatChangeTaskPro.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCWBSListUI.this, "ActionBatChangeTaskPro", "actionBatChangeTaskPro_actionPerformed", e);
        }
    }

    /**
     * output ActionBatChangeRespDept class
     */     
    protected class ActionBatChangeRespDept extends ItemAction {     
    
        public ActionBatChangeRespDept()
        {
            this(null);
        }

        public ActionBatChangeRespDept(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBatChangeRespDept.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatChangeRespDept.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatChangeRespDept.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCWBSListUI.this, "ActionBatChangeRespDept", "actionBatChangeRespDept_actionPerformed", e);
        }
    }

    /**
     * output ActionOutputToTemplate class
     */     
    protected class ActionOutputToTemplate extends ItemAction {     
    
        public ActionOutputToTemplate()
        {
            this(null);
        }

        public ActionOutputToTemplate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionOutputToTemplate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOutputToTemplate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOutputToTemplate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCWBSListUI.this, "ActionOutputToTemplate", "actionOutputToTemplate_actionPerformed", e);
        }
    }

    /**
     * output ActionUp class
     */     
    protected class ActionUp extends ItemAction {     
    
        public ActionUp()
        {
            this(null);
        }

        public ActionUp(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUp.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUp.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUp.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCWBSListUI.this, "ActionUp", "actionUp_actionPerformed", e);
        }
    }

    /**
     * output ActionDown class
     */     
    protected class ActionDown extends ItemAction {     
    
        public ActionDown()
        {
            this(null);
        }

        public ActionDown(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDown.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDown.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDown.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCWBSListUI.this, "ActionDown", "actionDown_actionPerformed", e);
        }
    }

    /**
     * output ActionForward class
     */     
    protected class ActionForward extends ItemAction {     
    
        public ActionForward()
        {
            this(null);
        }

        public ActionForward(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionForward.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionForward.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionForward.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCWBSListUI.this, "ActionForward", "actionForward_actionPerformed", e);
        }
    }

    /**
     * output ActionBackward class
     */     
    protected class ActionBackward extends ItemAction {     
    
        public ActionBackward()
        {
            this(null);
        }

        public ActionBackward(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionBackward.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBackward.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBackward.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCWBSListUI.this, "ActionBackward", "actionBackward_actionPerformed", e);
        }
    }

    /**
     * output ActionReCalcuNumber class
     */     
    protected class ActionReCalcuNumber extends ItemAction {     
    
        public ActionReCalcuNumber()
        {
            this(null);
        }

        public ActionReCalcuNumber(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionReCalcuNumber.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReCalcuNumber.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReCalcuNumber.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractFDCWBSListUI.this, "ActionReCalcuNumber", "actionReCalcuNumber_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "FDCWBSListUI");
    }




}