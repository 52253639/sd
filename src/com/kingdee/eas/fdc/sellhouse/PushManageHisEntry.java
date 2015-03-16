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

public class PushManageHisEntry extends DataBase implements IPushManageHisEntry
{
    public PushManageHisEntry()
    {
        super();
        registerInterface(IPushManageHisEntry.class, this);
    }
    public PushManageHisEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPushManageHisEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9995485A");
    }
    private PushManageHisEntryController getController() throws BOSException
    {
        return (PushManageHisEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public PushManageHisEntryInfo getPushManageHisEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPushManageHisEntryInfo(getContext(), oql);
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
    public PushManageHisEntryInfo getPushManageHisEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPushManageHisEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PushManageHisEntryInfo getPushManageHisEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPushManageHisEntryInfo(getContext(), pk);
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
    public PushManageHisEntryCollection getPushManageHisEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPushManageHisEntryCollection(getContext(), oql);
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
    public PushManageHisEntryCollection getPushManageHisEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPushManageHisEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PushManageHisEntryCollection getPushManageHisEntryCollection() throws BOSException
    {
        try {
            return getController().getPushManageHisEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}