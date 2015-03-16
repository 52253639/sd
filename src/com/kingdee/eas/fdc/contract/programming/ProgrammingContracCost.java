package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.programming.app.*;
import com.kingdee.eas.framework.ICoreBase;

public class ProgrammingContracCost extends CoreBase implements IProgrammingContracCost
{
    public ProgrammingContracCost()
    {
        super();
        registerInterface(IProgrammingContracCost.class, this);
    }
    public ProgrammingContracCost(Context ctx)
    {
        super(ctx);
        registerInterface(IProgrammingContracCost.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9E6FDD26");
    }
    private ProgrammingContracCostController getController() throws BOSException
    {
        return (ProgrammingContracCostController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ProgrammingContracCostInfo getProgrammingContracCostInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingContracCostInfo(getContext(), pk);
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
    public ProgrammingContracCostInfo getProgrammingContracCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingContracCostInfo(getContext(), pk, selector);
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
    public ProgrammingContracCostInfo getProgrammingContracCostInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingContracCostInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProgrammingContracCostCollection getProgrammingContracCostCollection() throws BOSException
    {
        try {
            return getController().getProgrammingContracCostCollection(getContext());
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
    public ProgrammingContracCostCollection getProgrammingContracCostCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProgrammingContracCostCollection(getContext(), view);
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
    public ProgrammingContracCostCollection getProgrammingContracCostCollection(String oql) throws BOSException
    {
        try {
            return getController().getProgrammingContracCostCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}