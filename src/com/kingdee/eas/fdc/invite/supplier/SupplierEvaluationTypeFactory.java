package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierEvaluationTypeFactory
{
    private SupplierEvaluationTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B357DDBA") ,com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationType.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B357DDBA") ,com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B357DDBA"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.ISupplierEvaluationType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B357DDBA"));
    }
}