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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class InvestorHouseTrackRecord extends DataBase implements IInvestorHouseTrackRecord
{
    public InvestorHouseTrackRecord()
    {
        super();
        registerInterface(IInvestorHouseTrackRecord.class, this);
    }
    public InvestorHouseTrackRecord(Context ctx)
    {
        super(ctx);
        registerInterface(IInvestorHouseTrackRecord.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E2FB70CF");
    }
    private InvestorHouseTrackRecordController getController() throws BOSException
    {
        return (InvestorHouseTrackRecordController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public InvestorHouseTrackRecordInfo getInvestorHouseTrackRecordInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestorHouseTrackRecordInfo(getContext(), pk);
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
    public InvestorHouseTrackRecordInfo getInvestorHouseTrackRecordInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestorHouseTrackRecordInfo(getContext(), pk, selector);
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
    public InvestorHouseTrackRecordInfo getInvestorHouseTrackRecordInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestorHouseTrackRecordInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public InvestorHouseTrackRecordCollection getInvestorHouseTrackRecordCollection() throws BOSException
    {
        try {
            return getController().getInvestorHouseTrackRecordCollection(getContext());
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
    public InvestorHouseTrackRecordCollection getInvestorHouseTrackRecordCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInvestorHouseTrackRecordCollection(getContext(), view);
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
    public InvestorHouseTrackRecordCollection getInvestorHouseTrackRecordCollection(String oql) throws BOSException
    {
        try {
            return getController().getInvestorHouseTrackRecordCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}