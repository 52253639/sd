package com.kingdee.eas.fdc.market;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;

public class ValuePlanDetialEntry extends CoreBillEntryBase implements IValuePlanDetialEntry
{
    public ValuePlanDetialEntry()
    {
        super();
        registerInterface(IValuePlanDetialEntry.class, this);
    }
    public ValuePlanDetialEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IValuePlanDetialEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2D4B1A48");
    }
    private ValuePlanDetialEntryController getController() throws BOSException
    {
        return (ValuePlanDetialEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ValuePlanDetialEntryInfo getValuePlanDetialEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getValuePlanDetialEntryInfo(getContext(), pk);
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
    public ValuePlanDetialEntryInfo getValuePlanDetialEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getValuePlanDetialEntryInfo(getContext(), pk, selector);
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
    public ValuePlanDetialEntryInfo getValuePlanDetialEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getValuePlanDetialEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ValuePlanDetialEntryCollection getValuePlanDetialEntryCollection() throws BOSException
    {
        try {
            return getController().getValuePlanDetialEntryCollection(getContext());
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
    public ValuePlanDetialEntryCollection getValuePlanDetialEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getValuePlanDetialEntryCollection(getContext(), view);
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
    public ValuePlanDetialEntryCollection getValuePlanDetialEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getValuePlanDetialEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}