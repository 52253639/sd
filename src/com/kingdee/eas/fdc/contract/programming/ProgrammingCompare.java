package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.programming.app.*;

public class ProgrammingCompare extends CoreBillEntryBase implements IProgrammingCompare
{
    public ProgrammingCompare()
    {
        super();
        registerInterface(IProgrammingCompare.class, this);
    }
    public ProgrammingCompare(Context ctx)
    {
        super(ctx);
        registerInterface(IProgrammingCompare.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("93F716FC");
    }
    private ProgrammingCompareController getController() throws BOSException
    {
        return (ProgrammingCompareController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ProgrammingCompareInfo getProgrammingCompareInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingCompareInfo(getContext(), pk);
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
    public ProgrammingCompareInfo getProgrammingCompareInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingCompareInfo(getContext(), pk, selector);
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
    public ProgrammingCompareInfo getProgrammingCompareInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingCompareInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProgrammingCompareCollection getProgrammingCompareCollection() throws BOSException
    {
        try {
            return getController().getProgrammingCompareCollection(getContext());
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
    public ProgrammingCompareCollection getProgrammingCompareCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProgrammingCompareCollection(getContext(), view);
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
    public ProgrammingCompareCollection getProgrammingCompareCollection(String oql) throws BOSException
    {
        try {
            return getController().getProgrammingCompareCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}