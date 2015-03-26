/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.Timestamp;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.BelongSystemEnum;
import com.kingdee.eas.fdc.market.MarketManageInfo;
import com.kingdee.eas.fdc.market.SchemeTypeEnum;
import com.kingdee.eas.fdc.market.client.QuestionPaperAnswerEditUI;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.TrackRecordEnum;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.client.TenancyBillListUI;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;
/**
 * output class name
 */
public class TrackRecordEditUI extends AbstractTrackRecordEditUI {
	private static final Logger logger = CoreUIObject.getLogger(TrackRecordEditUI.class);
	public final static String FROMTRACK = "fromtrack";

	// private Set billRoomSet = null; //所选关联单据里的房间id集合

	/**
	 * output class constructor
	 */
	public TrackRecordEditUI() throws Exception {
		super();
	}

	public void loadFields() {
		detachListeners();
		super.loadFields();

		setComboTrackResult((MoneySysTypeEnum) this.comboSysType.getSelectedItem());
		attachListeners();
	}

	public void onLoad() throws Exception {
		super.onLoad();

		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}

		// 设置隐藏菜单和工具按钮
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionSubmitOption.setVisible(false);
		this.btnWorkFlowG.setVisible(false);
		this.btnAudit.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.actionCalculator.setVisible(false);
		this.actionCopy.setVisible(false);

