package com.kingdee.eas.fdc.basecrm;

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
import com.kingdee.eas.fdc.basecrm.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class RevFDCCustomerEntry extends CoreBillEntryBase implements IRevFDCCustomerEntry
{
    public RevFDCCustomerEntry()
    {
        super();
        registerInterface(IRevFDCCustomerEntry.class, this);
    }
    public RevFDCCustomerEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IRevFDCCustomerEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0F394DB6");
    }
    private RevFDCCustomerEntryController getController() throws BOSException
    {
        return (RevFDCCustomerEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public RevFDCCustomerEntryInfo getRevFDCCustomerEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRevFDCCustomerEntryInfo(getContext(), pk);
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
    public RevFDCCustomerEntryInfo getRevFDCCustomerEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRevFDCCustomerEntryInfo(getContext(), pk, selector);
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
    public RevFDCCustomerEntryInfo getRevFDCCustomerEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRevFDCCustomerEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RevFDCCustomerEntryCollection getRevFDCCustomerEntryCollection() throws BOSException
    {
        try {
            return getController().getRevFDCCustomerEntryCollection(getContext());
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
    public RevFDCCustomerEntryCollection getRevFDCCustomerEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRevFDCCustomerEntryCollection(getContext(), view);
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
    public RevFDCCustomerEntryCollection getRevFDCCustomerEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getRevFDCCustomerEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}