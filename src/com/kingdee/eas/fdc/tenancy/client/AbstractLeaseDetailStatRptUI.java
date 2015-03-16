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
public abstract class AbstractLeaseDetailStatRptUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractLeaseDetailStatRptUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewPurchase;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewPurchase;
    protected ActionViewPurchase actionViewPurchase = null;
    /**
     * output class constructor
     */
    public AbstractLeaseDetailStatRptUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractLeaseDetailStatRptUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "SellStatRoomRptQuery");
        //actionViewPurchase
        this.actionViewPurchase = new ActionViewPurchase(this);
        getActionManager().registerAction("actionViewPurchase", actionViewPurchase);
         this.actionViewPurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnViewPurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewPurchase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnViewPurchase.setName("btnViewPurchase");
        this.menuItemViewPurchase.setName("menuItemViewPurchase");
        // CoreUI		
        this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","sellProject.id","building.id","unit.name","sellProject.name","building.name","number","roomModel.name","buildingProperty.name","buildingArea","roomArea","actualBuildingArea","actualRoomArea","baseBuildingPrice","baseRoomPrice","basePrice","isBasePriceAudited","buildPrice","isCalByRoomArea","roomPrice","standardTotalAmount","toPurchaseDate","lastPurchase.customerNames","sellState","dealPrice","dealTotalAmount","areaCompensateAmount","sellAmount","salesman.name","roomJoinState","roomBookState","roomLoanState","roomCompensateState","apportionArea","balconyArea","guardenArea","carbarnArea","useArea","flatArea","unit","floor","serialNumber","direction.name","sight.name","sellOrder.orderDate","toSignDate","payType.name"});

		
        this.menuEdit.setVisible(false);
        // btnViewPurchase
        this.btnViewPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewPurchase.setText(resHelper.getString("btnViewPurchase.text"));		
        this.btnViewPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scatterpurview"));
        // menuItemViewPurchase
        this.menuItemViewPurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewPurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewPurchase.setText(resHelper.getString("menuItemViewPurchase.text"));		
        this.menuItemViewPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scatterpurview"));
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        tblMain.setBounds(new Rectangle(10, 10, 980, 580));
        this.add(tblMain, new KDLayout.Constraints(10, 10, 980, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

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
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemViewPurchase);
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
        this.toolBar.add(btnViewPurchase);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.LeaseDetailStatRptUIHandler";
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
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("building.name"));
        sic.add(new SelectorItemInfo("unit"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("floor"));
        sic.add(new SelectorItemInfo("serialNumber"));
        sic.add(new SelectorItemInfo("roomModel.name"));
        sic.add(new SelectorItemInfo("buildingProperty.name"));
        sic.add(new SelectorItemInfo("buildingArea"));
        sic.add(new SelectorItemInfo("roomArea"));
        sic.add(new SelectorItemInfo("apportionArea"));
        sic.add(new SelectorItemInfo("balconyArea"));
        sic.add(new SelectorItemInfo("guardenArea"));
        sic.add(new SelectorItemInfo("carbarnArea"));
        sic.add(new SelectorItemInfo("useArea"));
        sic.add(new SelectorItemInfo("flatArea"));
        sic.add(new SelectorItemInfo("actualBuildingArea"));
        sic.add(new SelectorItemInfo("actualRoomArea"));
        sic.add(new SelectorItemInfo("sellState"));
        sic.add(new SelectorItemInfo("buildPrice"));
        sic.add(new SelectorItemInfo("isCalByRoomArea"));
        sic.add(new SelectorItemInfo("roomPrice"));
        sic.add(new SelectorItemInfo("standardTotalAmount"));
        sic.add(new SelectorItemInfo("roomJoinState"));
        sic.add(new SelectorItemInfo("roomBookState"));
        sic.add(new SelectorItemInfo("roomLoanState"));
        sic.add(new SelectorItemInfo("roomCompensateState"));
        sic.add(new SelectorItemInfo("sellProject.id"));
        sic.add(new SelectorItemInfo("building.id"));
        sic.add(new SelectorItemInfo("dealPrice"));
        sic.add(new SelectorItemInfo("dealTotalAmount"));
        sic.add(new SelectorItemInfo("direction.name"));
        sic.add(new SelectorItemInfo("sight.name"));
        sic.add(new SelectorItemInfo("lastPurchase.customerNames"));
        sic.add(new SelectorItemInfo("payType.name"));
        sic.add(new SelectorItemInfo("salesman.name"));
        sic.add(new SelectorItemInfo("sellOrder.orderDate"));
        sic.add(new SelectorItemInfo("baseBuildingPrice"));
        sic.add(new SelectorItemInfo("baseRoomPrice"));
        sic.add(new SelectorItemInfo("isBasePriceAudited"));
        sic.add(new SelectorItemInfo("toPurchaseDate"));
        sic.add(new SelectorItemInfo("toSignDate"));
        sic.add(new SelectorItemInfo("sellProject.name"));
        sic.add(new SelectorItemInfo("unit.name"));
        sic.add(new SelectorItemInfo("sellAmount"));
        sic.add(new SelectorItemInfo("areaCompensateAmount"));
        sic.add(new SelectorItemInfo("basePrice"));
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
    protected class ActionViewPurchase extends ItemAction
    {
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
            innerActionPerformed("eas", AbstractLeaseDetailStatRptUI.this, "ActionViewPurchase", "actionViewPurchase_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "LeaseDetailStatRptUI");
    }




}