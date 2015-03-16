/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.util.FDCSplitHelper;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.SettledMonthlyHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 工程量拆分序时薄界面
 */
public class WorkLoadSplitListUI extends AbstractWorkLoadSplitListUI
{
	private static final BOSObjectType PAYMENTSPLITBOSTYPE = new PaymentSplitInfo().getBOSType();
	private static final String VOUCHERFLAG = "Fivouchered";
	private static final String REFERFLAG = "voucherRefer";
    private static final Logger logger = CoreUIObject.getLogger(WorkLoadSplitListUI.class);
    private static final String CONTRACTID = "contractBill.id";
    private static final String ID = "id";
    private static final String COSTCREATORID = "costCreator.id";
    private static final String COSTAUDITORID = "costAuditor.id";
    private static final String COSTSPLITID = "costSplit.id";
    private static final String CURPROJECTID = "curProject.id";
    private static final String CURPROJECTISENABLED = "curProject.isEnabled";
    private static final String AMOUNT = "amount";
    private static final String WORKLOAD = "workLoad";
    
    /**
     * output class constructor
     */
    public WorkLoadSplitListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	initParam = PaymentSplitFactory.getRemoteInstance().fetchInitParam();
//    	if ((!SysContext.getSysContext().getCurrentOrgUnit()
//				.isIsCompanyOrgUnit())
//				&& (SysContext.getSysContext().getCurrentOrgUnit()
//						.isIsCostOrgUnit())) {
//			MsgBox.showWarning(this, PaymentBillClientUtils.getRes("CostUnit"));
//			SysUtil.abort();
//		}
    	super.onLoad();
    	//放在onload后，保证取到参数
    	if(!isWorkLoadSeparate()){
    		FDCMsgBox.showWarning(this, "未启用“工程量确认流程与付款流程分离”参数，不能使用本功能！");
    		SysUtil.abort();
    	}
//    	initWorkButton();
    }
    
    protected ICoreBase getBizInterface() throws Exception {
    	return PaymentSplitFactory.getRemoteInstance();
    }
    protected String getEditUIName() {
    	return com.kingdee.eas.fdc.finance.client.WorkLoadSplitEditUI.class.getName();
    }
    
	protected String getCurProjectPath() {
		return "curProject";
	}
	
