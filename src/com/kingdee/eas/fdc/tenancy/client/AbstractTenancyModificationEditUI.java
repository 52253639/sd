/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

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
public abstract class AbstractTenancyModificationEditUI extends com.kingdee.eas.fdc.tenancy.client.TenBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTenancyModificationEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancy;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contModificationReason;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemark;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewEndDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomerDesc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomDesc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAttResourseDesc;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStrartDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOriEndDate;
    protected com.kingdee.bos.ctrl.swing.KDContainer containerOriIncrease;
    protected com.kingdee.bos.ctrl.swing.KDContainer containerIncrease;
    protected com.kingdee.bos.ctrl.swing.KDContainer containerOriFree;
    protected com.kingdee.bos.ctrl.swing.KDContainer containerFree;
    protected com.kingdee.bos.ctrl.swing.KDContainer containerOriRent;
    protected com.kingdee.bos.ctrl.swing.KDContainer containerRent;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTenancy;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtModificationReason;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRemark;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkNewEndDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCustomerDesc;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoomDesc;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAttResourceDesc;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStartDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkOriEndDate;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblOriIncrease;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblIncrease;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblOriFree;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblFree;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblOriRent;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRent;
    protected com.kingdee.eas.fdc.tenancy.TenancyModificationInfo editData = null;
    protected ActionIncAdd actionIncAdd = null;
    protected ActionIncDel actionIncDel = null;
    protected ActionFeeAdd actionFeeAdd = null;
    protected ActionFeeDel actionFeeDel = null;
    /**
     * output class constructor
     */
    public AbstractTenancyModificationEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTenancyModificationEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionIncAdd
        this.actionIncAdd = new ActionIncAdd(this);
        getActionManager().registerAction("actionIncAdd", actionIncAdd);
         this.actionIncAdd.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionIncDel
        this.actionIncDel = new ActionIncDel(this);
        getActionManager().registerAction("actionIncDel", actionIncDel);
         this.actionIncDel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionFeeAdd
        this.actionFeeAdd = new ActionFeeAdd(this);
        getActionManager().registerAction("actionFeeAdd", actionFeeAdd);
         this.actionFeeAdd.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionFeeDel
        this.actionFeeDel = new ActionFeeDel(this);
        getActionManager().registerAction("actionFeeDel", actionFeeDel);
         this.actionFeeDel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancy = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contModificationReason = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRemark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNewEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomerDesc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomDesc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAttResourseDesc = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStrartDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOriEndDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.containerOriIncrease = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.containerIncrease = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.containerOriFree = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.containerFree = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.containerOriRent = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.containerRent = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtTenancy = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtModificationReason = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkNewEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtCustomerDesc = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRoomDesc = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAttResourceDesc = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkStartDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkOriEndDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.tblOriIncrease = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblIncrease = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblOriFree = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblFree = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblOriRent = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblRent = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contName.setName("contName");
        this.contTenancy.setName("contTenancy");
        this.contModificationReason.setName("contModificationReason");
        this.contRemark.setName("contRemark");
        this.contNewEndDate.setName("contNewEndDate");
        this.contCustomerDesc.setName("contCustomerDesc");
        this.contRoomDesc.setName("contRoomDesc");
        this.contAttResourseDesc.setName("contAttResourseDesc");
        this.contStrartDate.setName("contStrartDate");
        this.contOriEndDate.setName("contOriEndDate");
        this.containerOriIncrease.setName("containerOriIncrease");
        this.containerIncrease.setName("containerIncrease");
        this.containerOriFree.setName("containerOriFree");
        this.containerFree.setName("containerFree");
        this.containerOriRent.setName("containerOriRent");
        this.containerRent.setName("containerRent");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtName.setName("txtName");
        this.prmtTenancy.setName("prmtTenancy");
        this.txtModificationReason.setName("txtModificationReason");
        this.txtRemark.setName("txtRemark");
        this.pkNewEndDate.setName("pkNewEndDate");
        this.txtCustomerDesc.setName("txtCustomerDesc");
        this.txtRoomDesc.setName("txtRoomDesc");
        this.txtAttResourceDesc.setName("txtAttResourceDesc");
        this.pkStartDate.setName("pkStartDate");
        this.pkOriEndDate.setName("pkOriEndDate");
        this.tblOriIncrease.setName("tblOriIncrease");
        this.tblIncrease.setName("tblIncrease");
        this.tblOriFree.setName("tblOriFree");
        this.tblFree.setName("tblFree");
        this.tblOriRent.setName("tblOriRent");
        this.tblRent.setName("tblRent");
        // CoreUI
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contTenancy		
        this.contTenancy.setBoundLabelText(resHelper.getString("contTenancy.boundLabelText"));		
        this.contTenancy.setBoundLabelLength(100);		
        this.contTenancy.setBoundLabelUnderline(true);
        // contModificationReason		
        this.contModificationReason.setBoundLabelText(resHelper.getString("contModificationReason.boundLabelText"));		
        this.contModificationReason.setBoundLabelLength(100);		
        this.contModificationReason.setBoundLabelUnderline(true);
        // contRemark		
        this.contRemark.setBoundLabelText(resHelper.getString("contRemark.boundLabelText"));		
        this.contRemark.setBoundLabelLength(100);		
        this.contRemark.setBoundLabelUnderline(true);
        // contNewEndDate		
        this.contNewEndDate.setBoundLabelText(resHelper.getString("contNewEndDate.boundLabelText"));		
        this.contNewEndDate.setBoundLabelLength(100);		
        this.contNewEndDate.setBoundLabelUnderline(true);
        // contCustomerDesc		
        this.contCustomerDesc.setBoundLabelText(resHelper.getString("contCustomerDesc.boundLabelText"));		
        this.contCustomerDesc.setBoundLabelLength(100);		
        this.contCustomerDesc.setBoundLabelUnderline(true);
        // contRoomDesc		
        this.contRoomDesc.setBoundLabelText(resHelper.getString("contRoomDesc.boundLabelText"));		
        this.contRoomDesc.setBoundLabelLength(100);		
        this.contRoomDesc.setBoundLabelUnderline(true);
        // contAttResourseDesc		
        this.contAttResourseDesc.setBoundLabelText(resHelper.getString("contAttResourseDesc.boundLabelText"));		
        this.contAttResourseDesc.setBoundLabelLength(100);		
        this.contAttResourseDesc.setBoundLabelUnderline(true);
        // contStrartDate		
        this.contStrartDate.setBoundLabelText(resHelper.getString("contStrartDate.boundLabelText"));		
        this.contStrartDate.setBoundLabelLength(100);		
        this.contStrartDate.setBoundLabelUnderline(true);
        // contOriEndDate		
        this.contOriEndDate.setBoundLabelText(resHelper.getString("contOriEndDate.boundLabelText"));		
        this.contOriEndDate.setBoundLabelLength(100);		
        this.contOriEndDate.setBoundLabelUnderline(true);
        // containerOriIncrease		
        this.containerOriIncrease.setTitle(resHelper.getString("containerOriIncrease.title"));
        // containerIncrease		
        this.containerIncrease.setTitle(resHelper.getString("containerIncrease.title"));
        // containerOriFree		
        this.containerOriFree.setTitle(resHelper.getString("containerOriFree.title"));
        // containerFree		
        this.containerFree.setTitle(resHelper.getString("containerFree.title"));
        // containerOriRent		
        this.containerOriRent.setTitle(resHelper.getString("containerOriRent.title"));
        // containerRent		
        this.containerRent.setTitle(resHelper.getString("containerRent.title"));
        // txtNumber		
        this.txtNumber.setRequired(true);
        // pkBizDate
        // txtName		
        this.txtName.setRequired(true);
        // prmtTenancy		
        this.prmtTenancy.setDisplayFormat("$tenancyName$");		
        this.prmtTenancy.setEditFormat("$number$");		
        this.prmtTenancy.setCommitFormat("$number$");		
        this.prmtTenancy.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillQuery");
        this.prmtTenancy.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtTenancy_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtModificationReason		
        this.txtModificationReason.setMaxLength(255);
        // txtRemark		
        this.txtRemark.setMaxLength(255);
        // pkNewEndDate
        this.pkNewEndDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkNewEndDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtCustomerDesc
        // txtRoomDesc
        // txtAttResourceDesc
        // pkStartDate
        // pkOriEndDate
        // tblOriIncrease
		String tblOriIncreaseStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"increaseDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"increaseType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"increaseStyle\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"value\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{increaseDate}</t:Cell><t:Cell>$Resource{increaseType}</t:Cell><t:Cell>$Resource{increaseStyle}</t:Cell><t:Cell>$Resource{value}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblOriIncrease.setFormatXml(resHelper.translateString("tblOriIncrease",tblOriIncreaseStrXML));

        

        // tblIncrease
		String tblIncreaseStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"increaseDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"increaseType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"increaseStyle\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"value\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{increaseDate}</t:Cell><t:Cell>$Resource{increaseType}</t:Cell><t:Cell>$Resource{increaseStyle}</t:Cell><t:Cell>$Resource{value}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblIncrease.setFormatXml(resHelper.translateString("tblIncrease",tblIncreaseStrXML));
        this.tblIncrease.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblIncrease_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // tblOriFree
		String tblOriFreeStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"freeStartDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"freeEndDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"freeTenancyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{freeStartDate}</t:Cell><t:Cell>$Resource{freeEndDate}</t:Cell><t:Cell>$Resource{freeTenancyType}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblOriFree.setFormatXml(resHelper.translateString("tblOriFree",tblOriFreeStrXML));

        

        // tblFree
		String tblFreeStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"freeStartDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"freeEndDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"freeTenancyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{freeStartDate}</t:Cell><t:Cell>$Resource{freeEndDate}</t:Cell><t:Cell>$Resource{freeTenancyType}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblFree.setFormatXml(resHelper.translateString("tblFree",tblFreeStrXML));
        this.tblFree.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblFree_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // tblOriRent
		String tblOriRentStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"room\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"deposit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"firstRent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"tenancyModel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"rentType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{deposit}</t:Cell><t:Cell>$Resource{firstRent}</t:Cell><t:Cell>$Resource{tenancyModel}</t:Cell><t:Cell>$Resource{rentType}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room_Row2}</t:Cell><t:Cell>$Resource{deposit_Row2}</t:Cell><t:Cell>$Resource{firstRent_Row2}</t:Cell><t:Cell>$Resource{tenancyModel_Row2}</t:Cell><t:Cell>$Resource{rentType_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblOriRent.setFormatXml(resHelper.translateString("tblOriRent",tblOriRentStrXML));

        

        // tblRent
		String tblRentStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>#,##0.00;-#,##0.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"room\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"deposit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"firstRent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"tenancyModel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"rentType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room}</t:Cell><t:Cell>$Resource{deposit}</t:Cell><t:Cell>$Resource{firstRent}</t:Cell><t:Cell>$Resource{tenancyModel}</t:Cell><t:Cell>$Resource{rentType}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{room_Row2}</t:Cell><t:Cell>$Resource{deposit_Row2}</t:Cell><t:Cell>$Resource{firstRent_Row2}</t:Cell><t:Cell>$Resource{tenancyModel_Row2}</t:Cell><t:Cell>$Resource{rentType_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblRent.setFormatXml(resHelper.translateString("tblRent",tblRentStrXML));
        this.tblRent.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblRent_editStopped(e);
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
        this.setBounds(new Rectangle(10, 10, 1024, 768));
        this.setLayout(null);
        contNumber.setBounds(new Rectangle(10, 6, 270, 19));
        this.add(contNumber, null);
        contBizDate.setBounds(new Rectangle(743, 5, 270, 19));
        this.add(contBizDate, null);
        contName.setBounds(new Rectangle(376, 5, 270, 19));
        this.add(contName, null);
        contTenancy.setBounds(new Rectangle(10, 30, 270, 19));
        this.add(contTenancy, null);
        contModificationReason.setBounds(new Rectangle(376, 28, 270, 19));
        this.add(contModificationReason, null);
        contRemark.setBounds(new Rectangle(743, 26, 270, 19));
        this.add(contRemark, null);
        contNewEndDate.setBounds(new Rectangle(743, 125, 270, 19));
        this.add(contNewEndDate, null);
        contCustomerDesc.setBounds(new Rectangle(10, 53, 1000, 19));
        this.add(contCustomerDesc, null);
        contRoomDesc.setBounds(new Rectangle(10, 76, 1000, 19));
        this.add(contRoomDesc, null);
        contAttResourseDesc.setBounds(new Rectangle(10, 100, 1000, 19));
        this.add(contAttResourseDesc, null);
        contStrartDate.setBounds(new Rectangle(10, 125, 270, 19));
        this.add(contStrartDate, null);
        contOriEndDate.setBounds(new Rectangle(376, 125, 270, 19));
        this.add(contOriEndDate, null);
        containerOriIncrease.setBounds(new Rectangle(10, 152, 485, 106));
        this.add(containerOriIncrease, null);
        containerIncrease.setBounds(new Rectangle(527, 152, 485, 106));
        this.add(containerIncrease, null);
        containerOriFree.setBounds(new Rectangle(10, 265, 485, 106));
        this.add(containerOriFree, null);
        containerFree.setBounds(new Rectangle(527, 265, 485, 105));
        this.add(containerFree, null);
        containerOriRent.setBounds(new Rectangle(10, 376, 1002, 106));
        this.add(containerOriRent, null);
        containerRent.setBounds(new Rectangle(10, 489, 1002, 106));
        this.add(containerRent, null);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contName
        contName.setBoundEditor(txtName);
        //contTenancy
        contTenancy.setBoundEditor(prmtTenancy);
        //contModificationReason
        contModificationReason.setBoundEditor(txtModificationReason);
        //contRemark
        contRemark.setBoundEditor(txtRemark);
        //contNewEndDate
        contNewEndDate.setBoundEditor(pkNewEndDate);
        //contCustomerDesc
        contCustomerDesc.setBoundEditor(txtCustomerDesc);
        //contRoomDesc
        contRoomDesc.setBoundEditor(txtRoomDesc);
        //contAttResourseDesc
        contAttResourseDesc.setBoundEditor(txtAttResourceDesc);
        //contStrartDate
        contStrartDate.setBoundEditor(pkStartDate);
        //contOriEndDate
        contOriEndDate.setBoundEditor(pkOriEndDate);
        //containerOriIncrease
        containerOriIncrease.getContentPane().setLayout(new KDLayout());
        containerOriIncrease.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 152, 485, 106));        tblOriIncrease.setBounds(new Rectangle(0, 0, 485, 86));
        containerOriIncrease.getContentPane().add(tblOriIncrease, new KDLayout.Constraints(0, 0, 485, 86, 0));
        //containerIncrease
        containerIncrease.getContentPane().setLayout(new KDLayout());
        containerIncrease.getContentPane().putClientProperty("OriginalBounds", new Rectangle(527, 152, 485, 106));        tblIncrease.setBounds(new Rectangle(0, 0, 485, 87));
        containerIncrease.getContentPane().add(tblIncrease, new KDLayout.Constraints(0, 0, 485, 87, 0));
        //containerOriFree
        containerOriFree.getContentPane().setLayout(new KDLayout());
        containerOriFree.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 265, 485, 106));        tblOriFree.setBounds(new Rectangle(0, 0, 485, 86));
        containerOriFree.getContentPane().add(tblOriFree, new KDLayout.Constraints(0, 0, 485, 86, 0));
        //containerFree
        containerFree.getContentPane().setLayout(new KDLayout());
        containerFree.getContentPane().putClientProperty("OriginalBounds", new Rectangle(527, 265, 485, 105));        tblFree.setBounds(new Rectangle(0, 0, 485, 85));
        containerFree.getContentPane().add(tblFree, new KDLayout.Constraints(0, 0, 485, 85, 0));
        //containerOriRent
        containerOriRent.getContentPane().setLayout(new KDLayout());
        containerOriRent.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 376, 1002, 106));        tblOriRent.setBounds(new Rectangle(0, 0, 988, 86));
        containerOriRent.getContentPane().add(tblOriRent, new KDLayout.Constraints(0, 0, 988, 86, 0));
        //containerRent
        containerRent.getContentPane().setLayout(new KDLayout());
        containerRent.getContentPane().putClientProperty("OriginalBounds", new Rectangle(10, 489, 1002, 106));        tblRent.setBounds(new Rectangle(0, 0, 988, 87));
        containerRent.getContentPane().add(tblRent, new KDLayout.Constraints(0, 0, 988, 87, 0));

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
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
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
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("tenancy", com.kingdee.eas.fdc.tenancy.TenancyBillInfo.class, this.prmtTenancy, "data");
		dataBinder.registerBinding("modificationReason", String.class, this.txtModificationReason, "text");
		dataBinder.registerBinding("remark", String.class, this.txtRemark, "text");
		dataBinder.registerBinding("newEndDate", java.util.Date.class, this.pkNewEndDate, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.TenancyModificationEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.tenancy.TenancyModificationInfo)ov;
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
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tenancy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("modificationReason", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("newEndDate", ValidateHelper.ON_SAVE);    		
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
     * output prmtTenancy_dataChanged method
     */
    protected void prmtTenancy_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkNewEndDate_dataChanged method
     */
    protected void pkNewEndDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblIncrease_editStopped method
     */
    protected void tblIncrease_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblFree_editStopped method
     */
    protected void tblFree_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblRent_editStopped method
     */
    protected void tblRent_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("tenancy.*"));
        sic.add(new SelectorItemInfo("modificationReason"));
        sic.add(new SelectorItemInfo("remark"));
        sic.add(new SelectorItemInfo("newEndDate"));
        return sic;
    }        
    	

    /**
     * output actionIncAdd_actionPerformed method
     */
    public void actionIncAdd_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionIncDel_actionPerformed method
     */
    public void actionIncDel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFeeAdd_actionPerformed method
     */
    public void actionFeeAdd_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionFeeDel_actionPerformed method
     */
    public void actionFeeDel_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionIncAdd(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionIncAdd() {
    	return false;
    }
	public RequestContext prepareActionIncDel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionIncDel() {
    	return false;
    }
	public RequestContext prepareActionFeeAdd(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFeeAdd() {
    	return false;
    }
	public RequestContext prepareActionFeeDel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFeeDel() {
    	return false;
    }

    /**
     * output ActionIncAdd class
     */     
    protected class ActionIncAdd extends ItemAction {     
    
        public ActionIncAdd()
        {
            this(null);
        }

        public ActionIncAdd(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionIncAdd.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionIncAdd.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionIncAdd.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyModificationEditUI.this, "ActionIncAdd", "actionIncAdd_actionPerformed", e);
        }
    }

    /**
     * output ActionIncDel class
     */     
    protected class ActionIncDel extends ItemAction {     
    
        public ActionIncDel()
        {
            this(null);
        }

        public ActionIncDel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionIncDel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionIncDel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionIncDel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyModificationEditUI.this, "ActionIncDel", "actionIncDel_actionPerformed", e);
        }
    }

    /**
     * output ActionFeeAdd class
     */     
    protected class ActionFeeAdd extends ItemAction {     
    
        public ActionFeeAdd()
        {
            this(null);
        }

        public ActionFeeAdd(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionFeeAdd.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFeeAdd.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFeeAdd.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyModificationEditUI.this, "ActionFeeAdd", "actionFeeAdd_actionPerformed", e);
        }
    }

    /**
     * output ActionFeeDel class
     */     
    protected class ActionFeeDel extends ItemAction {     
    
        public ActionFeeDel()
        {
            this(null);
        }

        public ActionFeeDel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionFeeDel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFeeDel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionFeeDel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTenancyModificationEditUI.this, "ActionFeeDel", "actionFeeDel_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "TenancyModificationEditUI");
    }




}