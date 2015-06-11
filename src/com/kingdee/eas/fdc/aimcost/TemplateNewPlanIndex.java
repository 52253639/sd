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

public class TemplateNewPlanIndex extends CoreBillEntryBase implements ITemplateNewPlanIndex
{
    public TemplateNewPlanIndex()
    {
        super();
        registerInterface(ITemplateNewPlanIndex.class, this);
    }
    public TemplateNewPlanIndex(Context ctx)
    {
        super(ctx);
        registerInterface(ITemplateNewPlanIndex.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("046EFADA");
    }
    private TemplateNewPlanIndexController getController() throws BOSException
    {
        return (TemplateNewPlanIndexController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TemplateNewPlanIndexInfo getTemplateNewPlanIndexInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateNewPlanIndexInfo(getContext(), pk);
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
    public TemplateNewPlanIndexInfo getTemplateNewPlanIndexInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateNewPlanIndexInfo(getContext(), pk, selector);
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
    public TemplateNewPlanIndexInfo getTemplateNewPlanIndexInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateNewPlanIndexInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TemplateNewPlanIndexCollection getTemplateNewPlanIndexCollection() throws BOSException
    {
        try {
            return getController().getTemplateNewPlanIndexCollection(getContext());
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
    public TemplateNewPlanIndexCollection getTemplateNewPlanIndexCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTemplateNewPlanIndexCollection(getContext(), view);
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
    public TemplateNewPlanIndexCollection getTemplateNewPlanIndexCollection(String oql) throws BOSException
    {
        try {
            return getController().getTemplateNewPlanIndexCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}