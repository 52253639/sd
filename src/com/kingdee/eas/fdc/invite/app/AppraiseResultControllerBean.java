package com.kingdee.eas.fdc.invite.app;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.AcceptanceLetterFactory;
import com.kingdee.eas.fdc.invite.AppraiseResultCollection;
import com.kingdee.eas.fdc.invite.AppraiseResultEntryCollection;
import com.kingdee.eas.fdc.invite.AppraiseResultEntryInfo;
import com.kingdee.eas.fdc.invite.AppraiseResultFactory;
import com.kingdee.eas.fdc.invite.AppraiseResultInfo;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.fdc.invite.InviteProjectException;
import com.kingdee.eas.fdc.invite.NewQuotingPriceCollection;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.QuotingPriceStatusEnum;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordCollection;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordFactory;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryCollection;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryFactory;
import com.kingdee.eas.fdc.invite.TenderAccepterResultFactory;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

public class AppraiseResultControllerBean extends AbstractAppraiseResultControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.AppraiseResultControllerBean");

	public Map fetchInitData(Context ctx, Map param) throws BOSException, EASBizException {
		
		Map rMap = new HashMap();
		if(param==null)
			return rMap;
		String invProjectId = (String) param.get("invProjectId");
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("supplier.id");
		view.getSelector().add("supplier.name");
		view.getSelector().add("supplier.number");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.inviteProject",invProjectId));
		view.setFilter(filter);
		SupplierQualifyEntryCollection supplierQs = SupplierQualifyEntryFactory.getLocalInstance(ctx).getSupplierQualifyEntryCollection(view);
		rMap.put("supplierQs",supplierQs);
		
		SupplierInviteRecordCollection records = SupplierInviteRecordFactory.getLocalInstance(ctx).getSupplierInviteRecordCollection("select * where inviteProject='"+invProjectId+"'");
		Set hasRecordSupplierSet = new HashSet();
		for(int i=0;i<records.size();i++){
			if(records.get(i).getSupplier()!=null)
				hasRecordSupplierSet.add(records.get(i).getSupplier().getId().toString());
		}
		
		rMap.put("hasRecordSupplierSet", hasRecordSupplierSet);
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select e.fid from t_inv_expertqualifyentry e inner join t_inv_expertqualify p on e.fparentid=p.fid"); 
		builder.appendSql(" where p.finviteprojectid=?"); 
		builder.addParam(invProjectId);
		int expertQSize = builder.executeQuery().size();
		
//		view.getSelector().clear();
//		view.getSelector().add("id");
//		view.getFilter().getFilterItems().clear();
//		filter.getFilterItems().add(new FilterItemInfo("inviteProject",invProjectId));
//		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
//		ExpertAppraiseCollection experts = ExpertAppraiseFactory.getLocalInstance(ctx).getExpertAppraiseCollection(view);
		builder.clear();
		builder.appendSql(" select fid from t_inv_expertappraise where finviteprojectid=? and fstate=?");
		builder.addParam(invProjectId);
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		int expertSize = builder.executeQuery().size();
		if(expertSize<expertQSize||expertQSize==0){
//			throw new InviteProjectException(InviteProjectException.EXPERTAPPRAISENOTCOMPLETE);
		}
		boolean isUseWeight = true;
		builder.clear();
		builder.appendSql("select fisusewidth from t_inv_appraisetemplate where fid =(select FAPPRAISETEMPLATEID from t_inv_inviteproject where fid=?)");
		builder.addParam(invProjectId);
		IRowSet rSet  = builder.executeQuery();
		try {
			while(rSet.next()){
			   isUseWeight = rSet.getBoolean("fisusewidth");
				}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		builder.clear();
		builder.appendSql(" select sum(t.fweight) as weight from t_inv_inviteproject p inner join t_inv_appraisetemplateentry t on p.fappraisetemplateid=t.fparentid  where p.fid=?");
		builder.addParam(invProjectId);
		IRowSet rowSet = builder.executeQuery();
		Double weights = new Double(100.0);
		
		try{
			if(rowSet!=null&&rowSet.size()>0){
				rowSet.next();
				weights = new Double(rowSet.getDouble("weight"));
				isUseWeight = rowSet.getBoolean("FISUSEWIDTH");
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		Map supplierScoreMap = new HashMap();
		if(expertSize!=0){
			if(isUseWeight){
				builder.clear();
				builder.appendSql(" select s.fsupplierid,sum(s.fscore/"+expertSize+"*e.fweight/"+weights+") as score,a.finviteprojectid from t_inv_expertappraiseentryscore s,t_inv_expertappraiseentry e,t_inv_expertappraise a");
				builder.appendSql(" where s.fparentid=e.fid");
				builder.appendSql(" and e.fparentid=a.fid");
				builder.appendSql(" and a.finviteprojectid=?");
				builder.appendSql(" group by fsupplierid,a.finviteprojectid");
				builder.appendSql(" order by score desc");
				builder.addParam(invProjectId);
			}else{
				builder.clear();
				builder.appendSql(" select s.fsupplierid,sum(s.fscore/"+expertSize+") as score,a.finviteprojectid from t_inv_expertappraiseentryscore s,t_inv_expertappraiseentry e,t_inv_expertappraise a");
				builder.appendSql(" where s.fparentid=e.fid");
				builder.appendSql(" and e.fparentid=a.fid");
				builder.appendSql(" and a.finviteprojectid=?");
				builder.appendSql(" group by fsupplierid,a.finviteprojectid");
				builder.appendSql(" order by score desc");
				builder.addParam(invProjectId);
			}
			
			
			IRowSet rs = builder.executeQuery();
			int i=1;
			
			if(rs!=null&&rs.size()>0){
				try {
					while(rs.next()){
						Map valueMap = new HashMap();
						valueMap.put("seq",new Integer(i++));
						Double score = new Double(rs.getDouble("score"));
						valueMap.put("score",score);
						String supplierid = rs.getString("fsupplierid");
						supplierScoreMap.put(supplierid,valueMap);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		view.getSelector().clear();
		view.getSelector().add("*");
		view.getSelector().add("supplier.id");
		view.getSelector().add("supplier.name");
		view.getSelector().add("supplier.number");
		view.getFilter().getFilterItems().clear();
		
		view.getFilter().getFilterItems().add(new FilterItemInfo("listing.inviteProject",invProjectId));
//		view.getFilter().getFilterItems().add(new FilterItemInfo("status",QuotingPriceStatusEnum.IMPORTTODB_VALUE));
//		view.getFilter().getFilterItems().add(new FilterItemInfo("status",QuotingPriceStatusEnum.IMPORTBASE_VALUE));
//		view.getFilter().setMaskString(" #0 and ( #1 or #2 ) ");
		
		NewQuotingPriceCollection quotings = NewQuotingPriceFactory.getLocalInstance(ctx).getNewQuotingPriceCollection(view);
		if(quotings!=null&&quotings.size()>0){
			for(Iterator it=quotings.iterator();it.hasNext();){
				NewQuotingPriceInfo info = (NewQuotingPriceInfo) it.next();
				if(info.getSupplier()!=null){
					Map valueMap = (Map) supplierScoreMap.get(info.getSupplier().getId().toString());
					if(valueMap!=null){
						valueMap.put("quotingPrice",info.getTotoalPrice());
					}else{
						valueMap = new HashMap();
						valueMap.put("quotingPrice",info.getTotoalPrice());
						supplierScoreMap.put(info.getSupplier().getId().toString(), valueMap);
					}
				}
			}
		}
		rMap.put("supplierScoreMap",supplierScoreMap);
		
		return rMap;
		
	}

	protected boolean isUseName() {
		return false;
	}

	protected boolean isUseNumber() {
		return false;
	}
	protected void _audit(Context ctx, List idList) throws BOSException,
			EASBizException {
		
		Set idSet = new HashSet();
		for(int i = 0; i < idList.size(); ++i)
		{
			idSet.add(idList.get(i).toString());
		}
		
		
		//检测中标通知书是否有中标单位
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("entrys.*"));
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		
		AppraiseResultCollection resultCols = AppraiseResultFactory.getLocalInstance(ctx).getAppraiseResultCollection(view);
		Iterator iter = resultCols.iterator();
		
		int bidNum = 0;
		while(iter.hasNext())
		{
			AppraiseResultInfo resultInfo = (AppraiseResultInfo)iter.next();
			AppraiseResultEntryCollection entryCols = resultInfo.getEntrys();
			Iterator entryIter = entryCols.iterator();
			while(entryIter.hasNext())
			{
				AppraiseResultEntryInfo entryInfo = (AppraiseResultEntryInfo)entryIter.next();
				if(entryInfo.isHit())
				{
					bidNum++;
					break;
				}
			}
		}
		
		if(bidNum != resultCols.size())
		{
			throw new InviteProjectException(InviteProjectException.HASNOBIDSUPPLIER);
		}
		super._audit(ctx, idList);
	}
	
	/**
	 * R101206-376房地产成本招投标系统新增业务--中标结果报审 Added by Owen_wen 2011-04-11
	 * 评标结果报审单修改：如果招标立项目存在中标结果报审单（所有状态），则不允许反审批评标结果报审单。
	 * 提示：“所关联的招标立项已经有了中标结果报审单，不允许反审批。”
	 */
	protected void _unAudit(Context ctx, List idList) throws BOSException,
			EASBizException {
		
		Set idSet = new HashSet();
		for(int i = 0; i < idList.size(); ++i)
		{
			idSet.add(idList.get(i).toString());
		}
		
		
		//检测中标通知书是否有中标单位
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("inviteProject"));
		view.getSelector().add(new SelectorItemInfo("entrys.*"));
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		
		AppraiseResultCollection resultCols = AppraiseResultFactory.getLocalInstance(ctx).getAppraiseResultCollection(view);
		Iterator iter = resultCols.iterator();
		
		Set inviteProjectIdSet = new HashSet();
		while(iter.hasNext())
		{
			AppraiseResultInfo resultInfo = (AppraiseResultInfo)iter.next();
			inviteProjectIdSet.add(resultInfo.getInviteProject().getId().toString());
		}
		
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("inviteProject", inviteProjectIdSet, CompareType.INCLUDE));
		
		if (AcceptanceLetterFactory.getLocalInstance(ctx).exists(filter))
		{
			throw new InviteProjectException(InviteProjectException.HASACCEPTANCELETTER);
		}
		
		// 如果招标立项目存在中标结果报审单（所有状态），则不允许反审批评标结果报审单
		if (TenderAccepterResultFactory.getLocalInstance(ctx).exists(filter)) {
			throw new FDCInviteException(FDCInviteException.EXISTTENDERACPTRESULT);
		}
		
		super._unAudit(ctx, idList);
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		AppraiseResultInfo resultInfo = AppraiseResultFactory.getLocalInstance(ctx).getAppraiseResultInfo(pk);
		AppraiseResultEntryCollection entryCols = resultInfo.getEntrys();
		
		String inviteProjectId = resultInfo.getInviteProject().getId().toString();
		
		Iterator iter = entryCols.iterator();
		Set supSetId = new HashSet();
		while(iter.hasNext())
		{
			AppraiseResultEntryInfo entryinfo = (AppraiseResultEntryInfo)iter.next();
			if(entryinfo.getSupplier() != null)
			{
				supSetId.add(entryinfo.getSupplier().getId().toString());
			}
		}
		
		try {
			updateNewQuotingPriceStatus(ctx, supSetId, inviteProjectId);
		} catch (UuidException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		super._delete(ctx, pk);
	}
	
	private void updateNewQuotingPriceStatus(Context ctx, Set paramSupSetId, String paramInvPrjId) throws BOSException, EASBizException, UuidException, SQLException
	{
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select quoting.fid as quotingId from T_INV_NewQuotingPrice quoting ");
		builder.appendSql(" left outer join t_inv_newlisting listing on listing.fid = quoting.flistingid ");
		builder.appendSql(" left outer join t_inv_appraiseresult appRes on appRes.Finviteprojectid = listing.finviteprojectid ");
		builder.appendSql(" where appRes.Finviteprojectid = ");
		builder.appendParam(paramInvPrjId);
		builder.appendSql(" and ");
		builder.appendSql(" quoting.fsupplierid in ( ");
		builder.appendParam(paramSupSetId.toArray());
		builder.appendSql( " ) ");
		/*builder.appendParam("quoting.fsupplierid", paramSupSetId.toArray());
		
		builder.appendParam("appRes.Finviteprojectid = ", paramInvPrjId);*/
		
		IRowSet rowSet = builder.executeQuery(ctx);
		while (rowSet.next()) {
			String newQuoteId = rowSet.getString("quotingId");
			if(newQuoteId != null)
			{
				NewQuotingPriceInfo priceInfo = new NewQuotingPriceInfo();
				priceInfo.setId(BOSUuid.read(newQuoteId));
				priceInfo.setStatus(QuotingPriceStatusEnum.Evaluated);
				priceInfo.setHasEffected(true);

				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("status");
				sic.add("hasEffected");

				NewQuotingPriceFactory.getLocalInstance(ctx).updatePartial(priceInfo, sic);
			}
			
		}
	}

	/**
	 * 获取已审核单据中的招标立项
	 * @author msb 2010/04/26
	 */
	protected Set _getInviteProjectId(Context ctx) throws BOSException,
			EASBizException {
		Set idSet = new HashSet();
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("inviteProject.id");
		sic.add("state");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		view.setSelector(sic);
		view.setFilter(filter);
		
		AppraiseResultCollection coll =this.getAppraiseResultCollection(ctx, view);
		for(Iterator iterator = coll.iterator();iterator.hasNext();){
			AppraiseResultInfo model = (AppraiseResultInfo)iterator.next();
			if(model.getInviteProject() != null){
				idSet.add(model.getInviteProject().getId().toString());
			}
		}
		
		return idSet;
	}
	
}