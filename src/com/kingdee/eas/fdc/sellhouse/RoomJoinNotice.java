package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.framework.CoreBillBase;

public class RoomJoinNotice extends CoreBillBase implements IRoomJoinNotice
{
    public RoomJoinNotice()
    {
        super();
        registerInterface(IRoomJoinNotice.class, this);
    }
    public RoomJoinNotice(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomJoinNotice.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D4FD6B78");
    }
    private RoomJoinNoticeController getController() throws BOSException
    {
        return (RoomJoinNoticeController)getBizController();
    }
}