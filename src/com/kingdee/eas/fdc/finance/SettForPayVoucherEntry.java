package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.framework.IBillEntryBase;

public class SettForPayVoucherEntry extends BillEntryBase implements ISettForPayVoucherEntry
{
    public SettForPayVoucherEntry()
    {
        super();
        registerInterface(ISettForPayVoucherEntry.class, this);
    }
    public SettForPayVoucherEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISettForPayVoucherEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2F644AE4");
    }
    private SettForPayVoucherEntryController getController() throws BOSException
    {
        return (SettForPayVoucherEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SettForPayVoucherEntryInfo getSettForPayVoucherEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSettForPayVoucherEntryInfo(getContext(), pk);
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
    public SettForPayVoucherEntryInfo getSettForPayVoucherEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSettForPayVoucherEntryInfo(getContext(), pk, selector);
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
    public SettForPayVoucherEntryInfo getSettForPayVoucherEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSettForPayVoucherEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SettForPayVoucherEntryCollection getSettForPayVoucherEntryCollection() throws BOSException
    {
        try {
            return getController().getSettForPayVoucherEntryCollection(getContext());
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
    public SettForPayVoucherEntryCollection getSettForPayVoucherEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSettForPayVoucherEntryCollection(getContext(), view);
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
    public SettForPayVoucherEntryCollection getSettForPayVoucherEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSettForPayVoucherEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}