package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FocusTraversalPolicy;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.DataAccessException;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.data.SortType;
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
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CountryFactory;
import com.kingdee.eas.basedata.assistant.CountryInfo;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.customerservice.CmsTimeConferBillCollection;
import com.kingdee.eas.fdc.customerservice.CmsTimeConferBillFactory;
import com.kingdee.eas.fdc.customerservice.CmsTimeConferBillInfo;
import com.kingdee.eas.fdc.customerservice.FDCStdQueationProcessType;
import com.kingdee.eas.fdc.customerservice.ServiceProcessBillCollection;
import com.kingdee.eas.fdc.customerservice.ServiceProcessBillFactory;
import com.kingdee.eas.fdc.customerservice.ServiceProcessBillInfo;
import com.kingdee.eas.fdc.insider.InsiderCollection;
import com.kingdee.eas.fdc.insider.InsiderFactory;
import com.kingdee.eas.fdc.insider.InsiderInfo;
import com.kingdee.eas.fdc.insider.client.CertificateUtil;
import com.kingdee.eas.fdc.insider.client.InsiderApplicationNewEditUI;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.eas.fdc.market.client.QuestionPaperAnswerEditUI;
import com.kingdee.eas.fdc.propertymgmt.CustomerMoveVoucherCollection;
import com.kingdee.eas.fdc.propertymgmt.CustomerMoveVoucherFactory;
import com.kingdee.eas.fdc.propertymgmt.IJoinVoucher;
import com.kingdee.eas.fdc.propertymgmt.JoinStatus;
import com.kingdee.eas.fdc.propertymgmt.JoinType;
import com.kingdee.eas.fdc.propertymgmt.JoinVoucherCollection;
import com.kingdee.eas.fdc.propertymgmt.JoinVoucherFactory;
import com.kingdee.eas.fdc.propertymgmt.JoinVoucherInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMARBaseInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryInfo;
import com.kingdee.eas.fdc.sellhouse.AdapterLogCollection;
import com.kingdee.eas.fdc.sellhouse.AdapterLogInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.CertificateFactory;
import com.kingdee.eas.fdc.sellhouse.CertificateInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerBusinessScopeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerWorkAreaEntryInfo;
import com.kingdee.eas.fdc.sellhouse.EnterprisePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.IFDCCustomer;
import com.kingdee.eas.fdc.sellhouse.LinkmanEntryCollection;
import com.kingdee.eas.fdc.sellhouse.LinkmanEntryFactory;
import com.kingdee.eas.fdc.sellhouse.LinkmanEntryInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SexEnum;
import com.kingdee.eas.fdc.sellhouse.ShareModelEnum;
import com.kingdee.eas.fdc.sellhouse.ShareSellerCollection;
import com.kingdee.eas.fdc.sellhouse.ShareSellerFactory;
import com.kingdee.eas.fdc.sellhouse.ShareSellerInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.TrackPhaseEnum;
import com.kingdee.eas.fdc.sellhouse.TrackRecordCollection;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.fdc.sellhouse.WorkAreaInfo;
import com.kingdee.eas.fdc.sellhouse.XQEntryInfo;
import com.kingdee.eas.fdc.tenancy.BusinessScopeInfo;
import com.kingdee.eas.fdc.tenancy.MarketUnitControlCollection;
import com.kingdee.eas.fdc.tenancy.MarketUnitControlFactory;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatCollection;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatFactory;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo;
import com.kingdee.eas.fdc.tenancy.client.TenancyBillEditUI;
import com.kingdee.eas.fdc.tenancy.client.TenancyClientHelper;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.scm.common.client.GeneralKDPromptSelectorAdaptor;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;
import com.kingdee.util.enums.EnumUtils;

/**
 * output class name
 */
public class CustomerEditUI extends AbstractCustomerEditUI
{
	

	private static final Logger logger = CoreUIObject.getLogger(CustomerEditUI.class);
	UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	SaleOrgUnitInfo saleOrg = SysContext.getSysContext().getCurrentSaleUnit();
	MarketDisplaySetting setting = new MarketDisplaySetting();
	private KDWorkButton btnAddLinkman = null;
	private KDWorkButton btnRemoveLinkman = null;
	private final static BigDecimal ZERO = new BigDecimal("0.00"); 
	private String 	ect=null;
	//new add by renliang at 2011-3-2
	private final static String CERTIFICATE_ID_CARD_NAME="001";
	private final static String CERTIFICATE_ID_CARD_NAME_CHINA="中国";
	
	private String isFrom = "";
	
	protected boolean isShowAttachmentAction() {
		return true;
	}


	/**
	 * output class constructor
	 */
	public CustomerEditUI() throws Exception
	{
		super();
	}
	
	
	//对联系人分录进行获取
	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		CacheServiceFactory.getInstance().discardType(new LinkmanEntryInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new TrackRecordInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new ShareSellerInfo().getBOSType());
		
		FDCCustomerInfo cus = (FDCCustomerInfo) super.getValue(pk);
		
		FilterInfo acctFilter = new FilterInfo();
		acctFilter.getFilterItems().add(new FilterItemInfo("head", pk.toString()));
		
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("*"));
		sel.add(new SelectorItemInfo("fdcCustomer.*"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(acctFilter);
		
		SelectorItemCollection sel2 = new SelectorItemCollection();
		sel2.add(new SelectorItemInfo("*"));
		sel2.add(new SelectorItemInfo("seller.*"));
		sel2.add(new SelectorItemInfo("marketingUnit.*"));
		sel2.add(new SelectorItemInfo("orgUnit.*"));
		EntityViewInfo view2 = new EntityViewInfo();
		view2.getSelector().addObjectCollection(sel2);
		view2.setFilter(acctFilter);
		
		LinkmanEntryCollection linkmanList = LinkmanEntryFactory.getRemoteInstance().getLinkmanEntryCollection(view2);
		cus.getLinkmanList().addCollection(linkmanList);
		
		ShareSellerCollection shareSellerColl = ShareSellerFactory.getRemoteInstance().getShareSellerCollection(view2);
		cus.getShareSellerList().addCollection(shareSellerColl);
		return cus;
	}
	
	public void onLoad() throws Exception {
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionAddCommerceChance.setEnabled(true);
		actionRemoveLinkman.setEnabled(true);
		ect=(String)this.getUIContext().get("ECT");
			
		SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 999, 1);
		this.spinTradeTime.setModel(model);
		
		SpinnerNumberModel model1 = new SpinnerNumberModel(0, 0, 999, 1);
		this.spinTenTradeTime.setModel(model1);
		
		
		//specilAgio
	
//		this.numtxtTradeTime.getInputMap().put(KeyStroke.getKeyStroke('.', true), KDNumberTextField.undoAction);
		
		this.f7salesman.setRequired(true);
		
		//this.f7salesman.setEnabled(false);
		this.btnAddLine.setToolTipText("新增行");
		
		initLinkmanTable();		
		initShareSeller();
		initAdapterLog();
		initSaleRecordTable();
		
		//this.f7Country.setEnabledMultiSelection(true);
		this.f7WorkArea.setEnabledMultiSelection(true);
		this.f7BusinessScope.setEnabledMultiSelection(true);
		
		super.onLoad();
		///tblLinkman.getColumn("phone").setRequired(true);
		initNatureEnterpriseF7(this,enterpriceProperty,null);
		initBusinessScopeF7(this, f7BusinessScope, null);
		initCooperateModeF7(this, f7CooperateMode, null);
		lcCountry.setBoundLabelText("国家/区域");
		//this.f7HabitationArea.setEnabledMultiSelection(true);
//		if(STATUS_ADDNEW.equals(this.getOprtState())){
//			this.f7CustomerManager.setValue(this.f7salesman.getValue());
//			if(!"".equals(ect)&&ect!=null&&ect.equalsIgnoreCase("EC")){
//				boxCustomerType.setSelectedItem(com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum.EnterpriceCustomer);
//			}else{
//				boxCustomerType.setSelectedItem(com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum.PersonalCustomer);
//		        
//			}
//		}
		if(!STATUS_ADDNEW.equals(this.getOprtState())){
			initPropertyHouse();
			initCustomerServiceSpecial();
			//this.actionRev.setEnabled(true);
			//this.actionRefund.setEnabled(true);
		}
		
		//this.boxSex.addItem("");
		
		this.menuBar.remove(menuBiz);
		this.toolBar.remove(btnCancelCancel);
		this.toolBar.remove(btnCancel);
		
		this.actionRemove.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCustomerAdapter.setEnabled(false);
		this.actionCustomerShare.setEnabled(false);
		this.actionCustomerCancelShare.setEnabled(false);
		if(STATUS_EDIT.equals(this.getOprtState()))
		{
			if(this.tblShareSeller.getRowCount()>0)
			{
				for(int i=0;i<tblShareSeller.getRowCount();i++)
				{
					IRow row = tblShareSeller.getRow(i);
					ShareModelEnum shareModel = (ShareModelEnum)row.getCell("shareModel").getValue();
					if(ShareModelEnum.shareMarket.equals(shareModel) || ShareModelEnum.shareOrgUnit.equals(shareModel))
					{
						row.getCell("isAgainShare").getStyleAttributes().setLocked(true);
						row.getCell("isUpdate").getStyleAttributes().setLocked(false);
					}else
					{
						row.getCell("isAgainShare").getStyleAttributes().setLocked(false);
						row.getCell("isUpdate").getStyleAttributes().setLocked(false);
					}
				}
			}		
			String fdcCustomerID = this.editData.getId().toString();
			if(!hasUpdatePermission(fdcCustomerID))
			{
				MsgBox.showInfo("对该客户没有修改权!");
				this.abort();
			}
			if(CustomerShareUI.hasSharePermission(fdcCustomerID))
			{
				this.actionCustomerShare.setEnabled(true);
				this.actionCustomerCancelShare.setEnabled(true);
			}
			
			if(CustomerAdapterUI.hasAdpaterPermission(fdcCustomerID)) {
				this.actionCustomerAdapter.setEnabled(true);
			}
		}
		
		this.txtNumber.setMaxLength(70);
		this.f7ReceptionType.setRequired(true);
		this.pkFirstDate.setRequired(true);
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
		}
		
		setVisableAboutCustomerType();
		setTabVisable();
		SimpleKDTSortManager.setTableSortable(tblShareSeller);
		//如果销售页签中有数据,则修改时不能取消售楼属性
		if(this.tblSaleRecord.getRowCount() != 0  ||  this.tblSaleCommerce.getRowCount() != 0){
			this.chkIsForSHE.setEnabled(false);
		}
		if(this.tblTenancyRecord.getRowCount() != 0  ||  this.tblTenancyCommerce.getRowCount() != 0){
			this.chkIsForTen.setEnabled(false);
		}	
		SellProjectInfo sp = (SellProjectInfo) this.f7Project.getValue();
		
		setGroupFilter(sp);
		
		addGroupDataListener(f7FamillyEarning);
		addGroupDataListener(f7ReceptionType);
		addGroupDataListener(f7HabitationArea);
		addGroupDataListener(f7CustomerGrade);
		addGroupDataListener(f7CustomerOrigin);
		
/*		这一段是谁加的？  会导致客户序时簿打开的界面无法点商机跟进
  		MoneySysTypeEnum latSysType = (MoneySysTypeEnum)getUIContext().get("latSysType");
		if(latSysType == null){
			actionAddCommerceChance.setEnabled(false);
		}*/
		
		if(getOprtState().equals(this.STATUS_VIEW)){
			actionInsider.setEnabled(true);
		}else{
			actionInsider.setEnabled(false);
		}
		FilterInfo filter =  new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fdcCustomer", this.editData.getId()));
		if(InsiderFactory.getRemoteInstance().exists(filter)){
			actionInsider.setEnabled(false);
		}
		
		 //new add by renliang;
		 if(this.getUIContext().get("isInitCertifaicate")!=null){
			 initNewCertificateName(false);
		 }else{
			 initNewCertificateName(true);
		 }
		 
		 if(this.getUIContext().get("CustomerView")!=null){
			 this.actionAddNew.setEnabled(false);
			 this.actionEdit.setEnabled(false);
			 this.actionSave.setEnabled(false);
			 this.getUIContext().put("CustomerView",null);
		 }
		 
		 if(this.getUIContext().get("isPerson")!=null){
			 String isPerson = this.getUIContext().get("isPerson").toString();
			 if("true".equals(isPerson)){
				 initNewCertificateName(true);
			 }else{
				 initNewCertificateName(false);
			 }
		 }
		 this.tabNew.addChangeListener(new ChangeListener(){
				public void stateChanged(ChangeEvent e) {
//					int comIndex = tabNew.getSelectedIndex();
//					if(comIndex==6){
						try {
							getQuestionPaperAnswer();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
//					}
				}
			});
		 
		 
		 FilterInfo filterInfoLevel = SHEHelper.getDefaultFilterForTree();
			filterInfoLevel.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.TRUE,
							CompareType.EQUALS));
			filterInfoLevel.getFilterItems().add(
					new FilterItemInfo("type.id",
							CRMConstants.COMMERCECHANCE_LEVEL_ID,
							CompareType.EQUALS));
