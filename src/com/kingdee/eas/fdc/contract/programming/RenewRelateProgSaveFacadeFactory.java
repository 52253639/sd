package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RenewRelateProgSaveFacadeFactory
{
    private RenewRelateProgSaveFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.programming.IRenewRelateProgSaveFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IRenewRelateProgSaveFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("88151627") ,com.kingdee.eas.fdc.contract.programming.IRenewRelateProgSaveFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.programming.IRenewRelateProgSaveFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IRenewRelateProgSaveFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("88151627") ,com.kingdee.eas.fdc.contract.programming.IRenewRelateProgSaveFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.programming.IRenewRelateProgSaveFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IRenewRelateProgSaveFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("88151627"));
    }
    public static com.kingdee.eas.fdc.contract.programming.IRenewRelateProgSaveFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IRenewRelateProgSaveFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("88151627"));
    }
}