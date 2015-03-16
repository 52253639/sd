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

public class InviteEntSuppChkBillEntry extends CoreBillEntryBase implements IInviteEntSuppChkBillEntry
{
    public InviteEntSuppChkBillEntry()
    {
        super();
        registerInterface(IInviteEntSuppChkBillEntry.class, this);
    }
    public InviteEntSuppChkBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IInviteEntSuppChkBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F34CDE45");
    }
    private InviteEntSuppChkBillEntryController getController() throws BOSException
    {
        return (InviteEntSuppChkBillEntryController)getBizController();
    }
}