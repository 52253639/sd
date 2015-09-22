/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
public abstract class AbstractContractBillListUI extends com.kingdee.eas.fdc.contract.client.ContractListBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractBillListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProgram;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnProgram;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEditContractType;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEditAuditDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEditPerson;
    protected javax.swing.JToolBar.Separator separator3;
    protected javax.swing.JToolBar.Separator separator2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProjectAttachment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContent;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddContent;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnConMove;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewContract;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnStore;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAntiStore;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemProjectAttachment;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAddContent;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewContent;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemConMove;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemStore;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAntiStore;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemProgram;
    protected javax.swing.JPopupMenu.Separator separator1;
    protected ActionProjectAttachment actionProjectAttachment = null;
    protected ActionViewContent actionViewContent = null;
    protected ActionAddContent actionAddContent = null;
    protected ActionStore actionStore = null;
    protected ActionConMove actionConMove = null;
    protected ActionViewContract actionViewContract = null;
    protected ActionAntiStore actionAntiStore = null;
    protected ActionProgram actionProgram = null;
    protected ActionHeYue actionHeYue = null;
    protected ActionEditPerson actionEditPerson = null;
    protected ActionUnProgram actionUnProgram = null;
    protected ActionEditAuditDate actionEditAuditDate = null;
    protected ActionEditContractType actionEditContractType = null;
    /**
     * output class constructor
     */
    public AbstractContractBillListUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractBillListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ContractBillQuery");
        //actionRemove
        String _tempStr = null;
        actionRemove.setEnabled(true);
        actionRemove.setDaemonRun(false);

        actionRemove.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        _tempStr = resHelper.getString("ActionRemove.SHORT_DESCRIPTION");
        actionRemove.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.LONG_DESCRIPTION");
        actionRemove.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionRemove.NAME");
        actionRemove.putValue(ItemAction.NAME, _tempStr);
        this.actionRemove.setBindWorkFlow(true);
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionRemove.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionCancel
        actionCancel.setEnabled(true);
        actionCancel.setDaemonRun(false);

        actionCancel.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl -"));
        _tempStr = resHelper.getString("ActionCancel.SHORT_DESCRIPTION");
        actionCancel.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancel.LONG_DESCRIPTION");
        actionCancel.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancel.NAME");
        actionCancel.putValue(ItemAction.NAME, _tempStr);
         this.actionCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCancelCancel
        actionCancelCancel.setEnabled(false);
        actionCancelCancel.setDaemonRun(false);

        actionCancelCancel.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl +"));
        _tempStr = resHelper.getString("ActionCancelCancel.SHORT_DESCRIPTION");
        actionCancelCancel.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancelCancel.LONG_DESCRIPTION");
        actionCancelCancel.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionCancelCancel.NAME");
        actionCancelCancel.putValue(ItemAction.NAME, _tempStr);
         this.actionCancelCancel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAudit
        actionAudit.setEnabled(true);
        actionAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
        actionAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
        actionAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAudit.NAME");
        actionAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionAudit.setBindWorkFlow(true);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        actionUnAudit.setEnabled(true);
        actionUnAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProjectAttachment
        this.actionProjectAttachment = new ActionProjectAttachment(this);
        getActionManager().registerAction("actionProjectAttachment", actionProjectAttachment);
         this.actionProjectAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContent
        this.actionViewContent = new ActionViewContent(this);
        getActionManager().registerAction("actionViewContent", actionViewContent);
         this.actionViewContent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddContent
        this.actionAddContent = new ActionAddContent(this);
        getActionManager().registerAction("actionAddContent", actionAddContent);
         this.actionAddContent.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionStore
        this.actionStore = new ActionStore(this);
        getActionManager().registerAction("actionStore", actionStore);
         this.actionStore.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionConMove
        this.actionConMove = new ActionConMove(this);
        getActionManager().registerAction("actionConMove", actionConMove);
         this.actionConMove.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContract
        this.actionViewContract = new ActionViewContract(this);
        getActionManager().registerAction("actionViewContract", actionViewContract);
         this.actionViewContract.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAntiStore
        this.actionAntiStore = new ActionAntiStore(this);
        getActionManager().registerAction("actionAntiStore", actionAntiStore);
         this.actionAntiStore.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProgram
        this.actionProgram = new ActionProgram(this);
        getActionManager().registerAction("actionProgram", actionProgram);
         this.actionProgram.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionHeYue
        this.actionHeYue = new ActionHeYue(this);
        getActionManager().registerAction("actionHeYue", actionHeYue);
         this.actionHeYue.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEditPerson
        this.actionEditPerson = new ActionEditPerson(this);
        getActionManager().registerAction("actionEditPerson", actionEditPerson);
         this.actionEditPerson.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnProgram
        this.actionUnProgram = new ActionUnProgram(this);
        getActionManager().registerAction("actionUnProgram", actionUnProgram);
         this.actionUnProgram.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEditAuditDate
        this.actionEditAuditDate = new ActionEditAuditDate(this);
        getActionManager().registerAction("actionEditAuditDate", actionEditAuditDate);
         this.actionEditAuditDate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEditContractType
        this.actionEditContractType = new ActionEditContractType(this);
        getActionManager().registerAction("actionEditContractType", actionEditContractType);
         this.actionEditContractType.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnProgram = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnProgram = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEditContractType = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEditAuditDate = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnEditPerson = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.separator3 = new javax.swing.JToolBar.Separator();
        this.separator2 = new javax.swing.JToolBar.Separator();
        this.btnProjectAttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewContent = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddContent = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnConMove = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnStore = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAntiStore = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemProjectAttachment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAddContent = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewContent = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemViewContract = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemConMove = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemStore = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemAntiStore = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemProgram = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.separator1 = new javax.swing.JPopupMenu.Separator();
        this.btnProgram.setName("btnProgram");
        this.btnUnProgram.setName("btnUnProgram");
        this.btnEditContractType.setName("btnEditContractType");
        this.btnEditAuditDate.setName("btnEditAuditDate");
        this.btnEditPerson.setName("btnEditPerson");
        this.separator3.setName("separator3");
        this.separator2.setName("separator2");
        this.btnProjectAttachment.setName("btnProjectAttachment");
        this.btnViewContent.setName("btnViewContent");
        this.btnAddContent.setName("btnAddContent");
        this.btnConMove.setName("btnConMove");
        this.btnViewContract.setName("btnViewContract");
        this.btnStore.setName("btnStore");
        this.btnAntiStore.setName("btnAntiStore");
        this.menuItemProjectAttachment.setName("menuItemProjectAttachment");
        this.menuItemAddContent.setName("menuItemAddContent");
        this.menuItemViewContent.setName("menuItemViewContent");
        this.menuItemViewContract.setName("menuItemViewContract");
        this.menuItemConMove.setName("menuItemConMove");
        this.menuItemStore.setName("menuItemStore");
        this.menuItemAntiStore.setName("menuItemAntiStore");
        this.menuItemProgram.setName("menuItemProgram");
        this.separator1.setName("separator1");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol26\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol27\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol31\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol32\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"bookedDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"period\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"state\" t:width=\"45\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"hasSettle\" t:width=\"50\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"contractType.name\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"number\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"attachment\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"content\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"contract.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"mainContractNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"currency.name\" t:width=\"55\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"srcAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"originalAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"exRate\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"amount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"partB.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"contractSource\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"signDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"respPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"respDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"landDeveloper.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"partC.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"costProperty\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"contractPropert\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"curProject.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /><t:Column t:key=\"curProject.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"isArchived\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" /><t:Column t:key=\"orgUnit.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" /><t:Column t:key=\"curProject.displayName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" /><t:Column t:key=\"currency.id\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" t:styleID=\"sCol31\" /><t:Column t:key=\"currency.precision\" t:width=\"0\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" t:styleID=\"sCol32\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" /><t:Column t:key=\"auditTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" /><t:Column t:key=\"creator.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" /><t:Column t:key=\"isRespite\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{bookedDate}</t:Cell><t:Cell>$Resource{period}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{hasSettle}</t:Cell><t:Cell>$Resource{contractType.name}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{attachment}</t:Cell><t:Cell>$Resource{content}</t:Cell><t:Cell>$Resource{contract.name}</t:Cell><t:Cell>$Resource{mainContractNumber}</t:Cell><t:Cell>$Resource{currency.name}</t:Cell><t:Cell>$Resource{srcAmount}</t:Cell><t:Cell>$Resource{originalAmount}</t:Cell><t:Cell>$Resource{exRate}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{partB.name}</t:Cell><t:Cell>$Resource{contractSource}</t:Cell><t:Cell>$Resource{signDate}</t:Cell><t:Cell>$Resource{respPerson}</t:Cell><t:Cell>$Resource{respDept}</t:Cell><t:Cell>$Resource{landDeveloper.name}</t:Cell><t:Cell>$Resource{partC.name}</t:Cell><t:Cell>$Resource{costProperty}</t:Cell><t:Cell>$Resource{contractPropert}</t:Cell><t:Cell>$Resource{curProject.id}</t:Cell><t:Cell>$Resource{curProject.name}</t:Cell><t:Cell>$Resource{isArchived}</t:Cell><t:Cell>$Resource{orgUnit.name}</t:Cell><t:Cell>$Resource{curProject.displayName}</t:Cell><t:Cell>$Resource{currency.id}</t:Cell><t:Cell>$Resource{currency.precision}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{auditTime}</t:Cell><t:Cell>$Resource{creator.name}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{isRespite}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","bookedDate","period.number","state","hasSettled","contractType.name","number","name","attachment","content","contract.name","mainContractNumber","currency.name","srcAmount","originalAmount","exRate","amount","partB.name","contractSourceId.name","signDate","respPerson.name","respDept.name","landDeveloper.name","partC.name","costProperty","contractPropert","curProject.id","curProject.name","isArchived","orgUnit.name","curProject.displayName","currency.id","currency.precision","auditor.name","auditTime","creator.name","createTime","isRespite"});

		
        this.menuItemCancelCancel.setText(resHelper.getString("menuItemCancelCancel.text"));		
        this.menuItemCancelCancel.setToolTipText(resHelper.getString("menuItemCancelCancel.toolTipText"));		
        this.menuItemCancel.setText(resHelper.getString("menuItemCancel.text"));		
        this.menuItemCancel.setToolTipText(resHelper.getString("menuItemCancel.toolTipText"));		
        this.menuItemCancel.setVisible(true);		
        this.btnCancel.setText(resHelper.getString("btnCancel.text"));		
        this.btnCancel.setToolTipText(resHelper.getString("btnCancel.toolTipText"));		
        this.btnCancel.setVisible(true);		
        this.btnCancelCancel.setText(resHelper.getString("btnCancelCancel.text"));		
        this.btnCancelCancel.setToolTipText(resHelper.getString("btnCancelCancel.toolTipText"));		
        this.kDSplitPane2.setDividerLocation(600);		
        this.kDSplitPane2.setOneTouchExpandable(false);		
        this.kDSplitPane2.setDividerSize(0);
        // btnProgram
        this.btnProgram.setAction((IItemAction)ActionProxyFactory.getProxy(actionProgram, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProgram.setText(resHelper.getString("btnProgram.text"));		
        this.btnProgram.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_associatecreate"));
        // btnUnProgram
        this.btnUnProgram.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnProgram, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnProgram.setText(resHelper.getString("btnUnProgram.text"));
        // btnEditContractType
        this.btnEditContractType.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditContractType, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEditContractType.setText(resHelper.getString("btnEditContractType.text"));
        // btnEditAuditDate
        this.btnEditAuditDate.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditAuditDate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEditAuditDate.setText(resHelper.getString("btnEditAuditDate.text"));
        // btnEditPerson
        this.btnEditPerson.setAction((IItemAction)ActionProxyFactory.getProxy(actionEditPerson, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEditPerson.setText(resHelper.getString("btnEditPerson.text"));
        // separator3
        // separator2
        // btnProjectAttachment
        this.btnProjectAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionProjectAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProjectAttachment.setText(resHelper.getString("btnProjectAttachment.text"));		
        this.btnProjectAttachment.setToolTipText(resHelper.getString("btnProjectAttachment.toolTipText"));
        // btnViewContent
        this.btnViewContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContent.setText(resHelper.getString("btnViewContent.text"));		
        this.btnViewContent.setToolTipText(resHelper.getString("btnViewContent.toolTipText"));
        // btnAddContent
        this.btnAddContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddContent.setText(resHelper.getString("btnAddContent.text"));		
        this.btnAddContent.setToolTipText(resHelper.getString("btnAddContent.toolTipText"));
        // btnConMove
        this.btnConMove.setAction((IItemAction)ActionProxyFactory.getProxy(actionConMove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnConMove.setText(resHelper.getString("btnConMove.text"));		
        this.btnConMove.setToolTipText(resHelper.getString("btnConMove.toolTipText"));
        // btnViewContract
        this.btnViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContract.setText(resHelper.getString("btnViewContract.text"));
        // btnStore
        this.btnStore.setAction((IItemAction)ActionProxyFactory.getProxy(actionStore, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnStore.setText(resHelper.getString("btnStore.text"));		
        this.btnStore.setToolTipText(resHelper.getString("btnStore.toolTipText"));
        // btnAntiStore
        this.btnAntiStore.setAction((IItemAction)ActionProxyFactory.getProxy(actionAntiStore, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAntiStore.setText(resHelper.getString("btnAntiStore.text"));		
        this.btnAntiStore.setToolTipText(resHelper.getString("btnAntiStore.toolTipText"));		
        this.btnAntiStore.setMnemonic(76);
        // menuItemProjectAttachment
        this.menuItemProjectAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionProjectAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemProjectAttachment.setText(resHelper.getString("menuItemProjectAttachment.text"));		
        this.menuItemProjectAttachment.setToolTipText(resHelper.getString("menuItemProjectAttachment.toolTipText"));		
        this.menuItemProjectAttachment.setMnemonic(70);
        // menuItemAddContent
        this.menuItemAddContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAddContent.setText(resHelper.getString("menuItemAddContent.text"));		
        this.menuItemAddContent.setToolTipText(resHelper.getString("menuItemAddContent.toolTipText"));		
        this.menuItemAddContent.setMnemonic(65);
        // menuItemViewContent
        this.menuItemViewContent.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContent, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewContent.setText(resHelper.getString("menuItemViewContent.text"));		
        this.menuItemViewContent.setToolTipText(resHelper.getString("menuItemViewContent.toolTipText"));
        // menuItemViewContract
        this.menuItemViewContract.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContract, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewContract.setText(resHelper.getString("menuItemViewContract.text"));
        // menuItemConMove
        this.menuItemConMove.setAction((IItemAction)ActionProxyFactory.getProxy(actionConMove, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemConMove.setText(resHelper.getString("menuItemConMove.text"));		
        this.menuItemConMove.setToolTipText(resHelper.getString("menuItemConMove.toolTipText"));
        // menuItemStore
        this.menuItemStore.setAction((IItemAction)ActionProxyFactory.getProxy(actionStore, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemStore.setText(resHelper.getString("menuItemStore.text"));		
        this.menuItemStore.setToolTipText(resHelper.getString("menuItemStore.toolTipText"));		
        this.menuItemStore.setMnemonic(75);
        // menuItemAntiStore
        this.menuItemAntiStore.setAction((IItemAction)ActionProxyFactory.getProxy(actionAntiStore, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAntiStore.setText(resHelper.getString("menuItemAntiStore.text"));		
        this.menuItemAntiStore.setMnemonic(76);
        // menuItemProgram
        this.menuItemProgram.setAction((IItemAction)ActionProxyFactory.getProxy(actionProgram, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemProgram.setText(resHelper.getString("menuItemProgram.text"));		
        this.menuItemProgram.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_associatecreate"));
        // separator1
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
        pnlSplit.setBounds(new Rectangle(10, 10, 993, 609));
        this.add(pnlSplit, new KDLayout.Constraints(10, 10, 993, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlSplit
        pnlSplit.add(pnlLeftTree, "left");
        pnlSplit.add(pnlRight, "right");
        //pnlLeftTree
pnlLeftTree.setLayout(new BorderLayout(0, 0));        pnlLeftTree.add(kDSplitPane1, BorderLayout.CENTER);
        //kDSplitPane1
        kDSplitPane1.add(contProject, "top");
        kDSplitPane1.add(contContrType, "bottom");
        //contProject
contProject.getContentPane().setLayout(new BorderLayout(0, 0));        contProject.getContentPane().add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(treeProject, null);
        //contContrType
contContrType.getContentPane().setLayout(new BorderLayout(0, 0));        contContrType.getContentPane().add(kDScrollPane2, BorderLayout.CENTER);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(treeContractType, null);
        //pnlRight
pnlRight.setLayout(new BorderLayout(0, 0));        pnlRight.add(kDSplitPane2, BorderLayout.CENTER);
        //kDSplitPane2
        kDSplitPane2.add(contContrList, "top");
        //contContrList
contContrList.getContentPane().setLayout(new BorderLayout(0, 0));        contContrList.getContentPane().add(tblMain, BorderLayout.CENTER);

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
        this.menuBar.add(menuWorkFlow);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        this.menuBar.add(separator1);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemExportData);
        menuFile.add(menuItemProjectAttachment);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(separatorFile1);
        menuFile.add(menuItemCloudShare);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemAddContent);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemViewContent);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemSwitchView);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(menuItemQueryScheme);
        menuView.add(kDSeparator6);
        menuView.add(menuItemViewContract);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemSetRespite);
        menuBiz.add(menuItemCancelRespite);
        menuBiz.add(menuItemConMove);
        menuBiz.add(menuItemStore);
        menuBiz.add(menuItemAntiStore);
        menuBiz.add(menuItemProgram);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuWorkFlow
        menuWorkFlow.add(menuItemViewDoProccess);
        menuWorkFlow.add(menuItemMultiapprove);
        menuWorkFlow.add(menuItemWorkFlowG);
        menuWorkFlow.add(menuItemWorkFlowList);
        menuWorkFlow.add(separatorWF1);
        menuWorkFlow.add(menuItemNextPerson);
        menuWorkFlow.add(menuItemAuditResult);
        menuWorkFlow.add(kDSeparator7);
        menuWorkFlow.add(menuItemSendSmsMessage);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnView);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnProgram);
        this.toolBar.add(btnUnProgram);
        this.toolBar.add(btnEditContractType);
        this.toolBar.add(btnEditAuditDate);
        this.toolBar.add(btnEditPerson);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separator3);
        this.toolBar.add(separator2);
        this.toolBar.add(btnProjectAttachment);
        this.toolBar.add(btnViewContent);
        this.toolBar.add(btnAddContent);
        this.toolBar.add(btnConMove);
        this.toolBar.add(btnViewContract);
        this.toolBar.add(btnSetRespite);
        this.toolBar.add(btnCancelRespite);
        this.toolBar.add(btnStore);
        this.toolBar.add(btnAntiStore);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractBillListUIHandler";
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("signDate"));
        sic.add(new SelectorItemInfo("costProperty"));
        sic.add(new SelectorItemInfo("contractPropert"));
        sic.add(new SelectorItemInfo("landDeveloper.name"));
        sic.add(new SelectorItemInfo("contractType.name"));
        sic.add(new SelectorItemInfo("partB.name"));
        sic.add(new SelectorItemInfo("partC.name"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("contractSourceId.name"));
        sic.add(new SelectorItemInfo("respPerson.name"));
        sic.add(new SelectorItemInfo("respDept.name"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("currency.name"));
        sic.add(new SelectorItemInfo("exRate"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.name"));
        sic.add(new SelectorItemInfo("orgUnit.name"));
        sic.add(new SelectorItemInfo("isArchived"));
        sic.add(new SelectorItemInfo("curProject.displayName"));
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("currency.id"));
        sic.add(new SelectorItemInfo("currency.precision"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("mainContractNumber"));
        sic.add(new SelectorItemInfo("attachment"));
        sic.add(new SelectorItemInfo("content"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("isRespite"));
        sic.add(new SelectorItemInfo("contract.name"));
        sic.add(new SelectorItemInfo("srcAmount"));
        return sic;
    }        
    	

    /**
     * output actionRemove_actionPerformed method
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
    	

    /**
     * output actionCancel_actionPerformed method
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }
    	

    /**
     * output actionCancelCancel_actionPerformed method
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }
    	

    /**
     * output actionProjectAttachment_actionPerformed method
     */
    public void actionProjectAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContent_actionPerformed method
     */
    public void actionViewContent_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddContent_actionPerformed method
     */
    public void actionAddContent_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionStore_actionPerformed method
     */
    public void actionStore_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionConMove_actionPerformed method
     */
    public void actionConMove_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContract_actionPerformed method
     */
    public void actionViewContract_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAntiStore_actionPerformed method
     */
    public void actionAntiStore_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionProgram_actionPerformed method
     */
    public void actionProgram_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionHeYue_actionPerformed method
     */
    public void actionHeYue_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEditPerson_actionPerformed method
     */
    public void actionEditPerson_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnProgram_actionPerformed method
     */
    public void actionUnProgram_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEditAuditDate_actionPerformed method
     */
    public void actionEditAuditDate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEditContractType_actionPerformed method
     */
    public void actionEditContractType_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionRemove(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionRemove(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRemove() {
    	return false;
    }
	public RequestContext prepareActionCancel(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCancel(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancel() {
    	return false;
    }
	public RequestContext prepareActionCancelCancel(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionCancelCancel(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCancelCancel() {
    	return false;
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionUnAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }
	public RequestContext prepareActionProjectAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProjectAttachment() {
    	return false;
    }
	public RequestContext prepareActionViewContent(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContent() {
    	return false;
    }
	public RequestContext prepareActionAddContent(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddContent() {
    	return false;
    }
	public RequestContext prepareActionStore(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionStore() {
    	return false;
    }
	public RequestContext prepareActionConMove(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionConMove() {
    	return false;
    }
	public RequestContext prepareActionViewContract(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContract() {
    	return false;
    }
	public RequestContext prepareActionAntiStore(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAntiStore() {
    	return false;
    }
	public RequestContext prepareActionProgram(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProgram() {
    	return false;
    }
	public RequestContext prepareActionHeYue(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionHeYue() {
    	return false;
    }
	public RequestContext prepareActionEditPerson(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditPerson() {
    	return false;
    }
	public RequestContext prepareActionUnProgram(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnProgram() {
    	return false;
    }
	public RequestContext prepareActionEditAuditDate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditAuditDate() {
    	return false;
    }
	public RequestContext prepareActionEditContractType(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditContractType() {
    	return false;
    }

    /**
     * output ActionProjectAttachment class
     */     
    protected class ActionProjectAttachment extends ItemAction {     
    
        public ActionProjectAttachment()
        {
            this(null);
        }

        public ActionProjectAttachment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift A"));
            _tempStr = resHelper.getString("ActionProjectAttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProjectAttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProjectAttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillListUI.this, "ActionProjectAttachment", "actionProjectAttachment_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContent class
     */     
    protected class ActionViewContent extends ItemAction {     
    
        public ActionViewContent()
        {
            this(null);
        }

        public ActionViewContent(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift N"));
            _tempStr = resHelper.getString("ActionViewContent.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContent.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContent.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillListUI.this, "ActionViewContent", "actionViewContent_actionPerformed", e);
        }
    }

    /**
     * output ActionAddContent class
     */     
    protected class ActionAddContent extends ItemAction {     
    
        public ActionAddContent()
        {
            this(null);
        }

        public ActionAddContent(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddContent.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddContent.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddContent.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillListUI.this, "ActionAddContent", "actionAddContent_actionPerformed", e);
        }
    }

    /**
     * output ActionStore class
     */     
    protected class ActionStore extends ItemAction {     
    
        public ActionStore()
        {
            this(null);
        }

        public ActionStore(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift K"));
            _tempStr = resHelper.getString("ActionStore.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionStore.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionStore.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillListUI.this, "ActionStore", "actionStore_actionPerformed", e);
        }
    }

    /**
     * output ActionConMove class
     */     
    protected class ActionConMove extends ItemAction {     
    
        public ActionConMove()
        {
            this(null);
        }

        public ActionConMove(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shif M"));
            _tempStr = resHelper.getString("ActionConMove.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConMove.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionConMove.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillListUI.this, "ActionConMove", "actionConMove_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContract class
     */     
    protected class ActionViewContract extends ItemAction {     
    
        public ActionViewContract()
        {
            this(null);
        }

        public ActionViewContract(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift T"));
            _tempStr = resHelper.getString("ActionViewContract.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContract.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContract.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillListUI.this, "ActionViewContract", "actionViewContract_actionPerformed", e);
        }
    }

    /**
     * output ActionAntiStore class
     */     
    protected class ActionAntiStore extends ItemAction {     
    
        public ActionAntiStore()
        {
            this(null);
        }

        public ActionAntiStore(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift L"));
            _tempStr = resHelper.getString("ActionAntiStore.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAntiStore.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAntiStore.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillListUI.this, "ActionAntiStore", "actionAntiStore_actionPerformed", e);
        }
    }

    /**
     * output ActionProgram class
     */     
    protected class ActionProgram extends ItemAction {     
    
        public ActionProgram()
        {
            this(null);
        }

        public ActionProgram(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionProgram.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProgram.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProgram.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillListUI.this, "ActionProgram", "actionProgram_actionPerformed", e);
        }
    }

    /**
     * output ActionHeYue class
     */     
    protected class ActionHeYue extends ItemAction {     
    
        public ActionHeYue()
        {
            this(null);
        }

        public ActionHeYue(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionHeYue.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHeYue.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionHeYue.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillListUI.this, "ActionHeYue", "actionHeYue_actionPerformed", e);
        }
    }

    /**
     * output ActionEditPerson class
     */     
    protected class ActionEditPerson extends ItemAction {     
    
        public ActionEditPerson()
        {
            this(null);
        }

        public ActionEditPerson(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEditPerson.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditPerson.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditPerson.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillListUI.this, "ActionEditPerson", "actionEditPerson_actionPerformed", e);
        }
    }

    /**
     * output ActionUnProgram class
     */     
    protected class ActionUnProgram extends ItemAction {     
    
        public ActionUnProgram()
        {
            this(null);
        }

        public ActionUnProgram(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnProgram.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnProgram.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnProgram.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillListUI.this, "ActionUnProgram", "actionUnProgram_actionPerformed", e);
        }
    }

    /**
     * output ActionEditAuditDate class
     */     
    protected class ActionEditAuditDate extends ItemAction {     
    
        public ActionEditAuditDate()
        {
            this(null);
        }

        public ActionEditAuditDate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEditAuditDate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditAuditDate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditAuditDate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillListUI.this, "ActionEditAuditDate", "actionEditAuditDate_actionPerformed", e);
        }
    }

    /**
     * output ActionEditContractType class
     */     
    protected class ActionEditContractType extends ItemAction {     
    
        public ActionEditContractType()
        {
            this(null);
        }

        public ActionEditContractType(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionEditContractType.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditContractType.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditContractType.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractContractBillListUI.this, "ActionEditContractType", "actionEditContractType_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractBillListUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}