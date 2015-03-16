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

public class MarketSurveyEntry extends CoreBillEntryBase implements IMarketSurveyEntry
{
    public MarketSurveyEntry()
    {
        super();
        registerInterface(IMarketSurveyEntry.class, this);
    }
    public MarketSurveyEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSurveyEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("CA7B25AB");
    }
    private MarketSurveyEntryController getController() throws BOSException
    {
        return (MarketSurveyEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MarketSurveyEntryInfo getMarketSurveyEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSurveyEntryInfo(getContext(), pk);
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
    public MarketSurveyEntryInfo getMarketSurveyEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSurveyEntryInfo(getContext(), pk, selector);
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
    public MarketSurveyEntryInfo getMarketSurveyEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSurveyEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketSurveyEntryCollection getMarketSurveyEntryCollection() throws BOSException
    {
        try {
            return getController().getMarketSurveyEntryCollection(getContext());
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
    public MarketSurveyEntryCollection getMarketSurveyEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSurveyEntryCollection(getContext(), view);
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
    public MarketSurveyEntryCollection getMarketSurveyEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSurveyEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}