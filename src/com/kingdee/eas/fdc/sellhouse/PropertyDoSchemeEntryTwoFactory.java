package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PropertyDoSchemeEntryTwoFactory
{
    private PropertyDoSchemeEntryTwoFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntryTwo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntryTwo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5716E07A") ,com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntryTwo.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntryTwo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntryTwo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5716E07A") ,com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntryTwo.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntryTwo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntryTwo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5716E07A"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntryTwo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IPropertyDoSchemeEntryTwo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5716E07A"));
    }
}