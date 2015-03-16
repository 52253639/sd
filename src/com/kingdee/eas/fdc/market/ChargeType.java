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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;
import com.kingdee.eas.framework.TreeBase;

public class ChargeType extends TreeBase implements IChargeType
{
    public ChargeType()
    {
        super();
        registerInterface(IChargeType.class, this);
    }
    public ChargeType(Context ctx)
    {
        super(ctx);
        registerInterface(IChargeType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AAF3C09F");
    }
    private ChargeTypeController getController() throws BOSException
    {
        return (ChargeTypeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ChargeTypeInfo getChargeTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getChargeTypeInfo(getContext(), pk);
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
    public ChargeTypeInfo getChargeTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getChargeTypeInfo(getContext(), pk, selector);
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
    public ChargeTypeInfo getChargeTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getChargeTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ChargeTypeCollection getChargeTypeCollection() throws BOSException
    {
        try {
            return getController().getChargeTypeCollection(getContext());
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
    public ChargeTypeCollection getChargeTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getChargeTypeCollection(getContext(), view);
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
    public ChargeTypeCollection getChargeTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getChargeTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}