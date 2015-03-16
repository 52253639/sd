package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.framework.bireport.BireportBaseFacade;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;

public class RptCommercialAnalyseFacade extends BireportBaseFacade implements IRptCommercialAnalyseFacade
{
    public RptCommercialAnalyseFacade()
    {
        super();
        registerInterface(IRptCommercialAnalyseFacade.class, this);
    }
    public RptCommercialAnalyseFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IRptCommercialAnalyseFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7773B808");
    }
    private RptCommercialAnalyseFacadeController getController() throws BOSException
    {
        return (RptCommercialAnalyseFacadeController)getBizController();
    }
}