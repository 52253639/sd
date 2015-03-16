/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CityCollection;
import com.kingdee.eas.basedata.assistant.CityFactory;
import com.kingdee.eas.basedata.assistant.CityInfo;
import com.kingdee.eas.basedata.assistant.CountryCollection;
import com.kingdee.eas.basedata.assistant.CountryFactory;
import com.kingdee.eas.basedata.assistant.CountryInfo;
import com.kingdee.eas.basedata.assistant.ProvinceCollection;
import com.kingdee.eas.basedata.assistant.ProvinceFactory;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.assistant.RegionCollection;
import com.kingdee.eas.basedata.assistant.RegionFactory;
import com.kingdee.eas.basedata.assistant.RegionInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.CreateWayEnum;
import com.kingdee.eas.fdc.basecrm.CustomerTypeEnum;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataCollection;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataFactory;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerFactory;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SexEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ImportFDCOrgCustomerUI extends AbstractImportFDCOrgCustomerUI
{
    private static final Logger logger = CoreUIObject.getLogger(ImportFDCOrgCustomerUI.class);
    private String forSHE = "";
    private SellProjectInfo sellProject = null;
    /**
     * output class constructor
     */
    public ImportFDCOrgCustomerUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	this.comboCustomerType.addItems(CustomerTypeEnum.getEnumList().toArray());
    	super.onLoad();
    	
    	this.actionAddNew.setVisible(false);
    	this.actionEdit.setVisible(false);
    	this.actionView.setVisible(false);
    	this.actionRemove.setVisible(false);
    	this.actionRefresh.setVisible(false);
    	this.actionQuery.setVisible(false);
    	this.actionLocate.setVisible(false);
    	this.actionPrint.setVisible(false);
    	this.actionPrintPreview.setVisible(false);
    	
    	this.actionCancelCancel.setVisible(false);
    	this.actionImportFromExcel.setVisible(false);
    	this.contInvaluidCount.setVisible(false);

    	
    	if(this.getUIContext().get("forSHE") != null && !this.getUIContext().get("forSHE").equals("")){
    		forSHE = this.getUIContext().get("forSHE").toString();
        	if(forSHE.equals("true")){
        		sellProject = (SellProjectInfo)this.getUIContext().get("sellProject");
        	}
    	}
    	tblMain.getStyleAttributes().setLocked(false);
    	initImportTable();
    	this.btnSave.setEnabled(true);
    	
    	setMainQueryPK(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "SHECustomerQuery"));
    	this.tblMain.putBindContents("mainQuery",new String[] {"id"});
    }
    
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		
	}
	
	protected String getKeyFieldName() {
		return "name";
	}
	
	private void initImportTable() throws BOSException, EASBizException {
		this.tblMain.getColumn("name").getStyleAttributes().setBackground(
				CommerceHelper.BK_COLOR_MUST);
		this.tblMain.getColumn("phone").getStyleAttributes().setBackground(
				CommerceHelper.BK_COLOR_MUST);
		CustomerTypeEnum cusType = (CustomerTypeEnum) this.comboCustomerType.getSelectedItem();
		// 国籍/注册地
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		view.setFilter(filterInfo);
		KDBizPromptBox f7Nationality = new KDBizPromptBox();
		f7Nationality.setEditable(false);
		f7Nationality.setEntityViewInfo(view);
		f7Nationality.setDisplayFormat("$name$");
		f7Nationality.setEditFormat("$number$");
		f7Nationality.setCommitFormat("$number$");
		f7Nationality.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CountryQuery");
		KDTDefaultCellEditor nationalityEditor = new KDTDefaultCellEditor(f7Nationality);
		this.tblMain.getColumn("nationality").setEditor(nationalityEditor);
		
		// 证件类型
		KDBizPromptBox f7CertificateType = new KDBizPromptBox();
		f7CertificateType.setEditable(false);
		if(CustomerTypeEnum.PERSONALCUSTOMER.equals(cusType)){
			view = setBaseDataView(CRMConstants.PE_CERTIFICATE_TYPE_GROUP_ID);
		}else{
			view = setBaseDataView(CRMConstants.EN_CERTIFICATE_TYPE_GROUP_ID);
		}
		f7CertificateType.setEntityViewInfo(view);
		f7CertificateType.setDisplayFormat("$name$");
		f7CertificateType.setEditFormat("$number$");
		f7CertificateType.setCommitFormat("$number$");
		f7CertificateType.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");
		KDTDefaultCellEditor certificateTypeEditor = new KDTDefaultCellEditor(f7CertificateType);
		this.tblMain.getColumn("certificateType").setEditor(certificateTypeEditor);
		
		// 出生日期/注册日期
		this.tblMain.getColumn("birthday").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.tblMain.getColumn("birthday").setEditor(CommerceHelper.getKDDatePickerEditor());  
		
		// 性别		
		this.tblMain.getColumn("sex").setEditor(CommerceHelper.getKDComboBoxEditor(SexEnum.getEnumList()));
		
		
		if(CustomerTypeEnum.PERSONALCUSTOMER.equals(cusType)){
		// 客户身份
		KDBizPromptBox f7Status = new KDBizPromptBox();
		f7Status.setEditable(false);
		view = setBaseDataView(CRMConstants.CUSTOMER_STATUS_GROUP_ID);
		f7Status.setEntityViewInfo(view);
		f7Status.setDisplayFormat("$name$");
		f7Status.setEditFormat("$number$");
		f7Status.setCommitFormat("$number$");
		f7Status.setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");
		KDTDefaultCellEditor statusEditor = new KDTDefaultCellEditor(f7Status);
		this.tblMain.getColumn("status").setEditor(statusEditor);
		
		}
		
		// 国家/地区
		view = new EntityViewInfo();
		filterInfo = new FilterInfo();
		view.setFilter(filterInfo);
		KDBizPromptBox f7Country = new KDBizPromptBox();
		f7Country.setEditable(false);
		f7Country.setEntityViewInfo(view);
		f7Country.setDisplayFormat("$name$");
		f7Country.setEditFormat("$number$");
		f7Country.setCommitFormat("$number$");
		f7Country.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CountryQuery");
		KDTDefaultCellEditor countryEditor = new KDTDefaultCellEditor(f7Country);
		this.tblMain.getColumn("country").setEditor(countryEditor);
		
		// 省
		view = new EntityViewInfo();
		filterInfo = new FilterInfo();
		view.setFilter(filterInfo);
		KDBizPromptBox f7Province = new KDBizPromptBox();
		f7Province.setEditable(false);
		f7Province.setEntityViewInfo(view);
		f7Province.setDisplayFormat("$name$");
		f7Province.setEditFormat("$number$");
		f7Province.setCommitFormat("$number$");
		f7Province.setQueryInfo("com.kingdee.eas.basedata.assistant.app.ProvinceQuery");
		KDTDefaultCellEditor provinceEditor = new KDTDefaultCellEditor(f7Province);
		this.tblMain.getColumn("province").setEditor(provinceEditor);
		
		
		// 城市
		view = new EntityViewInfo();
		filterInfo = new FilterInfo();
		view.setFilter(filterInfo);
		KDBizPromptBox f7City = new KDBizPromptBox();
		f7City.setEditable(false);
		f7City.setEntityViewInfo(view);
		f7City.setDisplayFormat("$name$");
		f7City.setEditFormat("$number$");
		f7City.setCommitFormat("$number$");
		f7City.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CityQuery");
		KDTDefaultCellEditor cityEditor = new KDTDefaultCellEditor(f7City);
		this.tblMain.getColumn("city").setEditor(cityEditor);
		
		
		// 区/县
		view = new EntityViewInfo();
		filterInfo = new FilterInfo();
		view.setFilter(filterInfo);
		KDBizPromptBox f7Region = new KDBizPromptBox();
		f7Region.setEditable(false);
		f7Region.setEntityViewInfo(view);
		f7Region.setDisplayFormat("$name$");
		f7Region.setEditFormat("name");
		f7Region.setCommitFormat("$name$");
		f7Region.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7RegionQuery");
		KDTDefaultCellEditor regionEditor = new KDTDefaultCellEditor(f7Region);
		this.tblMain.getColumn("region").setEditor(regionEditor);
		
		if(forSHE.equals("true")){
			view = new EntityViewInfo();
			KDBizPromptBox f7PropertyConsultant = new KDBizPromptBox();
			f7PropertyConsultant.setEditable(false);
			view= NewCommerceHelper.getPermitSalemanView(sellProject, null);
			f7PropertyConsultant.setEntityViewInfo(view);
			f7PropertyConsultant.setDisplayFormat("$number$");
			f7PropertyConsultant.setEditFormat("$number$");
			f7PropertyConsultant.setCommitFormat("$number$");
			f7PropertyConsultant.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7PropertyConsultantQuery");
			KDTDefaultCellEditor propertyConsultantEditor = new KDTDefaultCellEditor(f7PropertyConsultant);
			this.tblMain.getColumn("firstConsultant").setEditor(propertyConsultantEditor);
			
		}
	}
	
	private EntityViewInfo setBaseDataView(String groupId){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("group.id", groupId));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		return view;
	}
	
    protected void btnSelectFile_actionPerformed(ActionEvent e) throws Exception {
    	this.tblMain.removeRows(false);
		
		String fileName = SHEHelper.showExcelSelectDlg(this);
		this.txtFilePath.setText(fileName);
		
		logger.info("**房地产客户导入--从excel导入开始....");
		
		int excelNum = CommerceHelper.importExcelToTable(fileName, this.tblMain);  //对隐藏的列不写入
		
		logger.info("**房地产客户导入--从excel导入结束，共导入"+excelNum+"条数据！");
		
		this.tblMain.setRowCount(excelNum);
		this.txtExcelCount.setText(String.valueOf(excelNum));
    }


    private FDCOrgCustomerCollection cols = new FDCOrgCustomerCollection();
    private SHECustomerCollection sheCols = new SHECustomerCollection();
    
	private void vertifyARowImportDate() throws BOSException, EASBizException {
		cols.clear();
		CustomerTypeEnum cusType = (CustomerTypeEnum) this.comboCustomerType.getSelectedItem();
		CusClientHelper.existCodingRule(null);
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
		if (orgUnit == null)
			orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		for(int i=0; i<this.tblMain.getRowCount(); i++){
			FDCOrgCustomerInfo cus = new FDCOrgCustomerInfo();
			FullOrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
			cus.setCustomerType(cusType);
			cus.setCreateUnit(org);
			cus.setBelongUnit(org);
			cus.setIsEnabled(true);
			cus.setCreateWay(CreateWayEnum.SYSTEMIMPORT);
			
			IRow row = this.tblMain.getRow(i);
			int rowIndex = i+1;
			cus.setName((String) row.getCell("name").getValue());
			if (isNullStr((String) row.getCell("name").getValue())) {
				MsgBox.showInfo(this, "第" + rowIndex + "行客户名称必须录入！");
				this.abort();
			}else if (row.getCell("name").getValue().toString().length() > 80) {
				MsgBox.showInfo(this, "第" + rowIndex + "行线索客户长度不能大于80！");
				this.abort();
			}
			
			Object nationality = row.getCell("nationality").getValue(); // 国籍
			if (nationality != null) {
					if (nationality instanceof String) {
						CountryInfo thisInfo = getCountryByName((String) nationality);
						if (thisInfo != null) {
							row.getCell("status").setValue(thisInfo);
							cus.setBookedPlace((CountryInfo) thisInfo);
						} else {
							MsgBox.showInfo(this, "第" + rowIndex + "行国籍无法识别！");
							this.abort();
						}
					} else if (nationality instanceof CountryInfo) {
						cus.setBookedPlace((CountryInfo) nationality);
					}
			}
			
			this.vertifyNotNull(row, rowIndex, "code", "客户代码");
			cus.setCode((String) row.getCell("code").getValue());
			this.vertifyNotNull(row, rowIndex, "simpleCode", "客户简码");
			cus.setSimpleCode((String) row.getCell("simpleCode").getValue());
			
			if(CustomerTypeEnum.ENTERPRICECUSTOMER.equals(cusType)){
				if(isNullStr((String) row.getCell("firstLinkman").getValue())){
					MsgBox.showInfo(this, "第" + rowIndex + "行首选联系人必须录入！");
					this.abort();
				}else if (row.getCell("firstLinkman").getValue().toString().length() > 80) {
					MsgBox.showInfo(this, "第" + rowIndex + "行首选联系人长度不能大于80！");
					this.abort();
				}
			}
			cus.setFirstLinkman((String) row.getCell("firstLinkman").getValue());
			
			cus.setPhone((String) row.getCell("phone").getValue());
			if(isNullStr((String) row.getCell("phone").getValue()) && 
					isNullStr((String) row.getCell("homeTel").getValue()) && 
					isNullStr((String) row.getCell("officeTel").getValue()) && 
					isNullStr((String) row.getCell("fax").getValue())) {
				MsgBox.showInfo(this, "第" + rowIndex + "移动电话、住宅电话、办公电话、传真至少录入一个！");
				this.abort();
			}
			
			if(row.getCell("phone").getValue() != null && !row.getCell("phone").getValue().equals("") ){
				if(row.getCell("phone").getValue().toString().length() < 7){
					MsgBox.showInfo(this, "第" + rowIndex + "行移动电话至少需要输入7位以上！");
					this.abort();
			     }else if (row.getCell("phone").getValue().toString().length() > 80) {
						MsgBox.showInfo(this, "第" + rowIndex + "行移动电话长度不能大于80！");
						this.abort();
				}
			}
			
			this.vertifyNotNull(row, rowIndex, "homeTel", "住宅电话");
			cus.setTel((String) row.getCell("homeTel").getValue());
			this.vertifyNotNull(row, rowIndex, "officeTel", "办公电话");
			cus.setOfficeTel((String) row.getCell("officeTel").getValue());
			this.vertifyNotNull(row, rowIndex, "fax", "传真");
			cus.setFax((String) row.getCell("fax").getValue());
			this.vertifyNotNull(row, rowIndex, "otherTel", "其他电话");
			cus.setOtherTel((String) row.getCell("otherTel").getValue());
			this.vertifyNotNull(row, rowIndex, "email", "Email");
			cus.setEmail((String) row.getCell("email").getValue());
			
			Object certificateType = row.getCell("certificateType").getValue(); // 证件类型
			if (certificateType != null) {
					if (certificateType instanceof String) {
						FDCCusBaseDataInfo thisInfo = new  FDCCusBaseDataInfo();
						if(CustomerTypeEnum.PERSONALCUSTOMER.equals(cusType)){
							thisInfo = getPerCertificateTypeByName((String) certificateType);
						}else{
							thisInfo = getEnCertificateTypeByName((String) certificateType);
						}
						 
						if (thisInfo != null) {
							row.getCell("certificateType").setValue(thisInfo);
							cus.setCertificateType((FDCCusBaseDataInfo) thisInfo);
						} else {
							MsgBox.showInfo(this, "第" + rowIndex + "行证件类型无法识别！");
							this.abort();
						}
					} else if (certificateType instanceof FDCCusBaseDataInfo) {
						cus.setCertificateType((FDCCusBaseDataInfo) certificateType);
					}
			}
			this.vertifyNotNull(row, rowIndex, "certificateNum", "证件号码");
			cus.setCertificateNumber((String) row.getCell("certificateNum").getValue());
			
			Object birthday = row.getCell("birthday").getValue(); // 出生日期
			if (birthday instanceof String) {
				Date thisInfo = stringToDate((String) birthday);
				if (thisInfo != null) {
					row.getCell("sex").setValue(thisInfo);
					cus.setDayOfbirth((Date) thisInfo);
				} else {
					MsgBox.showInfo(this, "第" + rowIndex + "行出生日期无法识别！");
					this.abort();
				}
			} else if (birthday instanceof Date) {
				cus.setDayOfbirth((Date) birthday);
			}
			Object sex = row.getCell("sex").getValue(); // 性别
			if (sex instanceof String) {
				SexEnum thisInfo = getSexByName((String) sex);
				if (thisInfo != null) {
					row.getCell("sex").setValue(thisInfo);
					cus.setSex((SexEnum) thisInfo);
				} else {
					MsgBox.showInfo(this, "第" + rowIndex + "行性别无法识别！");
					this.abort();
				}
			} else if (sex instanceof CustomerTypeEnum) {
				cus.setSex((SexEnum) sex);
			}
			
			if(CustomerTypeEnum.PERSONALCUSTOMER.equals(cusType)){
			Object status = row.getCell("status").getValue(); // 客户身份
			if (status != null) {
					if (status instanceof String) {
						FDCCusBaseDataInfo thisInfo = getCustomerStatusByName((String) status);
						if (thisInfo != null) {
							row.getCell("status").setValue(thisInfo);
							cus.setIdentity((FDCCusBaseDataInfo) thisInfo);
						} else {
							MsgBox.showInfo(this, "第" + rowIndex + "行客户身份无法识别！");
							this.abort();
						}
					} else if (status instanceof FDCCusBaseDataInfo) {
						cus.setIdentity((FDCCusBaseDataInfo) status);
					}
			}
		}
			Object country = row.getCell("country").getValue(); // 国家/地区
			if (country != null) {
					if (country instanceof String) {
						CountryInfo thisInfo = getCountryByName((String) country);
						if (thisInfo != null) {
							row.getCell("status").setValue(thisInfo);
							cus.setCountry((CountryInfo) thisInfo);
						} else {
							MsgBox.showInfo(this, "第" + rowIndex + "行国家/地区无法识别！");
							this.abort();
						}
					} else if (country instanceof CountryInfo) {
						cus.setCountry((CountryInfo) country);
					}
			}
			
			Object province = row.getCell("province").getValue(); // 省/州
			if (province != null) {
					if (province instanceof String) {
						ProvinceInfo thisInfo = getProvinceByName((String) province);
						if (thisInfo != null) {
							row.getCell("province").setValue(thisInfo);
							cus.setProvince((ProvinceInfo) thisInfo);
						} else {
							MsgBox.showInfo(this, "第" + rowIndex + "行省/州无法识别！");
							this.abort();
						}
					} else if (province instanceof ProvinceInfo) {
						cus.setProvince((ProvinceInfo) province);
					}
			}
			
			
			Object city = row.getCell("city").getValue(); // 城市
			if (city != null) {
					if (city instanceof String) {
						CityInfo thisInfo = getCityByName((String) city);
						if (thisInfo != null) {
							row.getCell("status").setValue(thisInfo);
							cus.setCity((CityInfo) thisInfo);
						} else {
							MsgBox.showInfo(this, "第" + rowIndex + "行城市无法识别！");
							this.abort();
						}
					} else if (city instanceof CityInfo) {
						cus.setCity((CityInfo) city);
					}
			}
			
			Object region = row.getCell("region").getValue(); // 区/县
			if (region != null) {
					if (region instanceof String) {
						RegionInfo thisInfo = getRegionByName((String) region);
						if (thisInfo != null) {
							row.getCell("status").setValue(thisInfo);
							cus.setRegion((RegionInfo) thisInfo);
						} else {
							MsgBox.showInfo(this, "第" + rowIndex + "行区/县无法识别！");
							this.abort();
						}
					} else if (region instanceof RegionInfo) {
						cus.setRegion((RegionInfo) region);
					}
			}
			
			this.vertifyNotNull(row, rowIndex, "mailAddr", "通信地址");
			this.vertifyNotNull(row, rowIndex, "certificateAddr", "证件地址");
			this.vertifyNotNull(row, rowIndex, "postcode", "邮政编码");
			this.vertifyNotNull(row, rowIndex, "corporate", "企业法人");
			this.vertifyNotNull(row, rowIndex, "corporateTel", "企业法人移动电话");
			cus.setMailAddress((String) row.getCell("mailAddr").getValue());
			cus.setBookedAddress((String) row.getCell("certificateAddr").getValue());
			cus.setPostalCode((String) row.getCell("postcode").getValue());
			cus.setCorporate((String) row.getCell("corporate").getValue());
			cus.setCorporateTel((String) row.getCell("corporateTel").getValue());
			
			cus.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
			String retNumber = iCodingRuleManager.getNumber(cus, orgUnit.getId().toString());
			cus.setNumber(retNumber);
			row.setUserObject(cus);
			
			parentIsExist(cus);
			
			cols.add(cus);
		}
	}
	private void vertifyNotNull(IRow row,int rowIndex,String key,String word){
		if(!isNullStr((String) row.getCell(key).getValue())){
			if (row.getCell(key).getValue().toString().length() > 80) {
				MsgBox.showInfo(this, "第" + rowIndex + "行"+word+"长度不能大于80！");
				this.abort();
			}
		}
	}
	
	private void vertifyARowImportDateForSHE() throws BOSException, EASBizException {
		sheCols.clear();
		CustomerTypeEnum cusType = (CustomerTypeEnum) this.comboCustomerType.getSelectedItem();
		CusClientHelper.existCodingRule("true");
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
		if (orgUnit == null)
			orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		for(int i=0; i<this.tblMain.getRowCount(); i++){
			SHECustomerInfo cus = new SHECustomerInfo();
			FullOrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
			cus.setCustomerType(cusType);
			cus.setCreateUnit(org);
			cus.setIsEnabled(true);
			cus.setCreateWay(CreateWayEnum.SYSTEMIMPORT);
			cus.setIsPublic(false);
			
			IRow row = this.tblMain.getRow(i);
			int rowIndex = i+1;
			cus.setName((String) row.getCell("name").getValue());
			if (isNullStr((String) row.getCell("name").getValue())) {
				MsgBox.showInfo(this, "第" + rowIndex + "行客户名称必须录入！");
				this.abort();
			}else if (row.getCell("name").getValue().toString().length() > 80) {
				MsgBox.showInfo(this, "第" + rowIndex + "行线索客户长度不能大于80！");
				this.abort();
			}
			
			Object nationality = row.getCell("nationality").getValue(); // 国籍
			if (nationality != null) {
					if (nationality instanceof String) {
						CountryInfo thisInfo = getCountryByName((String) nationality);
						if (thisInfo != null) {
							row.getCell("status").setValue(thisInfo);
							cus.setBookedPlace((CountryInfo) thisInfo);
						} else {
							MsgBox.showInfo(this, "第" + rowIndex + "行国籍无法识别！");
							this.abort();
						}
					} else if (nationality instanceof CountryInfo) {
						cus.setBookedPlace((CountryInfo) nationality);
					}
			}
			
			this.vertifyNotNull(row, rowIndex, "code", "客户代码");
			cus.setCode((String) row.getCell("code").getValue());
			this.vertifyNotNull(row, rowIndex, "simpleCode", "客户简码");
			cus.setSimpleCode((String) row.getCell("simpleCode").getValue());
			
			if(CustomerTypeEnum.ENTERPRICECUSTOMER.equals(cusType)){
				if(isNullStr((String) row.getCell("firstLinkman").getValue())){
					MsgBox.showInfo(this, "第" + rowIndex + "行首选联系人必须录入！");
					this.abort();
				}else if (row.getCell("firstLinkman").getValue().toString().length() > 80) {
					MsgBox.showInfo(this, "第" + rowIndex + "行首选联系人长度不能大于80！");
					this.abort();
				}
			}
			cus.setFirstLinkman((String) row.getCell("firstLinkman").getValue());
			
			cus.setPhone((String) row.getCell("phone").getValue());
			if(isNullStr((String) row.getCell("phone").getValue()) && 
					isNullStr((String) row.getCell("homeTel").getValue()) && 
					isNullStr((String) row.getCell("officeTel").getValue()) && 
					isNullStr((String) row.getCell("fax").getValue())) {
				MsgBox.showInfo(this, "第" + rowIndex + "移动电话、住宅电话、办公电话、传真至少录入一个！");
				this.abort();
			}
			
			if(row.getCell("phone").getValue() != null && !row.getCell("phone").getValue().equals("") ){
				if(row.getCell("phone").getValue().toString().length() < 7){
					MsgBox.showInfo(this, "第" + rowIndex + "行移动电话至少需要输入7位以上！");
					this.abort();
			     }else if (row.getCell("phone").getValue().toString().length() > 80) {
						MsgBox.showInfo(this, "第" + rowIndex + "行移动电话长度不能大于80！");
						this.abort();
				}
			}
			
			this.vertifyNotNull(row, rowIndex, "homeTel", "住宅电话");
			cus.setTel((String) row.getCell("homeTel").getValue());
			this.vertifyNotNull(row, rowIndex, "officeTel", "办公电话");
			cus.setOfficeTel((String) row.getCell("officeTel").getValue());
			this.vertifyNotNull(row, rowIndex, "fax", "传真");
			cus.setFax((String) row.getCell("fax").getValue());
			this.vertifyNotNull(row, rowIndex, "otherTel", "其他电话");
			cus.setOtherTel((String) row.getCell("otherTel").getValue());
			this.vertifyNotNull(row, rowIndex, "email", "Email");
			cus.setEmail((String) row.getCell("email").getValue());
			
			Object certificateType = row.getCell("certificateType").getValue(); // 证件类型
			if (certificateType != null) {
					if (certificateType instanceof String) {
						FDCCusBaseDataInfo thisInfo = new  FDCCusBaseDataInfo();
						if(CustomerTypeEnum.PERSONALCUSTOMER.equals(cusType)){
							thisInfo = getPerCertificateTypeByName((String) certificateType);
						}else{
							thisInfo = getEnCertificateTypeByName((String) certificateType);
						}
						 
						if (thisInfo != null) {
							row.getCell("certificateType").setValue(thisInfo);
							cus.setCertificateType((FDCCusBaseDataInfo) thisInfo);
						} else {
							MsgBox.showInfo(this, "第" + rowIndex + "行证件类型无法识别！");
							this.abort();
						}
					} else if (certificateType instanceof FDCCusBaseDataInfo) {
						cus.setCertificateType((FDCCusBaseDataInfo) certificateType);
					}
			}
			this.vertifyNotNull(row, rowIndex, "certificateNum", "证件号码");
			cus.setCertificateNumber((String) row.getCell("certificateNum").getValue());
			
			Object birthday = row.getCell("birthday").getValue(); // 出生日期
			if (birthday instanceof String) {
				Date thisInfo = stringToDate((String) birthday);
				if (thisInfo != null) {
					row.getCell("sex").setValue(thisInfo);
					cus.setDayOfbirth((Date) thisInfo);
				} else {
					MsgBox.showInfo(this, "第" + rowIndex + "行出生日期无法识别！");
					this.abort();
				}
			} else if (birthday instanceof Date) {
				cus.setDayOfbirth((Date) birthday);
			}
			Object sex = row.getCell("sex").getValue(); // 性别
			if (sex instanceof String) {
				SexEnum thisInfo = getSexByName((String) sex);
				if (thisInfo != null) {
					row.getCell("sex").setValue(thisInfo);
					cus.setSex((SexEnum) thisInfo);
				} else {
					MsgBox.showInfo(this, "第" + rowIndex + "行性别无法识别！");
					this.abort();
				}
			} else if (sex instanceof CustomerTypeEnum) {
				cus.setSex((SexEnum) sex);
			}else if(sex instanceof SexEnum){
				cus.setSex((SexEnum) sex);
			}
			
			if(CustomerTypeEnum.PERSONALCUSTOMER.equals(cusType)){
			Object status = row.getCell("status").getValue(); // 客户身份
			if (status != null) {
					if (status instanceof String) {
						FDCCusBaseDataInfo thisInfo = getCustomerStatusByName((String) status);
						if (thisInfo != null) {
							row.getCell("status").setValue(thisInfo);
							cus.setIdentity((FDCCusBaseDataInfo) thisInfo);
						} else {
							MsgBox.showInfo(this, "第" + rowIndex + "行客户身份无法识别！");
							this.abort();
						}
					} else if (status instanceof FDCCusBaseDataInfo) {
						cus.setIdentity((FDCCusBaseDataInfo) status);
					}
			}
		}
			Object country = row.getCell("country").getValue(); // 国家/地区
			if (country != null) {
					if (country instanceof String) {
						CountryInfo thisInfo = getCountryByName((String) country);
						if (thisInfo != null) {
							row.getCell("status").setValue(thisInfo);
							cus.setCountry((CountryInfo) thisInfo);
						} else {
							MsgBox.showInfo(this, "第" + rowIndex + "行国家/地区无法识别！");
							this.abort();
						}
					} else if (country instanceof CountryInfo) {
						cus.setCountry((CountryInfo) country);
					}
			}
			
			Object province = row.getCell("province").getValue(); // 省/州
			if (province != null) {
					if (province instanceof String) {
						ProvinceInfo thisInfo = getProvinceByName((String) province);
						if (thisInfo != null) {
							row.getCell("province").setValue(thisInfo);
							cus.setProvince((ProvinceInfo) thisInfo);
						} else {
							MsgBox.showInfo(this, "第" + rowIndex + "行省/州无法识别！");
							this.abort();
						}
					} else if (province instanceof ProvinceInfo) {
						cus.setProvince((ProvinceInfo) province);
					}
			}
			
			
			Object city = row.getCell("city").getValue(); // 城市
			if (city != null) {
					if (city instanceof String) {
						CityInfo thisInfo = getCityByName((String) city);
						if (thisInfo != null) {
							row.getCell("status").setValue(thisInfo);
							cus.setCity((CityInfo) thisInfo);
						} else {
							MsgBox.showInfo(this, "第" + rowIndex + "行城市无法识别！");
							this.abort();
						}
					} else if (city instanceof CityInfo) {
						cus.setCity((CityInfo) city);
					}
			}
			
			Object region = row.getCell("region").getValue(); // 区/县
			if (region != null) {
					if (region instanceof String) {
						RegionInfo thisInfo = getRegionByName((String) region);
						if (thisInfo != null) {
							row.getCell("status").setValue(thisInfo);
							cus.setRegion((RegionInfo) thisInfo);
						} else {
							MsgBox.showInfo(this, "第" + rowIndex + "行区/县无法识别！");
							this.abort();
						}
					} else if (region instanceof RegionInfo) {
						cus.setRegion((RegionInfo) region);
					}
			}
			
			if(forSHE.equals("true")){
			Object firstConsultant = row.getCell("firstConsultant").getValue(); // 首次接待顾问
			if (firstConsultant != null) {
					if (firstConsultant instanceof String) {
						UserInfo thisInfo = getUserByName((String) firstConsultant);
						if (thisInfo != null) {
							row.getCell("status").setValue(thisInfo);
							cus.setFirstConsultant((UserInfo) thisInfo);
						} else {
							MsgBox.showInfo(this, "第" + rowIndex + "行首次接待顾问无法识别！");
							this.abort();
						}
					} else if (firstConsultant instanceof UserInfo) {
						cus.setFirstConsultant((UserInfo) firstConsultant);
					}
		    	}else{
					MsgBox.showInfo(this, "第" + rowIndex + "行首次接待顾问必须录入！");
					this.abort();
				}
			}
			
			cus.setPropertyConsultant(cus.getFirstConsultant());
			this.vertifyNotNull(row, rowIndex, "mailAddr", "通信地址");
			this.vertifyNotNull(row, rowIndex, "certificateAddr", "证件地址");
			this.vertifyNotNull(row, rowIndex, "postcode", "邮政编码");
			this.vertifyNotNull(row, rowIndex, "corporate", "企业法人");
			this.vertifyNotNull(row, rowIndex, "corporateTel", "企业法人移动电话");
			cus.setMailAddress((String) row.getCell("mailAddr").getValue());
			cus.setBookedAddress((String) row.getCell("certificateAddr").getValue());
			cus.setPostalCode((String) row.getCell("postcode").getValue());
			cus.setCorporate((String) row.getCell("corporate").getValue());
			cus.setCorporateTel((String) row.getCell("corporateTel").getValue());
			
			cus.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
			String retNumber = iCodingRuleManager.getNumber(cus, orgUnit.getId().toString());
			cus.setNumber(retNumber);
			row.setUserObject(cus);
			sheCols.add(cus);
		}
	}
	
	private boolean isNullStr(String s){
		return s==null || s.equals("");
	}
	
	private Date stringToDate(String s) {
		Date d = null;
		if (FMHelper.isEmpty(s)) {
			d = FDCDateHelper.getDayBegin();
		} else {
			try {
				d = FDCConstants.FORMAT_DAY.parse(s);
			} catch (ParseException e) {
				try {
					d = new SimpleDateFormat().parse(s);
				} catch (ParseException e1) {
					
				}
			}
		}
		return d;
	}
	
	private SexEnum getSexByName(String name){
		if(name == null  ||  name.equals("")){
			return null;
		}
		
		if("男".equals(name)){
			return SexEnum.Mankind;
		}else if("女".equals(name)){
			return SexEnum.Womenfolk;
		}else{
			return null;
		}
	}
	
	protected void comboCustomerType_itemStateChanged(ItemEvent e) throws Exception {
		this.tblMain.checkParsed();
		this.tblMain.removeRows(false);
		this.txtFilePath.setText(null);
		this.txtExcelCount.setText(null);
		boolean isPer = CustomerTypeEnum.PERSONALCUSTOMER.equals(this.comboCustomerType.getSelectedItem());
		
		tblMain.getColumn("firstLinkman").getStyleAttributes().setHided(isPer);
		tblMain.getColumn("sex").getStyleAttributes().setHided(!isPer);
		tblMain.getColumn("status").getStyleAttributes().setHided(!isPer);
		tblMain.getColumn("corporate").getStyleAttributes().setHided(isPer);
		tblMain.getColumn("corporateTel").getStyleAttributes().setHided(isPer);
		
		if(this.getUIContext().get("forSHE") != null && !this.getUIContext().get("forSHE").equals("")){
    		forSHE = this.getUIContext().get("forSHE").toString();
    		if(forSHE.equals("true")){
    		    tblMain.getColumn("firstConsultant").getStyleAttributes().setHided(false);
    		}else{
    			tblMain.getColumn("firstConsultant").getStyleAttributes().setHided(true);
    		}
		}else{
			tblMain.getColumn("firstConsultant").getStyleAttributes().setHided(true);
		}
		
		this.initImportTable();
	}
	
    private Map countryMap;
    private Map provinceMap;
    private Map cityMap;
    private Map userMap;
    private Map regionMap;
    private Map perCertificateTypeMap;
    private Map enCertificateTypeMap;
    private Map customerStatusMap;
    
    private FDCCusBaseDataInfo getEnCertificateTypeByName(String name) throws BOSException{
		if(name==null || name.trim().equals("")) return null;
		
		if (enCertificateTypeMap == null) {
			initFDCCusBaseData();
		}
    	return (FDCCusBaseDataInfo)enCertificateTypeMap.get(name.trim());
	}
    
    private FDCCusBaseDataInfo getPerCertificateTypeByName(String name) throws BOSException{
		if(name==null || name.trim().equals("")) return null;
		
		if (perCertificateTypeMap == null) {
			initFDCCusBaseData();
		}
    	return (FDCCusBaseDataInfo)perCertificateTypeMap.get(name.trim());
	}

    private FDCCusBaseDataInfo getCustomerStatusByName(String name) throws BOSException{
		if(name==null || name.trim().equals("")) return null;
		
		if (customerStatusMap == null) {
			initFDCCusBaseData();
		}
    	return (FDCCusBaseDataInfo)customerStatusMap.get(name.trim());
	}
    
	/**
	 * @throws BOSException
	 */
	private void initFDCCusBaseData() throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("name");
		sels.add("group.id");
		view.setSelector(sels);
		FilterInfo filter = new FilterInfo();
		Set set = new HashSet();
		set.add(CRMConstants.PE_CERTIFICATE_TYPE_GROUP_ID);
		set.add(CRMConstants.EN_CERTIFICATE_TYPE_GROUP_ID);
		set.add(CRMConstants.CUSTOMER_STATUS_GROUP_ID);
		filter.getFilterItems().add(new FilterItemInfo("group.id", set, CompareType.INCLUDE));
		view.setFilter(filter);
		FDCCusBaseDataCollection co = FDCCusBaseDataFactory.getRemoteInstance().getFDCCusBaseDataCollection(view);
		
		perCertificateTypeMap = new HashMap();
		customerStatusMap = new HashMap();
		enCertificateTypeMap = new HashMap();
		for(int i=0;i<co.size();i++) {
			FDCCusBaseDataInfo thisInfo = co.get(i);
			if(thisInfo.getName() == null){
				continue;
			}
			
			if(CRMConstants.PE_CERTIFICATE_TYPE_GROUP_ID.equals(thisInfo.getGroup().getId().toString())){
				perCertificateTypeMap.put(thisInfo.getName().trim(),thisInfo);;
			}else if(CRMConstants.EN_CERTIFICATE_TYPE_GROUP_ID.equals(thisInfo.getGroup().getId().toString())){
				enCertificateTypeMap.put(thisInfo.getName().trim(),thisInfo);
			}else if(CRMConstants.CUSTOMER_STATUS_GROUP_ID.equals(thisInfo.getGroup().getId().toString())){
				customerStatusMap.put(thisInfo.getName().trim(),thisInfo);
			}
		}
	}
	
	 private UserInfo getUserByName(String name) throws BOSException{
			if(name==null || name.trim().equals("")) return null;
			
			if (userMap == null) {
				UserCollection co = UserFactory.getRemoteInstance().getUserCollection();
				userMap = new HashMap();
				for(int i=0;i<co.size();i++) {
					UserInfo thisInfo = co.get(i);
					if(thisInfo.getNumber() == null){					
						continue;
					}
					userMap.put(thisInfo.getNumber().trim(),thisInfo);
				}
			}
	    	return (UserInfo)userMap.get(name.trim());
		}
    
    private RegionInfo getRegionByName(String name) throws BOSException{
		if(name==null || name.trim().equals("")) return null;
		
		if (regionMap == null) {
			RegionCollection co = RegionFactory.getRemoteInstance().getRegionCollection();
			regionMap = new HashMap();
			for(int i=0;i<co.size();i++) {
				RegionInfo thisInfo = co.get(i);
				if(thisInfo.getName() == null){					
					continue;
				}
				regionMap.put(thisInfo.getName().trim(),thisInfo);
			}
		}
    	return (RegionInfo)regionMap.get(name.trim());
	}
    
    private CityInfo getCityByName(String name) throws BOSException{
		if(name==null || name.trim().equals("")) return null;
		
		if (cityMap == null) {
			CityCollection co = CityFactory.getRemoteInstance().getCityCollection();
			cityMap = new HashMap();
			for(int i=0;i<co.size();i++) {
				CityInfo thisInfo = co.get(i);
				if(thisInfo.getName() == null){					
					continue;
				}
				cityMap.put(thisInfo.getName().trim(),thisInfo);
			}
		}
    	return (CityInfo)cityMap.get(name.trim());
	}
    
	private ProvinceInfo getProvinceByName(String name) throws BOSException{
		if(name==null || name.trim().equals("")) return null;
		
		if (provinceMap == null) {
			ProvinceCollection co = ProvinceFactory.getRemoteInstance().getProvinceCollection();
			provinceMap = new HashMap();
			for(int i=0;i<co.size();i++) {
				ProvinceInfo thisInfo = co.get(i);
				if(thisInfo.getName() == null){
					continue;
				}
				provinceMap.put(thisInfo.getName().trim(),thisInfo);
			}
		}
    	return (ProvinceInfo)provinceMap.get(name.trim());
	}
    
	private CountryInfo getCountryByName(String name) throws BOSException{
		if(name==null || name.trim().equals("")) return null;
		
		if (countryMap == null) {
			CountryCollection co = CountryFactory.getRemoteInstance().getCountryCollection();
			countryMap = new HashMap();
			for(int i=0;i<co.size();i++) {
				CountryInfo thisInfo = co.get(i);
				if(thisInfo.getName() == null){
					continue;
				}
				countryMap.put(thisInfo.getName().trim(),thisInfo);
			}
		}
    	return (CountryInfo)countryMap.get(name.trim());
	}

	public void actionSaveImport_actionPerformed(ActionEvent e) throws Exception {
		if(forSHE.equals("true")){
			this.vertifyARowImportDateForSHE();
			if(sheCols == null  ||  sheCols.isEmpty()){
				MsgBox.showInfo(this, "没有要导入的数据！");
				this.abort();
			}
			SHECustomerFactory.getRemoteInstance().importCustomer(sheCols,sellProject);
		}else{
			this.vertifyARowImportDate();
			if(cols == null  ||  cols.isEmpty()){
				MsgBox.showInfo(this, "没有要导入的数据！");
				this.abort();
			}
			FDCOrgCustomerFactory.getRemoteInstance().importCustomer(cols);
		}
		
		MsgBox.showInfo(this, "导入成功！");
	}
	
	/**
	 * 当上级存在时，关联之
	 * @param orgInfo
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void parentIsExist(FDCOrgCustomerInfo orgInfo) throws EASBizException, BOSException{
		String name = orgInfo.getName();
		String phone = orgInfo.getPhone();
		String tel = orgInfo.getTel();
		String officeTel = orgInfo.getOfficeTel();
		String fax = orgInfo.getTel();
		String certificateNumber = orgInfo.getCertificateNumber();
		FilterInfo filter = new FilterInfo();
		FullOrgUnitInfo unitInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		
		List belongList = CusClientHelper.getBelongUnitSet(unitInfo);
		Set belongSet = new HashSet();
		if(belongList !=null && belongList.size()>0){
			for (int i = 0; i < belongList.size(); i++) {
				belongSet.add(belongList.get(i).toString());
			}
		}
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("customerType", this.comboCustomerType.getSelectedItem()));
		filter.getFilterItems().add(new FilterItemInfo("belongUnit.id",belongSet,CompareType.INCLUDE));
		if (name != null && !name.equals("")) {
			filter.getFilterItems().add(
					new FilterItemInfo("name", name, CompareType.EQUALS));
			if (certificateNumber != null && !certificateNumber.equals("")) {
				filter.getFilterItems().add(
						new FilterItemInfo("certificateNumber",
								certificateNumber, CompareType.EQUALS));
			}
			
			if (phone != null && !phone.equals("")) {
				filter.getFilterItems().add(
						new FilterItemInfo("phone", phone,
								CompareType.EQUALS));
			}else if(tel != null && !tel.equals("")){
				filter.getFilterItems().add(
						new FilterItemInfo("tel", tel,
								CompareType.EQUALS));
			}else if(officeTel != null && !officeTel.equals("")){
				filter.getFilterItems().add(
						new FilterItemInfo("officeTel", officeTel,
								CompareType.EQUALS));
			}else if(fax != null && !fax.equals("")){
				filter.getFilterItems().add(
						new FilterItemInfo("fax", fax,
								CompareType.EQUALS));
			}
		}
		
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		FDCOrgCustomerCollection coll = FDCOrgCustomerFactory.getRemoteInstance().getFDCOrgCustomerCollection(view);
		if(coll != null && coll.size() > 0){
//			if(coll.size() == belongSet.size()){
//				MsgBox.showInfo("客户已经存在！不能重复");
//				return;
//			}
			FDCOrgCustomerInfo info = coll.get(0);
			orgInfo.setMainCustomer(info.getMainCustomer());
		}
	}
}