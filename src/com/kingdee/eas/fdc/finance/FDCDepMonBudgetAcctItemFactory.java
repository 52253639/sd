package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCDepMonBudgetAcctItemFactory
{
    private FDCDepMonBudgetAcctItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EA5F3F1E") ,com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctItem.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EA5F3F1E") ,com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EA5F3F1E"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCDepMonBudgetAcctItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EA5F3F1E"));
    }
}