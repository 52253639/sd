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

public class NewListTempletValue extends CoreBillEntryBase implements INewListTempletValue
{
    public NewListTempletValue()
    {
        super();
        registerInterface(INewListTempletValue.class, this);
    }
    public NewListTempletValue(Context ctx)
    {
        super(ctx);
        registerInterface(INewListTempletValue.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DCF9BFC4");
    }
    private NewListTempletValueController getController() throws BOSException
    {
        return (NewListTempletValueController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public NewListTempletValueInfo getNewListTempletValueInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListTempletValueInfo(getContext(), pk);
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
    public NewListTempletValueInfo getNewListTempletValueInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListTempletValueInfo(getContext(), pk, selector);
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
    public NewListTempletValueInfo getNewListTempletValueInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListTempletValueInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public NewListTempletValueCollection getNewListTempletValueCollection() throws BOSException
    {
        try {
            return getController().getNewListTempletValueCollection(getContext());
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
    public NewListTempletValueCollection getNewListTempletValueCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNewListTempletValueCollection(getContext(), view);
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
    public NewListTempletValueCollection getNewListTempletValueCollection(String oql) throws BOSException
    {
        try {
            return getController().getNewListTempletValueCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *通过SQL获取集合-User defined method
     *@param listingIds id
     *@return
     */
    public NewListTempletValueCollection getCollectionBySQL(String listingIds) throws BOSException, EASBizException
    {
        try {
            return getController().getCollectionBySQL(getContext(), listingIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}