package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCMonthBudgetAcctEntryFactory
{
    private FDCMonthBudgetAcctEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FE98F8D0") ,com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FE98F8D0") ,com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FE98F8D0"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FE98F8D0"));
    }
}