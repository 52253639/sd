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

public class QuitRoomReason extends FDCDataBase implements IQuitRoomReason
{
    public QuitRoomReason()
    {
        super();
        registerInterface(IQuitRoomReason.class, this);
    }
    public QuitRoomReason(Context ctx)
    {
        super(ctx);
        registerInterface(IQuitRoomReason.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("51332249");
    }
    private QuitRoomReasonController getController() throws BOSException
    {
        return (QuitRoomReasonController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public QuitRoomReasonInfo getQuitRoomReasonInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitRoomReasonInfo(getContext(), pk);
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
    public QuitRoomReasonInfo getQuitRoomReasonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitRoomReasonInfo(getContext(), pk, selector);
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
    public QuitRoomReasonInfo getQuitRoomReasonInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitRoomReasonInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public QuitRoomReasonCollection getQuitRoomReasonCollection() throws BOSException
    {
        try {
            return getController().getQuitRoomReasonCollection(getContext());
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
    public QuitRoomReasonCollection getQuitRoomReasonCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getQuitRoomReasonCollection(getContext(), view);
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
    public QuitRoomReasonCollection getQuitRoomReasonCollection(String oql) throws BOSException
    {
        try {
            return getController().getQuitRoomReasonCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}