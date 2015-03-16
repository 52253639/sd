package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCCusBaseDataGroupFactory
{
    private FDCCusBaseDataGroupFactory()
    {
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCCusBaseDataGroup getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCCusBaseDataGroup)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4851950C") ,com.kingdee.eas.fdc.basecrm.IFDCCusBaseDataGroup.class);
    }
    
    public static com.kingdee.eas.fdc.basecrm.IFDCCusBaseDataGroup getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCCusBaseDataGroup)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4851950C") ,com.kingdee.eas.fdc.basecrm.IFDCCusBaseDataGroup.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCCusBaseDataGroup getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCCusBaseDataGroup)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4851950C"));
    }
    public static com.kingdee.eas.fdc.basecrm.IFDCCusBaseDataGroup getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basecrm.IFDCCusBaseDataGroup)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4851950C"));
    }
}