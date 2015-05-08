/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

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
public abstract class AbstractRptTaskListUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRptTaskListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProjects;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDatePicker1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDatePicker2;
    protected com.kingdee.bos.ctrl.swing.KDContainer contTaskList;
    protected com.kingdee.bos.ctrl.swing.KDTextField kDTextField1;
    protected com.kingdee.bos.ctrl.swing.KDTextField kDTextField2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable taskTable;
    /**
     * output class constructor
     */
    public AbstractRptTaskListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRptTaskListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProjects = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDDatePicker1 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDDatePicker2 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contTaskList = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDTextField1 = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDTextField2 = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.taskTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contOrgUnit.setName("contOrgUnit");
        this.contProjects.setName("contProjects");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDDatePicker1.setName("kDDatePicker1");
        this.kDLabel1.setName("kDLabel1");
        this.kDDatePicker2.setName("kDDatePicker2");
        this.contTaskList.setName("contTaskList");
        this.kDTextField1.setName("kDTextField1");
        this.kDTextField2.setName("kDTextField2");
        this.taskTable.setName("taskTable");
        // CoreUI		
        this.setBorder(null);
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelUnderline(true);		
        this.contOrgUnit.setBoundLabelLength(50);		
        this.contOrgUnit.setEnabled(false);		
        this.contOrgUnit.setVisible(false);
        // contProjects		
        this.contProjects.setBoundLabelText(resHelper.getString("contProjects.boundLabelText"));		
        this.contProjects.setEnabled(false);		
        this.contProjects.setBoundLabelLength(50);		
        this.contProjects.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDDatePicker1		
        this.kDDatePicker1.setEnabled(false);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDDatePicker2		
        this.kDDatePicker2.setEnabled(false);
        // contTaskList		
        this.contTaskList.setTitle(resHelper.getString("contTaskList.title"));		
        this.contTaskList.setEnableActive(false);
        // kDTextField1		
        this.kDTextField1.setEnabled(false);
        // kDTextField2		
        this.kDTextField2.setEnabled(false);
        // taskTable
		String taskTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"projectName\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"taskName\" t:width=\"230\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"taskType\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" /><t:Column t:key=\"startDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"endate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"checkdate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" t:styleID=\"sCol6\" /><t:Column t:key=\"actStartDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" t:styleID=\"sCol7\" /><t:Column t:key=\"actEndDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:configured=\"false\" t:styleID=\"sCol8\" /><t:Column t:key=\"adminDept\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:configured=\"false\" /><t:Column t:key=\"adminPerson\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:configured=\"false\" /><t:Column t:key=\"scheduleEvaPerson\" t:width=\"74\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:configured=\"false\" /><t:Column t:key=\"quatityEvaPerson\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{projectName}</t:Cell><t:Cell>$Resource{taskName}</t:Cell><t:Cell>$Resource{taskType}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{endate}</t:Cell><t:Cell>$Resource{checkdate}</t:Cell><t:Cell>$Resource{actStartDate}</t:Cell><t:Cell>$Resource{actEndDate}</t:Cell><t:Cell>$Resource{adminDept}</t:Cell><t:Cell>$Resource{adminPerson}</t:Cell><t:Cell>$Resource{scheduleEvaPerson}</t:Cell><t:Cell>$Resource{quatityEvaPerson}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.taskTable.setFormatXml(resHelper.translateString("taskTable",taskTableStrXML));
        this.taskTable.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    taskTable_tableClicked(e);
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
        contOrgUnit.setBounds(new Rectangle(14, 3, 270, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(14, 3, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProjects.setBounds(new Rectangle(15, 24, 622, 19));
        this.add(contProjects, new KDLayout.Constraints(15, 24, 622, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(661, 24, 79, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(661, 24, 79, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDDatePicker1.setBounds(new Rectangle(741, 24, 114, 19));
        this.add(kDDatePicker1, new KDLayout.Constraints(741, 24, 114, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabel1.setBounds(new Rectangle(863, 24, 20, 19));
        this.add(kDLabel1, new KDLayout.Constraints(863, 24, 20, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDDatePicker2.setBounds(new Rectangle(879, 24, 114, 19));
        this.add(kDDatePicker2, new KDLayout.Constraints(879, 24, 114, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contTaskList.setBounds(new Rectangle(13, 61, 988, 558));
        this.add(contTaskList, new KDLayout.Constraints(13, 61, 988, 558, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contOrgUnit
        contOrgUnit.setBoundEditor(kDTextField1);
        //contProjects
        contProjects.setBoundEditor(kDTextField2);
        //contTaskList
contTaskList.getContentPane().setLayout(new BorderLayout(0, 0));        contTaskList.getContentPane().add(taskTable, BorderLayout.CENTER);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
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
	    return "com.kingdee.eas.fdc.schedule.report.app.RptTaskListUIHandler";
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
     * output taskTable_tableClicked method
     */
    protected void taskTable_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.report.client", "RptTaskListUI");
    }




}