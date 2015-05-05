package com.kingdee.eas.fdc.basedata.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class FDCSplitBillControllerBean extends AbstractFDCSplitBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.FDCSplitBillControllerBean");
    protected FDCCostSplit fdcCostSplit=null;
    protected String _getLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		return null;
	}
	protected void _audit(Context ctx, BOSUuid billId)throws BOSException, EASBizException
    {
         super._audit(ctx, billId);
    }
    protected void _unAudit(Context ctx, BOSUuid billId)throws BOSException, EASBizException
    {
		FDCBillInfo billInfo = new FDCBillInfo();

		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.SAVED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");

		updatePartial(ctx, billInfo, selector);
    }
    protected void _audit(Context ctx, List idList)throws BOSException, EASBizException
    {
         super._audit(ctx, idList);
    }
    protected void _unAudit(Context ctx, List idList)throws BOSException, EASBizException
    {
         super._unAudit(ctx, idList);
    }
    
    
    
	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#checkBill(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectValue)
	 */
	protected void checkBill(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		//super.checkBill(ctx, model);
	}
	
	

	


	/**
	 * 
	 * 描述：删除拆分单据的拆分汇总数据
	 * 
	 * @param ctx
	 * @param CostSplitBillTypeEnum	拆分类型
	 * @param costBillId				成本单据
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:Jelon Lee 
	 * 
	 * 创建时间：2006-11-24
	 *               <p>
	 */	
	protected void deleteCostSplit(Context ctx, CostSplitBillTypeEnum costSplitBillType, BOSUuid costBillId) throws BOSException, EASBizException {
		if(fdcCostSplit==null){
			fdcCostSplit=new FDCCostSplit(ctx);
		}
		
		fdcCostSplit.deleteCostSplit(ctx, costSplitBillType, costBillId);
		//已用上面的新接口代码替换
/*		//删除原来的汇总
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		//filterItems.add(new FilterItemInfo("costBillType", costSplitBillType.toString()));
		filterItems.add(new FilterItemInfo("costBillType", costSplitBillType.getValue()));
		filterItems.add(new FilterItemInfo("costBillId", costBillId.toString()));
		
		CostSplitFactory.getLocalInstance(ctx).delete(filter);*/
	}

    


	/**
	 * 
	 * 描述：生成拆分单据的拆分汇总数据
	 * 
	 * @param ctx
	 * @param CostSplitBillTypeEnum	拆分类型
	 * @param costBillId				成本单据
	 * @param CostBillEntrys			拆分分录
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:Jelon Lee 
	 * 
	 * 创建时间：2006-11-24
	 *               <p>
	 */	
	protected void collectCostSplit(Context ctx, CostSplitBillTypeEnum costSplitBillType, CoreBillBaseInfo coreBillInfo, BOSUuid splitBillId, AbstractObjectCollection CostBillEntrys) throws BOSException, EASBizException {
		if(fdcCostSplit==null){
			fdcCostSplit=new FDCCostSplit(ctx);
		}
		fdcCostSplit.collectCostSplit(costSplitBillType, coreBillInfo, splitBillId, CostBillEntrys);
	}
	
	protected void updateEntrySeq(Context ctx,IObjectValue model) throws BOSException{
		String id=model.getString("id");
		if(id==null){
			return;
		}
		String tableName=null;
		if(model instanceof ContractCostSplitInfo){
			tableName="T_CON_ContractCostSplitEntry";
		}
		if(model instanceof ConChangeSplitInfo){
			tableName="T_CON_ConChangeSplitEntry";
		}
		if(model instanceof ContractCostSplitInfo){
			tableName="T_CON_SettlementCostSplitEntry";
		}
		if(model instanceof ContractCostSplitInfo){
			tableName="T_FNC_PaymentSplitEntry";
		}
		if(tableName==null){
			return;
		}
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update " +tableName +" set FSeq=FIndex where FParentId=?");
		builder.addParam(id);
		builder.execute();
	}
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info)
			throws BOSException, CodingRuleException, EASBizException {
		// 拆分没有编码
	}

	/* 重载父类的方法,直接调用IObjectPK[]方法 (non-Javadoc)
	 * @see com.kingdee.eas.framework.app.AbstractCoreBaseControllerBean#_delete(com.kingdee.bos.Context, com.kingdee.bos.metadata.entity.FilterInfo)
	 */
	protected IObjectPK[] _delete(Context ctx, FilterInfo filter)
			throws BOSException, EASBizException {
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo("id"));
		IObjectPK[] arrayPK = getPKList(ctx, filter, sorter);
		// 改成调用子类的方法
		_delete(ctx, arrayPK);

		return arrayPK;
	}

	/*
	 * 重载父类的方法,直接调用IObjectPK[]方法
	 * 
	 * @see com.kingdee.eas.framework.app.AbstractCoreBaseControllerBean#_delete(com.kingdee.bos.Context,
	 *      java.lang.String)
	 */
	protected IObjectPK[] _delete(Context ctx, String oql) throws BOSException,
			EASBizException {
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo("id"));
		IObjectPK[] arrayPK = getPKList(ctx, oql);
		// 改成调用子类的方法
		_delete(ctx, arrayPK);
		return arrayPK;
	}
	
	/*
	 * 重载父类的方法,直接调用IObjectPK[]方法
	 * (non-Javadoc)
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#_delete(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectPK)
	 */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		_delete(ctx, new IObjectPK[]{pk});
	}
	
	protected IObjectValue _getNewData(Context ctx, Map param)
			throws BOSException,EASBizException{
		throw new NullPointerException("子类须实现");
	}

	/**
	 * 描述：返回单据工程项目财务组织一体化参数值 
	 * 
	 * @param ctx
	 * @throws BOSException
	 * @throws EASBizException
	 * @return map(key,value) key:工程项目+参数编码；value：参数值
	 * 
	 * @author pengwei_hou date:2009-06-29
	 */
	protected Map _fetchInitParam(Context ctx) throws BOSException,
			EASBizException {
		Map initParam = new HashMap();
		Map dataMap = new HashMap();
		Set authorizedOrgs = new HashSet();
		Map orgs = null;
		/**
		 * orgs上面已经为空，所以下面代码不用做非空判断
		 */
		/*if(orgs==null){
			orgs = PermissionFactory.getLocalInstance(ctx).getAuthorizedOrgs(
					 new ObjectUuidPK(ContextUtil.getCurrentUserInfo(ctx).getId()),
		            OrgType.CostCenter,  null,  null, null);
		}*/
		
		orgs = PermissionFactory.getLocalInstance(ctx).getAuthorizedOrgs(
				 new ObjectUuidPK(ContextUtil.getCurrentUserInfo(ctx).getId()),
	            OrgType.CostCenter,  null,  null, null);
		
		if(orgs!=null){
			Set orgSet = orgs.keySet();
			Iterator it = orgSet.iterator();
			while(it.hasNext()){
				authorizedOrgs.add(it.next());
			}
		}
		List number = new ArrayList();
		number.add(FDCConstants.FDC_PARAM_FINACIAL);
		number.add(FDCConstants.FDC_PARAM_ADJUSTVOURCHER);
		number.add(FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
		number.add(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND);
		number.add(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("select item.FOrgUnitID,item.FValue_L1,param.FNumber,p.FID from t_bas_paramitem item ");
		builder
				.appendSql("inner join t_bas_param param on param.fid=item.fkeyid ");
		builder
				.appendSql("inner join t_org_baseunit unit on unit.fid = item.forgunitid ");
		builder
				.appendSql("inner join t_fdc_curproject p on p.ffullorgunit=unit.fid ");
		builder.appendSql("where ");
		builder.appendParam("item.FOrgUnitID", authorizedOrgs.toArray());
		builder.appendSql(" and ");
		builder.appendParam("param.FNumber",number.toArray());
		
		IRowSet rowSet = builder.executeQuery();
		try {
			while(rowSet.next()){
				String orgUnitID = rowSet.getString("FOrgUnitID");
				String projectID = rowSet.getString("FID");
				String key = rowSet.getString("FNumber");
				String value = rowSet.getString("FValue_L1");
				initParam.put(key+projectID, value);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return initParam;
	}
	
}