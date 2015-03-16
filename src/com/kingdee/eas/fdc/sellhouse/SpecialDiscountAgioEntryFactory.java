package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SpecialDiscountAgioEntryFactory
{
    private SpecialDiscountAgioEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISpecialDiscountAgioEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISpecialDiscountAgioEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6A8F21E7") ,com.kingdee.eas.fdc.sellhouse.ISpecialDiscountAgioEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISpecialDiscountAgioEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISpecialDiscountAgioEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6A8F21E7") ,com.kingdee.eas.fdc.sellhouse.ISpecialDiscountAgioEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISpecialDiscountAgioEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISpecialDiscountAgioEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6A8F21E7"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISpecialDiscountAgioEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISpecialDiscountAgioEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6A8F21E7"));
    }
}