package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PropertyNoticeFactory
{
    private PropertyNoticeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPropertyNotice getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPropertyNotice)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D896B8A8") ,com.kingdee.eas.fdc.sellhouse.IPropertyNotice.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPropertyNotice getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPropertyNotice)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D896B8A8") ,com.kingdee.eas.fdc.sellhouse.IPropertyNotice.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPropertyNotice getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPropertyNotice)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D896B8A8"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPropertyNotice getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPropertyNotice)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D896B8A8"));
    }
}