package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BirdEyePicFactory
{
    private BirdEyePicFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBirdEyePic getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBirdEyePic)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F8B568CD") ,com.kingdee.eas.fdc.sellhouse.IBirdEyePic.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBirdEyePic getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBirdEyePic)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F8B568CD") ,com.kingdee.eas.fdc.sellhouse.IBirdEyePic.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBirdEyePic getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBirdEyePic)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F8B568CD"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBirdEyePic getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBirdEyePic)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F8B568CD"));
    }
}