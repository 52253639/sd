package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;

public class FDCSplAuditBillEntry extends CoreBase implements IFDCSplAuditBillEntry
{
    public FDCSplAuditBillEntry()
    {
        super();
        registerInterface(IFDCSplAuditBillEntry.class, this);
    }
    public FDCSplAuditBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSplAuditBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7F7113F2");
    }
    private FDCSplAuditBillEntryController getController() throws BOSException
    {
        return (FDCSplAuditBillEntryController)getBizController();
    }
}