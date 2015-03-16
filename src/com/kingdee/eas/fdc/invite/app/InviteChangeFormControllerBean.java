package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Iterator;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.invite.BaseInviteInfo;
import com.kingdee.eas.fdc.invite.InviteChangeFormCollection;
import com.kingdee.eas.fdc.invite.InviteChangeFormEntryCollection;
import com.kingdee.eas.fdc.invite.InviteChangeFormEntryFactory;
import com.kingdee.eas.fdc.invite.InviteChangeFormEntryInfo;
import com.kingdee.eas.fdc.invite.InviteChangeFormFactory;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.invite.InviteChangeFormInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class InviteChangeFormControllerBean extends AbstractInviteChangeFormControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.InviteChangeFormControllerBean");
    
    protected boolean isUseName() {
		return false;
	}
    
    @SuppressWarnings("unchecked")
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	InviteChangeFormInfo info = InviteChangeFormFactory.getLocalInstance(ctx).getInviteChangeFormInfo(new ObjectUuidPK(billId));
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add( new FilterItemInfo("parent.id",billId.toString()));
    	view.setFilter(filter);
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("*");
    	sic.add("inviteProject.*");
    	sic.add("inviteProject.inviteForm.*");
    	
    	SelectorItemCollection sic2 = new SelectorItemCollection();
    	sic2.add("inviteForm");
    	InviteChangeFormEntryCollection entryColl = InviteChangeFormEntryFactory.getLocalInstance(ctx).getInviteChangeFormEntryCollection(view);
    	Iterator<InviteChangeFormEntryInfo> iterator = entryColl.iterator();
    	while(iterator.hasNext()) {
    		InviteChangeFormEntryInfo entry = iterator.next();
    		InviteProjectInfo project = entry.getInviteProject();
    		project.setInviteForm(entry.getNowInviteFrom());
    		
    		InviteProjectFactory.getLocalInstance(ctx).updatePartial(project, sic2);
    	}
    	
		super._audit(ctx, billId);
	}
}