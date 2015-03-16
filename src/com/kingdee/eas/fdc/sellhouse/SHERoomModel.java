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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class SHERoomModel extends CoreBase implements ISHERoomModel
{
    public SHERoomModel()
    {
        super();
        registerInterface(ISHERoomModel.class, this);
    }
    public SHERoomModel(Context ctx)
    {
        super(ctx);
        registerInterface(ISHERoomModel.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9C08FE99");
    }
    private SHERoomModelController getController() throws BOSException
    {
        return (SHERoomModelController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SHERoomModelInfo getSHERoomModelInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSHERoomModelInfo(getContext(), pk);
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
    public SHERoomModelInfo getSHERoomModelInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSHERoomModelInfo(getContext(), pk, selector);
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
    public SHERoomModelInfo getSHERoomModelInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSHERoomModelInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SHERoomModelCollection getSHERoomModelCollection() throws BOSException
    {
        try {
            return getController().getSHERoomModelCollection(getContext());
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
    public SHERoomModelCollection getSHERoomModelCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSHERoomModelCollection(getContext(), view);
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
    public SHERoomModelCollection getSHERoomModelCollection(String oql) throws BOSException
    {
        try {
            return getController().getSHERoomModelCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}