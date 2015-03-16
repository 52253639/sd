package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FamillyEarningFactory
{
    private FamillyEarningFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IFamillyEarning getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFamillyEarning)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AB323243") ,com.kingdee.eas.fdc.sellhouse.IFamillyEarning.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IFamillyEarning getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFamillyEarning)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AB323243") ,com.kingdee.eas.fdc.sellhouse.IFamillyEarning.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IFamillyEarning getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFamillyEarning)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AB323243"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IFamillyEarning getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFamillyEarning)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AB323243"));
    }
}