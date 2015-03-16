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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class RevList extends CoreBase implements IRevList
{
    public RevList()
    {
        super();
        registerInterface(IRevList.class, this);
    }
    public RevList(Context ctx)
    {
        super(ctx);
        registerInterface(IRevList.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E004F905");
    }
    private RevListController getController() throws BOSException
    {
        return (RevListController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public RevListInfo getRevListInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRevListInfo(getContext(), pk);
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
    public RevListInfo getRevListInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRevListInfo(getContext(), pk, selector);
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
    public RevListInfo getRevListInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRevListInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RevListCollection getRevListCollection() throws BOSException
    {
        try {
            return getController().getRevListCollection(getContext());
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
    public RevListCollection getRevListCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRevListCollection(getContext(), view);
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
    public RevListCollection getRevListCollection(String oql) throws BOSException
    {
        try {
            return getController().getRevListCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}