package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.bos.framework.*;

public class WeiChatFacade extends AbstractBizCtrl implements IWeiChatFacade
{
    public WeiChatFacade()
    {
        super();
        registerInterface(IWeiChatFacade.class, this);
    }
    public WeiChatFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IWeiChatFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2CD1C8C4");
    }
    private WeiChatFacadeController getController() throws BOSException
    {
        return (WeiChatFacadeController)getBizController();
    }
    /**
     *同步经纪人-User defined method
     *@param str str
     *@return
     */
    public String synBroker(String str) throws BOSException, EASBizException
    {
        try {
            return getController().synBroker(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步意向客户-User defined method
     *@param str str
     *@return
     */
    public String synCustomer(String str) throws BOSException, EASBizException
    {
        try {
            return getController().synCustomer(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步客户台账-User defined method
     */
    public void synFDCCustomer() throws BOSException, EASBizException
    {
        try {
            getController().synFDCCustomer(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步跟进-User defined method
     */
    public void sysTrackRecord() throws BOSException, EASBizException
    {
        try {
            getController().sysTrackRecord(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}