package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public class FDCSplKeepAuditIndexValue extends CoreBillEntryBase implements IFDCSplKeepAuditIndexValue
{
    public FDCSplKeepAuditIndexValue()
    {
        super();
        registerInterface(IFDCSplKeepAuditIndexValue.class, this);
    }
    public FDCSplKeepAuditIndexValue(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSplKeepAuditIndexValue.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("69F97413");
    }
    private FDCSplKeepAuditIndexValueController getController() throws BOSException
    {
        return (FDCSplKeepAuditIndexValueController)getBizController();
    }
}