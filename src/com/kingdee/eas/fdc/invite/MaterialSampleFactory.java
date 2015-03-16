package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaterialSampleFactory
{
    private MaterialSampleFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IMaterialSample getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IMaterialSample)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1A7A41D5") ,com.kingdee.eas.fdc.invite.IMaterialSample.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IMaterialSample getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IMaterialSample)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1A7A41D5") ,com.kingdee.eas.fdc.invite.IMaterialSample.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IMaterialSample getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IMaterialSample)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1A7A41D5"));
    }
    public static com.kingdee.eas.fdc.invite.IMaterialSample getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IMaterialSample)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1A7A41D5"));
    }
}