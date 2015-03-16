package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FDCProDepSplitNoConFactory
{
    private FDCProDepSplitNoConFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitNoCon getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitNoCon)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("056F90DF") ,com.kingdee.eas.fdc.finance.IFDCProDepSplitNoCon.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitNoCon getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitNoCon)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("056F90DF") ,com.kingdee.eas.fdc.finance.IFDCProDepSplitNoCon.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitNoCon getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitNoCon)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("056F90DF"));
    }
    public static com.kingdee.eas.fdc.finance.IFDCProDepSplitNoCon getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFDCProDepSplitNoCon)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("056F90DF"));
    }
}