/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IBlock;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTViewManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.client.ContractPayPlanEditUI;
import com.kingdee.eas.fdc.finance.client.PaymentBillContants;
import com.kingdee.eas.fdc.finance.client.PaymentBillEditUI;
import com.kingdee.eas.fdc.finance.client.PaymentFullListUI;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fi.cas.PaymentBillEntryFactory;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.Assert;
import com.kingdee.util.UuidException;

/**
 * 付款申请单录入
 */
public class PayRequestBillListUI extends AbstractPayRequestBillListUI {
	private static final Logger logger = CoreUIObject.getLogger(PayRequestBillListUI.class);

	private boolean hasClicked = false;

	// 新增付款申请单，是否校验合同未完全拆分
	private boolean checkAllSplit = true;

	/**
	 * output class constructor
	 */
	public PayRequestBillListUI() throws Exception {
		super();
	}

	/**
	 * 快速模糊定位
	 * 
	 * @author ling_peng  
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "contractName",  "partB.name", "contractType.name", "signDate"};
	}

	public void initUIToolBarLayout() {
		super.initUIToolBarLayout();
		this.btnProjectAttachment.setIcon(EASResource.getIcon("imgTbtn_createcredence"));
		actionContractAttachment.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_view"));
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		btnSelectDeduct.setIcon(EASResource.getIcon("imgTbtn_showdata"));
	}

	public void onLoad() throws Exception {

		super.onLoad();
		/*
		 * 以下注释段注释段代码与 onGetRowSet 方法结合使用处理奥园要求在付款申请单序时簿上添加一列 "是否存在附件"因为在
		 * tblMain_tableSelectChanged 方法中直接处理了,故此段代码与 onGetRowSet 方法暂时都不用到 by
		 * Cassiel_peng if(attachMap==null){ attachMap=new HashMap(); }
		 * this.tblPayRequestBill
		 * .getDataRequestManager().addDataFillListener(new
		 * KDTDataFillListener(){
		 * 
		 * public void afterDataFill(KDTDataRequestEvent e) {
		 * if(attachMap==null){ attachMap=new HashMap(); } for (int i =
		 * e.getFirstRow(); i <= e.getLastRow(); i++) { IRow row =
		 * tblPayRequestBill.getRow(i); String idkey =
		 * row.getCell("id").getValue().toString();
		 * if(attachMap.containsKey(idkey)){
		 * row.getCell("hasAttachment").setValue(Boolean.TRUE); }else{
		 * row.getCell("hasAttachment").setValue(Boolean.FALSE); } } } });
		 */
		actionAuditResult.setVisible(true);
		actionAuditResult.setEnabled(true);
		actionTraceDown.setVisible(true);
		actionTraceDown.setEnabled(true);
		actionTraceUp.setEnabled(true);
		actionTraceUp.setVisible(true);
		// 2009-1-22 sxhong 只有实体财务组织才显示关联生成
		if (SysContext.getSysContext().getCurrentOrgUnit() != null && SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()
				&& SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()) {
			// 实体财务组织才可以关联生成
			actionCreateTo.setEnabled(true);
		} else {
			actionCreateTo.setEnabled(false);
		}

