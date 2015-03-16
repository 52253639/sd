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
public abstract class AbstractCompeterEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCompeterEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSysType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboSysType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdvantage;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDisAdvantage;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblPriceRecordList;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtAdvantage;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane3;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDisAdvantage;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.eas.fdc.market.CompeterInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractCompeterEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCompeterEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSysType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboSysType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contAdvantage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDisAdvantage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tblPriceRecordList = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtAdvantage = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDScrollPane3 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDisAdvantage = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCreator.setName("contCreator");
        this.prmtCreator.setName("prmtCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contDescription.setName("contDescription");
        this.contSysType.setName("contSysType");
        this.comboSysType.setName("comboSysType");
        this.contAdvantage.setName("contAdvantage");
        this.contDisAdvantage.setName("contDisAdvantage");
        this.tblPriceRecordList.setName("tblPriceRecordList");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtAdvantage.setName("txtAdvantage");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.kDScrollPane3.setName("kDScrollPane3");
        this.txtDisAdvantage.setName("txtDisAdvantage");
        this.txtDescription.setName("txtDescription");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.txtName.setName("txtName");
        // CoreUI
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setRequired(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contSysType		
        this.contSysType.setBoundLabelText(resHelper.getString("contSysType.boundLabelText"));		
        this.contSysType.setBoundLabelLength(100);		
        this.contSysType.setBoundLabelUnderline(true);
        // comboSysType		
        this.comboSysType.setRequired(true);		
        this.comboSysType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.MoneySysTypeEnum").toArray());
        this.comboSysType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboSysType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contAdvantage		
        this.contAdvantage.setBoundLabelText(resHelper.getString("contAdvantage.boundLabelText"));		
        this.contAdvantage.setBoundLabelLength(100);		
        this.contAdvantage.setBoundLabelUnderline(true);
        // contDisAdvantage		
        this.contDisAdvantage.setBoundLabelText(resHelper.getString("contDisAdvantage.boundLabelText"));		
        this.contDisAdvantage.setBoundLabelLength(100);		
        this.contDisAdvantage.setBoundLabelUnderline(true);
        // tblPriceRecordList		
        this.tblPriceRecordList.setFormatXml(resHelper.getString("tblPriceRecordList.formatXml"));

                this.tblPriceRecordList.putBindContents("editData",new String[] {"investDate","rendtype","lastAveragePrice","favourableType","description"});


        // kDScrollPane1
        // txtAdvantage		
        this.txtAdvantage.setMaxLength(255);
        // kDScrollPane2
        // kDScrollPane3
        // txtDisAdvantage		
        this.txtDisAdvantage.setMaxLength(255);
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // kDDateCreateTime		
        this.kDDateCreateTime.setEnabled(false);
        // txtName		
        this.txtName.setMaxLength(80);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        contCreator.setBounds(new Rectangle(373, 170, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(373, 170, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contCreateTime.setBounds(new Rectangle(9, 170, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(9, 170, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(371, 9, 270, 19));
        this.add(contName, new KDLayout.Constraints(371, 9, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE));
        contNumber.setBounds(new Rectangle(9, 9, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(9, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(9, 123, 996, 40));
        this.add(contDescription, new KDLayout.Constraints(9, 123, 996, 40, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contSysType.setBounds(new Rectangle(734, 9, 270, 19));
        this.add(contSysType, new KDLayout.Constraints(734, 9, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAdvantage.setBounds(new Rectangle(9, 33, 996, 40));
        this.add(contAdvantage, new KDLayout.Constraints(9, 33, 996, 40, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contDisAdvantage.setBounds(new Rectangle(9, 78, 996, 40));
        this.add(contDisAdvantage, new KDLayout.Constraints(9, 78, 996, 40, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        tblPriceRecordList.setBounds(new Rectangle(8, 203, 996, 419));
        this.add(tblPriceRecordList, new KDLayout.Constraints(8, 203, 996, 419, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane3);
        //kDScrollPane3
        kDScrollPane3.getViewport().add(txtDescription, null);
        //contSysType
        contSysType.setBoundEditor(comboSysType);
        //contAdvantage
        contAdvantage.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtAdvantage, null);
        //contDisAdvantage
        contDisAdvantage.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtDisAdvantage, null);

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
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("sysType", com.kingdee.eas.fdc.basedata.MoneySysTypeEnum.class, this.comboSysType, "selectedItem");
		dataBinder.registerBinding("priceRecordList", com.kingdee.eas.fdc.market.CompetePriceRecordInfo.class, this.tblPriceRecordList, "userObject");
		dataBinder.registerBinding("priceRecordList.investDate", java.util.Date.class, this.tblPriceRecordList, "investDate.text");
		dataBinder.registerBinding("priceRecordList.rendtype", com.kingdee.eas.fdc.tenancy.RentTypeEnum.class, this.tblPriceRecordList, "rendtype.text");
		dataBinder.registerBinding("priceRecordList.lastAveragePrice", java.math.BigDecimal.class, this.tblPriceRecordList, "lastAveragePrice.text");
		dataBinder.registerBinding("priceRecordList.favourableType", String.class, this.tblPriceRecordList, "favourableType.text");
		dataBinder.registerBinding("priceRecordList.description", String.class, this.tblPriceRecordList, "description.text");
		dataBinder.registerBinding("advantage", String.class, this.txtAdvantage, "text");
		dataBinder.registerBinding("disAdvantage", String.class, this.txtDisAdvantage, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "userObject");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.CompeterEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.market.CompeterInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sysType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceRecordList", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceRecordList.investDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceRecordList.rendtype", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceRecordList.lastAveragePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceRecordList.favourableType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceRecordList.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("advantage", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("disAdvantage", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
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
     * output comboSysType_itemStateChanged method
     */
    protected void comboSysType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("sysType"));
        sic.add(new SelectorItemInfo("priceRecordList.*"));
//        sic.add(new SelectorItemInfo("priceRecordList.number"));
    sic.add(new SelectorItemInfo("priceRecordList.investDate"));
    sic.add(new SelectorItemInfo("priceRecordList.rendtype"));
    sic.add(new SelectorItemInfo("priceRecordList.lastAveragePrice"));
    sic.add(new SelectorItemInfo("priceRecordList.favourableType"));
    sic.add(new SelectorItemInfo("priceRecordList.description"));
        sic.add(new SelectorItemInfo("advantage"));
        sic.add(new SelectorItemInfo("disAdvantage"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("name"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "CompeterEditUI");
    }




}