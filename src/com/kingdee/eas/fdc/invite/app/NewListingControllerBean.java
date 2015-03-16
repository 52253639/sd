package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;

import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.fdc.invite.InviteFileItemInfo;
import com.kingdee.eas.fdc.invite.InviteFileMergeCollection;
import com.kingdee.eas.fdc.invite.InviteFileMergeFactory;
import com.kingdee.eas.fdc.invite.InviteProjectException;
import com.kingdee.eas.fdc.invite.ListingPageSumEntryInfo;
import com.kingdee.eas.fdc.invite.NewListingColumnCollection;
import com.kingdee.eas.fdc.invite.NewListingColumnFactory;
import com.kingdee.eas.fdc.invite.NewListingColumnInfo;
import com.kingdee.eas.fdc.invite.NewListingEntryCollection;
import com.kingdee.eas.fdc.invite.NewListingEntryFactory;
import com.kingdee.eas.fdc.invite.NewListingEntryInfo;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.eas.fdc.invite.NewListingPageCollection;
import com.kingdee.eas.fdc.invite.NewListingPageFactory;
import com.kingdee.eas.fdc.invite.NewListingPageInfo;
import com.kingdee.eas.fdc.invite.NewListingValueCollection;
import com.kingdee.eas.fdc.invite.NewListingValueFactory;
import com.kingdee.eas.fdc.invite.NewListingValueInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.common.AttachmentServerManager;
import com.kingdee.eas.base.security.util.SystemUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.invite.NewListingCollection;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

