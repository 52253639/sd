package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class FDCSplChangeHistroy extends FDCBill implements IFDCSplChangeHistroy
{
    public FDCSplChangeHistroy()
    {
        super();
        registerInterface(IFDCSplChangeHistroy.class, this);
    }
    public FDCSplChangeHistroy(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSplChangeHistroy.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("308F4088");
    }
    private FDCSplChangeHistroyController getController() throws BOSException
    {
        return (FDCSplChangeHistroyController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCSplChangeHistroyInfo getFDCSplChangeHistroyInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplChangeHistroyInfo(getContext(), pk);
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
    public FDCSplChangeHistroyInfo getFDCSplChangeHistroyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplChangeHistroyInfo(getContext(), pk, selector);
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
    public FDCSplChangeHistroyInfo getFDCSplChangeHistroyInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplChangeHistroyInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCSplChangeHistroyCollection getFDCSplChangeHistroyCollection() throws BOSException
    {
        try {
            return getController().getFDCSplChangeHistroyCollection(getContext());
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
    public FDCSplChangeHistroyCollection getFDCSplChangeHistroyCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCSplChangeHistroyCollection(getContext(), view);
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
    public FDCSplChangeHistroyCollection getFDCSplChangeHistroyCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCSplChangeHistroyCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}