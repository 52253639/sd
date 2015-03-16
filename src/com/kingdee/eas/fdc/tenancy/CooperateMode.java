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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class CooperateMode extends TreeBase implements ICooperateMode
{
    public CooperateMode()
    {
        super();
        registerInterface(ICooperateMode.class, this);
    }
    public CooperateMode(Context ctx)
    {
        super(ctx);
        registerInterface(ICooperateMode.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D0889652");
    }
    private CooperateModeController getController() throws BOSException
    {
        return (CooperateModeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public CooperateModeInfo getCooperateModeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCooperateModeInfo(getContext(), pk);
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
    public CooperateModeInfo getCooperateModeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCooperateModeInfo(getContext(), pk, selector);
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
    public CooperateModeInfo getCooperateModeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCooperateModeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public CooperateModeCollection getCooperateModeCollection() throws BOSException
    {
        try {
            return getController().getCooperateModeCollection(getContext());
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
    public CooperateModeCollection getCooperateModeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCooperateModeCollection(getContext(), view);
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
    public CooperateModeCollection getCooperateModeCollection(String oql) throws BOSException
    {
        try {
            return getController().getCooperateModeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}