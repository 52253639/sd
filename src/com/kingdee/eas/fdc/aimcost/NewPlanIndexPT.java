package com.kingdee.eas.fdc.aimcost;

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
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class NewPlanIndexPT extends CoreBillEntryBase implements INewPlanIndexPT
{
    public NewPlanIndexPT()
    {
        super();
        registerInterface(INewPlanIndexPT.class, this);
    }
    public NewPlanIndexPT(Context ctx)
    {
        super(ctx);
        registerInterface(INewPlanIndexPT.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DEAD8244");
    }
    private NewPlanIndexPTController getController() throws BOSException
    {
        return (NewPlanIndexPTController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public NewPlanIndexPTInfo getNewPlanIndexPTInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNewPlanIndexPTInfo(getContext(), pk);
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
    public NewPlanIndexPTInfo getNewPlanIndexPTInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNewPlanIndexPTInfo(getContext(), pk, selector);
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
    public NewPlanIndexPTInfo getNewPlanIndexPTInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNewPlanIndexPTInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public NewPlanIndexPTCollection getNewPlanIndexPTCollection() throws BOSException
    {
        try {
            return getController().getNewPlanIndexPTCollection(getContext());
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
    public NewPlanIndexPTCollection getNewPlanIndexPTCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNewPlanIndexPTCollection(getContext(), view);
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
    public NewPlanIndexPTCollection getNewPlanIndexPTCollection(String oql) throws BOSException
    {
        try {
            return getController().getNewPlanIndexPTCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}