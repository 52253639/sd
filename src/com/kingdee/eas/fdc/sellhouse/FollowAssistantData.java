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

public class FollowAssistantData extends FDCDataBase implements IFollowAssistantData
{
    public FollowAssistantData()
    {
        super();
        registerInterface(IFollowAssistantData.class, this);
    }
    public FollowAssistantData(Context ctx)
    {
        super(ctx);
        registerInterface(IFollowAssistantData.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F45AB0BC");
    }
    private FollowAssistantDataController getController() throws BOSException
    {
        return (FollowAssistantDataController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FollowAssistantDataInfo getFollowAssistantDataInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFollowAssistantDataInfo(getContext(), pk);
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
    public FollowAssistantDataInfo getFollowAssistantDataInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFollowAssistantDataInfo(getContext(), pk, selector);
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
    public FollowAssistantDataInfo getFollowAssistantDataInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFollowAssistantDataInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FollowAssistantDataCollection getFollowAssistantDataCollection() throws BOSException
    {
        try {
            return getController().getFollowAssistantDataCollection(getContext());
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
    public FollowAssistantDataCollection getFollowAssistantDataCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFollowAssistantDataCollection(getContext(), view);
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
    public FollowAssistantDataCollection getFollowAssistantDataCollection(String oql) throws BOSException
    {
        try {
            return getController().getFollowAssistantDataCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}