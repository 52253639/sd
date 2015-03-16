package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PartAMainContractFactory
{
    private PartAMainContractFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPartAMainContract getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPartAMainContract)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B2F9E68A") ,com.kingdee.eas.fdc.finance.IPartAMainContract.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPartAMainContract getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPartAMainContract)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B2F9E68A") ,com.kingdee.eas.fdc.finance.IPartAMainContract.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPartAMainContract getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPartAMainContract)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B2F9E68A"));
    }
    public static com.kingdee.eas.fdc.finance.IPartAMainContract getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPartAMainContract)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B2F9E68A"));
    }
}