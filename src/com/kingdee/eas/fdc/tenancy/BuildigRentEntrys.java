package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class BuildigRentEntrys extends DataBase implements IBuildigRentEntrys
{
    public BuildigRentEntrys()
    {
        super();
        registerInterface(IBuildigRentEntrys.class, this);
    }
    public BuildigRentEntrys(Context ctx)
    {
        super(ctx);
        registerInterface(IBuildigRentEntrys.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F925217D");
    }
    private BuildigRentEntrysController getController() throws BOSException
    {
        return (BuildigRentEntrysController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public BuildigRentEntrysInfo getBuildigRentEntrysInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildigRentEntrysInfo(getContext(), pk);
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
    public BuildigRentEntrysInfo getBuildigRentEntrysInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildigRentEntrysInfo(getContext(), pk, selector);
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
    public BuildigRentEntrysInfo getBuildigRentEntrysInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildigRentEntrysInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BuildigRentEntrysCollection getBuildigRentEntrysCollection() throws BOSException
    {
        try {
            return getController().getBuildigRentEntrysCollection(getContext());
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
    public BuildigRentEntrysCollection getBuildigRentEntrysCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBuildigRentEntrysCollection(getContext(), view);
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
    public BuildigRentEntrysCollection getBuildigRentEntrysCollection(String oql) throws BOSException
    {
        try {
            return getController().getBuildigRentEntrysCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}