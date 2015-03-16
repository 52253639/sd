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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class SHEFunctionSet extends DataBase implements ISHEFunctionSet
{
    public SHEFunctionSet()
    {
        super();
        registerInterface(ISHEFunctionSet.class, this);
    }
    public SHEFunctionSet(Context ctx)
    {
        super(ctx);
        registerInterface(ISHEFunctionSet.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DF288E35");
    }
    private SHEFunctionSetController getController() throws BOSException
    {
        return (SHEFunctionSetController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SHEFunctionSetInfo getSHEFunctionSetInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEFunctionSetInfo(getContext(), pk);
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
    public SHEFunctionSetInfo getSHEFunctionSetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEFunctionSetInfo(getContext(), pk, selector);
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
    public SHEFunctionSetInfo getSHEFunctionSetInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEFunctionSetInfo(getContext(), oql);
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
    public SHEFunctionSetCollection getSHEFunctionSetCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSHEFunctionSetCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SHEFunctionSetCollection getSHEFunctionSetCollection() throws BOSException
    {
        try {
            return getController().getSHEFunctionSetCollection(getContext());
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
    public SHEFunctionSetCollection getSHEFunctionSetCollection(String oql) throws BOSException
    {
        try {
            return getController().getSHEFunctionSetCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}