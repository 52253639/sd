package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SignSaleManEntryFactory
{
    private SignSaleManEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignSaleManEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignSaleManEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9FD877D7") ,com.kingdee.eas.fdc.sellhouse.ISignSaleManEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISignSaleManEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignSaleManEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9FD877D7") ,com.kingdee.eas.fdc.sellhouse.ISignSaleManEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignSaleManEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignSaleManEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9FD877D7"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISignSaleManEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISignSaleManEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9FD877D7"));
    }
}