package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHEFunProductTypeEntryFactory
{
    private SHEFunProductTypeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEFunProductTypeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEFunProductTypeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B354D293") ,com.kingdee.eas.fdc.sellhouse.ISHEFunProductTypeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHEFunProductTypeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEFunProductTypeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B354D293") ,com.kingdee.eas.fdc.sellhouse.ISHEFunProductTypeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEFunProductTypeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEFunProductTypeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B354D293"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHEFunProductTypeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHEFunProductTypeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B354D293"));
    }
}