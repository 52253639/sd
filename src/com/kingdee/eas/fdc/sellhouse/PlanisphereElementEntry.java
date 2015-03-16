package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class PlanisphereElementEntry extends CoreBillEntryBase implements IPlanisphereElementEntry
{
    public PlanisphereElementEntry()
    {
        super();
        registerInterface(IPlanisphereElementEntry.class, this);
    }
    public PlanisphereElementEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPlanisphereElementEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("28567C68");
    }
    private PlanisphereElementEntryController getController() throws BOSException
    {
        return (PlanisphereElementEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public PlanisphereElementEntryInfo getPlanisphereElementEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPlanisphereElementEntryInfo(getContext(), pk);
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
    public PlanisphereElementEntryInfo getPlanisphereElementEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPlanisphereElementEntryInfo(getContext(), pk, selector);
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
    public PlanisphereElementEntryInfo getPlanisphereElementEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPlanisphereElementEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PlanisphereElementEntryCollection getPlanisphereElementEntryCollection() throws BOSException
    {
        try {
            return getController().getPlanisphereElementEntryCollection(getContext());
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
    public PlanisphereElementEntryCollection getPlanisphereElementEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPlanisphereElementEntryCollection(getContext(), view);
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
    public PlanisphereElementEntryCollection getPlanisphereElementEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPlanisphereElementEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}