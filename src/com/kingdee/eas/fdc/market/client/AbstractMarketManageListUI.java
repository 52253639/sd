/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

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
public abstract class AbstractMarketManageListUI extends com.kingdee.eas.framework.client.BillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketManageListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTree sellProjectTree;
    protected com.kingdee.bos.ctrl.swing.KDTree marketTypeTree;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnActionIntegral;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnclueCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnActionEvaluate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnlatencyCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnintentCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnoldCustomer;
    protected ActionTDPrint actionTDPrint = null;
    protected ActionTDPrintPreview actionTDPrintPreview = null;
    protected ActionEvaluate actionEvaluate = null;
    protected ActionIntegral actionIntegral = null;
    protected ActionClueCustomer actionClueCustomer = null;
    protected ActionLatencyCustomer actionLatencyCustomer = null;
    protected ActionIntentCustomer actionIntentCustomer = null;
    protected ActionOldCustomer actionOldCustomer = null;
    public final static String STATUS_VIEW = "VIEW";
    /**
     * output class constructor
     */
    public AbstractMarketManageListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketManageListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.market.app", "MarketManageQuery");
        //actionRemove
        String _tempStr = null;
        actionRemove.setEnabled(true);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl d"));
        _tempStr = resHelper.getString("ActionRemove.SHORT_DESCRIPTION");
        actionRemove.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.LONG_DESCRIPTION");
        actionRemove.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.NAME");
        actionRemove.putValue(ItemAction.NAME, _tempStr);
        this.actionRemove.setBindWorkFlow(true);
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionTDPrint
        this.actionTDPrint = new ActionTDPrint(this);
        getActionManager().registerAction("actionTDPrint", actionTDPrint);
         this.actionTDPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTDPrintPreview
        this.actionTDPrintPreview = new ActionTDPrintPreview(this);
        getActionManager().registerAction("actionTDPrintPreview", actionTDPrintPreview);
         this.actionTDPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEvaluate
        this.actionEvaluate = new ActionEvaluate(this);
        getActionManager().registerAction("actionEvaluate", actionEvaluate);
         this.actionEvaluate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionIntegral
        this.actionIntegral = new ActionIntegral(this);
        getActionManager().registerAction("actionIntegral", actionIntegral);
         this.actionIntegral.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClueCustomer
        this.actionClueCustomer = new ActionClueCustomer(this);
        getActionManager().registerAction("actionClueCustomer", actionClueCustomer);
         this.actionClueCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionLatencyCustomer
        this.actionLatencyCustomer = new ActionLatencyCustomer(this);
        getActionManager().registerAction("actionLatencyCustomer", actionLatencyCustomer);
         this.actionLatencyCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionIntentCustomer
        this.actionIntentCustomer = new ActionIntentCustomer(this);
        getActionManager().registerAction("actionIntentCustomer", actionIntentCustomer);
         this.actionIntentCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionOldCustomer
        this.actionOldCustomer = new ActionOldCustomer(this);
        getActionManager().registerAction("actionOldCustomer", actionOldCustomer);
         this.actionOldCustomer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.sellProjectTree = new com.kingdee.bos.ctrl.swing.KDTree();
        this.marketTypeTree = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnActionIntegral = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnclueCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnActionEvaluate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnlatencyCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnintentCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnoldCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.kDContainer3.setName("kDContainer3");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.sellProjectTree.setName("sellProjectTree");
        this.marketTypeTree.setName("marketTypeTree");
        this.btnActionIntegral.setName("btnActionIntegral");
        this.btnclueCustomer.setName("btnclueCustomer");
        this.btnActionEvaluate.setName("btnActionEvaluate");
        this.btnlatencyCustomer.setName("btnlatencyCustomer");
        this.btnintentCustomer.setName("btnintentCustomer");
        this.btnoldCustomer.setName("btnoldCustomer");
        // CoreUI		
        this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
                this.tblMain.putBindContents("mainQuery",new String[] {"number","name","movementPlan","orgName","movemntTheme","markettype.name","planTotalMoney","factTotalMoney","schemeType","belongSystem","sellproject","beginDate","endDate","description","id","company.number","company.name","lastUpdateUser.lastUpdateUser","creator.creator","auditor.auditor","handler.name","lastUpdateTime","createTime","sourceFunction","sourceBillId","hasEffected","bizDate","sellProject.id","marketType.id"});


        this.tblMain.checkParsed();
        this.tblMain.getGroupManager().setGroup(true);
        // kDSplitPane1		
        this.kDSplitPane1.setOrientation(0);
        // kDContainer1
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));		
        this.kDContainer2.setAutoscrolls(true);		
        this.kDContainer2.setEnableActive(false);
        // kDContainer3		
        this.kDContainer3.setTitle(resHelper.getString("kDContainer3.title"));		
        this.kDContainer3.setAutoscrolls(true);		
        this.kDContainer3.setEnableActive(false);
        // kDScrollPane1
        // kDScrollPane2
        // sellProjectTree
        this.sellProjectTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    sellProjectTree_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // marketTypeTree
        this.marketTypeTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    marketTypeTree_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnActionIntegral
        this.btnActionIntegral.setAction((IItemAction)ActionProxyFactory.getProxy(actionIntegral, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnActionIntegral.setText(resHelper.getString("btnActionIntegral.text"));
        // btnclueCustomer
        this.btnclueCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionClueCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnclueCustomer.setText(resHelper.getString("btnclueCustomer.text"));		
        this.btnclueCustomer.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_userview"));
        // btnActionEvaluate
        this.btnActionEvaluate.setAction((IItemAction)ActionProxyFactory.getProxy(actionEvaluate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnActionEvaluate.setText(resHelper.getString("btnActionEvaluate.text"));
        // btnlatencyCustomer
        this.btnlatencyCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionLatencyCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnlatencyCustomer.setText(resHelper.getString("btnlatencyCustomer.text"));		
        this.btnlatencyCustomer.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_userview"));
        // btnintentCustomer
        this.btnintentCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionIntentCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnintentCustomer.setText(resHelper.getString("btnintentCustomer.text"));		
        this.btnintentCustomer.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_userview"));
        // btnoldCustomer
        this.btnoldCustomer.setAction((IItemAction)ActionProxyFactory.getProxy(actionOldCustomer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnoldCustomer.setText(resHelper.getString("btnoldCustomer.text"));		
        this.btnoldCustomer.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_userview"));
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
        kDSplitPane1.setBounds(new Rectangle(10, 10, 245, 608));
        this.add(kDSplitPane1, new KDLayout.Constraints(10, 10, 245, 608, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        kDContainer1.setBounds(new Rectangle(264, 13, 740, 603));
        this.add(kDContainer1, new KDLayout.Constraints(264, 13, 740, 603, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(kDContainer2, "top");
        kDSplitPane1.add(kDContainer3, "bottom");
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(sellProjectTree, null);
        //kDContainer3
kDContainer3.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer3.getContentPane().add(kDScrollPane2, BorderLayout.CENTER);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(marketTypeTree, null);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(tblMain, BorderLayout.CENTER);

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
        this.menuBar.add(menuWorkFlow);
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
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuWorkFlow
        menuWorkFlow.add(menuItemViewDoProccess);
        menuWorkFlow.add(menuItemMultiapprove);
        menuWorkFlow.add(menuItemWorkFlowG);
        menuWorkFlow.add(menuItemWorkFlowList);
        menuWorkFlow.add(separatorWF1);
        menuWorkFlow.add(menuItemNextPerson);
        menuWorkFlow.add(menuItemAuditResult);
        menuWorkFlow.add(kDSeparator7);
        menuWorkFlow.add(menuItemSendSmsMessage);
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
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnActionIntegral);
        this.toolBar.add(btnclueCustomer);
        this.toolBar.add(btnlatencyCustomer);
        this.toolBar.add(btnintentCustomer);
        this.toolBar.add(btnoldCustomer);
        this.toolBar.add(btnActionEvaluate);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.MarketManageListUIHandler";
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
	protected void Remove() throws Exception {
    	IObjectValue editData = getBizInterface().getValue(new com.kingdee.bos.dao.ormapping.ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
    	super.Remove();
    	recycleNumberByOrg(editData,"",editData.getString("number"));
    }
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Sale");
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
     * output sellProjectTree_valueChanged method
     */
    protected void sellProjectTree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output marketTypeTree_valueChanged method
     */
    protected void marketTypeTree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

			public SelectorItemCollection getBOTPSelectors() {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("*"));
			sic.add(new SelectorItemInfo("creator.*"));
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
			sic.add(new SelectorItemInfo("CU.*"));
			sic.add(new SelectorItemInfo("handler.*"));
			sic.add(new SelectorItemInfo("auditor.*"));
			sic.add(new SelectorItemInfo("company.*"));
			sic.add(new SelectorItemInfo("entrys.*"));
			sic.add(new SelectorItemInfo("entrys.costNumber.*"));
			sic.add(new SelectorItemInfo("marketPlan.*"));
			sic.add(new SelectorItemInfo("sellProject.*"));
			sic.add(new SelectorItemInfo("markettype.*"));
			sic.add(new SelectorItemInfo("Charge.*"));
			sic.add(new SelectorItemInfo("Charge.customer.*"));
			sic.add(new SelectorItemInfo("Customer.*"));
			sic.add(new SelectorItemInfo("Customer.fdcCustomer.*"));
			sic.add(new SelectorItemInfo("Effect.*"));
			sic.add(new SelectorItemInfo("Media.*"));
			sic.add(new SelectorItemInfo("Media.mediaType.*"));
			return sic;
		}

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("hasEffected"));
        sic.add(new SelectorItemInfo("sourceBillId"));
        sic.add(new SelectorItemInfo("sourceFunction"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("creator.number"));
        sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        sic.add(new SelectorItemInfo("entrys.seq"));
        sic.add(new SelectorItemInfo("handler.number"));
        sic.add(new SelectorItemInfo("handler.name"));
        sic.add(new SelectorItemInfo("auditor.number"));
        sic.add(new SelectorItemInfo("auditor.auditor"));
        sic.add(new SelectorItemInfo("creator.creator"));
        sic.add(new SelectorItemInfo("lastUpdateUser.lastUpdateUser"));
        sic.add(new SelectorItemInfo("company.name"));
        sic.add(new SelectorItemInfo("company.number"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("entrys.id"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("orgName"));
        sic.add(new SelectorItemInfo("schemeType"));
        sic.add(new SelectorItemInfo("beginDate"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("belongSystem"));
        sic.add(new SelectorItemInfo("movemntTheme"));
        sic.add(new SelectorItemInfo("markettype.name"));
        sic.add(new SelectorItemInfo("planTotalMoney"));
        sic.add(new SelectorItemInfo("factTotalMoney"));
        sic.add(new SelectorItemInfo("entrys.number.number"));
        sic.add(new SelectorItemInfo("entrys.name0"));
        sic.add(new SelectorItemInfo("entrys.butgetAmount"));
        sic.add(new SelectorItemInfo("entrys.amount"));
        sic.add(new SelectorItemInfo("entrys.remark"));
        sic.add(new SelectorItemInfo("sellproject"));
        sic.add(new SelectorItemInfo("movementPlan"));
        sic.add(new SelectorItemInfo("sellProject.id"));
        sic.add(new SelectorItemInfo("marketType.id"));
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
     * output actionTDPrint_actionPerformed method
     */
    public void actionTDPrint_actionPerformed(ActionEvent e) throws Exception
    {
        checkSelected();        
    	ArrayList idList =super.getSelectedIdValues();
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionTDPrintPreview_actionPerformed method
     */
    public void actionTDPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        checkSelected();
    	ArrayList idList =super.getSelectedIdValues();
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionEvaluate_actionPerformed method
     */
    public void actionEvaluate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionIntegral_actionPerformed method
     */
    public void actionIntegral_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClueCustomer_actionPerformed method
     */
    public void actionClueCustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionLatencyCustomer_actionPerformed method
     */
    public void actionLatencyCustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionIntentCustomer_actionPerformed method
     */
    public void actionIntentCustomer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionOldCustomer_actionPerformed method
     */
    public void actionOldCustomer_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionTDPrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTDPrint() {
    	return false;
    }
	public RequestContext prepareActionTDPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTDPrintPreview() {
    	return false;
    }
	public RequestContext prepareActionEvaluate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEvaluate() {
    	return false;
    }
	public RequestContext prepareActionIntegral(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionIntegral() {
    	return false;
    }
	public RequestContext prepareActionClueCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClueCustomer() {
    	return false;
    }
	public RequestContext prepareActionLatencyCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionLatencyCustomer() {
    	return false;
    }
	public RequestContext prepareActionIntentCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionIntentCustomer() {
    	return false;
    }
	public RequestContext prepareActionOldCustomer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionOldCustomer() {
    	return false;
    }

    /**
     * output ActionTDPrint class
     */
    protected class ActionTDPrint extends ItemAction
    {
        public ActionTDPrint()
        {
            this(null);
        }

        public ActionTDPrint(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionTDPrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTDPrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTDPrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketManageListUI.this, "ActionTDPrint", "actionTDPrint_actionPerformed", e);
        }
    }

    /**
     * output ActionTDPrintPreview class
     */
    protected class ActionTDPrintPreview extends ItemAction
    {
        public ActionTDPrintPreview()
        {
            this(null);
        }

        public ActionTDPrintPreview(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionTDPrintPreview.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTDPrintPreview.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTDPrintPreview.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketManageListUI.this, "ActionTDPrintPreview", "actionTDPrintPreview_actionPerformed", e);
        }
    }

    /**
     * output ActionEvaluate class
     */
    protected class ActionEvaluate extends ItemAction
    {
        public ActionEvaluate()
        {
            this(null);
        }

        public ActionEvaluate(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionEvaluate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEvaluate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEvaluate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketManageListUI.this, "ActionEvaluate", "actionEvaluate_actionPerformed", e);
        }
    }

    /**
     * output ActionIntegral class
     */
    protected class ActionIntegral extends ItemAction
    {
        public ActionIntegral()
        {
            this(null);
        }

        public ActionIntegral(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionIntegral.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionIntegral.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionIntegral.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketManageListUI.this, "ActionIntegral", "actionIntegral_actionPerformed", e);
        }
    }

    /**
     * output ActionClueCustomer class
     */
    protected class ActionClueCustomer extends ItemAction
    {
        public ActionClueCustomer()
        {
            this(null);
        }

        public ActionClueCustomer(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionClueCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClueCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClueCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketManageListUI.this, "ActionClueCustomer", "actionClueCustomer_actionPerformed", e);
        }
    }

    /**
     * output ActionLatencyCustomer class
     */
    protected class ActionLatencyCustomer extends ItemAction
    {
        public ActionLatencyCustomer()
        {
            this(null);
        }

        public ActionLatencyCustomer(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionLatencyCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLatencyCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLatencyCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketManageListUI.this, "ActionLatencyCustomer", "actionLatencyCustomer_actionPerformed", e);
        }
    }

    /**
     * output ActionIntentCustomer class
     */
    protected class ActionIntentCustomer extends ItemAction
    {
        public ActionIntentCustomer()
        {
            this(null);
        }

        public ActionIntentCustomer(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionIntentCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionIntentCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionIntentCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketManageListUI.this, "ActionIntentCustomer", "actionIntentCustomer_actionPerformed", e);
        }
    }

    /**
     * output ActionOldCustomer class
     */
    protected class ActionOldCustomer extends ItemAction
    {
        public ActionOldCustomer()
        {
            this(null);
        }

        public ActionOldCustomer(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionOldCustomer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOldCustomer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionOldCustomer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketManageListUI.this, "ActionOldCustomer", "actionOldCustomer_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "MarketManageListUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.market.client.MarketManageEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.MarketManageFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.MarketManageInfo objectValue = new com.kingdee.eas.fdc.market.MarketManageInfo();		
        return objectValue;
    }

    /**
     * output getMergeColumnKeys method
     */
    public String[] getMergeColumnKeys()
    {
        return new String[] {"number","name","movementPlan","orgName","movemntTheme","markettype.name","planTotalMoney","factTotalMoney","schemeType","belongSystem","sellProject","beginDate","endDate","description","id","company.number","company.name","handler.name","lastUpdateTime","createTime","sourceFunction","sourceBillId","hasEffected","bizDate","sellProject.id","marketType.id"};
    }



	protected String getTDFileName() {
    	return "/bim/fdc/market/MarketManage";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.market.app.MarketManageQuery");
	}

}