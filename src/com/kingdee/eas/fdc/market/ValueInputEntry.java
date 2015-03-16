package com.kingdee.eas.fdc.market;

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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;

public class ValueInputEntry extends CoreBillEntryBase implements IValueInputEntry
{
    public ValueInputEntry()
    {
        super();
        registerInterface(IValueInputEntry.class, this);
    }
    public ValueInputEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IValueInputEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("67F44128");
    }
    private ValueInputEntryController getController() throws BOSException
    {
        return (ValueInputEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ValueInputEntryInfo getValueInputEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getValueInputEntryInfo(getContext(), pk);
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
    public ValueInputEntryInfo getValueInputEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getValueInputEntryInfo(getContext(), pk, selector);
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
    public ValueInputEntryInfo getValueInputEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getValueInputEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ValueInputEntryCollection getValueInputEntryCollection() throws BOSException
    {
        try {
            return getController().getValueInputEntryCollection(getContext());
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
    public ValueInputEntryCollection getValueInputEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getValueInputEntryCollection(getContext(), view);
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
    public ValueInputEntryCollection getValueInputEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getValueInputEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}