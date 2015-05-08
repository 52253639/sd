package com.kingdee.eas.fdc.schedule;

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
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class HelpPersonEntry extends CoreBillEntryBase implements IHelpPersonEntry
{
    public HelpPersonEntry()
    {
        super();
        registerInterface(IHelpPersonEntry.class, this);
    }
    public HelpPersonEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IHelpPersonEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EC59AD86");
    }
    private HelpPersonEntryController getController() throws BOSException
    {
        return (HelpPersonEntryController)getBizController();
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public HelpPersonEntryCollection getHelpPersonEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getHelpPersonEntryCollection(getContext(), oql);
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
    public HelpPersonEntryCollection getHelpPersonEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getHelpPersonEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public HelpPersonEntryCollection getHelpPersonEntryCollection() throws BOSException
    {
        try {
            return getController().getHelpPersonEntryCollection(getContext());
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
    public HelpPersonEntryInfo getHelpPersonEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getHelpPersonEntryInfo(getContext(), oql);
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
    public HelpPersonEntryInfo getHelpPersonEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getHelpPersonEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public HelpPersonEntryInfo getHelpPersonEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getHelpPersonEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}