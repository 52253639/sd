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
public abstract class AbstractWarningTestUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractWarningTestUI.class);
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnShowEntityPropertys;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEntityClassName;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnShowMetaInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lableBosTypeName;
    protected com.kingdee.bos.ctrl.swing.KDButton btnExportEntityTable;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEntityClassName;
    protected com.kingdee.bos.ctrl.swing.KDTextField kDTextField1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblUIList;
    protected com.kingdee.bos.ctrl.swing.KDButton btnUpdatePurchase;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    /**
     * output class constructor
     */
    public AbstractWarningTestUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractWarningTestUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.btnShowEntityPropertys = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contEntityClassName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnShowMetaInfo = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.lableBosTypeName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnExportEntityTable = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtEntityClassName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDTextField1 = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tblUIList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnUpdatePurchase = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.kDContainer3.setName("kDContainer3");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.btnShowEntityPropertys.setName("btnShowEntityPropertys");
        this.contEntityClassName.setName("contEntityClassName");
        this.btnShowMetaInfo.setName("btnShowMetaInfo");
        this.lableBosTypeName.setName("lableBosTypeName");
        this.btnExportEntityTable.setName("btnExportEntityTable");
        this.kDLabel1.setName("kDLabel1");
        this.txtEntityClassName.setName("txtEntityClassName");
        this.kDTextField1.setName("kDTextField1");
        this.tblUIList.setName("tblUIList");
        this.btnUpdatePurchase.setName("btnUpdatePurchase");
        this.kDLabel2.setName("kDLabel2");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"message\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{message}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"",""});


        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // kDContainer3		
        this.kDContainer3.setTitle(resHelper.getString("kDContainer3.title"));
        // kDScrollPane1
        // btnShowEntityPropertys		
        this.btnShowEntityPropertys.setText(resHelper.getString("btnShowEntityPropertys.text"));		
        this.btnShowEntityPropertys.setToolTipText(resHelper.getString("btnShowEntityPropertys.toolTipText"));
        this.btnShowEntityPropertys.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnShowEntityPropertys_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contEntityClassName		
        this.contEntityClassName.setBoundLabelText(resHelper.getString("contEntityClassName.boundLabelText"));		
        this.contEntityClassName.setBoundLabelLength(50);		
        this.contEntityClassName.setBoundLabelUnderline(true);
        // btnShowMetaInfo		
        this.btnShowMetaInfo.setText(resHelper.getString("btnShowMetaInfo.text"));		
        this.btnShowMetaInfo.setToolTipText(resHelper.getString("btnShowMetaInfo.toolTipText"));
        this.btnShowMetaInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnShowMetaInfo_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // lableBosTypeName		
        this.lableBosTypeName.setBoundLabelText(resHelper.getString("lableBosTypeName.boundLabelText"));		
        this.lableBosTypeName.setBoundLabelLength(50);		
        this.lableBosTypeName.setBoundLabelUnderline(true);
        // btnExportEntityTable		
        this.btnExportEntityTable.setText(resHelper.getString("btnExportEntityTable.text"));		
        this.btnExportEntityTable.setToolTipText(resHelper.getString("btnExportEntityTable.toolTipText"));
        this.btnExportEntityTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnExportEntityTable_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));		
        this.kDLabel1.setAutoscrolls(true);
        // txtEntityClassName		
        this.txtEntityClassName.setMaxLength(200);
        // kDTextField1		
        this.kDTextField1.setMaxLength(8);
        // tblUIList
		String tblUIListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"ui\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{ui}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name_Row2}</t:Cell><t:Cell>$Resource{ui_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"0\" t:right=\"1\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblUIList.setFormatXml(resHelper.translateString("tblUIList",tblUIListStrXML));
        this.tblUIList.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblUIList_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnUpdatePurchase		
        this.btnUpdatePurchase.setText(resHelper.getString("btnUpdatePurchase.text"));		
        this.btnUpdatePurchase.setToolTipText(resHelper.getString("btnUpdatePurchase.toolTipText"));		
        this.btnUpdatePurchase.setHorizontalTextPosition(0);		
        this.btnUpdatePurchase.setHorizontalAlignment(2);
        this.btnUpdatePurchase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnUpdatePurchase_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));		
        this.kDLabel2.setAutoscrolls(true);
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
        kDContainer1.setBounds(new Rectangle(19, 17, 283, 593));
        this.add(kDContainer1, new KDLayout.Constraints(19, 17, 283, 593, 0));
        kDContainer2.setBounds(new Rectangle(349, 17, 283, 593));
        this.add(kDContainer2, new KDLayout.Constraints(349, 17, 283, 593, 0));
        kDContainer3.setBounds(new Rectangle(693, 19, 283, 593));
        this.add(kDContainer3, new KDLayout.Constraints(693, 19, 283, 593, 0));
        //kDContainer1
        kDContainer1.getContentPane().setLayout(null);        kDScrollPane1.setBounds(new Rectangle(5, 65, 264, 231));
        kDContainer1.getContentPane().add(kDScrollPane1, null);
        btnShowEntityPropertys.setBounds(new Rectangle(147, 10, 116, 19));
        kDContainer1.getContentPane().add(btnShowEntityPropertys, null);
        contEntityClassName.setBounds(new Rectangle(3, 11, 127, 19));
        kDContainer1.getContentPane().add(contEntityClassName, null);
        btnShowMetaInfo.setBounds(new Rectangle(145, 306, 119, 19));
        kDContainer1.getContentPane().add(btnShowMetaInfo, null);
        lableBosTypeName.setBounds(new Rectangle(4, 307, 128, 19));
        kDContainer1.getContentPane().add(lableBosTypeName, null);
        btnExportEntityTable.setBounds(new Rectangle(60, 335, 146, 24));
        kDContainer1.getContentPane().add(btnExportEntityTable, null);
        kDLabel1.setBounds(new Rectangle(2, 36, 261, 24));
        kDContainer1.getContentPane().add(kDLabel1, null);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(tblMain, null);
        //contEntityClassName
        contEntityClassName.setBoundEditor(txtEntityClassName);
        //lableBosTypeName
        lableBosTypeName.setBoundEditor(kDTextField1);
        //kDContainer2
        kDContainer2.getContentPane().setLayout(null);        tblUIList.setBounds(new Rectangle(4, 12, 273, 182));
        kDContainer2.getContentPane().add(tblUIList, null);
        //kDContainer3
        kDContainer3.getContentPane().setLayout(null);        btnUpdatePurchase.setBounds(new Rectangle(43, 54, 144, 21));
        kDContainer3.getContentPane().add(btnUpdatePurchase, null);
        kDLabel2.setBounds(new Rectangle(7, 8, 263, 35));
        kDContainer3.getContentPane().add(kDLabel2, null);

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
	    return "com.kingdee.eas.fdc.market.app.WarningTestUIHandler";
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
     * output btnShowEntityPropertys_actionPerformed method
     */
    protected void btnShowEntityPropertys_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnShowMetaInfo_actionPerformed method
     */
    protected void btnShowMetaInfo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnExportEntityTable_actionPerformed method
     */
    protected void btnExportEntityTable_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblUIList_tableClicked method
     */
    protected void tblUIList_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output btnUpdatePurchase_actionPerformed method
     */
    protected void btnUpdatePurchase_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "WarningTestUI");
    }




}