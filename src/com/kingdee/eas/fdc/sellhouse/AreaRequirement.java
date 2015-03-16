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

public class AreaRequirement extends FDCDataBase implements IAreaRequirement
{
    public AreaRequirement()
    {
        super();
        registerInterface(IAreaRequirement.class, this);
    }
    public AreaRequirement(Context ctx)
    {
        super(ctx);
        registerInterface(IAreaRequirement.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F96D713B");
    }
    private AreaRequirementController getController() throws BOSException
    {
        return (AreaRequirementController)getBizController();
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public AreaRequirementCollection getAreaRequirementCollection(String oql) throws BOSException
    {
        try {
            return getController().getAreaRequirementCollection(getContext(), oql);
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
    public AreaRequirementCollection getAreaRequirementCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAreaRequirementCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public AreaRequirementCollection getAreaRequirementCollection() throws BOSException
    {
        try {
            return getController().getAreaRequirementCollection(getContext());
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
    public AreaRequirementInfo getAreaRequirementInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAreaRequirementInfo(getContext(), oql);
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
    public AreaRequirementInfo getAreaRequirementInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAreaRequirementInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public AreaRequirementInfo getAreaRequirementInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAreaRequirementInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}