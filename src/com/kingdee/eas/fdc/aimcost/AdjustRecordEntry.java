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
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.framework.IBillEntryBase;

public class AdjustRecordEntry extends BillEntryBase implements IAdjustRecordEntry
{
    public AdjustRecordEntry()
    {
        super();
        registerInterface(IAdjustRecordEntry.class, this);
    }
    public AdjustRecordEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IAdjustRecordEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("52FA927D");
    }
    private AdjustRecordEntryController getController() throws BOSException
    {
        return (AdjustRecordEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public AdjustRecordEntryInfo getAdjustRecordEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAdjustRecordEntryInfo(getContext(), pk);
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
    public AdjustRecordEntryInfo getAdjustRecordEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAdjustRecordEntryInfo(getContext(), pk, selector);
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
    public AdjustRecordEntryInfo getAdjustRecordEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAdjustRecordEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public AdjustRecordEntryCollection getAdjustRecordEntryCollection() throws BOSException
    {
        try {
            return getController().getAdjustRecordEntryCollection(getContext());
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
    public AdjustRecordEntryCollection getAdjustRecordEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAdjustRecordEntryCollection(getContext(), view);
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
    public AdjustRecordEntryCollection getAdjustRecordEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getAdjustRecordEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}