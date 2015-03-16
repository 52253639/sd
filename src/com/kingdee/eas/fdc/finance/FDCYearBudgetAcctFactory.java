package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCYearBudgetAcctFactory
{
    private FDCYearBudgetAcctFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCYearBudgetAcct getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCYearBudgetAcct)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("38B3E6AB") ,com.kingdee.eas.fdc.finance.IFDCYearBudgetAcct.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCYearBudgetAcct getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCYearBudgetAcct)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("38B3E6AB") ,com.kingdee.eas.fdc.finance.IFDCYearBudgetAcct.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCYearBudgetAcct getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCYearBudgetAcct)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("38B3E6AB"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCYearBudgetAcct getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCYearBudgetAcct)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("38B3E6AB"));
    }
}