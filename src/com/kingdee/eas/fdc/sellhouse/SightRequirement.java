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

public class SightRequirement extends FDCDataBase implements ISightRequirement
{
    public SightRequirement()
    {
        super();
        registerInterface(ISightRequirement.class, this);
    }
    public SightRequirement(Context ctx)
    {
        super(ctx);
        registerInterface(ISightRequirement.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7D6FC741");
    }
    private SightRequirementController getController() throws BOSException
    {
        return (SightRequirementController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SightRequirementInfo getSightRequirementInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSightRequirementInfo(getContext(), pk);
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
    public SightRequirementInfo getSightRequirementInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSightRequirementInfo(getContext(), pk, selector);
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
    public SightRequirementInfo getSightRequirementInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSightRequirementInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SightRequirementCollection getSightRequirementCollection() throws BOSException
    {
        try {
            return getController().getSightRequirementCollection(getContext());
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
    public SightRequirementCollection getSightRequirementCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSightRequirementCollection(getContext(), view);
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
    public SightRequirementCollection getSightRequirementCollection(String oql) throws BOSException
    {
        try {
            return getController().getSightRequirementCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}