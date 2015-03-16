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

public class MortagageControlEntry extends FDCDataBase implements IMortagageControlEntry
{
    public MortagageControlEntry()
    {
        super();
        registerInterface(IMortagageControlEntry.class, this);
    }
    public MortagageControlEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IMortagageControlEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("57C7A7EF");
    }
    private MortagageControlEntryController getController() throws BOSException
    {
        return (MortagageControlEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public MortagageControlEntryInfo getMortagageControlEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMortagageControlEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MortagageControlEntryInfo getMortagageControlEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMortagageControlEntryInfo(getContext(), pk);
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
    public MortagageControlEntryInfo getMortagageControlEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMortagageControlEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MortagageControlEntryCollection getMortagageControlEntryCollection() throws BOSException
    {
        try {
            return getController().getMortagageControlEntryCollection(getContext());
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
    public MortagageControlEntryCollection getMortagageControlEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMortagageControlEntryCollection(getContext(), view);
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
    public MortagageControlEntryCollection getMortagageControlEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getMortagageControlEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}