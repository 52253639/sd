package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class LoanDataFactory
{
    private LoanDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ILoanData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ILoanData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D9698C95") ,com.kingdee.eas.fdc.sellhouse.ILoanData.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ILoanData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ILoanData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D9698C95") ,com.kingdee.eas.fdc.sellhouse.ILoanData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ILoanData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ILoanData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D9698C95"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ILoanData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ILoanData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D9698C95"));
    }
}