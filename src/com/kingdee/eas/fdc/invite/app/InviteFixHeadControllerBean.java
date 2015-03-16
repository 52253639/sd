package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
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
import com.kingdee.eas.fdc.invite.InviteFixHeadFactory;
import com.kingdee.eas.fdc.invite.InviteFixHeadInfo;
import com.kingdee.eas.fdc.invite.TenderAccepterResultFactory;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.invite.BaseInviteCollection;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.invite.InviteFixHeadCollection;
import com.kingdee.util.NumericExceptionSubItem;

public class InviteFixHeadControllerBean extends AbstractInviteFixHeadControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.InviteFixHeadControllerBean");
    
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	InviteFixHeadInfo fixHead = InviteFixHeadFactory.getLocalInstance(ctx).getInviteFixHeadInfo( new ObjectUuidPK(billId));
    	String inviteProjectId = fixHead.getInviteProject().getId().toString();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",inviteProjectId));
    	if( TenderAccepterResultFactory.getLocalInstance(ctx).exists(filter)) {
    		throw new EASBizException(new NumericExceptionSubItem("100","招标立项下存在中标审批，不能对此单据反审批！"));
    	}
    	
		super._unAudit(ctx, billId);
	}
}