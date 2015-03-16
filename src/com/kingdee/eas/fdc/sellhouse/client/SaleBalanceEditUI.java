/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.QuestionTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceFactory;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SaleBlanceSetting;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SettleMentFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SaleBalanceEditUI extends AbstractSaleBalanceEditUI {

	//勾选了入账但未收齐诚意认购金的诚意认购单
	public static String KEY_SINCER = "张未收齐诚意金的的诚意认购单";
	
	//未审批的认购单
	public static String KEY_NOAUDITPUR = "张未审批的认购单";
	
	//未复核的预定单
	public static String KEY_NOAPPLYPREPUR = "张未复核的预定单";
	
	//已审批未交清首付的认购单
	public static String KEY_FIRSTAMOUNTPUR = "张认购单未交清首付";
	
	//退房未审批的退房单
	public static String KEY_NOAUDITQUITROOM = "张未审批的退房单";
	
	//退房审批了但未付完款的退房单
	public static String KEY_CANREFUNDMENTAMOUNT = "张退房审批完但没退完款的退房单";
	
	//变更未审批的变更单
	public static String KEY_NOAUDITPURCHANGE = "张未审批的变更单";
	
	//换房未审批的换房单
	public static String KEY_NOAUDITCHANGEROOM = "张未审批的换房单";
	
	//办理完成按揭但未收齐按揭的认购单
	public static String KEY_LOANNOREV = "张已办理完成按揭但未收齐按揭的认购单";
	
	//办理完成公积金但未收齐公积金的认购单
	public static String KEY_ACCFUNDNOREV = "办理完成公积金但未收齐公积金的认购单";
	
	private static final Logger logger = CoreUIObject.getLogger(SaleBalanceEditUI.class);
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	SaleBlanceSetting saleBlanSetting = new SaleBlanceSetting();

	/**
	 * output class constructor
	 */
	public SaleBalanceEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initControl();
		prmtSellProjectSetViewInfo();
		//prmtPeriodSetEntityView();
	}

	public boolean checkBeforeWindowClosing() {
		// return super.checkBeforeWindowClosing();
		return true;
	}

	private void prmtSellProjectSetViewInfo() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",
		// saleOrg.getId().toString()));
		// filter.getFilterItems().add(new FilterItemInfo("isForSHE",
		// Boolean.TRUE));
		Set idSet = (Set) this.getUIContext().get("sellProjectIdSet");
		view.setFilter(filter);
		if (idSet.size() > 1) {
			filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		}
		this.prmtSellProject.setEntityViewInfo(view);
	}

	public void loadFields() {
		EventListener[] listeners = this.prmtSellProject.getListeners(DataChangeListener.class);
		for (int i = 0; i < listeners.length; i++) {
			this.prmtSellProject.removeDataChangeListener((DataChangeListener) listeners[i]);
		}
		EventListener[] periodListeners = this.prmtPeriod.getListeners(DataChangeListener.class);
		for (int i = 0; i < periodListeners.length; i++) {
			this.prmtPeriod.removeDataChangeListener((DataChangeListener) periodListeners[i]);
		}
		super.loadFields();
		for (int i = 0; i < listeners.length; i++) {
			this.prmtSellProject.addDataChangeListener((DataChangeListener) listeners[i]);
		}

		for (int i = 0; i < periodListeners.length; i++) {
			this.prmtPeriod.addDataChangeListener((DataChangeListener) periodListeners[i]);
		}

	}

	protected void prmtPeriod_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtPeriod_dataChanged(e);
		PeriodInfo period = (PeriodInfo) this.prmtPeriod.getValue();
		if (period != null) {
			this.pkStartDate.setValue(period.getBeginDate());
			this.pkEndDate.setValue(period.getEndDate());
			this.txtRemark.setText(period.getNumber() + "月结");
			this.btnBalanceCheck.setEnabled(true);
			this.btnBalanceConfirm.setEnabled(false);
		}
	}

	protected void prmtPeriodSetEntityView() throws Exception {
		if (this.pkStartDate.getValue() != null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("beginDate", this.pkStartDate.getValue()));
			view.setFilter(filter);
			this.prmtPeriod.setEntityViewInfo(view);
		}

	}

	protected void prmtSellProject_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtSellProject_dataChanged(e);
		SellProjectInfo sellProject = (SellProjectInfo) this.prmtSellProject.getValue();
		Date startDate = null;
		Date endDate = null;
		if (sellProject != null) {
			this.tblReport.removeRows();
			this.tblReport.checkParsed();
			this.actionBalanceCheck.setEnabled(true);
			this.actionBalanceConfirm.setEnabled(false);
			this.pkEndDate.setEnabled(false);
			// 获取上次结算结束日期。
			startDate = this.getLastEndDate(sellProject.getId().toString());
			endDate = this.getLastEndDate(sellProject.getId().toString());
			// startDate = sellProject.getBalanceEndDate();
			// endDate = sellProject.getBalanceEndDate();

			/**
			 * 如果存在上次结算结束日期， 则在上次结算结束日期加1天作为本次结算开始日期， 加1个月减1天为本次结算结束日期。
			 * **/
			if (startDate != null) {
				startDate = SHEHelper.getNextDay(startDate);
				endDate = SHEHelper.getNextDay(endDate);
				this.pkStartDate.setValue(SHEHelper.getDayStart(startDate));
				this.pkStartDate.setEnabled(false);
				endDate = SHEHelper.getNextMonthPreDate(endDate);
				this.pkEndDate.setValue(endDate);
			} else {
				/**
				 * 如果从未结算过， 则取该销售项目的盘次开盘日期为本次销售结算开始日期， 加1个月减1天为本次销售结算结束日期。
				 * */
				try {
					startDate = this.getStartDate(sellProject.getId().toString());
				} catch (Exception e1) {
					handleException(e1);
					e1.printStackTrace();
				}
				if (startDate == null) {
					/**
					 * 如果该销售项目并未开盘，且没有结算过，则取当天时间，通常这个情况不可能出现。
					 * */
					MsgBox.showInfo("该销售项目还没开盘，请检查。");
					return;
				}
			}
		}
	}

	/***
	 * 按销售项目取上次结算的截止日期。
	 * **/

	private Date getLastEndDate(String sellProjectId) throws Exception {
		Date lastEndDate = null;
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql("select FBalanceEndDate from T_SHE_SellProject where FID = ?");
		detailBuilder.addParam(sellProjectId);
		try {
			IRowSet detailSet = detailBuilder.executeQuery();
			if (detailSet.next()) {
				lastEndDate = detailSet.getDate("FBalanceEndDate");
			}
		} catch (Exception e) {
			handleException(e);
			e.printStackTrace();
		}
		return lastEndDate;
	}

	/*****
	 * 按销售项目取盘次的开盘日期。
	 * 
	 * **/
	private Date getStartDate(String sellProjectId) throws Exception {
		Date startDate = null;
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql("select FOrderDate from T_SHE_SellOrder where FProjectID = ?");
		detailBuilder.addParam(sellProjectId);
		try {
			IRowSet detailSet = detailBuilder.executeQuery();
			if (detailSet.next()) {
				startDate = detailSet.getDate("FOrderDate");
			}
		} catch (Exception e) {
			handleException(e);
			e.printStackTrace();
		}
		return startDate;
	}

	private void initControl() {
		this.actionBalanceCheck.setEnabled(true);
		this.actionBalanceConfirm.setEnabled(false);
		this.btnBalanceCheck.setEnabled(true);
		this.btnBalanceConfirm.setEnabled(false);
		this.menuItemBalanceCheck.setEnabled(true);
		this.menuItemBalanceConfirm.setEnabled(false);
		this.actionCopy.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);

		this.actionFirst.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionPre.setVisible(false);
		
		this.actionReset.setVisible(true);
		this.actionReset.setEnabled(true);
		this.btnReset.setEnabled(true);
	
		this.prmtPeriod.setEnabled(false);
		this.pkStartDate.setEnabled(false);
		this.pkEndDate.setEnabled(false);
		this.actionSave.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionSubmit.setEnabled(false);
		
		this.prmtSellProject.setEnabled(false);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (this.prmtSellProject.getValue() == null || this.prmtSellProject.getValue() == "") {
			MsgBox.showInfo("您还未输入销售项目，不能保存!");
			this.abort();
			return;
		}

