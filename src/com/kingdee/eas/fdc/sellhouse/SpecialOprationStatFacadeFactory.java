package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SpecialOprationStatFacadeFactory
{
    private SpecialOprationStatFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISpecialOprationStatFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISpecialOprationStatFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3D8B4C30") ,com.kingdee.eas.fdc.sellhouse.ISpecialOprationStatFacade.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISpecialOprationStatFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISpecialOprationStatFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3D8B4C30") ,com.kingdee.eas.fdc.sellhouse.ISpecialOprationStatFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISpecialOprationStatFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISpecialOprationStatFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3D8B4C30"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISpecialOprationStatFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISpecialOprationStatFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3D8B4C30"));
    }
}