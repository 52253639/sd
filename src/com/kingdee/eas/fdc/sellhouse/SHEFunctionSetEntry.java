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

public class SHEFunctionSetEntry extends CoreBase implements ISHEFunctionSetEntry
{
    public SHEFunctionSetEntry()
    {
        super();
        registerInterface(ISHEFunctionSetEntry.class, this);
    }
    public SHEFunctionSetEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISHEFunctionSetEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0871233D");
    }
    private SHEFunctionSetEntryController getController() throws BOSException
    {
        return (SHEFunctionSetEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SHEFunctionSetEntryInfo getSHEFunctionSetEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEFunctionSetEntryInfo(getContext(), pk);
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
    public SHEFunctionSetEntryInfo getSHEFunctionSetEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEFunctionSetEntryInfo(getContext(), pk, selector);
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
    public SHEFunctionSetEntryInfo getSHEFunctionSetEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEFunctionSetEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SHEFunctionSetEntryCollection getSHEFunctionSetEntryCollection() throws BOSException
    {
        try {
            return getController().getSHEFunctionSetEntryCollection(getContext());
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
    public SHEFunctionSetEntryCollection getSHEFunctionSetEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSHEFunctionSetEntryCollection(getContext(), view);
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
    public SHEFunctionSetEntryCollection getSHEFunctionSetEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSHEFunctionSetEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}