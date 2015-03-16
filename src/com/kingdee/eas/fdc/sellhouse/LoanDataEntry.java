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

public class LoanDataEntry extends CoreBillEntryBase implements ILoanDataEntry
{
    public LoanDataEntry()
    {
        super();
        registerInterface(ILoanDataEntry.class, this);
    }
    public LoanDataEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ILoanDataEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("88F020DD");
    }
    private LoanDataEntryController getController() throws BOSException
    {
        return (LoanDataEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public LoanDataEntryInfo getLoanDataEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getLoanDataEntryInfo(getContext(), pk);
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
    public LoanDataEntryInfo getLoanDataEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getLoanDataEntryInfo(getContext(), pk, selector);
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
    public LoanDataEntryInfo getLoanDataEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getLoanDataEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public LoanDataEntryCollection getLoanDataEntryCollection() throws BOSException
    {
        try {
            return getController().getLoanDataEntryCollection(getContext());
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
    public LoanDataEntryCollection getLoanDataEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getLoanDataEntryCollection(getContext(), view);
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
    public LoanDataEntryCollection getLoanDataEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getLoanDataEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}