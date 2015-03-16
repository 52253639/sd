package com.kingdee.eas.basedata.master.auxacct;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AssistantHGFactory
{
    private AssistantHGFactory()
    {
    }
    public static com.kingdee.eas.basedata.master.auxacct.IAssistantHG getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.basedata.master.auxacct.IAssistantHG)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("418A6CBB") ,com.kingdee.eas.basedata.master.auxacct.IAssistantHG.class);
    }
    
    public static com.kingdee.eas.basedata.master.auxacct.IAssistantHG getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.basedata.master.auxacct.IAssistantHG)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("418A6CBB") ,com.kingdee.eas.basedata.master.auxacct.IAssistantHG.class, objectCtx);
    }
    public static com.kingdee.eas.basedata.master.auxacct.IAssistantHG getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.basedata.master.auxacct.IAssistantHG)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("418A6CBB"));
    }
    public static com.kingdee.eas.basedata.master.auxacct.IAssistantHG getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.basedata.master.auxacct.IAssistantHG)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("418A6CBB"));
    }
}