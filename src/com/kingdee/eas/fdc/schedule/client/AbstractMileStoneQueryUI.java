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
public abstract class AbstractMileStoneQueryUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMileStoneQueryUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel leftPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel rightPanel;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlSubway;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDPanel logoPanel;
    /**
     * output class constructor
     */
    public AbstractMileStoneQueryUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMileStoneQueryUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.schedule.app", "FDCScheduleTaskStatusQuery");
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.leftPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.rightPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.kDSplitPane2 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlSubway = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.logoPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.leftPanel.setName("leftPanel");
        this.rightPanel.setName("rightPanel");
        this.kDContainer1.setName("kDContainer1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.treeMain.setName("treeMain");
        this.kDSplitPane2.setName("kDSplitPane2");
        this.kDContainer3.setName("kDContainer3");
        this.kDPanel1.setName("kDPanel1");
        this.pnlSubway.setName("pnlSubway");
        this.kDContainer2.setName("kDContainer2");
        this.logoPanel.setName("logoPanel");
        // CoreUI		
        this.menuTool.setVisible(false);
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"status\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"name\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"complete\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"checkDate\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"start\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"end\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"actualStartDate\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"actualEndDate\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"delayDay\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"hiddenStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"natureTimes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"srcid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"adminDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"adminPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"longnumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{status}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{complete}</t:Cell><t:Cell>$Resource{checkDate}</t:Cell><t:Cell>$Resource{start}</t:Cell><t:Cell>$Resource{end}</t:Cell><t:Cell>$Resource{actualStartDate}</t:Cell><t:Cell>$Resource{actualEndDate}</t:Cell><t:Cell>$Resource{delayDay}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{hiddenStatus}</t:Cell><t:Cell>$Resource{natureTimes}</t:Cell><t:Cell>$Resource{srcid}</t:Cell><t:Cell>$Resource{adminDept}</t:Cell><t:Cell>$Resource{adminPerson}</t:Cell><t:Cell>$Resource{longnumber}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"","name","complete","checkDate","start","end","actualStartDate","actualEndDate","delayDay","id","hiddenStatus","natureTimes","srcID","adminDept.name","adminPerson.name","longNumber"});

		
        this.btnAddNew.setVisible(false);		
        this.btnView.setVisible(false);		
        this.btnEdit.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnRefresh.setVisible(false);		
        this.btnLocate.setVisible(false);		
        this.btnQuery.setVisible(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemEdit.setVisible(false);		
        this.menuItemRemove.setVisible(false);		
        this.menuView.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(250);
        // leftPanel
        // rightPanel
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDScrollPane1
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
        // kDSplitPane2		
        this.kDSplitPane2.setOrientation(0);		
        this.kDSplitPane2.setDividerLocation(250);
        // kDContainer3		
        this.kDContainer3.setTitle(resHelper.getString("kDContainer3.title"));
        // kDPanel1
        // pnlSubway
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // logoPanel
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
        kDSplitPane1.setBounds(new Rectangle(4, 1, 1003, 623));
        this.add(kDSplitPane1, new KDLayout.Constraints(4, 1, 1003, 623, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(leftPanel, "left");
        kDSplitPane1.add(rightPanel, "right");
        //leftPanel
        leftPanel.setLayout(new KDLayout());
        leftPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 249, 622));        kDContainer1.setBounds(new Rectangle(6, 4, 240, 609));
        leftPanel.add(kDContainer1, new KDLayout.Constraints(6, 4, 240, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(6, 4, 240, 609));        kDScrollPane1.setBounds(new Rectangle(0, 1, 241, 599));
        kDContainer1.getContentPane().add(kDScrollPane1, new KDLayout.Constraints(0, 1, 241, 599, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDScrollPane1
        kDScrollPane1.getViewport().add(treeMain, null);
        //rightPanel
        rightPanel.setLayout(new KDLayout());
        rightPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 742, 622));        kDSplitPane2.setBounds(new Rectangle(2, 3, 739, 617));
        rightPanel.add(kDSplitPane2, new KDLayout.Constraints(2, 3, 739, 617, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane2
        kDSplitPane2.add(kDContainer3, "top");
        kDSplitPane2.add(kDPanel1, "bottom");
        //kDContainer3
        kDContainer3.getContentPane().setLayout(new KDLayout());
        kDContainer3.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 738, 249));        pnlSubway.setBounds(new Rectangle(1, 1, 853, 229));
        kDContainer3.getContentPane().add(pnlSubway, new KDLayout.Constraints(1, 1, 853, 229, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
pnlSubway.setLayout(new BorderLayout(0, 0));        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 738, 356));        kDContainer2.setBounds(new Rectangle(-2, 1, 739, 357));
        kDPanel1.add(kDContainer2, new KDLayout.Constraints(-2, 1, 739, 357, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainer2
        kDContainer2.getContentPane().setLayout(new KDLayout());
        kDContainer2.getContentPane().putClientProperty("OriginalBounds", new Rectangle(-2, 1, 739, 357));        tblMain.setBounds(new Rectangle(1, 1, 735, 306));
        kDContainer2.getContentPane().add(tblMain, new KDLayout.Constraints(1, 1, 735, 306, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        logoPanel.setBounds(new Rectangle(3, 310, 737, 23));
        kDContainer2.getContentPane().add(logoPanel, new KDLayout.Constraints(3, 310, 737, 23, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        logoPanel.setLayout(new KDLayout());
        logoPanel.putClientProperty("OriginalBounds", new Rectangle(3, 310, 737, 23));
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
	    return "com.kingdee.eas.fdc.schedule.app.MileStoneQueryUIHandler";
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
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("actualEndDate"));
        sic.add(new SelectorItemInfo("complete"));
        sic.add(new SelectorItemInfo("end"));
        sic.add(new SelectorItemInfo("meeting"));
        sic.add(new SelectorItemInfo("checkDate"));
        sic.add(new SelectorItemInfo("actualStartDate"));
        sic.add(new SelectorItemInfo("delayDay"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("start"));
        sic.add(new SelectorItemInfo("hiddenStatus"));
        sic.add(new SelectorItemInfo("natureTimes"));
        sic.add(new SelectorItemInfo("srcID"));
        sic.add(new SelectorItemInfo("adminDept.name"));
        sic.add(new SelectorItemInfo("adminPerson.name"));
        sic.add(new SelectorItemInfo("longNumber"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "MileStoneQueryUI");
    }




}