package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCSplAreaFactory
{
    private FDCSplAreaFactory()
    {
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplArea getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplArea)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DDF72BEF") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplArea.class);
    }
    
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplArea getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplArea)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DDF72BEF") ,com.kingdee.eas.fdc.invite.supplier.IFDCSplArea.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplArea getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplArea)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DDF72BEF"));
    }
    public static com.kingdee.eas.fdc.invite.supplier.IFDCSplArea getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.invite.supplier.IFDCSplArea)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DDF72BEF"));
    }
}