package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import java.util.Set;

public class SettleMentFacade extends AbstractBizCtrl implements ISettleMentFacade
{
    public SettleMentFacade()
    {
        super();
        registerInterface(ISettleMentFacade.class, this);
    }
    public SettleMentFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISettleMentFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D528101E");
    }
    private SettleMentFacadeController getController() throws BOSException
    {
        return (SettleMentFacadeController)getBizController();
    }
    /**
     *退房处理-User defined method
     *@param pk pk
     *@return
     */
    public Set dealQuitRoom(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().dealQuitRoom(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *换房处理-User defined method
     *@param pk pk
     *@return
     */
    public Set dealChangeRoom(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().dealChangeRoom(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *销售月结处理-User defined method
     *@param pk 销售结算pk
     */
    public void dealSaleBalance(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().dealSaleBalance(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反销售月结处理-User defined method
     *@param pk 销售结算pk
     */
    public void dealAntiSaleBalance(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().dealAntiSaleBalance(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}