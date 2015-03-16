package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierAppraiseTemplateFactory
{
    private SupplierAppraiseTemplateFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseTemplate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseTemplate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("218F44EF") ,com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseTemplate.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseTemplate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseTemplate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("218F44EF") ,com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseTemplate.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseTemplate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseTemplate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("218F44EF"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseTemplate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierAppraiseTemplate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("218F44EF"));
    }
}