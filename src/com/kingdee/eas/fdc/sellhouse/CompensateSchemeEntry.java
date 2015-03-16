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

public class CompensateSchemeEntry extends CoreBillEntryBase implements ICompensateSchemeEntry
{
    public CompensateSchemeEntry()
    {
        super();
        registerInterface(ICompensateSchemeEntry.class, this);
    }
    public CompensateSchemeEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ICompensateSchemeEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BEC8F81B");
    }
    private CompensateSchemeEntryController getController() throws BOSException
    {
        return (CompensateSchemeEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CompensateSchemeEntryInfo getCompensateSchemeEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensateSchemeEntryInfo(getContext(), pk);
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
    public CompensateSchemeEntryInfo getCompensateSchemeEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensateSchemeEntryInfo(getContext(), pk, selector);
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
    public CompensateSchemeEntryInfo getCompensateSchemeEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensateSchemeEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CompensateSchemeEntryCollection getCompensateSchemeEntryCollection() throws BOSException
    {
        try {
            return getController().getCompensateSchemeEntryCollection(getContext());
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
    public CompensateSchemeEntryCollection getCompensateSchemeEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCompensateSchemeEntryCollection(getContext(), view);
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
    public CompensateSchemeEntryCollection getCompensateSchemeEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getCompensateSchemeEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}