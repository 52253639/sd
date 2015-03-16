package com.kingdee.eas.fdc.invite.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.NewOrgViewHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.fdc.invite.ListingItemCollection;
import com.kingdee.eas.fdc.invite.ListingItemFactory;
import com.kingdee.eas.fdc.invite.ListingItemInfo;

public class ListingItemControllerBean extends
		AbstractListingItemControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.invite.app.ListingItemControllerBean");

	/**
	 *
	 * 描述：是否使用编码字段
	 *
	 * @return
	 * @author:liupd 创建时间：2006-10-17
	 *               <p>
	 */
	protected boolean isUseNumber() {
		return false;
	}
	protected void trimName(FDCBillInfo fDCBillInfo){

	}
	/**
	 *
	 * 描述：检查单据
	 *
	 * @param ctx
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd 创建时间：2006-10-13
	 *               <p>
	 */
	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		// super.checkBill(ctx,model);
		ListingItemInfo billInfo = ((ListingItemInfo) model);
		FilterInfo filter = new FilterInfo();
		//首先判断当前组织是否有相同的子目名称
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("name", billInfo.getName()));
		if (billInfo.getId() != null) {
			filterItems.add(new FilterItemInfo("id", billInfo.getId()
					.toString(), CompareType.NOTEQUALS));
		}
		filterItems.add(new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit()
				.getId()));
		filterItems.add(new FilterItemInfo("headType.id", billInfo
				.getHeadType().getId()));
		if (_exists(ctx, filter)) {
			throw new FDCInviteException(FDCInviteException.HAVE_SAME_ITEM_CURORG);
		}
		//然后判断上级是否有相同的子目名称
		Set updownOrgIdSet = getUpOrgIdSet(ctx,billInfo.getOrgUnit().getId());
		if(updownOrgIdSet.size()>0){
			filter = new FilterInfo();
			filterItems = filter.getFilterItems();
			filterItems.add(new FilterItemInfo("name", billInfo.getName()));
			filterItems.add(new FilterItemInfo("orgUnit", updownOrgIdSet,
					CompareType.INCLUDE));
			filterItems.add(new FilterItemInfo("headType.id", billInfo
					.getHeadType().getId()));
			if (_exists(ctx, filter)) {
				throw new FDCInviteException(FDCInviteException.HAVE_SAME_ITEM_UPORG);
			}
		}
		// 然后判断下级是否有相同的子目名称
		updownOrgIdSet = getDownOrgIdSet(ctx,billInfo.getOrgUnit().getId());
		if(updownOrgIdSet.size()>0){
			filter = new FilterInfo();
			filterItems = filter.getFilterItems();
			filterItems.add(new FilterItemInfo("name", billInfo.getName()));
			filterItems.add(new FilterItemInfo("orgUnit", updownOrgIdSet,
					CompareType.INCLUDE));
			filterItems.add(new FilterItemInfo("headType.id", billInfo
					.getHeadType().getId()));
			if (_exists(ctx, filter)) {
				throw new FDCInviteException(FDCInviteException.EXITONDOWNORG);
			}
		}
		
	}
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		return new HashMap();
	}
	protected IObjectCollection _getCollectionForUpOrg(Context ctx, BOSUuid currentOrgId, BOSUuid headTypeId) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		Set updownOrgIdSet = getUpOrgIdSet(ctx,currentOrgId);
		if(updownOrgIdSet.size()==0){
			return new ListingItemCollection();
		}
		return getCollectionForOrgs(ctx,updownOrgIdSet,headTypeId);
	}
	/***
	 * 获取上级关系的org
	 * @param ctx
	 * @param org
	 * @return
	 * @throws BOSException
	 */
	
	private Set getUpOrgIdSet(Context ctx,BOSUuid currentOrgId) throws BOSException , EASBizException{
		CompanyOrgUnitInfo currentOrg = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(new ObjectUuidPK(currentOrgId));
		String[] ancestor = NewOrgViewHelper.getAncestor(currentOrg.getLongNumber());
		Set lNumSet = new HashSet();
		for (int i = 0; i < ancestor.length; i++) {
			lNumSet.add(ancestor[i]);
		}
		EntityViewInfo orgView = new EntityViewInfo();
		FilterInfo orgFilter = new FilterInfo();
		orgFilter.getFilterItems().add(
				new FilterItemInfo("longNumber", lNumSet, CompareType.INCLUDE));
		orgFilter.getFilterItems().add(
				new FilterItemInfo("id", currentOrg.getId(), CompareType.NOTEQUALS));
		orgView.setFilter(orgFilter);
		ICompanyOrgUnit iComp = CompanyOrgUnitFactory.getLocalInstance(ctx);
		CompanyOrgUnitCollection ancestorComps = iComp
				.getCompanyOrgUnitCollection(orgView);
		Set orgIdSet = new HashSet(ancestorComps.size());
		for (int i = 0, n = ancestorComps.size(); i < n; i++) {
			orgIdSet.add(ancestorComps.get(i).getId().toString());
		}
		return orgIdSet;
	}
	protected IObjectCollection _getCollectionForDownOrg(Context ctx, BOSUuid currentOrgId, BOSUuid headTypeId) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		Set updownOrgIdSet = getDownOrgIdSet(ctx,currentOrgId);
		if(updownOrgIdSet.size()==0){
			return new ListingItemCollection();
		}
		return getCollectionForOrgs(ctx,updownOrgIdSet,headTypeId);
	}
	private IObjectCollection getCollectionForOrgs(Context ctx,Set orgs,BOSUuid headTypeId)throws BOSException, EASBizException {
		EntityViewInfo itemView = new EntityViewInfo();
		FilterInfo itemfilter = new FilterInfo();
		itemView.setFilter(itemfilter);
		itemfilter.getFilterItems().add(
				new FilterItemInfo("orgUnit", orgs, CompareType.INCLUDE));
		itemfilter.getFilterItems().add(
				new FilterItemInfo("headType.id", headTypeId));
		ListingItemCollection items = ListingItemFactory.getLocalInstance(ctx)
				.getListingItemCollection(itemView);
		return items;
	}
	/***
	 * 获取下级关系的org
	 * @param ctx
	 * @param org
	 * @return
	 * @throws BOSException
	 */
	
	private Set getDownOrgIdSet(Context ctx,BOSUuid currentOrgId) throws BOSException , EASBizException{
		CompanyOrgUnitInfo currentOrg = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(new ObjectUuidPK(currentOrgId));
		EntityViewInfo orgView = new EntityViewInfo();
		FilterInfo orgFilter = new FilterInfo();		
		orgFilter.getFilterItems().add(
				new FilterItemInfo("longNumber", currentOrg.getLongNumber() + "!%",
						CompareType.LIKE));		
		orgView.setFilter(orgFilter);
		ICompanyOrgUnit iComp = CompanyOrgUnitFactory.getLocalInstance(ctx);
		CompanyOrgUnitCollection ancestorComps = iComp
				.getCompanyOrgUnitCollection(orgView);
		Set orgIdSet = new HashSet(ancestorComps.size());
		for (int i = 0, n = ancestorComps.size(); i < n; i++) {
			orgIdSet.add(ancestorComps.get(i).getId().toString());
		}
		return orgIdSet;
	}
	protected IObjectCollection _getCollectionForCurOrg(Context ctx, BOSUuid currentOrgId, BOSUuid headTypeId) throws BOSException {
		// TODO 自动生成方法存根
		EntityViewInfo itemView = new EntityViewInfo();
		FilterInfo itemfilter = new FilterInfo();
		itemView.setFilter(itemfilter);
		itemfilter.getFilterItems().add(
				new FilterItemInfo("orgUnit", currentOrgId));
		itemfilter.getFilterItems().add(
				new FilterItemInfo("headType.id", headTypeId));
		ListingItemCollection items = ListingItemFactory.getLocalInstance(ctx)
				.getListingItemCollection(itemView);
		return items;
	}
}