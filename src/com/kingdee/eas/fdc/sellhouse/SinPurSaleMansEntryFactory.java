package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SinPurSaleMansEntryFactory
{
    private SinPurSaleMansEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISinPurSaleMansEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISinPurSaleMansEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C6EF0942") ,com.kingdee.eas.fdc.sellhouse.ISinPurSaleMansEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISinPurSaleMansEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISinPurSaleMansEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C6EF0942") ,com.kingdee.eas.fdc.sellhouse.ISinPurSaleMansEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISinPurSaleMansEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISinPurSaleMansEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C6EF0942"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISinPurSaleMansEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISinPurSaleMansEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C6EF0942"));
    }
}