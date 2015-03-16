package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;
import java.util.List;

public class PriceSetScheme extends FDCDataBase implements IPriceSetScheme
{
    public PriceSetScheme()
    {
        super();
        registerInterface(IPriceSetScheme.class, this);
    }
    public PriceSetScheme(Context ctx)
    {
        super(ctx);
        registerInterface(IPriceSetScheme.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0FAF28F9");
    }
    private PriceSetSchemeController getController() throws BOSException
    {
        return (PriceSetSchemeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public PriceSetSchemeInfo getPriceSetSchemeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPriceSetSchemeInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PriceSetSchemeInfo getPriceSetSchemeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPriceSetSchemeInfo(getContext(), pk);
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
    public PriceSetSchemeInfo getPriceSetSchemeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPriceSetSchemeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PriceSetSchemeCollection getPriceSetSchemeCollection() throws BOSException
    {
        try {
            return getController().getPriceSetSchemeCollection(getContext());
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
    public PriceSetSchemeCollection getPriceSetSchemeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPriceSetSchemeCollection(getContext(), view);
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
    public PriceSetSchemeCollection getPriceSetSchemeCollection(String oql) throws BOSException
    {
        try {
            return getController().getPriceSetSchemeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *方案的启用和禁用-User defined method
     *@param idList idList
     *@param isEnabled 启用/禁用
     */
    public void setEnabled(List idList, boolean isEnabled) throws BOSException
    {
        try {
            getController().setEnabled(getContext(), idList, isEnabled);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}