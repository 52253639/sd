package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class TranBusinessOverView extends TranPayListEntry implements ITranBusinessOverView
{
    public TranBusinessOverView()
    {
        super();
        registerInterface(ITranBusinessOverView.class, this);
    }
    public TranBusinessOverView(Context ctx)
    {
        super(ctx);
        registerInterface(ITranBusinessOverView.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7FB3321F");
    }
    private TranBusinessOverViewController getController() throws BOSException
    {
        return (TranBusinessOverViewController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TranBusinessOverViewInfo getTranBusinessOverViewInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTranBusinessOverViewInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public TranBusinessOverViewInfo getTranBusinessOverViewInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTranBusinessOverViewInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public TranBusinessOverViewInfo getTranBusinessOverViewInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTranBusinessOverViewInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TranBusinessOverViewCollection getTranBusinessOverViewCollection() throws BOSException
    {
        try {
            return getController().getTranBusinessOverViewCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public TranBusinessOverViewCollection getTranBusinessOverViewCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTranBusinessOverViewCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public TranBusinessOverViewCollection getTranBusinessOverViewCollection(String oql) throws BOSException
    {
        try {
            return getController().getTranBusinessOverViewCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}