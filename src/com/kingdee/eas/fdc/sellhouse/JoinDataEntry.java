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

public class JoinDataEntry extends BillEntryBase implements IJoinDataEntry
{
    public JoinDataEntry()
    {
        super();
        registerInterface(IJoinDataEntry.class, this);
    }
    public JoinDataEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IJoinDataEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("530FA9E3");
    }
    private JoinDataEntryController getController() throws BOSException
    {
        return (JoinDataEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public JoinDataEntryInfo getJoinDataEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getJoinDataEntryInfo(getContext(), pk);
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
    public JoinDataEntryInfo getJoinDataEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getJoinDataEntryInfo(getContext(), pk, selector);
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
    public JoinDataEntryInfo getJoinDataEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getJoinDataEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public JoinDataEntryCollection getJoinDataEntryCollection() throws BOSException
    {
        try {
            return getController().getJoinDataEntryCollection(getContext());
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
    public JoinDataEntryCollection getJoinDataEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getJoinDataEntryCollection(getContext(), view);
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
    public JoinDataEntryCollection getJoinDataEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getJoinDataEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}