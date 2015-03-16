/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CityInfo;
import com.kingdee.eas.basedata.assistant.CountryInfo;
import com.kingdee.eas.basedata.assistant.IndustryInfo;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.assistant.RegionInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.AssociationTypeEnum;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.ContactPreferencesEnum;
import com.kingdee.eas.fdc.basecrm.CreateWayEnum;
import com.kingdee.eas.fdc.basecrm.CustomerChangeLogCollection;
import com.kingdee.eas.fdc.basecrm.CustomerChangeLogFactory;
import com.kingdee.eas.fdc.basecrm.CustomerChangeLogInfo;
import com.kingdee.eas.fdc.basecrm.CustomerTypeEnum;
import com.kingdee.eas.fdc.basecrm.FDCBaseCustomerInfo;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataCollection;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataFactory;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerFactory;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerFactory;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo;
import com.kingdee.eas.fdc.basecrm.IDCard;
import com.kingdee.eas.fdc.basecrm.MaritalStatusEnum;
import com.kingdee.eas.fdc.basecrm.OrgCustomerLinkManCollection;
import com.kingdee.eas.fdc.basecrm.OrgCustomerLinkManFactory;
import com.kingdee.eas.fdc.basecrm.OrgCustomerLinkManInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SexEnum;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.DeletedStatusEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.enums.EnumUtils;

/**
 * output class name
 */
