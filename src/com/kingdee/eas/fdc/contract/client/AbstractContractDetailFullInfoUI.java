/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
public abstract class AbstractContractDetailFullInfoUI extends com.kingdee.eas.fdc.contract.client.ContractBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractDetailFullInfoUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSplitState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettleState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettleNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSplitState;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSettleState;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSettleNumber;
    protected com.kingdee.eas.fdc.contract.ContractBillInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractContractDetailFullInfoUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractDetailFullInfoUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contSplitState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSettleState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSettleNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSplitState = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSettleState = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSettleNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contSplitState.setName("contSplitState");
        this.contSettleState.setName("contSettleState");
        this.contSettleNumber.setName("contSettleNumber");
        this.txtSplitState.setName("txtSplitState");
        this.txtSettleState.setName("txtSettleState");
        this.txtSettleNumber.setName("txtSettleNumber");
        // CoreUI
        this.btnSettelGroup.add(this.btnAType);
        this.btnSettelGroup.add(this.btnBType);
        this.btnSettelGroup.add(this.BtnCType);
        this.txtamount.addFocusListener(new java.awt.event.FocusAdapter() {
        });
		String kdtSupplyEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"contractPropert\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"contractType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bookedDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"partB\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"localAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{contractPropert}</t:Cell><t:Cell>$Resource{contractType}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{bookedDate}</t:Cell><t:Cell>$Resource{partB}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{localAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";


        

		String tblDetailStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"detail\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol1\" /><t:Column t:key=\"content\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"desc\" t:width=\"500\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"rowKey\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol4\" /><t:Column t:key=\"dataType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol5\" /><t:Column t:key=\"detailDef.id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:styleID=\"sCol6\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{detail}</t:Cell><t:Cell>$Resource{content}</t:Cell><t:Cell>$Resource{desc}</t:Cell><t:Cell>$Resource{rowKey}</t:Cell><t:Cell>$Resource{dataType}</t:Cell><t:Cell>$Resource{detailDef.id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";

        this.tblDetail.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
        });

        

		String tblCostStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"acctNumber\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"acctName\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"aimCost\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"hasHappen\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"intendingHappen\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"dynamicCost\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"chayi\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{acctNumber}</t:Cell><t:Cell>$Resource{acctName}</t:Cell><t:Cell>$Resource{aimCost}</t:Cell><t:Cell>$Resource{hasHappen}</t:Cell><t:Cell>$Resource{intendingHappen}</t:Cell><t:Cell>$Resource{dynamicCost}</t:Cell><t:Cell>$Resource{chayi}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";


        

		String tblInviteStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"respDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{respDept}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createDate}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{auditDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";


        

        this.chkIsPartAMaterialCon.addMouseListener(new java.awt.event.MouseAdapter() {
        });
        this.chkCostSplit.addMouseListener(new java.awt.event.MouseAdapter() {
        });
		String tblAttachementStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sTable\"><c:Alignment horizontal=\"left\" /><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"2\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\" t:styleID=\"sTable\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"400\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"type\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";


        

		String tblEconItemStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol3\"><c:NumberFormat>###.00</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>###,##0.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"payType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"payCondition\" t:width=\"180\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"payRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"payAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"desc\" t:width=\"390\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{payType}</t:Cell><t:Cell>$Resource{payCondition}</t:Cell><t:Cell>$Resource{payRate}</t:Cell><t:Cell>$Resource{payAmount}</t:Cell><t:Cell>$Resource{desc}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";

        this.tblEconItem.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
        });

                this.tblEconItem.putBindContents("editData",new String[] {"payItemDate","paymentType","payCondition","prop","amount","desc"});


		String tblBailStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>###.00</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>###,##0.00</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"bailDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bailCondition\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bailRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"bailAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"desc\" t:width=\"460\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{bailDate}</t:Cell><t:Cell>$Resource{bailCondition}</t:Cell><t:Cell>$Resource{bailRate}</t:Cell><t:Cell>$Resource{bailAmount}</t:Cell><t:Cell>$Resource{desc}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";

        this.tblBail.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
        });

                this.tblBail.putBindContents("editData",new String[] {"ail.entry.bailDate","ail.entry.bailConditon","ail.entry.prop","ail.entry.amount","ail.entry.desc"});


        // contSplitState		
        this.contSplitState.setBoundLabelText(resHelper.getString("contSplitState.boundLabelText"));		
        this.contSplitState.setBoundLabelLength(100);		
        this.contSplitState.setBoundLabelUnderline(true);		
        this.contSplitState.setEnabled(false);
        // contSettleState		
        this.contSettleState.setBoundLabelText(resHelper.getString("contSettleState.boundLabelText"));		
        this.contSettleState.setBoundLabelUnderline(true);		
        this.contSettleState.setBoundLabelLength(100);		
        this.contSettleState.setEnabled(false);
        // contSettleNumber		
        this.contSettleNumber.setBoundLabelText(resHelper.getString("contSettleNumber.boundLabelText"));		
        this.contSettleNumber.setBoundLabelUnderline(true);		
        this.contSettleNumber.setBoundLabelLength(100);		
        this.contSettleNumber.setEnabled(false);
        // txtSplitState
        // txtSettleState
        // txtSettleNumber
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtOrg,txtProj,prmtcontractType,txtNumber,txtcontractName,prmtlandDeveloper,prmtpartB,prmtpartC,contractPropert,pksignDate,comboCurrency,txtExRate,prmtRespDept,txtamount,txtLocalAmount,prmtRespPerson,txtGrtRate,txtGrtAmount,pkbookedDate,contractSource,txtpayPercForWarn,cbPeriod,costProperty,txtchgPercForWarn,txtCreator,chkCostSplit,chkIsPartAMaterialCon,kDDateCreateTime,txtPayScale,txtStampTaxAmt,txtStampTaxRate,comboAttachmentNameList,btnViewAttachment,txtlowestPrice,txtlowerPrice,txtmiddlePrice,txthigherPrice,txthighestPrice,txtbasePrice,txtsecondPrice,prmtinviteType,txtwinPrice,prmtwinUnit,txtfileNo,txtquantity,lblPrice,lblUnit,prmtlowestPriceUnit,prmtlowerPriceUnit,prmtmiddlePriceUnit,prmthigherPriceUnit,prmthighestPriceUni,txtRemark,comboCoopLevel,comboPriceType,tblDetail,tblCost,prmtCharge,tblEconItem,txtBailOriAmount,txtBailRate,tblBail}));
        this.setFocusCycleRoot(true);
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
        this.setBounds(new Rectangle(0, 0, 1013, 730));
