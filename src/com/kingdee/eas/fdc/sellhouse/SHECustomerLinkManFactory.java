package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHECustomerLinkManFactory
{
    private SHECustomerLinkManFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHECustomerLinkMan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHECustomerLinkMan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4ED0F48D") ,com.kingdee.eas.fdc.sellhouse.ISHECustomerLinkMan.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHECustomerLinkMan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHECustomerLinkMan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4ED0F48D") ,com.kingdee.eas.fdc.sellhouse.ISHECustomerLinkMan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHECustomerLinkMan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHECustomerLinkMan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4ED0F48D"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHECustomerLinkMan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHECustomerLinkMan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4ED0F48D"));
    }
}