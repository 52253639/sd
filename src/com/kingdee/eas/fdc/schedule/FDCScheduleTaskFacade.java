package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.HashMap;
import java.util.HashSet;

public class FDCScheduleTaskFacade extends AbstractBizCtrl implements IFDCScheduleTaskFacade
{
    public FDCScheduleTaskFacade()
    {
        super();
        registerInterface(IFDCScheduleTaskFacade.class, this);
    }
    public FDCScheduleTaskFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCScheduleTaskFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("CB7402A5");
    }
    private FDCScheduleTaskFacadeController getController() throws BOSException
    {
        return (FDCScheduleTaskFacadeController)getBizController();
    }
    /**
     *获取项目节点下的主、专项节点-User defined method
     *@param projectNodes 工程项目节点集合
     *@return
     */
    public HashMap getMuTaskTreeNodes(HashSet projectNodes) throws BOSException, EASBizException
    {
        try {
            return getController().getMuTaskTreeNodes(getContext(), projectNodes);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}