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

public class DeveloperManage extends PPMDataBase implements IDeveloperManage
{
    public DeveloperManage()
    {
        super();
        registerInterface(IDeveloperManage.class, this);
    }
    public DeveloperManage(Context ctx)
    {
        super(ctx);
        registerInterface(IDeveloperManage.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9B500FDE");
    }
    private DeveloperManageController getController() throws BOSException
    {
        return (DeveloperManageController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public DeveloperManageInfo getDeveloperManageInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDeveloperManageInfo(getContext(), pk);
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
    public DeveloperManageInfo getDeveloperManageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDeveloperManageInfo(getContext(), pk, selector);
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
    public DeveloperManageInfo getDeveloperManageInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDeveloperManageInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public DeveloperManageCollection getDeveloperManageCollection() throws BOSException
    {
        try {
            return getController().getDeveloperManageCollection(getContext());
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
    public DeveloperManageCollection getDeveloperManageCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDeveloperManageCollection(getContext(), view);
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
    public DeveloperManageCollection getDeveloperManageCollection(String oql) throws BOSException
    {
        try {
            return getController().getDeveloperManageCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}