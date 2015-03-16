package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.framework.bireport.BireportBaseFacade;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;

public class RptBarginOnAnalyseFacade extends BireportBaseFacade implements IRptBarginOnAnalyseFacade
{
    public RptBarginOnAnalyseFacade()
    {
        super();
        registerInterface(IRptBarginOnAnalyseFacade.class, this);
    }
    public RptBarginOnAnalyseFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IRptBarginOnAnalyseFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AC781DB0");
    }
    private RptBarginOnAnalyseFacadeController getController() throws BOSException
    {
        return (RptBarginOnAnalyseFacadeController)getBizController();
    }
    /**
     *认购单过滤条件的Sql-User defined method
     *@return
     */
    public String getPurchaseQuerySql() throws BOSException
    {
        try {
            return getController().getPurchaseQuerySql(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}