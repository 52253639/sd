package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class BuildingCompensate extends FDCBill implements IBuildingCompensate
{
    public BuildingCompensate()
    {
        super();
        registerInterface(IBuildingCompensate.class, this);
    }
    public BuildingCompensate(Context ctx)
    {
        super(ctx);
        registerInterface(IBuildingCompensate.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7E46DD26");
    }
    private BuildingCompensateController getController() throws BOSException
    {
        return (BuildingCompensateController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public BuildingCompensateInfo getBuildingCompensateInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingCompensateInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public BuildingCompensateInfo getBuildingCompensateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingCompensateInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public BuildingCompensateInfo getBuildingCompensateInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingCompensateInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public BuildingCompensateCollection getBuildingCompensateCollection() throws BOSException
    {
        try {
            return getController().getBuildingCompensateCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public BuildingCompensateCollection getBuildingCompensateCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBuildingCompensateCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public BuildingCompensateCollection getBuildingCompensateCollection(String oql) throws BOSException
    {
        try {
            return getController().getBuildingCompensateCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}