//	该方法调用还未调试，需要调试
	public void actionViewInvalid_actionPerformed(ActionEvent e)throws Exception {
		IUIWindow invalidUI = null;
		UIContext uiContext = new UIContext(this);
		try {
			invalidUI = UIFactory
					.createUIFactory(UIFactoryName.NEWTAB)
					.create("com.kingdee.eas.fdc.finance.client.InvalidWorkLoadSplitListUI",uiContext, null);
			invalidUI.show();
		} catch (BOSException exe) {
			super.handUIException(exe);// OprtState.EDIT);
		}	
	}
	
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
//		this.initWorkButton();
	}
	protected void refresh(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.refresh(e);
		FDCClientHelper.setActionEnable(new ItemAction[] { actionVoucher,
				actionDelVoucher }, false);
	}
	protected void initWorkButton() {
		super.initWorkButton();
		actionViewContract.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_sequencecheck"));
		actionViewWorkload.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_linkviewlist"));
		actionViewInvalid.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_lookup"));
	}
	
	protected void filterByBillState(EntityViewInfo ev) {
		super.filterByBillState(ev);
		try {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.EQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.conSplitExecState", ConSplitExecStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
			ev.getFilter().mergeFilter(filter, "and");
//			filter.getFilterItems().add(
//					new FilterItemInfo("costSplit.isInvalid",Boolean.valueOf(true), CompareType.NOTEQUALS));
//			ev.getFilter().mergeFilter(filter, "and");
//			filter.getFilterItems().add(
//					new FilterItemInfo("costSplit.isWorkloadBill",Boolean.valueOf(true), CompareType.EQUALS));
//			ev.getFilter().mergeFilter(filter, "and");
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("costSplit.state",null,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("costSplit.state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
			filter.setMaskString("#0 or #1");
			ev.getFilter().mergeFilter(filter, "and");
		} catch (Exception e) {
			handUIException(e);
		}
	}
	
	public void actionViewContract_actionPerformed(ActionEvent e)
			throws Exception {
		BOSUuid.create(new ParamInfo().getBOSType());
		checkSelected();
		int selectRows[] = KDTableUtil.getSelectedRows(getMainTable());
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper
					.getRes("multiRowSelected"));
			SysUtil.abort();
		} else if (selectRows.length == 1) {
			Object obj = getMainTable()
					.getCell(selectRows[0], "contractBill.id").getValue();
			if (obj != null) {
				String contractID = obj.toString();
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, contractID);
				IUIWindow contractBillUI = UIFactory.createUIFactory(
						UIFactoryName.NEWTAB).create(
						ContractBillEditUI.class.getName(), uiContext,
						null, OprtState.VIEW);
				contractBillUI.show();
			}
		}
	}
	
	public void actionViewWorkLoad_actionPerformed(ActionEvent e)throws Exception {
		checkSelected();
		int selectRows[] = KDTableUtil.getSelectedRows(getMainTable());
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper.getRes("multiRowSelected"));
			SysUtil.abort();
		} else if (selectRows.length == 1) {
			Object obj = getMainTable().getCell(selectRows[0], "id").getValue();
			if (obj != null) {
				String id = obj.toString();
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, id);
				IUIWindow workLoadBillUI = UIFactory.createUIFactory(
						UIFactoryName.NEWTAB).create(
						WorkLoadConfirmBillEditUI.class.getName(), uiContext, null,OprtState.VIEW);
				workLoadBillUI.show();
			}
		}
	}

	protected void updateButtonStatus() {
		super.updateButtonStatus();
//		if (SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()) 
//			FDCClientHelper.setActionEnable(actionRemove, true);
//		else 
//			FDCClientHelper.setActionEnable(actionRemove, false);
//		去掉与凭证相关的按钮
		btnVoucher.setVisible(false);
		btnVoucher.setEnabled(false);
		btnDelVoucher.setVisible(false);
		btnDelVoucher.setEnabled(false);
		menuItemVoucher.setVisible(false);
		menuItemVoucher.setEnabled(false);
		menuItemDelVoucher.setVisible(false);
		menuItemDelVoucher.setEnabled(false);
		actionVoucher.setVisible(false);
		actionVoucher.setEnabled(false);
		actionDelVoucher.setVisible(false);
		actionDelVoucher.setEnabled(false);
//		添加显示编辑菜单
		menuEdit.setVisible(true);
		menuEdit.setEnabled(true);
		
		actionRemove.setVisible(true);
		actionRemove.setEnabled(true);
		menuItemRemove.setVisible(true);
		menuItemRemove.setEnabled(true);
		btnRemove.setVisible(true);
		btnRemove.setEnabled(true);
		
	}
	protected ICoreBase getRemoteInterface() throws BOSException {
		return  com.kingdee.eas.fdc.finance.PaymentSplitFactory.getRemoteInstance();
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
//		this.initWorkButton();
	}
	protected boolean checkBeforeSplit() throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper.getRes("multiRowSelected"));
			SysUtil.abort();
		}
		boolean isAddNew = super.checkBeforeSplit();
		String billId = getMainTable().getCell(selectRows[0], "id").getValue().toString();
		if (isAddNew) {
			// 到数据库内进行检查
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", billId));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));

			if (getRemoteInterface().exists(filter)) {
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				String costBillID = getRemoteInterface().getCollection(view).get(0).getId().toString();
				getMainTable().getCell(selectRows[0], getKeyFieldName()).setValue(costBillID);
				isAddNew = false;
			}
		} else{
			//是否被调整单或工程量确认单引用
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", billId));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));//#2
			filter.getFilterItems().add(new FilterItemInfo(REFERFLAG, PaySplitVoucherRefersEnum.ADJUSTBILL_VALUE));
			filter.getFilterItems().add(new FilterItemInfo(REFERFLAG, PaySplitVoucherRefersEnum.WORKLOADBILL_VALUE));
			filter.setMaskString("#0 and #1 and (#2 or #3)");
			if (getBizInterface().exists(filter)) {
				MsgBox.showWarning(this, "工程量拆分已被调整单或者工程量确认单引用，不能再拆分！");
				SysUtil.abort();
			}
		}
		if (!isAddNew && SettledMonthlyHelper.existObject(null, PAYMENTSPLITBOSTYPE, getSelectedKeyValue())) {
			MsgBox.showWarning(this, "财务成本月结已经引用本数据，不能修改，如需修改，请把此工程量对应的合同进入待处理流程！");
			SysUtil.abort();
		}
		
		return isAddNew;
	}
	
	protected void checkBeforeRemove() throws Exception {
		super.checkBeforeRemove();
		List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), "id");
		Set idSet=new HashSet(idList);
		if (SettledMonthlyHelper.existObject(null, PAYMENTSPLITBOSTYPE,idSet)) {
			MsgBox.showWarning(this,
					"财务成本月结已经引用本数据，不能删除，如需修改，请把此工程量对应的合同进入待处理流程！");
			SysUtil.abort();
		}
		
		//作废检查
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", idSet,CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE));
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(this, "存在已经作废的记录，不能执行删除操作！");
			SysUtil.abort();
		}
		
		//是否被调整单或工程量确认单引用
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", idSet,CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));//#2
		filter.getFilterItems().add(new FilterItemInfo(REFERFLAG, PaySplitVoucherRefersEnum.ADJUSTBILL_VALUE));
		filter.getFilterItems().add(new FilterItemInfo(REFERFLAG, PaySplitVoucherRefersEnum.PAYMENTBILL_VALUE));
		filter.setMaskString("#0 and #1 and (#2 or #3)");
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(this, "存在被调整单或者工程量确认单引用的工程量拆分，不能执行删除操作！");
			SysUtil.abort();
		}
		
		//工程量确认单分录
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select pe.fid from t_fnc_workloadcostvoucherentry pe left join t_fnc_paymentsplit ps on pe.fparentid=ps.FWorkLoadBillID where ");
		sqlBuilder.appendParam("ps.fworkLoadbillId", idSet.toArray());
		IRowSet rowSet = sqlBuilder.executeQuery();
		if (rowSet.size() > 0) {
			MsgBox.showWarning(this, "对应的工程量确认单已经生成凭证，不能执行删除操作！");
			SysUtil.abort();
		}
	}

	protected void getRowSetBeforeFillTable(IRowSet rowSet) {
		super.getRowSetBeforeFillTable(rowSet);
/*		try {
			rowSet.beforeFirst();
			while (rowSet.next()) {
				String refer = rowSet.getString(REFERFLAG);
				if(PaySplitVoucherRefersEnum.ADJUSTBILL_VALUE.equals(refer)){
					rowSet.updateObject(REFERFLAG,PaySplitVoucherRefersEnum.ADJUSTBILL);
				}else if(PaySplitVoucherRefersEnum.PAYMENTBILL_VALUE.equals(refer)){
					rowSet.updateObject(REFERFLAG,PaySplitVoucherRefersEnum.PAYMENTBILL);
				}else if(PaySplitVoucherRefersEnum.WORKLOADBILL_VALUE.equals(refer)){
					rowSet.updateObject(REFERFLAG,PaySplitVoucherRefersEnum.WORKLOADBill);
				}else{
					rowSet.updateObject(REFERFLAG,PaySplitVoucherRefersEnum.NOTREFER);
				}
			}
			rowSet.beforeFirst();
		} catch (Exception e) {
			handUIException(e);
		}*/

	}
	
	/**
	 * 增加快速定位字段：工程量确认单编号、合同编码、合同名称、确认工程量；
	 * @author owen_wen 2010-09-07
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "contractBill.number", "contractBill.name", WORKLOAD, };
	}
	
	/**
	 * 获取选中行的合同id
	 * @return
	 * @author owen_wen 2010-11-30
	 */
	private String getContractBillId(){
		this.checkSelected();
		int selectedRowIndex = this.getMainTable().getSelectManager().getActiveRowIndex();
		String contractId = this.getMainTable().getRow(selectedRowIndex).getCell("contractBill.id").getValue().toString();
		return contractId;
	}
	
	/**
	 * 工程量拆分，新需求拆分前要检查合同拆分和变更拆分是否已经审批。
	 * @author owen_wen 2010-12-01
	 */
	public void actionCostSplit_actionPerformed(ActionEvent e) throws Exception {
		// Added By Owen_wen 2010-12-03 
		FDCSplitClientHelper.checkAndShowMsgBeforeSplit(this.getContractBillId(), this, true);//工程量拆分只有成本类，所以第二个参数传入true
		super.actionCostSplit_actionPerformed(e);
	}	
}