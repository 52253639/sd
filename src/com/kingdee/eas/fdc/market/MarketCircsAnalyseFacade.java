package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.framework.bireport.BireportBaseFacade;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;

public class MarketCircsAnalyseFacade extends BireportBaseFacade implements IMarketCircsAnalyseFacade
{
    public MarketCircsAnalyseFacade()
    {
        super();
        registerInterface(IMarketCircsAnalyseFacade.class, this);
    }
    public MarketCircsAnalyseFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketCircsAnalyseFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E9153A3E");
    }
    private MarketCircsAnalyseFacadeController getController() throws BOSException
    {
        return (MarketCircsAnalyseFacadeController)getBizController();
    }
}