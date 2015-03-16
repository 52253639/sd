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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class Planisphere extends FDCBill implements IPlanisphere
{
    public Planisphere()
    {
        super();
        registerInterface(IPlanisphere.class, this);
    }
    public Planisphere(Context ctx)
    {
        super(ctx);
        registerInterface(IPlanisphere.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BC5F4C72");
    }
    private PlanisphereController getController() throws BOSException
    {
        return (PlanisphereController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public PlanisphereInfo getPlanisphereInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPlanisphereInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public PlanisphereInfo getPlanisphereInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPlanisphereInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public PlanisphereInfo getPlanisphereInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPlanisphereInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PlanisphereCollection getPlanisphereCollection() throws BOSException
    {
        try {
            return getController().getPlanisphereCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view view
     *@return
     */
    public PlanisphereCollection getPlanisphereCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPlanisphereCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public PlanisphereCollection getPlanisphereCollection(String oql) throws BOSException
    {
        try {
            return getController().getPlanisphereCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}