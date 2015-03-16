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

public class TrackRecord extends FDCBill implements ITrackRecord
{
    public TrackRecord()
    {
        super();
        registerInterface(ITrackRecord.class, this);
    }
    public TrackRecord(Context ctx)
    {
        super(ctx);
        registerInterface(ITrackRecord.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("83DD37C1");
    }
    private TrackRecordController getController() throws BOSException
    {
        return (TrackRecordController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public TrackRecordInfo getTrackRecordInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTrackRecordInfo(getContext(), pk);
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
    public TrackRecordInfo getTrackRecordInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTrackRecordInfo(getContext(), pk, selector);
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
    public TrackRecordInfo getTrackRecordInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTrackRecordInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TrackRecordCollection getTrackRecordCollection() throws BOSException
    {
        try {
            return getController().getTrackRecordCollection(getContext());
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
    public TrackRecordCollection getTrackRecordCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTrackRecordCollection(getContext(), view);
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
    public TrackRecordCollection getTrackRecordCollection(String oql) throws BOSException
    {
        try {
            return getController().getTrackRecordCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}