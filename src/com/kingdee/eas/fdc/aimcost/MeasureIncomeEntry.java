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

public class MeasureIncomeEntry extends DataBase implements IMeasureIncomeEntry
{
    public MeasureIncomeEntry()
    {
        super();
        registerInterface(IMeasureIncomeEntry.class, this);
    }
    public MeasureIncomeEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IMeasureIncomeEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E7D8F5A2");
    }
    private MeasureIncomeEntryController getController() throws BOSException
    {
        return (MeasureIncomeEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MeasureIncomeEntryInfo getMeasureIncomeEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureIncomeEntryInfo(getContext(), pk);
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
    public MeasureIncomeEntryInfo getMeasureIncomeEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureIncomeEntryInfo(getContext(), pk, selector);
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
    public MeasureIncomeEntryInfo getMeasureIncomeEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureIncomeEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MeasureIncomeEntryCollection getMeasureIncomeEntryCollection() throws BOSException
    {
        try {
            return getController().getMeasureIncomeEntryCollection(getContext());
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
    public MeasureIncomeEntryCollection getMeasureIncomeEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMeasureIncomeEntryCollection(getContext(), view);
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
    public MeasureIncomeEntryCollection getMeasureIncomeEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getMeasureIncomeEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}