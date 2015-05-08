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
public abstract class AbstractMainProgressReportBaseSchTaskPropertiesNewUI extends com.kingdee.eas.fdc.schedule.client.ProgressReportBaseSchTaskPropertiesNewUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMainProgressReportBaseSchTaskPropertiesNewUI.class);
    /**
     * output class constructor
     */
    public AbstractMainProgressReportBaseSchTaskPropertiesNewUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMainProgressReportBaseSchTaskPropertiesNewUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        // CoreUI
        this.kDButtonGroup1.add(this.txtYes);
        this.kDButtonGroup1.add(this.txtNo);		
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
        this.setBounds(new Rectangle(10, 10, 960, 550));
this.setLayout(new BorderLayout(0, 0));
        this.add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(kDTabbedPane1, null);
        //kDTabbedPane1
        kDTabbedPane1.add(pnlBaseInfo, resHelper.getString("pnlBaseInfo.constraints"));
        kDTabbedPane1.add(pnlAdvanced, resHelper.getString("pnlAdvanced.constraints"));
        //pnlBaseInfo
        pnlBaseInfo.setLayout(null);        kDTSchedulePane.setBounds(new Rectangle(8, 289, 921, 341));
        pnlBaseInfo.add(kDTSchedulePane, null);
        contPlanStart.setBounds(new Rectangle(8, 51, 270, 19));
        pnlBaseInfo.add(contPlanStart, null);
        contBizType.setBounds(new Rectangle(333, 29, 270, 19));
        pnlBaseInfo.add(contBizType, null);
        kDWorkButton1.setBounds(new Rectangle(610, 351, 22, 19));
        pnlBaseInfo.add(kDWorkButton1, null);
        kDWorkButton2.setBounds(new Rectangle(670, 350, 22, 19));
        pnlBaseInfo.add(kDWorkButton2, null);
        contTaskName.setBounds(new Rectangle(8, 7, 270, 19));
        pnlBaseInfo.add(contTaskName, null);
        contNumber.setBounds(new Rectangle(333, 7, 270, 19));
        pnlBaseInfo.add(contNumber, null);
        contTaskType.setBounds(new Rectangle(8, 29, 270, 19));
        pnlBaseInfo.add(contTaskType, null);
        txtYes.setBounds(new Rectangle(114, 74, 33, 19));
        pnlBaseInfo.add(txtYes, null);
        contIsAssessPoint.setBounds(new Rectangle(8, 74, 100, 19));
        pnlBaseInfo.add(contIsAssessPoint, null);
        txtNo.setBounds(new Rectangle(161, 74, 33, 19));
        pnlBaseInfo.add(txtNo, null);
        contPlanEnd.setBounds(new Rectangle(333, 51, 270, 19));
        pnlBaseInfo.add(contPlanEnd, null);
        contAccessDate.setBounds(new Rectangle(333, 74, 270, 19));
        pnlBaseInfo.add(contAccessDate, null);
        contTaskGuide.setBounds(new Rectangle(658, 7, 270, 19));
        pnlBaseInfo.add(contTaskGuide, null);
        contAchievementType.setBounds(new Rectangle(658, 29, 270, 19));
        pnlBaseInfo.add(contAchievementType, null);
        contWorkDay.setBounds(new Rectangle(658, 51, 270, 19));
        pnlBaseInfo.add(contWorkDay, null);
        btnAdd.setBounds(new Rectangle(626, 305, 22, 19));
        pnlBaseInfo.add(btnAdd, null);
        schedulereportbtn.setBounds(new Rectangle(681, 320, 85, 19));
        pnlBaseInfo.add(schedulereportbtn, null);
        schedulereportbtndel.setBounds(new Rectangle(782, 319, 93, 19));
        pnlBaseInfo.add(schedulereportbtndel, null);
        pnlExecuteInfo.setBounds(new Rectangle(719, 110, 180, 51));
        pnlBaseInfo.add(pnlExecuteInfo, null);
        btnDel.setBounds(new Rectangle(651, 308, 22, 19));
        pnlBaseInfo.add(btnDel, null);
        kDLabelContainer1.setBounds(new Rectangle(8, 229, 921, 46));
        pnlBaseInfo.add(kDLabelContainer1, null);
        contCheckNode.setBounds(new Rectangle(658, 74, 270, 19));
        pnlBaseInfo.add(contCheckNode, null);
        contDutyDep.setBounds(new Rectangle(8, 106, 270, 19));
        pnlBaseInfo.add(contDutyDep, null);
        contHelpDep.setBounds(new Rectangle(8, 128, 270, 19));
        pnlBaseInfo.add(contHelpDep, null);
        contScheduleAppraisePerson.setBounds(new Rectangle(8, 150, 270, 19));
        pnlBaseInfo.add(contScheduleAppraisePerson, null);
        contDutyPerson.setBounds(new Rectangle(333, 106, 270, 19));
        pnlBaseInfo.add(contDutyPerson, null);
        contHelpPerson.setBounds(new Rectangle(333, 128, 270, 19));
        pnlBaseInfo.add(contHelpPerson, null);
        contQualityAppraisePerson.setBounds(new Rectangle(333, 150, 270, 19));
        pnlBaseInfo.add(contQualityAppraisePerson, null);
        kDSeparator2.setBounds(new Rectangle(1, 98, 953, 10));
        pnlBaseInfo.add(kDSeparator2, null);
        kDSeparator3.setBounds(new Rectangle(1, 176, 953, 10));
        pnlBaseInfo.add(kDSeparator3, null);
        contActualStartDate.setBounds(new Rectangle(333, 185, 270, 19));
        pnlBaseInfo.add(contActualStartDate, null);
        conIntendEndDate.setBounds(new Rectangle(658, 185, 270, 19));
        pnlBaseInfo.add(conIntendEndDate, null);
        contWorkLoad.setBounds(new Rectangle(8, 207, 270, 19));
        pnlBaseInfo.add(contWorkLoad, null);
        contCompletePercent.setBounds(new Rectangle(8, 185, 270, 19));
        pnlBaseInfo.add(contCompletePercent, null);
        contTotalWorkLoad.setBounds(new Rectangle(333, 207, 270, 19));
        pnlBaseInfo.add(contTotalWorkLoad, null);
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
kDPanelScheduleReport.setLayout(new BorderLayout(0, 0));        kDPanelScheduleReport.add(scheduleReportContainer, BorderLayout.CENTER);
        //scheduleReportContainer
