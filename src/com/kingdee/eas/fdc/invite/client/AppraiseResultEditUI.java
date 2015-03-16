/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.VerticalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMultiLangArea;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.ProgrammingContractEditUI;
import com.kingdee.eas.fdc.invite.AppraiseResultEntryCollection;
import com.kingdee.eas.fdc.invite.AppraiseResultEntryInfo;
import com.kingdee.eas.fdc.invite.AppraiseResultFactory;
import com.kingdee.eas.fdc.invite.AppraiseResultInfo;
import com.kingdee.eas.fdc.invite.EffectivenessEnum;
import com.kingdee.eas.fdc.invite.IInviteFileMerge;
import com.kingdee.eas.fdc.invite.InviteFileMergeCollection;
import com.kingdee.eas.fdc.invite.InviteFileMergeFactory;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteProjectStateEnum;
import com.kingdee.eas.fdc.invite.NewQuotingPriceCollection;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.QuotingPriceStatusEnum;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryInfo;
import com.kingdee.eas.fdc.invite.client.offline.util.AttachmentPermissionUtil;
import com.kingdee.eas.fdc.migrate.StringUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;

/** 
 * 评标结果报审 编辑界面
 */
public class AppraiseResultEditUI extends AbstractAppraiseResultEditUI
{
	private boolean isContainAttachment = false;
	
	private static final Logger logger = CoreUIObject.getLogger(AppraiseResultEditUI.class);

	private FullOrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo();

	public static final String resourcePath = "com.kingdee.eas.fdc.invite.FDCInviteResource";
	//private ArrayList entryInfoList = null;  
	
