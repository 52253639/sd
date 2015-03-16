package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class LevelSetUp extends FDCDataBase implements ILevelSetUp
{
    public LevelSetUp()
    {
        super();
        registerInterface(ILevelSetUp.class, this);
    }
    public LevelSetUp(Context ctx)
    {
        super(ctx);
        registerInterface(ILevelSetUp.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("166AF1B1");
    }
    private LevelSetUpController getController() throws BOSException
    {
        return (LevelSetUpController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public LevelSetUpInfo getLevelSetUpInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getLevelSetUpInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public LevelSetUpInfo getLevelSetUpInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getLevelSetUpInfo(getContext(), pk, selector);
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
    public LevelSetUpInfo getLevelSetUpInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getLevelSetUpInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public LevelSetUpCollection getLevelSetUpCollection() throws BOSException
    {
        try {
            return getController().getLevelSetUpCollection(getContext());
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
    public LevelSetUpCollection getLevelSetUpCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getLevelSetUpCollection(getContext(), view);
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
    public LevelSetUpCollection getLevelSetUpCollection(String oql) throws BOSException
    {
        try {
            return getController().getLevelSetUpCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}