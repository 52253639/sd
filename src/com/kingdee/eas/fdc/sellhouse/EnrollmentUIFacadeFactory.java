package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EnrollmentUIFacadeFactory
{
    private EnrollmentUIFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IEnrollmentUIFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEnrollmentUIFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("27DF0D6D") ,com.kingdee.eas.fdc.sellhouse.IEnrollmentUIFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IEnrollmentUIFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEnrollmentUIFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("27DF0D6D") ,com.kingdee.eas.fdc.sellhouse.IEnrollmentUIFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IEnrollmentUIFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEnrollmentUIFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("27DF0D6D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IEnrollmentUIFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IEnrollmentUIFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("27DF0D6D"));
    }
}