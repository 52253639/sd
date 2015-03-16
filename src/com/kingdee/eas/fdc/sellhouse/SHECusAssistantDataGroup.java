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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class SHECusAssistantDataGroup extends TreeBase implements ISHECusAssistantDataGroup
{
    public SHECusAssistantDataGroup()
    {
        super();
        registerInterface(ISHECusAssistantDataGroup.class, this);
    }
    public SHECusAssistantDataGroup(Context ctx)
    {
        super(ctx);
        registerInterface(ISHECusAssistantDataGroup.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("808912E3");
    }
    private SHECusAssistantDataGroupController getController() throws BOSException
    {
        return (SHECusAssistantDataGroupController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SHECusAssistantDataGroupInfo getSHECusAssistantDataGroupInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSHECusAssistantDataGroupInfo(getContext(), pk);
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
    public SHECusAssistantDataGroupInfo getSHECusAssistantDataGroupInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSHECusAssistantDataGroupInfo(getContext(), pk, selector);
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
    public SHECusAssistantDataGroupInfo getSHECusAssistantDataGroupInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSHECusAssistantDataGroupInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SHECusAssistantDataGroupCollection getSHECusAssistantDataGroupCollection() throws BOSException
    {
        try {
            return getController().getSHECusAssistantDataGroupCollection(getContext());
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
    public SHECusAssistantDataGroupCollection getSHECusAssistantDataGroupCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSHECusAssistantDataGroupCollection(getContext(), view);
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
    public SHECusAssistantDataGroupCollection getSHECusAssistantDataGroupCollection(String oql) throws BOSException
    {
        try {
            return getController().getSHECusAssistantDataGroupCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}