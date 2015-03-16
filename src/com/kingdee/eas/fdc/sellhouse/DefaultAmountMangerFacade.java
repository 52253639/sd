package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import java.util.Set;

public class DefaultAmountMangerFacade extends AbstractBizCtrl implements IDefaultAmountMangerFacade
{
    public DefaultAmountMangerFacade()
    {
        super();
        registerInterface(IDefaultAmountMangerFacade.class, this);
    }
    public DefaultAmountMangerFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IDefaultAmountMangerFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5FEC5712");
    }
    private DefaultAmountMangerFacadeController getController() throws BOSException
    {
        return (DefaultAmountMangerFacadeController)getBizController();
    }
    /**
     *获取打印信息-User defined method
     *@param idSet id集合
     *@return
     */
    public IRowSet getPrintData(Set idSet) throws BOSException
    {
        try {
            return getController().getPrintData(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}