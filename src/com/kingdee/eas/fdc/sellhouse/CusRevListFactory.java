package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CusRevListFactory
{
    private CusRevListFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICusRevList getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICusRevList)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C64ADF7B") ,com.kingdee.eas.fdc.sellhouse.ICusRevList.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICusRevList getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICusRevList)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C64ADF7B") ,com.kingdee.eas.fdc.sellhouse.ICusRevList.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICusRevList getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICusRevList)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C64ADF7B"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICusRevList getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICusRevList)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C64ADF7B"));
    }
}