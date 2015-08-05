package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeWFTypeFactory
{
    private ChangeWFTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IChangeWFType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeWFType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5FC9A014") ,com.kingdee.eas.fdc.contract.IChangeWFType.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IChangeWFType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeWFType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5FC9A014") ,com.kingdee.eas.fdc.contract.IChangeWFType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IChangeWFType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeWFType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5FC9A014"));
    }
    public static com.kingdee.eas.fdc.contract.IChangeWFType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IChangeWFType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5FC9A014"));
    }
}