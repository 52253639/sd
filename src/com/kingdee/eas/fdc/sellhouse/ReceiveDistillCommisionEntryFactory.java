package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ReceiveDistillCommisionEntryFactory
{
    private ReceiveDistillCommisionEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IReceiveDistillCommisionEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceiveDistillCommisionEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E97BCCDB") ,com.kingdee.eas.fdc.sellhouse.IReceiveDistillCommisionEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IReceiveDistillCommisionEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceiveDistillCommisionEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E97BCCDB") ,com.kingdee.eas.fdc.sellhouse.IReceiveDistillCommisionEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IReceiveDistillCommisionEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceiveDistillCommisionEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E97BCCDB"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IReceiveDistillCommisionEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IReceiveDistillCommisionEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E97BCCDB"));
    }
}