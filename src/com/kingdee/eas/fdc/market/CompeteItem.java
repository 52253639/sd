package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.propertymgmt.IPPMProjectDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.propertymgmt.PPMProjectDataBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;

public class CompeteItem extends PPMProjectDataBase implements ICompeteItem
{
    public CompeteItem()
    {
        super();
        registerInterface(ICompeteItem.class, this);
    }
    public CompeteItem(Context ctx)
    {
        super(ctx);
        registerInterface(ICompeteItem.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("359F4D69");
    }
    private CompeteItemController getController() throws BOSException
    {
        return (CompeteItemController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public CompeteItemInfo getCompeteItemInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCompeteItemInfo(getContext(), pk);
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
    public CompeteItemInfo getCompeteItemInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCompeteItemInfo(getContext(), pk, selector);
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
    public CompeteItemInfo getCompeteItemInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCompeteItemInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public CompeteItemCollection getCompeteItemCollection() throws BOSException
    {
        try {
            return getController().getCompeteItemCollection(getContext());
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
    public CompeteItemCollection getCompeteItemCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCompeteItemCollection(getContext(), view);
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
    public CompeteItemCollection getCompeteItemCollection(String oql) throws BOSException
    {
        try {
            return getController().getCompeteItemCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}