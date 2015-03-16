package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierEvaluationContractEntryFactory
{
    private MarketSupplierEvaluationContractEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationContractEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationContractEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("47C7BC8E") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationContractEntry.class);
    }
    
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationContractEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationContractEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("47C7BC8E") ,com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationContractEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationContractEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationContractEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("47C7BC8E"));
    }
    public static com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationContractEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierEvaluationContractEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("47C7BC8E"));
    }
}