	/**
	 * output class constructor
	 */
	public AppraiseResultEditUI() throws Exception
	{
		super();
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionSave_actionPerformed(e);
		this.btnAttachment.setVisible(true);
		initTableStyle();
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
	{
		boolean hasBid = false ;
		BigDecimal amountTotal = FDCHelper.ZERO;// 中标金额总金额
		for (int i = 0; i < kdtEntrys.getRowCount(); ++i) {
			IRow row = kdtEntrys.getRow(i);
			Object bidAmoutObj = row.getCell("bidAmount").getValue();// 投标报价
			Object treatAmountObj = row.getCell("treatAmount").getValue();// 谈判价
				Boolean tmpHit = (Boolean)row.getCell("hit").getValue();
			if (tmpHit.booleanValue()) {
				if (treatAmountObj != null) {
					amountTotal = amountTotal.add(FDCHelper.toBigDecimal(treatAmountObj));
				} else {
					if (bidAmoutObj != null) {
						amountTotal = amountTotal.add(FDCHelper.toBigDecimal(bidAmoutObj));
				}
			}
				hasBid = true;
		}
		}
		if (!hasBid) {
			FDCMsgBox.showWarning(this, "没有选择中标单位，不能执行此操作。");
			abort();
		}
		if (isContactProgrammingContract()) {
			BigDecimal amount = fecthProgrammingAmount();// 规划金额
			if (amount == null) {
				amount = FDCHelper.ZERO;
			}
			if (amount.compareTo(amountTotal) < 0) {
				if (amount.compareTo(FDCHelper.ZERO) == 0) {
					int confirm = FDCMsgBox.showConfirm2("中标金额" + amountTotal + "大于合约规划金额" + amount + "，超出了100%，是否提交？选择是，继续操作，选择否返回当前界面。");
					if (MsgBox.CANCEL == confirm) {
						SysUtil.abort();
					}
				} else if (amount.compareTo(FDCHelper.ZERO) > 0) {
					int confirm = FDCMsgBox.showConfirm2("中标金额" + amountTotal + "大于合约规划金额" + amount + "，超出了"
							+ FDCHelper.divide(amountTotal, amount).subtract(FDCHelper.ONE).multiply(FDCHelper.ONE_HUNDRED)
							+ "%，是否提交？选择是，继续操作，选择否返回当前界面。");
					if (MsgBox.CANCEL == confirm) {//   
						SysUtil.abort();
					}
				}
			}
		}
		super.actionSubmit_actionPerformed(e);
		if (STATUS_EDIT.equals(this.getOprtState())) {
			if (this.editData != null && FDCBillStateEnum.SUBMITTED.equals(this.editData.getState())) {
				this.actionSave.setEnabled(false);
			}
		}
		
		initTableStyle();
		fillAttachmentList();
	}
	
	private boolean isContactProgrammingContract() {
		InviteProjectInfo inviteProjectObj = (InviteProjectInfo) prmtInviteProject.getData();
		if (inviteProjectObj != null) {
			if (inviteProjectObj.getProgrammingContract() != null) {
				return true;
			}
		}
		return false;
	}
	private BigDecimal fecthProgrammingAmount() {
		InviteProjectInfo inviteProjectObj = (InviteProjectInfo) prmtInviteProject.getData();
		if (inviteProjectObj != null) {
			ProgrammingContractInfo programmingContract = inviteProjectObj.getProgrammingContract();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("amount");
			try {
				ProgrammingContractInfo pcInfoWithAmount = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(
						new ObjectUuidPK(programmingContract.getId()), sic);
				BigDecimal amount = pcInfoWithAmount.getAmount();
				if (!FDCHelper.isEmpty(amount)) {
					return amount;
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		return FDCHelper.ZERO;
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		fillAttachmentList();
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionEdit_actionPerformed(e);

		/*
		 *   BT361821描述 ：修改状态的单据，查看界面点修改  保存按钮应该置灰
		 *   修改时间：2009-09-24 兰远军
		 *   
		 * 
		 */
		
		if(STATUS_EDIT.equals(this.getOprtState()))
		{
			if(this.editData!=null&&FDCBillStateEnum.SUBMITTED.equals(this.editData.getState()))
			{
				this.actionSave.setEnabled(false);
			}
		}
		this.actionPreHit.setEnabled(true);
		this.actionUnPreHit.setEnabled(true);
		this.actionHit.setEnabled(true);
		this.actionUnHit.setEnabled(true);
		this.actionVirtualPreSplit.setEnabled(true);
		this.actionAttachment.setVisible(true);
	}
   
	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}


	public SelectorItemCollection getSelectors() {

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("creator.*"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("auditor.*"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("inviteProject.*"));
		sic.add(new SelectorItemInfo("entrys.score"));
		sic.add(new SelectorItemInfo("entrys.isPartIn"));
		sic.add(new SelectorItemInfo("entrys.expertAppraiseSeq"));
		sic.add(new SelectorItemInfo("entrys.bidAmount"));
		sic.add(new SelectorItemInfo("entrys.treatAmount"));
		sic.add(new SelectorItemInfo("entrys.totalSeq"));
		sic.add(new SelectorItemInfo("entrys.isImport"));
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("entrys.supplier.number"));
		sic.add(new SelectorItemInfo("entrys.supplier.name"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("inviteProject.name"));
		sic.add(new SelectorItemInfo("inviteProject.project.name"));
		sic.add(new SelectorItemInfo("inviteProject.inviteForm"));
		sic.add(new SelectorItemInfo("inviteProject.programmingContract.*"));
		sic.add(new SelectorItemInfo("inviteProject.programmingContract.inviteOrg.*"));
		sic.add(new SelectorItemInfo("inviteProject.programmingContract.amount"));
		sic.add(new SelectorItemInfo("inviteProject.inviteMode.*"));
		sic.add(new SelectorItemInfo("inviteProject.orgUnit.*"));

		sic.add(new SelectorItemInfo("orgUnit"));
		return sic;
	}
	
	protected IObjectValue createNewData() {
		AppraiseResultInfo object = new AppraiseResultInfo();
		object.setOrgUnit(orgUnitInfo);
		object.setState(FDCBillStateEnum.SAVED);
		object.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		Date createDate = new Date();
		object.setCreateTime(new Timestamp(createDate.getTime()));		
		return object;
	}


	protected IObjectValue createNewDetailData(KDTable entryInfo) {
		AppraiseResultEntryInfo appEntryInfo = new AppraiseResultEntryInfo();
		appEntryInfo.setIsPartIn(true);
		return appEntryInfo;
	}


	protected ICoreBase getBizInterface() throws Exception {

		return AppraiseResultFactory.getRemoteInstance();
	}


	public ICoreBase getBillInterface() throws Exception {

		return super.getBillInterface();
	}



	protected void initWorkButton() {

		super.initWorkButton();
		// 设置查看文件按钮图标 add by msb 2010/04/27
		actionAssoViewBill.putValue(Action.SMALL_ICON,EASResource.getIcon("imgTbtn_seeperformance"));
				
		// 从initTableStyle中移过来的代码
		// 设置预中标、反预中标、中标和反中标按钮，
		remove(this.btnPreHit);
		remove(this.btnUnPreHit);
		remove(this.btnHit);
		remove(this.btnUnHit);
		
		kDContainer1.addButton(this.btnPreHit);
		kDContainer1.addButton(this.btnUnPreHit);
		kDContainer1.addButton(this.btnHit);
		kDContainer1.addButton(this.btnUnHit);

		this.btnHit.setIcon(EASResource.getIcon("imgTbtn_grantcollocate"));
		this.btnUnHit.setIcon(EASResource.getIcon("imgTbtn_cancelcollocate"));

	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		initHeadStyle();
		initTableStyle();
		initPrmtInviteProject();

		if(getOprtState().equals(OprtState.VIEW))
		{
			if(!checkCanOperate())
			{
				this.actionAddNew.setEnabled(false);
				this.actionEdit.setEnabled(false);
				this.actionSubmit.setEnabled(false);
				this.actionRemove.setEnabled(false);

				this.actionPreHit.setEnabled(false);
				this.actionUnPreHit.setEnabled(false);
				this.actionHit.setEnabled(false);
				this.actionUnHit.setEnabled(false);
				this.actionVirtualPreSplit.setEnabled(false);
			}
			
			else
			{
				if(editData.getState().equals(FDCBillStateEnum.SUBMITTED))
				{
					this.actionAddNew.setEnabled(true);
					this.actionEdit.setEnabled(true);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(true);
				}
				else if(editData.getState().equals(FDCBillStateEnum.AUDITTED))
				{
					this.actionAddNew.setEnabled(false);
					this.actionEdit.setEnabled(false);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(false);
				}
				else
				{
					this.actionAddNew.setEnabled(true);
					this.actionEdit.setEnabled(true);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(false);
				}

				this.actionPreHit.setEnabled(false);
				this.actionUnPreHit.setEnabled(false);
				this.actionHit.setEnabled(false);
				this.actionUnHit.setEnabled(false);
				this.actionVirtualPreSplit.setEnabled(false);
			}
		}

		else if(getOprtState().equals(OprtState.EDIT)){
			if(editData.getState().equals(FDCBillStateEnum.SUBMITTED))
			{
				this.actionAddNew.setEnabled(true);
				this.actionEdit.setEnabled(false);
				this.actionSave.setEnabled(false);
				this.actionSubmit.setEnabled(true);
				this.actionRemove.setEnabled(true);
			}

		}
		
		this.kdtEntrys.addKDTSelectListener(new KDTSelectListener(){

			public void tableSelectChanged(KDTSelectEvent e) {
				// TODO Auto-generated method stub
				IRow row = kdtEntrys.getRow(kdtEntrys.getSelectManager().getActiveRowIndex());
				if(row.getCell("effective") != null){
					if(row.getCell("effective").getValue().equals(EffectivenessEnum.NOTEFFECTIVE) || editData.getState().equals(FDCBillStateEnum.AUDITTED)||getOprtState().equals(OprtState.VIEW) ){
					    btnPreHit.setEnabled(false);
					    btnUnPreHit.setEnabled(false);
					    btnHit.setEnabled(false);
					    btnUnHit.setEnabled(false);
					}else{
						btnPreHit.setEnabled(true);
					    btnUnPreHit.setEnabled(true);
					    btnHit.setEnabled(true);
					    btnUnHit.setEnabled(true);
					}
				}
			}});
	    this.actionAttachment.setVisible(true);
		fillAttachmentList();
	    this.btnViewAttachment.setEnabled(isContainAttachment);
		
      
		actionPrintPreview.setVisible(true);
		actionPrint.setVisible(true);
		actionPrintPreview.setEnabled(true);
		actionPrint.setEnabled(true);
		actionInviteExecuteInfo.setEnabled(true);

		this.menuView.setEnabled(true);
		this.menuView.setVisible(true);
		this.menuItemViewContract.setIcon(EASResource.getIcon("imgTbtn_showlist"));
		this.btnViewContract.setEnabled(true);
		this.btnViewContract.setVisible(true);
		this.btnViewContract.setIcon(EASResource.getIcon("imgTbtn_showlist"));
	}

	protected void initHeadStyle()
	{
		this.txtDescription.setMaxLength(1000);

		this.actionAddLine.setVisible(false);
		this.actionAddLine.setEnabled(false);
		this.actionInsertLine.setEnabled(false);
		this.actionInsertLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionRemoveLine.setEnabled(false);

		this.actionFirst.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCreateTo.setVisible(false);

		this.actionCopy.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);

		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCopyFrom.setVisible(false);

		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);

		this.actionSubmitOption.setVisible(false);
	}

	/**
	 * 初始化招标立项F7控件
	 * 
	 * @author owen_wen 2011-04-08
	 */
	private void initPrmtInviteProject() {
		FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();

		Set stateSet = new HashSet();
		stateSet.add(InviteProjectStateEnum.AUDITTED_VALUE);
		stateSet.add(InviteProjectStateEnum.FILEMAKING_VALUE);
		stateSet.add(InviteProjectStateEnum.ANSWERING_VALUE);
		stateSet.add(InviteProjectStateEnum.APPRAISING_VALUE);
		stateSet.add(InviteProjectStateEnum.FIXED_VALUE);
		
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgUnit.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("inviteProjectState", stateSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE, CompareType.EQUALS));
		
		addExtraFilterItem(filter);

		view.setFilter(filter);
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("*");
		selectors.add(new SelectorItemInfo("inviteType.id"));
		selectors.add(new SelectorItemInfo("inviteType.number"));
		selectors.add(new SelectorItemInfo("inviteType.name"));
		selectors.add(new SelectorItemInfo("project.id"));
		selectors.add(new SelectorItemInfo("project.number"));
		selectors.add(new SelectorItemInfo("project.name"));

		prmtInviteProject.setSelectorCollection(selectors);
		prmtInviteProject.setEntityViewInfo(view);

		prmtInviteProject.setRequired(true);

		// add by david_yang PT043562 2011.04.02 (扩充name到255个字符)
		this.prmtInviteProject.setDisplayFormat("$name$");
		this.prmtInviteProject.setEditFormat("$name$");
		this.prmtInviteProject.setCommitFormat("$name$");
	}
	
	/**
	 * 为filter添加额外的过滤条件：过滤掉已录入评标结果报审的招标立项
	 * 
	 * @param filter
	 *            待添加额外过滤条件的Filter
	 * @author owen_wen 2011-04-08
	 */
	private void addExtraFilterItem(FilterInfo filter) {
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder();

			// 但未录入中标通知书的招标立项，
			builder.appendSql("select fInviteProjectId from T_INV_AppraiseResult ");
			IRowSet rowSet = builder.executeQuery();
			if (rowSet.size() > 0) {
				Set idSet = new HashSet();
				while (rowSet.next()) {
					idSet.add(rowSet.getString("fInviteProjectId"));
				}
				filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.NOTINCLUDE));
			}

		} catch (BOSException e) {
			logger.debug(e.getMessage(), e);
			e.printStackTrace();
		} catch (SQLException e) {
			logger.debug(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	protected void initTableStyle()
	{
		KDTable table = this.getDetailTable();
		table.getColumn("score").getStyleAttributes().setLocked(true);
		table.getColumn("score").getStyleAttributes().setNumberFormat("#0.00");
		table.getColumn("score").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("supplierNumber").getStyleAttributes().setLocked(true);
		table.getColumn("supplierName").getStyleAttributes().setLocked(true);
		table.getColumn("expertAppraiseSeq").getStyleAttributes().setLocked(true);
		table.getColumn("expertAppraiseSeq").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("totalSeq").getStyleAttributes().setLocked(true);
		table.getColumn("totalSeq").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		table.getColumn("bidAmount").getStyleAttributes().setNumberFormat("#0.00");
		table.getColumn("bidAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		table.getColumn("treatAmount").getStyleAttributes().setNumberFormat("#0.00");
		table.getColumn("treatAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		table.getColumn("preHit").getStyleAttributes().setLocked(true);
		table.getColumn("hit").getStyleAttributes().setLocked(true);
		table.getColumn("isPartIn").getStyleAttributes().setLocked(true);

		//设置权重列为正数且两位有效数字
		KDFormattedTextField preSplitAmountField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		preSplitAmountField.setPrecision(2);
		preSplitAmountField.setNegatived(false);
		preSplitAmountField.setSupportedEmpty(false);
		preSplitAmountField.setMaximumValue(FDCHelper.toBigDecimal("10000000000.00"));
		
		KDTDefaultCellEditor editorAmount = new KDTDefaultCellEditor(preSplitAmountField);
		
		table.getColumn("remark").setWidth(250);
	
		
		table.getColumn("bidAmount").setEditor(editorAmount);
		table.getColumn("treatAmount").setEditor(editorAmount);
		FDCHelper.formatTableNumber(table, "bidAmount", "#,##0.00");
		FDCHelper.formatTableNumber(table, "treatAmount", "#,##0.00");
		
		KDComboBox combEffective = new KDComboBox();
		combEffective.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.EffectivenessEnum").toArray());
		KDTDefaultCellEditor comboxEditor = new KDTDefaultCellEditor(combEffective);
		table.getColumn("effective").setEditor(comboxEditor);
		
		table.getColumn("remark").getStyleAttributes().setWrapText(true);		
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		for(int i=0; i<table.getRowCount(); i++){
			this.loadLineFields(table, table.getRow(i), (IObjectValue)table.getRow(i).getUserObject());
			KDTableHelper.autoFitRowHeight(kdtEntrys,i,5);
			kdtEntrys.getCell(i, "remark").getStyleAttributes().setVerticalAlign(VerticalAlignment.TOP);
			
			// 设置 remark 列的样式
			KDMultiLangArea textField = new KDMultiLangArea();
			textField.setMaxLength(500);
			textField.setAutoscrolls(true);
			textField.setEditable(true);
			KDTDefaultCellEditor remarkEditor = new KDTDefaultCellEditor(textField);
			if (table.getRow(i).getCell("remark").getValue() != null)
				textField.setDefaultLangItemData(table.getRow(i).getCell("remark").getValue().toString());
			table.getRow(i).getCell("remark").setEditor(remarkEditor);
		}
		
		kdtEntrys.addKDTEditListener(new KDTEditListener(){

			public void editCanceled(KDTEditEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void editStarted(KDTEditEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void editStarting(KDTEditEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void editStopped(KDTEditEvent e) {
				// TODO Auto-generated method stub
				KDTableHelper.autoFitRowHeight(kdtEntrys,e.getRowIndex(),5);
			}

			public void editStopping(KDTEditEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void editValueChanged(KDTEditEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		FDCTableHelper.disableAutoAddLineDownArrow(table);
		FDCTableHelper.disableAutoAddLine(table);
	}
	protected void verifyInput(ActionEvent e) throws Exception {

				
		if(editData.getInviteProject() == null)
		{
			FDCMsgBox.showError(this,"招标立项为空 ，不能执行此操作");
			abort();
		}

		if(editData.getEntrys().size()==0){
			FDCMsgBox.showError(this,"没有任何评标信息 ，不能执行此操作");
			abort();
		}else{
			
			AppraiseResultEntryCollection cols = editData.getEntrys();
			StringBuffer sb = new StringBuffer();
			sb.append("分录中，第");
			for(int i=0;i<cols.size();i++){
				if(cols.get(i).getRemark() != null && cols.get(i).getRemark().length() > 500){
					sb.append(i+1);
					sb.append("行备注字段超出系统约定的长度!");
					FDCMsgBox.showWarning(sb.toString());
					abort();
				}
			}
			
		}

		super.verifyInput(e);
	}

    
	protected void tblDetail_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.tblDetail_tableSelectChanged(e);
	}
	protected void prmtInviteProject_dataChanged(DataChangeEvent e) throws Exception {

		if(this.isFirstOnload())
			return;
		
		InviteProjectInfo project = null;
		if(e.getOldValue()!=null&&e.getNewValue()!=null)
		{
			project = (InviteProjectInfo)e.getNewValue();
			InviteProjectInfo oldProject = (InviteProjectInfo)e.getOldValue();
			if(!project.getId().toString().equals(oldProject.getId().toString())){
				if(MsgBox.YES == FDCMsgBox.showConfirm2(this,"更改招标立项，将清除所有分录，请确定？"))
				{
					/*if(project.getProject()!=null)
						this.txtCurProjectName.setText(project.getProject().getName());
					if(project.getInviteForm()!=null)
						this.txtInviteFrom.setText(project.getInviteForm().getAlias());*/
					this.txtInviteProjectNumber.setText(project.getNumber());
					editData.setInviteProject(project);
					editData.setName(project.getName());
					editData.setNumber(project.getNumber());
					initTable(project);
				}
			}
			
			
		}else if(e.getOldValue()==null){
			project = (InviteProjectInfo)e.getNewValue();
			/*if(project.getProject()!=null)
				this.txtCurProjectName.setText(project.getProject().getName());
			if(project.getInviteForm()!=null)
				this.txtInviteFrom.setText(project.getInviteForm().getAlias());*/
			this.txtInviteProjectNumber.setText(project.getNumber());
			editData.setInviteProject(project);
			editData.setName(project.getName());
			editData.setNumber(project.getNumber());
			initTable(project);
		}
		/**
		 * 急着出补丁，先这样玩，以后改
		 */
		if(((InviteProjectInfo)e.getNewValue()) != null  ){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("inviteMode.*");
			sic.add("orgUnit.*");
			ObjectUuidPK pk = new ObjectUuidPK(((InviteProjectInfo)e.getNewValue()).getId());
	       InviteProjectInfo pro = InviteProjectFactory.getRemoteInstance().getInviteProjectInfo(pk,sic);		
			
		/*if(pro.getInviteMode() != null){
			this.prmtInviteMode.setData(pro.getInviteMode());
		}*/
		if(pro.getOrgUnit() != null){
			this.txtInviteProjectOrg.setText(pro.getOrgUnit().getName());
		}}
		
		if(e.getNewValue() == null){
			prmtInviteMode.setDataNoNotify(null);
			txtInviteProjectOrg.setText(null,false);
		}
      
	}
	private void initTable(InviteProjectInfo project) throws BOSException, SQLException, EASBizException{
		/****
		 * 需要取招标立项关联的入围供应商
		 * 取入围的供应商的专家评标得分[平均分]
		 */
		KDTable table = this.getDetailTable();
		table.removeRows();
		editData.getEntrys().clear();
		//entryInfoList.clear();
		String invProjectId = project.getId().toString();
		Map supplierScoreMap = null;
		Map param = new HashMap();
		param.put("invProjectId",invProjectId);
		Map returnMap = AppraiseResultFactory.getRemoteInstance().fetchInitData(param);
		IObjectCollection supplierQs = (IObjectCollection)returnMap.get("supplierQs");
		Set hasRecordSupplierSet = (Set)returnMap.get("hasRecordSupplierSet");
		supplierScoreMap = (Map) returnMap.get("supplierScoreMap");

		boolean hasBidAmount = false ;
		
		for(Iterator it = supplierQs.iterator();it.hasNext();){
			SupplierQualifyEntryInfo info = (SupplierQualifyEntryInfo)it.next();
			if(!hasRecordSupplierSet.contains(info.getSupplier().getId().toString()))
				continue;
			AppraiseResultEntryInfo entryInfo = new AppraiseResultEntryInfo();
			entryInfo.setEffective(EffectivenessEnum.EFFECTIVE);   //新增时默认为“有效”，而不是“废标” added By Owen_wen 2010-8-4
//			entryInfo.setSupplier(info.getSupplier());
			entryInfo.setIsPartIn(true);
			Map valueMap = null;
			if(supplierScoreMap!=null){
				valueMap = (Map)supplierScoreMap.get(info.getSupplier().getId().toString());
				
				if(valueMap!=null){
					if(valueMap.containsKey("score"))
						entryInfo.setScore(((Double)valueMap.get("score")).doubleValue());
					if(valueMap.containsKey("seq"))
						entryInfo.setExpertAppraiseSeq(((Integer)valueMap.get("seq")).intValue());
					if(valueMap.get("quotingPrice")==null)
					{
						if(valueMap.containsKey("seq"))
							entryInfo.setTotalSeq(((Integer)valueMap.get("seq")).intValue());
						entryInfo.setIsImport(false);
					}
					else
					{
						entryInfo.setBidAmount(FDCHelper.toBigDecimal(valueMap.get("quotingPrice")));
						hasBidAmount = true;
						entryInfo.setIsImport(true);
					}
				}
			}
			

			//根据供应商和招标立项对应的报价信息中的状态来设置中标列的状态
			String supplierId = info.getSupplier().getId().toString();
			String InviteProjectId = project.getId().toString();
			NewQuotingPriceInfo priceInfo = getQuotingCols(supplierId, InviteProjectId);

			/*if(priceInfo.getStatus().equals(QuotingPriceStatusEnum.ImportBase)
					|| priceInfo.getStatus().equals(
							QuotingPriceStatusEnum.ImportToDB))
			{
				entryInfo.setHit(true);
			}
			else
			 */			/*{*/
			entryInfo.setHit(false);
			/*}*/

			IRow row = table.addRow();
			this.loadLineFields(table,row,entryInfo);
			entryInfo.setParent(editData);
			
			this.editData.getEntrys().add(entryInfo);
			row.getCell("score").getStyleAttributes().setLocked(true);
			row.getCell("expertAppraiseSeq").getStyleAttributes().setLocked(true);
			if(valueMap!=null){
				if(FDCHelper.toBigDecimal(valueMap.get("quotingPrice")).compareTo(FDCHelper.ZERO)!=0){
					row.getCell("bidAmount").getStyleAttributes().setLocked(true);
				}
			}
		}
		
		//如果该供应商有投标报价记录。则排名按照报价排名
		if(!hasBidAmount)
			return ;
		
		Map entryInfoMap = new HashMap();
		ArrayList tmpList = new ArrayList();
		for(int i = 0; i < getDetailTable().getRowCount(); ++i)
		{
			IRow tmpRow = getDetailTable().getRow(i);
			AppraiseResultEntryInfo tmpEntryInfo = (AppraiseResultEntryInfo)tmpRow.getUserObject();
			AppraiseResultEntryInfo tmp = new AppraiseResultEntryInfo();
			
			tmp.setSupplier(tmpEntryInfo.getSupplier());
			tmp.setBidAmount(tmpEntryInfo.getBidAmount());
			tmp.setTreatAmount(tmpEntryInfo.getTreatAmount());
			
			if(tmpEntryInfo.getBidAmount()== null && tmpEntryInfo.getTreatAmount() ==null)
				continue;
			
			if(tmpEntryInfo.getBidAmount()!= null && tmpEntryInfo.getTreatAmount() == null)
			{
				tmp.setTreatAmount(tmpEntryInfo.getBidAmount());
			}
			entryInfoMap.put(tmp.getSupplier().getId().toString(), tmp);
			tmpList.add(tmp);
		}
		AppraiseResultEntryInfo[] entryInfoList = new AppraiseResultEntryInfo[tmpList.size()];
		for(int i = 0;i < entryInfoList.length; ++i)
		{
			entryInfoList[i] = (AppraiseResultEntryInfo)tmpList.get(i);
		}
		//排序
		for(int i = 1; i < entryInfoList.length; ++i)
		{
			AppraiseResultEntryInfo tmpI = entryInfoList[i];
			
			int j = i-1;
			while(j >= 0 && tmpI.getTreatAmount().compareTo(entryInfoList[j].getTreatAmount())<0)
			{
				entryInfoList[j+1] = entryInfoList[j] ;
				j = j -1;
			}
			

			entryInfoList[j+1] = tmpI;
		}
		
		for(int i = 0; i < entryInfoList.length; ++i)
		{
			AppraiseResultEntryInfo tmpEntryInfo = (AppraiseResultEntryInfo)entryInfoList[i];
			tmpEntryInfo.setTotalSeq(i+1);
			entryInfoMap.put(tmpEntryInfo.getSupplier().getId().toString(), tmpEntryInfo);
		}
		
		//更新名次
		for(int i = 0;i <getDetailTable().getRowCount(); ++i)
		{
			IRow tmpRow = getDetailTable().getRow(i);
			AppraiseResultEntryInfo rowUserObject = (AppraiseResultEntryInfo)tmpRow.getUserObject();
			String  key = ((AppraiseResultEntryInfo)tmpRow.getUserObject()).getSupplier().getId().toString();
			AppraiseResultEntryInfo tmpEntryInfo = (AppraiseResultEntryInfo)entryInfoMap.get(key);
			
			if(tmpEntryInfo != null)
			{
				/*tmpRow.getCell("totalSeq").setValue(tmpEntryInfo.getTotalSeq());*/
				rowUserObject.setTotalSeq(tmpEntryInfo.getTotalSeq());
			}
			else
			{
				/*tmpRow.getCell("totalSeq").setValue(null);*/
				rowUserObject.setTotalSeq(0);
			}
			
			tmpRow.setUserObject(rowUserObject);
			this.loadLineFields(table,tmpRow,rowUserObject);
		}
	}

	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		KDTable table = this.getDetailTable();
		if(table.getRowCount() <= 0)
			return ;

		if(e.getColIndex()==table.getColumnIndex("treatAmount")){
			AppraiseResultEntryInfo tmp = ((AppraiseResultEntryInfo)table.getRow(e.getRowIndex()).getUserObject());
			if(e.getValue() != null)
			{
				tmp.setTreatAmount(FDCHelper.toBigDecimal(e.getValue()));
			}
			else
			{
				tmp.setTreatAmount(null);
			}
			table.getRow(e.getRowIndex()).setUserObject(tmp);
			handlEntryTotalSeq(e);
		}
		else if(e.getColIndex()==table.getColumnIndex("bidAmount"))
		{
			AppraiseResultEntryInfo tmp = ((AppraiseResultEntryInfo)table.getRow(e.getRowIndex()).getUserObject());
			if(e.getValue() != null)
			{
				tmp.setBidAmount(FDCHelper.toBigDecimal(e.getValue()));
			}
			else
			{
				tmp.setBidAmount(null);
			}
			table.getRow(e.getRowIndex()).setUserObject(tmp);
			
			handlEntryTotalSeq(e);
		}else if(e.getColIndex() == table.getColumnIndex("remark")){
			if(table.getCell(e.getRowIndex(), "remark").getValue() != null){
				String oldValue = table.getCell(e.getRowIndex(), "remark").getValue().toString();
				if(oldValue.length() > 500){
					FDCMsgBox.showWarning("备注长度超过系统约定长度(500)!");
					abort();
				}else {
					// 需要将新的备注放到userObject中，否则再修改其它字段时调用handlEntryTotalSeq(e)后会变成原来的值。   Added by Owen_wen
					((AppraiseResultEntryInfo)table.getRow(e.getRowIndex()).getUserObject()).setRemark(oldValue);
				}
				
			}
		}else if(e.getColIndex() == table.getColumnIndex("effective")){
			if(table.getCell(e.getRowIndex(),"effective").getValue().equals(EffectivenessEnum.NOTEFFECTIVE)){
//				table.getCell(e.getRowIndex(), "isPartIn").setValue(Boolean.FALSE);
			    this.btnPreHit.setEnabled(false);
			    this.btnUnPreHit.setEnabled(false);
			    this.btnHit.setEnabled(false);
			    this.btnUnHit.setEnabled(false);
			}else{
//				table.getCell(e.getRowIndex(), "isPartIn").setValue(Boolean.TRUE);
				this.btnPreHit.setEnabled(true);
			    this.btnUnPreHit.setEnabled(true);
			    this.btnHit.setEnabled(true);
			    this.btnUnHit.setEnabled(true);
			}
		}
		
	}
	/***
	 * 重新计算排名的方法
	 */
	private void handlEntryTotalSeq(KDTEditEvent e) {
				
		Map entryInfoMap = new HashMap();
		ArrayList tmpList = new ArrayList();
		for(int i = 0; i < getDetailTable().getRowCount(); ++i)
		{
			IRow tmpRow = getDetailTable().getRow(i);
			AppraiseResultEntryInfo tmpEntryInfo = (AppraiseResultEntryInfo)tmpRow.getUserObject();
			AppraiseResultEntryInfo tmp = new AppraiseResultEntryInfo();
			
			tmp.setSupplier(tmpEntryInfo.getSupplier());
			tmp.setBidAmount(tmpEntryInfo.getBidAmount());
			tmp.setTreatAmount(tmpEntryInfo.getTreatAmount());
			
			if(tmpEntryInfo.getBidAmount()== null && tmpEntryInfo.getTreatAmount() ==null)
				continue;
			
			if(tmpEntryInfo.getBidAmount()!= null && tmpEntryInfo.getTreatAmount() == null)
			{
				tmp.setTreatAmount(tmpEntryInfo.getBidAmount());
			}
			entryInfoMap.put(tmp.getSupplier().getId().toString(), tmp);
			tmpList.add(tmp);
		}
		AppraiseResultEntryInfo[] entryInfoList = new AppraiseResultEntryInfo[tmpList.size()];
		for(int i = 0;i < entryInfoList.length; ++i)
		{
			entryInfoList[i] = (AppraiseResultEntryInfo)tmpList.get(i);
		}
		//排序
		for(int i = 1; i < entryInfoList.length; ++i)
		{
			AppraiseResultEntryInfo tmpI = entryInfoList[i];
			
			int j = i-1;
			while(j >= 0 && tmpI.getTreatAmount().compareTo(entryInfoList[j].getTreatAmount())< 0)
			{
				entryInfoList[j+1] = entryInfoList[j] ;
				j = j -1;
			}
			

			entryInfoList[j+1] = tmpI;
		}
		
		for(int i = 0; i < entryInfoList.length; ++i)
		{
			AppraiseResultEntryInfo tmpEntryInfo = (AppraiseResultEntryInfo)entryInfoList[i];
			tmpEntryInfo.setTotalSeq(i+1);
			entryInfoMap.put(tmpEntryInfo.getSupplier().getId().toString(), tmpEntryInfo);
		}
		
		//更新名次
		for(int i = 0;i <getDetailTable().getRowCount(); ++i)
		{
			IRow tmpRow = getDetailTable().getRow(i);
			AppraiseResultEntryInfo rowUserObject = (AppraiseResultEntryInfo)tmpRow.getUserObject();
			String  key = ((AppraiseResultEntryInfo)tmpRow.getUserObject()).getSupplier().getId().toString();
			AppraiseResultEntryInfo tmpEntryInfo = (AppraiseResultEntryInfo)entryInfoMap.get(key);
			
			if(tmpEntryInfo != null)
			{
				/*tmpRow.getCell("totalSeq").setValue(tmpEntryInfo.getTotalSeq());*/
				rowUserObject.setTotalSeq(tmpEntryInfo.getTotalSeq());
			}
			else
			{
				/*tmpRow.getCell("totalSeq").setValue(null);*/
				rowUserObject.setTotalSeq(0);
			}
			
			tmpRow.setUserObject(rowUserObject);
			this.loadLineFields(getDetailTable(),tmpRow,rowUserObject);
		}
	}
	
	protected void loadLineFields(KDTable table, IRow row, IObjectValue obj) {
		dataBinder.loadLineFields(table, row, obj);
		row.getCell("bidAmount").getStyleAttributes().setLocked(((AppraiseResultEntryInfo)obj).isIsImport());
		row.getCell("isPartIn").setValue(Boolean.valueOf(((AppraiseResultEntryInfo)obj).isIsPartIn()));
	}

	private boolean checkCanOperate()
	{
		boolean flag = true ;

		String orgId = editData.getOrgUnit().getId().toString();
		if (orgUnitInfo.getId().toString().equals(orgId)) {
			flag = true;
		} else {
			flag = false ;
		}

		return flag;
	}

	protected boolean isShowAttachmentAction() {
		return false ;
	}

	public void actionPreHit_actionPerformed(ActionEvent e) throws Exception 
	{
		if(kdtEntrys.getRowCount() > 0 )
		{
			int rowIndex = kdtEntrys.getSelectManager().getActiveRowIndex();
			//没有选择时rowIndex=-1
			if(rowIndex == -1){
				FDCMsgBox.showWarning("请先选中行！");
				return ;
			}
			if( editData.getInviteProject().isIsRate()&&kdtEntrys.getRow(rowIndex).getCell("bidAmount").getValue()== null
					&& kdtEntrys.getRow(rowIndex).getCell("treatAmount").getValue() == null)
			{
				FDCMsgBox.showWarning(this, "投标报价和谈判价都为空，不能执行此操作。");
				abort();
			}
			if(!editData.getState().equals(FDCBillStateEnum.AUDITTED)&& !getOprtState().equals(OprtState.VIEW)){
				kdtEntrys.getRow(rowIndex).getCell("preHit").setValue(Boolean.valueOf(true));
			}else{
				FDCMsgBox.showWarning(this, "当前单据的状态不能执行此操作！");
				abort();
			}
			
			
		}
	}
	public void actionUnPreHit_actionPerformed(ActionEvent e) throws Exception 
	{
		if(kdtEntrys.getRowCount() > 0)
		{
			int rowIndex = kdtEntrys.getSelectManager().getActiveRowIndex();
			if(rowIndex == -1){
				FDCMsgBox.showWarning("请先选中行！");
				return ;
			}
			if(!editData.getState().equals(FDCBillStateEnum.AUDITTED)&& !getOprtState().equals(OprtState.VIEW)){
			      kdtEntrys.getRow(rowIndex).getCell("preHit").setValue(Boolean.valueOf(false));
			}else{
				FDCMsgBox.showWarning(this, "当前单据的状态不能执行此操作！");
				abort();
			}
		}
	}
	public void actionHit_actionPerformed(ActionEvent e) throws Exception 
	{
		if(kdtEntrys.getRowCount() <= 0)
		{
			return ;
		}
		int rowIndex = kdtEntrys.getSelectManager().getActiveRowIndex();
		if(rowIndex == -1){
			FDCMsgBox.showWarning("请先选中行！");
			return ;
		}
		if(editData.getState().equals(FDCBillStateEnum.AUDITTED)&& getOprtState().equals(OprtState.VIEW)){
			FDCMsgBox.showWarning(this, "当前单据的状态不能执行此操作！");
			abort();
		}
		
		Boolean hit = (Boolean)kdtEntrys.getRow(rowIndex).getCell("hit").getValue();
		if(hit.booleanValue())
			return ;

		if(!editData.getInviteProject().isIsRate()&&kdtEntrys.getRow(rowIndex).getCell("bidAmount").getValue()== null
				&& kdtEntrys.getRow(rowIndex).getCell("treatAmount").getValue() == null)
		{
			FDCMsgBox.showWarning(this, "投标报价和谈判价都为空，不能执行此操作。");
			abort();
		}

		//跟新对应的投标人报价信息的报价状态
		if(kdtEntrys.getRow(rowIndex).getUserObject() != null && kdtEntrys.getRow(rowIndex).getUserObject() instanceof AppraiseResultEntryInfo)
		{
			AppraiseResultEntryInfo resultEntryInfo = (AppraiseResultEntryInfo)kdtEntrys.getRow(rowIndex).getUserObject();

			if(resultEntryInfo.getSupplier() != null)
			{
				SupplierInfo supInfo = resultEntryInfo.getSupplier();
				InviteProjectInfo invPrjInfo = (InviteProjectInfo)prmtInviteProject.getValue();

				NewQuotingPriceInfo priceInfo = getQuotingCols(supInfo.getId().toString(), invPrjInfo.getId().toString());
				if(priceInfo!=null){
					String quotingId = priceInfo.getId().toString();
					NewQuotingPriceFactory.getRemoteInstance().acceptBid(quotingId);

					kdtEntrys.getRow(rowIndex).getCell("hit").setValue(Boolean.valueOf(true));
				}
				else
				{
					kdtEntrys.getRow(rowIndex).getCell("hit").setValue(Boolean.valueOf(true));
				}
			}
			this.storeLineFields(kdtEntrys, kdtEntrys.getRow(rowIndex), resultEntryInfo);
		}
		
	}
	
	
	public void actionUnHit_actionPerformed(ActionEvent e) throws Exception 
	{
		if(kdtEntrys.getRowCount() <= 0)
		{
			return ;
		}

		int rowIndex = kdtEntrys.getSelectManager().getActiveRowIndex();
		if(rowIndex == -1){
			FDCMsgBox.showWarning("请先选中行！");
			return ;
		}
		Boolean isHit = (Boolean)(kdtEntrys.getRow(rowIndex).getCell("hit").getValue());
		if(!isHit.booleanValue())
			return ;

		//跟新对应的投标人报价信息的报价状态
		if(kdtEntrys.getRow(rowIndex).getUserObject() != null && kdtEntrys.getRow(rowIndex).getUserObject() instanceof AppraiseResultEntryInfo)
		{
			AppraiseResultEntryInfo resultEntryInfo = (AppraiseResultEntryInfo)kdtEntrys.getRow(rowIndex).getUserObject();

			if(resultEntryInfo.getSupplier() != null)
			{
				SupplierInfo supInfo = resultEntryInfo.getSupplier();
				InviteProjectInfo invPrjInfo = (InviteProjectInfo)prmtInviteProject.getValue();

				NewQuotingPriceInfo priceInfo = getQuotingCols(supInfo.getId().toString(), invPrjInfo.getId().toString());

				if(priceInfo == null ||priceInfo.getStatus() == null)
				{
					kdtEntrys.getRow(rowIndex).getCell("hit").setValue(Boolean.valueOf(false));
				}
				else
				{
					if (priceInfo.getStatus().equals(QuotingPriceStatusEnum.ImportBase)
							|| priceInfo.getStatus().equals(
									QuotingPriceStatusEnum.ImportToDB)) {
						// 已经写入了数据库
						if (priceInfo.getStatus().equals(
								QuotingPriceStatusEnum.ImportToDB)) {
							MsgBox.showInfo(this, EASResource.getString(resourcePath,"alreadyToDBCanntAcceptBid"));
							return;
						}
						// 反中标
						String quotingId = priceInfo.getId().toString();
						NewQuotingPriceFactory.getRemoteInstance().unacceptBid(
								quotingId);

						kdtEntrys.getRow(rowIndex).getCell("hit").setValue(Boolean.valueOf(false));
					} else {
						MsgBox.showInfo(this, EASResource.getString(resourcePath,"onlyAcceptedBidCanInvalidate"));
					}
				}
			}
		}
	}

	public void actionVirtualPreSplit_actionPerformed(ActionEvent e)throws Exception 
	{
		if(prmtInviteProject.getValue() == null)
		{
			FDCMsgBox.showWarning(this, "招标立项为空，不能执行此操作。");
			abort();
		}

		UIContext uiContext = new UIContext(ui);
		uiContext.put("inviteProject", prmtInviteProject.getValue());
		IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				InvitePreSplitEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);

		window.show();
	}

	private NewQuotingPriceInfo getQuotingCols(String paramSupId, String invitePrjId) throws BOSException
	{

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("listing.id"));
		view.getSelector().add(new SelectorItemInfo("listing.inviteProject"));
		view.getSelector().add(new SelectorItemInfo("supplier"));

		view.getSelector().add(new SelectorItemInfo("status"));

		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("listing.inviteProject", invitePrjId);
		filter.appendFilterItem("supplier", paramSupId);

		view.setFilter(filter);

		NewQuotingPriceCollection priceCols = NewQuotingPriceFactory.getRemoteInstance().getNewQuotingPriceCollection(view);
		if(priceCols.size() == 1)
		{
			NewQuotingPriceInfo priceInfo = (NewQuotingPriceInfo)priceCols.get(0);

			return priceInfo;
		}

		return null;
	}
	
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = getSelectBOID();
        if(boID == null)
        {
            return;
        } 
        boolean isEdit = false;
        if(STATUS_ADDNEW.equals(getOprtState())||STATUS_EDIT.equals(getOprtState()))
        {
            isEdit = true;
        }
        
        //add liuguangyue 2010-4-28
        isEdit = AttachmentPermissionUtil.checkAuditingAttachmentEdit(editData.getState(), boID,isEdit);
        
        //modify liuguangyue 2010-04-27
        AttachmentUIContextInfo info = new AttachmentUIContextInfo();
		info.setBoID(boID);
		MultiApproveUtil.showAttachmentManager(info,this,editData,String.valueOf("1"),isEdit);
		
//		// TODO Auto-generated method stub
//		super.actionAttachment_actionPerformed(e);
		fillAttachmentList();
	}
	
	public void actionViewAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
//		super.actionViewAttachment_actionPerformed(e);
		String boID = null;
		if(this.combAttachmentList.getSelectedItem() != null){
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
			AttachmentInfo info = (AttachmentInfo) this.combAttachmentList.getSelectedItem();
			acm.viewAttachment(info.getId().toString());
		}
	}
	
	public void fillAttachmentList(){
		this.combAttachmentList.removeAllItems();
		String boID = null;
		if(this.editData.getId() != null){
			boID = editData.getId().toString();
		}else{
			return ;
		}
		if(boID != null){
			EntityViewInfo viewInfo = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("attachment.name"));
			sic.add(new SelectorItemInfo("attachment.id"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID",boID));
			viewInfo.setSelector(sic);
			viewInfo.setFilter(filter);
			BoAttchAssoCollection cols = null;
	        try {
				cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(viewInfo);
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(cols.size() > 0 ){
				for(Iterator it = cols.iterator();it.hasNext();){
					AttachmentInfo info = ((BoAttchAssoInfo)it.next()).getAttachment();
					this.combAttachmentList.addItem(info);
				}
				isContainAttachment = true;
			}else{
				isContainAttachment = false;
			}
			this.btnViewAttachment.setEnabled(isContainAttachment);
		}
	}
	
	protected void lockContainer(Container container) {
		// TODO Auto-generated method stub
		if("conAttachmentList".equalsIgnoreCase(container.getName())){
			return;
		}
		super.lockContainer(container);
	}

	/**
	 * 查看招标文件
	 */
	public void actionAssoViewBill_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAssoViewBill_actionPerformed(e);
		String className = InviteFileMergeEditUI.class.getName();
		String id = getAuditedInviteFileMergeBill();
		if(StringUtils.isEmpty(id)){
			MsgBox.showWarning(this,"没有关联的招标立项，暂无法查看！");
			abort();
		}
		Map uiContext = getUIContext();
		uiContext.put(UIContext.ID, id);
		uiContext.put(UIContext.OWNER, this);
		IUIWindow uiWIndow = UIFactory.createUIFactory(UIFactoryName.EDITWIN).create(className,uiContext ,null,OprtState.VIEW);
		uiWIndow.show();
	}
	
	/**
	 * 获取当前招标立项对应的招标文件
	 * @author msb 2010/04/26
	 * @return
	 */
	private String getAuditedInviteFileMergeBill() throws Exception{
		String id = "";
		Object obj = this.prmtInviteProject.getData();
		InviteProjectInfo invitePrj = null;
		if(obj != null && (obj instanceof InviteProjectInfo)){
			invitePrj =(InviteProjectInfo)obj; 
			
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("state");
			selector.add("inviteProject.id");
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",invitePrj.getId()));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			
			EntityViewInfo view = new EntityViewInfo();
			view.setSelector(selector);
			view.setFilter(filter);
			IInviteFileMerge invtFileDao = InviteFileMergeFactory.getRemoteInstance();
			InviteFileMergeCollection coll =invtFileDao.getInviteFileMergeCollection(view);
			if(coll != null && !coll.isEmpty()){
				return coll.get(0).getId().toString();
			}else{
				MsgBox.showInfo(this,"没有对应的招标文件合成单据！");
				abort();
			}
		}
		return id;
	}
	
	/**
	 * 打印
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null
				&& !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData
						.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		AppraiseResultDataProvider data=new AppraiseResultDataProvider(
				editData.getString("id"), getTDQueryPK(),getATTCHQueryPK());
		/*InvitePrintDataProvider data = new InvitePrintDataProvider(
				editData.getString("id"), getTDQueryPK());*/
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	/**
	 * 打印预览
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		logger.info("打印预览");
		ArrayList idList = new ArrayList();
		if (editData != null
				&& !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData
						.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		AppraiseResultDataProvider data=new AppraiseResultDataProvider(
				editData.getString("id"), getTDQueryPK(),getATTCHQueryPK());
/*		InvitePrintDataProvider data = new InvitePrintDataProvider(
				editData.getString("id"), getTDQueryPK());*/
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	// 获得无文本合同套打对应的目录
	protected String getTDFileName() {
		return "/bim/fdc/invite/AppraiseResultForPrint";
	}

	// 对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.invite.app.AppraiseResultForPrintQuery");
	}
	
	// 对应的套打Query
	protected IMetaDataPK getATTCHQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.invite.app.AppraiseResultAttchForPrintQuery");
	}
	public void actionInviteExecuteInfo_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionInviteExecuteInfo_actionPerformed(e);
		if(editData.getInviteProject() == null){
			FDCMsgBox.showError("请先选择招标立项！");
			abort();
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put("INVITE_PROJECT", editData.getInviteProject().getId().toString());
		uiContext.put("LIST_UI","com.kingdee.eas.fdc.invite.client.AppraiseResultEditUI");
		uiContext.put("INVITEPROJECT_NAME", null);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(InviteAllInformationUI.class.getName(), uiContext,
						null, OprtState.ADDNEW);
		uiWindow.show();
	}
	

	public void actionViewContract_actionPerformed(ActionEvent e) throws Exception {
		Object inviteProject = prmtInviteProject.getValue();
		if (inviteProject == null) {
			FDCMsgBox.showInfo("请选择招标立项");
			SysUtil.abort();
		}
		InviteProjectInfo ipInfo = (InviteProjectInfo) inviteProject;
		if (ipInfo.getProgrammingContract() == null) {
			FDCMsgBox.showInfo("所选招标立项没有关联框架合约");
			SysUtil.abort();
		}
		IUIWindow uiWindow = null;
		UIContext uiContext = new UIContext(this);
		ProgrammingContractInfo pcInfo = getProContInfo(ipInfo.getProgrammingContract().getId().toString());
		uiContext.put("programmingContract", pcInfo);
		uiContext.put("inviteProject", "inviteProject");
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingContractEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}

	/**
	 * 获取所关联的规划合约
	 * 
	 * @param id
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private ProgrammingContractInfo getProContInfo(String id) throws EASBizException, BOSException {
		SelectorItemCollection selItemCol = new SelectorItemCollection();
		selItemCol.add("*");
		selItemCol.add("costEntries.*");
		selItemCol.add("costEntries.costAccount.*");
		selItemCol.add("costEntries.costAccount.curProject.*");
		selItemCol.add("economyEntries.*");
		selItemCol.add("economyEntries.paymentType.*");
		ProgrammingContractInfo pcInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(id), selItemCol);
		return pcInfo;
	}
}