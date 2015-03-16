package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierEvaluationFactory
{
    private MarketSupplierEvaluationFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluation getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluation)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5B8D6312") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluation.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluation getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluation)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5B8D6312") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluation.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluation getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluation)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5B8D6312"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluation getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluation)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5B8D6312"));
    }
}