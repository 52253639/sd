package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCDepMonBudgetAcctEntryFactory
{
    private FDCDepMonBudgetAcctEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("614DC5C7") ,com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("614DC5C7") ,com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("614DC5C7"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("614DC5C7"));
    }
}