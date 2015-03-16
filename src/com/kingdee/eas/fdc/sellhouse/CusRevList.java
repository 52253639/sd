package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basecrm.IRevList;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basecrm.RevList;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class CusRevList extends RevList implements ICusRevList
{
    public CusRevList()
    {
        super();
        registerInterface(ICusRevList.class, this);
    }
    public CusRevList(Context ctx)
    {
        super(ctx);
        registerInterface(ICusRevList.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C64ADF7B");
    }
    private CusRevListController getController() throws BOSException
    {
        return (CusRevListController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public CusRevListInfo getCusRevListInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCusRevListInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public CusRevListInfo getCusRevListInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCusRevListInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public CusRevListInfo getCusRevListInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCusRevListInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public CusRevListCollection getCusRevListCollection() throws BOSException
    {
        try {
            return getController().getCusRevListCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view view
     *@return
     */
    public CusRevListCollection getCusRevListCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCusRevListCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public CusRevListCollection getCusRevListCollection(String oql) throws BOSException
    {
        try {
            return getController().getCusRevListCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}