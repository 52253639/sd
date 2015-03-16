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

public class DayAccumulateBld extends CoreBase implements IDayAccumulateBld
{
    public DayAccumulateBld()
    {
        super();
        registerInterface(IDayAccumulateBld.class, this);
    }
    public DayAccumulateBld(Context ctx)
    {
        super(ctx);
        registerInterface(IDayAccumulateBld.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D9136737");
    }
    private DayAccumulateBldController getController() throws BOSException
    {
        return (DayAccumulateBldController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public DayAccumulateBldInfo getDayAccumulateBldInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDayAccumulateBldInfo(getContext(), pk);
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
    public DayAccumulateBldInfo getDayAccumulateBldInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDayAccumulateBldInfo(getContext(), pk, selector);
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
    public DayAccumulateBldInfo getDayAccumulateBldInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDayAccumulateBldInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DayAccumulateBldCollection getDayAccumulateBldCollection() throws BOSException
    {
        try {
            return getController().getDayAccumulateBldCollection(getContext());
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
    public DayAccumulateBldCollection getDayAccumulateBldCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDayAccumulateBldCollection(getContext(), view);
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
    public DayAccumulateBldCollection getDayAccumulateBldCollection(String oql) throws BOSException
    {
        try {
            return getController().getDayAccumulateBldCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}