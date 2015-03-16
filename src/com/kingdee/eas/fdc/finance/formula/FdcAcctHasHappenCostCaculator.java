package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.FullDynamicCostMap;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.finance.client.FDCFormulaWizard;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.jdbc.rowset.IRowSet;

public class FdcAcctHasHappenCostCaculator implements IMethodBatchQuery,
		ICalculator {
	
	private static final Logger logger = Logger.getLogger(FdcAcctHasHappenCostCaculator.class);
	
	private Context ServerCtx = null;
	//公司编码
	private String fdcCompanyNumber = null ;
	//项目长编码
	private String prjNumber = null ;
	//成本科目长编码
	private String acctLongNumber = null ;
	
	private ChangeTypeCollection changeTypes;
	
	private HappenDataGetter happenGetter = null;
	
	private ICalculateContextProvider context;
	
	public Context getServerCtx()
	{
		return ServerCtx;
	}

	public void setServerCtx(Context serverCtx)
	{
		ServerCtx = serverCtx;
	}
	public void initCalculateContext(ICalculateContextProvider context) {
		this.context = context;
		this.ServerCtx = this.context.getServerContext();
		
	}
	public boolean batchQuery(Map methods) {
		//获得从界面传递过来的相关参数
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_acct_hasHappen_cost");
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();

				fdcCompanyNumber = (String) ((Variant) obj[0]).getValue();
				prjNumber = (String) ((Variant) obj[1]).getValue();
				acctLongNumber = (String) ((Variant) obj[2]).getValue();

				try {
					//通过计算公式计算，返回结果
					BigDecimal amount = this.fdc_acct_hasHappen_cost(
							fdcCompanyNumber, prjNumber, acctLongNumber);
					params.getParameter(i).setValue(amount);
				} catch (Exception e) {
					logger.error(e);
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 房地产工程项目已发生成本取数公式
	 * @param fdcCompanyNumber 公司编码
	 * @param prjNumber  项目长编码
	 * @param acctLongNumber 成本科目长编码
	 * @return
	 * @throws ParseException
	 * @throws BOSException
	 * @throws SQLException
	 */
	public BigDecimal fdc_acct_hasHappen_cost(String fdcCompanyNumber,String prjNumber,String acctLongNumber) throws ParseException, BOSException, SQLException
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * 将点替换成叹号
		 */
//		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		if(!"".equals(acctLongNumber))
			acctLongNumber = acctLongNumber.replace('.','!');
				
		/**
		 * 通过T_ORG_CostCenter中的number和fdcCompanyNumber,获取FCostCenterId
		 * 然后在T_FDC_CurProject和FCostCenterId对应的集合中，找FlongNumber和prjNumber
		 * 对应结果集，然后返回fid集合，即通过公司和项目选取当前项目的ID
		 */
		builder.appendSql("select curProject.fid from T_FDC_CurProject curProject ");
		builder.appendSql(" inner join T_ORG_CostCenter costCenter on curProject.FFullOrgUnit = costCenter.fid ");
		builder.appendSql(" where costCenter.FNumber = ? ");
		builder.addParam(fdcCompanyNumber);
		builder.appendSql(" and ");
		builder.appendSql(" curProject.FLongNumber = ? ");
		builder.addParam(prjNumber);
		
		/**
		 * 将当前项目的ID放在List中，便于在成本科目中查找对应的成本科目
		 */
		List curProjectID = new ArrayList();
		
		IRowSet rowSet = builder.executeQuery(ServerCtx);
	
		while((rowSet != null) && (rowSet.next()))
		{
			curProjectID.add(rowSet.getString("fid"));
		}
		
		//清除builder中的sql语句
		builder.clear();
		
		String projectID = "" ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
		}
		//获得成本科目
		builder.appendSql("select FID from T_FDC_CostAccount ");
		builder.appendSql(" where FCurProject = ? ");
		builder.addParam(projectID);
		builder.appendSql(" and FIsLeaf = ?");
		builder.addParam(new Integer(1));
		if(!"".equals(acctLongNumber)){
			builder.appendSql( " and ( ");
			builder.appendSql( " FlongNumber = ? ");
			builder.addParam(acctLongNumber);
			builder.appendSql( " or charindex ( ? ");
			builder.addParam(acctLongNumber);
			builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );			
		}
		
		List costAccountID = new ArrayList();
		
		//之前为了重用上面资源所引起的一些问题，现在重新定义变量
		IRowSet rowSetRes = builder.executeQuery(ServerCtx);
		
		while((rowSetRes != null) && (rowSetRes.next()))
		{
			costAccountID.add(rowSetRes.getString("fid"));
		}
		
		builder.clear() ;
		
		BigDecimal result = FDCHelper.ZERO;
		try {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.TRUE));
			view.getSorter().add(new SorterItemInfo("number"));
			view.getSelector().add("id");
			view.getSelector().add("name");
			this.changeTypes = ChangeTypeFactory.getLocalInstance(ServerCtx)
					.getChangeTypeCollection(view);
			if(!"".equals(projectID)){
				final FullDynamicCostMap fullDynamicCostMap = FDCCostRptFacadeFactory
					.getLocalInstance(ServerCtx).getFullDynamicCost(projectID, null);				
				this.happenGetter=fullDynamicCostMap.getHappenDataGetter();
			}
		} catch (EASBizException e) {
			logger.error(e);
		}
		if(happenGetter != null){
			for(Iterator it = costAccountID.iterator();it.hasNext();){
				String acctId = (String)it.next();
				HappenDataInfo happenDataInfo = (HappenDataInfo) this.happenGetter.conSplitMap.get(acctId + 0);
				//未结算合同金额
				BigDecimal noSettConAmount = null;
				if (happenDataInfo != null) {
					noSettConAmount = happenDataInfo.getAmount();
				}
				//未结算变更金额合计
				BigDecimal noSettleChangeSumAmount = null;
				for (int i = 0; i < changeTypes.size(); i++) {
					ChangeTypeInfo change = changeTypes.get(i);
					happenDataInfo = (HappenDataInfo) this.happenGetter.changeSplitMap
					.get(acctId + change.getId().toString() + 0);
					BigDecimal changeAmount = null;
					//未结算变更金额
					if (happenDataInfo != null) {
						changeAmount = happenDataInfo.getAmount();
					}
					if (changeAmount != null) {
						if (noSettleChangeSumAmount == null) {
							noSettleChangeSumAmount = FDCHelper.ZERO;
						}
						noSettleChangeSumAmount = noSettleChangeSumAmount
						.add(changeAmount);
					}
				}
				//未结算合同小计
				BigDecimal noSettleTotal = null;
				if (noSettConAmount != null) {
					noSettleTotal = noSettConAmount;
				}
				if (noSettleChangeSumAmount != null) {
					if (noSettleTotal == null) {
						noSettleTotal = noSettleChangeSumAmount;
					} else {
						noSettleTotal = noSettleTotal.add(noSettleChangeSumAmount);
					}
				}
				happenDataInfo = (HappenDataInfo) this.happenGetter.settleSplitMap.get(acctId);
				//结算合计
				BigDecimal settleTotal = null;
				if (happenDataInfo != null) {
					settleTotal = happenDataInfo.getAmount();
				}
				happenDataInfo = (HappenDataInfo) this.happenGetter.noTextSplitMap.get(acctId);
				//非合同性成本
				BigDecimal noTextAmount = null;
				if (happenDataInfo != null) {
					noTextAmount = happenDataInfo.getAmount();
				}
				//该科目上的已发生成本金额
				BigDecimal hasHappenAmount = null;
				if (noSettleTotal != null) {
					hasHappenAmount = noSettleTotal;
				}
				if (settleTotal != null) {
					if (hasHappenAmount == null) {
						hasHappenAmount = FDCHelper.ZERO;
					}
					hasHappenAmount = hasHappenAmount.add(settleTotal);
				}
				if (noTextAmount != null) {
					if (hasHappenAmount == null) {
						hasHappenAmount = FDCHelper.ZERO;
					}
					hasHappenAmount = hasHappenAmount.add(noTextAmount);
				}
				result = FDCHelper.add(result,hasHappenAmount);
			}
			
		}
		if(result == null){
			result = FDCHelper.ZERO;
		}
		return result;
	}
}
