package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import java.util.HashMap;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class RefPriceEntry extends CoreBase implements IRefPriceEntry
{
    public RefPriceEntry()
    {
        super();
        registerInterface(IRefPriceEntry.class, this);
    }
    public RefPriceEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IRefPriceEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8B68BCF8");
    }
    private RefPriceEntryController getController() throws BOSException
    {
        return (RefPriceEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public RefPriceEntryInfo getRefPriceEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRefPriceEntryInfo(getContext(), pk);
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
    public RefPriceEntryInfo getRefPriceEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRefPriceEntryInfo(getContext(), pk, selector);
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
    public RefPriceEntryInfo getRefPriceEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRefPriceEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *自定义取集合-System defined method
     *@return
     */
    public RefPriceEntryCollection getRefPriceEntryCollection() throws BOSException
    {
        try {
            return getController().getRefPriceEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *自定义取集合-System defined method
     *@param view 取集合
     *@return
     */
    public RefPriceEntryCollection getRefPriceEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRefPriceEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *自定义取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public RefPriceEntryCollection getRefPriceEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getRefPriceEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *自定义取集合-User defined method
     *@param view 取集合
     *@param cols 自定义的条件列
     *@return
     */
    public RefPriceEntryCollection getCollection(EntityViewInfo view, HashMap cols) throws BOSException
    {
        try {
            return getController().getCollection(getContext(), view, cols);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}