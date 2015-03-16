package com.kingdee.eas.fdc.invite.app;

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
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.invite.AcceptanceLetterCollection;
import com.kingdee.eas.fdc.invite.AcceptanceLetterFactory;
import com.kingdee.eas.fdc.invite.AcceptanceLetterInfo;
import com.kingdee.eas.fdc.invite.AppraiseResultCollection;
import com.kingdee.eas.fdc.invite.AppraiseResultFactory;
import com.kingdee.eas.fdc.invite.AppraiseResultInfo;
import com.kingdee.eas.fdc.invite.ExpertQualifyCollection;
import com.kingdee.eas.fdc.invite.ExpertQualifyFactory;
import com.kingdee.eas.fdc.invite.ExpertQualifyInfo;
import com.kingdee.eas.fdc.invite.InviteAllInformationFactory;
import com.kingdee.eas.fdc.invite.InviteAllInformationInfo;
import com.kingdee.eas.fdc.invite.InviteFileMergeCollection;
import com.kingdee.eas.fdc.invite.InviteFileMergeFactory;
import com.kingdee.eas.fdc.invite.InviteFileMergeInfo;
import com.kingdee.eas.fdc.invite.InviteProjectCollection;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyCollection;
import com.kingdee.eas.fdc.invite.SupplierQualifyFactory;
import com.kingdee.eas.fdc.invite.SupplierQualifyInfo;

public class InviteAllInformationControllerBean extends AbstractInviteAllInformationControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.InviteAllInformationControllerBean");
    
    protected IObjectPK _submit(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	IObjectPK pk = super._submit(ctx, model);
    	InviteAllInformationInfo allInformationInfo = (InviteAllInformationInfo)model;
    	
    	EntityViewInfo view = new EntityViewInfo();
    	view.getSelector().add("id");
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("parent",allInformationInfo.getInviteProject().getId().toString()));
    	
    	InviteProjectCollection projects= InviteProjectFactory.getLocalInstance(ctx).getInviteProjectCollection(view);
    	
    	Set idSet = new HashSet();
    	idSet.add(allInformationInfo.getInviteProject().getId().toString());
    	if(projects!=null&&projects.size()>0){
    		for(int i=0;i<projects.size();i++)
    			idSet.add(projects.get(i).getId().toString());
    	}

    	
    	if(allInformationInfo.getInviteFileMerge() != null)
    	{
    		InviteFileMergeFactory.getLocalInstance(ctx).setSubmitStatus(allInformationInfo.getInviteFileMerge().getId());
    	}
    	
    	view.getFilter().getFilterItems().clear();
    	view.getFilter().getFilterItems().add(new FilterItemInfo("inviteProject",idSet,CompareType.INCLUDE));
    	SupplierQualifyCollection sc = SupplierQualifyFactory.getLocalInstance(ctx).getSupplierQualifyCollection(view);
    	for(int i=0;i<sc.size();i++){
    		SupplierQualifyFactory.getLocalInstance(ctx).setSubmitStatus(sc.get(i).getId());
    	}
    	
//    	if(allInformationInfo.getSupplierQualify() != null)
//    	{
//    		SupplierQualifyFactory.getLocalInstance(ctx).setSubmitStatus(allInformationInfo.getSupplierQualify().getId());
//    	}
    	ExpertQualifyCollection ec = ExpertQualifyFactory.getLocalInstance(ctx).getExpertQualifyCollection(view);
    	for(int i=0;i<ec.size();i++){
    		ExpertQualifyFactory.getLocalInstance(ctx).setSubmitStatus(ec.get(i).getId());
    	}
