package com.kingdee.eas.fdc.invite.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
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
import com.kingdee.eas.fdc.invite.AppraiseTemplateFactory;
import com.kingdee.eas.fdc.invite.AppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.ExpertAppraiseCollection;
import com.kingdee.eas.fdc.invite.ExpertAppraiseEntryCollection;
import com.kingdee.eas.fdc.invite.ExpertAppraiseEntryFactory;
import com.kingdee.eas.fdc.invite.ExpertAppraiseEntryInfo;
import com.kingdee.eas.fdc.invite.ExpertAppraiseEntryScoreCollection;
import com.kingdee.eas.fdc.invite.ExpertAppraiseEntryScoreFactory;
import com.kingdee.eas.fdc.invite.ExpertAppraiseEntryScoreInfo;
import com.kingdee.eas.fdc.invite.ExpertAppraiseFactory;
import com.kingdee.eas.fdc.invite.ExpertAppraiseInfo;
import com.kingdee.eas.fdc.invite.ExpertAppraiseResultCollection;
import com.kingdee.eas.fdc.invite.ExpertAppraiseResultFactory;
import com.kingdee.eas.fdc.invite.ExpertAppraiseResultInfo;
import com.kingdee.eas.fdc.invite.InviteProjectException;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordCollection;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordFactory;
import com.kingdee.eas.fdc.invite.SupplierQualifyCollection;
import com.kingdee.eas.fdc.invite.SupplierQualifyFactory;
import com.kingdee.eas.fdc.invite.SupplierQualifyInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;

