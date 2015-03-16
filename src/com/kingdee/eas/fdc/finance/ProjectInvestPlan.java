package com.kingdee.eas.fdc.finance;

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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ProjectInvestPlan extends FDCBill implements IProjectInvestPlan
{
    public ProjectInvestPlan()
    {
        super();
        registerInterface(IProjectInvestPlan.class, this);
    }
    public ProjectInvestPlan(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectInvestPlan.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2E405388");
    }
    private ProjectInvestPlanController getController() throws BOSException
    {
        return (ProjectInvestPlanController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ProjectInvestPlanInfo getProjectInvestPlanInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectInvestPlanInfo(getContext(), pk);
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
    public ProjectInvestPlanInfo getProjectInvestPlanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectInvestPlanInfo(getContext(), pk, selector);
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
    public ProjectInvestPlanInfo getProjectInvestPlanInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectInvestPlanInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProjectInvestPlanCollection getProjectInvestPlanCollection() throws BOSException
    {
        try {
            return getController().getProjectInvestPlanCollection(getContext());
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
    public ProjectInvestPlanCollection getProjectInvestPlanCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectInvestPlanCollection(getContext(), view);
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
    public ProjectInvestPlanCollection getProjectInvestPlanCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectInvestPlanCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *setPublish-User defined method
     *@param id id
     */
    public void setPublish(String id) throws BOSException
    {
        try {
            getController().setPublish(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}