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
public abstract class AbstractProgressReportBaseSchTaskPropertiesNewUI extends com.kingdee.eas.fdc.schedule.client.FDCScheduleTaskPropertiesNewUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProgressReportBaseSchTaskPropertiesNewUI.class);
    /**
     * output class constructor
     */
    public AbstractProgressReportBaseSchTaskPropertiesNewUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractProgressReportBaseSchTaskPropertiesNewUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        // CoreUI
        this.kDButtonGroup1.add(this.txtYes);
        this.kDButtonGroup1.add(this.txtNo);		
        this.pnlExecuteInfo.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,0),1), resHelper.getString("pnlExecuteInfo.border.title")));		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDPanelRelevanceCompact.setVisible(false);
		String kDTableTaskGuideAStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"docPath\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"docName\" t:width=\"260\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"docID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"isCPFile\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"guideType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{docPath}</t:Cell><t:Cell>$Resource{docName}</t:Cell><t:Cell>$Resource{docID}</t:Cell><t:Cell>$Resource{isCPFile}</t:Cell><t:Cell>$Resource{guideType}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";


        

		String kDTableTaskGuideBStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"docPath\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"docName\" t:width=\"260\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"isCPFile\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"docID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"guideType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{docPath}</t:Cell><t:Cell>$Resource{docName}</t:Cell><t:Cell>$Resource{isCPFile}</t:Cell><t:Cell>$Resource{docID}</t:Cell><t:Cell>$Resource{guideType}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";


        

		String kDTablePredecessorStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>#,##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"280\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"linkType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"diff\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{linkType}</t:Cell><t:Cell>$Resource{diff}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";


        

		String scheduleReportTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>#,##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"reportor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"reportData\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol1\" /><t:Column t:key=\"completePrecent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol2\" /><t:Column t:key=\"completeAmount\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"description\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{reportor}</t:Cell><t:Cell>$Resource{reportData}</t:Cell><t:Cell>$Resource{completePrecent}</t:Cell><t:Cell>$Resource{completeAmount}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";


        

		String tblTaskAppriseStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"evaluationResult\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"evaluationType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"ID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{evaluationResult}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{evaluationType}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{ID}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";


        

		String tblAchievementStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"adoc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"aType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"submitPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"submitDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{adoc}</t:Cell><t:Cell>$Resource{aType}</t:Cell><t:Cell>$Resource{submitPerson}</t:Cell><t:Cell>$Resource{submitDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";


        

		String tblQualityStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"ID\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"checkPoint\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"checkPost\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"checkPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"score\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"checkResult\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:configured=\"false\" /><t:Column t:key=\"subPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:configured=\"false\" /><t:Column t:key=\"subDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{ID}</t:Cell><t:Cell>$Resource{checkPoint}</t:Cell><t:Cell>$Resource{checkPost}</t:Cell><t:Cell>$Resource{checkPercent}</t:Cell><t:Cell>$Resource{score}</t:Cell><t:Cell>$Resource{checkResult}</t:Cell><t:Cell>$Resource{subPerson}</t:Cell><t:Cell>$Resource{subDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";


        

		String tblPScheduleStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"ID\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"imgDescription\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{ID}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{imgDescription}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";


        

		String tblSpeciallyStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>###0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"ID\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"projectSpeci\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"planStartDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"planEndDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"projectTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"responPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"responDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{ID}</t:Cell><t:Cell>$Resource{projectSpeci}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{planStartDate}</t:Cell><t:Cell>$Resource{planEndDate}</t:Cell><t:Cell>$Resource{projectTime}</t:Cell><t:Cell>$Resource{responPerson}</t:Cell><t:Cell>$Resource{responDept}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";


        

		String tblContractStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"ID\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"responPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"responDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"signPartyB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{ID}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{responPerson}</t:Cell><t:Cell>$Resource{responDept}</t:Cell><t:Cell>$Resource{signPartyB}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";


        

		String tblPayStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"ID\" t:width=\"1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"payMonth\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol2\" /><t:Column t:key=\"payMoneyY\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol3\" /><t:Column t:key=\"payMoneyN\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"currentMonthPay\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"currentPayY\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell>$Resource{ID}</t:Cell><t:Cell>$Resource{payMonth}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{payMoneyY}</t:Cell><t:Cell>$Resource{payMoneyN}</t:Cell><t:Cell>$Resource{currentMonthPay}</t:Cell><t:Cell>$Resource{currentPayY}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";


        
		
        this.contCompletePercent.setBoundLabelText(resHelper.getString("contCompletePercent.boundLabelText"));
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
        this.setBounds(new Rectangle(10, 10, 933, 700));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 933, 700));
        kDTabbedPane1.setBounds(new Rectangle(10, 10, 913, 679));
        this.add(kDTabbedPane1, new KDLayout.Constraints(10, 10, 913, 679, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDTabbedPane1
        kDTabbedPane1.add(pnlBaseInfo, resHelper.getString("pnlBaseInfo.constraints"));
        kDTabbedPane1.add(pnlAdvanced, resHelper.getString("pnlAdvanced.constraints"));
        //pnlBaseInfo
        pnlBaseInfo.setLayout(new KDLayout());
        pnlBaseInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 912, 646));        kDTSchedulePane.setBounds(new Rectangle(10, 297, 890, 341));
        pnlBaseInfo.add(kDTSchedulePane, new KDLayout.Constraints(10, 297, 890, 341, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contPlanStart.setBounds(new Rectangle(10, 70, 270, 19));
        pnlBaseInfo.add(contPlanStart, new KDLayout.Constraints(10, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizType.setBounds(new Rectangle(320, 41, 270, 19));
        pnlBaseInfo.add(contBizType, new KDLayout.Constraints(320, 41, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDWorkButton1.setBounds(new Rectangle(610, 351, 22, 19));
        pnlBaseInfo.add(kDWorkButton1, new KDLayout.Constraints(610, 351, 22, 19, 0));
        kDWorkButton2.setBounds(new Rectangle(670, 350, 22, 19));
        pnlBaseInfo.add(kDWorkButton2, new KDLayout.Constraints(670, 350, 22, 19, 0));
        contTaskName.setBounds(new Rectangle(10, 10, 270, 19));
        pnlBaseInfo.add(contTaskName, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(320, 10, 270, 19));
        pnlBaseInfo.add(contNumber, new KDLayout.Constraints(320, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDutyDep.setBounds(new Rectangle(320, 130, 270, 19));
        pnlBaseInfo.add(contDutyDep, new KDLayout.Constraints(320, 130, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTaskType.setBounds(new Rectangle(10, 40, 270, 19));
        pnlBaseInfo.add(contTaskType, new KDLayout.Constraints(10, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        txtYes.setBounds(new Rectangle(114, 102, 33, 19));
        pnlBaseInfo.add(txtYes, new KDLayout.Constraints(114, 102, 33, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIsAssessPoint.setBounds(new Rectangle(10, 100, 100, 19));
        pnlBaseInfo.add(contIsAssessPoint, new KDLayout.Constraints(10, 100, 100, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        txtNo.setBounds(new Rectangle(161, 102, 33, 19));
        pnlBaseInfo.add(txtNo, new KDLayout.Constraints(161, 102, 33, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDutyPerson.setBounds(new Rectangle(10, 130, 270, 19));
        pnlBaseInfo.add(contDutyPerson, new KDLayout.Constraints(10, 130, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPlanEnd.setBounds(new Rectangle(320, 70, 270, 19));
        pnlBaseInfo.add(contPlanEnd, new KDLayout.Constraints(320, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAccessDate.setBounds(new Rectangle(320, 100, 270, 19));
        pnlBaseInfo.add(contAccessDate, new KDLayout.Constraints(320, 100, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contTaskGuide.setBounds(new Rectangle(630, 10, 270, 19));
        pnlBaseInfo.add(contTaskGuide, new KDLayout.Constraints(630, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAchievementType.setBounds(new Rectangle(630, 40, 270, 19));
        pnlBaseInfo.add(contAchievementType, new KDLayout.Constraints(630, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contWorkDay.setBounds(new Rectangle(630, 70, 270, 19));
        pnlBaseInfo.add(contWorkDay, new KDLayout.Constraints(630, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contScheduleAppraisePerson.setBounds(new Rectangle(630, 130, 270, 19));
        pnlBaseInfo.add(contScheduleAppraisePerson, new KDLayout.Constraints(630, 130, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contQualityAppraisePerson.setBounds(new Rectangle(10, 159, 270, 19));
        pnlBaseInfo.add(contQualityAppraisePerson, new KDLayout.Constraints(10, 159, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAdd.setBounds(new Rectangle(626, 305, 22, 19));
        pnlBaseInfo.add(btnAdd, new KDLayout.Constraints(626, 305, 22, 19, 0));
        schedulereportbtn.setBounds(new Rectangle(681, 320, 85, 19));
        pnlBaseInfo.add(schedulereportbtn, new KDLayout.Constraints(681, 320, 85, 19, 0));
        schedulereportbtndel.setBounds(new Rectangle(782, 319, 93, 19));
        pnlBaseInfo.add(schedulereportbtndel, new KDLayout.Constraints(782, 319, 93, 19, 0));
        pnlExecuteInfo.setBounds(new Rectangle(2, 184, 902, 111));
        pnlBaseInfo.add(pnlExecuteInfo, new KDLayout.Constraints(2, 184, 902, 111, 0));
        btnDel.setBounds(new Rectangle(651, 308, 22, 19));
        pnlBaseInfo.add(btnDel, new KDLayout.Constraints(651, 308, 22, 19, 0));
        contBelongToSpecial.setBounds(new Rectangle(319, 158, 270, 19));
        pnlBaseInfo.add(contBelongToSpecial, new KDLayout.Constraints(319, 158, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCheckNode.setBounds(new Rectangle(630, 100, 270, 19));
        pnlBaseInfo.add(contCheckNode, new KDLayout.Constraints(630, 100, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDTSchedulePane
        kDTSchedulePane.add(kDPanelTaskGuide, resHelper.getString("kDPanelTaskGuide.constraints"));
        kDTSchedulePane.add(kDPanelPreposeTask, resHelper.getString("kDPanelPreposeTask.constraints"));
        kDTSchedulePane.add(kDPanelScheduleReport, resHelper.getString("kDPanelScheduleReport.constraints"));
        kDTSchedulePane.add(kDPanelTaskAppraise, resHelper.getString("kDPanelTaskAppraise.constraints"));
        kDTSchedulePane.add(kDPanelStageAchievement, resHelper.getString("kDPanelStageAchievement.constraints"));
        kDTSchedulePane.add(kDPanelQualityInspectPoint, resHelper.getString("kDPanelQualityInspectPoint.constraints"));
        kDTSchedulePane.add(kDPanelFigureSchedule, resHelper.getString("kDPanelFigureSchedule.constraints"));
        kDTSchedulePane.add(kDPanelRelevanceSpecialTask, resHelper.getString("kDPanelRelevanceSpecialTask.constraints"));
        kDTSchedulePane.add(kDPanelRelevanceCompact, resHelper.getString("kDPanelRelevanceCompact.constraints"));
        //kDPanelTaskGuide
kDPanelTaskGuide.setLayout(new BorderLayout(0, 0));        kDPanelTaskGuide.add(kDSplitPane1, BorderLayout.CENTER);
        //kDSplitPane1
        kDSplitPane1.add(kDSplitPane2, "left");
        kDSplitPane1.add(kDContainer4, "right");
        //kDSplitPane2
        kDSplitPane2.add(kDContainer2, "top");
        kDSplitPane2.add(kDContainer3, "bottom");
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(kDTableTaskGuideA, BorderLayout.CENTER);
        //kDContainer3
kDContainer3.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer3.getContentPane().add(kDTableTaskGuideB, BorderLayout.CENTER);
        //kDContainer4
kDContainer4.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer4.getContentPane().add(kDPanel1TaskGuide, BorderLayout.CENTER);
        kDPanel1TaskGuide.setLayout(null);        //kDPanelPreposeTask
kDPanelPreposeTask.setLayout(new BorderLayout(0, 0));        kDPanelPreposeTask.add(kDContainer1, BorderLayout.CENTER);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kDTablePredecessor, BorderLayout.CENTER);
        //kDPanelScheduleReport
        kDPanelScheduleReport.setLayout(new KDLayout());
        kDPanelScheduleReport.putClientProperty("OriginalBounds", new Rectangle(0, 0, 889, 308));        scheduleReportContainer.setBounds(new Rectangle(0, 0, 886, 345));
        kDPanelScheduleReport.add(scheduleReportContainer, new KDLayout.Constraints(0, 0, 886, 345, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //scheduleReportContainer
scheduleReportContainer.getContentPane().setLayout(new BorderLayout(0, 0));        scheduleReportContainer.getContentPane().add(scheduleReportTable, BorderLayout.CENTER);
        //kDPanelTaskAppraise
        kDPanelTaskAppraise.setLayout(new KDLayout());
        kDPanelTaskAppraise.putClientProperty("OriginalBounds", new Rectangle(0, 0, 889, 308));        kDContainerTaskApprise.setBounds(new Rectangle(0, -1, 884, 344));
        kDPanelTaskAppraise.add(kDContainerTaskApprise, new KDLayout.Constraints(0, -1, 884, 344, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainerTaskApprise
kDContainerTaskApprise.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainerTaskApprise.getContentPane().add(tblTaskApprise, BorderLayout.CENTER);
        //kDPanelStageAchievement
        kDPanelStageAchievement.setLayout(new KDLayout());
        kDPanelStageAchievement.putClientProperty("OriginalBounds", new Rectangle(0, 0, 889, 308));        kDContainer5.setBounds(new Rectangle(1, 1, 885, 342));
        kDPanelStageAchievement.add(kDContainer5, new KDLayout.Constraints(1, 1, 885, 342, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainer5
kDContainer5.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer5.getContentPane().add(tblAchievement, BorderLayout.CENTER);
        //kDPanelQualityInspectPoint
        kDPanelQualityInspectPoint.setLayout(new KDLayout());
        kDPanelQualityInspectPoint.putClientProperty("OriginalBounds", new Rectangle(0, 0, 889, 308));        kDContainer6.setBounds(new Rectangle(-1, 0, 886, 344));
        kDPanelQualityInspectPoint.add(kDContainer6, new KDLayout.Constraints(-1, 0, 886, 344, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainer6
kDContainer6.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer6.getContentPane().add(tblQuality, BorderLayout.CENTER);
        //kDPanelFigureSchedule
        kDPanelFigureSchedule.setLayout(new KDLayout());
        kDPanelFigureSchedule.putClientProperty("OriginalBounds", new Rectangle(0, 0, 889, 308));        contaner.setBounds(new Rectangle(-1, -1, 885, 344));
        kDPanelFigureSchedule.add(contaner, new KDLayout.Constraints(-1, -1, 885, 344, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contaner
contaner.getContentPane().setLayout(new BorderLayout(0, 0));        contaner.getContentPane().add(tblPSchedule, BorderLayout.CENTER);
        //kDPanelRelevanceSpecialTask
        kDPanelRelevanceSpecialTask.setLayout(new KDLayout());
        kDPanelRelevanceSpecialTask.putClientProperty("OriginalBounds", new Rectangle(0, 0, 889, 308));        kDContainerSpe.setBounds(new Rectangle(-1, -1, 885, 345));
        kDPanelRelevanceSpecialTask.add(kDContainerSpe, new KDLayout.Constraints(-1, -1, 885, 345, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainerSpe
kDContainerSpe.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainerSpe.getContentPane().add(tblSpecially, BorderLayout.CENTER);
        //kDPanelRelevanceCompact
        kDPanelRelevanceCompact.setLayout(new KDLayout());
        kDPanelRelevanceCompact.putClientProperty("OriginalBounds", new Rectangle(0, 0, 889, 308));        kDContainerCon.setBounds(new Rectangle(-1, -1, 887, 161));
        kDPanelRelevanceCompact.add(kDContainerCon, new KDLayout.Constraints(-1, -1, 887, 161, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDContainer7.setBounds(new Rectangle(-1, 160, 889, 183));
        kDPanelRelevanceCompact.add(kDContainer7, new KDLayout.Constraints(-1, 160, 889, 183, KDLayout.Constraints.ANCHOR_TOP_SCALE | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDContainerCon
kDContainerCon.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainerCon.getContentPane().add(tblContract, BorderLayout.CENTER);
        //kDContainer7
kDContainer7.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer7.getContentPane().add(tblPay, BorderLayout.CENTER);
        //contPlanStart
        contPlanStart.setBoundEditor(pkPlanStart);
        //contBizType
        contBizType.setBoundEditor(prmtBizType);
        //contTaskName
        contTaskName.setBoundEditor(txtTaskName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDutyDep
        contDutyDep.setBoundEditor(prmtDutyDep);
        //contTaskType
        contTaskType.setBoundEditor(cbTaskType);
        //contDutyPerson
        contDutyPerson.setBoundEditor(prmtDutyPerson);
        //contPlanEnd
        contPlanEnd.setBoundEditor(pkPlanEnd);
        //contAccessDate
        contAccessDate.setBoundEditor(pkAccessDate);
        //contTaskGuide
        contTaskGuide.setBoundEditor(prmtTaskGuide);
        //contAchievementType
        contAchievementType.setBoundEditor(prmtAchievementType);
        //contWorkDay
        contWorkDay.setBoundEditor(txtWorkDay);
        //contScheduleAppraisePerson
        contScheduleAppraisePerson.setBoundEditor(prmtScheduleAppraisePerson);
        //contQualityAppraisePerson
        contQualityAppraisePerson.setBoundEditor(prmtQualityAppraisePerson);
        //pnlExecuteInfo
        pnlExecuteInfo.setLayout(new KDLayout());
        pnlExecuteInfo.putClientProperty("OriginalBounds", new Rectangle(2, 184, 902, 111));        kDLabelContainer1.setBounds(new Rectangle(10, 69, 884, 33));
        pnlExecuteInfo.add(kDLabelContainer1, new KDLayout.Constraints(10, 69, 884, 33, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contActualStartDate.setBounds(new Rectangle(10, 13, 270, 19));
        pnlExecuteInfo.add(contActualStartDate, new KDLayout.Constraints(10, 13, 270, 19, 0));
        conIntendEndDate.setBounds(new Rectangle(315, 12, 270, 19));
        pnlExecuteInfo.add(conIntendEndDate, new KDLayout.Constraints(315, 12, 270, 19, 0));
        contWorkLoad.setBounds(new Rectangle(624, 14, 270, 19));
        pnlExecuteInfo.add(contWorkLoad, new KDLayout.Constraints(624, 14, 270, 19, 0));
        contCompletePercent.setBounds(new Rectangle(10, 43, 270, 19));
        pnlExecuteInfo.add(contCompletePercent, new KDLayout.Constraints(10, 43, 270, 19, 0));
        contTotalWorkLoad.setBounds(new Rectangle(624, 43, 270, 19));
        pnlExecuteInfo.add(contTotalWorkLoad, new KDLayout.Constraints(624, 43, 270, 19, 0));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtDesciption);
        //contActualStartDate
        contActualStartDate.setBoundEditor(kdDpActualStartDate);
        //conIntendEndDate
        conIntendEndDate.setBoundEditor(kdDpFinishDate);
        //contWorkLoad
        contWorkLoad.setBoundEditor(txtWorkload);
        //contCompletePercent
        contCompletePercent.setBoundEditor(txtCompletePercent);
        //contTotalWorkLoad
        contTotalWorkLoad.setBoundEditor(txtTotalWorkload);
        //contBelongToSpecial
        contBelongToSpecial.setBoundEditor(prmtBelongSpecial);
        //contCheckNode
        contCheckNode.setBoundEditor(prmtCheckNode);
        //pnlAdvanced
        pnlAdvanced.setLayout(new KDLayout());
        pnlAdvanced.putClientProperty("OriginalBounds", new Rectangle(0, 0, 912, 646));        contEndSchdule.setBounds(new Rectangle(630, 10, 270, 19));
        pnlAdvanced.add(contEndSchdule, new KDLayout.Constraints(630, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contHelpPerson.setBounds(new Rectangle(10, 40, 270, 19));
        pnlAdvanced.add(contHelpPerson, new KDLayout.Constraints(10, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRealityStart.setBounds(new Rectangle(10, 10, 270, 19));
        pnlAdvanced.add(contRealityStart, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPriorityLevel.setBounds(new Rectangle(10, 70, 270, 19));
        pnlAdvanced.add(contPriorityLevel, new KDLayout.Constraints(10, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTaskCalendar.setBounds(new Rectangle(10, 100, 270, 19));
        pnlAdvanced.add(contTaskCalendar, new KDLayout.Constraints(10, 100, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRealityEnd.setBounds(new Rectangle(320, 10, 270, 19));
        pnlAdvanced.add(contRealityEnd, new KDLayout.Constraints(320, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contHelpDep.setBounds(new Rectangle(320, 40, 270, 19));
        pnlAdvanced.add(contHelpDep, new KDLayout.Constraints(320, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contShape.setBounds(new Rectangle(320, 70, 270, 19));
        pnlAdvanced.add(contShape, new KDLayout.Constraints(320, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAppendEndQuantity.setBounds(new Rectangle(630, 40, 270, 19));
        pnlAdvanced.add(contAppendEndQuantity, new KDLayout.Constraints(630, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contColour.setBounds(new Rectangle(630, 70, 100, 19));
        pnlAdvanced.add(contColour, new KDLayout.Constraints(630, 70, 100, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRiskChargePerson.setBounds(new Rectangle(630, 100, 270, 19));
        pnlAdvanced.add(contRiskChargePerson, new KDLayout.Constraints(630, 100, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        cbColour.setBounds(new Rectangle(730, 70, 170, 19));
        pnlAdvanced.add(cbColour, new KDLayout.Constraints(730, 70, 170, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contEndSchdule
        contEndSchdule.setBoundEditor(txtEndSchedule);
        //contHelpPerson
        contHelpPerson.setBoundEditor(prmtHelpPerson);
        //contRealityStart
        contRealityStart.setBoundEditor(pkRealityStart);
        //contPriorityLevel
        contPriorityLevel.setBoundEditor(cbPriorityLevel);
        //contTaskCalendar
        contTaskCalendar.setBoundEditor(prmtTaskCalendar);
        //contRealityEnd
        contRealityEnd.setBoundEditor(pkRealityEnd);
        //contHelpDep
        contHelpDep.setBoundEditor(prmtHelpDep);
        //contShape
        contShape.setBoundEditor(cbShape);
        //contAppendEndQuantity
        contAppendEndQuantity.setBoundEditor(txtAppendEndQuantity);
        //contRiskChargePerson
        contRiskChargePerson.setBoundEditor(prmtRiskChargePerson);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemCloudShare);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
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
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnCloud);
        this.toolBar.add(saveBtn);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnReport);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPreview);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.ProgressReportBaseSchTaskPropertiesNewUIHandler";
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
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "ProgressReportBaseSchTaskPropertiesNewUI");
    }




}