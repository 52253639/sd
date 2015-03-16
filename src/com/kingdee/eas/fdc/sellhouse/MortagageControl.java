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

public class MortagageControl extends FDCBill implements IMortagageControl
{
    public MortagageControl()
    {
        super();
        registerInterface(IMortagageControl.class, this);
    }
    public MortagageControl(Context ctx)
    {
        super(ctx);
        registerInterface(IMortagageControl.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DDBA6243");
    }
    private MortagageControlController getController() throws BOSException
    {
        return (MortagageControlController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MortagageControlInfo getMortagageControlInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMortagageControlInfo(getContext(), pk);
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
    public MortagageControlInfo getMortagageControlInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMortagageControlInfo(getContext(), pk, selector);
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
    public MortagageControlInfo getMortagageControlInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMortagageControlInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MortagageControlCollection getMortagageControlCollection() throws BOSException
    {
        try {
            return getController().getMortagageControlCollection(getContext());
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
    public MortagageControlCollection getMortagageControlCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMortagageControlCollection(getContext(), view);
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
    public MortagageControlCollection getMortagageControlCollection(String oql) throws BOSException
    {
        try {
            return getController().getMortagageControlCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *解除抵押-User defined method
     *@param model model
     *@return
     */
    public boolean antiMortagage(MortagageControlInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().antiMortagage(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批-User defined method
     *@param model model
     *@return
     */
    public boolean audit(MortagageControlInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().audit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批-User defined method
     *@param model model
     *@return
     */
    public boolean unAudit(MortagageControlInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().unAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *置审批中-User defined method
     *@param model model
     */
    public void setAuditingStatus(MortagageControlInfo model) throws BOSException, EASBizException
    {
        try {
            getController().setAuditingStatus(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}