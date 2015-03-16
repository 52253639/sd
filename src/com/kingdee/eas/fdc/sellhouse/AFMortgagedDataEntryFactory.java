package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AFMortgagedDataEntryFactory
{
    private AFMortgagedDataEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.IAFMortgagedDataEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAFMortgagedDataEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EDB8F0C0") ,com.kingdee.eas.fdc.sellhouse.IAFMortgagedDataEntry.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.IAFMortgagedDataEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAFMortgagedDataEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EDB8F0C0") ,com.kingdee.eas.fdc.sellhouse.IAFMortgagedDataEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.IAFMortgagedDataEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAFMortgagedDataEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EDB8F0C0"));
    }
    public static com.kingdee.eas.fdc.sellhouse.IAFMortgagedDataEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.IAFMortgagedDataEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EDB8F0C0"));
    }
}