public class ExpertAppraiseControllerBean extends AbstractExpertAppraiseControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.ExpertAppraiseControllerBean");

	
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		CoreBaseCollection baseCollection = new CoreBaseCollection();
		for(int i=0;i<((ExpertAppraiseInfo)model).getEntry().size();i++){
			baseCollection.add(((ExpertAppraiseInfo)model).getEntry().get(i));
			((ExpertAppraiseInfo)model).getEntry().get(i).setParent(((ExpertAppraiseInfo)model));
		}
		((ExpertAppraiseInfo)model).getEntry().clear();
		super._save(ctx, pk, model);
		if(baseCollection.size()>0)
		{
			for(int i=0;i<baseCollection.size();i++){
				((ExpertAppraiseEntryInfo)baseCollection.get(i)).setParent(((ExpertAppraiseInfo)model));
			}
			ExpertAppraiseEntryFactory.getLocalInstance(ctx).save(baseCollection);
		}
	}

	
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
		CoreBaseCollection baseCollection = new CoreBaseCollection();
		for(int i=0;i<((ExpertAppraiseInfo)model).getEntry().size();i++){
			baseCollection.add(((ExpertAppraiseInfo)model).getEntry().get(i));
			((ExpertAppraiseInfo)model).getEntry().get(i).setParent(((ExpertAppraiseInfo)model));
			
		}
		((ExpertAppraiseInfo)model).getEntry().clear();
		IObjectPK pk = super._save(ctx, model);
		if(baseCollection.size()>0)
		{
			for(int i=0;i<baseCollection.size();i++){
				((ExpertAppraiseEntryInfo)baseCollection.get(i)).setParent(((ExpertAppraiseInfo)model));
			}
			ExpertAppraiseEntryFactory.getLocalInstance(ctx).save(baseCollection);
		}
		
		return pk;
	}

	
	protected Map _fetchInitSumData(Context ctx, Map param) throws BOSException, EASBizException {
		
		Map initData = new HashMap();
		String inviteProjectId = (String)param.get("ID");
		if(param.containsKey("resultID")){
			String resultID = (String) param.get("resultID");
			ExpertAppraiseResultInfo resultInfo = ExpertAppraiseResultFactory.getLocalInstance(ctx).getExpertAppraiseResultInfo(new ObjectUuidPK(resultID));
			initData.put("resultInfo", resultInfo);
			if(resultInfo.getInviteProject()!=null)
				inviteProjectId = resultInfo.getInviteProject().getId().toString();
		}
		SelectorItemCollection selectors = new SelectorItemCollection();
		FilterInfo filter = null;
		EntityViewInfo view = null;
		
		if(inviteProjectId!=null){
			selectors.clear();
			selectors.add(new SelectorItemInfo("*"));
			selectors.add(new SelectorItemInfo("name"));
			selectors.add(new SelectorItemInfo("inviteMode.*"));
			selectors.add(new SelectorItemInfo("orgUnit.*"));
			selectors.add(new SelectorItemInfo("project.name"));
			selectors.add(new SelectorItemInfo("project.number"));
			selectors.add(new SelectorItemInfo("appraiseTemplate.templateType.name"));
			selectors.add(new SelectorItemInfo("appraiseTemplate.templateType.number"));
			selectors.add(new SelectorItemInfo("appraiseTemplate.templateType.id"));
			selectors.add(new SelectorItemInfo("appraiseTemplate.number"));
			selectors.add(new SelectorItemInfo("appraiseTemplate.name"));
			selectors.add(new SelectorItemInfo("appraiseTemplate.id"));
			InviteProjectInfo inviteProject = InviteProjectFactory.getLocalInstance(ctx).getInviteProjectInfo(new ObjectUuidPK(inviteProjectId),selectors);
			initData.put("inviteProject",inviteProject); 
			selectors.clear();
			selectors.add(new SelectorItemInfo("*"));
			selectors.add(new SelectorItemInfo("templateType.name"));
			selectors.add(new SelectorItemInfo("templateType.number"));
			selectors.add(new SelectorItemInfo("templateType.id"));
			selectors.add(new SelectorItemInfo("templateEntry.*"));
			selectors.add(new SelectorItemInfo("templateEntry.guideLine.*"));
			selectors.add(new SelectorItemInfo("templateEntry.guideLine.guideLineType.*"));
			if(inviteProject.getAppraiseTemplate() == null)
				return initData ;
			String appraiseTemplateId = inviteProject.getAppraiseTemplate().getId().toString();
			
			AppraiseTemplateInfo template = AppraiseTemplateFactory.getLocalInstance(ctx).getAppraiseTemplateInfo(new ObjectUuidPK(appraiseTemplateId),selectors);
			
			inviteProject.setAppraiseTemplate(template);
			initData.put("appraiseTemplate",template); 
			
			selectors.clear();
			selectors.add(new SelectorItemInfo("*"));
			selectors.add(new SelectorItemInfo("guideLine.*"));
			selectors.add(new SelectorItemInfo("entry.*"));
			selectors.add(new SelectorItemInfo("entry.supplier.*"));
			view = new EntityViewInfo();
			view.setSelector(selectors);
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("inviteProject",inviteProject.getId().toString()));
			view.setFilter(filter);
			SupplierQualifyCollection coll = SupplierQualifyFactory.getLocalInstance(ctx).getSupplierQualifyCollection(view);
			if(coll!=null&&coll.size()>0){
				SupplierQualifyInfo supplierQualify = coll.get(0);
				initData.put("supplierQualify",supplierQualify);
			}
			view.getSelector().clear();
			view.getSelector().add("*");
			SupplierInviteRecordCollection records = SupplierInviteRecordFactory.getLocalInstance(ctx).getSupplierInviteRecordCollection(view);
			Set hasRecordSupplierSet = new HashSet();
			for(int i=0;i<records.size();i++){
				if(records.get(i).getSupplier()!=null)
					hasRecordSupplierSet.add(records.get(i).getSupplier().getId().toString());
			}
			
			initData.put("hasRecordSupplierSet", hasRecordSupplierSet);
			selectors.clear();
			selectors.add(new SelectorItemInfo("*"));
			selectors.add(new SelectorItemInfo("supplier.*"));
			selectors.add(new SelectorItemInfo("parent.guideLine.*"));

			view = new EntityViewInfo();
			view.setSelector(selectors);
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.parent.inviteProject",inviteProject.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("parent.parent.state",FDCBillStateEnum.AUDITTED_VALUE));
			view.setFilter(filter);
			ExpertAppraiseEntryScoreCollection entryColl = ExpertAppraiseEntryScoreFactory.getLocalInstance(ctx).getExpertAppraiseEntryScoreCollection(view);
			
			selectors.clear();
			selectors.add(new SelectorItemInfo("id"));
			selectors.add(new SelectorItemInfo("description"));
			selectors.add(new SelectorItemInfo("creator.name"));
			selectors.add(new SelectorItemInfo("inviteProject.id"));
			selectors.add(new SelectorItemInfo("state"));
			
			selectors.add(new SelectorItemInfo("entry.guideLine.*"));
			selectors.add(new SelectorItemInfo("entry.*"));
			selectors.add(new SelectorItemInfo("creator.number"));
			selectors.add(new SelectorItemInfo("creator.name"));
			
			//
			view = new EntityViewInfo();
			view.setSelector(selectors);
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",inviteProject.getId().toString(),CompareType.EQUALS));
//			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
			view.setFilter(filter);
			
			ExpertAppraiseCollection experts = ExpertAppraiseFactory.getLocalInstance(ctx).getExpertAppraiseCollection(view);
			
			ExpertAppraiseCollection auditExpert = new ExpertAppraiseCollection();
			/***
			 * 返回专家评标的备注's
			 */
			StringBuffer buffer = new StringBuffer();
			int audittedSize  = 0;
			for(Iterator itertor = experts.iterator();itertor.hasNext();){
				ExpertAppraiseInfo tmpAppraiseInfo = (ExpertAppraiseInfo)itertor.next();
				if(!FDCBillStateEnum.AUDITTED.equals(tmpAppraiseInfo.getState()))
					continue;
				audittedSize++;
				buffer.append(tmpAppraiseInfo.getCreator().getName());
				buffer.append("：\n");
//				buffer.append("  ");
				if(tmpAppraiseInfo.getDescription() != null)
				{
					buffer.append(tmpAppraiseInfo.getDescription());
					buffer.append("\n");
				}
				
				auditExpert.add(tmpAppraiseInfo);
			}
			
			initData.put("expertAppraiseDesc",buffer.toString());
			initData.put("auditExpert", auditExpert);
			
			Map supplierScores = new HashMap();
			for(Iterator it = entryColl.iterator();it.hasNext();){
				ExpertAppraiseEntryScoreInfo entryInfo = (ExpertAppraiseEntryScoreInfo)it.next();
				
				String key = entryInfo.getParent().getGuideLine().getId().toString()+"_"+entryInfo.getSupplier().getName();
				Double value = new Double(entryInfo.getScore()/audittedSize);
				Double v = new Double(0.0);
				if(supplierScores.containsKey(key)){
					v = (Double)supplierScores.get(key);
				}
				supplierScores.put(key,new Double(value.doubleValue()+v.doubleValue()));
				
			}
			initData.put("supplierScores",supplierScores);
			
			//返回评标结果的纪录，此记录主要用来支持工作流
			if(!initData.containsKey("resultInfo")){
				view.getSelector().clear();
				view.getSelector().add("*");
				ExpertAppraiseResultCollection results = ExpertAppraiseResultFactory.getLocalInstance(ctx).getExpertAppraiseResultCollection(view);
				if(results!=null&&results.size()>0){
					ExpertAppraiseResultInfo info = results.get(0);
					info.setInviteProject(inviteProject);
					initData.put("resultInfo",info);
				}
			}
		}
		return initData;
	}

	
	public Map fetchInitData(Context ctx, Map param) throws BOSException, EASBizException {
		
		Map initData = new HashMap();
		String objectId = (String)param.get("objectId");
		String inviteProjectId = (String)param.get("inviteProjectId");
		SelectorItemCollection selectors = new SelectorItemCollection();
		FilterInfo filter = null;
		EntityViewInfo view = null;
		if(objectId!=null){
			selectors.clear();
			selectors.add(new SelectorItemInfo("*"));
			selectors.add(new SelectorItemInfo("parent.inviteProject.id"));
			selectors.add(new SelectorItemInfo("guideLine.*"));
			selectors.add(new SelectorItemInfo("guideLine.guideLineType.*"));
			selectors.add(new SelectorItemInfo("scores.*"));
			selectors.add(new SelectorItemInfo("scores.supplier.id"));
			selectors.add(new SelectorItemInfo("scores.supplier.name"));
			selectors.add(new SelectorItemInfo("scores.supplier.number"));
			view = new EntityViewInfo();
			view.setSelector(selectors);
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent",objectId));
			view.setFilter(filter);
			ExpertAppraiseEntryCollection entrys = ExpertAppraiseEntryFactory.getLocalInstance(ctx).getExpertAppraiseEntryCollection(view);
			initData.put("editDataEntry",entrys);
			if(inviteProjectId==null&&entrys.size()>0&&entrys.get(0).getParent()!=null&&entrys.get(0).getParent().getInviteProject()!=null){
				inviteProjectId = entrys.get(0).getParent().getInviteProject().getId().toString();
			}
		}
		if(inviteProjectId!=null){
			selectors.clear();
			selectors.add(new SelectorItemInfo("*"));
			selectors.add(new SelectorItemInfo("inviteMode.*"));
			selectors.add(new SelectorItemInfo("orgUnit.*"));
			selectors.add(new SelectorItemInfo("name"));
			selectors.add(new SelectorItemInfo("project.name"));
			selectors.add(new SelectorItemInfo("project.number"));
			selectors.add(new SelectorItemInfo("appraiseTemplate.templateType.name"));
			selectors.add(new SelectorItemInfo("appraiseTemplate.templateType.number"));
			selectors.add(new SelectorItemInfo("appraiseTemplate.templateType.id"));
			selectors.add(new SelectorItemInfo("appraiseTemplate.number"));
			selectors.add(new SelectorItemInfo("appraiseTemplate.name"));
			selectors.add(new SelectorItemInfo("appraiseTemplate.id"));
			InviteProjectInfo inviteProject = InviteProjectFactory.getLocalInstance(ctx).getInviteProjectInfo(new ObjectUuidPK(inviteProjectId),selectors);
			initData.put("inviteProject",inviteProject); 
			selectors.clear();
			selectors.add(new SelectorItemInfo("*"));
			selectors.add(new SelectorItemInfo("templateType.name"));
			selectors.add(new SelectorItemInfo("templateType.number"));
			selectors.add(new SelectorItemInfo("templateType.id"));
			selectors.add(new SelectorItemInfo("templateEntry.*"));
			selectors.add(new SelectorItemInfo("templateEntry.guideLine.*"));
			selectors.add(new SelectorItemInfo("templateEntry.guideLine.guideLineType.*"));
			String appraiseTemplateId = inviteProject.getAppraiseTemplate().getId().toString();
			
			AppraiseTemplateInfo template = AppraiseTemplateFactory.getLocalInstance(ctx).getAppraiseTemplateInfo(new ObjectUuidPK(appraiseTemplateId),selectors);
			
			inviteProject.setAppraiseTemplate(template);
			initData.put("appraiseTemplate",template); 
			
			selectors.clear();
			selectors.add(new SelectorItemInfo("*"));
			selectors.add(new SelectorItemInfo("entry.*"));
			selectors.add(new SelectorItemInfo("entry.supplier.*"));
			view = new EntityViewInfo();
			view.setSelector(selectors);
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",inviteProject.getId().toString()));
			view.setFilter(filter);
			SupplierQualifyCollection coll = SupplierQualifyFactory.getLocalInstance(ctx).getSupplierQualifyCollection(view);
			if(coll!=null&&coll.size()>0){
				SupplierQualifyInfo supplierQualify = coll.get(0);
				initData.put("supplierQualify",supplierQualify);
			}
			view.getSelector().clear();
			view.getSelector().add("*");
			SupplierInviteRecordCollection records = SupplierInviteRecordFactory.getLocalInstance(ctx).getSupplierInviteRecordCollection(view);
			Set hasRecordSupplierSet = new HashSet();
			for(int i=0;i<records.size();i++){
				if(records.get(i).getSupplier()!=null)
					hasRecordSupplierSet.add(records.get(i).getSupplier().getId().toString());
			}
			
			initData.put("hasRecordSupplierSet", hasRecordSupplierSet);
			
			
		}
		return initData;
	}

	
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		
		CoreBaseCollection baseCollection = new CoreBaseCollection();
		for(int i=0;i<((ExpertAppraiseInfo)model).getEntry().size();i++){
			baseCollection.add(((ExpertAppraiseInfo)model).getEntry().get(i));
			((ExpertAppraiseInfo)model).getEntry().get(i).setParent(((ExpertAppraiseInfo)model));
		}
		((ExpertAppraiseInfo)model).getEntry().clear();
		super._submit(ctx, pk, model);
		if(baseCollection.size()>0)
		{
			for(int i=0;i<baseCollection.size();i++){
				((ExpertAppraiseEntryInfo)baseCollection.get(i)).setParent(((ExpertAppraiseInfo)model));
			}
			ExpertAppraiseEntryFactory.getLocalInstance(ctx).save(baseCollection);
		}
	}

	
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
		CoreBaseCollection baseCollection = new CoreBaseCollection();
		for(int i=0;i<((ExpertAppraiseInfo)model).getEntry().size();i++){
			baseCollection.add(((ExpertAppraiseInfo)model).getEntry().get(i));
			((ExpertAppraiseInfo)model).getEntry().get(i).setParent(((ExpertAppraiseInfo)model));
		}
		((ExpertAppraiseInfo)model).getEntry().clear();
		IObjectPK pk =  super._submit(ctx, model);
		if(baseCollection.size()>0)
		{
			for(int i=0;i<baseCollection.size();i++){
				((ExpertAppraiseEntryInfo)baseCollection.get(i)).setParent(((ExpertAppraiseInfo)model));
			}
			ExpertAppraiseEntryFactory.getLocalInstance(ctx).save(baseCollection);
		}
		return pk;
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		checkTheCreator(ctx,billId.toString());
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
		sqlBuilder.clear();
		sqlBuilder.appendSql(" select r.fid from t_inv_appraiseresult r ,t_inv_expertappraise  q where r.finviteprojectid=q.finviteprojectid and q.fid=? ");
		sqlBuilder.addParam(billId.toString());
		if(sqlBuilder.isExist()){
			/***
			 * 已经存在评标结果
			 */
			throw new InviteProjectException(InviteProjectException.HASAPPRAISERESULTCANTUNAUDIT);
		}
		
