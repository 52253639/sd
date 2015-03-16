/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCParamInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.IFDCSQLFacade;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.FindDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * 描述:结算单列表界面
 * 
 * @author liupd date:2006-10-13
 *         <p>
 * @version EAS5.1.3
 */
public class ContractSettlementBillListUI extends
		AbstractContractSettlementBillListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractSettlementBillListUI.class);

	// 定位框 jelon
	private FindDialog findDialog = null;

	//合同可进行多次结算
	private boolean canSetterMore = false;
	
	protected void initWorkButton() {
		super.initWorkButton();
		actionRespite.setVisible(true);
		actionRespite.setEnabled(true);
		actionCancelRespite.setVisible(true);
		actionCancelRespite.setEnabled(true);
		
	}
	/**
	 * output class constructor
	 */
	public ContractSettlementBillListUI() throws Exception {
		super();
	}
	
	/**
	 * 快速模糊定位<P>
	 * 增加定位字段：合同类型、签约时间 Modified by Owen_wen 2010-09-07
	 * @author ling_peng
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "contractName", "contractType.name", "signDate", "partB.name",};
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// 检查是否选中合同
		checkSelected(getMainTable());
		checkCanAddNew(getSelectedKeyValue(getMainTable()));

		// 检查合同是否已有结算
		String billId = getSelectedKeyValue(getMainTable());
		ContractSettlementBillCollection billColl = null;
		// String oql="select id where isFinalSettle=1 and
		// contractBill.id='"+billId+"'";
		// billColl =
		// ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillCollection(oql);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill.id", billId));
		// filter.getFilterItems().add(new FilterItemInfo("state",
		// FDCBillStateEnum.SAVED,CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("isFinalSettle", BooleanEnum.TRUE));
		view.setFilter(filter);
		billColl = ContractSettlementBillFactory.getRemoteInstance()
				.getContractSettlementBillCollection(view);
		if (billColl != null && billColl.size() > 0) {
			MsgBox.showWarning(this, ContractClientUtils
					.getRes("hasFinalSettle"));
			SysUtil.abort();
		}

		super.actionAddNew_actionPerformed(e);
	}

	private void checkCanAddNew(String contractID) throws Exception {
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("id",contractID));
//		filter.getFilterItems().add(new FilterItemInfo("splitState",CostSplitStateEnum.NOSPLIT));
//		filter.getFilterItems().add(new FilterItemInfo("splitState",CostSplitStateEnum.PARTSPLIT));
//		filter.setMaskString("#0 and (#1 or #2)");
//		if(ContractBillFactory.getRemoteInstance().exists(filter)){
//			MsgBox.showWarning(this,"此合同未完全拆分！");
//			SysUtil.abort();
//		}
//		//判断变更指令单是否拆分
//		IFDCSQLFacade  fdcSqlFacade = FDCSQLFacadeFactory.getRemoteInstance();
//		List params = new ArrayList();
//		params.add(contractID);
//		
//		IRowSet noCost = fdcSqlFacade.executeQuery("select conSplit.FSplitState from T_CON_ContractChangeBill contractbill " +
//				" left join T_CON_ConChangeNoCostSplit conSplit on contractbill.fid = conSplit.FContractChangeID "+
//				" where contractbill.FContractBillID = ? and conSplit.FSplitState = '3ALLSPLIT'", params);
//		
//		IRowSet isCost = fdcSqlFacade.executeQuery("select conSplit.FSplitState from T_CON_ContractChangeBill contractbill " +
//				" left join T_CON_ConChangeSplit conSplit on contractbill.fid = conSplit.FContractChangeID "+
//				" where contractbill.FContractBillID = ? and conSplit.FSplitState ='3ALLSPLIT'", params);
//		if(!noCost.next()&&!isCost.next()){
//			MsgBox.showWarning(this,"此变更指令单未完全拆分！");
//			SysUtil.abort();
//		}
		
		//变更确认单必须审批
//		FilterInfo filter1 = new FilterInfo();
//		filter1.getFilterItems().add(new FilterItemInfo("contractBill.id",contractID));
//		EntityViewInfo view = new EntityViewInfo();
//		view.setFilter(filter1);
//		ContractChangeBillCollection coll = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(view);
//		Set idSet = new HashSet();
//		for(int i = 0 ; i< coll.size();i++){
//			idSet.add(coll.get(i).getId().toString());
//		}
//		FilterInfo filter4 = new FilterInfo();
//		filter4.getFilterItems().add(new FilterItemInfo("conChangeBill.id",idSet,CompareType.INCLUDE));
//		EntityViewInfo view1 = new EntityViewInfo();
//		view1.setFilter(filter4);
//		ContractChangeSettleBillCollection conColl = ContractChangeSettleBillFactory.getRemoteInstance().getContractChangeSettleBillCollection(view1);
//		if(idSet.size()!= conColl.size()){
//			MsgBox.showWarning(this,"一条或多条指令单未做变更确认！");
//			SysUtil.abort();
//		}
//		filter4.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED,CompareType.NOTEQUALS));
//		
//		if(ContractChangeSettleBillFactory.getRemoteInstance().exists(filter4)){
//			MsgBox.showWarning(this,"一条或多条指令单未做变更确认！");
//			SysUtil.abort();
//		}
	}
	
	
	/**
	 * 根据单据ID判断是否已完全拆分
	 * @param tabName
	 * @param billFieldName
	 * @return
	 * @throws Exception
	 */
	public  boolean isBillSplited(String id, String tabName, String billFieldName) throws Exception {
		List params = new ArrayList();
		params.add(id);
		StringBuffer sql = new StringBuffer();
		boolean b = false;
		
		sql.append("select fid from ");
		sql.append(tabName);
		sql.append(" where ");
		sql.append(" FSplitState IN ('1NOSPLIT','2PARTSPLIT')");
		sql.append(" and ");
		sql.append(billFieldName);
		sql.append(" = ? ");
		
		IRowSet set = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(sql.toString(), params);
		
		if(set.next()) {
			b = true;
		}
		
		return b;
		
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		boolean isCostSplit=FDCSplitClientHelper.isBillSplited(getSelectedKeyValue(), "T_CON_SettNoCostSplit", "FSettlementBillID");
		boolean isNoCostSplit=FDCSplitClientHelper.isBillSplited(getSelectedKeyValue(), "T_CON_SettlementCostSplit", "FSettlementBillID");
		if(isCostSplit||isNoCostSplit){
			MsgBox.showWarning("此结算单已拆分，不能删除！");
			SysUtil.abort();
		}
		super.actionRemove_actionPerformed(e);
	}
	
	
	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		// 打印非tabMain，jelon
		// super.actionPrint_actionPerformed(e);
		preparePrintPage(tblSettlementList);
		tblSettlementList.getPrintManager().print();
	}
	
	public boolean isOverContractAmt(){
		boolean isOverContractAmt = false;
		if(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo() != null){
			Map paramMap  = null;
			try {
				paramMap = FDCUtils.getDefaultFDCParam(null, SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(paramMap.get(FDCConstants.FDC_PARAM_SETTLEMENTOVERCONTRACTAMOUNT) != null){
				isOverContractAmt = Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_SETTLEMENTOVERCONTRACTAMOUNT).toString()).booleanValue();
			}
			
		}
		return isOverContractAmt;
	}
	
	public void chkSettAmtOverRate(ContractSettlementBillInfo conSettBill) throws EASBizException, BOSException, SQLException{
		
	 
		BigDecimal totalSettlementOrgiAmt = conSettBill.getTotalOriginalAmount();
		BigDecimal contractOrgiAmt = FDCHelper.ZERO;
		BigDecimal canSettAmt = FDCHelper.ZERO;
		ContractBillInfo contract = conSettBill.getContractBill();
		if(conSettBill.getContractBill().getId()!=null){
//			        contract = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(conSettBill.getContractBill().getId()));
//				    contractOrgiAmt = contract.getOriginalAmount();
//				    EntityViewInfo evi = new EntityViewInfo();
//				    SelectorItemCollection sic ;
//				    FilterInfo filter;
//					sic = new SelectorItemCollection();
//					sic.add(new SelectorItemInfo("originalAmount"));
//					filter = new FilterInfo();
//					filter.getFilterItems().add(new FilterItemInfo("contractBill.id", conSettBill.getContractBill().getId().toString()));
//					evi.setSelector(sic);
//					evi.setFilter(filter);
//					ContractChangeBillCollection collection = null;
//					collection = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(evi);
//					
//					if(collection != null && collection.size() >0 ){
//						for(Iterator it = collection.iterator();it.hasNext();){
//							contractOrgiAmt = FDCHelper.add(contractOrgiAmt, ((ContractChangeBillInfo)it.next()).getOriginalAmount());
//						}
//				    }
			//调用统一接口取数
			 try {
				 contractOrgiAmt = FDCHelper.toBigDecimal(FDCUtils.getLastOriginalAmt_Batch(null, new String[]{conSettBill.getContractBill().getId().toString()}).get(conSettBill.getContractBill().getId().toString()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			}
//			if(contractOrgiAmt != null && contractOrgiAmt.compareTo(FDCHelper.ZERO) != 0){
//				canSettAmt =FDCHelper.toBigDecimal(FDCHelper.multiply(contractOrgiAmt,FDCHelper.toBigDecimal(new Double(1+contract.getOverRate()/100.00))),2);
//			}
//			
//			if(FDCHelper.subtract(totalSettlementOrgiAmt, canSettAmt).compareTo(FDCHelper.ZERO) > 0 ){
//				if(isOverContractAmt()){
//					FDCMsgBox.showError("累计结算已超过合同最新造价约定比例，请通过补录变更单或其他方式增加合同最新造价!");
//					abort();
//				}else{
//					FDCMsgBox.showWarning("累计结算已超过合同最新造价约定比例，请通过补录变更单或其他方式增加合同最新造价!");
//				}
//			}
		HashMap paramItem = null;
		FDCParamInfo paramInfo = null;
		try {
			paramItem = ParamControlFactory.getRemoteInstance().getParamHashMap(SysContext.getSysContext().getCurrentCostUnit().getId().toString(),"com.kingdee.eas.fdc.contract.contract");
			paramInfo = new FDCParamInfo(paramItem);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String conSettType = "rdContractOverRate";
        String costCenterConstRate = "0.00";
		if(paramInfo != null){
			conSettType = paramInfo.getBase_ConSettType();
			costCenterConstRate = paramInfo.getBase_CostCenterConstRate();
		}
		if(contractOrgiAmt != null && contractOrgiAmt.compareTo(FDCHelper.ZERO) != 0){
			double newRate = 0.0;
			if(conSettType.equalsIgnoreCase("rdContractOverRate")){
				newRate = contract.getOverRate();
			}else{
				if(costCenterConstRate != null && !costCenterConstRate.trim().equals("")){
					newRate = new Double(costCenterConstRate).doubleValue();
				}
			}
			canSettAmt =FDCHelper.toBigDecimal(FDCHelper.multiply(contractOrgiAmt,FDCHelper.toBigDecimal(new Double(1+newRate/100.00))),2);
		}
		
		
		
		if(FDCHelper.subtract(totalSettlementOrgiAmt, canSettAmt).compareTo(FDCHelper.ZERO) > 0 ){
			if(isOverContractAmt()){
				FDCMsgBox.showError("累计结算已超过合同最新造价约定比例，请通过补录变更单或其他方式增加合同最新造价!");
				abort();
			}else{
				int result = MsgBox.showConfirm2New(null,"累计结算已超过合同最新造价约定比例，是否继续？");
				if(JOptionPane.NO_OPTION == result){
					 abort();
				}
			}
		}
	}		
		
	/**
	 * 检查变更审批单对应的合同拆分是否已经审批，并且根据参数是否启用，给出信息提示。
	 * @param conSettlementId 结算单编号
	 * @throws BOSException
	 * @throws EASBizException
	 * @author owen_wen 2010-12-10
	 */
	private void checkContractSplitIsAuditAndShowMsg(String conSettlementId) throws BOSException, EASBizException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter  = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", conSettlementId));
		view.setFilter(filter);
		view.getSelector().add(new SelectorItemInfo("contractBill.id"));
		view.getSelector().add(new SelectorItemInfo("contractBill.isCoseSplit"));
		
		ContractSettlementBillCollection csbColl = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillCollection(view);
		
		for (int j = 0; j < csbColl.size(); j++){ //其实数据只能有一条，将就着先用for循环了
			FDCSplitClientHelper.checkAndShowMsgBeforeSplit(csbColl.get(j).getContractBill().getId().toString(), this, 
					csbColl.get(j).getContractBill().isIsCoseSplit());
		}
	}

//	审批时加上数据互斥
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		
		checkSelected(this.tblSettlementList);
		
		// 检查状态
		checkBillState(new String[]{getStateForAudit()}, "selectRightRowForAudit");
		
		String conSettlementId=getSelectedKeyValue(tblSettlementList);
		if (FDCUtils.getBooleanValue4FDCParamByKey(null, SysContext.getSysContext().getCurrentOrgUnit().getId().toString(), FDCConstants.FDC_PARAM_IMPORTCONSPLIT))
			this.checkContractSplitIsAuditAndShowMsg(conSettlementId);
		
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("contractBill.id");
		selector.add("contractBill.*");
		selector.add("totalOriginalAmount");
		selector.add("isFinalSettle");
		selector.add("state");
		ContractSettlementBillInfo conSettBill=ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillInfo(new ObjectUuidPK(BOSUuid.read(conSettlementId)));
		
//		if(FDCBillStateEnum.AUDITTED.equals(conSettBill.getState())){
//			FDCMsgBox.showWarning("存在不符合审批条件的记录，请重新选择，保证所选的记录都是提交状态的");
//			this.abort();
//		}
		String contractBillId = getSelectedKeyValue(getMainTable());
		Map paramMap=new HashMap();
		if(contractBillId==null){
			contractBillId=conSettBill.getContractBill().getId().toString();
		}
		paramMap.put("contractBillId", contractBillId);
		ContractClientUtils.checkTotalSettlePriceSmallerThanZero(paramMap);
		
		//只有最终结算才判断实际付款数
		/*int activeIndex=this.tblSettlementList.getSelectManager().getActiveRowIndex();
		IRow activeRow=this.tblSettlementList.getRow(activeIndex);
		String id=this.tblSettlementList.getCell(activeIndex, "id").getValue().toString();
		ContractSettlementBillInfo conSettBill=ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillInfo(id);*/
		/*Object obj=activeRow.getUserObject();
		ContractSettlementBillInfo conSettBill=(ContractSettlementBillInfo)obj;*/
		if(conSettBill!=null){
			if(conSettBill.getBoolean("isFinalSettle")){
				chkSettAmtOverRate(conSettBill);
				boolean checkSettlePriceBiggerThanPayAmt=ContractClientUtils.checkSettlePriceBiggerThanPayAmt(conSettBill, contractBillId);
				if(!checkSettlePriceBiggerThanPayAmt){
					MsgBox.showError(this,"合同结算价不能小于合同实际付款数之和！");
					SysUtil.abort();
				}
				
				
			}
			 /**
		     * 非最终结算的结算单在提交、审批时，若启用了参数“未结算合同的实付款大于已实现产值时是否严格控制”
		     * 且合同实付款大于累计结算金额，则提示“未结算合同的实付款不能大于累计结算金额【合同实付款 =已关
		     * 闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】”，则不能提交
		     * 或审批通过，若未启用，则在提示之后允许提交、审批。
		     * by jian_wen 2009.12.24
		     */
			else{
				boolean b = FDCUtils.getDefaultFDCParamByKey(null, orgUnit.getId().toString(),
						FDCConstants.FDC_PARAM_ISCONTROLPAYMENT);
				// 取合同实付款
	    		BigDecimal payAmt = ContractClientUtils.getPayAmt(conSettBill.getContractBill().getId().toString());
	    		if(payAmt==null){
	    			payAmt=FDCHelper.ZERO;
	    		}
	    		if(payAmt.compareTo(conSettBill.getTotalSettlePrice())==1){
	    			if(b){
	    				MsgBox.showError("未结算合同的实付款不能大于累计结算金额【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】");
	        			abort();
	        		}
	    			else{
	    				MsgBox.showWarning("未结算合同的实付款不能大于累计结算金额【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】");
	    			}
	    		}	    		
			}
		}
		
//		获取用户选择的块
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());		
		/*Map canAudit = new HashMap();
		if(idList.size() > 1){
			for(int i =0 ;i<idList.size();i++){
				try{
					chkSettAmtOverRate(idList.get(i).toString());
					canAudit.put(idList.get(i), Boolean.TRUE);
				}catch (Exception ex) {
					canAudit.put(idList.get(i), Boolean.FALSE);
				}
				
			}
			
		}else if(idList.size() == 1){
			chkSettAmtOverRate(idList.get(0).toString());
		}*/
		boolean hasMutex = false;
		try{
			
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
           /* for(int i= 0;i<idList.size();i++){
            	if(canAudit.get(idList.get(i).toString()) != null){
            		if(((Boolean)canAudit.get(idList.get(i).toString())).booleanValue()){*/
            			super.actionAudit_actionPerformed(e);
       /*     		}
            	}
            }*/
			boolean isSplitAfterAudit =FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_SPLITAFTERAUDIT);
			boolean isImportConSplit = FDCUtils.getDefaultFDCParamByKey(null, orgUnit.getId().toString(), FDCConstants.FDC_PARAM_IMPORTCONSPLIT);
			if(isSplitAfterAudit&&!isImportConSplit){
				if(idList.size()>1){
					FDCMsgBox.showInfo(this, "多条单据审批通过，请立即进行拆分！");
				}else if(idList.size()==1){
					AbstractSplitInvokeStrategy strategy = SplitInvokeStrategyFactory.createSplitStrategyByParam((String)idList.get(0), this);
					strategy.invokeSplit();
				}
			}
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
		
	}
	
	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		// 打印非tabMain，jelon
		// super.actionPrintPreview_actionPerformed(e);
		preparePrintPage(tblSettlementList);
		tblSettlementList.getPrintManager().printPreview();
	}

	/**
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.framework.client.ListUI#getTableForPrintSetting()
	 *      描述：重载以实现打印非tblMain控件
	 * @return
	 * @author:jelon 创建时间：2006-09-13
	 *               <p>
	 */
	protected KDTable getTableForPrintSetting() {
		// return super.getTableForPrintSetting();
		return tblSettlementList;
	}

	/**
	 * 
	 * 描述：返回远程接口（子类必须实现）
	 * 
	 * @return
	 * @throws BOSException
	 * @author:liupd 创建时间：2006-8-1
	 *               <p>
	 */
	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return ContractSettlementBillFactory.getRemoteInstance();
	}

	protected ICoreBase getBizInterface() throws Exception {

		return getRemoteInterface();
	}

	/**
	 * 
	 * 描述：审核通过（子类必须实现，调用服务器端打审核标志的方法即可）
	 * 
	 * @param ids
	 * @throws Exception
	 * @author:liupd 创建时间：2006-8-1
	 *               <p>
	 */
	protected void audit(List ids) throws Exception {
		/*for (int i = 0; i < ids.size(); i++) {
			String settId=(String) ids.get(i);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("isFinalSettle");
			selector.add("totalSettlePrice");
			selector.add("contractBill.id");
			ContractSettlementBillInfo info = ContractSettlementBillFactory
					.getRemoteInstance().getContractSettlementBillInfo(new ObjectUuidPK(BOSUuid.read(settId)));
			//最终结算才进行检验
			if(!info.getBoolean("isFinalSettle")){
				continue;
			}
			BigDecimal settPrice = info.getTotalSettlePrice();
			BigDecimal totalPay = FDCHelper.ZERO;
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractId",info.getContractBill().getId().toString()));
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("amount");
			IPayRequestBill payReq = PayRequestBillFactory.getRemoteInstance();
			PayRequestBillCollection coll = payReq.getPayRequestBillCollection(view);
			PayRequestBillInfo infoPay = null;
			for(Iterator it = coll.iterator();it.hasNext();){
				infoPay = (PayRequestBillInfo) it.next();
				totalPay = totalPay.add(FDCHelper.toBigDecimal(infoPay.getAmount()));
			}
			if(settPrice.compareTo(totalPay)==-1){
				MsgBox.showError(this,"合同结算价不能小于合同实际付款数之和！");
				SysUtil.abort();
			}
		}*/
		ContractSettlementBillFactory.getRemoteInstance().audit(ids);
	}

	/**
	 * 
	 * 描述：反审核（子类必须实现，调用服务器端打反审核标志的方法即可）
	 * 
	 * @param ids
	 * @throws Exception
	 * @author:liupd 创建时间：2006-8-1
	 *               <p>
	 */
	protected void unAudit(List ids) throws Exception {
		 if(ids!=null&&ids.size()>0) {
			FilterInfo filterSett = new FilterInfo();
			filterSett.getFilterItems().add(
					new FilterItemInfo("settlementBill.id", new HashSet(ids),CompareType.INCLUDE));
			filterSett.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			if (SettlementCostSplitFactory.getRemoteInstance().exists(filterSett)
					|| SettNoCostSplitFactory.getRemoteInstance()
					.exists(filterSett)) {
				MsgBox.showWarning("结算单已经拆分,不能反审批!");
				SysUtil.abort();
			}

		}
		ContractSettlementBillFactory.getRemoteInstance().unAudit(ids);
	}

	/**
	 * 
	 * 描述：获取当前单据的Table（子类必须实现）
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-2
	 *               <p>
	 */
	protected KDTable getBillListTable() {
		return tblSettlementList;
	}

    
	/**
	 * 
	 * 描述：生成查询单据的EntityViewInfo
	 * @param e
	 * @return
	 * @author:liupd
	 * 创建时间：2006-8-2 <p>
	 */
	protected EntityViewInfo genBillQueryView(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
		KDTSelectBlock selectBlock = e.getSelectBlock();
    	int top = selectBlock.getTop();
    	if(getMainTable().getCell(top, getKeyFieldName())==null){
    		return null;
    	}
    	
    	String contractId = (String)getMainTable().getCell(top, getKeyFieldName()).getValue();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
    	view.setFilter(filter);
    	view.getSorter().add(new SorterItemInfo("createTime"));
    	
    	SelectorItemCollection selectors = genBillQuerySelector();
    	if(selectors != null && selectors.size() > 0) {
    		for (Iterator iter = selectors.iterator(); iter.hasNext();) {
				SelectorItemInfo element = (SelectorItemInfo) iter.next();
				view.getSelector().add(element);				
			}
    	}
    	
		return view;
	}
		
	/**
	 * 
	 * 描述：根据选择的合同显示单据列表
	 * 
	 * @param e
	 * @throws BOSException
	 * @author:Jelon 创建时间：2006-8-18
	 *               <p>
	 */
	
	protected boolean  displayBillByContract(KDTSelectEvent e,EntityViewInfo view) throws BOSException {
//		return false;
//	}	
//	
//	protected void displayBillByContract(EntityViewInfo view)
//			throws BOSException {
		if(view==null){
			return false;
		}

		int pre = getPre(e);
		
		if(canSetterMore){
			getBillListTable().getColumn(ContractSettlementBillContants.COL_ISFINALSETTLE).getStyleAttributes().setHided(false);
		}else{
			getBillListTable().getColumn(ContractSettlementBillContants.COL_ISFINALSETTLE).getStyleAttributes().setHided(true);
			getBillListTable().getColumn("totalSettlePrice").getStyleAttributes().setHided(true);
			getBillListTable().getColumn("totalOriginalAmount").getStyleAttributes().setHided(true);
		}

		//设置精度
		String oriFormat = FDCClientHelper.getNumberFormat(pre,true);
		getBillListTable().getColumn("originalAmount").getStyleAttributes().setNumberFormat(oriFormat);
		getBillListTable().getColumn("totalOriginalAmount").getStyleAttributes().setNumberFormat(oriFormat);
		
		ContractSettlementBillCollection contractSettlementBillCollection = ContractSettlementBillFactory
				.getRemoteInstance().getContractSettlementBillCollection(view);
		for (Iterator iter = contractSettlementBillCollection.iterator(); iter
				.hasNext();) {
			ContractSettlementBillInfo element = (ContractSettlementBillInfo) iter
					.next();

			String contractId = element.getContractBill().getId().toString();
			EntityViewInfo conView = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			conView.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("contract.Id", contractId));
			IRow row = getBillListTable().addRow();

			row.getCell("bookedDate").setValue(element.getBookedDate());
			row.getCell("period").setValue(element.getPeriod());
			
			row.getCell(ContractSettlementBillContants.COL_ID).setValue(
					element.getId().toString());
			row.getCell(ContractSettlementBillContants.COL_STATE).setValue(
					element.getState());
			row.getCell(ContractSettlementBillContants.COL_NUMBER).setValue(
					element.getNumber());
			row.getCell(ContractSettlementBillContants.COL_BILLNAME).setValue(
					element.getName());

//			row.getCell(ContractSettlementBillContants.COL_CONTRACTNUMBER)
//					.setValue(element.getContractBill().getNumber());
//			row.getCell(ContractSettlementBillContants.COL_CONTRACTNAME)
//					.setValue(element.getContractBill().getName());
			/**
			 * 按照周鹏的说法，如果以下金额列为0就应该显示0.00,而不是像以下代码未修改之前那样如果为0直接啥都不显示  by Cassiel_peng 2009-9-9
			 */
			if (element.getCurOriginalAmount() != null
					/*&& FDCHelper.ZERO.compareTo(element.getCurOriginalAmount()) != 0*/) {
				row.getCell("originalAmount")
						.setValue(FDCHelper.toBigDecimal(element.getCurOriginalAmount(),2));
			}
			
			if (element.getCurSettlePrice() != null
					/*&& FDCHelper.ZERO.compareTo(element.getCurSettlePrice()) != 0*/) {
				row.getCell(ContractSettlementBillContants.COL_SETTLEPRICE)
						.setValue(element.getCurSettlePrice());
			}
			
			if (element.getTotalOriginalAmount() != null
					/*&& FDCHelper.ZERO.compareTo(element.getTotalOriginalAmount()) != 0*/) {
				row.getCell("totalOriginalAmount")
						.setValue(element.getTotalOriginalAmount());
			}
			
			
			if (element.getTotalSettlePrice() != null
					/*&& FDCHelper.ZERO.compareTo(element.getTotalSettlePrice()) != 0*/) {
				row.getCell("totalSettlePrice")
						.setValue(element.getTotalSettlePrice());
			}

			if (element.getQualityGuarante() != null
					/*&& FDCHelper.ZERO.compareTo(element.getQualityGuarante()) != 0*/) {
				row.getCell(ContractSettlementBillContants.COL_QUALITYGUARANTE)
						.setValue(element.getQualityGuarante());
			}
			if (element.getBuildArea() != null
					&& FDCHelper.ZERO.compareTo(element.getBuildArea()) != 0) {
				row.getCell(ContractSettlementBillContants.COL_BUILDAREA)
						.setValue(element.getBuildArea());
			}
			if (element.getUnitPrice() != null
					/*&& FDCHelper.ZERO.compareTo(element.getUnitPrice()) != 0*/) {
				row.getCell(ContractSettlementBillContants.COL_UNITPRICE)
						.setValue(element.getUnitPrice());
			}
			row.getCell(ContractSettlementBillContants.COL_INFOPRICE).setValue(
					element.getInfoPrice());

			row.getCell(ContractSettlementBillContants.COL_GETFEECRITERIA)
					.setValue(element.getGetFeeCriteria());
			row.getCell(ContractSettlementBillContants.COL_ISFINALSETTLE)
					.setValue(element.getIsFinalSettle());

			row.getCell(ContractSettlementBillContants.COL_CREATOR).setValue(
					element.getCreator().getName());
			row.getCell(ContractSettlementBillContants.COL_CREATETIME)
					.setValue(element.getCreateTime());

			if (element.getAuditor() != null)
				row.getCell(ContractSettlementBillContants.COL_AUDITOR)
						.setValue(element.getAuditor().getName());
			row.getCell(ContractSettlementBillContants.COL_AUDITTIME).setValue(
					element.getAuditTime());

			row.getCell(ContractSettlementBillContants.COL_DESC).setValue(
					element.getDescription());

			if (element.getVoucher() != null)
				row.getCell(ContractSettlementBillContants.COL_VOUCHERNUMBER)
						.setValue(element.getVoucher().getNumber());
			
			row.getCell(COL_DATE).setValue(element.getBookedDate());
			row.getCell(COL_PERIOD).setValue(element.getPeriod());
			row.getCell(ContractSettlementBillContants.COL_ISRESPITE)
					.setValue(new Boolean(element.isIsRespite()));
		}

		return true;
	}

	/**
	 * 
	 * 描述：生成获取单据的Selector
	 * 
	 * @return
	 * @author:jelon 创建时间：2006-8-18
	 *               <p>
	 */
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("*");

		//币别精度
		//selectors.add("currency.precision");	
		
		selectors.add("contractBill.number");
		selectors.add("contractBill.name");
		
		selectors.add("creator.name");
		selectors.add("auditor.name");
		
