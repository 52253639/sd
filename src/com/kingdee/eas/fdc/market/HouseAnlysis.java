package com.kingdee.eas.fdc.market;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;
import com.kingdee.eas.fdc.market.app.*;

public class HouseAnlysis extends FDCDataBase implements IHouseAnlysis
{
    public HouseAnlysis()
    {
        super();
        registerInterface(IHouseAnlysis.class, this);
    }
    public HouseAnlysis(Context ctx)
    {
        super(ctx);
        registerInterface(IHouseAnlysis.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8B48F134");
    }
    private HouseAnlysisController getController() throws BOSException
    {
        return (HouseAnlysisController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public HouseAnlysisInfo getHouseAnlysisInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getHouseAnlysisInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public HouseAnlysisInfo getHouseAnlysisInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getHouseAnlysisInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public HouseAnlysisInfo getHouseAnlysisInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getHouseAnlysisInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public HouseAnlysisCollection getHouseAnlysisCollection() throws BOSException
    {
        try {
            return getController().getHouseAnlysisCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public HouseAnlysisCollection getHouseAnlysisCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getHouseAnlysisCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public HouseAnlysisCollection getHouseAnlysisCollection(String oql) throws BOSException
    {
        try {
            return getController().getHouseAnlysisCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}