		this.actionAttachment.setVisible(true);
		this.btnAuditResult.setVisible(false);
		this.btnMultiapprove.setVisible(false);
		this.btnNextPerson.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);

		if (this.oprtState.equals(OprtState.ADDNEW)) {
			this.pkEventDate.setValue(FDCSQLFacadeFactory.getRemoteInstance().getServerTime());

			this.prmtRetionBill.setEnabled(false); //
			this.comboTrackResult.setEnabled(false);
		}

		// 设置当前营销人员能看到的客户
		this.prmtCustomer.setEntityViewInfo(CommerceHelper.getPermitCustomerView(this.editData.getSellProject(),SysContext.getSysContext().getCurrentUserInfo()));
		// 设置当前营销人员能看到的项目
		this.prmtSellProject.setEntityViewInfo(CommerceHelper.getPermitProjectView());
		// 设置当前营销人员能看到的商机
		this.prmtCommerceChance.setEntityViewInfo(CommerceHelper.getPermitCommerceChanceView());
		this.prmtSaleMan.setEntityViewInfo(CommerceHelper.getPermitSalemanView(this.editData.getSellProject()));

		setCommerceChanceView();

		TrackRecordEnum trackRecord = null;
		Object trackValue = this.comboTrackResult.getSelectedItem();
		if (trackValue instanceof TrackRecordEnum)
			trackRecord = (TrackRecordEnum) trackValue;
		setTheTrackResultQuery(trackRecord);

		if (this.prmtCommerceChance.getValue() != null) {
			this.comboTrackResult.setEnabled(true);
		}

		this.txtTrackTxt.setMaxLength(80);
		this.txtDescription.setMaxLength(80);

		this.prmtRetionBill.setDisplayFormat("name"); //
		this.prmtRetionBill.setEditFormat("$number$");
		this.prmtRetionBill.setCommitFormat("$number$");

		// 如果是由 其它界面打开浏览的，则去掉按钮栏
		String actionView = (String) this.getUIContext().get("ActionView");
		if (actionView != null && actionView.equals("OnlyView")) {
		//	this.toolBar.setVisible(false);
			
			//需要保留"问卷填写"和"退出"这两个按钮 by lww 12172010
			this.btnAddNew.setVisible(false);
			this.btnEdit.setVisible(false);
			this.btnSave.setVisible(false);
			this.btnRemove.setVisible(false);
			this.btnFirst.setVisible(false);
			this.btnPre.setVisible(false);
			this.btnNext.setVisible(false);
			this.btnLast.setVisible(false);
}

		setGroupFilter();
		setMarketManageView();
		
		EntityViewInfo evi= new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
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
	    
	    this.actionQuestionPrint.setVisible(false);
	}

	/**
	 * 设置"问卷填写"图标 02122010
	 */
	protected void initWorkButton() {
		super.initWorkButton();
        this.btnQuestionPrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addcredence"));
	}
	/**
	 * 页面状态为"查看"、"编辑"下，问卷填写按钮可用
	 */
	protected void initDataStatus() {
		super.initDataStatus();
//		this.actionQuestionPrint.setEnabled(!OprtState.ADDNEW.equals(this.getOprtState()));
		this.actionQuestionPrint.setEnabled(OprtState.VIEW.equals(this.getOprtState()) || 
					OprtState.EDIT.equals(this.getOprtState()));
	}
		
	private void prmtCustomerSetFilter() {

	}

	/**
	 * 新增问卷填写action 01122010
	 */
	public void actionQuestionPrint_actionPerformed(ActionEvent e)
			throws Exception {
		if(isModify()){
			FDCMsgBox.showInfo("请先保存单据信息!");
			abort();
		}
		super.actionQuestionPrint_actionPerformed(e);
		
		if(this.editData.getId()!=null){//这里判断当前跟进 是否存在于数据库
			UIContext uiContext = new UIContext(this);
			uiContext.put("trackRecord", editData.clone());
			uiContext.put("from", FROMTRACK);
			IUIFactory uiFactory = null;
			uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
			IUIWindow curDialog = uiFactory.create(QuestionPaperAnswerEditUI.class
					.getName(), uiContext, null, OprtState.ADDNEW);
			curDialog.show();
		}
	}

	private void setGroupFilter() {
		SellProjectInfo sp = (SellProjectInfo) this.prmtSellProject.getValue();
		if (sp == null) {
			return;
		}
		String spId = sp.getId().toString();
		// 设置事件类型的集团管控过滤
		SHEHelper.SetGroupFilters(this.prmtEventType, spId, "事件类型", "commerArch");
		// 设置接待方式的集团管控过滤
		SHEHelper.SetGroupFilters(this.prmtReceptionType, spId, "接待方式", "CustomerArch");

	}

	/**
	 * 设置跟进商机的view
	 */
	private void setCommerceChanceView() {
		// if(this.oprtState.equals(OprtState.ADDNEW) ||
		// this.oprtState.equals(OprtState.EDIT))
		// this.prmtCommerceChance.setValue(null);

		FDCCustomerInfo custInfo = (FDCCustomerInfo) this.prmtCustomer.getValue();
		SellProjectInfo projectInfo = (SellProjectInfo) this.prmtSellProject.getValue();
		MoneySysTypeEnum sysType = (MoneySysTypeEnum) this.comboSysType.getSelectedItem();

		String custId = "null";
		String proId = "null";

		if (custInfo != null)
			custId = custInfo.getId().toString();
		if (projectInfo != null)
			proId = projectInfo.getId().toString();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sysType", sysType == null ? "null" : sysType.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id", custId));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", proId));
		filter.getFilterItems().add(new FilterItemInfo("commerceStatus", CommerceStatusEnum.Finish.getValue(), CompareType.NOTEQUALS));
		view.setFilter(filter);
		this.prmtCommerceChance.setEntityViewInfo(view);
	}

	/**
	 * 设置营销活动的view
	 */
	private void setMarketManageView() {
		SellProjectInfo projectInfo = (SellProjectInfo) this.prmtSellProject.getValue();
		MoneySysTypeEnum sysType = (MoneySysTypeEnum) this.comboSysType.getSelectedItem();

		String proId = "null";
		if (projectInfo != null)
			proId = projectInfo.getId().toString();
		String sysTypeValue = "null";
		if (sysType.equals(MoneySysTypeEnum.SalehouseSys))
			sysTypeValue = MoneySysTypeEnum.SALEHOUSESYS_VALUE;
		else if (sysType.equals(MoneySysTypeEnum.TenancySys))
			sysTypeValue = MoneySysTypeEnum.TENANCYSYS_VALUE;
		else if (sysType.equals(MoneySysTypeEnum.ManageSys))
			sysTypeValue = MoneySysTypeEnum.MANAGESYS_VALUE;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("belongSystem", sysTypeValue));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", proId));
		view.setFilter(filter);
		this.prmtMarketManage.setEntityViewInfo(view);
	}

	protected void prmtCustomer_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtCustomer_dataChanged(e);
		if (CommerceHelper.isF7DataChanged(e)) {
			this.prmtCommerceChance.setValue(null);
			setCommerceChanceView();

			TrackRecordEnum trackRecord = null;
			Object trackValue = this.comboTrackResult.getSelectedItem();
			if (trackValue instanceof TrackRecordEnum)
				trackRecord = (TrackRecordEnum) trackValue;
			setTheTrackResultQuery(trackRecord);
		}
	}

	protected void prmtSellProject_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtSellProject_dataChanged(e);
		if (CommerceHelper.isF7DataChanged(e)) {
			this.prmtCommerceChance.setValue(null);
			setCommerceChanceView();
			setMarketManageView();

			TrackRecordEnum trackRecord = null;
			Object trackValue = this.comboTrackResult.getSelectedItem();
			if (trackValue instanceof TrackRecordEnum)
				trackRecord = (TrackRecordEnum) trackValue;
			setTheTrackResultQuery(trackRecord);
		
		}
		setGroupFilter();
	}

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);

		vertifyInput();
	}

	public void vertifyInput() throws Exception {
		CommerceChanceInfo comChance = (CommerceChanceInfo) this.prmtCommerceChance.getValue();
		FDCCustomerInfo customer = (FDCCustomerInfo) this.prmtCustomer.getValue();

		StringBuffer buff = new StringBuffer();
		if (this.txtNumber.isVisible() && this.txtNumber.isEnabled() && this.txtNumber.isEditable()) {
			if (StringUtils.isEmpty(this.txtNumber.getText()))
				buff.append("编码不能为空!\n");
		}
		// if(this.prmtSellProject.getValue()==null)
		// buff.append("跟进项目不能空!\n");
		if (this.comboSysType.getSelectedItem() == null)
			buff.append("所属系统不能空!\n");
		if (customer == null)
			buff.append("客户不能空!\n");
		if (this.pkEventDate.getValue() == null)
			buff.append("跟进日期不能空!\n");
		if (this.prmtReceptionType.getValue() == null) {
			buff.append("接待方式不能空!\n");
		}

		if (comChance != null) {
			/*
			 * 有商机跟进也可以无跟进成果 20090223 TrackRecordEnum trackResult = null; Object
			 * trackValue = this.comboTrackResult.getSelectedItem();
			 * if(trackValue instanceof TrackRecordEnum) trackResult =
			 * (TrackRecordEnum)trackValue;
			 * 
			 * String relationBillId = this.txtRelationContract.getText();
			 * if(trackResult==null || relationBillId==null ||
			 * relationBillId.trim().equals("")){
			 * buff.append("跟进商机不为空的情况下,跟进成果和关联单据都不能为空!\n"); }
			 */

			if (customer.getId() != null && comChance.getFdcCustomer() != null && comChance.getFdcCustomer().getId() != null) {
				if (!customer.getId().equals(comChance.getFdcCustomer().getId()))
					buff.append("本跟进客户和跟进商机里的客户不一致!\n");
			}
		} else {
			TrackRecordTypeEnum trackType = (TrackRecordTypeEnum) this.comboTrackType.getSelectedItem();
			if (trackType != null && trackType.equals(TrackRecordTypeEnum.Intency)) {
				buff.append("'意向'时商机不能空!\n");
			}
		}

		TrackRecordEnum trackResult = null;
		Object trackValue = this.comboTrackResult.getSelectedItem();
		if (trackValue instanceof TrackRecordEnum)
			trackResult = (TrackRecordEnum) trackValue;
		if (trackResult != null) {
			if (this.prmtRetionBill.getValue() == null) {
				buff.append("有跟进成果的情况下，必须选择对应的'关联单据'！");
			}
		}

		// 当有跟进成果时校验 同一单据不能被同一客户多次跟进引用
		if (this.prmtRetionBill.getValue() != null && customer != null) {
			String relationContractID = this.txtRelationContract.getText();
			if (!FDCHelper.isEmpty(relationContractID)) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("head.id", customer.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("relationContract", relationContractID.trim()));
				// 如果是修改，此判读要把当前跟进记录排除
				if (this.editData.getId() != null) {
					filter.getFilterItems().add(new FilterItemInfo("id", this.editData.getId().toString(), CompareType.NOTEQUALS));
				}
				// 预订和认购可能会关联同一张认购单
				if (trackResult != null) {
					if (trackResult.equals(TrackRecordEnum.DestineApp)) {
						filter.getFilterItems().add(new FilterItemInfo("trackResult", TrackRecordEnum.DESTINEAPP_VALUE));
					} else if (trackResult.equals(TrackRecordEnum.PurchaseApp)) {
						filter.getFilterItems().add(new FilterItemInfo("trackResult", TrackRecordEnum.PURCHASEAPP_VALUE));
					}
				}
				if (TrackRecordFactory.getRemoteInstance().exists(filter)) {
					buff.append("该单据已被此客户跟进引用过，不能重复引用!");
				}
			}
		}

		if (!buff.toString().equals("")) {
			MsgBox.showWarning(buff.toString());
			this.abort();
			// return;
		}

	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {

		CommerceChanceInfo comChance = (CommerceChanceInfo) this.prmtCommerceChance.getValue();
		FDCCustomerInfo customer = (FDCCustomerInfo) this.prmtCustomer.getValue();

		if (customer == null || customer.getId() == null)
			return;

		/***
		 * 以下所有的反写逻辑，全部应该迁移到服务器端，减少RPC调用 TODO
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 */
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.id", customer.getId().toString()));
		boolean isExist = TrackRecordFactory.getRemoteInstance().exists(filter);
		SelectorItemCollection selectColl = new SelectorItemCollection();
		selectColl.add(new SelectorItemInfo("lastTrackDate"));
		customer.setLastTrackDate(editData.getEventDate());
		if (!isExist) {
			// 第一条跟进保存时反写客户资料中的跟进的接待方式
			selectColl.add(new SelectorItemInfo("receptionType"));
			customer.setReceptionType((ReceptionTypeInfo) this.prmtReceptionType.getValue());
		}

		FDCCustomerFactory.getRemoteInstance().updatePartial(customer, selectColl);

		super.actionSave_actionPerformed(e);
		CacheServiceFactory.getInstance().discardType(new TrackRecordInfo().getBOSType());

		//按"保存"按钮后，亦要求"问卷填写"按钮可用
//		this.actionQuestionPrint.setEnabled(true);
		
		// 反写商机到诚意认购上
		setSincerCommer();

		// CommerceStatusEnum 跟进保存时候反写客户的销售跟进阶段和租赁跟进阶段.同时反写跟进商机的阶段
		// 拿跟进成果的的最高级去反写
		TrackRecordEnum trackResult = null;
		Object trackValue = this.comboTrackResult.getSelectedItem();
		if (trackValue instanceof TrackRecordEnum)
			trackResult = (TrackRecordEnum) trackValue;

		MoneySysTypeEnum sysType = (MoneySysTypeEnum) this.comboSysType.getSelectedItem();
		TrackRecordTypeEnum trackType = (TrackRecordTypeEnum) this.comboTrackType.getSelectedItem();

		// 更新客户的跟进阶段
		updateCustomerTrackPhase(customer, sysType, trackType, trackResult);
		// 跟新商机的跟进阶段
		if (comChance == null || comChance.getId() == null)
			return;

		updateCommChanceTrackPhase(comChance, sysType, trackType, trackResult);
	}

	// 在添加跟进的时候如果跟进成果是诚意认购。而该诚意认购中又没有选择的商机的话反写这个商机到诚意认购上
	protected void setSincerCommer() throws EASBizException, BOSException {
		if (TrackRecordEnum.SincerityPur.equals(this.comboTrackResult.getSelectedItem())) {
			if (this.prmtRetionBill.getValue() != null) {
				SincerityPurchaseInfo sincer = (SincerityPurchaseInfo) this.prmtRetionBill.getValue();
				if (sincer.getCommerceChance() == null) {
					CommerceChanceInfo commerInfo = (CommerceChanceInfo) this.prmtCommerceChance.getValue();
					SelectorItemCollection selectColl = new SelectorItemCollection();
					selectColl.add(new SelectorItemInfo("commerceChance"));
					sincer.setCommerceChance(commerInfo);
					SincerityPurchaseFactory.getRemoteInstance().updatePartial(sincer, selectColl);
				}
			}
		}
	}

	/**
	 * 更新客户的跟进阶段 --拿最新的跟进记录的跟进成果去反写
	 * 
	 * @param customer
	 *            客户
	 * @param sysType
	 *            所属系统
	 * @param trackType
	 *            跟进类型 (线索、潜在、意向)
	 * @param trackResult
	 *            跟进成果 (诚意认购,预定申请,认购申请,签约申请,退房申请,认租申请)
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void updateCustomerTrackPhase(FDCCustomerInfo customer, MoneySysTypeEnum sysType, TrackRecordTypeEnum trackType, TrackRecordEnum trackResult) throws BOSException, EASBizException {
		if (customer == null)
			return;
		if (sysType == null)
			return;
		// 确保售楼系统对应的是售楼的跟踪结果，租赁系统对应的是租赁的跟进结果
		if (sysType.equals(MoneySysTypeEnum.SalehouseSys) && trackResult != null && !trackResult.getValue().startsWith("S"))
			return;
		if (sysType.equals(MoneySysTypeEnum.TenancySys) && trackResult != null && !trackResult.getValue().startsWith("T"))
			return;
		// 把跟进结果转换为对应的商机状态
		CommerceStatusEnum thisCommStatus = convertToCommStatus(sysType, trackType, trackResult);
		if (thisCommStatus == null)
			return;
		if (sysType.equals(MoneySysTypeEnum.SalehouseSys)) {
			CommerceStatusEnum custSaleTrack = customer.getSaleTrackPhase();
			if (compareCommerceStatus(sysType, thisCommStatus, custSaleTrack) > 0) {
				SelectorItemCollection selectColl = new SelectorItemCollection();
				selectColl.add(new SelectorItemInfo("saleTrackPhase"));
				customer.setSaleTrackPhase(thisCommStatus);
				FDCCustomerFactory.getRemoteInstance().updatePartial(customer, selectColl);
			}
		} else if (sysType.equals(MoneySysTypeEnum.TenancySys)) {
			CommerceStatusEnum custTenancyTrack = customer.getTenancyTrackPhase();
			if (compareCommerceStatus(sysType, thisCommStatus, custTenancyTrack) > 0) {
				SelectorItemCollection selectColl = new SelectorItemCollection();
				selectColl.add(new SelectorItemInfo("tenancyTrackPhase"));
				customer.setTenancyTrackPhase(thisCommStatus);
				FDCCustomerFactory.getRemoteInstance().updatePartial(customer, selectColl);
			}
		}
	}

	// 两个商机状态之间的比较
	// 线索Clew 潜在Latency 意向Intent 诚意Sincerity 预定SaleDestine 认购SalePurchase
	// 签约SaleSign 认租TenancySign 终止Finish
	// 售楼 "Clew,Latency,Intent,Sincerity,SaleDestine,SalePurchase,SaleSign,"
	// 租赁"Clew,Latency,Intent,TenancySign,"
	// 如果两个状态不属于同一系统中的，就默认相等
	public static int compareCommerceStatus(MoneySysTypeEnum sysType, CommerceStatusEnum sorStatus, CommerceStatusEnum orgStatus) {
		if (sysType == null)
			return 0;
		if (sorStatus == null && orgStatus == null)
			return 0;
		if (sorStatus == null)
			return -1;
		else if (orgStatus == null)
			return 1;
		if (sorStatus.equals(orgStatus))
			return 0;

		String statusString = "";
		if (sysType.equals(MoneySysTypeEnum.SalehouseSys)) {
			statusString = CommerceStatusEnum.CLEW_VALUE + "," + CommerceStatusEnum.LATENCY_VALUE + "," + CommerceStatusEnum.INTENT_VALUE + "," + CommerceStatusEnum.SINCERITY_VALUE + ","
					+ CommerceStatusEnum.SALEDESTINE_VALUE + "," + CommerceStatusEnum.SALEPURCHASE_VALUE + "," + CommerceStatusEnum.SALESIGN_VALUE + ",";
		} else if (sysType.equals(MoneySysTypeEnum.TenancySys)) {
			statusString = CommerceStatusEnum.CLEW_VALUE + "," + CommerceStatusEnum.LATENCY_VALUE + "," + CommerceStatusEnum.INTENT_VALUE + "," + CommerceStatusEnum.TENANCYSIGN_VALUE + ",";
		}
		if (statusString.indexOf(sorStatus.getValue() + ",") < 0 || statusString.indexOf(orgStatus.getValue() + ",") < 0)
			return 0; // 不属于同一系统，默认相等

		return statusString.indexOf(sorStatus.getValue() + ",") - statusString.indexOf(orgStatus.getValue() + ",");
	}

	/**
	 * 更新商机的跟进阶段
	 * 
	 * @param comChance
	 *            商机
	 * @param sysType
	 * @param trackType
	 * @param trackResult
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void updateCommChanceTrackPhase(CommerceChanceInfo comChance, MoneySysTypeEnum sysType, TrackRecordTypeEnum trackType, TrackRecordEnum trackResult) throws BOSException,
			EASBizException {
		if (comChance == null)
			return;
		if (sysType == null)
			return;
		// 确保售楼系统对应的是售楼的跟踪结果，租赁系统对应的是租赁的跟进结果
		if (sysType.equals(MoneySysTypeEnum.SalehouseSys) && trackResult != null && !trackResult.getValue().startsWith("S"))
			return;
		if (sysType.equals(MoneySysTypeEnum.TenancySys) && trackResult != null && !trackResult.getValue().startsWith("T"))
			return;
		// 确保商机的所属系统和当前的所属系统一致
		if (comChance.getSysType() == null || !comChance.getSysType().equals(sysType))
			return;
		// 把跟进结果转换为对应的商机状态
		CommerceStatusEnum thisCommStatus = convertToCommStatus(sysType, trackType, trackResult);
		if (thisCommStatus == null)
			return;
		if (compareCommerceStatus(sysType, thisCommStatus, comChance.getCommerceStatus()) > 0) {
			SelectorItemCollection selectColl = new SelectorItemCollection();
			selectColl.add(new SelectorItemInfo("commerceStatus"));
			comChance.setCommerceStatus(thisCommStatus);
			CommerceChanceFactory.getRemoteInstance().updatePartial(comChance, selectColl);
		}
	}

	/**
	 * 把跟进结果转换为对应的商机状态
	 * 
	 * @return
	 */
	public static CommerceStatusEnum convertToCommStatus(MoneySysTypeEnum sysType, TrackRecordTypeEnum trackType, TrackRecordEnum trackResult) {
		CommerceStatusEnum thisCommStatus = null;
		if (sysType == null || trackType == null)
			return null;

		thisCommStatus = CommerceStatusEnum.Clew;
		if (trackType.equals(TrackRecordTypeEnum.Latency))
			thisCommStatus = CommerceStatusEnum.Latency;
		else if (trackType.equals(TrackRecordTypeEnum.Intency)) {
			if (trackResult == null)
				thisCommStatus = CommerceStatusEnum.Intent;
			else {
				if (sysType.equals(MoneySysTypeEnum.SalehouseSys)) {
					if (trackResult.equals(TrackRecordEnum.SincerityPur))
						thisCommStatus = CommerceStatusEnum.Sincerity;
					else if (trackResult.equals(TrackRecordEnum.DestineApp))
						thisCommStatus = CommerceStatusEnum.SaleDestine;
					else if (trackResult.equals(TrackRecordEnum.PurchaseApp))
						thisCommStatus = CommerceStatusEnum.SalePurchase;
					else if (trackResult.equals(TrackRecordEnum.SignApp))
						thisCommStatus = CommerceStatusEnum.SaleSign;
					// 退房的情况要特殊处理下 ,具体如何处理待定
					// else if(trackResult.equals(TrackRecordEnum.CancelApp))
					// thisCommStatus = CommerceStatusEnum.SaleFinish;
				} else if (sysType.equals(MoneySysTypeEnum.TenancySys)) {
					if (trackResult.equals(TrackRecordEnum.TenancyApp))
						thisCommStatus = CommerceStatusEnum.TenancySign;
				}
			}
		}

		return thisCommStatus;
	}

	protected void prmtCommerceChance_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtCommerceChance_dataChanged(e);

		if (CommerceHelper.isF7DataChanged(e)) {
			if (e.getOldValue() != null) {
				this.comboTrackResult.setSelectedItem(null);
				this.prmtRetionBill.setValue(null);
				this.txtRelationContract.setText("");
			}

			if (e.getNewValue() != null) {
				// CommerceChanceInfo newCom =
				// (CommerceChanceInfo)e.getNewValue();
				this.comboTrackResult.setEnabled(true);
			} else {
				this.comboTrackResult.setEnabled(false);
				this.txtRelationContract.setEnabled(false);
			}
		}
	}

	// 根据所属系统过滤跟进成果
	private void setComboTrackResult(MoneySysTypeEnum sysType) {
		TrackRecordEnum selectItem = null;
		Object trackValue = this.comboTrackResult.getSelectedItem();
		if (trackValue instanceof TrackRecordEnum)
			selectItem = (TrackRecordEnum) trackValue;

		this.comboTrackResult.removeAllItems();
		this.comboTrackResult.addItem("");
		if (sysType == null)
			return;
		if (sysType.equals(MoneySysTypeEnum.SalehouseSys)) {
			this.comboTrackResult.addItems(TrackRecordEnum.getEnumList().toArray());
			this.comboTrackResult.removeItem(TrackRecordEnum.TenancyApp);
		} else if (sysType.equals(MoneySysTypeEnum.TenancySys)) {
			this.comboTrackResult.addItem(TrackRecordEnum.TenancyApp);
		}
		if (selectItem != null)
			this.comboTrackResult.setSelectedItem(selectItem);
	}

	protected void comboSysType_itemStateChanged(ItemEvent e) throws Exception {
		super.comboSysType_itemStateChanged(e);

		MoneySysTypeEnum sysType = (MoneySysTypeEnum) e.getItem();

		this.prmtCommerceChance.setValue(null);
		setCommerceChanceView();

		setComboTrackResult(sysType);

		setMarketManageView();
	}

	protected void comboTrackResult_itemStateChanged(ItemEvent e) throws Exception {
		super.comboTrackResult_itemStateChanged(e);
		TrackRecordEnum trackRec = null;
		Object itemValue = e.getItem(); // 可能为""
		if (!itemValue.equals(this.comboTrackResult.getSelectedItem()))
			return;

		this.txtRelationContract.setText("");
		this.prmtRetionBill.setValue(null);

		if (itemValue instanceof TrackRecordEnum)
			trackRec = (TrackRecordEnum) itemValue;
		// prmtRetionBill
		if (trackRec != null) {
			setTheTrackResultQuery(trackRec);
			this.prmtRetionBill.setRequired(true);
		} else {
			this.prmtRetionBill.setEnabled(false);
		}

	}

	protected void prmtRetionBill_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtRetionBill_dataChanged(e);

		// 如果是传递近来的关联单据,则不用处理id的付值问题
		String billNumberStr = (String) this.getUIContext().get("BillNumberString");
		if (billNumberStr != null)
			return;

		CoreBaseInfo coreBase = (CoreBaseInfo) e.getNewValue();
		if (coreBase != null) {
			this.txtRelationContract.setText(coreBase.getId().toString());

			/*
			 * 不用检查房间是否是商机的意向房间了,所以可以去掉 TrackRecordEnum trackRec = null; Object
			 * trackValue = this.comboTrackResult.getSelectedItem();
			 * if(trackValue instanceof TrackRecordEnum) trackRec =
			 * (TrackRecordEnum)trackValue;
			 * 
			 * if(trackRec!=null) { billRoomSet = new HashSet();
			 * if(trackRec.equals(TrackRecordEnum.SincerityPur)) {
			 * //诚意认购时,选择该商机的客户诚意认购单 SincerityPurchaseInfo sinPur =
			 * (SincerityPurchaseInfo)this.prmtRetionBill.getValue();
			 * if(sinPur!=null && sinPur.getRoom()!=null)
			 * billRoomSet.add(sinPur.getRoom().getId().toString()); }else
			 * if(trackRec.equals(TrackRecordEnum.DestineApp)) {
			 * //预定申请,选择该商机的客户预定申请或预定复核状态的认购单 PurchaseInfo pur =
			 * (PurchaseInfo)this.prmtRetionBill.getValue(); if(pur!=null &&
			 * pur.getRoom()!=null)
			 * billRoomSet.add(pur.getRoom().getId().toString()); }else
			 * if(trackRec.equals(TrackRecordEnum.PurchaseApp))
			 * {//认购申请,选择该商机客户认购申请或认购审批的认购单 PurchaseInfo pur =
			 * (PurchaseInfo)this.prmtRetionBill.getValue(); if(pur!=null &&
			 * pur.getRoom()!=null)
			 * billRoomSet.add(pur.getRoom().getId().toString()); }else
			 * if(trackRec.equals(TrackRecordEnum.SignApp))
			 * {//签约申请,选择提交状态,审批中或已审批的签约单. RoomSignContractInfo sign =
			 * (RoomSignContractInfo)this.prmtRetionBill.getValue();
			 * if(sign!=null && sign.getRoom()!=null)
			 * billRoomSet.add(sign.getRoom().getId().toString()); }else
			 * if(trackRec.equals(TrackRecordEnum.CancelApp))
			 * {//退房申请,选择该商机客户的退房单 QuitRoomInfo quit =
			 * (QuitRoomInfo)this.prmtRetionBill.getValue(); if(quit!=null &&
			 * quit.getRoom()!=null)
			 * billRoomSet.add(quit.getRoom().getId().toString()); }else
			 * if(trackRec.equals(TrackRecordEnum.TenancyApp))
			 * {//认租申请,选择该商机客户的认租合同 TenancyBillInfo tenBill =
			 * (TenancyBillInfo)this.prmtRetionBill.getValue();
			 * if(tenBill!=null) { EntityViewInfo view = new EntityViewInfo();
			 * FilterInfo filter = new FilterInfo();
			 * filter.getFilterItems().add(new
			 * FilterItemInfo("tenancy.id",tenBill.getId().toString()));
			 * view.setFilter(filter); TenancyRoomEntryCollection tenRooms =
			 * TenancyRoomEntryFactory
			 * .getRemoteInstance().getTenancyRoomEntryCollection(view); for(int
			 * i=0;i<tenRooms.size();i++){ TenancyRoomEntryInfo tenRoom =
			 * tenRooms.get(i);
			 * billRoomSet.add(tenRoom.getRoom().getId().toString()); } } } }
			 */
		} else {
			this.txtRelationContract.setText("");
			// billRoomSet = new HashSet();
		}
	}

	/**
	 * 设置关联单据的f7控件的query
	 */
	private void setTheTrackResultQuery(TrackRecordEnum trackRec) {
		this.contRelationContract.setVisible(false);
		if (trackRec != null) {
			this.contRelationContract.setVisible(true);
			String customerId = "null";
			FDCCustomerInfo fdcCust = (FDCCustomerInfo) this.prmtCustomer.getValue();
			if (fdcCust != null)
				customerId = fdcCust.getId().toString();
			else {// 所选择的商机所对应的客户
				CommerceChanceInfo comChance = (CommerceChanceInfo) this.prmtCommerceChance.getValue();
				if (comChance != null)
					customerId = comChance.getFdcCustomer().getId().toString();
			}

			String billId = this.txtRelationContract.getText();
			if (billId != null && billId.trim().equals("")) // billID不为空的时候，
				// 要查出对应的单据
				// ，并把附值到f7控件上
				billId = null;

			if (billId != null)
				this.contRelationContract.setEnabled(false);
			this.prmtRetionBill.setDisplayFormat("$number$"); // 这些单据基本都没有名称，
			// 只有用编号代替
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			SellProjectInfo sp = (SellProjectInfo) this.prmtSellProject.getValue();
			String sellProIdStr = sp==null?"null":sp.getId().toString();
			if (trackRec.equals(TrackRecordEnum.SincerityPur)) { // 诚意认购时,
				// 选择该商机的客户诚意认购单
				this.prmtRetionBill.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SincerityPurchaseQuery");
				filter.getFilterItems().add(new FilterItemInfo("customer.id", customerId));
				filter.getFilterItems().add(new FilterItemInfo("sincerityState", SincerityPurchaseStateEnum.QUITNUM_VALUE, CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProIdStr));
				if (billId != null) {
					try {
						SincerityPurchaseInfo billInfo = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(BOSUuid.read(billId)));
						if (billInfo != null)
							this.prmtRetionBill.setValue(billInfo);
					} catch (Exception e) {
						this.prmtRetionBill.setText("单据已被删除");
					}
				}
			} else if (trackRec.equals(TrackRecordEnum.DestineApp) || trackRec.equals(TrackRecordEnum.PurchaseApp)) { // 预定申请
				// ,
				// 认购申请
				// ,
				// 选择该商机的客户预定申请或预定复核状态的认购单
				this.prmtRetionBill.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.PurchaseQuery");
				filter.setMaskString("#0 and #1 and (#2 or #3)");
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProIdStr));
				filter.getFilterItems().add(new FilterItemInfo("customerInfo.customer.id", customerId));
				if (trackRec.equals(TrackRecordEnum.DestineApp)) {	
					filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PREPURCHASEAPPLY_VALUE));
					filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PREPURCHASECHECK_VALUE));
				} else {
					filter.setMaskString("#0 and (#1 or #2)");
					filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PURCHASEAPPLY_VALUE));
					filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PURCHASEAUDIT_VALUE));
				}

				if (billId != null) {
					try {
						PurchaseInfo billInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(BOSUuid.read(billId)));
						if (billInfo != null)
							this.prmtRetionBill.setValue(billInfo);
					} catch (Exception e) {
						this.prmtRetionBill.setText("单据已被删除");
					}
				}
			} else if (trackRec.equals(TrackRecordEnum.SignApp)) {//签约申请,选择提交状态,
				// 审批中或已审批的签约单
				// .
				this.prmtRetionBill.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomSignContractQuery");
				filter.setMaskString("#0 and #1 and (#2 or #3 or #4)");
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProIdStr));
				filter.getFilterItems().add(new FilterItemInfo("purchase.customerInfo.customer.id", customerId));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));

				if (billId != null) {
					try {
						RoomSignContractInfo billInfo = RoomSignContractFactory.getRemoteInstance().getRoomSignContractInfo(new ObjectUuidPK(BOSUuid.read(billId)));
						if (billInfo != null)
							this.prmtRetionBill.setValue(billInfo);
					} catch (Exception e) {
						this.prmtRetionBill.setText("单据已被删除");
					}
				}
			} else if (trackRec.equals(TrackRecordEnum.CancelApp)) {// 退房申请,
				// 选择该商机客户的退房单
				this.prmtRetionBill.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.QuitRoomQuery");
				filter.setMaskString("#0 and #1 and (#2 or #3 or #4)");
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProIdStr));
				filter.getFilterItems().add(new FilterItemInfo("purchase.customerInfo.customer.id", customerId));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));

				if (billId != null) {
					try {
						QuitRoomInfo billInfo = QuitRoomFactory.getRemoteInstance().getQuitRoomInfo(new ObjectUuidPK(BOSUuid.read(billId)));
						if (billInfo != null)
							this.prmtRetionBill.setValue(billInfo);
					} catch (Exception e) {
						this.prmtRetionBill.setText("单据已被删除");
					}
				}
			} else if (trackRec.equals(TrackRecordEnum.TenancyApp)) {// 认租申请,
				// 选择该商机客户的认租合同
				this.prmtRetionBill.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillQuery");
				filter.setMaskString("#0 and #1 and (#2 or #3 or #4)");
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProIdStr));
				filter.getFilterItems().add(new FilterItemInfo("tenCustomerList.fdcCustomer.id", customerId));
				filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.SUBMITTED_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.AUDITING_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.AUDITED_VALUE));

				if (billId != null) {
					try {
						TenancyBillInfo billInfo = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(BOSUuid.read(billId)));
						if (billInfo != null)
							this.prmtRetionBill.setValue(billInfo);
					} catch (Exception e) {
						this.prmtRetionBill.setText("单据已被删除");
					}
				}
			}
			this.prmtRetionBill.setEntityViewInfo(view);
			this.prmtRetionBill.setEnabled(true);

		}
	}

	protected void btnAddNewBill_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddNewBill_actionPerformed(e);

		TrackRecordEnum trackRec = null;
		Object trackValue = this.comboTrackResult.getSelectedItem();
		if (trackValue instanceof TrackRecordEnum)
			trackRec = (TrackRecordEnum) trackValue;

		if (trackRec != null) {
			UIContext uiContext = new UIContext(this);
			String classUIName = null;
			if (trackRec.equals(TrackRecordEnum.SincerityPur)) {
				classUIName = SincerityPurchaseEditUI.class.getName(); // 诚意认购时,
				// 诚意认购单
				uiContext.put("sellProject", this.prmtSellProject.getValue());
				uiContext.put("commerceChance", this.prmtCommerceChance.getValue());
				uiContext.put("customer", this.prmtCustomer.getValue());
			} else if (trackRec.equals(TrackRecordEnum.DestineApp)) {
				classUIName = PurchaseEditUI.class.getName(); // 预定申请,认购单
				uiContext.put("sellProject", this.prmtSellProject.getValue());
				uiContext.put("isPrePurchase", new Boolean(true));
			} else if (trackRec.equals(TrackRecordEnum.PurchaseApp)) {
				classUIName = PurchaseEditUI.class.getName(); // 认购申请,认购单
				uiContext.put("sellProject", this.prmtSellProject.getValue());
			} else if (trackRec.equals(TrackRecordEnum.SignApp)) {
				classUIName = RoomSignContractListUI.class.getName(); //签约申请,签约单
				// .
			} else if (trackRec.equals(TrackRecordEnum.CancelApp)) {
				classUIName = QuitRoomListUI.class.getName(); // 退房申请,退房单
			} else if (trackRec.equals(TrackRecordEnum.TenancyApp)) {
				classUIName = TenancyBillListUI.class.getName(); // 认租申请,认租合同
			}
			if (classUIName != null) {
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(classUIName, uiContext, null, OprtState.ADDNEW);
				uiWindow.show();
			}
		}

	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected IObjectValue createNewData() {
		this.detachListeners();
		TrackRecordInfo trackInfo = new TrackRecordInfo();

		// 可以从商机管理界面中调用该跟踪记录 带入商机、客户和租售项目、跟进成果、所属系统 跟进类型
		CommerceChanceInfo commInfo = (CommerceChanceInfo) this.getUIContext().get("CommerceChance");
		FDCCustomerInfo custInfo = (FDCCustomerInfo) this.getUIContext().get("FdcCustomer");
		SellProjectInfo sellProInfo = (SellProjectInfo) this.getUIContext().get("SellProject");
		TrackRecordEnum trackEnum = (TrackRecordEnum) this.getUIContext().get("TrackRecordEnum");

		// 为营销活动提供
		MarketManageInfo marketManInfo = (MarketManageInfo) this.getUIContext().get("MarketManage");
		if (marketManInfo != null) {
			this.prmtMarketManage.setValue(marketManInfo);
			trackInfo.setMarketManage(marketManInfo);
		}

		String billNumberStr = (String) this.getUIContext().get("BillNumberString");
		String billIdStr = (String) this.getUIContext().get("BillIdString");
		if (commInfo != null) { // 如果传入商机，则跟进类型一定为'意向',并且可以根据商机的所属系统设置跟进记录的所属系统
			trackInfo.setCommerceChance(commInfo);
			trackInfo.setTrackType(TrackRecordTypeEnum.Intency);
			this.comboTrackType.setSelectedItem(TrackRecordTypeEnum.Intency);

			if (commInfo.getSysType() != null) {
				trackInfo.setSysType(commInfo.getSysType());
				this.comboSysType.setSelectedItem(commInfo.getSysType());
			}
		} else {
			TrackRecordTypeEnum trackType = (TrackRecordTypeEnum) this.getUIContext().get("TrackRecordTypeEnum");
			if (trackType != null) {
				trackInfo.setTrackType(trackType);
				this.comboTrackType.setSelectedItem(TrackRecordTypeEnum.Intency);
			} else {
				trackInfo.setTrackType(TrackRecordTypeEnum.Clew);
				this.comboTrackType.setSelectedItem(TrackRecordTypeEnum.Clew);
			}
			this.prmtCommerceChance.setEnabled(false);

			MoneySysTypeEnum sysType = (MoneySysTypeEnum) this.getUIContext().get("MoneySysTypeEnum");
			if (sysType != null) {
				trackInfo.setSysType(sysType);
				this.comboSysType.setSelectedItem(sysType);
			} else {
				trackInfo.setSysType(MoneySysTypeEnum.SalehouseSys);
				this.comboSysType.setSelectedItem(MoneySysTypeEnum.SalehouseSys);
			}
		}
		if (trackInfo.getTrackType() != null && trackInfo.equals(TrackRecordTypeEnum.Intency)) {
			this.prmtCommerceChance.setEnabled(false);
		}

		if (trackEnum != null) {
			trackInfo.setTrackResult(trackEnum);
			this.comboTrackResult.setSelectedItem(trackEnum);
		} else {
			trackInfo.setTrackResult(null);
			this.comboTrackResult.setSelectedItem(null);
		}

		if (custInfo != null)
			trackInfo.setHead(custInfo);
		if (sellProInfo != null)
			trackInfo.setSellProject(sellProInfo);

		if (billIdStr != null)
			trackInfo.setRelationContract(billIdStr);
		if (billNumberStr != null) {
			this.prmtRetionBill.setValue(billNumberStr);
			this.prmtRetionBill.setEnabled(false);
		}

		UserInfo currUser = SysContext.getSysContext().getCurrentUserInfo();
		trackInfo.setSaleMan(currUser);
		this.prmtSaleMan.setValue(currUser);
		trackInfo.setCreator(currUser);
		this.prmtCreator.setValue(currUser);
		trackInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		this.pkCreateTime.setValue(new Timestamp(System.currentTimeMillis()));

		this.attachListeners();
		return trackInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return TrackRecordFactory.getRemoteInstance();
	}

	protected void comboTrackType_itemStateChanged(ItemEvent e) throws Exception {
		// super.comboTrackType_itemStateChanged(e);

		TrackRecordTypeEnum trackType = (TrackRecordTypeEnum) e.getItem();
		if (trackType != null && trackType.equals(TrackRecordTypeEnum.Intency)) {
			this.prmtCommerceChance.setEnabled(true);
			this.prmtCommerceChance.setRequired(true);
		} else {
			this.prmtCommerceChance.setEnabled(false);
		}
		this.prmtCommerceChance.setValue(null);

	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected void attachListeners() {
		this.addDataChangeListener(this.prmtCommerceChance);
		this.addDataChangeListener(this.comboSysType);
		this.addDataChangeListener(this.comboTrackType);
		this.addDataChangeListener(this.comboTrackResult);
		this.addDataChangeListener(this.prmtCustomer);
		this.addDataChangeListener(this.prmtSellProject);
	}

	protected void detachListeners() {
		this.removeDataChangeListener(this.prmtCommerceChance);
		this.removeDataChangeListener(this.comboSysType);
		this.removeDataChangeListener(this.comboTrackType);
		this.removeDataChangeListener(this.comboTrackResult);
		this.removeDataChangeListener(this.prmtCustomer);
		this.removeDataChangeListener(this.prmtSellProject);
	}

	// 客户商机管控中需要调用的
	public KDBizPromptBox getCustomerPrmt() {
		return this.prmtCustomer;
	}

	public KDBizPromptBox getSellProjectPrmt() {
		return this.prmtSellProject;
	}

	public KDBizPromptBox getSaleManPrmt() {
		return this.prmtSaleMan;
	}

	public KDBizPromptBox getReceptionTypePrmt() {
		return this.prmtReceptionType;
	}

	public KDComboBox getTrackTypeCombo() {
		return this.comboTrackType;
	}

	public KDComboBox getSysTypeCombo() {
		return this.comboSysType;
	}

	public KDComboBox getTrackResultCombo() {
		return this.comboTrackResult;
	}

	public KDBizPromptBox getCommercePrmt() {
		return this.prmtCommerceChance;
	}

	public TrackRecordInfo getTrackData() {
		return this.editData;
	}

	public TrackRecordInfo getTrackOldData() {
		return this.editData;
	}

	public void initOldDataNull() {
		super.initOldData(null);
	}
}