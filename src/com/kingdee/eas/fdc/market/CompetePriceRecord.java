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

public class CompetePriceRecord extends CoreBillEntryBase implements ICompetePriceRecord
{
    public CompetePriceRecord()
    {
        super();
        registerInterface(ICompetePriceRecord.class, this);
    }
    public CompetePriceRecord(Context ctx)
    {
        super(ctx);
        registerInterface(ICompetePriceRecord.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3BD02FE4");
    }
    private CompetePriceRecordController getController() throws BOSException
    {
        return (CompetePriceRecordController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CompetePriceRecordInfo getCompetePriceRecordInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCompetePriceRecordInfo(getContext(), pk);
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
    public CompetePriceRecordInfo getCompetePriceRecordInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCompetePriceRecordInfo(getContext(), pk, selector);
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
    public CompetePriceRecordInfo getCompetePriceRecordInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCompetePriceRecordInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CompetePriceRecordCollection getCompetePriceRecordCollection() throws BOSException
    {
        try {
            return getController().getCompetePriceRecordCollection(getContext());
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
    public CompetePriceRecordCollection getCompetePriceRecordCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCompetePriceRecordCollection(getContext(), view);
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
    public CompetePriceRecordCollection getCompetePriceRecordCollection(String oql) throws BOSException
    {
        try {
            return getController().getCompetePriceRecordCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}