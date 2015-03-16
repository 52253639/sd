package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;
import com.kingdee.eas.framework.CoreBaseCollection;

public class AgioScheme extends FDCDataBase implements IAgioScheme
{
    public AgioScheme()
    {
        super();
        registerInterface(IAgioScheme.class, this);
    }
    public AgioScheme(Context ctx)
    {
        super(ctx);
        registerInterface(IAgioScheme.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("918C91AC");
    }
    private AgioSchemeController getController() throws BOSException
    {
        return (AgioSchemeController)getBizController();
    }
}