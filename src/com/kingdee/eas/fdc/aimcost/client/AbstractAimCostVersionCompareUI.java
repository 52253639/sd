/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

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
public abstract class AbstractAimCostVersionCompareUI extends com.kingdee.eas.fdc.basedata.client.FDCRptBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAimCostVersionCompareUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conBaseVer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conCompareVer;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkAimCost;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkBuildArea;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkSellArea;
    protected com.kingdee.bos.ctrl.swing.KDButton btnOK;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel up;
    protected com.kingdee.bos.ctrl.swing.KDLabel down;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBaseVer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCompareVer;
    /**
     * output class constructor
     */
    public AbstractAimCostVersionCompareUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAimCostVersionCompareUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        //actionMoveTree
        String _tempStr = null;
        actionMoveTree.setEnabled(true);
        actionMoveTree.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionMoveTree.SHORT_DESCRIPTION");
        actionMoveTree.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionMoveTree.LONG_DESCRIPTION");
        actionMoveTree.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionMoveTree.NAME");
        actionMoveTree.putValue(ItemAction.NAME, _tempStr);
         this.actionMoveTree.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionMoveTree.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionMoveTree.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.conBaseVer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conCompareVer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkAimCost = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkBuildArea = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkSellArea = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.btnOK = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.up = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.down = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.prmtBaseVer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCompareVer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel1.setName("kDPanel1");
        this.conBaseVer.setName("conBaseVer");
        this.conCompareVer.setName("conCompareVer");
        this.chkAimCost.setName("chkAimCost");
        this.chkBuildArea.setName("chkBuildArea");
        this.chkSellArea.setName("chkSellArea");
        this.btnOK.setName("btnOK");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.up.setName("up");
        this.down.setName("down");
        this.prmtBaseVer.setName("prmtBaseVer");
        this.prmtCompareVer.setName("prmtCompareVer");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"acctNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"acctName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{acctNumber}</t:Cell><t:Cell>$Resource{acctName}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));		
        this.tblMain.setPreferredSize(new Dimension(728,493));
                this.tblMain.putBindContents("mainQuery",new String[] {"","",""});


        // kDPanel2
        // kDPanel1		
        this.kDPanel1.setPreferredSize(new Dimension(10,80));
        // conBaseVer		
        this.conBaseVer.setBoundLabelText(resHelper.getString("conBaseVer.boundLabelText"));		
        this.conBaseVer.setBoundLabelLength(100);		
        this.conBaseVer.setBoundLabelUnderline(true);
        // conCompareVer		
        this.conCompareVer.setBoundLabelText(resHelper.getString("conCompareVer.boundLabelText"));		
        this.conCompareVer.setBoundLabelLength(100);		
        this.conCompareVer.setBoundLabelUnderline(true);
        // chkAimCost		
        this.chkAimCost.setText(resHelper.getString("chkAimCost.text"));
        // chkBuildArea		
        this.chkBuildArea.setText(resHelper.getString("chkBuildArea.text"));
        // chkSellArea		
        this.chkSellArea.setText(resHelper.getString("chkSellArea.text"));
        // btnOK		
        this.btnOK.setText(resHelper.getString("btnOK.text"));
        this.btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnOK_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // up
        // down
        // prmtBaseVer		
        this.prmtBaseVer.setQueryInfo("com.kingdee.eas.fdc.aimcost.app.AIMCostF7Query");		
        this.prmtBaseVer.setRequired(true);
        // prmtCompareVer		
        this.prmtCompareVer.setQueryInfo("com.kingdee.eas.fdc.aimcost.app.AIMCostF7Query");		
        this.prmtCompareVer.setRequired(true);
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
        pnlMain.setBounds(new Rectangle(10, 10, 996, 583));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 996, 583, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(treeView, "left");
        pnlMain.add(kDPanel2, "right");
        //treeView
        treeView.setTree(treeMain);
        //kDPanel2
kDPanel2.setLayout(new BorderLayout(0, 0));        kDPanel2.add(tblMain, BorderLayout.CENTER);
        kDPanel2.add(kDPanel1, BorderLayout.NORTH);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 745, 582));        conBaseVer.setBounds(new Rectangle(13, 11, 270, 19));
        kDPanel1.add(conBaseVer, new KDLayout.Constraints(13, 11, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        conCompareVer.setBounds(new Rectangle(348, 11, 270, 19));
        kDPanel1.add(conCompareVer, new KDLayout.Constraints(348, 11, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        chkAimCost.setBounds(new Rectangle(27, 45, 84, 19));
        kDPanel1.add(chkAimCost, new KDLayout.Constraints(27, 45, 84, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        chkBuildArea.setBounds(new Rectangle(134, 45, 83, 19));
        kDPanel1.add(chkBuildArea, new KDLayout.Constraints(134, 45, 83, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        chkSellArea.setBounds(new Rectangle(241, 45, 85, 19));
        kDPanel1.add(chkSellArea, new KDLayout.Constraints(241, 45, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        btnOK.setBounds(new Rectangle(657, 11, 73, 21));
        kDPanel1.add(btnOK, new KDLayout.Constraints(657, 11, 73, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabel1.setBounds(new Rectangle(354, 45, 34, 19));
        kDPanel1.add(kDLabel1, new KDLayout.Constraints(354, 45, 34, 19, 0));
        kDLabel2.setBounds(new Rectangle(464, 45, 34, 19));
        kDPanel1.add(kDLabel2, new KDLayout.Constraints(464, 45, 34, 19, 0));
        up.setBounds(new Rectangle(394, 45, 64, 19));
        kDPanel1.add(up, new KDLayout.Constraints(394, 45, 64, 19, 0));
        down.setBounds(new Rectangle(504, 45, 64, 19));
        kDPanel1.add(down, new KDLayout.Constraints(504, 45, 64, 19, 0));
        //conBaseVer
        conBaseVer.setBoundEditor(prmtBaseVer);
        //conCompareVer
        conCompareVer.setBoundEditor(prmtCompareVer);

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
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemExportData);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(separatorFile1);
        menuFile.add(menuItemCloudShare);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(kdSeparatorFWFile1);
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
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnView);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.aimcost.app.AimCostVersionCompareUIHandler";
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
     * output btnOK_actionPerformed method
     */
    protected void btnOK_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
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
        return sic;
    }        
    	

    /**
     * output actionMoveTree_actionPerformed method
     */
    public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMoveTree_actionPerformed(e);
    }
	public RequestContext prepareActionMoveTree(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionMoveTree(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMoveTree() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.aimcost.client", "AimCostVersionCompareUI");
    }




}