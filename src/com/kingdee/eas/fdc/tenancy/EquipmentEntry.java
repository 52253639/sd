package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class EquipmentEntry extends CoreBillEntryBase implements IEquipmentEntry
{
    public EquipmentEntry()
    {
        super();
        registerInterface(IEquipmentEntry.class, this);
    }
    public EquipmentEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IEquipmentEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("124D916D");
    }
    private EquipmentEntryController getController() throws BOSException
    {
        return (EquipmentEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public EquipmentEntryInfo getEquipmentEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getEquipmentEntryInfo(getContext(), pk);
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
    public EquipmentEntryInfo getEquipmentEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getEquipmentEntryInfo(getContext(), pk, selector);
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
    public EquipmentEntryInfo getEquipmentEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getEquipmentEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public EquipmentEntryCollection getEquipmentEntryCollection() throws BOSException
    {
        try {
            return getController().getEquipmentEntryCollection(getContext());
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
    public EquipmentEntryCollection getEquipmentEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getEquipmentEntryCollection(getContext(), view);
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
    public EquipmentEntryCollection getEquipmentEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getEquipmentEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}