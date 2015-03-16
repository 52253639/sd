package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class LoanDataEntryFactory
{
    private LoanDataEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ILoanDataEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ILoanDataEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("88F020DD") ,com.kingdee.eas.fdc.sellhouse.ILoanDataEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ILoanDataEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ILoanDataEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("88F020DD") ,com.kingdee.eas.fdc.sellhouse.ILoanDataEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ILoanDataEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ILoanDataEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("88F020DD"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ILoanDataEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ILoanDataEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("88F020DD"));
    }
}