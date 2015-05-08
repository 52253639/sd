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
public abstract class AbstractScheduleAdjustReqBillEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractScheduleAdjustReqBillEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane mainTabbedPane;
    protected com.kingdee.bos.ctrl.swing.KDContainer contAdjustTask;
    protected com.kingdee.bos.ctrl.swing.KDContainer contNewTask;
    protected com.kingdee.bos.ctrl.swing.KDContainer contRpt;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAdjustTask;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblNewTask;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRpt;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdjustType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdjustReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAffect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboxAffect;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboxAdjustType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdjustReason;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalTimes;
    protected com.kingdee.eas.fdc.schedule.ScheduleAdjustReqBillInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractScheduleAdjustReqBillEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractScheduleAdjustReqBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionUnAudit
        String _tempStr = null;
        actionUnAudit.setEnabled(false);
        actionUnAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.mainTabbedPane = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contAdjustTask = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contNewTask = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contRpt = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.tblAdjustTask = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblNewTask = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblRpt = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contAdjustType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdjustReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAffect = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboxAffect = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboxAdjustType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtAdjustReason = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtTotalTimes = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.mainTabbedPane.setName("mainTabbedPane");
        this.contAdjustTask.setName("contAdjustTask");
        this.contNewTask.setName("contNewTask");
        this.contRpt.setName("contRpt");
        this.tblAdjustTask.setName("tblAdjustTask");
        this.tblNewTask.setName("tblNewTask");
        this.tblRpt.setName("tblRpt");
        this.contAdjustType.setName("contAdjustType");
        this.contAdjustReason.setName("contAdjustReason");
        this.contCreateTime.setName("contCreateTime");
        this.contCreator.setName("contCreator");
        this.contAffect.setName("contAffect");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.comboxAffect.setName("comboxAffect");
        this.comboxAdjustType.setName("comboxAdjustType");
        this.prmtAdjustReason.setName("prmtAdjustReason");
        this.txtTotalTimes.setName("txtTotalTimes");
        // CoreUI
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // mainTabbedPane
        this.mainTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    mainTabbedPane_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contAdjustTask
        // contNewTask
        // contRpt
        // tblAdjustTask
		String tblAdjustTaskStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>###,##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>###,##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>yyyy-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>yyyy-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>yyyy-MM-DD</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"effectTimes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"adjustEffectTimes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"adjustStartDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"adjustEndDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"adminDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"adminPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{effectTimes}</t:Cell><t:Cell>$Resource{adjustEffectTimes}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{adjustStartDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{adjustEndDate}</t:Cell><t:Cell>$Resource{adminDept}</t:Cell><t:Cell>$Resource{adminPerson}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblAdjustTask.setFormatXml(resHelper.translateString("tblAdjustTask",tblAdjustTaskStrXML));

                this.tblAdjustTask.putBindContents("editData",new String[] {"task","task.name","task.effectTimes","effectTimes","task.start","startDate","task.end","endDate","task.planDept.name","task.adminPerson.name"});


        // tblNewTask
		String tblNewTaskStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:NumberFormat>###,##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>yyyy-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>yyyy-MM-DD</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"parentTask\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dependTask\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"effectTimes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"adminDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"adminPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{parentTask}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{dependTask}</t:Cell><t:Cell>$Resource{effectTimes}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{adminDept}</t:Cell><t:Cell>$Resource{adminPerson}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblNewTask.setFormatXml(resHelper.translateString("tblNewTask",tblNewTaskStrXML));

                this.tblNewTask.putBindContents("editData",new String[] {"parentTask","number","name","","effectTimes","start","end","respDept","adminPerson"});


        // tblRpt
		String tblRptStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"startDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"adjustStartDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"startDateDiff\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"endDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"adjustEndDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"endDateDiff\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"effectTimes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"adjustEffectTimes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"effectTimesDiff\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{startDate}</t:Cell><t:Cell>$Resource{adjustStartDate}</t:Cell><t:Cell>$Resource{startDateDiff}</t:Cell><t:Cell>$Resource{endDate}</t:Cell><t:Cell>$Resource{adjustEndDate}</t:Cell><t:Cell>$Resource{endDateDiff}</t:Cell><t:Cell>$Resource{effectTimes}</t:Cell><t:Cell>$Resource{adjustEffectTimes}</t:Cell><t:Cell>$Resource{effectTimesDiff}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number_Row2}</t:Cell><t:Cell>$Resource{name_Row2}</t:Cell><t:Cell>$Resource{startDate_Row2}</t:Cell><t:Cell>$Resource{adjustStartDate_Row2}</t:Cell><t:Cell>$Resource{startDateDiff_Row2}</t:Cell><t:Cell>$Resource{endDate_Row2}</t:Cell><t:Cell>$Resource{adjustEndDate_Row2}</t:Cell><t:Cell>$Resource{endDateDiff_Row2}</t:Cell><t:Cell>$Resource{effectTimes_Row2}</t:Cell><t:Cell>$Resource{adjustEffectTimes_Row2}</t:Cell><t:Cell>$Resource{effectTimesDiff_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblRpt.setFormatXml(resHelper.translateString("tblRpt",tblRptStrXML));

        

        // contAdjustType		
        this.contAdjustType.setBoundLabelText(resHelper.getString("contAdjustType.boundLabelText"));		
        this.contAdjustType.setBoundLabelLength(100);		
        this.contAdjustType.setBoundLabelUnderline(true);
        // contAdjustReason		
        this.contAdjustReason.setBoundLabelText(resHelper.getString("contAdjustReason.boundLabelText"));		
        this.contAdjustReason.setBoundLabelLength(100);		
        this.contAdjustReason.setBoundLabelUnderline(true);
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
        // contAffect		
        this.contAffect.setBoundLabelText(resHelper.getString("contAffect.boundLabelText"));		
        this.contAffect.setBoundLabelLength(100);		
        this.contAffect.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setMaxLength(80);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // comboxAffect		
        this.comboxAffect.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.schedule.ScheduleAffectEnum").toArray());		
        this.comboxAffect.setRequired(true);
        // comboxAdjustType		
        this.comboxAdjustType.setRequired(true);		
        this.comboxAdjustType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.schedule.ScheduleAdjustTypeEnum").toArray());
        // prmtAdjustReason		
        this.prmtAdjustReason.setEditFormat("$number$");		
        this.prmtAdjustReason.setDisplayFormat("$name$");		
        this.prmtAdjustReason.setRequired(true);		
        this.prmtAdjustReason.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7AdjustReasonQuery");
        // txtTotalTimes		
        this.txtTotalTimes.setDataType(1);		
        this.txtTotalTimes.setPrecision(0);
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
        contNumber.setBounds(new Rectangle(11, 15, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(11, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(371, 15, 270, 19));
        this.add(contName, new KDLayout.Constraints(371, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        mainTabbedPane.setBounds(new Rectangle(10, 104, 996, 524));
        this.add(mainTabbedPane, new KDLayout.Constraints(10, 104, 996, 524, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAdjustType.setBounds(new Rectangle(10, 42, 270, 19));
        this.add(contAdjustType, new KDLayout.Constraints(10, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAdjustReason.setBounds(new Rectangle(10, 71, 270, 19));
        this.add(contAdjustReason, new KDLayout.Constraints(10, 71, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(731, 42, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(731, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreator.setBounds(new Rectangle(731, 15, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(731, 15, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAffect.setBounds(new Rectangle(371, 42, 270, 19));
        this.add(contAffect, new KDLayout.Constraints(371, 42, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer8.setBounds(new Rectangle(371, 71, 270, 19));
        this.add(kDLabelContainer8, new KDLayout.Constraints(371, 71, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //mainTabbedPane
        mainTabbedPane.add(contAdjustTask, resHelper.getString("contAdjustTask.constraints"));
        mainTabbedPane.add(contNewTask, resHelper.getString("contNewTask.constraints"));
        mainTabbedPane.add(contRpt, resHelper.getString("contRpt.constraints"));
        //contAdjustTask
contAdjustTask.getContentPane().setLayout(new BorderLayout(0, 0));        contAdjustTask.getContentPane().add(tblAdjustTask, BorderLayout.CENTER);
        //contNewTask
contNewTask.getContentPane().setLayout(new BorderLayout(0, 0));        contNewTask.getContentPane().add(tblNewTask, BorderLayout.CENTER);
        //contRpt
contRpt.getContentPane().setLayout(new BorderLayout(0, 0));        contRpt.getContentPane().add(tblRpt, BorderLayout.CENTER);
        //contAdjustType
        contAdjustType.setBoundEditor(comboxAdjustType);
        //contAdjustReason
        contAdjustReason.setBoundEditor(prmtAdjustReason);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contAffect
        contAffect.setBoundEditor(comboxAffect);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtTotalTimes);

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
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(separator2);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        //menuTable1
        menuTable1.add(menuItemAddLine);
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
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
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
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("adjustEntrys.task", com.kingdee.eas.fdc.schedule.ScheduleAdjustTaskEntryCollection.class, this.tblAdjustTask, "number.text");
		dataBinder.registerBinding("adjustEntrys", com.kingdee.eas.fdc.schedule.ScheduleAdjustTaskEntryInfo.class, this.tblAdjustTask, "userObject");
		dataBinder.registerBinding("adjustEntrys.task.name", String.class, this.tblAdjustTask, "name.text");
		dataBinder.registerBinding("adjustEntrys.task.effectTimes", java.math.BigDecimal.class, this.tblAdjustTask, "effectTimes.text");
		dataBinder.registerBinding("adjustEntrys.effectTimes", java.math.BigDecimal.class, this.tblAdjustTask, "adjustEffectTimes.text");
		dataBinder.registerBinding("adjustEntrys.task.start", java.util.Date.class, this.tblAdjustTask, "startDate.text");
		dataBinder.registerBinding("adjustEntrys.startDate", java.util.Date.class, this.tblAdjustTask, "adjustStartDate.text");
		dataBinder.registerBinding("adjustEntrys.endDate", java.util.Date.class, this.tblAdjustTask, "adjustEndDate.text");
		dataBinder.registerBinding("adjustEntrys.task.end", java.util.Date.class, this.tblAdjustTask, "endDate.text");
		dataBinder.registerBinding("adjustEntrys.task.adminPerson.name", String.class, this.tblAdjustTask, "adminPerson.text");
		dataBinder.registerBinding("adjustEntrys.task.planDept.name", String.class, this.tblAdjustTask, "adminDept.text");
		dataBinder.registerBinding("newTaskEntrys", com.kingdee.eas.fdc.schedule.ScheduleNewTaskEntryInfo.class, this.tblNewTask, "userObject");
		dataBinder.registerBinding("newTaskEntrys.parentTask", com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo.class, this.tblNewTask, "parentTask.text");
		dataBinder.registerBinding("newTaskEntrys.number", String.class, this.tblNewTask, "number.text");
		dataBinder.registerBinding("newTaskEntrys.name", String.class, this.tblNewTask, "name.text");
		dataBinder.registerBinding("newTaskEntrys.effectTimes", java.math.BigDecimal.class, this.tblNewTask, "effectTimes.text");
		dataBinder.registerBinding("newTaskEntrys.start", java.util.Date.class, this.tblNewTask, "startDate.text");
		dataBinder.registerBinding("newTaskEntrys.end", java.util.Date.class, this.tblNewTask, "endDate.text");
		dataBinder.registerBinding("newTaskEntrys.adminPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.tblNewTask, "adminPerson.text");
		dataBinder.registerBinding("newTaskEntrys.respDept", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.tblNewTask, "adminDept.text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("affect", com.kingdee.eas.fdc.schedule.ScheduleAffectEnum.class, this.comboxAffect, "selectedItem");
		dataBinder.registerBinding("adjustType", com.kingdee.eas.fdc.schedule.ScheduleAdjustTypeEnum.class, this.comboxAdjustType, "selectedItem");
		dataBinder.registerBinding("adjustReason", com.kingdee.eas.fdc.schedule.PlanAdjustReasonInfo.class, this.prmtAdjustReason, "data");
		dataBinder.registerBinding("totalTimes", java.math.BigDecimal.class, this.txtTotalTimes, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.ScheduleAdjustReqBillEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.ScheduleAdjustReqBillInfo)ov;
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
		getValidateHelper().registerBindProperty("adjustEntrys.task", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adjustEntrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adjustEntrys.task.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adjustEntrys.task.effectTimes", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adjustEntrys.effectTimes", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adjustEntrys.task.start", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adjustEntrys.startDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adjustEntrys.endDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adjustEntrys.task.end", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adjustEntrys.task.adminPerson.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adjustEntrys.task.planDept.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newTaskEntrys", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newTaskEntrys.parentTask", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newTaskEntrys.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newTaskEntrys.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newTaskEntrys.effectTimes", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newTaskEntrys.start", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newTaskEntrys.end", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newTaskEntrys.adminPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newTaskEntrys.respDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("affect", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adjustType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adjustReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("totalTimes", ValidateHelper.ON_SAVE);    		
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
     * output mainTabbedPane_stateChanged method
     */
    protected void mainTabbedPane_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("adjustEntrys.task.*"));
//        sic.add(new SelectorItemInfo("adjustEntrys.task.number"));
        sic.add(new SelectorItemInfo("adjustEntrys.*"));
//        sic.add(new SelectorItemInfo("adjustEntrys.number"));
    sic.add(new SelectorItemInfo("adjustEntrys.task.name"));
    sic.add(new SelectorItemInfo("adjustEntrys.task.effectTimes"));
    sic.add(new SelectorItemInfo("adjustEntrys.effectTimes"));
    sic.add(new SelectorItemInfo("adjustEntrys.task.start"));
    sic.add(new SelectorItemInfo("adjustEntrys.startDate"));
    sic.add(new SelectorItemInfo("adjustEntrys.endDate"));
    sic.add(new SelectorItemInfo("adjustEntrys.task.end"));
    sic.add(new SelectorItemInfo("adjustEntrys.task.adminPerson.name"));
    sic.add(new SelectorItemInfo("adjustEntrys.task.planDept.name"));
        sic.add(new SelectorItemInfo("newTaskEntrys.*"));
//        sic.add(new SelectorItemInfo("newTaskEntrys.number"));
        sic.add(new SelectorItemInfo("newTaskEntrys.parentTask.*"));
//        sic.add(new SelectorItemInfo("newTaskEntrys.parentTask.number"));
    sic.add(new SelectorItemInfo("newTaskEntrys.number"));
    sic.add(new SelectorItemInfo("newTaskEntrys.name"));
    sic.add(new SelectorItemInfo("newTaskEntrys.effectTimes"));
    sic.add(new SelectorItemInfo("newTaskEntrys.start"));
    sic.add(new SelectorItemInfo("newTaskEntrys.end"));
        sic.add(new SelectorItemInfo("newTaskEntrys.adminPerson.*"));
//        sic.add(new SelectorItemInfo("newTaskEntrys.adminPerson.number"));
        sic.add(new SelectorItemInfo("newTaskEntrys.respDept.*"));
//        sic.add(new SelectorItemInfo("newTaskEntrys.respDept.number"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("affect"));
        sic.add(new SelectorItemInfo("adjustType"));
        sic.add(new SelectorItemInfo("adjustReason.*"));
        sic.add(new SelectorItemInfo("totalTimes"));
        return sic;
    }        
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionUnAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "ScheduleAdjustReqBillEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}