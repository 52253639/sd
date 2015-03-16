package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
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

public class CommerceLevel extends FDCDataBase implements ICommerceLevel
{
    public CommerceLevel()
    {
        super();
        registerInterface(ICommerceLevel.class, this);
    }
    public CommerceLevel(Context ctx)
    {
        super(ctx);
        registerInterface(ICommerceLevel.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B372266E");
    }
    private CommerceLevelController getController() throws BOSException
    {
        return (CommerceLevelController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public CommerceLevelInfo getCommerceLevelInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceLevelInfo(getContext(), pk);
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
    public CommerceLevelInfo getCommerceLevelInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceLevelInfo(getContext(), oql);
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
    public CommerceLevelInfo getCommerceLevelInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceLevelInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public CommerceLevelCollection getCommerceLevelCollection() throws BOSException
    {
        try {
            return getController().getCommerceLevelCollection(getContext());
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
    public CommerceLevelCollection getCommerceLevelCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCommerceLevelCollection(getContext(), view);
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
    public CommerceLevelCollection getCommerceLevelCollection(String oql) throws BOSException
    {
        try {
            return getController().getCommerceLevelCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}