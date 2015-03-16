package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class ShareSaleMan extends CoreBase implements IShareSaleMan
{
    public ShareSaleMan()
    {
        super();
        registerInterface(IShareSaleMan.class, this);
    }
    public ShareSaleMan(Context ctx)
    {
        super(ctx);
        registerInterface(IShareSaleMan.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8E71290F");
    }
    private ShareSaleManController getController() throws BOSException
    {
        return (ShareSaleManController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ShareSaleManInfo getShareSaleManInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getShareSaleManInfo(getContext(), pk);
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
    public ShareSaleManInfo getShareSaleManInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getShareSaleManInfo(getContext(), pk, selector);
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
    public ShareSaleManInfo getShareSaleManInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getShareSaleManInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ShareSaleManCollection getShareSaleManCollection() throws BOSException
    {
        try {
            return getController().getShareSaleManCollection(getContext());
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
    public ShareSaleManCollection getShareSaleManCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getShareSaleManCollection(getContext(), view);
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
    public ShareSaleManCollection getShareSaleManCollection(String oql) throws BOSException
    {
        try {
            return getController().getShareSaleManCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}