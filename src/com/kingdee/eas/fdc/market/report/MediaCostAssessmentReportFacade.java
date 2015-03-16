package com.kingdee.eas.fdc.market.report;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.report.*;

public class MediaCostAssessmentReportFacade extends AbstractBizCtrl implements IMediaCostAssessmentReportFacade
{
    public MediaCostAssessmentReportFacade()
    {
        super();
        registerInterface(IMediaCostAssessmentReportFacade.class, this);
    }
    public MediaCostAssessmentReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IMediaCostAssessmentReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EBB7EBAB");
    }
    private MediaCostAssessmentReportFacadeController getController() throws BOSException
    {
        return (MediaCostAssessmentReportFacadeController)getBizController();
    }
    /**
     *获取媒体费用考核表数据-User defined method
     *@param params 参数
     *@return
     */
    public Map getDatas(Map params) throws BOSException, EASBizException
    {
        try {
            return getController().getDatas(getContext(), params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}