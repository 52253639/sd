package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AdapterLogFactory
{
    private AdapterLogFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IAdapterLog getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAdapterLog)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("030C6290") ,com.kingdee.eas.fdc.sellhouse.IAdapterLog.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IAdapterLog getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAdapterLog)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("030C6290") ,com.kingdee.eas.fdc.sellhouse.IAdapterLog.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IAdapterLog getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAdapterLog)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("030C6290"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IAdapterLog getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAdapterLog)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("030C6290"));
    }
}