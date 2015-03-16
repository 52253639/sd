package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;

public class EnrollmentUIFacade extends AbstractBizCtrl implements IEnrollmentUIFacade
{
    public EnrollmentUIFacade()
    {
        super();
        registerInterface(IEnrollmentUIFacade.class, this);
    }
    public EnrollmentUIFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IEnrollmentUIFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("27DF0D6D");
    }
    private EnrollmentUIFacadeController getController() throws BOSException
    {
        return (EnrollmentUIFacadeController)getBizController();
    }
    /**
     *查询统计会员-User defined method
     *@param saleLongNumber 销售组织长编码
     *@param beginQueryDate 起始时间
     *@param endQueryDate 结束时间
     *@return
     */
    public IRowSet getInisder(String saleLongNumber, String beginQueryDate, String endQueryDate) throws BOSException, EASBizException
    {
        try {
            return getController().getInisder(getContext(), saleLongNumber, beginQueryDate, endQueryDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}