		this.menuItemProjectAttachement.setIcon(EASResource.getIcon("imgTbtn_createcredence"));
		// 付款单提交时，是否检查合同未完全拆分
		checkAllSplit = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_CHECKALLSPLIT);
		tblPayRequestBill.getColumn(PayRequestBillContants.COL_ACTPAYLOCAMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		// FDCClientHelper.getAction(this,"F8");
		actionQuery.setEnabled(true);
		actionQuery.setVisible(true);
		
		if(SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("POMadmin")){
			KDWorkButton btnUpdate=new KDWorkButton();
			btnUpdate.setText("数据升级");
			this.toolBar.add(btnUpdate);
			btnUpdate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
		                beforeActionPerformed(e);
		                try {
		                	btnUpdate_actionPerformed(e);
		                } catch (Exception exc) {
		                    handUIException(exc);
		                } finally {
		                    afterActionPerformed(e);
		                }
		            }
		        });
		}
	}
	protected void btnUpdate_actionPerformed(ActionEvent e) throws Exception {
		int m=0;
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select pay.fid from T_CAS_PaymentBill pay left join T_CON_PayRequestBill request on request.fid=pay.fFdcPayReqId where pay.fsourceType=107 and pay.fbillstatus!=15 and pay.fFdcPayReqId is not null and pay.fCostCenterid is null and request.FIsBgControl=1 and pay.fcurrencyid=request.fcurrencyid");
		IRowSet rs = sqlBuilder.executeQuery();
		SelectorItemCollection payrequestSel=new SelectorItemCollection();
		payrequestSel.add("bgEntry.*");
		payrequestSel.add("bgEntry.expenseType*");
		payrequestSel.add("currency.*");
		payrequestSel.add("costedDept.*");
		payrequestSel.add("originalAmount");
		
		SelectorItemCollection updatesel=new SelectorItemCollection();
		updatesel.add("costCenter.*");
		updatesel.add("entries.*");
		updatesel.add("entries.currency.*");
		updatesel.add("entries.expenseType.*");
		updatesel.add("entries.costCenter.*");
		while(rs.next()){
			String id=rs.getString("fid");
			PaymentBillInfo info=PaymentBillFactory.getRemoteInstance().getPaymentBillInfo(new ObjectUuidPK(id));
			
			PayRequestBillInfo payReqBill=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(info.getFdcPayReqID()),payrequestSel);
			info.getEntries().clear();
			
			if(payReqBill.getOriginalAmount().compareTo(info.getAmount())!=0){
				BigDecimal rate=info.getAmount().divide(FDCHelper.toBigDecimal(payReqBill.getOriginalAmount()),2,BigDecimal.ROUND_HALF_UP);	
				BigDecimal total=FDCHelper.ZERO;
				for(int i=0;i<payReqBill.getBgEntry().size();i++){
					BigDecimal amount=FDCHelper.ZERO;
					if(i==payReqBill.getBgEntry().size()-1){
						amount=info.getAmount().subtract(total);
					}else{
						amount=payReqBill.getBgEntry().get(i).getRequestAmount().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP);
						total=total.add(amount);
					}
					PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
					entry.setAmount(amount);
					entry.setLocalAmt(amount);
		            entry.setActualAmt(amount);
		            entry.setActualLocAmt(amount);
		            entry.setCurrency(payReqBill.getCurrency());
		            entry.setExpenseType(payReqBill.getBgEntry().get(i).getExpenseType());
		            entry.setSourceBillEntryId(payReqBill.getBgEntry().get(i).getId().toString());
		            entry.setCostCenter(payReqBill.getCostedDept());
		            info.getEntries().add(entry);
				}
			}else{
				for(int i=0;i<payReqBill.getBgEntry().size();i++){
					PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
					entry.setAmount(payReqBill.getBgEntry().get(i).getRequestAmount());
					entry.setLocalAmt(payReqBill.getBgEntry().get(i).getRequestAmount());
		            entry.setActualAmt(payReqBill.getBgEntry().get(i).getRequestAmount());
		            entry.setActualLocAmt(payReqBill.getBgEntry().get(i).getRequestAmount());
		            entry.setCurrency(payReqBill.getCurrency());
		            entry.setExpenseType(payReqBill.getBgEntry().get(i).getExpenseType());
		            entry.setSourceBillEntryId(payReqBill.getBgEntry().get(i).getId().toString());
		            entry.setCostCenter(payReqBill.getCostedDept());
		            info.getEntries().add(entry);
				}
			}
			info.setCostCenter(payReqBill.getCostedDept());
			PaymentBillFactory.getRemoteInstance().update(new ObjectUuidPK(info.getId()), info);
			m++;
		}
		FDCMsgBox.showInfo(this,m+"条数据成功升级！");
	}
	protected void initWorkButton() {
		super.initWorkButton();
		actionRespite.setVisible(false);
		actionRespite.setEnabled(false);
		actionCancelRespite.setVisible(false);
		actionCancelRespite.setEnabled(false);
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		// 表头排序及合同查看
		// if(e.getType()==KDTStyleConstants.HEAD_ROW||e.getClickCount()==2){
		// super.tblMain_tableClicked(e);
		// }
		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		// checkSelected();
		// super.tblMain_tableSelectChanged(e);

		if (!isHasBillTable()) {
			super.tblMain_tableSelectChanged(e);
			return;
		}
		if (e.getSelectBlock() == null)
			return;
		getBillListTable().removeRows(false);

		EntityViewInfo view = genBillQueryView(e);
		if (!displayBillByContract(e, view)) {
			displayBillByContract(view);
		}
		if (getBillListTable() != null && getBillListTable().getRowCount() > 0) {
			getBillListTable().getSelectManager().select(0, 0);
		}
		/**
		 * 奥园要求在付款申请单序时簿和付款申请单查询界面添加一列 "是否存在附件" 如果存在附件就填充到界面表格中 by Cassiel_peng
		 */
		String contractBillID = this.getContractBillId();
		// 根据合同ID找到所有付款单ID
		EntityViewInfo idView = new EntityViewInfo();
		idView.getSelector().add("id");

		FilterInfo idFilter = new FilterInfo();
		idFilter.getFilterItems().add(new FilterItemInfo("contractId", contractBillID));
		idView.setFilter(idFilter);

		PayRequestBillCollection payReqBillColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(idView);
		if (payReqBillColl != null && payReqBillColl.size() > 0) {
			Set boIds = new HashSet();
			for (int i = 0; i < payReqBillColl.size(); i++) {
				PayRequestBillInfo info = (PayRequestBillInfo) payReqBillColl.get(i);
				boIds.add(info.getId().toString());
			}
			attachMap = PayReqTableHelper.handleAttachment(boIds);
			if (attachMap == null) {
				attachMap = new HashMap();
			}
			for (int i = 0; i < payReqBillColl.size(); i++) {
				IRow row = tblPayRequestBill.getRow(i);
				String idkey = row.getCell("id").getValue().toString();
				if (attachMap.containsKey(idkey)) {
					row.getCell("hasAttahement").setValue(Boolean.TRUE);
				} else {
					row.getCell("hasAttahement").setValue(Boolean.FALSE);
				}
			}
		}
		// super.tblMain_tableSelectChanged(e);
	}
	
	/**
	 * 获取合同ID
	 * @return 合同ID
	 * @author owen_wen 2010-12-07
	 */
	private String getContractBillId(){
		int rowIndex = getMainTable().getSelectManager().getActiveRowIndex();
		String contractBillId  = getMainTable().getRow(rowIndex).getCell("id").getValue().toString();
		return contractBillId;
	}

	/**
	 * 新增付款申请单前的参数检查<p>
	 * 合同是否上传正文控制付款申请单新增。参数值：不提示，提示控制，严格控制。
	 * 默认为不提示。参数值为不提示时，付款申请单新增不受合同是否上传正文的控制；参数值为提示控制时，
	 * 没有上传正文的合同新增付款申请单时，给出提示信息后，仍可以继续操作；参数值为严格控制时，
	 * 没有上传正文的合同新增付款申请单时，严格控制不能新增。
	 * @author owen_wen 2010-11-19
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void checkParamForAddNew() {
		//已上传正文，新增付款申请单不需要检查
		if (ContractClientUtils.isUploadFile4Contract(getContractBillId())) return; 
		
		String returnValue = "";
		BOSUuid companyId = SysContext.getSysContext().getCurrentFIUnit().getId();
		IObjectPK comPK = new ObjectUuidPK(companyId);
		try {
			returnValue = ParamControlFactory.getRemoteInstance().getParamValue(comPK, FDCConstants.FDC_PARAM_FDC324_NEWPAYREQWITHCONTRACTATT);
		} catch (EASBizException e) {
			logger.info("获取参数出现异常：" + e.getMessage());
			e.printStackTrace();
		} catch (BOSException e) {
			logger.info("获取参数出现异常：" + e.getMessage());
			e.printStackTrace();
		}
		
		if ("0".equals(returnValue)) { // 严格控制
			FDCMsgBox.showInfo("该合同没有上传正文，不能新增付款申请单！");
			SysUtil.abort();
		}else if("1".equals(returnValue)) { //提示控制
			int confirmResult  = FDCMsgBox.showConfirm2New(this, "该合同没有上传正文，是否继续新增付款申请单？");
			if (confirmResult == FDCMsgBox.NO || confirmResult == FDCMsgBox.CANCEL) // 因为用户可以按ESC关闭窗口，此时confirmResult为FDCMsgBox.CANCEL
				SysUtil.abort();
		}
	}
	
	
	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkSelected(getMainTable());
		checkContractSplitState();
		checkParamForAddNew();
		super.actionAddNew_actionPerformed(e);
	}

	// 检查拆分状态
	private void checkContractSplitState() {

		// if(!isIncorporation){
		// return ;
		// }
		// 付款申请单新增时，是否检查合同拆分完全
		if (!checkAllSplit) {
			return;
		}
		KDTable kdtContract = getMainTable();
		int selectRows[] = KDTableUtil.getSelectedRows(kdtContract);
		if (selectRows.length == 1) {
			String contractId = kdtContract.getCell(selectRows[0], "id").getValue().toString();
			if (contractId != null) {
				if (!ContractClientUtils.getContractSplitState(contractId)) {
					MsgBox.showWarning(this, FDCSplitClientHelper.getRes("conNotSplited"));
					SysUtil.abort();
				}
			}
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		//删除该付款申请单将会同步删除对应的无文本合同  处理方式已经改变了只能在无文本反审批
		/*if (flag) {
			if (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.fdc.contract.client.PayRequestBillResource", "contractwithouttext_del")))) {
				// 不继续
				return;
			}
		}*/
		FDCClientUtils.checkBillInWorkflow(this, this.getSelectedKeyValue());
		super.actionRemove_actionPerformed(e);
	}
	
	/**
	 * output actionCreateTo_actionPerformed
	 */
	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeCreateTo();
		// //2009-2-6 由于基础框架更改了关联生成的实现方式，导致付款单出不来，故重载掉使用原有方式
		// IBTPBillList billList2 = null;
		// billList2 = new BTPBillListImpl(this);
		// billList2.init();
		// CoreBillBaseCollection bills = getNewBillList();
		// billList2.createTo(bills);
		// 2008-2-9 重新使用基类关联生成方式，由于基类使用tblMain获取源单id，故做一下替换。
		KDTable temp = tblMain;
		try {
			tblMain = this.tblPayRequestBill;
			super.actionCreateTo_actionPerformed(e);
		} finally {
			tblMain = temp;
		}
	}

	// 返回分录的主键属性(实体属性名)
	public String getEntriesName() {
		return "entrys";
	}

	public String getEntriesPKName() {

		return "entrys.id";
	}

	public void actionProjectAttachment_actionPerformed(ActionEvent e) throws Exception {
		// checkSelected();
		super.actionProjectAttachment_actionPerformed(e);

		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		/*
		 * //不能直接用合同进行过滤，因为存在无文本合同的情况 String payReqBillId =
		 * this.getSelectedKeyValue(); PayRequestBillInfo info =
		 * PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new
		 * ObjectUuidPK(BOSUuid.read(payReqBillId))); CurProjectInfo curProject
		 * = info.getCurProject(); if (curProject == null) { return; }
		 */
		if (getProjSelectedTreeNode() != null && getProjSelectedTreeNode().getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo curProject = (CurProjectInfo) getProjSelectedTreeNode().getUserObject();
			acm.showAttachmentListUIByBoID(curProject.getId().toString(), this);
		} else {
			MsgBox.showWarning(this, PayReqUtils.getRes("selectPrj"));
		}
	}

	private void checkBeforeCreateTo() throws EASBizException, BOSException, UuidException {
		checkSelected(getBillListTable());

		String number = "";

		KDTable table = getBillListTable();
		ArrayList blocks = table.getSelectManager().getBlocks();
		Iterator iter = blocks.iterator();
		while (iter.hasNext()) {
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();
			if(top!=bottom){
				MsgBox.showWarning(this, "只能选择一条付款申请单记录进行关联生成！");
				SysUtil.abort();
			}
			for (int rowIndex = top;rowIndex <= bottom; rowIndex++) {
				ICell cell = table.getRow(rowIndex).getCell(getKeyFieldName());

				// 记录选中的分录ID
				if (cell.getValue() != null) {
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("state");
					selector.add("number");
					selector.add("hasClosed");
					selector.add("actPaiedLocAmount");
					selector.add("amount");
					String id = cell.getValue().toString();
					PayRequestBillInfo info = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(id)), selector);
					if (!info.getState().equals(FDCBillStateEnum.AUDITTED) || info.isHasClosed()) {
						MsgBox.showWarning(this, PayReqUtils.getRes("canntCreatTo"));
						SysUtil.abort();
					}
					if(info.getActPaiedLocAmount().compareTo(info.getAmount())>=0){
						MsgBox.showWarning(this, "已生成付款单金额不能大于申请金额！");
						SysUtil.abort();
					}
				}
			}
		}
	}

	public CoreBillBaseCollection getBillCollectionForPub() throws Exception {
		return getcontractBillList();
	}

	protected CoreBillBaseCollection getcontractBillList() throws Exception {
		checkSelected();

		ArrayList blocks = tblPayRequestBill.getSelectManager().getBlocks();
		ArrayList idList = new ArrayList();
		Iterator iter = blocks.iterator();

		List entriesKey = new ArrayList();

		while (iter.hasNext()) {
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++) {
				ICell cell = tblPayRequestBill.getRow(rowIndex).getCell(getKeyFieldName());

				// 记录选中的分录ID
				if (tblPayRequestBill.getRow(rowIndex).getCell(this.getEntriesPKName()) != null && tblPayRequestBill.getRow(rowIndex).getCell(this.getEntriesPKName()).getValue() != null) {
					entriesKey.add(tblPayRequestBill.getRow(rowIndex).getCell(this.getEntriesPKName()).getValue().toString());
				}

				if (!idList.contains(cell.getValue())) {
					idList.add(cell.getValue());
				}
			}
		}

		Assert.that(idList.size() > 0);

		if (idList.size() == 1) {
			String id = idList.get(0).toString();
			CoreBillBaseInfo sourceBillInfo;

			if (getBOTPSelectors() == null) {
				sourceBillInfo = (CoreBillBaseInfo) getCoreBaseInterface().getValue(new ObjectUuidPK(BOSUuid.read(id)));
			} else {
				sourceBillInfo = (CoreBillBaseInfo) getCoreBaseInterface().getValue(new ObjectUuidPK(BOSUuid.read(id)), getBOTPSelectors());
			}

			CoreBillBaseCollection sourceBillCollection = new CoreBillBaseCollection();
			sourceBillCollection.add(sourceBillInfo);

			// 删除集合中非选中的分录ID
			if (entriesKey.size() > 0) {
				removeUnSelect(entriesKey, sourceBillCollection);
			}

			return sourceBillCollection;
		} else {
			FilterInfo filterInfo = new FilterInfo();
			Iterator idIter = idList.iterator();
			int index = 0;
			StringBuffer sbMaskString = new StringBuffer();

			while (idIter.hasNext()) {
				String id = idIter.next().toString();
				FilterItemInfo filterItemInfo = new FilterItemInfo("id", id);

				if (index != 0) {
					sbMaskString.append(" or ");
				}

				sbMaskString.append("#");
				sbMaskString.append(String.valueOf(index));
				filterInfo.getFilterItems().add(filterItemInfo);
				index++;
			}

			filterInfo.setMaskString(sbMaskString.toString());

			EntityViewInfo entityViewInfo = new EntityViewInfo();
			entityViewInfo.setFilter(filterInfo);

			if (getBOTPSelectors() != null) {
				entityViewInfo.put("selector", getBOTPSelectors());
			}

			CoreBillBaseCollection sourceBillCollection = ((ICoreBillBase) getCoreBaseInterface()).getCoreBillBaseCollection(entityViewInfo);

			// 删除集合中非选中的分录ID
			// 删除集合中非选中的分录ID
			if (entriesKey.size() > 0) {
				removeUnSelect(entriesKey, sourceBillCollection);
			}
			return sourceBillCollection;
		}
	}

	private boolean isDAPTrans = false;

	private void removeUnSelect(List entriesKey, CoreBillBaseCollection sourceBillCollection) {
		// 如果是DAP,则不删除分录。
		if (isDAPTrans) {
			return;
		}
		// 删除集合中非选中的分录ID
		for (int i = 0; i < sourceBillCollection.size(); i++) {
			CoreBillBaseInfo bills = sourceBillCollection.get(i);

			boolean isHasSelect = false;
			IObjectCollection entries = (IObjectCollection) bills.get(this.getEntriesName());

			if (entries == null) {
				return;
			}

			Iterator iters = entries.iterator();
			int unSelect = 0;
			int count = 0;
			int ii = entries.size();
			while (iters.hasNext()) {
				CoreBaseInfo cInfo = (CoreBaseInfo) iters.next();
				String ss = cInfo.getId().toString();
				isHasSelect = false;
				for (int k = 0; k < entriesKey.size(); k++) {
					if (cInfo.get("id").toString().equals(entriesKey.get(k).toString())) {
						isHasSelect = true;
						break;
					}
				}
				if (!isHasSelect) {
					iters.remove();
					// entries.removeObject();
				}
				// count++;
			}
		}
	}

	/**
	 * output actionCopyTo_actionPerformed
	 */
	public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyTo_actionPerformed(e);
	}

	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		if (hasClicked)
			return;

		hasClicked = true;
		checkSelected(getMainTable());
		// super.actionTraceUp_actionPerformed(e);
		if (getSelectedKeyValue(getMainTable()) != null) {
			String id = getSelectedKeyValue(getMainTable());
			if (PayReqUtils.isContractBill(id)) {
				ContractPayPlanEditUI.showEditUI(this, getSelectedKeyValue(getMainTable()), "VIEW");
			} else {
				MsgBox.showWarning(this, "没有合同付款计划");
			}
		} else {
			MsgBox.showWarning(this, "没有合同付款计划");
		}

		hasClicked = false;
	}

	public void actionTranceUp2_actionPerformed(ActionEvent e) throws Exception {
		actionTraceUp_actionPerformed(e);
	}

	/**
	 * output actionTraceDown_actionPerformed
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		if (hasClicked)
			return;
		// super.actionTraceDown_actionPerformed(e);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		String id = this.getSelectedKeyValue();
		filter.getFilterItems().add(new FilterItemInfo("fdcPayReqID", id));

		Map uiContxt = new HashMap();
		uiContxt.put(UIContext.OWNER, this);
		// uiContxt.put(BOTPViewS,new Integer(1));
		PaymentFullListUI.showUI(view, uiContxt);

		hasClicked = false;
	}

	/***
	 * 重载基类的新方法 可能会有问题 旧方法getBillIdList被基类更改了范围
	 */
	protected CoreBillBaseCollection getNewBillList() throws Exception {
		return super.getBillList();
	}

	/**
	 * 获取当前表格选取的单据id和分录id
	 * 
	 * @return
	 * @throws Exception
	 */
	public void getBillIdList(List idList, List entriesList) {

		KDTable table = getBillListTable();
		int mode = 0;
		List blockList = table.getSelectManager().getBlocks();
		// 判断是整表选取还是分块选取
		if (blockList != null && blockList.size() == 1) {
			mode = ((IBlock) table.getSelectManager().getBlocks().get(0)).getMode();
		}
		if (mode == KDTSelectManager.TABLE_SELECT) {// 表选择
			List selectIdList = this.getQueryPkList();
			if (selectIdList != null) {
				Iterator lt = selectIdList.iterator();
				while (lt.hasNext()) {
					Object[] idObj = (Object[]) lt.next();
					if (idObj == null)
						continue;
					if (!idList.contains(idObj[0].toString()))
						idList.add(idObj[0].toString());
					if (idObj.length == 2)
						entriesList.add(idObj[1]);
				}
			}
		} else {
			ArrayList blocks = table.getSelectManager().getBlocks();
			Iterator iter = blocks.iterator();

			while (iter.hasNext()) {
				KDTSelectBlock block = (KDTSelectBlock) iter.next();
				int top = block.getTop();
				int bottom = block.getBottom();

				for (int rowIndex = top; rowIndex <= bottom; rowIndex++) {
					ICell cell = table.getRow(rowIndex).getCell(getKeyFieldName());

					// 记录选中的分录ID
					if (table.getRow(rowIndex).getCell(this.getEntriesPKName()) != null && table.getRow(rowIndex).getCell(this.getEntriesPKName()).getValue() != null) {
						entriesList.add(table.getRow(rowIndex).getCell(this.getEntriesPKName()).getValue().toString());
					}

					if (!idList.contains(cell.getValue())) {
						idList.add(cell.getValue());
					}
				}
			}
		}
	}

	/**
	 * output actionVoucher_actionPerformed
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionVoucher_actionPerformed(e);
	}

	/**
	 * output actionDelVoucher_actionPerformed
	 */
	public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionDelVoucher_actionPerformed(e);
	}

	/**
	 * output actionAuditResult_actionPerformed
	 */
	public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception {
		super.actionAuditResult_actionPerformed(e);
	}

	/**
	 * output actionViewDoProccess_actionPerformed
	 */
	public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		super.actionViewDoProccess_actionPerformed(e);
	}

	/**
	 * output actionMultiapprove_actionPerformed
	 */
	public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception {
		super.actionMultiapprove_actionPerformed(e);
	}

	/**
	 * output actionNextPerson_actionPerformed
	 */
	public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception {
		super.actionNextPerson_actionPerformed(e);
	}

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI.class.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.contract.PayRequestBillFactory.getRemoteInstance();
	}

	/**
	 * output getKeyFieldName method
	 */
	protected String getKeyFieldName() {
		return "id";
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.PayRequestBillInfo objectValue = new com.kingdee.eas.fdc.contract.PayRequestBillInfo();
		return objectValue;
	}

	/* 下面的几个方法来自ContractBillListUI */
	protected KDTable getBillListTable() {

		return tblPayRequestBill;
	}

	/*
	 * 重载initTable来实现对表格的冻结操作,在基类中表格默认冻结
	 * 
	 * @author sxhong 创建时间:2006-8-29
	 * 
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#initTable()
	 */
	protected void initTable() {
		super.initTable();
		// freezeMainTableColumn();
		// FDCHelper.formatTableNumber(getBillListTable(),
		// PayRequestBillContants.COL_AMOUNT);
		
		FDCHelper.formatTableNumber(tblPayRequestBill, "srcOriginalAmount");
		FDCHelper.formatTableNumber(tblPayRequestBill, "srcAmount");
		FDCHelper.formatTableNumber(tblPayRequestBill, "deduct");
		FDCHelper.formatTableNumber(tblPayRequestBill, "guerden");
		tblPayRequestBill.getColumn("srcOriginalAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblPayRequestBill.getColumn("srcAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblPayRequestBill.getColumn("deduct").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblPayRequestBill.getColumn("guerden").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	}

	/*
	 * 冻结付款申请单的需冻结列 @author sxhong 创建时间:2006-8-29
	 * 
	 * @see
	 * com.kingdee.eas.fdc.contract.client.ContractListBaseUI#freezeBillTableColumn
	 * ()
	 */
	protected void freezeBillTableColumn() {
		super.freezeBillTableColumn();
		KDTable billListTable = getBillListTable();
		/*
		 * 冻结最后一列，冻结最后一列即可，列的序号从1开始 // 状态 int state_col_index =
		 * billListTable.getColumn("state").getColumnIndex(); // 合同编码 int
		 * number_col_index = billListTable.getColumn("number")
		 * .getColumnIndex();
		 */
		// 申请金额
//		int amount_col_index = billListTable.getColumn("amount").getColumnIndex();
//		KDTViewManager viewManager = billListTable.getViewManager();
//
//		viewManager.setFreezeView(-1, amount_col_index + 1);
	}

	/**
	 * 
	 * 描述：为当前单据的新增、编辑、查看准备Context
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.CoreBillListUI#prepareUIContext(com.kingdee.eas.common.client.UIContext,
	 *      java.awt.event.ActionEvent)
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		
	}

	/**
	 * 该付款申请单是否存在附件 by Cassiel_peng
	 */
	private Map attachMap = new HashMap();

	/**
	 * 在填充Table前处理数据(附件) 因为在 tblMain_tableSelectChanged 方法中直接处理了,故此方法暂时不用 by
	 * Cassiel_peng
	 */
	/*
	 * public void onGetRowSet(IRowSet rowSet) { try { Set attachIds=new
	 * HashSet(); rowSet.beforeFirst(); while (rowSet.next()) { String
	 * boID=rowSet.getString("id"); attachIds.add(boID); }
	 * attachMap=PayReqTableHelper.handleAttachment(attachIds); } catch
	 * (Exception e) { e.printStackTrace(); }finally{ try {
	 * rowSet.beforeFirst(); } catch (SQLException e) { e.printStackTrace(); } }
	 * 
	 * 
	 * super.onGetRowSet(rowSet); }
	 */
	protected void displayBillByContract(EntityViewInfo view) throws BOSException {
		// 合计行本币
		BigDecimal localAmtAll = FDCHelper.ZERO;
		BigDecimal totalsrcAmount = FDCHelper.ZERO;
		BigDecimal totaldeduct = FDCHelper.ZERO;
		BigDecimal totalguerden = FDCHelper.ZERO;
		BigDecimal totalActPaiedLocAmount=FDCHelper.ZERO;
		if (view == null) {
			return;
		}

		PayRequestBillCollection payRequestBillCollection = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);

		if (this.getBOTPViewStatus() == 1) {
			Map botpCtx = (Map) this.getUIContext().get("BTPEDITPARAMETER");
			treeProject.setEnabled(false);
			treeContractType.setEnabled(false);
			final StyleAttributes sa = tblMain.getStyleAttributes();
			sa.setLocked(true);
			tblMain.setEnabled(false);
			// contContrList.setEnabled(false);
			if (botpCtx != null) {
				String id = botpCtx.get("contractbillid").toString();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
				filter.getFilterItems().add(new FilterItemInfo("hasClosed", Boolean.FALSE));
				filter.getFilterItems().add(new FilterItemInfo("contractId", id));
				// filter.getFilterItems().add(new
				// FilterItemInfo("hasPayoff",Boolean
				// .FALSE,CompareType.EQUALS));
				EntityViewInfo eview = new EntityViewInfo();
				eview.setFilter(filter);
				SelectorItemCollection selectors = genBillQuerySelector();
				if (selectors != null && selectors.size() > 0) {
					for (Iterator iter = selectors.iterator(); iter.hasNext();) {
						SelectorItemInfo element = (SelectorItemInfo) iter.next();
						eview.getSelector().add(element);

					}
				}
				PayRequestBillCollection pRequestBillCollection = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(eview);
				for (Iterator iter = pRequestBillCollection.iterator(); iter.hasNext();) {
					PayRequestBillInfo element = (PayRequestBillInfo) iter.next();

					int pre = element.getCurrency().getPrecision();
					String curFormat = FDCClientHelper.getNumberFormat(pre, true);
					IRow row = getBillListTable().addRow();
					row.getCell("originalAmount").getStyleAttributes().setNumberFormat(curFormat);
					row.getCell("actPaidLocAmount").getStyleAttributes().setNumberFormat(curFormat);
					row.getCell(PayRequestBillContants.COL_ID).setValue(element.getId().toString());
					row.getCell(PayRequestBillContants.COL_STATE).setValue(element.getState());
					row.getCell(PayRequestBillContants.COL_NUMBER).setValue(element.getNumber());
					row.getCell(PayRequestBillContants.COL_AMOUNT).setValue(element.getAmount());
					row.getCell(PayRequestBillContants.COL_ACTPAYAMOUNT).setValue(element.getActPaiedAmount());
					row.getCell(PayRequestBillContants.COL_ACTPAYLOCAMOUNT).setValue(element.getActPaiedLocAmount());

					row.getCell(PayRequestBillContants.COL_PAYDATE).setValue(element.getPayDate());
					if (element.getSupplier() != null) {
						row.getCell(PayRequestBillContants.COL_SUPPLIER).setValue(element.getSupplier().getName());
					}
					if (element.getCreator() != null) {
						row.getCell(PayRequestBillContants.COL_CREATER).setValue(element.getCreator().getName());
					}
					row.getCell(PayRequestBillContants.COL_CREATETIME).setValue(element.getCreateTime());
					row.getCell(PayRequestBillContants.COL_MONEYDESC).setValue(element.getUsage());
					row.getCell(PayRequestBillContants.COL_RECBANK).setValue(element.getRecBank());
					row.getCell(PayRequestBillContants.COL_RECACCOUNT).setValue(element.getRecAccount());
					if (element.getAuditor() != null) {
						row.getCell(PayRequestBillContants.COL_AUDITOR).setValue(element.getAuditor().getName());
					}
					row.getCell(PayRequestBillContants.COL_AUDITDATE).setValue(element.getAuditTime());
					row.getCell(PayRequestBillContants.COL_DESC).setValue(element.getDescription());
					row.getCell(PayRequestBillContants.COL_ATTACHMENT).setValue(new Integer(element.getAttachment()));

					row.getCell(COL_DATE).setValue(element.getBookedDate());
					row.getCell(COL_PERIOD).setValue(element.getPeriod());

					row.getCell("currency").setValue(element.getCurrency().getName());
					row.getCell("originalAmount").setValue(element.getOriginalAmount());
					row.getCell(PayRequestBillContants.COL_ISRESPITE).setValue(new Boolean(element.isIsRespite()));
					if (element.getPaymentType() != null) {
						row.getCell("paymentType").setValue(element.getPaymentType().getName());
					}
					row.getCell("isInvoice").setValue(Boolean.valueOf(element.isIsInvoice()));
					// 求合计行本币值
					localAmtAll = FDCHelper.add(localAmtAll, element.getAmount());
					tblPayRequestBill.getColumn(PayRequestBillContants.COL_ATTACHMENT).getStyleAttributes().setHided(true);
					
					row.getCell("srcOriginalAmount").setValue(element.getProjectPriceInContractOri());
					row.getCell("srcAmount").setValue(element.getProjectPriceInContract());
					BigDecimal deduct=FDCHelper.subtract(FDCHelper.add(element.getProjectPriceInContract(), element.getGuerdonOriginalAmt()), element.getAmount());
					if(deduct!=null&&deduct.compareTo(FDCHelper.ZERO)!=0){
						row.getCell("deduct").setValue(deduct);
					}
					if(element.getGuerdonOriginalAmt()!=null&&element.getGuerdonOriginalAmt().compareTo(FDCHelper.ZERO)!=0){
						row.getCell("guerden").setValue(element.getGuerdonOriginalAmt());
					}
					totalsrcAmount=FDCHelper.add(totalsrcAmount, element.getProjectPriceInContract());
					totaldeduct = FDCHelper.add(totaldeduct, deduct);
					totalguerden = FDCHelper.add(totalguerden, element.getGuerdonOriginalAmt());
					totalActPaiedLocAmount=FDCHelper.add(totalguerden, element.getActPaiedLocAmount());
				}
			} else {
				EntityViewInfo boCtx = (EntityViewInfo) this.getUIContext().get("BOTPFilter");
				SelectorItemCollection selectors = genBillQuerySelector();
				if (selectors != null && selectors.size() > 0) {
					for (Iterator iter = selectors.iterator(); iter.hasNext();) {
						SelectorItemInfo element = (SelectorItemInfo) iter.next();
						boCtx.getSelector().add(element);

					}
				}
				PayRequestBillCollection pRequestBillCollection = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(boCtx);
				for (Iterator iter = pRequestBillCollection.iterator(); iter.hasNext();) {
					PayRequestBillInfo element = (PayRequestBillInfo) iter.next();
					int pre = element.getCurrency().getPrecision();
					String curFormat = FDCClientHelper.getNumberFormat(pre, true);
					IRow row = getBillListTable().addRow();
					row.getCell("originalAmount").getStyleAttributes().setNumberFormat(curFormat);
					row.getCell("actPaidLocAmount").getStyleAttributes().setNumberFormat(curFormat);
					row.getCell(PayRequestBillContants.COL_ID).setValue(element.getId().toString());
					row.getCell(PayRequestBillContants.COL_STATE).setValue(element.getState());
					row.getCell(PayRequestBillContants.COL_NUMBER).setValue(element.getNumber());
					row.getCell(PayRequestBillContants.COL_AMOUNT).setValue(element.getAmount());
					row.getCell(PayRequestBillContants.COL_ACTPAYAMOUNT).setValue(element.getActPaiedAmount());
					row.getCell(PayRequestBillContants.COL_ACTPAYLOCAMOUNT).setValue(element.getActPaiedLocAmount());
					row.getCell(PayRequestBillContants.COL_PAYDATE).setValue(element.getPayDate());
					if (element.getSupplier() != null) {
						row.getCell(PayRequestBillContants.COL_SUPPLIER).setValue(element.getSupplier().getName());
					}
					if (element.getCreator() != null) {
						row.getCell(PayRequestBillContants.COL_CREATER).setValue(element.getCreator().getName());
					}
					row.getCell(PayRequestBillContants.COL_CREATETIME).setValue(element.getCreateTime());
					row.getCell(PayRequestBillContants.COL_MONEYDESC).setValue(element.getUsage());
					row.getCell(PayRequestBillContants.COL_RECBANK).setValue(element.getRecBank());
					row.getCell(PayRequestBillContants.COL_RECACCOUNT).setValue(element.getRecAccount());
					if (element.getAuditor() != null) {
						row.getCell(PayRequestBillContants.COL_AUDITOR).setValue(element.getAuditor().getName());
					}
					row.getCell(PayRequestBillContants.COL_AUDITDATE).setValue(element.getAuditTime());
					row.getCell(PayRequestBillContants.COL_DESC).setValue(element.getDescription());
					row.getCell(PayRequestBillContants.COL_ATTACHMENT).setValue(new Integer(element.getAttachment()));

					row.getCell(COL_DATE).setValue(element.getBookedDate());
					row.getCell(COL_PERIOD).setValue(element.getPeriod());

					row.getCell("currency").setValue(element.getCurrency().getName());
					row.getCell("originalAmount").setValue(element.getOriginalAmount());
					if (element.getPaymentType() != null) {
						row.getCell("paymentType").setValue(element.getPaymentType().getName());
					}
					row.getCell("isInvoice").setValue(Boolean.valueOf(element.isIsInvoice()));
					row.getCell(PayRequestBillContants.COL_ISRESPITE).setValue(new Boolean(element.isIsRespite()));
					
					// 求合计行本币值
					localAmtAll = FDCHelper.add(localAmtAll, element.getAmount());
					tblPayRequestBill.getColumn(PayRequestBillContants.COL_ATTACHMENT).getStyleAttributes().setHided(true);
					
					row.getCell("srcOriginalAmount").setValue(element.getProjectPriceInContractOri());
					row.getCell("srcAmount").setValue(element.getProjectPriceInContract());
					BigDecimal deduct=FDCHelper.subtract(FDCHelper.add(element.getProjectPriceInContract(), element.getGuerdonOriginalAmt()), element.getAmount());
					if(deduct!=null&&deduct.compareTo(FDCHelper.ZERO)!=0){
						row.getCell("deduct").setValue(deduct);
					}
					if(element.getGuerdonOriginalAmt()!=null&&element.getGuerdonOriginalAmt().compareTo(FDCHelper.ZERO)!=0){
						row.getCell("guerden").setValue(element.getGuerdonOriginalAmt());
					}
					totalsrcAmount=FDCHelper.add(totalsrcAmount, element.getProjectPriceInContract());
					totaldeduct = FDCHelper.add(totaldeduct, deduct);
					totalguerden = FDCHelper.add(totalguerden, element.getGuerdonOriginalAmt());
					totalActPaiedLocAmount=FDCHelper.add(totalguerden, element.getActPaiedLocAmount());
				}
			}

		} else {
			for (Iterator iter = payRequestBillCollection.iterator(); iter.hasNext();) {
				PayRequestBillInfo element = (PayRequestBillInfo) iter.next();

				int pre = element.getCurrency().getPrecision();
				String curFormat = FDCClientHelper.getNumberFormat(pre, true);
				IRow row = getBillListTable().addRow();
				row.getCell("originalAmount").getStyleAttributes().setNumberFormat(curFormat);
				row.getCell("actPaidLocAmount").getStyleAttributes().setNumberFormat(curFormat);
				row.getCell("actPaidLocAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				row.getCell(PayRequestBillContants.COL_ID).setValue(element.getId().toString());
				row.getCell(PayRequestBillContants.COL_STATE).setValue(element.getState());
				row.getCell(PayRequestBillContants.COL_NUMBER).setValue(element.getNumber());
				row.getCell(PayRequestBillContants.COL_AMOUNT).setValue(element.getAmount());
				row.getCell(PayRequestBillContants.COL_ACTPAYAMOUNT).setValue(element.getActPaiedAmount());
				row.getCell(PayRequestBillContants.COL_ACTPAYLOCAMOUNT).setValue(element.getActPaiedLocAmount());
				row.getCell(PayRequestBillContants.COL_PAYDATE).setValue(element.getPayDate());
				if (element.getSupplier() != null) {
					row.getCell(PayRequestBillContants.COL_SUPPLIER).setValue(element.getSupplier().getName());
				}
				if (element.getCreator() != null) {
					row.getCell(PayRequestBillContants.COL_CREATER).setValue(element.getCreator().getName());
				}
				row.getCell(PayRequestBillContants.COL_CREATETIME).setValue(element.getCreateTime());
				row.getCell(PayRequestBillContants.COL_MONEYDESC).setValue(element.getUsage());
				row.getCell(PayRequestBillContants.COL_RECBANK).setValue(element.getRecBank());
				row.getCell(PayRequestBillContants.COL_RECACCOUNT).setValue(element.getRecAccount());
				if (element.getAuditor() != null) {
					row.getCell(PayRequestBillContants.COL_AUDITOR).setValue(element.getAuditor().getName());
				}
				row.getCell(PayRequestBillContants.COL_AUDITDATE).setValue(element.getAuditTime());
				row.getCell(PayRequestBillContants.COL_DESC).setValue(element.getDescription());
				row.getCell(PayRequestBillContants.COL_ATTACHMENT).setValue(new Integer(element.getAttachment()));
				row.getCell(PayRequestBillContants.COL_ATTACHMENT).getStyleAttributes().setHided(true);

				row.getCell(COL_DATE).setValue(element.getBookedDate());
				row.getCell(COL_PERIOD).setValue(element.getPeriod());

				row.getCell("currency").setValue(element.getCurrency().getName());
				row.getCell("originalAmount").setValue(element.getOriginalAmount());
				if (element.getPaymentType() != null) {
					row.getCell("paymentType").setValue(element.getPaymentType().getName());
				}
				row.getCell("isInvoice").setValue(Boolean.valueOf(element.isIsInvoice()));
				row.getCell(PayRequestBillContants.COL_ISRESPITE).setValue(new Boolean(element.isIsRespite()));
				// 求合计行本币值
				localAmtAll = FDCHelper.add(localAmtAll, element.getAmount());
				tblPayRequestBill.getColumn(PayRequestBillContants.COL_ATTACHMENT).getStyleAttributes().setHided(true);
				
				row.getCell("srcOriginalAmount").setValue(element.getProjectPriceInContractOri());
				row.getCell("srcAmount").setValue(element.getProjectPriceInContract());
				BigDecimal deduct=FDCHelper.subtract(FDCHelper.add(element.getProjectPriceInContract(), element.getGuerdonOriginalAmt()), element.getAmount());
				if(deduct!=null&&deduct.compareTo(FDCHelper.ZERO)!=0){
					row.getCell("deduct").setValue(deduct);
				}
				if(element.getGuerdonOriginalAmt()!=null&&element.getGuerdonOriginalAmt().compareTo(FDCHelper.ZERO)!=0){
					row.getCell("guerden").setValue(element.getGuerdonOriginalAmt());
				}
				
				totalsrcAmount=FDCHelper.add(totalsrcAmount, element.getProjectPriceInContract());
				totaldeduct = FDCHelper.add(totaldeduct, deduct);
				totalguerden = FDCHelper.add(totalguerden, element.getGuerdonOriginalAmt());
				totalActPaiedLocAmount=FDCHelper.add(totalguerden, element.getActPaiedLocAmount());
			}
		}
		KDTFootManager footRowManager = tblPayRequestBill.getFootManager();
		IRow footRow = null;
		if (footRowManager == null) {
			footRowManager = new KDTFootManager(tblPayRequestBill);
			footRowManager.addFootView();
			tblPayRequestBill.setFootManager(footRowManager);
			footRow = tblPayRequestBill.addFootRow(0);
			footRow.getCell(PaymentBillContants.COL_AMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			tblPayRequestBill.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			tblPayRequestBill.getIndexColumn().setWidth(60);
			footRow.getCell(PaymentBillContants.COL_AMOUNT).setValue(localAmtAll);
			footRow.getStyleAttributes().setNumberFormat(FDCHelper.strDataFormat);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			footRowManager.addIndexText(0, "合计");
			
			footRow.getCell("srcAmount").setValue(totalsrcAmount);
			footRow.getCell("deduct").setValue(totaldeduct);
			footRow.getCell("guerden").setValue(totalguerden);
			footRow.getCell("actPaidLocAmount").setValue(totalActPaiedLocAmount);
		} else {
			footRow = tblPayRequestBill.getFootRow(0);
			footRow.getCell(PaymentBillContants.COL_AMOUNT).setValue(localAmtAll);
			
			footRow.getCell("srcAmount").setValue(totalsrcAmount);
			footRow.getCell("deduct").setValue(totaldeduct);
			footRow.getCell("guerden").setValue(totalguerden);
			footRow.getCell("actPaidLocAmount").setValue(totalActPaiedLocAmount);
		}
	}

	/**
	 * 生成付款申请单的selector 项为PayRequestBillContants中COL_开头的常量
	 * 
	 * @author sxhong Date 2006-9-11
	 * @return
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#genBillQuerySelector()
	 */
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selector = new SelectorItemCollection();
		Field[] fields = PayRequestBillContants.class.getFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().startsWith("COL_")) {
				try {
					selector.add(fields[i].get(null).toString());
				} catch (Exception e) {
					super.handUIException(e);
				}
			}
		}

		selector.add("usage");

		selector.add("period.number");
		selector.add("period.periodNumber");
		selector.add("period.periodYear");
		selector.add("bookedDate");
		selector.add("currency.name");

		selector.add("paymentType.name");
		selector.add("currency.precision");
		selector.add("originalAmount");
		selector.add("actPaiedAmount");
		selector.add("actPaiedLocAmount");
		selector.add("isRespite");
		selector.add("isInvoice");
		selector.add("projectPriceInContractOri");
		selector.add("projectPriceInContract");
		selector.add("guerdonOriginalAmt");
		return selector;
	}

	protected String[] getBillStateForEditOrRemove() {

		return new String[] { FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE };
	}

	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return PayRequestBillFactory.getRemoteInstance();
	}

	protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	}

	protected String getStateForUnAudit() {
		return FDCBillStateEnum.AUDITTED_VALUE;

	}

	/**
	 * Description: 显示特定状态的合同
	 * 
	 * @author sxhong Date 2006-9-6
	 * @return
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#getTreeSelectChangeFilter()
	 */
	protected FilterInfo getTreeSelectChangeFilter() {
		/*
		 * FilterInfo filter = new FilterInfo(); filter.getFilterItems().add(new
		 * FilterItemInfo("state", ContractStateEnum.AUDITTED_VALUE));
		 * filter.getFilterItems().add(new
		 * FilterItemInfo("contractType.isEnabled", Boolean.TRUE)); return
		 * filter;
		 */
		return super.getTreeSelectChangeFilter();
	}

	protected void audit(List ids) throws Exception {
		PayRequestBillFactory.getRemoteInstance().audit(ids);
	}

	protected void unAudit(List ids) throws Exception {
		PayRequestBillFactory.getRemoteInstance().unAudit(ids);
	}

	protected boolean isHasBillTable() {
		return true;
	}

	protected EntityViewInfo genBillQueryView(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
		KDTSelectBlock selectBlock = e.getSelectBlock();
		int top = selectBlock.getTop();
		if (getMainTable().getCell(top, getKeyFieldName()) == null) {
			return null;
		}
		String contractId = (String) getMainTable().getCell(top, getKeyFieldName()).getValue();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractId", contractId));
		// filter.appendFilterItem("contractBill.isAmtWithoutCost",
		// String.valueOf(0));
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo(getBillStatePropertyName()));
		view.getSorter().add(new SorterItemInfo("number"));

		SelectorItemCollection selectors = genBillQuerySelector();
		if (selectors != null && selectors.size() > 0) {
			for (Iterator iter = selectors.iterator(); iter.hasNext();) {
				SelectorItemInfo element = (SelectorItemInfo) iter.next();
				view.getSelector().add(element);

			}
		}
		return view;
	}

	protected void buildContractTypeTree() throws Exception {
		super.buildContractTypeTree();
		KingdeeTreeModel treeModel = (KingdeeTreeModel) treeContractType.getModel();
		DefaultKingdeeTreeNode oldRootNode = (DefaultKingdeeTreeNode) treeModel.getRoot();
		// oldRootNode.setText(PayReqUtils.getRes("contract"));
		// KDTreeNode root=new KDTreeNode("root");
		// root.add(oldRootNode);
		// treeModel.setRoot(root);
		KDTreeNode node = new KDTreeNode("ContractWithoutText");
		node.setText(PayReqUtils.getRes("contractWithoutText"));
		treeContractType.addNodeInto(node, oldRootNode);// 用无合同类型内的数据来模拟
		// treeContractType.setRootVisible(false);
		// treeContractType.expandAllNodes(true, oldRootNode);
		// treeContractType.expandPath(new TreePath(oldRootNode.getPath()));

	}

	private boolean flag = false;
	private static ResourceBundleHelper resHelperWithout = new ResourceBundleHelper(AbstractContractWithoutTextListUI.class.getName());

	protected void treeContractType_valueChanged(TreeSelectionEvent e) throws Exception {
		if (getTypeSelectedTreeNode() == null)
			return;
		KDTreeNode oldNode = null;
		if (e.getOldLeadSelectionPath() != null) {
			oldNode = (KDTreeNode) e.getOldLeadSelectionPath().getLastPathComponent();
		}

		// 选择无文本合同
		if (getTypeSelectedTreeNode().getUserObject().equals("ContractWithoutText")) {
			flag = true;
			if (oldNode == null || !oldNode.getUserObject().equals("ContractWithoutText")) {
				mainQuery.getSelector().clear();
				mainQuery.getFilter().getFilterItems().clear();
				mainQuery.getSorter().clear();
				this.tblMain.setFormatXml(resHelperWithout.getString("tblMain.formatXml"));
				this.tblMain.putBindContents("mainQuery", new String[] { "id", "bookedDate", "period.number", "state", "number", "billName", "contractType.name","contract.name", "currency.name", "originalAmount",
						"amount", "receiveUnit.name","useDeptment","signDate", "isInvoice","currency.id", "currency.precision"});
				mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ContractWithoutTextQuery");
				tblMain.checkParsed(true);

			}
			contractWithoutTextSelect();

		} else {
			flag = false;
			// 测试
			if (oldNode == null || oldNode.getUserObject().equals("ContractWithoutText")) {
				mainQuery.getSelector().clear();
				mainQuery.getFilter().getFilterItems().clear();
				mainQuery.getSorter().clear();
				this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
				this.tblMain.putBindContents("mainQuery", new String[] { "id", "bookedDate", "period.number", "state", "hasSettled", "contractType.name", "number", "name", "currency.name",
						"originalAmount", "amount", "partB.name", "contractSource", "signDate", "landDeveloper.name", "partC.name", "costProperty", "contractPropert", "entrys.id", "currency.id",
						"currency.precision" });
				mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ContractBillQuery");
				tblMain.checkParsed(true);
			}
			super.treeContractType_valueChanged(e);
		}
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		//getMainTable().getColumn("amount").getStyleAttributes().setNumberFormat
		// ("###,##0.00");
		FDCHelper.formatTableNumber(getMainTable(), "originalAmount");
		FDCHelper.formatTableNumber(getMainTable(), "amount");
	}

	private void contractWithoutTextSelect() throws Exception {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		// filterItems.add(new FilterItemInfo("contractType.isEnabled",
		// Boolean.TRUE));

		/*
		 * 工程项目树
		 */
		if (getProjSelectedTreeNode() != null && getProjSelectedTreeNode().getUserObject() instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) getProjSelectedTreeNode().getUserObject();

			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				BOSUuid id = ((OrgStructureInfo) projTreeNodeInfo).getUnit().getId();

				Set idSet = FDCClientUtils.genOrgUnitIdSet(id);
				filterItems.add(new FilterItemInfo("orgUnit.id", idSet, CompareType.INCLUDE));
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				BOSUuid id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("curProject.id", idSet, CompareType.INCLUDE));
			}

		}

		mainQuery.setFilter(filter);

		execQuery();

		if (isHasBillTable()) {
			getBillListTable().removeRows(false);
		}

		getMainTable().repaint();
		if (getMainTable().getRowCount() > 0) {
			getMainTable().getSelectManager().select(0, 0);

			// getMainTable().getRow(0).s
			// 无文本合同不能新增生成付款申请单
			btnAddNew.setEnabled(false);
			actionAddNew.setEnabled(false);
			menuItemAddNew.setEnabled(false);
		}

		tHelper.init();
		tHelper.setQuerySolutionInfo(null);
	}

	protected void treeProject_valueChanged(TreeSelectionEvent e) throws Exception {
		if (getTypeSelectedTreeNode() != null && getTypeSelectedTreeNode().getUserObject().equals("ContractWithoutText")) {
			contractWithoutTextSelect();
		} else {
			super.treeProject_valueChanged(e);
		}
	}

	protected void updateButtonStatus() {

		// super.updateButtonStatus();

		// 如果是虚体成本中心，则不能审批,反审批
		/*
		 * if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
		 * actionAudit.setEnabled(false); actionUnAudit.setEnabled(false); }
		 */
		actionSelectDeduct.setEnabled(true);
		tblPayRequestBill.getColumn(PayRequestBillContants.COL_ACTPAYLOCAMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		actionRespite.setVisible(false);
		actionCancelRespite.setVisible(false);
	}

	/**
	 * 在合同最终结算后，付款申请单提交、审批时，如果合同实付款(不含本次) 加 本次付款申请单合同内工程款本期发生大于合同结算价给出相应提示 by
	 * cassiel_peng 2009-12-03
	 */
	private void checkPrjPriceInContract() throws Exception {

		checkSelected(this.tblPayRequestBill);

		// 获取用户选择的块
		List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.list2Set(idList), CompareType.INCLUDE));
		view.getSelector().add("id");
		view.getSelector().add("contractId");
		view.getSelector().add("totalSettlePrice");
		view.getSelector().add("paymentType.name");
		Set payReqBillSet = new HashSet();
		// 一次取出
		PayRequestBillCollection payReqBillColls = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
		for (int i = 0; i < payReqBillColls.size(); i++) {
			PayRequestBillInfo payReqBill = payReqBillColls.get(i);
			payReqBillSet.add(payReqBill);
		}

		boolean isCanPass = PayReqUtils.checkProjectPriceInContract(payReqBillSet, null);
		if (!isCanPass) {
			MsgBox.showError("合同实付款不能大于合同结算价【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】");
			SysUtil.abort();
		}
		/**
		 * 当付款申请单所属的合同还未最终结算的情况下，在申请单保存、提交、审批时，
		 * 检查合同实付款是否大于已实现产值，若大于，则提示“未结算合同的实付款不能大于 已实现产值【合同实付款
		 * =已关闭的付款申请单对应的付款单合同内工程款合计 + 未 关闭的付款申请单合同内工程款合计】”，若启用了本参数，则不能提交或审批通过，
		 * 若未启用，则在提示之后允许提交、审批。 by jian_wen 2009.12.17
		 */
		// 一次取出
		Map paramMap = FDCUtils.getDefaultFDCParam(null, orgUnit.getId().toString());
		boolean b = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_ISCONTROLPAYMENT);
		boolean bl = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_MORESETTER);
		for (Iterator it = payReqBillSet.iterator(); it.hasNext();) {// 此循环只需执行一次
			PayRequestBillInfo payReqBill = (PayRequestBillInfo) it.next();
			// 如果合同已经最终结算
			ContractBillInfo contract = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(payReqBill.getContractId())));
			if (contract.isHasSettled()) {
				return;
			}
			// 预付款类型的申请单提交时不按此参数控制
			if (payReqBill.getPaymentType().getName().equals("预付款")) {

				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("paymentType.name", "%预付款%", CompareType.NOTLIKE));
				filter.getFilterItems().add(new FilterItemInfo("contractId", payReqBill.getContractId()));
				if (payReqBill.getId() != null) {
					filter.getFilterItems().add(new FilterItemInfo("id", payReqBill.getId().toString(), CompareType.NOTEQUALS));
				}
				// 存在进度款时受控制
				if (!PayRequestBillFactory.getRemoteInstance().exists(filter)) {
					continue;
				}
			}

			// boolean b=FDCUtils.getDefaultFDCParamByKey(null, null,
			// FDCConstants.FDC_PARAM_ISCONTROLPAYMENT);
			// 合同是否可进行多次结算
			// boolean bl=FDCUtils.getDefaultFDCParamByKey(null, null,
			// FDCConstants.FDC_PARAM_MORESETTER);
			// 取合同实付款
			BigDecimal payAmt = FDCHelper.toBigDecimal(ContractClientUtils.getPayAmt(payReqBill.getContractId()), 2);
			// 处理空值并处理小数
			if (payAmt != null && payAmt.compareTo(FDCHelper.toBigDecimal(payReqBill.getTotalSettlePrice(), 2)) == 1) {
				if (b) {// 启用了参数 提示错误信息并中断
					MsgBox.showError("未结算合同的实付款不能大于已实现产值【合同实付款=已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申 请单合同内工程款合计】");
					abort();
				} else {
					if (bl) {// 只启用了合同是否可进行多次结算 没启用 参数 就只 提示 但可以继续操作
						MsgBox.showWarning("未结算合同的实付款不能大于已实现产值【合同实付款=已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申 请单合同内工程款合计】");
					}
				}
			}
			return;
		}
	}

	private boolean isOpenPaymentBillEditUI() {
		boolean isOpenPaymentBillEditUI = false;
		try {
			isOpenPaymentBillEditUI = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_ISOPENPAYMENTEDITUI);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isOpenPaymentBillEditUI;
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		// BUG:无文本合同审批生成的请款单再次点击“审批”按钮时提示"该记录已经不存在"
		// 无文本合同生成的请款单没有审批和反审批操作，在这里直接提示就可以了 by cassiel_peng 2010-04-06
		int selectRows[] = KDTableUtil.getSelectedRows(tblMain);
		if (selectRows.length == 1) {
			String contractId = tblMain.getCell(selectRows[0], "id").getValue().toString();
			if (contractId != null && !FDCUtils.isContractBill(null, contractId)) {
				MsgBox.showWarning("存在不符合审批条件的记录，请重新选择，保证所选的记录都是提交状态的。");
				SysUtil.abort();
			}
			//重写父类方法，注意父类方法中其它方法是否需要重写
			checkBillState(new String[]{getStateForAudit()}, "selectRightRowForAudit");
			
			checkContractSplitState();
			checkPrjPriceInContract();

			// 加入数据互斥
			ArrayList list = this.getSelectedPayRequestBillIdValues();
			boolean hasMutex = false;
			try {
				FDCClientUtils.requestDataObjectLock(this, list, "Audit");

				if (isOpenPaymentBillEditUI()) {
					UIContext uiContext = new UIContext(this);
					BOSUuid billId = PayRequestBillFactory.getRemoteInstance().auditAndOpenPayment(BOSUuid.read(list.get(0).toString()));
					if (billId != null) {
						//取消弹出框 by Eric_Wang 2010.05.21
						//int result = MsgBox.showConfirm2New(null, "已经生成对应的付款单，是否打开付款单？");
						//if (JOptionPane.YES_OPTION == result) {
							uiContext.put(UIContext.ID, billId);
							IUIFactory uiFactory = null;
							uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
							IUIWindow dialog = uiFactory.create(PaymentBillEditUI.class.getName(), uiContext, null, OprtState.EDIT);
							dialog.show();
						//} else {
						//	showOprtOKMsgAndRefresh();
						//}
					}

				} else {
					super.actionAudit_actionPerformed(e);
				}
				refreshList();

				// super.actionAudit_actionPerformed(e);

			} catch (Throwable e1) {
				this.handUIException(e1);
				hasMutex = FDCClientUtils.hasMutexed(e1);
			} finally {
				if (!hasMutex) {
					try {
						FDCClientUtils.releaseDataObjectLock(this, list);
					} catch (Throwable e1) {
						this.handUIException(e1);
					}
				}
			}
		}
	}

	/**
	 * 返回所选付款申请单列表中行的ID列表，用于数据互斥。
	 * 
	 * @return
	 */
	protected ArrayList getSelectedPayRequestBillIdValues() {
		ArrayList selectList = new ArrayList();
		List selectKeyIdFields = null;
		int mode = 0;
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblPayRequestBill);
		List blockList = this.tblPayRequestBill.getSelectManager().getBlocks();
		// 判断是整表选取还是分块选取

		if (blockList != null && blockList.size() == 1) {
			mode = ((IBlock) this.tblPayRequestBill.getSelectManager().getBlocks().get(0)).getMode();
		}
		if (mode == KDTSelectManager.TABLE_SELECT && selectRows.length >= KDTDataRequestManager.defaultPageRow - 1) {
			selectKeyIdFields = this.getQueryPkList();
		}
		return ListUiHelper.getSelectedIdValues(this.tblPayRequestBill, this.getKeyFieldName(), selectList, selectKeyIdFields);
	}

	/**
	 * 付款申请单累计额大于合同金额最新造价时提醒（提交，审批）//暂时不做处理
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 */
	private void checkAmt(PayRequestBillInfo billInfo) throws BOSException {
		if (billInfo.getLatestPrice() == null) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("contractId", billInfo.getContractId());
		view.getSelector().add("amount");
		PayRequestBillCollection c = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
		BigDecimal total = FDCHelper.ZERO;
		for (int i = 0; i < c.size(); i++) {
			total = total.add(FDCHelper.toBigDecimal(c.get(0).getAmount()));
		}
		if (total.compareTo(billInfo.getLatestPrice()) > 0) {
			MsgBox.showInfo(this, "付款申请单的累计金额大于合同最新造价");
		}

	}

	protected boolean isFootVisible() {
		return true;
	}

	protected boolean isOrderPre() {
		return false;
	}

	/**
	 * 合同类型树是否有无文本合同
	 * 
	 * @return
	 */
	protected boolean containConWithoutTxt() {
		return true;
	}

	protected void fetchInitData() throws Exception {

		super.fetchInitData();

	}

	/**
	 * RPC改造，任何一次事件只有一次RPC
	 */

	public boolean isPrepareInit() {
		return true;
	}

	public boolean isPrepareActionSubmit() {
		return true;
	}

	public boolean isPrepareActionSave() {
		return true;
	}

	/*    *//**
	 * EditUI提供的初始化handler参数方法
	 */
	/*
	 * protected RequestContext PrepareHandlerParam(RequestContext request) {
	 * return super.PrepareHandlerParam(request); }
	 */
	public SelectorItemCollection getBOTPSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("creator.*"));
		sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		sic.add(new SelectorItemInfo("CU.*"));
		sic.add(new SelectorItemInfo("handler.*"));
		sic.add(new SelectorItemInfo("auditor.*"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		sic.add(new SelectorItemInfo("period.*"));
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("entrys.paymentBill.*"));
		sic.add(new SelectorItemInfo("curProject.*"));
		sic.add(new SelectorItemInfo("useDepartment.*"));
		sic.add(new SelectorItemInfo("supplier.*"));
		sic.add(new SelectorItemInfo("realSupplier.*"));
		sic.add(new SelectorItemInfo("paymentType.*"));
		sic.add(new SelectorItemInfo("currency.*"));
		sic.add(new SelectorItemInfo("settlementType.*"));
		sic.add(new SelectorItemInfo("conPayplan.*"));
		return sic;
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		FDCClientHelper.checkAuditor(getSelectedIdValues(), "T_Con_PayRequestBill");
		super.actionUnAudit_actionPerformed(e);
	}

	public void actionContractAttachment_actionPerformed(ActionEvent e) throws Exception {
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		KDTable kdtContract = getMainTable();
		int selectRows[] = KDTableUtil.getSelectedRows(kdtContract);
		if (selectRows.length == 1) {
			String contractId = kdtContract.getCell(selectRows[0], "id").getValue().toString();
			if (contractId != null) {
				acm.showAttachmentListUIByBoID(contractId, this, false);
			}
		} else {
			MsgBox.showWarning(this, "请先选择一条合同记录");
		}
	}

	public void onShow() throws Exception {
		super.onShow();
		tblPayRequestBill.getColumn(PayRequestBillContants.COL_ACTPAYLOCAMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblPayRequestBill.getColumn(PayRequestBillContants.COL_ATTACHMENT).getStyleAttributes().setHided(true);
	}

	protected FilterInfo getTreeSelectFilter(Object projectNode, Object typeNode, boolean containConWithoutTxt) throws Exception {
		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();
		String companyID = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		/*
		 * 工程项目树
		 */
		if (projectNode != null && projectNode instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			boolean isCompanyUint = false;
			FullOrgUnitInfo selectedOrg = new FullOrgUnitInfo();
			BOSUuid id = null;
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {

				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					id = ((OrgStructureInfo) projTreeNodeInfo).getUnit().getId();
					selectedOrg = ((OrgStructureInfo) projTreeNodeInfo).getUnit();
					isCompanyUint = selectedOrg.isIsCompanyOrgUnit();
				} else {
					selectedOrg = (FullOrgUnitInfo) projTreeNodeInfo;
					id = ((FullOrgUnitInfo) projTreeNodeInfo).getId();
					isCompanyUint = selectedOrg.isIsCompanyOrgUnit();
				}

				String orgUnitLongNumber = null;
				if (orgUnit != null && id.toString().equals(orgUnit.getId().toString())) {
					orgUnitLongNumber = orgUnit.getLongNumber();
				} else {
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}

				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%", CompareType.LIKE));

				f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
				f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs, CompareType.INCLUDE));

				f.setMaskString("#0 and #1 and #2");
				if (FDCUtils.getDefaultFDCParamByKey(null, companyID, FDCConstants.FDC_PARAM_CROSSPROJECTSPLIT) && (!isCompanyUint)) {
					FilterInfo f2 = new FilterInfo();
					Set proSet = FDCClientUtils.getProjIdsOfCostOrg(selectedOrg, true);
					String filterSplitSql = "select fcontractbillid from T_con_contractCostSplit head " + " inner join T_Con_contractCostSplitEntry entry on head.fid=entry.fparentid "
							+ " inner join T_FDC_CostAccount acct on acct.fid=entry.fcostaccountid where acct.fcurProject in " + " (" + FDCClientUtils.getSQLIdSet(FDCClientUtils.getSQLIdSet(proSet))
							+ ") and fstate<>'9INVALID'";
					f2.getFilterItems().add(new FilterItemInfo("id", filterSplitSql, CompareType.INNER));
					f2.setMaskString("(#0 or #1)");
					if (filter != null) {
						filter.mergeFilter(f2, "and");
					}
				}
				if (filter != null) {
					filter.mergeFilter(f, "and");
				}
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(new FilterItemInfo("curProject.id", idSet, CompareType.INCLUDE));
				if (FDCUtils.getDefaultFDCParamByKey(null, companyID, FDCConstants.FDC_PARAM_CROSSPROJECTSPLIT)) {
					String filterSplitSql = "select fcontractbillid from T_con_contractCostSplit head " + " inner join T_Con_contractCostSplitEntry entry on head.fid=entry.fparentid "
							+ " inner join T_FDC_CostAccount acct on acct.fid=entry.fcostaccountid where acct.fcurProject in " + " (" + FDCClientUtils.getSQLIdSet(idSet) + ") and fstate<>'9INVALID'";
					f.getFilterItems().add(new FilterItemInfo("id", filterSplitSql, CompareType.INNER));
					f.setMaskString("(#0 or #1)");

				}
				if (filter != null) {
					filter.mergeFilter(f, "and");
				}
			}
		}

		FilterInfo typefilter = new FilterInfo();
		FilterItemCollection typefilterItems = typefilter.getFilterItems();
		/*
		 * 合同类型树
		 */
		if (typeNode != null && typeNode instanceof TreeBaseInfo) {
			TreeBaseInfo typeTreeNodeInfo = (TreeBaseInfo) typeNode;
			BOSUuid id = typeTreeNodeInfo.getId();
			Set idSet = FDCClientUtils.genContractTypeIdSet(id);
			typefilterItems.add(new FilterItemInfo("contractType.id", idSet, CompareType.INCLUDE));
		} else if (containConWithoutTxt && typeNode != null && typeNode.equals("allContract")) {
			// 如果包含无文本合同，查询所有时，让它查不到合同
			typefilterItems.add(new FilterItemInfo("contractType.id", "allContract"));
		}

		// 三方合同
		if (!((ContractListBaseUI) this instanceof ContractBillListUI)) {
			typefilter.appendFilterItem("isAmtWithoutCost", String.valueOf(0));
		}

		if (filter != null && typefilter != null) {
			filter.mergeFilter(typefilter, "and");
		}

		return filter;
	}
	protected boolean isOnlyQueryAudited() {
		return true;
	}
}