package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class BanBasedataEntryList extends CoreBillEntryBase implements IBanBasedataEntryList
{
    public BanBasedataEntryList()
    {
        super();
        registerInterface(IBanBasedataEntryList.class, this);
    }
    public BanBasedataEntryList(Context ctx)
    {
        super(ctx);
        registerInterface(IBanBasedataEntryList.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0B6B1781");
    }
    private BanBasedataEntryListController getController() throws BOSException
    {
        return (BanBasedataEntryListController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public BanBasedataEntryListInfo getBanBasedataEntryListInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBanBasedataEntryListInfo(getContext(), pk);
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
    public BanBasedataEntryListInfo getBanBasedataEntryListInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBanBasedataEntryListInfo(getContext(), pk, selector);
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
    public BanBasedataEntryListInfo getBanBasedataEntryListInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBanBasedataEntryListInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BanBasedataEntryListCollection getBanBasedataEntryListCollection() throws BOSException
    {
        try {
            return getController().getBanBasedataEntryListCollection(getContext());
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
    public BanBasedataEntryListCollection getBanBasedataEntryListCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBanBasedataEntryListCollection(getContext(), view);
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
    public BanBasedataEntryListCollection getBanBasedataEntryListCollection(String oql) throws BOSException
    {
        try {
            return getController().getBanBasedataEntryListCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}