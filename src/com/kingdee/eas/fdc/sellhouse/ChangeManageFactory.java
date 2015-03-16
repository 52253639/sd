package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeManageFactory
{
    private ChangeManageFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeManage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeManage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("27800F90") ,com.kingdee.eas.fdc.sellhouse.IChangeManage.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IChangeManage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeManage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("27800F90") ,com.kingdee.eas.fdc.sellhouse.IChangeManage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeManage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeManage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("27800F90"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IChangeManage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IChangeManage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("27800F90"));
    }
}