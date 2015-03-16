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

public class ValueInputPriceEntry extends CoreBillEntryBase implements IValueInputPriceEntry
{
    public ValueInputPriceEntry()
    {
        super();
        registerInterface(IValueInputPriceEntry.class, this);
    }
    public ValueInputPriceEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IValueInputPriceEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C1BC95B3");
    }
    private ValueInputPriceEntryController getController() throws BOSException
    {
        return (ValueInputPriceEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ValueInputPriceEntryInfo getValueInputPriceEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getValueInputPriceEntryInfo(getContext(), pk);
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
    public ValueInputPriceEntryInfo getValueInputPriceEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getValueInputPriceEntryInfo(getContext(), pk, selector);
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
    public ValueInputPriceEntryInfo getValueInputPriceEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getValueInputPriceEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ValueInputPriceEntryCollection getValueInputPriceEntryCollection() throws BOSException
    {
        try {
            return getController().getValueInputPriceEntryCollection(getContext());
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
    public ValueInputPriceEntryCollection getValueInputPriceEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getValueInputPriceEntryCollection(getContext(), view);
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
    public ValueInputPriceEntryCollection getValueInputPriceEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getValueInputPriceEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}