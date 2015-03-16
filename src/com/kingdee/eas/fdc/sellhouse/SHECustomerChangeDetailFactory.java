package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHECustomerChangeDetailFactory
{
    private SHECustomerChangeDetailFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHECustomerChangeDetail getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHECustomerChangeDetail)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5BC352B4") ,com.kingdee.eas.fdc.sellhouse.ISHECustomerChangeDetail.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHECustomerChangeDetail getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHECustomerChangeDetail)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5BC352B4") ,com.kingdee.eas.fdc.sellhouse.ISHECustomerChangeDetail.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHECustomerChangeDetail getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHECustomerChangeDetail)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5BC352B4"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHECustomerChangeDetail getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHECustomerChangeDetail)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5BC352B4"));
    }
}