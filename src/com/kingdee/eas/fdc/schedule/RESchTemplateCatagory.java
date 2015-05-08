package com.kingdee.eas.fdc.schedule;

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
import com.kingdee.eas.fdc.basedata.IFDCTreeBaseData;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseData;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class RESchTemplateCatagory extends FDCTreeBaseData implements IRESchTemplateCatagory
{
    public RESchTemplateCatagory()
    {
        super();
        registerInterface(IRESchTemplateCatagory.class, this);
    }
    public RESchTemplateCatagory(Context ctx)
    {
        super(ctx);
        registerInterface(IRESchTemplateCatagory.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F425DEA3");
    }
    private RESchTemplateCatagoryController getController() throws BOSException
    {
        return (RESchTemplateCatagoryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RESchTemplateCatagoryInfo getRESchTemplateCatagoryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRESchTemplateCatagoryInfo(getContext(), pk);
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
    public RESchTemplateCatagoryInfo getRESchTemplateCatagoryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRESchTemplateCatagoryInfo(getContext(), pk, selector);
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
    public RESchTemplateCatagoryInfo getRESchTemplateCatagoryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRESchTemplateCatagoryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RESchTemplateCatagoryCollection getRESchTemplateCatagoryCollection() throws BOSException
    {
        try {
            return getController().getRESchTemplateCatagoryCollection(getContext());
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
    public RESchTemplateCatagoryCollection getRESchTemplateCatagoryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRESchTemplateCatagoryCollection(getContext(), view);
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
    public RESchTemplateCatagoryCollection getRESchTemplateCatagoryCollection(String oql) throws BOSException
    {
        try {
            return getController().getRESchTemplateCatagoryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}