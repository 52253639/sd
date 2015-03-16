package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class QuitRoomChange extends FDCBill implements IQuitRoomChange
{
    public QuitRoomChange()
    {
        super();
        registerInterface(IQuitRoomChange.class, this);
    }
    public QuitRoomChange(Context ctx)
    {
        super(ctx);
        registerInterface(IQuitRoomChange.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("37C4A3B5");
    }
    private QuitRoomChangeController getController() throws BOSException
    {
        return (QuitRoomChangeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public QuitRoomChangeInfo getQuitRoomChangeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitRoomChangeInfo(getContext(), pk);
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
    public QuitRoomChangeInfo getQuitRoomChangeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitRoomChangeInfo(getContext(), pk, selector);
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
    public QuitRoomChangeInfo getQuitRoomChangeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitRoomChangeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public QuitRoomChangeCollection getQuitRoomChangeCollection() throws BOSException
    {
        try {
            return getController().getQuitRoomChangeCollection(getContext());
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
    public QuitRoomChangeCollection getQuitRoomChangeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getQuitRoomChangeCollection(getContext(), view);
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
    public QuitRoomChangeCollection getQuitRoomChangeCollection(String oql) throws BOSException
    {
        try {
            return getController().getQuitRoomChangeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}