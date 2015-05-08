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

public class AchievementDocEntry extends CoreBillEntryBase implements IAchievementDocEntry
{
    public AchievementDocEntry()
    {
        super();
        registerInterface(IAchievementDocEntry.class, this);
    }
    public AchievementDocEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IAchievementDocEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5A8292D3");
    }
    private AchievementDocEntryController getController() throws BOSException
    {
        return (AchievementDocEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public AchievementDocEntryInfo getAchievementDocEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAchievementDocEntryInfo(getContext(), pk);
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
    public AchievementDocEntryInfo getAchievementDocEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAchievementDocEntryInfo(getContext(), pk, selector);
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
    public AchievementDocEntryInfo getAchievementDocEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAchievementDocEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public AchievementDocEntryCollection getAchievementDocEntryCollection() throws BOSException
    {
        try {
            return getController().getAchievementDocEntryCollection(getContext());
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
    public AchievementDocEntryCollection getAchievementDocEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAchievementDocEntryCollection(getContext(), view);
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
    public AchievementDocEntryCollection getAchievementDocEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getAchievementDocEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}