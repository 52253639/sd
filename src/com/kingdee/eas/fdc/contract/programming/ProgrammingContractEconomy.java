package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.programming.app.*;
import com.kingdee.eas.framework.ICoreBase;

public class ProgrammingContractEconomy extends CoreBase implements IProgrammingContractEconomy
{
    public ProgrammingContractEconomy()
    {
        super();
        registerInterface(IProgrammingContractEconomy.class, this);
    }
    public ProgrammingContractEconomy(Context ctx)
    {
        super(ctx);
        registerInterface(IProgrammingContractEconomy.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("144467E3");
    }
    private ProgrammingContractEconomyController getController() throws BOSException
    {
        return (ProgrammingContractEconomyController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ProgrammingContractEconomyInfo getProgrammingContractEconomyInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingContractEconomyInfo(getContext(), pk);
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
    public ProgrammingContractEconomyInfo getProgrammingContractEconomyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingContractEconomyInfo(getContext(), pk, selector);
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
    public ProgrammingContractEconomyInfo getProgrammingContractEconomyInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingContractEconomyInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProgrammingContractEconomyCollection getProgrammingContractEconomyCollection() throws BOSException
    {
        try {
            return getController().getProgrammingContractEconomyCollection(getContext());
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
    public ProgrammingContractEconomyCollection getProgrammingContractEconomyCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProgrammingContractEconomyCollection(getContext(), view);
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
    public ProgrammingContractEconomyCollection getProgrammingContractEconomyCollection(String oql) throws BOSException
    {
        try {
            return getController().getProgrammingContractEconomyCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}