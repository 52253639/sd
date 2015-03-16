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

public class QuaLevel extends FDCDataBase implements IQuaLevel
{
    public QuaLevel()
    {
        super();
        registerInterface(IQuaLevel.class, this);
    }
    public QuaLevel(Context ctx)
    {
        super(ctx);
        registerInterface(IQuaLevel.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E75504FF");
    }
    private QuaLevelController getController() throws BOSException
    {
        return (QuaLevelController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public QuaLevelInfo getQuaLevelInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getQuaLevelInfo(getContext(), pk);
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
    public QuaLevelInfo getQuaLevelInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getQuaLevelInfo(getContext(), pk, selector);
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
    public QuaLevelInfo getQuaLevelInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getQuaLevelInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public QuaLevelCollection getQuaLevelCollection() throws BOSException
    {
        try {
            return getController().getQuaLevelCollection(getContext());
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
    public QuaLevelCollection getQuaLevelCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getQuaLevelCollection(getContext(), view);
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
    public QuaLevelCollection getQuaLevelCollection(String oql) throws BOSException
    {
        try {
            return getController().getQuaLevelCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}