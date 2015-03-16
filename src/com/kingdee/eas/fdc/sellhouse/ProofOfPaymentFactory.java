package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProofOfPaymentFactory
{
    private ProofOfPaymentFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IProofOfPayment getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IProofOfPayment)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EC661AE6") ,com.kingdee.eas.fdc.sellhouse.IProofOfPayment.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IProofOfPayment getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IProofOfPayment)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EC661AE6") ,com.kingdee.eas.fdc.sellhouse.IProofOfPayment.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IProofOfPayment getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IProofOfPayment)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EC661AE6"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IProofOfPayment getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IProofOfPayment)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EC661AE6"));
    }
}