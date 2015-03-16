package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HousingRentalFacadeFactory
{
    private HousingRentalFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IHousingRentalFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IHousingRentalFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3BE2EF12") ,com.kingdee.eas.fdc.tenancy.IHousingRentalFacade.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IHousingRentalFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IHousingRentalFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3BE2EF12") ,com.kingdee.eas.fdc.tenancy.IHousingRentalFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IHousingRentalFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IHousingRentalFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3BE2EF12"));
    }
    public static com.kingdee.eas.fdc.tenancy.IHousingRentalFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IHousingRentalFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3BE2EF12"));
    }
}