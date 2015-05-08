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
public abstract class AbstractWorkAmountBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractWorkAmountBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBizDept;
    protected com.kingdee.bos.ctrl.swing.KDContainer mainContainer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conTaskStartDateStart;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSelected;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker startDateStart;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker startDateEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conAdminPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminPerson;
    protected com.kingdee.eas.fdc.schedule.WorkAmountBillInfo editData = null;
    protected ActionSelected actionSelected = null;
    /**
     * output class constructor
     */
    public AbstractWorkAmountBillEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractWorkAmountBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionSelected
        this.actionSelected = new ActionSelected(this);
        getActionManager().registerAction("actionSelected", actionSelected);
         this.actionSelected.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBizDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtBizDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.mainContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.conTaskStartDateStart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSelected = new com.kingdee.bos.ctrl.swing.KDButton();
        this.startDateStart = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.startDateEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.conAdminPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtAdminPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreator.setName("contCreator");
        this.prmtCreator.setName("prmtCreator");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contBizDate.setName("contBizDate");
        this.pkBizDate.setName("pkBizDate");
        this.contName.setName("contName");
        this.txtName.setName("txtName");
        this.contBizDept.setName("contBizDept");
        this.prmtBizDept.setName("prmtBizDept");
        this.mainContainer.setName("mainContainer");
        this.kdtEntry.setName("kdtEntry");
        this.conTaskStartDateStart.setName("conTaskStartDateStart");
        this.btnSelected.setName("btnSelected");
        this.startDateStart.setName("startDateStart");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.startDateEndDate.setName("startDateEndDate");
        this.conAdminPerson.setName("conAdminPerson");
        this.prmtAdminPerson.setName("prmtAdminPerson");
        // CoreUI		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setRequestFocusEnabled(false);
        // prmtCreator		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$number$");		
        this.prmtCreator.setQueryInfo("com.kingdee.eas.basedata.person.app.F7PersonQuery");		
        this.prmtCreator.setRequired(true);		
        this.prmtCreator.setEnabled(false);
        this.prmtCreator.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtCreator_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setEnabled(false);
        // pkBizDate		
        this.pkBizDate.setEnabled(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // txtName		
        this.txtName.setRequired(true);
        // contBizDept		
        this.contBizDept.setBoundLabelText(resHelper.getString("contBizDept.boundLabelText"));		
        this.contBizDept.setBoundLabelLength(100);		
        this.contBizDept.setBoundLabelUnderline(true);
        // prmtBizDept		
        this.prmtBizDept.setDisplayFormat("$name$");		
        this.prmtBizDept.setEditFormat("$number$");		
        this.prmtBizDept.setCommitFormat("$number$");		
        this.prmtBizDept.setRequired(true);
        this.prmtBizDept.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtBizDept_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // mainContainer		
        this.mainContainer.setTitle(resHelper.getString("mainContainer.title"));
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"tasknumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"taskname\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"adminPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"totalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"totalPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"percent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"completeDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"confirmAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"confirmPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{tasknumber}</t:Cell><t:Cell>$Resource{taskname}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{adminPerson}</t:Cell><t:Cell>$Resource{totalAmount}</t:Cell><t:Cell>$Resource{totalPercent}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{percent}</t:Cell><t:Cell>$Resource{completeDate}</t:Cell><t:Cell>$Resource{confirmAmount}</t:Cell><t:Cell>$Resource{confirmPercent}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));

                this.kdtEntry.putBindContents("editData",new String[] {"","","","","task.adminPerson.name","totalAmount","totalPercent","amount","percent","completeDate","confirmAmount","confirmPercent","remark","id"});


        // conTaskStartDateStart		
        this.conTaskStartDateStart.setBoundLabelText(resHelper.getString("conTaskStartDateStart.boundLabelText"));		
        this.conTaskStartDateStart.setBoundLabelUnderline(true);		
        this.conTaskStartDateStart.setBoundLabelLength(100);
        // btnSelected
        this.btnSelected.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelected, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSelected.setText(resHelper.getString("btnSelected.text"));
        // startDateStart
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(20);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // startDateEndDate
        // conAdminPerson		
        this.conAdminPerson.setBoundLabelText(resHelper.getString("conAdminPerson.boundLabelText"));		
        this.conAdminPerson.setBoundLabelLength(100);		
        this.conAdminPerson.setBoundLabelUnderline(true);
        // prmtAdminPerson		
        this.prmtAdminPerson.setDisplayFormat("$name$");		
        this.prmtAdminPerson.setEditFormat("$number$");		
        this.prmtAdminPerson.setCommitFormat("$number$");		
        this.prmtAdminPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.F7PersonQuery");		
        this.prmtAdminPerson.setRequired(true);
        this.prmtAdminPerson.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmt_adminPerson_willShow(e);
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
        contCreator.setBounds(new Rectangle(728, 15, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(728, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(11, 15, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(11, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(11, 41, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(11, 41, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(364, 15, 270, 19));
        this.add(contName, new KDLayout.Constraints(364, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDept.setBounds(new Rectangle(364, 41, 270, 19));
        this.add(contBizDept, new KDLayout.Constraints(364, 41, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        mainContainer.setBounds(new Rectangle(11, 101, 995, 519));
        this.add(mainContainer, new KDLayout.Constraints(11, 101, 995, 519, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        conTaskStartDateStart.setBounds(new Rectangle(11, 68, 270, 19));
        this.add(conTaskStartDateStart, new KDLayout.Constraints(11, 68, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnSelected.setBounds(new Rectangle(475, 68, 73, 21));
        this.add(btnSelected, new KDLayout.Constraints(475, 68, 73, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(292, 68, 166, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(292, 68, 166, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conAdminPerson.setBounds(new Rectangle(728, 47, 270, 19));
        this.add(conAdminPerson, new KDLayout.Constraints(728, 47, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contName
        contName.setBoundEditor(txtName);
        //contBizDept
        contBizDept.setBoundEditor(prmtBizDept);
        //mainContainer
mainContainer.getContentPane().setLayout(new BorderLayout(0, 0));        mainContainer.getContentPane().add(kdtEntry, BorderLayout.CENTER);
        //conTaskStartDateStart
        conTaskStartDateStart.setBoundEditor(startDateStart);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(startDateEndDate);
        //conAdminPerson
        conAdminPerson.setBoundEditor(prmtAdminPerson);

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
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
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
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("bizDept", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtBizDept, "data");
		dataBinder.registerBinding("entry.totalAmount", java.math.BigDecimal.class, this.kdtEntry, "totalAmount.text");
		dataBinder.registerBinding("entry.totalPercent", java.math.BigDecimal.class, this.kdtEntry, "totalPercent.text");
		dataBinder.registerBinding("entry.amount", java.math.BigDecimal.class, this.kdtEntry, "amount.text");
		dataBinder.registerBinding("entry.percent", java.math.BigDecimal.class, this.kdtEntry, "percent.text");
		dataBinder.registerBinding("entry.completeDate", java.util.Date.class, this.kdtEntry, "completeDate.text");
		dataBinder.registerBinding("entry.confirmAmount", java.math.BigDecimal.class, this.kdtEntry, "confirmAmount.text");
		dataBinder.registerBinding("entry.confirmPercent", java.math.BigDecimal.class, this.kdtEntry, "confirmPercent.text");
		dataBinder.registerBinding("entry.remark", String.class, this.kdtEntry, "remark.text");
		dataBinder.registerBinding("entry", com.kingdee.eas.fdc.schedule.WorkAmountEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entry.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntry, "id.text");
		dataBinder.registerBinding("entry.task.adminPerson.name", String.class, this.kdtEntry, "adminPerson.text");
		dataBinder.registerBinding("adminPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtAdminPerson, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.WorkAmountBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.WorkAmountBillInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.totalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.totalPercent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.percent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.completeDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.confirmAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.confirmPercent", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.task.adminPerson.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adminPerson", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output prmtCreator_willShow method
     */
    protected void prmtCreator_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtBizDept_dataChanged method
     */
    protected void prmtBizDept_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmt_adminPerson_willShow method
     */
    protected void prmt_adminPerson_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("bizDept.*"));
    sic.add(new SelectorItemInfo("entry.totalAmount"));
    sic.add(new SelectorItemInfo("entry.totalPercent"));
    sic.add(new SelectorItemInfo("entry.amount"));
    sic.add(new SelectorItemInfo("entry.percent"));
    sic.add(new SelectorItemInfo("entry.completeDate"));
    sic.add(new SelectorItemInfo("entry.confirmAmount"));
    sic.add(new SelectorItemInfo("entry.confirmPercent"));
    sic.add(new SelectorItemInfo("entry.remark"));
        sic.add(new SelectorItemInfo("entry.*"));
//        sic.add(new SelectorItemInfo("entry.number"));
    sic.add(new SelectorItemInfo("entry.id"));
    sic.add(new SelectorItemInfo("entry.task.adminPerson.name"));
        sic.add(new SelectorItemInfo("adminPerson.*"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionSelected_actionPerformed method
     */
    public void actionSelected_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionSelected(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSelected() {
    	return false;
    }

    /**
     * output ActionSelected class
     */     
    protected class ActionSelected extends ItemAction {     
    
        public ActionSelected()
        {
            this(null);
        }

        public ActionSelected(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSelected.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelected.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSelected.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractWorkAmountBillEditUI.this, "ActionSelected", "actionSelected_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "WorkAmountBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}