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

public class AreaCompensateRevList extends RevList implements IAreaCompensateRevList
{
    public AreaCompensateRevList()
    {
        super();
        registerInterface(IAreaCompensateRevList.class, this);
    }
    public AreaCompensateRevList(Context ctx)
    {
        super(ctx);
        registerInterface(IAreaCompensateRevList.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("179EB1A2");
    }
    private AreaCompensateRevListController getController() throws BOSException
    {
        return (AreaCompensateRevListController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public AreaCompensateRevListInfo getAreaCompensateRevListInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAreaCompensateRevListInfo(getContext(), pk);
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
    public AreaCompensateRevListInfo getAreaCompensateRevListInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAreaCompensateRevListInfo(getContext(), pk, selector);
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
    public AreaCompensateRevListInfo getAreaCompensateRevListInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAreaCompensateRevListInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public AreaCompensateRevListCollection getAreaCompensateRevListCollection() throws BOSException
    {
        try {
            return getController().getAreaCompensateRevListCollection(getContext());
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
    public AreaCompensateRevListCollection getAreaCompensateRevListCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAreaCompensateRevListCollection(getContext(), view);
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
    public AreaCompensateRevListCollection getAreaCompensateRevListCollection(String oql) throws BOSException
    {
        try {
            return getController().getAreaCompensateRevListCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}