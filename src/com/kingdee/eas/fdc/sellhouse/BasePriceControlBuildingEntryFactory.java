package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BasePriceControlBuildingEntryFactory
{
    private BasePriceControlBuildingEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IBasePriceControlBuildingEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBasePriceControlBuildingEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4C5347DE") ,com.kingdee.eas.fdc.sellhouse.IBasePriceControlBuildingEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IBasePriceControlBuildingEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBasePriceControlBuildingEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4C5347DE") ,com.kingdee.eas.fdc.sellhouse.IBasePriceControlBuildingEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IBasePriceControlBuildingEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBasePriceControlBuildingEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4C5347DE"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IBasePriceControlBuildingEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IBasePriceControlBuildingEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4C5347DE"));
    }
}