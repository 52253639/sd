/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordCollection;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceChanceEditUI;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.CustomerAdapterUI;
import com.kingdee.eas.fdc.sellhouse.client.CustomerCancelShareUI;
import com.kingdee.eas.fdc.sellhouse.client.CustomerEditUI;
import com.kingdee.eas.fdc.sellhouse.client.CustomerShareObjectUI;
import com.kingdee.eas.fdc.sellhouse.client.CustomerShareUI;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.TrackRecordEditUI;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 客户商机管控
 */
public class CustomerManagementUI extends AbstractCustomerManagementUI {

	SaleOrgUnitInfo saleOrg = SysContext.getSysContext().getCurrentSaleUnit();
	UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();

	boolean isFirstload =false;
	
	//用于判断 点击"问卷调查"按钮时，客户最后点击的是哪个分录(商机or跟进)
	boolean commerceFlag = false;
	boolean trackFlag = false;
	public final static String FROMTRACK = "fromtrack";
	
	public void actionClueQueryShow_actionPerformed(ActionEvent e)
			throws Exception {
		CommerceHelper.openTheUIWindow(this, CustomerShareObjectUI.class
				.getName(), null);
		this.refresh(e);
//		this.onLoad();
	}

	private static final Logger logger = CoreUIObject
			.getLogger(CustomerManagementUI.class);

	// private Set allProjectIds = null; //所包含所有的销售项目id

	/**
	 * output class constructor
	 */
	public CustomerManagementUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIName() {
		return CustomerEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCCustomerFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		Date date = FDCDateHelper.getPreMonth(new Date());
		dateBookedDate.setValue(date);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		super.onLoad();

		this.actionAddNew.setVisible(false);
		this.actionView.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionRefresh.setVisible(false);

		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuView.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionClueQueryShow.setVisible(true);// 线索查询
		this.actionClueQueryShow.setEnabled(true);// 线索查询
		this.actionImportantTrack.setEnabled(true);
		this.actionCancelImportantTrack.setEnabled(true);
		this.actionCustomerAdapter.setEnabled(true);
		this.actionCustomerShare.setEnabled(true);
		this.actionCustomerCancelShare.setEnabled(true);

//		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
//		if (!saleOrg.isIsBizUnit()) {
//			this.actionEdit.setEnabled(false);
//			this.actionRemove.setEnabled(false);
//			this.actionAddCustomer.setEnabled(false);
//			this.actionAddCommerceChance.setEnabled(false);
//			this.actionAddTrackRecord.setEnabled(false);
//			this.actionAddTotalAll.setEnabled(false);
//			this.actionClueQueryShow.setEnabled(false);// 线索查询
//			this.actionImportantTrack.setEnabled(false);
//			this.actionCancelImportantTrack.setEnabled(false);
//			this.actionCustomerAdapter.setEnabled(false);
//			this.actionCustomerShare.setEnabled(false);
//			this.actionCustomerCancelShare.setEnabled(false);
//		}

		// 初始化营销单元树
		this.treeMarketUnit.setModel(FDCTreeHelper
				.getMarketTree(this.actionOnLoad));
		this.treeMarketUnit.expandAllNodes(true, (TreeNode) this.treeMarketUnit
				.getModel().getRoot());
		this.treeMarketUnit.setSelectionRow(0);

		this.checkBoxAdapter.setSelected(true);
		this.checkBoxShared.setSelected(true);

		this.prmtSellProject.setEntityViewInfo(CommerceHelper
				.getPermitProjectView());

		this.tblCommerceChance.getStyleAttributes().setLocked(true);
		this.tblTrackRecord.getStyleAttributes().setLocked(true);

		setCustTrackStauts();

		// 设置主标中租赁或售楼状态的显示
		setColumnTrackPhaseShowStatus();
		
		//新增过滤条件 截至当前日期已经逾期多天未跟进客户 默认值
		this.checkBoxTime.setSelected(false);
		this.days.setEnabled(false);
		this.nowDate.setEnabled(false);
		this.days.setValue(new Integer(0));
		this.days.setMinimumValue(new Integer(0));
		this.days.setHorizontalAlignment(FDCClientHelper.NUMBERTEXTFIELD_ALIGNMENT);
		this.nowDate.setTimeEnabled(false);
		this.nowDate.setValue(new Date());
		//添加的按钮 问卷填写 可用
		this.actionQuestionPrint.setEnabled(true);
		
		this.isForShe.setSelected(false);
		this.checkBoxDisEnable.setSelected(false);
		this.comboCustomerType.setSelectedItem(null);
		this.checkBoxDisEnable.setSelected(true);
		this.dateBookedDate.setValue(null);
		this.dateBookedDateEnd.setValue(null);
		
		
		
		/**
		 * 重新设置系统维度
		 */
		setSysType();
	}
	
	private void setSysType(){
		this.comboSysType.removeItem(MoneySysTypeEnum.ManageSys);
	}
	
