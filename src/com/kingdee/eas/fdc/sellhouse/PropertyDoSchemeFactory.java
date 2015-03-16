package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PropertyDoSchemeFactory
{
    private PropertyDoSchemeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPropertyDoScheme getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPropertyDoScheme)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C65D47E0") ,com.kingdee.eas.fdc.sellhouse.IPropertyDoScheme.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPropertyDoScheme getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPropertyDoScheme)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C65D47E0") ,com.kingdee.eas.fdc.sellhouse.IPropertyDoScheme.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPropertyDoScheme getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPropertyDoScheme)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C65D47E0"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPropertyDoScheme getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPropertyDoScheme)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C65D47E0"));
    }
}