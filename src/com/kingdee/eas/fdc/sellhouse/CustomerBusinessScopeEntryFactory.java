package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CustomerBusinessScopeEntryFactory
{
    private CustomerBusinessScopeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerBusinessScopeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerBusinessScopeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F94C15F7") ,com.kingdee.eas.fdc.sellhouse.ICustomerBusinessScopeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ICustomerBusinessScopeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerBusinessScopeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F94C15F7") ,com.kingdee.eas.fdc.sellhouse.ICustomerBusinessScopeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerBusinessScopeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerBusinessScopeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F94C15F7"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ICustomerBusinessScopeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ICustomerBusinessScopeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F94C15F7"));
    }
}