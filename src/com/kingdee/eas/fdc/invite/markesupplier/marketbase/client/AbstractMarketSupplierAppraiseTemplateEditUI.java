/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.marketbase.client;

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
public abstract class AbstractMarketSupplierAppraiseTemplateEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketSupplierAppraiseTemplateEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttempType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAccreditationType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contremake;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE1;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE1_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisEnable;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contstate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contauditPerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimpleName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmttempType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAccreditationType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtremake;
    protected com.kingdee.bos.ctrl.swing.KDComboBox state;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkauditDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtauditPerson;
    protected com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractMarketSupplierAppraiseTemplateEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketSupplierAppraiseTemplateEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conttempType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAccreditationType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contremake = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtE1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.chkisEnable = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contstate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contauditPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtSimpleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.prmttempType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAccreditationType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtremake = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.state = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkauditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtauditPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.conttempType.setName("conttempType");
        this.contAccreditationType.setName("contAccreditationType");
        this.contremake.setName("contremake");
        this.kdtE1.setName("kdtE1");
        this.chkisEnable.setName("chkisEnable");
        this.contstate.setName("contstate");
        this.contauditDate.setName("contauditDate");
        this.contauditPerson.setName("contauditPerson");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.txtSimpleName.setName("txtSimpleName");
        this.txtDescription.setName("txtDescription");
        this.prmttempType.setName("prmttempType");
        this.prmtAccreditationType.setName("prmtAccreditationType");
        this.txtremake.setName("txtremake");
        this.state.setName("state");
        this.pkauditDate.setName("pkauditDate");
        this.prmtauditPerson.setName("prmtauditPerson");
        // CoreUI		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);		
        this.kDLabelContainer4.setVisible(true);
        // conttempType		
        this.conttempType.setBoundLabelText(resHelper.getString("conttempType.boundLabelText"));		
        this.conttempType.setBoundLabelLength(100);		
        this.conttempType.setBoundLabelUnderline(true);		
        this.conttempType.setVisible(true);
        // contAccreditationType		
        this.contAccreditationType.setBoundLabelText(resHelper.getString("contAccreditationType.boundLabelText"));		
        this.contAccreditationType.setBoundLabelLength(100);		
        this.contAccreditationType.setBoundLabelUnderline(true);		
        this.contAccreditationType.setVisible(true);
        // contremake		
        this.contremake.setBoundLabelText(resHelper.getString("contremake.boundLabelText"));		
        this.contremake.setBoundLabelLength(100);		
        this.contremake.setBoundLabelUnderline(true);		
        this.contremake.setVisible(true);
        // kdtE1
		String kdtE1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>&amp;double</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"Accreditationwd\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:configured=\"false\" /><t:Column t:key=\"IndexName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:configured=\"false\" /><t:Column t:key=\"threeStandard\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"IndexDesc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"ScoreType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"qz\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol6\" /><t:Column t:key=\"remake\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">单据分录序列号</t:Cell><t:Cell t:configured=\"false\">评审维度</t:Cell><t:Cell t:configured=\"false\">指标名称</t:Cell><t:Cell t:configured=\"false\">3分标准</t:Cell><t:Cell t:configured=\"false\">指标描述</t:Cell><t:Cell t:configured=\"false\">评分类别</t:Cell><t:Cell t:configured=\"false\">权重%</t:Cell><t:Cell t:configured=\"false\">备注</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE1.setFormatXml(resHelper.translateString("kdtE1",kdtE1StrXML));
        this.kdtE1.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtE1_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtE1.putBindContents("editData",new String[] {"seq","Accreditationwd","IndexName","threeStandard","IndexDesc","ScoreType","qz","remake"});


        this.kdtE1.checkParsed();
        KDTextField kdtE1_Accreditationwd_TextField = new KDTextField();
        kdtE1_Accreditationwd_TextField.setName("kdtE1_Accreditationwd_TextField");
        kdtE1_Accreditationwd_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtE1_Accreditationwd_CellEditor = new KDTDefaultCellEditor(kdtE1_Accreditationwd_TextField);
        this.kdtE1.getColumn("Accreditationwd").setEditor(kdtE1_Accreditationwd_CellEditor);
        final KDBizPromptBox kdtE1_IndexName_PromptBox = new KDBizPromptBox();
        kdtE1_IndexName_PromptBox.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.MarketSplAuditIndexQuery");
        kdtE1_IndexName_PromptBox.setVisible(true);
        kdtE1_IndexName_PromptBox.setEditable(true);
        kdtE1_IndexName_PromptBox.setDisplayFormat("$number$");
        kdtE1_IndexName_PromptBox.setEditFormat("$number$");
        kdtE1_IndexName_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtE1_IndexName_CellEditor = new KDTDefaultCellEditor(kdtE1_IndexName_PromptBox);
        this.kdtE1.getColumn("IndexName").setEditor(kdtE1_IndexName_CellEditor);
        ObjectValueRender kdtE1_IndexName_OVR = new ObjectValueRender();
        kdtE1_IndexName_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtE1.getColumn("IndexName").setRenderer(kdtE1_IndexName_OVR);
        			kdtE1_IndexName_PromptBox.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.fdc.invite.markesupplier.marketbase.client.MarketSplAuditIndexListUI kdtE1_IndexName_PromptBox_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (kdtE1_IndexName_PromptBox_F7ListUI == null) {
					try {
						kdtE1_IndexName_PromptBox_F7ListUI = new com.kingdee.eas.fdc.invite.markesupplier.marketbase.client.MarketSplAuditIndexListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(kdtE1_IndexName_PromptBox_F7ListUI));
					kdtE1_IndexName_PromptBox_F7ListUI.setF7Use(true,ctx);
					kdtE1_IndexName_PromptBox.setSelector(kdtE1_IndexName_PromptBox_F7ListUI);
				}
			}
		});
					
        KDTextField kdtE1_threeStandard_TextField = new KDTextField();
        kdtE1_threeStandard_TextField.setName("kdtE1_threeStandard_TextField");
        kdtE1_threeStandard_TextField.setMaxLength(1000);
        KDTDefaultCellEditor kdtE1_threeStandard_CellEditor = new KDTDefaultCellEditor(kdtE1_threeStandard_TextField);
        this.kdtE1.getColumn("threeStandard").setEditor(kdtE1_threeStandard_CellEditor);
        KDTextField kdtE1_IndexDesc_TextField = new KDTextField();
        kdtE1_IndexDesc_TextField.setName("kdtE1_IndexDesc_TextField");
        kdtE1_IndexDesc_TextField.setMaxLength(1000);
        KDTDefaultCellEditor kdtE1_IndexDesc_CellEditor = new KDTDefaultCellEditor(kdtE1_IndexDesc_TextField);
        this.kdtE1.getColumn("IndexDesc").setEditor(kdtE1_IndexDesc_CellEditor);
        KDComboBox kdtE1_ScoreType_ComboBox = new KDComboBox();
        kdtE1_ScoreType_ComboBox.setName("kdtE1_ScoreType_ComboBox");
        kdtE1_ScoreType_ComboBox.setVisible(true);
        kdtE1_ScoreType_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.supplier.AppraiseTypeEnum").toArray());
        KDTDefaultCellEditor kdtE1_ScoreType_CellEditor = new KDTDefaultCellEditor(kdtE1_ScoreType_ComboBox);
        this.kdtE1.getColumn("ScoreType").setEditor(kdtE1_ScoreType_CellEditor);
        KDFormattedTextField kdtE1_qz_TextField = new KDFormattedTextField();
        kdtE1_qz_TextField.setName("kdtE1_qz_TextField");
        kdtE1_qz_TextField.setVisible(true);
        kdtE1_qz_TextField.setEditable(true);
        kdtE1_qz_TextField.setHorizontalAlignment(2);
        kdtE1_qz_TextField.setDataType(1);
        	kdtE1_qz_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E26"));
        	kdtE1_qz_TextField.setMaximumValue(new java.math.BigDecimal("1.0E26"));
        kdtE1_qz_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtE1_qz_CellEditor = new KDTDefaultCellEditor(kdtE1_qz_TextField);
        this.kdtE1.getColumn("qz").setEditor(kdtE1_qz_CellEditor);
        KDTextField kdtE1_remake_TextField = new KDTextField();
        kdtE1_remake_TextField.setName("kdtE1_remake_TextField");
        kdtE1_remake_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_remake_CellEditor = new KDTDefaultCellEditor(kdtE1_remake_TextField);
        this.kdtE1.getColumn("remake").setEditor(kdtE1_remake_CellEditor);
        // chkisEnable		
        this.chkisEnable.setText(resHelper.getString("chkisEnable.text"));		
        this.chkisEnable.setVisible(true);		
        this.chkisEnable.setHorizontalAlignment(2);
        // contstate		
        this.contstate.setBoundLabelText(resHelper.getString("contstate.boundLabelText"));		
        this.contstate.setBoundLabelLength(100);		
        this.contstate.setBoundLabelUnderline(true);		
        this.contstate.setVisible(true);
        // contauditDate		
        this.contauditDate.setBoundLabelText(resHelper.getString("contauditDate.boundLabelText"));		
        this.contauditDate.setBoundLabelLength(100);		
        this.contauditDate.setBoundLabelUnderline(true);		
        this.contauditDate.setVisible(true);
        // contauditPerson		
        this.contauditPerson.setBoundLabelText(resHelper.getString("contauditPerson.boundLabelText"));		
        this.contauditPerson.setBoundLabelLength(100);		
        this.contauditPerson.setBoundLabelUnderline(true);		
        this.contauditPerson.setVisible(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // txtName
        // txtSimpleName		
        this.txtSimpleName.setMaxLength(80);		
        this.txtSimpleName.setVisible(false);
        // txtDescription		
        this.txtDescription.setVisible(false);
        // prmttempType		
        this.prmttempType.setQueryInfo("com.kingdee.eas.fdc.insider.app.AimSourceHouseQuery");		
        this.prmttempType.setVisible(true);		
        this.prmttempType.setEditable(true);		
        this.prmttempType.setDisplayFormat("$name$");		
        this.prmttempType.setEditFormat("$number$");		
        this.prmttempType.setCommitFormat("$number$");		
        this.prmttempType.setRequired(false);
        		EntityViewInfo eviprmttempType = new EntityViewInfo ();
		eviprmttempType.setFilter(com.kingdee.eas.framework.FrameWorkUtils.getF7FilterInfoByAuthorizedOrg(com.kingdee.eas.basedata.org.OrgType.getEnum("Sale"),"saleOrgUnit.id"));
		prmttempType.setEntityViewInfo(eviprmttempType);
					
        // prmtAccreditationType		
        this.prmtAccreditationType.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.MarketAccreditationTypeQuery");		
        this.prmtAccreditationType.setVisible(true);		
        this.prmtAccreditationType.setEditable(true);		
        this.prmtAccreditationType.setDisplayFormat("$name$");		
        this.prmtAccreditationType.setEditFormat("$number$");		
        this.prmtAccreditationType.setCommitFormat("$number$");		
        this.prmtAccreditationType.setRequired(false);
        // txtremake		
        this.txtremake.setVisible(true);		
        this.txtremake.setHorizontalAlignment(2);		
        this.txtremake.setMaxLength(100);		
        this.txtremake.setRequired(false);
        // state		
        this.state.setVisible(true);		
        this.state.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.FDCBillStateEnum").toArray());		
        this.state.setRequired(false);
        // pkauditDate		
        this.pkauditDate.setVisible(true);		
        this.pkauditDate.setRequired(false);
        // prmtauditPerson		
        this.prmtauditPerson.setQueryInfo("com.kingdee.eas.base.permission.app.UserListQuery");		
        this.prmtauditPerson.setVisible(true);		
        this.prmtauditPerson.setEditable(true);		
        this.prmtauditPerson.setDisplayFormat("$name$");		
        this.prmtauditPerson.setEditFormat("$number$");		
        this.prmtauditPerson.setCommitFormat("$number$");		
        this.prmtauditPerson.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {kdtE1,txtremake,prmtAccreditationType,prmttempType,txtSimpleName,txtDescription,txtNumber,txtName}));
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
        this.setBounds(new Rectangle(0, 0, 693, 500));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(30, 52, 270, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(381, 52, 270, 19));
        this.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(646, 177, 270, 19));
        this.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(680, 152, 270, 19));
        this.add(kDLabelContainer4, null);
        conttempType.setBounds(new Rectangle(30, 18, 270, 19));
        this.add(conttempType, null);
        contAccreditationType.setBounds(new Rectangle(381, 18, 270, 19));
        this.add(contAccreditationType, null);
        contremake.setBounds(new Rectangle(30, 80, 622, 19));
        this.add(contremake, null);
        kdtE1.setBounds(new Rectangle(30, 106, 621, 377));
        kdtE1_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE1,new com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateE1Info(),null,false);
        this.add(kdtE1_detailPanel, null);
		kdtE1_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
			public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
				IObjectValue vo = event.getObjectValue();
