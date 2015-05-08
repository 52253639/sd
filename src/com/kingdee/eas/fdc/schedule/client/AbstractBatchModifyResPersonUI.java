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
public abstract class AbstractBatchModifyResPersonUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBatchModifyResPersonUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnLeftMove;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAllLeftMove;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRightMove;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAllRightMove;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cboTask;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cboPanelPoint;
    protected com.kingdee.bos.ctrl.swing.KDButton btnmMakeSure;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSaveAndClose;
    protected com.kingdee.bos.ctrl.swing.KDButton btnClose;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDutyPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDutyDep;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtScheduleAppraisePerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtQualityAppraisePerson;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDLeftTable;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDRightTable;
    protected ActionLeftMove actionLeftMove = null;
    protected ActionAllLeftMove actionAllLeftMove = null;
    protected ActionAllRightMove actionAllRightMove = null;
    protected ActionRightMove actionRightMove = null;
    protected ActionMakeSure actionMakeSure = null;
    protected ActionSaveAndClose actionSaveAndClose = null;
    protected ActionClose actionClose = null;
    /**
     * output class constructor
     */
    public AbstractBatchModifyResPersonUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBatchModifyResPersonUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionLeftMove
        this.actionLeftMove = new ActionLeftMove(this);
        getActionManager().registerAction("actionLeftMove", actionLeftMove);
         this.actionLeftMove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAllLeftMove
        this.actionAllLeftMove = new ActionAllLeftMove(this);
        getActionManager().registerAction("actionAllLeftMove", actionAllLeftMove);
         this.actionAllLeftMove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAllRightMove
        this.actionAllRightMove = new ActionAllRightMove(this);
        getActionManager().registerAction("actionAllRightMove", actionAllRightMove);
         this.actionAllRightMove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRightMove
        this.actionRightMove = new ActionRightMove(this);
        getActionManager().registerAction("actionRightMove", actionRightMove);
         this.actionRightMove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMakeSure
        this.actionMakeSure = new ActionMakeSure(this);
        getActionManager().registerAction("actionMakeSure", actionMakeSure);
         this.actionMakeSure.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSaveAndClose
        this.actionSaveAndClose = new ActionSaveAndClose(this);
        getActionManager().registerAction("actionSaveAndClose", actionSaveAndClose);
         this.actionSaveAndClose.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionClose
        this.actionClose = new ActionClose(this);
        getActionManager().registerAction("actionClose", actionClose);
         this.actionClose.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnLeftMove = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAllLeftMove = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRightMove = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAllRightMove = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.cboTask = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cboPanelPoint = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.btnmMakeSure = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnSaveAndClose = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnClose = new com.kingdee.bos.ctrl.swing.KDButton();
        this.prmtDutyPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtDutyDep = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtScheduleAppraisePerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtQualityAppraisePerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLeftTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDRightTable = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.btnLeftMove.setName("btnLeftMove");
        this.btnAllLeftMove.setName("btnAllLeftMove");
        this.btnRightMove.setName("btnRightMove");
        this.btnAllRightMove.setName("btnAllRightMove");
        this.cboTask.setName("cboTask");
        this.cboPanelPoint.setName("cboPanelPoint");
        this.btnmMakeSure.setName("btnmMakeSure");
        this.btnSaveAndClose.setName("btnSaveAndClose");
        this.btnClose.setName("btnClose");
        this.prmtDutyPerson.setName("prmtDutyPerson");
        this.prmtDutyDep.setName("prmtDutyDep");
        this.prmtScheduleAppraisePerson.setName("prmtScheduleAppraisePerson");
        this.prmtQualityAppraisePerson.setName("prmtQualityAppraisePerson");
        this.kDLeftTable.setName("kDLeftTable");
        this.kDRightTable.setName("kDRightTable");
        // CoreUI		
        this.setPreferredSize(new Dimension(950,559));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(70);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelLength(70);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelLength(70);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelLength(70);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setEnableActive(false);
        // kDContainer2		
        this.kDContainer2.setEnableActive(false);		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // btnLeftMove
        this.btnLeftMove.setAction((IItemAction)ActionProxyFactory.getProxy(actionLeftMove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnLeftMove.setText(resHelper.getString("btnLeftMove.text"));
        // btnAllLeftMove
        this.btnAllLeftMove.setAction((IItemAction)ActionProxyFactory.getProxy(actionAllLeftMove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAllLeftMove.setText(resHelper.getString("btnAllLeftMove.text"));
        // btnRightMove
        this.btnRightMove.setAction((IItemAction)ActionProxyFactory.getProxy(actionRightMove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRightMove.setText(resHelper.getString("btnRightMove.text"));
        // btnAllRightMove
        this.btnAllRightMove.setAction((IItemAction)ActionProxyFactory.getProxy(actionAllRightMove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAllRightMove.setText(resHelper.getString("btnAllRightMove.text"));
        // cboTask		
        this.cboTask.setText(resHelper.getString("cboTask.text"));
        this.cboTask.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    cboTask_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // cboPanelPoint		
        this.cboPanelPoint.setText(resHelper.getString("cboPanelPoint.text"));
        this.cboPanelPoint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    cboPanelPoint_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnmMakeSure
        this.btnmMakeSure.setAction((IItemAction)ActionProxyFactory.getProxy(actionMakeSure, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnmMakeSure.setText(resHelper.getString("btnmMakeSure.text"));
        // btnSaveAndClose
        this.btnSaveAndClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionSaveAndClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSaveAndClose.setText(resHelper.getString("btnSaveAndClose.text"));
        this.btnSaveAndClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSaveAndClose_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnClose
        this.btnClose.setAction((IItemAction)ActionProxyFactory.getProxy(actionClose, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClose.setText(resHelper.getString("btnClose.text"));
        // prmtDutyPerson		
        this.prmtDutyPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtDutyPerson.setCommitFormat("$name$");
        // prmtDutyDep		
        this.prmtDutyDep.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7FullOrgUnitQuery");		
        this.prmtDutyDep.setCommitFormat("$name$");
        // prmtScheduleAppraisePerson		
        this.prmtScheduleAppraisePerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtScheduleAppraisePerson.setCommitFormat("$name$");
        // prmtQualityAppraisePerson		
        this.prmtQualityAppraisePerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtQualityAppraisePerson.setCommitFormat("$name$");
        // kDLeftTable
		String kDLeftTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"taskName\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"resPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"resDep\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"isLeaf\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"longNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol6\" /><t:Column t:key=\"planEvaluatePerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"qualityEvaluatePerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">ID</t:Cell><t:Cell t:configured=\"false\">任务名称</t:Cell><t:Cell t:configured=\"false\">责任人</t:Cell><t:Cell t:configured=\"false\">责任部门</t:Cell><t:Cell t:configured=\"false\">级次</t:Cell><t:Cell t:configured=\"false\">是否叶子节点</t:Cell><t:Cell t:configured=\"false\">长编码</t:Cell><t:Cell t:configured=\"false\">进度评价人</t:Cell><t:Cell t:configured=\"false\">质量评价人</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDLeftTable.setFormatXml(resHelper.translateString("kDLeftTable",kDLeftTableStrXML));

        

        // kDRightTable
		String kDRightTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"taskName\" t:width=\"320\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{taskName}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDRightTable.setFormatXml(resHelper.translateString("kDRightTable",kDRightTableStrXML));
        this.kDRightTable.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kDRightTable_tableClicked(e);
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
        this.setBounds(new Rectangle(10, 10, 950, 559));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 950, 559));
        kDLabelContainer1.setBounds(new Rectangle(10, 10, 200, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(10, 10, 200, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(253, 10, 200, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(253, 10, 200, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(496, 10, 200, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(496, 10, 200, 19, 0));
        kDLabelContainer4.setBounds(new Rectangle(740, 10, 200, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(740, 10, 200, 19, 0));
        kDContainer1.setBounds(new Rectangle(10, 40, 453, 469));
        this.add(kDContainer1, new KDLayout.Constraints(10, 40, 453, 469, 0));
        kDContainer2.setBounds(new Rectangle(577, 40, 362, 469));
        this.add(kDContainer2, new KDLayout.Constraints(577, 40, 362, 469, 0));
        btnLeftMove.setBounds(new Rectangle(472, 248, 96, 22));
        this.add(btnLeftMove, new KDLayout.Constraints(472, 248, 96, 22, 0));
        btnAllLeftMove.setBounds(new Rectangle(472, 319, 96, 22));
        this.add(btnAllLeftMove, new KDLayout.Constraints(472, 319, 96, 22, 0));
        btnRightMove.setBounds(new Rectangle(472, 213, 96, 22));
        this.add(btnRightMove, new KDLayout.Constraints(472, 213, 96, 22, 0));
        btnAllRightMove.setBounds(new Rectangle(472, 283, 96, 22));
        this.add(btnAllRightMove, new KDLayout.Constraints(472, 283, 96, 22, 0));
        cboTask.setBounds(new Rectangle(30, 520, 121, 19));
        this.add(cboTask, new KDLayout.Constraints(30, 520, 121, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        cboPanelPoint.setBounds(new Rectangle(166, 520, 102, 19));
        this.add(cboPanelPoint, new KDLayout.Constraints(166, 520, 102, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        btnmMakeSure.setBounds(new Rectangle(599, 520, 73, 21));
        this.add(btnmMakeSure, new KDLayout.Constraints(599, 520, 73, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT));
        btnSaveAndClose.setBounds(new Rectangle(688, 520, 101, 21));
        this.add(btnSaveAndClose, new KDLayout.Constraints(688, 520, 101, 21, 0));
        btnClose.setBounds(new Rectangle(806, 520, 73, 21));
        this.add(btnClose, new KDLayout.Constraints(806, 520, 73, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(prmtDutyPerson);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(prmtDutyDep);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(prmtScheduleAppraisePerson);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(prmtQualityAppraisePerson);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kDLeftTable, BorderLayout.CENTER);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(kDRightTable, BorderLayout.CENTER);

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
	    return "com.kingdee.eas.fdc.schedule.app.BatchModifyResPersonUIHandler";
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
	 * ????????У??
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
     * output cboTask_mouseClicked method
     */
    protected void cboTask_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output cboPanelPoint_mouseClicked method
     */
    protected void cboPanelPoint_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnSaveAndClose_actionPerformed method
     */
    protected void btnSaveAndClose_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kDRightTable_tableClicked method
     */
    protected void kDRightTable_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    	

    /**
     * output actionLeftMove_actionPerformed method
     */
    public void actionLeftMove_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAllLeftMove_actionPerformed method
     */
    public void actionAllLeftMove_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAllRightMove_actionPerformed method
     */
    public void actionAllRightMove_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRightMove_actionPerformed method
     */
    public void actionRightMove_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMakeSure_actionPerformed method
     */
    public void actionMakeSure_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSaveAndClose_actionPerformed method
     */
    public void actionSaveAndClose_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionClose_actionPerformed method
     */
    public void actionClose_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionLeftMove(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionLeftMove() {
    	return false;
    }
	public RequestContext prepareActionAllLeftMove(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAllLeftMove() {
    	return false;
    }
	public RequestContext prepareActionAllRightMove(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAllRightMove() {
    	return false;
    }
	public RequestContext prepareActionRightMove(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRightMove() {
    	return false;
    }
	public RequestContext prepareActionMakeSure(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMakeSure() {
    	return false;
    }
	public RequestContext prepareActionSaveAndClose(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSaveAndClose() {
    	return false;
    }
	public RequestContext prepareActionClose(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionClose() {
    	return false;
    }

    /**
     * output ActionLeftMove class
     */     
    protected class ActionLeftMove extends ItemAction {     
    
        public ActionLeftMove()
        {
            this(null);
        }

        public ActionLeftMove(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionLeftMove.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLeftMove.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionLeftMove.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBatchModifyResPersonUI.this, "ActionLeftMove", "actionLeftMove_actionPerformed", e);
        }
    }

    /**
     * output ActionAllLeftMove class
     */     
    protected class ActionAllLeftMove extends ItemAction {     
    
        public ActionAllLeftMove()
        {
            this(null);
        }

        public ActionAllLeftMove(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAllLeftMove.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllLeftMove.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllLeftMove.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBatchModifyResPersonUI.this, "ActionAllLeftMove", "actionAllLeftMove_actionPerformed", e);
        }
    }

    /**
     * output ActionAllRightMove class
     */     
    protected class ActionAllRightMove extends ItemAction {     
    
        public ActionAllRightMove()
        {
            this(null);
        }

        public ActionAllRightMove(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAllRightMove.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllRightMove.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllRightMove.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBatchModifyResPersonUI.this, "ActionAllRightMove", "actionAllRightMove_actionPerformed", e);
        }
    }

    /**
     * output ActionRightMove class
     */     
    protected class ActionRightMove extends ItemAction {     
    
        public ActionRightMove()
        {
            this(null);
        }

        public ActionRightMove(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRightMove.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRightMove.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRightMove.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBatchModifyResPersonUI.this, "ActionRightMove", "actionRightMove_actionPerformed", e);
        }
    }

    /**
     * output ActionMakeSure class
     */     
    protected class ActionMakeSure extends ItemAction {     
    
        public ActionMakeSure()
        {
            this(null);
        }

        public ActionMakeSure(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionMakeSure.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMakeSure.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMakeSure.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBatchModifyResPersonUI.this, "ActionMakeSure", "actionMakeSure_actionPerformed", e);
        }
    }

    /**
     * output ActionSaveAndClose class
     */     
    protected class ActionSaveAndClose extends ItemAction {     
    
        public ActionSaveAndClose()
        {
            this(null);
        }

        public ActionSaveAndClose(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSaveAndClose.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveAndClose.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveAndClose.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBatchModifyResPersonUI.this, "ActionSaveAndClose", "actionSaveAndClose_actionPerformed", e);
        }
    }

    /**
     * output ActionClose class
     */     
    protected class ActionClose extends ItemAction {     
    
        public ActionClose()
        {
            this(null);
        }

        public ActionClose(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionClose.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClose.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionClose.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractBatchModifyResPersonUI.this, "ActionClose", "actionClose_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "BatchModifyResPersonUI");
    }




}