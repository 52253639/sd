package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class GradeSetUpFactory
{
    private GradeSetUpFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IGradeSetUp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IGradeSetUp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9A0C7884") ,com.kingdee.eas.fdc.invite.supplier.IGradeSetUp.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IGradeSetUp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IGradeSetUp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9A0C7884") ,com.kingdee.eas.fdc.invite.supplier.IGradeSetUp.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IGradeSetUp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IGradeSetUp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9A0C7884"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IGradeSetUp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IGradeSetUp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9A0C7884"));
    }
}