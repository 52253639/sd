package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FirstPayProportionFactory
{
    private FirstPayProportionFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IFirstPayProportion getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFirstPayProportion)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("186CA0AD") ,com.kingdee.eas.fdc.sellhouse.IFirstPayProportion.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IFirstPayProportion getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFirstPayProportion)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("186CA0AD") ,com.kingdee.eas.fdc.sellhouse.IFirstPayProportion.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IFirstPayProportion getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFirstPayProportion)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("186CA0AD"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IFirstPayProportion getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IFirstPayProportion)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("186CA0AD"));
    }
}