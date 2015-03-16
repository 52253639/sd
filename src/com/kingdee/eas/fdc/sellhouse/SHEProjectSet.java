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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class SHEProjectSet extends CoreBase implements ISHEProjectSet
{
    public SHEProjectSet()
    {
        super();
        registerInterface(ISHEProjectSet.class, this);
    }
    public SHEProjectSet(Context ctx)
    {
        super(ctx);
        registerInterface(ISHEProjectSet.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("42F0C45E");
    }
    private SHEProjectSetController getController() throws BOSException
    {
        return (SHEProjectSetController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SHEProjectSetInfo getSHEProjectSetInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEProjectSetInfo(getContext(), pk);
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
    public SHEProjectSetInfo getSHEProjectSetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEProjectSetInfo(getContext(), pk, selector);
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
    public SHEProjectSetInfo getSHEProjectSetInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEProjectSetInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SHEProjectSetCollection getSHEProjectSetCollection() throws BOSException
    {
        try {
            return getController().getSHEProjectSetCollection(getContext());
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
    public SHEProjectSetCollection getSHEProjectSetCollection(String oql) throws BOSException
    {
        try {
            return getController().getSHEProjectSetCollection(getContext(), oql);
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
    public SHEProjectSetCollection getSHEProjectSetCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSHEProjectSetCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}