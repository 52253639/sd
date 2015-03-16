package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingContractEconomyFactory
{
    private ProgrammingContractEconomyFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingContractEconomy getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingContractEconomy)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("144467E3") ,com.kingdee.eas.fdc.contract.programming.IProgrammingContractEconomy.class);
    }
    
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingContractEconomy getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingContractEconomy)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("144467E3") ,com.kingdee.eas.fdc.contract.programming.IProgrammingContractEconomy.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingContractEconomy getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingContractEconomy)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("144467E3"));
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingContractEconomy getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingContractEconomy)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("144467E3"));
    }
}