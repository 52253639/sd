package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCYearBudgetAcctItemFactory
{
    private FDCYearBudgetAcctItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("59DD885E") ,com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctItem.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("59DD885E") ,com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("59DD885E"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCYearBudgetAcctItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("59DD885E"));
    }
}