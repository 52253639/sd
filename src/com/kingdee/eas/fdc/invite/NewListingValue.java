package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
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

public class NewListingValue extends CoreBillEntryBase implements INewListingValue
{
    public NewListingValue()
    {
        super();
        registerInterface(INewListingValue.class, this);
    }
    public NewListingValue(Context ctx)
    {
        super(ctx);
        registerInterface(INewListingValue.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D6CFB769");
    }
    private NewListingValueController getController() throws BOSException
    {
        return (NewListingValueController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public NewListingValueInfo getNewListingValueInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListingValueInfo(getContext(), pk);
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
    public NewListingValueInfo getNewListingValueInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListingValueInfo(getContext(), pk, selector);
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
    public NewListingValueInfo getNewListingValueInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListingValueInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public NewListingValueCollection getNewListingValueCollection() throws BOSException
    {
        try {
            return getController().getNewListingValueCollection(getContext());
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
    public NewListingValueCollection getNewListingValueCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNewListingValueCollection(getContext(), view);
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
    public NewListingValueCollection getNewListingValueCollection(String oql) throws BOSException
    {
        try {
            return getController().getNewListingValueCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *使用SQL获取集合，原因：1万行的限制-User defined method
     *@param listingId id
     *@return
     */
    public NewListingValueCollection getCollectionBySQL(String listingId) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionBySQL(getContext(), listingId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}