package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import java.util.HashMap;
import com.kingdee.eas.fdc.market.app.*;

public class MonthFactValueFacade extends AbstractBizCtrl implements IMonthFactValueFacade
{
    public MonthFactValueFacade()
    {
        super();
        registerInterface(IMonthFactValueFacade.class, this);
    }
    public MonthFactValueFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IMonthFactValueFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("70D10410");
    }
    private MonthFactValueFacadeController getController() throws BOSException
    {
        return (MonthFactValueFacadeController)getBizController();
    }
    /**
     *获取实际值-User defined method
     *@param map 参数
     *@return
     */
    public IRowSet getFactValue(HashMap map) throws BOSException, EASBizException
    {
        try {
            return getController().getFactValue(getContext(), map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取最新年度版值-User defined method
     *@param ProjectId ProjectId
     *@param year year
     *@return
     */
    public IRowSet getYearValue(String ProjectId, String year) throws BOSException, EASBizException
    {
        try {
            return getController().getYearValue(getContext(), ProjectId, year);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取最新版本值-User defined method
     *@param projectId projectId
     *@return
     */
    public IRowSet getLastValue(String projectId) throws BOSException, EASBizException
    {
        try {
            return getController().getLastValue(getContext(), projectId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}