/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

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
public abstract class AbstractValueInputEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractValueInputEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProject;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane panel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    protected com.kingdee.bos.ctrl.swing.KDContainer contEntry;
    protected com.kingdee.bos.ctrl.swing.KDContainer contPriceEntry;
    protected com.kingdee.bos.ctrl.swing.KDContainer contTotalEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtPriceEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtTotalEntry;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtVersion;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.eas.fdc.market.ValueInputInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractValueInputEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractValueInputEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.panel = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contVersion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contPriceEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contTotalEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtPriceEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtTotalEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtVersion = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contCreator.setName("contCreator");
        this.contAuditor.setName("contAuditor");
        this.contCreateTime.setName("contCreateTime");
        this.contAuditTime.setName("contAuditTime");
        this.contProject.setName("contProject");
        this.panel.setName("panel");
        this.contVersion.setName("contVersion");
        this.contName.setName("contName");
        this.txtNumber.setName("txtNumber");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.prmtCreator.setName("prmtCreator");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkCreateTime.setName("pkCreateTime");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtProject.setName("prmtProject");
        this.contEntry.setName("contEntry");
        this.contPriceEntry.setName("contPriceEntry");
        this.contTotalEntry.setName("contTotalEntry");
        this.kdtEntry.setName("kdtEntry");
        this.kdtPriceEntry.setName("kdtPriceEntry");
        this.kdtTotalEntry.setName("kdtTotalEntry");
        this.txtVersion.setName("txtVersion");
        this.txtName.setName("txtName");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelAlignment(7);		
        this.contNumber.setVisible(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);		
        this.contAuditTime.setVisible(true);		
        this.contAuditTime.setBoundLabelAlignment(7);
        // contProject		
        this.contProject.setBoundLabelText(resHelper.getString("contProject.boundLabelText"));		
        this.contProject.setBoundLabelLength(100);		
        this.contProject.setBoundLabelUnderline(true);		
        this.contProject.setVisible(true);
        // panel
        // contVersion		
        this.contVersion.setBoundLabelText(resHelper.getString("contVersion.boundLabelText"));		
        this.contVersion.setBoundLabelLength(100);		
        this.contVersion.setBoundLabelUnderline(true);		
        this.contVersion.setEnabled(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);		
        this.contName.setBoundLabelAlignment(7);		
        this.contName.setVisible(true);		
        this.contName.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setRequired(true);
        // kDScrollPane1		
        this.kDScrollPane1.setAutoscrolls(true);
        // txtDescription		
        this.txtDescription.setToolTipText(resHelper.getString("txtDescription.toolTipText"));		
        this.txtDescription.setMaxLength(1000);		
        this.txtDescription.setWrapStyleWord(true);		
        this.txtDescription.setLineWrap(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$number$");
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$number$");		
        this.prmtAuditor.setCommitFormat("$number$");
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setVisible(true);		
        this.pkAuditTime.setEnabled(false);
        // prmtProject		
        this.prmtProject.setVisible(true);		
        this.prmtProject.setEditable(true);		
        this.prmtProject.setDisplayFormat("$name$");		
        this.prmtProject.setEditFormat("$number$");		
        this.prmtProject.setCommitFormat("$number$");		
        this.prmtProject.setRequired(false);		
        this.prmtProject.setEnabled(false);		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7OperationPhasesQuery");
        // contEntry
        // contPriceEntry
        // contTotalEntry
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"project\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"batch\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"build\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"productType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"decorate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"4\" /><t:Column t:key=\"modelName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"modelType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"modelArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"account\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"area\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"price\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"11\" /><t:Column t:key=\"calculateType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{batch}</t:Cell><t:Cell>$Resource{build}</t:Cell><t:Cell>$Resource{productType}</t:Cell><t:Cell>$Resource{decorate}</t:Cell><t:Cell>$Resource{modelName}</t:Cell><t:Cell>$Resource{modelType}</t:Cell><t:Cell>$Resource{modelArea}</t:Cell><t:Cell>$Resource{account}</t:Cell><t:Cell>$Resource{area}</t:Cell><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{calculateType}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));
        this.kdtEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntry.putBindContents("editData",new String[] {"project","batch","build","productType","decorate","modelName","modelType","modelArea","account","area","date","price","calculateType","amount","remark"});


        // kdtPriceEntry
		String kdtPriceEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"build\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"unit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"room\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sellType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"priceType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"standardTotalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"baseStandardPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildingPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{build}</t:Cell><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{sellType}</t:Cell><t:Cell>$Resource{priceType}</t:Cell><t:Cell>$Resource{standardTotalAmount}</t:Cell><t:Cell>$Resource{baseStandardPrice}</t:Cell><t:Cell>$Resource{buildingPrice}</t:Cell><t:Cell>$Resource{roomPrice}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtPriceEntry.setFormatXml(resHelper.translateString("kdtPriceEntry",kdtPriceEntryStrXML));

                this.kdtPriceEntry.putBindContents("editData",new String[] {"project","build","unit","room","sellType","priceType","standardTotalAmount","baseStandardPrice","buildingPrice","roomPrice","buildingArea","roomArea"});


        // kdtTotalEntry
		String kdtTotalEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"project\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"operationPhases\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"batch\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"productType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"decorate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"account\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"area\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"saccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sarea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sprice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"samount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"daccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"darea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"dprice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"damount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"paccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"parea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"pprice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"pamount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{operationPhases}</t:Cell><t:Cell>$Resource{batch}</t:Cell><t:Cell>$Resource{productType}</t:Cell><t:Cell>$Resource{decorate}</t:Cell><t:Cell>$Resource{account}</t:Cell><t:Cell>$Resource{area}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{saccount}</t:Cell><t:Cell>$Resource{sarea}</t:Cell><t:Cell>$Resource{sprice}</t:Cell><t:Cell>$Resource{samount}</t:Cell><t:Cell>$Resource{daccount}</t:Cell><t:Cell>$Resource{darea}</t:Cell><t:Cell>$Resource{dprice}</t:Cell><t:Cell>$Resource{damount}</t:Cell><t:Cell>$Resource{paccount}</t:Cell><t:Cell>$Resource{parea}</t:Cell><t:Cell>$Resource{pprice}</t:Cell><t:Cell>$Resource{pamount}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{project_Row2}</t:Cell><t:Cell>$Resource{operationPhases_Row2}</t:Cell><t:Cell>$Resource{batch_Row2}</t:Cell><t:Cell>$Resource{productType_Row2}</t:Cell><t:Cell>$Resource{decorate_Row2}</t:Cell><t:Cell>$Resource{account_Row2}</t:Cell><t:Cell>$Resource{area_Row2}</t:Cell><t:Cell>$Resource{price_Row2}</t:Cell><t:Cell>$Resource{amount_Row2}</t:Cell><t:Cell>$Resource{saccount_Row2}</t:Cell><t:Cell>$Resource{sarea_Row2}</t:Cell><t:Cell>$Resource{sprice_Row2}</t:Cell><t:Cell>$Resource{samount_Row2}</t:Cell><t:Cell>$Resource{daccount_Row2}</t:Cell><t:Cell>$Resource{darea_Row2}</t:Cell><t:Cell>$Resource{dprice_Row2}</t:Cell><t:Cell>$Resource{damount_Row2}</t:Cell><t:Cell>$Resource{paccount_Row2}</t:Cell><t:Cell>$Resource{parea_Row2}</t:Cell><t:Cell>$Resource{pprice_Row2}</t:Cell><t:Cell>$Resource{pamount_Row2}</t:Cell></t:Row><t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{project_Row3}</t:Cell><t:Cell>$Resource{operationPhases_Row3}</t:Cell><t:Cell>$Resource{batch_Row3}</t:Cell><t:Cell>$Resource{productType_Row3}</t:Cell><t:Cell>$Resource{decorate_Row3}</t:Cell><t:Cell>$Resource{account_Row3}</t:Cell><t:Cell>$Resource{area_Row3}</t:Cell><t:Cell>$Resource{price_Row3}</t:Cell><t:Cell>$Resource{amount_Row3}</t:Cell><t:Cell>$Resource{saccount_Row3}</t:Cell><t:Cell>$Resource{sarea_Row3}</t:Cell><t:Cell>$Resource{sprice_Row3}</t:Cell><t:Cell>$Resource{samount_Row3}</t:Cell><t:Cell>$Resource{daccount_Row3}</t:Cell><t:Cell>$Resource{darea_Row3}</t:Cell><t:Cell>$Resource{dprice_Row3}</t:Cell><t:Cell>$Resource{damount_Row3}</t:Cell><t:Cell>$Resource{paccount_Row3}</t:Cell><t:Cell>$Resource{parea_Row3}</t:Cell><t:Cell>$Resource{pprice_Row3}</t:Cell><t:Cell>$Resource{pamount_Row3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"2\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"2\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"2\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"2\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"2\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"0\" t:right=\"8\" /><t:Block t:top=\"0\" t:left=\"9\" t:bottom=\"0\" t:right=\"12\" /><t:Block t:top=\"0\" t:left=\"13\" t:bottom=\"0\" t:right=\"16\" /><t:Block t:top=\"0\" t:left=\"17\" t:bottom=\"0\" t:right=\"20\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtTotalEntry.setFormatXml(resHelper.translateString("kdtTotalEntry",kdtTotalEntryStrXML));

                this.kdtTotalEntry.putBindContents("editData",new String[] {"project","operationPhases","batch","productType","decorate","account","area","price","amount","saccount","sarea","sprice","samount","daccount","darea","dprice","damount","paccount","parea","pprice","pamount"});


        // txtVersion		
        this.txtVersion.setPrecision(0);		
        this.txtVersion.setEnabled(false);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setVisible(true);		
        this.txtName.setEnabled(false);		
        this.txtName.setHorizontalAlignment(2);		
        this.txtName.setRequired(true);
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
        contNumber.setBounds(new Rectangle(10, 8, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(10, 30, 634, 76));
        this.add(contDescription, new KDLayout.Constraints(10, 30, 634, 76, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(10, 580, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 580, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(10, 602, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(10, 602, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(373, 580, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(373, 580, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(373, 602, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(373, 602, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProject.setBounds(new Rectangle(373, 8, 270, 19));
        this.add(contProject, new KDLayout.Constraints(373, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        panel.setBounds(new Rectangle(10, 112, 996, 460));
        this.add(panel, new KDLayout.Constraints(10, 112, 996, 460, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contVersion.setBounds(new Rectangle(737, 30, 270, 19));
        this.add(contVersion, new KDLayout.Constraints(737, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contName.setBounds(new Rectangle(737, 8, 270, 19));
        this.add(contName, new KDLayout.Constraints(737, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //panel
        panel.add(contEntry, resHelper.getString("contEntry.constraints"));
        panel.add(contPriceEntry, resHelper.getString("contPriceEntry.constraints"));
        panel.add(contTotalEntry, resHelper.getString("contTotalEntry.constraints"));
        //contEntry
contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contEntry.getContentPane().add(kdtEntry, BorderLayout.CENTER);
        //contPriceEntry
contPriceEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contPriceEntry.getContentPane().add(kdtPriceEntry, BorderLayout.CENTER);
        //contTotalEntry
contTotalEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contTotalEntry.getContentPane().add(kdtTotalEntry, BorderLayout.CENTER);
        //contVersion
        contVersion.setBoundEditor(txtVersion);
        //contName
        contName.setBoundEditor(txtName);

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
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCopyLine);
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
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("project", com.kingdee.eas.fdc.basedata.OperationPhasesInfo.class, this.prmtProject, "data");
		dataBinder.registerBinding("entry", com.kingdee.eas.fdc.market.ValueInputEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entry.project", String.class, this.kdtEntry, "project.text");
		dataBinder.registerBinding("entry.batch", String.class, this.kdtEntry, "batch.text");
		dataBinder.registerBinding("entry.productType", String.class, this.kdtEntry, "productType.text");
		dataBinder.registerBinding("entry.modelName", String.class, this.kdtEntry, "modelName.text");
		dataBinder.registerBinding("entry.modelType", String.class, this.kdtEntry, "modelType.text");
		dataBinder.registerBinding("entry.modelArea", java.math.BigDecimal.class, this.kdtEntry, "modelArea.text");
		dataBinder.registerBinding("entry.area", java.math.BigDecimal.class, this.kdtEntry, "area.text");
		dataBinder.registerBinding("entry.date", java.util.Date.class, this.kdtEntry, "date.text");
		dataBinder.registerBinding("entry.price", java.math.BigDecimal.class, this.kdtEntry, "price.text");
		dataBinder.registerBinding("entry.calculateType", com.kingdee.eas.fdc.basedata.CalculateTypeEnum.class, this.kdtEntry, "calculateType.text");
		dataBinder.registerBinding("entry.amount", java.math.BigDecimal.class, this.kdtEntry, "amount.text");
		dataBinder.registerBinding("entry.remark", String.class, this.kdtEntry, "remark.text");
		dataBinder.registerBinding("entry.decorate", com.kingdee.eas.fdc.market.DecorateEnum.class, this.kdtEntry, "decorate.text");
		dataBinder.registerBinding("entry.build", String.class, this.kdtEntry, "build.text");
		dataBinder.registerBinding("entry.account", int.class, this.kdtEntry, "account.text");
		dataBinder.registerBinding("priceEntry", com.kingdee.eas.fdc.market.ValueInputPriceEntryInfo.class, this.kdtPriceEntry, "userObject");
		dataBinder.registerBinding("priceEntry.project", String.class, this.kdtPriceEntry, "project.text");
		dataBinder.registerBinding("priceEntry.build", String.class, this.kdtPriceEntry, "build.text");
		dataBinder.registerBinding("priceEntry.unit", String.class, this.kdtPriceEntry, "unit.text");
		dataBinder.registerBinding("priceEntry.room", String.class, this.kdtPriceEntry, "room.text");
		dataBinder.registerBinding("priceEntry.sellType", com.kingdee.eas.fdc.sellhouse.SellTypeEnum.class, this.kdtPriceEntry, "sellType.text");
		dataBinder.registerBinding("priceEntry.priceType", com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum.class, this.kdtPriceEntry, "priceType.text");
		dataBinder.registerBinding("priceEntry.standardTotalAmount", java.math.BigDecimal.class, this.kdtPriceEntry, "standardTotalAmount.text");
		dataBinder.registerBinding("priceEntry.baseStandardPrice", java.math.BigDecimal.class, this.kdtPriceEntry, "baseStandardPrice.text");
		dataBinder.registerBinding("priceEntry.buildingPrice", java.math.BigDecimal.class, this.kdtPriceEntry, "buildingPrice.text");
		dataBinder.registerBinding("priceEntry.roomPrice", java.math.BigDecimal.class, this.kdtPriceEntry, "roomPrice.text");
		dataBinder.registerBinding("priceEntry.buildingArea", java.math.BigDecimal.class, this.kdtPriceEntry, "buildingArea.text");
		dataBinder.registerBinding("priceEntry.roomArea", java.math.BigDecimal.class, this.kdtPriceEntry, "roomArea.text");
		dataBinder.registerBinding("dyEntry", com.kingdee.eas.fdc.market.ValueInputDYEntryInfo.class, this.kdtTotalEntry, "userObject");
		dataBinder.registerBinding("dyEntry.project", String.class, this.kdtTotalEntry, "project.text");
		dataBinder.registerBinding("dyEntry.operationPhases", String.class, this.kdtTotalEntry, "operationPhases.text");
		dataBinder.registerBinding("dyEntry.batch", String.class, this.kdtTotalEntry, "batch.text");
		dataBinder.registerBinding("dyEntry.productType", String.class, this.kdtTotalEntry, "productType.text");
		dataBinder.registerBinding("dyEntry.decorate", com.kingdee.eas.fdc.market.DecorateEnum.class, this.kdtTotalEntry, "decorate.text");
		dataBinder.registerBinding("dyEntry.account", int.class, this.kdtTotalEntry, "account.text");
		dataBinder.registerBinding("dyEntry.area", java.math.BigDecimal.class, this.kdtTotalEntry, "area.text");
		dataBinder.registerBinding("dyEntry.price", java.math.BigDecimal.class, this.kdtTotalEntry, "price.text");
		dataBinder.registerBinding("dyEntry.amount", java.math.BigDecimal.class, this.kdtTotalEntry, "amount.text");
		dataBinder.registerBinding("dyEntry.saccount", int.class, this.kdtTotalEntry, "saccount.text");
		dataBinder.registerBinding("dyEntry.sarea", java.math.BigDecimal.class, this.kdtTotalEntry, "sarea.text");
		dataBinder.registerBinding("dyEntry.sprice", java.math.BigDecimal.class, this.kdtTotalEntry, "sprice.text");
		dataBinder.registerBinding("dyEntry.samount", java.math.BigDecimal.class, this.kdtTotalEntry, "samount.text");
		dataBinder.registerBinding("dyEntry.daccount", int.class, this.kdtTotalEntry, "daccount.text");
		dataBinder.registerBinding("dyEntry.darea", java.math.BigDecimal.class, this.kdtTotalEntry, "darea.text");
		dataBinder.registerBinding("dyEntry.dprice", java.math.BigDecimal.class, this.kdtTotalEntry, "dprice.text");
		dataBinder.registerBinding("dyEntry.damount", java.math.BigDecimal.class, this.kdtTotalEntry, "damount.text");
		dataBinder.registerBinding("dyEntry.paccount", int.class, this.kdtTotalEntry, "paccount.text");
		dataBinder.registerBinding("dyEntry.parea", java.math.BigDecimal.class, this.kdtTotalEntry, "parea.text");
		dataBinder.registerBinding("dyEntry.pprice", java.math.BigDecimal.class, this.kdtTotalEntry, "pprice.text");
		dataBinder.registerBinding("dyEntry.pamount", java.math.BigDecimal.class, this.kdtTotalEntry, "pamount.text");
		dataBinder.registerBinding("version", int.class, this.txtVersion, "value");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.ValueInputEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.market.ValueInputInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.batch", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.productType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.modelName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.modelType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.modelArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.area", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.date", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.calculateType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.decorate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.build", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.account", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceEntry.project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceEntry.build", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceEntry.unit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceEntry.room", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceEntry.sellType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceEntry.priceType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceEntry.standardTotalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceEntry.baseStandardPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceEntry.buildingPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceEntry.roomPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceEntry.buildingArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceEntry.roomArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.operationPhases", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.batch", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.productType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.decorate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.account", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.area", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.saccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.sarea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.sprice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.samount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.daccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.darea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.dprice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.damount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.paccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.parea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.pprice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dyEntry.pamount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("version", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    		
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
     * output kdtEntry_editStopped method
     */
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("project.*"));
        sic.add(new SelectorItemInfo("entry.*"));
//        sic.add(new SelectorItemInfo("entry.number"));
    sic.add(new SelectorItemInfo("entry.project"));
    sic.add(new SelectorItemInfo("entry.batch"));
    sic.add(new SelectorItemInfo("entry.productType"));
    sic.add(new SelectorItemInfo("entry.modelName"));
    sic.add(new SelectorItemInfo("entry.modelType"));
    sic.add(new SelectorItemInfo("entry.modelArea"));
    sic.add(new SelectorItemInfo("entry.area"));
    sic.add(new SelectorItemInfo("entry.date"));
    sic.add(new SelectorItemInfo("entry.price"));
    sic.add(new SelectorItemInfo("entry.calculateType"));
    sic.add(new SelectorItemInfo("entry.amount"));
    sic.add(new SelectorItemInfo("entry.remark"));
    sic.add(new SelectorItemInfo("entry.decorate"));
    sic.add(new SelectorItemInfo("entry.build"));
    sic.add(new SelectorItemInfo("entry.account"));
        sic.add(new SelectorItemInfo("priceEntry.*"));
//        sic.add(new SelectorItemInfo("priceEntry.number"));
    sic.add(new SelectorItemInfo("priceEntry.project"));
    sic.add(new SelectorItemInfo("priceEntry.build"));
    sic.add(new SelectorItemInfo("priceEntry.unit"));
    sic.add(new SelectorItemInfo("priceEntry.room"));
    sic.add(new SelectorItemInfo("priceEntry.sellType"));
    sic.add(new SelectorItemInfo("priceEntry.priceType"));
    sic.add(new SelectorItemInfo("priceEntry.standardTotalAmount"));
    sic.add(new SelectorItemInfo("priceEntry.baseStandardPrice"));
    sic.add(new SelectorItemInfo("priceEntry.buildingPrice"));
    sic.add(new SelectorItemInfo("priceEntry.roomPrice"));
    sic.add(new SelectorItemInfo("priceEntry.buildingArea"));
    sic.add(new SelectorItemInfo("priceEntry.roomArea"));
        sic.add(new SelectorItemInfo("dyEntry.*"));
//        sic.add(new SelectorItemInfo("dyEntry.number"));
    sic.add(new SelectorItemInfo("dyEntry.project"));
    sic.add(new SelectorItemInfo("dyEntry.operationPhases"));
    sic.add(new SelectorItemInfo("dyEntry.batch"));
    sic.add(new SelectorItemInfo("dyEntry.productType"));
    sic.add(new SelectorItemInfo("dyEntry.decorate"));
    sic.add(new SelectorItemInfo("dyEntry.account"));
    sic.add(new SelectorItemInfo("dyEntry.area"));
    sic.add(new SelectorItemInfo("dyEntry.price"));
    sic.add(new SelectorItemInfo("dyEntry.amount"));
    sic.add(new SelectorItemInfo("dyEntry.saccount"));
    sic.add(new SelectorItemInfo("dyEntry.sarea"));
    sic.add(new SelectorItemInfo("dyEntry.sprice"));
    sic.add(new SelectorItemInfo("dyEntry.samount"));
    sic.add(new SelectorItemInfo("dyEntry.daccount"));
    sic.add(new SelectorItemInfo("dyEntry.darea"));
    sic.add(new SelectorItemInfo("dyEntry.dprice"));
    sic.add(new SelectorItemInfo("dyEntry.damount"));
    sic.add(new SelectorItemInfo("dyEntry.paccount"));
    sic.add(new SelectorItemInfo("dyEntry.parea"));
    sic.add(new SelectorItemInfo("dyEntry.pprice"));
    sic.add(new SelectorItemInfo("dyEntry.pamount"));
        sic.add(new SelectorItemInfo("version"));
        sic.add(new SelectorItemInfo("name"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "ValueInputEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}