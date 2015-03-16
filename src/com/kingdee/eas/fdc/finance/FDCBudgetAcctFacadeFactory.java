package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCBudgetAcctFacadeFactory
{
    private FDCBudgetAcctFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCBudgetAcctFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCBudgetAcctFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("94DADFC8") ,com.kingdee.eas.fdc.finance.IFDCBudgetAcctFacade.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCBudgetAcctFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCBudgetAcctFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("94DADFC8") ,com.kingdee.eas.fdc.finance.IFDCBudgetAcctFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCBudgetAcctFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCBudgetAcctFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("94DADFC8"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCBudgetAcctFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCBudgetAcctFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("94DADFC8"));
    }
}