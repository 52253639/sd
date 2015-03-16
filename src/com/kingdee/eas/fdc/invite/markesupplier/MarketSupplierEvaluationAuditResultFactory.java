package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierEvaluationAuditResultFactory
{
    private MarketSupplierEvaluationAuditResultFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationAuditResult getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationAuditResult)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9B82C566") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationAuditResult.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationAuditResult getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationAuditResult)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9B82C566") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationAuditResult.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationAuditResult getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationAuditResult)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9B82C566"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationAuditResult getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationAuditResult)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9B82C566"));
    }
}