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

public class FDCSplAuditIndexValue extends CoreBase implements IFDCSplAuditIndexValue
{
    public FDCSplAuditIndexValue()
    {
        super();
        registerInterface(IFDCSplAuditIndexValue.class, this);
    }
    public FDCSplAuditIndexValue(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSplAuditIndexValue.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A660E158");
    }
    private FDCSplAuditIndexValueController getController() throws BOSException
    {
        return (FDCSplAuditIndexValueController)getBizController();
    }
}