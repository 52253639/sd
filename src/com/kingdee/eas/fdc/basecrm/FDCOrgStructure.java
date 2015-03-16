package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basecrm.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.framework.ICoreBase;

public class FDCOrgStructure extends CoreBase implements IFDCOrgStructure
{
    public FDCOrgStructure()
    {
        super();
        registerInterface(IFDCOrgStructure.class, this);
    }
    public FDCOrgStructure(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCOrgStructure.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6A12CC18");
    }
    private FDCOrgStructureController getController() throws BOSException
    {
        return (FDCOrgStructureController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCOrgStructureInfo getFDCOrgStructureInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCOrgStructureInfo(getContext(), pk);
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
    public FDCOrgStructureInfo getFDCOrgStructureInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCOrgStructureInfo(getContext(), pk, selector);
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
    public FDCOrgStructureInfo getFDCOrgStructureInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCOrgStructureInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCOrgStructureCollection getFDCOrgStructureCollection() throws BOSException
    {
        try {
            return getController().getFDCOrgStructureCollection(getContext());
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
    public FDCOrgStructureCollection getFDCOrgStructureCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCOrgStructureCollection(getContext(), view);
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
    public FDCOrgStructureCollection getFDCOrgStructureCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCOrgStructureCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updateData-User defined method
     */
    public void updateData() throws BOSException, EASBizException
    {
        try {
            getController().updateData(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *saveData-User defined method
     *@param idList idList
     */
    public void saveData(List idList) throws BOSException, EASBizException
    {
        try {
            getController().saveData(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}