/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FocusTraversalPolicy;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.UIFocusTraversalPolicy;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CityInfo;
import com.kingdee.eas.basedata.assistant.CountryCollection;
import com.kingdee.eas.basedata.assistant.CountryFactory;
import com.kingdee.eas.basedata.assistant.CountryInfo;
import com.kingdee.eas.basedata.assistant.IndustryInfo;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.assistant.RegionCollection;
import com.kingdee.eas.basedata.assistant.RegionFactory;
import com.kingdee.eas.basedata.assistant.RegionInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
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
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataCollection;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataFactory;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo;
import com.kingdee.eas.fdc.basecrm.IDCard;
import com.kingdee.eas.fdc.basecrm.MaritalStatusEnum;
import com.kingdee.eas.fdc.basecrm.client.CusClientHelper;
import com.kingdee.eas.fdc.basecrm.client.MyKDCommonPromptDialog;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerChangeDetailCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerChangeDetailFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerChangeDetailInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerChangeReasonEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerChangeReasonFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SexEnum;
import com.kingdee.eas.fdc.sellhouse.SharePropertyCollection;
import com.kingdee.eas.fdc.sellhouse.SharePropertyFactory;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo;
import com.kingdee.eas.framework.DeletedStatusEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.IPropertyContainer;
import com.kingdee.util.enums.EnumUtils;

/**
 * output class name
 */
public class CustomerRptEditUI extends AbstractCustomerRptEditUI {
	private static final Logger logger = CoreUIObject.getLogger(CustomerRptEditUI.class);
	private Map defValues;
	private boolean isLoadField = false;
	private boolean isOld = false;
	private String firstLinkMan = null;
	private String corporate = null;
	private String corporateTel = null;
	private String type = "";
	private FDCSQLBuilder builder;
	private boolean state = false;
	private boolean isNew = false;
	private boolean isAddQuestion = false;
	private String isSignManage = "";

