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
public abstract class AbstractInvoiceEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractInvoiceEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomSignContractNumber;
    protected com.kingdee.bos.ctrl.swing.KDButton btnSelectRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCheque;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer chequeType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmount;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtUser;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomSignContractNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Cheque;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblReceive;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRecord;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCustomer;
    protected com.kingdee.bos.ctrl.swing.KDComboBox KDchequeType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conStatus;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.eas.fdc.sellhouse.InvoiceInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractInvoiceEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractInvoiceEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomSignContractNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSelectRoom = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contCheque = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chequeType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtRoomNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRoomSignContractNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Cheque = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblReceive = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.KDchequeType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.conStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contAmount.setName("contAmount");
        this.contDate.setName("contDate");
        this.contUser.setName("contUser");
        this.contRoomNumber.setName("contRoomNumber");
        this.contRoomSignContractNumber.setName("contRoomSignContractNumber");
        this.btnSelectRoom.setName("btnSelectRoom");
        this.contCheque.setName("contCheque");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.chequeType.setName("chequeType");
        this.txtNumber.setName("txtNumber");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.txtAmount.setName("txtAmount");
        this.pkDate.setName("pkDate");
        this.prmtUser.setName("prmtUser");
        this.txtRoomNumber.setName("txtRoomNumber");
        this.txtRoomSignContractNumber.setName("txtRoomSignContractNumber");
        this.f7Cheque.setName("f7Cheque");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.tblReceive.setName("tblReceive");
        this.tblRecord.setName("tblRecord");
        this.prmtCustomer.setName("prmtCustomer");
        this.KDchequeType.setName("KDchequeType");
        this.conStatus.setName("conStatus");
        this.comboStatus.setName("comboStatus");
        // CoreUI		
        this.setPreferredSize(new Dimension(700,420));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAmount		
        this.contAmount.setBoundLabelText(resHelper.getString("contAmount.boundLabelText"));		
        this.contAmount.setBoundLabelLength(100);		
        this.contAmount.setBoundLabelUnderline(true);
        // contDate		
        this.contDate.setBoundLabelText(resHelper.getString("contDate.boundLabelText"));		
        this.contDate.setBoundLabelLength(100);		
        this.contDate.setBoundLabelUnderline(true);
        // contUser		
        this.contUser.setBoundLabelText(resHelper.getString("contUser.boundLabelText"));		
        this.contUser.setBoundLabelLength(100);		
        this.contUser.setBoundLabelUnderline(true);
        // contRoomNumber		
        this.contRoomNumber.setBoundLabelText(resHelper.getString("contRoomNumber.boundLabelText"));		
        this.contRoomNumber.setBoundLabelLength(100);		
        this.contRoomNumber.setBoundLabelUnderline(true);
        // contRoomSignContractNumber		
        this.contRoomSignContractNumber.setBoundLabelText(resHelper.getString("contRoomSignContractNumber.boundLabelText"));		
        this.contRoomSignContractNumber.setBoundLabelLength(100);		
        this.contRoomSignContractNumber.setBoundLabelUnderline(true);
        // btnSelectRoom		
        this.btnSelectRoom.setText(resHelper.getString("btnSelectRoom.text"));
        this.btnSelectRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSelectRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contCheque		
        this.contCheque.setBoundLabelText(resHelper.getString("contCheque.boundLabelText"));		
        this.contCheque.setBoundLabelLength(100);		
        this.contCheque.setBoundLabelUnderline(true);
        // kDTabbedPane1
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // chequeType		
        this.chequeType.setBoundLabelText(resHelper.getString("chequeType.boundLabelText"));		
        this.chequeType.setBoundLabelLength(100);		
        this.chequeType.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // kDScrollPane1
        // txtDescription
        // txtAmount		
        this.txtAmount.setDataType(1);
        // pkDate
        // prmtUser		
        this.prmtUser.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtUser.setCommitFormat("$number$");		
        this.prmtUser.setEditFormat("$number$");		
        this.prmtUser.setDisplayFormat("$name$");		
        this.prmtUser.setEditable(true);
        // txtRoomNumber		
        this.txtRoomNumber.setEnabled(false);
        // txtRoomSignContractNumber		
        this.txtRoomSignContractNumber.setEnabled(false);
        // f7Cheque		
        this.f7Cheque.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ChequeQuery");		
        this.f7Cheque.setDisplayFormat("$number$");		
        this.f7Cheque.setEditFormat("$number$");		
        this.f7Cheque.setCommitFormat("$number$");
        this.f7Cheque.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Cheque_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDPanel1
        // kDPanel2
        // tblReceive
		String tblReceiveStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"isSelected\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"revNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"moneyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"billDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"receipt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"invoiceNum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{isSelected}</t:Cell><t:Cell>$Resource{revNumber}</t:Cell><t:Cell>$Resource{moneyName}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{billDate}</t:Cell><t:Cell>$Resource{receipt}</t:Cell><t:Cell>$Resource{invoiceNum}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblReceive.setFormatXml(resHelper.translateString("tblReceive",tblReceiveStrXML));
        this.tblReceive.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblReceive_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblReceive.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblReceive_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // tblRecord
		String tblRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"recordType\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"content\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"operatingUser\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"operatingDate\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"description\" t:width=\"240\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{recordType}</t:Cell><t:Cell>$Resource{content}</t:Cell><t:Cell>$Resource{operatingUser}</t:Cell><t:Cell>$Resource{operatingDate}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblRecord.setFormatXml(resHelper.translateString("tblRecord",tblRecordStrXML));

        

        // prmtCustomer		
        this.prmtCustomer.setDisplayFormat("$name$");		
        this.prmtCustomer.setEditFormat("$number$");		
        this.prmtCustomer.setCommitFormat("$number$");		
        this.prmtCustomer.setEnabled(false);		
        this.prmtCustomer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");
        // KDchequeType		
        this.KDchequeType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum").toArray());
        // conStatus		
        this.conStatus.setBoundLabelText(resHelper.getString("conStatus.boundLabelText"));		
        this.conStatus.setBoundLabelLength(100);		
        this.conStatus.setBoundLabelUnderline(true);
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum").toArray());
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
        this.setBounds(new Rectangle(10, 10, 700, 450));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 700, 450));
        contNumber.setBounds(new Rectangle(400, 8, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(400, 8, 270, 19, 0));
        contDescription.setBounds(new Rectangle(10, 130, 664, 43));
        this.add(contDescription, new KDLayout.Constraints(10, 130, 664, 43, 0));
        contAmount.setBounds(new Rectangle(400, 38, 270, 19));
        this.add(contAmount, new KDLayout.Constraints(400, 38, 270, 19, 0));
        contDate.setBounds(new Rectangle(10, 38, 270, 19));
        this.add(contDate, new KDLayout.Constraints(10, 38, 270, 19, 0));
        contUser.setBounds(new Rectangle(10, 99, 270, 19));
        this.add(contUser, new KDLayout.Constraints(10, 99, 270, 19, 0));
        contRoomNumber.setBounds(new Rectangle(10, 70, 270, 19));
        this.add(contRoomNumber, new KDLayout.Constraints(10, 70, 270, 19, 0));
        contRoomSignContractNumber.setBounds(new Rectangle(10, 128, 270, 19));
        this.add(contRoomSignContractNumber, new KDLayout.Constraints(10, 128, 270, 19, 0));
        btnSelectRoom.setBounds(new Rectangle(290, 70, 87, 21));
        this.add(btnSelectRoom, new KDLayout.Constraints(290, 70, 87, 21, 0));
        contCheque.setBounds(new Rectangle(400, 8, 270, 19));
        this.add(contCheque, new KDLayout.Constraints(400, 8, 270, 19, 0));
        kDTabbedPane1.setBounds(new Rectangle(10, 183, 680, 238));
        this.add(kDTabbedPane1, new KDLayout.Constraints(10, 183, 680, 238, 0));
        kDLabelContainer1.setBounds(new Rectangle(400, 70, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(400, 70, 270, 19, 0));
        chequeType.setBounds(new Rectangle(13, 8, 270, 19));
        this.add(chequeType, new KDLayout.Constraints(13, 8, 270, 19, 0));
        conStatus.setBounds(new Rectangle(402, 97, 270, 19));
        this.add(conStatus, new KDLayout.Constraints(402, 97, 270, 19, 0));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contAmount
        contAmount.setBoundEditor(txtAmount);
        //contDate
        contDate.setBoundEditor(pkDate);
        //contUser
        contUser.setBoundEditor(prmtUser);
        //contRoomNumber
        contRoomNumber.setBoundEditor(txtRoomNumber);
        //contRoomSignContractNumber
        contRoomSignContractNumber.setBoundEditor(txtRoomSignContractNumber);
        //contCheque
        contCheque.setBoundEditor(f7Cheque);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(tblReceive, BorderLayout.CENTER);
        //kDPanel2
kDPanel2.setLayout(new BorderLayout(0, 0));        kDPanel2.add(tblRecord, BorderLayout.CENTER);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(prmtCustomer);
        //chequeType
        chequeType.setBoundEditor(KDchequeType);
        //conStatus
        conStatus.setBoundEditor(comboStatus);

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
        menuEdit.add(menuItemReset);
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
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtAmount, "value");
		dataBinder.registerBinding("date", java.util.Date.class, this.pkDate, "value");
		dataBinder.registerBinding("user", com.kingdee.eas.base.permission.UserInfo.class, this.prmtUser, "data");
		dataBinder.registerBinding("room.number", String.class, this.txtRoomNumber, "text");
		dataBinder.registerBinding("room", com.kingdee.eas.fdc.sellhouse.RoomInfo.class, this.txtRoomNumber, "userObject");
		dataBinder.registerBinding("cheque", com.kingdee.eas.fm.nt.ChequeInfo.class, this.f7Cheque, "data");
		dataBinder.registerBinding("customer", com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo.class, this.prmtCustomer, "data");
		dataBinder.registerBinding("cheque.status", com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum.class, this.comboStatus, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.InvoiceEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.InvoiceInfo)ov;
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
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("date", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("user", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("room.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("room", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cheque", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("customer", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cheque.status", ValidateHelper.ON_SAVE);    		
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
     * output btnSelectRoom_actionPerformed method
     */
    protected void btnSelectRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7Cheque_dataChanged method
     */
    protected void f7Cheque_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblReceive_tableClicked method
     */
    protected void tblReceive_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblReceive_editStopped method
     */
    protected void tblReceive_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("date"));
        sic.add(new SelectorItemInfo("user.*"));
        sic.add(new SelectorItemInfo("room.number"));
        sic.add(new SelectorItemInfo("room"));
        sic.add(new SelectorItemInfo("cheque.*"));
        sic.add(new SelectorItemInfo("customer.*"));
        sic.add(new SelectorItemInfo("cheque.status"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "InvoiceEditUI");
    }




}