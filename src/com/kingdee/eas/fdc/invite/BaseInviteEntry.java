package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public class BaseInviteEntry extends CoreBillEntryBase implements IBaseInviteEntry
{
    public BaseInviteEntry()
    {
        super();
        registerInterface(IBaseInviteEntry.class, this);
    }
    public BaseInviteEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IBaseInviteEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1EF21BB4");
    }
    private BaseInviteEntryController getController() throws BOSException
    {
        return (BaseInviteEntryController)getBizController();
    }
}