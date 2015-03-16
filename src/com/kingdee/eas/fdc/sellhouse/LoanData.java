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
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class LoanData extends CoreBillBase implements ILoanData
{
    public LoanData()
    {
        super();
        registerInterface(ILoanData.class, this);
    }
    public LoanData(Context ctx)
    {
        super(ctx);
        registerInterface(ILoanData.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D9698C95");
    }
    private LoanDataController getController() throws BOSException
    {
        return (LoanDataController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public LoanDataInfo getLoanDataInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getLoanDataInfo(getContext(), pk);
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
    public LoanDataInfo getLoanDataInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getLoanDataInfo(getContext(), pk, selector);
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
    public LoanDataInfo getLoanDataInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getLoanDataInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public LoanDataCollection getLoanDataCollection() throws BOSException
    {
        try {
            return getController().getLoanDataCollection(getContext());
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
    public LoanDataCollection getLoanDataCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getLoanDataCollection(getContext(), view);
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
    public LoanDataCollection getLoanDataCollection(String oql) throws BOSException
    {
        try {
            return getController().getLoanDataCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *∆Ù”√-User defined method
     *@param pk pk
     */
    public void enable(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().enable(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ω˚”√-User defined method
     *@param pk pk
     */
    public void disEnable(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().disEnable(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}