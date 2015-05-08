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
public abstract class AbstractRESchCompareUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRESchCompareUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBaseVer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompareVer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnVerCompareOfPage;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBaseVer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCompareVer;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected ActionCompareVer actionCompareVer = null;
    /**
     * output class constructor
     */
    public AbstractRESchCompareUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRESchCompareUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionCompareVer
        this.actionCompareVer = new ActionCompareVer(this);
        getActionManager().registerAction("actionCompareVer", actionCompareVer);
         this.actionCompareVer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contBaseVer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompareVer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnVerCompareOfPage = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.prmtBaseVer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCompareVer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contBaseVer.setName("contBaseVer");
        this.contCompareVer.setName("contCompareVer");
        this.btnVerCompareOfPage.setName("btnVerCompareOfPage");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.prmtBaseVer.setName("prmtBaseVer");
        this.prmtCompareVer.setName("prmtCompareVer");
        this.kDPanel1.setName("kDPanel1");
        this.kDTable1.setName("kDTable1");
        // CoreUI		
        this.menuTool.setVisible(false);
        // contBaseVer		
        this.contBaseVer.setBoundLabelText(resHelper.getString("contBaseVer.boundLabelText"));		
        this.contBaseVer.setBoundLabelUnderline(true);		
        this.contBaseVer.setBoundLabelLength(80);
        // contCompareVer		
        this.contCompareVer.setBoundLabelText(resHelper.getString("contCompareVer.boundLabelText"));		
        this.contCompareVer.setBoundLabelUnderline(true);		
        this.contCompareVer.setBoundLabelLength(80);
        // btnVerCompareOfPage
        this.btnVerCompareOfPage.setAction((IItemAction)ActionProxyFactory.getProxy(actionCompareVer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnVerCompareOfPage.setText(resHelper.getString("btnVerCompareOfPage.text"));
        // kDTabbedPane1
        // prmtBaseVer		
        this.prmtBaseVer.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7ScheduleVerCompareQuery");		
        this.prmtBaseVer.setCommitFormat("$versionName$");		
        this.prmtBaseVer.setEditFormat("$versionName$");		
        this.prmtBaseVer.setDisplayFormat("$versionName$");
        // prmtCompareVer		
        this.prmtCompareVer.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7ScheduleVerCompareQuery");		
        this.prmtCompareVer.setCommitFormat("$versionName$");		
        this.prmtCompareVer.setEditFormat("$versionName$");		
        this.prmtCompareVer.setDisplayFormat("$versionName$");
        // kDPanel1		
        this.kDPanel1.setBorder(null);
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"taskName\" t:width=\"330\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"workDayPre\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"workDayDep\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"planStartPre\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol3\" /><t:Column t:key=\"planStartDep\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol4\" /><t:Column t:key=\"planEndPre\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol5\" /><t:Column t:key=\"planEndDep\" t:width=\"90\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol6\" /><t:Column t:key=\"longNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"isLeaf\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{taskName}</t:Cell><t:Cell>$Resource{workDayPre}</t:Cell><t:Cell>$Resource{workDayDep}</t:Cell><t:Cell>$Resource{planStartPre}</t:Cell><t:Cell>$Resource{planStartDep}</t:Cell><t:Cell>$Resource{planEndPre}</t:Cell><t:Cell>$Resource{planEndDep}</t:Cell><t:Cell>$Resource{longNumber}</t:Cell><t:Cell>$Resource{isLeaf}</t:Cell><t:Cell>$Resource{level}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{taskName_Row2}</t:Cell><t:Cell>$Resource{workDayPre_Row2}</t:Cell><t:Cell>$Resource{workDayDep_Row2}</t:Cell><t:Cell>$Resource{planStartPre_Row2}</t:Cell><t:Cell>$Resource{planStartDep_Row2}</t:Cell><t:Cell>$Resource{planEndPre_Row2}</t:Cell><t:Cell>$Resource{planEndDep_Row2}</t:Cell><t:Cell>$Resource{longNumber_Row2}</t:Cell><t:Cell>$Resource{isLeaf_Row2}</t:Cell><t:Cell>$Resource{level_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"0\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"0\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"0\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"1\" t:right=\"7\" /><t:Block t:top=\"0\" t:left=\"8\" t:bottom=\"1\" t:right=\"8\" /><t:Block t:top=\"0\" t:left=\"9\" t:bottom=\"1\" t:right=\"9\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));

        

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
        contBaseVer.setBounds(new Rectangle(10, 10, 378, 19));
        this.add(contBaseVer, new KDLayout.Constraints(10, 10, 378, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCompareVer.setBounds(new Rectangle(463, 10, 378, 19));
        this.add(contCompareVer, new KDLayout.Constraints(463, 10, 378, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnVerCompareOfPage.setBounds(new Rectangle(889, 10, 86, 19));
        this.add(btnVerCompareOfPage, new KDLayout.Constraints(889, 10, 86, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTabbedPane1.setBounds(new Rectangle(10, 40, 993, 579));
        this.add(kDTabbedPane1, new KDLayout.Constraints(10, 40, 993, 579, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contBaseVer
        contBaseVer.setBoundEditor(prmtBaseVer);
        //contCompareVer
        contCompareVer.setBoundEditor(prmtCompareVer);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(kDTable1, BorderLayout.CENTER);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
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
        this.toolBar.add(btnPageSetup);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.RESchCompareUIHandler";
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
     * output actionCompareVer_actionPerformed method
     */
    public void actionCompareVer_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionCompareVer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCompareVer() {
    	return false;
    }

    /**
     * output ActionCompareVer class
     */     
    protected class ActionCompareVer extends ItemAction {     
    
        public ActionCompareVer()
        {
            this(null);
        }

        public ActionCompareVer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionCompareVer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCompareVer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCompareVer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRESchCompareUI.this, "ActionCompareVer", "actionCompareVer_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "RESchCompareUI");
    }




}