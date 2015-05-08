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
public abstract class AbstractFDCPlanTaskPropertyUI extends com.kingdee.eas.fdc.schedule.client.FDCScheduleTaskPropertiesUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCPlanTaskPropertyUI.class);
    /**
     * output class constructor
     */
    public AbstractFDCPlanTaskPropertyUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractFDCPlanTaskPropertyUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        // CoreUI
		String predecessorsTableStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:NumberFormat>###,##0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"linkType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"linkTimes\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{linkType}</t:Cell><t:Cell>$Resource{linkTimes}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";


        

		String tblWorkloadStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"completeAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"completePercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"completeDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"creatorMemo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"auditTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{completeAmt}</t:Cell><t:Cell>$Resource{completePercent}</t:Cell><t:Cell>$Resource{completeDate}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{creatorMemo}</t:Cell><t:Cell>$Resource{auditTime}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";


        

		String tableSafeStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"status\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"date\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"description\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{status}</t:Cell><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";


        

		String tableSafePlanStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"description\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol2\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";


        

		String tblPreventionStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"prevention\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{prevention}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";


        

		String tblQCItemStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"longNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"chkCriterion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"description\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{longNumber}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{chkCriterion}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";


        

		String tblQCPositionStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"position\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"samplePropotion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"person\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"checkFrequency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"qtyAssignID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"qtyCheckEntryID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{position}</t:Cell><t:Cell>$Resource{samplePropotion}</t:Cell><t:Cell>$Resource{person}</t:Cell><t:Cell>$Resource{checkFrequency}</t:Cell><t:Cell>$Resource{qtyAssignID}</t:Cell><t:Cell>$Resource{qtyCheckEntryID}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";


        

		String tblQtyReltStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"checkItem\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"checkPostion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"checkPropotion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"score\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"result\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"memo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{checkItem}</t:Cell><t:Cell>$Resource{checkPostion}</t:Cell><t:Cell>$Resource{checkPropotion}</t:Cell><t:Cell>$Resource{score}</t:Cell><t:Cell>$Resource{result}</t:Cell><t:Cell>$Resource{memo}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";


        

		String tblQualityBigEventStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"happenTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"directPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"indirectPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"suggestion\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{happenTime}</t:Cell><t:Cell>$Resource{directPerson}</t:Cell><t:Cell>$Resource{indirectPerson}</t:Cell><t:Cell>$Resource{suggestion}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";


        

		String tblWorkResultStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"adminDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"attachmentName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"attachmentID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{adminDept}</t:Cell><t:Cell>$Resource{attachmentName}</t:Cell><t:Cell>$Resource{attachmentID}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";


        

		String tblTaskLogStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>YYYY-MM-DD</c:NumberFormat></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"happenTime\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"title\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{happenTime}</t:Cell><t:Cell>$Resource{title}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";


        

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
        this.setLayout(null);
        kDTabbedPane1.setBounds(new Rectangle(4, 5, 692, 440));
        this.add(kDTabbedPane1, null);
        //kDTabbedPane1
        kDTabbedPane1.add(mainPanel, resHelper.getString("mainPanel.constraints"));
        kDTabbedPane1.add(predecessorsPanel, resHelper.getString("predecessorsPanel.constraints"));
        kDTabbedPane1.add(workloadPanel, resHelper.getString("workloadPanel.constraints"));
        kDTabbedPane1.add(costInfoPanel, resHelper.getString("costInfoPanel.constraints"));
        kDTabbedPane1.add(materialPlanPanel, resHelper.getString("materialPlanPanel.constraints"));
        kDTabbedPane1.add(qualityPlanPanel, resHelper.getString("qualityPlanPanel.constraints"));
        kDTabbedPane1.add(workResultPanel, resHelper.getString("workResultPanel.constraints"));
        kDTabbedPane1.add(taskLogPanel, resHelper.getString("taskLogPanel.constraints"));
        //mainPanel