//		sqlBuilder.clear();
//		sqlBuilder.appendSql(" select r.fid from t_inv_expertappraiseresult er,t_inv_expertappraise q where er.finviteprojectid=q.finviteprojectid and q.fid=? ");
//		sqlBuilder.addParam(billId.toString());
//		if(sqlBuilder.isExist()){
//			/***
//			 * 已经存在技术标评审记录【专家评标结果汇总走工作流】
//			 */
//			throw new InviteProjectException(InviteProjectException.HAS_EXPERT_APPRAISE_RESULT);
//		}
		super._unAudit(ctx, billId);
		
		//当招标立项下所有的专家评标记录都不为已审批状态时，更新招标立项下的专家评标字段
		IObjectPK pk = new ObjectUuidPK(billId.toString());
		ExpertAppraiseInfo epAppInfo = ExpertAppraiseFactory.getLocalInstance(ctx).getExpertAppraiseInfo(pk);
		
		if(epAppInfo.getInviteProject() != null)
		{
			String prjId = epAppInfo.getInviteProject().getId().toString();
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("select id, state, inviteProject where inviteProject = '");
			buffer.append(prjId);
			buffer.append("'");
			buffer.append(" state = '");
			buffer.append(FDCBillStateEnum.AUDITTED_VALUE);
			buffer.append("'");
			
			ExpertAppraiseCollection epCols = ExpertAppraiseFactory.getLocalInstance(ctx).getExpertAppraiseCollection(buffer.toString());
			
			if(epCols.size() <= 1)
			{
//				InviteProjectFactory.getLocalInstance(ctx).setUnExpertAppraise(prjId);
			}
			
		}
		
	}
	public void checkTheCreator(Context ctx,String billId) throws InviteProjectException, BOSException{
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
		sqlBuilder.appendSql("select fid from t_inv_expertappraise where fcreatorid=? and fid=?");
		sqlBuilder.addParam(ContextUtil.getCurrentUserInfo(ctx).getId().toString());
		sqlBuilder.addParam(billId.toString());
		if(!sqlBuilder.isExist()){
			throw new InviteProjectException(InviteProjectException.NOTTHECREATOR);
		}
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		checkTheCreator(ctx,billId.toString());
		super._audit(ctx, billId);
		
		//更新招标立项的状态为评标中
		IObjectPK pk = new ObjectUuidPK(billId.toString());
		ExpertAppraiseInfo info = ExpertAppraiseFactory.getLocalInstance(ctx).getExpertAppraiseInfo(pk);
		IObjectPK InvPrjId = new ObjectUuidPK(info.getInviteProject().getId().toString());
//		InviteProjectFactory.getLocalInstance(ctx).setAppraising(InvPrjId);
		
//		InviteProjectFactory.getLocalInstance(ctx).setExpertAppraise(info.getInviteProject().getId().toString());
		
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		checkTheCreator(ctx,pk.toString());
		super._delete(ctx, pk);
	}
}