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
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.framework.ICoreBillBase;

public class CRMCheque extends CoreBillBase implements ICRMCheque
{
    public CRMCheque()
    {
        super();
        registerInterface(ICRMCheque.class, this);
    }
    public CRMCheque(Context ctx)
    {
        super(ctx);
        registerInterface(ICRMCheque.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EF7E1D24");
    }
    private CRMChequeController getController() throws BOSException
    {
        return (CRMChequeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CRMChequeInfo getCRMChequeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCRMChequeInfo(getContext(), pk);
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
    public CRMChequeInfo getCRMChequeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCRMChequeInfo(getContext(), pk, selector);
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
    public CRMChequeInfo getCRMChequeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCRMChequeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CRMChequeCollection getCRMChequeCollection() throws BOSException
    {
        try {
            return getController().getCRMChequeCollection(getContext());
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
    public CRMChequeCollection getCRMChequeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCRMChequeCollection(getContext(), view);
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
    public CRMChequeCollection getCRMChequeCollection(String oql) throws BOSException
    {
        try {
            return getController().getCRMChequeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *登记-User defined method
     *@param model model
     */
    public void addBatch(CRMChequeCollection model) throws BOSException, EASBizException
    {
        try {
            getController().addBatch(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *核销-User defined method
     *@param ids ids
     */
    public void vc(Map ids) throws BOSException, EASBizException
    {
        try {
            getController().vc(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *作废(传递id集合)-User defined method
     *@param ids ids
     */
    public void abandon(List ids) throws BOSException, EASBizException
    {
        try {
            getController().abandon(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *分配  -User defined method
     *@param ids 待分配的票据ID集合
     *@param newKeepOrgUnitId 新的保管组织
     *@param newKeeperId 新保管人
     */
    public void distribute(String[] ids, String newKeepOrgUnitId, String newKeeperId) throws BOSException, EASBizException
    {
        try {
            getController().distribute(getContext(), ids, newKeepOrgUnitId, newKeeperId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *领用-User defined method
     *@param dataMap dataMap
     */
    public void pickCheque(Map dataMap) throws BOSException
    {
        try {
            getController().pickCheque(getContext(), dataMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}