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

public class AIMAimCostProductSplitEntry extends DataBase implements IAIMAimCostProductSplitEntry
{
    public AIMAimCostProductSplitEntry()
    {
        super();
        registerInterface(IAIMAimCostProductSplitEntry.class, this);
    }
    public AIMAimCostProductSplitEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IAIMAimCostProductSplitEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1EB8A1E3");
    }
    private AIMAimCostProductSplitEntryController getController() throws BOSException
    {
        return (AIMAimCostProductSplitEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public AIMAimCostProductSplitEntryInfo getAIMAimCostProductSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAIMAimCostProductSplitEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public AIMAimCostProductSplitEntryInfo getAIMAimCostProductSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAIMAimCostProductSplitEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public AIMAimCostProductSplitEntryInfo getAIMAimCostProductSplitEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAIMAimCostProductSplitEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public AIMAimCostProductSplitEntryCollection getAIMAimCostProductSplitEntryCollection() throws BOSException
    {
        try {
            return getController().getAIMAimCostProductSplitEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public AIMAimCostProductSplitEntryCollection getAIMAimCostProductSplitEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAIMAimCostProductSplitEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public AIMAimCostProductSplitEntryCollection getAIMAimCostProductSplitEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getAIMAimCostProductSplitEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}