mainPanel.setLayout(new BorderLayout(0, 0));        mainPanel.add(kDTabbedPane3, BorderLayout.CENTER);
        //kDTabbedPane3
        kDTabbedPane3.add(mainPnlSchedule, resHelper.getString("mainPnlSchedule.constraints"));
        kDTabbedPane3.add(mainPnlExecute, resHelper.getString("mainPnlExecute.constraints"));
        //mainPnlSchedule
        mainPnlSchedule.setLayout(null);        kDLabelContainer1.setBounds(new Rectangle(392, 10, 270, 19));
        mainPnlSchedule.add(kDLabelContainer1, null);
        contNatureTimes.setBounds(new Rectangle(392, 64, 270, 19));
        mainPnlSchedule.add(contNatureTimes, null);
        chkKey.setBounds(new Rectangle(392, 172, 146, 21));
        mainPnlSchedule.add(chkKey, null);
        kDLabelContainer4.setBounds(new Rectangle(10, 37, 270, 19));
        mainPnlSchedule.add(kDLabelContainer4, null);
        kDLabelContainer5.setBounds(new Rectangle(10, 64, 270, 19));
        mainPnlSchedule.add(kDLabelContainer5, null);
        kDLabelContainer6.setBounds(new Rectangle(392, 145, 106, 19));
        mainPnlSchedule.add(kDLabelContainer6, null);
        txtColor.setBounds(new Rectangle(492, 145, 170, 19));
        mainPnlSchedule.add(txtColor, null);
        kDLabelContainer7.setBounds(new Rectangle(10, 199, 270, 19));
        mainPnlSchedule.add(kDLabelContainer7, null);
        labelPart.setBounds(new Rectangle(10, 91, 270, 19));
        mainPnlSchedule.add(labelPart, null);
        labelAdminPerson.setBounds(new Rectangle(10, 118, 270, 19));
        mainPnlSchedule.add(labelAdminPerson, null);
        kDLabelContainer20.setBounds(new Rectangle(10, 280, 652, 80));
        mainPnlSchedule.add(kDLabelContainer20, null);
        contExecor.setBounds(new Rectangle(10, 172, 270, 19));
        mainPnlSchedule.add(contExecor, null);
        contWBSNumber.setBounds(new Rectangle(10, 10, 270, 19));
        mainPnlSchedule.add(contWBSNumber, null);
        contEffectTimes.setBounds(new Rectangle(392, 37, 270, 19));
        mainPnlSchedule.add(contEffectTimes, null);
        contAdministrator.setBounds(new Rectangle(10, 145, 270, 19));
        mainPnlSchedule.add(contAdministrator, null);
        contPriority.setBounds(new Rectangle(392, 91, 270, 19));
        mainPnlSchedule.add(contPriority, null);
        contShape.setBounds(new Rectangle(392, 118, 270, 19));
        mainPnlSchedule.add(contShape, null);
        kDLabelContainer2.setBounds(new Rectangle(10, 226, 270, 19));
        mainPnlSchedule.add(kDLabelContainer2, null);
        kDPanel1.setBounds(new Rectangle(380, 190, 300, 80));
        mainPnlSchedule.add(kDPanel1, null);
        kDLabelContainer8.setBounds(new Rectangle(10, 253, 270, 19));
        mainPnlSchedule.add(kDLabelContainer8, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtName);
        //contNatureTimes
        contNatureTimes.setBoundEditor(txtNatureTimes);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtStartDate);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(txtEndDate);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(prmtNoter);
        //labelPart
        labelPart.setBoundEditor(f7Part);
        //labelAdminPerson
        labelAdminPerson.setBoundEditor(prmtManager);
        //kDLabelContainer20
        kDLabelContainer20.setBoundEditor(txtDescription);
        //contExecor
        contExecor.setBoundEditor(prmtExecor);
        //contWBSNumber
        contWBSNumber.setBoundEditor(txtWBSNumber);
        //contEffectTimes
        contEffectTimes.setBoundEditor(txtEffectTimes);
        //contAdministrator
        contAdministrator.setBoundEditor(prmtAdministrator);
        //contPriority
        contPriority.setBoundEditor(comboxPriority);
        //contShape
        contShape.setBoundEditor(comboxShape);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(prmtRistResPerson);
        //kDPanel1
        kDPanel1.setLayout(null);        chkIsMileStone.setBounds(new Rectangle(12, 10, 200, 19));
        kDPanel1.add(chkIsMileStone, null);
        conMileStoneStatus.setBounds(new Rectangle(12, 35, 270, 19));
        kDPanel1.add(conMileStoneStatus, null);
        //conMileStoneStatus
        conMileStoneStatus.setBoundEditor(comboxMileStoneStatus);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(cbBizType);
        //mainPnlExecute
        mainPnlExecute.setLayout(null);        kDLabelContainer3.setBounds(new Rectangle(10, 37, 270, 19));
        mainPnlExecute.add(kDLabelContainer3, null);
        contActualDate.setBounds(new Rectangle(405, 10, 270, 19));
        mainPnlExecute.add(contActualDate, null);
        contActualStartDate.setBounds(new Rectangle(10, 10, 270, 19));
        mainPnlExecute.add(contActualStartDate, null);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtProcess);
        //contActualDate
        contActualDate.setBoundEditor(pkActualDate);
        //contActualStartDate
        contActualStartDate.setBoundEditor(pkActualStartDate);
        //predecessorsPanel
        predecessorsPanel.setLayout(null);        contPredecessorPanel.setBounds(new Rectangle(10, 4, 673, 401));
        predecessorsPanel.add(contPredecessorPanel, null);
        //contPredecessorPanel
