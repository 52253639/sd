package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.List;
import java.util.Set;

public class StandardTaskGuideFacade extends AbstractBizCtrl implements IStandardTaskGuideFacade
{
    public StandardTaskGuideFacade()
    {
        super();
        registerInterface(IStandardTaskGuideFacade.class, this);
    }
    public StandardTaskGuideFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IStandardTaskGuideFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0C0072DE");
    }
    private StandardTaskGuideFacadeController getController() throws BOSException
    {
        return (StandardTaskGuideFacadeController)getBizController();
    }
    /**
     *删除-User defined method
     *@param list list
     */
    public void standardTaskGuideDel(List list) throws BOSException
    {
        try {
            getController().standardTaskGuideDel(getContext(), list);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到所有ID-User defined method
     *@param list list
     *@return
     */
    public Set getAllId(List list) throws BOSException
    {
        try {
            return getController().getAllId(getContext(), list);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}