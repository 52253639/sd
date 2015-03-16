package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCBudgetAcctDataFactory
{
    private FDCBudgetAcctDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCBudgetAcctData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCBudgetAcctData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4E341258") ,com.kingdee.eas.fdc.finance.IFDCBudgetAcctData.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCBudgetAcctData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCBudgetAcctData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4E341258") ,com.kingdee.eas.fdc.finance.IFDCBudgetAcctData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCBudgetAcctData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCBudgetAcctData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4E341258"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCBudgetAcctData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCBudgetAcctData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4E341258"));
    }
}