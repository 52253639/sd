package com.kingdee.eas.fdc.contract.programming.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.util.*;

public class RenewRelateProgSaveFacadeControllerBean extends
		AbstractRenewRelateProgSaveFacadeControllerBean {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.contract.programming.app.RenewRelateProgSaveFacadeControllerBean");

	protected void _save(Context ctx, IObjectCollection objCol)
			throws BOSException, EASBizException {
		if (objCol.getObject(0) instanceof ContractBillInfo) {
			// 保存或者更新新集合对象
			SelectorItemCollection st = new SelectorItemCollection();
			st.add("programmingContract");
			st.add("isRenewRelateProg");
			st.add("srcProID");
			for (int i = 0; i < objCol.size(); i++) {
				String tempOldProg = null;
				ContractBillInfo info = (ContractBillInfo) objCol.getObject(i);
				if (checkIsExistProg(ctx, info.getId().toString()) != null) {
					tempOldProg = checkIsExistProg(ctx, info.getId().toString());
				} 
				info.setIsRenewRelateProg(1);
				info.setProgrammingContract(info.getProgrammingContract());
				//维护源ID用于动态规划
				if(info.getProgrammingContract() != null){
					info.setSrcProID(info.getProgrammingContract().getId().toString());
				}else{
					info.setSrcProID(null);
					info.setIsRenewRelateProg(0);
				}
				ContractBillFactory.getLocalInstance(ctx).updatePartial(info, st);
				//更新补充合同框架合约
				try {
					relateContractProg(ctx,info);
				} catch (SQLException e1) {
					logger.error(e1);
				}
				try {
					// 更新旧的框架合约金额
					if (tempOldProg != null) {
						int count = 0;// 关联合约数
						count = isCitingByProg(ctx,tempOldProg);
						boolean isCiting = preVersionProg(ctx,tempOldProg);
						if (count <= 1 && !isCiting) {
							updateProgrammingContract(ctx,tempOldProg, 0);
						}
						synUpdateBillByRelation(ctx, info,tempOldProg, false);
					}
					if (info.getProgrammingContract() != null) {
						updateProgrammingContract(ctx,info
								.getProgrammingContract().getId().toString(),1);
						synUpdateBillByRelation(ctx, info,null, true);
					}
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		} else if (objCol.getObject(0) instanceof ContractWithoutTextInfo) {
			// 保存或者更新新集合对象
			SelectorItemCollection st = new SelectorItemCollection();
			st.add("programmingContract");
			for (int i = 0; i < objCol.size(); i++) {
				ContractWithoutTextInfo info = (ContractWithoutTextInfo) objCol
						.getObject(i);
				ContractWithoutTextFactory.getLocalInstance(ctx).updatePartial(
						info, st);
			}
		}

	}
	/**
	 * 找出所关联的框架合约的记录数(无文本已经废除，不再查找)
	 * 
	 * @param proContId
	 * @return
	 */
	private int isCitingByProg(Context ctx ,String proContId) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql(" select count(1) count from T_INV_InviteProject ");
		buildSQL.appendSql(" where FProgrammingContractId = '" + proContId + "' ");
		buildSQL.appendSql(" union ");
		buildSQL.appendSql(" select count(1) count from T_CON_ContractBill ");
		buildSQL.appendSql(" where FProgrammingContract = '" + proContId + "' ");
		int count = 0;
		try {
			IRowSet iRowSet = buildSQL.executeQuery();
			while (iRowSet.next()) {
				count += iRowSet.getInt("count");
			}
		} catch (BOSException e) {
			logger.error(e);
		} catch (SQLException e) {
			logger.error(e);
		}
		return count;
	}
	private boolean preVersionProg(Context ctx, String progId) throws BOSException, SQLException{
		boolean isCityingProg = false;
		int tempIsCiting = 0;
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql(" select t1.FIsCiting isCiting from t_con_programmingContract t1 where t1.fid = (");
		buildSQL.appendSql(" select t2.FSrcId from t_con_programmingContract t2 where t2.fid = '"+progId+"')");
		IRowSet rowSet = buildSQL.executeQuery();
		while(rowSet.next()){
			tempIsCiting = rowSet.getInt("isCiting");
		}
		if(tempIsCiting > 0 ){
			isCityingProg = true;
		}
		return isCityingProg;
	}
	/**
	 * 更新规划合约"是否被引用"字段
	 * 
	 * @param proContId
	 * @param isCiting
	 */
	private void updateProgrammingContract(Context ctx,String proContId, int isCiting) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql("update T_CON_ProgrammingContract set FIsCiting = " + isCiting + " ");
		buildSQL.appendSql("where FID = '" + proContId + "' ");
		try {
			buildSQL.executeUpdate();
		} catch (BOSException e) {
			logger.error(e);
		}
	}
	private void relateContractProg(Context ctx,ContractBillInfo conInfo) throws BOSException, SQLException, EASBizException{
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select con.fid conId from t_con_contractbillentry entry");
		builder.appendSql(" inner join T_CON_Contractbill con on con.fid=entry.fparentid  and con.fisAmtWithoutCost=1 and con.fcontractPropert='SUPPLY'  ");
		builder.appendSql(" inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and parent.fcurprojectid=con.fcurprojectid	 ");
		builder.appendSql("  where entry.FRowkey='am' and");
		builder.appendParam("  parent.fid",conInfo.getId().toString());
		IRowSet rowSet = builder.executeQuery();
		while(rowSet.next()){
			ContractBillInfo relateConInfo = new ContractBillInfo() ;
			relateConInfo.setId(BOSUuid.read(rowSet.getString("conId")));
			relateConInfo.setProgrammingContract(conInfo.getProgrammingContract());
			SelectorItemCollection st = new SelectorItemCollection();
			st.add("programmingContract");
			ContractBillFactory.getLocalInstance(ctx).updatePartial(relateConInfo, st);
			
		}
	}
	/**
	 * 检查合同是否关联框架合约
	 * 
	 * @param ctx
	 * @param contractId
	 * @return
	 * @throws BOSException
	 */
	private String checkIsExistProg(Context ctx, String contractId)
			throws BOSException {
		String flag = null;
		String sql = "select fprogrammingcontract from t_con_contractbill where fid='"
				+ contractId + "'";
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx, sql.toString());
		IRowSet rs = fdcSB.executeQuery();
		try {
			while (rs.next()) {
				flag = rs.getString("fprogrammingcontract");
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return flag;

	}


	/**
	 * 1 .当合同未结算时(无最终结算或最终结算未审批)，规划余额=规划金额-（签约金额+变更金额），控制余额=控制金额-签约金额 2
	 * .当合同已结算时(最终结算已审批)，规划余额=规划金额-结算金额，控制余额=控制金额-结算金额 3
	 * .反写时点在合同单据审批通过时、变更审批单审批通过时、变更指令单结算时、合同结算审批通过时。 4.
	 * 合同修订审批后，规划余额=规划金额-（修订后的签约金额+变更金额），控制余额=控制金额-修订后的签约金额。
	 * 
	 * @param ctx
	 * @param billId
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws SQLException
	 * @throws SQLException
	 */
	private void synUpdateBillByRelation(Context ctx, ContractBillInfo contractInfo,String billId,
			boolean flag) throws EASBizException, BOSException, SQLException {
		ContractBillInfo contractBillInfo =
			ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new
					ObjectUuidPK(contractInfo.getId()), getSic());
		ProgrammingContractInfo pcInfo = null;
		IProgrammingContract service = ProgrammingContractFactory
		.getLocalInstance(ctx);
//		if (contractBillInfo.getProgrammingContract() == null)
//			return;
		//合同签约金额
		BigDecimal conSignAmt = FDCHelper.ZERO;
		//合同变更金额
		BigDecimal conChangeAmt = FDCHelper.ZERO;
		//合同结算金额
		BigDecimal conSettleAmt = FDCHelper.ZERO;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		IRowSet rowSet = null;
		builder.appendSql("select con.famount conSignAmt,change.changeAmount conChangeAmt,settle.settleAmount conSettleAmt from t_con_contractbill con ");
		builder.appendSql(" left join (select FContractBillID,sum(case when fhassettled = 1 then FBalanceAmount else famount end ) changeAmount from t_con_contractChangeBill where fstate in  ");
		builder.appendSql(" ('4AUDITTED','7ANNOUNCE','8VISA') group by FContractBillID ) change on change.FContractBillID = con.fid ");
		builder.appendSql(" left join (select FContractBillID,sum(case when fstate in( '4AUDITTED','7ANNOUNCE','8VISA') then FCurSettlePrice else 0 end) settleAmount from");
		builder.appendSql(" T_CON_ContractSettlementBill where fisSettled = 1  group by FContractBillID)  settle on con.fid =  settle.FContractBillID where ");
		builder.appendParam("con.fid", contractBillInfo.getId().toString());
		rowSet = builder.executeQuery();
		while (rowSet.next()) {
			conSignAmt = FDCHelper.toBigDecimal(rowSet.getString("conSignAmt"));
			conChangeAmt = FDCHelper.toBigDecimal(rowSet.getString("conChangeAmt"));
			conSettleAmt = FDCHelper.toBigDecimal(rowSet.getString("conSettleAmt"));
		}
		if(billId == null){
			pcInfo = service
					.getProgrammingContractInfo(new ObjectUuidPK(contractBillInfo.getProgrammingContract().getId().toString()),getSic());
			if(pcInfo == null) return;
			// 规划余额
			BigDecimal balanceAmt = pcInfo.getBalance();
			// 控制余额
			BigDecimal controlBalanceAmt = pcInfo.getControlBalance();
	//		//结算本币金额
	//		BigDecimal settleAmount = model.getTotalSettlePrice();
			//框架合约签约金额
			BigDecimal signAmountProg = pcInfo.getSignUpAmount();
			//框架合约变更金额
			BigDecimal changeAmountProg = pcInfo.getChangeAmount();
			//框架合约结算金额
			BigDecimal settleAmountProg = pcInfo.getSettleAmount();
			//反写各种金额
			pcInfo.setSignUpAmount(FDCHelper.add(signAmountProg, conSignAmt));
			pcInfo.setChangeAmount(FDCHelper.add(changeAmountProg, conChangeAmt));
			pcInfo.setSettleAmount(FDCHelper.add(settleAmountProg, conSettleAmt));
			if(contractBillInfo.isHasSettled()){
				BigDecimal settleAmount = conSettleAmt;
				pcInfo.setBalance(FDCHelper.subtract(balanceAmt, settleAmount));
				pcInfo.setControlBalance(FDCHelper.subtract(controlBalanceAmt, settleAmount));
			}else{
				pcInfo.setBalance(FDCHelper.subtract(balanceAmt, FDCHelper.add(conSignAmt, conChangeAmt)));
				pcInfo.setControlBalance(FDCHelper.subtract(controlBalanceAmt, conSignAmt));
			}
		}else{
			pcInfo = service
			.getProgrammingContractInfo(new ObjectUuidPK(billId),getSic());
			if(pcInfo == null) return;
			// 规划余额
			BigDecimal balanceAmt = pcInfo.getBalance();
			// 控制余额
			BigDecimal controlBalanceAmt = pcInfo.getControlBalance();
	//		//结算本币金额
	//		BigDecimal settleAmount = model.getTotalSettlePrice();
			//框架合约签约金额
			BigDecimal signAmountProg = pcInfo.getSignUpAmount();
			//框架合约变更金额
			BigDecimal changeAmountProg = pcInfo.getChangeAmount();
			//框架合约结算金额
			BigDecimal settleAmountProg = pcInfo.getSettleAmount();
			//反写各种金额
			pcInfo.setSignUpAmount(FDCHelper.subtract(signAmountProg, conSignAmt));
			pcInfo.setChangeAmount(FDCHelper.subtract(changeAmountProg, conChangeAmt));
			pcInfo.setSettleAmount(FDCHelper.subtract(settleAmountProg, conSettleAmt));
			if(contractBillInfo.isHasSettled()){
				BigDecimal settleAmount = conSettleAmt;
				pcInfo.setBalance(FDCHelper.add(balanceAmt, settleAmount));
				pcInfo.setControlBalance(FDCHelper.add(controlBalanceAmt, settleAmount));
			}else{
				pcInfo.setBalance(FDCHelper.add(balanceAmt, FDCHelper.add(conSignAmt, conChangeAmt)));
				pcInfo.setControlBalance(FDCHelper.add(controlBalanceAmt, conSignAmt));
			}
		}
		SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("controlBalance");
		sict.add("signUpAmount");
		sict.add("changeAmount");
		sict.add("settleAmount");
		sict.add("isCiting");
		service.updatePartial(pcInfo, sict);
	}

	private SelectorItemCollection getSic() {
		// 此过滤为详细信息定义
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("hasSettled"));
		sic.add(new SelectorItemInfo("programmingContract.*"));
		sic.add(new SelectorItemInfo("*"));
		return sic;
	}
	//add by david_yang R110415-556 2011.04.22
	protected Set _getContractbillID(Context ctx, Object[] id) throws BOSException {
		Set tempAllId = new HashSet();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select con.fid from t_con_contractbill con where 1 = 1 and con.FisRenewRelateProg = 0 and ");
		builder.appendParam("con.fid", id,"VARCHAR(44)");
		builder.appendSql("  and con.fprogrammingcontract in (SELECT prog.fid  FROM T_CON_ProgrammingContract AS prog");
		builder.appendSql(" inner JOIN T_CON_Programming AS programming ON prog.FProgrammingID = programming.FID");
		builder.appendSql(" where programming.fstate = '4AUDITTED')");
		IRowSet rowSet;
		try {
			rowSet = builder.executeQuery();
			while(rowSet.next()){
				tempAllId.add(rowSet.getString("fid").toString());
			}
			builder.releasTempTables();
		} catch (Exception e) {
			logger.error(e);
		}
		return tempAllId;
	}
}