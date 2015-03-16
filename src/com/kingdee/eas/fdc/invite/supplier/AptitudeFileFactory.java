package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AptitudeFileFactory
{
    private AptitudeFileFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IAptitudeFile getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IAptitudeFile)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("11DD1A40") ,com.kingdee.eas.fdc.invite.supplier.IAptitudeFile.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IAptitudeFile getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IAptitudeFile)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("11DD1A40") ,com.kingdee.eas.fdc.invite.supplier.IAptitudeFile.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IAptitudeFile getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IAptitudeFile)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("11DD1A40"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IAptitudeFile getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IAptitudeFile)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("11DD1A40"));
    }
}