//    	if(allInformationInfo.getExpertQualify() != null)
//    	{
//    		ExpertQualifyFactory.getLocalInstance(ctx).setSubmitStatus(allInformationInfo.getExpertQualify().getId());
//    	}
    	
    	return pk;
    }
    
    protected void _setSubmitStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		super._setSubmitStatus(ctx, billId);
		InviteAllInformationInfo allInformationInfo = InviteAllInformationFactory.getLocalInstance(ctx).getInviteAllInformationInfo(new ObjectUuidPK(billId));
		_submit(ctx,allInformationInfo);
	}

	protected Map _fetchInitData(Context ctx, Map paramMap)
			throws BOSException, EASBizException {
		Map returnMap = new HashMap();
		
		String prjId = null;
		String prjName = null;
		if(paramMap.containsKey("billId")){
			String billId = String.valueOf(paramMap.get("billId"));
			InviteAllInformationInfo billInfo = InviteAllInformationFactory.getLocalInstance(ctx).getInviteAllInformationInfo(new ObjectUuidPK(billId));
			prjId = billInfo.getInviteProject().getId().toString();
		}
		else{
			prjId = String.valueOf(paramMap.get("prjId"));
			prjName = String.valueOf(paramMap.get("prjName"));
		}
		
		returnMap.put("prjId", prjId);
		returnMap.put("prjName", prjName);
		
		InviteProjectInfo projectInfo = InviteProjectFactory.getLocalInstance(ctx).getInviteProjectInfo(new ObjectUuidPK(prjId));
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("select id ,inviteProject.id where inviteProject = '");
		buffer.append(prjId);
		buffer.append("'");
		
		InviteFileMergeCollection fileMergcols = InviteFileMergeFactory.getLocalInstance(ctx).getInviteFileMergeCollection(buffer.toString());
		
		if(fileMergcols.size() > 0)
		{
			InviteFileMergeInfo info = fileMergcols.get(0);
			returnMap.put("InviteFileMergeID", info.getId().toString());
		}
		
		Set AppraiseTemplateID = new HashSet();
		List leafProjectID = new ArrayList();
		if(projectInfo.isIsLeaf()){
			if(projectInfo.getAppraiseTemplate()!=null){
				returnMap.put("AppraiseTemplateID"+projectInfo.getId().toString(),projectInfo.getAppraiseTemplate().getId().toString());
			}
		}else{
			buffer = new StringBuffer();
			buffer.append("select id,appraiseTemplate.id where parent = '");
			buffer.append(prjId);
			buffer.append("'");
			buffer.append(" order by number");
			
			InviteProjectCollection leafProCols = InviteProjectFactory.getLocalInstance(ctx).getInviteProjectCollection(buffer.toString());
			for(Iterator it=leafProCols.iterator();it.hasNext();){
				InviteProjectInfo leafProject = (InviteProjectInfo)it.next();
				if(leafProject!=null){
					leafProjectID.add(leafProject.getId().toString());
				}
				if(leafProject!=null&&leafProject.getAppraiseTemplate()!=null){
					returnMap.put("AppraiseTemplateID"+leafProject.getId().toString(), leafProject.getAppraiseTemplate().getId().toString());
				}
			}
			if(leafProjectID.size()>0){
				returnMap.put("leafProjectID", leafProjectID);
			}
		}
		
		FilterInfo filter= new FilterInfo();
		if(!projectInfo.isIsLeaf())
			filter.getFilterItems().add(new FilterItemInfo("inviteProject",FDCHelper.list2Set(leafProjectID),CompareType.INCLUDE));
		else
			filter.getFilterItems().add(new FilterItemInfo("inviteProject",prjId,CompareType.EQUALS));
		
		/*
		 * 不能再添加过滤条件，因为Filter在3级（含）以上组合关系查询会报中断，改为先查出来，再根据条件billInfo.isIsLastVersion()过滤。
		 * 因此注释下面一句，	Added by Owen_wen 2010-11-26 
		 * 
		 */
		
//		filter.getFilterItems().add(new FilterItemInfo("isLastVersion", Boolean.TRUE)); 
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("inviteProject");
		view.getSelector().add("isLastVersion");
		view.setFilter(filter);
		IObjectCollection listCols = NewListingFactory.getLocalInstance(ctx).getNewListingCollection(view);
		
		if(listCols.size() > 0)
		{
			for(Iterator it = listCols.iterator();it.hasNext();){
				NewListingInfo billInfo = (NewListingInfo)it.next();
				if(billInfo.isIsLastVersion())
					returnMap.put("NewListingID"+billInfo.getInviteProject().getId().toString(), billInfo.getId().toString());
			}
		}

		if(!projectInfo.isIsLeaf()){
			buffer = new StringBuffer();
			buffer.append("select id,inviteProject.id where inviteProject.parent = '");
			buffer.append(prjId);
			buffer.append("'");
		}
		
		IObjectCollection suppCols = SupplierQualifyFactory.getLocalInstance(ctx).getSupplierQualifyCollection(buffer.toString());
		
		if(suppCols.size() > 0)
		{
			for(Iterator it = suppCols.iterator();it.hasNext();){
				SupplierQualifyInfo billInfo = (SupplierQualifyInfo)it.next();
				returnMap.put("SupplierQualifyID"+billInfo.getInviteProject().getId().toString(), billInfo.getId().toString());
			}
		}
		
		ExpertQualifyCollection expertCols = ExpertQualifyFactory.getLocalInstance(ctx).getExpertQualifyCollection(buffer.toString());
		
		if(expertCols.size() > 0)
		{
			for(Iterator it = expertCols.iterator();it.hasNext();){
				ExpertQualifyInfo billInfo = (ExpertQualifyInfo)it.next();
				returnMap.put("ExpertQualifyID"+billInfo.getInviteProject().getId().toString(), billInfo.getId().toString());
			}
		}

		// R110831-0237：招标执行信息中看不到评标结果报审信息
		// 常旭说改为都可以看到
		// edit by emanon
		// if(projectInfo.isIsExpertAppraise())
		// {
			AppraiseResultCollection appRecols = AppraiseResultFactory.getLocalInstance(ctx).getAppraiseResultCollection(buffer.toString());
			if(appRecols.size() > 0)
			{
				for(Iterator it = appRecols.iterator();it.hasNext();){
					AppraiseResultInfo billInfo = (AppraiseResultInfo)it.next();
					returnMap.put("AppraiseResultID"+billInfo.getInviteProject().getId().toString(), billInfo.getId().toString());
				}
			}
		// }
		/*
		 * 由于对中标通知书做过修订功能，所以这里要取最终版本
		 */
		AcceptanceLetterCollection accCols = AcceptanceLetterFactory.getLocalInstance(ctx).getAcceptanceLetterCollection(buffer.toString()+"and isLastVersion = 1");
		if(accCols.size() > 0)
		{
			for(Iterator it = accCols.iterator();it.hasNext();){
				AcceptanceLetterInfo billInfo = (AcceptanceLetterInfo)it.next();
				returnMap.put("AcceptanceLetterID"+billInfo.getInviteProject().getId().toString(), billInfo.getId().toString());
			}
		}
				
		return returnMap;
	}

	private Set getIds(IObjectCollection cols) {
		Set idSet = new HashSet();
		for(Iterator it = cols.iterator();it.hasNext();){
			FDCBillInfo billInfo = (FDCBillInfo)it.next();
			String key = billInfo.getId().toString();
			if(billInfo.containsKey("inviteProject")&&billInfo.get("inviteProject")!=null)
			{
				InviteProjectInfo leafPro = (InviteProjectInfo)billInfo.get("inviteProject");
				key = leafPro.getId().toString() + "_" + billInfo.getId().toString();
			}
			idSet.add(key);
		}
		return idSet;
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	super._audit(ctx, billId);
    	IObjectPK pk = new ObjectUuidPK(billId.toString());
    	InviteAllInformationInfo allInformationInfo = InviteAllInformationFactory.getLocalInstance(ctx).getInviteAllInformationInfo(pk);
    	
    	
    	if(allInformationInfo.getInviteFileMerge() != null)
    	{
    		InviteFileMergeFactory.getLocalInstance(ctx).audit(allInformationInfo.getInviteFileMerge().getId());
    	}
    	
    	EntityViewInfo view = new EntityViewInfo();
    	view.getSelector().add("id");
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("parent",allInformationInfo.getInviteProject().getId().toString()));
    	
    	InviteProjectCollection projects= InviteProjectFactory.getLocalInstance(ctx).getInviteProjectCollection(view);
    	
    	Set idSet = new HashSet();
    	idSet.add(allInformationInfo.getInviteProject().getId().toString());
    	if(projects!=null&&projects.size()>0){
    		for(int i=0;i<projects.size();i++)
    			idSet.add(projects.get(i).getId().toString());
    	}

    	
    	view.getFilter().getFilterItems().clear();
    	view.getFilter().getFilterItems().add(new FilterItemInfo("inviteProject",idSet,CompareType.INCLUDE));
    	SupplierQualifyCollection sc = SupplierQualifyFactory.getLocalInstance(ctx).getSupplierQualifyCollection(view);
    	for(int i=0;i<sc.size();i++){
    		SupplierQualifyFactory.getLocalInstance(ctx).audit(sc.get(i).getId());
    	}
    	
    	ExpertQualifyCollection ec = ExpertQualifyFactory.getLocalInstance(ctx).getExpertQualifyCollection(view);
    	for(int i=0;i<ec.size();i++){
    		ExpertQualifyFactory.getLocalInstance(ctx).audit(ec.get(i).getId());
    	}
    	
