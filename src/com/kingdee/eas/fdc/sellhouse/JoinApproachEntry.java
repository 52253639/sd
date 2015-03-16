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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.framework.IBillEntryBase;

public class JoinApproachEntry extends BillEntryBase implements IJoinApproachEntry
{
    public JoinApproachEntry()
    {
        super();
        registerInterface(IJoinApproachEntry.class, this);
    }
    public JoinApproachEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IJoinApproachEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9086DB25");
    }
    private JoinApproachEntryController getController() throws BOSException
    {
        return (JoinApproachEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public JoinApproachEntryInfo getJoinApproachEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getJoinApproachEntryInfo(getContext(), pk);
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
    public JoinApproachEntryInfo getJoinApproachEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getJoinApproachEntryInfo(getContext(), pk, selector);
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
    public JoinApproachEntryInfo getJoinApproachEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getJoinApproachEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public JoinApproachEntryCollection getJoinApproachEntryCollection() throws BOSException
    {
        try {
            return getController().getJoinApproachEntryCollection(getContext());
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
    public JoinApproachEntryCollection getJoinApproachEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getJoinApproachEntryCollection(getContext(), view);
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
    public JoinApproachEntryCollection getJoinApproachEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getJoinApproachEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}