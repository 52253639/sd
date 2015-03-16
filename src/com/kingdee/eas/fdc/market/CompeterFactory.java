package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompeterFactory
{
    private CompeterFactory()
    {
    }
    public static com.kingdee.eas.fdc.market.ICompeter getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeter)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0D5D3DFC") ,com.kingdee.eas.fdc.market.ICompeter.class);
    }
    
    public static com.kingdee.eas.fdc.market.ICompeter getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeter)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0D5D3DFC") ,com.kingdee.eas.fdc.market.ICompeter.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.market.ICompeter getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeter)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0D5D3DFC"));
    }
    public static com.kingdee.eas.fdc.market.ICompeter getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.market.ICompeter)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0D5D3DFC"));
    }
}