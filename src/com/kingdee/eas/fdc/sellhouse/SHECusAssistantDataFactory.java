package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SHECusAssistantDataFactory
{
    private SHECusAssistantDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHECusAssistantData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHECusAssistantData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9D3830FC") ,com.kingdee.eas.fdc.sellhouse.ISHECusAssistantData.class);
    }
    
    public static com.kingdee.eas.fdc.sellhouse.ISHECusAssistantData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHECusAssistantData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9D3830FC") ,com.kingdee.eas.fdc.sellhouse.ISHECusAssistantData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHECusAssistantData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHECusAssistantData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9D3830FC"));
    }
    public static com.kingdee.eas.fdc.sellhouse.ISHECusAssistantData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.sellhouse.ISHECusAssistantData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9D3830FC"));
    }
}