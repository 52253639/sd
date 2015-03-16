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
public abstract class AbstractInvestorHouseListUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractInvestorHouseListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuDelTrackRecord;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeUnitView;
    protected com.kingdee.bos.ctrl.swing.KDTree kDTreeUnit;
    protected ActionTrackRecord actionTrackRecord = null;
    protected ActionDelTrackRecord actionDelTrackRecord = null;
    /**
     * output class constructor
     */
    public AbstractInvestorHouseListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractInvestorHouseListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "InvestorHouseQuery");
        //actionTrackRecord
        this.actionTrackRecord = new ActionTrackRecord(this);
        getActionManager().registerAction("actionTrackRecord", actionTrackRecord);
         this.actionTrackRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelTrackRecord
        this.actionDelTrackRecord = new ActionDelTrackRecord(this);
        getActionManager().registerAction("actionDelTrackRecord", actionDelTrackRecord);
         this.actionDelTrackRecord.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnTrackRecord = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuTrackRecord = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnDelTrackRecord = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuDelTrackRecord = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDTreeUnitView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.kDTreeUnit = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnTrackRecord.setName("btnTrackRecord");
        this.menuTrackRecord.setName("menuTrackRecord");
        this.btnDelTrackRecord.setName("btnDelTrackRecord");
        this.menuDelTrackRecord.setName("menuDelTrackRecord");
        this.kDTreeUnitView.setName("kDTreeUnitView");
        this.kDTreeUnit.setName("kDTreeUnit");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"oldEnrollName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"habitationArea.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"orderLocation\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"orderSourceClass\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"assignBuilding\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"assignRoom\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"areaDescription\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"priceDescription\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"chargeComplexion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"salesman.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"informationSource.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"buildingProperty.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"OwnerShipState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"ownerShipNew\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"wuyeActuality\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"booker.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"bizDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"creator.createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{oldEnrollName}</t:Cell><t:Cell>$Resource{habitationArea.name}</t:Cell><t:Cell>$Resource{orderLocation}</t:Cell><t:Cell>$Resource{orderSourceClass}</t:Cell><t:Cell>$Resource{assignBuilding}</t:Cell><t:Cell>$Resource{assignRoom}</t:Cell><t:Cell>$Resource{areaDescription}</t:Cell><t:Cell>$Resource{priceDescription}</t:Cell><t:Cell>$Resource{chargeComplexion}</t:Cell><t:Cell>$Resource{salesman.name}</t:Cell><t:Cell>$Resource{informationSource.name}</t:Cell><t:Cell>$Resource{buildingProperty.name}</t:Cell><t:Cell>$Resource{OwnerShipState}</t:Cell><t:Cell>$Resource{ownerShipNew}</t:Cell><t:Cell>$Resource{wuyeActuality}</t:Cell><t:Cell>$Resource{booker.name}</t:Cell><t:Cell>$Resource{bizDate}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{creator.createTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","number","name","oldEnrollName","habitationArea.name","orderLocation","orderSourceClass","assignBuilding","assignRoom","areaDescription","priceDescription","chargeComplexion","salesman.name","informationSource.name","buildingProperty.name","OwnerShipState","ownerShipNew","wuyeActuality","booker.name","bizDate","creator.name","createTime"});


        // btnTrackRecord
        this.btnTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTrackRecord.setText(resHelper.getString("btnTrackRecord.text"));
        // menuTrackRecord
        this.menuTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuTrackRecord.setText(resHelper.getString("menuTrackRecord.text"));
        // btnDelTrackRecord
        this.btnDelTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelTrackRecord.setText(resHelper.getString("btnDelTrackRecord.text"));
        // menuDelTrackRecord
        this.menuDelTrackRecord.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelTrackRecord, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuDelTrackRecord.setText(resHelper.getString("menuDelTrackRecord.text"));
        // kDTreeUnitView
        // kDTreeUnit
        this.kDTreeUnit.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    kDTreeUnit_valueChanged(e);
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
        tblMain.setBounds(new Rectangle(216, 8, 790, 614));
        this.add(tblMain, new KDLayout.Constraints(216, 8, 790, 614, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTreeUnitView.setBounds(new Rectangle(9, 8, 200, 614));
        this.add(kDTreeUnitView, new KDLayout.Constraints(9, 8, 200, 614, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDTreeUnitView
        kDTreeUnitView.setTree(kDTreeUnit);

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
        menuBiz.add(menuTrackRecord);
        menuBiz.add(menuDelTrackRecord);
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
        this.toolBar.add(btnTrackRecord);
        this.toolBar.add(btnDelTrackRecord);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.InvestorHouseListUIHandler";
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
     * output kDTreeUnit_valueChanged method
     */
    protected void kDTreeUnit_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("oldEnrollName"));
        sic.add(new SelectorItemInfo("orderLocation"));
        sic.add(new SelectorItemInfo("orderSourceClass"));
        sic.add(new SelectorItemInfo("assignBuilding"));
        sic.add(new SelectorItemInfo("assignRoom"));
        sic.add(new SelectorItemInfo("areaDescription"));
        sic.add(new SelectorItemInfo("priceDescription"));
        sic.add(new SelectorItemInfo("chargeComplexion"));
        sic.add(new SelectorItemInfo("OwnerShipState"));
        sic.add(new SelectorItemInfo("ownerShipNew"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("wuyeActuality"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("salesman.name"));
        sic.add(new SelectorItemInfo("buildingProperty.name"));
        sic.add(new SelectorItemInfo("booker.name"));
        sic.add(new SelectorItemInfo("informationSource.name"));
        sic.add(new SelectorItemInfo("habitationArea.name"));
        sic.add(new SelectorItemInfo("createTime"));
        return sic;
    }        
    	

    /**
     * output actionTrackRecord_actionPerformed method
     */
    public void actionTrackRecord_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelTrackRecord_actionPerformed method
     */
    public void actionDelTrackRecord_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionTrackRecord(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTrackRecord() {
    	return false;
    }
	public RequestContext prepareActionDelTrackRecord(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelTrackRecord() {
    	return false;
    }

    /**
     * output ActionTrackRecord class
     */     
    protected class ActionTrackRecord extends ItemAction {     
    
        public ActionTrackRecord()
        {
            this(null);
        }

        public ActionTrackRecord(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionTrackRecord.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTrackRecord.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTrackRecord.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInvestorHouseListUI.this, "ActionTrackRecord", "actionTrackRecord_actionPerformed", e);
        }
    }

    /**
     * output ActionDelTrackRecord class
     */     
    protected class ActionDelTrackRecord extends ItemAction {     
    
        public ActionDelTrackRecord()
        {
            this(null);
        }

        public ActionDelTrackRecord(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDelTrackRecord.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelTrackRecord.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelTrackRecord.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInvestorHouseListUI.this, "ActionDelTrackRecord", "actionDelTrackRecord_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "InvestorHouseListUI");
    }




}