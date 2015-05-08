package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.eas.fdc.basedata.FDCDataBase;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;

public class GlobalTaskNode extends FDCDataBase implements IGlobalTaskNode
{
    public GlobalTaskNode()
    {
        super();
        registerInterface(IGlobalTaskNode.class, this);
    }
    public GlobalTaskNode(Context ctx)
    {
        super(ctx);
        registerInterface(IGlobalTaskNode.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1F89DF40");
    }
    private GlobalTaskNodeController getController() throws BOSException
    {
        return (GlobalTaskNodeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public GlobalTaskNodeInfo getGlobalTaskNodeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getGlobalTaskNodeInfo(getContext(), pk);
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
    public GlobalTaskNodeInfo getGlobalTaskNodeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getGlobalTaskNodeInfo(getContext(), pk, selector);
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
    public GlobalTaskNodeInfo getGlobalTaskNodeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getGlobalTaskNodeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *addnew-System defined method
     *@param model model
     *@return
     */
    public IObjectPK addnew(GlobalTaskNodeInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().addnew(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param filter filter
     *@return
     */
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param oql oql
     *@return
     */
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param arrayPK arrayPK
     */
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), arrayPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param pk pk
     */
    public void delete(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public GlobalTaskNodeCollection getGlobalTaskNodeCollection() throws BOSException
    {
        try {
            return getController().getGlobalTaskNodeCollection(getContext());
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
    public GlobalTaskNodeCollection getGlobalTaskNodeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getGlobalTaskNodeCollection(getContext(), view);
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
    public GlobalTaskNodeCollection getGlobalTaskNodeCollection(String oql) throws BOSException
    {
        try {
            return getController().getGlobalTaskNodeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}