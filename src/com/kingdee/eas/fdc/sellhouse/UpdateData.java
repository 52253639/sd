package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;

public class UpdateData extends DataBase implements IUpdateData
{
    public UpdateData()
    {
        super();
        registerInterface(IUpdateData.class, this);
    }
    public UpdateData(Context ctx)
    {
        super(ctx);
        registerInterface(IUpdateData.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2A0FE12E");
    }
    private UpdateDataController getController() throws BOSException
    {
        return (UpdateDataController)getBizController();
    }
    /**
     *Êý¾ÝÉý¼¶-User defined method
     *@param idSet idSet
     *@return
     */
    public boolean executeUpdate(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().executeUpdate(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}