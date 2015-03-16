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
import java.util.Set;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class PushManage extends FDCDataBase implements IPushManage
{
    public PushManage()
    {
        super();
        registerInterface(IPushManage.class, this);
    }
    public PushManage(Context ctx)
    {
        super(ctx);
        registerInterface(IPushManage.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8FC1CE3A");
    }
    private PushManageController getController() throws BOSException
    {
        return (PushManageController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PushManageInfo getPushManageInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPushManageInfo(getContext(), pk);
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
    public PushManageInfo getPushManageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPushManageInfo(getContext(), pk, selector);
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
    public PushManageInfo getPushManageInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPushManageInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PushManageCollection getPushManageCollection() throws BOSException
    {
        try {
            return getController().getPushManageCollection(getContext());
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
    public PushManageCollection getPushManageCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPushManageCollection(getContext(), view);
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
    public PushManageCollection getPushManageCollection(String oql) throws BOSException
    {
        try {
            return getController().getPushManageCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *推盘-User defined method
     *@param pushRoomIds 推盘房间ID
     */
    public void executed(Set pushRoomIds) throws BOSException
    {
        try {
            getController().executed(getContext(), pushRoomIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *撤盘-User defined method
     *@param quitRoomIds 撤盘ID
     */
    public void quitOrder(Set quitRoomIds) throws BOSException
    {
        try {
            getController().quitOrder(getContext(), quitRoomIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}