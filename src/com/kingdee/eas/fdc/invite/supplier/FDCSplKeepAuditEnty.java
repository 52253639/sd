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

public class FDCSplKeepAuditEnty extends CoreBase implements IFDCSplKeepAuditEnty
{
    public FDCSplKeepAuditEnty()
    {
        super();
        registerInterface(IFDCSplKeepAuditEnty.class, this);
    }
    public FDCSplKeepAuditEnty(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSplKeepAuditEnty.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0EC9AC42");
    }
    private FDCSplKeepAuditEntyController getController() throws BOSException
    {
        return (FDCSplKeepAuditEntyController)getBizController();
    }
}