package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class FDCCostLog extends CoreBase implements IFDCCostLog
{
    public FDCCostLog()
    {
        super();
        registerInterface(IFDCCostLog.class, this);
    }
    public FDCCostLog(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCCostLog.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D654F9A9");
    }
    private FDCCostLogController getController() throws BOSException
    {
        return (FDCCostLogController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public FDCCostLogCollection getFDCCostLogCollection() throws BOSException
    {
        try {
            return getController().getFDCCostLogCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public FDCCostLogCollection getFDCCostLogCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCCostLogCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public FDCCostLogCollection getFDCCostLogCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCCostLogCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public FDCCostLogInfo getFDCCostLogInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostLogInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public FDCCostLogInfo getFDCCostLogInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostLogInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public FDCCostLogInfo getFDCCostLogInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostLogInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据月获取历史-User defined method
     *@param selectMonth selectMonth
     *@return
     */
    public Map getHistoryByMonth(Map selectMonth) throws BOSException, EASBizException
    {
        try {
            return getController().getHistoryByMonth(getContext(), selectMonth);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}