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

public class PreconcertPostponeEntry extends CoreBillEntryBase implements IPreconcertPostponeEntry
{
    public PreconcertPostponeEntry()
    {
        super();
        registerInterface(IPreconcertPostponeEntry.class, this);
    }
    public PreconcertPostponeEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPreconcertPostponeEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E68BEE5E");
    }
    private PreconcertPostponeEntryController getController() throws BOSException
    {
        return (PreconcertPostponeEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PreconcertPostponeEntryInfo getPreconcertPostponeEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPreconcertPostponeEntryInfo(getContext(), pk);
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
    public PreconcertPostponeEntryInfo getPreconcertPostponeEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPreconcertPostponeEntryInfo(getContext(), pk, selector);
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
    public PreconcertPostponeEntryInfo getPreconcertPostponeEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPreconcertPostponeEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PreconcertPostponeEntryCollection getPreconcertPostponeEntryCollection() throws BOSException
    {
        try {
            return getController().getPreconcertPostponeEntryCollection(getContext());
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
    public PreconcertPostponeEntryCollection getPreconcertPostponeEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPreconcertPostponeEntryCollection(getContext(), view);
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
    public PreconcertPostponeEntryCollection getPreconcertPostponeEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPreconcertPostponeEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}