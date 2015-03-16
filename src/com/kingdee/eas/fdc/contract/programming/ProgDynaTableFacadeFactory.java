package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgDynaTableFacadeFactory
{
    private ProgDynaTableFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgDynaTableFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgDynaTableFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4F5771F6") ,com.kingdee.eas.fdc.contract.programming.IProgDynaTableFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.programming.IProgDynaTableFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgDynaTableFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4F5771F6") ,com.kingdee.eas.fdc.contract.programming.IProgDynaTableFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgDynaTableFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgDynaTableFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4F5771F6"));
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgDynaTableFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgDynaTableFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4F5771F6"));
    }
}