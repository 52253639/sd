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

public class NewListTempletPage extends CoreBillEntryBase implements INewListTempletPage
{
    public NewListTempletPage()
    {
        super();
        registerInterface(INewListTempletPage.class, this);
    }
    public NewListTempletPage(Context ctx)
    {
        super(ctx);
        registerInterface(INewListTempletPage.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("492E9DDC");
    }
    private NewListTempletPageController getController() throws BOSException
    {
        return (NewListTempletPageController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public NewListTempletPageInfo getNewListTempletPageInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListTempletPageInfo(getContext(), pk);
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
    public NewListTempletPageInfo getNewListTempletPageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListTempletPageInfo(getContext(), pk, selector);
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
    public NewListTempletPageInfo getNewListTempletPageInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListTempletPageInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public NewListTempletPageCollection getNewListTempletPageCollection() throws BOSException
    {
        try {
            return getController().getNewListTempletPageCollection(getContext());
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
    public NewListTempletPageCollection getNewListTempletPageCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNewListTempletPageCollection(getContext(), view);
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
    public NewListTempletPageCollection getNewListTempletPageCollection(String oql) throws BOSException
    {
        try {
            return getController().getNewListTempletPageCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}