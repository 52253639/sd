package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AcctConCostFactory
{
    private AcctConCostFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IAcctConCost getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IAcctConCost)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CC31746D") ,com.kingdee.eas.fdc.finance.IAcctConCost.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IAcctConCost getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IAcctConCost)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CC31746D") ,com.kingdee.eas.fdc.finance.IAcctConCost.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IAcctConCost getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IAcctConCost)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CC31746D"));
    }
    public static com.kingdee.eas.fdc.finance.IAcctConCost getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IAcctConCost)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CC31746D"));
    }
}