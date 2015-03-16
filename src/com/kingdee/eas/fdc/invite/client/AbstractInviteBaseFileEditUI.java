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
public abstract class AbstractInviteBaseFileEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractInviteBaseFileEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer ctnNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer ctnName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer ctnInviteType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer ctnVerNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblCreatTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lblAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnModel;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtVerNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditDate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblModel;
    protected com.kingdee.bos.ctrl.swing.KDTextArea areaDesc;
    protected com.kingdee.bos.ctrl.swing.KDTextArea areaDescription;
    protected com.kingdee.eas.fdc.invite.InviteBaseFileInfo editData = null;
    protected ActionEditLine actionEditLine = null;
    /**
     * output class constructor
     */
    public AbstractInviteBaseFileEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractInviteBaseFileEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionEditLine
        this.actionEditLine = new ActionEditLine(this);
        getActionManager().registerAction("actionEditLine", actionEditLine);
         this.actionEditLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.ctnNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.ctnName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.ctnInviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.ctnVerNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblCreatTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.ctnModel = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtInviteType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtVerNumber = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.tblModel = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.areaDesc = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.areaDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.ctnNumber.setName("ctnNumber");
        this.ctnName.setName("ctnName");
        this.ctnInviteType.setName("ctnInviteType");
        this.ctnVerNumber.setName("ctnVerNumber");
        this.lblCreator.setName("lblCreator");
        this.lblCreatTime.setName("lblCreatTime");
        this.lblAuditor.setName("lblAuditor");
        this.lblAuditTime.setName("lblAuditTime");
        this.ctnModel.setName("ctnModel");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDLabel1.setName("kDLabel1");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.kDLabel2.setName("kDLabel2");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.prmtInviteType.setName("prmtInviteType");
        this.txtVerNumber.setName("txtVerNumber");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateDate.setName("pkCreateDate");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditDate.setName("pkAuditDate");
        this.tblModel.setName("tblModel");
        this.areaDesc.setName("areaDesc");
        this.areaDescription.setName("areaDescription");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnCancelCancel.setVisible(true);		
        this.btnCancelCancel.setEnabled(true);		
        this.btnCancel.setEnabled(true);		
        this.btnCancel.setVisible(true);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceUp.setEnabled(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnTraceDown.setEnabled(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceUp.setEnabled(false);		
        this.menuItemTraceDown.setEnabled(false);		
        this.menuItemTraceDown.setVisible(false);
        // ctnNumber		
        this.ctnNumber.setBoundLabelText(resHelper.getString("ctnNumber.boundLabelText"));		
        this.ctnNumber.setBoundLabelUnderline(true);		
        this.ctnNumber.setBoundLabelLength(100);
        // ctnName		
        this.ctnName.setBoundLabelText(resHelper.getString("ctnName.boundLabelText"));		
        this.ctnName.setBoundLabelUnderline(true);		
        this.ctnName.setBoundLabelLength(100);
        // ctnInviteType		
        this.ctnInviteType.setBoundLabelText(resHelper.getString("ctnInviteType.boundLabelText"));		
        this.ctnInviteType.setBoundLabelUnderline(true);		
        this.ctnInviteType.setBoundLabelLength(100);
        // ctnVerNumber		
        this.ctnVerNumber.setBoundLabelText(resHelper.getString("ctnVerNumber.boundLabelText"));		
        this.ctnVerNumber.setBoundLabelUnderline(true);		
        this.ctnVerNumber.setBoundLabelLength(100);		
        this.ctnVerNumber.setEnabled(false);		
        this.ctnVerNumber.setVisible(false);
        // lblCreator		
        this.lblCreator.setBoundLabelText(resHelper.getString("lblCreator.boundLabelText"));		
        this.lblCreator.setBoundLabelLength(100);		
        this.lblCreator.setBoundLabelUnderline(true);		
        this.lblCreator.setEnabled(false);
        // lblCreatTime		
        this.lblCreatTime.setBoundLabelText(resHelper.getString("lblCreatTime.boundLabelText"));		
        this.lblCreatTime.setBoundLabelLength(100);		
        this.lblCreatTime.setBoundLabelUnderline(true);		
        this.lblCreatTime.setEnabled(false);
        // lblAuditor		
        this.lblAuditor.setBoundLabelText(resHelper.getString("lblAuditor.boundLabelText"));		
        this.lblAuditor.setBoundLabelLength(100);		
        this.lblAuditor.setBoundLabelUnderline(true);		
        this.lblAuditor.setEnabled(false);
        // lblAuditTime		
        this.lblAuditTime.setBoundLabelText(resHelper.getString("lblAuditTime.boundLabelText"));		
        this.lblAuditTime.setBoundLabelUnderline(true);		
        this.lblAuditTime.setBoundLabelLength(100);		
        this.lblAuditTime.setEnabled(false);
        // ctnModel		
        this.ctnModel.setTitle(resHelper.getString("ctnModel.title"));
        // kDScrollPane1
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDScrollPane2
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // txtNumber		
        this.txtNumber.setRequired(true);
        // txtName		
        this.txtName.setRequired(true);
        // prmtInviteType		
        this.prmtInviteType.setEnabled(false);		
        this.prmtInviteType.setDisplayFormat("$name$");		
        this.prmtInviteType.setEditFormat("$number$");		
        this.prmtInviteType.setCommitFormat("$number$");
        // txtVerNumber		
        this.txtVerNumber.setEnabled(false);		
        this.txtVerNumber.setDataType(5);		
        this.txtVerNumber.setPrecision(2);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateDate		
        this.pkCreateDate.setEnabled(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // pkAuditDate		
        this.pkAuditDate.setEnabled(false);
        // tblModel
		String tblModelStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"fileName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creatorName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"version\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fileType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{fileName}</t:Cell><t:Cell>$Resource{creatorName}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{version}</t:Cell><t:Cell>$Resource{fileType}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblModel.setFormatXml(resHelper.translateString("tblModel",tblModelStrXML));
        this.tblModel.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblModel_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblModel.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblModel_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // areaDesc
        this.areaDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                try {
                    areaDesc_keyReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void keyPressed(java.awt.event.KeyEvent e) {
                try {
                    areaDesc_keyPressed(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void keyTyped(java.awt.event.KeyEvent e) {
                try {
                    areaDesc_keyTyped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // areaDescription
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
        ctnNumber.setBounds(new Rectangle(18, 13, 270, 19));
        this.add(ctnNumber, new KDLayout.Constraints(18, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        ctnName.setBounds(new Rectangle(371, 13, 270, 19));
        this.add(ctnName, new KDLayout.Constraints(371, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        ctnInviteType.setBounds(new Rectangle(724, 13, 270, 19));
        this.add(ctnInviteType, new KDLayout.Constraints(724, 13, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        ctnVerNumber.setBounds(new Rectangle(17, 40, 270, 19));
        this.add(ctnVerNumber, new KDLayout.Constraints(17, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lblCreator.setBounds(new Rectangle(17, 571, 420, 19));
        this.add(lblCreator, new KDLayout.Constraints(17, 571, 420, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lblCreatTime.setBounds(new Rectangle(17, 595, 420, 19));
        this.add(lblCreatTime, new KDLayout.Constraints(17, 595, 420, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        lblAuditor.setBounds(new Rectangle(574, 572, 420, 19));
        this.add(lblAuditor, new KDLayout.Constraints(574, 572, 420, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        lblAuditTime.setBounds(new Rectangle(574, 595, 420, 19));
        this.add(lblAuditTime, new KDLayout.Constraints(574, 595, 420, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        ctnModel.setBounds(new Rectangle(17, 69, 977, 141));
        this.add(ctnModel, new KDLayout.Constraints(17, 69, 977, 141, 0));
        kDScrollPane1.setBounds(new Rectangle(17, 240, 977, 110));
        this.add(kDScrollPane1, new KDLayout.Constraints(17, 240, 977, 110, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabel1.setBounds(new Rectangle(17, 216, 100, 19));
        this.add(kDLabel1, new KDLayout.Constraints(17, 216, 100, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        kDScrollPane2.setBounds(new Rectangle(17, 378, 977, 150));
        this.add(kDScrollPane2, new KDLayout.Constraints(17, 378, 977, 150, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabel2.setBounds(new Rectangle(17, 357, 100, 19));
        this.add(kDLabel2, new KDLayout.Constraints(17, 357, 100, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //ctnNumber
        ctnNumber.setBoundEditor(txtNumber);
        //ctnName
        ctnName.setBoundEditor(txtName);
        //ctnInviteType
        ctnInviteType.setBoundEditor(prmtInviteType);
        //ctnVerNumber
        ctnVerNumber.setBoundEditor(txtVerNumber);
        //lblCreator
        lblCreator.setBoundEditor(prmtCreator);
        //lblCreatTime
        lblCreatTime.setBoundEditor(pkCreateDate);
        //lblAuditor
        lblAuditor.setBoundEditor(prmtAuditor);
        //lblAuditTime
        lblAuditTime.setBoundEditor(pkAuditDate);
        //ctnModel
ctnModel.getContentPane().setLayout(new BorderLayout(0, 0));        ctnModel.getContentPane().add(tblModel, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(areaDesc, null);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(areaDescription, null);

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
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
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
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCopyLine);
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
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("inviteType", com.kingdee.eas.fdc.invite.InviteTypeInfo.class, this.prmtInviteType, "data");
		dataBinder.registerBinding("verNumber", int.class, this.txtVerNumber, "value");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateDate, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditDate, "value");
		dataBinder.registerBinding("description", String.class, this.areaDescription, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.InviteBaseFileEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.InviteBaseFileInfo)ov;
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
		getValidateHelper().registerBindProperty("verNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
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
     * output tblModel_tableSelectChanged method
     */
    protected void tblModel_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output tblModel_tableClicked method
     */
    protected void tblModel_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output areaDesc_keyReleased method
     */
    protected void areaDesc_keyReleased(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output areaDesc_keyPressed method
     */
    protected void areaDesc_keyPressed(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output areaDesc_keyTyped method
     */
    protected void areaDesc_keyTyped(java.awt.event.KeyEvent e) throws Exception
    {
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
        sic.add(new SelectorItemInfo("verNumber"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("description"));
        return sic;
    }        
    	

    /**
     * output actionEditLine_actionPerformed method
     */
    public void actionEditLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionEditLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditLine() {
    	return false;
    }

    /**
     * output ActionEditLine class
     */     
    protected class ActionEditLine extends ItemAction {     
    
        public ActionEditLine()
        {
            this(null);
        }

        public ActionEditLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionEditLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractInviteBaseFileEditUI.this, "ActionEditLine", "actionEditLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "InviteBaseFileEditUI");
    }




}