package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class ReceptionType extends FDCDataBase implements IReceptionType
{
    public ReceptionType()
    {
        super();
        registerInterface(IReceptionType.class, this);
    }
    public ReceptionType(Context ctx)
    {
        super(ctx);
        registerInterface(IReceptionType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6BF4F7AE");
    }
    private ReceptionTypeController getController() throws BOSException
    {
        return (ReceptionTypeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ReceptionTypeInfo getReceptionTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getReceptionTypeInfo(getContext(), pk);
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
    public ReceptionTypeInfo getReceptionTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getReceptionTypeInfo(getContext(), pk, selector);
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
    public ReceptionTypeInfo getReceptionTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getReceptionTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ReceptionTypeCollection getReceptionTypeCollection() throws BOSException
    {
        try {
            return getController().getReceptionTypeCollection(getContext());
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
    public ReceptionTypeCollection getReceptionTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getReceptionTypeCollection(getContext(), view);
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
    public ReceptionTypeCollection getReceptionTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getReceptionTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}