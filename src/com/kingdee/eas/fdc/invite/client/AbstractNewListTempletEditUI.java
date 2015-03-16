/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

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
public abstract class AbstractNewListTempletEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractNewListTempletEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane tabbedPnlTable;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChoosePage;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChooseColumn;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChoosePage;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChooseColumn;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUpLevel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDownLevel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUpLevel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemDownLevel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAllSelect;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnNoneSelect;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAllSelect;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemNoneSelect;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportExcel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuImportExcel;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCutLine;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPasteLine;
    protected com.kingdee.eas.fdc.invite.NewListTempletInfo editData = null;
    protected ActionChoosePage actionChoosePage = null;
    protected ActionChooseColumn actionChooseColumn = null;
    protected ActionPasteLine actionPasteLine = null;
    protected ActionCutLine actionCutLine = null;
    protected ActionUpLine actionUpLine = null;
    protected ActionDownLine actionDownLine = null;
    protected ActionAllSelect actionAllSelect = null;
    protected ActionNoneSelect actionNoneSelect = null;
    protected ActionImportExcel actionImportExcel = null;
    /**
     * output class constructor
     */
    public AbstractNewListTempletEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractNewListTempletEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionCopyLine
        String _tempStr = null;
        actionCopyLine.setEnabled(true);
        actionCopyLine.setDaemonRun(false);

        actionCopyLine.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl O"));
        _tempStr = resHelper.getString("actionCopyLine.SHORT_DESCRIPTION");
        actionCopyLine.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionCopyLine.LONG_DESCRIPTION");
        actionCopyLine.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("actionCopyLine.NAME");
        actionCopyLine.putValue(ItemAction.NAME, _tempStr);
         this.actionCopyLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveLine
        actionRemoveLine.setEnabled(true);
        actionRemoveLine.setDaemonRun(false);

        actionRemoveLine.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift D"));
        _tempStr = resHelper.getString("ActionRemoveLine.SHORT_DESCRIPTION");
        actionRemoveLine.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemoveLine.LONG_DESCRIPTION");
        actionRemoveLine.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemoveLine.NAME");
        actionRemoveLine.putValue(ItemAction.NAME, _tempStr);
         this.actionRemoveLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRemoveLine.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRemoveLine.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionChoosePage
        this.actionChoosePage = new ActionChoosePage(this);
        getActionManager().registerAction("actionChoosePage", actionChoosePage);
         this.actionChoosePage.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionChooseColumn
        this.actionChooseColumn = new ActionChooseColumn(this);
        getActionManager().registerAction("actionChooseColumn", actionChooseColumn);
         this.actionChooseColumn.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPasteLine
        this.actionPasteLine = new ActionPasteLine(this);
        getActionManager().registerAction("actionPasteLine", actionPasteLine);
         this.actionPasteLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCutLine
        this.actionCutLine = new ActionCutLine(this);
        getActionManager().registerAction("actionCutLine", actionCutLine);
         this.actionCutLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUpLine
        this.actionUpLine = new ActionUpLine(this);
        getActionManager().registerAction("actionUpLine", actionUpLine);
         this.actionUpLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDownLine
        this.actionDownLine = new ActionDownLine(this);
        getActionManager().registerAction("actionDownLine", actionDownLine);
         this.actionDownLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAllSelect
        this.actionAllSelect = new ActionAllSelect(this);
        getActionManager().registerAction("actionAllSelect", actionAllSelect);
         this.actionAllSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionNoneSelect
        this.actionNoneSelect = new ActionNoneSelect(this);
        getActionManager().registerAction("actionNoneSelect", actionNoneSelect);
         this.actionNoneSelect.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionImportExcel
        this.actionImportExcel = new ActionImportExcel(this);
        getActionManager().registerAction("actionImportExcel", actionImportExcel);
         this.actionImportExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contInviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtInviteType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.tabbedPnlTable = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.btnChoosePage = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnChooseColumn = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemChoosePage = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemChooseColumn = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnUpLevel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDownLevel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemUpLevel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemDownLevel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnAllSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnNoneSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemAllSelect = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemNoneSelect = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.btnImportExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuImportExcel = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCutLine = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPasteLine = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contName.setName("contName");
        this.txtName.setName("txtName");
        this.contInviteType.setName("contInviteType");
        this.prmtInviteType.setName("prmtInviteType");
        this.contDescription.setName("contDescription");
        this.txtDescription.setName("txtDescription");
        this.tabbedPnlTable.setName("tabbedPnlTable");
        this.btnChoosePage.setName("btnChoosePage");
        this.btnChooseColumn.setName("btnChooseColumn");
        this.menuItemChoosePage.setName("menuItemChoosePage");
        this.menuItemChooseColumn.setName("menuItemChooseColumn");
        this.btnUpLevel.setName("btnUpLevel");
        this.btnDownLevel.setName("btnDownLevel");
        this.menuItemUpLevel.setName("menuItemUpLevel");
        this.menuItemDownLevel.setName("menuItemDownLevel");
        this.btnAllSelect.setName("btnAllSelect");
        this.btnNoneSelect.setName("btnNoneSelect");
        this.menuItemAllSelect.setName("menuItemAllSelect");
        this.menuItemNoneSelect.setName("menuItemNoneSelect");
        this.btnImportExcel.setName("btnImportExcel");
        this.menuImportExcel.setName("menuImportExcel");
        this.menuItemCutLine.setName("menuItemCutLine");
        this.menuItemPasteLine.setName("menuItemPasteLine");
        // CoreUI		
        this.menuEdit.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);
        this.menuItemCopyLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionCopyLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCopyLine.setText(resHelper.getString("menuItemCopyLine.text"));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);
        // contInviteType		
        this.contInviteType.setBoundLabelText(resHelper.getString("contInviteType.boundLabelText"));		
        this.contInviteType.setBoundLabelLength(100);		
        this.contInviteType.setBoundLabelUnderline(true);
        // prmtInviteType		
        this.prmtInviteType.setDisplayFormat("$name$");		
        this.prmtInviteType.setEditFormat("$number$");		
        this.prmtInviteType.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteTypeQuery");		
        this.prmtInviteType.setCommitFormat("$number$");		
        this.prmtInviteType.setRequired(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // tabbedPnlTable
        // btnChoosePage
        this.btnChoosePage.setAction((IItemAction)ActionProxyFactory.getProxy(actionChoosePage, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChoosePage.setText(resHelper.getString("btnChoosePage.text"));		
        this.btnChoosePage.setToolTipText(resHelper.getString("btnChoosePage.toolTipText"));		
        this.btnChoosePage.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_tabchoose"));
        // btnChooseColumn
        this.btnChooseColumn.setAction((IItemAction)ActionProxyFactory.getProxy(actionChooseColumn, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnChooseColumn.setText(resHelper.getString("btnChooseColumn.text"));		
        this.btnChooseColumn.setToolTipText(resHelper.getString("btnChooseColumn.toolTipText"));		
        this.btnChooseColumn.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_organiselist"));
        // menuItemChoosePage
        this.menuItemChoosePage.setAction((IItemAction)ActionProxyFactory.getProxy(actionChoosePage, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemChoosePage.setText(resHelper.getString("menuItemChoosePage.text"));		
        this.menuItemChoosePage.setToolTipText(resHelper.getString("menuItemChoosePage.toolTipText"));		
        this.menuItemChoosePage.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_tabchoose"));
        // menuItemChooseColumn
        this.menuItemChooseColumn.setAction((IItemAction)ActionProxyFactory.getProxy(actionChooseColumn, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemChooseColumn.setText(resHelper.getString("menuItemChooseColumn.text"));		
        this.menuItemChooseColumn.setToolTipText(resHelper.getString("menuItemChooseColumn.toolTipText"));		
        this.menuItemChooseColumn.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_organiselist"));
        // btnUpLevel
        this.btnUpLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUpLevel.setText(resHelper.getString("btnUpLevel.text"));		
        this.btnUpLevel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_upgrade"));		
        this.btnUpLevel.setToolTipText(resHelper.getString("btnUpLevel.toolTipText"));
        // btnDownLevel
        this.btnDownLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionDownLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDownLevel.setText(resHelper.getString("btnDownLevel.text"));		
        this.btnDownLevel.setToolTipText(resHelper.getString("btnDownLevel.toolTipText"));		
        this.btnDownLevel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_degrade"));
        // menuItemUpLevel
        this.menuItemUpLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUpLevel.setText(resHelper.getString("menuItemUpLevel.text"));		
        this.menuItemUpLevel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_upgrade"));		
        this.menuItemUpLevel.setToolTipText(resHelper.getString("menuItemUpLevel.toolTipText"));
        // menuItemDownLevel
        this.menuItemDownLevel.setAction((IItemAction)ActionProxyFactory.getProxy(actionDownLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemDownLevel.setText(resHelper.getString("menuItemDownLevel.text"));		
        this.menuItemDownLevel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_degrade"));		
        this.menuItemDownLevel.setToolTipText(resHelper.getString("menuItemDownLevel.toolTipText"));
        // btnAllSelect
        this.btnAllSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionAllSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAllSelect.setText(resHelper.getString("btnAllSelect.text"));		
        this.btnAllSelect.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_selectall"));		
        this.btnAllSelect.setToolTipText(resHelper.getString("btnAllSelect.toolTipText"));
        // btnNoneSelect
        this.btnNoneSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionNoneSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnNoneSelect.setText(resHelper.getString("btnNoneSelect.text"));		
        this.btnNoneSelect.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteall"));		
        this.btnNoneSelect.setToolTipText(resHelper.getString("btnNoneSelect.toolTipText"));
        // menuItemAllSelect
        this.menuItemAllSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionAllSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAllSelect.setText(resHelper.getString("menuItemAllSelect.text"));		
        this.menuItemAllSelect.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_selectall"));		
        this.menuItemAllSelect.setToolTipText(resHelper.getString("menuItemAllSelect.toolTipText"));
        // menuItemNoneSelect
        this.menuItemNoneSelect.setAction((IItemAction)ActionProxyFactory.getProxy(actionNoneSelect, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemNoneSelect.setText(resHelper.getString("menuItemNoneSelect.text"));		
        this.menuItemNoneSelect.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteall"));		
        this.menuItemNoneSelect.setToolTipText(resHelper.getString("menuItemNoneSelect.toolTipText"));
        // btnImportExcel
        this.btnImportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportExcel.setText(resHelper.getString("btnImportExcel.text"));		
        this.btnImportExcel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));
        // menuImportExcel
        this.menuImportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuImportExcel.setText(resHelper.getString("menuImportExcel.text"));		
        this.menuImportExcel.setSelectedIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));
        // menuItemCutLine
        this.menuItemCutLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionCutLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCutLine.setText(resHelper.getString("menuItemCutLine.text"));
        // menuItemPasteLine
        this.menuItemPasteLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionPasteLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPasteLine.setText(resHelper.getString("menuItemPasteLine.text"));
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
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(371, 10, 270, 19));
        this.add(contName, new KDLayout.Constraints(371, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteType.setBounds(new Rectangle(733, 10, 270, 19));
        this.add(contInviteType, new KDLayout.Constraints(733, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(10, 36, 993, 19));
        this.add(contDescription, new KDLayout.Constraints(10, 36, 993, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        tabbedPnlTable.setBounds(new Rectangle(10, 65, 993, 551));
        this.add(tabbedPnlTable, new KDLayout.Constraints(10, 65, 993, 551, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contInviteType
        contInviteType.setBoundEditor(prmtInviteType);
        //contDescription
        contDescription.setBoundEditor(txtDescription);

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
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
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
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        menuEdit.add(menuItemEnterToNextRow);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemChoosePage);
        menuBiz.add(menuItemChooseColumn);
        menuBiz.add(menuItemUpLevel);
        menuBiz.add(menuItemDownLevel);
        menuBiz.add(menuItemAllSelect);
        menuBiz.add(menuItemNoneSelect);
        menuBiz.add(menuImportExcel);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        menuTable1.add(menuItemCutLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemPasteLine);
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
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
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
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(btnUpLevel);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnDownLevel);
        this.toolBar.add(btnAllSelect);
        this.toolBar.add(btnNoneSelect);
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
        this.toolBar.add(btnChoosePage);
        this.toolBar.add(btnChooseColumn);
        this.toolBar.add(btnImportExcel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("inviteType", com.kingdee.eas.fdc.invite.InviteTypeInfo.class, this.prmtInviteType, "data");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.NewListTempletEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.NewListTempletInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    		
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("inviteType.*"));
        sic.add(new SelectorItemInfo("description"));
        return sic;
    }        
    	

    /**
     * output actionCopyLine_actionPerformed method
     */
    public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyLine_actionPerformed(e);
    }
    	

    /**
     * output actionRemoveLine_actionPerformed method
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoveLine_actionPerformed(e);
    }
    	

    /**
     * output actionChoosePage_actionPerformed method
     */
    public void actionChoosePage_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionChooseColumn_actionPerformed method
     */
    public void actionChooseColumn_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPasteLine_actionPerformed method
     */
    public void actionPasteLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCutLine_actionPerformed method
     */
    public void actionCutLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUpLine_actionPerformed method
     */
    public void actionUpLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDownLine_actionPerformed method
     */
    public void actionDownLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAllSelect_actionPerformed method
     */
    public void actionAllSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionNoneSelect_actionPerformed method
     */
    public void actionNoneSelect_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionImportExcel_actionPerformed method
     */
    public void actionImportExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareactionCopyLine(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareactionCopyLine(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionCopyLine() {
    	return false;
    }
	public RequestContext prepareActionRemoveLine(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRemoveLine(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemoveLine() {
    	return false;
    }
	public RequestContext prepareActionChoosePage(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChoosePage() {
    	return false;
    }
	public RequestContext prepareActionChooseColumn(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionChooseColumn() {
    	return false;
    }
	public RequestContext prepareActionPasteLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPasteLine() {
    	return false;
    }
	public RequestContext prepareActionCutLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCutLine() {
    	return false;
    }
	public RequestContext prepareActionUpLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpLine() {
    	return false;
    }
	public RequestContext prepareActionDownLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDownLine() {
    	return false;
    }
	public RequestContext prepareActionAllSelect(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAllSelect() {
    	return false;
    }
	public RequestContext prepareActionNoneSelect(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionNoneSelect() {
    	return false;
    }
	public RequestContext prepareActionImportExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportExcel() {
    	return false;
    }

    /**
     * output ActionChoosePage class
     */     
    protected class ActionChoosePage extends ItemAction {     
    
        public ActionChoosePage()
        {
            this(null);
        }

        public ActionChoosePage(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl G"));
            _tempStr = resHelper.getString("ActionChoosePage.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChoosePage.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChoosePage.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListTempletEditUI.this, "ActionChoosePage", "actionChoosePage_actionPerformed", e);
        }
    }

    /**
     * output ActionChooseColumn class
     */     
    protected class ActionChooseColumn extends ItemAction {     
    
        public ActionChooseColumn()
        {
            this(null);
        }

        public ActionChooseColumn(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl C"));
            _tempStr = resHelper.getString("ActionChooseColumn.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChooseColumn.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionChooseColumn.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListTempletEditUI.this, "ActionChooseColumn", "actionChooseColumn_actionPerformed", e);
        }
    }

    /**
     * output ActionPasteLine class
     */     
    protected class ActionPasteLine extends ItemAction {     
    
        public ActionPasteLine()
        {
            this(null);
        }

        public ActionPasteLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl F"));
            _tempStr = resHelper.getString("ActionPasteLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPasteLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPasteLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListTempletEditUI.this, "ActionPasteLine", "actionPasteLine_actionPerformed", e);
        }
    }

    /**
     * output ActionCutLine class
     */     
    protected class ActionCutLine extends ItemAction {     
    
        public ActionCutLine()
        {
            this(null);
        }

        public ActionCutLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl M"));
            _tempStr = resHelper.getString("ActionCutLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCutLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCutLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListTempletEditUI.this, "ActionCutLine", "actionCutLine_actionPerformed", e);
        }
    }

    /**
     * output ActionUpLine class
     */     
    protected class ActionUpLine extends ItemAction {     
    
        public ActionUpLine()
        {
            this(null);
        }

        public ActionUpLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl Q"));
            _tempStr = resHelper.getString("ActionUpLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListTempletEditUI.this, "ActionUpLine", "actionUpLine_actionPerformed", e);
        }
    }

    /**
     * output ActionDownLine class
     */     
    protected class ActionDownLine extends ItemAction {     
    
        public ActionDownLine()
        {
            this(null);
        }

        public ActionDownLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl V"));
            _tempStr = resHelper.getString("ActionDownLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDownLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDownLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListTempletEditUI.this, "ActionDownLine", "actionDownLine_actionPerformed", e);
        }
    }

    /**
     * output ActionAllSelect class
     */     
    protected class ActionAllSelect extends ItemAction {     
    
        public ActionAllSelect()
        {
            this(null);
        }

        public ActionAllSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl F"));
            _tempStr = resHelper.getString("ActionAllSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAllSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListTempletEditUI.this, "ActionAllSelect", "actionAllSelect_actionPerformed", e);
        }
    }

    /**
     * output ActionNoneSelect class
     */     
    protected class ActionNoneSelect extends ItemAction {     
    
        public ActionNoneSelect()
        {
            this(null);
        }

        public ActionNoneSelect(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl L"));
            _tempStr = resHelper.getString("ActionNoneSelect.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNoneSelect.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionNoneSelect.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListTempletEditUI.this, "ActionNoneSelect", "actionNoneSelect_actionPerformed", e);
        }
    }

    /**
     * output ActionImportExcel class
     */     
    protected class ActionImportExcel extends ItemAction {     
    
        public ActionImportExcel()
        {
            this(null);
        }

        public ActionImportExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl L"));
            _tempStr = resHelper.getString("ActionImportExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractNewListTempletEditUI.this, "ActionImportExcel", "actionImportExcel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "NewListTempletEditUI");
    }




}