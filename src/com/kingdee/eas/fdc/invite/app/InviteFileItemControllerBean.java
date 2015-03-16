package com.kingdee.eas.fdc.invite.app;

import java.sql.SQLException;
import java.util.Date;
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
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.common.AttachmentServerManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.fdc.invite.InviteContentCollection;
import com.kingdee.eas.fdc.invite.InviteContentFactory;
import com.kingdee.eas.fdc.invite.InviteContentInfo;
import com.kingdee.eas.fdc.invite.InviteFileItemEntryInfo;
import com.kingdee.eas.fdc.invite.InviteFileItemFactory;
import com.kingdee.eas.fdc.invite.InviteFileItemInfo;
import com.kingdee.eas.fdc.invite.InviteFileMergeCollection;
import com.kingdee.eas.fdc.invite.InviteFileMergeFactory;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

public class InviteFileItemControllerBean extends AbstractInviteFileItemControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.InviteFileItemControllerBean");


	protected void _aduit(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		InviteFileItemInfo info = new InviteFileItemInfo();
		info.setState(FDCBillStateEnum.AUDITTED);
		info.setId(BOSUuid.read(pk.toString()));
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		info.setAuditTime(new Date());
		info.setIsLastVersion(Boolean.TRUE.booleanValue());
		
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("state");
		sic.add("auditor");
		sic.add("auditTime");
		this.updatePartial(ctx,info,sic);
		
		
		
	}
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		super._audit(ctx, billId);
		InviteFileItemInfo billInfo = InviteFileItemFactory.getLocalInstance(ctx).getInviteFileItemInfo(new ObjectUuidPK(billId));
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_inv_invitefileitem set fislastversion = 1 where fnumber = ? ");
		builder.addParam(billInfo.getNumber());
		builder.execute();
		
		builder.clear();
		builder.appendSql("update t_inv_invitefileitem set fislastversion = 0 where fnumber =? and fid <> ?");
		builder.addParam(billInfo.getNumber());
		builder.addParam(billId.toString());
		builder.execute();
		
	}

	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		InviteFileItemInfo info = (InviteFileItemInfo) model;
		if(info.getVersion() == 0.0){
			info.setVersion(1.0);
		}else{
			double maxVersion = getMaxVersionByNumber(info.getNumber(), ctx);
			if(info.getVersion() == maxVersion){
				info.setVersion(info.getVersion()+0.1);
			}
		}
		info.setState(FDCBillStateEnum.SAVED);
		return super._save(ctx, info);
	}
	
   protected double  getMaxVersionByNumber(String fnumber,Context ctx) {
	  double version = 0.0;
	  FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
	  builder.appendSql("select fversion from t_inv_invitefileitem where fnumber = ? and fisLastVersion = 1");
	  builder.addParam(fnumber);
	  IRowSet rs;
	try {
		rs = builder.executeQuery();
		while(rs.next()){
			  version = rs.getDouble(1);
		  }
	} catch (BOSException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  return version;
   }
	
   protected IObjectPK _submit(Context ctx, IObjectValue model)
		throws BOSException, EASBizException {
	// TODO Auto-generated method stub
	   InviteFileItemInfo info = (InviteFileItemInfo) model;
		if(info.getVersion() == 0.0){
			info.setVersion(1.0);
		}else{
			double maxVersion = getMaxVersionByNumber(info.getNumber(), ctx);
			if(info.getVersion() == maxVersion){
				info.setVersion(info.getVersion()+0.1);
			}
		}
		return super._submit(ctx, info);
}
	
	protected void _unAduit(Context ctx, IObjectValue pk) throws BOSException,
			EASBizException {
	
		
		InviteFileItemInfo info = new InviteFileItemInfo();
		info.setState(FDCBillStateEnum.SUBMITTED);
		info.setId(BOSUuid.read(pk.toString()));
		info.setAuditor(null);
		info.setAuditTime(null);
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("state");
		sic.add("auditor");
		sic.add("auditTime");
		this.updatePartial(ctx,info,sic);
	}
	
	
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		super._unAudit(ctx, billId);
		InviteFileItemInfo billInfo = InviteFileItemFactory.getLocalInstance(ctx).getInviteFileItemInfo(new ObjectUuidPK(billId));
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_inv_invitefileitem set fislastversion = 1 where fnumber = ? and fstate = '4AUDITTED' and fversion =(select max(fversion) from t_inv_invitefileitem where fnumber = ? and fstate='4AUDITTED') ");
		builder.addParam(billInfo.getNumber());
		builder.addParam(billInfo.getNumber());
		builder.execute();
		
		builder.clear();
		builder.appendSql("update t_inv_invitefileitem set fislastversion = 0 where  fid = ?");
		builder.addParam(billId.toString());
		builder.execute();
	}
	/**
	 * 重载删除对应的附件
	 */
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
		
		//删除标书文件
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("parent", pk.toString());
		InviteContentFactory.getLocalInstance(ctx).delete(filter);
		InviteFileItemInfo info = InviteFileItemFactory.getLocalInstance(ctx).getInviteFileItemInfo(pk);
//		builder.clear();
//		builder.addBatch("update t_inv_inviteFileItem set fisLastVersion = 1 where fid = (select fid from t_inv_inviteFileItem where fnumber = ?  and  fversion = (select max(fversion) from t_inv_invitefileItem where fnumber = ?))");
//		builder.addParam(info.getNumber());
//		builder.addParam(info.getNumber());
//		builder.execute();
		super._delete(ctx, pk);
	}
	
	 protected void checkBill(Context ctx, IObjectValue model)
		throws BOSException, EASBizException {
 	//重写检查单据方法  修订时能够保存提交    不报名称和编码可以重复 的错误 
		FDCBillInfo FDCBillInfo = ((FDCBillInfo) model);
		InviteFileItemInfo info=(InviteFileItemInfo) model;
		if(info.getVersion() == 1.0){
			checkNumberDup(ctx, FDCBillInfo);
			checkNameDup(ctx, FDCBillInfo);
		}
	
	}
