package com.kingdee.eas.fdc.market;

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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.propertymgmt.PPMDataBase;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;
import com.kingdee.eas.fdc.propertymgmt.IPPMDataBase;

public class FlatLayerType extends PPMDataBase implements IFlatLayerType
{
    public FlatLayerType()
    {
        super();
        registerInterface(IFlatLayerType.class, this);
    }
    public FlatLayerType(Context ctx)
    {
        super(ctx);
        registerInterface(IFlatLayerType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F71E9681");
    }
    private FlatLayerTypeController getController() throws BOSException
    {
        return (FlatLayerTypeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public FlatLayerTypeInfo getFlatLayerTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFlatLayerTypeInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public FlatLayerTypeInfo getFlatLayerTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFlatLayerTypeInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public FlatLayerTypeInfo getFlatLayerTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFlatLayerTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public FlatLayerTypeCollection getFlatLayerTypeCollection() throws BOSException
    {
        try {
            return getController().getFlatLayerTypeCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public FlatLayerTypeCollection getFlatLayerTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFlatLayerTypeCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public FlatLayerTypeCollection getFlatLayerTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getFlatLayerTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}