package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierEvaluationIndexValueFactory
{
    private MarketSupplierEvaluationIndexValueFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationIndexValue getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationIndexValue)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9B553C71") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationIndexValue.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationIndexValue getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationIndexValue)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9B553C71") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationIndexValue.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationIndexValue getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationIndexValue)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9B553C71"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationIndexValue getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationIndexValue)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9B553C71"));
    }
}