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
public abstract class AbstractCompensateSchemeEditUI extends com.kingdee.eas.framework.client.BillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCompensateSchemeEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlScheme;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctnEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCalcWay;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompensateType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDecimalCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDecimaltype;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCalcWay;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCompensateType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDecimalCount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboDecimalType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreatTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractCompensateSchemeEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCompensateSchemeEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.pnlScheme = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.ctnEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCalcWay = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contCompensateType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDecimalCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDecimaltype = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboCalcWay = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboCompensateType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtDecimalCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboDecimalType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreatTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.pnlScheme.setName("pnlScheme");
        this.ctnEntry.setName("ctnEntry");
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contCalcWay.setName("contCalcWay");
        this.contDescription.setName("contDescription");
        this.kDLabel1.setName("kDLabel1");
        this.contCompensateType.setName("contCompensateType");
        this.contDecimalCount.setName("contDecimalCount");
        this.contDecimaltype.setName("contDecimaltype");
        this.contSellProject.setName("contSellProject");
        this.contCreateTime.setName("contCreateTime");
        this.contCreator.setName("contCreator");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.comboCalcWay.setName("comboCalcWay");
        this.txtDescription.setName("txtDescription");
        this.comboCompensateType.setName("comboCompensateType");
        this.txtDecimalCount.setName("txtDecimalCount");
        this.comboDecimalType.setName("comboDecimalType");
        this.prmtSellProject.setName("prmtSellProject");
        this.pkCreatTime.setName("pkCreatTime");
        this.prmtCreator.setName("prmtCreator");
        this.kdtEntrys.setName("kdtEntrys");
        // CoreUI		
        this.setPreferredSize(new Dimension(600,440));
        // pnlScheme		
        this.pnlScheme.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlScheme.border.title")));		
        this.pnlScheme.setPreferredSize(new Dimension(600,10));		
        this.pnlScheme.setMinimumSize(new Dimension(600,10));
        // ctnEntry		
        this.ctnEntry.setTitle(resHelper.getString("ctnEntry.title"));		
        this.ctnEntry.setEnableActive(false);		
        this.ctnEntry.setPreferredSize(new Dimension(600,29));		
        this.ctnEntry.setMinimumSize(new Dimension(600,19));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contCalcWay		
        this.contCalcWay.setBoundLabelText(resHelper.getString("contCalcWay.boundLabelText"));		
        this.contCalcWay.setBoundLabelLength(100);		
        this.contCalcWay.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));		
        this.kDLabel1.setBackground(new java.awt.Color(255,255,255));		
        this.kDLabel1.setForeground(new java.awt.Color(255,0,0));
        // contCompensateType		
        this.contCompensateType.setBoundLabelText(resHelper.getString("contCompensateType.boundLabelText"));		
        this.contCompensateType.setBoundLabelLength(100);		
        this.contCompensateType.setBoundLabelUnderline(true);
        // contDecimalCount		
        this.contDecimalCount.setBoundLabelText(resHelper.getString("contDecimalCount.boundLabelText"));		
        this.contDecimalCount.setBoundLabelLength(100);		
        this.contDecimalCount.setBoundLabelUnderline(true);
        // contDecimaltype		
        this.contDecimaltype.setBoundLabelLength(100);		
        this.contDecimaltype.setBoundLabelUnderline(true);		
        this.contDecimaltype.setBoundLabelText(resHelper.getString("contDecimaltype.boundLabelText"));
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);		
        this.contSellProject.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setRequired(true);		
        this.txtName.setPreferredSize(new Dimension(600,22));
        // comboCalcWay		
        this.comboCalcWay.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CalcWayEnum").toArray());		
        this.comboCalcWay.setRequired(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // comboCompensateType		
        this.comboCompensateType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.CompensateTypeEnum").toArray());		
        this.comboCompensateType.setRequired(true);
        // txtDecimalCount		
        this.txtDecimalCount.setRequired(true);
        // comboDecimalType		
        this.comboDecimalType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.DecimalTypeEnum").toArray());		
        this.comboDecimalType.setRequired(true);
        // prmtSellProject		
        this.prmtSellProject.setEnabled(false);
        // pkCreatTime		
        this.pkCreatTime.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sTable\"><c:Alignment horizontal=\"left\" /><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\" t:styleID=\"sTable\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"calcTerms\" t:width=\"170\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" /><t:Column t:key=\"isCompensate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" /><t:Column t:key=\"calcFormula\" t:width=\"170\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"minValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"minCompareType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol6\" /><t:Column t:key=\"maxValue\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol7\" /><t:Column t:key=\"maxCompareType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol8\" /><t:Column t:key=\"factor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{calcTerms}</t:Cell><t:Cell>$Resource{isCompensate}</t:Cell><t:Cell>$Resource{calcFormula}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{minValue}</t:Cell><t:Cell>$Resource{minCompareType}</t:Cell><t:Cell>$Resource{maxValue}</t:Cell><t:Cell>$Resource{maxCompareType}</t:Cell><t:Cell>$Resource{factor}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));

                this.kdtEntrys.putBindContents("editData",new String[] {"id","calcTerms","isCompensate","calcFormula","description","minValue","minCompareType","maxValue","maxCompareType","factor"});


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
        this.setBounds(new Rectangle(10, 10, 600, 440));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 600, 440));
        pnlScheme.setBounds(new Rectangle(0, 5, 600, 173));
        this.add(pnlScheme, new KDLayout.Constraints(0, 5, 600, 173, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        ctnEntry.setBounds(new Rectangle(5, 176, 590, 254));
        this.add(ctnEntry, new KDLayout.Constraints(5, 176, 590, 254, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlScheme
        pnlScheme.setLayout(new KDLayout());
        pnlScheme.putClientProperty("OriginalBounds", new Rectangle(0, 5, 600, 173));        contNumber.setBounds(new Rectangle(15, 15, 270, 19));
        pnlScheme.add(contNumber, new KDLayout.Constraints(15, 15, 270, 19, 0));
        contName.setBounds(new Rectangle(315, 15, 270, 19));
        pnlScheme.add(contName, new KDLayout.Constraints(315, 15, 270, 19, 0));
        contCalcWay.setBounds(new Rectangle(315, 40, 270, 19));
        pnlScheme.add(contCalcWay, new KDLayout.Constraints(315, 40, 270, 19, 0));
        contDescription.setBounds(new Rectangle(315, 118, 270, 19));
        pnlScheme.add(contDescription, new KDLayout.Constraints(315, 118, 270, 19, 0));
        kDLabel1.setBounds(new Rectangle(15, 141, 570, 19));
        pnlScheme.add(kDLabel1, new KDLayout.Constraints(15, 141, 570, 19, 0));
        contCompensateType.setBounds(new Rectangle(15, 40, 270, 19));
        pnlScheme.add(contCompensateType, new KDLayout.Constraints(15, 40, 270, 19, 0));
        contDecimalCount.setBounds(new Rectangle(15, 66, 270, 19));
        pnlScheme.add(contDecimalCount, new KDLayout.Constraints(15, 66, 270, 19, 0));
        contDecimaltype.setBounds(new Rectangle(315, 66, 270, 19));
        pnlScheme.add(contDecimaltype, new KDLayout.Constraints(315, 66, 270, 19, 0));
        contSellProject.setBounds(new Rectangle(15, 92, 270, 19));
        pnlScheme.add(contSellProject, new KDLayout.Constraints(15, 92, 270, 19, 0));
        contCreateTime.setBounds(new Rectangle(15, 118, 270, 19));
        pnlScheme.add(contCreateTime, new KDLayout.Constraints(15, 118, 270, 19, 0));
        contCreator.setBounds(new Rectangle(315, 92, 270, 19));
        pnlScheme.add(contCreator, new KDLayout.Constraints(315, 92, 270, 19, 0));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contCalcWay
        contCalcWay.setBoundEditor(comboCalcWay);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contCompensateType
        contCompensateType.setBoundEditor(comboCompensateType);
        //contDecimalCount
        contDecimalCount.setBoundEditor(txtDecimalCount);
        //contDecimaltype
        contDecimaltype.setBoundEditor(comboDecimalType);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreatTime);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //ctnEntry
ctnEntry.getContentPane().setLayout(new BorderLayout(0, 0));        ctnEntry.getContentPane().add(kdtEntrys, BorderLayout.CENTER);

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


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("calcWay", com.kingdee.eas.fdc.sellhouse.CalcWayEnum.class, this.comboCalcWay, "selectedItem");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("compensateType", com.kingdee.eas.fdc.sellhouse.CompensateTypeEnum.class, this.comboCompensateType, "selectedItem");
		dataBinder.registerBinding("decimalCount", int.class, this.txtDecimalCount, "value");
		dataBinder.registerBinding("decimalType", com.kingdee.eas.fdc.sellhouse.DecimalTypeEnum.class, this.comboDecimalType, "selectedItem");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreatTime, "value");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys.calcTerms", String.class, this.kdtEntrys, "calcTerms.text");
		dataBinder.registerBinding("entrys.isCompensate", com.kingdee.eas.fdc.sellhouse.BooleanEnum.class, this.kdtEntrys, "isCompensate.text");
		dataBinder.registerBinding("entrys.calcFormula", String.class, this.kdtEntrys, "calcFormula.text");
		dataBinder.registerBinding("entrys.description", String.class, this.kdtEntrys, "description.text");
		dataBinder.registerBinding("entrys.minValue", java.math.BigDecimal.class, this.kdtEntrys, "minValue.text");
		dataBinder.registerBinding("entrys.minCompareType", com.kingdee.eas.fdc.sellhouse.SHECompareTypeEnum.class, this.kdtEntrys, "minCompareType.text");
		dataBinder.registerBinding("entrys.maxValue", java.math.BigDecimal.class, this.kdtEntrys, "maxValue.text");
		dataBinder.registerBinding("entrys.maxCompareType", com.kingdee.eas.fdc.sellhouse.SHECompareTypeEnum.class, this.kdtEntrys, "maxCompareType.text");
		dataBinder.registerBinding("entrys.factor", java.math.BigDecimal.class, this.kdtEntrys, "factor.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CompensateSchemeEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo)ov;
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
		getValidateHelper().registerBindProperty("calcWay", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("compensateType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("decimalCount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("decimalType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.calcTerms", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.isCompensate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.calcFormula", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.minValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.minCompareType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.maxValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.maxCompareType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.factor", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("calcWay"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("compensateType"));
        sic.add(new SelectorItemInfo("decimalCount"));
        sic.add(new SelectorItemInfo("decimalType"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
    sic.add(new SelectorItemInfo("entrys.id"));
    sic.add(new SelectorItemInfo("entrys.calcTerms"));
    sic.add(new SelectorItemInfo("entrys.isCompensate"));
    sic.add(new SelectorItemInfo("entrys.calcFormula"));
    sic.add(new SelectorItemInfo("entrys.description"));
    sic.add(new SelectorItemInfo("entrys.minValue"));
    sic.add(new SelectorItemInfo("entrys.minCompareType"));
    sic.add(new SelectorItemInfo("entrys.maxValue"));
    sic.add(new SelectorItemInfo("entrys.maxCompareType"));
    sic.add(new SelectorItemInfo("entrys.factor"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CompensateSchemeEditUI");
    }




}