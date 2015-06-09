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

public class NewPlanIndex extends CoreBillEntryBase implements INewPlanIndex
{
    public NewPlanIndex()
    {
        super();
        registerInterface(INewPlanIndex.class, this);
    }
    public NewPlanIndex(Context ctx)
    {
        super(ctx);
        registerInterface(INewPlanIndex.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("302E8840");
    }
    private NewPlanIndexController getController() throws BOSException
    {
        return (NewPlanIndexController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public NewPlanIndexInfo getNewPlanIndexInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNewPlanIndexInfo(getContext(), pk);
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
    public NewPlanIndexInfo getNewPlanIndexInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNewPlanIndexInfo(getContext(), pk, selector);
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
    public NewPlanIndexInfo getNewPlanIndexInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNewPlanIndexInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public NewPlanIndexCollection getNewPlanIndexCollection() throws BOSException
    {
        try {
            return getController().getNewPlanIndexCollection(getContext());
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
    public NewPlanIndexCollection getNewPlanIndexCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNewPlanIndexCollection(getContext(), view);
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
    public NewPlanIndexCollection getNewPlanIndexCollection(String oql) throws BOSException
    {
        try {
            return getController().getNewPlanIndexCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}