scheduleReportContainer.getContentPane().setLayout(new BorderLayout(0, 0));        scheduleReportContainer.getContentPane().add(scheduleReportTable, BorderLayout.CENTER);
        //kDPanelTaskAppraise
kDPanelTaskAppraise.setLayout(new BorderLayout(0, 0));        kDPanelTaskAppraise.add(kDContainerTaskApprise, BorderLayout.CENTER);
        //kDContainerTaskApprise
kDContainerTaskApprise.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainerTaskApprise.getContentPane().add(tblTaskApprise, BorderLayout.CENTER);
        //kDPanelStageAchievement
kDPanelStageAchievement.setLayout(new BorderLayout(0, 0));        kDPanelStageAchievement.add(kDContainer5, BorderLayout.CENTER);
        //kDContainer5
kDContainer5.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer5.getContentPane().add(tblAchievement, BorderLayout.CENTER);
        //kDPanelQualityInspectPoint
kDPanelQualityInspectPoint.setLayout(new BorderLayout(0, 0));        kDPanelQualityInspectPoint.add(kDContainer6, BorderLayout.CENTER);
        //kDContainer6
kDContainer6.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer6.getContentPane().add(tblQuality, BorderLayout.CENTER);
        //kDPanelFigureSchedule
kDPanelFigureSchedule.setLayout(new BorderLayout(0, 0));        kDPanelFigureSchedule.add(contaner, BorderLayout.CENTER);
        //contaner
contaner.getContentPane().setLayout(new BorderLayout(0, 0));        contaner.getContentPane().add(tblPSchedule, BorderLayout.CENTER);
        //kDPanelRelevanceSpecialTask
kDPanelRelevanceSpecialTask.setLayout(new BorderLayout(0, 0));        kDPanelRelevanceSpecialTask.add(kDContainerSpe, BorderLayout.CENTER);
        //kDContainerSpe
