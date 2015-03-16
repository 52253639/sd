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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class ShareSellProject extends DataBase implements IShareSellProject
{
    public ShareSellProject()
    {
        super();
        registerInterface(IShareSellProject.class, this);
    }
    public ShareSellProject(Context ctx)
    {
        super(ctx);
        registerInterface(IShareSellProject.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("94CAEAE3");
    }
    private ShareSellProjectController getController() throws BOSException
    {
        return (ShareSellProjectController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ShareSellProjectInfo getShareSellProjectInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getShareSellProjectInfo(getContext(), pk);
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
    public ShareSellProjectInfo getShareSellProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getShareSellProjectInfo(getContext(), pk, selector);
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
    public ShareSellProjectInfo getShareSellProjectInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getShareSellProjectInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ShareSellProjectCollection getShareSellProjectCollection() throws BOSException
    {
        try {
            return getController().getShareSellProjectCollection(getContext());
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
    public ShareSellProjectCollection getShareSellProjectCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getShareSellProjectCollection(getContext(), view);
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
    public ShareSellProjectCollection getShareSellProjectCollection(String oql) throws BOSException
    {
        try {
            return getController().getShareSellProjectCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}