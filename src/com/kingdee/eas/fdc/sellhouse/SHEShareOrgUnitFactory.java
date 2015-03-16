package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHEShareOrgUnitFactory
{
    private SHEShareOrgUnitFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEShareOrgUnit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEShareOrgUnit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B1C2963E") ,com.kingdee.eas.fdc.sellhouse.ISHEShareOrgUnit.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHEShareOrgUnit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEShareOrgUnit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B1C2963E") ,com.kingdee.eas.fdc.sellhouse.ISHEShareOrgUnit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEShareOrgUnit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEShareOrgUnit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B1C2963E"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEShareOrgUnit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEShareOrgUnit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B1C2963E"));
    }
}