public class NewListingControllerBean extends AbstractNewListingControllerBean
{
	private static final long serialVersionUID = 8824444838916670346L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.NewListingControllerBean");
    protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		return new HashMap();
	}
	protected IObjectValue _getNewListingAllValue(Context ctx, String listingId) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		NewListingInfo listing = null;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		/***
		 * 获取清单的基础信息
		 */
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("inviteType.*");
		sic.add("inviteProject.*");
		sic.add("orgUnit.*");
		sic.add("oriOrg.*");
		sic.add("curProject.*");
		sic.add("currency.*");
		listing = this.getNewListingInfo(ctx,new ObjectUuidPK(BOSUuid.read(listingId)),sic);
		
		/****
		 *获取清单的页签
		 * 
		 */
		view = new EntityViewInfo();
		filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", listing.getId().toString()));
		sic = view.getSelector();
		sic.add("*");
		sic.add("pageHead.*");
		view.getSorter().add(new SorterItemInfo("seq"));
		NewListingPageCollection pages = NewListingPageFactory
				.getLocalInstance(ctx).getNewListingPageCollection(view);
		listing.getPages().clear();
		listing.getPages().addCollection(pages);
		/*****
		 * 将清单的页签组合成一个map
		 * 便于后面批量取数据
		 */
		Map pagesMap = new HashMap();
		for (int i = 0; i < listing.getPages().size(); i++) {
			NewListingPageInfo page = listing.getPages().get(i);
			page.getColumns().clear();
			page.getEntrys().clear();
			pagesMap.put(page.getId().toString(),page);
		}
		if(pagesMap.size()>0){
			Set pagesIdSet = pagesMap.keySet();
			
			/*****
			 * 批量获取清单中的列信息
			 */
			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("page.id", pagesIdSet,CompareType.INCLUDE));
			sic = view.getSelector();
			sic.add("*");
			sic.add("headColumn.*");
			sic.add("headColumn.parent.*");
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListingColumnCollection columns = null;
			columns = NewListingColumnFactory.getLocalInstance(ctx).getNewListingColumnCollection(view);
			/***
			 * 将列信息插入page中
			 */
			for(Iterator it=columns.iterator();it.hasNext();){
				NewListingColumnInfo columnInfo = (NewListingColumnInfo)it.next();
				if(pagesMap.containsKey(columnInfo.getPage().getId().toString())){
					NewListingPageInfo page = (NewListingPageInfo)pagesMap.get(columnInfo.getPage().getId().toString());
					page.getColumns().add(columnInfo);
				}
			}
			
			/****
			 * 获取清单中的行信息
			 */
			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("item.*");
			filter.getFilterItems().add(
					new FilterItemInfo("head.id", pagesIdSet,CompareType.INCLUDE));
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListingEntryCollection entrys = null;
			logger.info("begin get entry>>:"+String.valueOf(System.currentTimeMillis()));
			entrys = NewListingEntryFactory.getLocalInstance(ctx)
					.getNewListingEntryCollection(view);
			logger.info("end get entry>>:"+String.valueOf(System.currentTimeMillis()));
			/****
			 * 将行信息插入清单中
			 */
			Map entrysMap = new HashMap();
			for(Iterator it = entrys.iterator();it.hasNext();){
				NewListingEntryInfo entryInfo = (NewListingEntryInfo)it.next();
				entrysMap.put(entryInfo.getId().toString(), entryInfo);
				entryInfo.getValues().clear();
				if(pagesMap.containsKey(entryInfo.getHead().getId().toString())){
					NewListingPageInfo page = (NewListingPageInfo)pagesMap.get(entryInfo.getHead().getId().toString());
					page.getEntrys().add(entryInfo);
				}
			}
			
			/****
			 * 获取清单中的值信息
			 */
			NewListingValueCollection valueCollection = null;
			logger.info("begin get value>>:"+String.valueOf(System.currentTimeMillis()));
			valueCollection = NewListingValueFactory.getLocalInstance(ctx).getCollectionBySQL(listingId);
			logger.info("end get value>>:"+String.valueOf(System.currentTimeMillis()));
			for(Iterator it = valueCollection.iterator();it.hasNext();){
				NewListingValueInfo valueInfo = (NewListingValueInfo)it.next();
				String entryId = valueInfo.getEntry().getId().toString();
				NewListingEntryInfo entry = (NewListingEntryInfo) entrysMap
						.get(entryId);
				entry.getValues().add(valueInfo);
			}
			logger.info("return>>:"+String.valueOf(System.currentTimeMillis()));
		}
		
		
		return listing;
	}
	protected void _inviteAuditSubmit(Context ctx, BOSUuid listingId) throws BOSException, EASBizException {
//		setInviteQuotingPriceState(ctx,listingId,FDCBillStateEnum.SUBMITTED);
//		super.su
	}
	protected void _setAuditing(Context ctx, BOSUuid listingId) throws BOSException, EASBizException {
//		etInviteQuotingPriceState(ctx,listingId,FDCBillStateEnum.AUDITTING);
		super._setAudittingStatus(ctx, listingId);
	}
	//审批时，更当前操作的清单对象为最新版本，更改前一个最新为非最新版本
	protected void _setAudited(Context ctx, BOSUuid listingId) throws BOSException, EASBizException {
//		setInviteQuotingPriceState(ctx,listingId,FDCBillStateEnum.AUDITTED);
		super.audit(ctx, listingId);
	}
	
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		NewListingInfo info = (NewListingInfo) model;
		if(info.getVersion() == 0.0){
			info.setVersion(1.0);
		}else{
			double maxVersion = getMaxVersionByNumber(info.getNumber(), ctx);
			if(info.getVersion() == maxVersion){
				info.setVersion(info.getVersion()+0.1);
			}
			
		}
		
		return super._save(ctx, info);
	}
	
	
	
	
	
	
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		NewListingInfo info = (NewListingInfo) model;
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
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		super._audit(ctx, billId);
		NewListingInfo info = NewListingFactory.getLocalInstance(ctx).getNewListingInfo(new ObjectUuidPK(billId));
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_inv_newlisting set fisLastVersion = 1 where fid = ?");
		builder.addParam(billId.toString());
		builder.execute();
		
		builder.clear();
		builder.appendSql("update t_inv_newlisting set fisLastVersion = 0 where fnumber = ? and fisLastVersion = 1 and fid <> ?");
		builder.addParam(info.getNumber());
		builder.addParam(billId.toString());
		builder.execute();
	}
	
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		super._unAudit(ctx, billId);
		NewListingInfo billInfo = NewListingFactory.getLocalInstance(ctx).getNewListingInfo(new ObjectUuidPK(billId));
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_inv_newlisting set fislastversion = 1 where fnumber = ? and fstate = '4AUDITTED' and fversion =(select max(fversion) from t_inv_newlisting where fnumber = ? and fstate='4AUDITTED') ");
		builder.addParam(billInfo.getNumber());
		builder.addParam(billInfo.getNumber());
		builder.execute();
		
		builder.clear();
		builder.appendSql("update t_inv_newlisting set fislastversion = 0 where  fid = ?");
		builder.addParam(billId.toString());
		builder.execute();
	}
	protected void _setInviteSubmitStatus(Context ctx, BOSUuid listingId) throws BOSException, EASBizException {
//		setInviteQuotingPriceState(ctx,listingId,FDCBillStateEnum.SUBMITTED);
		super.setSubmitStatus(ctx, listingId);
	}
	private void setInviteQuotingPriceState(Context ctx,BOSUuid listingId,FDCBillStateEnum state) throws EASBizException, BOSException{
		NewListingInfo  listInfo = this.getNewListingInfo(ctx,new ObjectUuidPK(listingId));
		listInfo.setInviteAuditState(state);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("InviteAuditState");
		updatePartial(ctx,listInfo,selector);
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		
		//获取附件id
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("inviteProject.id");
		sic.add("inviteProject.parent.id");
		NewListingInfo  listInfo = this.getNewListingInfo(ctx,pk,sic);
		String prjId = null;
		if(listInfo.getInviteProject()!=null){
			if(listInfo.getInviteProject().getParent()!=null){
				prjId = listInfo.getInviteProject().getParent().getId().toString();
			}else{
				prjId = listInfo.getInviteProject().getId().toString();
			}
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",prjId,CompareType.EQUALS));
			if(InviteFileMergeFactory.getLocalInstance(ctx).exists(filter)){
				throw new InviteProjectException(InviteProjectException.CANDELETNEWLISTHASFILEMERGE);
			}
		}
		
		
		Set attidSet = new HashSet();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		
		builder.appendSql("select att.fid as id, att.fname_l2 as name, bo.fboid  from  T_BAS_Attachment att RIGHT OUTER JOIN T_BAS_BoAttchAsso bo on bo.fattachmentid = att.fid where");
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
		
		super._delete(ctx, pk);
	}
	
	
	 protected double  getMaxVersionByNumber(String fnumber,Context ctx) {
		  double version = 0.0;
		  FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		  builder.appendSql("select fversion from t_inv_newlisting where fnumber = ? and fisLastVersion = 1");
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
	
	
	 protected void checkBill(Context ctx, IObjectValue model)
		throws BOSException, EASBizException {
	//重写检查单据方法  修订时能够保存提交    不报名称和编码可以重复 的错误 
		FDCBillInfo FDCBillInfo = ((FDCBillInfo) model);
		NewListingInfo info=(NewListingInfo) model;
		if(info.getVersion() == 1.0){
			checkNumberDup(ctx, FDCBillInfo);
			checkNameDup(ctx, FDCBillInfo);
		}
	
	}
	 
	 
//	private void checkNumberDup(Context ctx, FDCBillInfo billInfo)	throws BOSException, EASBizException {
//		if(!isUseNumber()) return;
//     FilterInfo filter = new FilterInfo();
//
//     filter.getFilterItems().add(new FilterItemInfo("number", billInfo.getNumber()));
//     filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));		
//     filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit().getId()));
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
		// TODO Auto-generated method stub
		NewListingInfo info = null;
		try {
			info = getNewListingAllValue(ctx, billId.toString());
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double version = getMaxVersionByNumber(info.getNumber(), ctx);
		boolean isLastVersion = info.isIsLastVersion();
		if(!isLastVersion || info.getVersion() < version || !info.getState().equals(FDCBillStateEnum.AUDITTED)){
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
		NewListingPageCollection pageCols = info.getPages();
		NewListingPageInfo page = null;
		NewListingColumnInfo column  = null;
		NewListingEntryInfo entry = null;
		NewListingValueInfo valueInfo = null;
		ListingPageSumEntryInfo sumEntryInfo= null;
		for(Iterator it = pageCols.iterator();it.hasNext();){
			page = (NewListingPageInfo) it.next();
			page.setId(BOSUuid.create(page.getBOSType()));
			page.setHead(info);
			
			Map oldIdColumnMap = new HashMap();
			for(Iterator colIt = page.getColumns().iterator();colIt.hasNext();){
				column = (NewListingColumnInfo) colIt.next();
				oldIdColumnMap.put(column.getId().toString(), column);
				column.setId(BOSUuid.create(column.getBOSType()));
				column.setPage(page);
			}
			
			for(Iterator entryIt = page.getEntrys().iterator();entryIt.hasNext();){
				entry = (NewListingEntryInfo) entryIt.next();
				entry.setId(BOSUuid.create(entry.getBOSType()));
				entry.setHead(page);
				for(Iterator entryValueIt = entry.getValues().iterator();entryValueIt.hasNext();){
					valueInfo = (NewListingValueInfo) entryValueIt.next();
					valueInfo.setId(BOSUuid.create(valueInfo.getBOSType()));
					valueInfo.setEntry(entry);
					String oldColumnId = valueInfo.getColumn().getId().toString();
					if(oldIdColumnMap.containsKey(oldColumnId)){
						NewListingColumnInfo listColumn = (NewListingColumnInfo)oldIdColumnMap.get(oldColumnId);
						valueInfo.setColumn(listColumn);
					}
					
				}
			}
			for(Iterator sumEntryIt =page.getSumEntry().iterator();sumEntryIt.hasNext(); ){
				sumEntryInfo = (ListingPageSumEntryInfo) sumEntryIt.next();
				sumEntryInfo.setId(BOSUuid.create(sumEntryInfo.getBOSType()));
				sumEntryInfo.setPage(page);
			}
			
		}
		
		info.setState(FDCBillStateEnum.SAVED);
		info.setIsLastVersion(Boolean.FALSE.booleanValue());
		info.setAuditor(null);
		info.setAuditTime(null);
		//关联附件的处理
          try {
			AttachmentManagerFactory.getServerManager(ctx).copyBizAttachmentFiles(billId.toString(), info.getId().toString());
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}    
}