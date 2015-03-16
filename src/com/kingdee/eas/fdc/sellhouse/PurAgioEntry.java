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

public class PurAgioEntry extends AgioEntry implements IPurAgioEntry
{
    public PurAgioEntry()
    {
        super();
        registerInterface(IPurAgioEntry.class, this);
    }
    public PurAgioEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPurAgioEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("500EF1D4");
    }
    private PurAgioEntryController getController() throws BOSException
    {
        return (PurAgioEntryController)getBizController();
    }
}