public class FDCCustomerEditUI extends AbstractFDCCustomerEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCCustomerEditUI.class);
    
    private Map defValues;
    private boolean isOld = false;
    private String firstLinkMan = null;
    private String corporate = null;
    private String corporateTel = null;
    public FDCCustomerEditUI() throws Exception
    {
        super();
		
		defValues = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
//		SelectorItemCollection sels = new SelectorItemCollection();
//		sels.add("*");
//		view.setSelector(sels);
		FilterInfo filter = new FilterInfo();
		filter .getFilterItems().add(new FilterItemInfo("isDefault", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		FDCCusBaseDataCollection baseDatas = FDCCusBaseDataFactory.getRemoteInstance().getFDCCusBaseDataCollection(view);
		
		for(int i=0; i<baseDatas.size(); i++){
			FDCCusBaseDataInfo data = baseDatas.get(i);
			if(data.getGroup() != null){
				String groupID = data.getGroup().getId().toString();
				defValues.put(groupID, data);
			}
		}
    }

    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	this.getUIContext().remove("mainCus");
    	this.getUIContext().remove("customerType");
    	this.getUIContext().remove("cusName");
    	this.getUIContext().remove("cusPhone");
    	
    	FilterItemInfo scope = new FilterItemInfo("belongUnit.id", SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
		Map linkedCus = CusClientHelper.addNewCusBegin2(this, FDCOrgCustomerFactory.getRemoteInstance(), scope);
		if(linkedCus != null){
			Boolean isAbort = (Boolean) linkedCus.get("isAbort");
			if(isAbort != null  &&  isAbort.booleanValue()){
				this.abort();
			}
			this.getUIContext().putAll(linkedCus);
		}
    	
    	super.actionAddNew_actionPerformed(e);
    }
    
    protected void f7Country_dataChanged(DataChangeEvent e) throws Exception {
    	if(!isLoadField)
    	setAddr();
    }
    
    protected void f7Province_dataChanged(DataChangeEvent e) throws Exception {
    	if(!isLoadField)
    	setAddr();	
    }
    
    protected void f7City_dataChanged(DataChangeEvent e) throws Exception {
    	if(!isLoadField)setAddr();
    }
    
    protected void f7Area_dataChanged(DataChangeEvent e) throws Exception {
    	if(!isLoadField)setAddr();
    }
    
    private void setAddr(){
    	CountryInfo country = (CountryInfo) this.f7Country.getValue();
    	ProvinceInfo province = (ProvinceInfo) this.f7Province.getValue();
    	CityInfo city = (CityInfo) this.f7City.getValue();
    	RegionInfo region = (RegionInfo) this.f7Area.getValue();
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(country == null ? "" : country.getName());
    	sb.append(province == null ? "" : province.getName());
    	sb.append(city == null ? "" : city.getName());
    	sb.append(region == null ? "" : region.getName());
    	
    	this.txtMailAddress.setText(sb.toString());
    	this.txtCertificateAddr.setText(sb.toString());
    }
    
	protected IObjectValue createNewData() {
		FDCOrgCustomerInfo orgCus = new FDCOrgCustomerInfo();
		orgCus.setCreateWay(CreateWayEnum.HAND);
		orgCus.setContactPreferences(ContactPreferencesEnum.ANY);
		
		orgCus.setIdentity((FDCCusBaseDataInfo) defValues.get(CRMConstants.CUSTOMER_STATUS_GROUP_ID));
		orgCus.setCustomerOrigin((FDCCusBaseDataInfo) defValues.get(CRMConstants.CUSTOMER_ORIGIN_GROUP_ID));
		orgCus.setHabitationStatus((FDCCusBaseDataInfo) defValues.get(CRMConstants.HABITATION_STATUS_GROUP_ID));
		orgCus.setEnterpriceProperty((FDCCusBaseDataInfo) defValues.get(CRMConstants.ENTERPRICE_PROPERTY_GROUP_ID));
		orgCus.setEnterpriceSize((FDCCusBaseDataInfo) defValues.get(CRMConstants.ENTERPRICE_SIZE_GROUP_ID));
		
		orgCus.setCooperateModel((FDCCusBaseDataInfo) defValues.get(CRMConstants.COOPERATE_MODEL_GROUP_ID));
		
		Map uiContext = this.getUIContext();
		
		FDCMainCustomerInfo mainCus = (FDCMainCustomerInfo) uiContext.get("mainCus");
		if(mainCus != null){
			try {
				mainCus = FDCMainCustomerFactory.getRemoteInstance().getFDCMainCustomerInfo(new ObjectUuidPK(mainCus.getId().toString()), this.getSelectors());
			} catch (EASBizException e) {
				this.handleException(e);
				this.abort();
			} catch (BOSException e) {
				this.handleException(e);
				this.abort();
			}
			FDCMainCustomerInfo cloneMainCus = (FDCMainCustomerInfo) mainCus.clone();
			cloneMainCus.setId(null);
			
			CRMHelper.changeObjectValue(cloneMainCus, orgCus, false);
			
			orgCus.setMainCustomer(mainCus);
			orgCus.setNumber(null);
			orgCus.setCreateWay(CreateWayEnum.PARENTREFERENCE);
		}else{
			orgCus.setCustomerType((CustomerTypeEnum) uiContext.get("customerType"));
			orgCus.setName((String) uiContext.get("cusName"));
			orgCus.setPhone((String) uiContext.get("cusPhone"));
			orgCus.setCertificateNumber((String) uiContext.get("cusCertificateNum"));
		}
		
		if(orgCus.getCertificateType() == null){
			orgCus.setCertificateType((FDCCusBaseDataInfo) (CustomerTypeEnum.PERSONALCUSTOMER.equals(orgCus.getCustomerType()) ? defValues.get(CRMConstants.PE_CERTIFICATE_TYPE_GROUP_ID) : defValues.get(CRMConstants.EN_CERTIFICATE_TYPE_GROUP_ID)));
		}
		
		FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo();
		orgCus.setBelongUnit(orgUnit);
		orgCus.setCreateUnit(orgUnit);
		orgCus.setIsEnabled(true);
		orgCus.setCreator((UserInfo) (SysContext.getSysContext().getCurrentUserInfo()));
		orgCus.setCreateTime(new Timestamp(new Date().getTime()));
		
		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			String retNumber;
			retNumber = iCodingRuleManager.getNumber(orgCus, orgUnit.getId().toString());
			orgCus.setNumber(retNumber);
		} catch (BOSException e) {
			e.printStackTrace();
		}catch (EASBizException e) {
			e.printStackTrace();
		}
		
		return orgCus;
	}
	
	
	    
	protected ICoreBase getBizInterface() throws Exception {
		return FDCOrgCustomerFactory.getRemoteInstance();
	}

	private boolean isLoadField = false;
	public void loadFields() {
		isLoadField = true;
		super.loadFields();
		
		FDCOrgCustomerInfo cus = (FDCOrgCustomerInfo) this.editData;
		
		this.txtNumber.setText(cus.getNumber());
		this.comboCustomerType.setSelectedItem(cus.getCustomerType());
		this.txtName.setText(cus.getName());
		this.f7Nationality.setValue(cus.getBookedPlace());
		this.txtCode.setText(cus.getCode());
		
		this.txtSimCode.setText(cus.getSimpleCode());
		this.txtPhone.setText(cus.getPhone());
		this.txtTel.setText(cus.getTel());
		this.txtOfficeTel.setText(cus.getOfficeTel());
		this.txtFax.setText(cus.getFax());
		
		this.txtOtherTel.setText(cus.getOtherTel());
		this.txtEmail.setText(cus.getEmail());
		this.f7Certificate.setValue(cus.getCertificateType());
		this.txtCertificateNumber.setText(cus.getCertificateNumber());
		this.dpDayOfbirth.setValue(cus.getDayOfbirth());
		
		this.comboSex.setSelectedItem(cus.getSex());
		this.f7CusStatus.setValue(cus.getIdentity());
		this.f7Country.setValue(cus.getCountry());
		this.f7Province.setValue(cus.getProvince());
		this.f7City.setValue(cus.getCity());
		
		this.f7Area.setValue(cus.getRegion());
		this.txtMailAddress.setText(cus.getMailAddress());
		this.txtCertificateAddr.setText(cus.getBookedAddress());
		this.txtPostalcode.setText(cus.getPostalCode());
		this.txtFirstLinkman.setText(cus.getFirstLinkman());
		
		this.f7Industry.setValue(cus.getIndustry());
		this.f7EnterpriceProperty.setValue(cus.getEnterpriceProperty());
		this.txtLegalPerson.setText(cus.getCorporate());
		this.txtLegalPersonTel.setText(cus.getCorporateTel());
		this.f7CustomerOrigin.setValue(cus.getCustomerOrigin());
		
		this.f7PersonArea.setValue(cus.getNativePlace());
		this.comboMarriage.setSelectedItem(cus.getMaritalStatus());
		f7HabitationInfo.setValue(cus.getHabitationStatus());
		txtWorkCompany.setText(cus.getWorkCompany());
		comboFirstLinkType.setSelectedItem(cus.getContactPreferences());
		
		this.txtDescription.setText(cus.getDescription());
		
		txtCarCount.setText(cus.getMotorcycles());
		f7EnterpriceSize.setValue(cus.getEnterpriceSize());
		txtCapital.setText(cus.getBookedCapital());
		txtDealArea.setText(cus.getBusinessScope());
		txtEmployeeCount.setText(cus.getEmployees());
		
		txtTaxNumG.setText(cus.getTaxRegistrationNoG());
		txtTaxNumD.setText(cus.getTaxRegistrationNoD());
		f7CooperateMode.setValue(cus.getCooperateModel());
		
		//setComs(CustomerTypeEnum.PERSONALCUSTOMER.equals(cus.getCustomerType()));
		
		loadLinkMan(cus);
		
		loadDistributing();
		loadRooms();
		loadCommerce();
		loadTrackRecord();
		loadQuestions();
		
		loadSHEInfo();
		loadChangeLog();
		
		prmtCreateUnit.setValue(cus.getCreateUnit());
		prmtCreator.setValue(cus.getCreator());
		pkCreateTime.setValue(cus.getCreateTime());
		prmtLastUpdateUnit.setValue(cus.getLastUpdateUnit());

		if (!STATUS_ADDNEW.equals(getOprtState())
				&& cus.getLastUpdateUnit() == null) {
			prmtLastUpdateUnit.setValue(SysContext.getSysContext()
					.getCurrentSaleUnit().castToFullOrgUnitInfo());
		}
		prmtLastUpdateUser.setValue(cus.getLastUpdateUser());
		pkLastUpdateTime.setValue(cus.getLastUpdateTime());
		comboCreateWay.setSelectedItem(cus.getCreateWay());
		if("ADDNEW".equals(this.getOprtState())){
			setAddr();
		}
		
		
		boolean isView = "VIEW".equals(this.getOprtState()) ||  "FINDVIEW".equals(this.getOprtState());
		this.btnAdd.setEnabled(!isView);
		this.btnDelete.setEnabled(!isView);
		this.chkShowAll.setAccessAuthority(isView ? CtrlCommonConstant.AUTHORITY_COMMON : CtrlCommonConstant.AUTHORITY_READ_ONLY);
		this.chkShowAll.setEnabled(isView);
		this.chkShowAll.setEditable(isView);
		isLoadField = false;
		
		
		//initOldData(editData);
	}
	
    
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		this.btnAdd.setEnabled(true);
		this.btnDelete.setEnabled(true);
//		this.chkShowAll.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.chkShowAll.setSelected(false);
		this.chkShowAll.setEnabled(false);
		this.chkShowAll.setEditable(false);
		loadLinkMan((FDCOrgCustomerInfo) this.editData);
	}
	
	protected void tblLinkman_editStopped(KDTEditEvent e) throws Exception {
    	int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		IColumn col = tblLinkman.getColumn(colIndex);
		IRow row = tblLinkman.getRow(rowIndex);
		ICell isAssociation = this.tblLinkman.getRow(rowIndex).getCell("isAssociation");
		Boolean isSel = (Boolean) isAssociation.getValue();
		
		Object assTypeObj =row.getCell("associationType").getValue();
		AssociationTypeEnum assType = null;
		if(assTypeObj != null){
			if(assTypeObj instanceof AssociationTypeEnum){
				assType = (AssociationTypeEnum)assTypeObj;
			}else if(assTypeObj instanceof String){
				if ("系统关联".equals(assTypeObj)) {
					assType =(AssociationTypeEnum) AssociationTypeEnum.SYSTEM;
				}else if("手工关联".equals(assTypeObj)){
					assType = (AssociationTypeEnum) AssociationTypeEnum.HAND;
				}else if("".equals(assTypeObj)){
					assType = (AssociationTypeEnum) AssociationTypeEnum.NULL;
				}
			}
		}
		if(AssociationTypeEnum.SYSTEM.equals(assType)){
			this.isLocked(rowIndex, true);
			this.tblLinkman.getRow(rowIndex).getCell("isAssociation").getStyleAttributes().setLocked(true);
			this.tblLinkman.getRow(rowIndex).getCell("isEmergencyContact").getStyleAttributes().setLocked(true);
			this.tblLinkman.getRow(rowIndex).getCell("isPrincipal").getStyleAttributes().setLocked(true);
			this.tblLinkman.getRow(rowIndex).getCell("principalNo").getStyleAttributes().setLocked(true);
			this.tblLinkman.getRow(rowIndex).getCell("relation").getStyleAttributes().setLocked(true);
			this.tblLinkman.getRow(rowIndex).getCell("name").getStyleAttributes().setLocked(true);
			this.tblLinkman.getRow(rowIndex).getCell("number").getStyleAttributes().setLocked(true);
		}else{
		if(isSel != null  &&  isSel.booleanValue()){
			row.getCell("associationType").setValue(AssociationTypeEnum.HAND);
			KDBizPromptBox f7Box = new KDBizPromptBox();
			f7Box.setEditFormat("$number$");
			f7Box.setDisplayFormat("$name$");
			f7Box.setCommitFormat("$number$");
			f7Box.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.F7OrgCustomerQuery");
			
			FilterInfo mainFilter = new FilterInfo();
			FullOrgUnitInfo currentUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
			mainFilter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			mainFilter.getFilterItems().add(new FilterItemInfo("customerType", CustomerTypeEnum.PERSONALCUSTOMER_VALUE));
			mainFilter.getFilterItems().add(new FilterItemInfo("belongUnit.id", currentUnit.getId()));
			
			EntityViewInfo viewInfo = new EntityViewInfo();
			viewInfo.setFilter(mainFilter);
			f7Box.setEntityViewInfo(viewInfo);
			
			KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
			ICell name = this.tblLinkman.getRow(rowIndex).getCell("name");
			//IColumn name = this.tblLinkman.getColumn("name");
			name.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			name.setEditor(f7Editor);
			
			this.isLocked(rowIndex,true);
			
			if(col.getKey().equals("name")){
				Object obj = row.getCell("name").getValue();
				if(obj != null && obj instanceof FDCOrgCustomerInfo){
					FDCOrgCustomerInfo info = (FDCOrgCustomerInfo) obj;
					row.getCell("number").setValue(info.getNumber());
					row.getCell("sex").setValue(info.getSex());
					row.getCell("phone").setValue(info.getPhone());
					
					row.getCell("tel").setValue(info.getTel());
					row.getCell("officeTel").setValue(info.getOfficeTel());
					row.getCell("fax").setValue(info.getFax());
					row.getCell("otherTel").setValue(info.getOtherTel());
					row.getCell("email").setValue(info.getEmail());

					row.getCell("certificateType").setValue(info.getCertificateType());
					row.getCell("certificateNumber").setValue(info.getCertificateNumber());
					row.getCell("dayOfBirth").setValue(null);
					row.getCell("mailAddress").setValue(info.getMailAddress());
					row.getCell("bookedAddress").setValue(info.getBookedAddress());

					row.getCell("postalCode").setValue(info.getPostalCode());
					row.getCell("description").setValue(info.getDescription());
				}
			}
		}else{
			row.getCell("associationType").setValue(AssociationTypeEnum.NULL);
			ICell name = this.tblLinkman.getRow(rowIndex).getCell("name");
			name.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			name.setEditor(createTxtCellEditor(80, true));
			this.isLocked(rowIndex,false);
		}
		}
    }
	
	private void isLocked(int rowIndex,boolean isLocked){
		this.tblLinkman.getRow(rowIndex).getCell("number").getStyleAttributes().setLocked(isLocked);
		this.tblLinkman.getRow(rowIndex).getCell("sex").getStyleAttributes().setLocked(isLocked);
		this.tblLinkman.getRow(rowIndex).getCell("phone").getStyleAttributes().setLocked(isLocked);
		this.tblLinkman.getRow(rowIndex).getCell("tel").getStyleAttributes().setLocked(isLocked);
		this.tblLinkman.getRow(rowIndex).getCell("officeTel").getStyleAttributes().setLocked(isLocked);
		this.tblLinkman.getRow(rowIndex).getCell("fax").getStyleAttributes().setLocked(isLocked);
		this.tblLinkman.getRow(rowIndex).getCell("otherTel").getStyleAttributes().setLocked(isLocked);
		this.tblLinkman.getRow(rowIndex).getCell("email").getStyleAttributes().setLocked(isLocked);
		this.tblLinkman.getRow(rowIndex).getCell("certificateType").getStyleAttributes().setLocked(isLocked);
		this.tblLinkman.getRow(rowIndex).getCell("certificateNumber").getStyleAttributes().setLocked(isLocked);
		this.tblLinkman.getRow(rowIndex).getCell("dayOfBirth").getStyleAttributes().setLocked(isLocked);
		this.tblLinkman.getRow(rowIndex).getCell("mailAddress").getStyleAttributes().setLocked(isLocked);
		this.tblLinkman.getRow(rowIndex).getCell("bookedAddress").getStyleAttributes().setLocked(isLocked);
		this.tblLinkman.getRow(rowIndex).getCell("postalCode").getStyleAttributes().setLocked(isLocked);
		this.tblLinkman.getRow(rowIndex).getCell("description").getStyleAttributes().setLocked(isLocked);
	}
	
	private void addLinkManRow(IRow row, OrgCustomerLinkManInfo linkManInfo) {
		row.setUserObject(linkManInfo);
		row.getCell("isAssociation").setValue(new Boolean(linkManInfo.isIsAssociation()));
		row.getCell("associationType").setValue(linkManInfo.getAssociationType());
		row.getCell("isEmergencyContact").setValue(new Boolean(linkManInfo.isIsEmergencyContact()));
		row.getCell("isPrincipal").setValue(new Boolean(linkManInfo.isIsPrincipal()));
		row.getCell("principalNo").setValue(linkManInfo.getPrincipalNo());

		row.getCell("number").setValue(linkManInfo.getNumber());
		row.getCell("name").setValue(linkManInfo.getName());
		row.getCell("relation").setValue(linkManInfo.getRelation());
		row.getCell("sex").setValue(linkManInfo.getSex());
		row.getCell("phone").setValue(linkManInfo.getPhone());

		row.getCell("tel").setValue(linkManInfo.getTel());
		row.getCell("officeTel").setValue(linkManInfo.getOfficeTel());
		row.getCell("fax").setValue(linkManInfo.getFax());
		row.getCell("otherTel").setValue(linkManInfo.getOtherTel());
		row.getCell("email").setValue(linkManInfo.getEmail());

		row.getCell("certificateType").setValue(linkManInfo.getCertificateType());
		row.getCell("certificateNumber").setValue(linkManInfo.getCertificateNumber());
		
		if(linkManInfo.getDayOfBirth() != null){
			row.getCell("dayOfBirth").setValue(FDCDateHelper.stringToDate(linkManInfo.getDayOfBirth()));
		}else{
			row.getCell("dayOfBirth").setValue(null);
		}
		
		row.getCell("mailAddress").setValue(linkManInfo.getMailAddress());
		row.getCell("bookedAddress").setValue(linkManInfo.getBookedAddress());

		row.getCell("postalCode").setValue(linkManInfo.getPostalCode());
		row.getCell("description").setValue(linkManInfo.getDescription());
	}
	
	private void loadLinkMan(FDCOrgCustomerInfo cus) {
		this.tblLinkman.checkParsed();
		this.tblLinkman.removeRows(false);
		OrgCustomerLinkManCollection linkmans = cus.getLinkMan();
		for (int i = 0; i < linkmans.size(); i++) {
			OrgCustomerLinkManInfo linkManInfo = linkmans.get(i);
			IRow row = tblLinkman.addRow();
			addLinkManRow(row, linkManInfo);
			
			if(AssociationTypeEnum.SYSTEM.equals(row.getCell("associationType").getValue())){
					this.isLocked(i, true);
					this.tblLinkman.getRow(i).getCell("isAssociation").getStyleAttributes().setLocked(true);
					this.tblLinkman.getRow(i).getCell("isEmergencyContact").getStyleAttributes().setLocked(true);
					this.tblLinkman.getRow(i).getCell("isPrincipal").getStyleAttributes().setLocked(true);
					this.tblLinkman.getRow(i).getCell("principalNo").getStyleAttributes().setLocked(true);
					this.tblLinkman.getRow(i).getCell("relation").getStyleAttributes().setLocked(true);
					this.tblLinkman.getRow(i).getCell("name").getStyleAttributes().setLocked(true);
					this.tblLinkman.getRow(i).getCell("number").getStyleAttributes().setLocked(true);
				}else{
			ICell isAssociation = this.tblLinkman.getRow(i).getCell("isAssociation");
			Boolean isSel = (Boolean) isAssociation.getValue();
			
			if(isSel != null  &&  isSel.booleanValue()){
				KDBizPromptBox f7Box = new KDBizPromptBox();
				f7Box.setEditFormat("$number$");
				f7Box.setDisplayFormat("$name$");
				f7Box.setCommitFormat("$number$");
				f7Box.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCOrgCustomerQuery");
				
				FilterInfo mainFilter = new FilterInfo();
				FullOrgUnitInfo currentUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
				mainFilter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
				mainFilter.getFilterItems().add(new FilterItemInfo("customerType", CustomerTypeEnum.PERSONALCUSTOMER_VALUE));
				mainFilter.getFilterItems().add(new FilterItemInfo("belongUnit.id", currentUnit.getId()));
				
				EntityViewInfo viewInfo = new EntityViewInfo();
				viewInfo.setFilter(mainFilter);
				f7Box.setEntityViewInfo(viewInfo);
				
				KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
				ICell name = this.tblLinkman.getRow(i).getCell("name");
				name.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
				name.setEditor(f7Editor);
				this.isLocked(i,true);
			}else{
				if(AssociationTypeEnum.SYSTEM.equals(row.getCell("associationType").getValue())){
					row.getCell("associationType").setValue(AssociationTypeEnum.SYSTEM);
				}else{
					row.getCell("associationType").setValue(AssociationTypeEnum.NULL);
				}
				
				ICell name = this.tblLinkman.getRow(i).getCell("name");
				name.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
				name.setEditor(createTxtCellEditor(80, true));
				this.isLocked(i,false);
			}
		}
		}
	}
	
	
	private void loadSHEInfo() {
		// TODO Auto-generated method stub
		
	}

	private void loadQuestions() {
		this.tblQuestion.checkParsed();
		this.tblQuestion.removeRows(false);

		FDCOrgCustomerInfo orgCus = (FDCOrgCustomerInfo) this.editData;
		if (orgCus.getId() == null || orgCus.getMainCustomer() == null) {
			return;
		}

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("orgUnit.name");
		sic.add("sellProject.name");
		sic.add("questionPaper.bizScene");// 业务场景
		sic.add("inputDate");
		sic.add("number");
		sic.add("questionPaper.topric");
		sic.add("bizDate");
		sic.add("creator.name");
		sic.add("createTime");
		sic.add("lastUpdateUser.name");
		sic.add("lastUpdateTime");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("sheCustomer.mainCustomer.id", orgCus.getMainCustomer().getId().toString()));
		view.setSelector(sic);
		view.setFilter(filter);

		QuestionPaperAnswerCollection coll = null;
		try {
			coll = QuestionPaperAnswerFactory.getRemoteInstance().getQuestionPaperAnswerCollection(view);
		} catch (BOSException e) {
			this.handleException(e);
			this.abort();
		}

		tblQuestion.getColumn("fillInDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		tblQuestion.getColumn("bizDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		tblQuestion.getColumn("createTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		tblQuestion.getColumn("lastUpdateDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");

		for (int i = 0; i < coll.size(); i++) {
			QuestionPaperAnswerInfo info = coll.get(i);
			tblQuestion.checkParsed();
			tblQuestion.setEditable(false);
			tblQuestion.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
			IRow addRow = tblQuestion.addRow();

			if (info.getOrgUnit() != null) {
				addRow.getCell("org").setValue(info.getOrgUnit().getName());
			}
			if (info.getSellProject() != null) {
				addRow.getCell("project").setValue(info.getSellProject().getName());
			}
			addRow.getCell("fillInDate").setValue(info.getInputDate());
			addRow.getCell("number").setValue(info.getNumber());
			if (info.getQuestionPaper() != null) {
				addRow.getCell("bizScene").setValue(info.getQuestionPaper().getBizScene());
			}
//			if (info.getQuestionPaper() != null) {
//				addRow.getCell("topric").setValue(info.getQuestionPaper().getTopric());
//			}

			addRow.getCell("bizDate").setValue(info.getBizDate());
			if (info.getCreator() != null) {
				addRow.getCell("creaotr").setValue(info.getCreator().getName());
			}

			if (info.getLastUpdateUser() != null) {
				addRow.getCell("lastUpdateUser").setValue(info.getLastUpdateUser().getName());
			}

			addRow.getCell("createTime").setValue(info.getCreateTime());
			addRow.getCell("lastUpdateDate").setValue(info.getLastUpdateTime());
		}
	}

	private void loadTrackRecord() {
		this.tblTrackRecord.checkParsed();
		this.tblTrackRecord.removeRows(false);

		FDCOrgCustomerInfo orgCus = (FDCOrgCustomerInfo) this.editData;
		if (orgCus.getId() == null || orgCus.getMainCustomer() == null) {
			return;
		}

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		FilterInfo filter = new FilterInfo();
		sic.add("name");
		sic.add("number");
		sic.add("trackDate");
		sic.add("trackEvent");
		sic.add("interactionType");
		sic.add("trackResult");
		sic.add("description");
		sic.add("creator.name");
		sic.add("createTime");
		sic.add("saleMan.name");

		filter.getFilterItems().add(
				new FilterItemInfo("commerceChance.customer.mainCustomer.id", orgCus.getMainCustomer().getId()
						.toString()));
		view.setSelector(sic);
		view.setFilter(filter);

		CommerceChanceTrackCollection trackColl = null;
		try {
			trackColl = CommerceChanceTrackFactory.getRemoteInstance().getCommerceChanceTrackCollection(view);
		} catch (BOSException e) {
			this.handleException(e);
			this.abort();
		}

		tblTrackRecord.getColumn("date").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		tblTrackRecord.getColumn("createTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		for (int j = 0; j < trackColl.size(); j++) {
			CommerceChanceTrackInfo trackInfo = trackColl.get(j);
			IRow addRow = tblTrackRecord.addRow();

			addRow.getCell("number").setValue(trackInfo.getNumber());
			addRow.getCell("date").setValue(trackInfo.getTrackDate());
			addRow.getCell("event").setValue(trackInfo.getTrackEvent());
			addRow.getCell("type").setValue(trackInfo.getInteractionType());
			addRow.getCell("step").setValue(trackInfo.getCommerceChanceStage());
			addRow.getCell("description").setValue(trackInfo.getDescription());
			addRow.getCell("saleman").setValue(trackInfo.getSaleMan() == null ? "" : trackInfo.getSaleMan().getName());
			if (trackInfo.getCreator() != null) {
				addRow.getCell("creator").setValue(trackInfo.getCreator().getName());
			}
			addRow.getCell("createTime").setValue(trackInfo.getCreateTime());
		}
	}

	private void loadCommerce() {
		this.tblCommerce.checkParsed();
		this.tblCommerce.removeRows(false);
		FDCOrgCustomerInfo orgCus = (FDCOrgCustomerInfo) this.editData;
		if (orgCus.getId() == null || orgCus.getMainCustomer() == null) {
			return;
		}

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("sysType");
		sic.add("orgUnit.name");
		sic.add("sellProject.id");
		sic.add("sellProject.name");
		sic.add("status");
		sic.add("number");
		sic.add("name");
		sic.add("commerceLevel.name");
		sic.add("commerceChanceStage.name");
		sic.add("effectiveDate");
		sic.add("commerceSrc");
		sic.add("worth");
		sic.add("probability");
		sic.add("saleMan.name");
		sic.add("creator.name");
		sic.add("createtime");
		sic.add("description");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("customer.mainCustomer.id", orgCus.getMainCustomer().getId().toString()));
		view.setSelector(sic);
		view.setFilter(filter);

		CommerceChanceCollection coll = null;
		try {
			coll = CommerceChanceFactory.getRemoteInstance().getCommerceChanceCollection(view);
		} catch (BOSException e) {
			this.handleException(e);
			this.abort();
		}
		for (int i = 0; i < coll.size(); i++) {
			CommerceChanceInfo commerceChanceInfo = coll.get(i);
			IRow addRow = tblCommerce.addRow();

			addRow.getCell("sys").setValue(commerceChanceInfo.getSysType());
			if (commerceChanceInfo.getOrgUnit() != null) {
				addRow.getCell("org").setValue(commerceChanceInfo.getOrgUnit().getName());
			} 
			if (commerceChanceInfo.getSellProject() != null) {
				addRow.getCell("project").setValue(commerceChanceInfo.getSellProject().getName());
			}

			addRow.getCell("state").setValue(commerceChanceInfo.getStatus());
			addRow.getCell("number").setValue(commerceChanceInfo.getNumber());
			addRow.getCell("name").setValue(commerceChanceInfo.getName());
			if (commerceChanceInfo.getCommerceLevel() != null) {
				addRow.getCell("level").setValue(commerceChanceInfo.getCommerceLevel().getName());
			}
			if (commerceChanceInfo.getCommerceChanceStage() != null) {
				addRow.getCell("step").setValue(
						commerceChanceInfo.getCommerceChanceStage().getName());
			}
			addRow.getCell("validDate").setValue(commerceChanceInfo.getEffectiveDate());
			addRow.getCell("source").setValue(commerceChanceInfo.getCommerceSrc());
			addRow.getCell("preValue").setValue(commerceChanceInfo.getWorth());

			addRow.getCell("dealProbability").setValue(commerceChanceInfo.getProbability());
			if (commerceChanceInfo.getSaleMan() != null) {
				addRow.getCell("curBelong").setValue(commerceChanceInfo.getSaleMan().getName());
			}
			if (commerceChanceInfo.getCreator() != null) {
				addRow.getCell("creator").setValue(commerceChanceInfo.getCreator().getName());
			} 

			addRow.getCell("createTime").setValue(commerceChanceInfo.getCreateTime());

			addRow.getCell("description").setValue(commerceChanceInfo.getDescription());

			addRow.getCell("validDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
			addRow.getCell("createTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		}
	}

	private void loadRooms() {
		this.tblRoomInfo.checkParsed();
		this.tblRoomInfo.removeRows(false);
		if(!getOprtState().equals("VIEW")){
			return;
		}
		
		FDCOrgCustomerInfo orgCus = (FDCOrgCustomerInfo) this.editData;
		if(orgCus.getMainCustomer() == null){
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("head.id");
		sels.add("head.bulidingArea");
		sels.add("head.roomArea");
		sels.add("head.bizDate");
		sels.add("head.room.name");
		sels.add("head.room.building.name");
		sels.add("head.sellProject.name");
		view.setSelector(sels);
		
		FilterInfo filter = new FilterInfo();
		Set set = new HashSet();
		set.add(TransactionStateEnum.PREAPPLE_VALUE);
		set.add(TransactionStateEnum.PREAUDIT_VALUE);
		set.add(TransactionStateEnum.CHANGENAMEAUDITING_VALUE);
		set.add(TransactionStateEnum.QUITROOMAUDITING_VALUE);
		set.add(TransactionStateEnum.CHANGEROOMAUDITING_VALUE);
		set.add(TransactionStateEnum.CHANGEPIRCEAUDITING_VALUE);
		set.add(TransactionStateEnum.PURAPPLE_VALUE);
		set.add(TransactionStateEnum.PURAUDIT_VALUE);
		set.add(TransactionStateEnum.SIGNAPPLE_VALUE);
		set.add(TransactionStateEnum.SIGNAUDIT_VALUE);
		
		filter.getFilterItems().add(new FilterItemInfo("head.bizState", set, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("customer.mainCustomer.id", orgCus.getMainCustomer().getId().toString()));
		
		view.setFilter(filter);
		
		try {
			IObjectCollection preCol = PrePurchaseCustomerEntryFactory.getRemoteInstance().getPrePurchaseCustomerEntryCollection(view);
			IObjectCollection purCol = PurCustomerEntryFactory.getRemoteInstance().getPurCustomerEntryCollection(view);
			IObjectCollection signCol = SignCustomerEntryFactory.getRemoteInstance().getSignCustomerEntryCollection(view);
			
			addRoomInfoRecord(signCol, true);
			addRoomInfoRecord(purCol, false);
			addRoomInfoRecord(preCol, false);
			
		} catch (BOSException e) {
			this.handleException(e);
			this.abort();
		}
	}

	/**
	 * @param preCol
	 */
	private void addRoomInfoRecord(IObjectCollection col, boolean isSign) {
		for(int i=0; i<col.size(); i++){
			IObjectValue obj = col.getObject(i);
			
			BaseTransactionInfo info = (BaseTransactionInfo) obj.get("head");
			if(info == null){
				continue;
			}
			IRow row = this.tblRoomInfo.addRow();
			
			row.getCell("sys").setValue("售楼");
			row.getCell("type").setValue("业主");
			row.getCell("state").setValue("当前");
			row.getCell("project").setValue(CRMHelper.getValue(info, "sellProject.name"));
			row.getCell("building").setValue(CRMHelper.getValue(info, "room.building.name"));
			
			row.getCell("room").setValue(CRMHelper.getValue(info, "room.name"));
			row.getCell("buildArea").setValue(CRMHelper.getValue(info, "bulidingArea"));
			row.getCell("roomArea").setValue(CRMHelper.getValue(info, "roomArea"));
			row.getCell("tenancyArea").setValue("");
			row.getCell("feeArea").setValue("");
			
			row.getCell("isFeeObj").setValue("");
			if(isSign)row.getCell("signDate").setValue(CRMHelper.getValue(info, "bizDate"));
			row.getCell("tenancyBeginDate").setValue("");
			row.getCell("tenancyEndDate").setValue("");
			row.getCell("joinDate").setValue("");
			
			row.getCell("outDate").setValue("");
			row.getCell("feeBeginDate").setValue("");
			row.getCell("feeEndDate").setValue("");
		}
	}

	protected void txtCertificateNumber_focusLost(FocusEvent e) throws Exception {
		if(isLoadField)return;		
		customerNumberChange();
	}
	
	/**
	 * 根据身份证带出出生日期和性别
	 * @throws Exception
	 */
	private void customerNumberChange() throws Exception{
		String cerNum = this.txtCertificateNumber.getText();
		if(cerNum != null && cerNum.length() != 0){
			cerNum = cerNum.trim();
		}
		if(!CustomerTypeEnum.PERSONALCUSTOMER.equals(this.comboCustomerType.getSelectedItem())){
			return;
		}
		FDCCusBaseDataInfo cerType = (FDCCusBaseDataInfo) this.f7Certificate.getValue();
		if(cerType == null){
			return;
		}
		
		if(!"身份证".equals(cerType.getName())){
			return;
		}
		
		if(cerNum == null  ||  (cerNum.length()!=15 && cerNum.length()!=18)){
			return;
		}			
		
		if(cerNum.length() == 15 && IDCard.IDCardValidate(cerNum).equals("")){
			String id14 = cerNum.substring(14, 15);
			if (Integer.parseInt(id14) % 2 != 0) {
				this.comboSex.setSelectedItem(SexEnum.Mankind);
			} else {
				this.comboSex.setSelectedItem(SexEnum.Womenfolk);
			}
			// 获取出生日期
			String birthday = "19"+cerNum.substring(6, 12);
			Date birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
			this.dpDayOfbirth.setValue(birthdate);
		}
		//合法的才
		if (cerNum.length()==18 && IDCard.IDCardValidate(cerNum).equals("")) {
			String id17 = cerNum.substring(16, 17);
			if (Integer.parseInt(id17) % 2 != 0) {
				this.comboSex.setSelectedItem(SexEnum.Mankind);
			} else {
				this.comboSex.setSelectedItem(SexEnum.Womenfolk);
			}
			
			// 获取出生日期
			String birthday = cerNum.substring(6, 14);
			Date birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
			this.dpDayOfbirth.setValue(birthdate);
		}
	}
	
	private void loadDistributing() {
		this.tblDistributing.checkParsed();
		this.tblDistributing.removeRows(false);
//		if(!getOprtState().equals("VIEW")){
//			return;
//		}
		
		FDCOrgCustomerInfo orgCus = (FDCOrgCustomerInfo) this.editData;
		if(orgCus.getMainCustomer() == null){
			return;
		}
		
		String mainCusId = orgCus.getMainCustomer().getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("createUnit.name");
		sels.add("createUnit.id");
		sels.add("sellProject.name");
		sels.add("identity.name");
		sels.add("creator.name");
		sels.add("lastUpdateUser.name");
		view.setSelector(sels);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("mainCustomer.id", mainCusId));
		view.setFilter(filter);
		
		Set org=new HashSet();
		try {
			SHECustomerCollection sheCuses = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
			
			for(int i=0; i<sheCuses.size(); i++){
				SHECustomerInfo sci = sheCuses.get(i);
				IRow row = this.tblDistributing.addRow();
				row.getCell("org").setValue(CRMHelper.getValue(sci, "createUnit.name"));
				org.add(sci.getCreateUnit().getId());
				row.getCell("project").setValue(CRMHelper.getValue(sci, "sellProject.name"));
				row.getCell("cusNumber").setValue(CRMHelper.getValue(sci, "number"));
				row.getCell("cusName").setValue(CRMHelper.getValue(sci, "name"));
				row.getCell("cusType").setValue(sci.getCustomerType());
				row.getCell("firstLinkman").setValue(CRMHelper.getValue(sci, "firstLinkman"));
				row.getCell("phone").setValue(CRMHelper.getValue(sci, "phone"));
				row.getCell("birthday").setValue(CRMHelper.getValue(sci, "dayOfbirth"));
				row.getCell("sex").setValue(sci.getSex());
				row.getCell("cusStatus").setValue(CRMHelper.getValue(sci, "identity.name"));
				row.getCell("mainAddr").setValue(CRMHelper.getValue(sci, "mailAddress"));
				row.getCell("certificateAddr").setValue(CRMHelper.getValue(sci, "bookedAddress"));
				row.getCell("creator").setValue(CRMHelper.getValue(sci, "creator.name"));
				row.getCell("createTime").setValue(CRMHelper.getValue(sci, "createTime"));
				row.getCell("lastUpdater").setValue(CRMHelper.getValue(sci, "lastUpdateUser.name"));
				row.getCell("lastUpdateTime").setValue(CRMHelper.getValue(sci, "lastUpdateTime"));
			}
			
			sels.add("belongUnit.name");
			filter.getFilterItems().add(new FilterItemInfo("belongUnit.longNumber", orgCus.getBelongUnit().getLongNumber() + "%", CompareType.LIKE));
			filter.getFilterItems().add(new FilterItemInfo("belongUnit.id",org,CompareType.NOTINCLUDE));
			FDCOrgCustomerCollection orgCuses = FDCOrgCustomerFactory.getRemoteInstance().getFDCOrgCustomerCollection(view);
			for(int i=0; i<orgCuses.size(); i++){
				FDCBaseCustomerInfo cus = orgCuses.get(i);
				IRow row = this.tblDistributing.addRow();
				row.getCell("org").setValue(CRMHelper.getValue(cus, "belongUnit.name"));
				row.getCell("project").setValue(CRMHelper.getValue(cus, "sellProject.name"));
				row.getCell("cusNumber").setValue(CRMHelper.getValue(cus, "number"));
				row.getCell("cusName").setValue(CRMHelper.getValue(cus, "name"));
				row.getCell("cusType").setValue(cus.getCustomerType());
				row.getCell("firstLinkman").setValue(CRMHelper.getValue(cus, "firstLinkman"));
				row.getCell("phone").setValue(CRMHelper.getValue(cus, "phone"));
				row.getCell("birthday").setValue(CRMHelper.getValue(cus, "dayOfbirth"));
				row.getCell("sex").setValue(cus.getSex());
				row.getCell("cusStatus").setValue(CRMHelper.getValue(cus, "identity.name"));
				row.getCell("mainAddr").setValue(CRMHelper.getValue(cus, "mailAddress"));
				row.getCell("certificateAddr").setValue(CRMHelper.getValue(cus, "bookedAddress"));
				row.getCell("creator").setValue(CRMHelper.getValue(cus, "creator.name"));
				row.getCell("createTime").setValue(CRMHelper.getValue(cus, "createTime"));
				row.getCell("lastUpdater").setValue(CRMHelper.getValue(cus, "lastUpdateUser.name"));
				row.getCell("lastUpdateTime").setValue(CRMHelper.getValue(cus, "lastUpdateTime"));
			}
			
		} catch (BOSException e) {
			this.handleException(e);
			this.abort();
		}
		
	}
	
	public void actionChangeCusName_actionPerformed(ActionEvent e) throws Exception {
		super.actionChangeCusName_actionPerformed(e);
		UIContext uiContext = new UIContext(this);
		uiContext.put("srcCus", this.editData);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ChangeCusNameUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
		
		Map result = uiWindow.getUIObject().getUIContext();
		
		if(result.get("continue") == null  ||  !((Boolean)result.get("continue")).booleanValue()){
			return;
		}
		
		String newName = (String) result.get("newName");
		FDCOrgCustomerFactory.getRemoteInstance().changeCusName(this.editData.getId().toString(), newName);
		
		MsgBox.showInfo(this, "操作成功！");
		this.txtName.setText(newName);
	}
	
	public void actionUpdateCus_actionPerformed(ActionEvent e) throws Exception {
		List list = new ArrayList();
		list.add(this.editData.getId().toString());
		FDCOrgCustomerFactory.getRemoteInstance().updateCustomerInfo(list);
		MsgBox.showInfo(this, "操作成功！");
	}
	
	private void loadChangeLog() {
		this.tblChangeLog.checkParsed();
		this.tblChangeLog.removeRows(false);
		if (this.editData.getId() == null) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("creator.name");
		sels.add("orgUnit.name");
		view.setSelector(sels);
		filter.getFilterItems().add(new FilterItemInfo("orgCustomer.id", this.editData.getId().toString()));
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("createTime"));
		
		try {
			CustomerChangeLogCollection logs = CustomerChangeLogFactory.getRemoteInstance()
					.getCustomerChangeLogCollection(view);
			for (int i = 0; i < logs.size(); i++) {
				CustomerChangeLogInfo info = logs.get(i);

				IRow row = this.tblChangeLog.addRow();
				row.getCell("seq").setValue(Integer.toString(i + 1));
				row.getCell("oldInfo").setValue(info.getOldField());
				row.getCell("newInfo").setValue(info.getNewField());
				row.getCell("changeDate").setValue(info.getCreateTime());
				row.getCell("changer").setValue(info.getCreator() == null ? "" : info.getCreator().getName());
				row.getCell("org").setValue(info.getOrgUnit() == null ? "" : info.getOrgUnit().getName());
			}
		} catch (BOSException e) {
			this.handleException(e);
			this.abort();
		}
		
		// 设置变更记录不能修改，时间格式
		tblChangeLog.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		tblChangeLog.setEditable(false);
		if (tblChangeLog.getColumn("changeDate") != null) {
			tblChangeLog.getColumn("changeDate").getStyleAttributes()
					.setNumberFormat("yyyy-MM-dd HH:mm:ss");
		}

	}

	public void storeFields() {
		super.storeFields();
		
		FDCOrgCustomerInfo cus = (FDCOrgCustomerInfo) this.editData;
		
		//cus.setNumber(this.txtNumber.getText().trim());
		cus.setCustomerType((CustomerTypeEnum) this.comboCustomerType.getSelectedItem());
		cus.setName(this.txtName.getText().trim());
		cus.setBookedPlace((CountryInfo) this.f7Nationality.getValue());
		cus.setCode(this.txtCode.getText().trim());
		
		cus.setSimpleCode(this.txtSimCode.getText().trim());
		cus.setPhone(this.txtPhone.getText().trim());
		cus.setTel(this.txtTel.getText().trim());
		cus.setOfficeTel(this.txtOfficeTel.getText().trim());
		cus.setFax(this.txtFax.getText().trim());
		
		cus.setOtherTel(this.txtOtherTel.getText().trim());
		cus.setEmail(this.txtEmail.getText().trim());
		cus.setCertificateType((FDCCusBaseDataInfo) this.f7Certificate.getValue());
		cus.setCertificateNumber(this.txtCertificateNumber.getText().trim());
		cus.setDayOfbirth(this.dpDayOfbirth.getTimestamp());
		
		cus.setSex((SexEnum) this.comboSex.getSelectedItem());
		cus.setIdentity((FDCCusBaseDataInfo) this.f7CusStatus.getValue());
		cus.setCountry((CountryInfo) this.f7Country.getValue());
		cus.setProvince((ProvinceInfo) this.f7Province.getValue());
		cus.setCity((CityInfo) this.f7City.getValue());
		
		cus.setRegion((RegionInfo) this.f7Area.getValue());
		cus.setMailAddress(this.txtMailAddress.getText().trim());
		cus.setBookedAddress(this.txtCertificateAddr.getText().trim());
		cus.setPostalCode(this.txtPostalcode.getText().trim());
		cus.setFirstLinkman(this.txtFirstLinkman.getText().trim());
		
		cus.setIndustry((IndustryInfo) this.f7Industry.getValue());
		cus.setEnterpriceProperty((FDCCusBaseDataInfo) this.f7EnterpriceProperty.getValue());
		cus.setCorporate(this.txtLegalPerson.getText().trim());
		cus.setCorporateTel(this.txtLegalPersonTel.getText().trim());
		cus.setCustomerOrigin((FDCCusBaseDataInfo) this.f7CustomerOrigin.getValue());
		
		cus.setNativePlace((RegionInfo) this.f7PersonArea.getValue());
		cus.setMaritalStatus((MaritalStatusEnum) this.comboMarriage.getSelectedItem());
		cus.setHabitationStatus((FDCCusBaseDataInfo) this.f7HabitationInfo.getValue());
		cus.setWorkCompany(this.txtWorkCompany.getText().trim());
		cus.setContactPreferences((ContactPreferencesEnum) this.comboFirstLinkType.getSelectedItem());
		
		cus.setDescription(this.txtDescription.getText().trim());
		
		cus.setMotorcycles(this.txtCarCount.getText().trim());
		cus.setEnterpriceSize((FDCCusBaseDataInfo) this.f7EnterpriceSize.getValue());
		cus.setBookedCapital(this.txtCapital.getText().trim());
		cus.setBusinessScope(this.txtDealArea.getText().trim());
		cus.setEmployees(this.txtEmployeeCount.getText().trim());
		
		cus.setTaxRegistrationNoG(this.txtTaxNumG.getText().trim());
		cus.setTaxRegistrationNoD(this.txtTaxNumD.getText().trim());
		cus.setCooperateModel((FDCCusBaseDataInfo) this.f7CooperateMode.getValue());
		
		cus.setCreateUnit((FullOrgUnitInfo) this.prmtCreateUnit
				.getValue());
		cus.setCreator((UserInfo) this.prmtCreator.getValue());
		cus.setCreateTime(this.pkCreateTime.getTimestamp());
		cus.setLastUpdateUnit((FullOrgUnitInfo) this.prmtLastUpdateUnit
						.getValue());
		cus.setLastUpdateUser((UserInfo) this.prmtLastUpdateUser
				.getValue());
		cus.setLastUpdateTime(this.pkLastUpdateTime.getTimestamp());
		cus.setCreateWay((CreateWayEnum) this.comboCreateWay
				.getSelectedItem());
		
//		storeLinkMan(cus);
		
//		firstLinkMan = cus.getFirstLinkman();
//		corporate = cus.getCorporate();
//		corporateTel = cus.getCorporateTel();
	}
	
	private void storeLinkMan(FDCOrgCustomerInfo cus) {
		cus.getLinkMan().clear();
		for (int i = 0; i < this.tblLinkman.getRowCount(); i++) {
			IRow row = this.tblLinkman.getRow(i);
			OrgCustomerLinkManInfo linkMan = new OrgCustomerLinkManInfo();
			if (row.getCell("isAssociation").getValue() == null) {
				linkMan.setIsAssociation(false);
			} else {
				linkMan.setIsAssociation(((Boolean) row.getCell("isAssociation").getValue()).booleanValue());
			}

			if (row.getCell("associationType").getValue() == null) {
				linkMan.setAssociationType(null);
			} else {
				linkMan.setAssociationType((AssociationTypeEnum) row.getCell("associationType").getValue());
			}

			if (row.getCell("isEmergencyContact").getValue() == null) {
				linkMan.setIsEmergencyContact(false);
			} else {
				linkMan.setIsEmergencyContact(((Boolean) row.getCell("isEmergencyContact").getValue()).booleanValue());
			}

			if (row.getCell("isPrincipal").getValue() == null) {
				linkMan.setIsPrincipal(false);
			} else {
				linkMan.setIsPrincipal(((Boolean) row.getCell("isPrincipal").getValue()).booleanValue());
			}

			if (row.getCell("sex").getValue() == null) {
				linkMan.setSex(null);
			} else {
				linkMan.setSex((SexEnum) row.getCell("sex").getValue());
			}

			if (row.getCell("certificateType").getValue() == null) {
				linkMan.setCertificateType(null);
			} else {
				FDCCusBaseDataInfo cerType = (FDCCusBaseDataInfo) row.getCell("certificateType").getValue();
				if(cerType != null  && "身份证".equals(cerType.getName())){
					Object obj = row.getCell("certificateNumber").getValue();
					if(obj != null){
						String cerNum = obj.toString().trim();
						if(cerNum != null && !cerNum.equals("") && (cerNum.length() !=15 && cerNum.length()!=18)){
							MsgBox.showWarning("联系人身份证号码长度必须为15或18位");
							this.abort();
						}
						if(cerNum != null && !cerNum.equals("")){
							try {
								String m = IDCard.IDCardValidate(cerNum);
								if(m != null  &&  m.length() != 0){
									MsgBox.showWarning(m);
									this.abort();	
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
					
				}
				linkMan.setCertificateType((FDCCusBaseDataInfo) row.getCell("certificateType").getValue());
			}

			if (row.getCell("principalNo").getValue() == null) {
				linkMan.setPrincipalNo(null);
			} else {
				linkMan.setPrincipalNo(row.getCell("principalNo").getValue().toString());
			}

			if (row.getCell("number").getValue() == null) {
				MsgBox.showWarning("联系人编码不能为空!");
				this.abort();
			} else {
				linkMan.setNumber(row.getCell("number").getValue().toString());
			}

			if (row.getCell("name").getValue() == null) {
				MsgBox.showWarning("联系人姓名不能为空!");
				this.abort();
			} else {
				linkMan.setName(row.getCell("name").getValue().toString());
			}

			if (row.getCell("relation").getValue() == null) {
				linkMan.setRelation(null);
			} else {
				linkMan.setRelation(row.getCell("relation").getValue().toString());
			}

			if (row.getCell("phone").getValue() == null && row.getCell("tel").getValue() == null
					&& row.getCell("officeTel").getValue() == null && row.getCell("fax").getValue() == null) {
				MsgBox.showWarning("联系人移动电话、住宅电话、办公电话、传真至少录入一个!");
				this.abort();
			} else {
				if (row.getCell("phone").getValue() == null) {
					linkMan.setPhone(null);
				} else {
					linkMan.setPhone(row.getCell("phone").getValue().toString());
				}

				if (row.getCell("tel").getValue() == null) {
					linkMan.setTel(null);
				} else {
					linkMan.setTel(row.getCell("tel").getValue().toString());
				}

				if (row.getCell("officeTel").getValue() == null) {
					linkMan.setOfficeTel(null);
				} else {
					linkMan.setOfficeTel(row.getCell("officeTel").getValue().toString());
				}

				if (row.getCell("fax").getValue() == null) {
					linkMan.setFax(null);
				} else {
					linkMan.setFax(row.getCell("fax").getValue().toString());
				}
			}

			if (row.getCell("otherTel").getValue() == null) {
				linkMan.setOtherTel(null);
			} else {
				linkMan.setOtherTel(row.getCell("otherTel").getValue().toString());
			}

			if (row.getCell("email").getValue() == null) {
				linkMan.setEmail(null);
			} else {
				linkMan.setEmail(row.getCell("email").getValue().toString());
			}

			if (row.getCell("certificateNumber").getValue() == null) {
				linkMan.setCertificateNumber(null);
			} else {
				linkMan.setCertificateNumber(row.getCell("certificateNumber").getValue().toString());
			}

			if (row.getCell("dayOfBirth").getValue() == null) {
				linkMan.setDayOfBirth(null);
			} else {
				linkMan.setDayOfBirth(FDCDateHelper.DateToString((Date)row.getCell("dayOfBirth").getValue()));
			}

			if (row.getCell("bookedAddress").getValue() == null) {
				linkMan.setBookedAddress(null);
			} else {
				linkMan.setBookedAddress(row.getCell("bookedAddress").getValue().toString());
			}
			if (row.getCell("mailAddress").getValue() == null) {
				linkMan.setMailAddress(null);
			} else {
				linkMan.setMailAddress(row.getCell("mailAddress").getValue().toString());
			}
			if (row.getCell("postalCode").getValue() == null) {
				linkMan.setPostalCode(null);
			} else {
				linkMan.setPostalCode(row.getCell("postalCode").getValue().toString());
			}
			if (row.getCell("description").getValue() == null) {
				linkMan.setDescription(null);
			} else {
				linkMan.setDescription(row.getCell("description").getValue().toString());
			}

			cus.getLinkMan().add(linkMan);
		}
	}

	private void initLinkmanTable() {
		this.tblLinkman.checkParsed();
		this.tblLinkman.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		tblLinkman.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		KDCheckBox chkBox = new KDCheckBox();
		ICellEditor checkBox = new KDTDefaultCellEditor(chkBox);
		IColumn isAssociation = this.tblLinkman.getColumn("isAssociation");
		isAssociation.setEditor(checkBox);

		KDComboBox box1 = new KDComboBox();
		box1.addItems(EnumUtils.getEnumList(
				"com.kingdee.eas.fdc.basecrm.AssociationTypeEnum").toArray());
		box1.setSelectedIndex(0);
		KDTDefaultCellEditor sexEditor1 = new KDTDefaultCellEditor(box1);
		IColumn associationType = this.tblLinkman.getColumn("associationType");
		associationType.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.LEFT);
		associationType.setEditor(sexEditor1);
		associationType.setEditor(createTxtCellEditor(80, false));
		
		chkBox = new KDCheckBox();
		ICellEditor checkEmergencyContact = new KDTDefaultCellEditor(chkBox);
		IColumn isEmergencyContact = this.tblLinkman
				.getColumn("isEmergencyContact");
		isEmergencyContact.setEditor(checkEmergencyContact);

		chkBox = new KDCheckBox();
		ICellEditor checkBoxPrincipal = new KDTDefaultCellEditor(chkBox);
		IColumn isPrincipal = this.tblLinkman.getColumn("isPrincipal");
		isPrincipal.setEditor(checkBoxPrincipal);

		IColumn principalNo = this.tblLinkman.getColumn("principalNo");
		principalNo.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.LEFT);
		principalNo.setEditor(createTxtCellEditor(80, true));

		IColumn number = this.tblLinkman.getColumn("number");
		number.getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.LEFT);
		number.setEditor(createTxtCellEditor(80, true));

		IColumn name = this.tblLinkman.getColumn("name");
		name.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		name.setEditor(createTxtCellEditor(80, true));

		IColumn relation = this.tblLinkman.getColumn("relation");
		relation.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.LEFT);
		relation.setEditor(createTxtCellEditor(80, true));

		KDComboBox box = new KDComboBox();
		box.addItems(EnumUtils.getEnumList(
				"com.kingdee.eas.fdc.sellhouse.SexEnum").toArray());
		box.setSelectedIndex(0);
		KDTDefaultCellEditor sexEditor = new KDTDefaultCellEditor(box);
		IColumn sex = this.tblLinkman.getColumn("sex");
		sex.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		sex.setEditor(sexEditor);

		IColumn phone = this.tblLinkman.getColumn("phone");
		phone.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		phone.setEditor(createTxtCellEditor(80, true));

		IColumn tel = this.tblLinkman.getColumn("tel");
		tel.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		tel.setEditor(createTxtCellEditor(80, true));

		IColumn officeTel = this.tblLinkman.getColumn("officeTel");
		officeTel.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.LEFT);
		officeTel.setEditor(createTxtCellEditor(80, true));

		IColumn fax = this.tblLinkman.getColumn("fax");
		fax.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		fax.setEditor(createTxtCellEditor(80, true));

		IColumn otherTel = this.tblLinkman.getColumn("otherTel");
		otherTel.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.LEFT);
		otherTel.setEditor(createTxtCellEditor(80, true));

		IColumn email = this.tblLinkman.getColumn("email");
		email.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		email.setEditor(createTxtCellEditor(80, true));

		KDBizPromptBox f7Box = new KDBizPromptBox();
		f7Box.setEditFormat("$number$");
		f7Box.setDisplayFormat("$name$");
		f7Box.setCommitFormat("$number$");
		f7Box
				.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");
		FilterInfo mainFilter = new FilterInfo();
		mainFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		EntityViewInfo viewInfo = new EntityViewInfo();
		viewInfo.setFilter(mainFilter);
		f7Box.setEntityViewInfo(viewInfo);
		setBaseDataFilter(f7Box, CRMConstants.PE_CERTIFICATE_TYPE_GROUP_ID);

		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		IColumn certificateType = this.tblLinkman.getColumn("certificateType");
		certificateType.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.LEFT);
		certificateType.setEditor(f7Editor);

		IColumn certificateNumber = this.tblLinkman
				.getColumn("certificateNumber");
		certificateNumber.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.LEFT);
		certificateNumber.setEditor(createTxtCellEditor(80, true));

		String formatString = "yyyy-MM-dd";
		IColumn dayOfBirth = this.tblLinkman.getColumn("dayOfBirth");
		dayOfBirth.getStyleAttributes().setNumberFormat(formatString);
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		dayOfBirth.setEditor(dateEditor);
		
