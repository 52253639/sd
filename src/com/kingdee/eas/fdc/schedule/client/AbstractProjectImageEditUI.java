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
public abstract class AbstractProjectImageEditUI extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectImageEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddImage;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteImage;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSchedule;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRelateTask;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSubmitTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsKeyCert;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton noKeyCert;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton KeyCert;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDTextField number;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextField name;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox creator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox relationTask;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker creatorTime;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctIMG;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntries;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlIMG;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.eas.fdc.schedule.ProjectImageInfo editData = null;
    protected ActionAddImage actionAddImage = null;
    protected ActionDeleteImage actionDeleteImage = null;
    protected ActionSavePic actionSavePic = null;
    /**
     * output class constructor
     */
    public AbstractProjectImageEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProjectImageEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddImage
        this.actionAddImage = new ActionAddImage(this);
        getActionManager().registerAction("actionAddImage", actionAddImage);
         this.actionAddImage.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeleteImage
        this.actionDeleteImage = new ActionDeleteImage(this);
        getActionManager().registerAction("actionDeleteImage", actionDeleteImage);
         this.actionDeleteImage.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSavePic
        this.actionSavePic = new ActionSavePic(this);
        getActionManager().registerAction("actionSavePic", actionSavePic);
         this.actionSavePic.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.btnAddImage = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteImage = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contSchedule = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRelateTask = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSubmitTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIsKeyCert = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.noKeyCert = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.KeyCert = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.number = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.name = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.creator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.relationTask = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.creatorTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.ctIMG = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.pnlIMG = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.btnAddImage.setName("btnAddImage");
        this.btnDeleteImage.setName("btnDeleteImage");
        this.contSchedule.setName("contSchedule");
        this.contPerson.setName("contPerson");
        this.contRelateTask.setName("contRelateTask");
        this.contSubmitTime.setName("contSubmitTime");
        this.contIsKeyCert.setName("contIsKeyCert");
        this.noKeyCert.setName("noKeyCert");
        this.KeyCert.setName("KeyCert");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.number.setName("number");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.name.setName("name");
        this.creator.setName("creator");
        this.relationTask.setName("relationTask");
        this.creatorTime.setName("creatorTime");
        this.kDContainer1.setName("kDContainer1");
        this.ctIMG.setName("ctIMG");
        this.kdtEntries.setName("kdtEntries");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.pnlIMG.setName("pnlIMG");
        this.txtDescription.setName("txtDescription");
        // CoreUI		
        this.menuSubmitOption.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnNextPerson.setVisible(false);		
        this.btnWorkFlowG.setVisible(false);
        // btnAddImage
        this.btnAddImage.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddImage.setText(resHelper.getString("btnAddImage.text"));		
        this.btnAddImage.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));
        this.btnAddImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddImage_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDeleteImage
        this.btnDeleteImage.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDeleteImage.setText(resHelper.getString("btnDeleteImage.text"));		
        this.btnDeleteImage.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));
        // contSchedule		
        this.contSchedule.setBoundLabelText(resHelper.getString("contSchedule.boundLabelText"));		
        this.contSchedule.setBoundLabelUnderline(true);		
        this.contSchedule.setBoundLabelLength(100);
        // contPerson		
        this.contPerson.setBoundLabelText(resHelper.getString("contPerson.boundLabelText"));		
        this.contPerson.setBoundLabelLength(100);		
        this.contPerson.setBoundLabelUnderline(true);
        // contRelateTask		
        this.contRelateTask.setBoundLabelText(resHelper.getString("contRelateTask.boundLabelText"));		
        this.contRelateTask.setBoundLabelLength(100);		
        this.contRelateTask.setBoundLabelUnderline(true);
        // contSubmitTime		
        this.contSubmitTime.setBoundLabelText(resHelper.getString("contSubmitTime.boundLabelText"));		
        this.contSubmitTime.setBoundLabelLength(100);		
        this.contSubmitTime.setBoundLabelUnderline(true);
        // contIsKeyCert		
        this.contIsKeyCert.setBoundLabelText(resHelper.getString("contIsKeyCert.boundLabelText"));		
        this.contIsKeyCert.setBoundLabelUnderline(true);
        // noKeyCert		
        this.noKeyCert.setText(resHelper.getString("noKeyCert.text"));		
        this.noKeyCert.setFocusPainted(false);		
        this.noKeyCert.setSelected(true);
        // KeyCert		
        this.KeyCert.setText(resHelper.getString("KeyCert.text"));		
        this.KeyCert.setFocusPainted(false);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(300);
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.noKeyCert);
        this.kDButtonGroup1.add(this.KeyCert);
        // number		
        this.number.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // kDScrollPane2
        // name		
        this.name.setRequired(true);
        this.name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    name_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // creator		
        this.creator.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");		
        this.creator.setReadOnly(true);		
        this.creator.setEnabled(false);
        // relationTask		
        this.relationTask.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7ScheduleTaskQueryForProjectImage");
        // creatorTime		
        this.creatorTime.setEditable(false);		
        this.creatorTime.setEnabled(false);
        // kDContainer1		
        this.kDContainer1.setEnableActive(false);		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // ctIMG		
        this.ctIMG.setTitle(resHelper.getString("ctIMG.title"));		
        this.ctIMG.setEnableActive(false);
        // kdtEntries
		String kdtEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"fileName\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"file\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"size\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"sizeInByte\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{fileName}</t:Cell><t:Cell>$Resource{file}</t:Cell><t:Cell>$Resource{size}</t:Cell><t:Cell>$Resource{sizeInByte}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntries.setFormatXml(resHelper.translateString("kdtEntries",kdtEntriesStrXML));
        this.kdtEntries.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    entries_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEntries.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    entries_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntries.putBindContents("editData",new String[] {"","name","fileName","","size","sizeInByte"});


        // kDScrollPane1
        // pnlIMG
        // txtDescription
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
        this.setBounds(new Rectangle(10, 10, 800, 634));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 800, 634));
        btnAddImage.setBounds(new Rectangle(51, 428, 22, 19));
        this.add(btnAddImage, new KDLayout.Constraints(51, 428, 22, 19, 0));
        btnDeleteImage.setBounds(new Rectangle(95, 429, 22, 19));
        this.add(btnDeleteImage, new KDLayout.Constraints(95, 429, 22, 19, 0));
        contSchedule.setBounds(new Rectangle(10, 8, 270, 19));
        this.add(contSchedule, new KDLayout.Constraints(10, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contPerson.setBounds(new Rectangle(10, 35, 270, 19));
        this.add(contPerson, new KDLayout.Constraints(10, 35, 270, 19, 0));
        contRelateTask.setBounds(new Rectangle(520, 8, 270, 19));
        this.add(contRelateTask, new KDLayout.Constraints(520, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSubmitTime.setBounds(new Rectangle(520, 35, 270, 19));
        this.add(contSubmitTime, new KDLayout.Constraints(520, 35, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contIsKeyCert.setBounds(new Rectangle(10, 62, 100, 19));
        this.add(contIsKeyCert, new KDLayout.Constraints(10, 62, 100, 19, 0));
        noKeyCert.setBounds(new Rectangle(243, 65, 38, 19));
        this.add(noKeyCert, new KDLayout.Constraints(243, 65, 38, 19, 0));
        KeyCert.setBounds(new Rectangle(173, 65, 34, 19));
        this.add(KeyCert, new KDLayout.Constraints(173, 65, 34, 19, 0));
        kDSplitPane1.setBounds(new Rectangle(11, 200, 780, 429));
        this.add(kDSplitPane1, new KDLayout.Constraints(11, 200, 780, 429, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        number.setBounds(new Rectangle(619, 73, 170, 19));
        this.add(number, new KDLayout.Constraints(619, 73, 170, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(10, 91, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(10, 91, 270, 19, 0));
        kDScrollPane2.setBounds(new Rectangle(10, 116, 780, 72));
        this.add(kDScrollPane2, new KDLayout.Constraints(10, 116, 780, 72, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contSchedule
        contSchedule.setBoundEditor(name);
        //contPerson
        contPerson.setBoundEditor(creator);
        //contRelateTask
        contRelateTask.setBoundEditor(relationTask);
        //contSubmitTime
        contSubmitTime.setBoundEditor(creatorTime);
        //kDSplitPane1
        kDSplitPane1.add(kDContainer1, "left");
        kDSplitPane1.add(ctIMG, "right");
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kdtEntries, BorderLayout.CENTER);
        //ctIMG
ctIMG.getContentPane().setLayout(new BorderLayout(0, 0));        ctIMG.getContentPane().add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(pnlIMG, null);
pnlIMG.setLayout(new BorderLayout(0, 0));        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtDescription, null);

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
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
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
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
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
        this.toolBar.add(btnNumberSign);
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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.number, "text");
		dataBinder.registerBinding("name", String.class, this.name, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.creator, "data");
		dataBinder.registerBinding("relateTask", com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo.class, this.relationTask, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.creatorTime, "value");
		dataBinder.registerBinding("entries.size", String.class, this.kdtEntries, "size.text");
		dataBinder.registerBinding("entries.sizeInByte", int.class, this.kdtEntries, "sizeInByte.text");
		dataBinder.registerBinding("entries.fileName", String.class, this.kdtEntries, "fileName.text");
		dataBinder.registerBinding("entries.name", String.class, this.kdtEntries, "name.text");
		dataBinder.registerBinding("entries", com.kingdee.eas.fdc.schedule.ProjectImageEntryInfo.class, this.kdtEntries, "userObject");
		dataBinder.registerBinding("imgDescription", String.class, this.txtDescription, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.ProjectImageEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.ProjectImageInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("relateTask", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.size", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.sizeInByte", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.fileName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entries", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("imgDescription", ValidateHelper.ON_SAVE);    		
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
     * output btnAddImage_actionPerformed method
     */
    protected void btnAddImage_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output name_actionPerformed method
     */
    protected void name_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output entries_editStopped method
     */
    protected void entries_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output entries_tableSelectChanged method
     */
    protected void entries_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("relateTask.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("relateTask.id"));
        	sic.add(new SelectorItemInfo("relateTask.number"));
        	sic.add(new SelectorItemInfo("relateTask.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
    	sic.add(new SelectorItemInfo("entries.size"));
    	sic.add(new SelectorItemInfo("entries.sizeInByte"));
    	sic.add(new SelectorItemInfo("entries.fileName"));
    	sic.add(new SelectorItemInfo("entries.name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entries.*"));
		}
		else{
		}
        sic.add(new SelectorItemInfo("imgDescription"));
        return sic;
    }        
    	

    /**
     * output actionAddImage_actionPerformed method
     */
    public void actionAddImage_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeleteImage_actionPerformed method
     */
    public void actionDeleteImage_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSavePic_actionPerformed method
     */
    public void actionSavePic_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAddImage(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddImage() {
    	return false;
    }
	public RequestContext prepareActionDeleteImage(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeleteImage() {
    	return false;
    }
	public RequestContext prepareActionSavePic(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSavePic() {
    	return false;
    }

    /**
     * output ActionAddImage class
     */     
    protected class ActionAddImage extends ItemAction {     
    
        public ActionAddImage()
        {
            this(null);
        }

        public ActionAddImage(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddImage.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddImage.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddImage.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectImageEditUI.this, "ActionAddImage", "actionAddImage_actionPerformed", e);
        }
    }

    /**
     * output ActionDeleteImage class
     */     
    protected class ActionDeleteImage extends ItemAction {     
    
        public ActionDeleteImage()
        {
            this(null);
        }

        public ActionDeleteImage(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDeleteImage.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteImage.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteImage.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectImageEditUI.this, "ActionDeleteImage", "actionDeleteImage_actionPerformed", e);
        }
    }

    /**
     * output ActionSavePic class
     */     
    protected class ActionSavePic extends ItemAction {     
    
        public ActionSavePic()
        {
            this(null);
        }

        public ActionSavePic(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSavePic.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSavePic.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSavePic.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProjectImageEditUI.this, "ActionSavePic", "actionSavePic_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "ProjectImageEditUI");
    }




}