//		selectors.add("voucher.number");
		
		selectors.add("period.number");
		selectors.add("period.periodNumber");
		selectors.add("period.periodYear");
		selectors.add("isRespite");
		return selectors;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#freezeBillTableColumn()
	 */
	protected void freezeBillTableColumn() {
		// TODO 自动生成方法存根
		super.freezeBillTableColumn();
		// 结算单造价
		int price_col_index = getBillListTable().getColumn(
				ContractSettlementBillContants.COL_SETTLEPRICE)
				.getColumnIndex();

		getBillListTable().getViewManager().setFreezeView(-1,
				price_col_index + 1);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#getEditUIName()
	 */
	protected String getEditUIName() {
		// TODO 自动生成方法存根
		// return super.getEditUIName();

		return com.kingdee.eas.fdc.contract.client.ContractSettlementBillEditUI.class
				.getName();
	}


	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#updateButtonStatus()
	 */
	protected void updateButtonStatus() {
		super.updateButtonStatus();

		// 如果是虚体成本中心，则不能审批、反审批
		// if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
		// /*
		// * actionAudit.setEnabled(false); actionUnAudit.setEnabled(false);
		// */
		// actionAudit.setVisible(false);
		// actionUnAudit.setVisible(false);
		// }

		// 放开，在虚体可以增删改
		actionAddNew.setEnabled(true);
		actionEdit.setEnabled(true);
		actionRemove.setEnabled(true);
		actionAddNew.setVisible(true);
		actionEdit.setVisible(true);
		actionRemove.setVisible(true);
		actionAttachment.setEnabled(true);
		actionAttachment.setVisible(true);
		menuEdit.setVisible(true);
		actionRespite.setVisible(true);
		actionCancelRespite.setVisible(true);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractSettlementBillListUI#initUIToolBarLayout()
	 */
	public void initUIToolBarLayout() {
		// TODO 自动生成方法存根
		super.initUIToolBarLayout();

		// 设置审批、反审批图标
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
	}

	protected boolean checkBeforeRemove() throws Exception {
		if(!super.checkBeforeRemove()){
			return false;
		}
		
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(
						new FilterItemInfo("settlementBill", idSet,
								CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBillBaseCollection coll = SettlementCostSplitFactory
				.getRemoteInstance().getCoreBillBaseCollection(view);
		Iterator iter = coll.iterator();
		if (iter.hasNext()) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("exist_ref"));
			SysUtil.abort();
		}
		
		return true;
	}

	protected boolean isFootVisible() {
		return false;
	}

	protected void fetchInitData() throws Exception {
		Map param = new HashMap();
		Map initData = ((IFDCBill)getRemoteInterface()).fetchInitData(param);
		
		//获得当前组织
		orgUnit = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		
		try {
			//合同可进行多次结算
			HashMap paramItem = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
			if(paramItem.get(FDCConstants.FDC_PARAM_MORESETTER)!=null){
				canSetterMore = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_MORESETTER).toString()).booleanValue();
			}
			
			//启用成本财务一体化
			if(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION)!=null){
				isIncorporation = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString()).booleanValue();
			}
		}catch (Exception e) {
			handUIException(e);
		}
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		FDCClientHelper.checkAuditor(getSelectedIdValues(), "T_CON_ContractSettlementBill");
		super.actionUnAudit_actionPerformed(e);
	}

	protected void tblSettlementList_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		super.tblSettlementList_tableSelectChanged(e);
		IRow row;
		if (this.tblSettlementList.getSelectManager().getActiveRowIndex()!= -1) {
			if((tblSettlementList.getSelectManager().getBlocks().size() > 1)
				||(e.getSelectBlock().getBottom() - e.getSelectBlock().getTop() > 0)){
				actionRespite.setEnabled(true);
				actionCancelRespite.setEnabled(true);
			}else{
				row = this.tblSettlementList.getRow(this.tblSettlementList.getSelectManager().getActiveRowIndex());
				if(Boolean.TRUE.equals(row.getCell("isRespite").getValue())){
					actionRespite.setEnabled(false);
					actionCancelRespite.setEnabled(true);
				}else{
					actionRespite.setEnabled(true);
					actionCancelRespite.setEnabled(false);
				}
				if(tblMain.getSelectManager().getBlocks().size() == 1 
					&&FDCBillStateEnum.AUDITTED.equals(row.getCell("state").getValue())){
					actionRespite.setEnabled(false);
					actionCancelRespite.setEnabled(true);
				}
			}
		}
	}
	
	/**
	 * 重载父类方法，支持跨项目拆分的工程项目也可进行合同结算及请款
	 */
	protected FilterInfo getTreeSelectFilter(Object projectNode,
			Object typeNode, boolean containConWithoutTxt) throws Exception {
		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();
		String companyID = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		/*
		 * 工程项目树
		 */
		if (projectNode != null 	&& projectNode instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			boolean isCompanyUint = false;
			FullOrgUnitInfo selectedOrg = new FullOrgUnitInfo();
			BOSUuid id = null;
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					id = ((OrgStructureInfo)projTreeNodeInfo).getUnit().getId();
					selectedOrg = ((OrgStructureInfo)projTreeNodeInfo).getUnit();
					isCompanyUint = selectedOrg.isIsCompanyOrgUnit();
				}else{
					selectedOrg = (FullOrgUnitInfo)projTreeNodeInfo;
					id = ((FullOrgUnitInfo)projTreeNodeInfo).getId();
					isCompanyUint = selectedOrg.isIsCompanyOrgUnit();
				}				
				
				String orgUnitLongNumber = null;
				if(orgUnit!=null && id.toString().equals(orgUnit.getId().toString())){					
					orgUnitLongNumber = orgUnit.getLongNumber();
				}else{
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance()
					.getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}
				
				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(
						new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));

				f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
				f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs,CompareType.INCLUDE));
				
				f.setMaskString("#0 and #1 and #2");
				if(FDCUtils.getDefaultFDCParamByKey(null, companyID, FDCConstants.FDC_PARAM_CROSSPROJECTSPLIT)
						&& (!isCompanyUint)){
					FilterInfo f2 = new FilterInfo();
					Set proSet = FDCClientUtils.getProjIdsOfCostOrg(selectedOrg, true);
					String filterSplitSql="select fcontractbillid from T_con_contractCostSplit head " +
						" inner join T_Con_contractCostSplitEntry entry on head.fid=entry.fparentid " +
						" inner join T_FDC_CostAccount acct on acct.fid=entry.fcostaccountid where acct.fcurProject in " +
						" ("+FDCClientUtils.getSQLIdSet(FDCClientUtils.getSQLIdSet(proSet))+") and fstate<>'9INVALID'";
					f2.getFilterItems().add(new FilterItemInfo("id", filterSplitSql,CompareType.INNER));
					f2.setMaskString("(#0 or #1)");
					if(filter!=null){
						filter.mergeFilter(f2,"and");
					}
				}
				if(filter!=null){
					filter.mergeFilter(f,"and");
				}
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(new FilterItemInfo("curProject.id", idSet,CompareType.INCLUDE));
				if(FDCUtils.getDefaultFDCParamByKey(null, companyID, FDCConstants.FDC_PARAM_CROSSPROJECTSPLIT)){
					String filterSplitSql="select fcontractbillid from T_con_contractCostSplit head " +
							" inner join T_Con_contractCostSplitEntry entry on head.fid=entry.fparentid " +
							" inner join T_FDC_CostAccount acct on acct.fid=entry.fcostaccountid where acct.fcurProject in " +
							" ("+FDCClientUtils.getSQLIdSet(idSet)+") and fstate<>'9INVALID'";
					f.getFilterItems().add(new FilterItemInfo("id", filterSplitSql,CompareType.INNER));
					f.setMaskString("(#0 or #1)");

				}
				if(filter!=null){
					filter.mergeFilter(f,"and");
				}
			}
		}

		FilterInfo typefilter =  new FilterInfo();
		FilterItemCollection typefilterItems = typefilter.getFilterItems();	
		/*
		 * 合同类型树
		 */
		if (typeNode != null&& typeNode instanceof TreeBaseInfo) {
			TreeBaseInfo typeTreeNodeInfo = (TreeBaseInfo)typeNode;
			BOSUuid id = typeTreeNodeInfo.getId();
			Set idSet = FDCClientUtils.genContractTypeIdSet(id);
			typefilterItems.add(new FilterItemInfo("contractType.id", idSet,CompareType.INCLUDE));
		}else if(containConWithoutTxt && typeNode != null &&typeNode.equals("allContract")){
			//如果包含无文本合同，查询所有时，让它查不到合同
			typefilterItems.add(new FilterItemInfo("contractType.id", "allContract"));
		}
		
		//三方合同
		if(!((ContractListBaseUI)this instanceof ContractBillListUI)){
			typefilter.appendFilterItem("isAmtWithoutCost", String.valueOf(0));
		}
		
		if(filter!=null && typefilter!=null){
			filter.mergeFilter(typefilter,"and");
		}
		
		return filter;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		this.actionQuery.setEnabled(true);
		this.actionQuery.setVisible(true);
	}	

	protected boolean isOnlyQueryAudited() {
		return true;
	}
}