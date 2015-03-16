package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IPurchaseChangeCustomer extends IFDCBill
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public PurchaseChangeCustomerInfo getPurchaseChangeCustomerInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PurchaseChangeCustomerInfo getPurchaseChangeCustomerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PurchaseChangeCustomerInfo getPurchaseChangeCustomerInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(PurchaseChangeCustomerInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, PurchaseChangeCustomerInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, PurchaseChangeCustomerInfo model) throws BOSException, EASBizException;
    public void updatePartial(PurchaseChangeCustomerInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, PurchaseChangeCustomerInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public PurchaseChangeCustomerCollection getPurchaseChangeCustomerCollection() throws BOSException;
    public PurchaseChangeCustomerCollection getPurchaseChangeCustomerCollection(EntityViewInfo view) throws BOSException;
    public PurchaseChangeCustomerCollection getPurchaseChangeCustomerCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public void setState(BOSUuid id, FDCBillStateEnum state) throws BOSException, EASBizException;
    public void purchaseChangeCustomerToPurchase(BOSUuid id) throws BOSException, EASBizException;
}