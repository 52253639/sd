package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basecrm.app.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;

public class BasePayList extends CoreBase implements IBasePayList
{
    public BasePayList()
    {
        super();
        registerInterface(IBasePayList.class, this);
    }
    public BasePayList(Context ctx)
    {
        super(ctx);
        registerInterface(IBasePayList.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("95DA079A");
    }
    private BasePayListController getController() throws BOSException
    {
        return (BasePayListController)getBizController();
    }
}