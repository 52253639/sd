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

public class InvestorHouseLinkmanEntrys extends DataBase implements IInvestorHouseLinkmanEntrys
{
    public InvestorHouseLinkmanEntrys()
    {
        super();
        registerInterface(IInvestorHouseLinkmanEntrys.class, this);
    }
    public InvestorHouseLinkmanEntrys(Context ctx)
    {
        super(ctx);
        registerInterface(IInvestorHouseLinkmanEntrys.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("72085814");
    }
    private InvestorHouseLinkmanEntrysController getController() throws BOSException
    {
        return (InvestorHouseLinkmanEntrysController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public InvestorHouseLinkmanEntrysInfo getInvestorHouseLinkmanEntrysInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestorHouseLinkmanEntrysInfo(getContext(), pk);
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
    public InvestorHouseLinkmanEntrysInfo getInvestorHouseLinkmanEntrysInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestorHouseLinkmanEntrysInfo(getContext(), pk, selector);
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
    public InvestorHouseLinkmanEntrysInfo getInvestorHouseLinkmanEntrysInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestorHouseLinkmanEntrysInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public InvestorHouseLinkmanEntrysCollection getInvestorHouseLinkmanEntrysCollection() throws BOSException
    {
        try {
            return getController().getInvestorHouseLinkmanEntrysCollection(getContext());
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
    public InvestorHouseLinkmanEntrysCollection getInvestorHouseLinkmanEntrysCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInvestorHouseLinkmanEntrysCollection(getContext(), view);
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
    public InvestorHouseLinkmanEntrysCollection getInvestorHouseLinkmanEntrysCollection(String oql) throws BOSException
    {
        try {
            return getController().getInvestorHouseLinkmanEntrysCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}