/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

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
public abstract class AbstractFDCAdjustBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCAdjustBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPeriod;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPeriod;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractBill;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContractBill;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkFiVouchered;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVoucher;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtVoucher;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.eas.fdc.finance.FDCAdjustBillInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractFDCAdjustBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCAdjustBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contPeriod = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtPeriod = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contContractBill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtContractBill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.chkFiVouchered = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contVoucher = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtVoucher = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contPeriod.setName("contPeriod");
        this.prmtPeriod.setName("prmtPeriod");
        this.contContractBill.setName("contContractBill");
        this.prmtContractBill.setName("prmtContractBill");
        this.chkFiVouchered.setName("chkFiVouchered");
        this.contVoucher.setName("contVoucher");
        this.prmtVoucher.setName("prmtVoucher");
        this.kdtEntrys.setName("kdtEntrys");
        // CoreUI		
        this.setBorder(null);		
        this.menuItemSendMessage.setVisible(false);		
        this.btnAddNew.setVisible(false);		
        this.btnEdit.setVisible(false);		
        this.btnSave.setVisible(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemSave.setVisible(false);		
        this.menuItemSubmit.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.menuEdit.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuItemFirst.setVisible(false);		
        this.menuItemPre.setVisible(false);		
        this.menuItemNext.setVisible(false);		
        this.menuItemLast.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.menuItemEdit.setVisible(false);		
        this.menuItemRemove.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.btnAddLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnCreateFrom.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.menuItemCreateFrom.setVisible(false);		
        this.menuItemCopyFrom.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.btnVoucher.setVisible(true);		
        this.btnDelVoucher.setVisible(true);		
        this.btnWorkFlowG.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.MenuItemVoucher.setVisible(true);		
        this.menuItemDelVoucher.setVisible(true);		
        this.menuWorkflow.setVisible(false);		
        this.btnAudit.setVisible(false);		
        this.btnUnAudit.setVisible(false);		
        this.menuItemAudit.setVisible(false);		
        this.menuItemUnAudit.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber
        // contPeriod		
        this.contPeriod.setBoundLabelText(resHelper.getString("contPeriod.boundLabelText"));		
        this.contPeriod.setBoundLabelLength(100);		
        this.contPeriod.setBoundLabelUnderline(true);
        // prmtPeriod
        // contContractBill		
        this.contContractBill.setBoundLabelText(resHelper.getString("contContractBill.boundLabelText"));		
        this.contContractBill.setBoundLabelLength(100);		
        this.contContractBill.setBoundLabelUnderline(true);
        // prmtContractBill
        // chkFiVouchered		
        this.chkFiVouchered.setText(resHelper.getString("chkFiVouchered.text"));
        // contVoucher		
        this.contVoucher.setBoundLabelText(resHelper.getString("contVoucher.boundLabelText"));		
        this.contVoucher.setBoundLabelLength(100);		
        this.contVoucher.setBoundLabelUnderline(true);
        // prmtVoucher
        // kdtEntrys
		String kdtEntrysStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol18\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol19\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol20\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol21\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol22\"><c:NumberFormat>###,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol23\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol24\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"accountView\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"invoiceAcct\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"curProject\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"costAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"costDifAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"invoiceDifAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"payedDifAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"qualityDifAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"contractSplitAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"changeSplitAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"settleSplitAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"costAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"grtSplitAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"preGrtSplitAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"happenCostAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"invoiceAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"payedAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"qualityAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"preHappenCostAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"preInvoiceAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"prePayedAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"preQualityAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol22\" /><t:Column t:key=\"productType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"isLeaf\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol24\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{accountView}</t:Cell><t:Cell>$Resource{invoiceAcct}</t:Cell><t:Cell>$Resource{curProject}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{costDifAmt}</t:Cell><t:Cell>$Resource{invoiceDifAmt}</t:Cell><t:Cell>$Resource{payedDifAmt}</t:Cell><t:Cell>$Resource{qualityDifAmt}</t:Cell><t:Cell>$Resource{contractSplitAmt}</t:Cell><t:Cell>$Resource{changeSplitAmt}</t:Cell><t:Cell>$Resource{settleSplitAmt}</t:Cell><t:Cell>$Resource{costAmt}</t:Cell><t:Cell>$Resource{grtSplitAmt}</t:Cell><t:Cell>$Resource{preGrtSplitAmt}</t:Cell><t:Cell>$Resource{happenCostAmt}</t:Cell><t:Cell>$Resource{invoiceAmt}</t:Cell><t:Cell>$Resource{payedAmt}</t:Cell><t:Cell>$Resource{qualityAmt}</t:Cell><t:Cell>$Resource{preHappenCostAmt}</t:Cell><t:Cell>$Resource{preInvoiceAmt}</t:Cell><t:Cell>$Resource{prePayedAmt}</t:Cell><t:Cell>$Resource{preQualityAmt}</t:Cell><t:Cell>$Resource{productType}</t:Cell><t:Cell>$Resource{isLeaf}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntrys.setFormatXml(resHelper.translateString("kdtEntrys",kdtEntrysStrXML));
        this.kdtEntrys.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtEntrys_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

                this.kdtEntrys.putBindContents("editData",new String[] {"id","accountView.name","invoiceAcct.name","curProject.name","costAccount.name","costDifAmt","invoiceDifAmt","payedDifAmt","qualityDifAmt","contractSplitAmt","changeSplitAmt","settleSplitAmt","costAmt","grtSplitAmt","preGrtSplitAmt","happenCostAmt","invoiceAmt","payedAmt","qualityAmt","preHappenCostAmt","preInvoiceAmt","prePayedAmt","preQualityAmt","","isLeaf"});


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
        contNumber.setBounds(new Rectangle(14, 8, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(14, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPeriod.setBounds(new Rectangle(14, 32, 270, 19));
        this.add(contPeriod, new KDLayout.Constraints(14, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractBill.setBounds(new Rectangle(404, 32, 270, 19));
        this.add(contContractBill, new KDLayout.Constraints(404, 32, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        chkFiVouchered.setBounds(new Rectangle(744, 10, 140, 18));
        this.add(chkFiVouchered, new KDLayout.Constraints(744, 10, 140, 18, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contVoucher.setBounds(new Rectangle(405, 8, 270, 19));
        this.add(contVoucher, new KDLayout.Constraints(405, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtEntrys.setBounds(new Rectangle(14, 61, 986, 554));
        this.add(kdtEntrys, new KDLayout.Constraints(14, 61, 986, 554, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contPeriod
        contPeriod.setBoundEditor(prmtPeriod);
        //contContractBill
        contContractBill.setBoundEditor(prmtContractBill);
        //contVoucher
        contVoucher.setBoundEditor(prmtVoucher);

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
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(btnCopyLine);
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
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("period", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.prmtPeriod, "data");
		dataBinder.registerBinding("contractBill", com.kingdee.eas.fdc.contract.ContractBillInfo.class, this.prmtContractBill, "data");
		dataBinder.registerBinding("fiVouchered", boolean.class, this.chkFiVouchered, "selected");
		dataBinder.registerBinding("voucher", com.kingdee.eas.fi.gl.VoucherInfo.class, this.prmtVoucher, "data");
		dataBinder.registerBinding("entrys.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntrys, "id.text");
		dataBinder.registerBinding("entrys.preHappenCostAmt", java.math.BigDecimal.class, this.kdtEntrys, "preHappenCostAmt.text");
		dataBinder.registerBinding("entrys.happenCostAmt", java.math.BigDecimal.class, this.kdtEntrys, "happenCostAmt.text");
		dataBinder.registerBinding("entrys.costAmt", java.math.BigDecimal.class, this.kdtEntrys, "costAmt.text");
		dataBinder.registerBinding("entrys.prePayedAmt", java.math.BigDecimal.class, this.kdtEntrys, "prePayedAmt.text");
		dataBinder.registerBinding("entrys.payedAmt", java.math.BigDecimal.class, this.kdtEntrys, "payedAmt.text");
		dataBinder.registerBinding("entrys.contractSplitAmt", java.math.BigDecimal.class, this.kdtEntrys, "contractSplitAmt.text");
		dataBinder.registerBinding("entrys.changeSplitAmt", java.math.BigDecimal.class, this.kdtEntrys, "changeSplitAmt.text");
		dataBinder.registerBinding("entrys.grtSplitAmt", java.math.BigDecimal.class, this.kdtEntrys, "grtSplitAmt.text");
		dataBinder.registerBinding("entrys.costDifAmt", java.math.BigDecimal.class, this.kdtEntrys, "costDifAmt.text");
		dataBinder.registerBinding("entrys.payedDifAmt", java.math.BigDecimal.class, this.kdtEntrys, "payedDifAmt.text");
		dataBinder.registerBinding("entrys.settleSplitAmt", java.math.BigDecimal.class, this.kdtEntrys, "settleSplitAmt.text");
		dataBinder.registerBinding("entrys.accountView.name", String.class, this.kdtEntrys, "accountView.text");
		dataBinder.registerBinding("entrys", com.kingdee.eas.fdc.finance.FDCAdjustBillEntryInfo.class, this.kdtEntrys, "userObject");
		dataBinder.registerBinding("entrys.costAccount.name", String.class, this.kdtEntrys, "costAccount.text");
		dataBinder.registerBinding("entrys.isLeaf", boolean.class, this.kdtEntrys, "isLeaf.text");
		dataBinder.registerBinding("entrys.preGrtSplitAmt", java.math.BigDecimal.class, this.kdtEntrys, "preGrtSplitAmt.text");
		dataBinder.registerBinding("entrys.curProject.name", String.class, this.kdtEntrys, "curProject.text");
		dataBinder.registerBinding("entrys.preQualityAmt", java.math.BigDecimal.class, this.kdtEntrys, "preQualityAmt.text");
		dataBinder.registerBinding("entrys.qualityDifAmt", java.math.BigDecimal.class, this.kdtEntrys, "qualityDifAmt.text");
		dataBinder.registerBinding("entrys.qualityAmt", java.math.BigDecimal.class, this.kdtEntrys, "qualityAmt.text");
		dataBinder.registerBinding("entrys.invoiceAcct.name", String.class, this.kdtEntrys, "invoiceAcct.text");
		dataBinder.registerBinding("entrys.invoiceAmt", java.math.BigDecimal.class, this.kdtEntrys, "invoiceAmt.text");
		dataBinder.registerBinding("entrys.preInvoiceAmt", java.math.BigDecimal.class, this.kdtEntrys, "preInvoiceAmt.text");
		dataBinder.registerBinding("entrys.invoiceDifAmt", java.math.BigDecimal.class, this.kdtEntrys, "invoiceDifAmt.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.FDCAdjustBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.finance.FDCAdjustBillInfo)ov;
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
		getValidateHelper().registerBindProperty("period", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBill", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fiVouchered", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("voucher", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.preHappenCostAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.happenCostAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.costAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.prePayedAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.payedAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.contractSplitAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.changeSplitAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.grtSplitAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.costDifAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.payedDifAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.settleSplitAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.accountView.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.costAccount.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.isLeaf", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.preGrtSplitAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.curProject.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.preQualityAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.qualityDifAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.qualityAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.invoiceAcct.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.invoiceAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.preInvoiceAmt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entrys.invoiceDifAmt", ValidateHelper.ON_SAVE);    		
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
     * output kdtEntrys_tableClicked method
     */
    protected void kdtEntrys_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("period.*"));
        sic.add(new SelectorItemInfo("contractBill.*"));
        sic.add(new SelectorItemInfo("fiVouchered"));
        sic.add(new SelectorItemInfo("voucher.*"));
    sic.add(new SelectorItemInfo("entrys.id"));
    sic.add(new SelectorItemInfo("entrys.preHappenCostAmt"));
    sic.add(new SelectorItemInfo("entrys.happenCostAmt"));
    sic.add(new SelectorItemInfo("entrys.costAmt"));
    sic.add(new SelectorItemInfo("entrys.prePayedAmt"));
    sic.add(new SelectorItemInfo("entrys.payedAmt"));
    sic.add(new SelectorItemInfo("entrys.contractSplitAmt"));
    sic.add(new SelectorItemInfo("entrys.changeSplitAmt"));
    sic.add(new SelectorItemInfo("entrys.grtSplitAmt"));
    sic.add(new SelectorItemInfo("entrys.costDifAmt"));
    sic.add(new SelectorItemInfo("entrys.payedDifAmt"));
    sic.add(new SelectorItemInfo("entrys.settleSplitAmt"));
    sic.add(new SelectorItemInfo("entrys.accountView.name"));
        sic.add(new SelectorItemInfo("entrys.*"));
//        sic.add(new SelectorItemInfo("entrys.number"));
    sic.add(new SelectorItemInfo("entrys.costAccount.name"));
    sic.add(new SelectorItemInfo("entrys.isLeaf"));
    sic.add(new SelectorItemInfo("entrys.preGrtSplitAmt"));
    sic.add(new SelectorItemInfo("entrys.curProject.name"));
    sic.add(new SelectorItemInfo("entrys.preQualityAmt"));
    sic.add(new SelectorItemInfo("entrys.qualityDifAmt"));
    sic.add(new SelectorItemInfo("entrys.qualityAmt"));
    sic.add(new SelectorItemInfo("entrys.invoiceAcct.name"));
    sic.add(new SelectorItemInfo("entrys.invoiceAmt"));
    sic.add(new SelectorItemInfo("entrys.preInvoiceAmt"));
    sic.add(new SelectorItemInfo("entrys.invoiceDifAmt"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "FDCAdjustBillEditUI");
    }




}