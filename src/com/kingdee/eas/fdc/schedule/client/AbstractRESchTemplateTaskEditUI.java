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
public abstract class AbstractRESchTemplateTaskEditUI extends com.kingdee.eas.fdc.basedata.client.FDCTreeBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRESchTemplateTaskEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStandardTask;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTaskType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAchievementType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDutyPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDutyDep;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtStandardTask;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboTaskType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAchievementType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmpBusinessType;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtReferenceDay;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer3;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer4;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblGuideOne;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblGuideTwo;
    protected com.kingdee.bos.ctrl.swing.KDPanel KDPanelImg;
    protected com.kingdee.bos.ctrl.swing.KDContainer CtaPredecessorTask;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblTaskMain;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDutyPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDutyDep;
    protected com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo editData = null;
    protected actionAddRow actionAddRow = null;
    protected actionRemoveRow actionRemoveRow = null;
    /**
     * output class constructor
     */
    public AbstractRESchTemplateTaskEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRESchTemplateTaskEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddRow
        this.actionAddRow = new actionAddRow(this);
        getActionManager().registerAction("actionAddRow", actionAddRow);
         this.actionAddRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRemoveRow
        this.actionRemoveRow = new actionRemoveRow(this);
        getActionManager().registerAction("actionRemoveRow", actionRemoveRow);
         this.actionRemoveRow.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStandardTask = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTaskType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAchievementType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.contDutyPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDutyDep = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtStandardTask = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboTaskType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtAchievementType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmpBusinessType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtReferenceDay = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDContainer3 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer4 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblGuideOne = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblGuideTwo = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.KDPanelImg = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.CtaPredecessorTask = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblTaskMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtDutyPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtDutyDep = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contStandardTask.setName("contStandardTask");
        this.contTaskType.setName("contTaskType");
        this.contAchievementType.setName("contAchievementType");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.contDutyPerson.setName("contDutyPerson");
        this.contDutyDep.setName("contDutyDep");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.prmtStandardTask.setName("prmtStandardTask");
        this.comboTaskType.setName("comboTaskType");
        this.prmtAchievementType.setName("prmtAchievementType");
        this.prmpBusinessType.setName("prmpBusinessType");
        this.txtReferenceDay.setName("txtReferenceDay");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDContainer3.setName("kDContainer3");
        this.kDContainer2.setName("kDContainer2");
        this.kDContainer4.setName("kDContainer4");
        this.tblGuideOne.setName("tblGuideOne");
        this.tblGuideTwo.setName("tblGuideTwo");
        this.KDPanelImg.setName("KDPanelImg");
        this.CtaPredecessorTask.setName("CtaPredecessorTask");
        this.tblTaskMain.setName("tblTaskMain");
        this.txtDescription.setName("txtDescription");
        this.prmtDutyPerson.setName("prmtDutyPerson");
        this.prmtDutyDep.setName("prmtDutyDep");
        // CoreUI		
        this.btnSave.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.btnAttachment.setVisible(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(60);		
        this.contName.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(60);		
        this.contNumber.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);
        // contStandardTask		
        this.contStandardTask.setBoundLabelText(resHelper.getString("contStandardTask.boundLabelText"));		
        this.contStandardTask.setBoundLabelLength(60);		
        this.contStandardTask.setBoundLabelUnderline(true);
        // contTaskType		
        this.contTaskType.setBoundLabelText(resHelper.getString("contTaskType.boundLabelText"));		
        this.contTaskType.setBoundLabelLength(60);		
        this.contTaskType.setBoundLabelUnderline(true);
        // contAchievementType		
        this.contAchievementType.setBoundLabelText(resHelper.getString("contAchievementType.boundLabelText"));		
        this.contAchievementType.setBoundLabelLength(60);		
        this.contAchievementType.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(60);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(79);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDTabbedPane1
        // kDScrollPane1
        // contDutyPerson		
        this.contDutyPerson.setBoundLabelText(resHelper.getString("contDutyPerson.boundLabelText"));		
        this.contDutyPerson.setBoundLabelLength(60);		
        this.contDutyPerson.setBoundLabelUnderline(true);
        // contDutyDep		
        this.contDutyDep.setBoundLabelText(resHelper.getString("contDutyDep.boundLabelText"));		
        this.contDutyDep.setBoundLabelLength(60);		
        this.contDutyDep.setBoundLabelUnderline(true);
        // txtName
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // prmtStandardTask		
        this.prmtStandardTask.setQueryInfo("com.kingdee.eas.fdc.schedule.app.StandardTaskGuideNewQuery");
        // comboTaskType		
        this.comboTaskType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum").toArray());
        // prmtAchievementType
        // prmpBusinessType
        // txtReferenceDay
        // kDPanel1
        // kDPanel2
        // kDContainer3		
        this.kDContainer3.setTitle(resHelper.getString("kDContainer3.title"));
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // kDContainer4		
        this.kDContainer4.setTitle(resHelper.getString("kDContainer4.title"));
        // tblGuideOne
		String tblGuideOneStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"docPath\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"docName\" t:width=\"260\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{docPath}</t:Cell><t:Cell>$Resource{docName}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblGuideOne.setFormatXml(resHelper.translateString("tblGuideOne",tblGuideOneStrXML));

        

        // tblGuideTwo
		String tblGuideTwoStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"docPath\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"id\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol1\" /><t:Column t:key=\"docName\" t:width=\"260\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{docPath}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{docName}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblGuideTwo.setFormatXml(resHelper.translateString("tblGuideTwo",tblGuideTwoStrXML));

        

        // KDPanelImg
        // CtaPredecessorTask		
        this.CtaPredecessorTask.setTitle(resHelper.getString("CtaPredecessorTask.title"));
        // tblTaskMain
		String tblTaskMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"number\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"differentRelation\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"differenceDay\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"pTask\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{differentRelation}</t:Cell><t:Cell>$Resource{differenceDay}</t:Cell><t:Cell>$Resource{pTask}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblTaskMain.setFormatXml(resHelper.translateString("tblTaskMain",tblTaskMainStrXML));

        

        // txtDescription
        // prmtDutyPerson		
        this.prmtDutyPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtDutyPerson.setCommitFormat("$name$");		
        this.prmtDutyPerson.setDisplayFormat("$name$");		
        this.prmtDutyPerson.setEditFormat("$name$");
        // prmtDutyDep		
        this.prmtDutyDep.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7FullOrgUnitQuery");		
        this.prmtDutyDep.setCommitFormat("$name$");		
        this.prmtDutyDep.setDisplayFormat("$name$");		
        this.prmtDutyDep.setEditFormat("$name$");
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
        this.setBounds(new Rectangle(10, 10, 1000, 570));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1000, 570));
        contName.setBounds(new Rectangle(12, 9, 270, 19));
        this.add(contName, new KDLayout.Constraints(12, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(373, 9, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(373, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(12, 105, 270, 19));
        this.add(contDescription, new KDLayout.Constraints(12, 105, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStandardTask.setBounds(new Rectangle(713, 9, 270, 19));
        this.add(contStandardTask, new KDLayout.Constraints(713, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contTaskType.setBounds(new Rectangle(12, 41, 270, 19));
        this.add(contTaskType, new KDLayout.Constraints(12, 41, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAchievementType.setBounds(new Rectangle(713, 41, 270, 19));
        this.add(contAchievementType, new KDLayout.Constraints(713, 41, 270, 19, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(373, 41, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(373, 41, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(12, 73, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(12, 73, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTabbedPane1.setBounds(new Rectangle(13, 181, 977, 379));
        this.add(kDTabbedPane1, new KDLayout.Constraints(13, 181, 977, 379, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDScrollPane1.setBounds(new Rectangle(12, 131, 977, 36));
        this.add(kDScrollPane1, new KDLayout.Constraints(12, 131, 977, 36, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contDutyPerson.setBounds(new Rectangle(373, 73, 270, 19));
        this.add(contDutyPerson, new KDLayout.Constraints(373, 73, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDutyDep.setBounds(new Rectangle(713, 73, 270, 19));
        this.add(contDutyDep, new KDLayout.Constraints(713, 73, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contStandardTask
        contStandardTask.setBoundEditor(prmtStandardTask);
        //contTaskType
        contTaskType.setBoundEditor(comboTaskType);
        //contAchievementType
        contAchievementType.setBoundEditor(prmtAchievementType);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(prmpBusinessType);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtReferenceDay);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 976, 346));        kDContainer3.setBounds(new Rectangle(2, 4, 498, 163));
        kDPanel1.add(kDContainer3, new KDLayout.Constraints(2, 4, 498, 163, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer2.setBounds(new Rectangle(2, 179, 498, 163));
        kDPanel1.add(kDContainer2, new KDLayout.Constraints(2, 179, 498, 163, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer4.setBounds(new Rectangle(509, 3, 458, 339));
        kDPanel1.add(kDContainer4, new KDLayout.Constraints(509, 3, 458, 339, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainer3
kDContainer3.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer3.getContentPane().add(tblGuideOne, BorderLayout.CENTER);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(tblGuideTwo, BorderLayout.CENTER);
        //kDContainer4
kDContainer4.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer4.getContentPane().add(KDPanelImg, BorderLayout.CENTER);
        KDPanelImg.setLayout(new KDLayout());
        KDPanelImg.putClientProperty("OriginalBounds", new Rectangle(509, 3, 458, 339));        //kDPanel2
        kDPanel2.setLayout(new KDLayout());
        kDPanel2.putClientProperty("OriginalBounds", new Rectangle(0, 0, 976, 346));        CtaPredecessorTask.setBounds(new Rectangle(3, 1, 957, 342));
        kDPanel2.add(CtaPredecessorTask, new KDLayout.Constraints(3, 1, 957, 342, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //CtaPredecessorTask
CtaPredecessorTask.getContentPane().setLayout(new BorderLayout(0, 0));        CtaPredecessorTask.getContentPane().add(tblTaskMain, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contDutyPerson
        contDutyPerson.setBoundEditor(prmtDutyPerson);
        //contDutyDep
        contDutyDep.setBoundEditor(prmtDutyDep);

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
        menuTool.add(menuItemToolBarCustom);
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
		dataBinder.registerBinding("standardTask", com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo.class, this.prmtStandardTask, "data");
		dataBinder.registerBinding("taskType", com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum.class, this.comboTaskType, "selectedItem");
		dataBinder.registerBinding("achievementType", com.kingdee.eas.fdc.schedule.AchievementTypeInfo.class, this.prmtAchievementType, "data");
		dataBinder.registerBinding("referenceDay", int.class, this.txtReferenceDay, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("adminPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtDutyPerson, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.RESchTemplateTaskEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo)ov;
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
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("standardTask", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("taskType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("achievementType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("referenceDay", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adminPerson", ValidateHelper.ON_SAVE);    		
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("standardTask.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("standardTask.id"));
        	sic.add(new SelectorItemInfo("standardTask.number"));
        	sic.add(new SelectorItemInfo("standardTask.name"));
		}
        sic.add(new SelectorItemInfo("taskType"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("achievementType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("achievementType.id"));
        	sic.add(new SelectorItemInfo("achievementType.number"));
        	sic.add(new SelectorItemInfo("achievementType.name"));
		}
        sic.add(new SelectorItemInfo("referenceDay"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("adminPerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("adminPerson.id"));
        	sic.add(new SelectorItemInfo("adminPerson.number"));
        	sic.add(new SelectorItemInfo("adminPerson.name"));
		}
        return sic;
    }        
    	

    /**
     * output actionAddRow_actionPerformed method
     */
    public void actionAddRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRemoveRow_actionPerformed method
     */
    public void actionRemoveRow_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareactionAddRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionAddRow() {
    	return false;
    }
	public RequestContext prepareactionRemoveRow(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionRemoveRow() {
    	return false;
    }

    /**
     * output actionAddRow class
     */     
    protected class actionAddRow extends ItemAction {     
    
        public actionAddRow()
        {
            this(null);
        }

        public actionAddRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionAddRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionAddRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRESchTemplateTaskEditUI.this, "actionAddRow", "actionAddRow_actionPerformed", e);
        }
    }

    /**
     * output actionRemoveRow class
     */     
    protected class actionRemoveRow extends ItemAction {     
    
        public actionRemoveRow()
        {
            this(null);
        }

        public actionRemoveRow(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionRemoveRow.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionRemoveRow.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionRemoveRow.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRESchTemplateTaskEditUI.this, "actionRemoveRow", "actionRemoveRow_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "RESchTemplateTaskEditUI");
    }




}