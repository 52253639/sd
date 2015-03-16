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
public abstract class AbstractInviteFileItemEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractInviteFileItemEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFileItemType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRespDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFileTemplate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProject;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewAttachment;
    protected com.kingdee.bos.ctrl.swing.KDButton btnViewContrnt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conInviteMode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conInviteOrg;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboFileItemType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRespDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFileTemplate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurProject;
    protected com.kingdee.bos.ctrl.swing.KDContainer contCategory;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTree treeCategory;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboAttachmentNameList;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteMode;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteProjectOrg;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewFile;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUpLoadFile;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewInviteFile;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUploadInviteFile;
    protected com.kingdee.eas.fdc.invite.InviteFileItemInfo editData = null;
    protected ActionUpLoadFile actionUpLoadFile = null;
    protected ActionViewFile actionViewFile = null;
    protected ActionViewAttachmentSelf actionViewAttachmentSelf = null;
    protected ActionViewContentSelf actionViewContentSelf = null;
    /**
     * output class constructor
     */
    public AbstractInviteFileItemEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractInviteFileItemEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionUpLoadFile
        this.actionUpLoadFile = new ActionUpLoadFile(this);
        getActionManager().registerAction("actionUpLoadFile", actionUpLoadFile);
         this.actionUpLoadFile.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewFile
        this.actionViewFile = new ActionViewFile(this);
        getActionManager().registerAction("actionViewFile", actionViewFile);
         this.actionViewFile.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAttachmentSelf
        this.actionViewAttachmentSelf = new ActionViewAttachmentSelf(this);
        getActionManager().registerAction("actionViewAttachmentSelf", actionViewAttachmentSelf);
         this.actionViewAttachmentSelf.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewContentSelf
        this.actionViewContentSelf = new ActionViewContentSelf(this);
        getActionManager().registerAction("actionViewContentSelf", actionViewContentSelf);
         this.actionViewContentSelf.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFileItemType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRespDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFileTemplate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.btnViewAttachment = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnViewContrnt = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conInviteMode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conInviteOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboFileItemType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtRespDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtInviteProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtFileTemplate = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCurProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCategory = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.treeCategory = new com.kingdee.bos.ctrl.swing.KDTree();
        this.comboAttachmentNameList = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtInviteMode = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtInviteProjectOrg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnViewFile = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUpLoadFile = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewInviteFile = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUploadInviteFile = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contAuditor.setName("contAuditor");
        this.contName.setName("contName");
        this.contAuditTime.setName("contAuditTime");
        this.contFileItemType.setName("contFileItemType");
        this.contRespDept.setName("contRespDept");
        this.contInviteProject.setName("contInviteProject");
        this.contFileTemplate.setName("contFileTemplate");
        this.contCurProject.setName("contCurProject");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.btnViewAttachment.setName("btnViewAttachment");
        this.btnViewContrnt.setName("btnViewContrnt");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.conInviteMode.setName("conInviteMode");
        this.conInviteOrg.setName("conInviteOrg");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtName.setName("txtName");
        this.pkAuditTime.setName("pkAuditTime");
        this.comboFileItemType.setName("comboFileItemType");
        this.prmtRespDept.setName("prmtRespDept");
        this.prmtInviteProject.setName("prmtInviteProject");
        this.prmtFileTemplate.setName("prmtFileTemplate");
        this.prmtCurProject.setName("prmtCurProject");
        this.contCategory.setName("contCategory");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.treeCategory.setName("treeCategory");
        this.comboAttachmentNameList.setName("comboAttachmentNameList");
        this.prmtInviteMode.setName("prmtInviteMode");
        this.txtInviteProjectOrg.setName("txtInviteProjectOrg");
        this.btnViewFile.setName("btnViewFile");
        this.btnUpLoadFile.setName("btnUpLoadFile");
        this.menuItemViewInviteFile.setName("menuItemViewInviteFile");
        this.menuItemUploadInviteFile.setName("menuItemUploadInviteFile");
        // CoreUI		
        this.menuTable1.setEnabled(false);		
        this.menuTable1.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setEnabled(false);		
        this.contLastUpdateUser.setVisible(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setVisible(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setEnabled(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);		
        this.contName.setEnabled(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);		
        this.contAuditTime.setEnabled(false);
        // contFileItemType		
        this.contFileItemType.setBoundLabelText(resHelper.getString("contFileItemType.boundLabelText"));		
        this.contFileItemType.setBoundLabelLength(100);		
        this.contFileItemType.setBoundLabelUnderline(true);		
        this.contFileItemType.setEnabled(false);
        // contRespDept		
        this.contRespDept.setBoundLabelText(resHelper.getString("contRespDept.boundLabelText"));		
        this.contRespDept.setBoundLabelLength(100);		
        this.contRespDept.setBoundLabelUnderline(true);
        // contInviteProject		
        this.contInviteProject.setBoundLabelText(resHelper.getString("contInviteProject.boundLabelText"));		
        this.contInviteProject.setBoundLabelLength(100);		
        this.contInviteProject.setBoundLabelUnderline(true);
        // contFileTemplate		
        this.contFileTemplate.setBoundLabelText(resHelper.getString("contFileTemplate.boundLabelText"));		
        this.contFileTemplate.setBoundLabelLength(100);		
        this.contFileTemplate.setBoundLabelUnderline(true);
        // contCurProject		
        this.contCurProject.setBoundLabelText(resHelper.getString("contCurProject.boundLabelText"));		
        this.contCurProject.setBoundLabelLength(100);		
        this.contCurProject.setBoundLabelUnderline(true);		
        this.contCurProject.setEnabled(false);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(270);
        // btnViewAttachment
        this.btnViewAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAttachmentSelf, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewAttachment.setText(resHelper.getString("btnViewAttachment.text"));
        this.btnViewAttachment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnViewAttachment_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnViewContrnt
        this.btnViewContrnt.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewContentSelf, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewContrnt.setText(resHelper.getString("btnViewContrnt.text"));
        this.btnViewContrnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnViewContrnt_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // conInviteMode		
        this.conInviteMode.setBoundLabelText(resHelper.getString("conInviteMode.boundLabelText"));		
        this.conInviteMode.setBoundLabelUnderline(true);		
        this.conInviteMode.setBoundLabelLength(100);
        // conInviteOrg		
        this.conInviteOrg.setBoundLabelText(resHelper.getString("conInviteOrg.boundLabelText"));		
        this.conInviteOrg.setBoundLabelUnderline(true);		
        this.conInviteOrg.setBoundLabelLength(100);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$name$");
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setVisible(false);		
        this.prmtLastUpdateUser.setDisplayFormat("$name$");		
        this.prmtLastUpdateUser.setEditFormat("$name$");
        // pkLastUpdateTime		
        this.pkLastUpdateTime.setVisible(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$name$");
        // txtName		
        this.txtName.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // comboFileItemType		
        this.comboFileItemType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.InviteFileItemTypeEnum").toArray());		
        this.comboFileItemType.setEnabled(false);
        // prmtRespDept
        // prmtInviteProject		
        this.prmtInviteProject.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteProjectQuery");
        this.prmtInviteProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtInviteProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtFileTemplate
        this.prmtFileTemplate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtFileTemplate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCurProject		
        this.prmtCurProject.setEnabled(false);
        // contCategory
        // kDScrollPane1
        // treeCategory		
        this.treeCategory.setAutoscrolls(true);
        this.treeCategory.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeCategory_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboAttachmentNameList
        this.comboAttachmentNameList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    comboAttachmentNameList_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // prmtInviteMode		
        this.prmtInviteMode.setDisplayFormat("$name$");		
        this.prmtInviteMode.setEditFormat("$number$");		
        this.prmtInviteMode.setCommitFormat("$number$");		
        this.prmtInviteMode.setQueryInfo("com.kingdee.eas.fdc.invite.app.InviteModeQuery");		
        this.prmtInviteMode.setEnabled(false);
        // txtInviteProjectOrg		
        this.txtInviteProjectOrg.setEnabled(false);
        // btnViewFile
        this.btnViewFile.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewFile, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewFile.setText(resHelper.getString("btnViewFile.text"));		
        this.btnViewFile.setToolTipText(resHelper.getString("btnViewFile.toolTipText"));		
        this.btnViewFile.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_seeperformance"));
        // btnUpLoadFile
        this.btnUpLoadFile.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpLoadFile, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUpLoadFile.setText(resHelper.getString("btnUpLoadFile.text"));		
        this.btnUpLoadFile.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_upenumnew"));		
        this.btnUpLoadFile.setToolTipText(resHelper.getString("btnUpLoadFile.toolTipText"));
        // menuItemViewInviteFile
        this.menuItemViewInviteFile.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewFile, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewInviteFile.setText(resHelper.getString("menuItemViewInviteFile.text"));		
        this.menuItemViewInviteFile.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_seeperformance"));
        // menuItemUploadInviteFile
        this.menuItemUploadInviteFile.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpLoadFile, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUploadInviteFile.setText(resHelper.getString("menuItemUploadInviteFile.text"));		
        this.menuItemUploadInviteFile.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_upenumnew"));
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
        this.setBounds(new Rectangle(10, 10, 1000, 620));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1000, 620));
        contCreator.setBounds(new Rectangle(9, 559, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(9, 559, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(9, 589, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(9, 589, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateUser.setBounds(new Rectangle(364, 559, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(364, 559, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLastUpdateTime.setBounds(new Rectangle(364, 589, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(364, 589, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(719, 559, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(719, 559, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contName.setBounds(new Rectangle(365, 10, 270, 19));
        this.add(contName, new KDLayout.Constraints(365, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(719, 589, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(719, 589, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contFileItemType.setBounds(new Rectangle(720, 10, 270, 19));
        this.add(contFileItemType, new KDLayout.Constraints(720, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contRespDept.setBounds(new Rectangle(365, 40, 270, 19));
        this.add(contRespDept, new KDLayout.Constraints(365, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteProject.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contInviteProject, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFileTemplate.setBounds(new Rectangle(720, 40, 270, 19));
        this.add(contFileTemplate, new KDLayout.Constraints(720, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCurProject.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contCurProject, new KDLayout.Constraints(10, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSplitPane1.setBounds(new Rectangle(6, 138, 980, 411));
        this.add(kDSplitPane1, new KDLayout.Constraints(6, 138, 980, 411, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnViewAttachment.setBounds(new Rectangle(449, 99, 110, 21));
        this.add(btnViewAttachment, new KDLayout.Constraints(449, 99, 110, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE));
        btnViewContrnt.setBounds(new Rectangle(584, 98, 148, 21));
        this.add(btnViewContrnt, new KDLayout.Constraints(584, 98, 148, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(10, 99, 424, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(10, 99, 424, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conInviteMode.setBounds(new Rectangle(9, 71, 270, 19));
        this.add(conInviteMode, new KDLayout.Constraints(9, 71, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conInviteOrg.setBounds(new Rectangle(365, 71, 270, 19));
        this.add(conInviteOrg, new KDLayout.Constraints(365, 71, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contName
        contName.setBoundEditor(txtName);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contFileItemType
        contFileItemType.setBoundEditor(comboFileItemType);
        //contRespDept
        contRespDept.setBoundEditor(prmtRespDept);
        //contInviteProject
        contInviteProject.setBoundEditor(prmtInviteProject);
        //contFileTemplate
        contFileTemplate.setBoundEditor(prmtFileTemplate);
        //contCurProject
        contCurProject.setBoundEditor(prmtCurProject);
        //kDSplitPane1
        kDSplitPane1.add(contCategory, "left");
        //contCategory
contCategory.getContentPane().setLayout(new BorderLayout(0, 0));        contCategory.getContentPane().add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(treeCategory, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(comboAttachmentNameList);
        //conInviteMode
        conInviteMode.setBoundEditor(prmtInviteMode);
        //conInviteOrg
        conInviteOrg.setBoundEditor(txtInviteProjectOrg);

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
        menuView.add(menuItemViewInviteFile);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemUploadInviteFile);
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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnViewFile);
        this.toolBar.add(btnUpLoadFile);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("fileItemType", com.kingdee.eas.fdc.invite.InviteFileItemTypeEnum.class, this.comboFileItemType, "selectedItem");
		dataBinder.registerBinding("respDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtRespDept, "data");
		dataBinder.registerBinding("inviteProject", com.kingdee.eas.fdc.invite.InviteProjectInfo.class, this.prmtInviteProject, "data");
		dataBinder.registerBinding("fileTemplate", com.kingdee.eas.fdc.invite.TemplateFileInfo.class, this.prmtFileTemplate, "data");
		dataBinder.registerBinding("inviteProject.inviteMode", com.kingdee.eas.fdc.invite.InviteModeInfo.class, this.prmtInviteMode, "data");
		dataBinder.registerBinding("inviteProject.orgUnit.name", String.class, this.txtInviteProjectOrg, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.InviteFileItemEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.InviteFileItemInfo)ov;
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
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fileItemType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("respDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fileTemplate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.inviteMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.orgUnit.name", ValidateHelper.ON_SAVE);    		
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
     * output btnViewAttachment_actionPerformed method
     */
    protected void btnViewAttachment_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnViewContrnt_actionPerformed method
     */
    protected void btnViewContrnt_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtInviteProject_dataChanged method
     */
    protected void prmtInviteProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtFileTemplate_dataChanged method
     */
    protected void prmtFileTemplate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output treeCategory_valueChanged method
     */
    protected void treeCategory_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output comboAttachmentNameList_actionPerformed method
     */
    protected void comboAttachmentNameList_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("fileItemType"));
        sic.add(new SelectorItemInfo("respDept.*"));
        sic.add(new SelectorItemInfo("inviteProject.*"));
        sic.add(new SelectorItemInfo("fileTemplate.*"));
        sic.add(new SelectorItemInfo("inviteProject.inviteMode"));
        sic.add(new SelectorItemInfo("inviteProject.orgUnit.name"));
        return sic;
    }        
    	

    /**
     * output actionUpLoadFile_actionPerformed method
     */
    public void actionUpLoadFile_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewFile_actionPerformed method
     */
    public void actionViewFile_actionPerformed(ActionEvent e) throws Exception
    {
        //write your code here
    }
    	

    /**
     * output actionViewAttachmentSelf_actionPerformed method
     */
    public void actionViewAttachmentSelf_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewContentSelf_actionPerformed method
     */
    public void actionViewContentSelf_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionUpLoadFile(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpLoadFile() {
    	return false;
    }
	public RequestContext prepareActionViewFile(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewFile() {
    	return false;
    }
	public RequestContext prepareActionViewAttachmentSelf(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewAttachmentSelf() {
    	return false;
    }
	public RequestContext prepareActionViewContentSelf(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewContentSelf() {
    	return false;
    }

    /**
     * output ActionUpLoadFile class
     */     
    protected class ActionUpLoadFile extends ItemAction {     
    
        public ActionUpLoadFile()
        {
            this(null);
        }

        public ActionUpLoadFile(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUpLoadFile.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpLoadFile.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpLoadFile.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteFileItemEditUI.this, "ActionUpLoadFile", "actionUpLoadFile_actionPerformed", e);
        }
    }

    /**
     * output ActionViewFile class
     */     
    protected class ActionViewFile extends ItemAction {     
    
        public ActionViewFile()
        {
            this(null);
        }

        public ActionViewFile(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewFile.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewFile.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewFile.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteFileItemEditUI.this, "ActionViewFile", "actionViewFile_actionPerformed", e);
        }
    }

    /**
     * output ActionViewAttachmentSelf class
     */     
    protected class ActionViewAttachmentSelf extends ItemAction {     
    
        public ActionViewAttachmentSelf()
        {
            this(null);
        }

        public ActionViewAttachmentSelf(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewAttachmentSelf.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAttachmentSelf.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAttachmentSelf.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteFileItemEditUI.this, "ActionViewAttachmentSelf", "actionViewAttachmentSelf_actionPerformed", e);
        }
    }

    /**
     * output ActionViewContentSelf class
     */     
    protected class ActionViewContentSelf extends ItemAction {     
    
        public ActionViewContentSelf()
        {
            this(null);
        }

        public ActionViewContentSelf(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewContentSelf.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContentSelf.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewContentSelf.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteFileItemEditUI.this, "ActionViewContentSelf", "actionViewContentSelf_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "InviteFileItemEditUI");
    }




}