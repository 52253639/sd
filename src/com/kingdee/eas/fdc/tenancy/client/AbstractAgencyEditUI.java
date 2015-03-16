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
public abstract class AbstractAgencyEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAgencyEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSimpleName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimpleName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contadress;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtadress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contfax;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtfax;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contphone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtphone;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtPersons;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtPersons_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.eas.fdc.tenancy.AgencyInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractAgencyEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractAgencyEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSimpleName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSimpleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contadress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtadress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contfax = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtfax = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contphone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtphone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdtPersons = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contName.setName("contName");
        this.contSimpleName.setName("contSimpleName");
        this.txtSimpleName.setName("txtSimpleName");
        this.contDescription.setName("contDescription");
        this.contadress.setName("contadress");
        this.txtadress.setName("txtadress");
        this.contfax.setName("contfax");
        this.txtfax.setName("txtfax");
        this.contphone.setName("contphone");
        this.txtphone.setName("txtphone");
        this.kdtPersons.setName("kdtPersons");
        this.txtName.setName("txtName");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        // CoreUI		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);		
        this.contNumber.setBoundLabelAlignment(7);		
        this.contNumber.setVisible(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setRequired(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contSimpleName		
        this.contSimpleName.setBoundLabelText(resHelper.getString("contSimpleName.boundLabelText"));		
        this.contSimpleName.setBoundLabelLength(100);		
        this.contSimpleName.setBoundLabelUnderline(true);		
        this.contSimpleName.setBoundLabelAlignment(7);		
        this.contSimpleName.setVisible(true);
        // txtSimpleName		
        this.txtSimpleName.setMaxLength(80);		
        this.txtSimpleName.setVisible(true);		
        this.txtSimpleName.setEnabled(true);		
        this.txtSimpleName.setHorizontalAlignment(2);		
        this.txtSimpleName.setRequired(false);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);		
        this.contDescription.setBoundLabelAlignment(7);		
        this.contDescription.setVisible(true);
        // contadress		
        this.contadress.setBoundLabelText(resHelper.getString("contadress.boundLabelText"));		
        this.contadress.setBoundLabelLength(100);		
        this.contadress.setBoundLabelUnderline(true);		
        this.contadress.setVisible(true);
        // txtadress		
        this.txtadress.setVisible(true);		
        this.txtadress.setHorizontalAlignment(2);		
        this.txtadress.setMaxLength(50);		
        this.txtadress.setRequired(false);
        // contfax		
        this.contfax.setBoundLabelText(resHelper.getString("contfax.boundLabelText"));		
        this.contfax.setBoundLabelLength(100);		
        this.contfax.setBoundLabelUnderline(true);		
        this.contfax.setVisible(true);
        // txtfax		
        this.txtfax.setVisible(true);		
        this.txtfax.setHorizontalAlignment(2);		
        this.txtfax.setMaxLength(50);		
        this.txtfax.setRequired(false);
        // contphone		
        this.contphone.setBoundLabelText(resHelper.getString("contphone.boundLabelText"));		
        this.contphone.setBoundLabelLength(100);		
        this.contphone.setBoundLabelUnderline(true);		
        this.contphone.setVisible(true);		
        this.contphone.setBoundLabelAlignment(7);
        // txtphone		
        this.txtphone.setVisible(true);		
        this.txtphone.setHorizontalAlignment(2);		
        this.txtphone.setMaxLength(50);		
        this.txtphone.setRequired(false);		
        this.txtphone.setEnabled(true);
        // kdtPersons		
        this.kdtPersons.setFormatXml(resHelper.getString("kdtPersons.formatXml"));		
        this.kdtPersons.setToolTipText(resHelper.getString("kdtPersons.toolTipText"));

                this.kdtPersons.putBindContents("editData",new String[] {"job","name","phone"});


        this.kdtPersons.checkParsed();
        KDTextField kdtPersons_job_TextField = new KDTextField();
        kdtPersons_job_TextField.setName("kdtPersons_job_TextField");
        kdtPersons_job_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtPersons_job_CellEditor = new KDTDefaultCellEditor(kdtPersons_job_TextField);
        this.kdtPersons.getColumn("job").setEditor(kdtPersons_job_CellEditor);
        KDTextField kdtPersons_name_TextField = new KDTextField();
        kdtPersons_name_TextField.setName("kdtPersons_name_TextField");
        kdtPersons_name_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtPersons_name_CellEditor = new KDTDefaultCellEditor(kdtPersons_name_TextField);
        this.kdtPersons.getColumn("name").setEditor(kdtPersons_name_CellEditor);
        KDTextField kdtPersons_phone_TextField = new KDTextField();
        kdtPersons_phone_TextField.setName("kdtPersons_phone_TextField");
        kdtPersons_phone_TextField.setMaxLength(80);
        KDTDefaultCellEditor kdtPersons_phone_CellEditor = new KDTDefaultCellEditor(kdtPersons_phone_TextField);
        this.kdtPersons.getColumn("phone").setEditor(kdtPersons_phone_CellEditor);
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setMaxLength(80);
        // kDScrollPane1
        // txtDescription		
        this.txtDescription.setMaxLength(80);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(0, 0, 600, 400));
        this.setLayout(null);
        contNumber.setBounds(new Rectangle(9, 6, 270, 19));
        this.add(contNumber, null);
        contName.setBounds(new Rectangle(298, 5, 270, 19));
        this.add(contName, null);
        contSimpleName.setBounds(new Rectangle(300, 35, 270, 19));
        this.add(contSimpleName, null);
        contDescription.setBounds(new Rectangle(9, 102, 567, 61));
        this.add(contDescription, null);
        contadress.setBounds(new Rectangle(9, 36, 270, 19));
        this.add(contadress, null);
        contfax.setBounds(new Rectangle(10, 71, 270, 19));
        this.add(contfax, null);
        contphone.setBounds(new Rectangle(297, 69, 270, 19));
        this.add(contphone, null);
        kdtPersons.setBounds(new Rectangle(11, 172, 572, 222));
        kdtPersons_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtPersons,new com.kingdee.eas.fdc.tenancy.AgencyPersonInfo(),null,false);
        this.add(kdtPersons_detailPanel, null);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contSimpleName
        contSimpleName.setBoundEditor(txtSimpleName);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contadress
        contadress.setBoundEditor(txtadress);
        //contfax
        contfax.setBoundEditor(txtfax);
        //contphone
        contphone.setBoundEditor(txtphone);

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
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("simpleName", String.class, this.txtSimpleName, "text");
		dataBinder.registerBinding("adress", String.class, this.txtadress, "text");
		dataBinder.registerBinding("fax", String.class, this.txtfax, "text");
		dataBinder.registerBinding("phone", String.class, this.txtphone, "text");
		dataBinder.registerBinding("Persons.job", String.class, this.kdtPersons, "job.text");
		dataBinder.registerBinding("Persons.name", String.class, this.kdtPersons, "name.text");
		dataBinder.registerBinding("Persons.phone", String.class, this.kdtPersons, "phone.text");
		dataBinder.registerBinding("Persons", com.kingdee.eas.fdc.tenancy.AgencyPersonInfo.class, this.kdtPersons, "userObject");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtSimpleName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtSimpleName, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtNumber, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtSimpleName, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.AgencyEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.tenancy.AgencyInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"Sale",editData.getString("number"));
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
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Sale");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("Sale");
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
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adress", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("phone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Persons.job", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Persons.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Persons.phone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Persons", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
		            this.txtNumber.setEnabled(true);
		            this.txtSimpleName.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.txtNumber.setEnabled(true);
		            this.txtSimpleName.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.txtNumber.setEnabled(false);
		            this.txtSimpleName.setEnabled(false);
        }
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("adress"));
        sic.add(new SelectorItemInfo("fax"));
        sic.add(new SelectorItemInfo("phone"));
    sic.add(new SelectorItemInfo("Persons.job"));
    sic.add(new SelectorItemInfo("Persons.name"));
    sic.add(new SelectorItemInfo("Persons.phone"));
        sic.add(new SelectorItemInfo("Persons.*"));
//        sic.add(new SelectorItemInfo("Persons.number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("description"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "AgencyEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.tenancy.client.AgencyEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.tenancy.AgencyFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.tenancy.AgencyInfo objectValue = new com.kingdee.eas.fdc.tenancy.AgencyInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtPersons;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}