//		KDDatePicker pk = new KDDatePicker();
//		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
//		this.tblLinkman.getColumn("dayOfBirth").setEditor(dateEditor);
//		String formatString = "yyyy-MM-dd";
//		this.tblLinkman.getColumn("dayOfBirth").getStyleAttributes()
//				.setNumberFormat(formatString);
		
		
		IColumn mailAddress = this.tblLinkman.getColumn("mailAddress");
		mailAddress.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.LEFT);
		mailAddress.setEditor(createTxtCellEditor(80, true));

		IColumn bookedAddress = this.tblLinkman.getColumn("bookedAddress");
		bookedAddress.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.LEFT);
		bookedAddress.setEditor(createTxtCellEditor(80, true));

		IColumn postalCode = this.tblLinkman.getColumn("postalCode");
		postalCode.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.LEFT);
		postalCode.setEditor(createTxtCellEditor(80, true));

		IColumn description = this.tblLinkman.getColumn("description");
		description.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.LEFT);
		description.setEditor(createTxtCellEditor(80, true));
		
		

	}

	private KDTDefaultCellEditor createTxtCellEditor(int length,
			boolean editable) {
		KDTextField textField = new KDTextField();
		textField.setMaxLength(length);
		textField.setEditable(editable);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		return txtEditor;
	}
	
	protected void chkShowAll_actionPerformed(ActionEvent e) throws Exception {
		boolean showAll = this.chkShowAll.isSelected();
		if(showAll){
			FDCOrgCustomerInfo orgCus = (FDCOrgCustomerInfo) this.editData;
			if(orgCus.getMainCustomer() == null){
				return;
			}
			
			if(orgCus.getBelongUnit() == null  ||  orgCus.getBelongUnit().getLongNumber() == null){
				return;
			}
			
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("linkMan.*");
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("mainCustomer.id", orgCus.getMainCustomer().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("id", orgCus.getId().toString(), CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("belongUnit.longNumber", orgCus.getBelongUnit().getLongNumber() + "%", CompareType.LIKE));
			
			view.setFilter(filter);
			view.setSelector(sel);
			
			FDCOrgCustomerCollection orgCuses = FDCOrgCustomerFactory.getRemoteInstance().getFDCOrgCustomerCollection(view);
			for(int i=0; i<orgCuses.size(); i++){
				FDCOrgCustomerInfo tmp = orgCuses.get(i);
				OrgCustomerLinkManCollection linkmen = tmp.getLinkMan();
				for (int j = 0; j < linkmen.size(); j++) {
					OrgCustomerLinkManInfo linkManInfo = linkmen.get(j);
					IRow row = tblLinkman.addRow();
					addLinkManRow(row, linkManInfo);
				}
			}
		}else{
			loadLinkMan((FDCOrgCustomerInfo) this.editData);
		}
	}
	
	protected void btnDelete_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblLinkman.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			MsgBox.showInfo(this, "请先选中行！");
			return;
		}
		this.tblLinkman.removeRow(activeRowIndex);
	}
	
	protected void btnAdd_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.tblLinkman.addRow();
		OrgCustomerLinkManInfo linkManInfo = new OrgCustomerLinkManInfo();
		linkManInfo.setAssociationType(AssociationTypeEnum.NULL);
