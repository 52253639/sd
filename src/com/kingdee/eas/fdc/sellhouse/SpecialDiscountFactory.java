package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SpecialDiscountFactory
{
    private SpecialDiscountFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISpecialDiscount getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISpecialDiscount)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6012F8DF") ,com.kingdee.eas.fdc.sellhouse.ISpecialDiscount.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISpecialDiscount getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISpecialDiscount)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6012F8DF") ,com.kingdee.eas.fdc.sellhouse.ISpecialDiscount.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISpecialDiscount getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISpecialDiscount)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6012F8DF"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISpecialDiscount getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISpecialDiscount)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6012F8DF"));
    }
}