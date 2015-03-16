package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCYearBudgetAcctEntryFactory
{
    private FDCYearBudgetAcctEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E198A487") ,com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E198A487") ,com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E198A487"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E198A487"));
    }
}