package com.kingdee.eas.fdc.invite.app;

import java.sql.SQLException;
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
import com.kingdee.eas.base.attachment.AttachmentCollection;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.InviteContentCollection;
import com.kingdee.eas.fdc.invite.InviteContentInfo;
import com.kingdee.eas.fdc.invite.InviteFileItemCollection;
import com.kingdee.eas.fdc.invite.InviteFileItemFactory;
import com.kingdee.eas.fdc.invite.InviteFileItemInfo;
import com.kingdee.eas.fdc.invite.InviteFileItemTypeEnum;
import com.kingdee.eas.fdc.invite.InviteFileMergeFactory;
import com.kingdee.eas.fdc.invite.InviteFileMergeInfo;
import com.kingdee.eas.fdc.invite.InviteProjectCollection;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteProjectStateEnum;
import com.kingdee.eas.fdc.invite.NewListingCollection;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

public class InviteFileMergeControllerBean extends AbstractInviteFileMergeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.InviteFileMergeControllerBean");
    protected void _audit(Context ctx, IObjectPK pk)throws BOSException, EASBizException
    {
    	InviteFileMergeInfo info = new InviteFileMergeInfo();
		info.setState(FDCBillStateEnum.AUDITTED);
		info.setId(BOSUuid.read(pk.toString()));
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("state");
		this.updatePartial(ctx,info,sic);
    }
    protected void _unAudit(Context ctx, IObjectPK pk)throws BOSException, EASBizException
    {
    	InviteFileMergeInfo info = new InviteFileMergeInfo();
		info.setState(FDCBillStateEnum.SUBMITTED);
		info.setId(BOSUuid.read(pk.toString()));
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("state");
		this.updatePartial(ctx,info,sic);
    }
    protected IObjectPK _addnew(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	// PBG048446,提交失败不应该更新，改在服务端当新增单据时更新
    	IObjectPK pk =  super._addnew(ctx, model);
    	InviteFileMergeInfo info = (InviteFileMergeInfo)model;
    	
    	if (info.getInviteProject() != null) {
			// 设置招标立项为文件合成状态
			IObjectPK inviteProjectId = new ObjectUuidPK(info.getInviteProject().getId()
					.toString());
//			try {
//				InviteProjectFactory.getLocalInstance(ctx).setFileMakeing(inviteProjectId);
//			} catch (EASBizException e) {
//				e.printStackTrace();
//			} catch (BOSException e) {
//				e.printStackTrace();
//			}
		}
    	return pk;
    }
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
    		EASBizException {
    	
    	//获取附件id
    	Set attidSet = new HashSet();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		
		builder.appendSql("select att.fid as id, att.fname_l2 as name, bo.fboid  from  T_BAS_Attachment att RIGHT OUTER JOIN T_BAS_BoAttchAsso bo on bo.fattachmentid = att.fid where att.fisShared=0 and ");
		builder.appendParam("bo.fboid", pk.toString());
		
		IRowSet rowSet = builder.executeQuery();
		try {
			while (rowSet.next()) {
				if(rowSet.getString("id") != null)
					attidSet.add(rowSet.getString("id"));
			}
		} catch (UuidException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		IObjectPK[] pkList = new IObjectPK[attidSet.size()];
		Iterator iter = attidSet.iterator();
		int i = 0;
		while(iter.hasNext())
		{
			IObjectPK tmpPK = new ObjectUuidPK(iter.next().toString());
			pkList[i] = tmpPK;
			i++;
		}
		
		AttachmentFactory.getLocalInstance(ctx).delete(pkList);
		//更新招标立项状态
		InviteFileMergeInfo fileInfo = InviteFileMergeFactory.getLocalInstance(ctx).getInviteFileMergeInfo(pk);
		if(fileInfo.getInviteProject() != null)
    	{
    		//设置为审批状态
    		IObjectPK tmpPK = new ObjectUuidPK(fileInfo.getInviteProject().getId().toString());
    		InviteProjectInfo info = new InviteProjectInfo();
    		
    		info.setId(BOSUuid.read(tmpPK.toString()));
    		info.setInviteProjectState(InviteProjectStateEnum.AUDITTED);
    		SelectorItemCollection selector = new SelectorItemCollection();
    		selector.add("inviteProjectState");
    		
    		InviteProjectFactory.getLocalInstance(ctx).updatePartial(info, selector);
    	}
		
    	super._delete(ctx, pk);
    }
	protected Map _getInviteProjectAllFiles(Context ctx, String value)throws BOSException, EASBizException {
		Map inviteFiles = new HashMap();
		
		
		Map attachment = new HashMap();

		
		Map contentMap = new HashMap();
		
        StringBuffer str =new StringBuffer();
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");		
		FilterInfo pfilter= new FilterInfo();
		pfilter.getFilterItems().add(new FilterItemInfo("parent.id",value));
		view.setFilter(pfilter);
		InviteProjectCollection projectInfos = InviteProjectFactory.getLocalInstance(ctx).getInviteProjectCollection(view);
		Set idSet = new HashSet();
		idSet.add(value);
		if(projectInfos.size()>0){
			for(Iterator it=projectInfos.iterator();it.hasNext();){
				InviteProjectInfo info = (InviteProjectInfo) it.next();
				idSet.add(info.getId().toString());
			}
		}
		

		
		
		FDCSQLBuilder b = new FDCSQLBuilder(ctx);
//		b.appendSql("select listing.fid,inviteproject.fid,parent.fid,orgunit.fid,orgunit.fname_l2,creator.fid,creator.fname_l2,listing.fcreatetime,auditor.fid,auditor.fname_l2,listing.faudittime,listing.fislastversion,listing.fstate from t_inv_newlisting listing left join t_inv_inviteProject inviteproject on listing.FInviteProjectID = inviteproject.fid" +
//				" left join T_ORG_BaseUnit orgunit on listing.forgunitid = orgunit.fid left join t_inv_inviteproject parent on inviteproject.FParentID = parent.fid left join t_pm_user auditor on listing.fauditorid = auditor.fid" +
//				" left join t_pm_user creator on listing.fcreatorid = creator.fid where " );
		
		Set listingIdSet = new HashSet();
		b.appendSql("select fid from t_inv_newlisting  where ");
		b.appendParam("fisLastversion", new Integer(1));
		b.appendFilter("finviteProjectId", idSet, CompareType.INCLUDE);
		IRowSet rs = b.executeQuery();
		try {
			while(rs.next()){
			   listingIdSet.add(rs.getString(1));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		//招标清单
		EntityViewInfo listView = new EntityViewInfo();
		
		listView.getSelector().add(new SelectorItemInfo("id"));
		listView.getSelector().add(new SelectorItemInfo("inviteProject"));
		listView.getSelector().add(new SelectorItemInfo("inviteProject.id"));
		listView.getSelector().add(new SelectorItemInfo("inviteProject.parent.id"));
		listView.getSelector().add(new SelectorItemInfo("orgUnit.id"));
		listView.getSelector().add(new SelectorItemInfo("orgUnit.name"));
		
		listView.getSelector().add(new SelectorItemInfo("creator.id"));
		listView.getSelector().add(new SelectorItemInfo("creator.name"));
		listView.getSelector().add(new SelectorItemInfo("createTime"));
		
		listView.getSelector().add(new SelectorItemInfo("auditor.id"));
		listView.getSelector().add(new SelectorItemInfo("auditor.name"));
		listView.getSelector().add(new SelectorItemInfo("auditTime"));
		listView.getSelector().add(new SelectorItemInfo("isLastVersion"));
		listView.getSelector().add(new SelectorItemInfo("state"));
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",listingIdSet,CompareType.INCLUDE));
		
		listView.setFilter(filter);
		NewListingCollection listCols = NewListingFactory.getLocalInstance(ctx).getNewListingCollection(listView);
		
		

		Iterator listIter = listCols.iterator();
		NewListingCollection listColsCopy = new NewListingCollection();
		while(listIter.hasNext())
		{
			NewListingInfo listInfo = (NewListingInfo)listIter.next();
			
			if(FDCBillStateEnum.AUDITTED.equals(listInfo.getState()))
			{
				listColsCopy.add(listInfo);
//				inviteFiles.put(InviteFileItemTypeEnum.INVITELISTOFITEM_VALUE, listInfo);
				
				AttachmentCollection tmpAttCols = new AttachmentCollection();
				
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				
				builder.appendSql("select att.fid as id, att.fname_l2 as name, bo.fboid  from  T_BAS_Attachment att RIGHT OUTER JOIN T_BAS_BoAttchAsso bo on bo.fattachmentid = att.fid where");
				builder.appendParam("bo.fboid", listInfo.getId().toString());
				
				IRowSet rowSet = builder.executeQuery();
				try {
					while (rowSet.next()) {
						if(rowSet.getString("id") != null)
						{
							AttachmentInfo tmpAttInfo = new AttachmentInfo();
							tmpAttInfo.setId(BOSUuid.read(rowSet.getString("id")));
							tmpAttInfo.setName(rowSet.getString("name"));
							
							tmpAttCols.add(tmpAttInfo);
						}
						
					}
				} catch (UuidException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				
				attachment.put(InviteFileItemTypeEnum.INVITELISTOFITEM_VALUE+listInfo.getId().toString(), tmpAttCols);
				
			}
		}
		inviteFiles.put(InviteFileItemTypeEnum.INVITELISTOFITEM_VALUE, listColsCopy);
		
		EntityViewInfo fileItemView = new EntityViewInfo();
		fileItemView.getSelector().add(new SelectorItemInfo("id"));
		fileItemView.getSelector().add(new SelectorItemInfo("inviteProject"));
		
		fileItemView.getSelector().add(new SelectorItemInfo("respDept.id"));
		fileItemView.getSelector().add(new SelectorItemInfo("respDept.name"));
		
		fileItemView.getSelector().add(new SelectorItemInfo("creator.id"));
		fileItemView.getSelector().add(new SelectorItemInfo("creator.name"));
		fileItemView.getSelector().add(new SelectorItemInfo("createTime"));
		
		fileItemView.getSelector().add(new SelectorItemInfo("auditor.id"));
		fileItemView.getSelector().add(new SelectorItemInfo("auditor.name"));
		fileItemView.getSelector().add(new SelectorItemInfo("auditTime"));
		fileItemView.getSelector().add(new SelectorItemInfo("state"));
		
		fileItemView.getSelector().add(new SelectorItemInfo("fileItemType"));
		
		
		b.clear();
		Set fileIdSet = new HashSet();
		b.appendSql("select fid from t_inv_invitefileitem  where ");
		b.appendParam("fisLastversion", new Integer(1));
		b.appendFilter("finviteProjectId", idSet, CompareType.INCLUDE);
		IRowSet rs1 = b.executeQuery();
		try {
			while(rs1.next()){
				fileIdSet.add(rs1.getString(1));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		FilterInfo fileItemFilter = new FilterInfo();
		fileItemFilter.getFilterItems().add(new FilterItemInfo("id", fileIdSet,CompareType.INCLUDE));
//		FilterInfo filter2 = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("isLastVersion",Boolean.TRUE));
	 
		fileItemView.setFilter(fileItemFilter);
//		fileItemView.getFilter().mergeFilter(filter2, "and");
		InviteFileItemCollection itemCols = InviteFileItemFactory.getLocalInstance(ctx).getInviteFileItemCollection(fileItemView);
		Iterator iter = itemCols.iterator();
		while(iter.hasNext())
		{
			InviteFileItemInfo itemInfo = (InviteFileItemInfo)iter.next();
			if(FDCBillStateEnum.AUDITTED.equals(itemInfo.getState()))
			{
				inviteFiles.put(itemInfo.getFileItemType().getValue(), itemInfo);
				
				AttachmentCollection tmpAttCols = new AttachmentCollection();
				
				InviteContentCollection contentCols = new InviteContentCollection();
				
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				
				builder.appendSql("select att.fid as id, att.fname_l2 as name, bo.fboid  from  T_BAS_Attachment att RIGHT OUTER JOIN T_BAS_BoAttchAsso bo on bo.fattachmentid = att.fid where");
				builder.appendParam("bo.fboid", itemInfo.getId().toString());
				
				IRowSet rowSet = builder.executeQuery();
				try {
					while (rowSet.next()) {
						if(rowSet.getString("id") != null)
						{
							AttachmentInfo tmpAttInfo = new AttachmentInfo();
							tmpAttInfo.setId(BOSUuid.read(rowSet.getString("id")));
							tmpAttInfo.setName(rowSet.getString("name"));

							tmpAttCols.add(tmpAttInfo);
						}
					}
				} catch (UuidException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				attachment.put(itemInfo.getFileItemType().getValue(), tmpAttCols);
				
				builder.clear();
				builder.appendSql("select fid,ffilename from t_inv_invitecontent where");
				builder.appendParam("fparent",itemInfo.getId().toString());
				
				rowSet = builder.executeQuery();
				try {
					while(rowSet.next()){
						if(rowSet.getString("fid") != null){
							
							InviteContentInfo contentInfo = new InviteContentInfo();
							contentInfo.setId(BOSUuid.read(rowSet.getString("fid")));
							contentInfo.setFileName(rowSet.getString("ffilename"));
							contentCols.add(contentInfo);
						}
						
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				contentMap.put(itemInfo.getFileItemType().getValue(), contentCols);
			}
		}
		
		inviteFiles.put("Attachment", attachment);
		inviteFiles.put("Content", contentMap);
		
		return inviteFiles;
	}
	private void setFileMasking(Context ctx,IObjectValue model) throws BOSException, EASBizException{
		if (((InviteFileMergeInfo)model).getInviteProject() != null) {
			// 设置招标立项为文件合成状态
			IObjectPK pk = new ObjectUuidPK(((InviteFileMergeInfo)model).getInviteProject().getId());
			
//			InviteProjectFactory.getLocalInstance(ctx).setFileMakeing(pk);
		}
	}
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		super._save(ctx, pk, model);
		setFileMasking(ctx,model);
	}
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		IObjectPK pk = super._save(ctx, model);
		setFileMasking(ctx,model);
		return pk;
	}
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		super._submit(ctx, pk, model);
		setFileMasking(ctx,model);
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		IObjectPK pk = super._submit(ctx, model);
		setFileMasking(ctx,model);
		return pk;
	}
	protected void _setStatusSaved(Context ctx, String billId)
			throws BOSException, EASBizException {
		InviteFileMergeInfo info = new InviteFileMergeInfo();
		info.setState(FDCBillStateEnum.SAVED);
		info.setId(BOSUuid.read(billId));
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("state");
		this.updatePartial(ctx,info,sic);
	}
}