contPredecessorPanel.getContentPane().setLayout(new BorderLayout(0, 0));        contPredecessorPanel.getContentPane().add(predecessorsTable, BorderLayout.CENTER);
        //workloadPanel
        workloadPanel.setLayout(null);        conPlanWorkload.setBounds(new Rectangle(10, 10, 270, 19));
        workloadPanel.add(conPlanWorkload, null);
        kDLabelContainer9.setBounds(new Rectangle(480, 10, 200, 19));
        workloadPanel.add(kDLabelContainer9, null);
        conWorkload.setBounds(new Rectangle(10, 60, 670, 340));
        workloadPanel.add(conWorkload, null);
        btnWorkLoadView.setBounds(new Rectangle(560, 35, 120, 19));
        workloadPanel.add(btnWorkLoadView, null);
        conCompletePercent.setBounds(new Rectangle(10, 35, 270, 19));
        workloadPanel.add(conCompletePercent, null);
        //conPlanWorkload
        conPlanWorkload.setBoundEditor(txtPlanWorkload);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(prmtMeasureUnit);
        //conWorkload
conWorkload.getContentPane().setLayout(new BorderLayout(0, 0));        conWorkload.getContentPane().add(tblWorkload, BorderLayout.CENTER);
        //conCompletePercent
        conCompletePercent.setBoundEditor(txtCompletePercent);
        //costInfoPanel
        costInfoPanel.setLayout(null);        kDLabel1.setBounds(new Rectangle(10, 80, 100, 19));
        costInfoPanel.add(kDLabel1, null);
        kDScrollPane1.setBounds(new Rectangle(10, 100, 660, 150));
        costInfoPanel.add(kDScrollPane1, null);
        conAimCost.setBounds(new Rectangle(10, 10, 270, 19));
        costInfoPanel.add(conAimCost, null);
        conDeviation.setBounds(new Rectangle(320, 10, 270, 19));
        costInfoPanel.add(conDeviation, null);
        conActCost.setBounds(new Rectangle(10, 40, 270, 19));
        costInfoPanel.add(conActCost, null);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtCostMemo, null);
        //conAimCost
        conAimCost.setBoundEditor(txtAimCost);
        //conDeviation
        conDeviation.setBoundEditor(txtDeviation);
        //conActCost
        conActCost.setBoundEditor(txtActCost);
        //materialPlanPanel
        materialPlanPanel.setLayout(null);        kDLabelContainer10.setBounds(new Rectangle(10, 5, 60, 19));
        materialPlanPanel.add(kDLabelContainer10, null);
        kDSeparator2.setBounds(new Rectangle(342, 30, 8, 371));
        materialPlanPanel.add(kDSeparator2, null);
        kDLabelContainer11.setBounds(new Rectangle(364, 4, 60, 19));
        materialPlanPanel.add(kDLabelContainer11, null);
        tableSafe.setBounds(new Rectangle(359, 53, 325, 347));
        materialPlanPanel.add(tableSafe, null);
        btnSafeAddline.setBounds(new Rectangle(441, 4, 80, 20));
        materialPlanPanel.add(btnSafeAddline, null);
        btnSafeDeleteline.setBounds(new Rectangle(605, 4, 80, 20));
        materialPlanPanel.add(btnSafeDeleteline, null);
        btnSafeEditLine.setBounds(new Rectangle(523, 4, 80, 20));
        materialPlanPanel.add(btnSafeEditLine, null);
        btnUnAudit.setBounds(new Rectangle(605, 28, 80, 20));
        materialPlanPanel.add(btnUnAudit, null);
        btnAudit.setBounds(new Rectangle(523, 28, 80, 20));
        materialPlanPanel.add(btnAudit, null);
        btnAuditResult.setBounds(new Rectangle(359, 28, 80, 20));
        materialPlanPanel.add(btnAuditResult, null);
        btnWorkFlow.setBounds(new Rectangle(441, 28, 80, 20));
        materialPlanPanel.add(btnWorkFlow, null);
        tableSafePlan.setBounds(new Rectangle(9, 31, 317, 366));
        materialPlanPanel.add(tableSafePlan, null);
        btnSafePlanAdd.setBounds(new Rectangle(76, 4, 80, 20));
        materialPlanPanel.add(btnSafePlanAdd, null);
        btnSafePlanEdit.setBounds(new Rectangle(160, 4, 80, 20));
        materialPlanPanel.add(btnSafePlanEdit, null);
        btnSafePlanDel.setBounds(new Rectangle(244, 4, 80, 20));
        materialPlanPanel.add(btnSafePlanDel, null);
        //qualityPlanPanel
