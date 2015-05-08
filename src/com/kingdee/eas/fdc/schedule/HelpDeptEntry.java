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

public class HelpDeptEntry extends CoreBillEntryBase implements IHelpDeptEntry
{
    public HelpDeptEntry()
    {
        super();
        registerInterface(IHelpDeptEntry.class, this);
    }
    public HelpDeptEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IHelpDeptEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0ECA66D6");
    }
    private HelpDeptEntryController getController() throws BOSException
    {
        return (HelpDeptEntryController)getBizController();
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public HelpDeptEntryCollection getHelpDeptEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getHelpDeptEntryCollection(getContext(), oql);
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
    public HelpDeptEntryCollection getHelpDeptEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getHelpDeptEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public HelpDeptEntryCollection getHelpDeptEntryCollection() throws BOSException
    {
        try {
            return getController().getHelpDeptEntryCollection(getContext());
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
    public HelpDeptEntryInfo getHelpDeptEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getHelpDeptEntryInfo(getContext(), oql);
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
    public HelpDeptEntryInfo getHelpDeptEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getHelpDeptEntryInfo(getContext(), pk, selector);
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
    public HelpDeptEntryInfo getHelpDeptEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getHelpDeptEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}