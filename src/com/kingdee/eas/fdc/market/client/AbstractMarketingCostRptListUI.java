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
public abstract class AbstractMarketingCostRptListUI extends com.kingdee.eas.framework.report.client.CommRptBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketingCostRptListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel MarketingCostRptListUI;
    protected com.kingdee.bos.ctrl.swing.KDToolBar MarketingCostRptListUI_toolbar;
    protected com.kingdee.bos.ctrl.swing.KDMenuBar MarketingCostRptListUI_menubar;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane mainSplitPane;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable MarketFeeTable;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane rightSplitPane;
    protected com.kingdee.bos.ctrl.swing.KDContainer topContaniner;
    protected com.kingdee.bos.ctrl.swing.KDContainer downContaniner;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDTree treeSellproject;
    /**
     * output class constructor
     */
    public AbstractMarketingCostRptListUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractMarketingCostRptListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.MarketingCostRptListUI = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.MarketingCostRptListUI_toolbar = new com.kingdee.bos.ctrl.swing.KDToolBar();
        this.MarketingCostRptListUI_menubar = new com.kingdee.bos.ctrl.swing.KDMenuBar();
        this.mainSplitPane = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.MarketFeeTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.rightSplitPane = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.topContaniner = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.downContaniner = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.treeSellproject = new com.kingdee.bos.ctrl.swing.KDTree();
        this.MarketingCostRptListUI.setName("MarketingCostRptListUI");
        this.MarketingCostRptListUI_toolbar.setName("MarketingCostRptListUI_toolbar");
        this.MarketingCostRptListUI_menubar.setName("MarketingCostRptListUI_menubar");
        this.mainSplitPane.setName("mainSplitPane");
        this.MarketFeeTable.setName("MarketFeeTable");
        this.rightSplitPane.setName("rightSplitPane");
        this.topContaniner.setName("topContaniner");
        this.downContaniner.setName("downContaniner");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.treeMain.setName("treeMain");
        this.treeSellproject.setName("treeSellproject");
        // CoreUI
        // MarketingCostRptListUI
        // MarketingCostRptListUI_toolbar
        // MarketingCostRptListUI_menubar
        // mainSplitPane		
        this.mainSplitPane.setDividerLocation(200);
        // MarketFeeTable		
        this.MarketFeeTable.setFormatXml(resHelper.getString("MarketFeeTable.formatXml"));

        

        // rightSplitPane		
        this.rightSplitPane.setOrientation(0);
        // topContaniner		
        this.topContaniner.setTitle(resHelper.getString("topContaniner.title"));
        // downContaniner		
        this.downContaniner.setTitle(resHelper.getString("downContaniner.title"));
        // kDScrollPane1
        // kDScrollPane2
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
        // treeSellproject
        this.treeSellproject.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeSellproject_valueChanged(e);
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

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1016, 600));
        mainSplitPane.setBounds(new Rectangle(2, 8, 995, 580));
        this.add(mainSplitPane, new KDLayout.Constraints(2, 8, 995, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //mainSplitPane
        mainSplitPane.add(MarketFeeTable, "right");
        mainSplitPane.add(rightSplitPane, "left");
        //rightSplitPane
        rightSplitPane.add(topContaniner, "top");
        rightSplitPane.add(downContaniner, "bottom");
        //topContaniner
topContaniner.getContentPane().setLayout(new BorderLayout(0, 0));        topContaniner.getContentPane().add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(treeSellproject, null);
        //downContaniner
downContaniner.getContentPane().setLayout(new BorderLayout(0, 0));        downContaniner.getContentPane().add(kDScrollPane2, BorderLayout.CENTER);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(treeMain, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuHelp);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuView);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemAbout);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuView
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemChart);
        menuView.add(kDSeparator2);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnChart);
        this.toolBar.add(separator1);
        this.toolBar.add(separator2);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.MarketingCostRptListUIHandler";
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
     * output treeSellproject_valueChanged method
     */
    protected void treeSellproject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "MarketingCostRptListUI");
    }




}