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
public abstract class AbstractQuestionPaperDefineEditUI extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractQuestionPaperDefineEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpaperType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdocumentId;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane paneBIZLayerControl21;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttopric;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizScene;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizPromptLastUpdateUser;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtpaperType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdocumentId;
    protected com.kingdee.bos.ctrl.swing.KDPanel paperPanel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDTextField txttopric;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompany;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combBizScene;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected javax.swing.JToolBar.Separator newSeparator2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton addNewSubject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton deleteSubject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton updateSubject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton selectSubject;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton paperProperty;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton answerResult;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnJumpSet;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSimulateAnser;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuJumpSet;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuAddNewSubject;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuDeleteSubject;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuUpdateSubject;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuPaperPro;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuSimulateAnswer;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuSelectSubject;
    protected com.kingdee.eas.fdc.market.QuestionPaperDefineInfo editData = null;
    protected Action_NewSubject action_NewSubject = null;
    protected Action_DeleteSubject action_DeleteSubject = null;
    protected Action_SelectSubject action_SelectSubject = null;
    protected Action_UpdateSubject action_UpdateSubject = null;
    protected ActionJumpSet actionJumpSet = null;
    protected ActionSimulateAnswer actionSimulateAnswer = null;
    /**
     * output class constructor
     */
    public AbstractQuestionPaperDefineEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractQuestionPaperDefineEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl s"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrint
        actionPrint.setEnabled(true);
        actionPrint.setDaemonRun(false);

        actionPrint.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
        actionPrint.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
        actionPrint.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.NAME");
        actionPrint.putValue(ItemAction.NAME, _tempStr);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrintPreview
        actionPrintPreview.setEnabled(true);
        actionPrintPreview.setDaemonRun(false);

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionAddNew
        actionAddNew.setEnabled(true);
        actionAddNew.setDaemonRun(false);

        actionAddNew.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl n"));
        _tempStr = resHelper.getString("ActionAddNew.SHORT_DESCRIPTION");
        actionAddNew.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.LONG_DESCRIPTION");
        actionAddNew.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionAddNew.NAME");
        actionAddNew.putValue(ItemAction.NAME, _tempStr);
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //action_NewSubject
        this.action_NewSubject = new Action_NewSubject(this);
        getActionManager().registerAction("action_NewSubject", action_NewSubject);
        this.action_NewSubject.setExtendProperty("canForewarn", "true");
        this.action_NewSubject.setExtendProperty("userDefined", "false");
        this.action_NewSubject.setExtendProperty("isObjectUpdateLock", "false");
         this.action_NewSubject.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.action_NewSubject.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //action_DeleteSubject
        this.action_DeleteSubject = new Action_DeleteSubject(this);
        getActionManager().registerAction("action_DeleteSubject", action_DeleteSubject);
        this.action_DeleteSubject.setExtendProperty("canForewarn", "true");
        this.action_DeleteSubject.setExtendProperty("userDefined", "false");
        this.action_DeleteSubject.setExtendProperty("isObjectUpdateLock", "false");
         this.action_DeleteSubject.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.action_DeleteSubject.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //action_SelectSubject
        this.action_SelectSubject = new Action_SelectSubject(this);
        getActionManager().registerAction("action_SelectSubject", action_SelectSubject);
        this.action_SelectSubject.setExtendProperty("canForewarn", "true");
        this.action_SelectSubject.setExtendProperty("userDefined", "false");
        this.action_SelectSubject.setExtendProperty("isObjectUpdateLock", "false");
         this.action_SelectSubject.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.action_SelectSubject.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //action_UpdateSubject
        this.action_UpdateSubject = new Action_UpdateSubject(this);
        getActionManager().registerAction("action_UpdateSubject", action_UpdateSubject);
        this.action_UpdateSubject.setExtendProperty("canForewarn", "true");
        this.action_UpdateSubject.setExtendProperty("userDefined", "false");
        this.action_UpdateSubject.setExtendProperty("isObjectUpdateLock", "false");
         this.action_UpdateSubject.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.action_UpdateSubject.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionJumpSet
        this.actionJumpSet = new ActionJumpSet(this);
        getActionManager().registerAction("actionJumpSet", actionJumpSet);
         this.actionJumpSet.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSimulateAnswer
        this.actionSimulateAnswer = new ActionSimulateAnswer(this);
        getActionManager().registerAction("actionSimulateAnswer", actionSimulateAnswer);
         this.actionSimulateAnswer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpaperType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdocumentId = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.paneBIZLayerControl21 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.conttopric = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizScene = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.bizPromptAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.bizPromptCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.bizPromptLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtpaperType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtdocumentId = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.paperPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txttopric = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCompany = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.combBizScene = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.newSeparator2 = new javax.swing.JToolBar.Separator();
        this.addNewSubject = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.deleteSubject = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.updateSubject = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.selectSubject = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.paperProperty = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.answerResult = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnJumpSet = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSimulateAnser = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuJumpSet = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuAddNewSubject = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuDeleteSubject = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuUpdateSubject = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuPaperPro = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuSimulateAnswer = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuSelectSubject = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.contBizDate.setName("contBizDate");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.contpaperType.setName("contpaperType");
        this.contdocumentId.setName("contdocumentId");
        this.paneBIZLayerControl21.setName("paneBIZLayerControl21");
        this.conttopric.setName("conttopric");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.contBizScene.setName("contBizScene");
        this.contSellProject.setName("contSellProject");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.dateLastUpdateTime.setName("dateLastUpdateTime");
        this.dateCreateTime.setName("dateCreateTime");
        this.bizPromptAuditor.setName("bizPromptAuditor");
        this.txtDescription.setName("txtDescription");
        this.bizPromptCreator.setName("bizPromptCreator");
        this.bizPromptLastUpdateUser.setName("bizPromptLastUpdateUser");
        this.prmtpaperType.setName("prmtpaperType");
        this.txtdocumentId.setName("txtdocumentId");
        this.paperPanel.setName("paperPanel");
        this.kdtEntrys.setName("kdtEntrys");
        this.txttopric.setName("txttopric");
        this.txtCompany.setName("txtCompany");
        this.combBizScene.setName("combBizScene");
        this.prmtSellProject.setName("prmtSellProject");
        this.newSeparator2.setName("newSeparator2");
        this.addNewSubject.setName("addNewSubject");
        this.deleteSubject.setName("deleteSubject");
        this.updateSubject.setName("updateSubject");
        this.selectSubject.setName("selectSubject");
        this.paperProperty.setName("paperProperty");
        this.answerResult.setName("answerResult");
        this.btnJumpSet.setName("btnJumpSet");
        this.btnSimulateAnser.setName("btnSimulateAnser");
        this.menuJumpSet.setName("menuJumpSet");
        this.menuAddNewSubject.setName("menuAddNewSubject");
        this.menuDeleteSubject.setName("menuDeleteSubject");
        this.menuUpdateSubject.setName("menuUpdateSubject");
        this.menuPaperPro.setName("menuPaperPro");
        this.menuSimulateAnswer.setName("menuSimulateAnswer");
        this.menuSelectSubject.setName("menuSelectSubject");
        // CoreUI
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.setVisible(true);		
        this.kDLabelContainer1.setForeground(new java.awt.Color(0,0,0));
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);		
        this.contBizDate.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelAlignment(7);		
        this.kDLabelContainer3.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setVisible(true);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);		
        this.kDLabelContainer4.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setBoundLabelAlignment(7);		
        this.kDLabelContainer5.setVisible(false);		
        this.kDLabelContainer5.setForeground(new java.awt.Color(0,0,0));		
        this.kDLabelContainer5.setEnabled(false);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setBoundLabelAlignment(7);		
        this.kDLabelContainer6.setVisible(true);		
        this.kDLabelContainer6.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setBoundLabelAlignment(7);		
        this.kDLabelContainer7.setVisible(true);		
        this.kDLabelContainer7.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);		
        this.kDLabelContainer8.setBoundLabelAlignment(7);		
        this.kDLabelContainer8.setForeground(new java.awt.Color(0,0,0));
        // contpaperType		
        this.contpaperType.setBoundLabelText(resHelper.getString("contpaperType.boundLabelText"));		
        this.contpaperType.setBoundLabelLength(100);		
        this.contpaperType.setBoundLabelUnderline(true);		
        this.contpaperType.setVisible(true);		
        this.contpaperType.setBoundLabelAlignment(7);		
        this.contpaperType.setForeground(new java.awt.Color(0,0,0));
        // contdocumentId		
        this.contdocumentId.setBoundLabelText(resHelper.getString("contdocumentId.boundLabelText"));		
        this.contdocumentId.setBoundLabelLength(100);		
        this.contdocumentId.setBoundLabelUnderline(true);		
        this.contdocumentId.setVisible(false);		
        this.contdocumentId.setBoundLabelAlignment(7);		
        this.contdocumentId.setForeground(new java.awt.Color(0,0,0));
        // paneBIZLayerControl21		
        this.paneBIZLayerControl21.setVisible(true);
        // conttopric		
        this.conttopric.setBoundLabelText(resHelper.getString("conttopric.boundLabelText"));		
        this.conttopric.setBoundLabelLength(100);		
        this.conttopric.setBoundLabelUnderline(true);		
        this.conttopric.setVisible(true);		
        this.conttopric.setBoundLabelAlignment(7);		
        this.conttopric.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelAlignment(7);		
        this.kDLabelContainer2.setVisible(true);		
        this.kDLabelContainer2.setForeground(new java.awt.Color(0,0,0));
        // contBizScene		
        this.contBizScene.setBoundLabelText(resHelper.getString("contBizScene.boundLabelText"));		
        this.contBizScene.setBoundLabelLength(100);		
        this.contBizScene.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setForeground(new java.awt.Color(0,0,0));		
        this.txtNumber.setRequired(false);
        // pkBizDate		
        this.pkBizDate.setVisible(true);		
        this.pkBizDate.setEnabled(true);		
        this.pkBizDate.setForeground(new java.awt.Color(0,0,0));		
        this.pkBizDate.setRequired(false);
        // dateLastUpdateTime		
        this.dateLastUpdateTime.setRequired(false);		
        this.dateLastUpdateTime.setForeground(new java.awt.Color(0,0,0));
        // dateCreateTime		
        this.dateCreateTime.setEnabled(false);		
        this.dateCreateTime.setVisible(true);		
        this.dateCreateTime.setRequired(false);		
        this.dateCreateTime.setForeground(new java.awt.Color(0,0,0));
        // bizPromptAuditor		
        this.bizPromptAuditor.setEnabled(false);		
        this.bizPromptAuditor.setVisible(true);		
        this.bizPromptAuditor.setEditable(true);		
        this.bizPromptAuditor.setDisplayFormat("$name$");		
        this.bizPromptAuditor.setEditFormat("$number$");		
        this.bizPromptAuditor.setCommitFormat("$number$");		
        this.bizPromptAuditor.setRequired(false);		
        this.bizPromptAuditor.setForeground(new java.awt.Color(0,0,0));
        // txtDescription		
        this.txtDescription.setMaxLength(80);		
        this.txtDescription.setVisible(true);		
        this.txtDescription.setEnabled(true);		
        this.txtDescription.setHorizontalAlignment(2);		
        this.txtDescription.setForeground(new java.awt.Color(0,0,0));		
        this.txtDescription.setRequired(false);
        // bizPromptCreator		
        this.bizPromptCreator.setEnabled(false);		
        this.bizPromptCreator.setVisible(true);		
        this.bizPromptCreator.setEditable(true);		
        this.bizPromptCreator.setDisplayFormat("$name$");		
        this.bizPromptCreator.setEditFormat("$number$");		
        this.bizPromptCreator.setCommitFormat("$number$");		
        this.bizPromptCreator.setRequired(false);		
        this.bizPromptCreator.setForeground(new java.awt.Color(0,0,0));
        // bizPromptLastUpdateUser		
        this.bizPromptLastUpdateUser.setEditable(true);		
        this.bizPromptLastUpdateUser.setDisplayFormat("$name$");		
        this.bizPromptLastUpdateUser.setEditFormat("$number$");		
        this.bizPromptLastUpdateUser.setCommitFormat("$number$");		
        this.bizPromptLastUpdateUser.setRequired(false);		
        this.bizPromptLastUpdateUser.setForeground(new java.awt.Color(0,0,0));
        // prmtpaperType		
        this.prmtpaperType.setQueryInfo("com.kingdee.eas.fdc.market.app.QuestionTypeQuery");		
        this.prmtpaperType.setVisible(true);		
        this.prmtpaperType.setEditable(true);		
        this.prmtpaperType.setDisplayFormat("$name$");		
        this.prmtpaperType.setEditFormat("$number$");		
        this.prmtpaperType.setCommitFormat("$number$");		
        this.prmtpaperType.setRequired(false);		
        this.prmtpaperType.setEnabled(true);		
        this.prmtpaperType.setForeground(new java.awt.Color(0,0,0));
        // txtdocumentId		
        this.txtdocumentId.setVisible(true);		
        this.txtdocumentId.setHorizontalAlignment(2);		
        this.txtdocumentId.setMaxLength(200);		
        this.txtdocumentId.setRequired(false);		
        this.txtdocumentId.setEnabled(true);		
        this.txtdocumentId.setForeground(new java.awt.Color(0,0,0));
        // paperPanel		
        this.paperPanel.setVisible(true);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));

                this.kdtEntrys.putBindContents("editData",new String[] {"id"});


        // txttopric		
        this.txttopric.setVisible(true);		
        this.txttopric.setHorizontalAlignment(2);		
        this.txttopric.setMaxLength(200);		
        this.txttopric.setRequired(false);		
        this.txttopric.setEnabled(true);		
        this.txttopric.setForeground(new java.awt.Color(0,0,0));
        // txtCompany		
        this.txtCompany.setText(resHelper.getString("txtCompany.text"));		
        this.txtCompany.setEnabled(false);		
        this.txtCompany.setVisible(true);		
        this.txtCompany.setHorizontalAlignment(2);		
        this.txtCompany.setForeground(new java.awt.Color(0,0,0));		
        this.txtCompany.setRequired(false);
        // combBizScene		
        this.combBizScene.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.QuestPaperBizSceneEnum").toArray());
        // prmtSellProject		
        this.prmtSellProject.setDisplayFormat("$name$");		
        this.prmtSellProject.setEditFormat("$number$");		
        this.prmtSellProject.setCommitFormat("$number$");		
        this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectForSHEQuery");
        // newSeparator2		
        this.newSeparator2.setOrientation(1);
        // addNewSubject
        this.addNewSubject.setAction((IItemAction)ActionProxyFactory.getProxy(action_NewSubject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.addNewSubject.setText(resHelper.getString("addNewSubject.text"));		
        this.addNewSubject.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_upenumnew"));
        // deleteSubject
        this.deleteSubject.setAction((IItemAction)ActionProxyFactory.getProxy(action_DeleteSubject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.deleteSubject.setText(resHelper.getString("deleteSubject.text"));		
        this.deleteSubject.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_upenumdel"));
        // updateSubject
        this.updateSubject.setAction((IItemAction)ActionProxyFactory.getProxy(action_UpdateSubject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.updateSubject.setText(resHelper.getString("updateSubject.text"));		
        this.updateSubject.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_grantcollocate"));
        // selectSubject
        this.selectSubject.setAction((IItemAction)ActionProxyFactory.getProxy(action_SelectSubject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.selectSubject.setText(resHelper.getString("selectSubject.text"));		
        this.selectSubject.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_courseoffer"));
        // paperProperty		
        this.paperProperty.setText(resHelper.getString("paperProperty.text"));		
        this.paperProperty.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_attributeset"));
        this.paperProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    paperProperty_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // answerResult		
        this.answerResult.setText(resHelper.getString("answerResult.text"));
        this.answerResult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    answerResult_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnJumpSet
        this.btnJumpSet.setAction((IItemAction)ActionProxyFactory.getProxy(actionJumpSet, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnJumpSet.setText(resHelper.getString("btnJumpSet.text"));		
        this.btnJumpSet.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));
        // btnSimulateAnser
        this.btnSimulateAnser.setAction((IItemAction)ActionProxyFactory.getProxy(actionSimulateAnswer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSimulateAnser.setText(resHelper.getString("btnSimulateAnser.text"));		
        this.btnSimulateAnser.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_register"));
        // menuJumpSet
        this.menuJumpSet.setAction((IItemAction)ActionProxyFactory.getProxy(actionJumpSet, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuJumpSet.setText(resHelper.getString("menuJumpSet.text"));		
        this.menuJumpSet.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));
        // menuAddNewSubject
        this.menuAddNewSubject.setAction((IItemAction)ActionProxyFactory.getProxy(action_NewSubject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuAddNewSubject.setText(resHelper.getString("menuAddNewSubject.text"));		
        this.menuAddNewSubject.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_upenumnew"));
        // menuDeleteSubject
        this.menuDeleteSubject.setAction((IItemAction)ActionProxyFactory.getProxy(action_DeleteSubject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuDeleteSubject.setText(resHelper.getString("menuDeleteSubject.text"));		
        this.menuDeleteSubject.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_upenumdel"));
        // menuUpdateSubject
        this.menuUpdateSubject.setAction((IItemAction)ActionProxyFactory.getProxy(action_UpdateSubject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuUpdateSubject.setText(resHelper.getString("menuUpdateSubject.text"));		
        this.menuUpdateSubject.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_grantcollocate"));
        // menuPaperPro		
        this.menuPaperPro.setText(resHelper.getString("menuPaperPro.text"));		
        this.menuPaperPro.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_attributeset"));
        // menuSimulateAnswer
        this.menuSimulateAnswer.setAction((IItemAction)ActionProxyFactory.getProxy(actionSimulateAnswer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuSimulateAnswer.setText(resHelper.getString("menuSimulateAnswer.text"));		
        this.menuSimulateAnswer.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_register"));
        // menuSelectSubject
        this.menuSelectSubject.setAction((IItemAction)ActionProxyFactory.getProxy(action_SelectSubject, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuSelectSubject.setText(resHelper.getString("menuSelectSubject.text"));		
        this.menuSelectSubject.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_courseoffer"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtCompany,dateLastUpdateTime,bizPromptLastUpdateUser,dateCreateTime,bizPromptCreator,bizPromptAuditor,txtDescription,pkBizDate,txtNumber,kdtEntrys,prmtpaperType,txtdocumentId,txttopric}));
        this.setFocusCycleRoot(true);
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
        this.setBounds(new Rectangle(0, 0, 1013, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 600));
        kDLabelContainer1.setBounds(new Rectangle(15, 13, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(15, 13, 270, 19, 0));
        contBizDate.setBounds(new Rectangle(583, 13, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(583, 13, 270, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(583, 564, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(583, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer4.setBounds(new Rectangle(15, 564, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(15, 564, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer5.setBounds(new Rectangle(299, 538, 270, 19));
        this.add(kDLabelContainer5, new KDLayout.Constraints(299, 538, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer6.setBounds(new Rectangle(15, 69, 838, 19));
        this.add(kDLabelContainer6, new KDLayout.Constraints(15, 69, 838, 19, 0));
        kDLabelContainer7.setBounds(new Rectangle(15, 537, 270, 19));
        this.add(kDLabelContainer7, new KDLayout.Constraints(15, 537, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer8.setBounds(new Rectangle(583, 537, 270, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(583, 537, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        contpaperType.setBounds(new Rectangle(15, 41, 270, 19));
        this.add(contpaperType, new KDLayout.Constraints(15, 41, 270, 19, 0));
        contdocumentId.setBounds(new Rectangle(865, 128, 270, 19));
        this.add(contdocumentId, new KDLayout.Constraints(865, 128, 270, 19, 0));
        paneBIZLayerControl21.setBounds(new Rectangle(15, 97, 833, 433));
        this.add(paneBIZLayerControl21, new KDLayout.Constraints(15, 97, 833, 433, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        conttopric.setBounds(new Rectangle(299, 13, 270, 19));
        this.add(conttopric, new KDLayout.Constraints(299, 13, 270, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(863, 100, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(863, 100, 270, 19, 0));
        contBizScene.setBounds(new Rectangle(299, 41, 270, 19));
        this.add(contBizScene, new KDLayout.Constraints(299, 41, 270, 19, 0));
        contSellProject.setBounds(new Rectangle(583, 41, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(583, 41, 270, 19, 0));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(dateLastUpdateTime);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(dateCreateTime);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(bizPromptAuditor);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtDescription);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(bizPromptCreator);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(bizPromptLastUpdateUser);
        //contpaperType
        contpaperType.setBoundEditor(prmtpaperType);
        //contdocumentId
        contdocumentId.setBoundEditor(txtdocumentId);
        //paneBIZLayerControl21
        paneBIZLayerControl21.add(paperPanel, resHelper.getString("paperPanel.constraints"));
        //paperPanel
        paperPanel.setLayout(null);        kdtEntrys.setBounds(new Rectangle(106, 56, 610, 269));
        paperPanel.add(kdtEntrys, null);
        //conttopric
        conttopric.setBoundEditor(txttopric);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtCompany);
        //contBizScene
        contBizScene.setBoundEditor(combBizScene);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);

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
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
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
        menuBiz.add(menuJumpSet);
        menuBiz.add(menuAddNewSubject);
        menuBiz.add(menuDeleteSubject);
        menuBiz.add(menuUpdateSubject);
        menuBiz.add(menuPaperPro);
        menuBiz.add(menuSimulateAnswer);
        menuBiz.add(menuSelectSubject);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
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
        this.toolBar.add(btnCopyLine);
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
        this.toolBar.add(newSeparator2);
        this.toolBar.add(addNewSubject);
        this.toolBar.add(deleteSubject);
        this.toolBar.add(updateSubject);
        this.toolBar.add(selectSubject);
        this.toolBar.add(paperProperty);
        this.toolBar.add(answerResult);
        this.toolBar.add(btnJumpSet);
        this.toolBar.add(btnSimulateAnser);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.dateLastUpdateTime, "value");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.dateCreateTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptAuditor, "data");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptCreator, "data");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.bizPromptLastUpdateUser, "data");
		dataBinder.registerBinding("paperType", com.kingdee.eas.fdc.market.QuestionTypeInfo.class, this.prmtpaperType, "data");
		dataBinder.registerBinding("documentId", String.class, this.txtdocumentId, "text");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.market.QuestionPaperDefineEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("topric", String.class, this.txttopric, "text");
		dataBinder.registerBinding("company.name", String.class, this.txtCompany, "text");
		dataBinder.registerBinding("bizScene", com.kingdee.eas.fdc.sellhouse.QuestPaperBizSceneEnum.class, this.combBizScene, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.QuestionPaperDefineEditUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.txtCompany.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.market.QuestionPaperDefineInfo)ov;
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Sale");
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
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("paperType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("documentId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("topric", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizScene", ValidateHelper.ON_SAVE);    		
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
     * output paperProperty_actionPerformed method
     */
    protected void paperProperty_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output answerResult_actionPerformed method
     */
    protected void answerResult_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("paperType.*"));
        sic.add(new SelectorItemInfo("documentId"));
    sic.add(new SelectorItemInfo("entrys.id"));
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
        sic.add(new SelectorItemInfo("topric"));
        sic.add(new SelectorItemInfo("company.name"));
        sic.add(new SelectorItemInfo("bizScene"));
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
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }
    	

    /**
     * output actionAddNew_actionPerformed method
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }
    	

    /**
     * output action_NewSubject_actionPerformed method
     */
    public void action_NewSubject_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.fdc.market.QuestionPaperDefineFactory.getRemoteInstance().action_NewSubject(editData);
    }
    	

    /**
     * output action_DeleteSubject_actionPerformed method
     */
    public void action_DeleteSubject_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.fdc.market.QuestionPaperDefineFactory.getRemoteInstance().action_DeleteSubject(editData);
    }
    	

    /**
     * output action_SelectSubject_actionPerformed method
     */
    public void action_SelectSubject_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.fdc.market.QuestionPaperDefineFactory.getRemoteInstance().action_SelectSubject(editData);
    }
    	

    /**
     * output action_UpdateSubject_actionPerformed method
     */
    public void action_UpdateSubject_actionPerformed(ActionEvent e) throws Exception
    {
        com.kingdee.eas.fdc.market.QuestionPaperDefineFactory.getRemoteInstance().action_UpdateSubject(editData);
    }
    	

    /**
     * output actionJumpSet_actionPerformed method
     */
    public void actionJumpSet_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSimulateAnswer_actionPerformed method
     */
    public void actionSimulateAnswer_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrint(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrintPreview(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }
	public RequestContext prepareActionAddNew(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionAddNew(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddNew() {
    	return false;
    }
	public RequestContext prepareAction_NewSubject(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareAction_NewSubject() {
    	return false;
    }
	public RequestContext prepareAction_DeleteSubject(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareAction_DeleteSubject() {
    	return false;
    }
	public RequestContext prepareAction_SelectSubject(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareAction_SelectSubject() {
    	return false;
    }
	public RequestContext prepareAction_UpdateSubject(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareAction_UpdateSubject() {
    	return false;
    }
	public RequestContext prepareActionJumpSet(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionJumpSet() {
    	return false;
    }
	public RequestContext prepareActionSimulateAnswer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSimulateAnswer() {
    	return false;
    }

    /**
     * output Action_NewSubject class
     */     
    protected class Action_NewSubject extends ItemAction {     
    
        public Action_NewSubject()
        {
            this(null);
        }

        public Action_NewSubject(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("Action_NewSubject.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("Action_NewSubject.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("Action_NewSubject.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuestionPaperDefineEditUI.this, "Action_NewSubject", "action_NewSubject_actionPerformed", e);
        }
    }

    /**
     * output Action_DeleteSubject class
     */     
    protected class Action_DeleteSubject extends ItemAction {     
    
        public Action_DeleteSubject()
        {
            this(null);
        }

        public Action_DeleteSubject(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("Action_DeleteSubject.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("Action_DeleteSubject.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("Action_DeleteSubject.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuestionPaperDefineEditUI.this, "Action_DeleteSubject", "action_DeleteSubject_actionPerformed", e);
        }
    }

    /**
     * output Action_SelectSubject class
     */     
    protected class Action_SelectSubject extends ItemAction {     
    
        public Action_SelectSubject()
        {
            this(null);
        }

        public Action_SelectSubject(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("Action_SelectSubject.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("Action_SelectSubject.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("Action_SelectSubject.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuestionPaperDefineEditUI.this, "Action_SelectSubject", "action_SelectSubject_actionPerformed", e);
        }
    }

    /**
     * output Action_UpdateSubject class
     */     
    protected class Action_UpdateSubject extends ItemAction {     
    
        public Action_UpdateSubject()
        {
            this(null);
        }

        public Action_UpdateSubject(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("Action_UpdateSubject.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("Action_UpdateSubject.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("Action_UpdateSubject.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuestionPaperDefineEditUI.this, "Action_UpdateSubject", "action_UpdateSubject_actionPerformed", e);
        }
    }

    /**
     * output ActionJumpSet class
     */     
    protected class ActionJumpSet extends ItemAction {     
    
        public ActionJumpSet()
        {
            this(null);
        }

        public ActionJumpSet(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionJumpSet.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionJumpSet.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionJumpSet.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuestionPaperDefineEditUI.this, "ActionJumpSet", "actionJumpSet_actionPerformed", e);
        }
    }

    /**
     * output ActionSimulateAnswer class
     */     
    protected class ActionSimulateAnswer extends ItemAction {     
    
        public ActionSimulateAnswer()
        {
            this(null);
        }

        public ActionSimulateAnswer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSimulateAnswer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSimulateAnswer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSimulateAnswer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractQuestionPaperDefineEditUI.this, "ActionSimulateAnswer", "actionSimulateAnswer_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "QuestionPaperDefineEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.market.client.QuestionPaperDefineEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.QuestionPaperDefineFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.QuestionPaperDefineInfo objectValue = new com.kingdee.eas.fdc.market.QuestionPaperDefineInfo();		
        return objectValue;
    }




}