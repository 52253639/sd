package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCMonthBudgetAcctFactory
{
    private FDCMonthBudgetAcctFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcct getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcct)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("096048C2") ,com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcct.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcct getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcct)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("096048C2") ,com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcct.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcct getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcct)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("096048C2"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcct getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcct)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("096048C2"));
    }
}