kDContainerSpe.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainerSpe.getContentPane().add(tblSpecially, BorderLayout.CENTER);
        //kDPanelRelevanceCompact
kDPanelRelevanceCompact.setLayout(new BorderLayout(0, 0));        kDPanelRelevanceCompact.add(kDContainerCon, BorderLayout.NORTH);
        kDPanelRelevanceCompact.add(kDContainer7, BorderLayout.CENTER);
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
        //contTaskType
        contTaskType.setBoundEditor(cbTaskType);
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
        pnlExecuteInfo.setLayout(new KDLayout());
        pnlExecuteInfo.putClientProperty("OriginalBounds", new Rectangle(719, 110, 180, 51));        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtDesciption);
        //contCheckNode
        contCheckNode.setBoundEditor(prmtCheckNode);
        //contDutyDep
        contDutyDep.setBoundEditor(prmtDutyDep);
        //contHelpDep
        contHelpDep.setBoundEditor(prmtHelpDep);
        //contScheduleAppraisePerson
        contScheduleAppraisePerson.setBoundEditor(prmtScheduleAppraisePerson);
        //contDutyPerson
        contDutyPerson.setBoundEditor(prmtDutyPerson);
        //contHelpPerson
        contHelpPerson.setBoundEditor(prmtHelpPerson);
        //contQualityAppraisePerson
        contQualityAppraisePerson.setBoundEditor(prmtQualityAppraisePerson);
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
        //pnlAdvanced
        pnlAdvanced.setLayout(null);        contEndSchdule.setBounds(new Rectangle(658, 10, 270, 19));
        pnlAdvanced.add(contEndSchdule, null);
        contRealityStart.setBounds(new Rectangle(10, 10, 270, 19));
        pnlAdvanced.add(contRealityStart, null);
        contPriorityLevel.setBounds(new Rectangle(10, 32, 270, 19));
        pnlAdvanced.add(contPriorityLevel, null);
        contTaskCalendar.setBounds(new Rectangle(10, 54, 270, 19));
        pnlAdvanced.add(contTaskCalendar, null);
        contRealityEnd.setBounds(new Rectangle(334, 10, 270, 19));
        pnlAdvanced.add(contRealityEnd, null);
        contShape.setBounds(new Rectangle(334, 32, 270, 19));
        pnlAdvanced.add(contShape, null);
        contAppendEndQuantity.setBounds(new Rectangle(658, 32, 270, 19));
        pnlAdvanced.add(contAppendEndQuantity, null);
        contRiskChargePerson.setBounds(new Rectangle(334, 54, 270, 19));
        pnlAdvanced.add(contRiskChargePerson, null);
        contBelongToSpecial.setBounds(new Rectangle(10, 76, 270, 19));
        pnlAdvanced.add(contBelongToSpecial, null);
        contColour.setBounds(new Rectangle(658, 54, 270, 19));
        pnlAdvanced.add(contColour, null);
        //contEndSchdule
        contEndSchdule.setBoundEditor(txtEndSchedule);
        //contRealityStart
        contRealityStart.setBoundEditor(pkRealityStart);
        //contPriorityLevel
        contPriorityLevel.setBoundEditor(cbPriorityLevel);
        //contTaskCalendar
        contTaskCalendar.setBoundEditor(prmtTaskCalendar);
        //contRealityEnd
        contRealityEnd.setBoundEditor(pkRealityEnd);
        //contShape
        contShape.setBoundEditor(cbShape);
        //contAppendEndQuantity
        contAppendEndQuantity.setBoundEditor(txtAppendEndQuantity);
        //contRiskChargePerson
        contRiskChargePerson.setBoundEditor(prmtRiskChargePerson);
        //contBelongToSpecial
        contBelongToSpecial.setBoundEditor(prmtBelongSpecial);
        //contColour
        contColour.setBoundEditor(cbColour);

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
        this.toolBar.add(btnReport);
        this.toolBar.add(saveBtn);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(kDSeparatorCloud);
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
	    return "com.kingdee.eas.fdc.schedule.app.MainProgressReportBaseSchTaskPropertiesNewUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "MainProgressReportBaseSchTaskPropertiesNewUI");
    }




}