//		linkManInfo.setAssociationType(AssociationTypeEnum.HAND);
		linkManInfo.setSex(SexEnum.Mankind);
//		linkManInfo.set
		addLinkManRow(row, linkManInfo);
	}
	
	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = this.editData.getId().toString();
		List ids = new ArrayList();
		ids.add(id);
		if(CusClientHelper.beforeRemove(this,ids)){
			super.actionRemove_actionPerformed(e);
		}
	}
	
	protected void initOldData(IObjectValue dataObject) {
		if(!STATUS_ADDNEW.equals(getOprtState())){
			if (!this.isOld) {
				super.initOldData(dataObject);
			}
		}else{
			super.initOldData(dataObject);
		}
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		this.isOld = true;
		super.actionSubmit_actionPerformed(e);
		//暂时不做这个功能
	//	submitEnterpriceCustomer();
	}
	
	private void submitEnterpriceCustomer() throws Exception {
		if(CustomerTypeEnum.ENTERPRICECUSTOMER.equals(this.comboCustomerType.getSelectedItem())){
			FDCOrgCustomerInfo info = (FDCOrgCustomerInfo)this.editData;
			String name = this.txtFirstLinkman.getText();
			String legalPerson = this.txtLegalPerson.getText();
			String legalPersonTel = this.txtLegalPersonTel.getText();
			boolean isCorporateChange = false;
			if(!FDCHelper.isEqual(corporate,info.getCorporate())){
				isCorporateChange = true;
			}
			if(confirmFirstLinkMan()){
				FDCOrgCustomerFactory.getRemoteInstance().submitEnterpriceCustomer(info, name, null);
				MsgBox.showInfo("首选联系人客户资料保存成功");
				if(!isCorporateChange){
					this.destroyWindow();
				}else{
					if(legalPerson != null && !legalPerson.equals("")){
						if(confirmLegalPerson()){
							FDCOrgCustomerFactory.getRemoteInstance().submitEnterpriceCustomer(info, legalPerson, legalPersonTel);
							MsgBox.showInfo("企业法人资料保存成功");
							this.destroyWindow();
					}
				}
				}
			}else{
				if(legalPerson != null && !legalPerson.equals("")){
					if(confirmLegalPerson()){
						FDCOrgCustomerFactory.getRemoteInstance().submitEnterpriceCustomer(info, legalPerson, legalPersonTel);
						MsgBox.showInfo("企业法人资料保存成功");
						this.destroyWindow();
				}
			}
			}
			
			if(!FDCHelper.isEqual(corporateTel,info.getCorporateTel())){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				SelectorItemCollection selector = new SelectorItemCollection();
				filter.getFilterItems().add(new FilterItemInfo("orgCustomer.id", info.getId()));
				filter.getFilterItems().add(new FilterItemInfo("name", info.getCorporate()));
				view.setFilter(filter);
				OrgCustomerLinkManCollection linkManColl = OrgCustomerLinkManFactory.getRemoteInstance().getOrgCustomerLinkManCollection(view);
				if(linkManColl != null && linkManColl.size() > 0){
					for(int i=0;i<linkManColl.size();i++){
						OrgCustomerLinkManInfo linkManInfo = linkManColl.get(i);
						selector.add("phone");
						linkManInfo.setPhone(info.getCorporateTel());
						OrgCustomerLinkManFactory.getRemoteInstance().updatePartial(linkManInfo, selector);
					}
				}
			}
		}else{
			//个人客户，看其是否是企业客户的首选联系人或企业法人
			FDCOrgCustomerInfo info = (FDCOrgCustomerInfo)this.editData;
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("name");
			selector.add("number");
			selector.add("relation");
			selector.add("orgCustomer.id");
			selector.add("orgCustomer.number");
			view.setSelector(selector);
			
			filter.getFilterItems().add(new FilterItemInfo("name", info.getName()));
			view.setFilter(filter);
			OrgCustomerLinkManCollection linkColl = OrgCustomerLinkManFactory.getRemoteInstance().getOrgCustomerLinkManCollection(view);
			Set firstLinkIdSet = new HashSet();
			Set corporateIdSet = new HashSet();
			if(linkColl != null && linkColl.size() > 0){
				for(int i = 0; i<linkColl.size();i++){
					OrgCustomerLinkManInfo linkInfo = linkColl.get(i);
					if("企业职员".equals(linkInfo.getRelation())){
						firstLinkIdSet.add(linkInfo.getOrgCustomer().getId());
					}else{
						corporateIdSet.add(linkInfo.getOrgCustomer().getId());
					}
				}
			}
			
			//首选练系人修改时，同步修改企业客户的移动电话、住宅电话
			if(firstLinkIdSet != null && firstLinkIdSet.size() > 0){
				FDCOrgCustomerFactory.getRemoteInstance().updateEnterpriceCustomer(firstLinkIdSet,info.getPhone(),info.getTel(),true);
			}
			
			if(corporateIdSet != null && corporateIdSet.size() > 0){
				FDCOrgCustomerFactory.getRemoteInstance().updateEnterpriceCustomer(corporateIdSet,info.getName(),info.getPhone(),false);
			}
		}
		this.loadFields();
	}
	
	private boolean confirmFirstLinkMan() throws BOSException {
		if("ADDNEW".equals(this.getOprtState())){
			if (MsgBox.isYes(MsgBox.showConfirm2(this, "是否生成首选联系人客户资料？"))) {
				return true;
			}
		}else{
			FDCOrgCustomerInfo info = (FDCOrgCustomerInfo) this.editData;
			if(!FDCHelper.isEqual(firstLinkMan,info.getFirstLinkman())){
				if (MsgBox.isYes(MsgBox.showConfirm2(this, "是否生成新的首选联系人客户资料？"))) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean confirmLegalPerson() throws BOSException {
		if ("ADDNEW".equals(this.getOprtState())) {
			if (MsgBox.isYes(MsgBox.showConfirm2(this, "是否继续生成企业法人资料？"))) {
				return true;
			} else {
				this.destroyWindow();
				//return false;
			}
		} else {
			FDCOrgCustomerInfo info = (FDCOrgCustomerInfo) this.editData;
			if (!FDCHelper.isEqual(corporate, info.getCorporate())) {
				if (MsgBox.isYes(MsgBox.showConfirm2(this, "是否生成新的企业法人资料？"))) {
					return true;
				} else {
					this.destroyWindow();
					//return false;
				}
			}
		}
		return false;
	}
	
	protected void comboCustomerType_itemStateChanged(ItemEvent e) throws Exception {
//		if(isLoadField){
//			return;
//		}
		boolean isPer = CustomerTypeEnum.PERSONALCUSTOMER.equals(this.comboCustomerType.getSelectedItem());
		setComs(isPer);
		this.f7Certificate.setValue(isPer ? defValues.get(CRMConstants.PE_CERTIFICATE_TYPE_GROUP_ID) : defValues.get(CRMConstants.EN_CERTIFICATE_TYPE_GROUP_ID));
	}

	/**
	 * 
	 */
	private void setComs(boolean isPer) {
		this.contNationality.setBoundLabelText(isPer ? "国籍" : "注册地");
		setBaseDataFilter(this.f7Certificate, isPer ? CRMConstants.PE_CERTIFICATE_TYPE_GROUP_ID : CRMConstants.EN_CERTIFICATE_TYPE_GROUP_ID);
		
		this.txtFirstLinkman.setRequired(!isPer);
		
		this.contDayOfbirth.setBoundLabelText(isPer ? "出生日期" : "注册日期");
		this.contSex.setVisible(isPer);
		this.contCusStatus.setVisible(isPer);

		this.lcMailAddress.setBoundLabelText(isPer ? "通信地址" : "公司地址");
		this.lcCertificateAddr.setBoundLabelText(isPer ? "证件地址" : "注册地址");			
		
		//this.contFirstLinkman.setVisible(!isPer);
		//this.contIndustry.setVisible(!isPer);
		//this.contEnterpriceProperty.setVisible(!isPer);
		this.contLegalPerson.setVisible(!isPer);
		this.contLegalPersonTel.setVisible(!isPer);
		
		this.contExtendInfo.setTitle(isPer ? "个人客户" : "企业客户");
		
		this.contCustomerOrigin.setVisible(isPer);
		this.contPersonArea.setVisible(isPer);
		this.contMarriage.setVisible(isPer);
		this.contHabitationInfo.setVisible(isPer);
		this.contWorkCompany.setVisible(isPer);
		this.contFirstLinkType.setVisible(isPer);
		this.contCarCount.setVisible(isPer);
		
		this.contEnterpriceSize.setVisible(!isPer);
		this.contCapital.setVisible(!isPer);
		this.contDealArea.setVisible(!isPer);
		this.contEmployeeCount.setVisible(!isPer);
		this.contTaxNumG.setVisible(!isPer);
		this.contTaxNumD.setVisible(!isPer);
		this.contCooperateMode.setVisible(!isPer);
		
		txtFirstLinkman.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		f7Industry.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.f7EnterpriceProperty.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		txtFirstLinkman.setEnabled(!isPer);
		f7Industry.setEnabled(!isPer );
		f7EnterpriceProperty.setEnabled(!isPer);
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		//FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.comboCustomerType);
		
		//四个电话，至少要填写一个
		if((txtPhone.getText() == null || txtPhone.getText().equals(""))
				&& (txtTel.getText() == null || txtTel.getText().equals(""))
				&& (txtOfficeTel.getText() == null || txtOfficeTel.getText().equals(""))
				&& (txtFax.getText() == null || txtFax.getText().equals(""))){
			MsgBox.showWarning("移动电话、住宅电话、办公电话、传真至少录入一个!");
			this.abort();
		}
		
		if(CusClientHelper.isImportNotNum(txtPhone) ||  CusClientHelper.isImportNotNum(txtTel) || 
				CusClientHelper.isImportNotNum(txtOfficeTel) || CusClientHelper.isImportNotNum(txtFax)){
			MsgBox.showWarning("移动电话、住宅电话、办公电话、传真只能录入数字!");
			this.abort();
		}
		
		if (this.txtPhone.getText() != null
				&& !this.txtPhone.getText().trim().equals("")
				&& this.txtPhone.getText().trim().length() < 7) {
			MsgBox.showInfo(this, "移动电话至少需要输入7位以上!");
			this.abort();
		}

		if(CustomerTypeEnum.PERSONALCUSTOMER.equals(this.comboCustomerType.getSelectedItem())){
			FDCCusBaseDataInfo cerType = (FDCCusBaseDataInfo) this.f7Certificate.getValue();
			if(cerType != null  && "身份证".equals(cerType.getName())){
				String cerNum = this.txtCertificateNumber.getText().trim();
				if(cerNum != null && !cerNum.equals("") && (cerNum.length()!=15 && cerNum.length()!=18)){
					MsgBox.showWarning("客户身份证号码长度必须为15或18位");
					this.abort();
				}
				if(cerNum != null && !cerNum.equals("")){
					String m = IDCard.IDCardValidate(cerNum);
					if(m != null  &&  m.length() != 0){
						MsgBox.showWarning(m);
						this.abort();	
					}
				}
			}
		}
		
		//企业客户，首选联系人必填
		if (CustomerTypeEnum.ENTERPRICECUSTOMER.equals(this.comboCustomerType.getSelectedItem())) {
			FDCClientVerifyHelper.verifyEmpty(this, this.txtFirstLinkman);
		}
		
		if(CusClientHelper.isImportNotNum(txtPostalcode)){
			MsgBox.showWarning("邮政编码只能录入数字!");
			this.abort();
		}
		
		FDCOrgCustomerInfo orgInfo = (FDCOrgCustomerInfo)this.editData;
		beforeOperate(orgInfo);//重名验证
		
		storeLinkMan(orgInfo);
	}
	


	/**
	 * 判断的前置条件
	 * @param cus
	 * @return
	 * @throws BOSException 
	 */
	private FilterInfo getFilter(FDCOrgCustomerInfo cus) throws BOSException{
		FilterInfo filter = new FilterInfo();
		FDCOrgCustomerInfo orgInfo = (FDCOrgCustomerInfo)this.editData;
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("customerType", this.comboCustomerType.getSelectedItem()));
		filter.getFilterItems().add(new FilterItemInfo("belongUnit.id",orgInfo.getBelongUnit().getId()));
		if (editData != null && editData.getId() != null) {
			String id = this.editData.getId().toString();
			filter.getFilterItems().add(
					new FilterItemInfo("id", id,
							CompareType.NOTEQUALS));
//			if(STATUS_ADDNEW.equals(getOprtState())){
//				EntityViewInfo view = new EntityViewInfo();
//				FilterInfo tempFilter = new FilterInfo();
//				tempFilter.getFilterItems().add(
//						new FilterItemInfo("id", id,
//								CompareType.EQUALS));
//				view.setFilter(tempFilter);
//				FDCOrgCustomerCollection coll = FDCOrgCustomerFactory.getRemoteInstance().getFDCOrgCustomerCollection(view);
//				if(coll != null && coll.size() > 0){
//					FDCOrgCustomerInfo info = coll.get(0);
//					String mainCustomerId = info.getMainCustomer().getId().toString();
//					filter.getFilterItems().add(
//							new FilterItemInfo("mainCustomer.id", mainCustomerId,
//									CompareType.EQUALS));
//				}
//			}
		}
		return filter;
	}
	
	/**
	 * 验证名称和电话
	 * @param ctx
	 * @param cus
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void verifyNameAndPhone(FDCOrgCustomerInfo cus) throws BOSException, EASBizException{
		FilterInfo filter = this.getFilter(cus);
		String name = this.txtName.getText().trim();
		String phone = this.txtPhone.getText().trim();
		String tel = this.txtTel.getText().trim();
		String officeTel = this.txtOfficeTel.getText().trim();
		String fax = this.txtFax.getText().trim();
		filter.getFilterItems().add(
				new FilterItemInfo("name", name, CompareType.EQUALS));
		FilterInfo tempFilter = new FilterInfo();
		FilterInfo telFilter = new FilterInfo();
		FilterInfo officeTelFilter = new FilterInfo();
	    FilterInfo faxFilter = new FilterInfo();
		
	    if (phone != null && !phone.equals("")) {
	    	tempFilter  = getPhoneFilter(phone);
		}
	    
		if(tel != null && !tel.equals("")){
			telFilter = getPhoneFilter(tel);
			 if(tempFilter != null){
				 tempFilter.mergeFilter(telFilter, "OR");
			 }
		 }
		 
       if(officeTel != null && !officeTel.equals("")){
    	   officeTelFilter = getPhoneFilter(officeTel);
			 if(tempFilter != null){
				 tempFilter.mergeFilter(officeTelFilter, "OR");
			 }
		}
		 
       if(fax != null && !fax.equals("")){
    	   faxFilter = getPhoneFilter(fax);
			 if(tempFilter != null){
				 tempFilter.mergeFilter(faxFilter, "OR");
			 }
		}
		
		filter.mergeFilter(tempFilter, "AND");
		ICoreBase iface = FDCOrgCustomerFactory.getRemoteInstance();
		if(iface.exists(filter)){
//			if(STATUS_ADDNEW.equals(getOprtState())){
//				parentIsExist(filter);
//			}else{
				MsgBox.showInfo("客户名称、联系方式已经存在！不能重复");
				SysUtil.abort();
//			}
		}
	}
	
	private FilterInfo getPhoneFilter(String phone) throws BOSException{
		FilterInfo tempFilter = new FilterInfo();
		tempFilter.getFilterItems().add(
				new FilterItemInfo("phone", phone,
						CompareType.EQUALS));
		tempFilter.getFilterItems().add(
				new FilterItemInfo("tel", phone,
						CompareType.EQUALS));
		tempFilter.getFilterItems().add(
				new FilterItemInfo("officeTel", phone,
						CompareType.EQUALS));
		tempFilter.getFilterItems().add(
				new FilterItemInfo("fax", phone,
						CompareType.EQUALS));
		
		tempFilter.setMaskString(" #0 or #1 or #2 or #3");
		return tempFilter;
	}
	
	/**
	 * 验证名称和证件号码
	 * @param ctx
	 * @param cus
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void verifyNameAndCertificateNum(FDCOrgCustomerInfo cus) throws BOSException, EASBizException{
		FilterInfo filter = this.getFilter(cus);
		String name = this.txtName.getText().trim();
		String certificateNumber = this.txtCertificateNumber.getText().trim();
		filter.getFilterItems().add(
				new FilterItemInfo("name", name));
		if (certificateNumber != null && !"".equals(certificateNumber)) {
			filter.getFilterItems().add(
					new FilterItemInfo("certificateNumber",certificateNumber));
			ICoreBase iface = FDCOrgCustomerFactory.getRemoteInstance();
			if(iface.exists(filter)){
				throw new EASBizException(new NumericExceptionSubItem("100","客户名称、证件号码已经存在！不能重复" ));
			}
		}
	} 
	
	/**
	 * 验证电话和证件号码
	 * @param ctx
	 * @param cus
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void verifyPhoneAndCertificateNum(FDCOrgCustomerInfo cus) throws BOSException, EASBizException{
		FilterInfo filter = this.getFilter(cus);
		String phone = this.txtPhone.getText().trim();
		String tel = this.txtTel.getText().trim();
		String officeTel = this.txtOfficeTel.getText().trim();
		String fax = this.txtFax.getText().trim();
		String certificateNumber = this.txtCertificateNumber.getText().trim();
		FilterInfo tempFilter = new FilterInfo();
		FilterInfo telFilter = new FilterInfo();
		FilterInfo officeTelFilter = new FilterInfo();
	    FilterInfo faxFilter = new FilterInfo();
		
	    if (certificateNumber != null && !"".equals(certificateNumber)) {
			filter.getFilterItems().add(
					new FilterItemInfo("certificateNumber",certificateNumber));
			
			 if (phone != null && !phone.equals("")) {
			    	tempFilter  = getPhoneFilter(phone);
				}
			    
				if(tel != null && !tel.equals("")){
					telFilter = getPhoneFilter(tel);
					 if(tempFilter != null){
						 tempFilter.mergeFilter(telFilter, "OR");
					 }
				 }
				 
		       if(officeTel != null && !officeTel.equals("")){
		    	   officeTelFilter = getPhoneFilter(officeTel);
					 if(tempFilter != null){
						 tempFilter.mergeFilter(officeTelFilter, "OR");
					 }
				}
				 
		       if(fax != null && !fax.equals("")){
		    	   faxFilter = getPhoneFilter(fax);
					 if(tempFilter != null){
						 tempFilter.mergeFilter(faxFilter, "OR");
					 }
				}
				
			filter.mergeFilter(tempFilter, "AND");
			ICoreBase iface = FDCOrgCustomerFactory.getRemoteInstance();
			if(iface.exists(filter)){
				throw new EASBizException(new NumericExceptionSubItem("100","联系方式、证件号码已经存在！不能重复" ));
			}
		}
	} 
	
	/**
	 * 验证
	 * @param ctx
	 * @param cus
	 * @throws BOSException
	 * @throws EASBizException
	 */
	 private void beforeOperate(FDCOrgCustomerInfo cus)throws BOSException, EASBizException{
		 this.verifyNameAndPhone(cus);
		 this.verifyNameAndCertificateNum( cus);
		 this.verifyPhoneAndCertificateNum(cus);
	  }
//	/**
//	 * 如果上级存在，就关联
//	 * @param filter
//	 * @throws BOSException
//	 * @throws EASBizException
//	 */
//	private void parentIsExist(FilterInfo filter) throws BOSException, EASBizException{
//		EntityViewInfo view = new EntityViewInfo();
//		view.setFilter(filter);
//		FDCOrgCustomerCollection coll = FDCOrgCustomerFactory.getRemoteInstance().getFDCOrgCustomerCollection(view);
//		if(coll != null && coll.size()>0){
//			for(int i=0;i<coll.size();i++){
//				FDCOrgCustomerInfo info = coll.get(i);
//				String id = info.getBelongUnit().getId().toString();
//				List ids = CusClientHelper.getBelongUnitSet(((FDCOrgCustomerInfo)editData).getBelongUnit());
//				if(ids != null && ids.size()>0){
//					for(int j=0;j<ids.size();j++){
//						if(id.equals(ids.get(j))){
//							((FDCOrgCustomerInfo)createNewData()).setMainCustomer(info.getMainCustomer());
//						}
//					}
//				}
//			}
//		}
//	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		this.txtName.setEnabled(false);
		if (!FDCSysContext.getInstance().getCusMgeMap().containsKey(
				SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			this.actionChangeCusName.setEnabled(false);
			this.actionupdateCus.setEnabled(false);
		}else{
			if("VIEW".equals(oprtType)){
				this.actionChangeCusName.setEnabled(true);
				this.actionupdateCus.setEnabled(true);
			}else{
				this.actionChangeCusName.setEnabled(false);
				this.actionupdateCus.setEnabled(false);
			}
		}
		
		Map map = this.getUIContext();
		if(map.get("isCurOrg") != null  &&  !((Boolean)map.get("isCurOrg")).booleanValue()){
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionupdateCus.setEnabled(false);
			this.actionChangeCusName.setEnabled(false);
		}
		
		//当登录者为营销管控人员时，在客户台账查看打开客户资料，设置按钮不可用
		//2011-09-13
		Object obj = this.getUIContext().get("isOrgData");
		if(obj ==null){
			return;
		}else{
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionupdateCus.setEnabled(false);
			this.actionChangeCusName.setEnabled(false);
		}
	}
	
	public void onLoad() throws Exception {
		initLinkmanTable();
		
		
		this.btnChangeCusName.setIcon(EASResource.getIcon("imgTbtn_rename"));
		this.btnUpdateCus.setIcon(EASResource.getIcon("imgIcon_update"));
		
		actionSave.setVisible(false);
		
		actionAddCommerceChance.setVisible(false);
		actionAddLine.setVisible(false);
		actionInsider.setVisible(false);
		actionCustomerAdapter.setVisible(false);
		actionCustomerShare.setVisible(false);
		actionCustomerCancelShare.setVisible(false);
		actionCopy.setVisible(false);
		
		actionAttachment.setVisible(true);
		
		//TODO 隐藏按钮
		
		this.comboCustomerType.addItems(CustomerTypeEnum.getEnumList().toArray());
		this.comboMarriage.addItems(MaritalStatusEnum.getEnumList().toArray());
		this.comboFirstLinkType.addItems(ContactPreferencesEnum.getEnumList().toArray());
		
		super.onLoad();
		FDCOrgCustomerInfo cus = (FDCOrgCustomerInfo) this.editData;
		setBaseDataFilter(f7Certificate, CustomerTypeEnum.PERSONALCUSTOMER.equals(cus.getCustomerType()) ? CRMConstants.PE_CERTIFICATE_TYPE_GROUP_ID : CRMConstants.EN_CERTIFICATE_TYPE_GROUP_ID);
		
		setBaseDataFilter(f7CusStatus, CRMConstants.CUSTOMER_STATUS_GROUP_ID);
		
		if(CustomerTypeEnum.PERSONALCUSTOMER.equals(this.comboCustomerType.getSelectedItem())){
			f7Industry.setValue(null);
			f7EnterpriceProperty.setValue(null);
		}else{
			setBaseDataFilter(f7EnterpriceProperty, CRMConstants.ENTERPRICE_PROPERTY_GROUP_ID);
		}
		
		setBaseDataFilter(f7CustomerOrigin, CRMConstants.CUSTOMER_ORIGIN_GROUP_ID);
		setBaseDataFilter(f7HabitationInfo, CRMConstants.HABITATION_STATUS_GROUP_ID);
		
		setBaseDataFilter(f7EnterpriceSize, CRMConstants.ENTERPRICE_SIZE_GROUP_ID);
		setBaseDataFilter(f7CooperateMode, CRMConstants.COOPERATE_MODEL_GROUP_ID);
		
		setEnableFilter(f7Nationality);
		setEnableFilter(f7Country);
		setEnableFilter(f7Province);
		setEnableFilter(f7City);
		setEnableFilter(f7Area);
		
//		setEnableFilter(f7Industry);
		
		if (!FDCSysContext.getInstance().getCusMgeMap().containsKey(
				SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
		}
		
		lcPhone.getBoundLabel().setForeground(Color.BLUE);
		lcTel.getBoundLabel().setForeground(Color.BLUE);
		lcOfficeTel.getBoundLabel().setForeground(Color.BLUE);
		lcFax.getBoundLabel().setForeground(Color.BLUE);
		txtPhone.setRequired(false);
		
		this.tabNew.remove(7);
		this.tabNew.remove(6);
		this.tabNew.remove(5);
		this.tabNew.remove(4);
//		this.tabNew.remove(3);
//		this.tabNew.remove(2);
//		
		this.kDTabbedPane1.remove(6);
//		this.kDTabbedPane1.remove(5);
//		this.kDTabbedPane1.remove(4);
		this.kDTabbedPane1.remove(3);
		
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		customerNumberChange();
	
		firstLinkMan = cus.getFirstLinkman();
		corporate = cus.getCorporate();
		corporateTel = cus.getCorporateTel();
		
		
	}
	
	private void setEnableFilter(KDBizPromptBox f7) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("deletedStatus", new Integer(DeletedStatusEnum.NORMAL_VALUE)));
		view.setFilter(filter);
		f7.setEntityViewInfo(view);
	}

	private void setBaseDataFilter(KDBizPromptBox f7, String groupId){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("group.id", groupId));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		f7.setEntityViewInfo(view);
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("number");
		sels.add("industry.name");
		sels.add("country.name");
		sels.add("province.name");
		sels.add("city.name");
		sels.add("region.name");
		sels.add("identity.name");
		
		sels.add("createUnit.id");
		sels.add("createUnit.name");
		sels.add("createTime");
		sels.add("creator.id");
		sels.add("creator.name");
		sels.add("lastUpdateUser.id");
		sels.add("lastUpdateUser.name");
		sels.add("lastUpdateTime");
		sels.add("lastUpdateUnit.id");
		sels.add("lastUpdateUnit.name");
		
		sels.add("CustomerOrigin.name");
		sels.add("cooperateModel.name");
		sels.add("enterpriceProperty.name");
		
		sels.add("certificateType.name");
		sels.add("nativePlace.name");
		sels.add("enterpriceSize.name");
		sels.add("bookedPlace.name");
		sels.add("habitationStatus.name");
		
		sels.add("belongUnit.longNumber");
		sels.add("linkMan.*");
		sels.add("linkMan.certificateType.id");
		sels.add("linkMan.certificateType.name");
		return sels;
	}
	
}