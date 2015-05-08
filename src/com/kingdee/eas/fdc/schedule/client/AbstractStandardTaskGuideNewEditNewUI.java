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
public abstract class AbstractStandardTaskGuideNewEditNewUI extends com.kingdee.eas.fdc.basedata.client.FDCTreeBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractStandardTaskGuideNewEditNewUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLongNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardDuration;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttaskType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtParentNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStandardDuration;
    protected com.kingdee.bos.ctrl.swing.KDComboBox taskTypeSelect;
    protected com.kingdee.bos.ctrl.swing.KDContainer imageContainer;
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDContainer entryBContainer;
    protected com.kingdee.bos.ctrl.swing.KDContainer entryAContainer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable entryB;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable entryA;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractStandardTaskGuideNewEditNewUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractStandardTaskGuideNewEditNewUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLongNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandardDuration = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttaskType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtParentNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtStandardDuration = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.taskTypeSelect = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.imageContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDSplitPane2 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.entryBContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.entryAContainer = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.entryB = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.entryA = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.contLongNumber.setName("contLongNumber");
        this.contStandardDuration.setName("contStandardDuration");
        this.conttaskType.setName("conttaskType");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.txtParentNumber.setName("txtParentNumber");
        this.txtStandardDuration.setName("txtStandardDuration");
        this.taskTypeSelect.setName("taskTypeSelect");
        this.imageContainer.setName("imageContainer");
        this.kDSplitPane2.setName("kDSplitPane2");
        this.kDPanel1.setName("kDPanel1");
        this.entryBContainer.setName("entryBContainer");
        this.entryAContainer.setName("entryAContainer");
        this.entryB.setName("entryB");
        this.entryA.setName("entryA");
        this.txtDescription.setName("txtDescription");
        // CoreUI		
        this.setMaximumSize(new Dimension(792,466));		
        this.setMinimumSize(new Dimension(792,466));		
        this.setPreferredSize(new Dimension(792,466));		
        this.btnSave.setToolTipText(resHelper.getString("btnSave.toolTipText"));		
        this.btnSubmit.setText(resHelper.getString("btnSubmit.text"));		
        this.btnSubmit.setVisible(false);		
        this.rMenuItemSubmitAndAddNew.setRequestFocusEnabled(false);		
        this.rMenuItemSubmitAndAddNew.setVerifyInputWhenFocusTarget(false);		
        this.rMenuItemSubmitAndAddNew.setBorderPainted(false);		
        this.rMenuItemSubmitAndAddNew.setContentAreaFilled(false);		
        this.menuSubmitOption.setVisible(false);		
        this.menuSubmitOption.setEnabled(false);		
        this.chkMenuItemSubmitAndAddNew.setVisible(false);		
        this.chkMenuItemSubmitAndAddNew.setEnabled(false);		
        this.chkMenuItemSubmitAndAddNew.setBorderPainted(false);		
        this.chkMenuItemSubmitAndAddNew.setContentAreaFilled(false);		
        this.chkMenuItemSubmitAndAddNew.setOpaque(false);		
        this.chkMenuItemSubmitAndAddNew.setRequestFocusEnabled(false);		
        this.chkMenuItemSubmitAndAddNew.setVerifyInputWhenFocusTarget(false);		
        this.chkMenuItemSubmitAndPrint.setVisible(false);		
        this.chkMenuItemSubmitAndPrint.setEnabled(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(70);		
        this.contName.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(80);		
        this.contNumber.setBoundLabelUnderline(true);
        // contLongNumber		
        this.contLongNumber.setBoundLabelText(resHelper.getString("contLongNumber.boundLabelText"));		
        this.contLongNumber.setBoundLabelLength(100);		
        this.contLongNumber.setBoundLabelUnderline(true);		
        this.contLongNumber.setVisible(false);
        // contStandardDuration		
        this.contStandardDuration.setBoundLabelText(resHelper.getString("contStandardDuration.boundLabelText"));		
        this.contStandardDuration.setBoundLabelLength(80);		
        this.contStandardDuration.setBoundLabelUnderline(true);
        // conttaskType		
        this.conttaskType.setBoundLabelText(resHelper.getString("conttaskType.boundLabelText"));		
        this.conttaskType.setBoundLabelLength(70);		
        this.conttaskType.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerSize(2);		
        this.kDSplitPane1.setDividerLocation(400);
        // kDScrollPane1
        // txtName		
        this.txtName.setMaxLength(100);		
        this.txtName.setRequired(true);
        // txtNumber		
        this.txtNumber.setMaxLength(4);		
        this.txtNumber.setRequired(true);
        // txtParentNumber		
        this.txtParentNumber.setEnabled(false);
        // txtStandardDuration
        // taskTypeSelect		
        this.taskTypeSelect.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum").toArray());
        // imageContainer		
        this.imageContainer.setTitle(resHelper.getString("imageContainer.title"));		
        this.imageContainer.setEnableActive(false);
        // kDSplitPane2		
        this.kDSplitPane2.setOrientation(0);		
        this.kDSplitPane2.setDividerLocation(150);		
        this.kDSplitPane2.setDividerSize(1);
        // kDPanel1
        // entryBContainer		
        this.entryBContainer.setEnableActive(false);		
        this.entryBContainer.setTitle(resHelper.getString("entryBContainer.title"));		
        this.entryBContainer.setAutoscrolls(true);
        // entryAContainer		
        this.entryAContainer.setEnableActive(false);		
        this.entryAContainer.setTitle(resHelper.getString("entryAContainer.title"));		
        this.entryAContainer.setAutoscrolls(true);
        // entryB
		String entryBStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"docPath\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"docName\" t:width=\"260\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"docID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol2\" /><t:Column t:key=\"ID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"guideType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"isCPFile\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"id\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">文档目录</t:Cell><t:Cell t:configured=\"false\">文档名称</t:Cell><t:Cell t:configured=\"false\">附件ID</t:Cell><t:Cell t:configured=\"false\">id</t:Cell><t:Cell t:configured=\"false\">文件类别</t:Cell><t:Cell t:configured=\"false\">是否协同文档</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.entryB.setFormatXml(resHelper.translateString("entryB",entryBStrXML));
        this.entryB.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    entryB_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // entryA
		String entryAStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"docPath\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"docName\" t:width=\"260\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"docID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol2\" /><t:Column t:key=\"ID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"guideType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"isCPFile\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">文档目录</t:Cell><t:Cell t:configured=\"false\">文档名称</t:Cell><t:Cell t:configured=\"false\">附件ID</t:Cell><t:Cell t:configured=\"false\">id</t:Cell><t:Cell t:configured=\"false\">文件类别</t:Cell><t:Cell t:configured=\"false\">是否协同文档</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.entryA.setFormatXml(resHelper.translateString("entryA",entryAStrXML));
        this.entryA.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    entryA_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // txtDescription		
        this.txtDescription.setMaxLength(200);
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
        this.setBounds(new Rectangle(10, 10, 792, 466));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 792, 466));
        contName.setBounds(new Rectangle(10, 9, 280, 19));
        this.add(contName, new KDLayout.Constraints(10, 9, 280, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(504, 9, 280, 19));
        this.add(contNumber, new KDLayout.Constraints(504, 9, 280, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLongNumber.setBounds(new Rectangle(257, 59, 270, 19));
        this.add(contLongNumber, new KDLayout.Constraints(257, 59, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contStandardDuration.setBounds(new Rectangle(504, 38, 280, 19));
        this.add(contStandardDuration, new KDLayout.Constraints(504, 38, 280, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        conttaskType.setBounds(new Rectangle(10, 38, 280, 19));
        this.add(conttaskType, new KDLayout.Constraints(10, 38, 280, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(10, 65, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(10, 65, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSplitPane1.setBounds(new Rectangle(12, 135, 769, 322));
        this.add(kDSplitPane1, new KDLayout.Constraints(12, 135, 769, 322, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDScrollPane1.setBounds(new Rectangle(12, 88, 770, 37));
        this.add(kDScrollPane1, new KDLayout.Constraints(12, 88, 770, 37, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contLongNumber
        contLongNumber.setBoundEditor(txtParentNumber);
        //contStandardDuration
        contStandardDuration.setBoundEditor(txtStandardDuration);
        //conttaskType
        conttaskType.setBoundEditor(taskTypeSelect);
        //kDSplitPane1
        kDSplitPane1.add(imageContainer, "right");
        kDSplitPane1.add(kDSplitPane2, "left");
        //imageContainer
imageContainer.getContentPane().setLayout(new BorderLayout(0, 0));        imageContainer.getContentPane().add(kDPanel1, BorderLayout.CENTER);
        kDPanel1.setLayout(null);        //kDSplitPane2
        kDSplitPane2.add(entryBContainer, "bottom");
        kDSplitPane2.add(entryAContainer, "top");
        //entryBContainer
entryBContainer.getContentPane().setLayout(new BorderLayout(0, 0));        entryBContainer.getContentPane().add(entryB, BorderLayout.CENTER);
        //entryAContainer
entryAContainer.getContentPane().setLayout(new BorderLayout(0, 0));        entryAContainer.getContentPane().add(entryA, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);

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
        this.menuBar.add(menuTool);
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
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
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
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("standardDuration", java.math.BigDecimal.class, this.txtStandardDuration, "value");
		dataBinder.registerBinding("taskType", com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum.class, this.taskTypeSelect, "selectedItem");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.StandardTaskGuideNewEditNewUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo)ov;
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
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("standardDuration", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskType", ValidateHelper.ON_SAVE);    
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
        }
    }

    /**
     * output entryB_tableClicked method
     */
    protected void entryB_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output entryA_tableClicked method
     */
    protected void entryA_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("standardDuration"));
        sic.add(new SelectorItemInfo("taskType"));
        sic.add(new SelectorItemInfo("description"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "StandardTaskGuideNewEditNewUI");
    }




}