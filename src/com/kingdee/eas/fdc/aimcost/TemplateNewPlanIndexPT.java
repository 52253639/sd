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

public class TemplateNewPlanIndexPT extends CoreBillEntryBase implements ITemplateNewPlanIndexPT
{
    public TemplateNewPlanIndexPT()
    {
        super();
        registerInterface(ITemplateNewPlanIndexPT.class, this);
    }
    public TemplateNewPlanIndexPT(Context ctx)
    {
        super(ctx);
        registerInterface(ITemplateNewPlanIndexPT.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A49BB65E");
    }
    private TemplateNewPlanIndexPTController getController() throws BOSException
    {
        return (TemplateNewPlanIndexPTController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TemplateNewPlanIndexPTInfo getTemplateNewPlanIndexPTInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateNewPlanIndexPTInfo(getContext(), pk);
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
    public TemplateNewPlanIndexPTInfo getTemplateNewPlanIndexPTInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateNewPlanIndexPTInfo(getContext(), pk, selector);
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
    public TemplateNewPlanIndexPTInfo getTemplateNewPlanIndexPTInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTemplateNewPlanIndexPTInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TemplateNewPlanIndexPTCollection getTemplateNewPlanIndexPTCollection() throws BOSException
    {
        try {
            return getController().getTemplateNewPlanIndexPTCollection(getContext());
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
    public TemplateNewPlanIndexPTCollection getTemplateNewPlanIndexPTCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTemplateNewPlanIndexPTCollection(getContext(), view);
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
    public TemplateNewPlanIndexPTCollection getTemplateNewPlanIndexPTCollection(String oql) throws BOSException
    {
        try {
            return getController().getTemplateNewPlanIndexPTCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}