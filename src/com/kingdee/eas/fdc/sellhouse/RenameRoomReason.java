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

public class RenameRoomReason extends FDCDataBase implements IRenameRoomReason
{
    public RenameRoomReason()
    {
        super();
        registerInterface(IRenameRoomReason.class, this);
    }
    public RenameRoomReason(Context ctx)
    {
        super(ctx);
        registerInterface(IRenameRoomReason.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("96BE9DB8");
    }
    private RenameRoomReasonController getController() throws BOSException
    {
        return (RenameRoomReasonController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RenameRoomReasonInfo getRenameRoomReasonInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRenameRoomReasonInfo(getContext(), pk);
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
    public RenameRoomReasonInfo getRenameRoomReasonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRenameRoomReasonInfo(getContext(), pk, selector);
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
    public RenameRoomReasonInfo getRenameRoomReasonInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRenameRoomReasonInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RenameRoomReasonCollection getRenameRoomReasonCollection() throws BOSException
    {
        try {
            return getController().getRenameRoomReasonCollection(getContext());
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
    public RenameRoomReasonCollection getRenameRoomReasonCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRenameRoomReasonCollection(getContext(), view);
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
    public RenameRoomReasonCollection getRenameRoomReasonCollection(String oql) throws BOSException
    {
        try {
            return getController().getRenameRoomReasonCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}