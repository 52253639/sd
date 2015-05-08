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
public abstract class AbstractREScheduleImportPlanTemplateListUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractREScheduleImportPlanTemplateListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCatagory;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer cont;
    protected com.kingdee.bos.ctrl.swing.KDButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDButton btnClose;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTemplate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCatagory;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dpStartDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combTemplate;
    protected actionConfirm actionConfirm = null;
    protected actionClose actionClose = null;
    /**
     * output class constructor
     */
    public AbstractREScheduleImportPlanTemplateListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractREScheduleImportPlanTemplateListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.schedule.app", "RESchTemplateTaskQuery");
        //actionConfirm
        this.actionConfirm = new actionConfirm(this);
        getActionManager().registerAction("actionConfirm", actionConfirm);
         this.actionConfirm.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClose
        this.actionClose = new actionClose(this);
        getActionManager().registerAction("actionClose", actionClose);
         this.actionClose.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contCatagory = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cont = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnClose = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contTemplate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCatagory = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.dpStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.combTemplate = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDContainer1.setName("kDContainer1");
        this.contCatagory.setName("contCatagory");
        this.cont.setName("cont");
        this.btnConfirm.setName("btnConfirm");
        this.btnClose.setName("btnClose");
        this.contTemplate.setName("contTemplate");
        this.prmtCatagory.setName("prmtCatagory");
        this.dpStartDate.setName("dpStartDate");
        this.combTemplate.setName("combTemplate");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"predecessorDesc\" t:width=\"130\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"taskType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" /><t:Column t:key=\"businessTypeDesc\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" /><t:Column t:key=\"referenceDay\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"isLeaf\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" t:styleID=\"sCol6\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" t:styleID=\"sCol7\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:configured=\"false\" t:styleID=\"sCol8\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:configured=\"false\" t:styleID=\"sCol9\" /><t:Column t:key=\"standardTask.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:configured=\"false\" t:styleID=\"sCol10\" /><t:Column t:key=\"achievementType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:configured=\"false\" t:styleID=\"sCol11\" /><t:Column t:key=\"template.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:configured=\"false\" t:styleID=\"sCol12\" /><t:Column t:key=\"longNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:configured=\"false\" t:styleID=\"sCol13\" /><t:Column t:key=\"parent.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{predecessorDesc}</t:Cell><t:Cell>$Resource{taskType}</t:Cell><t:Cell>$Resource{businessTypeDesc}</t:Cell><t:Cell>$Resource{referenceDay}</t:Cell><t:Cell>$Resource{isLeaf}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{standardTask.name}</t:Cell><t:Cell>$Resource{achievementType.name}</t:Cell><t:Cell>$Resource{template.id}</t:Cell><t:Cell>$Resource{longNumber}</t:Cell><t:Cell>$Resource{parent.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","name","predecessorDesc","taskType","businessTypeDesc","referenceDay","isLeaf","level","number","description","standardTask.name","achievementType.name","template.id","longNumber","parent.id"});


        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setEnableActive(false);
        // contCatagory		
        this.contCatagory.setBoundLabelText(resHelper.getString("contCatagory.boundLabelText"));		
        this.contCatagory.setBoundLabelLength(60);		
        this.contCatagory.setBoundLabelUnderline(true);
        // cont		
        this.cont.setBoundLabelText(resHelper.getString("cont.boundLabelText"));		
        this.cont.setBoundLabelLength(80);		
        this.cont.setBoundLabelUnderline(true);
        // btnConfirm
        this.btnConfirm.setAction((IItemAction)ActionProxyFactory.getProxy(actionConfirm, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnConfirm.setText(resHelper.getString("btnConfirm.text"));
        // btnClose
        this.btnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClose.setText(resHelper.getString("btnClose.text"));
        // contTemplate		
        this.contTemplate.setBoundLabelText(resHelper.getString("contTemplate.boundLabelText"));
        // prmtCatagory		
        this.prmtCatagory.setQueryInfo("com.kingdee.eas.fdc.schedule.app.RESchTemplateCatagoryQuery");		
        this.prmtCatagory.setEditFormat("$name$");		
        this.prmtCatagory.setDisplayFormat("$name$");		
        this.prmtCatagory.setCommitFormat("$name$");
        this.prmtCatagory.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCatagory_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // dpStartDate
        // combTemplate
        this.combTemplate.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    combTemplate_itemStateChanged(e);
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
        this.setBounds(new Rectangle(10, 10, 700, 400));
        this.setLayout(null);
        kDContainer1.setBounds(new Rectangle(10, 40, 680, 320));
        this.add(kDContainer1, null);
        contCatagory.setBounds(new Rectangle(10, 10, 240, 19));
        this.add(contCatagory, null);
        cont.setBounds(new Rectangle(480, 10, 200, 19));
        this.add(cont, null);
        btnConfirm.setBounds(new Rectangle(506, 369, 73, 21));
        this.add(btnConfirm, null);
        btnClose.setBounds(new Rectangle(601, 369, 73, 21));
        this.add(btnClose, null);
        contTemplate.setBounds(new Rectangle(259, 10, 208, 19));
        this.add(contTemplate, null);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(tblMain, BorderLayout.CENTER);
        //contCatagory
        contCatagory.setBoundEditor(prmtCatagory);
        //cont
        cont.setBoundEditor(dpStartDate);
        //contTemplate
        contTemplate.setBoundEditor(combTemplate);

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
	    return "com.kingdee.eas.fdc.schedule.app.REScheduleImportPlanTemplateListUIHandler";
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
     * output prmtCatagory_dataChanged method
     */
    protected void prmtCatagory_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output combTemplate_itemStateChanged method
     */
    protected void combTemplate_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("taskType"));
        sic.add(new SelectorItemInfo("referenceDay"));
        sic.add(new SelectorItemInfo("isLeaf"));
        sic.add(new SelectorItemInfo("level"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("predecessorDesc"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("standardTask.name"));
        sic.add(new SelectorItemInfo("achievementType.name"));
        sic.add(new SelectorItemInfo("template.id"));
        sic.add(new SelectorItemInfo("longNumber"));
        sic.add(new SelectorItemInfo("businessTypeDesc"));
        sic.add(new SelectorItemInfo("parent.id"));
        return sic;
    }        
    	

    /**
     * output actionConfirm_actionPerformed method
     */
    public void actionConfirm_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClose_actionPerformed method
     */
    public void actionClose_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareactionConfirm(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionConfirm() {
    	return false;
    }
	public RequestContext prepareactionClose(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionClose() {
    	return false;
    }

    /**
     * output actionConfirm class
     */     
    protected class actionConfirm extends ItemAction {     
    
        public actionConfirm()
        {
            this(null);
        }

        public actionConfirm(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionConfirm.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionConfirm.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionConfirm.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractREScheduleImportPlanTemplateListUI.this, "actionConfirm", "actionConfirm_actionPerformed", e);
        }
    }

    /**
     * output actionClose class
     */     
    protected class actionClose extends ItemAction {     
    
        public actionClose()
        {
            this(null);
        }

        public actionClose(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("actionClose.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionClose.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionClose.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractREScheduleImportPlanTemplateListUI.this, "actionClose", "actionClose_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "REScheduleImportPlanTemplateListUI");
    }




}