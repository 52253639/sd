package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaterialSampleEntrysFactory
{
    private MaterialSampleEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.IMaterialSampleEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IMaterialSampleEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CB97C076") ,com.kingdee.eas.fdc.invite.IMaterialSampleEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.invite.IMaterialSampleEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IMaterialSampleEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CB97C076") ,com.kingdee.eas.fdc.invite.IMaterialSampleEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.IMaterialSampleEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IMaterialSampleEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CB97C076"));
    }
    public static com.kingdee.eas.fdc.invite.IMaterialSampleEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.IMaterialSampleEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CB97C076"));
    }
}