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

public class PrePurchaseAgioEntry extends AgioEntry implements IPrePurchaseAgioEntry
{
    public PrePurchaseAgioEntry()
    {
        super();
        registerInterface(IPrePurchaseAgioEntry.class, this);
    }
    public PrePurchaseAgioEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPrePurchaseAgioEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E0CFCEFD");
    }
    private PrePurchaseAgioEntryController getController() throws BOSException
    {
        return (PrePurchaseAgioEntryController)getBizController();
    }
}