//    	if(info.getInviteProject() != null && info.getAppraiseTemplate() != null)
//    	{
//    		InviteProjectFactory.getLocalInstance(ctx).setAppraiseTemplate(info.getInviteProject().getId(),info.getAppraiseTemplate());
//    	}
    }
    
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	super._unAudit(ctx, billId);
    	
    	IObjectPK pk = new ObjectUuidPK(billId.toString());
    	InviteAllInformationInfo allInformationInfo = InviteAllInformationFactory.getLocalInstance(ctx).getInviteAllInformationInfo(pk);
    	
    	if(allInformationInfo.getInviteFileMerge() != null)
    	{
    		InviteFileMergeFactory.getLocalInstance(ctx).unAudit(allInformationInfo.getInviteFileMerge().getId());
    	}
    	

    	EntityViewInfo view = new EntityViewInfo();
    	view.getSelector().add("id");
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("parent",allInformationInfo.getInviteProject().getId().toString()));
    	
    	InviteProjectCollection projects= InviteProjectFactory.getLocalInstance(ctx).getInviteProjectCollection(view);
    	
    	Set idSet = new HashSet();
    	idSet.add(allInformationInfo.getInviteProject().getId().toString());
    	if(projects!=null&&projects.size()>0){
    		for(int i=0;i<projects.size();i++)
    			idSet.add(projects.get(i).getId().toString());
    	}

    	
    	view.getFilter().getFilterItems().clear();
    	view.getFilter().getFilterItems().add(new FilterItemInfo("inviteProject",idSet,CompareType.INCLUDE));
    	SupplierQualifyCollection sc = SupplierQualifyFactory.getLocalInstance(ctx).getSupplierQualifyCollection(view);
    	for(int i=0;i<sc.size();i++){
    		SupplierQualifyFactory.getLocalInstance(ctx).unAudit(sc.get(i).getId());
    	}
    	
    	ExpertQualifyCollection ec = ExpertQualifyFactory.getLocalInstance(ctx).getExpertQualifyCollection(view);
    	for(int i=0;i<ec.size();i++){
    		ExpertQualifyFactory.getLocalInstance(ctx).unAudit(ec.get(i).getId());
    	}
    	
    }
    
    protected void _setAudittingStatus(Context ctx, BOSUuid billId)
    		throws BOSException, EASBizException {
    	super._setAudittingStatus(ctx, billId);
    	
    	IObjectPK pk = new ObjectUuidPK(billId.toString());
    	InviteAllInformationInfo allInformationInfo = InviteAllInformationFactory.getLocalInstance(ctx).getInviteAllInformationInfo(pk);
    	
    	if(allInformationInfo.getInviteFileMerge() != null)
    	{
    		InviteFileMergeFactory.getLocalInstance(ctx).setAudittingStatus(allInformationInfo.getInviteFileMerge().getId());
    	}
    	
    	EntityViewInfo view = new EntityViewInfo();
    	view.getSelector().add("id");
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("parent",allInformationInfo.getInviteProject().getId().toString()));
    	
    	InviteProjectCollection projects= InviteProjectFactory.getLocalInstance(ctx).getInviteProjectCollection(view);
    	
    	Set idSet = new HashSet();
    	idSet.add(allInformationInfo.getInviteProject().getId().toString());
    	if(projects!=null&&projects.size()>0){
    		for(int i=0;i<projects.size();i++)
    			idSet.add(projects.get(i).getId().toString());
    	}

    	
    	view.getFilter().getFilterItems().clear();
    	view.getFilter().getFilterItems().add(new FilterItemInfo("inviteProject",idSet,CompareType.INCLUDE));
    	SupplierQualifyCollection sc = SupplierQualifyFactory.getLocalInstance(ctx).getSupplierQualifyCollection(view);
    	for(int i=0;i<sc.size();i++){
    		SupplierQualifyFactory.getLocalInstance(ctx).setAudittingStatus(sc.get(i).getId());
    	}
    	
    	ExpertQualifyCollection ec = ExpertQualifyFactory.getLocalInstance(ctx).getExpertQualifyCollection(view);
    	for(int i=0;i<ec.size();i++){
    		ExpertQualifyFactory.getLocalInstance(ctx).setAudittingStatus(ec.get(i).getId());
    	}
    }
    
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
    		EASBizException {
   
    	InviteAllInformationInfo allInformationInfo = InviteAllInformationFactory.getLocalInstance(ctx).getInviteAllInformationInfo(pk);
    	
    	if(allInformationInfo.getInviteFileMerge() != null)
    	{
    		//设置为保存状态
    		InviteFileMergeFactory.getLocalInstance(ctx).setStatusSaved(allInformationInfo.getInviteFileMerge().getId().toString());
    	}
    	
    	EntityViewInfo view = new EntityViewInfo();
    	view.getSelector().add("id");
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("parent",allInformationInfo.getInviteProject().getId().toString()));
    	
    	InviteProjectCollection projects= InviteProjectFactory.getLocalInstance(ctx).getInviteProjectCollection(view);
    	
    	Set idSet = new HashSet();
    	idSet.add(allInformationInfo.getInviteProject().getId().toString());
    	if(projects!=null&&projects.size()>0){
    		for(int i=0;i<projects.size();i++)
    			idSet.add(projects.get(i).getId().toString());
    	}

    	
    	view.getFilter().getFilterItems().clear();
    	view.getFilter().getFilterItems().add(new FilterItemInfo("inviteProject",idSet,CompareType.INCLUDE));
    	SupplierQualifyCollection sc = SupplierQualifyFactory.getLocalInstance(ctx).getSupplierQualifyCollection(view);
    	for(int i=0;i<sc.size();i++){
//    		SupplierQualifyFactory.getLocalInstance(ctx).setStatusSaved(sc.get(i).getId().toString());
    	}
    	
    	ExpertQualifyCollection ec = ExpertQualifyFactory.getLocalInstance(ctx).getExpertQualifyCollection(view);
    	for(int i=0;i<ec.size();i++){
    		ExpertQualifyFactory.getLocalInstance(ctx).setStatus(ec.get(i).getId().toString());
    	}
    	
    	super._delete(ctx, pk);
    }
}