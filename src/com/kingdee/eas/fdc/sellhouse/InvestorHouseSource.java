package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class InvestorHouseSource extends FDCDataBase implements IInvestorHouseSource
{
    public InvestorHouseSource()
    {
        super();
        registerInterface(IInvestorHouseSource.class, this);
    }
    public InvestorHouseSource(Context ctx)
    {
        super(ctx);
        registerInterface(IInvestorHouseSource.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4767D4E8");
    }
    private InvestorHouseSourceController getController() throws BOSException
    {
        return (InvestorHouseSourceController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public InvestorHouseSourceInfo getInvestorHouseSourceInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestorHouseSourceInfo(getContext(), pk);
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
    public InvestorHouseSourceInfo getInvestorHouseSourceInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestorHouseSourceInfo(getContext(), pk, selector);
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
    public InvestorHouseSourceInfo getInvestorHouseSourceInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestorHouseSourceInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public InvestorHouseSourceCollection getInvestorHouseSourceCollection() throws BOSException
    {
        try {
            return getController().getInvestorHouseSourceCollection(getContext());
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
    public InvestorHouseSourceCollection getInvestorHouseSourceCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInvestorHouseSourceCollection(getContext(), view);
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
    public InvestorHouseSourceCollection getInvestorHouseSourceCollection(String oql) throws BOSException
    {
        try {
            return getController().getInvestorHouseSourceCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}