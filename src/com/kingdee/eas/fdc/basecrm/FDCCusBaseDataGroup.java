package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basecrm.app.*;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class FDCCusBaseDataGroup extends TreeBase implements IFDCCusBaseDataGroup
{
    public FDCCusBaseDataGroup()
    {
        super();
        registerInterface(IFDCCusBaseDataGroup.class, this);
    }
    public FDCCusBaseDataGroup(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCCusBaseDataGroup.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4851950C");
    }
    private FDCCusBaseDataGroupController getController() throws BOSException
    {
        return (FDCCusBaseDataGroupController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCCusBaseDataGroupInfo getFDCCusBaseDataGroupInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCusBaseDataGroupInfo(getContext(), pk);
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
    public FDCCusBaseDataGroupInfo getFDCCusBaseDataGroupInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCusBaseDataGroupInfo(getContext(), pk, selector);
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
    public FDCCusBaseDataGroupInfo getFDCCusBaseDataGroupInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCusBaseDataGroupInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCCusBaseDataGroupCollection getFDCCusBaseDataGroupCollection() throws BOSException
    {
        try {
            return getController().getFDCCusBaseDataGroupCollection(getContext());
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
    public FDCCusBaseDataGroupCollection getFDCCusBaseDataGroupCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCCusBaseDataGroupCollection(getContext(), view);
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
    public FDCCusBaseDataGroupCollection getFDCCusBaseDataGroupCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCCusBaseDataGroupCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}