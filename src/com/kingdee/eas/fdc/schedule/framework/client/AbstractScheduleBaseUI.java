/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework.client;

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
public abstract class AbstractScheduleBaseUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractScheduleBaseUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDPanel mainPanel;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnZoomLeft;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnZoomCenter;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnZoomRight;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnZoomOut;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnZoomIn;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProperty;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnShowByLevel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCritical;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPert;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBatchModifyTaskType;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemZoomLeft;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemZoomCenter;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemZoomRight;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemZoomOut;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemZoomIn;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemProperty;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemGraphOption;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemCritical;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPERT;
    protected com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo editData = null;
    protected ActionZoomLeft actionZoomLeft = null;
    protected ActionZoomRight actionZoomRight = null;
    protected ActionZoomCenter actionZoomCenter = null;
    protected ActionZoomOut actionZoomOut = null;
    protected ActionZoomIn actionZoomIn = null;
    protected ActionProperty actionProperty = null;
    protected ActionGraphOption actionGraphOption = null;
    protected ActionPert actionPert = null;
    protected ActionCritical actionCritical = null;
    protected ActionShowAllLevels actionShowAllLevels = null;
    protected ActionShowLevel1 actionShowLevel1 = null;
    protected ActionShowLevel2 actionShowLevel2 = null;
    protected ActionShowLevel3 actionShowLevel3 = null;
    protected ActionShowLevel4 actionShowLevel4 = null;
    protected ActionShowLevel5 actionShowLevel5 = null;
    protected ActionShowLevel6 actionShowLevel6 = null;
    protected ActionShowLevel7 actionShowLevel7 = null;
    protected ActionShowLevel8 actionShowLevel8 = null;
    protected ActionShowLevel9 actionShowLevel9 = null;
    protected ActionBatchModifyTaskType actionBatchModifyTaskType = null;
    /**
     * output class constructor
     */
    public AbstractScheduleBaseUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractScheduleBaseUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionZoomLeft
        this.actionZoomLeft = new ActionZoomLeft(this);
        getActionManager().registerAction("actionZoomLeft", actionZoomLeft);
         this.actionZoomLeft.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionZoomRight
        this.actionZoomRight = new ActionZoomRight(this);
        getActionManager().registerAction("actionZoomRight", actionZoomRight);
         this.actionZoomRight.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionZoomCenter
        this.actionZoomCenter = new ActionZoomCenter(this);
        getActionManager().registerAction("actionZoomCenter", actionZoomCenter);
         this.actionZoomCenter.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionZoomOut
        this.actionZoomOut = new ActionZoomOut(this);
        getActionManager().registerAction("actionZoomOut", actionZoomOut);
         this.actionZoomOut.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionZoomIn
        this.actionZoomIn = new ActionZoomIn(this);
        getActionManager().registerAction("actionZoomIn", actionZoomIn);
         this.actionZoomIn.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProperty
        this.actionProperty = new ActionProperty(this);
        getActionManager().registerAction("actionProperty", actionProperty);
         this.actionProperty.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionGraphOption
        this.actionGraphOption = new ActionGraphOption(this);
        getActionManager().registerAction("actionGraphOption", actionGraphOption);
         this.actionGraphOption.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPert
        this.actionPert = new ActionPert(this);
        getActionManager().registerAction("actionPert", actionPert);
         this.actionPert.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCritical
        this.actionCritical = new ActionCritical(this);
        getActionManager().registerAction("actionCritical", actionCritical);
         this.actionCritical.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowAllLevels
        this.actionShowAllLevels = new ActionShowAllLevels(this);
        getActionManager().registerAction("actionShowAllLevels", actionShowAllLevels);
         this.actionShowAllLevels.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowLevel1
        this.actionShowLevel1 = new ActionShowLevel1(this);
        getActionManager().registerAction("actionShowLevel1", actionShowLevel1);
         this.actionShowLevel1.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowLevel2
        this.actionShowLevel2 = new ActionShowLevel2(this);
        getActionManager().registerAction("actionShowLevel2", actionShowLevel2);
         this.actionShowLevel2.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowLevel3
        this.actionShowLevel3 = new ActionShowLevel3(this);
        getActionManager().registerAction("actionShowLevel3", actionShowLevel3);
         this.actionShowLevel3.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowLevel4
        this.actionShowLevel4 = new ActionShowLevel4(this);
        getActionManager().registerAction("actionShowLevel4", actionShowLevel4);
         this.actionShowLevel4.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowLevel5
        this.actionShowLevel5 = new ActionShowLevel5(this);
        getActionManager().registerAction("actionShowLevel5", actionShowLevel5);
         this.actionShowLevel5.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowLevel6
        this.actionShowLevel6 = new ActionShowLevel6(this);
        getActionManager().registerAction("actionShowLevel6", actionShowLevel6);
         this.actionShowLevel6.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowLevel7
        this.actionShowLevel7 = new ActionShowLevel7(this);
        getActionManager().registerAction("actionShowLevel7", actionShowLevel7);
         this.actionShowLevel7.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowLevel8
        this.actionShowLevel8 = new ActionShowLevel8(this);
        getActionManager().registerAction("actionShowLevel8", actionShowLevel8);
         this.actionShowLevel8.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowLevel9
        this.actionShowLevel9 = new ActionShowLevel9(this);
        getActionManager().registerAction("actionShowLevel9", actionShowLevel9);
         this.actionShowLevel9.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBatchModifyTaskType
        this.actionBatchModifyTaskType = new ActionBatchModifyTaskType(this);
        getActionManager().registerAction("actionBatchModifyTaskType", actionBatchModifyTaskType);
         this.actionBatchModifyTaskType.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.mainPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.btnZoomLeft = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnZoomCenter = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnZoomRight = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnZoomOut = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnZoomIn = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnProperty = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnShowByLevel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCritical = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPert = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBatchModifyTaskType = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemZoomLeft = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemZoomCenter = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemZoomRight = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemZoomOut = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemZoomIn = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemProperty = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemGraphOption = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemCritical = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPERT = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.mainPanel.setName("mainPanel");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditTime.setName("pkAuditTime");
        this.btnZoomLeft.setName("btnZoomLeft");
        this.btnZoomCenter.setName("btnZoomCenter");
        this.btnZoomRight.setName("btnZoomRight");
        this.btnZoomOut.setName("btnZoomOut");
        this.btnZoomIn.setName("btnZoomIn");
        this.btnProperty.setName("btnProperty");
        this.btnShowByLevel.setName("btnShowByLevel");
        this.btnCritical.setName("btnCritical");
        this.btnPert.setName("btnPert");
        this.btnBatchModifyTaskType.setName("btnBatchModifyTaskType");
        this.menuItemZoomLeft.setName("menuItemZoomLeft");
        this.menuItemZoomCenter.setName("menuItemZoomCenter");
        this.menuItemZoomRight.setName("menuItemZoomRight");
        this.menuItemZoomOut.setName("menuItemZoomOut");
        this.menuItemZoomIn.setName("menuItemZoomIn");
        this.menuItemProperty.setName("menuItemProperty");
        this.menuItemGraphOption.setName("menuItemGraphOption");
        this.menuItemCritical.setName("menuItemCritical");
        this.menuItemPERT.setName("menuItemPERT");
        // CoreUI		
        this.btnRemove.setVisible(false);		
        this.menuItemRemove.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // mainPanel
        // prmtCreator
        // pkCreateTime
        // prmtAuditor
        // pkAuditTime
        // btnZoomLeft
        this.btnZoomLeft.setAction((IItemAction)ActionProxyFactory.getProxy(actionZoomLeft, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnZoomLeft.setText(resHelper.getString("btnZoomLeft.text"));		
        this.btnZoomLeft.setToolTipText(resHelper.getString("btnZoomLeft.toolTipText"));
        // btnZoomCenter
        this.btnZoomCenter.setAction((IItemAction)ActionProxyFactory.getProxy(actionZoomCenter, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnZoomCenter.setText(resHelper.getString("btnZoomCenter.text"));		
        this.btnZoomCenter.setToolTipText(resHelper.getString("btnZoomCenter.toolTipText"));
        // btnZoomRight
        this.btnZoomRight.setAction((IItemAction)ActionProxyFactory.getProxy(actionZoomRight, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnZoomRight.setText(resHelper.getString("btnZoomRight.text"));		
        this.btnZoomRight.setToolTipText(resHelper.getString("btnZoomRight.toolTipText"));
        // btnZoomOut
        this.btnZoomOut.setAction((IItemAction)ActionProxyFactory.getProxy(actionZoomOut, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnZoomOut.setText(resHelper.getString("btnZoomOut.text"));		
        this.btnZoomOut.setToolTipText(resHelper.getString("btnZoomOut.toolTipText"));
        // btnZoomIn
        this.btnZoomIn.setAction((IItemAction)ActionProxyFactory.getProxy(actionZoomIn, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnZoomIn.setText(resHelper.getString("btnZoomIn.text"));		
        this.btnZoomIn.setToolTipText(resHelper.getString("btnZoomIn.toolTipText"));
        // btnProperty
        this.btnProperty.setAction((IItemAction)ActionProxyFactory.getProxy(actionProperty, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProperty.setText(resHelper.getString("btnProperty.text"));		
        this.btnProperty.setToolTipText(resHelper.getString("btnProperty.toolTipText"));
        // btnShowByLevel		
        this.btnShowByLevel.setText(resHelper.getString("btnShowByLevel.text"));		
        this.btnShowByLevel.setToolTipText(resHelper.getString("btnShowByLevel.toolTipText"));
        // btnCritical
        this.btnCritical.setAction((IItemAction)ActionProxyFactory.getProxy(actionCritical, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCritical.setText(resHelper.getString("btnCritical.text"));		
        this.btnCritical.setToolTipText(resHelper.getString("btnCritical.toolTipText"));
        // btnPert
        this.btnPert.setAction((IItemAction)ActionProxyFactory.getProxy(actionPert, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPert.setText(resHelper.getString("btnPert.text"));		
        this.btnPert.setToolTipText(resHelper.getString("btnPert.toolTipText"));
        // btnBatchModifyTaskType
        this.btnBatchModifyTaskType.setAction((IItemAction)ActionProxyFactory.getProxy(actionBatchModifyTaskType, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBatchModifyTaskType.setText(resHelper.getString("btnBatchModifyTaskType.text"));
        // menuItemZoomLeft
        this.menuItemZoomLeft.setAction((IItemAction)ActionProxyFactory.getProxy(actionZoomLeft, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemZoomLeft.setText(resHelper.getString("menuItemZoomLeft.text"));		
        this.menuItemZoomLeft.setMnemonic(76);
        // menuItemZoomCenter
        this.menuItemZoomCenter.setAction((IItemAction)ActionProxyFactory.getProxy(actionZoomCenter, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemZoomCenter.setText(resHelper.getString("menuItemZoomCenter.text"));		
        this.menuItemZoomCenter.setMnemonic(67);		
        this.menuItemZoomCenter.setToolTipText(resHelper.getString("menuItemZoomCenter.toolTipText"));
        // menuItemZoomRight
        this.menuItemZoomRight.setAction((IItemAction)ActionProxyFactory.getProxy(actionZoomRight, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemZoomRight.setText(resHelper.getString("menuItemZoomRight.text"));		
        this.menuItemZoomRight.setMnemonic(82);		
        this.menuItemZoomRight.setToolTipText(resHelper.getString("menuItemZoomRight.toolTipText"));
        // menuItemZoomOut
        this.menuItemZoomOut.setAction((IItemAction)ActionProxyFactory.getProxy(actionZoomOut, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemZoomOut.setText(resHelper.getString("menuItemZoomOut.text"));		
        this.menuItemZoomOut.setMnemonic(79);		
        this.menuItemZoomOut.setToolTipText(resHelper.getString("menuItemZoomOut.toolTipText"));
        // menuItemZoomIn
        this.menuItemZoomIn.setAction((IItemAction)ActionProxyFactory.getProxy(actionZoomIn, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemZoomIn.setText(resHelper.getString("menuItemZoomIn.text"));		
        this.menuItemZoomIn.setMnemonic(73);		
        this.menuItemZoomIn.setToolTipText(resHelper.getString("menuItemZoomIn.toolTipText"));
        // menuItemProperty
        this.menuItemProperty.setAction((IItemAction)ActionProxyFactory.getProxy(actionProperty, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemProperty.setText(resHelper.getString("menuItemProperty.text"));		
        this.menuItemProperty.setMnemonic(80);		
        this.menuItemProperty.setToolTipText(resHelper.getString("menuItemProperty.toolTipText"));
        // menuItemGraphOption
        this.menuItemGraphOption.setAction((IItemAction)ActionProxyFactory.getProxy(actionGraphOption, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemGraphOption.setText(resHelper.getString("menuItemGraphOption.text"));		
        this.menuItemGraphOption.setMnemonic(79);		
        this.menuItemGraphOption.setToolTipText(resHelper.getString("menuItemGraphOption.toolTipText"));
        // menuItemCritical
        this.menuItemCritical.setAction((IItemAction)ActionProxyFactory.getProxy(actionCritical, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemCritical.setText(resHelper.getString("menuItemCritical.text"));		
        this.menuItemCritical.setMnemonic(67);		
        this.menuItemCritical.setToolTipText(resHelper.getString("menuItemCritical.toolTipText"));
        // menuItemPERT
        this.menuItemPERT.setAction((IItemAction)ActionProxyFactory.getProxy(actionPert, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPERT.setText(resHelper.getString("menuItemPERT.text"));		
        this.menuItemPERT.setToolTipText(resHelper.getString("menuItemPERT.toolTipText"));		
        this.menuItemPERT.setMnemonic(80);
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
        this.setBounds(new Rectangle(10, 10, 800, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 800, 600));
        contCreator.setBounds(new Rectangle(10, 547, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 547, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(344, 547, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(344, 547, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(10, 568, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(10, 568, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(345, 568, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(345, 568, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        mainPanel.setBounds(new Rectangle(12, 44, 778, 499));
        this.add(mainPanel, new KDLayout.Constraints(12, 44, 778, 499, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
mainPanel.setLayout(new BorderLayout(0, 0));
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
        menuView.add(menuItemZoomLeft);
        menuView.add(menuItemZoomCenter);
        menuView.add(menuItemZoomRight);
        menuView.add(menuItemZoomOut);
        menuView.add(menuItemZoomIn);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemProperty);
        menuBiz.add(menuItemGraphOption);
        menuBiz.add(menuItemCritical);
        menuBiz.add(menuItemPERT);
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
        this.toolBar.add(btnZoomLeft);
        this.toolBar.add(btnZoomCenter);
        this.toolBar.add(btnZoomRight);
        this.toolBar.add(btnZoomOut);
        this.toolBar.add(btnZoomIn);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
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
        this.toolBar.add(btnProperty);
        this.toolBar.add(btnShowByLevel);
        this.toolBar.add(btnCritical);
        this.toolBar.add(btnPert);
        this.toolBar.add(btnBatchModifyTaskType);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.framework.app.ScheduleBaseUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo)ov;
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
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        return sic;
    }        
    	

    /**
     * output actionZoomLeft_actionPerformed method
     */
    public void actionZoomLeft_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionZoomRight_actionPerformed method
     */
    public void actionZoomRight_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionZoomCenter_actionPerformed method
     */
    public void actionZoomCenter_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionZoomOut_actionPerformed method
     */
    public void actionZoomOut_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionZoomIn_actionPerformed method
     */
    public void actionZoomIn_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionProperty_actionPerformed method
     */
    public void actionProperty_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionGraphOption_actionPerformed method
     */
    public void actionGraphOption_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPert_actionPerformed method
     */
    public void actionPert_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCritical_actionPerformed method
     */
    public void actionCritical_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowAllLevels_actionPerformed method
     */
    public void actionShowAllLevels_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowLevel1_actionPerformed method
     */
    public void actionShowLevel1_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowLevel2_actionPerformed method
     */
    public void actionShowLevel2_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowLevel3_actionPerformed method
     */
    public void actionShowLevel3_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowLevel4_actionPerformed method
     */
    public void actionShowLevel4_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowLevel5_actionPerformed method
     */
    public void actionShowLevel5_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowLevel6_actionPerformed method
     */
    public void actionShowLevel6_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowLevel7_actionPerformed method
     */
    public void actionShowLevel7_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowLevel8_actionPerformed method
     */
    public void actionShowLevel8_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowLevel9_actionPerformed method
     */
    public void actionShowLevel9_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBatchModifyTaskType_actionPerformed method
     */
    public void actionBatchModifyTaskType_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionZoomLeft(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionZoomLeft() {
    	return false;
    }
	public RequestContext prepareActionZoomRight(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionZoomRight() {
    	return false;
    }
	public RequestContext prepareActionZoomCenter(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionZoomCenter() {
    	return false;
    }
	public RequestContext prepareActionZoomOut(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionZoomOut() {
    	return false;
    }
	public RequestContext prepareActionZoomIn(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionZoomIn() {
    	return false;
    }
	public RequestContext prepareActionProperty(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProperty() {
    	return false;
    }
	public RequestContext prepareActionGraphOption(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionGraphOption() {
    	return false;
    }
	public RequestContext prepareActionPert(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPert() {
    	return false;
    }
	public RequestContext prepareActionCritical(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCritical() {
    	return false;
    }
	public RequestContext prepareActionShowAllLevels(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowAllLevels() {
    	return false;
    }
	public RequestContext prepareActionShowLevel1(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowLevel1() {
    	return false;
    }
	public RequestContext prepareActionShowLevel2(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowLevel2() {
    	return false;
    }
	public RequestContext prepareActionShowLevel3(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowLevel3() {
    	return false;
    }
	public RequestContext prepareActionShowLevel4(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowLevel4() {
    	return false;
    }
	public RequestContext prepareActionShowLevel5(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowLevel5() {
    	return false;
    }
	public RequestContext prepareActionShowLevel6(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowLevel6() {
    	return false;
    }
	public RequestContext prepareActionShowLevel7(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowLevel7() {
    	return false;
    }
	public RequestContext prepareActionShowLevel8(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowLevel8() {
    	return false;
    }
	public RequestContext prepareActionShowLevel9(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowLevel9() {
    	return false;
    }
	public RequestContext prepareActionBatchModifyTaskType(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBatchModifyTaskType() {
    	return false;
    }

    /**
     * output ActionZoomLeft class
     */     
    protected class ActionZoomLeft extends ItemAction {     
    
        public ActionZoomLeft()
        {
            this(null);
        }

        public ActionZoomLeft(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionZoomLeft.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionZoomLeft.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionZoomLeft.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionZoomLeft", "actionZoomLeft_actionPerformed", e);
        }
    }

    /**
     * output ActionZoomRight class
     */     
    protected class ActionZoomRight extends ItemAction {     
    
        public ActionZoomRight()
        {
            this(null);
        }

        public ActionZoomRight(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionZoomRight.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionZoomRight.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionZoomRight.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionZoomRight", "actionZoomRight_actionPerformed", e);
        }
    }

    /**
     * output ActionZoomCenter class
     */     
    protected class ActionZoomCenter extends ItemAction {     
    
        public ActionZoomCenter()
        {
            this(null);
        }

        public ActionZoomCenter(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionZoomCenter.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionZoomCenter.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionZoomCenter.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionZoomCenter", "actionZoomCenter_actionPerformed", e);
        }
    }

    /**
     * output ActionZoomOut class
     */     
    protected class ActionZoomOut extends ItemAction {     
    
        public ActionZoomOut()
        {
            this(null);
        }

        public ActionZoomOut(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionZoomOut.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionZoomOut.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionZoomOut.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionZoomOut", "actionZoomOut_actionPerformed", e);
        }
    }

    /**
     * output ActionZoomIn class
     */     
    protected class ActionZoomIn extends ItemAction {     
    
        public ActionZoomIn()
        {
            this(null);
        }

        public ActionZoomIn(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionZoomIn.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionZoomIn.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionZoomIn.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionZoomIn", "actionZoomIn_actionPerformed", e);
        }
    }

    /**
     * output ActionProperty class
     */     
    protected class ActionProperty extends ItemAction {     
    
        public ActionProperty()
        {
            this(null);
        }

        public ActionProperty(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionProperty.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProperty.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProperty.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionProperty", "actionProperty_actionPerformed", e);
        }
    }

    /**
     * output ActionGraphOption class
     */     
    protected class ActionGraphOption extends ItemAction {     
    
        public ActionGraphOption()
        {
            this(null);
        }

        public ActionGraphOption(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionGraphOption.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionGraphOption.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionGraphOption.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionGraphOption", "actionGraphOption_actionPerformed", e);
        }
    }

    /**
     * output ActionPert class
     */     
    protected class ActionPert extends ItemAction {     
    
        public ActionPert()
        {
            this(null);
        }

        public ActionPert(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPert.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPert.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPert.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionPert", "actionPert_actionPerformed", e);
        }
    }

    /**
     * output ActionCritical class
     */     
    protected class ActionCritical extends ItemAction {     
    
        public ActionCritical()
        {
            this(null);
        }

        public ActionCritical(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCritical.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCritical.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCritical.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionCritical", "actionCritical_actionPerformed", e);
        }
    }

    /**
     * output ActionShowAllLevels class
     */     
    protected class ActionShowAllLevels extends ItemAction {     
    
        public ActionShowAllLevels()
        {
            this(null);
        }

        public ActionShowAllLevels(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionShowAllLevels.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowAllLevels.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowAllLevels.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionShowAllLevels", "actionShowAllLevels_actionPerformed", e);
        }
    }

    /**
     * output ActionShowLevel1 class
     */     
    protected class ActionShowLevel1 extends ItemAction {     
    
        public ActionShowLevel1()
        {
            this(null);
        }

        public ActionShowLevel1(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionShowLevel1.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel1.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel1.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionShowLevel1", "actionShowLevel1_actionPerformed", e);
        }
    }

    /**
     * output ActionShowLevel2 class
     */     
    protected class ActionShowLevel2 extends ItemAction {     
    
        public ActionShowLevel2()
        {
            this(null);
        }

        public ActionShowLevel2(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionShowLevel2.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel2.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel2.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionShowLevel2", "actionShowLevel2_actionPerformed", e);
        }
    }

    /**
     * output ActionShowLevel3 class
     */     
    protected class ActionShowLevel3 extends ItemAction {     
    
        public ActionShowLevel3()
        {
            this(null);
        }

        public ActionShowLevel3(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionShowLevel3.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel3.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel3.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionShowLevel3", "actionShowLevel3_actionPerformed", e);
        }
    }

    /**
     * output ActionShowLevel4 class
     */     
    protected class ActionShowLevel4 extends ItemAction {     
    
        public ActionShowLevel4()
        {
            this(null);
        }

        public ActionShowLevel4(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionShowLevel4.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel4.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel4.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionShowLevel4", "actionShowLevel4_actionPerformed", e);
        }
    }

    /**
     * output ActionShowLevel5 class
     */     
    protected class ActionShowLevel5 extends ItemAction {     
    
        public ActionShowLevel5()
        {
            this(null);
        }

        public ActionShowLevel5(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionShowLevel5.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel5.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel5.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionShowLevel5", "actionShowLevel5_actionPerformed", e);
        }
    }

    /**
     * output ActionShowLevel6 class
     */     
    protected class ActionShowLevel6 extends ItemAction {     
    
        public ActionShowLevel6()
        {
            this(null);
        }

        public ActionShowLevel6(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionShowLevel6.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel6.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel6.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionShowLevel6", "actionShowLevel6_actionPerformed", e);
        }
    }

    /**
     * output ActionShowLevel7 class
     */     
    protected class ActionShowLevel7 extends ItemAction {     
    
        public ActionShowLevel7()
        {
            this(null);
        }

        public ActionShowLevel7(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionShowLevel7.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel7.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel7.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionShowLevel7", "actionShowLevel7_actionPerformed", e);
        }
    }

    /**
     * output ActionShowLevel8 class
     */     
    protected class ActionShowLevel8 extends ItemAction {     
    
        public ActionShowLevel8()
        {
            this(null);
        }

        public ActionShowLevel8(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionShowLevel8.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel8.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel8.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionShowLevel8", "actionShowLevel8_actionPerformed", e);
        }
    }

    /**
     * output ActionShowLevel9 class
     */     
    protected class ActionShowLevel9 extends ItemAction {     
    
        public ActionShowLevel9()
        {
            this(null);
        }

        public ActionShowLevel9(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionShowLevel9.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel9.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowLevel9.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionShowLevel9", "actionShowLevel9_actionPerformed", e);
        }
    }

    /**
     * output ActionBatchModifyTaskType class
     */     
    protected class ActionBatchModifyTaskType extends ItemAction {     
    
        public ActionBatchModifyTaskType()
        {
            this(null);
        }

        public ActionBatchModifyTaskType(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBatchModifyTaskType.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchModifyTaskType.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBatchModifyTaskType.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleBaseUI.this, "ActionBatchModifyTaskType", "actionBatchModifyTaskType_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.framework.client", "ScheduleBaseUI");
    }




}