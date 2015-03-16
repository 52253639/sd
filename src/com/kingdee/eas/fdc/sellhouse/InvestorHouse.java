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

public class InvestorHouse extends FDCDataBase implements IInvestorHouse
{
    public InvestorHouse()
    {
        super();
        registerInterface(IInvestorHouse.class, this);
    }
    public InvestorHouse(Context ctx)
    {
        super(ctx);
        registerInterface(IInvestorHouse.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("58D7C66D");
    }
    private InvestorHouseController getController() throws BOSException
    {
        return (InvestorHouseController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public InvestorHouseInfo getInvestorHouseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestorHouseInfo(getContext(), pk);
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
    public InvestorHouseInfo getInvestorHouseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestorHouseInfo(getContext(), pk, selector);
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
    public InvestorHouseInfo getInvestorHouseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestorHouseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public InvestorHouseCollection getInvestorHouseCollection() throws BOSException
    {
        try {
            return getController().getInvestorHouseCollection(getContext());
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
    public InvestorHouseCollection getInvestorHouseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInvestorHouseCollection(getContext(), view);
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
    public InvestorHouseCollection getInvestorHouseCollection(String oql) throws BOSException
    {
        try {
            return getController().getInvestorHouseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}