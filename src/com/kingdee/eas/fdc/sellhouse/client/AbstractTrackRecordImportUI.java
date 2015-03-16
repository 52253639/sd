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
public abstract class AbstractTrackRecordImportUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTrackRecordImportUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFilePath;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contExcelCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAddNewCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvaluableCont;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contModifyCount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFilePath;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtExcelCount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAddNewCount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtInvaluableCont;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtModifyCount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExcelImport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExcelSave;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExcelImport;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemExcelSave;
    protected ActionExcelImport actionExcelImport = null;
    protected ActionExcelSave actionExcelSave = null;
    /**
     * output class constructor
     */
    public AbstractTrackRecordImportUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTrackRecordImportUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        //actionExcelImport
        this.actionExcelImport = new ActionExcelImport(this);
        getActionManager().registerAction("actionExcelImport", actionExcelImport);
         this.actionExcelImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionExcelSave
        this.actionExcelSave = new ActionExcelSave(this);
        getActionManager().registerAction("actionExcelSave", actionExcelSave);
         this.actionExcelSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFilePath = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contExcelCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAddNewCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvaluableCont = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contModifyCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtFilePath = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtExcelCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAddNewCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtInvaluableCont = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtModifyCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnExcelImport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnExcelSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemExcelImport = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemExcelSave = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contSellProject.setName("contSellProject");
        this.contFilePath.setName("contFilePath");
        this.contExcelCount.setName("contExcelCount");
        this.contAddNewCount.setName("contAddNewCount");
        this.contInvaluableCont.setName("contInvaluableCont");
        this.contModifyCount.setName("contModifyCount");
        this.prmtSellProject.setName("prmtSellProject");
        this.txtFilePath.setName("txtFilePath");
        this.txtExcelCount.setName("txtExcelCount");
        this.txtAddNewCount.setName("txtAddNewCount");
        this.txtInvaluableCont.setName("txtInvaluableCont");
        this.txtModifyCount.setName("txtModifyCount");
        this.btnExcelImport.setName("btnExcelImport");
        this.btnExcelSave.setName("btnExcelSave");
        this.menuItemExcelImport.setName("menuItemExcelImport");
        this.menuItemExcelSave.setName("menuItemExcelSave");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"importStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"seller\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"phoneNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"commerceChance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"eventDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"receptionType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"eventType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"trackDes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{importStatus}</t:Cell><t:Cell>$Resource{seller}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{phoneNumber}</t:Cell><t:Cell>$Resource{commerceChance}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{eventDate}</t:Cell><t:Cell>$Resource{receptionType}</t:Cell><t:Cell>$Resource{eventType}</t:Cell><t:Cell>$Resource{trackDes}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblMain_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
                this.tblMain.putBindContents("mainQuery",new String[] {"","","","","","","","","","","",""});


        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contFilePath		
        this.contFilePath.setBoundLabelText(resHelper.getString("contFilePath.boundLabelText"));		
        this.contFilePath.setBoundLabelLength(100);		
        this.contFilePath.setBoundLabelUnderline(true);
        // contExcelCount		
        this.contExcelCount.setBoundLabelText(resHelper.getString("contExcelCount.boundLabelText"));		
        this.contExcelCount.setBoundLabelLength(100);		
        this.contExcelCount.setBoundLabelUnderline(true);
        // contAddNewCount		
        this.contAddNewCount.setBoundLabelText(resHelper.getString("contAddNewCount.boundLabelText"));		
        this.contAddNewCount.setBoundLabelLength(100);		
        this.contAddNewCount.setBoundLabelUnderline(true);
        // contInvaluableCont		
        this.contInvaluableCont.setBoundLabelText(resHelper.getString("contInvaluableCont.boundLabelText"));		
        this.contInvaluableCont.setBoundLabelLength(100);		
        this.contInvaluableCont.setBoundLabelUnderline(true);
        // contModifyCount		
        this.contModifyCount.setBoundLabelText(resHelper.getString("contModifyCount.boundLabelText"));		
        this.contModifyCount.setBoundLabelLength(100);		
        this.contModifyCount.setBoundLabelUnderline(true);
        // prmtSellProject		
        this.prmtSellProject.setRequired(true);		
        this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");
        this.prmtSellProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSellProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtFilePath		
        this.txtFilePath.setEditable(false);		
        this.txtFilePath.setEnabled(false);
        // txtExcelCount		
        this.txtExcelCount.setEnabled(false);		
        this.txtExcelCount.setPrecision(0);		
        this.txtExcelCount.setEditable(false);
        this.txtExcelCount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtExcelCount_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtAddNewCount		
        this.txtAddNewCount.setEditable(false);		
        this.txtAddNewCount.setEnabled(false);		
        this.txtAddNewCount.setPrecision(0);
        // txtInvaluableCont		
        this.txtInvaluableCont.setEditable(false);		
        this.txtInvaluableCont.setEnabled(false);		
        this.txtInvaluableCont.setPrecision(0);
        // txtModifyCount		
        this.txtModifyCount.setPrecision(0);		
        this.txtModifyCount.setEnabled(false);		
        this.txtModifyCount.setEditable(false);
        // btnExcelImport
        this.btnExcelImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionExcelImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExcelImport.setText(resHelper.getString("btnExcelImport.text"));		
        this.btnExcelImport.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));		
        this.btnExcelImport.setToolTipText(resHelper.getString("btnExcelImport.toolTipText"));
        // btnExcelSave
        this.btnExcelSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionExcelSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExcelSave.setText(resHelper.getString("btnExcelSave.text"));		
        this.btnExcelSave.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));		
        this.btnExcelSave.setToolTipText(resHelper.getString("btnExcelSave.toolTipText"));
        // menuItemExcelImport
        this.menuItemExcelImport.setAction((IItemAction)ActionProxyFactory.getProxy(actionExcelImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExcelImport.setText(resHelper.getString("menuItemExcelImport.text"));		
        this.menuItemExcelImport.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));		
        this.menuItemExcelImport.setToolTipText(resHelper.getString("menuItemExcelImport.toolTipText"));
        // menuItemExcelSave
        this.menuItemExcelSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionExcelSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemExcelSave.setText(resHelper.getString("menuItemExcelSave.text"));		
        this.menuItemExcelSave.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));		
        this.menuItemExcelSave.setToolTipText(resHelper.getString("menuItemExcelSave.toolTipText"));
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
        tblMain.setBounds(new Rectangle(9, 95, 996, 526));
        this.add(tblMain, new KDLayout.Constraints(9, 95, 996, 526, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contSellProject.setBounds(new Rectangle(12, 9, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(12, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contFilePath.setBounds(new Rectangle(372, 9, 630, 19));
        this.add(contFilePath, new KDLayout.Constraints(372, 9, 630, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contExcelCount.setBounds(new Rectangle(12, 35, 270, 19));
        this.add(contExcelCount, new KDLayout.Constraints(12, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contAddNewCount.setBounds(new Rectangle(12, 62, 270, 19));
        this.add(contAddNewCount, new KDLayout.Constraints(12, 62, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contInvaluableCont.setBounds(new Rectangle(374, 35, 270, 19));
        this.add(contInvaluableCont, new KDLayout.Constraints(374, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contModifyCount.setBounds(new Rectangle(375, 62, 270, 19));
        this.add(contModifyCount, new KDLayout.Constraints(375, 62, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contFilePath
        contFilePath.setBoundEditor(txtFilePath);
        //contExcelCount
        contExcelCount.setBoundEditor(txtExcelCount);
        //contAddNewCount
        contAddNewCount.setBoundEditor(txtAddNewCount);
        //contInvaluableCont
        contInvaluableCont.setBoundEditor(txtInvaluableCont);
        //contModifyCount
        contModifyCount.setBoundEditor(txtModifyCount);

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
        menuEdit.add(menuItemExcelImport);
        menuEdit.add(menuItemExcelSave);
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
        this.toolBar.add(btnExcelImport);
        this.toolBar.add(btnExcelSave);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.TrackRecordImportUIHandler";
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
     * output tblMain_editStopped method
     */
    protected void tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output prmtSellProject_dataChanged method
     */
    protected void prmtSellProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtExcelCount_actionPerformed method
     */
    protected void txtExcelCount_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
     * output actionExcelImport_actionPerformed method
     */
    public void actionExcelImport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionExcelSave_actionPerformed method
     */
    public void actionExcelSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionExcelImport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExcelImport() {
    	return false;
    }
	public RequestContext prepareActionExcelSave(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExcelSave() {
    	return false;
    }

    /**
     * output ActionExcelImport class
     */     
    protected class ActionExcelImport extends ItemAction {     
    
        public ActionExcelImport()
        {
            this(null);
        }

        public ActionExcelImport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionExcelImport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelImport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelImport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTrackRecordImportUI.this, "ActionExcelImport", "actionExcelImport_actionPerformed", e);
        }
    }

    /**
     * output ActionExcelSave class
     */     
    protected class ActionExcelSave extends ItemAction {     
    
        public ActionExcelSave()
        {
            this(null);
        }

        public ActionExcelSave(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionExcelSave.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelSave.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExcelSave.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTrackRecordImportUI.this, "ActionExcelSave", "actionExcelSave_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "TrackRecordImportUI");
    }




}