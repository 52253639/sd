package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PropertyDoSchemeEntryFactory
{
    private PropertyDoSchemeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5614BC72") ,com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5614BC72") ,com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5614BC72"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5614BC72"));
    }
}