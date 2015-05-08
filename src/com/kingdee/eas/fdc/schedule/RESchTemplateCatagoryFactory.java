package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RESchTemplateCatagoryFactory
{
    private RESchTemplateCatagoryFactory()
    {
    }
    public static com.kingdee.eas.fdc.schedule.IRESchTemplateCatagory getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplateCatagory)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F425DEA3") ,com.kingdee.eas.fdc.schedule.IRESchTemplateCatagory.class);
    }
    
    public static com.kingdee.eas.fdc.schedule.IRESchTemplateCatagory getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplateCatagory)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F425DEA3") ,com.kingdee.eas.fdc.schedule.IRESchTemplateCatagory.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.schedule.IRESchTemplateCatagory getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplateCatagory)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F425DEA3"));
    }
    public static com.kingdee.eas.fdc.schedule.IRESchTemplateCatagory getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.schedule.IRESchTemplateCatagory)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F425DEA3"));
    }
}