//	private void checkNumberDup(Context ctx, FDCBillInfo billInfo)	throws BOSException, EASBizException {
//		if(!isUseNumber()) return;
//        FilterInfo filter = new FilterInfo();
//
//        filter.getFilterItems().add(new FilterItemInfo("number", billInfo.getNumber()));
//        filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));		
//        filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit().getId()));
//		if (billInfo.getId() != null) {
//			filter.getFilterItems().add(new FilterItemInfo("id", billInfo.getId().toString(),CompareType.NOTEQUALS));
//		}
//
//		if (_exists(ctx, filter)) {
//			throw new ContractException(ContractException.NUMBER_DUP);
//		}
//}

	protected IObjectValue _getAllReversionData(Context ctx, BOSUuid billId)
			throws BOSException, FDCInviteException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("creator.*"));
		sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		sic.add(new SelectorItemInfo("auditor.*"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("fileItemType"));
		sic.add(new SelectorItemInfo("respDept.*"));
		sic.add(new SelectorItemInfo("inviteProject.*"));
		sic.add(new SelectorItemInfo("inviteProject.inputedAmount"));
		sic.add(new SelectorItemInfo("inviteProject.project.id"));
		sic.add(new SelectorItemInfo("inviteProject.project.number"));
		sic.add(new SelectorItemInfo("inviteProject.project.name"));
		sic.add(new SelectorItemInfo("inviteProject.inviteMode.*"));
		sic.add(new SelectorItemInfo("inviteProject.orgUnit.*"));
		sic.add(new SelectorItemInfo("itemEntry.*"));
		
		
		sic.add(new SelectorItemInfo("fileTemplate.*"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("lastUpdateTime"));
		sic.add(new SelectorItemInfo("number"));
		
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("orgUnit.number"));
		sic.add(new SelectorItemInfo("orgUnit.name"));
	
		sic.add(new SelectorItemInfo("version"));
		sic.add(new SelectorItemInfo("isLastVersion"));
		InviteFileItemInfo info = null;
		try {
			 info = InviteFileItemFactory.getLocalInstance(ctx).getInviteFileItemInfo(new ObjectUuidPK(billId),sic);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder .appendSql("select max(fversion) from t_inv_invitefileitem where fnumber = ?");
		builder.addParam(info.getNumber());
		IRowSet rs = builder.executeQuery();
		double version = 0.0;
		try {
			while(rs.next()){
				version = rs.getDouble(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!info.isIsLastVersion() || !info.getState().equals(FDCBillStateEnum.AUDITTED) || info.getVersion() < version){
//			FDCMsgBox.showError("只能修订最终版本！");
			throw new FDCInviteException(FDCInviteException.CANNOTREVERSIONFINAL);
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",info.getInviteProject().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		view.setFilter(filter);
		InviteFileMergeCollection cols = InviteFileMergeFactory.getLocalInstance(ctx).getInviteFileMergeCollection(view);
		if(cols.size()>0){
			
				throw new FDCInviteException(FDCInviteException.ALREADY_DO_MERGER);
			
		}
		
		info.setId(BOSUuid.create(info.getBOSType()));
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setIsLastVersion(Boolean.FALSE.booleanValue());
		info.setState(FDCBillStateEnum.SAVED);
		InviteFileItemEntryInfo entryInfo =null;
		Map oldIdMap = new HashMap();
	    for(int i=0;i<info.getItemEntry().size();i++){
	    	
	    	entryInfo= info.getItemEntry().get(i); 
	    	BOSUuid oldId = entryInfo.getId();
	    	entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
	    	oldIdMap.put(oldId.toString(), entryInfo.getId());
	    	entryInfo.setFileItem(info);
	    	if(entryInfo.getParent() != null){
	    		if(oldIdMap.containsKey(entryInfo.getParent().getId().toString())){
		    		entryInfo.getParent().setId((BOSUuid)oldIdMap.get(entryInfo.getParent().getId().toString()));
		    	}
	    	}
	    	
	    }
		
	    //附件的处理
	    AttachmentServerManager asm = AttachmentManagerFactory.getServerManager(ctx);
	    try {
			asm.copyBizAttachmentFiles(billId.toString(), info.getId().toString());
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//标书文件处理
		String parentId = billId.toString();
		EntityViewInfo contentView = new EntityViewInfo();
		FilterInfo contentFilter = new FilterInfo();
		contentFilter.getFilterItems().add(new FilterItemInfo("parent",parentId));
		contentView.setFilter(contentFilter);
		InviteContentCollection coll = InviteContentFactory.getLocalInstance(ctx).getInviteContentCollection(contentView);
		if(coll.size()>0){
			for(Iterator it = coll.iterator();it.hasNext();){
				InviteContentInfo contentInfo = (InviteContentInfo) it.next();
				contentInfo.setId(BOSUuid.create(contentInfo.getBOSType()));
				contentInfo.setParent(info.getId().toString());
				try {
					InviteContentFactory.getLocalInstance(ctx).addnew(contentInfo);
				} catch (EASBizException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		try {
			_save(ctx,info);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}    
	
	
}