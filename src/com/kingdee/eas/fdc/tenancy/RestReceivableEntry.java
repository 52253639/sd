package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class RestReceivableEntry extends CoreBillEntryBase implements IRestReceivableEntry
{
    public RestReceivableEntry()
    {
        super();
        registerInterface(IRestReceivableEntry.class, this);
    }
    public RestReceivableEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IRestReceivableEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3BC2CCF9");
    }
    private RestReceivableEntryController getController() throws BOSException
    {
        return (RestReceivableEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public RestReceivableEntryInfo getRestReceivableEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRestReceivableEntryInfo(getContext(), pk);
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
    public RestReceivableEntryInfo getRestReceivableEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRestReceivableEntryInfo(getContext(), pk, selector);
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
    public RestReceivableEntryInfo getRestReceivableEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRestReceivableEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RestReceivableEntryCollection getRestReceivableEntryCollection() throws BOSException
    {
        try {
            return getController().getRestReceivableEntryCollection(getContext());
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
    public RestReceivableEntryCollection getRestReceivableEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRestReceivableEntryCollection(getContext(), view);
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
    public RestReceivableEntryCollection getRestReceivableEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getRestReceivableEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}