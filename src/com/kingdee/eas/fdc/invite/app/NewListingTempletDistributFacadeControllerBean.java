package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;

import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCException;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.fdc.invite.NewListTempletCollection;
import com.kingdee.eas.fdc.invite.NewListTempletFactory;
import com.kingdee.eas.fdc.invite.NewListTempletInfo;


public class NewListingTempletDistributFacadeControllerBean extends AbstractNewListingTempletDistributFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.NewListingTempletDistributFacadeControllerBean");
    protected void _Distribute(Context ctx, String templetId, IObjectCollection selectOrgs)throws BOSException, EASBizException
    {
    	SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("inviteType.*"));
		sic.add(new SelectorItemInfo("oriOrg.*"));
		sic.add(new SelectorItemInfo("pages.*"));
		sic.add(new SelectorItemInfo("pages.pageHead.*"));
		sic.add(new SelectorItemInfo("pages.headType.*"));
		sic.add(new SelectorItemInfo("pages.templetColumns.*"));
		sic.add(new SelectorItemInfo("pages.entrys.*"));
		sic.add(new SelectorItemInfo("pages.templetColumns.headColumn.*"));
		sic
				.add(new SelectorItemInfo(
						"pages.templetColumns.headColumn.parent.*"));
		sic.add(new SelectorItemInfo("pages.entrys.item.*"));
		sic.add(new SelectorItemInfo("pages.entrys.values.*"));
		NewListTempletInfo templetInfo = NewListTempletFactory
				.getLocalInstance(ctx).getNewListTempletInfo(
						new ObjectUuidPK(BOSUuid.read(templetId)), sic);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();		
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("id", templetId, CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("name", templetInfo.getName()));
		NewListTempletCollection nameEquels = NewListTempletFactory
				.getLocalInstance(ctx).getNewListTempletCollection(view);
		Map nameEquelMap = new HashMap();
		for (int i = 0; i < nameEquels.size(); i++) {
			nameEquelMap.put(nameEquels.get(i).getOrgUnit().getId().toString(),
					nameEquels.get(i).getOrgUnit());
		}
		filter.getFilterItems().clear();
		filter.getFilterItems().add(
				new FilterItemInfo("id", templetId, CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("number", templetInfo.getNumber()));
		nameEquels = NewListTempletFactory.getLocalInstance(ctx)
				.getNewListTempletCollection(view);
		Map numEquelMap = new HashMap();
		for (int i = 0; i < nameEquels.size(); i++) {
			numEquelMap.put(nameEquels.get(i).getOrgUnit().getId().toString(),
					nameEquels.get(i).getOrgUnit());
		}
		for (int i = 0; i < selectOrgs.size(); i++) {
			FullOrgUnitInfo org = ((FullOrgUnitCollection)selectOrgs).get(i);
			if (numEquelMap.containsKey(org.getId().toString())) {
				throw new FDCInviteException(FDCInviteException.HAVE_SAME_TEMPLET_NUM_INORG,new Object[]{org.getName()});				
			}
			if (nameEquelMap.containsKey(org.getId().toString())) {
				throw new FDCInviteException(FDCInviteException.HAVE_SAME_TEMPLET_NAME_INORG,new Object[]{org.getName()});
			}
		}
		for (int i = 0; i < selectOrgs.size(); i++) {
			FullOrgUnitInfo org = ((FullOrgUnitCollection)selectOrgs).get(i);
			NewListTempletInfo templetClone = (NewListTempletInfo) templetInfo.clone();
			templetClone.getPages().clear();
			templetClone.setId(BOSUuid.create(templetClone.getBOSType()));
			templetClone.setOrgUnit(org);
			templetClone.setOriOrg(templetInfo.getOrgUnit());			
			NewListTempletFactory.getLocalInstance(ctx).submit(templetClone);
		}
		
    }
}