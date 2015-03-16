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
public abstract class AbstractRoomLoanBatchEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomLoanBatchEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRoomJoinInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRoomInfo;
    protected com.kingdee.bos.ctrl.swing.KDContainer contLoanData;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProcessLoanDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTransactor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAppDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAfmortgaged;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurProcess;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkProcessLoanDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Transactor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellProject;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAppDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Afmortgaged;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCurProcess;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRoomInfo;
    protected com.kingdee.bos.ctrl.swing.KDButton btnAddRoom;
    protected com.kingdee.bos.ctrl.swing.KDButton btnDeleteRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAddRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7AddRoom;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblLoanData;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkBatchLoanData;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRoomLoanBatchEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomLoanBatchEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.pnlRoomJoinInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlRoomInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contLoanData = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contProcessLoanDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTransactor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAppDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAfmortgaged = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurProcess = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkProcessLoanDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Transactor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7SellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAppDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Afmortgaged = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboCurProcess = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.tblRoomInfo = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddRoom = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnDeleteRoom = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contAddRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7AddRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tblLoanData = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.checkBatchLoanData = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.pnlRoomJoinInfo.setName("pnlRoomJoinInfo");
        this.pnlRoomInfo.setName("pnlRoomInfo");
        this.contLoanData.setName("contLoanData");
        this.contProcessLoanDate.setName("contProcessLoanDate");
        this.contTransactor.setName("contTransactor");
        this.contSellProject.setName("contSellProject");
        this.contAppDate.setName("contAppDate");
        this.contAfmortgaged.setName("contAfmortgaged");
        this.contCurProcess.setName("contCurProcess");
        this.pkProcessLoanDate.setName("pkProcessLoanDate");
        this.f7Transactor.setName("f7Transactor");
        this.f7SellProject.setName("f7SellProject");
        this.pkAppDate.setName("pkAppDate");
        this.f7Afmortgaged.setName("f7Afmortgaged");
        this.comboCurProcess.setName("comboCurProcess");
        this.tblRoomInfo.setName("tblRoomInfo");
        this.btnAddRoom.setName("btnAddRoom");
        this.btnDeleteRoom.setName("btnDeleteRoom");
        this.contAddRoom.setName("contAddRoom");
        this.f7AddRoom.setName("f7AddRoom");
        this.tblLoanData.setName("tblLoanData");
        this.checkBatchLoanData.setName("checkBatchLoanData");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));
        // pnlRoomJoinInfo		
        this.pnlRoomJoinInfo.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlRoomJoinInfo.border.title")));
        // pnlRoomInfo		
        this.pnlRoomInfo.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlRoomInfo.border.title")));
        // contLoanData		
        this.contLoanData.setTitle(resHelper.getString("contLoanData.title"));
        // contProcessLoanDate		
        this.contProcessLoanDate.setBoundLabelText(resHelper.getString("contProcessLoanDate.boundLabelText"));		
        this.contProcessLoanDate.setBoundLabelLength(100);		
        this.contProcessLoanDate.setBoundLabelUnderline(true);
        // contTransactor		
        this.contTransactor.setBoundLabelText(resHelper.getString("contTransactor.boundLabelText"));		
        this.contTransactor.setBoundLabelLength(100);		
        this.contTransactor.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);		
        this.contSellProject.setEnabled(false);
        // contAppDate		
        this.contAppDate.setBoundLabelText(resHelper.getString("contAppDate.boundLabelText"));		
        this.contAppDate.setBoundLabelLength(100);		
        this.contAppDate.setBoundLabelUnderline(true);
        // contAfmortgaged		
        this.contAfmortgaged.setBoundLabelText(resHelper.getString("contAfmortgaged.boundLabelText"));		
        this.contAfmortgaged.setBoundLabelLength(100);		
        this.contAfmortgaged.setBoundLabelUnderline(true);
        // contCurProcess		
        this.contCurProcess.setBoundLabelText(resHelper.getString("contCurProcess.boundLabelText"));		
        this.contCurProcess.setBoundLabelLength(100);		
        this.contCurProcess.setBoundLabelUnderline(true);
        // pkProcessLoanDate		
        this.pkProcessLoanDate.setRequired(true);
        // f7Transactor		
        this.f7Transactor.setDisplayFormat("$name$");		
        this.f7Transactor.setEditFormat("$number$");		
        this.f7Transactor.setCommitFormat("$number$");		
        this.f7Transactor.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7Transactor.setRequired(true);
        // f7SellProject		
        this.f7SellProject.setEnabled(false);
        // pkAppDate
        // f7Afmortgaged		
        this.f7Afmortgaged.setRequired(true);		
        this.f7Afmortgaged.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.AFMortgagedQuery");
        this.f7Afmortgaged.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Afmortgaged_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboCurProcess		
        this.comboCurProcess.setRequired(true);
        // tblRoomInfo
		String tblRoomInfoStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sTable\"><c:Alignment horizontal=\"left\" /><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\" t:styleID=\"sTable\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contractNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"afmNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"mmType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"loanAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"loanBank\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"loanFixYear\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{contractNumber}</t:Cell><t:Cell>$Resource{afmNumber}</t:Cell><t:Cell>$Resource{mmType}</t:Cell><t:Cell>$Resource{loanAmount}</t:Cell><t:Cell>$Resource{loanBank}</t:Cell><t:Cell>$Resource{loanFixYear}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblRoomInfo.setFormatXml(resHelper.translateString("tblRoomInfo",tblRoomInfoStrXML));
        this.tblRoomInfo.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblRoomInfo_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // btnAddRoom		
        this.btnAddRoom.setText(resHelper.getString("btnAddRoom.text"));
        this.btnAddRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDeleteRoom		
        this.btnDeleteRoom.setText(resHelper.getString("btnDeleteRoom.text"));
        this.btnDeleteRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDeleteRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contAddRoom		
        this.contAddRoom.setBoundLabelText(resHelper.getString("contAddRoom.boundLabelText"));		
        this.contAddRoom.setBoundLabelLength(100);		
        this.contAddRoom.setBoundLabelUnderline(true);		
        this.contAddRoom.setVisible(false);
        // f7AddRoom		
        this.f7AddRoom.setVisible(false);
        this.f7AddRoom.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7AddRoom_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblLoanData
		String tblLoanDataStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isFinish\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"finishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"remark\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{isFinish}</t:Cell><t:Cell>$Resource{finishDate}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblLoanData.setFormatXml(resHelper.translateString("tblLoanData",tblLoanDataStrXML));
        this.tblLoanData.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblLoanData_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // checkBatchLoanData		
        this.checkBatchLoanData.setText(resHelper.getString("checkBatchLoanData.text"));
        this.checkBatchLoanData.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    checkBatchLoanData_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
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
        pnlRoomJoinInfo.setBounds(new Rectangle(5, 10, 1005, 140));
        this.add(pnlRoomJoinInfo, new KDLayout.Constraints(5, 10, 1005, 140, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        pnlRoomInfo.setBounds(new Rectangle(5, 155, 1005, 201));
        this.add(pnlRoomInfo, new KDLayout.Constraints(5, 155, 1005, 201, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contLoanData.setBounds(new Rectangle(4, 368, 1007, 249));
        this.add(contLoanData, new KDLayout.Constraints(4, 368, 1007, 249, 0));
        //pnlRoomJoinInfo
        pnlRoomJoinInfo.setLayout(new KDLayout());
        pnlRoomJoinInfo.putClientProperty("OriginalBounds", new Rectangle(5, 10, 1005, 140));        contProcessLoanDate.setBounds(new Rectangle(721, 48, 270, 19));
        pnlRoomJoinInfo.add(contProcessLoanDate, new KDLayout.Constraints(721, 48, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTransactor.setBounds(new Rectangle(720, 20, 270, 19));
        pnlRoomJoinInfo.add(contTransactor, new KDLayout.Constraints(720, 20, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSellProject.setBounds(new Rectangle(12, 20, 270, 19));
        pnlRoomJoinInfo.add(contSellProject, new KDLayout.Constraints(12, 20, 270, 19, 0));
        contAppDate.setBounds(new Rectangle(366, 20, 270, 19));
        pnlRoomJoinInfo.add(contAppDate, new KDLayout.Constraints(366, 20, 270, 19, 0));
        contAfmortgaged.setBounds(new Rectangle(11, 48, 270, 19));
        pnlRoomJoinInfo.add(contAfmortgaged, new KDLayout.Constraints(11, 48, 270, 19, 0));
        contCurProcess.setBounds(new Rectangle(366, 48, 270, 19));
        pnlRoomJoinInfo.add(contCurProcess, new KDLayout.Constraints(366, 48, 270, 19, 0));
        //contProcessLoanDate
        contProcessLoanDate.setBoundEditor(pkProcessLoanDate);
        //contTransactor
        contTransactor.setBoundEditor(f7Transactor);
        //contSellProject
        contSellProject.setBoundEditor(f7SellProject);
        //contAppDate
        contAppDate.setBoundEditor(pkAppDate);
        //contAfmortgaged
        contAfmortgaged.setBoundEditor(f7Afmortgaged);
        //contCurProcess
        contCurProcess.setBoundEditor(comboCurProcess);
        //pnlRoomInfo
        pnlRoomInfo.setLayout(new KDLayout());
        pnlRoomInfo.putClientProperty("OriginalBounds", new Rectangle(5, 155, 1005, 201));        tblRoomInfo.setBounds(new Rectangle(12, 42, 979, 140));
        pnlRoomInfo.add(tblRoomInfo, new KDLayout.Constraints(12, 42, 979, 140, 0));
        btnAddRoom.setBounds(new Rectangle(789, 15, 92, 21));
        pnlRoomInfo.add(btnAddRoom, new KDLayout.Constraints(789, 15, 92, 21, 0));
        btnDeleteRoom.setBounds(new Rectangle(886, 15, 92, 21));
        pnlRoomInfo.add(btnDeleteRoom, new KDLayout.Constraints(886, 15, 92, 21, 0));
        contAddRoom.setBounds(new Rectangle(498, 15, 270, 19));
        pnlRoomInfo.add(contAddRoom, new KDLayout.Constraints(498, 15, 270, 19, 0));
        //contAddRoom
        contAddRoom.setBoundEditor(f7AddRoom);
        //contLoanData
        contLoanData.getContentPane().setLayout(new KDLayout());
        contLoanData.getContentPane().putClientProperty("OriginalBounds", new Rectangle(4, 368, 1007, 249));        tblLoanData.setBounds(new Rectangle(2, 29, 998, 195));
        contLoanData.getContentPane().add(tblLoanData, new KDLayout.Constraints(2, 29, 998, 195, 0));
        checkBatchLoanData.setBounds(new Rectangle(6, 7, 140, 19));
        contLoanData.getContentPane().add(checkBatchLoanData, new KDLayout.Constraints(6, 7, 140, 19, 0));

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
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomLoanBatchEditUIHandler";
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
        this.editData = (com.kingdee.eas.framework.CoreBaseInfo)ov;
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
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output f7Afmortgaged_dataChanged method
     */
    protected void f7Afmortgaged_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblRoomInfo_tableClicked method
     */
    protected void tblRoomInfo_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output btnAddRoom_actionPerformed method
     */
    protected void btnAddRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDeleteRoom_actionPerformed method
     */
    protected void btnDeleteRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output f7AddRoom_dataChanged method
     */
    protected void f7AddRoom_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblLoanData_editStopped method
     */
    protected void tblLoanData_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output checkBatchLoanData_itemStateChanged method
     */
    protected void checkBatchLoanData_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomLoanBatchEditUI");
    }




}