//			if (this.editData.getProject() != null) {
//				filterInfoLevel.getFilterItems().add(
//						new FilterItemInfo("sellProject.id", this.editData
//								.getProject().getId().toString(),
//								CompareType.EQUALS));
//			} else {
//				if (this.getUIContext().get("sellProject") != null) {
//					SellProjectInfo sell = (SellProjectInfo) this.getUIContext()
//							.get("sellProject");
//					filterInfoLevel.getFilterItems().add(
//							new FilterItemInfo("sellProject.id", sell.getId()
//									.toString(), CompareType.EQUALS));
//				}
//			}
			filterInfoLevel.getFilterItems().add(
					new FilterItemInfo("sellProject.id", null, CompareType.EQUALS));
			filterInfoLevel.setMaskString("#0 and #1 and #2 and #3");
			
			String queryInfoLevel = "com.kingdee.eas.fdc.sellhouse.app.CommerceAssistantForSHEQuery";
			SHEHelper.initF7(this.prmtLevel, queryInfoLevel, filterInfoLevel);
			
			this.actionInsider.setVisible(false);
			this.actionAddCommerceChance.setVisible(false);
			this.f7Project.setEnabled(false);
			
			EntityViewInfo saleManView = CommerceHelper.getPermitSalemanView(this.editData.getProject());
			this.f7salesman.setEntityViewInfo(saleManView);	
			this.f7CustomerManager.setEntityViewInfo(saleManView);
			
			EntityViewInfo evi= new EntityViewInfo();
			filter = new FilterInfo();
			
			filter.getFilterItems().add(new FilterItemInfo("statrusing",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("CU.id",SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()));
			evi.setFilter(filter);
			
			HashMap ctx=new HashMap();
			ctx.put("EntityViewInfo", evi);
			ctx.put("EnableMultiSelection", new Boolean(false));
			ctx.put("HasCUDefaultFilter", new Boolean(true));
			this.prmtClassify.setQueryInfo("com.kingdee.eas.fdc.market.app.ChannelTypeQuery");	
			this.prmtClassify.setDisplayFormat("$name$");		
	        this.prmtClassify.setEditFormat("$number$");		
		    this.prmtClassify.setCommitFormat("$number$");
		    this.prmtClassify.setEntityViewInfo(evi);
	}
	public void getQuestionPaperAnswer() throws Exception{
		KDTable tblQuestion = this.tblQuestion;
		tblQuestion.getStyleAttributes().setLocked(true);
		tblQuestion.removeRows();
		tblQuestion.getColumn("inputDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		tblQuestion.getColumn("createTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		tblQuestion.getColumn("lastUpdateTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		String customerID = this.editData.getId().toString();
		EntityViewInfo questionPaperView=new EntityViewInfo();
		SelectorItemCollection questionPaperSic=new SelectorItemCollection();
		questionPaperSic.add("id");
		questionPaperSic.add("number");
		questionPaperSic.add("inputDate");
		questionPaperSic.add("questionPaper.topric");
		questionPaperSic.add("sellProject");
		questionPaperSic.add("sellProject.name");
		questionPaperSic.add("customer");
		questionPaperSic.add("customer.name");
		questionPaperSic.add("creator.name");
		questionPaperSic.add("createTime");
		questionPaperSic.add("lastUpdateUser.name");
		questionPaperSic.add("lastUpdateTime");
		questionPaperSic.add("commerceChance.name");
		questionPaperSic.add("purchase.name");
		questionPaperSic.add("commerceChance.id");
		questionPaperSic.add("purchase.id");
		FilterInfo questionPaperFilter=new FilterInfo();
		questionPaperFilter.getFilterItems().add(new FilterItemInfo("customer.id",customerID));
		questionPaperView.setSelector(questionPaperSic);
		questionPaperView.setFilter(questionPaperFilter);
		SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
		sorterItemInfo.setSortType(SortType.DESCEND);
		questionPaperView.getSorter().add(sorterItemInfo);
		QuestionPaperAnswerCollection questionPaperCol = QuestionPaperAnswerFactory.getRemoteInstance().getQuestionPaperAnswerCollection(questionPaperView);
		QuestionPaperAnswerInfo questionPaperAnswerInfo = null;
		for(int i=0;i<questionPaperCol.size();i++) {
			questionPaperAnswerInfo = (QuestionPaperAnswerInfo)questionPaperCol.get(i);
			IRow row = tblQuestion.addRow();			
			row.getCell("id").setValue(questionPaperAnswerInfo.getId());
			row.getCell("number").setValue(questionPaperAnswerInfo.getNumber());
			row.getCell("inputDate").setValue(questionPaperAnswerInfo.getInputDate());
			row.getCell("questionPaper.topric").setValue(questionPaperAnswerInfo.getQuestionPaper()!=null?questionPaperAnswerInfo.getQuestionPaper().getTopric():"");
			row.getCell("sellProject.name").setValue(questionPaperAnswerInfo.getSellProject()!=null?questionPaperAnswerInfo.getSellProject().getName():"");
			row.getCell("customer.name").setValue(questionPaperAnswerInfo.getCustomer()!=null?questionPaperAnswerInfo.getCustomer().getName():"");
			row.getCell("creator.name").setValue(questionPaperAnswerInfo.getCreator().getName());
			row.getCell("createTime").setValue(questionPaperAnswerInfo.getCreateTime());
			row.getCell("lastUpdateUser.name").setValue(questionPaperAnswerInfo.getLastUpdateUser().getName());
			row.getCell("lastUpdateTime").setValue(questionPaperAnswerInfo.getLastUpdateTime());
			row.getCell("commerceChance.name").setValue(questionPaperAnswerInfo.getCommerceChance()!=null?questionPaperAnswerInfo.getCommerceChance().getName():"");
			row.getCell("purchase.name").setValue(questionPaperAnswerInfo.getPurchase()!=null?questionPaperAnswerInfo.getPurchase().getName():"");
			row.getCell("commerceChance.id").setValue(questionPaperAnswerInfo.getCommerceChance()!=null?questionPaperAnswerInfo.getCommerceChance().getId():null);
			row.getCell("purchase.id").setValue(questionPaperAnswerInfo.getPurchase()!=null?questionPaperAnswerInfo.getPurchase().getId():null);
		}
	}
	
	 protected void tblQuestion_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		 super.tblQuestion_tableClicked(e);
		 if (e.getButton() == 1 && e.getClickCount() == 2) {
				if(e.getType()!=1) return;
				
				IRow row = this.tblQuestion.getRow(e.getRowIndex());
				String id = row.getCell("id").getValue().toString();
				if (id == null) {
					return;
				}
				
				CommerceHelper.openTheUIWindow(this,QuestionPaperAnswerEditUI.class.getName(),id);
			}	
	 }
	
	/**
	 * 初始化证件名称的过滤条件
	 * @param isPerson
	 */
	private void initNewCertificateName(boolean isPerson) {
		
		this.txtCertificate.setEditable(false);
		
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		if(isPerson){
			filterInfo.getFilterItems().add(new FilterItemInfo("customerType", CustomerTypeEnum.PERSONALCUSTOMER_VALUE, CompareType.EQUALS));
			filterInfo.setMaskString("#0");
		}else{
			filterInfo.getFilterItems().add(new FilterItemInfo("customerType", CustomerTypeEnum.ENTERPRICECUSTOMER_VALUE, CompareType.EQUALS));
			filterInfo.setMaskString("#0");
		}
		filterInfo.getFilterItems().add(new FilterItemInfo("customerType", "3Other", CompareType.EQUALS));
		filterInfo.setMaskString("#0 or #1");
		evi.setFilter(filterInfo);
		this.txtCertificate.setEntityViewInfo(evi);
		
	}


	private void addGroupDataListener(final KDBizPromptBox f7){
		f7.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				if(f7.getValue() == null){
					return;
				}
				if(CustomerEditUI.this.f7Project.getValue() == null){
					MsgBox.showInfo("请先选择初始登记项目!");
					f7.setValue(null);
				}
			}
		});
	}
	
	protected void f7Project_dataChanged(DataChangeEvent e) throws Exception {
		super.f7Project_dataChanged(e);		
		this.initF7Filter();
		SellProjectInfo sellProject = (SellProjectInfo) this.f7Project.getValue();
		this.setGroupFilter(sellProject);
	}
	private void setGroupFilter(SellProjectInfo sp) {
		if (sp == null){
			return;
		}
		String spId = sp.getId().toString();
		//设置家庭收入的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7FamillyEarning, spId, "家庭收入", "CustomerArch");		
		//设置接待方式的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7ReceptionType, spId, "接待方式", "CustomerArch");		
		//设置居住区域的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7HabitationArea, spId, "居住区域", "CustomerArch");		
		//设置客户分级的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7CustomerGrade, spId, "客户分级", "CustomerArch");		
		//设置客户来源的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7CustomerOrigin, spId, "客户来源", "CustomerArch");						
	}
	private void initF7Filter() {
		if(this.f7FamillyEarning.getEntityViewInfo()!=null){
			this.f7FamillyEarning.setEntityViewInfo(null);
		}
		if(this.f7ReceptionType.getEntityViewInfo()!=null){			
			this.f7ReceptionType.setEntityViewInfo(null);
		}			
		if(this.f7HabitationArea.getEntityViewInfo()!=null){			
			this.f7HabitationArea.setEntityViewInfo(null);
		}
		if(this.f7CustomerGrade.getEntityViewInfo()!=null){			
			this.f7CustomerGrade.setEntityViewInfo(null);
		}
		if(this.f7CustomerOrigin.getEntityViewInfo()!=null){			
			this.f7CustomerOrigin.setEntityViewInfo(null);
		}
	}
	


	private void initSaleRecordTable() {
		this.tblSaleRecord.checkParsed();
		this.tblSaleRecord.getColumn("prePurchaseAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblSaleRecord.getColumn("dealAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblSaleRecord.getColumn("dealPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		if(this.tblSaleRecord.getColumn("specilAgio")!=null){
			this.tblSaleRecord.getColumn("specilAgio").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		}
	}


	protected void boxCustomerType_itemStateChanged(ItemEvent e) throws Exception {
		super.boxCustomerType_itemStateChanged(e);
//		setVisableAboutCustomerType();
	}
	
	private void setVisableAboutCustomerType() {
//		CustomerTypeEnum customerType = (CustomerTypeEnum) boxCustomerType.getSelectedItem();
//		boolean isPersonal = false;
//		boolean isEnterprice = false;
//		String desLabelText = "";
//		if(CustomerTypeEnum.PersonalCustomer.equals(customerType)){
//			isPersonal = true;
//			txtPhone.setRequired(isPersonal);
//			desLabelText = "个人简介";
//			lcHabitationArea.setBoundLabelText("居住区域");
//			kDLabelContainer2.setBoundLabelText("工作区域");
//			lcBusinessScope.setVisible(false);
//			lcCooperateMode.setVisible(false);
//			lcCustomerManager.setVisible(false);
//			//new add by renliang at 2011-3-2
//			initNewCertificateName(true);
//		}else if(CustomerTypeEnum.EnterpriceCustomer.equals(customerType)){
//			isEnterprice = true;
//			txtPhone.setRequired(isPersonal);
//			desLabelText = "企业简介";
//			lcHabitationArea.setBoundLabelText("所在区域");
//			kDLabelContainer2.setBoundLabelText("服务区域");
//			//lcBusinessScope.setVisible(true);
//			//lcCooperateMode.setVisible(true);
//			//lcCustomerManager.setVisible(true);
//			initNewCertificateName(false);
//		}else{
//			initNewCertificateName(true);
//			logger.error("error.! customerType: " + customerType);
//		}
//		this.contEnterpriceProperty.setVisible(isEnterprice);
//		this.contEnterpriceSize.setVisible(isEnterprice);
//		this.contIndustry.setVisible(isEnterprice);
//		this.contEnterpriceHomepage.setVisible(isEnterprice);
		
//		this.lcSex.setVisible(isPersonal);
//		this.lcFamillyEarning.setVisible(isPersonal);
//		this.lcEmployment.setVisible(isPersonal);
//		this.lcWorkCompany.setVisible(isPersonal);
//		this.lcDescription.setBoundLabelText(desLabelText);
//		set(isPersonal);
	}
	/**根据选中 客户类型*/
	private void set(boolean isPersonal) {
		/*boxCertificateName.removeAllItems();
		if(isPersonal){
			boxCertificateName.addItem(com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum.IDCard);
			boxCertificateName.addItem(com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum.Passport);
			boxCertificateName.addItem(com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum.PRC);
			boxCertificateName.addItem(com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum.MilitaryID);
			boxCertificateName.addItem(com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum.Others);
			boxCertificateName.setSelectedItem(com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum.IDCard);		
		}else if(!isPersonal){
			boxCertificateName.addItem(com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum.License);
			boxCertificateName.addItem(com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum.Histolysis);
			boxCertificateName.addItem(com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum.Revenuer);
			boxCertificateName.addItem(com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum.Corporative);
			boxCertificateName.addItem(com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum.Others);
			boxCertificateName.setSelectedItem(com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum.License);		
		}*/

	
	}


	private void setTabVisable(){
		boolean isForSHE = this.chkIsForSHE.isSelected();
		boolean isForTen = this.chkIsForTen.isSelected();
		boolean isForPPM = this.chkIsForPPM.isSelected();
		
		this.tabNew.remove(panelSale);
		this.tabNew.remove(panelTenancy);
		this.tabNew.remove(panelManager);
		this.tabNew.remove(panelCustomer);
		this.tabNew.remove(panelCusService);
		this.tabNew.remove(panelQuestion);
		
		kDTabbedPane1.remove(tblShareSeller);
		kDTabbedPane1.remove(tblAdapterLog);
//		this.tabNew.remove(3);
		if(isForSHE){
//			this.tabNew.add(panelSale, "售楼");
		}
		if(isForTen){
//			this.tabNew.add(panelTenancy, "租赁");
		}
		if(isForPPM){
//			this.tabNew.add(panelManager, "物业");
		}
//		tabNew.add(panelQuestion, resHelper.getString("panelQuestion.constraints"));
	}
	
	protected void chkIsForSHE_actionPerformed(ActionEvent e) throws Exception {
		super.chkIsForSHE_actionPerformed(e);
		setTabVisable();
	}
	
	protected void chkIsForTen_actionPerformed(ActionEvent e) throws Exception {
		super.chkIsForTen_actionPerformed(e);
		setTabVisable();
	}
	
	protected void chkIsForPPM_actionPerformed(ActionEvent e) throws Exception {
		super.chkIsForPPM_actionPerformed(e);
		setTabVisable();
	}
	
	
	/**
	 * 初始化跟进记录列表
	 * */
	public void initTrackRecordTable() {		
		tblTrackRecord.checkParsed();
		tblTrackRecord.getStyleAttributes().setLocked(true);
		tblTrackRecord.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);	
		tblTrackRecord.getColumn("name").getStyleAttributes().setHided(true);
		tblTrackRecord.getColumn("head.name").getStyleAttributes().setHided(true);
		tblTrackRecord.getColumn("createTime").getStyleAttributes().setNumberFormat("YYYY-MM-DD");
		
		if(this.editData!=null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("head.id",this.editData.getId().toString()));
			//filter.getFilterItems().add(new FilterItemInfo("sellProject.id",this.editData.getProject().getId().toString()));
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("head.name"));
			selector.add(new SelectorItemInfo("eventType.name"));
			selector.add(new SelectorItemInfo("receptionType.name"));
			selector.add(new SelectorItemInfo("commerceChance.name"));
			selector.add(new SelectorItemInfo("classify.name"));
			view.setSelector(selector);
			TrackRecordCollection trackColl = null;			
			try {
				trackColl = TrackRecordFactory.getRemoteInstance().getTrackRecordCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				this.handleException(e);
			}				
			
			this.tblTrackRecord.removeRows();
			if(trackColl != null){
				for(int i=0;i<trackColl.size();i++) {
					TrackRecordInfo trackInfo = trackColl.get(i);

					//解决第一条跟进后，更改当前的首次接待方式.否则只是数据库里更改，但当前界面保存后又成旧的了
					if(trackColl.size()==1 && this.getOprtState().equals(OprtState.EDIT)) {
						this.f7ReceptionType.setValue(trackInfo.getReceptionType());
					}
					
					IRow row = this.tblTrackRecord.addRow();
					row.setUserObject(trackInfo);
					row.getCell("id").setValue(trackInfo.getId().toString());
					row.getCell("number").setValue(trackInfo.getNumber());
					row.getCell("name").setValue(trackInfo.getName());
					row.getCell("eventDate").setValue(trackInfo.getEventDate());
					row.getCell("head.name").setValue(trackInfo.getHead());
					row.getCell("trackType").setValue(trackInfo.getTrackType());
					row.getCell("trackResult").setValue(trackInfo.getTrackResult()==null?"":trackInfo.getTrackResult().getAlias());
					row.getCell("eventType.name").setValue(trackInfo.getEventType()==null?"":trackInfo.getEventType().getName());
					row.getCell("receptionType.name").setValue(trackInfo.getReceptionType()==null?"":trackInfo.getReceptionType().getName());
					row.getCell("commerceChance.name").setValue(trackInfo.getCommerceChance()==null?"":trackInfo.getCommerceChance().getName());
					row.getCell("description").setValue(trackInfo.getDescription());
					row.getCell("createTime").setValue(trackInfo.getCreateTime());
					row.getCell("classify").setValue(trackInfo.getClassify()==null?"":trackInfo.getClassify().getName());
				}	
			}
		}
	}
	
	
	/**
	 *售楼认购记录    客户分录PurchaseCustomerInfo
	 */	
	private void initSalePurchaseRecord() {
		tblSaleRecord.checkParsed();
		//保存以后，需要清空表格，不然已有数据出现复制情况 eric_wang 2010.07.27
		tblSaleRecord.removeRows();
		tblSaleRecord.getStyleAttributes().setLocked(false);
		tblSaleRecord.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblSaleRecord.getColumn("id").getStyleAttributes().setHided(true);
		
		if(this.editData!=null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("customer.id",this.editData.getId().toString()));
			//filter.getFilterItems().add(new FilterItemInfo("customer.project.id",this.editData.getProject().getId().toString()));
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("head.*"));
			selector.add(new SelectorItemInfo("head.sincerityPurchase.number"));
			selector.add(new SelectorItemInfo("customer.id"));
			selector.add(new SelectorItemInfo("head.room.number"));
			//selector.add(new SelectorItemInfo("customer.project.id"));
			view.setSelector(selector);
			PurchaseCustomerInfoCollection trackColl = null;			
			try {
				trackColl = PurchaseCustomerInfoFactory.getRemoteInstance().getPurchaseCustomerInfoCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				this.handleException(e);
			}				
			if(trackColl==null) return;
			for(int i=0;i<trackColl.size();i++) {
				PurchaseCustomerInfoInfo custInfo = trackColl.get(i);
				PurchaseInfo trackInfo = custInfo.getHead();
				if(trackInfo!=null) {
					IRow row = this.tblSaleRecord.addRow();
					row.setUserObject(trackInfo);
					row.getCell("id").setValue(trackInfo.getId().toString());
					row.getCell("number").setValue(trackInfo.getNumber());
					row.getCell("purchaseState").setValue(trackInfo.getPurchaseState());
					row.getCell("room.number").setValue(trackInfo.getRoom()==null?"":trackInfo.getRoom().getNumber());
					row.getCell("customer").setValue(trackInfo.getCustomerNames());
					row.getCell("sincerityPurchase.number").setValue(trackInfo.getSincerityPurchase() == null ? "" : trackInfo.getSincerityPurchase().getNumber());
					row.getCell("prePurchaseAmount").setValue(trackInfo.getPrePurchaseAmount());
					row.getCell("prePurchaseCurrency.name").setValue(trackInfo.getPrePurchaseCurrency());
					row.getCell("prePurchaseDate").setValue(trackInfo.getPrePurchaseDate());
					row.getCell("dealAmount").setValue(trackInfo.getAmount());
					row.getCell("dealPrice").setValue(trackInfo.getDealPrice());
					row.getCell("payType.name").setValue(trackInfo.getPayType());
					row.getCell("salesman.name").setValue(trackInfo.getSalesman());
					row.getCell("prePurchaseAuditor.name").setValue(trackInfo.getPrePurchaseAuditor());
					row.getCell("prePurchaseAuditDate").setValue(trackInfo.getPrePurchaseAuditDate());
					row.getCell("sellType").setValue(trackInfo.getSellType());
					row.getCell("specilAgio").setValue(trackInfo.getSpecialAgio());
				}
			}	
		}
	}
	
	/**
	 * 客户会员分录 insider
	 */
	public void initInside(){
		this.tblInsider.checkParsed();
		this.tblInsider.removeRows(false);
		tblInsider.getStyleAttributes().setLocked(false);
		tblInsider.getColumn("id").getStyleAttributes().setHided(true);
		if(this.editData!=null && this.editData.getId()!=null){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id",this.editData.getId().toString()));
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("vipLevel.id"));
			selector.add(new SelectorItemInfo("vipLevel.name"));
			selector.add(new SelectorItemInfo("vipLevel.number"));
			view.setSelector(selector);
			InsiderCollection insiderColl = null;
			try {
				insiderColl = InsiderFactory.getRemoteInstance().getInsiderCollection(view);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(insiderColl!=null && insiderColl.size()>0){
				for(int i=0;i<insiderColl.size();i++){
					InsiderInfo insiderInfo = insiderColl.get(i);
					IRow row = this.tblInsider.addRow();
					row.setUserObject(insiderInfo);
					row.getCell("id").setValue(insiderInfo.getId());
					row.getCell("insider").setValue(insiderInfo.getCustomerName());
					row.getCell("insiderCode").setValue(insiderInfo.getInsiderCode());
					row.getCell("vipLevel").setValue(insiderInfo.getVipLevel());
					row.getCell("requestDate").setValue(insiderInfo.getRequestDate());
					row.getCell("bizDate").setValue(insiderInfo.getBizDate());
					row.getCell("insiderRemove").setValue(insiderInfo.getInsiderRemove());
					row.getCell("insiderHortation").setValue(insiderInfo.getInsiderHortation());
				}
			}
		}
	}
	
	/**
	 * 租赁认租记录   客户分录 TenancyCustomerEntry
	 */
	private void initTenancyBillRecord() {
		this.tblTenancyRecord.checkParsed();
		//保存以后，需要清空表格，不然已有数据出现复制情况 eric_wang 2010.07.29
		tblTenancyRecord.removeRows();
		tblTenancyRecord.getStyleAttributes().setLocked(false);
		tblTenancyRecord.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblTenancyRecord.getColumn("id").getStyleAttributes().setHided(true);
		
		if(this.editData!=null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id",this.editData.getId().toString()));
			//filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.project.id",this.editData.getProject().getId().toString()));
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("tenancyBill.*"));
			selector.add(new SelectorItemInfo("fdcCustomer.id"));
			//selector.add(new SelectorItemInfo("fdcCustomer.project.id"));
			view.setSelector(selector);
			TenancyCustomerEntryCollection trackColl = null;			
			try {
				trackColl = TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				this.handleException(e);
			}				
			
			if(trackColl==null) return;
			for(int i=0;i<trackColl.size();i++) {
				TenancyCustomerEntryInfo custInfo = trackColl.get(i);
				TenancyBillInfo trackInfo = custInfo.getTenancyBill();
				if(trackInfo!=null) {
					IRow row = this.tblTenancyRecord.addRow();
					row.setUserObject(trackInfo);
					row.getCell("id").setValue(trackInfo.getId().toString());
					row.getCell("number").setValue(trackInfo.getNumber());
					row.getCell("tenancyState").setValue(trackInfo.getTenancyState());
					row.getCell("tenancyName").setValue(trackInfo.getTenancyName());
					row.getCell("tenRoomsDes").setValue(trackInfo.getTenRoomsDes());
					row.getCell("tenAttachesDes").setValue(trackInfo.getTenAttachesDes());
					row.getCell("tenCustomerDes").setValue(trackInfo.getTenCustomerDes());
					row.getCell("tenancyType").setValue(trackInfo.getTenancyType());
					row.getCell("oldTenancyBill.tenancyName").setValue(trackInfo.getOldTenancyBill()==null?"":trackInfo.getOldTenancyBill().getTenancyName());
					row.getCell("startDate").setValue(trackInfo.getStartDate());
					row.getCell("leaseCount").setValue(trackInfo.getLeaseCount());
					row.getCell("endDate").setValue(trackInfo.getEndDate());
					row.getCell("leaseTime").setValue(String.valueOf(trackInfo.getLeaseTime()));
					row.getCell("flagAtTerm").setValue(trackInfo.getFlagAtTerm());
					row.getCell("tenancyAdviser.name").setValue(trackInfo.getTenancyAdviser());
					row.getCell("agency.name").setValue(trackInfo.getAgency());
					row.getCell("dealTotalRent").setValue(trackInfo.getDealTotalRent());
					row.getCell("standardTotalRent").setValue(trackInfo.getStandardTotalRent());
					row.getCell("depositAmount").setValue(trackInfo.getDepositAmount());
					row.getCell("firstPayRent").setValue(trackInfo.getFirstPayRent());
					row.getCell("deliveryRoomDate").setValue(trackInfo.getDeliveryRoomDate());
					row.getCell("description").setValue(trackInfo.getDescription());
					row.getCell("specialClause").setValue(trackInfo.getSpecialClause());
					row.getCell("createTime").setValue(trackInfo.getCreateTime());
				}
			}	
		}
	}
	
	/**
	 * 商机 :包括售楼和租赁  
	 * @param type  :  Sale  , Tenancy
	 */
	public void initCommerceChanceRecord(String type) {
		if(type==null) return;
		//保存以后，需要清空表格，不然已有数据出现复制情况 eric_wang 2010.07.27
		
		this.tblSaleCommerce.checkParsed();
		//this.tblSaleCommerce.removeRows();
		this.tblSaleCommerce.getStyleAttributes().setLocked(false);
		this.tblSaleCommerce.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblSaleCommerce.getColumn("id").getStyleAttributes().setHided(true);
		
		this.tblTenancyCommerce.checkParsed();
		//tblTenancyCommerce.removeRows();
		this.tblTenancyCommerce.getStyleAttributes().setLocked(false);
		this.tblTenancyCommerce.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblTenancyCommerce.getColumn("id").getStyleAttributes().setHided(true);
		
		if(this.editData!=null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id",this.editData.getId().toString()));
			//filter.getFilterItems().add(new FilterItemInfo("sellProject.id",this.editData.getProject().getId().toString()));
			
			if(type.equals("Sale"))
				filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SalehouseSys.getValue()));
			else if(type.equals("Tenancy"))
				filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.TenancySys.getValue()));
			
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("sellProject.name"));
			selector.add(new SelectorItemInfo("saleMan.name"));
			selector.add(new SelectorItemInfo("commerceLevel.name"));
			selector.add(new SelectorItemInfo("fdcCustomer.name"));
			selector.add(new SelectorItemInfo("creator.name"));
			view.setSelector(selector);
			CommerceChanceCollection trackColl = null;			
			try {
				trackColl = CommerceChanceFactory.getRemoteInstance().getCommerceChanceCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				this.handleException(e);
			}				
			
			if(trackColl==null) return;
			for(int i=0;i<trackColl.size();i++) {
				CommerceChanceInfo trackInfo = trackColl.get(i);
				IRow row = null;
				if(type.equals("Sale"))
					row = this.tblSaleCommerce.addRow();
				else if(type.equals("Tenancy"))
					row = this.tblTenancyCommerce.addRow();
				row.setUserObject(trackInfo);
				row.getCell("id").setValue(trackInfo.getId().toString());
				row.getCell("number").setValue(trackInfo.getNumber());
				row.getCell("name").setValue(trackInfo.getName());
				row.getCell("sysType").setValue(trackInfo.getSysType());
				row.getCell("sellProject.name").setValue(trackInfo.getSellProject());
				row.getCell("saleMan.name").setValue(trackInfo.getSaleMan().getName());
				row.getCell("commerceLevel.name").setValue(trackInfo.getCommerceLevel());
				row.getCell("commerceStatus").setValue(trackInfo.getCommerceStatus());
				row.getCell("fdcCustomer.name").setValue(trackInfo.getFdcCustomer());
				row.getCell("creator.name").setValue(trackInfo.getCreator().getName());
				row.getCell("createTime").setValue(trackInfo.getCreateTime());
			}	
		}
		
	}
	
	
	
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
	 * 初始化转接历史记录列表
	 * */
	private void initAdapterLog()
	{
		tblBaseSet(this.tblAdapterLog);
		
		IColumn columnAdapterDate = this.tblAdapterLog.getColumn("adapterDate");
		columnAdapterDate.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnAdapterDate.setEditor(createTxtCellEditor(80, false));
		
		KDCheckBox chkBox = new KDCheckBox();
		ICellEditor checkBox = new KDTDefaultCellEditor(chkBox);
		IColumn columnIsAdapterInter = this.tblAdapterLog.getColumn("isAdapterInter");
		columnIsAdapterInter.setEditor(checkBox);
		
		chkBox = new KDCheckBox();
		ICellEditor isAdaptercheckBox = new KDTDefaultCellEditor(chkBox);
		IColumn columnIsAdapterFunction= this.tblAdapterLog.getColumn("isAdapterFunction");
		columnIsAdapterFunction.setEditor(isAdaptercheckBox);
		
		chkBox = new KDCheckBox();
		ICellEditor isEndAdapterCheckBox = new KDTDefaultCellEditor(chkBox);
		IColumn columnIsEndAdapter= this.tblAdapterLog.getColumn("isEndAdapter");
		columnIsEndAdapter.setEditor(isEndAdapterCheckBox);
		
		KDBizPromptBox f7Box = new KDBizPromptBox();
		f7Box.setEditFormat("$number$");
		f7Box.setDisplayFormat("$name$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
		//f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		
		IColumn beforeSeller = this.tblAdapterLog.getColumn("beforeSeller");
		beforeSeller.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		beforeSeller.setEditor(f7Editor);
		
		IColumn afterSeller = this.tblAdapterLog.getColumn("afterSeller");
		afterSeller.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		afterSeller.setEditor(f7Editor);
		
		IColumn operationPerson = this.tblAdapterLog.getColumn("operationPerson");
		operationPerson.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		operationPerson.setEditor(f7Editor);
	}
	/**
	 * 初始化共享销售顾问列表
	 * */
	private void initShareSeller() {
		tblBaseSet(this.tblShareSeller);
		
//		IColumn columnShareSellerNumber = this.tblShareSeller.getColumn("shareSellerNumber");
//		columnShareSellerNumber.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
//		columnShareSellerNumber.setEditor(createTxtCellEditor(80, false));
		
		
		IColumn columnDescription = this.tblShareSeller.getColumn("description");
		columnDescription.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnDescription.setEditor(createTxtCellEditor(80, true));
		
		IColumn columnShareDate = this.tblShareSeller.getColumn("shareDate");
		columnShareDate.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnShareDate.setEditor(createTxtCellEditor(80, false));
		
		KDCheckBox chkBox = new KDCheckBox();
		ICellEditor checkBox = new KDTDefaultCellEditor(chkBox);
		IColumn columnIsAgainShare = this.tblShareSeller.getColumn("isAgainShare");
		columnIsAgainShare.setEditor(checkBox);
		
		chkBox = new KDCheckBox();
		ICellEditor checkBoxUpdate = new KDTDefaultCellEditor(chkBox);
		IColumn columnIsUpdate = this.tblShareSeller.getColumn("isUpdate");
		columnIsUpdate.setEditor(checkBoxUpdate);
		
//		KDBizPromptBox f7Box = new KDBizPromptBox();
//		f7Box.setEditFormat("$number$");
//		f7Box.setDisplayFormat("$name$");
//		f7Box.setCommitFormat("$number$");
//		f7Box.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
//		//f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");
//		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
//		
//		IColumn columnName = this.tblShareSeller.getColumn("shareSellerName");
//		columnName.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
//		columnName.setEditor(f7Editor);
	}
	
	private void findShareSeller()
	{
		if(this.editData.getId()!=null)
		{
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("head.id",this.editData.getId().toString()));
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("head.name"));
			selector.add(new SelectorItemInfo("seller.*"));
			selector.add(new SelectorItemInfo("marketingUnit.*"));
			selector.add(new SelectorItemInfo("orgUnit.*"));
			selector.add(new SelectorItemInfo("operationPerson.*"));
			view.setSelector(selector);
			ShareSellerCollection shareColl = null;
			try {
				shareColl = ShareSellerFactory.getRemoteInstance().getShareSellerCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				this.handleException(e);
			}
			this.tblShareSeller.removeRows();
			if(shareColl != null){
				for(int i=0;i<shareColl.size();i++) {
					ShareSellerInfo shareSellerInfo = shareColl.get(i);
					addShareSellerRow(shareSellerInfo);
				}
			}
		}
	}
	
	private void findSaleman()
	{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("salesman.*");

		sels.add("adapterLogList.*");
		sels.add("adapterLogList.beforeSeller.*");
		sels.add("adapterLogList.afterSeller.*");
		sels.add("adapterLogList.operationPerson.*");
		sels.add("shareSellerList.*");
		sels.add("shareSellerList.seller.*");
		sels.add("shareSellerList.marketingUnit.*");
		sels.add("shareSellerList.orgUnit.*");
		sels.add("shareSellerList.operationPerson.*");
		AdapterLogCollection adaColl = null;
		ShareSellerCollection shareSellColl = null;
		try {
			FDCCustomerInfo customerInfo = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(
					new ObjectUuidPK(this.editData.getId()), sels);
			UserInfo user = customerInfo.getSalesman();
			this.f7salesman.setValue(user);
			if(STATUS_ADDNEW.equals(this.getOprtState()))
			this.f7CustomerManager.setValue(user);
			adaColl = customerInfo.getAdapterLogList();
			shareSellColl = customerInfo.getShareSellerList();
		} catch (EASBizException e) {
			logger.error(e.getMessage());
			this.handleException(e);
		} catch (BOSException e) {
			logger.error(e.getMessage());
			this.handleException(e);
		}
		this.tblAdapterLog.removeRows();
		if(adaColl.size()>0)
		{
			Timestamp lastUpdateTime = adaColl.get(0).getLastUpdateTime();
			String afterSellerID  = null;
			for(int i=0;i<adaColl.size();i++)
			{
				AdapterLogInfo adaInfo = (AdapterLogInfo)adaColl.get(i);
				addAdapterLogRow(adaInfo);
				if(adaInfo.getLastUpdateTime().after(lastUpdateTime) ||adaInfo.getLastUpdateTime().equals(lastUpdateTime))
				{
					lastUpdateTime = adaInfo.getLastUpdateTime();
					afterSellerID = adaInfo.getAfterSeller().getId().toString();
				}
			}
			for(int i=0;i<tblShareSeller.getRowCount();i++)
			{
				IRow row = tblShareSeller.getRow(i);
				ShareSellerInfo shareSellerInfo = (ShareSellerInfo)row.getUserObject();
				ShareModelEnum shareModel = shareSellerInfo.getShareModel();
				if(ShareModelEnum.sharePerson.equals(shareModel))
				{
					if(afterSellerID.equals(shareSellerInfo.getSeller().getId().toString()))
					{
						try {
							this.tblShareSeller.removeRow(i);
							ShareSellerFactory.getRemoteInstance().delete(new ObjectUuidPK(shareSellerInfo.getId()));
						} catch (EASBizException e) {
							e.printStackTrace();
						} catch (BOSException e) {
							e.printStackTrace();
						}
					}
				}
				
			}
		}
	}
	
	/**
	 * 初始化联系人列表
	 * */
	private void initLinkmanTable() {
		btnAddLinkman = new KDWorkButton();
		this.actionAddLinkman.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_sortstandard"));
		btnAddLinkman = (KDWorkButton) this.containLinkman.add(this.actionAddLinkman);
		btnAddLinkman.setToolTipText("增加联系人");
		btnAddLinkman.setText("增加联系人");
		
		btnRemoveLinkman = new KDWorkButton();
		this.actionRemoveLinkman.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
		btnRemoveLinkman = (KDWorkButton) this.containLinkman.add(this.actionRemoveLinkman);
		btnRemoveLinkman.setToolTipText("删除联系人");
		btnRemoveLinkman.setText("删除联系人");
		
		KDWorkButton btnXQAddLine = new KDWorkButton();
		this.actionXQAddLine.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_sortstandard"));
		btnXQAddLine = (KDWorkButton) this.containXQ.add(this.actionXQAddLine);
		btnXQAddLine.setToolTipText("增加行");
		btnXQAddLine.setText("增加行");
		
		KDWorkButton btnXQRemoveLine = new KDWorkButton();
		this.actionXQRemoveLine.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
		btnXQRemoveLine = (KDWorkButton) this.containXQ.add(this.actionXQRemoveLine);
		btnXQRemoveLine.setToolTipText("删除行");
		btnXQRemoveLine.setText("删除行");
		
		KDBizPromptBox f7Box = new KDBizPromptBox();
		f7Box.setEditFormat("$number$");
		f7Box.setDisplayFormat("$name$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
		
		this.tblXQ.checkParsed();
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		IColumn columnNumber = this.tblXQ.getColumn("productType");
		columnNumber.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnNumber.setEditor(f7Editor);
		
		KDFormattedTextField txtWeight = new KDFormattedTextField();
		txtWeight.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWeight.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		
		txtWeight.setPrecision(2);
		KDTDefaultCellEditor weight = new KDTDefaultCellEditor(txtWeight);
		this.tblXQ.getColumn("area").setEditor(weight);
		this.tblXQ.getColumn("area").getStyleAttributes().setNumberFormat("#0.00");
		this.tblXQ.getColumn("area").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.tblXQ.getColumn("high").setEditor(weight);
		this.tblXQ.getColumn("high").getStyleAttributes().setNumberFormat("#0.00");
		this.tblXQ.getColumn("high").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.tblXQ.getColumn("fz").setEditor(weight);
		this.tblXQ.getColumn("fz").getStyleAttributes().setNumberFormat("#0.00");
		this.tblXQ.getColumn("fz").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.tblXQ.getColumn("ydl").setEditor(weight);
		this.tblXQ.getColumn("ydl").getStyleAttributes().setNumberFormat("#0.00");
		this.tblXQ.getColumn("ydl").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		tblBaseSet(tblLinkman);
		
		IColumn columnRelation = this.tblLinkman.getColumn("relation");
		columnRelation.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnRelation.setEditor(createTxtCellEditor(80, true));
		
		IColumn columnName = this.tblLinkman.getColumn("customerNumber");
		columnName.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnName.setEditor(createTxtCellEditor(80, false));
		
		KDComboBox box=new KDComboBox();
		box.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SexEnum").toArray());		
		box.setSelectedIndex(0);
		KDTDefaultCellEditor sexEditor =  new KDTDefaultCellEditor(box);
		
		
		IColumn columnSex = this.tblLinkman.getColumn("customerSex");
		columnSex.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		//columnSex.setEditor(createTxtCellEditor(80, true));
		columnSex.setEditor(sexEditor);
		
		IColumn columnPhone = this.tblLinkman.getColumn("phone");
		columnPhone.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnPhone.setEditor(createTxtCellEditor(80, true));
		
		IColumn columnDes = this.tblLinkman.getColumn("description");
		columnDes.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnDes.setEditor(createTxtCellEditor(80, true));
		
		f7Box = new KDBizPromptBox();
		f7Box.setEditFormat("$number$");
		f7Box.setDisplayFormat("$name$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");
		
		//只能选未被禁用的
		FilterInfo mainFilter = new FilterInfo();
		mainFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		/*
		 * 注释掉原来以职位隔离，应该是以当前登录用户来隔离
		 * by xiaoao_liu
		 */
				
				//对客户F7增加职位隔离
		//		Set personIds = FDCCustomerHelper.getChildPersonIdsOfCurrentUser();
		EntityViewInfo viewInfo = new EntityViewInfo();
		
		//判断当前用户是否有管控当前组织的权限
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		String parentNumberStr = "'"+orgUnit.getNumber()+"'"; //上级组织编码，包含自己
		String[] parentNumbers = orgUnit.getLongNumber().split("!");
		if(parentNumbers.length>1) {
			for(int i=0;i<(parentNumbers.length-1);i++) {
				parentNumberStr += ",'"+ parentNumbers[i] +"'";
			}
		}
		MarketUnitControlCollection muControl=null;
		try {
			MarketUnitControlCollection muControlColl = MarketUnitControlFactory.getRemoteInstance().getMarketUnitControlCollection(
					"select orgUnit.id,orgUnit.number,orgUnit.longNumber where controler.id ='"+userInfo.getId().toString()+"' and orgUnit.number in ("+parentNumberStr+") ");
			muControl=muControlColl;
		} catch (BOSException e2) {
			e2.printStackTrace();
		}
		Set saleManIds = new HashSet();
		Set marketingUnitIDs = new HashSet();
		if(muControl!=null && muControl.size()>0){
			if(this.getUIContext().get("MarketingNode")!=null){
				TreeNode treeNode = (TreeNode)this.getUIContext().get("MarketingNode");
				getAllSaleManIds(treeNode,saleManIds);
				getAllMarketingunitIds(treeNode,marketingUnitIDs);
			}
		}
		
//		MarketUnitControlCollection muControlColl = MarketUnitControlFactory.getLocalInstance().getMarketUnitControlCollection(
//				"select orgUnit.id,orgUnit.number,orgUnit.longNumber where controler.id ='"+userInfo.getId().toString()+"' and orgUnit.number in ("+parentNumberStr+") ");
//		
		Set retSet=null;
		MarketingUnitCollection muColl=null;
	
		Set userID=new HashSet();
		userID.add(userInfo.getId().toString());
		try {
			retSet=MarketingUnitFactory.getRemoteInstance().getPermitSaleManIdSet(userInfo);
			muColl=MarketingUnitFactory.getRemoteInstance().getMarketUnitCollByDutySaleMan(userInfo);
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
		Set marketingUnitID =new HashSet();
		Set saleOrgID=new HashSet();
		Set saleOrgIDs=new HashSet();
		saleOrgIDs.add(saleOrg.getId().toString());
	
		if(muColl!=null && muColl.size()<0){
			for(int i=0;i<muColl.size();i++){
				marketingUnitID.add(muColl.get(i).getId().toString());
			}
		}
		if(marketingUnitID!=null && marketingUnitID.size()>0){
			saleOrgID.add(saleOrg.getId().toString());
		}
		FilterInfo filter = new FilterInfo();
		FilterItemInfo item = new FilterItemInfo();
		
		if(muControl!=null && muControl.size()>0){//当前用户为管控人员的时候可以看到所有的
			filter.getFilterItems().add(new FilterItemInfo("salesman.id", saleManIds, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("shareSellerList.seller.id", saleManIds, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("shareSellerList.marketingUnit.id", marketingUnitIDs, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("shareSellerList.orgUnit.id", saleOrg.getId().toString(), CompareType.INCLUDE));
			filter.setMaskString("(#0 or #1 or #2 or #3)");
		}else{
			if(retSet == null){
			item = new FilterItemInfo("salesman.id", userInfo.getId().toString());
		    }else{
//			item = new FilterItemInfo("salesman.person.id", personIds, CompareType.INCLUDE);
			item = new FilterItemInfo("salesman.id", retSet, CompareType.INCLUDE);
		    }
		   filter.getFilterItems().add(item);
        
		   filter.getFilterItems().add(new FilterItemInfo("shareSellerList.seller.id", retSet, CompareType.INCLUDE));
		   if(marketingUnitID!=null && marketingUnitID.size()>0){
			  filter.getFilterItems().add(new FilterItemInfo("shareSellerList.marketingUnit.id", marketingUnitID, CompareType.INCLUDE));
			  filter.getFilterItems().add(new FilterItemInfo("shareSellerList.orgUnit.id", saleOrgID, CompareType.INCLUDE));
			  filter.setMaskString("(#0 or #1 or #2 or #3)");
		   }else{
			  filter.setMaskString("(#0 or #1)");
		   }
		}


		try {
			mainFilter.mergeFilter(filter, "and");
		} catch (BOSException e) {
			logger.error(e.getMessage());
			this.handleException(e);
		}
		
		viewInfo.setFilter(mainFilter);
		f7Box.setEntityViewInfo(viewInfo);
		
		f7Editor = new KDTDefaultCellEditor(f7Box);
		
		columnNumber = this.tblLinkman.getColumn("customerName");
		columnNumber.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnNumber.setEditor(f7Editor);

//		this.tblLinkman.getColumn("jiange").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));
	}
	
	
	/**
	 * 对界面上的可否点击，可否编辑等进行控制
	 * */
	private void initControl() {
		this.dpBookedDate.setEnabled(false);
		this.txtBooker.setEditable(false);
		
		//非增加下不能修改姓名和电话
		boolean isStatusAddNew = this.oprtState.equals(STATUS_ADDNEW);
		this.txtName.setEditable(isStatusAddNew);
		this.txtPhone.setEditable(isStatusAddNew);
		
		boolean isStatusView = this.oprtState.equals(STATUS_VIEW);
		this.tblLinkman.setEditable(!isStatusView);
		this.tblTrackRecord.setEditable(!isStatusView);
		
		//修改或查看状态可以进行增加跟进记录的操作
		boolean isViewOrEdit = isStatusView || STATUS_EDIT.equals(this.oprtState);
		
		this.actionAddLine.setEnabled(isViewOrEdit);
		this.actionAddCommerceChance.setEnabled(isViewOrEdit);
//		this.actionCustomerAdapter.setEnabled(isViewOrEdit);
//		this.actionCustomerShare.setEnabled(isViewOrEdit);
//		this.actionCustomerCancelShare.setEnabled(isViewOrEdit);
		
		boolean isStatusEdit = this.oprtState.equals(STATUS_EDIT);
		this.txtNumber.setEditable(!isStatusEdit);
		
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
	}
	//基类方法报错，暂重写处理
	protected void checkIsOUSealUp() throws Exception{
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		actionInsider.setEnabled(false);
		
	}
	
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String fdcCustomerID = this.editData.getId().toString();
		if(!hasUpdatePermission(fdcCustomerID))
		{
			MsgBox.showInfo("对该共享客户没有修改权!");
			this.abort();
		}
		if(this.tblShareSeller.getRowCount()>0)
		{
			for(int i=0;i<tblShareSeller.getRowCount();i++)
			{
				IRow row = tblShareSeller.getRow(i);
				ShareModelEnum shareModel = (ShareModelEnum)row.getCell("shareModel").getValue();
				if(ShareModelEnum.shareMarket.equals(shareModel) || ShareModelEnum.shareOrgUnit.equals(shareModel))
				{
					row.getCell("isAgainShare").getStyleAttributes().setLocked(true);
					row.getCell("isUpdate").getStyleAttributes().setLocked(false);
				}else
				{
					row.getCell("isAgainShare").getStyleAttributes().setLocked(false);
					row.getCell("isUpdate").getStyleAttributes().setLocked(false);
				}
			}
		}
		//查看状态下再点修改，对界面操作进行控制
		
		boolean isStatusViewBefore = this.oprtState.equals(STATUS_VIEW);
		if(CustomerShareUI.hasSharePermission(fdcCustomerID))
		{
			this.actionCustomerShare.setEnabled(true);
			this.actionCustomerCancelShare.setEnabled(true);
		}
		
		if(CustomerAdapterUI.hasAdpaterPermission(fdcCustomerID))	{
			this.actionCustomerAdapter.setEnabled(true);
		}
		super.actionEdit_actionPerformed(e);
		if(isStatusViewBefore)
			initControl();
	}
	
	public void loadFields() {
		initControl();//对增加，察看，修改操作时界面操作的相应控制,但查看状态下再点修改不会调用该函数，需要特殊处理
		
logger.info("CustomerEditUI:loadFields--" + this.getOprtState() + new Timestamp(new Date().getTime()));		
		
		FDCCustomerInfo info = this.editData;
		loadTblLinkmanFields(info);
		if(STATUS_ADDNEW.equals(this.getOprtState()))
		{
			loadTblShareSeller(info);
			loadTblAdapterLog(info);
		}else
		{
			findShareSeller();
			findSaleman();
		}	
logger.info("CustomerEditUI:loadFields--" + "ShareSeller&&findSaleman" + new Timestamp(new Date().getTime()));		
		SHEHelper.setNumberEnabled(this.editData,this.getOprtState(),this.txtNumber);
		
		super.loadFields();
		
		if(!this.oprtState.equals(OprtState.ADDNEW)) {
			//加载客户跟踪记录
			initTrackRecordTable();
logger.info("CustomerEditUI:loadFields--" + "initTrackRecordTable()" + new Timestamp(new Date().getTime()));				
			//加载售楼认购记录
			initSalePurchaseRecord();
logger.info("CustomerEditUI:loadFields--" + "initSalePurchaseRecord()" + new Timestamp(new Date().getTime()));			
			//加载租赁认租记录
			initTenancyBillRecord();
logger.info("CustomerEditUI:loadFields--" + "initTenancyBillRecord()" + new Timestamp(new Date().getTime()));			
			//加载会员信息
			initInside();
			//加载售楼商机和租赁商机
			//initInside();
			//eric_wang 2010.08.05
			this.tblSaleCommerce.removeRows();
			this.tblTenancyCommerce.removeRows();
			initCommerceChanceRecord("Sale");
			initCommerceChanceRecord("Tenancy");
logger.info("CustomerEditUI:loadFields--" + "initCommerceChanceRecord()" + new Timestamp(new Date().getTime()));			
		}
		
		if(editData.getId()!=null){
			int size = editData.getWorkArea().size();
			CoreBaseInfo[] workareas = new CoreBaseInfo[size];
			for(int i=0;i<size;i++){
				CustomerWorkAreaEntryInfo CWAEinfo = editData.getWorkArea().get(i);
				workareas[i] = CWAEinfo.getWorkArea();
			}
			f7WorkArea.setValue(workareas);
			
			int size1 = editData.getBusinessScope().size();
			CoreBaseInfo[] businessScope = new CoreBaseInfo[size1];
			for(int i=0;i<size1;i++){
				CustomerBusinessScopeEntryInfo CBSEinfo = editData.getBusinessScope().get(i);
				businessScope[i] = CBSEinfo.getBusinessScope();
			}
			f7BusinessScope.setValue(businessScope);
		}
		this.storeFields();
		
		initOldData(this.editData);
	}
	
	
	
	/**
	 * 加载数据到联系人列表界面
	 * */
	private void loadTblLinkmanFields(FDCCustomerInfo info) {
		//此时分录里的客户为空只有id,所有需要重新查询一次
		//LinkmanEntryCollection linkmanList = info.getLinkmanList();
		
		if(info==null || info.getId()==null) return;
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("head.id",info.getId().toString());
		view.getSelector().add("*");
		view.getSelector().add("fdcCustomer.*");
		LinkmanEntryCollection linkmanList = null;
		try {
			linkmanList = LinkmanEntryFactory.getRemoteInstance().getLinkmanEntryCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}				
		tblLinkman.removeRows();		
		
		for (int i = 0; i < linkmanList.size(); i++) {
			LinkmanEntryInfo entry = linkmanList.get(i);
			addLinkmanEntryRow(entry);
		}
	}
	
	private void addLinkmanEntryRow(LinkmanEntryInfo entry) {
		IRow row = tblLinkman.addRow();
		FDCCustomerInfo linkman = entry.getFdcCustomer();
		
		row.setUserObject(entry);

		row.getCell("customerName").setValue(linkman);
		row.getCell("name").setValue(entry.getPersonName());
		row.getCell("relation").setValue(entry.getRelation());
		row.getCell("customerNumber").setValue(
				linkman != null ? linkman.getNumber() : null);
		row.getCell("customerSex").setValue(entry.getSex());
		row.getCell("phone").setValue(entry.getPhone());
		row.getCell("description").setValue(entry.getDescription());
		
		row.getCell("mail").setValue(entry.getMail());
		row.getCell("address").setValue(entry.getAddress());
		row.getCell("ah").setValue(entry.getAh());
		row.getCell("job").setValue(entry.getJob());

	}
	
	/**
	 * 加载数据到联系人列表界面
	 * */
	private void loadTblShareSeller(FDCCustomerInfo info) {
		ShareSellerCollection shareSellerList = info.getShareSellerList();
		tblShareSeller.removeRows();
		for (int i = 0; i < shareSellerList.size(); i++) {
			ShareSellerInfo shareSellerInfo = shareSellerList.get(i);
			addShareSellerRow(shareSellerInfo);
		}
	}
	
	private void loadTblAdapterLog(FDCCustomerInfo info) {
		AdapterLogCollection adapterCpll = info.getAdapterLogList();
		tblAdapterLog.removeRows();
		for (int i = 0; i < adapterCpll.size(); i++) {
			AdapterLogInfo adaLogInfo = adapterCpll.get(i);
			addAdapterLogRow(adaLogInfo);
		}
	}
	
	
	/**
	 * 加载数据到转接历史记录列表界面
	 * */
	private void addAdapterLogRow(AdapterLogInfo info) {
		IRow row = this.tblAdapterLog.addRow();
		row.setUserObject(info);
		UserInfo beforeSeller = info.getBeforeSeller();
		UserInfo afterSeller = info.getAfterSeller();
		UserInfo operationPerson = info.getOperationPerson();
		
		row.getCell("beforeSeller").setValue(beforeSeller.getName());
		row.getCell("beforeSeller").setUserObject(beforeSeller);
		row.getCell("afterSeller").setValue(afterSeller.getName());
		row.getCell("afterSeller").setUserObject(afterSeller);
		row.getCell("operationPerson").setValue(operationPerson.getName());
		row.getCell("operationPerson").setUserObject(operationPerson);
		row.getCell("operationPerson").getStyleAttributes().setLocked(true);
		row.getCell("afterSeller").getStyleAttributes().setLocked(true);
		row.getCell("beforeSeller").getStyleAttributes().setLocked(true);
		row.getCell("adapterDate").setValue(info.getAdapterDate());
		row.getCell("adapterDate").getStyleAttributes().setLocked(true);
		row.getCell("isAdapterInter").setValue(new Boolean(info.isIsAdapterInter()));
		row.getCell("isAdapterFunction").setValue(new Boolean(info.isIsAdapterFunction()));
		row.getCell("isEndAdapter").setValue(new Boolean(info.isIsEndAdapter()));
//		if(STATUS_EDIT.equals(this.getOprtState()) || STATUS_ADDNEW.equals(this.getOprtState()))
//		{
//			row.getCell("isAdapterInter").getStyleAttributes().setLocked(false);
//			row.getCell("isAdapterFunction").getStyleAttributes().setLocked(false);
//			row.getCell("isEndAdapter").getStyleAttributes().setLocked(false);
//		}else
//		{
			row.getCell("isAdapterInter").getStyleAttributes().setLocked(true);
			row.getCell("isAdapterFunction").getStyleAttributes().setLocked(true);
			row.getCell("isEndAdapter").getStyleAttributes().setLocked(true);
//		}
	}
	/**
	 * 加载数据到共享销售顾问列表界面
	 * */
	private void addShareSellerRow(ShareSellerInfo info) {
		IRow row = tblShareSeller.addRow();
		row.setUserObject(info);
		ShareModelEnum shareModel = (ShareModelEnum)info.getShareModel();
		row.getCell("shareModel").setValue(shareModel);
		row.getCell("shareModel").getStyleAttributes().setLocked(true);
		if(ShareModelEnum.sharePerson.equals(shareModel))
		{		
			UserInfo userInfo = info.getSeller();	
			if(userInfo != null){
				row.getCell("shareObject").setValue(userInfo.getName());
				row.getCell("shareObject").setUserObject(userInfo);
				row.getCell("shareObject").getStyleAttributes().setLocked(true);
				if(info!=null && info.getOperationPerson()!=null){
					row.getCell("operationPerson").setValue(info.getOperationPerson().getName());
				}
				row.getCell("operationPerson").setUserObject(info.getOperationPerson());
				row.getCell("operationPerson").getStyleAttributes().setLocked(true);
				row.getCell("isAgainShare").setValue(new Boolean(info.isIsAgainShare()));
				
				row.getCell("shareDate").setValue(info.getShareDate());
				row.getCell("isUpdate").setValue(new Boolean(info.isIsUpdate()));
				
				if(STATUS_EDIT.equals(this.getOprtState()) || STATUS_ADDNEW.equals(this.getOprtState()))
				{
					row.getCell("isAgainShare").getStyleAttributes().setLocked(false);
					row.getCell("isUpdate").getStyleAttributes().setLocked(false);
				}else
				{
					row.getCell("isAgainShare").getStyleAttributes().setLocked(true);
					row.getCell("isUpdate").getStyleAttributes().setLocked(true);
				}
				row.getCell("description").setValue(info.getDescription());
			}else
			{
				row.getCell("isAgainShare").setValue(new Boolean(false));
				row.getCell("isUpdate").setValue(new Boolean(false));
				if(STATUS_EDIT.equals(this.getOprtState()) || STATUS_ADDNEW.equals(this.getOprtState()))
				{
					row.getCell("isAgainShare").getStyleAttributes().setLocked(false);
					row.getCell("isUpdate").getStyleAttributes().setLocked(false);
				}else
				{
					row.getCell("isAgainShare").getStyleAttributes().setLocked(true);
					row.getCell("isUpdate").getStyleAttributes().setLocked(true);
				}
			}
		}else if(ShareModelEnum.shareMarket.equals(shareModel))
		{
			MarketingUnitInfo marketingInfo = (MarketingUnitInfo)info.getMarketingUnit();
			if(marketingInfo!=null)
			{
				row.getCell("shareObject").setValue(marketingInfo.getName());
				row.getCell("shareObject").setUserObject(marketingInfo);
				row.getCell("shareObject").getStyleAttributes().setLocked(true);
				
				row.getCell("operationPerson").setValue(info.getOperationPerson().getName());
				row.getCell("operationPerson").setUserObject(info.getOperationPerson());
				row.getCell("operationPerson").getStyleAttributes().setLocked(true);
				
                row.getCell("isAgainShare").setValue(new Boolean(info.isIsAgainShare()));
				
				row.getCell("shareDate").setValue(info.getShareDate());
				row.getCell("isUpdate").setValue(new Boolean(info.isIsUpdate()));
				row.getCell("isAgainShare").getStyleAttributes().setLocked(true);
				row.getCell("shareDate").getStyleAttributes().setLocked(true);
				//row.getCell("isUpdate").getStyleAttributes().setLocked(true);
				if(STATUS_EDIT.equals(this.getOprtState()) || STATUS_ADDNEW.equals(this.getOprtState()))
				{
					row.getCell("isUpdate").getStyleAttributes().setLocked(false);
				}else
				{
					row.getCell("isUpdate").getStyleAttributes().setLocked(true);
				}
				row.getCell("description").setValue(info.getDescription());
			}
		}else if(ShareModelEnum.shareOrgUnit.equals(shareModel))
		{
			FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo)info.getOrgUnit();
			if(orgUnitInfo!=null)
			{
				row.getCell("shareObject").setValue(orgUnitInfo.getName());
				row.getCell("shareObject").setUserObject(orgUnitInfo);
				row.getCell("shareObject").getStyleAttributes().setLocked(true);
				
				row.getCell("operationPerson").setValue(info.getOperationPerson().getName());
				row.getCell("operationPerson").setUserObject(info.getOperationPerson());
				row.getCell("operationPerson").getStyleAttributes().setLocked(true);
				
                row.getCell("isAgainShare").setValue(new Boolean(info.isIsAgainShare()));
				
				row.getCell("shareDate").setValue(info.getShareDate());
				row.getCell("isUpdate").setValue(new Boolean(info.isIsUpdate()));
				row.getCell("isAgainShare").getStyleAttributes().setLocked(true);
				row.getCell("shareDate").getStyleAttributes().setLocked(true);
				//row.getCell("isUpdate").getStyleAttributes().setLocked(true);
				if(STATUS_EDIT.equals(this.getOprtState()) || STATUS_ADDNEW.equals(this.getOprtState()))
				{
					row.getCell("isUpdate").getStyleAttributes().setLocked(false);
				}else
				{
					row.getCell("isUpdate").getStyleAttributes().setLocked(true);
				}
				row.getCell("description").setValue(info.getDescription());
			}
		}
		
	}
	
	protected IObjectValue createNewData() {
		FDCCustomerInfo value = new FDCCustomerInfo();
		value.setIsForTen(true);
		value.setIsEnabled(true);
		value.setIsImportantTrack(false);
		value.setCustomerType(CustomerTypeEnum.EnterpriceCustomer);
		value.setTrackPhase(TrackPhaseEnum.PotentialCustomer);
//		value.setSex(SexEnum.Mankind);
		//value.setCertificateName(CertifacateNameEnum.IDCard);
		value.setSalesman(userInfo);
		try{
			value.setCreateTime(SHEHelper.getServerDate());
		}catch(Exception e){
			e.printStackTrace();
		}
		value.setCreator(userInfo);
		value.setProject((SellProjectInfo) this.getUIContext().get("sellProject"));
		
		value.setEnterpriceProperty(EnterprisePropertyEnum.PersonalAsset);
		
		FDCCustomerInfo lsCus = getLastAddedCustomer();
		if(lsCus != null){
			value.setProject(lsCus.getProject());
			value.setBookedPlace(lsCus.getBookedPlace());
			value.setReceptionType(lsCus.getReceptionType());
		}
		value.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		/*
		 * 支持默认值功能,建立的用户如果有售楼业务、租赁和物业职能,就默认选中售楼业务、租赁和物业职能复选框
		 * add by wenyaowei 20090616
		 * ------------------start
		 */
//		boolean isSellFunction = false;
//		boolean isTenancyFunction = false;
//		boolean isWuYeFunction = false;
//		MarketingUnitMemberCollection memCol = null;
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		view.setFilter(filter);
//		filter.getFilterItems().add(new FilterItemInfo("member.id", SysContext.getSysContext().getCurrentUserInfo().getId().toString()));
//
//		try {
//			memCol = MarketingUnitMemberFactory.getRemoteInstance().getMarketingUnitMemberCollection(view);
//		} catch (BOSException e) {
//			handleException(e);
//		}
//		if (!memCol.isEmpty()) {
//			for (int i = 0; i < memCol.size(); i++) {
//				MarketingUnitMemberInfo info = memCol.get(i);
//				if (info.isIsSellFunction()) {
//					isSellFunction = true;
//				}
//				if (info.isIsTenancyFunction()) {
//					isTenancyFunction = true;
//				}
//				if (info.isIsWuYeFunction()) {
//					isWuYeFunction = true;
//				}
//			}
//		}		
//		
//		value.setIsForSHE(isSellFunction); // 设置售楼职能
//		value.setIsForTen(isTenancyFunction);// 设置租赁职能
//		value.setIsForPPM(isWuYeFunction); // 设置物业职能
		/*
		 * ------------------end
		 */
		
		
	//点新增按钮时，界面上往往遗留有之前用户里的跟进记录信息和其它，在这里清除掉
		this.tblAdapterLog.removeRows(false);
		this.tblInsider.removeRows(false);
		this.tblLinkman.removeRows(false);
		this.tblSaleCommerce.removeRows(false);
		this.tblSaleRecord.removeRows(false);
		this.tblShareSeller.removeRows(false);
		this.tblTenancyCommerce.removeRows(false);
		this.tblTenancyCommerce.removeRows(false);
		this.tblTrackRecord.removeRows(false);
		
		
		if(this.getUIContext().get("isInitCertifaicate")==null){
			CertificateInfo certificateInfo = null;
			try {
				certificateInfo = CertificateFactory.getRemoteInstance().getCertificateInfo("select id,name,number where number='"+CERTIFICATE_ID_CARD_NAME+"'");
			} catch (EASBizException e) {
				logger.error(e.getMessage());
			} catch (BOSException e) {
				logger.error(e.getMessage());
			}
			
			if(certificateInfo!=null){
				value.setCertificateName(certificateInfo);
			}

		}
		return value;
	}
	
	private static FDCCustomerInfo lastAddedCustomerCache;//用于缓存客户上次添加的客户
	private FDCCustomerInfo getLastAddedCustomer(){
		try {
			initLastAddedCustomerCache(false);
		} catch (BOSException e) {
			logger.warn("初始化最后录入客户出错："+e.getMessage());
			logger.debug("", e);
		}
		return lastAddedCustomerCache;
	}
	
	//
	private void initLastAddedCustomerCache(boolean reload) throws BOSException {
		if(lastAddedCustomerCache != null  &&  !reload){
			return;
		}
		FilterInfo cusFilter = new FilterInfo();
		cusFilter.getFilterItems().add(new FilterItemInfo("booker", userInfo.getName()));
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("*"));
		sel.add(new SelectorItemInfo("receptionType.*"));
		sel.add(new SelectorItemInfo("project.*"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(cusFilter);
		
		FDCCustomerCollection cs = FDCCustomerFactory.getRemoteInstance().getFDCCustomerCollection(view);
		lastAddedCustomerCache = getLastRecord(cs);//取最新的一条	
	}
	
	private FDCCustomerInfo getLastRecord(FDCCustomerCollection cs) {
		FDCCustomerInfo cus = null;
		for(int i=0; i<cs.size(); i++){
			FDCCustomerInfo tmp = cs.get(i);
			if(cus == null){
				cus = tmp;
				continue;
			}
			if(cus.getCreateTime() != null  &&  cus.getCreateTime().before(tmp.getCreateTime())){
				cus = tmp;
			}
		}
		return cus;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return FDCCustomerFactory.getRemoteInstance();
	}	
	
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
		if(this.editData!=null && this.editData.getId()!=null) {
			UIContext uiContext = new UIContext(this); 		
			uiContext.put("FdcCustomer", this.editData);
		
			SellProjectInfo proInfo = (SellProjectInfo)this.f7Project.getValue();
			if(proInfo!=null)
			uiContext.put("SellProject", proInfo);
			
			if(this.chkIsForSHE.isSelected())
				uiContext.put("MoneySysTypeEnum", MoneySysTypeEnum.SalehouseSys);
			else if(this.chkIsForTen.isSelected())
				uiContext.put("MoneySysTypeEnum", MoneySysTypeEnum.TenancySys);
			
			try {
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TrackRecordEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
				uiWindow.show();
			} catch (UIException ee) {
				logger.error(ee.getMessage());
				this.handleException(ee);
			}
			initTrackRecordTable();   //刷新
		}
	}
	
	public void actionAddLinkman_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLinkman_actionPerformed(e);
		initLinkmanTableCell();
		LinkmanEntryInfo entry = new LinkmanEntryInfo();
		this.addLinkmanEntryRow(entry);
	}
	/**
	 * @description 每次新增联系人分录的时候需要把联系人关系转成非毕录项，因为可能填入新联系人
	 * @author tim_gao
	 * @date 2010-11-18
	 * 
	 */
	public void initLinkmanTableCell()
	{
		this.tblLinkman.getColumn("relation").setRequired(false);
	}
	
	public void actionRemoveLinkman_actionPerformed(ActionEvent e)
			throws Exception {
		
		if (tblLinkman != null) {
			removeLine(tblLinkman);
			appendFootRow(tblLinkman);
			// 实现删除后的焦点策略 2007-10-09 haiti_yang
			if (tblLinkman.getRowCount() == 0) {
				FocusTraversalPolicy policy = null;
				Container container = null;
				Component initComponent = null;
				if (this.getFocusTraversalPolicy() != null
						&& this.getFocusTraversalPolicy() instanceof UIFocusTraversalPolicy) {
					policy = this.getFocusTraversalPolicy();
					container = this;
					Component[] traverComponent = ((UIFocusTraversalPolicy) policy)
							.getComponents();
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
						Component component = policy.getComponentBefore(
								container, initComponent);
						while ((component instanceof IKDTextComponent) == false
								|| component.isEnabled() == false) {
							component = policy.getComponentBefore(container,
									component);
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
							Component component = policy.getComponentBefore(
									container, tblLinkman);
							while ((component instanceof IKDTextComponent) == false
									|| component.isEnabled() == false) {
								component = policy.getComponentBefore(
										container, component);
							}
							component.requestFocusInWindow();
						} catch (Exception ex) {
						}
					}
				}
			}
		}
		//super.actionRemoveLinkman_actionPerformed(e);
	}

	/**
	 * 在指定表格中删除当前选中行 增加隔行删除功能 2007-03-12
	 * 
	 * @param table
	 */
	protected void removeLine(KDTable table) {
		if (table == null) {
			return;
		}

		if ((table.getSelectManager().size() == 0))
		// || isTableColumnSelected(table))
		{
			MsgBox.showInfo(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_NoneEntry"));

			return;
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
					MsgBox.showInfo(this, EASResource
							.getString(FrameWorkClientUtils.strResource
									+ "Msg_NoneEntry"));
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
				IObjectValue detailData = (IObjectValue) table.getRow(rowIndex)
						.getUserObject();
				table.removeRow(rowIndex);
				IObjectCollection collection = (IObjectCollection) table
						.getUserObject();

				if (collection == null) {
					logger.error("collection not be binded to table");
				} else {
					// Modify By Jacky Zhang
					if (detailData != null) {
						int index = getCollectionIndex(collection, detailData);
						// 避免有合计行的分录处理。
						if (index >= 0 && collection.size() > index) {
							collection.removeObject(index);
						}
					}
				}
			///	afterRemoveLine(table, detailData);
			}

			// 如果现在有记录定位到第一行
			if (table.getRow(0) != null)
				table.getSelectManager().select(0, 0);
		}
	}
	
	// 因为目前ObjectValue比较是按值比较，但集合中使用，如果分录值相同，
	// 都会删除找到的第一个，会出错。自行实现按指针比较。2007-2-5
	private int getCollectionIndex(IObjectCollection collection,
			IObjectValue obj) {
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
	//增加联系人和跟进记录以及意向房间的Store
	public void storeFields() {
		FDCCustomerInfo type = this.editData;
		storeLinkmanList(type);		
		storeShareSellerList(type);
		storeAdapterLog(type);
		super.storeFields();
		storeWorkArea(type);
		storeBusinessScope(type);
		//缓存上次保存的客户所属项目
		if(this.oprtState.equals(STATUS_ADDNEW)){
			lastAddedCustomerCache = this.editData;
		}
	}
	/*
	 * 工作区域f7多选功能保存
	 */
	private void storeWorkArea(FDCCustomerInfo type) {
		type.getWorkArea().clear();
		if(f7WorkArea.getText()!=null&&(!f7WorkArea.getText().equals(""))){
			Object v = f7WorkArea.getValue();
	 		if(v instanceof Object[]){
				Object object[] = (Object[])v;
				for(int i=0;i<object.length;i++){
					WorkAreaInfo workarea = (WorkAreaInfo)object[i];
					CustomerWorkAreaEntryInfo customerWorkAreaEntryInfo  = new CustomerWorkAreaEntryInfo();				
					customerWorkAreaEntryInfo.setId(BOSUuid.create(customerWorkAreaEntryInfo.getBOSType()));
					customerWorkAreaEntryInfo.setWorkArea(workarea); 
					customerWorkAreaEntryInfo.setParent(editData);
					type.getWorkArea().add(customerWorkAreaEntryInfo);
				}
			}else if(v instanceof WorkAreaInfo){
				WorkAreaInfo workarea = (WorkAreaInfo)v;
				CustomerWorkAreaEntryInfo customerWorkAreaEntryInfo  = new CustomerWorkAreaEntryInfo();	
				customerWorkAreaEntryInfo.setId(BOSUuid.create(customerWorkAreaEntryInfo.getBOSType()));
				customerWorkAreaEntryInfo.setWorkArea(workarea);
				customerWorkAreaEntryInfo.setParent(editData);
				type.getWorkArea().add(customerWorkAreaEntryInfo);
			}
		}
	}
	
	
	/*
	 * 业务范围f7多选功能保存
	 */
	private void storeBusinessScope(FDCCustomerInfo type) {
		type.getBusinessScope().clear();
		if(f7BusinessScope.getText()!=null&&(!f7BusinessScope.getText().equals(""))){
			Object v = f7BusinessScope.getValue();
	 		if(v instanceof Object[]){
				Object object[] = (Object[])v;
				for(int i=0;i<object.length;i++){
					BusinessScopeInfo businessScopeInfo = (BusinessScopeInfo)object[i];
					CustomerBusinessScopeEntryInfo customerBusinessScopeEntryInfo  = new CustomerBusinessScopeEntryInfo();				
					customerBusinessScopeEntryInfo.setId(BOSUuid.create(customerBusinessScopeEntryInfo.getBOSType()));
					customerBusinessScopeEntryInfo.setBusinessScope(businessScopeInfo); 
					customerBusinessScopeEntryInfo.setParent(editData);
					type.getBusinessScope().add(customerBusinessScopeEntryInfo);
				}
			}else if(v instanceof WorkAreaInfo){
				BusinessScopeInfo businessScopeInfo = (BusinessScopeInfo)v;
				CustomerBusinessScopeEntryInfo customerBusinessScopeEntryInfo  = new CustomerBusinessScopeEntryInfo();				
				customerBusinessScopeEntryInfo.setId(BOSUuid.create(customerBusinessScopeEntryInfo.getBOSType()));
				customerBusinessScopeEntryInfo.setBusinessScope(businessScopeInfo); 
				customerBusinessScopeEntryInfo.setParent(editData);
				type.getBusinessScope().add(customerBusinessScopeEntryInfo);
			}
		}
	}
	/**
	 * 根据跟进日期判定最新一条跟进记录，由于日期只精确到天，可能取得记录不准确
	 * */
	/*  跟踪记录为客户分录里时 才用到  ,现在跟踪记录已经提出来了 
	 private TrackRecordInfo getLastTrackDate(TrackRecordCollection collection) {
	 TrackRecordInfo lastTrack = null;
	 for(int i=0; i<collection.size(); i++){
	 TrackRecordInfo tmp = collection.get(i);
	 if(lastTrack == null){
	 lastTrack = tmp;
	 continue;
	 }
	 if(tmp.getEventDate().after(lastTrack.getEventDate())){
	 lastTrack = tmp;
	 }
	 }
	 return lastTrack;
	 }
	 
	 
	 private void storeTrackRecordList(FDCCustomerInfo type) {
	 TrackRecordCollection trackRecordList = type.getTrackRecordList();
	 trackRecordList.clear();
	 for(int i=0; i<tblTrackRecord.getRowCount(); i++){
	 IRow row = tblTrackRecord.getRow(i);
	 TrackRecordInfo entry = (TrackRecordInfo) row.getUserObject();
	 Object obj = row.getCell("eventType").getValue();
	 if(obj == null){
	 logger.info("没有选择事件类型，不进行保存！");
	 continue;
	 }
	 entry.setEventDate((Date) row.getCell("eventDate").getValue());
	 entry.setEventType((EventTypeInfo) obj);
	 entry.setDescription((String) row.getCell("description").getValue());			
	 trackRecordList.add(entry);
	 }
	 }
	 */
	
	
	private void storeLinkmanList(FDCCustomerInfo type) {
		LinkmanEntryCollection linkmanList = type.getLinkmanList();
		linkmanList.clear();
		for (int i = 0; i < tblLinkman.getRowCount(); i++) {
			IRow row = tblLinkman.getRow(i);
			LinkmanEntryInfo entry = (LinkmanEntryInfo) row.getUserObject();
			if(entry == null){
				entry  = new LinkmanEntryInfo();
				entry.setId(BOSUuid.create(entry.getBOSType()));
			}
			Object obj = row.getCell("customerName").getValue();
			/*if(row.getCell("phone").getValue()==null||row.getCell("phone").getValue().toString().equals("")){
				MsgBox.showError("联系人分录中联系电话不能为空！");
				abort();
			}*/
//			if(obj == null){
//				logger.info("没有选择联系人，不进行保存！");
//				continue;
//			}
			entry.setFdcCustomer((FDCCustomerInfo) obj);
			entry.setRelation((String) row.getCell("relation").getValue());
			if(row.getCell("customerSex").getValue()!=null){
				entry.setSex(row.getCell("customerSex").getValue().toString());
			}else{
				entry.setSex("");
			}
			entry.setPhone((String) row.getCell("phone").getValue());
			entry.setDescription((String) row.getCell("description").getValue());
			entry.setPersonName((String)row.getCell("name").getValue());
			entry.setMail((String)row.getCell("mail").getValue());
			entry.setAddress((String)row.getCell("address").getValue());
			entry.setAh((String)row.getCell("ah").getValue());
			entry.setJob((String)row.getCell("job").getValue());
			linkmanList.add(entry);
		}
	}
	
	private void storeAdapterLog(FDCCustomerInfo type)
	{
		AdapterLogCollection adapterLogList = type.getAdapterLogList();
		adapterLogList.clear();
		if(tblAdapterLog.getRowCount()>0)
		{
			for(int i=0;i<tblAdapterLog.getRowCount();i++)
			{
				IRow row = tblAdapterLog.getRow(i);
				AdapterLogInfo adapterLogInfo = (AdapterLogInfo) row.getUserObject();
				adapterLogInfo.setAfterSeller((UserInfo)row.getCell("afterSeller").getUserObject());
				adapterLogInfo.setBeforeSeller((UserInfo)row.getCell("beforeSeller").getUserObject());
				adapterLogInfo.setOperationPerson((UserInfo)row.getCell("operationPerson").getUserObject());
				adapterLogInfo.setAdapterDate((Date)row.getCell("adapterDate").getValue());
				if(row.getCell("isAdapterInter").getValue()==null)
				{
					adapterLogInfo.setIsAdapterInter(false);
				}else
				{
					adapterLogInfo.setIsAdapterInter(((Boolean)row.getCell("isAdapterInter").getValue()).booleanValue());
				}
				if(row.getCell("isAdapterFunction").getValue()==null)
				{
					adapterLogInfo.setIsAdapterFunction(false);
				}else
				{
					adapterLogInfo.setIsAdapterFunction(((Boolean)row.getCell("isAdapterFunction").getValue()).booleanValue());
				}
				if(row.getCell("isEndAdapter").getValue()==null)
				{
					adapterLogInfo.setIsEndAdapter(false);
				}else
				{
					adapterLogInfo.setIsEndAdapter(((Boolean)row.getCell("isEndAdapter").getValue()).booleanValue());
				}
				adapterLogList.add(adapterLogInfo);
			}
			
		}
		
	}
	
	private void storeShareSellerList(FDCCustomerInfo type) {
		ShareSellerCollection shareSellerList = type.getShareSellerList();
		shareSellerList.clear();
		if(tblShareSeller.getRowCount()>0){
			for (int i = 0; i < tblShareSeller.getRowCount(); i++) {
				IRow row = tblShareSeller.getRow(i);
				ShareSellerInfo shareSellerInfo = (ShareSellerInfo) row.getUserObject();
				ShareModelEnum shareModel = shareSellerInfo.getShareModel();
				if(ShareModelEnum.sharePerson.equals(shareModel))
				{
					Object obj = row.getCell("shareObject").getUserObject();
					if(obj == null){
						logger.info("没有选择销售顾问，不进行保存！");
						continue;
					}
					shareSellerInfo.setSeller((UserInfo)obj);
				}else if(ShareModelEnum.shareMarket.equals(shareModel))
				{
					Object obj = row.getCell("shareObject").getUserObject();
					shareSellerInfo.setMarketingUnit((MarketingUnitInfo)obj);
				}else if(ShareModelEnum.shareOrgUnit.equals(shareModel))
				{
					Object obj = row.getCell("shareObject").getUserObject();
					shareSellerInfo.setOrgUnit((FullOrgUnitInfo)obj);
				}
				
				if(row.getCell("isAgainShare").getValue()==null)
				{
					shareSellerInfo.setIsAgainShare(false);
				}else
				{
					shareSellerInfo.setIsAgainShare(((Boolean)row.getCell("isAgainShare").getValue()).booleanValue());
				}
				try {
					shareSellerInfo.setShareDate(FDCSQLFacadeFactory.getRemoteInstance()
							.getServerTime());
				} catch (BOSException e) {
					logger.error(e.getMessage());
					this.handleException(e);
				}
				shareSellerInfo.setOperationPerson((UserInfo)row.getCell("operationPerson").getUserObject());
				if(row.getCell("isUpdate").getValue()==null)
				{
					shareSellerInfo.setIsUpdate(false);
				}else
				{
					shareSellerInfo.setIsUpdate(((Boolean)row.getCell("isUpdate").getValue()).booleanValue());
				}
				shareSellerInfo.setDescription((String) row.getCell("description").getValue());
				shareSellerList.add(shareSellerInfo);
			}
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("*");
		selectors.add("salesman.*");
		selectors.add("adapterLogList.*");
		selectors.add("adapterLogList.beforeSeller.*");
		selectors.add("adapterLogList.afterSeller.*");
		selectors.add("adapterLogList.operationPerson.*");
		selectors.add("workArea.*");
		selectors.add("workArea.parent");
		selectors.add("workArea.workArea.*");
		selectors.add("businessScope.*");
		selectors.add("businessScope.parent");
		selectors.add("businessScope.businessScope.*");
		selectors.add("linkmanList.*");
		selectors.add("nativePlace");
		selectors.add("dayOfbirth.*");
		return selectors;
	}
	
	public static final String KEY_DESTORY_WINDOW = "destoryWindowAfterSubmit";
	public static final String KEY_SUBMITTED_DATA = "submittedData";
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		this.setFocusLost();
		super.actionSubmit_actionPerformed(e);
		if(Boolean.TRUE.equals(this.getUIContext().get(KEY_DESTORY_WINDOW))){
			destroyWindow();
		}
		
		this.getUIContext().put(KEY_SUBMITTED_DATA, this.editData);
		CacheServiceFactory.getInstance().discardType(new LinkmanEntryInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new TrackRecordInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new ShareSellerInfo().getBOSType());
		actionInsider.setEnabled(true);
		
	}
	
	
	
	/**
	 * 对录入进行验证
	 * */
	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);
		vertifyInput();
	}
	
	public void vertifyInput() throws Exception {
//		FDCCustomerInfo info = this.editData;
		if(this.txtNumber.isEnabled() && this.txtNumber.isEditable() && StringUtils.isEmpty(this.txtNumber.getText())){
			MsgBox.showWarning("客户代码不能为空");
			this.abort();
		}
		if(this.prmtLevel.getValue()==null) {
			MsgBox.showInfo(this, "商机级别不能为空!");
			this.abort();			
		}
		if(!this.chkIsForSHE.isSelected()  &&  !this.chkIsForTen.isSelected()  &&  !this.chkIsForPPM.isSelected()){
			MsgBox.showInfo(this, "售楼,租赁,物业属性至少选一项!");
			this.abort();
		}
		
		verifyF7InputNotNull(f7Project, "初始登记项目不能为空");
		if(!com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum.EnterpriceCustomer.equals(boxCustomerType.getSelectedItem())){
			verifyInputNotNull(txtPhone, "公司电话号码不能为空");
			if(this.txtPhone.getText().length()>0&&this.txtPhone.getText().length()<5){
				MsgBox.showWarning("公司电话录入时输入位数不能少于5位！");
				this.abort();
			}
		}
		if(this.f7ReceptionType.getValue()==null) {
			MsgBox.showInfo(this, "首次接待方式不能为空!");
			this.abort();			
		}
		if(this.pkFirstDate.getValue()==null) {
			MsgBox.showInfo(this, "首次接待时间不能为空!");
			this.abort();			
		}
		if(this.txtPhone.getText().length()>0&&this.txtPhone.getText().length()<5){
			MsgBox.showWarning("公司电话录入时输入位数不能少于5位！");
			this.abort();
		}
		
		verifyInputNotNull(txtName, "姓名不能为空");
		verifyF7InputNotNull(f7salesman, "销售顾问不能为空");
		verifyLength(txtareaDescription, 255, "企业简介、主营业务");
		
		/***
		 * 此处，下面的判断语句，有5个，每个里面都有RPC调用
		 * 需要优化考虑
		 * 
		 * 此次优化，暂时将CustomerName,Phone合并到服务器端判断
		 * TODO 后续继续完善
		 */
		//此处的editData本来应该有值的，verifyInput应该是在storeField之后,现在是在之前
		
		//by tim_gao 2010-11-17 radiobutton 已经去掉
	//	boolean isSingle = setting.getCheckedRadioButton().equals("kDRadioSingle");
		editData.setName(txtName.getText());
		editData.setPhone(txtPhone.getText());
//		Map rMap = ((IFDCCustomer)this.getBizInterface()).verifySave(editData,isSingle);
		Map rMap = ((IFDCCustomer)this.getBizInterface()).verifySave(editData);
//		if((rMap.containsKey("dupNameMsg")&&setting.getNameRepeat()==0)&&
//				(rMap.containsKey("dupPhoneMsg")&&setting.getPhoneRepeat()==0)){
//			//两个都强制不保存，合并显示
//			FDCMsgBox.showConfirm3a(this, "不能保存，请查看详细信息", (String)rMap.get("dupNameMsg")+"\n"+(String)rMap.get("dupPhoneMsg"));
//			abort();//强制不保存
//		}
//		//检查用户选择的重复条件
//		
//		
//		if(rMap.containsKey("dupNameMsg")){
//				if(setting.getNameRepeat()==2)
//				{
//					//不提示，直接保存
//				}else if(setting.getNameRepeat()==0){
//					FDCMsgBox.showConfirm3a(this, "不能保存，请查看详细信息", (String)rMap.get("dupNameMsg"));
//					abort();//强制不保存
//				}else if(setting.getNameRepeat()==1){
//					//给出提示，客户选择保存;
//					if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(this, "存在重复的信息，请查看详细信息，确定是否保存？", (String)rMap.get("dupNameMsg"))){
//						abort();
//					}
//				}else{
//					abort();
//				}
//			}
//			
//			if(rMap.containsKey("dupPhoneMsg")){
//				if(setting.getPhoneRepeat()==2)
//				{
//					//不提示，直接保存
//				}else if(setting.getPhoneRepeat()==0){
//					FDCMsgBox.showConfirm3a(this, "不能保存，请查看详细信息", (String)rMap.get("dupPhoneMsg"));
//					abort();//强制不保存
//				}else if(setting.getPhoneRepeat()==1){
//					//给出提示，客户选择保存;
//					if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(this, "存在重复的信息，请查看详细信息，确定是否保存？", (String)rMap.get("dupPhoneMsg"))){
//						abort();
//					}
//				}else{
//					abort();
//				}
//			}
//			if(rMap.containsKey("dupNameAndPhoneMsg")){
//				if(setting.getNameAndPhoneRepeat() == 2){
//					
//				}else if(setting.getNameAndPhoneRepeat()==0){
//					FDCMsgBox.showConfirm3a(this, "不能保存，请查看详细信息", (String)rMap.get("dupNameAndPhoneMsg"));
//					abort();//强制不保存
//				}else if(setting.getNameAndPhoneRepeat()==1){
//					//给出提示，客户选择保存;
//					if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(this, "存在重复的信息，请查看详细信息，确定是否保存？", (String)rMap.get("dupNameAndPhoneMsg"))){
//						abort();
//					}
//				}else{
//					abort();
//				}
//			}
//			
////		}
		
	//by tim_gao 2010-11-17 修改时，客户名称和电话显灰，不进行校验
		if(!(OprtState.EDIT).equals(this.getOprtState())&&!com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum.EnterpriceCustomer.equals(boxCustomerType.getSelectedItem())){
			if(FDCCustomerHelper.verifyPhoneAndName(rMap, this)){
		   abort();
			}	
		}
		verifyCertificateNumber();
//		verifyPhone();
		verifyLinkman();
		verifyShareSeller();
	}
	
	
	
	private void verifyCertificateNumber() throws BOSException {
		//CertifacateNameEnum nameEnum = (CertifacateNameEnum) this.boxCertificateName.getSelectedItem();
		
		//update by renliang
		CertificateInfo  cetifiInfo = null;
		if(this.txtCertificate.getValue()!=null){
			cetifiInfo = (CertificateInfo) this.txtCertificate.getValue();
		}
		
		/*if(cetifiInfo==null){
			FDCMsgBox.showWarning(this,"请先选择证件名称！");
			SysUtil.abort();
		}*/
		 
		String certificateNum = this.txtCertificateNumber.getText().trim();
		if(FDCHelper.isEmpty(certificateNum)){
			return;
		}
		
		//如果是身份证，号码位数不为15或18时，给出提示
		/*if(CertifacateNameEnum.IDCard.equals(nameEnum)){
			if(certificateNum.length() != 18  &&  certificateNum.length() != 15){
				int result = MsgBox.showConfirm2New(null, "客户身份证号码位数不正确,是否继续？");
				if(result != MsgBox.YES){
					this.abort();
				}
			}
		}*/
		
		
		if(cetifiInfo!=null && cetifiInfo.getId()!=null && cetifiInfo.getNumber().equals(CERTIFICATE_ID_CARD_NAME)){
			if(certificateNum.length() != 18  &&  certificateNum.length() != 15){
				int result = MsgBox.showConfirm2New(null, "客户身份证号码位数不正确,是否继续？");
				if(result != MsgBox.YES){
					this.abort();
				}
			}else{
				//如何身份证满足15或者18位的话，则要校验
				try {
					if(!CertificateUtil.CardValidate(certificateNum)){
						FDCMsgBox.showWarning(this,"身份证号码不符合规则，请重新录入！");
						SysUtil.abort();
					}
				} catch (ParseException e) {
					logger.error(e.getMessage());
				}
			}
		
		//如果是修改操作,证件未进行了变化时不用进行验证
		if(STATUS_EDIT.equals(this.getOprtState())){
			if(this.editData.getCertificateName()!=null && this.editData.getCertificateNumber()!=null)	{
				/*if(nameEnum.equals(this.editData.getCertificateName())  &&  certificateNum.equals(this.editData.getCertificateNumber())){
					return;
				}*/
				if(cetifiInfo!=null && cetifiInfo.getName()!=null && cetifiInfo.getName().toString().equals(this.editData.getCertificateName())  &&  certificateNum.equals(this.editData.getCertificateNumber())){
					return;
				}
			}
		}
		
		String id = null;
		if(this.editData.getId() != null){
			id = this.editData.getId().toString();
		}
		
		/*if(!FDCCustomerHelper.verifyCertificateNumber(nameEnum, certificateNum, id)){
			this.abort();;
		}*/
		if(!FDCCustomerHelper.verifyCertificateNumber(cetifiInfo.getId().toString(), certificateNum, id)){
			this.abort();;
		}
	}
}
	
	private void verifyCustomerName() throws BOSException {
		String customerName = this.txtName.getText();
		if(!FDCCustomerHelper.verifyCustomerName(customerName,setting)){
			this.abort();;
		}
	}
	
	private void verifyF7InputNotNull(KDBizPromptBox f7box, String msg) {
		Object obj = f7box.getValue();
		if(obj == null){
			MsgBox.showWarning(this,msg);
			SysUtil.abort();
		}
	}
	
	
	
	
	/**
	 * 验证联系人的录入条件，联系人，关系为必录
	 * */
	private void verifyLinkman() {
		for (int i = 0; i < tblLinkman.getRowCount(); i++) {
			IRow row = tblLinkman.getRow(i);
			//LinkmanEntryInfo entry = (LinkmanEntryInfo) row.getUserObject();						
			
			Object obj = row.getCell("customerName").getValue();
			Object obj1 = row.getCell("relation").getValue();			
			
			if(obj != null  &&  this.editData.getId() != null){
				if(this.editData.getId().equals(((FDCCustomerInfo)obj).getId())){
					MsgBox.showWarning("联系人不能选择自己.");
					this.abort();
				}
			}
			
//			if(obj == null  &&  obj1 != null){
//				this.tabNew.setSelectedIndex(0);
//				this.kDTabbedPane1.setSelectedIndex(0);
//				MsgBox.showWarning("联系人中的客户名称为必录项,请录入客户名称.");
//				this.abort();
//			}
			
//			if(obj == null  &&  !linkmanFlag){
//				this.tabNew.setSelectedIndex(0);
//				this.kDTabbedPane1.setSelectedIndex(0);
//				int result = MsgBox.showConfirm2("未录入联系人,确定则忽略该联系人进行保存,取消中断保存操作");
//				if(result != MsgBox.YES){
//					SysUtil.abort();
//				}
//				linkmanFlag = true;
//				continue;
//			}
			
			
			//联系人不为空，但关系为空，提示关系为必录项
			if(obj != null  &&  obj1 == null){
				this.tabNew.setSelectedIndex(0);
				this.kDTabbedPane1.setSelectedIndex(0);
				MsgBox.showWarning("联系人中的关系为必录项,请录入关系.");
				SysUtil.abort();
			}
		}
	}
	
	/**
	 * 验证共享销售顾问的录入条件，销售顾问为必录
	 * */
	private void verifyShareSeller() {
		boolean shareSellerFlag = false;//联系人为NULL时，是否忽略联系人进行客户保存
		for (int i = 0; i < tblShareSeller.getRowCount(); i++) {
			IRow row = tblShareSeller.getRow(i);
			
			Object obj = row.getCell("shareObject").getValue();
			if(obj == null  &&  !shareSellerFlag){
				this.tabNew.setSelectedIndex(0);
				this.kDTabbedPane1.setSelectedIndex(0);
				MsgBox.showInfo("未录入销售顾问不能保存!");
				SysUtil.abort();
				shareSellerFlag = true;
				continue;
			}
		}
	}
	
	public Date simpleDate(Date date) throws ParseException{
		Date d = (isNotNull(date)) ? date : new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");;
		return format .parse(format.format(d));
	}
	
	public boolean isNotNull(Object o){
		return (!isNull(o));
	}
	
	public static boolean isNull(Object o){
		return (o == null);
	}
	
	private void verifyLength(KDTextArea txtareaDescription, int length, String msg) {
		String str = txtareaDescription.getText().trim();
		if(!str.equals("") && str.length()>length){
			MsgBox.showWarning(msg + "超过最大字符长度" + length);
			SysUtil.abort();
		}
		
	}
	
	private void verifyInputNotNull(KDTextField txt, String msg) {
		if(!FDCCustomerHelper.verifyInputNotNull(txt, msg)){
			this.abort();
		}
	}
	
	public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmitOption_actionPerformed(e);
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		this.setFocusLost();
//		if("".equals(this.f7WorkArea.getValue())&& null==this.f7WorkArea.getValue())
//		{
//			return;
//		}
//		else
//			this.f7WorkArea.setValue(this.editData.getWorkArea().getName());
		//RoomPriceBillInfo roomInfo = RoomPriceBillFactory.getRemoteInstance().getRoomPriceBillInfo(pk,sic);
	//	FDCCustomerFactory.getRemoteInstance().updatePartial(model, selector);
		super.actionSave_actionPerformed(e);
		actionInsider.setEnabled(true);
	}
	
	/**
	 * 创建带日期选择的cellEditor
	 * */
	private KDTDefaultCellEditor createDPCellEditor(boolean editable) {
		KDDatePicker datePicker = new KDDatePicker();
		datePicker.setEditable(editable);
		KDTDefaultCellEditor dpEditor = new KDTDefaultCellEditor(datePicker);
		return dpEditor;
	}
	
	/**
	 * 创建带文本编辑框的cellEditor
	 * @param length
	 * 			文本框最大输入长度
	 * @param editable
	 * 			是否可编辑
	 * */
	private KDTDefaultCellEditor createTxtCellEditor(int length, boolean editable) {
		KDTextField textField = new KDTextField();
		textField.setMaxLength(length);
		textField.setEditable(editable);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		return txtEditor;
	}
	
	
	protected void tblShareSeller_editStopped(KDTEditEvent e) throws Exception {
		super.tblShareSeller_editStopped(e);
		int colIndex = e.getColIndex();
		
		if(colIndex == 0){
			int rowIndex = e.getRowIndex();
			IRow row = tblShareSeller.getRow(rowIndex);
			Object obj = row.getCell("shareSellerName").getUserObject();
			if(obj != null){
				UserInfo info = (UserInfo) obj;
				row.getCell("shareSellerNumber").setValue(info.getNumber());
				//row.getCell("shareDate").setValue(info.getCreateTime());
				//row.getCell("description").setValue(info.getDescription());
			}
			else{
				row.getCell("shareSellerNumber").setValue(null);
				//row.getCell("shareDate").setValue(null);
				//row.getCell("description").setValue(null);
			}
		}
	}
	
	/**
	 * 单元格事件，解决单选框鼠标单击不能选中问题
	 */
	protected void tblShareSeller_activeCellChanged(KDTActiveCellEvent e) throws Exception {
		super.tblShareSeller_activeCellChanged(e);
		TenancyClientHelper.tableName_activeCellChanged(e,this.tblShareSeller);
	}
	
	private void verifyPhone() throws BOSException, Exception {
		if(!oprtState.equals(STATUS_ADDNEW)){
			return;
		}
		String phone ="";
		if(CustomerTypeEnum.PersonalCustomer.equals(this.boxCustomerType.getSelectedItem())){
			phone = txtPhone.getText();
		}else{
			return;
		}
		if(!FDCCustomerHelper.verifyPhone2(phone,setting)){
			this.abort();
		}
	}
	
	
	
	protected void tblLinkman_editStopped(KDTEditEvent e) throws Exception {
		super.tblLinkman_editStopped(e);
		int colIndex = e.getColIndex();
		
		if(colIndex == 1){
			int rowIndex = e.getRowIndex();
			IRow row = tblLinkman.getRow(rowIndex);
			Object obj = row.getCell("customerName").getValue();
			if(obj != null){
				tblLinkman.getColumn("relation").setRequired(true);
				FDCCustomerInfo info = (FDCCustomerInfo) obj;
				row.getCell("customerNumber").setValue(info.getNumber());
				row.getCell("name").setValue(info.getName());
				row.getCell("customerSex").setValue(info.getSex());
				row.getCell("phone").setValue(info.getPhone());
			}
			else{
				//by tim_gao 如不选入已有客户，不对联系人的旧数据分录清空
//				row.getCell("customerNumber").setValue(null);
//				row.getCell("customerSex").setValue(null);
//				row.getCell("phone").setValue(null);
//				row.getCell("name").setValue(null);
				tblLinkman.getColumn("relation").setRequired(false);
			}
		}
	}
	
	protected void tblSaleCommerce_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblSaleCommerce_tableClicked(e);
		
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if(e.getType()!=1) return;
			CommerceChanceInfo trackInfo = (CommerceChanceInfo)this.tblSaleCommerce.getRow(e.getRowIndex()).getUserObject();
			if (trackInfo == null) {
				return;
			}
			
			CommerceHelper.openTheUIWindow(this,CommerceChanceEditUI.class.getName(),trackInfo.getId().toString());
		}		
	}
	
	protected void tblSaleRecord_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblSaleRecord_tableClicked(e);
		
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if(e.getType()!=1) return;
			
			PurchaseInfo trackInfo = (PurchaseInfo)this.tblSaleRecord.getRow(e.getRowIndex()).getUserObject();
			if (trackInfo == null) {
				return;
			}
			
			CommerceHelper.openTheUIWindow(this,PurchaseEditUI.class.getName(),trackInfo.getId().toString());
		}	
	}
	
	protected void tblTenancyCommerce_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblTenancyCommerce_tableClicked(e);
		
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if(e.getType()!=1) return;
			
			CommerceChanceInfo trackInfo = (CommerceChanceInfo)this.tblTenancyCommerce.getRow(e.getRowIndex()).getUserObject();
			if (trackInfo == null) {
				return;
			}
			
			CommerceHelper.openTheUIWindow(this,CommerceChanceEditUI.class.getName(),trackInfo.getId().toString());
		}	
	}
	
	protected void tblTenancyRecord_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblTenancyRecord_tableClicked(e);
		
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if(e.getType()!=1) return;
			
			TenancyBillInfo trackInfo = (TenancyBillInfo)this.tblTenancyRecord.getRow(e.getRowIndex()).getUserObject();
			if (trackInfo == null) {
				return;
			}
			
			CommerceHelper.openTheUIWindow(this,TenancyBillEditUI.class.getName(),trackInfo.getId().toString());
		}	
	}
	
	protected void tblTrackRecord_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblTrackRecord_tableClicked(e);
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if(e.getType()!=1) return;
			
			TrackRecordInfo trackInfo = (TrackRecordInfo)this.tblTrackRecord.getRow(e.getRowIndex()).getUserObject();
			if (trackInfo == null) {
				return;
			}
			
			CommerceHelper.openTheUIWindow(this,TrackRecordEditUI.class.getName(),trackInfo.getId().toString());
		}		
	}
	
	public void actionCustomerAdapter_actionPerformed(ActionEvent e) throws Exception {
		String  fdcCustomerID = this.editData.getId().toString();
		CustomerAdapterUI.showEditUI(this,fdcCustomerID);
		findSaleman();
	}
	
	public void actionCustomerShare_actionPerformed(ActionEvent e) throws Exception {
		String  fdcCustomerID = this.editData.getId().toString();
		CustomerShareUI.showEditUI(this,fdcCustomerID);
		findShareSeller();
	}
	
	public void actionCustomerCancelShare_actionPerformed(ActionEvent e) throws Exception {
		String  fdcCustomerID = this.editData.getId().toString();
		CustomerCancelShareUI.showEditUI(this,fdcCustomerID);
		findShareSeller();
	}
	
	//当销售顾问变化时,改变所属销售项目的过滤条件
	public void f7salesman_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e){
		UserInfo saleMan = (UserInfo)this.f7salesman.getValue();
		//if(saleMan!=)
		Set proIds = null;
		try {
			proIds = CommerceHelper.getPermitProjectIds(saleMan);
			proIds.add("null");	
		} catch (BOSException e1) {
			
			e1.printStackTrace();
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",proIds,CompareType.INCLUDE));
		view.setFilter(filter);
//		this.f7Project.setEntityViewInfo(view);
	}
	
	
	
	
	
	
	
	//商机管控中  客户跟进调用 
	public KDTabbedPane getKDTabbedPane() {
		return this.kDTabbedPane1;
	}	
	public KDTable getTblTrackRecord() {
		return this.tblTrackRecord;
	}	
	public KDTable getTblShareSeller() {
		return this.tblShareSeller;
	} 	
	public KDTable getTblAdapterLog() {
		return this.tblAdapterLog;
	}	
//	public KDTextField getCustomerName() {
//		return this.txtName;	
//	}	
	public KDBizPromptBox getSaleManPrmt() {
		return this.f7salesman;	
	}
	public KDBizPromptBox getSellProject() {
		return this.f7Project;	
	}
//	public KDTextField getCustomerPhone() {
//		return this.txtPhone;	
//	}	
	public FDCCustomerInfo getCustomerData() {
		return this.editData;	
	}	
	
	public void initOldData() {
		this.initOldData(this.editData);
	}
	
	
	
	//当前登录用户对指定客户是否有修改的权限
	public static boolean hasUpdatePermission(FDCCustomerInfo customerInfo) throws EASBizException, BOSException, UuidException {
		if(customerInfo==null) return false;		
		return hasUpdatePermission(customerInfo.getId().toString());
	}
	
	public static boolean hasUpdatePermission(String customerId) throws EASBizException, BOSException, UuidException {
		if(customerId==null) return false;
		
		UserInfo currUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		if(FDCCustomerFactory.getRemoteInstance().exists("where id='"+customerId+"' and salesman.id='"+currUserInfo.getId().toString()+"' "))
			return true;  //当前登录人员的自己的客户
		
		boolean hasPermission = false;
		//当前组织下，作为指定客户所属的顾问的负责人，且拥有业务操作权限
		hasPermission = SHEHelper.hasOperatorPermission(customerId);
		if(hasPermission) return true;
			
		//是  对该客户的有修改权的共享顾问 
		hasPermission = ShareSellerFactory.getRemoteInstance().exists("where head.id='"+ customerId +"'  and seller.id='"+ currUserInfo.getId().toString() +"'" +
				"and isUpdate=1 and shareModel = '"+ShareModelEnum.SHAREPERSON_VALUE+"' ");
		if(hasPermission) return true;

		//有修改权限的共享单元
		String filterMarketSql = "select FMarketingUnitID from T_SHE_ShareSeller where FHeadID='"+customerId+"' and FisUpdate=1 and FShareModel='shareMarket' ";
		if(MarketingUnitMemberFactory.getRemoteInstance().exists("where head.id in ("+filterMarketSql+") and member.id='"+currUserInfo.getId().toString()+"'"))
			return true;
		
		//有修改权限的共享组织		
		String filterOrgSql = "select FOrgUnitID from T_SHE_ShareSeller where FHeadID='"+customerId+"' and FisUpdate=1 and FShareModel='shareOrgUnit' ";		
		if(MarketingUnitMemberFactory.getRemoteInstance().exists("where head.orgUnit.id in ("+filterOrgSql+") and member.id='"+currUserInfo.getId().toString()+"'"))
			return true;
		
		//是   对该客户的有修改权的共享顾问 的 负责人且有操作权限
		SaleOrgUnitInfo saleOrg = SysContext.getSysContext().getCurrentSaleUnit();
		String filterSaleManSql = "select FSellerID from T_SHE_ShareSeller where FHeadID='"+customerId+"' and FisUpdate=1 and FShareModel='sharePerson' ";
		MarketUnitPlatCollection muPlatColl = MarketUnitPlatFactory.getRemoteInstance().getMarketUnitPlatCollection("select dutyPerson.id,marketUnit.id " +
				"where orgUnit.id='"+ saleOrg.getId().toString() +"' and dutyPerson.id='"+currUserInfo.getId().toString()+"' and member.id in ("+ filterSaleManSql +") ");
		for(int i=0;i<muPlatColl.size();i++) {
			MarketUnitPlatInfo muPlatInfo = muPlatColl.get(i);
			boolean existFlag = MarketingUnitMemberFactory.getRemoteInstance().exists("where head.id='"+muPlatInfo.getMarketUnit().getId().toString()+"' " +
					"and isDuty=1 and isOperation=1 and member.id='"+muPlatInfo.getDutyPerson().getId().toString()+"'");
			if(existFlag) return true;
		}
		
		return false;
	}
	
	
	////////////////////
	/**
	 * 初始化  物业页签  房产信息
	 */
	private void initPropertyHouse(){
		
		FDCCustomerInfo info = this.editData;//  new FDCCustomerInfo();
		if(info==null) 
			return;
		JoinVoucherCollection moveInColl = getJoinInList(info);
		CustomerMoveVoucherCollection moveOutColl = new CustomerMoveVoucherCollection();//getCustomerMoveOutList(info);
		
		fetchHouseTable(moveInColl,moveOutColl);
		
		
		registerListener();
		
//		if(moveInColl!=null && moveInColl.get(0)!=null){
//			initPropertyArrearage(info,moveInColl.get(0).getRoom());
//		}else if(moveOutColl!=null && moveOutColl.get(0)!=null){
//			initPropertyArrearage(info,moveOutColl.get(0).getRoom());
//		}
//		if(moveInColl!=null && moveInColl.get(0)!=null){
//			initPropertyDeposit(info,moveInColl.get(0).getRoom());
//		}else if(moveOutColl!=null && moveOutColl.get(0)!=null){
//			initPropertyDeposit(info,moveOutColl.get(0).getRoom());
//		}
		
	}
	
	/**
	 * 初始化  物业页签   欠款记录
	 * @param info
	 * @param room
	 */
	private void  initPropertyArrearage(FDCCustomerInfo info,RoomInfo room){  //物业房号
		PPMGeneralARCollection generalcoll = getPPMGeneralAR(info,room.getId().toString());
		PPMMeasureARCollection measurecoll = getMeasureAR(info,room.getId().toString());
		PPMTemporaryCollection temporarycoll =  getTemporaryAR(info,room.getId().toString());
		
		filterPPMARData(generalcoll,measurecoll,temporarycoll);
		
		fetchArrearageTable(generalcoll,measurecoll,temporarycoll);
		
		
	}
	
	/**
	 * 初始化 物业页签   预收款记录
	 * @param info
	 * @param room
	 */
	private void  initPropertyDeposit(FDCCustomerInfo info,RoomInfo room) {
		
		FDCReceiveBillEntryCollection receivingentryColl = getPPMDeposit(info,room.getId().toString());
		
		RoomInfo roomInfo =room;
		SellProjectInfo sellProjectInfo = new SellProjectInfo();
		
		try
		{
			if(roomInfo.getBuilding()==null){
				roomInfo = 	RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(room.getId()));
				if(roomInfo==null)
				{
					return ;
				}
			}
			
			BuildingInfo  building = (BuildingInfo) com.kingdee.bos.ui.face.UIRuleUtil.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(roomInfo,"building"));
			if(building.getSellProject()!=null){
				sellProjectInfo =(SellProjectInfo) com.kingdee.bos.ui.face.UIRuleUtil.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(building,"sellproject"));
			}
		} catch (BOSException e)
		{
			e.printStackTrace();
		} catch (EASBizException e)
		{
			e.printStackTrace();
		}
		
		
		fetchDepositTable(receivingentryColl,roomInfo,sellProjectInfo);
	} 
	
	/**
	 * 初始化客服 页签  特约服务
	 */
	private void initCustomerServiceSpecial(){
		FDCCustomerInfo info = this.editData;//  new FDCCustomerInfo();
		if(info==null) 
			return;
		CmsTimeConferBillCollection cmsTimeConferColl = getCMSTimeConferList(info);
		
		fetchCustomerServiceSpecialTable(cmsTimeConferColl);
		
		
		//registerListener();
		
		//if(cmsTimeConferColl!=null && cmsTimeConferColl.get(0)!=null){
			//CmsTimeConferBillInfo  tempInfo = cmsTimeConferColl.get(0);
			initCustomerServiceRecord(info,new RoomInfo());
			initCustomerServiceComplaint(info,new RoomInfo());
		//}
		
		
	}
	
	/**
	 * 初始化客服 页签  服务记录
	 * @param info
	 * @param room
	 */
	private void  initCustomerServiceRecord(FDCCustomerInfo info,RoomInfo room){
		ServiceProcessBillCollection serviceProcessColl = getCustomerServiceRecordList(info,"");
		fetchCustomerServiceRecordTable(serviceProcessColl);
	}
	/**
	 * 初始化客服 页签  投诉记录
	 * @param info
	 * @param room
	 */
	private void  initCustomerServiceComplaint(FDCCustomerInfo info,RoomInfo room) {
		ServiceProcessBillCollection serviceProcessColl = getCustomerServiceComplaintList(info,"");
		fetchCustomerServiceComplaintTable(serviceProcessColl);
	}
	
	
	
	/**
	 * 过滤常规应收 计量应收及临时应收中，各笔应收款的收费情况，如果应收金额-减免金额-已收金额>0  则保留
	 * @param generalcoll
	 * @param measurecoll
	 * @param temporarycoll
	 */
	private void filterPPMARData(PPMGeneralARCollection generalcoll,
			PPMMeasureARCollection measurecoll,
			PPMTemporaryCollection temporarycoll)
	{
		PPMGeneralARCollection temp_generalcoll = new PPMGeneralARCollection();
		PPMMeasureARCollection temp_measurecoll =  new PPMMeasureARCollection();
		PPMTemporaryCollection  temp_temporarycoll =  new PPMTemporaryCollection();
		if(generalcoll!=null){
			int len = generalcoll.size();
			for(int i=0;i<len;i++){
				PPMGeneralARInfo info =generalcoll.get(i);
				int desvalue =checkarrearage(info).compareTo(ZERO);
				if(desvalue>0){
					temp_generalcoll.add(info);
				}
			}
			generalcoll.clear();
			generalcoll.addCollection(temp_generalcoll);
		}
		
		if(measurecoll!=null){
			int len = measurecoll.size();
			for(int i=0;i<len;i++){
				PPMMeasureARInfo info =measurecoll.get(i);
				int desvalue = checkarrearage(info).compareTo(ZERO);
				if(desvalue>0){
					temp_measurecoll.add(info);
				}
			}
			measurecoll.clear();
			measurecoll.addCollection(temp_measurecoll);
		}
		
		if(temporarycoll!=null){
			int len = temporarycoll.size();
			for(int i=0;i<len;i++){
				PPMTemporaryInfo info =temporarycoll.get(i);
				int desvalue = checkarrearage(info).compareTo(ZERO);
				if(desvalue>0){
					temp_temporarycoll.add(info);
				}
			}
			temporarycoll.clear();
			temporarycoll.addCollection(temp_temporarycoll);
		}
		
		
	}
	
	/**
	 * 获取 客户入住登记
	 * @param info
	 * @return
	 */
	
	private JoinVoucherCollection getJoinInList(FDCCustomerInfo info){
		
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		
    	FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", info.getId().toString()));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));	
		entityViewInfo.setFilter(filterInfo);
		SorterItemCollection sorterColl = new SorterItemCollection();
		SorterItemInfo roomsort = new SorterItemInfo("room");
		roomsort.setSortType(SortType.DESCEND);
		sorterColl.add(roomsort);
		sorterColl.add(new SorterItemInfo("joinStatus"));
		sorterColl.add(new SorterItemInfo("joinType"));
		entityViewInfo.setSorter(sorterColl);
		JoinVoucherCollection incoll =null;
		try
		{
			 incoll = 	JoinVoucherFactory.getRemoteInstance().getJoinVoucherCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		
		
		return incoll;
	}
	
	
	/**
	 * 获取 客户迁出登记
	 * 已废弃方法
	 * @param info
	 * @return
	 */
	private CustomerMoveVoucherCollection getCustomerMoveOutList(FDCCustomerInfo info){
		
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		
    	FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", info.getId().toString()));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));	
		entityViewInfo.setFilter(filterInfo);
		CustomerMoveVoucherCollection outcoll =null;
		try
		{
			outcoll = 	CustomerMoveVoucherFactory.getRemoteInstance().getCustomerMoveVoucherCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		return outcoll;
	}
	
	
	/**
	 * 获取常规应收
	 * @param info
	 * @param roomId
	 * @return
	 */
	private PPMGeneralARCollection  getPPMGeneralAR(FDCCustomerInfo info,String roomId){
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		
    	FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", info.getId().toString()));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("room.id", roomId));	
		entityViewInfo.setFilter(filterInfo);
		PPMGeneralARCollection incoll =null;
		try
		{
			 incoll = 	PPMGeneralARFactory.getRemoteInstance().getPPMGeneralARCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		return incoll;
	}
	
	
	/**
	 * 计量应收
	 * @param info
	 * @param roomId
	 * @return
	 */
	private PPMMeasureARCollection  getMeasureAR(FDCCustomerInfo info,String roomId){
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", info.getId().toString()));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("room.id", roomId));	
		entityViewInfo.setFilter(filterInfo);
		PPMMeasureARCollection incoll =null;
		try
		{
			incoll = 	PPMMeasureARFactory.getRemoteInstance().getPPMMeasureARCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		return incoll;
	}
	
	
	/**
	 * 临时应收
	 * @param info
	 * @param roomId
	 * @return
	 */
	private PPMTemporaryCollection  getTemporaryAR(FDCCustomerInfo info,String roomId){
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", info.getId().toString()));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("room.id", roomId));	
		entityViewInfo.setFilter(filterInfo);
		PPMTemporaryCollection incoll =null;
		try
		{
			incoll = 	PPMTemporaryFactory.getRemoteInstance().getPPMTemporaryCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		return incoll;
	}
	
	
	/** 
	 *获取 收款记录  收款明细
	 * @param info
	 * @param roomId
	 * @return
	 */
	private  FDCReceiveBillEntryCollection  getPPMDeposit(FDCCustomerInfo info,String roomId){
		///获得  房地产客户关联的客户对象
		
		String sysCustomerId =null;
		try
		{
			CustomerInfo tempObj =  (CustomerInfo)UIRuleUtil.getObject(UIRuleUtil.getProperty(info, "sysCustomer"));
			if(tempObj!=null ){
				sysCustomerId = tempObj.getId().toString();
			}
		} catch (DataAccessException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (BOSException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if(sysCustomerId==null || sysCustomerId.length()<1){
			return new FDCReceiveBillEntryCollection();
		}
		/////
		/// 客户分录对象中 获得 包含该客户的收款单单号  ///
		EntityViewInfo ceView = new EntityViewInfo();
		FilterInfo cefilterInfo = new FilterInfo();
		
		cefilterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", sysCustomerId));	
		ceView.setFilter(cefilterInfo);
		
		CustomerEntryCollection cecoll = null;
		try
		{
			cecoll = CustomerEntryFactory.getRemoteInstance().getCustomerEntryCollection(ceView);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		if(cecoll==null ||  cecoll.size()<1){
			return  new FDCReceiveBillEntryCollection();
		}
		
		////////////////////////////////////////////////////////////////////////////////
		///过滤 包含指点房间的收款单
		Set receiveBillSet = new HashSet();
		
		CustomerEntryInfo tempInfo=null;
		for(int i=0;i< cecoll.size();i++){
			tempInfo =  (CustomerEntryInfo)cecoll.get(i);
			receiveBillSet.add(tempInfo.getHead().getId().toString());
		}
		tempInfo= null;
		
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new  FilterItemInfo("room.id", roomId));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("id", receiveBillSet,CompareType.INCLUDE));
		entityViewInfo.setFilter(filterInfo);
		FDCReceiveBillCollection fdcreceiveColl =null;
		try
		{
			 fdcreceiveColl = 	FDCReceiveBillFactory.getRemoteInstance().getFDCReceiveBillCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		
		if(fdcreceiveColl==null || fdcreceiveColl.size()<1){
			return new FDCReceiveBillEntryCollection();
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////
		///根据收款单获得 关联收款单 -收款明细对象
		FDCReceiveBillEntryCollection  returnColl= new FDCReceiveBillEntryCollection();
		Set  set = new HashSet();
		for(int i=0;i<fdcreceiveColl.size();i++){
			set.add(fdcreceiveColl.get(i).getId().toString());
		}
	
		EntityViewInfo receiveViewInfo = new EntityViewInfo();
		FilterInfo receivefilterInfo = new FilterInfo();
		
		receivefilterInfo.getFilterItems().add(new  FilterItemInfo("fdcReceiveBill.id", set,CompareType.INCLUDE));
		ReceivingBillCollection receivecoll = null;
		Set receiveSet = new HashSet();
		receiveViewInfo.setFilter(receivefilterInfo);
		try
		{
			receivecoll  = ReceivingBillFactory.getRemoteInstance().getReceivingBillCollection(receiveViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		
		if(receivecoll==null ||receivecoll.size()<1){
			return returnColl;
			
		}
		for(int i=0;i<receivecoll.size();i++){
			receiveSet.add(receivecoll.get(i).getId().toString());
		}
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		///获得 预收款 的  款项定义 集合
		EntityViewInfo moneyDefineViewInfo = new EntityViewInfo();
		FilterInfo moneyDefinefilterInfo = new FilterInfo();
		moneyDefinefilterInfo.getFilterItems().add(new  FilterItemInfo("moneyType", MoneyTypeEnum.PREMONEY_VALUE));
		moneyDefineViewInfo.setFilter(moneyDefinefilterInfo);
		MoneyDefineCollection mdcoll = null;
		Set mdSet = new HashSet();
		try
		{
			mdcoll = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(moneyDefineViewInfo);
		} catch (BOSException e1)
		{
			e1.printStackTrace();
		}
		if(mdcoll==null  || mdcoll.size()<1){
			return returnColl;
			
		}
		for(int i=0;i<mdcoll.size();i++){
			mdSet.add(mdcoll.get(i).getId().toString());
		}
		
		//////////////////////////////////////////////////////////////////
		////收款明细中 过滤  指点关联单据 + 收款类型为 预收的 内容
		EntityViewInfo entryViewInfo = new EntityViewInfo();
		FilterInfo entryfilterInfo = new FilterInfo();
		entryfilterInfo.getFilterItems().add(new  FilterItemInfo("moneyDefine.id", mdSet,CompareType.INCLUDE));
		entryfilterInfo.getFilterItems().add(new  FilterItemInfo("receivingBill.id", receiveSet,CompareType.INCLUDE));
		entryViewInfo.setFilter(entryfilterInfo);
		
		try
		{
			returnColl  = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(entryViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		
		return returnColl;
		
		
	}
	
	
	/**
	 * 获取特约特约服务数据
	 * @param info
	 * @return
	 */
	private  CmsTimeConferBillCollection getCMSTimeConferList(FDCCustomerInfo info){
		
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		
    	FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", info.getId().toString()));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("isEnable","1"));	
		entityViewInfo.setFilter(filterInfo);
		CmsTimeConferBillCollection cmsConfercoll =null;
		try
		{
			 cmsConfercoll = CmsTimeConferBillFactory.getRemoteInstance().getCmsTimeConferBillCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		
		
		return cmsConfercoll;
	}
	
	
	/**
	 * 获取服务记录
	 * @param info
	 * @param roomId
	 * @return
	 */
	private  ServiceProcessBillCollection getCustomerServiceRecordList(FDCCustomerInfo info,String roomId){
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		
    	FilterInfo filterInfo = new FilterInfo();
    	filterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", info.getId().toString()));	
    	//filterInfo.getFilterItems().add(new  FilterItemInfo("room", roomId));	
    	filterInfo.getFilterItems().add(new  FilterItemInfo("processType", FDCStdQueationProcessType.CONSULATION_VALUE));	
    	filterInfo.getFilterItems().add(new  FilterItemInfo("processType", FDCStdQueationProcessType.REPAIR_VALUE));	
    	filterInfo.getFilterItems().add(new  FilterItemInfo("processType", FDCStdQueationProcessType.SERVICE_VALUE));
    	
    	filterInfo.setMaskString(" #0 and (#1 or  #2 or #3) ");
    	 entityViewInfo.setFilter(filterInfo);
    	ServiceProcessBillCollection csrColl =null;
		try
		{
			 csrColl = ServiceProcessBillFactory.getRemoteInstance().getServiceProcessBillCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		return csrColl;
	}
	
	/**
	 * 获取投诉记录
	 * @param info
	 * @param roomId
	 * @return
	 */
	private  ServiceProcessBillCollection getCustomerServiceComplaintList(FDCCustomerInfo info,String roomId){
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		
		
		
    	FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", info.getId().toString()));	
		//filterInfo.getFilterItems().add(new  FilterItemInfo("room", roomId));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("processType", FDCStdQueationProcessType.COMPLAIN_VALUE));	
		entityViewInfo.setFilter(filterInfo);
		ServiceProcessBillCollection spbColl =null;
		try
		{
			 spbColl = ServiceProcessBillFactory.getRemoteInstance().getServiceProcessBillCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		return spbColl;
	}
	
	
	/**
	 * 填充 房产信息
	 * @param incoll
	 * @param outcoll
	 */
	private void fetchHouseTable(JoinVoucherCollection incoll, CustomerMoveVoucherCollection outcoll){
		this.houseProperty.checkParsed();
		houseProperty.getStyleAttributes().setLocked(false);
		houseProperty.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		houseProperty.getColumn("id").getStyleAttributes().setHided(true);
		
		
		//客户入住登记  迁出登记
		if(incoll!=null){
			for(int i=0;i<incoll.size();i++) {
				JoinVoucherInfo inInfo = incoll.get(i);
				try
				{
					//TenancyBillInfo trackInfo = custInfo.getTenancyBill();
					if(inInfo!=null) {
						RoomInfo room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(inInfo.getRoom().getId()));
						SellProjectInfo sellProjectInfo = (SellProjectInfo)com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"sellproject"));
						IRow row = this.houseProperty.addRow();
						row.setUserObject(inInfo);
						row.getCell("id").setValue(inInfo.getId().toString());
						row.getCell("projectName").setValue(sellProjectInfo);
						if(sellProjectInfo.getSubarea()!=null && sellProjectInfo.getSubarea().size()>0){
							SubareaInfo subInfo = (SubareaInfo) sellProjectInfo.getSubarea().get(0);
							row.getCell("partition").setValue(subInfo.getNumber());
						}
						row.getCell("building").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(room,"building")));
						row.getCell("unit").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(room,"buildunit")));
						row.getCell("room").setValue(room);
						row.getCell("buildingarea").setValue(room.getBuildingArea());
						row.getCell("roomArea").setValue(room.getRoomArea());
						row.getCell("actualBuildingArea").setValue(room.getActualBuildingArea());
						row.getCell("actualRoomArea").setValue(room.getActualRoomArea());
						row.getCell("bizDate").setValue(inInfo.getBizDate());
						
						row.getCell("customerType").setValue(getCustomerType(inInfo.getJoinType(),inInfo.getJoinStatus()));
						row.getCell("phone").setValue(inInfo.getPhone());
						
					}
				} catch (EASBizException e)
				{
					e.printStackTrace();
				} catch (BOSException e)
				{
					e.printStackTrace();
				}
			}	
		}
		
		/*  Unused
		//MoveOut
		if(outcoll!=null){
			for(int i=0;i<outcoll.size();i++) {
				CustomerMoveVoucherInfo inInfo = outcoll.get(i);
				try
				{
				//TenancyBillInfo trackInfo = custInfo.getTenancyBill();
					if(inInfo!=null) {
						RoomInfo room;
					
							room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(inInfo.getRoom().getId()));
						//RoomInfo room = inInfo.getRoom();
						IRow row = this.houseProperty.addRow();
						row.setUserObject(inInfo);
						row.getCell("id").setValue(inInfo.getId().toString());
						row.getCell("projectName").setValue(
								com.kingdee.bos.ui.face.UIRuleUtil
										.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"sellproject")));
						row.getCell("building").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(room,"building")));
						row.getCell("unit").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(room,"buildunit")));
						row.getCell("room").setValue(room);
						row.getCell("buildingarea").setValue(room.getBuildingArea());
						row.getCell("roomArea").setValue(room.getRoomArea());
						row.getCell("actualBuildingArea").setValue(room.getActualBuildingArea());
						row.getCell("actualRoomArea").setValue(room.getActualRoomArea());
						row.getCell("bizDate").setValue(inInfo.getBizDate());
						
						//row.getCell("customerType").setValue(getCustomerType(inInfo.getJoinType(),inInfo.getJoinStatus()));
						//row.getCell("phone").setValue(inInfo.get);//TODO
						
					}
				} catch (EASBizException e)
				{
					e.printStackTrace();
				} catch (BOSException e)
				{
					e.printStackTrace();
				}
			}	
			
		}*/
		
		
	}
	
	/**
	 * 填充欠款记录
	 * 
	 * @param generalcoll
	 * @param measurecoll
	 * @param temporarycoll
	 */
	private void fetchArrearageTable(PPMGeneralARCollection generalcoll,
	PPMMeasureARCollection measurecoll,
	PPMTemporaryCollection temporarycoll){
		this.advanceReceipt.removeRows();
		this.advanceReceipt.checkParsed();
		advanceReceipt.getStyleAttributes().setLocked(false);
		advanceReceipt.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		advanceReceipt.getColumn("id").getStyleAttributes().setHided(true);
		
		int seq =0; //line number  
		
		if(generalcoll!=null){
			for(int i=0;i<generalcoll.size();i++) {
				fetchArrearageTableRow(this.advanceReceipt,generalcoll.get(i),seq);
			}	
		}
		
		if(measurecoll!=null){
			for(int i=0;i<measurecoll.size();i++) {
				fetchArrearageTableRow(this.advanceReceipt,measurecoll.get(i),seq);
			}	
		}
		if(temporarycoll!=null){
			for(int i=0;i<temporarycoll.size();i++) {
					fetchArrearageTableRow(this.advanceReceipt,temporarycoll.get(i),seq);
			}
		}
			
		}
	
	
	/**
	 *填充  常规应收、计量应收及临时应收  欠款记录 
	 * @param table
	 * @param inInfo
	 * @param seq   未用
	 */
	private void  fetchArrearageTableRow(KDTable table,PPMARBaseInfo inInfo,int seq){
		
		IRow row = table.addRow();
		if(inInfo!=null){
			try
			{
			//row.setUserObject(inInfo);
			row.getCell("id").setValue(inInfo.getId().toString());
				row.getCell("projectName").setValue(com.kingdee.bos.ui.face.UIRuleUtil
						.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"sellproject")));
			row.getCell("roomNumber").setValue(com.kingdee.bos.ui.face.UIRuleUtil
					.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"room")));
			row.getCell("fundName").setValue(
					com.kingdee.bos.ui.face.UIRuleUtil
					.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"chargeitem")));
			row.getCell("payFeeDate").setValue(inInfo.getArDate());
			row.getCell("receivableFee").setValue(inInfo.getArAmout());
			row.getCell("arrearageFee").setValue(checkarrearage(inInfo));
			
			} catch (DataAccessException e)
			{
				e.printStackTrace();
			} catch (BOSException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 填充预收款记录
	 * @param coll
	 * @param room
	 * @param sellProject
	 */
	private void fetchDepositTable(FDCReceiveBillEntryCollection coll,RoomInfo room,SellProjectInfo  sellProject){
		this.arrearsRecord.removeRows();
		this.arrearsRecord.checkParsed();
		arrearsRecord.getStyleAttributes().setLocked(false);
		arrearsRecord.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		arrearsRecord.getColumn("id").getStyleAttributes().setHided(true);
		
		if(coll!=null){
			for(int i=0;i<coll.size();i++) {
				FDCReceiveBillEntryInfo inInfo = coll.get(i);
				try
				{
						/////////去掉收款金额-红冲金额<0
						if(inInfo!=null) {
						if(inInfo.getAmount()!=null && inInfo.getCounteractAmount()!=null){
							 if(inInfo.getAmount().compareTo(inInfo.getCounteractAmount())<0){
								 continue;
							 }
						}
					
					IRow row = this.arrearsRecord.addRow();
					row.getCell("id").setValue(inInfo.getId().toString());
					row.getCell("projectName").setValue(sellProject);
					row.getCell("room").setValue(room);
					row.getCell("fundName").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"moneyDefine")));
					
					row.getCell("receiveFee").setValue(inInfo.getAmount());
					
					
					BigDecimal usableFee =inInfo.getAmount();
					if(inInfo.getAmount()!=null &&inInfo.getCounteractAmount()!=null){
						 usableFee =  inInfo.getAmount().subtract(inInfo.getCounteractAmount());
					}
					row.getCell("usableFee").setValue(usableFee);
					
				
				}
				} catch (DataAccessException e)
				{
					e.printStackTrace();
				} catch (BOSException e)
				{
					e.printStackTrace();
				}
			}	
		}
	}
	
	/**
	 * 填充特约服务
	 * @param coll
	 */
	private void fetchCustomerServiceSpecialTable(CmsTimeConferBillCollection coll){
		this.specialServer.checkParsed();
		specialServer.getStyleAttributes().setLocked(false);
		specialServer.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		specialServer.getColumn("id").getStyleAttributes().setHided(true);
		
		int seq =0; //line number  
		
		if(coll!=null){
			for(int i=0;i<coll.size();i++) {
				CmsTimeConferBillInfo inInfo = coll.get(i);
				seq++;
				try
				{
				if(inInfo!=null) {
					IRow row = this.specialServer.addRow();
					//row.setUserObject(inInfo);
					row.getCell("id").setValue(inInfo.getId().toString());
					row.getCell("proeprtyItem").setValue(com.kingdee.bos.ui.face.UIRuleUtil
							.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"sellproject")));
					row.getCell("roomNumber").setValue(com.kingdee.bos.ui.face.UIRuleUtil
							.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"room")));
					row.getCell("servicesProject").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"serviceItem")));
					row.getCell("appointments").setValue(inInfo.getAdvanceTime());
				}
				} catch (DataAccessException e)
				{
					e.printStackTrace();
				} catch (BOSException e)
				{
					e.printStackTrace();
				}
			}	
		}
		
		
		
	}
	
	/**
	 * 填充服务,	投诉  记录
	 * @param coll
	 */
	private void fetchCustomerServiceRecordTable(ServiceProcessBillCollection coll){
		this.serverRecord.removeRows();
		this.serverRecord.checkParsed();
		serverRecord.getStyleAttributes().setLocked(false);
		serverRecord.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		serverRecord.getColumn("id").getStyleAttributes().setHided(true);
		
		int seq =0; //line number  
		if(coll!=null){
			for(int i=0;i<coll.size();i++) {
				ServiceProcessBillInfo inInfo = coll.get(i);
				seq++;
				try
				{
					if(inInfo!=null) {
						IRow row = this.serverRecord.addRow();
						//row.setUserObject(inInfo);
						row.getCell("id").setValue(inInfo.getId().toString());
						row.getCell("propertyItem").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"sellproject")));
						row.getCell("roomNumber").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"room")));
						
						row.getCell("phone").setValue(inInfo.getLinkTel());
						row.getCell("callTime").setValue(inInfo.getRegisterTime());
						row.getCell("callcontent").setValue(inInfo.getRegisterNote());
				
						row.getCell("questionSort").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"stdQuestion")));
						
						row.getCell("dealSchedule").setValue(inInfo.getBillStatus());
						row.getCell("dealDescription").setValue(inInfo.getProcessNote());
						//row.getCell("receiveFee").setValue(inInfo.getProcessNote());  dealTime
						row.getCell("dealPerson").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"accepter")));
						
					}
				} catch (DataAccessException e)
				{
					e.printStackTrace();
				} catch (BOSException e)
				{
					e.printStackTrace();
				}
			}	
		}
	}
	
	private void fetchCustomerServiceComplaintTable(ServiceProcessBillCollection coll){
		this.complaintRecord.removeRows();
		this.complaintRecord.checkParsed();
		complaintRecord.getStyleAttributes().setLocked(false);
		complaintRecord.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		complaintRecord.getColumn("id").getStyleAttributes().setHided(true);
		
		int seq =0; //line number  
		if(coll!=null){
			for(int i=0;i<coll.size();i++) {
				ServiceProcessBillInfo inInfo = coll.get(i);
				seq++;
				try
				{
					if(inInfo!=null) {
						IRow row = this.complaintRecord.addRow();
						//row.setUserObject(inInfo);
						row.getCell("id").setValue(inInfo.getId().toString());
						row.getCell("propertyItem").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"sellproject")));
						row.getCell("roomNumber").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"room")));
						
						row.getCell("phone").setValue(inInfo.getLinkTel());
						row.getCell("callTime").setValue(inInfo.getRegisterTime());
						row.getCell("callContent").setValue(inInfo.getRegisterNote());
						//row.getCell("dealType").setValue(inInfo.getProcessType());
						row.getCell("questionSort").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"stdQuestion")));
						
						row.getCell("dealSchedule").setValue(inInfo.getBillStatus());
						row.getCell("dealDescription").setValue(inInfo.getProcessNote());
						//row.getCell("receiveFee").setValue(inInfo.getProcessNote());  dealTime
						row.getCell("dealPerson").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"accepter")));
						
					}
				} catch (DataAccessException e)
				{
					e.printStackTrace();
				} catch (BOSException e)
				{
					e.printStackTrace();
				}
			}	
		}
	}
	
	/**
	 * 根据 入伙类型 入伙状态 构造 客户类型
	 * @param type  入伙类型
	 * @param status入伙状态
	 * @return  客户类型  
	 */
	private Object  getCustomerType(JoinType type,JoinStatus status){
		if(JoinType.HOUSEMASTER.equals(type) && JoinStatus.JOIN.equals(status) ){
			return "现业主";
		}
		else if(JoinType.HOUSEMASTER.equals(type) && JoinStatus.WITHDRAW.equals(status) ){
			return "老业主";
		}else if(JoinType.LESSEE.equals(type) && JoinStatus.JOIN.equals(status) ){
			return "现租户";
		}else if(JoinType.LESSEE.equals(type) && JoinStatus.WITHDRAW.equals(status) ){
			return "老租户";
		}
		return null;
	}
	
	
	private BigDecimal checkarrearage(PPMARBaseInfo info){
			BigDecimal arAmount = info.getArAmout();
				if(arAmount==null) arAmount = new BigDecimal(0);
			BigDecimal derateAmount = info.getDerateAmount();
				if(derateAmount==null) derateAmount = new BigDecimal(0);
			BigDecimal payedAmount = info.getPayedAmount();
			    if(payedAmount==null) payedAmount = new BigDecimal(0);
			return arAmount.subtract(derateAmount).subtract(payedAmount);
	}	
	
	private void registerListener(){
		
		
	}
	
	/**
	 * 选择 房产信息联动 欠款记录 及预收款
	 * @param info
	 * @return
	 */
	protected void houseProperty_tableSelectChanged(KDTSelectEvent e)
			throws Exception
	{
		super.houseProperty_tableSelectChanged(e);

		IRow row = KDTableUtil.getSelectedRow(this.houseProperty);
		int index = this.houseProperty.getSelectManager().getActiveRowIndex();
		if(index>-1){
			RoomInfo room = (RoomInfo) row.getCell("room").getValue();
			initPropertyArrearage(this.editData, room);
			initPropertyDeposit(this.editData, room);
		}
	}
	
	/**
	 * 选择 特约服务 联动 服务记录 及投诉记录
	 */
	protected void specialServer_tableSelectChanged(KDTSelectEvent e)
			throws Exception
	{
		super.specialServer_tableSelectChanged(e);
		IRow row = KDTableUtil.getSelectedRow(this.specialServer);
		int index = this.specialServer.getSelectManager().getActiveRowIndex();
		if(index>-1){
			RoomInfo room = (RoomInfo) row.getCell("roomNumber").getValue();
			initCustomerServiceRecord(this.editData, room);
			initCustomerServiceComplaint(this.editData, room);
		}
	}
	
	protected void houseProperty_editStopped(KDTEditEvent e) throws Exception
	{
		if ((e.getValue() != null && e.getValue().equals(e.getOldValue()))
				|| (e.getValue() == null && e.getOldValue() == null)) {
			return;
		}
		int selectRow = e.getRowIndex();
		int selectCol = e.getColIndex();
		KDTable kDTable = (KDTable) e.getSource();

		IRow curRow = houseProperty.getRow(selectRow);
		
		int phoneCol = kDTable.getColumnIndex("phone");
		if (selectCol == phoneCol) {
			
			if(curRow.getCell("id")!=null){
				String phone= houseProperty.getRow(selectRow).getCell("phone").getValue().toString();
				updatePhoneToJoinVoucher(curRow.getCell("id").getValue().toString(),phone);
			}
		}
		
	}
	
	private void updatePhoneToJoinVoucher(String id,String phone){
		IJoinVoucher ijoin;
		try
		{
			ijoin = JoinVoucherFactory.getRemoteInstance();
			JoinVoucherInfo info= ijoin.getJoinVoucherInfo(new ObjectUuidPK(BOSUuid.read(id)));
			if(info!=null){
				info.setPhone(phone);
				ijoin.update(new ObjectUuidPK(info.getId()), info);
			}
		} catch (BOSException e)
		{
			e.printStackTrace();
		} catch (EASBizException e)
		{
			e.printStackTrace();
		} catch (UuidException e)
		{
			e.printStackTrace();
		}
	}
	/////////////////
	
	/**
	 * 新增功能：添加商机
	 */
	public void actionAddCommerceChance_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAddCommerceChance_actionPerformed(e);

		
		if (editData.getId() == null) {
			MsgBox.showInfo("请先保存客户信息!");
			return;
		}
		String idStr = editData.getId().toString();

		FDCCustomerInfo fdcCust = FDCCustomerFactory.getRemoteInstance()
				.getFDCCustomerInfo(new ObjectUuidPK(BOSUuid.read(idStr)));
		MoneySysTypeEnum latSysType = (MoneySysTypeEnum)getUIContext().get("latSysType");
		if(latSysType == null){
			latSysType = MoneySysTypeEnum.SalehouseSys;
		}
		UIContext uiContext = new UIContext(this);
		// 传入所属系统和客户
		if (fdcCust != null)
			uiContext.put("FdcCustomer", fdcCust);
		uiContext.put("SysType", latSysType);
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(CommerceChanceEditUI.class.getName(), uiContext,
							null, OprtState.ADDNEW);
			uiWindow.show();
		} catch (UIException ee) {
			ee.printStackTrace();
			this.handleException(ee);
			this.abort();
		}
	}
	
	
	//初始化f7企业性质树结构
	public static void initNatureEnterpriseF7(CoreUIObject owner, KDBizPromptBox box, String cuId) {
		GeneralKDPromptSelectorAdaptor selectorLisenterSupplier = new GeneralKDPromptSelectorAdaptor(
				box,F7NatureEnterpriseListUI.class.getName(),owner,"com.kingdee.eas.fdc.tenancy.app.F7NatureEnterpriseQuery");
		box.setSelector(selectorLisenterSupplier);
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",new Integer(1)));
		box.setEntityViewInfo(evi);
		box.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.F7NatureEnterpriseQuery");
	}
	//初始化f7业务范围树结构
	public static void initBusinessScopeF7(CoreUIObject owner, KDBizPromptBox box, String cuId) {
		GeneralKDPromptSelectorAdaptor selectorLisenterSupplier = new GeneralKDPromptSelectorAdaptor(
				box,F7BusinessScopeListUI.class.getName(),owner,"com.kingdee.eas.fdc.tenancy.app.F7BusinessScopeQuery");
		box.setSelector(selectorLisenterSupplier);
		box.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.F7BusinessScopeQuery");
	}
	//初始化f7合作模式树结构
	public static void initCooperateModeF7(CoreUIObject owner, KDBizPromptBox box, String cuId) {
		GeneralKDPromptSelectorAdaptor selectorLisenterSupplier = new GeneralKDPromptSelectorAdaptor(
				box,F7CooperateModeListUI.class.getName(),owner,"com.kingdee.eas.fdc.tenancy.app.F7CooperateModeQuery");
		box.setSelector(selectorLisenterSupplier);
		box.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.F7CooperateModeQuery");
	}
	public void actionInsider_actionPerformed(ActionEvent e) throws Exception {
		FDCCustomerInfo info = this.editData;
		Map ctxMap = new HashMap();
		ctxMap.put("fdcCustomerInfo", info);
		ctxMap.put(UIContext.OWNER, this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN)
			.create(InsiderApplicationNewEditUI.class.getName(), ctxMap,null,OprtState.ADDNEW);
		uiWindow.show();
	}
	
		/////////////////
	
	public void actionRev_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put(SHEReceivingBillEditUI.KEY_SELL_PROJECT, this.editData.getProject());
		uiContext.put(SHEReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.gathering);
		uiContext.put(SHEReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, new Boolean(true));	
		uiContext.put(SHEReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.customer);
		uiContext.put(SHEReceivingBillEditUI.KEY_SELL_FdcCustUser, this.editData);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(SHEReceivingBillEditUI.class.getName(), uiContext, null,"ADDNEW");
		uiWindow.show();			
	}
	
	public void actionRefund_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put(SHEReceivingBillEditUI.KEY_SELL_PROJECT, this.editData.getProject());
		uiContext.put(SHEReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.refundment);
		uiContext.put(SHEReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, new Boolean(true));	
		uiContext.put(SHEReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.customer);
		uiContext.put(SHEReceivingBillEditUI.KEY_SELL_FdcCustUser, this.editData);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(SHEReceivingBillEditUI.class.getName(), uiContext, null,"ADDNEW");
		uiWindow.show();		
	}
	
	
	/**
	 * 查询当前组织下所有的营销人员的ids
	 * xiaoa_liu
	 * @param treeNode
	 */
	private void getAllSaleManIds(TreeNode treeNode, Set saleManIds) {
		if (treeNode != null) {
			if (treeNode.isLeaf()) {
				DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
				if (thisNode.getUserObject() instanceof UserInfo) {
					UserInfo user = (UserInfo) thisNode.getUserObject();
					saleManIds.add(user.getId().toString());
				}
			} else {
				int childCount = treeNode.getChildCount();
				while (childCount > 0) {
					getAllSaleManIds(treeNode.getChildAt(childCount - 1),
							saleManIds);
					childCount--;
				}
			}
		}
	}
	/**
	 * 查询当前组织下所有的营销单元的ids
	 * xiaoa_liu
	 * @param treeNode
	 */
	private void getAllMarketingunitIds(TreeNode treeNode, Set marketingUnitIDs){
		if (treeNode != null) {
				DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
				if (thisNode.getUserObject() instanceof MarketingUnitInfo) {
					MarketingUnitInfo user = (MarketingUnitInfo) thisNode.getUserObject();
					marketingUnitIDs.add(user.getId().toString());
			    } else {
				int childCount = treeNode.getChildCount();
				while (childCount > 0) {
					getAllMarketingunitIds(treeNode.getChildAt(childCount - 1),marketingUnitIDs);
							
					childCount--;
				}
			}
		}
	}


	protected void boxCertificateName_itemStateChanged(ItemEvent e)
			throws Exception {
		/*CertifacateNameEnum certifacateName = (CertifacateNameEnum) boxCertificateName.getSelectedItem();
		if(null!= certifacateName && (!CertifacateNameEnum.IDCard.equals(certifacateName))){
			return;
		}*/
		/**
		 * update by renliang at 2011-3-2
		 */
		CertificateInfo  cetifiInfo = null;
		if(this.txtCertificate.getValue()!=null){
			cetifiInfo = (CertificateInfo) this.txtCertificate.getValue();
		}
		
		if(cetifiInfo==null){
			FDCMsgBox.showWarning(this,"请先选择证件名称！");
			SysUtil.abort();
		}

		if(null!= cetifiInfo && !cetifiInfo.getNumber().equals(CERTIFICATE_ID_CARD_NAME)){
			return;
		}
		String idcard = txtCertificateNumber.getText();	
		if(null!=idcard && idcard.trim().length()!=0 && ( idcard.trim().length()==15 || idcard.trim().length()==18 )){
			if(CertificateUtil.CardValidate(idcard)){
				String sex = CertificateUtil.getSexFromCard(idcard);
				boxSex.setSelectedItem(SexEnum.getEnum(sex));
			}
		}
	}


	protected void txtCertificateNumber_focusLost(FocusEvent e)
			throws Exception {
		/*CertifacateNameEnum certifacateName = (CertifacateNameEnum) boxCertificateName.getSelectedItem();
		if(null!= certifacateName && (!CertifacateNameEnum.IDCard.equals(certifacateName))){
			return;
		}*/
		/**
		 * update by renliang at 2011-3-2
		 */
		CertificateInfo  cetifiInfo = null;
		if(this.txtCertificate.getValue()!=null){
			cetifiInfo = (CertificateInfo) this.txtCertificate.getValue();
		}
		
		if(cetifiInfo==null){
			FDCMsgBox.showWarning(this,"请先选择证件名称！");
			this.txtCertificateNumber.setText(null);
			SysUtil.abort();
		}

		if(null!= cetifiInfo && !cetifiInfo.getNumber().equals(CERTIFICATE_ID_CARD_NAME)){
			this.txtNative.setText(null);
			this.dpDayOfbirth.setValue(null);
			this.boxSex.setSelectedItem(null);
			this.f7Country.setValue(null);
			return;
		}
		String idcard = txtCertificateNumber.getText();	
		
	
		if(null!=idcard && idcard.trim().length()!=0 && ( idcard.trim().length()==15 || idcard.trim().length()==18 )){
			if(CertificateUtil.CardValidate(idcard)){
				//带出性别
				String sex = CertificateUtil.getSexFromCard(idcard);
				boxSex.setSelectedItem(SexEnum.getEnum(sex));
				//带出籍贯
				String area = CertificateUtil.getAreaFromCard(idcard);
				this.txtNative.setText(area);
				//带出出生日期
				String datOfbirth = CertificateUtil.getBirthDateFromCard(idcard);
				this.dpDayOfbirth.setValue(GetDateFromString(datOfbirth));
				//默认带出中国
				fillDataInCountry();
			}else{
				FDCMsgBox.showWarning(this,"身份证号码不符合规则，请重新录入！");
				this.txtNative.setText(null);
				this.dpDayOfbirth.setValue(null);
				this.boxSex.setSelectedItem(null);
				this.f7Country.setValue(null);
				this.txtCertificateNumber.setText(null);
				SysUtil.abort();
			}
		}else{
			this.txtNative.setText(null);
			this.dpDayOfbirth.setValue(null);
			this.boxSex.setSelectedItem(null);
			this.f7Country.setValue(null);
			this.txtCertificateNumber.setText(null);
		}
		
	}

	private Date GetDateFromString(String date){
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateTemp = null;
		try {
			dateTemp = sf.parse(date);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		
		return dateTemp;
	}
	
	private void fillDataInCountry() {
		//String name = "中国";
		try {
			CountryInfo info = (CountryInfo)CountryFactory.getRemoteInstance().getValue("select id,number,name where name='"+CERTIFICATE_ID_CARD_NAME_CHINA+"'");
			if(info!=null){
				this.f7Country.setValue(info);
			}
		} catch (EASBizException e) {
			logger.error(e.getMessage());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		
	}
	protected void initOldData(IObjectValue dataObject) {
		// TODO Auto-generated method stub
		
	}


	public void actionXQAddLine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.tblXQ.addRow();
		XQEntryInfo entry=new XQEntryInfo();
		row.setUserObject(entry);
	}

	public void actionXQRemoveLine_actionPerformed(ActionEvent e)throws Exception {
		removeLine(this.tblXQ);
	}

	@Override
	protected void f7Province_dataChanged(DataChangeEvent e) throws Exception {
	    	EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			
			ProvinceInfo pinfo = (ProvinceInfo) f7Province.getValue();
			if(pinfo!=null){
				filter.getFilterItems().add(new FilterItemInfo("province.id" , pinfo.getId().toString()));
			}
			view.setFilter(filter);
			this.prmtCity.setEntityViewInfo(view);
	}
	
}