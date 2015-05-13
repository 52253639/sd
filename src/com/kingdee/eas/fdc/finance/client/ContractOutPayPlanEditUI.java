/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ActionMap;
import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.client.ConSettlementPrintProvider;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.PayRequestBillContants;
import com.kingdee.eas.fdc.finance.ContractOutPayPlanFactory;
import com.kingdee.eas.fdc.finance.ContractOutPayPlanInfo;
import com.kingdee.eas.fi.gl.common.KDSpinnerCellEditor;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.ma.budget.BgItemObject;
import com.kingdee.eas.ma.budget.client.NewBgItemDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class ContractOutPayPlanEditUI extends AbstractContractOutPayPlanEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractOutPayPlanEditUI.class);
    public ContractOutPayPlanEditUI() throws Exception
    {
        super();
    }

    protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ContractOutPayPlanFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	protected void initControl() throws EASBizException, BOSException {
		this.txtNumber.setMaxLength(255);

		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		
//		this.actionWorkFlowG.setVisible(false);
//		this.actionAuditResult.setVisible(false);
		
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPre.setVisible(false);
		this.menuView.setVisible(false);
		
		this.menuBiz.setVisible(false);
//		this.menuWorkflow.setVisible(false);
		this.actionAddNew.setVisible(false);
		
//		this.btnSubmit.setText("提交&审批");
//		this.btnSubmit.setToolTipText("提交&审批");
//		this.txtVersion.setPrecision(1);
		
//		String cuId=SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
//    	Map param = (Map) ActionCache.get("FDCBillEditUIHandler.orgParamItem");
//		if (param == null) {
//			param = FDCUtils.getDefaultFDCParam(null, SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId().toString());
//		}
//		if (param.get(FDCConstants.FDC_PARAM_SELECTPERSON) != null) {
//			canSelectOtherOrgPerson = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SELECTPERSON).toString()).booleanValue();
//		}
//		FDCClientUtils.setRespDeptF7(prmtRespDept, this,canSelectOtherOrgPerson ? null : cuId);
//		FDCClientUtils.setPersonF7(prmtRespPerson, this,canSelectOtherOrgPerson ? null : cuId);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionALine.setEnabled(false);
			this.actionILine.setEnabled(false);
			this.actionRLine.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionALine.setEnabled(true);
			this.actionILine.setEnabled(true);
			this.actionRLine.setEnabled(true);
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		initControl();
	}
	public void storeFields(){
		super.storeFields();
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		if(this.editData.getContract()!=null){
			this.txtContractInfo.setText(this.editData.getContract().getNumber() + " " + editData.getContract().getName());
			this.txtOrg.setText(this.editData.getContract().getOrgUnit().getDisplayName());
			this.txtContractAmount.setValue(this.editData.getContract().getAmount());
			
			BigDecimal allCompletePrjAmt = FDCHelper.ZERO;
			if (contractBill != null && contractBill.isHasSettled()) {
				// 已完工工程量不能为0 eric_wang 2010.06.01
				BigDecimal settleAmt = contractBill.getSettleAmt();
				if (FDCHelper.ZERO.equals(settleAmt)) {
					// 弹出框提示
					FDCMsgBox.showError(this, "本币：进度款+结算款不能超过合同结算价-保修金");
					SysUtil.abort();
				} else {
					txtGCL.setValue(settleAmt);
				}
				return;
			}
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("completePrjAmt");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractId", editData.getContract().getId().toString()));
			view.setFilter(filter);

			PayRequestBillCollection payReqColl = null;
			try {
				payReqColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);

				if (payReqColl != null) {
					for (int i = 0; i < payReqColl.size(); i++) {
						PayRequestBillInfo info = payReqColl.get(i);
						allCompletePrjAmt = allCompletePrjAmt.add(FDCHelper.toBigDecimal(info.getCompletePrjAmt()));
					}
				}
			} catch (BOSException e) {
				handUIException(e);
			}

			txtGCL.setValue(FDCHelper.add(allCompletePrjAmt, txtGCL.getBigDecimalValue()));
//			try {
//				this.txtLastAmount.setValue(FDCUtils.getContractLastAmt (null,this.editData.getContract().getId().toString()));
//			} catch (EASBizException e) {
//				e.printStackTrace();
//			} catch (BOSException e) {
//				e.printStackTrace();
//			} catch (UuidException e) {
//				e.printStackTrace();
//			} 
			try {
				BigDecimal actPayAmount=FDCHelper.ZERO;
				FDCSQLBuilder _builder = new FDCSQLBuilder();
				_builder.appendSql("select sum(FAmount) sumCount from t_cas_paymentbill where fbillstatus=15 and fcontractbillid='"+this.editData.getContract().getId().toString()+"'");
				final IRowSet rowSet = _builder.executeQuery();
				if (rowSet.size() == 1) {
					rowSet.next();
					actPayAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("sumCount"));
				}
				this.txtTotalAmount.setValue(actPayAmount);

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			
			try {
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", editData.getContract().getId().toString());
				ContractBillEditUI ui = (ContractBillEditUI) UIFactoryHelper.initUIObject(ContractBillEditUI.class.getName(), uiContext, null,OprtState.VIEW);
				this.contract.setViewportView(ui);
				this.contract.setKeyBoardControl(true);
				this.contract.setEnabled(false);
			} catch (UIException e) {
				e.printStackTrace();
			}
		}
		
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
	protected void fetchInitData() throws Exception {
		
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel = super.getSelectors();
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("contract.*");
		return sel;
	}
	protected IObjectValue createNewData() {
		ContractOutPayPlanInfo info=(ContractOutPayPlanInfo)this.getUIContext().get("info");
		if(info==null){
			info= new ContractOutPayPlanInfo();
			String contractBillId = (String)getUIContext().get("contractBillId");
		
			ContractBillInfo contractBillInfo = null;
			try {
				SelectorItemCollection sic=new SelectorItemCollection();
				sic.add("*");
				sic.add("curProject.displayName");
				sic.add("orgUnit.displayName");
				contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(contractBillId), sic);	
			} catch (Exception e1) {
				handUIException(e1);
			}
			if(contractBillInfo!=null){
				info.setOrgUnit(contractBillInfo.getOrgUnit());
				info.setContract(contractBillInfo);
			}else{
				FDCMsgBox.showWarning(this,"合同为空！");
	    		SysUtil.abort();
			}
		}else{
			info.setId(null);
		}
		info.setState(FDCBillStateEnum.SAVED);
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setBizDate(now);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setCreator(null);
		info.setCreateTime(null);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setLastUpdateUser(null);
		info.setLastUpdateTime(null);
		return info;
	}
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
	}
	protected boolean checkBizDate() throws EASBizException, BOSException, SQLException{
		if(this.editData.getBizDate()!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.editData.getBizDate());
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;
			

			FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql("select count(*) account from T_FNC_ContractOutPayPlan where year(fbizdate)="+year+" and month(fbizdate)="+month+" and fcontractid='"+this.editData.getContract().getId().toString()+"'");
			if(this.editData.getId()!=null){
				_builder.appendSql(" and fid!='"+this.editData.getId().toString()+"'");
			}
			int account=0;
			final IRowSet rowSet = _builder.executeQuery();
			if (rowSet.size() == 1) {
				rowSet.next();
				account =rowSet.getInt("account");
			}
			if(account>0){
				FDCMsgBox.showWarning(this,"已存在当前计划付款日期单据！");
				return true;
			}
			return false;
		}
		return true;
	}
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, this.txtAmount);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		if(checkBizDate()){
			SysUtil.abort();
		}
	}
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		ContractOutPayPlanPrintProvider data = new ContractOutPayPlanPrintProvider(
				editData.getString("id"), getTDQueryPK(),this.txtTotalAmount.getBigDecimalValue(),this.txtGCL.getBigDecimalValue());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		ContractOutPayPlanPrintProvider data = new ContractOutPayPlanPrintProvider(
				editData.getString("id"), getTDQueryPK(),this.txtTotalAmount.getBigDecimalValue(),this.txtGCL.getBigDecimalValue());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	protected String getTDFileName() {
		return "/bim/fdc/finance/ContractoutPayPlan";
	}

	// 对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.finance.app.ContractOutPayPlanQuery");
	}
}