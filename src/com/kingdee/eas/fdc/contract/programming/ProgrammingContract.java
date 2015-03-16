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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.programming.app.*;
import com.kingdee.eas.framework.TreeBase;

public class ProgrammingContract extends TreeBase implements IProgrammingContract
{
    public ProgrammingContract()
    {
        super();
        registerInterface(IProgrammingContract.class, this);
    }
    public ProgrammingContract(Context ctx)
    {
        super(ctx);
        registerInterface(IProgrammingContract.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("ECE079DB");
    }
    private ProgrammingContractController getController() throws BOSException
    {
        return (ProgrammingContractController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ProgrammingContractInfo getProgrammingContractInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingContractInfo(getContext(), pk);
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
    public ProgrammingContractInfo getProgrammingContractInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingContractInfo(getContext(), pk, selector);
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
    public ProgrammingContractInfo getProgrammingContractInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingContractInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProgrammingContractCollection getProgrammingContractCollection() throws BOSException
    {
        try {
            return getController().getProgrammingContractCollection(getContext());
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
    public ProgrammingContractCollection getProgrammingContractCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProgrammingContractCollection(getContext(), view);
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
    public ProgrammingContractCollection getProgrammingContractCollection(String oql) throws BOSException
    {
        try {
            return getController().getProgrammingContractCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}