this.setLayout(new BorderLayout(0, 0));
        this.add(tabPanel, BorderLayout.CENTER);
        //tabPanel
        tabPanel.add(mainPanel, resHelper.getString("mainPanel.constraints"));
        tabPanel.add(ecoItemPanel, resHelper.getString("ecoItemPanel.constraints"));
        //mainPanel
        mainPanel.setLayout(new KDLayout());
        mainPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1012, 697));        contCreateTime.setBounds(new Rectangle(630, 412, 276, 19));
        mainPanel.add(contCreateTime, new KDLayout.Constraints(630, 412, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(531, 31, 470, 19));
        mainPanel.add(contNumber, new KDLayout.Constraints(531, 31, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contamount.setBounds(new Rectangle(320, 119, 276, 19));
        mainPanel.add(contamount, new KDLayout.Constraints(320, 119, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contlandDeveloper.setBounds(new Rectangle(7, 75, 470, 19));
        mainPanel.add(contlandDeveloper, new KDLayout.Constraints(7, 75, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontractType.setBounds(new Rectangle(7, 31, 470, 19));
        mainPanel.add(contcontractType, new KDLayout.Constraints(7, 31, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontractPropert.setBounds(new Rectangle(531, 97, 470, 19));
        mainPanel.add(contcontractPropert, new KDLayout.Constraints(531, 97, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contpartB.setBounds(new Rectangle(531, 75, 470, 19));
        mainPanel.add(contpartB, new KDLayout.Constraints(531, 75, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contpartC.setBounds(new Rectangle(7, 97, 470, 19));
        mainPanel.add(contpartC, new KDLayout.Constraints(7, 97, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontractName.setBounds(new Rectangle(7, 53, 471, 19));
        mainPanel.add(contcontractName, new KDLayout.Constraints(7, 53, 471, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDTabbedPane1.setBounds(new Rectangle(9, 439, 994, 221));
        mainPanel.add(kDTabbedPane1, new KDLayout.Constraints(9, 439, 994, 221, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contExRate.setBounds(new Rectangle(320, 219, 276, 19));
        mainPanel.add(contExRate, new KDLayout.Constraints(320, 219, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLocalAmount.setBounds(new Rectangle(16, 241, 276, 19));
        mainPanel.add(contLocalAmount, new KDLayout.Constraints(16, 241, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contGrtAmount.setBounds(new Rectangle(320, 185, 276, 19));
        mainPanel.add(contGrtAmount, new KDLayout.Constraints(320, 185, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrency.setBounds(new Rectangle(16, 219, 276, 19));
        mainPanel.add(contCurrency, new KDLayout.Constraints(16, 219, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRespPerson.setBounds(new Rectangle(16, 285, 276, 19));
        mainPanel.add(contRespPerson, new KDLayout.Constraints(16, 285, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(345, 412, 276, 19));
        mainPanel.add(contCreator, new KDLayout.Constraints(345, 412, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrg.setBounds(new Rectangle(7, 9, 470, 19));
        mainPanel.add(contOrg, new KDLayout.Constraints(7, 9, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProj.setBounds(new Rectangle(531, 9, 470, 19));
        mainPanel.add(contProj, new KDLayout.Constraints(531, 9, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contGrtRate.setBounds(new Rectangle(320, 163, 276, 19));
        mainPanel.add(contGrtRate, new KDLayout.Constraints(320, 163, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contIsPartAMaterialCon.setBounds(new Rectangle(16, 263, 276, 19));
        mainPanel.add(contIsPartAMaterialCon, new KDLayout.Constraints(16, 263, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conContrarctRule.setBounds(new Rectangle(531, 53, 470, 19));
        mainPanel.add(conContrarctRule, new KDLayout.Constraints(531, 53, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrgAmtBig.setBounds(new Rectangle(320, 141, 276, 19));
        mainPanel.add(contOrgAmtBig, new KDLayout.Constraints(320, 141, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmtBig.setBounds(new Rectangle(320, 241, 276, 19));
        mainPanel.add(contAmtBig, new KDLayout.Constraints(320, 241, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer1.setBounds(new Rectangle(320, 263, 276, 19));
        mainPanel.add(kDLabelContainer1, new KDLayout.Constraints(320, 263, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateOrg.setBounds(new Rectangle(16, 412, 276, 19));
        mainPanel.add(contCreateOrg, new KDLayout.Constraints(16, 412, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDes.setBounds(new Rectangle(16, 374, 979, 35));
        mainPanel.add(contDes, new KDLayout.Constraints(16, 374, 979, 35, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel1.setBounds(new Rectangle(7, 119, 276, 88));
        mainPanel.add(kDPanel1, new KDLayout.Constraints(7, 119, 276, 88, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRespDept.setBounds(new Rectangle(320, 285, 276, 19));
        mainPanel.add(contRespDept, new KDLayout.Constraints(320, 285, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contsignDate.setBounds(new Rectangle(896, 383, 223, 19));
        mainPanel.add(contsignDate, new KDLayout.Constraints(896, 383, 223, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOrgType.setBounds(new Rectangle(320, 329, 276, 19));
        mainPanel.add(contOrgType, new KDLayout.Constraints(320, 329, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contcontractSource.setBounds(new Rectangle(16, 307, 276, 19));
        mainPanel.add(contcontractSource, new KDLayout.Constraints(16, 307, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractWFType.setBounds(new Rectangle(16, 329, 276, 19));
        mainPanel.add(contContractWFType, new KDLayout.Constraints(16, 329, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAttachment.setBounds(new Rectangle(631, 175, 368, 172));
        mainPanel.add(contAttachment, new KDLayout.Constraints(631, 175, 368, 172, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contMode.setBounds(new Rectangle(631, 119, 368, 50));
        mainPanel.add(contMode, new KDLayout.Constraints(631, 119, 368, 50, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSrcAmount.setBounds(new Rectangle(320, 307, 276, 19));
        mainPanel.add(contSrcAmount, new KDLayout.Constraints(320, 307, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contInviteType.setBounds(new Rectangle(16, 351, 276, 19));
        mainPanel.add(contInviteType, new KDLayout.Constraints(16, 351, 276, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSplitState.setBounds(new Rectangle(12, 665, 270, 19));
        mainPanel.add(contSplitState, new KDLayout.Constraints(12, 665, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSettleState.setBounds(new Rectangle(345, 665, 270, 19));
        mainPanel.add(contSettleState, new KDLayout.Constraints(345, 665, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSettleNumber.setBounds(new Rectangle(630, 665, 270, 19));
        mainPanel.add(contSettleNumber, new KDLayout.Constraints(630, 665, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contamount
        contamount.setBoundEditor(txtamount);
        //contlandDeveloper
        contlandDeveloper.setBoundEditor(prmtlandDeveloper);
        //contcontractType
        contcontractType.setBoundEditor(prmtcontractType);
        //contcontractPropert
        contcontractPropert.setBoundEditor(contractPropert);
        //contpartB
        contpartB.setBoundEditor(prmtpartB);
        //contpartC
        contpartC.setBoundEditor(prmtpartC);
        //contcontractName
        contcontractName.setBoundEditor(txtcontractName);
        //kDTabbedPane1
        kDTabbedPane1.add(pnlInviteInfo, resHelper.getString("pnlInviteInfo.constraints"));
        kDTabbedPane1.add(pnlDetail, resHelper.getString("pnlDetail.constraints"));
        kDTabbedPane1.add(pnlCost, resHelper.getString("pnlCost.constraints"));
        kDTabbedPane1.add(panelInvite, resHelper.getString("panelInvite.constraints"));
        kDTabbedPane1.add(kdtSupplyEntry, resHelper.getString("kdtSupplyEntry.constraints"));
        //pnlInviteInfo
        pnlInviteInfo.setLayout(null);        contRemark.setBounds(new Rectangle(8, 88, 270, 19));
        pnlInviteInfo.add(contRemark, null);
        contCoopLevel.setBounds(new Rectangle(31, 80, 270, 19));
        pnlInviteInfo.add(contCoopLevel, null);
        contPriceType.setBounds(new Rectangle(26, 162, 270, 19));
        pnlInviteInfo.add(contPriceType, null);
        chkIsSubMainContract.setBounds(new Rectangle(30, 25, 118, 19));
        pnlInviteInfo.add(chkIsSubMainContract, null);
        conMainContract.setBounds(new Rectangle(31, 51, 270, 19));
        pnlInviteInfo.add(conMainContract, null);
        conEffectiveStartDate.setBounds(new Rectangle(327, 25, 270, 19));
        pnlInviteInfo.add(conEffectiveStartDate, null);
        conEffectiveEndDate.setBounds(new Rectangle(701, 24, 270, 19));
        pnlInviteInfo.add(conEffectiveEndDate, null);
        kDScrollPane1.setBounds(new Rectangle(330, 75, 642, 58));
        pnlInviteInfo.add(kDScrollPane1, null);
        conInformation.setBounds(new Rectangle(326, 51, 270, 19));
        pnlInviteInfo.add(conInformation, null);
        contlowestPrice.setBounds(new Rectangle(8, 33, 270, 19));
        pnlInviteInfo.add(contlowestPrice, null);
        contlowerPrice.setBounds(new Rectangle(8, 60, 270, 19));
        pnlInviteInfo.add(contlowerPrice, null);
        conthigherPrice.setBounds(new Rectangle(8, 114, 270, 19));
        pnlInviteInfo.add(conthigherPrice, null);
        contmiddlePrice.setBounds(new Rectangle(8, 87, 270, 19));
        pnlInviteInfo.add(contmiddlePrice, null);
        conthighestPrice.setBounds(new Rectangle(8, 141, 270, 19));
        pnlInviteInfo.add(conthighestPrice, null);
        contbasePrice.setBounds(new Rectangle(8, 33, 270, 19));
        pnlInviteInfo.add(contbasePrice, null);
        contsecondPrice.setBounds(new Rectangle(8, 60, 270, 19));
        pnlInviteInfo.add(contsecondPrice, null);
        continviteType.setBounds(new Rectangle(636, 114, 346, 19));
        pnlInviteInfo.add(continviteType, null);
        contwinPrice.setBounds(new Rectangle(636, 33, 346, 19));
        pnlInviteInfo.add(contwinPrice, null);
        contwinUnit.setBounds(new Rectangle(636, 60, 346, 19));
        pnlInviteInfo.add(contwinUnit, null);
        contfileNo.setBounds(new Rectangle(636, 141, 346, 19));
        pnlInviteInfo.add(contfileNo, null);
        contquantity.setBounds(new Rectangle(636, 87, 346, 19));
        pnlInviteInfo.add(contquantity, null);
        prmtlowestPriceUnit.setBounds(new Rectangle(298, 33, 292, 19));
        pnlInviteInfo.add(prmtlowestPriceUnit, null);
        prmtlowerPriceUnit.setBounds(new Rectangle(298, 60, 292, 19));
        pnlInviteInfo.add(prmtlowerPriceUnit, null);
        prmtmiddlePriceUnit.setBounds(new Rectangle(298, 87, 292, 19));
        pnlInviteInfo.add(prmtmiddlePriceUnit, null);
        prmthigherPriceUnit.setBounds(new Rectangle(298, 114, 292, 19));
        pnlInviteInfo.add(prmthigherPriceUnit, null);
        prmthighestPriceUni.setBounds(new Rectangle(298, 141, 292, 19));
        pnlInviteInfo.add(prmthighestPriceUni, null);
        lblPrice.setBounds(new Rectangle(169, 8, 58, 19));
        pnlInviteInfo.add(lblPrice, null);
        lblUnit.setBounds(new Rectangle(431, 8, 65, 19));
        pnlInviteInfo.add(lblUnit, null);
        lblOverRateContainer.setBounds(new Rectangle(36, 209, 270, 19));
        pnlInviteInfo.add(lblOverRateContainer, null);
        contpayPercForWarn.setBounds(new Rectangle(305, 208, 270, 19));
        pnlInviteInfo.add(contpayPercForWarn, null);
        contchgPercForWarn.setBounds(new Rectangle(598, 206, 270, 19));
        pnlInviteInfo.add(contchgPercForWarn, null);
        contcostProperty.setBounds(new Rectangle(70, 223, 270, 19));
        pnlInviteInfo.add(contcostProperty, null);
        contStampTaxRate.setBounds(new Rectangle(358, 228, 270, 19));
        pnlInviteInfo.add(contStampTaxRate, null);
        contStampTaxAmt.setBounds(new Rectangle(715, 228, 270, 19));
        pnlInviteInfo.add(contStampTaxAmt, null);
        contPayScale.setBounds(new Rectangle(702, 187, 270, 19));
        pnlInviteInfo.add(contPayScale, null);
        prmtFwContractTemp.setBounds(new Rectangle(7, 186, 170, 19));
        pnlInviteInfo.add(prmtFwContractTemp, null);
        contAttachmentNameList.setBounds(new Rectangle(317, 163, 636, 19));
        pnlInviteInfo.add(contAttachmentNameList, null);
        btnViewAttachment.setBounds(new Rectangle(511, 184, 87, 21));
        pnlInviteInfo.add(btnViewAttachment, null);
        conChargeType.setBounds(new Rectangle(539, 3, 270, 19));
        pnlInviteInfo.add(conChargeType, null);
        //contRemark
        contRemark.setBoundEditor(txtRemark);
        //contCoopLevel
        contCoopLevel.setBoundEditor(comboCoopLevel);
        //contPriceType
        contPriceType.setBoundEditor(comboPriceType);
        //conMainContract
        conMainContract.setBoundEditor(prmtMainContract);
        //conEffectiveStartDate
        conEffectiveStartDate.setBoundEditor(kdpEffectStartDate);
        //conEffectiveEndDate
        conEffectiveEndDate.setBoundEditor(kdpEffectiveEndDate);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtInformation, null);
        //contlowestPrice
        contlowestPrice.setBoundEditor(txtlowestPrice);
        //contlowerPrice
        contlowerPrice.setBoundEditor(txtlowerPrice);
        //conthigherPrice
        conthigherPrice.setBoundEditor(txthigherPrice);
        //contmiddlePrice
        contmiddlePrice.setBoundEditor(txtmiddlePrice);
        //conthighestPrice
        conthighestPrice.setBoundEditor(txthighestPrice);
        //contbasePrice
        contbasePrice.setBoundEditor(txtbasePrice);
        //contsecondPrice
        contsecondPrice.setBoundEditor(txtsecondPrice);
        //continviteType
        continviteType.setBoundEditor(prmtinviteType);
        //contwinPrice
        contwinPrice.setBoundEditor(txtwinPrice);
        //contwinUnit
        contwinUnit.setBoundEditor(prmtwinUnit);
        //contfileNo
        contfileNo.setBoundEditor(txtfileNo);
        //contquantity
        contquantity.setBoundEditor(txtquantity);
        //lblOverRateContainer
        lblOverRateContainer.setBoundEditor(txtOverAmt);
        //contpayPercForWarn
        contpayPercForWarn.setBoundEditor(txtpayPercForWarn);
        //contchgPercForWarn
        contchgPercForWarn.setBoundEditor(txtchgPercForWarn);
        //contcostProperty
        contcostProperty.setBoundEditor(costProperty);
        //contStampTaxRate
        contStampTaxRate.setBoundEditor(txtStampTaxRate);
        //contStampTaxAmt
        contStampTaxAmt.setBoundEditor(txtStampTaxAmt);
        //contPayScale
        contPayScale.setBoundEditor(txtPayScale);
        //contAttachmentNameList
        contAttachmentNameList.setBoundEditor(comboAttachmentNameList);
        //conChargeType
        conChargeType.setBoundEditor(prmtCharge);
        //pnlDetail
        pnlDetail.getViewport().add(tblDetail, null);
        //pnlCost
        pnlCost.setLayout(new KDLayout());
        pnlCost.putClientProperty("OriginalBounds", new Rectangle(0, 0, 993, 188));        tblCost.setBounds(new Rectangle(10, 10, 965, 190));
        pnlCost.add(tblCost, new KDLayout.Constraints(10, 10, 965, 190, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        //panelInvite
panelInvite.setLayout(new BorderLayout(0, 0));        panelInvite.add(tblInvite, BorderLayout.CENTER);
        //contExRate
        contExRate.setBoundEditor(txtExRate);
        //contLocalAmount
        contLocalAmount.setBoundEditor(txtLocalAmount);
        //contGrtAmount
        contGrtAmount.setBoundEditor(txtGrtAmount);
        //contCurrency
        contCurrency.setBoundEditor(comboCurrency);
        //contRespPerson
        contRespPerson.setBoundEditor(prmtRespPerson);
        //contCreator
        contCreator.setBoundEditor(txtCreator);
        //contOrg
        contOrg.setBoundEditor(txtOrg);
        //contProj
        contProj.setBoundEditor(txtProj);
        //contGrtRate
        contGrtRate.setBoundEditor(txtGrtRate);
        //contIsPartAMaterialCon
        contIsPartAMaterialCon.setBoundEditor(cbPeriod);
        //conContrarctRule
        conContrarctRule.setBoundEditor(textFwContract);
        //contOrgAmtBig
        contOrgAmtBig.setBoundEditor(txtOrgAmtBig);
        //contAmtBig
        contAmtBig.setBoundEditor(txtAmtBig);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(pkbookedDate);
        //contCreateOrg
        contCreateOrg.setBoundEditor(prmtCreateOrg);
        //contDes
        contDes.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtDes, null);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(7, 119, 276, 88));        chkIsOpen.setBounds(new Rectangle(134, 12, 129, 19));
        kDPanel1.add(chkIsOpen, new KDLayout.Constraints(134, 12, 129, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkIsStandardContract.setBounds(new Rectangle(17, 12, 101, 19));
        kDPanel1.add(chkIsStandardContract, new KDLayout.Constraints(17, 12, 101, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labSettleType.setBounds(new Rectangle(17, 32, 79, 19));
        kDPanel1.add(labSettleType, new KDLayout.Constraints(17, 32, 79, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAType.setBounds(new Rectangle(108, 32, 40, 19));
        kDPanel1.add(btnAType, new KDLayout.Constraints(108, 32, 40, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnBType.setBounds(new Rectangle(160, 32, 45, 19));
        kDPanel1.add(btnBType, new KDLayout.Constraints(160, 32, 45, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        BtnCType.setBounds(new Rectangle(212, 32, 44, 19));
        kDPanel1.add(BtnCType, new KDLayout.Constraints(212, 32, 44, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkIsPartAMaterialCon.setBounds(new Rectangle(134, 53, 126, 19));
        kDPanel1.add(chkIsPartAMaterialCon, new KDLayout.Constraints(134, 53, 126, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkCostSplit.setBounds(new Rectangle(17, 53, 99, 19));
        kDPanel1.add(chkCostSplit, new KDLayout.Constraints(17, 53, 99, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contRespDept
        contRespDept.setBoundEditor(prmtRespDept);
        //contsignDate
        contsignDate.setBoundEditor(pksignDate);
        //contOrgType
        contOrgType.setBoundEditor(cbOrgType);
        //contcontractSource
        contcontractSource.setBoundEditor(contractSource);
        //contContractWFType
        contContractWFType.setBoundEditor(prmtContractWFType);
        //contAttachment
contAttachment.getContentPane().setLayout(new BorderLayout(0, 0));        contAttachment.getContentPane().add(tblAttachement, BorderLayout.CENTER);
        //contMode
contMode.getContentPane().setLayout(new BorderLayout(0, 0));        contMode.getContentPane().add(prmtModel, BorderLayout.CENTER);
        //contSrcAmount
        contSrcAmount.setBoundEditor(txtSrcAmount);
        //contInviteType
        contInviteType.setBoundEditor(prmtInviteType);
        //contSplitState
        contSplitState.setBoundEditor(txtSplitState);
        //contSettleState
        contSettleState.setBoundEditor(txtSettleState);
        //contSettleNumber
        contSettleNumber.setBoundEditor(txtSettleNumber);
        //ecoItemPanel
ecoItemPanel.setLayout(new BorderLayout(0, 0));        ecoItemPanel.add(kDContainer1, BorderLayout.CENTER);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kDSplitPane1, BorderLayout.CENTER);
        //kDSplitPane1
        kDSplitPane1.add(contPayItem, "top");
        kDSplitPane1.add(contBailItem, "bottom");
        //contPayItem
contPayItem.getContentPane().setLayout(new BorderLayout(0, 0));        contPayItem.getContentPane().add(tblEconItem, BorderLayout.CENTER);
        //contBailItem
        contBailItem.getContentPane().setLayout(new KDLayout());
        contBailItem.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 1011, 342));        contBailOriAmount.setBounds(new Rectangle(5, 8, 463, 19));
        contBailItem.getContentPane().add(contBailOriAmount, new KDLayout.Constraints(5, 8, 463, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBailRate.setBounds(new Rectangle(544, 8, 450, 19));
        contBailItem.getContentPane().add(contBailRate, new KDLayout.Constraints(544, 8, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        tblBail.setBounds(new Rectangle(3, 40, 995, 271));
        contBailItem.getContentPane().add(tblBail, new KDLayout.Constraints(3, 40, 995, 271, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contBailOriAmount
        contBailOriAmount.setBoundEditor(txtBailOriAmount);
        //contBailRate
        contBailRate.setBoundEditor(txtBailRate);

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
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
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
        menuView.add(menuItemViewContent);
        menuView.add(menuItemLocate);
        menuView.add(menuItemViewBgBalance);
        menuView.add(menuItemViewProg);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemSplit);
        menuBiz.add(menuItemDelSplit);
        menuBiz.add(menuItemContractPayPlan);
        menuBiz.add(enuItemViewCost);
        menuBiz.add(menuItemProgram);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnProgram);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnCalculator);
        this.toolBar.add(btnSplit);
        this.toolBar.add(btnDelSplit);
        this.toolBar.add(btnViewContent);
        this.toolBar.add(btnContractPlan);
        this.toolBar.add(btnViewCost);
        this.toolBar.add(btnViewBgBalance);
        this.toolBar.add(btnViewProgramContract);
        this.toolBar.add(btnAgreement);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.ContractDetailFullInfoUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.txtOrg.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.contract.ContractBillInfo)ov;
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
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("originalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("landDeveloper", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractPropert", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("partB", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("partC", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isSubContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lowestPriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lowerPriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("middlePriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("higherPriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("highestPriceUni", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("coopLevel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mainContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("effectiveStartDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("effectiveEndDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("information", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lowestPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lowerPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("higherPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("middlePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("highestPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("basePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("secondPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("winPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("winUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fileNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("quantity", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payPercForWarn", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chgPercForWarn", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costProperty", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payScale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("conChargeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("exRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("grtAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("respPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("grtRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("period", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("programmingContract.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bookedDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isOpenContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isStardContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isPartAMaterialCon", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isCoseSplit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("respDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractSourceId", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractWFType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("model", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("srcAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.payItemDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.payCondition", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.prop", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.desc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payItems.paymentType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry.bailDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry.bailConditon", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry.prop", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry.desc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bail.prop", ValidateHelper.ON_SAVE);    		
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractDetailFullInfoUI");
    }




}