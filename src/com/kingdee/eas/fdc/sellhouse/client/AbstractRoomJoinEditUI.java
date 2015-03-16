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
public abstract class AbstractRoomJoinEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomJoinEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRoomInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRoomJoinInfo;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane sclPanel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contJoinDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApptJoinDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contF7JoinDoScheme;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox ckFinish;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActFinishDate;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkJoinDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkApptJoinDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7JoinDoScheme;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkActFinishDate;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAppEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblDataEntry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Creator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateDate;
    protected com.kingdee.eas.fdc.sellhouse.RoomJoinInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRoomJoinEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomJoinEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.pnlRoomInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pnlRoomJoinInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.sclPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.contJoinDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApptJoinDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contF7JoinDoScheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.ckFinish = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contActFinishDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkJoinDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkApptJoinDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7JoinDoScheme = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkActFinishDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblAppEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblDataEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.f7Creator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pnlRoomInfo.setName("pnlRoomInfo");
        this.pnlRoomJoinInfo.setName("pnlRoomJoinInfo");
        this.sclPanel.setName("sclPanel");
        this.contJoinDate.setName("contJoinDate");
        this.contApptJoinDate.setName("contApptJoinDate");
        this.contDescription.setName("contDescription");
        this.contNumber.setName("contNumber");
        this.contF7JoinDoScheme.setName("contF7JoinDoScheme");
        this.ckFinish.setName("ckFinish");
        this.contActFinishDate.setName("contActFinishDate");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contCreator.setName("contCreator");
        this.contCreateDate.setName("contCreateDate");
        this.pkJoinDate.setName("pkJoinDate");
        this.pkApptJoinDate.setName("pkApptJoinDate");
        this.txtDescription.setName("txtDescription");
        this.txtNumber.setName("txtNumber");
        this.f7JoinDoScheme.setName("f7JoinDoScheme");
        this.pkActFinishDate.setName("pkActFinishDate");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.tblAppEntry.setName("tblAppEntry");
        this.tblDataEntry.setName("tblDataEntry");
        this.f7Creator.setName("f7Creator");
        this.pkCreateDate.setName("pkCreateDate");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));
        // pnlRoomInfo		
        this.pnlRoomInfo.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlRoomInfo.border.title")));		
        this.pnlRoomInfo.setAutoscrolls(true);
        // pnlRoomJoinInfo		
        this.pnlRoomJoinInfo.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlRoomJoinInfo.border.title")));		
        this.pnlRoomJoinInfo.setAutoscrolls(true);
        // sclPanel		
        this.sclPanel.setAutoscrolls(true);
        // contJoinDate		
        this.contJoinDate.setBoundLabelText(resHelper.getString("contJoinDate.boundLabelText"));		
        this.contJoinDate.setBoundLabelLength(100);		
        this.contJoinDate.setBoundLabelUnderline(true);
        // contApptJoinDate		
        this.contApptJoinDate.setBoundLabelText(resHelper.getString("contApptJoinDate.boundLabelText"));		
        this.contApptJoinDate.setBoundLabelLength(100);		
        this.contApptJoinDate.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contF7JoinDoScheme		
        this.contF7JoinDoScheme.setBoundLabelText(resHelper.getString("contF7JoinDoScheme.boundLabelText"));		
        this.contF7JoinDoScheme.setBoundLabelLength(100);		
        this.contF7JoinDoScheme.setBoundLabelUnderline(true);
        // ckFinish		
        this.ckFinish.setText(resHelper.getString("ckFinish.text"));		
        this.ckFinish.setEnabled(false);
        // contActFinishDate		
        this.contActFinishDate.setBoundLabelText(resHelper.getString("contActFinishDate.boundLabelText"));		
        this.contActFinishDate.setBoundLabelLength(70);		
        this.contActFinishDate.setBoundLabelUnderline(true);
        // kDTabbedPane1
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateDate		
        this.contCreateDate.setBoundLabelText(resHelper.getString("contCreateDate.boundLabelText"));		
        this.contCreateDate.setBoundLabelLength(100);		
        this.contCreateDate.setBoundLabelUnderline(true);
        // pkJoinDate
        // pkApptJoinDate
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // f7JoinDoScheme		
        this.f7JoinDoScheme.setDisplayFormat("$name$");		
        this.f7JoinDoScheme.setRequired(true);		
        this.f7JoinDoScheme.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.JoinDoSchemeQuery");
        this.f7JoinDoScheme.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7JoinDoScheme_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkActFinishDate
        // kDPanel1
        this.kDPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent e) {
                try {
                    kDPanel1_mouseExited(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.kDPanel1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                try {
                    kDPanel1_focusGained(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.kDPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                try {
                    kDPanel1_keyPressed(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void keyReleased(java.awt.event.KeyEvent e) {
                try {
                    kDPanel1_keyReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDPanel2
        // tblAppEntry
		String tblAppEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"promFinishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"isFinish\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"actFinishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"transactor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"description\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"isFinishFlag\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{promFinishDate}</t:Cell><t:Cell>$Resource{isFinish}</t:Cell><t:Cell>$Resource{actFinishDate}</t:Cell><t:Cell>$Resource{transactor}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{isFinishFlag}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblAppEntry.setFormatXml(resHelper.translateString("tblAppEntry",tblAppEntryStrXML));
        this.tblAppEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblAppEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblAppEntry.putBindContents("editData",new String[] {"id","name","promiseFinishDate","isFinish","actualFinishDate","transactor","remark","isFinishFlag"});


        // tblDataEntry
		String tblDataEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"isFinish\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"actFinishDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"description\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{isFinish}</t:Cell><t:Cell>$Resource{actFinishDate}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
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

                this.tblDataEntry.putBindContents("editData",new String[] {"name","isFinish","finishDate","remark","id"});


        // f7Creator		
        this.f7Creator.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7Creator.setEditFormat("$number$");		
        this.f7Creator.setDisplayFormat("$name$");		
        this.f7Creator.setCommitFormat("$number$");		
        this.f7Creator.setEnabled(false);
        // pkCreateDate		
        this.pkCreateDate.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 1013, 650));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 650));
        pnlRoomInfo.setBounds(new Rectangle(4, 5, 1005, 136));
        this.add(pnlRoomInfo, new KDLayout.Constraints(4, 5, 1005, 136, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        pnlRoomJoinInfo.setBounds(new Rectangle(4, 147, 1005, 494));
        this.add(pnlRoomJoinInfo, new KDLayout.Constraints(4, 147, 1005, 494, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //pnlRoomInfo
pnlRoomInfo.setLayout(new BorderLayout(0, 0));        pnlRoomInfo.add(sclPanel, BorderLayout.CENTER);
        //pnlRoomJoinInfo
        pnlRoomJoinInfo.setLayout(new KDLayout());
        pnlRoomJoinInfo.putClientProperty("OriginalBounds", new Rectangle(4, 147, 1005, 494));        contJoinDate.setBounds(new Rectangle(370, 20, 270, 19));
        pnlRoomJoinInfo.add(contJoinDate, new KDLayout.Constraints(370, 20, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contApptJoinDate.setBounds(new Rectangle(720, 20, 270, 19));
        pnlRoomJoinInfo.add(contApptJoinDate, new KDLayout.Constraints(720, 20, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(20, 67, 972, 48));
        pnlRoomJoinInfo.add(contDescription, new KDLayout.Constraints(20, 67, 972, 48, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contNumber.setBounds(new Rectangle(20, 20, 270, 19));
        pnlRoomJoinInfo.add(contNumber, new KDLayout.Constraints(20, 20, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contF7JoinDoScheme.setBounds(new Rectangle(20, 42, 270, 19));
        pnlRoomJoinInfo.add(contF7JoinDoScheme, new KDLayout.Constraints(20, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        ckFinish.setBounds(new Rectangle(370, 42, 78, 19));
        pnlRoomJoinInfo.add(ckFinish, new KDLayout.Constraints(370, 42, 78, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contActFinishDate.setBounds(new Rectangle(459, 42, 181, 19));
        pnlRoomJoinInfo.add(contActFinishDate, new KDLayout.Constraints(459, 42, 181, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTabbedPane1.setBounds(new Rectangle(21, 142, 969, 293));
        pnlRoomJoinInfo.add(kDTabbedPane1, new KDLayout.Constraints(21, 142, 969, 293, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreator.setBounds(new Rectangle(26, 450, 270, 19));
        pnlRoomJoinInfo.add(contCreator, new KDLayout.Constraints(26, 450, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateDate.setBounds(new Rectangle(370, 450, 270, 19));
        pnlRoomJoinInfo.add(contCreateDate, new KDLayout.Constraints(370, 450, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contJoinDate
        contJoinDate.setBoundEditor(pkJoinDate);
        //contApptJoinDate
        contApptJoinDate.setBoundEditor(pkApptJoinDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contF7JoinDoScheme
        contF7JoinDoScheme.setBoundEditor(f7JoinDoScheme);
        //contActFinishDate
        contActFinishDate.setBoundEditor(pkActFinishDate);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(tblAppEntry, BorderLayout.CENTER);
        //kDPanel2
kDPanel2.setLayout(new BorderLayout(0, 0));        kDPanel2.add(tblDataEntry, BorderLayout.CENTER);
        //contCreator
        contCreator.setBoundEditor(f7Creator);
        //contCreateDate
        contCreateDate.setBoundEditor(pkCreateDate);

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
		dataBinder.registerBinding("joinDate", java.util.Date.class, this.pkJoinDate, "value");
		dataBinder.registerBinding("apptJoinDate", java.util.Date.class, this.pkApptJoinDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("roomJoinDoScheme", com.kingdee.eas.fdc.sellhouse.JoinDoSchemeInfo.class, this.f7JoinDoScheme, "data");
		dataBinder.registerBinding("actualFinishDate", java.util.Date.class, this.pkActFinishDate, "value");
		dataBinder.registerBinding("approachEntry.id", com.kingdee.bos.util.BOSUuid.class, this.tblAppEntry, "id.text");
		dataBinder.registerBinding("approachEntry.name", String.class, this.tblAppEntry, "name.text");
		dataBinder.registerBinding("approachEntry.promiseFinishDate", java.util.Date.class, this.tblAppEntry, "promFinishDate.text");
		dataBinder.registerBinding("approachEntry.isFinish", boolean.class, this.tblAppEntry, "isFinish.text");
		dataBinder.registerBinding("approachEntry.actualFinishDate", java.util.Date.class, this.tblAppEntry, "actFinishDate.text");
		dataBinder.registerBinding("approachEntry.transactor", com.kingdee.eas.base.permission.UserInfo.class, this.tblAppEntry, "transactor.text");
		dataBinder.registerBinding("approachEntry.remark", String.class, this.tblAppEntry, "description.text");
		dataBinder.registerBinding("approachEntry", com.kingdee.eas.fdc.sellhouse.RoomJoinApproachEntryInfo.class, this.tblAppEntry, "userObject");
		dataBinder.registerBinding("approachEntry.isFinishFlag", boolean.class, this.tblAppEntry, "isFinishFlag.text");
		dataBinder.registerBinding("dataEntry.id", com.kingdee.bos.util.BOSUuid.class, this.tblDataEntry, "id.text");
		dataBinder.registerBinding("dataEntry.name", String.class, this.tblDataEntry, "name.text");
		dataBinder.registerBinding("dataEntry.isFinish", boolean.class, this.tblDataEntry, "isFinish.text");
		dataBinder.registerBinding("dataEntry.finishDate", java.util.Date.class, this.tblDataEntry, "actFinishDate.text");
		dataBinder.registerBinding("dataEntry.remark", String.class, this.tblDataEntry, "description.text");
		dataBinder.registerBinding("dataEntry", com.kingdee.eas.fdc.sellhouse.RoomJoinDataEntryInfo.class, this.tblDataEntry, "userObject");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.f7Creator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateDate, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomJoinEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.RoomJoinInfo)ov;
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
		getValidateHelper().registerBindProperty("joinDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("apptJoinDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("roomJoinDoScheme", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("actualFinishDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("approachEntry.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("approachEntry.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("approachEntry.promiseFinishDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("approachEntry.isFinish", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("approachEntry.actualFinishDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("approachEntry.transactor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("approachEntry.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("approachEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("approachEntry.isFinishFlag", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dataEntry.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dataEntry.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dataEntry.isFinish", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dataEntry.finishDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dataEntry.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dataEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    		
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
     * output f7JoinDoScheme_dataChanged method
     */
    protected void f7JoinDoScheme_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kDPanel1_mouseExited method
     */
    protected void kDPanel1_mouseExited(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output kDPanel1_focusGained method
     */
    protected void kDPanel1_focusGained(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output kDPanel1_keyPressed method
     */
    protected void kDPanel1_keyPressed(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output kDPanel1_keyReleased method
     */
    protected void kDPanel1_keyReleased(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output tblAppEntry_editStopped method
     */
    protected void tblAppEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("joinDate"));
        sic.add(new SelectorItemInfo("apptJoinDate"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("roomJoinDoScheme.*"));
        sic.add(new SelectorItemInfo("actualFinishDate"));
    sic.add(new SelectorItemInfo("approachEntry.id"));
    sic.add(new SelectorItemInfo("approachEntry.name"));
    sic.add(new SelectorItemInfo("approachEntry.promiseFinishDate"));
    sic.add(new SelectorItemInfo("approachEntry.isFinish"));
    sic.add(new SelectorItemInfo("approachEntry.actualFinishDate"));
        sic.add(new SelectorItemInfo("approachEntry.transactor.*"));
//        sic.add(new SelectorItemInfo("approachEntry.transactor.number"));
    sic.add(new SelectorItemInfo("approachEntry.remark"));
        sic.add(new SelectorItemInfo("approachEntry.*"));
//        sic.add(new SelectorItemInfo("approachEntry.number"));
    sic.add(new SelectorItemInfo("approachEntry.isFinishFlag"));
    sic.add(new SelectorItemInfo("dataEntry.id"));
    sic.add(new SelectorItemInfo("dataEntry.name"));
    sic.add(new SelectorItemInfo("dataEntry.isFinish"));
    sic.add(new SelectorItemInfo("dataEntry.finishDate"));
    sic.add(new SelectorItemInfo("dataEntry.remark"));
        sic.add(new SelectorItemInfo("dataEntry.*"));
//        sic.add(new SelectorItemInfo("dataEntry.number"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomJoinEditUI");
    }




}