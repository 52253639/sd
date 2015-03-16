package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class AimCostProdcutSplitEntry extends DataBase implements IAimCostProdcutSplitEntry
{
    public AimCostProdcutSplitEntry()
    {
        super();
        registerInterface(IAimCostProdcutSplitEntry.class, this);
    }
    public AimCostProdcutSplitEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IAimCostProdcutSplitEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("59124F28");
    }
    private AimCostProdcutSplitEntryController getController() throws BOSException
    {
        return (AimCostProdcutSplitEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public AimCostProdcutSplitEntryInfo getAimCostProdcutSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCostProdcutSplitEntryInfo(getContext(), pk);
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
    public AimCostProdcutSplitEntryInfo getAimCostProdcutSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCostProdcutSplitEntryInfo(getContext(), pk, selector);
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
    public AimCostProdcutSplitEntryInfo getAimCostProdcutSplitEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAimCostProdcutSplitEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public AimCostProdcutSplitEntryCollection getAimCostProdcutSplitEntryCollection() throws BOSException
    {
        try {
            return getController().getAimCostProdcutSplitEntryCollection(getContext());
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
    public AimCostProdcutSplitEntryCollection getAimCostProdcutSplitEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAimCostProdcutSplitEntryCollection(getContext(), view);
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
    public AimCostProdcutSplitEntryCollection getAimCostProdcutSplitEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getAimCostProdcutSplitEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}