	/**
	 * 在原有基础上(即商机)，给跟进记录添加问卷调查按钮监听  11302010
	 */
	public void actionQuestionPrint_actionPerformed(ActionEvent e)
			throws Exception {
		if(commerceFlag && !trackFlag){
			if(this.tblCommerceChance.getRowCount()>0){
				if(this.tblCommerceChance.getSelectManager().getActiveRowIndex()>=0){
					//选择了商机 才能 使用问卷填写
					IRow row=tblCommerceChance.getRow(tblCommerceChance.getSelectManager().getActiveRowIndex());
					CommerceChanceInfo info=(CommerceChanceInfo)row.getUserObject();
					UIContext uiContext = new UIContext(this);
					uiContext.put("commerceChance", info);
					IUIFactory uiFactory = null;
					uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
					IUIWindow curDialog = uiFactory.create(QuestionPaperAnswerEditUI.class
							.getName(), uiContext, null, OprtState.ADDNEW);
					curDialog.show();
				}
			}
		}else if(trackFlag && !commerceFlag){
//			if(this.tblTrackRecord.getRowCount()>0){
				if(this.tblTrackRecord.getSelectManager().getActiveRowIndex()>=0){
					//选择了跟进 才能 使用问卷填写
					IRow row = tblTrackRecord.getRow(tblTrackRecord.getSelectManager().getActiveRowIndex());
					TrackRecordInfo info = (TrackRecordInfo)row.getUserObject();
					UIContext uiContext = new UIContext(this);
					uiContext.put("trackRecord", info);
					uiContext.put("from", FROMTRACK);
					IUIFactory uiFactory = null;
					uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
					IUIWindow curDialog = uiFactory.create(QuestionPaperAnswerEditUI.class
							.getName(), uiContext, null, OprtState.ADDNEW);
					curDialog.show();
				}
//			}
		}else if(!trackFlag && !commerceFlag){
			//选择了客户才能使用问卷填写
			if(this.tblMain.getSelectManager().getActiveRowIndex()>=0){
				IRow row=this.tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex());
				String idStr=row.getCell("id").getValue().toString();
				FDCCustomerInfo info = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(BOSUuid.read(idStr)));
				UIContext uiContext = new UIContext(this);

				//每个客户只有一个调查问卷，可以多次问卷，都是在上次基础上修改，判断客户是否有问卷：有则编辑，没有则新增
				EntityViewInfo questionPaperView=new EntityViewInfo();
				SelectorItemCollection questionPaperSic=new SelectorItemCollection();
				questionPaperSic.add("id");
				questionPaperSic.add("name");
				questionPaperSic.add("number");
				FilterInfo questionPaperFilter=new FilterInfo();
				questionPaperFilter.getFilterItems().add(new FilterItemInfo("customer.id",getSelectedKeyValue()));
				questionPaperView.setSelector(questionPaperSic);
				questionPaperView.setFilter(questionPaperFilter);
				questionPaperView.getSorter().add(new SorterItemInfo("createTime"));
				QuestionPaperAnswerCollection questionPaperCol = QuestionPaperAnswerFactory.getRemoteInstance().getQuestionPaperAnswerCollection(questionPaperView);
				QuestionPaperAnswerInfo questionPaperAnswerInfo = null;
				String questionPaperID = null;
				if(questionPaperCol != null && questionPaperCol.size()>0){
					int lastIndex = questionPaperCol.size();
					questionPaperID = questionPaperCol.get(lastIndex-1).getId().toString();
				}
				uiContext.put("fdcCustomer", info);
				IUIFactory uiFactory = null;
				uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
				if(questionPaperID != null){
					uiContext.put(UIContext.ID, questionPaperID); 
					IUIWindow curDialog = uiFactory.create(QuestionPaperAnswerEditUI.class
							.getName(), uiContext, null, OprtState.EDIT);
					curDialog.show();
				}else{
					IUIWindow curDialog = uiFactory.create(QuestionPaperAnswerEditUI.class
							.getName(), uiContext, null, OprtState.ADDNEW);
					curDialog.show();
				}
			}
		}
		
		
	}
	
	protected void checkBoxTime_actionPerformed(ActionEvent e) throws Exception {
		if(this.prmtSellProject.getValue()==null){
			MsgBox.showWarning("请选择项目维度!");
			this.checkBoxTime.setSelected(false);
			abort();
		}
		if(e.getSource()==this.checkBoxTime && !this.checkBoxTime.isSelected()){
			days.setEnabled(false);
			nowDate.setEnabled(false);
		}
		if(e.getSource()==this.checkBoxTime && this.checkBoxTime.isSelected()){
			days.setEnabled(true);
			nowDate.setEnabled(true);
		}
	}

	/**
	 * 查询所选节点下所有的营销人员的ids
	 * 
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
	 * 查询所选节点下所有的营销单元的ids
	 * 
	 * @param treeNode
	 */
	private void getAllMarketUnitIds(TreeNode treeNode, Set muIds) {
		if (treeNode != null) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
			if (thisNode.getUserObject() instanceof MarketingUnitInfo) {
				MarketingUnitInfo muInfo = (MarketingUnitInfo) thisNode
						.getUserObject();
				muIds.add(muInfo.getId().toString());
			}

			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllMarketUnitIds(treeNode.getChildAt(childCount - 1), muIds);
				childCount--;
			}
		}
	}

	private void getTheParentOrgUnitId(TreeNode treeNode, String orgId) {
		if (treeNode != null) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
			if (thisNode.getUserObject() instanceof OrgStructureInfo) {
				OrgStructureInfo orgInfo = (OrgStructureInfo) thisNode
						.getUserObject();
				orgId = orgInfo.getId().toString();
			} else if (thisNode.getParent() != null) {
				getTheParentOrgUnitId(thisNode.getParent(), orgId);
			}
		}

	}

	protected void comboSysType_itemStateChanged(ItemEvent e) throws Exception {
		super.comboSysType_itemStateChanged(e);

		setCustTrackStauts();
	}

	// 根据系统纬度设置客户跟进状态
	private void setCustTrackStauts() {
		MoneySysTypeEnum latSysType = (MoneySysTypeEnum) this.comboSysType
				.getSelectedItem();
		//this.comboSysType.remove(2);
		this.comboCustCommStatus.removeAllItems();
		this.comboCustCommStatus.addItem("全部");
		this.comboCustCommStatus.addItem("为空");
		this.comboCustCommStatus.addItem(CommerceStatusEnum.Clew);
		this.comboCustCommStatus.addItem(CommerceStatusEnum.Latency);
		this.comboCustCommStatus.addItem(CommerceStatusEnum.Intent);
		if (latSysType.equals(MoneySysTypeEnum.SalehouseSys)) {
			this.comboCustCommStatus.addItem(CommerceStatusEnum.Sincerity);
			this.comboCustCommStatus.addItem(CommerceStatusEnum.SaleDestine);
			this.comboCustCommStatus.addItem(CommerceStatusEnum.SalePurchase);
			this.comboCustCommStatus.addItem(CommerceStatusEnum.SaleSign);
		} else if (latSysType.equals(MoneySysTypeEnum.TenancySys)) {
			this.comboCustCommStatus.addItem(CommerceStatusEnum.TenancySign);
		}
		this.comboCustCommStatus.addItem(CommerceStatusEnum.Finish);
	}

	protected void btnSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.btnSubmit_actionPerformed(e);
		//当重新查询时将商机和跟进表删除add by warship
		this.tblCommerceChance.removeRows();
		this.tblTrackRecord.removeRows();
		setColumnTrackPhaseShowStatus();

		TreeNode marketNode = (TreeNode) this.treeMarketUnit
				.getLastSelectedPathComponent();
		if (marketNode == null)
			return;
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) marketNode;
		if (!(thisNode.getUserObject() instanceof UserInfo)
				&& !(thisNode.getUserObject() instanceof MarketingUnitInfo)) {
			MsgBox.showInfo("所选树节点必须是销售顾问或营销单元节点!");
			return;
		}

		this.execQuery();
	}

	private void setColumnTrackPhaseShowStatus() {
		MoneySysTypeEnum latSysType = (MoneySysTypeEnum) this.comboSysType
				.getSelectedItem(); // 系统纬度
		if (latSysType.equals(MoneySysTypeEnum.SalehouseSys)) {
			this.tblMain.getColumn("saleTrackPhase").getStyleAttributes()
					.setHided(false);
			this.tblMain.getColumn("tenancyTrackPhase").getStyleAttributes()
					.setHided(true);
			this.tblMain.getColumn("tradeTime").getStyleAttributes().setHided(
					false);
			this.tblMain.getColumn("tenTradeTime").getStyleAttributes()
					.setHided(true);
		} else if (latSysType.equals(MoneySysTypeEnum.TenancySys)) {
			this.tblMain.getColumn("saleTrackPhase").getStyleAttributes()
					.setHided(true);
			this.tblMain.getColumn("tenancyTrackPhase").getStyleAttributes()
					.setHided(false);
			this.tblMain.getColumn("tradeTime").getStyleAttributes().setHided(
					true);
			this.tblMain.getColumn("tenTradeTime").getStyleAttributes()
					.setHided(false);
		}
		this.tblMain.repaint();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		
		CustomerTypeEnum custType = this.comboCustomerType.isEnabled() ? (CustomerTypeEnum) this.comboCustomerType
				.getSelectedItem()
				: null; // 客户类型
		Date bookedDateBegin = this.dateBookedDate.isEnabled() ? (Date) this.dateBookedDate
				.getValue()
				: null; // 登记开始日期
		Date bookedDateEnd = this.dateBookedDateEnd.isEnabled() ? (Date) this.dateBookedDateEnd
				.getValue()
				: null; // 登记结束日期
		if (bookedDateEnd != null)
			bookedDateEnd = FDCDateHelper.getNextDay(bookedDateEnd);

		String custName = (String) this.txtCustomerName.getText(); // 客户名称
		String custPhone = (String) this.txtPhone.getText(); // 客户电话
		String custCetificate = (String) this.txtCertificateNumber.getText(); // 证件号码
		boolean includeAdapter = this.checkBoxAdapter.isSelected(); // 是否包含转接
		boolean includeShared = this.checkBoxShared.isSelected(); // 是否包换共享
		boolean includeDisEnable = this.checkBoxDisEnable.isSelected(); // 是否包换作废的
		boolean isForShe = this.isForShe.isSelected(); // 拥有售楼属性
		boolean isForTen = this.isForTen.isSelected(); // 拥有租赁属性
		boolean isForPpm = this.isForPpm.isSelected(); // 拥有物业属性

		boolean isAll = this.ckbAll.isSelected();
		CommerceStatusEnum commStatus = null; // 商机状态 saleTrackPhase
		// tenancyTrackPhase
		Object commerceStatusObject = this.comboCustCommStatus
				.getSelectedItem();
		if (commerceStatusObject instanceof CommerceStatusEnum)
			commStatus = (CommerceStatusEnum) commerceStatusObject;

		// 统计所选节点下的所有营销人员
		Set saleManIds = new HashSet();
		saleManIds.add("null");
		TreeNode marketNode = (TreeNode) this.treeMarketUnit
				.getLastSelectedPathComponent();
		getAllSaleManIds(marketNode, saleManIds);
		
		SellProjectInfo latSellPro = (SellProjectInfo) this.prmtSellProject
				.getValue(); // 项目纬度
		MoneySysTypeEnum latSysType = (MoneySysTypeEnum) this.comboSysType
				.getSelectedItem(); // 系统纬度

		viewInfo = (EntityViewInfo) this.mainQuery.clone();
		FilterInfo filter = new FilterInfo();
		if (custType != null)
			filter.getFilterItems().add(
					new FilterItemInfo("customerType", custType.getValue()));
		else
			filter.getFilterItems().add(
					new FilterItemInfo("customerType", null,
							CompareType.NOTEQUALS));
		String maskString = "#0";
		int maskNum = 1;
		if (!isAll) {
			if (!isForShe && !isForTen && !isForPpm) {
				filter.getFilterItems().add(
						new FilterItemInfo("isForSHE", Boolean.valueOf(false)));
				filter.getFilterItems().add(
						new FilterItemInfo("isForTen", Boolean.valueOf(false)));
				filter.getFilterItems().add(
						new FilterItemInfo("isForPPM", Boolean.valueOf(false)));
				maskString += " and #1 and #2 and #3";
//				maskString += " or #1 or #2 or #3";
				maskNum += 3;
			} else {
				if (isForShe) {
					filter.getFilterItems().add(
							new FilterItemInfo("isForSHE", Boolean.valueOf(true)));
					maskString += " and #" + maskNum;
					maskNum++;
				}
				if (isForTen) {
					filter.getFilterItems().add(
							new FilterItemInfo("isForTen", Boolean.valueOf(true)));
					maskString += " and #" + maskNum;
					maskNum++;
				}
				if (isForPpm) {
					filter.getFilterItems().add(
							new FilterItemInfo("isForPPM", Boolean.valueOf(true)));
					maskString += " and #" + maskNum;
					maskNum++;
				}
			}
			if (bookedDateBegin != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("createTime", bookedDateBegin,
								CompareType.GREATER_EQUALS));
				maskString += " and #" + maskNum;
				maskNum++;
			}
			if (bookedDateEnd != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("createTime", bookedDateEnd,
								CompareType.LESS));
				maskString += " and #" + maskNum;
				maskNum++;
			}
			if (custName != null && !custName.trim().equals("")) {
				filter.getFilterItems().add(
						new FilterItemInfo("name", "%" + custName.trim() + "%",
								CompareType.LIKE));
				maskString += " and #" + maskNum;
				maskNum++;
			}
			if (custPhone != null && !custPhone.trim().equals("")) {
				filter.getFilterItems().add(
						new FilterItemInfo("phone", "%" + custPhone.trim()
								+ "%", CompareType.LIKE));
				maskString += " and #" + maskNum;
				maskNum++;
			}
			if (custCetificate != null && !custCetificate.trim().equals("")) {
				filter.getFilterItems()
						.add(
								new FilterItemInfo("certificateNumber", "%"
										+ custCetificate.trim() + "%",
										CompareType.LIKE));
				maskString += " and #" + maskNum;
				maskNum++;
			}
			if (!includeDisEnable) {
				filter.getFilterItems().add(
						new FilterItemInfo("isEnabled", new Integer(1)));
				maskString += " and #" + maskNum;
				maskNum++;
			}
		}

		/*if (latSellPro == null) {
			String trackPhaseStr = "";
			if (latSysType.equals(MoneySysTypeEnum.SalehouseSys))
				trackPhaseStr = "saleTrackPhase";
			else if (latSysType.equals(MoneySysTypeEnum.TenancySys))
				trackPhaseStr = "tenancyTrackPhase";
			if (commStatus != null) {
				filter.getFilterItems()
						.add(
								new FilterItemInfo(trackPhaseStr, commStatus
										.getValue()));
				maskString += " and #" + maskNum;
				maskNum++;
			} else {
				if (commerceStatusObject instanceof String) {
					String comStatusStr = (String) commerceStatusObject;
					if (comStatusStr.equals("为空")) {
						filter.getFilterItems().add(
								new FilterItemInfo(trackPhaseStr, null,
										CompareType.EQUALS));
						maskString += " and #" + maskNum;
						maskNum++;
					}
				}
			}
		}else{
			if(!isAll){
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", latSellPro.getId().toString(),
								CompareType.EQUALS));
				maskString += " and #" + maskNum;
				maskNum++;
			}
		}*/
		if (latSellPro != null){
			//if(!isAll){
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", latSellPro.getId().toString(),
								CompareType.EQUALS));
				maskString += " and #" + maskNum;
				maskNum++;
			//}
		}
		String trackPhaseStr = "";
		if (latSysType.equals(MoneySysTypeEnum.SalehouseSys)){
			trackPhaseStr = "saleTrackPhase";
		}else if (latSysType.equals(MoneySysTypeEnum.TenancySys)){
			trackPhaseStr = "tenancyTrackPhase";
		}
		
		if (commStatus != null) {
			filter.getFilterItems()
					.add(
							new FilterItemInfo(trackPhaseStr, commStatus
									.getValue()));
			maskString += " and #" + maskNum;
			maskNum++;
		} else {
			if (commerceStatusObject instanceof String) {
				String comStatusStr = (String) commerceStatusObject;
				if (comStatusStr.equals("为空")) {
					filter.getFilterItems().add(
							new FilterItemInfo(trackPhaseStr, null,
									CompareType.EQUALS));
					maskString += " and #" + maskNum;
					maskNum++;
				}
			}
		}

		// 查询时，只提供选择营销顾问和营销单元节点的情况
		// 1.选择营销顾问时 不考虑组织共享和营销单元共享
		// 2.选择营销单元时，不考虑组织共享 。只需要考虑下属所有营销顾问 + 下属所有营销单元的共享
		boolean addMarUnitShare = false;
		// String orgIds = "null"; //当前所选节点往上的第一个组织id
		Set muSetIds = new HashSet(); // 当前所选节点往下的所有营销单元
		muSetIds.add("null");
		if (marketNode != null) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) marketNode;
			if (thisNode.getUserObject() instanceof MarketingUnitInfo) { // 当前节点选的营销单元
				addMarUnitShare = true;
				// selectedMuInfo = (MarketingUnitInfo)thisNode.getUserObject();
				getAllMarketUnitIds(thisNode, muSetIds);
				// getTheParentOrgUnitId(thisNode,orgIds);
			}
		}

		if (includeShared) {
			// 包含共享 + 包含转接
			if (includeAdapter) {
				filter.getFilterItems().add(
						new FilterItemInfo("salesman.id", saleManIds,
								CompareType.INCLUDE));
				filter.getFilterItems().add(
						new FilterItemInfo("shareSellerList.seller.id",
								saleManIds, CompareType.INCLUDE));
				if (addMarUnitShare) {
					// filter.getFilterItems().add(new
					// FilterItemInfo("shareSellerList.orgUnit.id", orgIds));
					filter.getFilterItems().add(
							new FilterItemInfo(
									"shareSellerList.marketingUnit.id",
									muSetIds, CompareType.INCLUDE));
					maskString += " and (#" + maskNum + " or #" + (maskNum + 1)
							+ " or #" + (maskNum + 2) + ")";
					maskNum += 3;
				} else {
					maskString += " and (#" + maskNum + " or #" + (maskNum + 1)
							+ ")";
					maskNum += 2;
				}
			} else { // 包含共享 + 不包含转接
				filter.getFilterItems().add(
						new FilterItemInfo("salesman.id", saleManIds,
								CompareType.INCLUDE));
				filter.getFilterItems().add(
						new FilterItemInfo("creator.id", saleManIds,
								CompareType.INCLUDE));
				filter.getFilterItems().add(
						new FilterItemInfo("shareSellerList.seller.id",
								saleManIds, CompareType.INCLUDE));
				if (addMarUnitShare) {
					// filter.getFilterItems().add(new
					// FilterItemInfo("shareSellerList.orgUnit.id", orgIds));
					filter.getFilterItems().add(
							new FilterItemInfo(
									"shareSellerList.marketingUnit.id",
									muSetIds, CompareType.INCLUDE));
					maskString += " and ((#" + maskNum + " and #"
							+ (maskNum + 1) + ") or #" + (maskNum + 2)
							+ " or #" + (maskNum + 3) + ")";
					maskNum += 4;
				} else {
					maskString += " and ((#" + maskNum + " and #"
							+ (maskNum + 1) + ") or #" + (maskNum + 2) + ")";
					maskNum += 3;
				}
			}
		} else {
			// 不包含共享 + 包含转接
			filter.getFilterItems().add(
					new FilterItemInfo("salesman.id", saleManIds,
							CompareType.INCLUDE));
			maskString += " and #" + maskNum;
			maskNum++;
			// 不包含共享 + 不包含转接 = 所属和创建人都必须是自己
			if (!includeAdapter) {
				filter.getFilterItems().add(
						new FilterItemInfo("creator.id", saleManIds,
								CompareType.INCLUDE));
				maskString += " and #" + maskNum;
				maskNum++;
			}
		}

		filter.setMaskString(maskString);

		// 需要增加项目隔离
		try {
			FilterInfo addSpFilter = addSeparateBySellProject((DefaultMutableTreeNode) marketNode);
			filter.mergeFilter(addSpFilter, "and");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (viewInfo.getFilter() == null)
			viewInfo.setFilter(filter);
		else {
			try {
				viewInfo.getFilter().mergeFilter(filter, "AND");
			} catch (BOSException e) {
				e.printStackTrace();
				this.handleException(e);
				this.abort();
			}
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	// 若选择的节点非当前用户，则必须增加项目隔离
	private FilterInfo addSeparateBySellProject(DefaultMutableTreeNode thisNode)
			throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();
		String spFilterString = "";
		if (thisNode == null
				|| thisNode.getUserObject() instanceof OrgStructureInfo) {
			spFilterString = CommerceHelper.getPermitProjectIdSql(userInfo);
		} else if (thisNode.getUserObject() instanceof MarketingUnitInfo) {// 选择单元或人节点的情况
			MarketingUnitInfo marketingInfo = (MarketingUnitInfo) thisNode
					.getUserObject();
			spFilterString = "select FSellProjectID from T_TEN_MarketingUnitSellProject where fHeadId ='"
					+ marketingInfo.getId().toString() + "'";
		} else if (thisNode.getUserObject() instanceof UserInfo) {
			UserInfo nodeUser = (UserInfo) thisNode.getUserObject();
			if (!nodeUser.getId().toString()
					.equals(userInfo.getId().toString())) {
				MarketingUnitInfo marketingUnit = (MarketingUnitInfo) ((DefaultKingdeeTreeNode) thisNode
						.getParent()).getUserObject();
				spFilterString = "select FSellProjectID from T_TEN_MarketingUnitSellProject where fHeadId ='"
						+ marketingUnit.getId().toString() + "'";
			}
		}

		if (!spFilterString.equals(""))
			filter.getFilterItems().add(
					new FilterItemInfo("project.id", spFilterString,
							CompareType.INNER));
		return filter;
	}

	protected void ckbAll_actionPerformed(ActionEvent e) throws Exception {

		if (this.ckbAll.isSelected()) {
			comboCustomerType.setSelectedItem(null);
			dateBookedDate.setValue(null);
			dateBookedDateEnd.setValue(null);
			
			comboCustomerType.setEnabled(false);
			this.dateBookedDate.setEnabled(false);
			this.dateBookedDateEnd.setEnabled(false);

			this.txtCustomerName.setText("");
			this.txtCustomerName.setEnabled(false);

			this.txtCertificateNumber.setText("");
			this.txtCertificateNumber.setEnabled(false);

			this.txtPhone.setText("");
			this.txtPhone.setEnabled(false);

			checkBoxAdapter.setSelected(true);
			checkBoxShared.setSelected(true);
			checkBoxDisEnable.setSelected(true); 
			
			checkBoxAdapter.setEnabled(false);
			checkBoxShared.setEnabled(false);
			checkBoxDisEnable.setEnabled(false);
			
			isForShe.setSelected(false);
			isForTen.setSelected(false);
			isForPpm.setSelected(false);
			
			isForShe.setEnabled(false);
			isForTen.setEnabled(false);
			isForPpm.setEnabled(false);

			this.prmtSellProject.setValue(null);
			this.prmtSellProject.setEnabled(false);
			
			comboCustCommStatus.setEnabled(false);
			
//			--- 
			
		} else {
			comboCustomerType.setEnabled(true);

			this.dateBookedDate.setEnabled(true);
			this.dateBookedDateEnd.setEnabled(true);
			this.txtCustomerName.setEnabled(true);

			this.txtCertificateNumber.setEnabled(true);
			this.txtPhone.setEnabled(true);

			checkBoxAdapter.setEnabled(true);
			checkBoxShared.setEnabled(true);
			checkBoxDisEnable.setEnabled(true);

			isForShe.setEnabled(true);
			isForTen.setEnabled(true);
			isForPpm.setEnabled(true);
			this.prmtSellProject.setEnabled(true);
			comboCustCommStatus.setEnabled(true);
		}

	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected void execQuery() {
		
		if(checkBoxTime.isSelected()){
			if(this.nowDate.getValue()==null){
				MsgBox.showWarning("截至时间不能为空！");
				abort();
			}
			if(this.days.getIntegerValue()==null){
				MsgBox.showWarning("逾期天数不能为空！");
				abort();
			}
		}
//		if (this.tblMain.getRowCount() == 0)
//			return;
		SellProjectInfo latSellPro = (SellProjectInfo) this.prmtSellProject
		.getValue(); // 项目纬度
	
		boolean isAll = this.ckbAll.isSelected();
		if(!isAll){
			// 针对所选的项目，更新所查出来的客户的跟进状态
			if (latSellPro == null){
				MsgBox.showWarning("项目纬度不能为空！");
				abort();			
			}
		}
		

		// 根据项目纬度刷新客户的跟进状态
		MoneySysTypeEnum latSysType = (MoneySysTypeEnum) this.comboSysType
				.getSelectedItem(); // 系统纬度
		
		Date bookedDateBegin = (Date) this.dateBookedDate.getValue(); // 登记开始日期
		Date bookedDateEnd = (Date) this.dateBookedDateEnd.getValue(); // 登记结束日期
		if (bookedDateEnd != null)
			bookedDateEnd = FDCDateHelper.getNextDay(bookedDateEnd);

		Set custIdSet = new HashSet();
		Map userMap = new HashMap();
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow thisRow = this.tblMain.getRow(i);
			String idStr = (String) thisRow.getCell("id").getValue();
			custIdSet.add(idStr);
			userMap.put(idStr, thisRow);
			if (latSellPro != null) {
				if (latSysType.equals(MoneySysTypeEnum.SalehouseSys))
					thisRow.getCell("saleTrackPhase").setValue(null);
				else if (latSysType.equals(MoneySysTypeEnum.TenancySys))
					thisRow.getCell("tenancyTrackPhase").setValue(null);
			}
		}

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("head.id"));
		selector.add(new SelectorItemInfo("sysType"));
		selector.add(new SelectorItemInfo("trackType"));
		selector.add(new SelectorItemInfo("trackResult"));
		view.setSelector(selector);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", custIdSet, CompareType.INCLUDE));
		if(!isAll){
			if (latSellPro != null)
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", latSellPro.getId()
								.toString()));
		}
		if (bookedDateBegin != null)
			filter.getFilterItems().add(
					new FilterItemInfo("eventDate", bookedDateBegin,
							CompareType.GREATER_EQUALS));
		if (bookedDateEnd != null)
			filter.getFilterItems().add(
					new FilterItemInfo("eventDate", bookedDateEnd,
							CompareType.LESS));
		if (latSysType.equals(MoneySysTypeEnum.SalehouseSys))
			filter.getFilterItems().add(
					new FilterItemInfo("sysType",
							MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		else if (latSysType.equals(MoneySysTypeEnum.TenancySys))
			filter.getFilterItems().add(
					new FilterItemInfo("sysType",
							MoneySysTypeEnum.TENANCYSYS_VALUE));
		view.setFilter(filter);
		try {
			TrackRecordCollection trackColl = TrackRecordFactory
					.getRemoteInstance().getTrackRecordCollection(view);
			for (int i = 0; i < trackColl.size(); i++) {
				TrackRecordInfo trackInfo = trackColl.get(i);
				CommerceStatusEnum commStatus = TrackRecordEditUI
						.convertToCommStatus(trackInfo.getSysType(), trackInfo
								.getTrackType(), trackInfo.getTrackResult());
				if (commStatus != null) {
					if (latSysType.equals(MoneySysTypeEnum.SalehouseSys)) {
						IRow thisRow = (IRow) userMap.get(trackInfo.getHead()
								.getId().toString());
						CommerceStatusEnum custSaleTrack = null;
						Object saleTrackObject = thisRow.getCell(
								"saleTrackPhase").getValue();
						if (saleTrackObject != null
								&& saleTrackObject instanceof CommerceStatusEnum)
							custSaleTrack = (CommerceStatusEnum) saleTrackObject;
						if (TrackRecordEditUI.compareCommerceStatus(trackInfo
								.getSysType(), commStatus, custSaleTrack) > 0) {
							thisRow.getCell("saleTrackPhase").setValue(
									commStatus);
						}// /
					} else if (latSysType.equals(MoneySysTypeEnum.TenancySys)) {
						IRow thisRow = (IRow) userMap.get(trackInfo.getHead()
								.getId().toString());
						CommerceStatusEnum custTenancyTrack = null;
						Object tenTrackObject = thisRow.getCell(
								"tenancyTrackPhase").getValue();
						if (tenTrackObject != null
								&& tenTrackObject instanceof CommerceStatusEnum)
							custTenancyTrack = (CommerceStatusEnum) tenTrackObject;

						if (TrackRecordEditUI.compareCommerceStatus(trackInfo
								.getSysType(), commStatus, custTenancyTrack) > 0) {
							thisRow.getCell("tenancyTrackPhase").setValue(
									commStatus);
						}
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
			this.handleException(e);
			this.abort();
		}

		
		
		
		// 如果查询的客户跟进状态不为全部的话，要删除不符合条件的记录
		/*Object commerceStatusObject = this.comboCustCommStatus
				.getSelectedItem();
		if (commerceStatusObject != null) {
			if (!(commerceStatusObject instanceof String && ((String) commerceStatusObject)
					.equals("全部"))) {
				CommerceStatusEnum thisComStatus = null;
				boolean onlyShowNull = false;
				if (commerceStatusObject instanceof String
						&& ((String) commerceStatusObject).equals("为空")) {
					onlyShowNull = true;
				} else if (commerceStatusObject instanceof CommerceStatusEnum) {
					thisComStatus = (CommerceStatusEnum) commerceStatusObject;
				}
				String cellName = "saleTrackPhase";
				if (latSysType.equals(MoneySysTypeEnum.TenancySys))
					cellName = "tenancyTrackPhase";
				int rowCount = this.tblMain.getRowCount();
				for (int i = rowCount - 1; i >= 0; i--) {
					IRow thisRow = this.tblMain.getRow(i);
					Object trackObject = thisRow.getCell(cellName).getValue();
					if (onlyShowNull && trackObject != null) {
						this.tblMain.removeRow(i);
					} else if (thisComStatus != null) {
						if ((trackObject != null && !thisComStatus
								.equals(trackObject))
								|| trackObject == null)
							this.tblMain.removeRow(i);
					}
				}
			}
		}*/
		
		super.execQuery();
		
		// 新增加了过滤条件  为了不影响前面逻辑   直接从最后的kdtable里去掉不符合条件的
		//逻辑条件为   当前系统日期-最新跟进=差值（为负数时直接过滤掉）大于或等于逾期天数    的数据
		if(checkBoxTime.isSelected() && tblMain.getRowCount()>0){
			for(int i=0;i<tblMain.getRowCount();i++){
				if(tblMain.getCell(i, "lastTrackDate").getValue()!=null){
					Date lastTrackDate=FDCDateHelper.getDayDate((Date)tblMain.getCell(i, "lastTrackDate").getValue());
					Date now=FDCDateHelper.getDayDate((Date)this.nowDate.getValue());
					long l=(now.getTime()-lastTrackDate.getTime())/(24*60*60*1000);
					if(l<this.days.getIntegerValue().longValue()){
						tblMain.removeRow(i);
						i--;
					}

				}
			}
		}

	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	public void actionAddCommerceChance_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAddCommerceChance_actionPerformed(e);

		String selectId = this.getSelectedKeyValue();
		if (selectId == null) {
			MsgBox.showInfo("请先选择要添加商机的客户!");
			return;
		}

		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		String idStr = (String) this.tblMain.getRow(selectRows[0])
				.getCell("id").getValue();
		if (idStr == null)
			return;

		FDCCustomerInfo fdcCust = FDCCustomerFactory.getRemoteInstance()
				.getFDCCustomerInfo(new ObjectUuidPK(BOSUuid.read(idStr)));
		MoneySysTypeEnum latSysType = (MoneySysTypeEnum) this.comboSysType
				.getSelectedItem(); // 系统纬度

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

	public void actionAddCustomer_actionPerformed(ActionEvent e)
			throws Exception {

		super.actionAddCustomer_actionPerformed(e);
		UIContext uiContext = new UIContext(this);
		String opera = OprtState.ADDNEW;
		MoneySysTypeEnum latSysType = (MoneySysTypeEnum) comboSysType.getSelectedItem();
		uiContext.put("latSysType", latSysType);
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(CustomerEditUI.class.getName(), uiContext, null,
							opera);
			uiWindow.show();
		} catch (UIException ex) {
			ex.printStackTrace();
			SysUtil.abort();
		}
	}

	public void actionAddTrackRecord_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAddTrackRecord_actionPerformed(e);

		String selectId = this.getSelectedKeyValue();
		if (selectId == null) {
			MsgBox.showInfo("请先选择要添加跟进的客户!");
			return;
		}

		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		String idStr = (String) this.tblMain.getRow(selectRows[0])
				.getCell("id").getValue();
		if (idStr == null)
			return;
		FDCCustomerInfo fdcCust = FDCCustomerFactory.getRemoteInstance()
				.getFDCCustomerInfo(new ObjectUuidPK(BOSUuid.read(idStr)));
		MoneySysTypeEnum latSysType = (MoneySysTypeEnum) this.comboSysType
				.getSelectedItem(); // 系统纬度

		UIContext uiContext = new UIContext(this);
		// 传入所属系统和客户
		if (fdcCust != null)
			uiContext.put("FdcCustomer", fdcCust);
		uiContext.put("MoneySysTypeEnum", latSysType);
		// 选择的商机
		int[] selectComRows = KDTableUtil
				.getSelectedRows(this.tblCommerceChance);
		if (selectComRows.length > 0) {
			CommerceChanceInfo comInfo = (CommerceChanceInfo) this.tblCommerceChance
					.getRow(selectComRows[0]).getUserObject();
			uiContext.put("CommerceChance", comInfo);

			if (comInfo != null && comInfo.getSellProject() != null) {
				SelectorItemCollection itemColl = new SelectorItemCollection();
				itemColl.add(new SelectorItemInfo("name"));
				itemColl.add(new SelectorItemInfo("number"));
				itemColl.add(new SelectorItemInfo("id"));
				BOSUuid id = comInfo.getSellProject().getId();
				SellProjectInfo info = SellProjectFactory.getRemoteInstance()
						.getSellProjectInfo(new ObjectUuidPK(id), itemColl);
				uiContext.put("SellProject", info);
			}
		}

		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(TrackRecordEditUI.class.getName(), uiContext, null,
							OprtState.ADDNEW);
			uiWindow.show();
		} catch (UIException ee) {
			ee.printStackTrace();
			this.handleException(ee);
			this.abort();
		}

	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {

		if (e.getButton() == 1) {
			if (e.getType() != 1)
				return;

			if (e.getClickCount() == 2)
				super.tblMain_tableClicked(e);
		}

	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		MoneySysTypeEnum latSysType = (MoneySysTypeEnum) comboSysType
				.getSelectedItem();
		uiContext.put("latSysType", latSysType);
		super.prepareUIContext(uiContext, e);
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);

		String selectId = this.getSelectedKeyValue();
		if (selectId == null) {
			return;
		}
		commerceFlag = false;
		trackFlag = false;
		
		MoneySysTypeEnum latSysType = (MoneySysTypeEnum) this.comboSysType
				.getSelectedItem(); // 系统纬度
		String type = null;
		if (latSysType.equals(MoneySysTypeEnum.SalehouseSys)) {
			type = "Sale";
		} else if (latSysType.equals(MoneySysTypeEnum.TenancySys)) {
			type = "Tenancy";
		}else if (latSysType.equals(MoneySysTypeEnum.ManageSys)) {
			type = "ManageSys";
		}
		initTrackRecordTable(selectId, type);
		initCommerceChanceRecord(selectId, type);
	}

	protected void tblCommerceChance_tableClicked(KDTMouseEvent e)
			throws Exception {
		super.tblCommerceChance_tableClicked(e);
		
		commerceFlag = true;
		trackFlag = false;
		
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if (e.getType() != 1)
				return;
			CommerceChanceInfo commInfo = (CommerceChanceInfo) this.tblCommerceChance
					.getRow(e.getRowIndex()).getUserObject();

			if (commInfo != null) {
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, commInfo.getId().toString());
				// uiContext.put("ActionView", "OnlyView");
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.MODEL).create(
						CommerceChanceEditUI.class.getName(), uiContext, null,
						OprtState.VIEW);
				uiWindow.show();
			}
		}
	}

	protected void tblTrackRecord_tableClicked(KDTMouseEvent e)
			throws Exception {
		super.tblTrackRecord_tableClicked(e);

		trackFlag = true;
		commerceFlag = false;
		
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if (e.getType() != 1)
				return;
			TrackRecordInfo trackInfo = (TrackRecordInfo) this.tblTrackRecord
					.getRow(e.getRowIndex()).getUserObject();
			if (trackInfo != null) {
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, trackInfo.getId().toString());
				uiContext.put("ActionView", "OnlyView");
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.MODEL).create(
						TrackRecordEditUI.class.getName(), uiContext, null,
						OprtState.VIEW);
				uiWindow.show();
			}
		}
	}

	protected void checkBoxAdapter_itemStateChanged(ItemEvent e)
			throws Exception {
		super.checkBoxAdapter_itemStateChanged(e);

		// boolean isAdapter = this.checkBoxAdapter.isSelected();
		// boolean isShared = this.checkBoxShared.isSelected();
		//		
		// if(!isAdapter && !isShared)
		// this.checkBoxAdapter.setSelected(true);
	}

	protected void checkBoxShared_itemStateChanged(ItemEvent e)
			throws Exception {
		super.checkBoxShared_itemStateChanged(e);

		// boolean isAdapter = this.checkBoxAdapter.isSelected();
		// boolean isShared = this.checkBoxShared.isSelected();
		//		
		// if(!isAdapter && !isShared)
		// this.checkBoxShared.setSelected(true);
	}

	/**
	 * 商机 :包括售楼和租赁
	 * 
	 * @param type
	 *            : Sale , Tenancy
	 */
	private void initCommerceChanceRecord(String custId, String type) {
		if (type == null)
			return;

		this.tblCommerceChance.checkParsed();
		this.tblCommerceChance.getStyleAttributes().setLocked(true);
		this.tblCommerceChance.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		this.tblCommerceChance.getColumn("id").getStyleAttributes().setHided(
				true);
		this.tblCommerceChance.getColumn("createTime").getStyleAttributes()
				.setNumberFormat("YYYY-MM-DD");

		this.tblCommerceChance.removeRows();
		if (custId != null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("fdcCustomer.id", custId));
			// filter.getFilterItems().add(new
			// FilterItemInfo("sellProject.id",this.editData.getProject().getId().toString()));
			if (type.equals("Sale"))
				filter.getFilterItems().add(
						new FilterItemInfo("sysType",
								MoneySysTypeEnum.SalehouseSys.getValue()));
			else if (type.equals("Tenancy"))
				filter.getFilterItems().add(
						new FilterItemInfo("sysType",
								MoneySysTypeEnum.TenancySys.getValue()));

			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("sellProject.id"));
			selector.add(new SelectorItemInfo("sellProject.name"));
			selector.add(new SelectorItemInfo("sellProject.number"));
			selector.add(new SelectorItemInfo("saleMan.name"));
			selector.add(new SelectorItemInfo("commerceLevel.name"));
			selector.add(new SelectorItemInfo("fdcCustomer.id"));
			selector.add(new SelectorItemInfo("fdcCustomer.name"));
			selector.add(new SelectorItemInfo("fdcCustomer.number"));
			selector.add(new SelectorItemInfo("creator.name"));
			view.setSelector(selector);
			CommerceChanceCollection commColl = null;
			try {
				commColl = CommerceChanceFactory.getRemoteInstance()
						.getCommerceChanceCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				this.handleException(e);
				this.abort();
			}

			if (commColl == null)
				return;
			for (int i = 0; i < commColl.size(); i++) {
				CommerceChanceInfo commInfo = commColl.get(i);
				IRow row = null;
				row = this.tblCommerceChance.addRow();
				row.setUserObject(commInfo);
				row.getCell("id").setValue(commInfo.getId().toString());
				row.getCell("number").setValue(commInfo.getNumber());
				row.getCell("name").setValue(commInfo.getName());
				row.getCell("sysType").setValue(commInfo.getSysType());
				row.getCell("sellProject.name").setValue(
						commInfo.getSellProject());
				row.getCell("saleMan.name").setValue(
						commInfo.getSaleMan().getName());
				row.getCell("commerceLevel.name").setValue(
						commInfo.getCommerceLevel());
				row.getCell("commerceStatus").setValue(
						commInfo.getCommerceStatus());
				row.getCell("fdcCustomer.name").setValue(
						commInfo.getFdcCustomer());
				row.getCell("creator.name").setValue(
						commInfo.getCreator().getName());
				row.getCell("createTime").setValue(commInfo.getCreateTime());
			}
		}

	}

	/**
	 * 初始化跟进记录列表
	 * 
	 * @param type
	 *            : Sale , Tenancy
	 * */
	private void initTrackRecordTable(String custId, String type) {
		tblTrackRecord.checkParsed();
		tblTrackRecord.getStyleAttributes().setLocked(true);
		tblTrackRecord.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		tblTrackRecord.getColumn("name").getStyleAttributes().setHided(true);
		this.tblTrackRecord.getColumn("createTime").getStyleAttributes()
				.setNumberFormat("YYYY-MM-DD");

		tblTrackRecord.removeRows();
		if (custId != null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("head.id", custId));
			// filter.getFilterItems().add(new
			// FilterItemInfo("sellProject.id",this.editData.getProject().getId().toString()));
			if (type.equals("Sale"))
				filter.getFilterItems().add(
						new FilterItemInfo("sysType",
								MoneySysTypeEnum.SalehouseSys.getValue()));
			else if (type.equals("Tenancy"))
				filter.getFilterItems().add(
						new FilterItemInfo("sysType",
								MoneySysTypeEnum.TenancySys.getValue()));
			else if (type.equals("ManageSys"))
				filter.getFilterItems().add(
						new FilterItemInfo("sysType",
								MoneySysTypeEnum.ManageSys.getValue()));
			
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("sellProject.id"));
			selector.add(new SelectorItemInfo("sellProject.name"));
			selector.add(new SelectorItemInfo("sellProject.number"));
			selector.add(new SelectorItemInfo("head.name"));
			selector.add(new SelectorItemInfo("eventType.name"));
			selector.add(new SelectorItemInfo("receptionType.name"));
			selector.add(new SelectorItemInfo("commerceChance.name"));
			view.setSelector(selector);
			TrackRecordCollection trackColl = null;
			try {
				trackColl = TrackRecordFactory.getRemoteInstance()
						.getTrackRecordCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				this.handleException(e);
			}

			this.tblTrackRecord.removeRows();
			if (trackColl != null) {
				for (int i = 0; i < trackColl.size(); i++) {
					TrackRecordInfo trackInfo = trackColl.get(i);

					IRow row = this.tblTrackRecord.addRow();
					row.setUserObject(trackInfo);
					row.getCell("id").setValue(trackInfo.getId().toString());
					row.getCell("number").setValue(trackInfo.getNumber());
					row.getCell("name").setValue(trackInfo.getName());
					row.getCell("eventDate").setValue(trackInfo.getEventDate());
					row.getCell("head.name").setValue(trackInfo.getHead());
					row.getCell("trackResult").setValue(
							trackInfo.getTrackResult() == null ? "" : trackInfo
									.getTrackResult().getAlias());
					row.getCell("eventType.name").setValue(
							trackInfo.getEventType() == null ? "" : trackInfo
									.getEventType().getName());
					row.getCell("receptionType.name").setValue(
							trackInfo.getReceptionType() == null ? ""
									: trackInfo.getReceptionType().getName());
					row.getCell("commerceChance.name").setValue(
							trackInfo.getCommerceChance() == null ? ""
									: trackInfo.getCommerceChance().getName());
					row.getCell("description").setValue(
							trackInfo.getDescription());
					row.getCell("createTime").setValue(
							trackInfo.getCreateTime());
				}
			}
		}

	}

	public void actionAddTotalAll_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAddTotalAll_actionPerformed(e);

		UIContext uiContext = new UIContext(this);
		uiContext.put("ActionCommand", e.getActionCommand());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(CustomerTotalAddUI.class.getName(), uiContext, null,
						OprtState.ADDNEW);
		uiWindow.show();

	}

	protected void prmtSellProject_dataChanged(DataChangeEvent e)
			throws Exception {
		super.prmtSellProject_dataChanged(e);

		// if(e.getNewValue()==null){
		// this.comboCustCommStatus.setEnabled(true);
		// }else{
		// this.comboCustCommStatus.setEnabled(false);
		// this.comboCustCommStatus.setSelectedItem("全部");
		// }
	}

	/**
	 * 标识为重点跟进
	 */
	public void actionImportantTrack_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionImportantTrack_actionPerformed(e);
		this.checkSelected();
		FDCCustomerFactory.getRemoteInstance().signImportantTrack(
				getSelectedIdValues());
		CacheServiceFactory.getInstance().discardType(
				new FDCCustomerInfo().getBOSType());
		refresh(e);
	}

	/**
	 * 反标识重点跟进
	 */
	public void actionCancelImportantTrack_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancelImportantTrack_actionPerformed(e);
		this.checkSelected();
		FDCCustomerFactory.getRemoteInstance().cancelImportantTrack(
				getSelectedIdValues());
		CacheServiceFactory.getInstance().discardType(
				new FDCCustomerInfo().getBOSType());
		refresh(e);
	}

	/**
	 * 转接
	 * 
	 * @throws Exception
	 * */
	public void actionCustomerAdapter_actionPerformed(ActionEvent e)
			throws Exception {
		this.checkSelected();
		int selectedRows[] = KDTableUtil.getSelectedRows(this.tblMain);
		for (int i = 0; i < selectedRows.length; i++) {
			IRow row = this.tblMain.getRow(selectedRows[i]);
			String custIdStr = (String) row.getCell("id").getValue();
			if (!CustomerAdapterUI.hasAdpaterPermission(custIdStr)) {
				MsgBox.showInfo("您对第" + (row.getRowIndex() + 1)
						+ "行客户没有转接权限，不能转接!");
				return;
			}
		}

		List idList = this.getSelectedIdValues();
		CustomerAdapterUI.showUI(this, idList);

		this.actionRefresh_actionPerformed(e);
	}

	/**
	 * 共享
	 * 
	 * @throws Exception
	 * */
	public void actionCustomerShare_actionPerformed(ActionEvent e)
			throws Exception {
		this.checkSelected();
		int selectedRows[] = KDTableUtil.getSelectedRows(this.tblMain);
		for (int i = 0; i < selectedRows.length; i++) {
			IRow row = this.tblMain.getRow(selectedRows[i]);
			String custIdStr = (String) row.getCell("id").getValue();
			if (!CustomerShareUI.hasSharePermission(custIdStr)) {
				MsgBox.showInfo("您对第" + (row.getRowIndex() + 1)
						+ "行客户没有共享权限，不能共享!");
				return;
			}
		}

		List idList = this.getSelectedIdValues();
		CustomerShareUI.showUI(this, idList);
		this.actionRefresh_actionPerformed(e);
	}

	/**
	 * 取消共享
	 * */
	public void actionCustomerCancelShare_actionPerformed(ActionEvent e)
			throws Exception {
		this.checkSelected();
		int selectedRows[] = KDTableUtil.getSelectedRows(this.tblMain);
		for (int i = 0; i < selectedRows.length; i++) {
			IRow row = this.tblMain.getRow(selectedRows[i]);
			String custIdStr = (String) row.getCell("id").getValue();
			if (!CustomerShareUI.hasSharePermission(custIdStr)) {
				MsgBox.showInfo("您对第" + (row.getRowIndex() + 1)
						+ "行客户没有共享权限，不能取消共享!"); 
				return;
			}
		}

		String customerID = this.getSelectedKeyValue();
		CustomerCancelShareUI.showEditUI(this, customerID);
		super.actionCustomerCancelShare_actionPerformed(e);
	}

}