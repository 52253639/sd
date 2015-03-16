package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BanBasedataEntryListFactory
{
    private BanBasedataEntryListFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBanBasedataEntryList getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBanBasedataEntryList)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0B6B1781") ,com.kingdee.eas.fdc.sellhouse.IBanBasedataEntryList.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBanBasedataEntryList getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBanBasedataEntryList)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0B6B1781") ,com.kingdee.eas.fdc.sellhouse.IBanBasedataEntryList.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBanBasedataEntryList getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBanBasedataEntryList)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0B6B1781"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBanBasedataEntryList getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBanBasedataEntryList)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0B6B1781"));
    }
}