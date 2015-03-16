package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCDepMonBudgetAcctFactory
{
    private FDCDepMonBudgetAcctFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcct getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcct)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("361EFD6B") ,com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcct.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcct getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcct)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("361EFD6B") ,com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcct.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcct getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcct)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("361EFD6B"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcct getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcct)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("361EFD6B"));
    }
}