	/**
	 * output class constructor
	 */
	public CustomerRptEditUI() throws Exception {
		super();
		defValues = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isDefault", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		FDCCusBaseDataCollection baseDatas = FDCCusBaseDataFactory.getRemoteInstance().getFDCCusBaseDataCollection(view);

		for (int i = 0; i < baseDatas.size(); i++) {
			FDCCusBaseDataInfo data = baseDatas.get(i);
			if (data.getGroup() != null) {
				String groupID = data.getGroup().getId().toString();
				defValues.put(groupID, data);
			}
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initCom();//初始化控件

		setBaseDataFilter(prmtIdentity, CRMConstants.CUSTOMER_STATUS_GROUP_ID); // 客户身份

		if (CustomerTypeEnum.PERSONALCUSTOMER.equals(this.comboCustomerType.getSelectedItem())) {
			prmtIndustry.setValue(null);
			prmtEnterpriceProperty.setValue(null);
		} else {
			setBaseDataFilter(prmtEnterpriceProperty, CRMConstants.ENTERPRICE_PROPERTY_GROUP_ID);// 企业性质
		}

		setBaseDataFilter(prmtCustomerOrigin, CRMConstants.CUSTOMER_ORIGIN_GROUP_ID);// 客户来源
		setBaseDataFilter(prmtHabitationStatus, CRMConstants.HABITATION_STATUS_GROUP_ID);// 居住情况

		setBaseDataFilter(prmtEnterpriceSize, CRMConstants.ENTERPRICE_SIZE_GROUP_ID);// 企业规模
		setBaseDataFilter(prmtCooperateModel, CRMConstants.COOPERATE_MODEL_GROUP_ID);// 合作模式

		setBaseDataFilter(prmtWorkArea, CRMConstants.WORK_AREA_GROUP_ID);// 工作区域
		setBaseDataFilter(prmtHabitationArea, CRMConstants.HABITATION_AREA_GROUP_ID);// 居住区域
		if (this.getUIContext().get("sellProject") == null) {
			getUIContext().put("sellProject", editData.getSellProject());
		}

		EntityViewInfo view = NewCommerceHelper.getPermitSalemanView(editData.getSellProject(), false);
		this.prmtFirstConsultant.setEntityViewInfo(view);

		setEnableFilter(prmtBookedPlace);
		setEnableFilter(prmtCountry);
		setEnableFilter(prmtProvince);
		setEnableFilter(prmtCity);
		setEnableFilter(prmtRegion);
		setEnableFilter(prmtNativePlace);

		initLinkmanTable();
		initNewCertificate();
		setVisableAboutCustomerType();

		if (editData != null) {
			SHECustomerInfo sheCustomerInfo = (SHECustomerInfo) this.editData;

			querySellBill(); // 查询单据
			// this.tblSellBill.setEditable(false);
			loadTblLinkmanFields(sheCustomerInfo); // 查询联系人

			queryQuestionPaper(); // 查询问卷

			queryCommerceChance();// 查询商机

			queryTrack(); // 查询跟进

			firstLinkMan = sheCustomerInfo.getFirstLinkman();
			corporate = sheCustomerInfo.getCorporate();
			corporateTel = sheCustomerInfo.getCorporateTel();
		}
		customerNumberChange();
		
		//wyh 新增状态下添加参数
		if(!this.oprtState.equals(OprtState.ADDNEW)){
			initKDPCustomerDetial();
			this.prmtFirstConsultant.setEnabled(false);
			if(this.f7Recommended.getValue()!=null){
				this.f7Recommended.setEnabled(false);
			}
		}
		if(this.oprtState.equals(OprtState.ADDNEW)){
			isNew = true;
			if(this.getUIContext().get("isSignManage") != null){
				isSignManage = this.getUIContext().get("isSignManage").toString();
			}
			if(isSignManage.equals("yes")){
				this.btnQuestion.setEnabled(false);
				if(this.getUIContext().get("selectCustomer") != null){
					List list = (ArrayList)this.getUIContext().get("selectCustomer");
					for(int i=0;i<list.size();i++){
						SHECustomerLinkManInfo linkManinfo = new SHECustomerLinkManInfo();
						IRow row = (IRow) list.get(i);
						IRow linkMan = tblLinkman.addRow();
						SHECustomerInfo sci = (SHECustomerInfo)row.getCell("id").getValue();
						linkManinfo.putAll(sci);
						linkManinfo.setIsAssociation(true);
						linkManinfo.setId(null);
						addLinkManRow(linkMan, linkManinfo);
					}
				}
			}
		}

		//扩展信息与置业顾问隐藏 wyh
		tblNew.remove(panelExtend);
		tblNew.remove(panelPropertyConsultant);
		
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
	
	
		EntityViewInfo review = new EntityViewInfo();
		FilterInfo refilter = new FilterInfo();
		refilter.getFilterItems().add(new FilterItemInfo("sellProject.id", editData.getSellProject().getId()));
		refilter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		review.setFilter(refilter);
		this.f7Recommended.setEntityViewInfo(review);
		
		this.tblLinkman.getColumn("number").getStyleAttributes().setHided(true);
	}
	
	public void initCom(){
		
		this.comboCustomerType.addItems(CustomerTypeEnum.getEnumList().toArray());
		
		contPhone.getBoundLabel().setForeground(Color.BLUE);
		contTel.getBoundLabel().setForeground(Color.BLUE);
		contOfficeTel.getBoundLabel().setForeground(Color.BLUE);
		contFax.getBoundLabel().setForeground(Color.BLUE);

		this.actionFirst.setEnabled(false);
		this.actionPre.setEnabled(false);
		this.actionNext.setEnabled(false);
		this.actionLast.setEnabled(false);
		this.actionAddNew.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.btnAddNew.setVisible(false);
		this.btnRemove.setVisible(false);
		this.txtNumber.setEnabled(false);
		this.contPropertyConsultant.setBoundLabelText("置业顾问");
		
		this.btnQuestion.setVisible(false);
	}

	/**
	 * 把证件类型换成动态的下拉框形式
	 */
	private void initNewCertificate() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (this.editData.getCustomerType().equals(CustomerTypeEnum.PERSONALCUSTOMER)) {
			filter.getFilterItems().add(new FilterItemInfo("group.id", CRMConstants.PE_CERTIFICATE_TYPE_GROUP_ID));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("group.id", CRMConstants.EN_CERTIFICATE_TYPE_GROUP_ID));
		}
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("name"));
		coll.add(new SelectorItemInfo("number"));
		coll.add(new SelectorItemInfo("description"));
		coll.add(new SelectorItemInfo("isDefault"));
		view.setSelector(coll);
		try {
			String select="";
			FDCCusBaseDataCollection dataColl = FDCCusBaseDataFactory.getRemoteInstance().getFDCCusBaseDataCollection(view);
			boolean isDefault = false;
			for (int i = 0; i < dataColl.size(); i++) {
				FDCCusBaseDataInfo info = dataColl.get(i);
				this.comboCertificateType.addItem(info.getName());
				if (info.isIsDefault()) {
					isDefault = true;
					select=info.getName();
				}
			}
			if (!isDefault) {
				this.comboCertificateType.setSelectedItem(null);
			}else{
				this.comboCertificateType.setSelectedItem(select);
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		if (this.editData.getCertificateType() != null) {
			view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("name");
			sic.add("number");
			view.setSelector(sic);
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", this.editData.getCertificateType().getId().toString()));
			view.setFilter(filter);
			try {
				FDCCusBaseDataCollection baseDatacoll = FDCCusBaseDataFactory.getRemoteInstance().getFDCCusBaseDataCollection(view);
				if (baseDatacoll != null && baseDatacoll.size() > 0) {
					for (int i = 0; i < baseDatacoll.size(); i++) {
						FDCCusBaseDataInfo info = baseDatacoll.get(i);
						this.comboCertificateType.setSelectedItem(info.getName());
					}
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}

	private void setEnableFilter(KDBizPromptBox f7) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("deletedStatus", new Integer(DeletedStatusEnum.NORMAL_VALUE)));
		view.setFilter(filter);
		f7.setEntityViewInfo(view);
	}

	private void setBaseDataFilter(KDBizPromptBox f7, String groupId) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("group.id", groupId));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		f7.setEntityViewInfo(view);
	}

	protected IObjectValue createNewData() {
		SHECustomerInfo sheCustomerInfo = new SHECustomerInfo();
		sheCustomerInfo.setIsPublic(false);
		sheCustomerInfo.setContactPreferences(ContactPreferencesEnum.ANY);
		sheCustomerInfo.setCreateWay(CreateWayEnum.HAND);// 建立方式:手工建立
		sheCustomerInfo.setIsEnabled(true);// 默认启用
		try {
			CountryCollection col=CountryFactory.getRemoteInstance().getCountryCollection("select * from where name='中国'");
			if(col.size()>0){
				CountryInfo country=col.get(0);
				sheCustomerInfo.setCountry(country);
				sheCustomerInfo.setBookedPlace(country);
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		sheCustomerInfo.setIdentity((FDCCusBaseDataInfo) defValues.get(CRMConstants.CUSTOMER_STATUS_GROUP_ID));
		sheCustomerInfo.setCustomerOrigin((FDCCusBaseDataInfo) defValues.get(CRMConstants.CUSTOMER_ORIGIN_GROUP_ID));
		sheCustomerInfo.setHabitationStatus((FDCCusBaseDataInfo) defValues.get(CRMConstants.HABITATION_STATUS_GROUP_ID));
		sheCustomerInfo.setEnterpriceProperty((FDCCusBaseDataInfo) defValues.get(CRMConstants.ENTERPRICE_PROPERTY_GROUP_ID));
		sheCustomerInfo.setEnterpriceSize((FDCCusBaseDataInfo) defValues.get(CRMConstants.ENTERPRICE_SIZE_GROUP_ID));

		sheCustomerInfo.setCooperateModel((FDCCusBaseDataInfo) defValues.get(CRMConstants.COOPERATE_MODEL_GROUP_ID));
		sheCustomerInfo.setWorkArea((SHECusAssistantDataInfo) defValues.get(CRMConstants.WORK_AREA_GROUP_ID));
		sheCustomerInfo.setHabitationArea((SHECusAssistantDataInfo) defValues.get(CRMConstants.HABITATION_AREA_GROUP_ID));
		if (sheCustomerInfo.getCertificateType() == null) {
			sheCustomerInfo.setCertificateType((FDCCusBaseDataInfo) (CustomerTypeEnum.PERSONALCUSTOMER.equals(comboCustomerType.getSelectedItem()) ? defValues
					.get(CRMConstants.PE_CERTIFICATE_TYPE_GROUP_ID) : defValues.get(CRMConstants.EN_CERTIFICATE_TYPE_GROUP_ID)));
		}
		Map uiContext = this.getUIContext();
		FDCMainCustomerInfo mainCus = (FDCMainCustomerInfo) uiContext.get("mainCus");
		if (mainCus != null) {
			FDCMainCustomerInfo cloneMainCus = (FDCMainCustomerInfo) mainCus.clone();
			cloneMainCus.setId(null);
			CRMHelper.changeObjectValue(cloneMainCus, sheCustomerInfo, false);
			sheCustomerInfo.setMainCustomer(mainCus);
			sheCustomerInfo.setCreateWay(CreateWayEnum.PARENTREFERENCE);
		} else {
			sheCustomerInfo.setCustomerType((CustomerTypeEnum) uiContext.get("customerType"));
			sheCustomerInfo.setName((String) uiContext.get("cusName"));
			sheCustomerInfo.setPhone((String) uiContext.get("cusPhone"));
			sheCustomerInfo.setCertificateNumber((String) uiContext.get("cusCertificateNum"));
		}
		FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo();
		sheCustomerInfo.setCreator((UserInfo) (SysContext.getSysContext().getCurrentUserInfo()));
		sheCustomerInfo.setCreateTime(new Timestamp(new Date().getTime()));
		sheCustomerInfo.setCreateUnit(orgUnit);
		sheCustomerInfo.setLastUpdateUser((UserInfo) ((UserInfo) (SysContext.getSysContext().getCurrentUserInfo())));
		sheCustomerInfo.setLastUpdateTime(new Timestamp(new Date().getTime()));
		sheCustomerInfo.setLastUpdateUnit(orgUnit);
		SellProjectInfo sellProjectInfo = (SellProjectInfo) this.getUIContext().get("sellProject");
		if (sellProjectInfo != null) {
			sheCustomerInfo.setSellProject(sellProjectInfo);
		}

		if (this.getUIContext().get("cluesCustomer") != null) {
			CluesManageInfo cluesInfo = (CluesManageInfo) this.getUIContext().get("cluesCustomer");
			String name = this.getUIContext().get("cusName").toString();
			String phone = this.getUIContext().get("cusPhone").toString().trim();
			if (this.getUIContext().get("cusCertificateNum") != null && !this.getUIContext().get("cusCertificateNum").toString().trim().equals("")) {
				String cusCertificateNum = this.getUIContext().get("cusCertificateNum").toString().trim();
				sheCustomerInfo.setCertificateNumber(cusCertificateNum);
			}
			sheCustomerInfo.setName(name);
			sheCustomerInfo.setPhone(phone);
			sheCustomerInfo.setClues(cluesInfo);

			if (this.getUIContext().get("firstLinkMan") != null && !this.getUIContext().get("firstLinkMan").toString().trim().equals("")) {
				String firstLinkMan = this.getUIContext().get("firstLinkMan").toString().trim();
				sheCustomerInfo.setFirstLinkman(firstLinkMan);
			}

		}

		if (this.getUIContext().get("propertyConsultant") != null) {
			UserInfo userInfo = (UserInfo) this.getUIContext().get("propertyConsultant");
			// sheCustomerInfo.setPropertyConsultant(userInfo);
			sheCustomerInfo.setFirstConsultant(userInfo);
		} else {
			// sheCustomerInfo.setPropertyConsultant(SysContext.getSysContext().getCurrentUserInfo());
			try {
				UserInfo userInfo = getPropertyConsultant();
				sheCustomerInfo.setFirstConsultant(userInfo);// 首次接待顾问
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
//		try {
//			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
//			String retNumber;
//			retNumber = iCodingRuleManager.getNumber(sheCustomerInfo, orgUnit.getId().toString());
//			sheCustomerInfo.setNumber(retNumber);
//		} catch (BOSException e) {
//			e.printStackTrace();
//		} catch (EASBizException e) {
//			e.printStackTrace();
//		}
		sheCustomerInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return sheCustomerInfo;
	}

	/**
	 * 首次接待顾问
	 * 如当前操作员为当前登录组织中某一营销团队成员（非负责人），则默认为当前操作员为首次接待顾问，如当前操作员不为营销团队成员（非负责人），则默认值为空
	 * 
	 * @return
	 * @throws BOSException
	 */
	private UserInfo getPropertyConsultant() throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		UserInfo currentInfo = SysContext.getSysContext().getCurrentUserInfo();
		Set idSet = this.getCurrentUnitId();
		filter.getFilterItems().add(new FilterItemInfo("head.id", idSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isDuty", Boolean.FALSE, CompareType.EQUALS));
		view.setFilter(filter);
		MarketingUnitMemberCollection memberColl = MarketingUnitMemberFactory.getRemoteInstance().getMarketingUnitMemberCollection(view);
		if (memberColl != null && memberColl.size() > 0) {
			for (int i = 0; i < memberColl.size(); i++) {
				if (currentInfo.getId().equals(memberColl.get(i).getMember().getId())) {
					return currentInfo;
				}
			}
		}
		return null;
	}

	private Set getCurrentUnitId() throws BOSException {
		FullOrgUnitInfo currentUnit = SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", currentUnit.getId(), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE, CompareType.EQUALS));
		view.setFilter(filter);
		MarketingUnitCollection coll = MarketingUnitFactory.getRemoteInstance().getMarketingUnitCollection(view);
		Set idSet = new HashSet();
		if (coll != null && coll.size() > 0) {
			for (int i = 0; i < coll.size(); i++) {
				idSet.add(coll.get(i).getId());
			}
		}
		return idSet;
	}

	// /**
	// * 首次接待顾问
	// *
	// 如当前操作员为当前登录组织中某一营销团队成员（非负责人），则默认为当前操作员为首次接待顾问，如当前操作员不为营销团队成员（非负责人），则默认值为空
	// * @return
	// * @throws BOSException
	// */
	// private EntityViewInfo getPermitView() throws BOSException{
	// UserInfo currentInfo = SysContext.getSysContext().getCurrentUserInfo();
	// EntityViewInfo view = new EntityViewInfo();
	// FilterInfo filter = new FilterInfo();
	// if(getPropertyConsultant() == null){
	// //根据项目过滤出当前登录组织中包含该项目的营销团队下的所有成员（非负责人）
	// SellProjectInfo sellProject =
	// ((SHECustomerInfo)this.editData).getSellProject();
	// filter.getFilterItems().add(new FilterItemInfo("sellProject.id",
	// sellProject.getId(), CompareType.EQUALS));
	// view.setFilter(filter);
	// MarketingUnitSellProjectCollection sellProjectColl =
	// MarketingUnitSellProjectFactory.getRemoteInstance().getMarketingUnitSellProjectCollection(view);
	// Set idSet = new HashSet();
	// if(sellProjectColl != null && sellProjectColl.size() > 0){
	// for(int i=0;i<sellProjectColl.size();i++){
	// String id = sellProjectColl.get(i).getHead().getId().toString();
	// EntityViewInfo tempView = new EntityViewInfo();
	// FilterInfo tempFilter = new FilterInfo();
	// FullOrgUnitInfo currentUnit =
	// SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo();
	// tempFilter.getFilterItems().add(new FilterItemInfo("id", id,
	// CompareType.EQUALS));
	// tempFilter.getFilterItems().add(new FilterItemInfo("isEnabled",
	// Boolean.TRUE, CompareType.EQUALS));
	// tempFilter.getFilterItems().add(new FilterItemInfo("orgUnit.id",
	// currentUnit.getId(), CompareType.EQUALS));
	// tempView.setFilter(tempFilter);
	// MarketingUnitCollection unitColl =
	// MarketingUnitFactory.getRemoteInstance().getMarketingUnitCollection(tempView);
	// if(unitColl != null && unitColl.size() > 0){
	// idSet.add(id);
	// }
	// }
	// }
	//			
	// view = new EntityViewInfo();
	// filter = new FilterInfo();
	// filter.getFilterItems().add(new FilterItemInfo("head.id", idSet,
	// CompareType.INCLUDE));
	// filter.getFilterItems().add(new FilterItemInfo("isDuty", Boolean.FALSE,
	// CompareType.EQUALS));
	// view.setFilter(filter);
	// MarketingUnitMemberCollection memberColl =
	// MarketingUnitMemberFactory.getRemoteInstance().getMarketingUnitMemberCollection(view);
	// Set memerIdSet = new HashSet();
	// if(memberColl != null && memberColl.size() > 0){
	// for(int j=0;j<memberColl.size();j++){
	// memerIdSet.add(memberColl.get(j).getMember().getId().toString());
	// }
	// }
	// view = new EntityViewInfo();
	// filter = new FilterInfo();
	// filter.getFilterItems().add(new FilterItemInfo("id", memerIdSet,
	// CompareType.INCLUDE));
	// }else{
	// filter.getFilterItems().add(new
	// FilterItemInfo("id",currentInfo.getId(),CompareType.EQUALS));
	// }
	// view.setFilter(filter);
	// return view;
	// }

	public void loadFields() {
		isLoadField = true;
		super.loadFields();
		SHECustomerInfo sheCustomerInfo = (SHECustomerInfo) this.editData;
		this.txtNumber.setText(sheCustomerInfo.getNumber());
		this.comboCustomerType.setSelectedItem(sheCustomerInfo.getCustomerType());
		this.txtName.setText(sheCustomerInfo.getName());
		this.prmtBookedPlace.setValue(sheCustomerInfo.getBookedPlace());
		this.txtCode.setText(sheCustomerInfo.getCode());

		this.txtSimpleCode.setText(sheCustomerInfo.getSimpleCode());
		this.txtPhone.setText(sheCustomerInfo.getPhone());
		this.txtTel.setText(sheCustomerInfo.getTel());
		this.txtOfficeTel.setText(sheCustomerInfo.getOfficeTel());
		this.txtFax.setText(sheCustomerInfo.getFax());

		this.txtOtherTel.setText(sheCustomerInfo.getOtherTel());
		this.txtEmail.setText(sheCustomerInfo.getEmail());
		// this.prmtCertificateType.setValue(sheCustomerInfo.getCertificateType()
		// );
		this.comboCertificateType.setSelectedItem(sheCustomerInfo.getCertificateType());
		this.txtCertificateNumber.setText(sheCustomerInfo.getCertificateNumber());
		this.pkDayOfbirth.setValue(sheCustomerInfo.getDayOfbirth());

		this.comboSex.setSelectedItem(sheCustomerInfo.getSex());
		this.prmtIdentity.setValue(sheCustomerInfo.getIdentity());
		this.prmtCountry.setValue(sheCustomerInfo.getCountry());
		this.prmtProvince.setValue(sheCustomerInfo.getProvince());
		this.prmtCity.setValue(sheCustomerInfo.getCity());

		this.prmtRegion.setValue(sheCustomerInfo.getRegion());
		this.txtMailAddress.setText(sheCustomerInfo.getMailAddress());
		this.txtBookedAddress.setText(sheCustomerInfo.getBookedAddress());
		this.txtPostalCode.setText(sheCustomerInfo.getPostalCode());
		this.txtFirstLinkman.setText(sheCustomerInfo.getFirstLinkman());

		this.prmtIndustry.setValue(sheCustomerInfo.getIndustry());
		this.prmtEnterpriceProperty.setValue(sheCustomerInfo.getEnterpriceProperty());
		this.txtCorporate.setText(sheCustomerInfo.getCorporate());
		this.txtCorporateTel.setText(sheCustomerInfo.getCorporateTel());
		this.prmtCustomerOrigin.setValue(sheCustomerInfo.getCustomerOrigin());
		this.prmtFirstConsultant.setValue(sheCustomerInfo.getPropertyConsultant());
		this.prmtNativePlace.setValue(sheCustomerInfo.getNativePlace());
		this.comboMaritalStatus.setSelectedItem(sheCustomerInfo.getMaritalStatus());
		this.prmtHabitationStatus.setValue(sheCustomerInfo.getHabitationStatus());
		txtWorkCompany.setText(sheCustomerInfo.getWorkCompany());
		comboContactPreferences.setSelectedItem(sheCustomerInfo.getContactPreferences());

		txtMotorcycles.setText(sheCustomerInfo.getMotorcycles());
		prmtEnterpriceSize.setValue(sheCustomerInfo.getEnterpriceSize());
		txtBookedCapital.setText(sheCustomerInfo.getBookedCapital());
		txtBusinessScope.setText(sheCustomerInfo.getBusinessScope());
		txtEmployees.setText(sheCustomerInfo.getEmployees());

		txtTaxRegistrationNoG.setText(sheCustomerInfo.getTaxRegistrationNoG());
		txtTaxRegistrationNoD.setText(sheCustomerInfo.getTaxRegistrationNoD());
		prmtCooperateModel.setValue(sheCustomerInfo.getCooperateModel());

		prmtCreateUnit.setValue(sheCustomerInfo.getCreateUnit());
		prmtCreator.setValue(sheCustomerInfo.getCreator());
		pkCreateTime.setValue(sheCustomerInfo.getCreateTime());

		prmtLastUpdateUnit.setValue(sheCustomerInfo.getLastUpdateUnit());

		if (!STATUS_ADDNEW.equals(getOprtState()) && sheCustomerInfo.getLastUpdateUnit() == null) {
			prmtLastUpdateUnit.setValue(SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo());
		}
		if(STATUS_ADDNEW.equals(getOprtState())){
			this.prmtFirstConsultant.setValue(SysContext.getSysContext().getCurrentUserInfo());
		}
		prmtLastUpdateUser.setValue(sheCustomerInfo.getLastUpdateUser());
		pkLastUpdateTime.setValue(sheCustomerInfo.getLastUpdateTime());

		comboCreateWay.setSelectedItem(sheCustomerInfo.getCreateWay());
		prmtProject.setValue(sheCustomerInfo.getSellProject());

		boolean isView = "VIEW".equals(this.getOprtState()) || "FINDVIEW".equals(this.getOprtState());
		this.btnAddLinkman.setEnabled(!isView);
		this.btnRemoveLinkman.setEnabled(!isView);
		this.checkAll.setAccessAuthority(isView ? CtrlCommonConstant.AUTHORITY_COMMON : CtrlCommonConstant.AUTHORITY_READ_ONLY);
		this.checkAll.setEnabled(isView);
		this.checkAll.setEditable(isView);
		// if("EDIT".equals(this.getOprtState()) ||
		// "VIEW".equals(this.getOprtState())){
		// this.prmtFirstConsultant.setEnabled(false);
		// }

		isLoadField = false;
		initOldData(editData);
	}

	public void storeFields() {
		super.storeFields();
		SHECustomerInfo sheCustomerInfo = (SHECustomerInfo) this.editData;

		sheCustomerInfo.setNumber(this.txtNumber.getText().trim());
		sheCustomerInfo.setCustomerType((CustomerTypeEnum) this.comboCustomerType.getSelectedItem());
		sheCustomerInfo.setName(this.txtName.getText().trim());
		sheCustomerInfo.setBookedPlace((CountryInfo) this.prmtBookedPlace.getValue());
		sheCustomerInfo.setCode(this.txtCode.getText().trim());

		sheCustomerInfo.setSimpleCode(this.txtSimpleCode.getText().trim());
		sheCustomerInfo.setPhone(this.txtPhone.getText().trim());
		sheCustomerInfo.setTel(this.txtTel.getText().trim());
		sheCustomerInfo.setOfficeTel(this.txtOfficeTel.getText().trim());
		sheCustomerInfo.setFax(this.txtFax.getText().trim());

		sheCustomerInfo.setOtherTel(this.txtOtherTel.getText().trim());
		sheCustomerInfo.setEmail(this.txtEmail.getText().trim());
		// sheCustomerInfo
		// .setCertificateType((FDCCusBaseDataInfo) this.prmtCertificateType
		// .getValue());
		sheCustomerInfo.setCertificateNumber(this.txtCertificateNumber.getText().trim());
		sheCustomerInfo.setDayOfbirth(this.pkDayOfbirth.getTimestamp());

		sheCustomerInfo.setSex((SexEnum) this.comboSex.getSelectedItem());
		sheCustomerInfo.setIdentity((FDCCusBaseDataInfo) this.prmtIdentity.getValue());
		sheCustomerInfo.setCountry((CountryInfo) this.prmtCountry.getValue());
		sheCustomerInfo.setProvince((ProvinceInfo) this.prmtProvince.getValue());
		sheCustomerInfo.setCity((CityInfo) this.prmtCity.getValue());

		sheCustomerInfo.setRegion((RegionInfo) this.prmtRegion.getValue());
		sheCustomerInfo.setMailAddress(this.txtMailAddress.getText().trim());
		sheCustomerInfo.setBookedAddress(this.txtBookedAddress.getText().trim());
		sheCustomerInfo.setPostalCode(this.txtPostalCode.getText().trim());
		sheCustomerInfo.setFirstLinkman(this.txtFirstLinkman.getText().trim());

		sheCustomerInfo.setIndustry((IndustryInfo) this.prmtIndustry.getValue());
		sheCustomerInfo.setEnterpriceProperty((FDCCusBaseDataInfo) this.prmtEnterpriceProperty.getValue());
		sheCustomerInfo.setCorporate(this.txtCorporate.getText().trim());
		sheCustomerInfo.setCorporateTel(this.txtCorporateTel.getText().trim());
		sheCustomerInfo.setCustomerOrigin((FDCCusBaseDataInfo) this.prmtCustomerOrigin.getValue());

		sheCustomerInfo.setNativePlace((RegionInfo) this.prmtNativePlace.getValue());
		sheCustomerInfo.setMaritalStatus((MaritalStatusEnum) this.comboMaritalStatus.getSelectedItem());
		sheCustomerInfo.setHabitationStatus((FDCCusBaseDataInfo) this.prmtHabitationStatus.getValue());
		sheCustomerInfo.setWorkCompany(this.txtWorkCompany.getText().trim());
		sheCustomerInfo.setContactPreferences((ContactPreferencesEnum) this.comboContactPreferences.getSelectedItem());

		sheCustomerInfo.setMotorcycles(this.txtMotorcycles.getText().trim());
		sheCustomerInfo.setEnterpriceSize((FDCCusBaseDataInfo) this.prmtEnterpriceSize.getValue());
		sheCustomerInfo.setBookedCapital(this.txtBookedCapital.getText().trim());
		sheCustomerInfo.setBusinessScope(this.txtBusinessScope.getText().trim());
		sheCustomerInfo.setEmployees(this.txtEmployees.getText().trim());

		sheCustomerInfo.setTaxRegistrationNoG(this.txtTaxRegistrationNoG.getText().trim());
		sheCustomerInfo.setTaxRegistrationNoD(this.txtTaxRegistrationNoD.getText().trim());
		sheCustomerInfo.setCooperateModel((FDCCusBaseDataInfo) this.prmtCooperateModel.getValue());
		//
		sheCustomerInfo.setCreateUnit((FullOrgUnitInfo) this.prmtCreateUnit.getValue());
		sheCustomerInfo.setCreator((UserInfo) this.prmtCreator.getValue());
		sheCustomerInfo.setCreateTime(this.pkCreateTime.getTimestamp());
		sheCustomerInfo.setLastUpdateUnit((FullOrgUnitInfo) this.prmtLastUpdateUnit.getValue());
		sheCustomerInfo.setLastUpdateUser((UserInfo) this.prmtLastUpdateUser.getValue());
		sheCustomerInfo.setLastUpdateTime(this.pkLastUpdateTime.getTimestamp());
		sheCustomerInfo.setCreateWay((CreateWayEnum) this.comboCreateWay.getSelectedItem());
		sheCustomerInfo.setSellProject((SellProjectInfo) this.prmtProject.getValue());
		sheCustomerInfo.setFirstConsultant((UserInfo) this.prmtFirstConsultant.getValue());

//		sheCustomerInfo.remove("linkman");
		// 证件类型存id
		if (this.comboCertificateType.getSelectedItem() != null) {
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("name");
			sic.add("number");
			view.setSelector(sic);
			FilterInfo filter = new FilterInfo();

			filter.getFilterItems().add(new FilterItemInfo("name", this.comboCertificateType.getSelectedItem().toString()));
			view.setFilter(filter);
			try {
				FDCCusBaseDataCollection coll = FDCCusBaseDataFactory.getRemoteInstance().getFDCCusBaseDataCollection(view);
				if (coll != null && coll.size() > 0) {
					for (int i = 0; i < coll.size(); i++) {
						FDCCusBaseDataInfo info = coll.get(i);
						sheCustomerInfo.setCertificateType(info);
					}
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		// storeLinkMan();
	}

	/**
	 * 联系人列表
	 */
	private void storeLinkMan() {
		if (this.editData.getLinkMan() != null) {
			this.editData.getLinkMan().clear();
		}

		for (int i = 0; i < this.tblLinkman.getRowCount(); i++) {
			IRow row = this.tblLinkman.getRow(i);
			SHECustomerLinkManInfo linkMan = new SHECustomerLinkManInfo();
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

			// if (row.getCell("certificateType").getValue() == null) {
			// linkMan.setCertificateType(null);
			// } else {
			// linkMan.setCertificateType((FDCCusBaseDataInfo) row.getCell(
			// "certificateType").getValue());
			// }
			if (row.getCell("certificateType").getValue() == null) {
				linkMan.setCertificateType(null);
			} else {
				EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("id");
				sic.add("name");
				sic.add("number");
				view.setSelector(sic);
				FilterInfo filter = new FilterInfo();

				filter.getFilterItems().add(new FilterItemInfo("name", row.getCell("certificateType").getValue()));
				view.setFilter(filter);
				try {
					FDCCusBaseDataCollection baseDatacoll = FDCCusBaseDataFactory.getRemoteInstance().getFDCCusBaseDataCollection(view);
					if (baseDatacoll != null && baseDatacoll.size() > 0) {
						for (int j = 0; j < baseDatacoll.size(); j++) {
							FDCCusBaseDataInfo info = baseDatacoll.get(j);
							linkMan.setCertificateType(info);
							// linkMan.getCertificateType().setSelectedItem(info.
							// getName());
						}
					}
				} catch (BOSException e) {
					e.printStackTrace();
				}

				if (row.getCell("certificateType").getValue() != null && "身份证".equals(row.getCell("certificateType").getValue())) {
					Object obj = row.getCell("certificateNumber").getValue();
					if (obj != null) {
						String cerNum = obj.toString().trim();
						if (cerNum != null && !cerNum.equals("") && (cerNum.length() != 15 && cerNum.length() != 18)) {
							MsgBox.showWarning("联系人身份证号码长度必须为15或18位");
							this.abort();
						}
						if (cerNum != null && !cerNum.equals("")) {
							try {
								String m = IDCard.IDCardValidate(cerNum);
								if (m != null && m.length() != 0) {
									MsgBox.showWarning(m);
									this.abort();
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

			if (row.getCell("principalNo").getValue() == null) {
				linkMan.setPrincipalNo(null);
			} else {
				linkMan.setPrincipalNo(row.getCell("principalNo").getValue().toString());
			}

//			if (row.getCell("number").getValue() == null) {
//				MsgBox.showWarning("联系人编码不能为空!");
//				this.abort();
//			} else {
//				linkMan.setNumber(row.getCell("number").getValue().toString());
//			}

			if (row.getCell("name").getValue() == null) {
				MsgBox.showWarning("联系人姓名不能为空!");// 判断姓名重复
				this.abort();
			} else {
				linkMan.setName(row.getCell("name").getValue().toString());
			}

			if (row.getCell("relation").getValue() == null) {
				MsgBox.showWarning("关系不能为空!");
				this.abort();
			} else {
				linkMan.setRelation(row.getCell("relation").getValue().toString());
			}

			if (row.getCell("phone").getValue() == null && row.getCell("tel").getValue() == null && row.getCell("officeTel").getValue() == null && row.getCell("fax").getValue() == null) {
				MsgBox.showWarning("联系人移动电话、住宅电话、办公电话、传真至少录入一个!");
				this.abort();
			} else {
				if (row.getCell("phone").getValue() == null) {
					linkMan.setPhone(null);
				} else {
					if (row.getCell("phone").getValue().toString().trim().length() < 7) {
						MsgBox.showInfo(this, "移动电话至少需要输入7位以上!");
						this.abort();
					} else {
						linkMan.setPhone(row.getCell("phone").getValue().toString());
					}
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
				linkMan.setDayOfBirth(FDCDateHelper.DateToString((Date) row.getCell("dayOfBirth").getValue()));
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
			if (row.getCell("id").getValue() == null) {
				linkMan.setId(null);
			} else {
				linkMan.setId((BOSUuid) row.getCell("id").getValue());
			}
			this.editData.getLinkMan().add(linkMan);
		}
	}

	/**
	 * 判断联系人列表中是否存在不是的当前用户的联系人
	 * 
	 * @param row
	 * @return
	 * @throws Exception
	 */
	private void beforeSubmit() throws Exception {
		for (int i = 0; i < this.tblLinkman.getRowCount(); i++) {
			IRow row = this.tblLinkman.getRow(i);
			if (row != null) {
				if (row.getCell("id").getValue() != null) {
					String linkManId = row.getCell("id").getValue().toString();

					SHECustomerLinkManInfo info = SHECustomerLinkManFactory.getRemoteInstance().getSHECustomerLinkManInfo("select sheCustomer.id as customerId where id = '" + linkManId + "'");
					if (info != null) {
						if (!info.getSheCustomer().getId().equals(this.editData.getId())) {
							MsgBox.showWarning("非当前客户直接关联的联系人不允许保存!");
							this.abort();
							;
						}
					}
				}
			}
		}

	}

	// private void verifyLinkManNameAndNumber(IRow row,String cellString,String
	// msg){
	// EntityViewInfo view = new EntityViewInfo();
	// SelectorItemCollection sic = new SelectorItemCollection();
	// sic.add("id");
	// sic.add("name");
	// sic.add("number");
	// view.setSelector(sic);
	// FilterInfo filter = new FilterInfo();
	// filter.getFilterItems().add(
	// new FilterItemInfo(cellString,
	// row.getCell(cellString).getValue().toString()));
	// if(row.getCell("id").getValue() != null){
	// filter.getFilterItems().add(
	// new FilterItemInfo("id",
	// row.getCell("id").getValue().toString(),CompareType.NOTEQUALS));
	// }
	//		
	// if (editData != null && editData.getId() != null) {
	// String id = this.editData.getId().toString();
	// filter.getFilterItems().add(
	// new FilterItemInfo("sheCustomer.id", id));
	// view.setFilter(filter);
	// SHECustomerLinkManCollection coll;
	// try {
	// coll = SHECustomerLinkManFactory
	// .getRemoteInstance().getSHECustomerLinkManCollection(
	// view);
	// if (coll != null && coll.size() > 0) {
	// MsgBox.showWarning(msg+"不能重复!");
	// this.abort();
	// }
	// } catch (BOSException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	protected ICoreBase getBizInterface() throws Exception {
		// if(STATUS_VIEW.equals(getOprtState())){
		// return FDCMainCustomerFactory.getRemoteInstance();
		// }else{
		return SHECustomerFactory.getRemoteInstance();
		// }
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add(new SelectorItemInfo("id"));
		sels.add(new SelectorItemInfo("*"));
		sels.add(new SelectorItemInfo("mainCustomer.id"));
		sels.add(new SelectorItemInfo("mainCustomer.name"));
		sels.add(new SelectorItemInfo("mainCustomer.number"));
		sels.add(new SelectorItemInfo("certificateType.*"));
		sels.add("industry.name");
		sels.add("country.name");
		sels.add("province.name");
		sels.add("city.name");
		sels.add("region.name");
		sels.add("identity.name");
		sels.add("CustomerOrigin.name");
		sels.add("cooperateModel.name");
		sels.add("enterpriceProperty.name");
		sels.add("nativePlace.name");
		sels.add("enterpriceSize.name");
		sels.add("bookedPlace.name");
		sels.add("habitationStatus.name");
		sels.add("propertyConsultant.*");
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
		sels.add("CU.*");
		return sels;
	}

	protected void comboCustomerType_itemStateChanged(ItemEvent e) throws Exception {
		// super.comboCustomerType_itemStateChanged(e);
		setVisableAboutCustomerType();
	}

	private void setVisableAboutCustomerType() {
		boolean isPer = CustomerTypeEnum.PERSONALCUSTOMER.equals(this.comboCustomerType.getSelectedItem());
		this.contBookedPlace.setBoundLabelText(isPer ? "国籍" : "注册地");
		this.contMailAddress.setBoundLabelText(isPer ? "通信地址" : "公司地址");
		this.contBookedAddress.setBoundLabelText(isPer ? "证件地址" : "注册地址");
		this.contDayOfbirth.setBoundLabelText(isPer ? "出生日期" : "注册日期");
		this.personContainer.setVisible(isPer);
		this.enterpriceContainer.setVisible(!isPer);

		this.contSex.setVisible(isPer ? true : false);
		this.contCorporate.setVisible(isPer ? false : true);
		this.contIdentity.setVisible(isPer ? true : false);
		this.contCorporateTel.setVisible(isPer ? false : true);
		if (!isPer) {
			panelExtend.add(enterpriceContainer, new KDLayout.Constraints(10, 10, 980, 130, 261));// 企业客户显示位置
		}
		txtFirstLinkman.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		prmtIndustry.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.prmtEnterpriceProperty.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		txtFirstLinkman.setEnabled(!isPer);
		prmtIndustry.setEnabled(!isPer);
		prmtEnterpriceProperty.setEnabled(!isPer);
	}

	protected void initOldData(IObjectValue dataObject) {
		if (!STATUS_ADDNEW.equals(getOprtState())) {
			if (!this.isOld) {
				super.initOldData(dataObject);
			}
		} else {
			super.initOldData(dataObject);
		}
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		beforeSubmit();
		verify();
		this.isOld = true;
		state = false;
		boolean isAddCommerce = false;
		// 如果是新增
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			isNew = true;
			// 直接新增导向式用户必须填写问卷
			if (this.getUIContext().get("addnewDerict") != null) {
				type = this.getUIContext().get("addnewDerict").toString();
			}
			if (type.equals("derict") && isSignManage.equals("")) {
				SHECustomerInfo sheci = new SHECustomerInfo();
				editData.setId(BOSUuid.create(sheci.getBOSType()));
				this.getUIContext().put("customerId", (SHECustomerInfo)editData);
				this.getUIContext().put("HashMap", new HashMap());
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CommerceChangeNewEditUI.class.getName(), this.getUIContext(), null, "ADDNEW");
				uiWindow.show();
				if(this.getUIContext().get("HashMap") != null){
					if(((HashMap)this.getUIContext().get("HashMap")).get("CommerceChance") != null){
						isAddCommerce = true;
					}
					if(((HashMap)this.getUIContext().get("HashMap")).get("CommerceChance") == null){
						FDCMsgBox.showInfo("请填写并保存商机!");
						SysUtil.abort();
					}
				}
			}
		}
		else{
			isNew = false;
		}
		if (state) {
			SysUtil.abort();
		}
		//保存联动数据
		if(isAddCommerce){
			if(getActionFromActionEvent(e) == null && !checkClickTime())
	            return;
	        IObjectPK pk = null;
	        doBeforeSubmit(e);
	        if(UtilRequest.isPrepare("ActionSubmit", this))
	        {
	            IUIActionPostman post = prepareSubmit();
	            if(post != null)
	                post.callHandler();
	        }
	        pk = SHECustomerFactory.getRemoteInstance().submitAll((HashMap)this.getUIContext().get("HashMap"), editData);
	        doAfterSubmit(pk);
	        isNew = false;
		}else{
			super.actionSubmit_actionPerformed(e);
		}
		if(this.getUIContext().get("tranInfo")!=null){
			TranCustomerEntryInfo tranInfo=(TranCustomerEntryInfo)this.getUIContext().get("tranInfo");
			tranInfo.setCustomer(editData);
			tranInfo.setName(editData.getName());
			tranInfo.setAddress(editData.getMailAddress());
			tranInfo.setCertificate(editData.getCertificateType());
			tranInfo.setCertificateNumber(editData.getCertificateNumber());
			tranInfo.setPhone(editData.getPhone());
			tranInfo.setTel(editData.getTel());
		}
		
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		queryQuestionPaper();
		initKDPCustomerDetial();
		queryCommerceChance();
		queryTrack();
		
		this.prmtFirstConsultant.setEnabled(false);
		if(this.f7Recommended.getValue()!=null){
			this.f7Recommended.setEnabled(false);
		}
	}

	private void submitEnterpriceCustomer() throws Exception {
		if (CustomerTypeEnum.ENTERPRICECUSTOMER.equals(this.comboCustomerType.getSelectedItem())) {
			SHECustomerInfo info = (SHECustomerInfo) this.editData;
			String txtName = this.txtFirstLinkman.getText();
			String txtCorporate = this.txtCorporate.getText();
			String txtCorporateTel = this.txtCorporateTel.getText();
			boolean isCorporateChange = false;
			if (!FDCHelper.isEqual(corporate, info.getCorporate())) {
				isCorporateChange = true;
			}
			if (confirmFirstLinkMan()) {
				// 新增联系人客户前，先新增客户主线，用于联系人客户的关联
				FDCMainCustomerInfo mainCus = new FDCMainCustomerInfo();
				CRMHelper.changeObjectValue((IPropertyContainer) info.clone(), mainCus, true);
				mainCus.setId(BOSUuid.create(mainCus.getBOSType()));
				info.setMainCustomer(mainCus);
				SHECustomerFactory.getRemoteInstance().submitEnterpriceCustomer(info, txtName, null);
				MsgBox.showInfo("首选联系人客户资料保存成功");
				if (!isCorporateChange) {
					this.destroyWindow();
				} else {
					if (txtCorporate != null && !txtCorporate.equals("")) {
						if (confirmLegalPerson()) {
							mainCus = new FDCMainCustomerInfo();
							CRMHelper.changeObjectValue((IPropertyContainer) info.clone(), mainCus, true);
							mainCus.setId(BOSUuid.create(mainCus.getBOSType()));
							info.setMainCustomer(mainCus);
							SHECustomerFactory.getRemoteInstance().submitEnterpriceCustomer(info, txtCorporate, txtCorporateTel);
							MsgBox.showInfo("企业法人资料保存成功");
							this.destroyWindow();
						}
					}
				}
			}

			if (!FDCHelper.isEqual(corporateTel, info.getCorporateTel())) {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				SelectorItemCollection selector = new SelectorItemCollection();
				filter.getFilterItems().add(new FilterItemInfo("sheCustomer.id", info.getId()));
				filter.getFilterItems().add(new FilterItemInfo("name", info.getCorporate()));
				view.setFilter(filter);
				SHECustomerLinkManCollection linkManColl = SHECustomerLinkManFactory.getRemoteInstance().getSHECustomerLinkManCollection(view);
				if (linkManColl != null && linkManColl.size() > 0) {
					for (int i = 0; i < linkManColl.size(); i++) {
						SHECustomerLinkManInfo linkManInfo = linkManColl.get(i);
						selector.add("phone");
						linkManInfo.setPhone(info.getCorporateTel());
						SHECustomerLinkManFactory.getRemoteInstance().updatePartial(linkManInfo, selector);
					}
				}
			}
		} else {
			// 个人客户，看其是否是企业客户的首选联系人或企业法人
			SHECustomerInfo info = (SHECustomerInfo) this.editData;
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("name");
			selector.add("number");
			selector.add("relation");
			selector.add("sheCustomer.id");
			selector.add("sheCustomer.number");
			view.setSelector(selector);

			filter.getFilterItems().add(new FilterItemInfo("name", info.getName()));
			view.setFilter(filter);
			SHECustomerLinkManCollection linkColl = SHECustomerLinkManFactory.getRemoteInstance().getSHECustomerLinkManCollection(view);
			Set firstLinkIdSet = new HashSet();
			Set corporateIdSet = new HashSet();
			if (linkColl != null && linkColl.size() > 0) {
				for (int i = 0; i < linkColl.size(); i++) {
					SHECustomerLinkManInfo linkInfo = linkColl.get(i);
					if ("企业职员".equals(linkInfo.getRelation())) {
						firstLinkIdSet.add(linkInfo.getSheCustomer().getId());
					} else {
						corporateIdSet.add(linkInfo.getSheCustomer().getId());
					}
				}
			}

			// 首选练系人修改时，同步修改企业客户的移动电话、住宅电话
			Iterator it = firstLinkIdSet.iterator();
			while (it.hasNext()) {
				SHECustomerInfo enterpriceInfo = new SHECustomerInfo();
				String id = it.next().toString();
				enterpriceInfo = SHECustomerFactory.getRemoteInstance().getSHECustomerInfo(new ObjectStringPK(id));
				enterpriceInfo.setPhone(info.getPhone());
				enterpriceInfo.setTel(info.getTel());
				SHECustomerFactory.getRemoteInstance().submit(enterpriceInfo);
			}

			// 公司法人修改时，同步修改企业客户的公司法人、公司法人移动电话,
			Iterator its = corporateIdSet.iterator();
			while (its.hasNext()) {
				SHECustomerInfo enterpriceInfo = new SHECustomerInfo();
				String id = its.next().toString();
				enterpriceInfo = SHECustomerFactory.getRemoteInstance().getSHECustomerInfo(new ObjectStringPK(id));
				enterpriceInfo.setCorporate(info.getName());
				enterpriceInfo.setCorporateTel(info.getPhone());
				SHECustomerFactory.getRemoteInstance().submit(enterpriceInfo);
			}
		}
		this.loadFields();
	}

	private boolean confirmFirstLinkMan() throws BOSException {
		if ("ADDNEW".equals(this.getOprtState())) {
			if (MsgBox.isYes(MsgBox.showConfirm2(this, "是否生成首选联系人客户资料？"))) {
				return true;
			} else {
				// return false;
				this.destroyWindow();
			}
		} else {
			SHECustomerInfo info = (SHECustomerInfo) this.editData;
			if (!FDCHelper.isEqual(firstLinkMan, info.getFirstLinkman())) {
				if (MsgBox.isYes(MsgBox.showConfirm2(this, "是否生成新的首选联系人客户资料？"))) {
					return true;
				} else {
					// return false;
					this.destroyWindow();
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
				// return false;
			}
		} else {
			SHECustomerInfo info = (SHECustomerInfo) this.editData;
			if (!FDCHelper.isEqual(corporate, info.getCorporate())) {
				if (MsgBox.isYes(MsgBox.showConfirm2(this, "是否生成新的企业法人资料？"))) {
					return true;
				} else {
					this.destroyWindow();
					// return false;
				}
			}
		}
		return false;
	}

	//代替verifyInput
	public void verify() throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.comboCustomerType);

		// 企业客户，首选联系人必填
		if (this.comboCustomerType.getSelectedItem().equals(CustomerTypeEnum.ENTERPRICECUSTOMER)) {
			FDCClientVerifyHelper.verifyEmpty(this, this.txtFirstLinkman);
		}

		if (CusClientHelper.isImportNotNum(txtPhone) || CusClientHelper.isImportNotNum(txtTel) || CusClientHelper.isImportNotNum(txtOfficeTel) || CusClientHelper.isImportNotNum(txtFax)) {
			MsgBox.showWarning("移动电话、住宅电话、办公电话、传真只能录入数字!");
			this.abort();
		}

		// 四个电话，至少要填写一个
		if ((txtPhone.getText() == null || txtPhone.getText().equals("")) && (txtTel.getText() == null || txtTel.getText().equals(""))
				&& (txtOfficeTel.getText() == null || txtOfficeTel.getText().equals("")) && (txtFax.getText() == null || txtFax.getText().equals(""))) {
			MsgBox.showWarning("移动电话、住宅电话、办公电话、传真至少录入一个!");
			this.abort();
		}

		if (this.txtPhone.getText() != null && !this.txtPhone.getText().trim().equals("") && this.txtPhone.getText().trim().length() < 7) {
			MsgBox.showInfo(this, "移动电话至少需要输入7位以上!");
			this.abort();
		}

		if (CusClientHelper.isImportNotNum(txtPostalCode)) {
			MsgBox.showWarning("邮政编码只能录入数字!");
			this.abort();
		}

		FDCClientVerifyHelper.verifyEmpty(this, this.prmtFirstConsultant);
		String name = this.txtName.getText().trim();
		String phone = this.txtPhone.getText().trim();
		String certificateNumber = this.txtCertificateNumber.getText().trim();
		String certificateType = "";
		if (this.comboCertificateType.getSelectedItem() != null) {
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("name");
			sic.add("number");
			view.setSelector(sic);
			FilterInfo filter = new FilterInfo();

			filter.getFilterItems().add(new FilterItemInfo("name", this.comboCertificateType.getSelectedItem().toString()));
			view.setFilter(filter);
			try {
				FDCCusBaseDataCollection coll = FDCCusBaseDataFactory.getRemoteInstance().getFDCCusBaseDataCollection(view);
				if (coll != null && coll.size() > 0) {
					for (int i = 0; i < coll.size(); i++) {
						FDCCusBaseDataInfo info = coll.get(i);
						certificateType = info.getId().toString();
					}
				}
			} catch (BOSException ex) {
				ex.printStackTrace();
			}
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		String id = "";
		if(editData.getId() != null){
			id = editData.getId().toString();
		}

		if (editData.getSellProject() != null) {
			SellProjectInfo sellProject = editData.getSellProject();
			// 递归获取根项目
			sellProject = getParentProject(sellProject);
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId()));
		} else {
			SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
			sellProject = getParentProject(sellProject);
			if (sellProject != null) {
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId()));
			}
		}
		filter.getFilterItems().add(new FilterItemInfo("customerType", ((CustomerTypeEnum)this.comboCustomerType.getSelectedItem()).getValue()));
		// 新增时进行重名验证
		// if (STATUS_ADDNEW.equals(getOprtState())) {
		Map params = CusClientHelper.getCRMConstants();
		// 系统规则参数,在系统参数里看
		String rule = (String) params.get(CRMConstants.FDCSHE_PARAM_CUSREPEATRULE);
		if (!id.equals("")) {
			filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.NOTEQUALS));
		}
		if (CusClientHelper.CUSREPEATRULE_VALUE_NAME.equals(rule)) {
			if (name != null && !name.equals("")) {
				filter.getFilterItems().add(new FilterItemInfo("name", name, CompareType.EQUALS));
				if (SHECustomerFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("客户名称已经存在！不能重复");
					SysUtil.abort();
				}
			}
		} else if (CusClientHelper.CUSREPEATRULE_VALUE_TEL.equals(rule)) {
			// if (phone != null && !phone.equals("")) {

			FilterInfo tempInfo = getPhoneFilter();
			filter.mergeFilter(tempInfo, "AND");
			if (SHECustomerFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("联系方式已经存在！不能重复");
				SysUtil.abort();
			}
			// }
		} else if (CusClientHelper.CUSREPEATRULE_VALUE_CER.equals(rule)) {
			if (certificateNumber != null && !certificateNumber.equals("")) {
				filter.getFilterItems().add(new FilterItemInfo("certificateNumber", certificateNumber, CompareType.EQUALS));
				if (SHECustomerFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("证件号码已经存在！不能重复");
					SysUtil.abort();
				}
			}

		} else if (CusClientHelper.CUSREPEATRULE_VALUE_NAME_TEL.equals(rule)) {
			if (name != null && !name.equals("")) {
				filter.getFilterItems().add(new FilterItemInfo("name", name, CompareType.EQUALS));
				if (phone != null && !phone.equals("")) {
					filter.getFilterItems().add(new FilterItemInfo("phone", phone, CompareType.EQUALS));
				}	
				if (SHECustomerFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("客户名称、联系方式已经存在！不能重复");
					SysUtil.abort();
				}
				
			}
		} else if (CusClientHelper.CUSREPEATRULE_VALUE_NAME_CER.equals(rule)) {
			if (name != null && !name.equals("")) {
				filter.getFilterItems().add(new FilterItemInfo("name", name, CompareType.EQUALS));
				if (certificateNumber != null && !certificateNumber.equals("")) {
					filter.getFilterItems().add(new FilterItemInfo("certificateNumber", certificateNumber, CompareType.EQUALS));
				}

				if (SHECustomerFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("客户名称、证件号码已经存在！不能重复");
					SysUtil.abort();
				}
			}
		} //else if (CusClientHelper.CUSREPEATRULE_VALUE_TEL_CER.equals(rule)) {
		  else if (false) {
			// if (phone != null && !phone.equals("")) {

			if (certificateNumber != null && !certificateNumber.equals("")) {
				filter.getFilterItems().add(new FilterItemInfo("certificateNumber", certificateNumber, CompareType.EQUALS));
			}

			// filter.getFilterItems().add(
			// new FilterItemInfo("phone", phone, CompareType.EQUALS));

			FilterInfo tempInfo = getPhoneFilter();
			filter.mergeFilter(tempInfo, "AND");
			if (SHECustomerFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("联系方式、证件号码已经存在！不能重复");
				SysUtil.abort();
			}
			// }

		} else if (CusClientHelper.CUSREPEATRULE_VALUE_NAME_TEL_CER.equals(rule)) {
			if (name != null && !name.equals("")) {
				filter.getFilterItems().add(new FilterItemInfo("name", name, CompareType.EQUALS));
				// if (phone != null && !phone.equals("")) {
				// filter.getFilterItems().add(
				// new FilterItemInfo("phone", phone,
				// CompareType.EQUALS));
				if (certificateNumber != null && !certificateNumber.equals("")) {
					filter.getFilterItems().add(new FilterItemInfo("certificateNumber", certificateNumber, CompareType.EQUALS));
				}

				FilterInfo tempInfo = getPhoneFilter();
				filter.mergeFilter(tempInfo, "AND");
				// }

				if (SHECustomerFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("客户名称、联系方式、证件号码已经存在！不能重复");
					SysUtil.abort();
				}
			}
		} else if(CusClientHelper.CUSREPEATRULE_VALUE_TEL_OR_CER.equals(rule)){
			//如果电话号码与证件好,只要有一个不同,就说明是重复客户
			FilterInfo filter_certificateNumber = new FilterInfo();
			FilterInfo filter_Phone = new FilterInfo();
			if (certificateNumber != null && !certificateNumber.equals("")) {
				filter_certificateNumber.getFilterItems().add(new FilterItemInfo("certificateNumber", certificateNumber, CompareType.EQUALS));
				filter_certificateNumber.getFilterItems().add(new FilterItemInfo("certificatetype.id", certificateType, CompareType.EQUALS));
				filter_certificateNumber.mergeFilter(filter, "AND");
				if (SHECustomerFactory.getRemoteInstance().exists(filter_certificateNumber)) {
					MsgBox.showInfo("证件号码已经存在！不能重复");
					SysUtil.abort();
				}
			}
			if ((txtPhone.getText() != null && !txtPhone.getText().equals("")) || (txtTel.getText() != null && !txtTel.getText().equals(""))
						&& (txtOfficeTel.getText() != null && !txtOfficeTel.getText().equals("")) || (txtFax.getText() != null && !txtFax.getText().equals(""))) {
				FilterInfo tempInfo = getPhoneFilter();
				filter_Phone.mergeFilter(tempInfo, "AND");
				filter_Phone.mergeFilter(filter, "AND");
				if (SHECustomerFactory.getRemoteInstance().exists(filter_Phone)) {
					MsgBox.showInfo("联系方式已经存在！不能重复");
					SysUtil.abort();
				}
			}
		}

		storeLinkMan();

		if (CustomerTypeEnum.PERSONALCUSTOMER.equals(this.comboCustomerType.getSelectedItem())) {
			Object obj = this.comboCertificateType.getSelectedItem();
			if (obj != null) {
				String cerType = obj.toString();
				if (cerType != null && "身份证".equals(cerType)) {
					String cerNum = this.txtCertificateNumber.getText().trim();
					if (cerNum != null && !cerNum.equals("") && (cerNum.length() != 15 && cerNum.length() != 18)) {
						MsgBox.showWarning("客户身份证号码长度必须为15或18位");
						this.abort();
					}
					if (cerNum != null && !cerNum.equals("")) {
						String number = IDCard.IDCardValidate(cerNum);
						if (number != null && number.length() != 0) {
							MsgBox.showWarning(number);
							this.abort();
						}
					}
				}
			}
		}
	}
	
	/**
	 *校验
	 */
//	protected void verifyInput(ActionEvent e) throws Exception {
//		super.verifyInput(e);
//		
//	}

	private FilterInfo getPhoneFilter() throws BOSException {
		String phone = this.txtPhone.getText().trim();
		String tel = this.txtTel.getText().trim();
		String officeTel = this.txtOfficeTel.getText().trim();
		String fax = this.txtFax.getText().trim();

		FilterInfo tempFilter = new FilterInfo();
		FilterInfo telFilter = new FilterInfo();
		FilterInfo officeTelFilter = new FilterInfo();
		FilterInfo faxFilter = new FilterInfo();

		if (phone != null && !phone.equals("")) {
			tempFilter = getPhoneFilter(phone);
		}

		if (tel != null && !tel.equals("")) {
			telFilter = getPhoneFilter(tel);
			if (tempFilter != null) {
				tempFilter.mergeFilter(telFilter, "OR");
			}
		}

		if (officeTel != null && !officeTel.equals("")) {
			officeTelFilter = getPhoneFilter(officeTel);
			if (tempFilter != null) {
				tempFilter.mergeFilter(officeTelFilter, "OR");
			}
		}

		if (fax != null && !fax.equals("")) {
			faxFilter = getPhoneFilter(fax);
			if (tempFilter != null) {
				tempFilter.mergeFilter(faxFilter, "OR");
			}
		}

		return tempFilter;
	}

	private FilterInfo getPhoneFilter(String phone) throws BOSException {
		FilterInfo tempFilter = new FilterInfo();
		tempFilter.getFilterItems().add(new FilterItemInfo("phone", phone, CompareType.EQUALS));
		tempFilter.getFilterItems().add(new FilterItemInfo("tel", phone, CompareType.EQUALS));
		tempFilter.getFilterItems().add(new FilterItemInfo("officeTel", phone, CompareType.EQUALS));
		tempFilter.getFilterItems().add(new FilterItemInfo("fax", phone, CompareType.EQUALS));

		tempFilter.setMaskString(" #0 or #1 or #2 or #3");
		return tempFilter;
	}

	/**
	 * 递归获取根项目
	 * 
	 * @param sellProject
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private SellProjectInfo getParentProject(SellProjectInfo sellProject) throws EASBizException, BOSException {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("parent.id");
		sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProject.getId()));
		if (sellProject.getParent() != null) {
			sellProject = sellProject.getParent();
			getParentProject(sellProject);
		}
		return sellProject;
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		// VerifyEnabledById("修改");
		super.actionEdit_actionPerformed(e);
		this.btnAddLinkman.setEnabled(true);
		this.btnRemoveLinkman.setEnabled(true);
		this.checkAll.setSelected(false);
		this.checkAll.setEnabled(false);
		this.checkAll.setEditable(false);
		this.prmtFirstConsultant.setEnabled(false);
		if(this.f7Recommended.getValue()!=null){
			this.f7Recommended.setEnabled(false);
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		// VerifyEnabledById("删除");

		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		this.getUIContext().remove("mainCus");
		this.getUIContext().remove("customerType");
		this.getUIContext().remove("cusName");
		this.getUIContext().remove("cusPhone");
		SellProjectInfo sellProjectInfo = (SellProjectInfo) this.getUIContext().get("sellProject");
		Map linkedCus = CusClientHelper.addNewCusBegin(this, sellProjectInfo.getId().toString());
		if (linkedCus != null) {
			this.getUIContext().putAll(linkedCus);
			Boolean isAbort = (Boolean) linkedCus.get("isAbort");
			if (isAbort != null && isAbort.booleanValue()) {
				this.abort();
			}
		}
		// this.prmtFirstConsultant.setEnabled(true);
		super.actionAddNew_actionPerformed(e);
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
	}

	public void tblEntry_stateChanged(javax.swing.event.ChangeEvent e) throws Exception {
		// if (editData != null) {
		// SHECustomerInfo sheCustomerInfo = (SHECustomerInfo) this.editData;
		//
		// querySellBill(); // 查询单据
		//
		// loadTblLinkmanFields(sheCustomerInfo); // 查询联系人
		//
		// queryQuestionPaper(); // 查询问卷
		//
		// queryCommerceChance();// 查询商机
		//
		// queryTrack(); // 查询跟进
		// }

	}

	/**
	 * 查询客户变更记录
	 */
	public void tblNew_stateChanged(javax.swing.event.ChangeEvent e) throws Exception {
		if (!STATUS_ADDNEW.equals(getOprtState())) {
			queryPropertyConsultant();
			this.tblCustomerChange.removeRows();
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("oldField");
			sic.add("newField");
			sic.add("createTime");
			sic.add("creator.id");
			sic.add("creator.name");
			sic.add("orgUnit.id");
			sic.add("orgUnit.name");
			// sic.add("fdcBaseCustomer.id");
			view.setSelector(sic);
			FilterInfo filter = new FilterInfo();
			if (editData != null && editData.getId() != null) {
				String id = this.editData.getId().toString();
				filter.getFilterItems().add(new FilterItemInfo("sheCustomer.id", id));
				view.setFilter(filter);
				CustomerChangeLogCollection coll = CustomerChangeLogFactory.getRemoteInstance().getCustomerChangeLogCollection(view);
				if (coll != null && coll.size() > 0) {
					for (int i = 0; i < coll.size(); i++) {
						CustomerChangeLogInfo info = coll.get(i);
						loadLogInfo(info);
					}
				}
			}
		}
		// 设置变更记录不能修改，时间格式
		tblCustomerChange.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblCustomerChange.setEditable(false);
		if (tblCustomerChange.getColumn("createTime") != null) {
			tblCustomerChange.getColumn("createTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd HH:mm:ss");
		}
	}

	/**
	 * 将查到的变更记录展示
	 * 
	 * @param info
	 */
	private void loadLogInfo(CustomerChangeLogInfo info) {
		if (info == null) {
			return;
		}
		IRow row = this.tblCustomerChange.addRow();
		if (info.getOldField() != null) {
			row.getCell("oldField").setValue(info.getOldField());
		}
		if (info.getNewField() != null) {
			row.getCell("newField").setValue(info.getNewField());
		}
		if (info.getCreateTime() != null) {
			row.getCell("createTime").setValue(info.getCreateTime());
		}
		if (info.getCreator() != null) {
			row.getCell("creator.name").setValue(info.getCreator().getName());
		}
		if (info.getOrgUnit() != null) {
			row.getCell("orgUnit.name").setValue(info.getOrgUnit().getName());
		}
	}

	/**
	 * 查询共享置业顾问
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void queryPropertyConsultant() throws BOSException, EASBizException {
		this.tblPropertyConsultant.removeRows();
		tblPropertyConsultant.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblPropertyConsultant.setEditable(false);
		if (this.editData == null) {
			return;
		}
		// SellProjectInfo sellProject = new SellProjectInfo();
		// if(editData == null){
		// sellProject = (SellProjectInfo)
		// this.getUIContext().get("sellProject");
		// }else{
		// sellProject = editData.getSellProject();
		// }

		// EntityViewInfo view=
		// NewCommerceHelper.getPermitSalemanView(sellProject,false);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("customer.id", this.editData.getId()));
		view.setFilter(filter);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("name");
		sic.add("user.id");
		sic.add("user.name");
		sic.add("user.number");
		sic.add("user.type");
		sic.add("user.CU.id");
		sic.add("user.CU.name");
		sic.add("user.group.id");
		sic.add("user.group.name");
		view.setSelector(sic);
		SharePropertyCollection shareColl = SharePropertyFactory.getRemoteInstance().getSharePropertyCollection(view);
		if (shareColl != null && shareColl.size() > 0) {
			for (int i = 0; i < shareColl.size(); i++) {
				UserInfo info = shareColl.get(i).getUser();
				IRow row = this.tblPropertyConsultant.addRow();
				if (info == null) {
					return;
				}
				if (row.getCell("id") != null) {
					row.getCell("id").setValue(info.getId());
					if (info.getNumber() != null) {
						row.getCell("number").setValue(info.getNumber());
					}
					if (info.getType() != null) {
						row.getCell("type").setValue(info.getType());
					}
					if (info.getName() != null) {
						row.getCell("name").setValue(info.getName());
					}
					if (info.getCU() != null) {
						row.getCell("CU.name").setValue(info.getCU().getName());
					}
					if (info.getGroup() != null) {
						row.getCell("group.name").setValue(info.getGroup().getName());
					}
				}
			}
		}

		// SelectorItemCollection sic = new SelectorItemCollection();
		// sic.add("id");
		// sic.add("number");
		// sic.add("name");
		// sic.add("type");
		// sic.add("CU.id");
		// sic.add("CU.name");
		// sic.add("group.id");
		// sic.add("group.name");
		// view.setSelector(sic);
		// UserCollection userColl =
		// UserFactory.getRemoteInstance().getUserCollection(view);
		// if(userColl != null && userColl.size() > 0){
		// for(int i=0; i < userColl.size(); i++){
		// UserInfo info = userColl.get(i);
		// IRow row = this.tblPropertyConsultant.addRow();
		// if (info == null) {
		// return;
		// }
		// if(row.getCell("id") != null){
		// row.getCell("id").setValue(info.getId());
		// if (info.getNumber() != null) {
		// row.getCell("number").setValue(info.getNumber());
		// }
		// if (info.getType() != null) {
		// row.getCell("type").setValue(info.getType());
		// }
		// if (info.getName() != null) {
		// row.getCell("name").setValue(info.getName());
		// }
		// if (info.getCU() != null) {
		// row.getCell("CU.name").setValue(info.getCU().getName());
		// }
		// if (info.getGroup() != null) {
		// row.getCell("group.name").setValue(info.getGroup().getName());
		// }
		// }
		// }
		// }
	}

	/**
	 * 查询销售单
	 * 
	 * @throws Exception
	 */
	public void querySellBill() throws Exception {
		this.tblSellBill.removeRows();
		tblSellBill.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblSellBill.setEditable(false);
		EntityViewInfo view = new EntityViewInfo();
		EntityViewInfo mainView = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("head.id");
		sic.add("customer.name");
		FilterInfo filter = new FilterInfo();
		if (editData.getId() != null && !editData.getId().equals("")) {

			filter.getFilterItems().add(new FilterItemInfo("customer.id", this.editData.getId()));
			mainView.setSelector(sic);
			mainView.setFilter(filter);
			//预约排号
			SincerityPurchaseCustomerEntryCollection sincerityPurEntryColl = SincerityPurchaseCustomerEntryFactory.getRemoteInstance().getSincerityPurchaseCustomerEntryCollection(mainView);

			if (sincerityPurEntryColl != null && sincerityPurEntryColl.size() > 0) {
				String customerName = "";

				for (int i = 0; i < sincerityPurEntryColl.size(); i++) {
					SincerityPurchaseCustomerEntryInfo entryInfo = sincerityPurEntryColl.get(i);

					// 根据分录头去查单据
					if (entryInfo != null) {
						if (entryInfo.getCustomer() != null && !entryInfo.getCustomer().equals("")) {
							customerName = sincerityPurEntryColl.get(0).getCustomer().getName();
							if (i > 1) {
								customerName += ", " + entryInfo.getCustomer().getName();
							}
						}

						view = new EntityViewInfo();
						sic = new SelectorItemCollection();
						filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("id", entryInfo.getHead().getId().toString()));
						sic.add("id");
						sic.add("head.id");
						sic.add("orgUnit.name");
						sic.add("sellProject.name");
						sic.add("room.name");// 业务场景
						// sic.add("customerNames");
						sic.add("bizState");
						sic.add("number");
						sic.add("bizDate");
						sic.add("payType.name");
						sic.add("lastAgio");
						sic.add("dealTotalAmount");
						sic.add("dealBuildPrice");
						sic.add("dealRoomPrice");
						sic.add("saleManStr");

						view.setSelector(sic);
						view.setFilter(filter);
						// 预约排号单
						SincerityPurchaseCollection sincerityPurColl = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseCollection(view);
						if (sincerityPurColl != null && sincerityPurColl.size() > 0) {
							for (int j = 0; j < sincerityPurColl.size(); j++) {
								SincerityPurchaseInfo info = sincerityPurColl.get(j);
								String saleManNames = info.getSaleManStr();
								setValue((BaseTransactionInfo) info, customerName, saleManNames , "1");
							}
						}
					}
				}
			}
			
			//预定管理
			PrePurchaseCustomerEntryCollection prePurchaseEntryColl = PrePurchaseCustomerEntryFactory.getRemoteInstance().getPrePurchaseCustomerEntryCollection(mainView);
			if (prePurchaseEntryColl != null && prePurchaseEntryColl.size() > 0) {
				String customerName = "";
				for (int i = 0; i < prePurchaseEntryColl.size(); i++) {
					PrePurchaseCustomerEntryInfo entryInfo = prePurchaseEntryColl.get(i);

					// 根据分录去查单据
					if (entryInfo != null) {
						if (entryInfo.getCustomer() != null && !entryInfo.getCustomer().equals("")) {
							customerName = prePurchaseEntryColl.get(0).getCustomer().getName();
							if (i > 1) {
								customerName += ", " + entryInfo.getCustomer().getName();
							}
						}
						view = new EntityViewInfo();
						sic = new SelectorItemCollection();
						filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("id", entryInfo.getHead().getId().toString()));
						sic.add("id");
						sic.add("head.id");
						sic.add("orgUnit.name");
						sic.add("sellProject.name");
						sic.add("room.name");// 业务场景
						// sic.add("customerNames");
						sic.add("bizState");
						sic.add("number");
						sic.add("bizDate");
						sic.add("payType.name");
						sic.add("lastAgio");
						sic.add("dealTotalAmount");
						sic.add("dealBuildPrice");
						sic.add("dealRoomPrice");
						sic.add("saleManNames");

						view.setSelector(sic);
						view.setFilter(filter);
						// 预定单
						PrePurchaseManageCollection prePurchasecoll = PrePurchaseManageFactory.getRemoteInstance().getPrePurchaseManageCollection(view);
						if (prePurchasecoll != null && prePurchasecoll.size() > 0) {
							for (int j = 0; j < prePurchasecoll.size(); j++) {
								PrePurchaseManageInfo info = prePurchasecoll.get(j);
								String saleManNames = info.getSaleManNames();
								setValue((BaseTransactionInfo) info, customerName, saleManNames, "2");
							}
						}
					}
				}
			}

			// 认购管理
			PurCustomerEntryCollection purchaseEntryColl = PurCustomerEntryFactory.getRemoteInstance().getPurCustomerEntryCollection(mainView);
			if (purchaseEntryColl != null && purchaseEntryColl.size() > 0) {
				String customerName = "";
				for (int i = 0; i < purchaseEntryColl.size(); i++) {
					PurCustomerEntryInfo entryInfo = purchaseEntryColl.get(i);

					// 根据分录去查单据
					if (entryInfo != null) {
						if (entryInfo.getCustomer() != null && !entryInfo.getCustomer().equals("")) {
							customerName = purchaseEntryColl.get(0).getCustomer().getName();
							if (i > 1) {
								customerName += ", " + entryInfo.getCustomer().getName();
							}
						}
						view = new EntityViewInfo();
						sic = new SelectorItemCollection();
						filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("id", entryInfo.getHead().getId().toString()));
						sic.add("id");
						sic.add("head.id");
						sic.add("orgUnit.name");
						sic.add("sellProject.name");
						sic.add("room.name");// 业务场景
						// sic.add("customerNames");
						sic.add("bizState");
						sic.add("number");
						sic.add("bizDate");
						sic.add("payType.name");
						sic.add("lastAgio");
						sic.add("dealTotalAmount");
						sic.add("dealBuildPrice");
						sic.add("dealRoomPrice");
						sic.add("saleManNames");

						view.setSelector(sic);
						view.setFilter(filter);
						// 认购单
						PurchaseManageCollection purchasecoll = PurchaseManageFactory.getRemoteInstance().getPurchaseManageCollection(view);
						if (purchasecoll != null && purchasecoll.size() > 0) {
							for (int j = 0; j < purchasecoll.size(); j++) {
								PurchaseManageInfo info = purchasecoll.get(j);
								String saleManNames = info.getSaleManNames();
								setValue((BaseTransactionInfo) info, customerName, saleManNames, "3");
							}
						}
					}
				}
			}

			//签约管理
			SignCustomerEntryCollection signEntryColl = SignCustomerEntryFactory.getRemoteInstance().getSignCustomerEntryCollection(mainView);
			if (signEntryColl != null && signEntryColl.size() > 0) {
				String customerName = "";
				for (int i = 0; i < signEntryColl.size(); i++) {
					SignCustomerEntryInfo entryInfo = signEntryColl.get(i);

					// 根据分录去查单据
					if (entryInfo != null) {
						if (entryInfo.getCustomer() != null && !entryInfo.getCustomer().equals("")) {
							customerName = signEntryColl.get(0).getCustomer().getName();
							if (i > 1) {
								customerName += ", " + entryInfo.getCustomer().getName();
							}
						}
						view = new EntityViewInfo();
						sic = new SelectorItemCollection();
						filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("id", entryInfo.getHead().getId().toString()));
						sic.add("id");
						sic.add("number");
						sic.add("head.id");
						sic.add("orgUnit.name");
						sic.add("sellProject.name");
						sic.add("room.name");// 业务场景
						// sic.add("customerNames");
						sic.add("state");
						sic.add("bizDate");
						sic.add("payType.name");
						sic.add("lastAgio");
						sic.add("dealTotalAmount");
						sic.add("dealBuildPrice");
						sic.add("dealRoomPrice");
						sic.add("saleManNames");

						view.setSelector(sic);
						view.setFilter(filter);
						SignManageCollection signColl = SignManageFactory.getRemoteInstance().getSignManageCollection(view);
						if (signColl != null && signColl.size() > 0) {
							for (int j = 0; j < signColl.size(); j++) {
								SignManageInfo info = signColl.get(j);
								String saleManNames = info.getSaleManNames();
								setValue((BaseTransactionInfo) info, customerName, saleManNames, "4");
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 查询到的单据填充到表中
	 * 
	 * @param info
	 * @param addRow
	 */
	private void setValue(BaseTransactionInfo info, String customerName, String saleManNames, String bizType) {
		tblSellBill.checkParsed();
		tblSellBill.setEditable(false);
		tblSellBill.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		IRow addRow = tblSellBill.addRow();
		if (info != null) {
			addRow.getCell("id").setValue(info.getId());
			if (info.getOrgUnit() != null) {
				addRow.getCell("orgUnit.name").setValue(info.getOrgUnit().getName());
			} else {
				addRow.getCell("orgUnit.name").setValue("");
			}
			if (info.getSellProject() != null) {
				addRow.getCell("sellProject.name").setValue(info.getSellProject().getName());
			} else {
				addRow.getCell("sellProject.name").setValue("");
			}
			if (info.getRoom() != null) {
				addRow.getCell("room.name").setValue(info.getRoom().getName());
			} else {
				addRow.getCell("room.name").setValue("");
			}
			addRow.getCell("customerNames").setValue(customerName); // 客户
			addRow.getCell("bizState").setValue(info.getBizState());
			addRow.getCell("number").setValue(info.getNumber());
			addRow.getCell("bizDate").setValue(info.getBizDate());
			if (info.getPayType() != null) {
				addRow.getCell("payType.name").setValue(info.getPayType().getName());
			} else {
				addRow.getCell("payType.name").setValue("");
			}
			addRow.getCell("lastAgio").setValue(info.getLastAgio());
			addRow.getCell("dealTotalAmount").setValue(info.getDealTotalAmount());
			addRow.getCell("dealBuildPrice").setValue(info.getDealBuildPrice());
			addRow.getCell("dealRoomPrice").setValue(info.getDealRoomPrice());
			addRow.getCell("saleMan.name").setValue(saleManNames);
			if (info.getBizDate() != null) {
				addRow.getCell("bizDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
			}
			addRow.getCell("type").setValue(bizType);
		}
	}

	/**
	 * 初始化联系人列表
	 * */
	private void initLinkmanTable() {
		this.actionAddLinkman.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_sortstandard"));
		this.actionRemoveLinkman.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
		tblBaseSet(tblLinkman);

		KDCheckBox chkBox = new KDCheckBox();
		ICellEditor checkBox = new KDTDefaultCellEditor(chkBox);
		IColumn isAssociation = this.tblLinkman.getColumn("isAssociation");
		isAssociation.setEditor(checkBox);

		KDComboBox box1 = new KDComboBox();
		box1.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basecrm.AssociationTypeEnum").toArray());
		box1.setSelectedIndex(0);
		KDTDefaultCellEditor associationTypeEditor = new KDTDefaultCellEditor(box1);
		IColumn associationType = this.tblLinkman.getColumn("associationType");
		associationType.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		associationType.setEditor(associationTypeEditor);
		associationType.setEditor(createTxtCellEditor(80, false));

		chkBox = new KDCheckBox();
		ICellEditor checkEmergencyContact = new KDTDefaultCellEditor(chkBox);
		IColumn isEmergencyContact = this.tblLinkman.getColumn("isEmergencyContact");
		isEmergencyContact.setEditor(checkEmergencyContact);

		chkBox = new KDCheckBox();
		ICellEditor checkBoxPrincipal = new KDTDefaultCellEditor(chkBox);
		IColumn isPrincipal = this.tblLinkman.getColumn("isPrincipal");
		isPrincipal.setEditor(checkBoxPrincipal);

		IColumn principalNo = this.tblLinkman.getColumn("principalNo");
		principalNo.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		principalNo.setEditor(createTxtCellEditor(80, true));

		IColumn number = this.tblLinkman.getColumn("number");
		number.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		number.setEditor(createTxtCellEditor(80, true));

		IColumn name = this.tblLinkman.getColumn("name");
		name.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		name.setEditor(createTxtCellEditor(80, true));

		IColumn relation = this.tblLinkman.getColumn("relation");
		relation.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		relation.setEditor(createTxtCellEditor(80, true));

		KDComboBox box = new KDComboBox();
		box.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SexEnum").toArray());
		box.setSelectedIndex(0);
		KDTDefaultCellEditor sexEditor = new KDTDefaultCellEditor(box);
		IColumn sex = this.tblLinkman.getColumn("sex");
		sex.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		sex.setEditor(sexEditor);
		
		KDComboBox comboRelation = new com.kingdee.bos.ctrl.swing.KDComboBox();;
		comboRelation.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SocialRelationsEnum").toArray());		
        comboRelation.setRequired(true);		
        comboRelation.setEnabled(true);
        comboRelation.setVisible(true);
        comboRelation.setName("relation");
        comboRelation.insertItemAt("", 0);
        KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(comboRelation);
        tblLinkman.getColumn("relation").setEditor(cellEditor);

		IColumn phone = this.tblLinkman.getColumn("phone");
		phone.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		phone.setEditor(createTxtCellEditor(80, true));

		IColumn tel = this.tblLinkman.getColumn("tel");
		tel.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		tel.setEditor(createTxtCellEditor(80, true));

		IColumn officeTel = this.tblLinkman.getColumn("officeTel");
		officeTel.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		officeTel.setEditor(createTxtCellEditor(80, true));

		IColumn fax = this.tblLinkman.getColumn("fax");
		fax.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		fax.setEditor(createTxtCellEditor(80, true));

		IColumn otherTel = this.tblLinkman.getColumn("otherTel");
		otherTel.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		otherTel.setEditor(createTxtCellEditor(80, true));

		IColumn email = this.tblLinkman.getColumn("email");
		email.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		email.setEditor(createTxtCellEditor(80, true));

		// KDBizPromptBox f7Box = new KDBizPromptBox();
		// f7Box.setEditFormat("$number$");
		// f7Box.setDisplayFormat("$name$");
		// f7Box.setCommitFormat("$number$");
		// f7Box
		// .setQueryInfo("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataQuery");
		// FilterInfo mainFilter = new FilterInfo();
		// mainFilter.getFilterItems().add(
		// new FilterItemInfo("isEnabled", Boolean.TRUE));
		// EntityViewInfo viewInfo = new EntityViewInfo();
		// viewInfo.setFilter(mainFilter);
		// f7Box.setEntityViewInfo(viewInfo);
		// setBaseDataFilter(f7Box, CRMConstants.PE_CERTIFICATE_TYPE_GROUP_ID);

		// KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		// IColumn certificateType =
		// this.tblLinkman.getColumn("certificateType");
		// certificateType.getStyleAttributes().setHorizontalAlign(
		// HorizontalAlignment.LEFT);
		// certificateType.setEditor(f7Editor);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("group.id", CRMConstants.PE_CERTIFICATE_TYPE_GROUP_ID));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("name"));
		coll.add(new SelectorItemInfo("number"));
		coll.add(new SelectorItemInfo("description"));
		view.setSelector(coll);
		KDComboBox box2 = new KDComboBox();
		IColumn certificateType = this.tblLinkman.getColumn("certificateType");
		certificateType.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		try {
			FDCCusBaseDataCollection dataColl = FDCCusBaseDataFactory.getRemoteInstance().getFDCCusBaseDataCollection(view);
			for (int i = 0; i < dataColl.size(); i++) {
				FDCCusBaseDataInfo info = dataColl.get(i);
				box2.addItem(info.getName());
				box2.setSelectedIndex(0);
				KDTDefaultCellEditor certificateTypeEditor = new KDTDefaultCellEditor(box2);
				certificateType.setEditor(certificateTypeEditor);
			}

		} catch (BOSException e) {
			logger.error(e.getMessage());
		}

		IColumn certificateNumber = this.tblLinkman.getColumn("certificateNumber");
		certificateNumber.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		certificateNumber.setEditor(createTxtCellEditor(80, true));

		String formatString = "yyyy-MM-dd";
		IColumn dayOfBirth = this.tblLinkman.getColumn("dayOfBirth");
		dayOfBirth.getStyleAttributes().setNumberFormat(formatString);
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		dayOfBirth.setEditor(dateEditor);

		IColumn mailAddress = this.tblLinkman.getColumn("mailAddress");
		mailAddress.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		mailAddress.setEditor(createTxtCellEditor(80, true));

		IColumn bookedAddress = this.tblLinkman.getColumn("bookedAddress");
		bookedAddress.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		bookedAddress.setEditor(createTxtCellEditor(80, true));

		IColumn postalCode = this.tblLinkman.getColumn("postalCode");
		postalCode.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		postalCode.setEditor(createTxtCellEditor(80, true));

		IColumn description = this.tblLinkman.getColumn("description");
		description.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		description.setEditor(createTxtCellEditor(80, true));

		this.tblLinkman.getColumn("name").setRenderer(new ObjectValueRender() {
			public String getText(Object obj) {
				if (obj == null) {
					return null;
				} else if (obj instanceof Object[]) {
					Object[] os = (Object[]) obj;
					return (String) CRMHelper.getValue((IObjectValue) os[0], "name");
				}
				return super.getText(obj);
			}
		});

	}

	// private void initControl() {
	// boolean isStatusView = this.oprtState.equals(STATUS_VIEW);
	// this.btnAddLinkman.setEnabled(!isStatusView);
	// this.btnRemoveLinkman.setEnabled(!isStatusView);
	// this.checkAll.setEnabled(true);
	// }

	/**
	 * 为增加行编辑进行基础设置
	 * */
	private void tblBaseSet(KDTable table) {
		table.checkParsed();
		table.getStyleAttributes().setLocked(false);
		table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
	}

	/**
	 * 创建带文本编辑框的cellEditor
	 * 
	 * @param length
	 *            文本框最大输入长度
	 * @param editable
	 *            是否可编辑
	 * */
	private KDTDefaultCellEditor createTxtCellEditor(int length, boolean editable) {
		KDTextField textField = new KDTextField();
		textField.setMaxLength(length);
		textField.setEditable(editable);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		return txtEditor;
	}

	private Set getLeafIdSet(OrgUnitInfo unitInfo) throws BOSException, SQLException {
		Set rs = new HashSet();
		if (unitInfo == null)
			return rs;
		FDCSQLBuilder build = new FDCSQLBuilder();
		StringBuffer sql = new StringBuffer();
		sql.append("select fid as id from  t_org_sale where ");
		sql.append(" (FNumber = '" + unitInfo.getNumber() + "'");
		sql.append(" or FLongNumber like '" + unitInfo.getLongNumber() + "!%') ");
		sql.append(" and FisLeaf =1 ");
		build.appendSql(sql.toString());
		IRowSet idSet = build.executeQuery();
		while (idSet.next()) {
			String id = idSet.getString("id");
			rs.add(id);
		}
		return rs;
	}

	protected void txtCertificateNumber_focusLost(FocusEvent e) throws Exception {
		if (isLoadField)
			return;
		customerNumberChange();
	}

	/**
	 * 根据身份证带出出生日期和性别
	 * 
	 * @throws Exception
	 */
	private void customerNumberChange() throws Exception {
		String cerNum = this.txtCertificateNumber.getText();
		if (cerNum != null && cerNum.length() != 0) {
			cerNum = cerNum.trim();
		}
		if (!CustomerTypeEnum.PERSONALCUSTOMER.equals(this.comboCustomerType.getSelectedItem())) {
			return;
		}
		if (this.comboCertificateType.getSelectedItem() == null) {
			return;
		} else if (!this.comboCertificateType.getSelectedItem().equals("身份证")) {
			return;
		}
		if (cerNum == null || (cerNum.length() != 15 && cerNum.length() != 18)) {
			return;
		}

		if (cerNum.length() == 15 && IDCard.IDCardValidate(cerNum).equals("")) {
			String id14 = cerNum.substring(14, 15);
			if (Integer.parseInt(id14) % 2 != 0) {
				this.comboSex.setSelectedItem(SexEnum.Mankind);
			} else {
				this.comboSex.setSelectedItem(SexEnum.Womenfolk);
			}
			// 获取出生日期
			String birthday = "19" + cerNum.substring(6, 12);
			Date birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
			this.pkDayOfbirth.setValue(birthdate);
		}
		// 合法的身份证带出性别和出生日期
		if (cerNum.length() == 18 && IDCard.IDCardValidate(cerNum).equals("")) {
			String id17 = cerNum.substring(16, 17);
			if (Integer.parseInt(id17) % 2 != 0) {
				this.comboSex.setSelectedItem(SexEnum.Mankind);
			} else {
				this.comboSex.setSelectedItem(SexEnum.Womenfolk);
			}
			// 获取出生日期
			String birthday = cerNum.substring(6, 14);
			Date birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
			this.pkDayOfbirth.setValue(birthdate);
		}
	}

	/**
	 * 全选
	 */
	public void actionCheckAll_actionPerformed(ActionEvent e) throws Exception {
		FullOrgUnitInfo currentUnit = SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection coll = new SelectorItemCollection();
		tblLinkman.removeRows();
		if (this.checkAll.isSelected()) {
			// 当前组织
			Set ids = this.getLeafIdSet(currentUnit);
			String idStr = "";
			idStr = FDCTreeHelper.getStringFromSet(ids);
			filter.getFilterItems().add(new FilterItemInfo("sheCustomer.createUnit.id", idStr, CompareType.INNER));
			view.setFilter(filter);
			coll.add(new SelectorItemInfo("*"));
			coll.add(new SelectorItemInfo("id"));
			coll.add(new SelectorItemInfo("name"));
			view.setSelector(coll);
			SHECustomerLinkManCollection linkManColl = SHECustomerLinkManFactory.getRemoteInstance().getSHECustomerLinkManCollection(view);
			if (linkManColl != null && linkManColl.size() > 0) {
				for (int i = 0; i < linkManColl.size(); i++) {
					SHECustomerLinkManInfo linkManinfo = linkManColl.get(i);
					IRow row = this.tblLinkman.addRow();
					addLinkManRow(row, linkManinfo);
				}
			}
		} else {
			filter.getFilterItems().add(new FilterItemInfo("sheCustomer.id", this.editData.getId(), CompareType.EQUALS));
			view.setFilter(filter);
			coll.add(new SelectorItemInfo("*"));
			coll.add(new SelectorItemInfo("id"));
			coll.add(new SelectorItemInfo("name"));
			view.setSelector(coll);
			SHECustomerLinkManCollection linkManColl = SHECustomerLinkManFactory.getRemoteInstance().getSHECustomerLinkManCollection(view);

			if (linkManColl != null && linkManColl.size() > 0) {
				for (int i = 0; i < linkManColl.size(); i++) {
					SHECustomerLinkManInfo linkManinfo = linkManColl.get(i);
					IRow row = this.tblLinkman.addRow();
					addLinkManRow(row, linkManinfo);
				}
			}
		}
		super.actionCheckAll_actionPerformed(e);
	}

	public void actionAddLinkman_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLinkman_actionPerformed(e);
		SHECustomerLinkManInfo linkManInfo = new SHECustomerLinkManInfo();
		linkManInfo.setAssociationType(AssociationTypeEnum.NULL);
		linkManInfo.setSex(SexEnum.Mankind);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("group.id", CRMConstants.PE_CERTIFICATE_TYPE_GROUP_ID));
		view.setFilter(filter);
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("name"));
		coll.add(new SelectorItemInfo("number"));
		view.setSelector(coll);
		FDCCusBaseDataCollection dataColl = FDCCusBaseDataFactory.getRemoteInstance().getFDCCusBaseDataCollection(view);
		linkManInfo.setCertificateType((dataColl.get(0)));

		IRow row = this.tblLinkman.addRow();
		addLinkManRow(row, linkManInfo);
	}

	/**
	 * 加载数据到联系人列表界面
	 * */
	private void loadTblLinkmanFields(SHECustomerInfo info) {
		if (info == null || info.getId() == null)
			return;
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("sheCustomer.id", info.getId().toString());
		view.getSelector().add("*");
		view.getSelector().add("id");
		SHECustomerLinkManCollection linkmanList = null;
		try {
			linkmanList = SHECustomerLinkManFactory.getRemoteInstance().getSHECustomerLinkManCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		tblLinkman.removeRows();
		if (linkmanList != null && linkmanList.size() > 0) {
			for (int i = 0; i < linkmanList.size(); i++) {
				SHECustomerLinkManInfo linkManinfo = linkmanList.get(i);
				IRow row = tblLinkman.addRow(i);
				addLinkManRow(row, linkManinfo);
			}
		}
	}

	/**
	 * 添加联系人
	 * 
	 * @param row
	 * @param linkManInfo
	 */
	private void addLinkManRow(IRow row, SHECustomerLinkManInfo linkManInfo) {
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

		// row.getCell("certificateType").setValue(
		// linkManInfo.getCertificateType());

		row.getCell("certificateNumber").setValue(linkManInfo.getCertificateNumber());

		if (linkManInfo.getDayOfBirth() != null) {
			row.getCell("dayOfBirth").setValue(FDCDateHelper.stringToDate(linkManInfo.getDayOfBirth()));
		} else {
			row.getCell("dayOfBirth").setValue(null);
		}

		row.getCell("mailAddress").setValue(linkManInfo.getMailAddress());
		row.getCell("bookedAddress").setValue(linkManInfo.getBookedAddress());

		row.getCell("postalCode").setValue(linkManInfo.getPostalCode());
		row.getCell("description").setValue(linkManInfo.getDescription());
		row.getCell("id").setValue(linkManInfo.getId());
		// 证件类型
		if (linkManInfo.getCertificateType() != null) {
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(new FilterInfo());
			view.getFilter().appendFilterItem("id", linkManInfo.getCertificateType().getId().toString());
			view.getSelector().add("id");
			view.getSelector().add("name");
			FDCCusBaseDataCollection fdcCusBaseDataColl = null;
			try {
				fdcCusBaseDataColl = FDCCusBaseDataFactory.getRemoteInstance().getFDCCusBaseDataCollection(view);
				if (fdcCusBaseDataColl != null && fdcCusBaseDataColl.size() > 0) {
					row.getCell("certificateType").setValue(fdcCusBaseDataColl.get(0));
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 联系人的编辑 动态
	 */
	protected void tblLinkman_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		IColumn col = tblLinkman.getColumn(colIndex);
		IRow row = tblLinkman.getRow(rowIndex);
		ICell isAssociation = this.tblLinkman.getRow(rowIndex).getCell("isAssociation");
		Boolean isSel = (Boolean) isAssociation.getValue();
		Object assTypeObj = row.getCell("associationType").getValue();
		AssociationTypeEnum assType = null;
		SellProjectInfo sellProject = null;
		if (this.editData != null) {
			sellProject = editData.getSellProject();
		}
		if (assTypeObj != null) {
			if (assTypeObj instanceof AssociationTypeEnum) {
				assType = (AssociationTypeEnum) assTypeObj;
			} else if (assTypeObj instanceof String) {
				if ("系统关联".equals(assTypeObj)) {
					assType = (AssociationTypeEnum) AssociationTypeEnum.SYSTEM;
				} else if ("手工关联".equals(assTypeObj)) {
					assType = (AssociationTypeEnum) AssociationTypeEnum.HAND;
				} else if ("".equals(assTypeObj)) {
					assType = (AssociationTypeEnum) AssociationTypeEnum.NULL;
				}
			}
		}
		if (AssociationTypeEnum.SYSTEM.equals(assType)) {
			this.isLocked(rowIndex, true);
			this.tblLinkman.getRow(rowIndex).getCell("isAssociation").getStyleAttributes().setLocked(true);
			this.tblLinkman.getRow(rowIndex).getCell("isEmergencyContact").getStyleAttributes().setLocked(true);
			this.tblLinkman.getRow(rowIndex).getCell("isPrincipal").getStyleAttributes().setLocked(true);
			this.tblLinkman.getRow(rowIndex).getCell("principalNo").getStyleAttributes().setLocked(true);
			this.tblLinkman.getRow(rowIndex).getCell("relation").getStyleAttributes().setLocked(true);
			this.tblLinkman.getRow(rowIndex).getCell("name").getStyleAttributes().setLocked(true);
			this.tblLinkman.getRow(rowIndex).getCell("number").getStyleAttributes().setLocked(true);
		} else {
			if (isSel != null && isSel.booleanValue()) {
				row.getCell("associationType").setValue(AssociationTypeEnum.HAND);
				KDBizPromptBox f7Box = new KDBizPromptBox();

				MyKDCommonPromptDialog dlg = new MyKDCommonPromptDialog() {
					protected void handleDisplayCol(KDTDataRequestEvent e, KDTable table) {
						super.handleDisplayCol(e, table);

						int begin = e.getFirstRow();
						int last = e.getLastRow();

						for (int i = begin; i < last + 1; i++) {
							ICell cellP = table.getRow(i).getCell("phone");

							String phone = (String) cellP.getValue();
							if (phone != null && phone.length() != 0) {
								cellP.setValue(parseStr(phone, 3, 4));
							}

							ICell cellC = table.getRow(i).getCell("certificateNumber");
							String cer = (String) cellC.getValue();
							if (cer != null && cer.length() != 0) {
								cellC.setValue(parseStr(cer, 4, 4));
							}
						}
					}
				};

				IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
				dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.F7SHECustomerQuery")));

				FilterInfo mainFilter = new FilterInfo();
				mainFilter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
				mainFilter.getFilterItems().add(new FilterItemInfo("customerType", CustomerTypeEnum.PERSONALCUSTOMER_VALUE));
				mainFilter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId()));

				EntityViewInfo tempView = NewCommerceHelper.getPermitSalemanView(editData.getSellProject(), false);
				UserCollection userColl = UserFactory.getRemoteInstance().getUserCollection(tempView);
				Set idSet = new HashSet();
				if (userColl != null && userColl.size() > 0) {
					for (int i = 0; i < userColl.size(); i++) {
						idSet.add(userColl.get(i).getId().toString());
					}
				}
				mainFilter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id", idSet, CompareType.INCLUDE));

				EntityViewInfo viewInfo = new EntityViewInfo();
				viewInfo.setFilter(mainFilter);
				f7Box.setEntityViewInfo(viewInfo);
				f7Box.setEditFormat("$number$");
				f7Box.setDisplayFormat("$name$");
				f7Box.setCommitFormat("$number$");

				KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
				ICell name = this.tblLinkman.getRow(rowIndex).getCell("name");
				name.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
				name.setEditor(f7Editor);

				dlg.setEntityViewInfo(viewInfo);
				f7Box.setSelector(dlg);

				this.isLocked(rowIndex, true);

				if (col.getKey().equals("name")) {
					if(row.getCell("name").getValue() instanceof Object []){
						Object []obj = (Object [])row.getCell("name").getValue();
						SHECustomerInfo info = new SHECustomerInfo();
						if (obj != null && obj[0] instanceof SHECustomerInfo) {
							info = (SHECustomerInfo) obj[0];
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
							row.getCell("name").setValue(info.getName());
						}
					}
				}
			} else {
				row.getCell("associationType").setValue(AssociationTypeEnum.NULL);
				ICell name = this.tblLinkman.getRow(rowIndex).getCell("name");
				name.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
				name.setEditor(createTxtCellEditor(80, true));
				this.isLocked(rowIndex, false);
			}
		}

	}

	private String parseStr(String src, int b, int e) {
		int length = src.length();

		if (length <= b + e) {
			return src;
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i < b || i >= length - e) {
				sb.append(src.charAt(i));
			} else {
				sb.append("*");
			}
		}

		return sb.toString();
	}

	/**
	 * 设置字段栏是否锁住，不能编辑
	 * 
	 * @param rowIndex
	 * @param isLocked
	 */
	private void isLocked(int rowIndex, boolean isLocked) {
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

	/**
	 * 删除联系人
	 */
	public void actionRemoveLinkman_actionPerformed(ActionEvent e) throws Exception {
		if (tblLinkman != null) {
			removeLine(tblLinkman);
			appendFootRow(tblLinkman);
			if (tblLinkman.getRowCount() == 0) {
				FocusTraversalPolicy policy = null;
				Container container = null;
				Component initComponent = null;
				if (this.getFocusTraversalPolicy() != null && this.getFocusTraversalPolicy() instanceof UIFocusTraversalPolicy) {
					policy = this.getFocusTraversalPolicy();
					container = this;
					Component[] traverComponent = ((UIFocusTraversalPolicy) policy).getComponents();
					for (int i = 0; i < traverComponent.length; i++) {
						if (traverComponent[i] == this.tblLinkman) {
							initComponent = traverComponent[i];
							break;
						}
					}
					if (initComponent == null) {
						initComponent = policy.getLastComponent(container);
						initComponent.requestFocusInWindow();
					} else if (initComponent != null) {
						Component component = policy.getComponentBefore(container, initComponent);
						while ((component instanceof IKDTextComponent) == false || component.isEnabled() == false) {
							component = policy.getComponentBefore(container, component);
						}
						component.requestFocusInWindow();
					}
				} else if (policy == null) {
					if (this.getUIWindow() instanceof Dialog) {
						policy = ((Dialog) uiWindow).getFocusTraversalPolicy();
						container = (Dialog) uiWindow;
					} else if (this.getUIWindow() instanceof Window) {
						policy = ((Window) uiWindow).getFocusTraversalPolicy();
						container = (Window) uiWindow;
					}

					if (policy != null) {
						try {
							Component component = policy.getComponentBefore(container, tblLinkman);
							while ((component instanceof IKDTextComponent) == false || component.isEnabled() == false) {
								component = policy.getComponentBefore(container, component);
							}
							component.requestFocusInWindow();
						} catch (Exception ex) {
						}
					}
				}
			}
		}
		// super.actionRemoveLinkman_actionPerformed(e);
	}

	/**
	 * 判断是否是当前客户的联系人，不是则不能操作（删除）
	 * 
	 * @param table
	 * @return
	 * @throws Exception
	 */
	private boolean beforeRemoveLinkMan(KDTable table) throws Exception {
		ArrayList list = new ArrayList();
		int[] selectRows = KDTableUtil.getSelectedRows(table);
		for (int i = 0; i < selectRows.length; i++) {
			IRow row = table.getRow(selectRows[i]);
			list.add(row.getCell("id").getValue());
		}

		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				SHECustomerLinkManInfo info = SHECustomerLinkManFactory.getRemoteInstance()
						.getSHECustomerLinkManInfo("select sheCustomer.id as customerId where id = '" + list.get(j).toString() + "'");
				if (info != null) {
					if (!info.getSheCustomer().getId().equals(this.editData.getId())) {
						MsgBox.showWarning("非当前客户直接关联的联系人不允许删除!");
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 在指定表格中删除当前选中行 增加隔行删除功能
	 * 
	 * @param table
	 */
	protected void removeLine(KDTable table) {
		if (table == null) {
			return;
		}

		if ((table.getSelectManager().size() == 0)) {
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_NoneEntry"));
			return;
		}

		try {
			// 能否删除 ;
			if (!beforeRemoveLinkMan(table)) {
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// [begin]进行删除分录的提示处理。
		if (confirmRemove()) {
			// 获取选择块的总个数
			KDTSelectManager selectManager = table.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			// 因为先择块的顺序可能并非是表中行的顺序，所以先要排序使选择块的顺序正好是由小到大
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (table.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null)
				return;
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				IObjectValue detailData = (IObjectValue) table.getRow(rowIndex).getUserObject();
				table.removeRow(rowIndex);
				IObjectCollection collection = (IObjectCollection) table.getUserObject();

				if (collection == null) {
					logger.error("collection not be binded to table");
				} else {
					if (detailData != null) {
						int index = getCollectionIndex(collection, detailData);
						// 避免有合计行的分录处理。
						if (index >= 0 && collection.size() > index) {
							collection.removeObject(index);
						}
					}
				}
			}

			// 如果现在有记录定位到第一行
			if (table.getRow(0) != null)
				table.getSelectManager().select(0, 0);
		}
	}

	private int getCollectionIndex(IObjectCollection collection, IObjectValue obj) {
		int index = -1;
		if (collection == null) {
			return index;
		}
		for (int i = collection.size() - 1; i >= 0; i--) {
			if (obj == collection.getObject(i)) {
				index = i;
				return index;
			}
		}
		return index;
	}

	/**
	 * 查询问卷
	 */
	public void queryQuestionPaper() throws Exception {
		this.tblQuestion.removeRows();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("orgUnit.id");
		sic.add("orgUnit.name");
		sic.add("sellProject.id");
		sic.add("sellProject.name");
		sic.add("questionPaper.bizScene");// 业务场景
		sic.add("inputDate");
		sic.add("number");
		sic.add("questionPaper.topric");
		sic.add("bizDate");
		sic.add("creator.id");
		sic.add("creator.name");
		sic.add("createTime");
		sic.add("lastUpdateUser.name");
		sic.add("lastUpdateTime");
		FilterInfo filter = new FilterInfo();
		if (editData.getId() != null && !editData.getId().equals("")) {
			filter.getFilterItems().add(new FilterItemInfo("sheCustomer.id", this.editData.getId()));
			view.setSelector(sic);
			view.setFilter(filter);

			QuestionPaperAnswerCollection coll = QuestionPaperAnswerFactory.getRemoteInstance().getQuestionPaperAnswerCollection(view);
			if (coll != null && coll.size() > 0) {
				for (int i = 0; i < coll.size(); i++) {
					QuestionPaperAnswerInfo info = coll.get(i);
					tblQuestion.checkParsed();
					tblQuestion.setEditable(false);
					tblQuestion.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
					IRow addRow = tblQuestion.addRow();

					if (info != null) {
						addRow.getCell("id").setValue(info.getId());
						if (info.getOrgUnit() != null) {
							addRow.getCell("orgUnit.name").setValue(info.getOrgUnit().getName());
						} else {
							addRow.getCell("orgUnit.name").setValue("");
						}
						if (info.getSellProject() != null) {
							addRow.getCell("sellProject.name").setValue(info.getSellProject().getName());
						} else {
							addRow.getCell("sellProject.name").setValue("");
						}
						addRow.getCell("inputDate").setValue(info.getInputDate());
						addRow.getCell("number").setValue(info.getNumber());
						if (info.getQuestionPaper() != null) {
							addRow.getCell("questionPaper.bizScene").setValue(info.getQuestionPaper().getBizScene());
						} else {
							addRow.getCell("questionPaper.bizScene").setValue("");
						}
						if (info.getQuestionPaper() != null) {
							addRow.getCell("questionPaper.topric").setValue(info.getQuestionPaper().getTopric());
						} else {
							addRow.getCell("questionPaper.topric").setValue("");
						}

						addRow.getCell("bizDate").setValue(info.getBizDate());
						if (info.getCreator() != null) {
							addRow.getCell("creator.name").setValue(info.getCreator().getName());
						} else {
							addRow.getCell("creator.name").setValue("");
						}

						if (info.getLastUpdateUser() != null) {
							addRow.getCell("lastUpdateUser.name").setValue(info.getLastUpdateUser().getName());
						} else {
							addRow.getCell("lastUpdateUser.name").setValue("");
						}

						addRow.getCell("createTime").setValue(info.getCreateTime());

						addRow.getCell("lastUpdateTime").setValue(info.getLastUpdateTime());

						if (info.getInputDate() != null) {
							addRow.getCell("inputDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
						}

						if (info.getBizDate() != null) {
							addRow.getCell("bizDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
						}
						if (info.getCreateTime() != null) {
							addRow.getCell("createTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
						}
						if (info.getLastUpdateTime() != null) {
							addRow.getCell("lastUpdateTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
						}
					}
				}
			}
		}
	}

	/**
	 * 查询商机资料
	 */
	public void queryCommerceChance() throws Exception {
		tblCommerceChance.removeRows();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("sysType");
		sic.add("orgUnit.id");
		sic.add("orgUnit.name");
		sic.add("sellProject.id");
		sic.add("sellProject.name");
		sic.add("status");
		sic.add("number");
		sic.add("name");
		sic.add("commerceLevel.id");
		sic.add("commerceLevel.name");
		sic.add("commerceChanceStage.id");
		sic.add("commerceChanceStage.name");
		sic.add("effectiveDate");
		sic.add("commerceSrc");
		sic.add("worth");
		sic.add("probability");
		sic.add("saleMan.id");
		sic.add("saleMan.name");
		sic.add("creator.id");
		sic.add("creator.name");
		sic.add("createtime");
		sic.add("description");
		FilterInfo filter = new FilterInfo();
		if (editData.getId() != null && !editData.getId().equals("")) {
			filter.getFilterItems().add(new FilterItemInfo("customer.id", this.editData.getId()));
			view.setSelector(sic);
			view.setFilter(filter);

			CommerceChanceCollection coll = CommerceChanceFactory.getRemoteInstance().getCommerceChanceCollection(view);
			if (coll != null && coll.size() > 0) {
				for (int i = 0; i < coll.size(); i++) {
					CommerceChanceInfo commerceChanceInfo = coll.get(i);
					tblCommerceChance.checkParsed();
					tblCommerceChance.setEditable(false);
					tblCommerceChance.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
					IRow addRow = tblCommerceChance.addRow();

					if (commerceChanceInfo != null) {
						addRow.getCell("id").setValue(commerceChanceInfo.getId());

						addRow.getCell("sysType").setValue(commerceChanceInfo.getSysType());
						if (commerceChanceInfo.getOrgUnit() != null) {
							addRow.getCell("orgUnit.name").setValue(commerceChanceInfo.getOrgUnit().getName());
						} else {
							addRow.getCell("orgUnit.name").setValue("");
						}
						if (commerceChanceInfo.getSellProject() != null) {
							addRow.getCell("sellProject.name").setValue(commerceChanceInfo.getSellProject().getName());
						} else {
							addRow.getCell("sellProject.name").setValue("");
						}

						addRow.getCell("status").setValue(commerceChanceInfo.getStatus());
						addRow.getCell("number").setValue(commerceChanceInfo.getNumber());
						addRow.getCell("name").setValue(commerceChanceInfo.getName());
						if (commerceChanceInfo.getCommerceLevel() != null) {
							addRow.getCell("commerceLevel.name").setValue(commerceChanceInfo.getCommerceLevel().getName());
						} else {
							addRow.getCell("commerceLevel.name").setValue("");
						}
						if (commerceChanceInfo.getCommerceChanceStage() != null) {
							addRow.getCell("commerceChanceStage.name").setValue(commerceChanceInfo.getCommerceChanceStage().getName());
						} else {
							addRow.getCell("commerceChanceStage.name").setValue("");
						}
						addRow.getCell("effectiveDate").setValue(commerceChanceInfo.getEffectiveDate());
						addRow.getCell("commerceSrc").setValue(commerceChanceInfo.getCommerceSrc());
						addRow.getCell("worth").setValue(commerceChanceInfo.getWorth());

						addRow.getCell("probability").setValue(commerceChanceInfo.getProbability());
						if (commerceChanceInfo.getSaleMan() != null) {
							addRow.getCell("saleMan.name").setValue(commerceChanceInfo.getSaleMan().getName());
						} else {
							addRow.getCell("saleMan.name").setValue("");
						}
//						addRow.getCell("number").setValue(commerceChanceInfo);

						if (commerceChanceInfo.getCreator() != null) {
							addRow.getCell("creator.name").setValue(commerceChanceInfo.getCreator().getName());
						} else {
							addRow.getCell("creator.name").setValue("");
						}

						addRow.getCell("createTime").setValue(commerceChanceInfo.getCreateTime());

						addRow.getCell("description").setValue(commerceChanceInfo.getDescription());

						if (commerceChanceInfo.getEffectiveDate() != null) {
							addRow.getCell("effectiveDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
						}
						if (commerceChanceInfo.getCreateTime() != null) {
							addRow.getCell("createTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
						}
					}
				}
			}
		}
	}

	/**
	 * 查询跟进资料
	 */
	public void queryTrack() throws Exception {
		tblTrack.removeRows();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		FilterInfo filter = new FilterInfo();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		if (editData.getId() != null && !editData.getId().equals("")) {
			filter.getFilterItems().add(new FilterItemInfo("customer.id", this.editData.getId()));
			view.setSelector(sic);
			view.setFilter(filter);
			// 先查商机
			CommerceChanceCollection coll = CommerceChanceFactory.getRemoteInstance().getCommerceChanceCollection(view);
			if (coll != null && coll.size() > 0) {
				for (int i = 0; i < coll.size(); i++) {
					CommerceChanceInfo commerceChanceInfo = coll.get(i);
					if (commerceChanceInfo != null) {
						// 再查商机下的跟进记录
						view = new EntityViewInfo();
						sic = new SelectorItemCollection();
						filter = new FilterInfo();
						sic.add("id");
						sic.add("name");
						sic.add("number");
						sic.add("trackDate");
						sic.add("trackType");
						sic.add("interactionType");
						sic.add("trackEvent");
						sic.add("trackContent");
						sic.add("saleMan.id");
						sic.add("saleMan.name");
						sic.add("creator.name");
						sic.add("createTime");
						sic.add("creator.id");
						sic.add("commerceChanceStage.id");
						sic.add("commerceChanceStage.name");
						filter.getFilterItems().add(new FilterItemInfo("commerceChance.id", commerceChanceInfo.getId()));
						view.setSelector(sic);
						view.setFilter(filter);

						CommerceChanceTrackCollection trackColl = CommerceChanceTrackFactory.getRemoteInstance().getCommerceChanceTrackCollection(view);
						if (trackColl != null && trackColl.size() > 0) {
							for (int j = 0; j < trackColl.size(); j++) {
								CommerceChanceTrackInfo trackInfo = trackColl.get(j);
								tblTrack.checkParsed();
								tblTrack.setEditable(false);
								tblTrack.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
								IRow addRow = tblTrack.addRow();

								if (trackInfo != null) {
									addRow.getCell("id").setValue(trackInfo.getId());
									addRow.getCell("number").setValue(trackInfo.getNumber());
									addRow.getCell("trackDate").setValue(trackInfo.getTrackDate());
									addRow.getCell("trackType").setValue(trackInfo.getInteractionType());
									addRow.getCell("interactionType").setValue(trackInfo.getTrackEvent());
									addRow.getCell("trackEvent").setValue(trackInfo.getCommerceChanceStage() == null ? "" : trackInfo.getCommerceChanceStage().getName());
									addRow.getCell("trackContent").setValue(trackInfo.getTrackContent());
									addRow.getCell("propertyConsultant").setValue(trackInfo.getSaleMan().getName());
									if (trackInfo.getCreator() != null) {
										addRow.getCell("creator.name").setValue(trackInfo.getCreator().getName());
									} else {
										addRow.getCell("creator.name").setValue("");
									}
									addRow.getCell("createTime").setValue(trackInfo.getCreateTime());

									if (trackInfo.getTrackDate() != null) {
										addRow.getCell("trackDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
									}
									if (trackInfo.getCreateTime() != null) {
										addRow.getCell("createTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
									}
								}
							}
						}
					}
				}
			}
		}
	}

	protected void tblSellBill_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row = tblSellBill.getRow(e.getRowIndex());
			Object idObj = row.getCell("id").getValue();
			Object typeObj = row.getCell("type").getValue();
			if (idObj == null) {
				return;
			}
			String idStr = "";
			if (idObj instanceof String) {
				idStr = (String) idObj;
			} else if (idObj instanceof BOSUuid) {
				idStr = ((BOSUuid) idObj).toString();
			}
			if (!idStr.equals("")) {
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, idStr);
				ObjectUuidPK pk = new ObjectUuidPK(idStr);
//				IObjectValue objectValue = DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(), pk);
				String uiClassName = "";
//				if (objectValue instanceof SincerityPurchaseInfo) {
//					uiClassName = "com.kingdee.eas.fdc.sellhouse.client.SincerityPurchaseChangeNameUI";
//				} else if (objectValue instanceof PrePurchaseManageInfo) {
//					uiClassName = "com.kingdee.eas.fdc.sellhouse.client.PrePurchaseManageEditUI";
//				} else if (objectValue instanceof PurchaseManageInfo) {
//					uiClassName = "com.kingdee.eas.fdc.sellhouse.client.PurchaseManageEditUI";
//				} else if (objectValue instanceof SignManageInfo) {
//					uiClassName = "com.kingdee.eas.fdc.sellhouse.client.SignManageEditUI";
//				} else {
//					return;
//				}
				if (typeObj == "1") {
					uiClassName = "com.kingdee.eas.fdc.sellhouse.client.SincerityPurchaseChangeNameUI";
				} else if (typeObj == "2") {
					uiClassName = "com.kingdee.eas.fdc.sellhouse.client.PrePurchaseManageEditUI";
				} else if (typeObj == "3") {
					uiClassName = "com.kingdee.eas.fdc.sellhouse.client.PurchaseManageEditUI";
				} else if (typeObj == "4") {
					uiClassName = "com.kingdee.eas.fdc.sellhouse.client.SignManageEditUI";
				} else {
					return;
				}
				try {
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiClassName, uiContext, null, OprtState.VIEW);
					uiWindow.show();
				} catch (UIException e1) {
					Logger logger = CoreUIObject.getLogger(uiClassName);
					logger.error(e1.getMessage() + "打开" + uiClassName + "界面失败！");
				}
			}
		}

	}

	// protected void tblLinkman_tableClicked(
	// com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
	// throws Exception {
	// String uiClassName =
	// "com.kingdee.eas.fdc.sellhouse.client.CommerceChanceTrackEditUI";
	// CusClientHelper.openDetailPage(this, e, tblLinkman, uiClassName);
	// }

	protected void tblQuestion_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		String uiClassName = "com.kingdee.eas.fdc.sellhouse.client.QuestionPaperAnswerEditUI";
		CusClientHelper.openDetailPage(this, e, tblQuestion, uiClassName);
	}

	protected void tblCommerceChance_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		String uiClassName = "com.kingdee.eas.fdc.sellhouse.client.CommerceChangeNewEditUI";
		CusClientHelper.openDetailPage(this, e, tblCommerceChance, uiClassName);
	}

	protected void tblTrack_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		String uiClassName = "com.kingdee.eas.fdc.sellhouse.client.CommerceChanceTrackEditUI";
		CusClientHelper.openDetailPage(this, e, tblTrack, uiClassName);
	}

	protected void btnQuestion_actionPerformed(ActionEvent e) throws Exception {
		this.getUIContext().put("id", "");
		this.getUIContext().put("fnumber", this.txtNumber.getText());
		this.getUIContext().put("sellProject", this.prmtProject.getValue());
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow curDialog = uiFactory.create(QuestionPaperAnswerEditUI.class.getName(), this.getUIContext(), null, OprtState.ADDNEW);
		curDialog.show();
	}

	public boolean destroyWindow() {
		int i = 1;
		if (isNew) {
			try {
				if(isAddQuestion){
					i = MsgBox.showConfirm2("是否删除对应的客户问卷？");
				}
				if(i == 2){
					SysUtil.abort();
				}
				String sql = "delete from T_MAR_QuestionPaperAnswer where fshecustomerid = '" + this.getUIContext().get("fnumber") + "'";
				builder = new FDCSQLBuilder();
				builder.appendSql(sql);
				builder.execute();
			} catch (BOSException e) {
				logger.error(e.getMessage());
			}
		}
		return super.destroyWindow();
	}
	
	//初始化客户历史信息表
	public void initKDPCustomerDetial(){
		kdtCustomerChangeDetial.checkParsed();
		kdtCustomerChangeDetial.removeRows(false);
		try {
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("reason");
			sic.add("sheCustomer.name");
			sic.add("propertyConsultant.name");
			sic.add("orgUnit.name");
			sic.add("creator.name");
			sic.add("createTime");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("sheCustomer", editData.getId().toString(),CompareType.EQUALS));
	    	SorterItemCollection sort = new SorterItemCollection();
	    	sort.add(new SorterItemInfo("createTime"));
	    	view.setSorter(sort);
			view.setSelector(sic);
			view.setFilter(filter);
			SHECustomerChangeDetailCollection shecccol = SHECustomerChangeDetailFactory.getRemoteInstance().getSHECustomerChangeDetailCollection(view);
			for(int i=0;i<shecccol.size();i++){
				SHECustomerChangeDetailInfo info = shecccol.get(i);
				IRow row = kdtCustomerChangeDetial.addRow();
				row.getCell("sheCustomer.name").setValue(info.getSheCustomer().getName());
				row.getCell("company.name").setValue(info.getOrgUnit().getName());
				row.getCell("propertyConsultant.name").setValue(info.getPropertyConsultant().getName());
				row.getCell("reason").setValue(SHECustomerChangeReasonEnum.getEnum(info.getReason()));
				row.getCell("user.name").setValue(info.getCreator().getName());
				row.getCell("time").setValue(info.getCreateTime());
				row.getCell("id").setValue(info.getId().toString());
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	protected void f7Recommended_dataChanged(DataChangeEvent e) throws Exception {
		if(this.f7Recommended.getValue()!=null){
			this.txtRecommended.setText(((SHECustomerInfo)this.f7Recommended.getValue()).getName());
			this.pkRecommendDate.setValue(FDCCommonServerHelper.getServerTimeStamp());
		}else{
			this.txtRecommended.setText(null);
			this.pkRecommendDate.setValue(null);
		}
	}
	
}