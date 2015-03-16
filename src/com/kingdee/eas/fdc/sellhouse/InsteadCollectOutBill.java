package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.eas.fdc.propertymgmt.IPPMProjectBill;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMProjectBill;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class InsteadCollectOutBill extends PPMProjectBill implements IInsteadCollectOutBill
{
    public InsteadCollectOutBill()
    {
        super();
        registerInterface(IInsteadCollectOutBill.class, this);
    }
    public InsteadCollectOutBill(Context ctx)
    {
        super(ctx);
        registerInterface(IInsteadCollectOutBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F2A687D2");
    }
    private InsteadCollectOutBillController getController() throws BOSException
    {
        return (InsteadCollectOutBillController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public InsteadCollectOutBillCollection getInsteadCollectOutBillCollection() throws BOSException
    {
        try {
            return getController().getInsteadCollectOutBillCollection(getContext());
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
    public InsteadCollectOutBillCollection getInsteadCollectOutBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInsteadCollectOutBillCollection(getContext(), view);
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
    public InsteadCollectOutBillCollection getInsteadCollectOutBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getInsteadCollectOutBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public InsteadCollectOutBillInfo getInsteadCollectOutBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInsteadCollectOutBillInfo(getContext(), pk);
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
    public InsteadCollectOutBillInfo getInsteadCollectOutBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInsteadCollectOutBillInfo(getContext(), pk, selector);
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
    public InsteadCollectOutBillInfo getInsteadCollectOutBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInsteadCollectOutBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量转出-User defined method
     *@param personInfo 代付人员
     *@param bizDate 代付日期
     *@param rows 批量代付的所有行
     */
    public void generateNewData(PersonInfo personInfo, Date bizDate, Set rows) throws BOSException, SellHouseException
    {
        try {
            getController().generateNewData(getContext(), personInfo, bizDate, rows);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}