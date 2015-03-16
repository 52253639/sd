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
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class PolicyManage extends CoreBillBase implements IPolicyManage
{
    public PolicyManage()
    {
        super();
        registerInterface(IPolicyManage.class, this);
    }
    public PolicyManage(Context ctx)
    {
        super(ctx);
        registerInterface(IPolicyManage.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F13F3588");
    }
    private PolicyManageController getController() throws BOSException
    {
        return (PolicyManageController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PolicyManageCollection getPolicyManageCollection() throws BOSException
    {
        try {
            return getController().getPolicyManageCollection(getContext());
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
    public PolicyManageCollection getPolicyManageCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPolicyManageCollection(getContext(), view);
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
    public PolicyManageCollection getPolicyManageCollection(String oql) throws BOSException
    {
        try {
            return getController().getPolicyManageCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PolicyManageInfo getPolicyManageInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPolicyManageInfo(getContext(), pk);
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
    public PolicyManageInfo getPolicyManageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPolicyManageInfo(getContext(), pk, selector);
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
    public PolicyManageInfo getPolicyManageInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPolicyManageInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}