//		if (this.prmtPeriod.getValue() == null || this.prmtPeriod.getValue() == "") {
//			MsgBox.showInfo("您还未输入月结期间，不能保存!");
//			this.abort();
//			return;
//		}

		if (this.pkStartDate.getValue() == null || this.pkStartDate.getValue() == "") {
			MsgBox.showInfo("您还未输入结算起始日期，不能保存!");
			this.abort();
			return;
		}
		if (this.pkEndDate.getValue() == null || this.pkEndDate.getValue() == "") {
			MsgBox.showInfo("您还未输入结算结束日期，不能保存!");
			this.abort();
			return;
		}
		Date startDate = SHEHelper.getDayStart((Date) this.pkStartDate.getValue());
		Date endDate = SHEHelper.getDayEnd((Date) this.pkEndDate.getValue());
		if (startDate.after(endDate)) {
			MsgBox.showInfo("结算起始日期不能大于结算结束日期!");
			this.abort();
			return;
		}
		Date balanceDate = SHEHelper.getDayEnd((Date) this.pkBalanceDate.getValue());
		if (startDate.after(balanceDate)) {
			MsgBox.showInfo("结算起始日期不能大于结算操作日期!");
			this.abort();
			return;
		}

		if (endDate.after(balanceDate)) {
			MsgBox.showInfo("结算结束日期不能大于结算操作日期!");
			this.abort();
			return;
		}
		super.actionSubmit_actionPerformed(e);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	// 禁止类:未审批的收款单
	private int getNoAudit(SellProjectInfo project) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select count(*) count from t_bdc_fdcreceivingbill ");
		builder.appendSql("where FBillStatus in ('"+RevBillStatusEnum.SAVE_VALUE+"','"+RevBillStatusEnum.SUBMIT_VALUE+"','"+RevBillStatusEnum.AUDITING_VALUE+"') ");
		builder.appendSql("and FRevBizType in ('"+RevBizTypeEnum.CUSTOMER_VALUE+"','"+RevBizTypeEnum.PURCHASE_VALUE+"','"+RevBizTypeEnum.SINCERITY_VALUE+"','"+RevBizTypeEnum.AREACOMPENSATE_VALUE+"') ");
		builder.appendSql("and FSellProjectID ='"+project.getId()+"' ");
		this.appendFilterSql(builder, "FBizDate");

		IRowSet countSet = null;
		// 禁止条数
		int estopCount = 0;
		try {
			countSet = builder.executeQuery();
			if(countSet.next()) {
				estopCount = countSet.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return estopCount;
	}

	// 禁止类二：已审批或收款的收款单未生成凭证
	private int getNoFiVouchered(SellProjectInfo project) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select count(*) count from t_bdc_fdcreceivingbill ");
		builder.appendSql("where FBillStatus in ('"+RevBillStatusEnum.AUDITED_VALUE+"','"+RevBillStatusEnum.RECED_VALUE+"') ");
		builder.appendSql("and FRevBizType in ('"+RevBizTypeEnum.CUSTOMER_VALUE+"','"+RevBizTypeEnum.PURCHASE_VALUE+"','"+RevBizTypeEnum.SINCERITY_VALUE+"','"+RevBizTypeEnum.AREACOMPENSATE_VALUE+"') ");
		builder.appendSql("and (FFiVouchered=0 or FFiVouchered is null)  and FSellProjectID ='"+project.getId()+"' ");
		//新收款单里转款也需要生成凭证，没有红冲的收款单了
//		builder.appendSql("and FRevBillType !='"+RevBillTypeEnum.TRANSFER_VALUE+"' ");
		this.appendFilterSql(builder, "FBizDate");

		IRowSet countSet = null;
		// 禁止条数
		int estopCount = 0;
		try {
			countSet = builder.executeQuery();
			if (countSet.next()) {
				estopCount = countSet.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return estopCount;
	}
	
	//提醒类：诚意认购勾已入账但诚意金未收齐的诚意认购单
	private int getSincerity(SellProjectInfo project)
	{
		FDCSQLBuilder builder3 = new FDCSQLBuilder();
		builder3.appendSql(" select count(*) count from T_SHE_SincerReceiveEntry sinEntry");
		builder3.appendSql(" left join t_she_sinceritypurchase sinPur on sinEntry.fheadid = sinPur.fid");
		builder3.appendSql(" where sinPur.FSellProjectID='" + project.getId().toString() + "'");
		builder3.appendSql(" and sinPur.FIsReceiveEnterAccount=1 ");
		builder3.appendSql("and FappAmount > (isnull(FactRevAmount,0) - isnull(FHasToFeeAmount,0) - isnull(FHasTransferredAmount,0) - isnull(FHasRefundmentAmount,0) - isnull(FHasAdjustedAmount,0)) ");
		this.appendFilterSql(builder3, "sinEntry.FCreateTime");
		IRowSet countSet3 = null;
		//诚意金未收齐条数
		int warningCount2 = 0;
		try {
			countSet3 = builder3.executeQuery();
			if(countSet3.next()) {
				warningCount2 = countSet3.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return warningCount2;
	}

	// 提醒类：未审批的认购单包括认购申请和认购审批中的认购单
	private int getNoAuditPurchase(SellProjectInfo project) {		
		FDCSQLBuilder builder3 = new FDCSQLBuilder();
		builder3.appendSql(" select count(*) count from t_she_purchase purchase ");
		builder3.appendSql(" where purchase.FSellProjectID='" + project.getId().toString() + "'");
		builder3.appendSql(" and (purchase.FPurchaseState='PurchaseApply' or purchase.FPurchaseState='PurchaseAuditing')");
		this.appendFilterSql(builder3, "purchase.FPurchaseDate");
		IRowSet countSet3 = null;
		// 认购未审批提醒条数
		int warningCount2 = 0;
		try {
			countSet3 = builder3.executeQuery();
			if(countSet3.next()) {
				warningCount2 = countSet3.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return warningCount2;
	}
	
	// 提醒类：未复核的预定单
	public int getNoAuditPrePur(SellProjectInfo project)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select count(*) count from t_she_purchase purchase ");
		builder.appendSql(" where purchase.FSellProjectID='" + project.getId().toString() + "'");
		builder.appendSql(" and (purchase.FPurchaseState='PrePurchaseApply')");
		this.appendFilterSql(builder, "purchase.FPrePurchaseDate");
		IRowSet countSet = null;
		// 预定未复核提醒条数
		int warningCount = 0;
		try {
			countSet = builder.executeQuery();
			if(countSet.next()) {
				warningCount = countSet.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return warningCount;
	}

	// 提醒类：认购审批过了的认购单但是没有交清首付
	private int getFisrtAmount(SellProjectInfo project) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select count(*) count from T_SHE_PurchasePayListEntry payList ");
		builder.appendSql(" left join t_she_purchase pur on payList.fheadid = pur.fid");
		builder.appendSql(" left join T_SHE_MoneyDefine define on payList.fmoneydefineid = define.fid");
		builder.appendSql(" where pur.FSellProjectID='" + project.getId().toString() + "' ");
		builder.appendSql("and FappAmount > (isnull(FactRevAmount,0) - isnull(FHasToFeeAmount,0) - isnull(FHasTransferredAmount,0) - isnull(FHasRefundmentAmount,0) - isnull(FHasAdjustedAmount,0)) ");
		builder.appendSql(" and pur.FPurchaseState='PurchaseAudit' and define.FMoneyType='FisrtAmount'");
		this.appendFilterSql(builder, "pur.FAuditTime");
		IRowSet countSet = null;
		int count = 0;
		// 认购审批未交清首付提醒条数
		try {
			countSet = builder.executeQuery();
			if(countSet.next()) {
				count = countSet.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return count;
	}

	// 提醒类：退房未审批提醒条数
	private int getQuitRoom(SellProjectInfo project) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select count(*) count from t_she_quitRoom quitRoom ");
		builder.appendSql(" left join t_she_purchase purchase on quitRoom.fpurchaseid = purchase.fid ");
		builder.appendSql(" where purchase.FSellProjectID='" + project.getId().toString() + "'");
		builder.appendSql(" and (quitRoom.FState='1SAVED' or quitRoom.FState='2SUBMITTED' or quitRoom.FState='3AUDITTING')");
		this.appendFilterSql(builder, "quitRoom.FQuitDate");
		IRowSet countSet = null;
		int count = 0;
		// 退房未审批提醒条数
		try {
			countSet = builder.executeQuery();
			if(countSet.next()) {
				count = countSet.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return count;
	}

	// 提醒类：退房审批但未退完款的提醒条数
	private int getCanRefundmentAmount(SellProjectInfo project) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select quitRoom.fid quitRoomID,sum(payList.FLimitAmount) amount,sum(elsePayList.FLimitAmount) elseAmount from t_she_quitroom quitRoom ");
		builder.appendSql(" left join t_she_purchase pur on quitRoom.fpurchaseid = pur.fid ");
		builder.appendSql(" left join T_SHE_PurchasePayListEntry payList on pur.fid = payList.FHeadID ");
		builder.appendSql(" left join T_SHE_PurchaseElsePayListEntry elsePayList on pur.fid = elsePayList.FHeadID ");
		builder.appendSql(" where pur.FSellProjectID='" + project.getId().toString() + "'");
		builder.appendSql(" and quitRoom.FState='4AUDITTED' ");
		this.appendFilterSql(builder, "quitRoom.FQuitDate");
		builder.appendSql(" group by quitRoom.fid ");
		IRowSet countSet = null;
		int count = 0;
		// 退房审批但未退完款的提醒条数,除了要找付款明细分录的可退金额还要找其他应付分录的可退金额
		//注意的是只是要找提醒条数的话这样写没问题，如果要找具体的金额的话这个SQL就有问题了
		//因为付款明细和其他应付明细都有可能是多条，一起left join的话会让金额重复叠加，但只找大于0的就没问题了
		try {
			countSet = builder.executeQuery();
			while (countSet.next()) {
				BigDecimal amount = countSet.getBigDecimal("amount");
				BigDecimal elseAmount = countSet.getBigDecimal("elseAmount");
				elseAmount = elseAmount==null?new BigDecimal(0):elseAmount;
				amount = amount==null?new BigDecimal(0):amount;
				if (amount.compareTo(new BigDecimal(0)) != 0 || elseAmount.compareTo(new BigDecimal(0))!=0) {
					count++;
				}
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		
		return count;
	}

	// 提醒类：变更未审批的条数
	private int getPurchaseChange(SellProjectInfo project) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select count(*) count from t_she_purchaseChange change ");
		builder.appendSql(" left join t_she_purchase purchase on change.fpurchaseid = purchase.fid ");
		builder.appendSql(" where purchase.FSellProjectID='" + project.getId().toString() + "'");
		builder.appendSql(" and (change.FState='1SAVED' or change.FState='2SUBMITTED' or change.FState='3AUDITTING')");
		this.appendFilterSql(builder, "change.FChangeDate");
		IRowSet countSet = null;
		int count = 0;
		// 变更未审批提醒条数
		try {
			countSet = builder.executeQuery();
			while (countSet.next()) {
				count = countSet.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return count;
	}

	// 提醒类：换房未审批提醒条数
	private int getChangeRoom(SellProjectInfo project) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select count(*) count from t_she_changeRoom change ");
		builder.appendSql(" left join t_she_purchase purchase on change.FOldPurchaseID = purchase.fid ");
		builder.appendSql(" where purchase.FSellProjectID='" + project.getId().toString() + "'");
		builder.appendSql(" and (change.FState='1SAVED' or change.FState='2SUBMITTED' or change.FState='3AUDITTING')");
		this.appendFilterSql(builder, "change.FChangeDate");
		IRowSet countSet = null;
		int count = 0;
		// 换房未审批提醒条数
		try {
			countSet = builder.executeQuery();
			while (countSet.next()) {
				count = countSet.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return count;
	}
	
	
	//提醒类：已完成公积金按揭但未收齐公积金的认购单条数
	private int getAccFundRevCount(SellProjectInfo project) throws BOSException, EASBizException
	{
		int count =0;
		Set purchaseSet = new HashSet();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		//首先找出已经办理完成公积金按揭的认购单
		builder.appendSql(" select purchase.fid purchaseID from T_SHE_RoomLoan loan ");
		builder.appendSql(" left join t_she_purchase purchase on loan.FPurchaseID = purchase.fid ");
		builder.appendSql(" left join T_SHE_moneyDefine moneyDefine on loan.FMmType = moneyDefine.fid ");
		builder.appendSql(" where purchase.FSellProjectID='" + project.getId().toString() + "'");				
		builder.appendSql(" and loan.FAFMortgagedState=3 and moneyDefine.FMoneyType='AccFundAmount'");
		IRowSet countSet = null;
		try {
			countSet = builder.executeQuery();
			while (countSet.next()) {
				String purchaseid = countSet.getString("purchaseID");
				purchaseSet.add(purchaseid);			
			}
			} catch (Exception e) {
				this.handleException(e);
			}
			
		//然后查看这些认购单是否收齐公积金
			if(purchaseSet.size()>0)
			{
				builder = new FDCSQLBuilder();
				builder.appendSql(" select count(*) count from T_SHE_Purchase purchase ");
				builder.appendSql(" left join T_SHE_PurchasePayListEntry payList on payList.fHeadID = purchase.fid ");
				builder.appendSql(" left join T_SHE_moneyDefine moneyDefine on payList.fMoneyDefineID = moneyDefine.fid where ");
				builder.appendParam("purchase.fid", purchaseSet.toArray());
				builder.appendSql(" and purchase.FSellProjectID='" + project.getId().toString() + "'");
				builder.appendSql(" and payList.FappAmount > (isnull(payList.FactRevAmount,0) - isnull(payList.FHasToFeeAmount,0) - isnull(payList.FHasTransferredAmount,0) - isnull(payList.FHasRefundmentAmount,0) - isnull(payList.FHasAdjustedAmount,0)) ");
				builder.appendSql(" and moneyDefine.FMoneyType='AccFundAmount' and purchase.FPurchaseState='PurchaseAudit' ");
				this.appendFilterSql(builder, "purchase.FAuditTime ");
				try {
					countSet = builder.executeQuery();
					while (countSet.next()) {
						count = countSet.getInt("count");
					}
				} catch (Exception e) {
					this.handleException(e);
				}						
			}	
		return count;
	}
	
	//提醒类：已完成按揭但未收齐按揭款的认购单条数
	private int getLoanNoRevCount2(SellProjectInfo project) throws BOSException, EASBizException
	{
		int count =0;
		Set purchaseSet = new HashSet();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		//首先找出已经办理完成按揭的认购单
		builder.appendSql(" select purchase.fid purchaseID from T_SHE_RoomLoan loan ");
		builder.appendSql(" left join t_she_purchase purchase on loan.FPurchaseID = purchase.fid ");
		builder.appendSql(" left join T_SHE_moneyDefine moneyDefine on loan.FMmType = moneyDefine.fid ");
		builder.appendSql(" where purchase.FSellProjectID='" + project.getId().toString() + "'");				
		builder.appendSql(" and loan.FAFMortgagedState=3 and moneyDefine.FMoneyType='LoanAmount'");
		IRowSet countSet = null;
		try {
			countSet = builder.executeQuery();
			while (countSet.next()) {
				String purchaseid = countSet.getString("purchaseID");
				purchaseSet.add(purchaseid);			
			}
			} catch (Exception e) {
				this.handleException(e);
			}
			
		//然后查看这些认购单是否收齐按揭款
			if(purchaseSet.size()>0)
			{
				builder = new FDCSQLBuilder();
				builder.appendSql(" select count(*) count from T_SHE_Purchase purchase ");
				builder.appendSql(" left join T_SHE_PurchasePayListEntry payList on payList.fHeadID = purchase.fid ");
				builder.appendSql(" left join T_SHE_moneyDefine moneyDefine on payList.fMoneyDefineID = moneyDefine.fid where ");
				builder.appendParam("purchase.fid", purchaseSet.toArray());
				builder.appendSql(" and purchase.FSellProjectID='" + project.getId().toString() + "'");
				builder.appendSql(" and payList.FappAmount > (isnull(payList.FactRevAmount,0) - isnull(payList.FHasToFeeAmount,0) - isnull(payList.FHasTransferredAmount,0) - isnull(payList.FHasRefundmentAmount,0) - isnull(payList.FHasAdjustedAmount,0)) ");
				builder.appendSql(" and moneyDefine.FMoneyType='LoanAmount' and purchase.FPurchaseState='PurchaseAudit' ");
				this.appendFilterSql(builder, "purchase.FAuditTime ");
				try {
					countSet = builder.executeQuery();
					while (countSet.next()) {
						count = countSet.getInt("count");
					}
				} catch (Exception e) {
					this.handleException(e);
				}						
			}	
		return count;
	}
	
	//  下面这个方法把公积金和按揭放在一起校验。当办理按揭的房间过多时可能会引起性能问题，先把他们拆开
//	//提醒类：已完成按揭但未收齐按揭款的认购单条数
//	private int getLoanNoRevCount(SellProjectInfo project) throws BOSException, EASBizException
//	{
//		int count = 0;
//		//找出已办理完成或者还未办理的按揭或者公积金
//		Set purchaseSet = new HashSet();
//		Map purchaseMap = new HashMap();
//		FDCSQLBuilder builder = new FDCSQLBuilder();
//		builder.appendSql(" select purchase.fid purchaseID,moneyDefine.FMoneyType moneyType from T_SHE_RoomLoan loan ");
//		builder.appendSql(" left join t_she_purchase purchase on loan.FPurchaseID = purchase.fid ");
//		builder.appendSql(" left join T_SHE_moneyDefine moneyDefine on loan.FMmType = moneyDefine.fid ");
//		builder.appendSql(" where purchase.FSellProjectID='" + project.getId().toString() + "'");				
//		builder.appendSql(" and loan.FAFMortgagedState='3' and (moneyDefine.FMoneyType='LoanAmount' or moneyDefine.FMoneyType='AccFundAmount')");
//		builder.appendSql(" order by moneyDefine.FMoneyType ");
//		IRowSet countSet = null;
//		try {
//			countSet = builder.executeQuery();
//			while (countSet.next()) {
//				String purchaseid = countSet.getString("purchaseID");
//				String moneyType = countSet.getString("moneyType");
//				if(purchaseMap.get(purchaseid)!=null)
//				{
//					String str = (String)purchaseMap.get(purchaseid);
//					purchaseMap.put(purchaseid, str+","+moneyType);
//				}else
//				{
//					purchaseSet.add(purchaseid);
//					purchaseMap.put(purchaseid, moneyType);
//				}				
//			}
//			} catch (Exception e) {
//				this.handleException(e);
//			}
//		
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("id",purchaseSet,CompareType.INCLUDE));
//		SelectorItemCollection sel=new SelectorItemCollection();
//		sel.add("payType.id");
//		sel.add("payType.name");
//		sel.add("payType.payLists.id");
//		sel.add("payType.payLists.moneyDefine");
//		sel.add("payType.payLists.moneyDefine.moneyType");
//		view.setFilter(filter);
//		view.setSelector(sel);
//		PurchaseCollection purColl = PurchaseFactory.getRemoteInstance().getPurchaseCollection(view);
//		for(int i=0;i<purColl.size();i++)
//		{
//			PurchaseInfo pur = purColl.get(i);
//			SHEPayTypeInfo payInfo = pur.getPayType();
//			SelectorItemCollection sic=new SelectorItemCollection();			
//			sic.add("payLists.id");
//			sic.add("payLists.name");
//			sic.add("payLists.moneyDefine.*");
//			sic.add("payLists.moneyDefine.name");
//			sic.add("payLists.moneyDefine.moneyType");
//			payInfo = SHEPayTypeFactory.getRemoteInstance().getSHEPayTypeInfo(new ObjectUuidPK(payInfo.getId()), sic);
//			PayListEntryCollection payColl =  payInfo.getPayLists();
//			StringBuffer string = new StringBuffer();
//			for(int j=0;j<payColl.size();j++)
//			{
//				PayListEntryInfo payEntryInfo = payColl.get(j);
//				MoneyTypeEnum mon = payEntryInfo.getMoneyDefine().getMoneyType();
//				if(MoneyTypeEnum.LoanAmount.equals(mon))
//				{
//					if(string.length()>0)
//					{
//						string.append(",");
//						string = string.append(MoneyTypeEnum.LOANAMOUNT_VALUE);
//					}else
//					{
//						string = string.append(MoneyTypeEnum.LOANAMOUNT_VALUE);
//					}
//					
//				}else if(MoneyTypeEnum.AccFundAmount.equals(mon))
//				{
//					//因为上面进行排序了accfundAmount肯定会在前面所以在这处理下
//					if(string.length()>0)
//					{
//						StringBuffer str = new StringBuffer();
//						str.append(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE);
//						str.append(",");
//						string = str.append(string);
//					}else
//					{
//						string = string.append(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE);
//					}
//				}
//			}
//			String moneyType = (String)purchaseMap.get(pur.getId().toString());
//			if(!moneyType.equals(string.toString()))
//			{
//				purchaseSet.remove(pur.getId().toString());
//			}
//		}	
//		if(purchaseSet.size()>0)
//		{
//			builder = new FDCSQLBuilder();
//			builder.appendSql(" select purchaseID,sum(appAmount) appAmount,sum(actRevAmount) revAmount from (");
//			builder.appendSql(" select purchase.fid purchaseID,payList.FappAmount appAmount,payList.FActRevAmount actRevAmount from T_SHE_Purchase purchase ");
//			builder.appendSql(" left join T_SHE_PurchasePayListEntry payList on payList.fHeadID = purchase.fid ");
//			builder.appendSql(" left join T_SHE_moneyDefine moneyDefine on payList.fMoneyDefineID = moneyDefine.fid where ");
//			builder.appendParam("purchase.fid", purchaseSet.toArray());
//			builder.appendSql(" and purchase.FSellProjectID='" + project.getId().toString() + "'");
//			builder.appendSql(" and payList.FappAmount > isnull(payList.FActRevAmount,0) and moneyDefine.FMoneyType='LoanAmount' ");
//			builder.appendSql(" and purchase.FPurchaseState='PurchaseAudit' ");
//			this.appendFilterSql(builder, "purchase.FAuditTime ");
//			builder.appendSql(" union ");
//			builder.appendSql(" select purchase.fid purchaseID,payList.FappAmount appAmount,payList.FActRevAmount actRevAmount from T_SHE_Purchase purchase ");
//			builder.appendSql(" left join T_SHE_PurchasePayListEntry payList on payList.fHeadID = purchase.fid ");
//			builder.appendSql(" left join T_SHE_moneyDefine moneyDefine on payList.fMoneyDefineID = moneyDefine.fid where ");
//			builder.appendParam("purchase.fid", purchaseSet.toArray());
//			builder.appendSql(" and purchase.FSellProjectID='" + project.getId().toString() + "'");
//			builder.appendSql(" and payList.FappAmount > isnull(payList.FActRevAmount,0) and moneyDefine.FMoneyType='AccFundAmount' ");
//			builder.appendSql(" and purchase.FPurchaseState='PurchaseAudit' ");
//			this.appendFilterSql(builder, "purchase.FAuditTime ");
//			builder.appendSql(" ) aa group by purchaseID");
//			try {
//				countSet = builder.executeQuery();
//				while (countSet.next()) {
//					String purchaseID = countSet.getString("purchaseID");
//					BigDecimal appAmount = countSet.getBigDecimal("appAmount");
//					BigDecimal revAmount = countSet.getBigDecimal("revAmount");
//					appAmount = appAmount==null?new BigDecimal(0):appAmount;
//					revAmount = revAmount==null?new BigDecimal(0):revAmount;
//					if(appAmount.compareTo(revAmount)==1)
//					{
//						count++;
//					}
//				}
//			} catch (Exception e) {
//				this.handleException(e);
//			}						
//		}		
//		return count;
//	}

	private void show(int type, int count, SellProjectInfo project, String str) {
		this.tblReport.checkParsed();
		IRow row = tblReport.addRow();
		this.showTblReport(type, row, count, project, str);
	}
	
	private int checkSaleBlance(Object obj,int count,String str,SellProjectInfo project)
	{
		int estopCount =0;
		if(count!=0)
		{
			if(obj instanceof QuestionTypeEnum)
			{
				if(QuestionTypeEnum.prompt.equals((QuestionTypeEnum)obj))
				{
					show(1, count, project, str);
				}else if(QuestionTypeEnum.estop.equals((QuestionTypeEnum)obj))
				{
					show(0, count, project, str);
					estopCount = count;
				}else
				{
					//如果设置是不提示那就什么都不做
				}
			}
		}
		return estopCount;
	}
	
	/**
	 *结算检查
	 */
	protected void btnBalanceCheck_actionPerformed(ActionEvent e) throws Exception {
		this.tblReport.removeRows();
		SellProjectInfo project = (SellProjectInfo) this.prmtSellProject.getValue();
		// 禁止类:未审批的收款单
		int estopCount = this.getNoAudit(project);
		if (estopCount != 0) {
			String str = "张未审批的收款单";
			show(0, estopCount, project, str);
		}
		// 禁止类二：已审批或收款的收款单未生成凭证
		int estopCount0 = this.getNoFiVouchered(project);
		if (estopCount0 != 0) {
			String str = "张未生成凭证的收款单";
			show(0, estopCount0, project, str);
		}
		
		//提醒类：勾选已入账的诚意认购单未收齐诚意金 的
		int estopCount1 = checkSaleBlance(saleBlanSetting.getSincerObject(),this.getSincerity(project),
				SaleBalanceEditUI.KEY_SINCER,project);
		
		//提醒类：未审批的认购单包括认购申请和认购审批中的认购单
		int estopCount2 = checkSaleBlance(saleBlanSetting.getNoAuditPur(),getNoAuditPurchase(project),
				SaleBalanceEditUI.KEY_NOAUDITPUR,project);
		
		// 提醒类：未复核的预定单包括预定审批的认购单
		int estopCount3 = checkSaleBlance(saleBlanSetting.getNoApplyPrePur(),getNoAuditPrePur(project),
				SaleBalanceEditUI.KEY_NOAPPLYPREPUR,project);

		// 提醒类：认购审批过了的认购单单是没有交清首付
		int estopCount4 = checkSaleBlance(saleBlanSetting.getFirstAmountPur(),getFisrtAmount(project),
				SaleBalanceEditUI.KEY_FIRSTAMOUNTPUR,project);

		// 提醒类：退房未审批提醒条数
		int estopCount5 = checkSaleBlance(saleBlanSetting.getNoAuditQuitRoom(),getQuitRoom(project),
				SaleBalanceEditUI.KEY_NOAUDITQUITROOM,project);
		

		// 提醒类：退房审批但未退完款的提醒条数
		int estopCount6 = checkSaleBlance(saleBlanSetting.getCanRefundmentAmount(),getCanRefundmentAmount(project),
				SaleBalanceEditUI.KEY_CANREFUNDMENTAMOUNT,project);
		
		// 提醒类：变更未审批的条数
		int estopCount7 = checkSaleBlance(saleBlanSetting.getNoAuditPurChange(),getPurchaseChange(project),
				SaleBalanceEditUI.KEY_NOAUDITPURCHANGE,project);

		// 提醒类：换房未审批的提醒条数
		int estopCount8 = checkSaleBlance(saleBlanSetting.getNoAuditChangeRoom(),getChangeRoom(project),
				SaleBalanceEditUI.KEY_NOAUDITCHANGEROOM,project);
		
//		//提醒类：已完成按揭但未收取按揭款的认购单条数
//		int estopCount9 = checkSaleBlance(saleBlanSetting.getLoanNoRev(),getLoanNoRevCount(project),
//				SaleBalanceEditUI.KEY_LOANNOREV,project);
		//提醒类：已完成按揭但未收取按揭款的认购单条数
		int estopCount9 = checkSaleBlance(saleBlanSetting.getLoanNoRev(),getLoanNoRevCount2(project),
				SaleBalanceEditUI.KEY_LOANNOREV,project);
		
		int estopCount10 = checkSaleBlance(saleBlanSetting.getAccFundNoRev(),getAccFundRevCount(project),
				SaleBalanceEditUI.KEY_ACCFUNDNOREV,project);
		
		if (estopCount == 0 && estopCount0 == 0 && estopCount1 == 0 && estopCount2 == 0 && estopCount3 == 0 && 
				estopCount4 == 0 && estopCount5 == 0 && estopCount6 == 0 && estopCount7 == 0 && estopCount8 == 0 && estopCount9 ==0 && estopCount10 ==0) {
			Date beginDate = (Date) this.pkStartDate.getValue();
			Date endDate = (Date) this.pkEndDate.getValue();
			Date bizDate = SHEHelper.getDayEnd((Date) this.pkBalanceDate.getValue());
			if (endDate == null) {
				MsgBox.showInfo("月结结束日期不能为空!");
				this.abort();
			}
			if (endDate.after(beginDate) == false && endDate.equals(beginDate) == false) {
				MsgBox.showInfo("月结结束日期不能在月结起始日期之前,没有禁止项也不能正式月结!");
				this.abort();
			}
			if (endDate.after(bizDate)) {
				MsgBox.showInfo("月结结束日期不能在月结操作日期之后,没有禁止项也不能正式月结!");
				this.abort();
			}
			this.btnBalanceConfirm.setEnabled(true);
		} else {
			this.btnBalanceConfirm.setEnabled(false);
		}
		super.btnBalanceCheck_actionPerformed(e);
	}

	protected void showTblReport(int type, IRow row, int count, SellProjectInfo project, String str) {
		if (type == 0) {
			row.getCell("problemType").setValue("禁止");
		} else {
			row.getCell("problemType").setValue("提示");
		}
		row.getCell("problemType").getStyleAttributes().setLocked(true);
		row.getCell("problemDesc").setValue(project.getName() + "有" + count + str + "!");
		row.getCell("problemDesc").getStyleAttributes().setLocked(true);
	}

	public void appendFilterSql(FDCSQLBuilder builder, String proName) {		
		if (this.prmtSellProject.getValue() == null || this.prmtSellProject.getValue() == "") {
			MsgBox.showInfo("销售项目不能为空。");
			this.abort();
			return;
		}

		if (this.prmtPeriod.getValue() == null || this.prmtPeriod.getValue() == "") {
			MsgBox.showInfo("月结期间不能为空。");
			this.abort();
			return;
		}
		
		Date beginDate = (Date) this.pkStartDate.getValue();
		if (beginDate == null) {
			MsgBox.showInfo("月结起始日期不能为空!");
			this.abort();
		}
		if (beginDate != null) {
			builder.appendSql(" and " + proName + " >= ? ");
			builder.addParam(FDCDateHelper.getSqlDate(beginDate));
		}
		
		Date endDate = (Date) this.pkEndDate.getValue();
//		Date bizDate = SHEHelper.getDayEnd((Date) this.pkBalanceDate.getValue());
//		if (endDate == null) {
//			MsgBox.showInfo("结算结束日期不能为空!");
//			this.abort();
//		}
//		if (endDate.after(beginDate) == false && endDate.equals(beginDate) == false) {
//			MsgBox.showInfo("结算结束日期不能在结束起始日期之前");
//			this.abort();
//		}
//		if (endDate.after(bizDate)) {
//			MsgBox.showInfo("结算结束日期不能在结算操作日期之后!");
//			this.abort();
//		}
		if (endDate != null) {
			builder.appendSql(" and " + proName + " < ? ");
			builder.addParam(FDCDateHelper.getNextDay(endDate));
		}
	}

	/**
	 * output actionBalanceCheck_actionPerformed
	 */
	public void actionBalanceCheck_actionPerformed(ActionEvent e) throws Exception {
		super.actionBalanceCheck_actionPerformed(e);
	}

	/**
	 * output actionBalanceConfirm_actionPerformed
	 */
	public void actionBalanceConfirm_actionPerformed(ActionEvent e) throws Exception {
		SellProjectInfo project = (SellProjectInfo) this.prmtSellProject.getValue();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",project.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("startDate",this.pkStartDate.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("operateType",SaleBalanceTypeEnum.Balance));
		if(SaleBalanceFactory.getRemoteInstance().exists(filter)){
			this.btnBalanceConfirm.setEnabled(false);
			this.btnBalanceCheck.setEnabled(false);
			this.btnReset.setEnabled(true);
			MsgBox.showInfo("该月结期间已经月结。");
			return;
		}
		
		if (this.getNoAudit(project) != 0 || this.getNoFiVouchered(project) != 0) {
			MsgBox.showInfo("存在禁止月结的业务，请在数据检查中查看详细情况，不可进行销售月结!");
			this.abort();
		} 
//		else if (this.getNoAuditPurchase(project) != 0 || this.getFisrtAmount(project) != 0 || this.getQuitRoom(project) != 0 || this.getCanRefundmentAmount(project) != 0
//				|| this.getPurchaseChange(project) != 0 || this.getChangeRoom(project) != 0 || this.getSincerity(project)!=0 || this.getNoAuditPrePur(project)!=0) {
//			if (MsgBox.showConfirm2New(this, "存在提醒类业务，请在数据检查中查看详细情况,是否进行月结?") == MsgBox.YES) {
//				this.storeFields();
//				SaleBalanceInfo saleBalanceInfo = this.editData;
//				saleBalanceInfo.setId(BOSUuid.create(saleBalanceInfo.getBOSType()));
//
//				try {
//					SaleBalanceFactory.getRemoteInstance().addnew(saleBalanceInfo);
//					SettleMentFacadeFactory.getRemoteInstance().dealSaleBalance(new ObjectUuidPK(saleBalanceInfo.getId()));
//					this.btnBalanceConfirm.setEnabled(false);
//					this.btnReset.setEnabled(false);
//					MsgBox.showInfo("月结成功！");
//					// 下一个期间是否存在
//					PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(saleBalanceInfo.getPeriod());
//					if (nextPeriod == null || nextPeriod.getId() == null) {
//						MsgBox.showInfo("下一个期间不存在，请重新设置");
//						this.abort();
//					}else
//					{
//						SellProjectInfo projectInfo = saleBalanceInfo.getSellProject();
//						projectInfo.setSaleNowTerm(new Integer(nextPeriod.getNumber()).toString());
//						projectInfo.setSaleNowPeriod(nextPeriod);
//						SelectorItemCollection sels = new SelectorItemCollection();
//						sels.add("saleNowTerm");
//						sels.add("saleNowPeriod.id");
//						SellProjectFactory.getRemoteInstance().updatePartial(projectInfo, sels);
//					}
//					this.uiWindow.close();
//				} catch (Exception ee) {
//					SaleBalanceFactory.getRemoteInstance().delete(new ObjectUuidPK(saleBalanceInfo.getId()));
//					this.handleException(ee);
//					this.abort();
//				}
//			}
//		} 
		else {
			this.storeFields();
			SaleBalanceInfo saleBalanceInfo = this.editData;
			saleBalanceInfo.setId(BOSUuid.create(saleBalanceInfo.getBOSType()));

			try {
				SaleBalanceFactory.getRemoteInstance().addnew(saleBalanceInfo);
				SettleMentFacadeFactory.getRemoteInstance().dealSaleBalance(new ObjectUuidPK(saleBalanceInfo.getId()));
				this.btnBalanceConfirm.setEnabled(false);
				this.btnReset.setEnabled(false);
				MsgBox.showInfo("月结成功！");
				// 下一个期间是否存在
				PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(saleBalanceInfo.getPeriod());
				if (nextPeriod == null || nextPeriod.getId() == null) {
					MsgBox.showInfo("下一个期间不存在，请重新设置");
					this.abort();
				}else
				{
					SellProjectInfo projectInfo = saleBalanceInfo.getSellProject();
					projectInfo.setSaleNowTerm(new Integer(nextPeriod.getNumber()).toString());
					projectInfo.setSaleNowPeriod(nextPeriod);
					SelectorItemCollection sels = new SelectorItemCollection();
					sels.add("saleNowTerm");
					sels.add("saleNowPeriod.id");
					SellProjectFactory.getRemoteInstance().updatePartial(projectInfo, sels);
				}
				this.uiWindow.close();
			} catch (Exception ee) {
				SaleBalanceFactory.getRemoteInstance().delete(new ObjectUuidPK(saleBalanceInfo.getId()));
				this.handleException(ee);
				this.abort();
			}
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("salePeriod.*");
		sels.add("saleNowPeriod.*");
		return super.getSelectors();
	}

	protected IObjectValue createNewData() {
		SaleBalanceInfo info = new SaleBalanceInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");

		Date startDate = null;
		Date endDate = null;
		if (sellProject != null) {
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("salePeriod.*");
			sels.add("saleNowPeriod.*");
			try {
				sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProject.getId()),sels);
			} catch (EASBizException e2) {
				this.handleException(e2);
			} catch (BOSException e2) {
				this.handleException(e2);
			}
			info.setSellProject(sellProject);
			if(sellProject.getSaleNowPeriod()==null)
			{
				MsgBox.showInfo("该销售项目还没有进行月结初始化，请到销售项目月结状态表进行初始化");
				this.abort();
			}else
			{
				info.setPeriod(sellProject.getSaleNowPeriod());
			}
			PeriodInfo periodInfo = sellProject.getSaleNowPeriod();
			startDate = periodInfo.getBeginDate();
			endDate = periodInfo.getEndDate();
			info.setStartDate(startDate);
			info.setEndDate(endDate);
		}
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		info.setBalanceDate(new Timestamp(System.currentTimeMillis()));
		info.setOperateType(SaleBalanceTypeEnum.Balance);
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setCreateTime(new Timestamp(System.currentTimeMillis()));
		info.setLastBalancDate(new Timestamp(System.currentTimeMillis()));
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SaleBalanceFactory.getRemoteInstance();
	}

}