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

public class SwapRoomReason extends FDCDataBase implements ISwapRoomReason
{
    public SwapRoomReason()
    {
        super();
        registerInterface(ISwapRoomReason.class, this);
    }
    public SwapRoomReason(Context ctx)
    {
        super(ctx);
        registerInterface(ISwapRoomReason.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("60D4CC8D");
    }
    private SwapRoomReasonController getController() throws BOSException
    {
        return (SwapRoomReasonController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SwapRoomReasonInfo getSwapRoomReasonInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSwapRoomReasonInfo(getContext(), pk);
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
    public SwapRoomReasonInfo getSwapRoomReasonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSwapRoomReasonInfo(getContext(), pk, selector);
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
    public SwapRoomReasonInfo getSwapRoomReasonInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSwapRoomReasonInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SwapRoomReasonCollection getSwapRoomReasonCollection() throws BOSException
    {
        try {
            return getController().getSwapRoomReasonCollection(getContext());
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
    public SwapRoomReasonCollection getSwapRoomReasonCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSwapRoomReasonCollection(getContext(), view);
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
    public SwapRoomReasonCollection getSwapRoomReasonCollection(String oql) throws BOSException
    {
        try {
            return getController().getSwapRoomReasonCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}