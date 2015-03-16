package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCMonthBudgetAcctItemFactory
{
    private FDCMonthBudgetAcctItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5ACD06F5") ,com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctItem.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5ACD06F5") ,com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5ACD06F5"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCMonthBudgetAcctItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5ACD06F5"));
    }
}