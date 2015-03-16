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

public class HabitationRecord extends CoreBillEntryBase implements IHabitationRecord
{
    public HabitationRecord()
    {
        super();
        registerInterface(IHabitationRecord.class, this);
    }
    public HabitationRecord(Context ctx)
    {
        super(ctx);
        registerInterface(IHabitationRecord.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4CA7468D");
    }
    private HabitationRecordController getController() throws BOSException
    {
        return (HabitationRecordController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public HabitationRecordInfo getHabitationRecordInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getHabitationRecordInfo(getContext(), pk);
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
    public HabitationRecordInfo getHabitationRecordInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getHabitationRecordInfo(getContext(), oql);
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
    public HabitationRecordInfo getHabitationRecordInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getHabitationRecordInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public HabitationRecordCollection getHabitationRecordCollection() throws BOSException
    {
        try {
            return getController().getHabitationRecordCollection(getContext());
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
    public HabitationRecordCollection getHabitationRecordCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getHabitationRecordCollection(getContext(), view);
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
    public HabitationRecordCollection getHabitationRecordCollection(String oql) throws BOSException
    {
        try {
            return getController().getHabitationRecordCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}