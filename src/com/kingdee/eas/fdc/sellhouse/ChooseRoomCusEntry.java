package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ChooseRoomCusEntry extends CoreBillEntryBase implements IChooseRoomCusEntry
{
    public ChooseRoomCusEntry()
    {
        super();
        registerInterface(IChooseRoomCusEntry.class, this);
    }
    public ChooseRoomCusEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IChooseRoomCusEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BA24967E");
    }
    private ChooseRoomCusEntryController getController() throws BOSException
    {
        return (ChooseRoomCusEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ChooseRoomCusEntryInfo getChooseRoomCusEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getChooseRoomCusEntryInfo(getContext(), pk);
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
    public ChooseRoomCusEntryInfo getChooseRoomCusEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getChooseRoomCusEntryInfo(getContext(), pk, selector);
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
    public ChooseRoomCusEntryInfo getChooseRoomCusEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getChooseRoomCusEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ChooseRoomCusEntryCollection getChooseRoomCusEntryCollection() throws BOSException
    {
        try {
            return getController().getChooseRoomCusEntryCollection(getContext());
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
    public ChooseRoomCusEntryCollection getChooseRoomCusEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getChooseRoomCusEntryCollection(getContext(), view);
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
    public ChooseRoomCusEntryCollection getChooseRoomCusEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getChooseRoomCusEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}