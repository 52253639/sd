package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RevListFactory
{
    private RevListFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.IRevList getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IRevList)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E004F905") ,com.kingdee.eas.fdc.basecrm.IRevList.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.IRevList getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IRevList)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E004F905") ,com.kingdee.eas.fdc.basecrm.IRevList.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.IRevList getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IRevList)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E004F905"));
    }
    public static com.kingdee.eas.fdc.basecrm.IRevList getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IRevList)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E004F905"));
    }
}