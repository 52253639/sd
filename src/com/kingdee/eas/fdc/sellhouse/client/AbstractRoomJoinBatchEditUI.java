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
public abstract class AbstractRoomJoinBatchEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomJoinBatchEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRoomJoinInfo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAddRoom;
    protected com.kingdee.bos.ctrl.swing.KDButton btnAddRoom;
    protected com.kingdee.bos.ctrl.swing.KDButton btnDeleteRoom;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contJoinDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTransactor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contScheme;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBatch;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurApp;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkJoinDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Transactor;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Scheme;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Batch;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellProject;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboCurApp;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7AddRoom;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblJoin;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox ckBatchUpdate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblDataEntry;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRoomJoinBatchEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomJoinBatchEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.pnlRoomJoinInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contAddRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnAddRoom = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnDeleteRoom = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contJoinDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTransactor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contScheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBatch = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurApp = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkJoinDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Transactor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Scheme = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Batch = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7SellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboCurApp = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7AddRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tblJoin = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.ckBatchUpdate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.tblDataEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.pnlRoomJoinInfo.setName("pnlRoomJoinInfo");
        this.contAddRoom.setName("contAddRoom");
        this.btnAddRoom.setName("btnAddRoom");
        this.btnDeleteRoom.setName("btnDeleteRoom");
        this.kDContainer1.setName("kDContainer1");
        this.kDContainer2.setName("kDContainer2");
        this.contJoinDate.setName("contJoinDate");
        this.contTransactor.setName("contTransactor");
        this.contScheme.setName("contScheme");
        this.contBatch.setName("contBatch");
        this.contSellProject.setName("contSellProject");
        this.contCurApp.setName("contCurApp");
        this.pkJoinDate.setName("pkJoinDate");
        this.f7Transactor.setName("f7Transactor");
        this.f7Scheme.setName("f7Scheme");
        this.f7Batch.setName("f7Batch");
        this.f7SellProject.setName("f7SellProject");
        this.comboCurApp.setName("comboCurApp");
        this.f7AddRoom.setName("f7AddRoom");
        this.tblJoin.setName("tblJoin");
        this.ckBatchUpdate.setName("ckBatchUpdate");
        this.tblDataEntry.setName("tblDataEntry");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));
        // pnlRoomJoinInfo		
        this.pnlRoomJoinInfo.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlRoomJoinInfo.border.title")));
        // contAddRoom		
        this.contAddRoom.setBoundLabelText(resHelper.getString("contAddRoom.boundLabelText"));		
        this.contAddRoom.setBoundLabelLength(100);		
        this.contAddRoom.setBoundLabelUnderline(true);		
        this.contAddRoom.setVisible(false);
        // btnAddRoom		
        this.btnAddRoom.setText(resHelper.getString("btnAddRoom.text"));
        this.btnAddRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRoomSelect_actionPerformed(e);
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
                    btnRoomDelete_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));		
        this.kDContainer1.setEnableActive(false);
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // contJoinDate		
        this.contJoinDate.setBoundLabelText(resHelper.getString("contJoinDate.boundLabelText"));		
        this.contJoinDate.setBoundLabelLength(100);		
        this.contJoinDate.setBoundLabelUnderline(true);
        // contTransactor		
        this.contTransactor.setBoundLabelText(resHelper.getString("contTransactor.boundLabelText"));		
        this.contTransactor.setBoundLabelLength(100);		
        this.contTransactor.setBoundLabelUnderline(true);
        // contScheme		
        this.contScheme.setBoundLabelText(resHelper.getString("contScheme.boundLabelText"));		
        this.contScheme.setBoundLabelLength(100);		
        this.contScheme.setBoundLabelUnderline(true);
        // contBatch		
        this.contBatch.setBoundLabelLength(100);		
        this.contBatch.setBoundLabelUnderline(true);		
        this.contBatch.setBoundLabelText(resHelper.getString("contBatch.boundLabelText"));
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);		
        this.contSellProject.setEnabled(false);
        // contCurApp		
        this.contCurApp.setBoundLabelText(resHelper.getString("contCurApp.boundLabelText"));		
        this.contCurApp.setBoundLabelLength(100);		
        this.contCurApp.setBoundLabelUnderline(true);
        // pkJoinDate		
        this.pkJoinDate.setRequired(true);
        // f7Transactor		
        this.f7Transactor.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7Transactor.setCommitFormat("$number$");		
        this.f7Transactor.setDisplayFormat("$name$");		
        this.f7Transactor.setEditFormat("$number$");
        // f7Scheme		
        this.f7Scheme.setDisplayFormat("$name$");		
        this.f7Scheme.setRequired(true);		
        this.f7Scheme.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.JoinDoSchemeQuery");
        this.f7Scheme.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7JoinDoScheme_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7Batch		
        this.f7Batch.setRequired(true);		
        this.f7Batch.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BatchManageQuery");		
        this.f7Batch.setDisplayFormat("$number$");
        this.f7Batch.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Batch_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7SellProject		
        this.f7SellProject.setEnabled(false);
        // comboCurApp		
        this.comboCurApp.setRequired(true);
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
        // tblJoin
		String tblJoinStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol6\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"customerPhone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"contractNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"contractNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"signDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"dealAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"roomId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{customerPhone}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{contractNumber}</t:Cell><t:Cell>$Resource{contractNo}</t:Cell><t:Cell>$Resource{signDate}</t:Cell><t:Cell>$Resource{dealAmount}</t:Cell><t:Cell>$Resource{roomId}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblJoin.setFormatXml(resHelper.translateString("tblJoin",tblJoinStrXML));
        this.tblJoin.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblJoin_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblJoin.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblJoin_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // ckBatchUpdate		
        this.ckBatchUpdate.setText(resHelper.getString("ckBatchUpdate.text"));
        this.ckBatchUpdate.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    ckBatchUpdateData_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblDataEntry
		String tblDataEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isFinish\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actFinishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{isFinish}</t:Cell><t:Cell>$Resource{actFinishDate}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblDataEntry.setFormatXml(resHelper.translateString("tblDataEntry",tblDataEntryStrXML));
        this.tblDataEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblDataEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
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
        this.setBounds(new Rectangle(10, 10, 1013, 700));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 700));
        pnlRoomJoinInfo.setBounds(new Rectangle(5, 10, 1005, 96));
        this.add(pnlRoomJoinInfo, new KDLayout.Constraints(5, 10, 1005, 96, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAddRoom.setBounds(new Rectangle(543, 111, 270, 19));
        this.add(contAddRoom, new KDLayout.Constraints(543, 111, 270, 19, 0));
        btnAddRoom.setBounds(new Rectangle(822, 110, 82, 21));
        this.add(btnAddRoom, new KDLayout.Constraints(822, 110, 82, 21, 0));
        btnDeleteRoom.setBounds(new Rectangle(911, 110, 82, 21));
        this.add(btnDeleteRoom, new KDLayout.Constraints(911, 110, 82, 21, 0));
        kDContainer1.setBounds(new Rectangle(10, 136, 993, 246));
        this.add(kDContainer1, new KDLayout.Constraints(10, 136, 993, 246, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer2.setBounds(new Rectangle(10, 390, 993, 270));
        this.add(kDContainer2, new KDLayout.Constraints(10, 390, 993, 270, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //pnlRoomJoinInfo
        pnlRoomJoinInfo.setLayout(new KDLayout());
        pnlRoomJoinInfo.putClientProperty("OriginalBounds", new Rectangle(5, 10, 1005, 96));        contJoinDate.setBounds(new Rectangle(715, 50, 270, 19));
        pnlRoomJoinInfo.add(contJoinDate, new KDLayout.Constraints(715, 50, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTransactor.setBounds(new Rectangle(715, 20, 270, 19));
        pnlRoomJoinInfo.add(contTransactor, new KDLayout.Constraints(715, 20, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contScheme.setBounds(new Rectangle(15, 50, 270, 19));
        pnlRoomJoinInfo.add(contScheme, new KDLayout.Constraints(15, 50, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBatch.setBounds(new Rectangle(365, 20, 270, 19));
        pnlRoomJoinInfo.add(contBatch, new KDLayout.Constraints(365, 20, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellProject.setBounds(new Rectangle(15, 20, 270, 19));
        pnlRoomJoinInfo.add(contSellProject, new KDLayout.Constraints(15, 20, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurApp.setBounds(new Rectangle(365, 50, 270, 19));
        pnlRoomJoinInfo.add(contCurApp, new KDLayout.Constraints(365, 50, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contJoinDate
        contJoinDate.setBoundEditor(pkJoinDate);
        //contTransactor
        contTransactor.setBoundEditor(f7Transactor);
        //contScheme
        contScheme.setBoundEditor(f7Scheme);
        //contBatch
        contBatch.setBoundEditor(f7Batch);
        //contSellProject
        contSellProject.setBoundEditor(f7SellProject);
        //contCurApp
        contCurApp.setBoundEditor(comboCurApp);
        //contAddRoom
        contAddRoom.setBoundEditor(f7AddRoom);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 136, 993, 246));        tblJoin.setBounds(new Rectangle(10, 10, 960, 206));
        kDContainer1.getContentPane().add(tblJoin, new KDLayout.Constraints(10, 10, 960, 206, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDContainer2
        kDContainer2.getContentPane().setLayout(new KDLayout());
        kDContainer2.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 390, 993, 270));        ckBatchUpdate.setBounds(new Rectangle(15, 2, 140, 26));
        kDContainer2.getContentPane().add(ckBatchUpdate, new KDLayout.Constraints(15, 2, 140, 26, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        tblDataEntry.setBounds(new Rectangle(6, 30, 953, 210));
        kDContainer2.getContentPane().add(tblDataEntry, new KDLayout.Constraints(6, 30, 953, 210, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

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
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomJoinBatchEditUIHandler";
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
     * output btnRoomSelect_actionPerformed method
     */
    protected void btnRoomSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnRoomDelete_actionPerformed method
     */
    protected void btnRoomDelete_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output f7JoinDoScheme_dataChanged method
     */
    protected void f7JoinDoScheme_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7Batch_dataChanged method
     */
    protected void f7Batch_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7AddRoom_dataChanged method
     */
    protected void f7AddRoom_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblJoin_tableClicked method
     */
    protected void tblJoin_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output tblJoin_editStopped method
     */
    protected void tblJoin_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output ckBatchUpdateData_itemStateChanged method
     */
    protected void ckBatchUpdateData_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output tblDataEntry_editStopped method
     */
    protected void tblDataEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomJoinBatchEditUI");
    }




}