vo.put("ScoreType","WEIGHT");
			}
			public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
			}
		});
        chkisEnable.setBounds(new Rectangle(59, 328, 96, 19));
        this.add(chkisEnable, null);
        contstate.setBounds(new Rectangle(405, 328, 270, 19));
        this.add(contstate, null);
        contauditDate.setBounds(new Rectangle(333, 25, 270, 19));
        this.add(contauditDate, null);
        contauditPerson.setBounds(new Rectangle(342, 4, 270, 19));
        this.add(contauditPerson, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtName);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtSimpleName);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtDescription);
        //conttempType
        conttempType.setBoundEditor(prmttempType);
        //contAccreditationType
        contAccreditationType.setBoundEditor(prmtAccreditationType);
        //contremake
        contremake.setBoundEditor(txtremake);
        //contstate
        contstate.setBoundEditor(state);
        //contauditDate
        contauditDate.setBoundEditor(pkauditDate);
        //contauditPerson
        contauditPerson.setBoundEditor(prmtauditPerson);

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
		dataBinder.registerBinding("E1.seq", int.class, this.kdtE1, "seq.text");
		dataBinder.registerBinding("E1", com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateE1Info.class, this.kdtE1, "userObject");
		dataBinder.registerBinding("E1.IndexName", java.lang.Object.class, this.kdtE1, "IndexName.text");
		dataBinder.registerBinding("E1.Accreditationwd", String.class, this.kdtE1, "Accreditationwd.text");
		dataBinder.registerBinding("E1.threeStandard", String.class, this.kdtE1, "threeStandard.text");
		dataBinder.registerBinding("E1.IndexDesc", String.class, this.kdtE1, "IndexDesc.text");
		dataBinder.registerBinding("E1.ScoreType", com.kingdee.util.enums.Enum.class, this.kdtE1, "ScoreType.text");
		dataBinder.registerBinding("E1.qz", java.math.BigDecimal.class, this.kdtE1, "qz.text");
		dataBinder.registerBinding("E1.remake", String.class, this.kdtE1, "remake.text");
		dataBinder.registerBinding("isEnable", boolean.class, this.chkisEnable, "selected");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("simpleName", String.class, this.txtSimpleName, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "_multiLangItem");
		dataBinder.registerBinding("tempType", com.kingdee.eas.fdc.insider.AimSourceHouseInfo.class, this.prmttempType, "data");
		dataBinder.registerBinding("AccreditationType", com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeInfo.class, this.prmtAccreditationType, "data");
		dataBinder.registerBinding("remake", String.class, this.txtremake, "text");
		dataBinder.registerBinding("state", com.kingdee.eas.fdc.basedata.FDCBillStateEnum.class, this.state, "selectedItem");
		dataBinder.registerBinding("auditDate", java.util.Date.class, this.pkauditDate, "value");
		dataBinder.registerBinding("auditPerson", com.kingdee.eas.base.permission.UserInfo.class, this.prmtauditPerson, "data");		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtDescription, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtSimpleName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtDescription, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtSimpleName, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtDescription, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtNumber, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtSimpleName, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.MarketSupplierAppraiseTemplateEditUIHandler";
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
        this.kdtE1.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"NONE",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("NONE");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer oufip=(com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer)com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
		}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????У??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("E1.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.IndexName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.Accreditationwd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.threeStandard", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.IndexDesc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.ScoreType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.qz", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.remake", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isEnable", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tempType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("AccreditationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("remake", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditPerson", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtDescription.setEnabled(true);
		            this.txtNumber.setEnabled(true);
		            this.txtSimpleName.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtDescription.setEnabled(true);
		            this.txtNumber.setEnabled(true);
		            this.txtSimpleName.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(false);
		            this.txtDescription.setEnabled(false);
		            this.txtNumber.setEnabled(false);
		            this.txtSimpleName.setEnabled(false);
        }
    }

    /**
     * output kdtE1_editStopped method
     */
    protected void kdtE1_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code heresss
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
    sic.add(new SelectorItemInfo("E1.seq"));
        sic.add(new SelectorItemInfo("E1.*"));
//        sic.add(new SelectorItemInfo("E1.number"));
        sic.add(new SelectorItemInfo("E1.IndexName.*"));
//        sic.add(new SelectorItemInfo("E1.IndexName.number"));
    sic.add(new SelectorItemInfo("E1.Accreditationwd"));
    sic.add(new SelectorItemInfo("E1.threeStandard"));
    sic.add(new SelectorItemInfo("E1.IndexDesc"));
    sic.add(new SelectorItemInfo("E1.ScoreType"));
    sic.add(new SelectorItemInfo("E1.qz"));
    sic.add(new SelectorItemInfo("E1.remake"));
        sic.add(new SelectorItemInfo("isEnable"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("tempType.*"));
        sic.add(new SelectorItemInfo("AccreditationType.*"));
        sic.add(new SelectorItemInfo("remake"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("auditDate"));
        sic.add(new SelectorItemInfo("auditPerson.*"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.markesupplier.marketbase.client", "MarketSupplierAppraiseTemplateEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.client.MarketSupplierAppraiseTemplateEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtE1;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
		vo.put("state","1SAVED");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}