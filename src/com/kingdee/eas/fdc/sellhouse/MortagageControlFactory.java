package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MortagageControlFactory
{
    private MortagageControlFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IMortagageControl getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMortagageControl)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DDBA6243") ,com.kingdee.eas.fdc.sellhouse.IMortagageControl.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IMortagageControl getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMortagageControl)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DDBA6243") ,com.kingdee.eas.fdc.sellhouse.IMortagageControl.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IMortagageControl getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMortagageControl)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DDBA6243"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IMortagageControl getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IMortagageControl)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DDBA6243"));
    }
}