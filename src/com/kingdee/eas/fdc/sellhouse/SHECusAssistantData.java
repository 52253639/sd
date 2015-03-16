package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class SHECusAssistantData extends FDCDataBase implements ISHECusAssistantData
{
    public SHECusAssistantData()
    {
        super();
        registerInterface(ISHECusAssistantData.class, this);
    }
    public SHECusAssistantData(Context ctx)
    {
        super(ctx);
        registerInterface(ISHECusAssistantData.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9D3830FC");
    }
    private SHECusAssistantDataController getController() throws BOSException
    {
        return (SHECusAssistantDataController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SHECusAssistantDataInfo getSHECusAssistantDataInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSHECusAssistantDataInfo(getContext(), pk);
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
    public SHECusAssistantDataInfo getSHECusAssistantDataInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSHECusAssistantDataInfo(getContext(), pk, selector);
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
    public SHECusAssistantDataInfo getSHECusAssistantDataInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSHECusAssistantDataInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SHECusAssistantDataCollection getSHECusAssistantDataCollection() throws BOSException
    {
        try {
            return getController().getSHECusAssistantDataCollection(getContext());
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
    public SHECusAssistantDataCollection getSHECusAssistantDataCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSHECusAssistantDataCollection(getContext(), view);
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
    public SHECusAssistantDataCollection getSHECusAssistantDataCollection(String oql) throws BOSException
    {
        try {
            return getController().getSHECusAssistantDataCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}