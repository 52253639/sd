package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.sellhouse.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;

public class GatheringTrendFacade extends AbstractBizCtrl implements IGatheringTrendFacade
{
    public GatheringTrendFacade()
    {
        super();
        registerInterface(IGatheringTrendFacade.class, this);
    }
    public GatheringTrendFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IGatheringTrendFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E379196B");
    }
    private GatheringTrendFacadeController getController() throws BOSException
    {
        return (GatheringTrendFacadeController)getBizController();
    }
    /**
     *获取收款趋势分析表数据-User defined method
     *@param dateMap 过滤条件
     *@return
     */
    public Map getGatheringData(Map dateMap) throws BOSException, EASBizException
    {
        try {
            return getController().getGatheringData(getContext(), dateMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}