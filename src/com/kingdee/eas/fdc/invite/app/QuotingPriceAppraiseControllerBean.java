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
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.eas.fdc.invite.QuotingPriceAppraiseInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.invite.QuotingPriceAppraiseCollection;

public class QuotingPriceAppraiseControllerBean extends AbstractQuotingPriceAppraiseControllerBean
{
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		super._audit(ctx, billId);
		setInviteQuotingPriceState(ctx,billId,FDCBillStateEnum.AUDITTED);
	}

	protected void _setAudittingStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		super._setAudittingStatus(ctx, billId);
		setInviteQuotingPriceState(ctx,billId,FDCBillStateEnum.AUDITTING);
	}

	protected void _setSubmitStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		super._setSubmitStatus(ctx, billId);
		setInviteQuotingPriceState(ctx,billId,FDCBillStateEnum.SUBMITTED);
	}

	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		super._submit(ctx, pk, model);
		setInviteQuotingPriceState(ctx,BOSUuid.read(pk.toString()),FDCBillStateEnum.SUBMITTED);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		IObjectPK pk = super._submit(ctx, model);
		setInviteQuotingPriceState(ctx,BOSUuid.read(pk.toString()),FDCBillStateEnum.SUBMITTED);
		return pk;
	}

	protected boolean isUseName() {
		return false;
	}

	protected boolean isUseNumber() {
		return false;
	}
	private void setInviteQuotingPriceState(Context ctx,BOSUuid billId,FDCBillStateEnum state) throws EASBizException, BOSException{
		QuotingPriceAppraiseInfo info = this.getQuotingPriceAppraiseInfo(ctx, new ObjectUuidPK(billId));
		NewListingInfo  listInfo = new NewListingInfo();
		listInfo.setId(info.getInviteListing());
		listInfo.setInviteAuditState(state);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("InviteAuditState");
		NewListingFactory.getLocalInstance(ctx).updatePartial(listInfo, selector);
	}

	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.QuotingPriceAppraiseControllerBean");
}