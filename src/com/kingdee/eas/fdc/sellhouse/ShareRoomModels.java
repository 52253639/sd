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

public class ShareRoomModels extends FDCDataBase implements IShareRoomModels
{
    public ShareRoomModels()
    {
        super();
        registerInterface(IShareRoomModels.class, this);
    }
    public ShareRoomModels(Context ctx)
    {
        super(ctx);
        registerInterface(IShareRoomModels.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("29A55029");
    }
    private ShareRoomModelsController getController() throws BOSException
    {
        return (ShareRoomModelsController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ShareRoomModelsInfo getShareRoomModelsInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getShareRoomModelsInfo(getContext(), pk);
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
    public ShareRoomModelsInfo getShareRoomModelsInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getShareRoomModelsInfo(getContext(), pk, selector);
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
    public ShareRoomModelsInfo getShareRoomModelsInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getShareRoomModelsInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ShareRoomModelsCollection getShareRoomModelsCollection() throws BOSException
    {
        try {
            return getController().getShareRoomModelsCollection(getContext());
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
    public ShareRoomModelsCollection getShareRoomModelsCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getShareRoomModelsCollection(getContext(), view);
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
    public ShareRoomModelsCollection getShareRoomModelsCollection(String oql) throws BOSException
    {
        try {
            return getController().getShareRoomModelsCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}