package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import java.util.Set;

public class InvitePreSplitCostAmountFacade extends AbstractBizCtrl implements IInvitePreSplitCostAmountFacade
{
    public InvitePreSplitCostAmountFacade()
    {
        super();
        registerInterface(IInvitePreSplitCostAmountFacade.class, this);
    }
    public InvitePreSplitCostAmountFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IInvitePreSplitCostAmountFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("51EC1423");
    }
    private InvitePreSplitCostAmountFacadeController getController() throws BOSException
    {
        return (InvitePreSplitCostAmountFacadeController)getBizController();
    }
    /**
     *获取成本科目相关数据-User defined method
     *@param costAcctCols costAcctCols
     *@return
     */
    public Map getCostAccRelDatas(Set costAcctCols) throws BOSException, EASBizException
    {
        try {
            return getController().getCostAccRelDatas(getContext(), costAcctCols);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}