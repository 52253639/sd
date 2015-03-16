package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewListingTempletDistributFacadeFactory
{
    private NewListingTempletDistributFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.INewListingTempletDistributFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingTempletDistributFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7044C0FF") ,com.kingdee.eas.fdc.invite.INewListingTempletDistributFacade.class);
    }
    
    public static com.kingdee.eas.fdc.invite.INewListingTempletDistributFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingTempletDistributFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7044C0FF") ,com.kingdee.eas.fdc.invite.INewListingTempletDistributFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.INewListingTempletDistributFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingTempletDistributFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7044C0FF"));
    }
    public static com.kingdee.eas.fdc.invite.INewListingTempletDistributFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.INewListingTempletDistributFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7044C0FF"));
    }
}