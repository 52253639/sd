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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class BasePriceControlBuildingEntry extends CoreBase implements IBasePriceControlBuildingEntry
{
    public BasePriceControlBuildingEntry()
    {
        super();
        registerInterface(IBasePriceControlBuildingEntry.class, this);
    }
    public BasePriceControlBuildingEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IBasePriceControlBuildingEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4C5347DE");
    }
    private BasePriceControlBuildingEntryController getController() throws BOSException
    {
        return (BasePriceControlBuildingEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public BasePriceControlBuildingEntryInfo getBasePriceControlBuildingEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBasePriceControlBuildingEntryInfo(getContext(), pk);
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
    public BasePriceControlBuildingEntryInfo getBasePriceControlBuildingEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBasePriceControlBuildingEntryInfo(getContext(), pk, selector);
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
    public BasePriceControlBuildingEntryInfo getBasePriceControlBuildingEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBasePriceControlBuildingEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public BasePriceControlBuildingEntryCollection getBasePriceControlBuildingEntryCollection() throws BOSException
    {
        try {
            return getController().getBasePriceControlBuildingEntryCollection(getContext());
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
    public BasePriceControlBuildingEntryCollection getBasePriceControlBuildingEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBasePriceControlBuildingEntryCollection(getContext(), view);
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
    public BasePriceControlBuildingEntryCollection getBasePriceControlBuildingEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getBasePriceControlBuildingEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}