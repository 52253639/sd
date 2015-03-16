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

public class ChangeEntry extends CoreBase implements IChangeEntry
{
    public ChangeEntry()
    {
        super();
        registerInterface(IChangeEntry.class, this);
    }
    public ChangeEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IChangeEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D79124E7");
    }
    private ChangeEntryController getController() throws BOSException
    {
        return (ChangeEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ChangeEntryInfo getChangeEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeEntryInfo(getContext(), pk);
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
    public ChangeEntryInfo getChangeEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeEntryInfo(getContext(), pk, selector);
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
    public ChangeEntryInfo getChangeEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeEntryInfo(getContext(), oql);
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
    public ChangeEntryCollection getChangeEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getChangeEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ChangeEntryCollection getChangeEntryCollection() throws BOSException
    {
        try {
            return getController().getChangeEntryCollection(getContext());
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
    public ChangeEntryCollection getChangeEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getChangeEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}