qualityPlanPanel.setLayout(new BorderLayout(0, 0));        qualityPlanPanel.add(kDTabbedPane2, BorderLayout.CENTER);
        //kDTabbedPane2
        kDTabbedPane2.add(preventionPanel, resHelper.getString("preventionPanel.constraints"));
        kDTabbedPane2.add(qualityCheckPanel, resHelper.getString("qualityCheckPanel.constraints"));
        kDTabbedPane2.add(qualityFaultPanel, resHelper.getString("qualityFaultPanel.constraints"));
        kDTabbedPane2.add(bigEventPanel, resHelper.getString("bigEventPanel.constraints"));
        //preventionPanel
        preventionPanel.setLayout(null);        conPrevention.setBounds(new Rectangle(10, 10, 660, 330));
        preventionPanel.add(conPrevention, null);
        //conPrevention
conPrevention.getContentPane().setLayout(new BorderLayout(0, 0));        conPrevention.getContentPane().add(tblPrevention, BorderLayout.CENTER);
        //qualityCheckPanel
        qualityCheckPanel.setLayout(null);        kDContainer2.setBounds(new Rectangle(10, 10, 645, 150));
        qualityCheckPanel.add(kDContainer2, null);
        kDContainer3.setBounds(new Rectangle(10, 170, 645, 170));
        qualityCheckPanel.add(kDContainer3, null);
        //kDContainer2
kDContainer2.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer2.getContentPane().add(tblQCItem, BorderLayout.CENTER);
        //kDContainer3
kDContainer3.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer3.getContentPane().add(tblQCPosition, BorderLayout.CENTER);
        //qualityFaultPanel
        qualityFaultPanel.setLayout(null);        kDContainer1.setBounds(new Rectangle(10, 10, 645, 350));
        qualityFaultPanel.add(kDContainer1, null);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(tblQtyRelt, BorderLayout.CENTER);
        //bigEventPanel
        bigEventPanel.setLayout(null);        conBigEvent.setBounds(new Rectangle(10, 10, 660, 325));
        bigEventPanel.add(conBigEvent, null);
        //conBigEvent
conBigEvent.getContentPane().setLayout(new BorderLayout(0, 0));        conBigEvent.getContentPane().add(tblQualityBigEvent, BorderLayout.CENTER);
        //workResultPanel
        workResultPanel.setLayout(null);        conWorkResult.setBounds(new Rectangle(10, 10, 670, 385));
        workResultPanel.add(conWorkResult, null);
        //conWorkResult
conWorkResult.getContentPane().setLayout(new BorderLayout(0, 0));        conWorkResult.getContentPane().add(tblWorkResult, BorderLayout.CENTER);
        //taskLogPanel
        taskLogPanel.setLayout(null);        labTaskLogList.setBounds(new Rectangle(10, 5, 60, 19));
        taskLogPanel.add(labTaskLogList, null);
        labTaskLogDescription.setBounds(new Rectangle(370, 5, 60, 19));
        taskLogPanel.add(labTaskLogDescription, null);
        tblTaskLog.setBounds(new Rectangle(10, 30, 330, 370));
        taskLogPanel.add(tblTaskLog, null);
        kDSeparator3.setBounds(new Rectangle(350, 5, 8, 400));
        taskLogPanel.add(kDSeparator3, null);
        kDScrollPane2.setBounds(new Rectangle(370, 30, 300, 370));
        taskLogPanel.add(kDScrollPane2, null);
        btnAddTaskLog.setBounds(new Rectangle(90, 5, 80, 19));
        taskLogPanel.add(btnAddTaskLog, null);
        btnDelTaskLog.setBounds(new Rectangle(260, 5, 80, 19));
        taskLogPanel.add(btnDelTaskLog, null);
        btnEditTaskLog.setBounds(new Rectangle(175, 5, 80, 19));
        taskLogPanel.add(btnEditTaskLog, null);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtTaskLogDetail, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
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
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnOK);
        this.toolBar.add(separator1);
        this.toolBar.add(btnTaskPre);
        this.toolBar.add(btnTaskNext);
        this.toolBar.add(btnAttacthment);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.FDCPlanTaskPropertyUIHandler";
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
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "FDCPlanTaskPropertyUI");
    }




}