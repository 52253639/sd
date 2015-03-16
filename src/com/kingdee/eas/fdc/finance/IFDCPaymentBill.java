package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.HashMap;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import java.util.HashSet;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface IFDCPaymentBill extends IFDCBill
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public FDCPaymentBillInfo getFDCPaymentBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCPaymentBillInfo getFDCPaymentBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCPaymentBillInfo getFDCPaymentBillInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(FDCPaymentBillInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, FDCPaymentBillInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, FDCPaymentBillInfo model) throws BOSException, EASBizException;
    public void updatePartial(FDCPaymentBillInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, FDCPaymentBillInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public FDCPaymentBillCollection getFDCPaymentBillCollection() throws BOSException;
    public FDCPaymentBillCollection getFDCPaymentBillCollection(EntityViewInfo view) throws BOSException;
    public FDCPaymentBillCollection getFDCPaymentBillCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public HashMap getIsRespiteByPaymentBillIds(HashSet idSet) throws BOSException, EASBizException;
    public void updatePeriodAfterAudit(List idList) throws BOSException, EASBizException;
    public void updateInvoiceAmt(IObjectValue fdcPayment, PaymentBillInfo paymentBill) throws BOSException, EASBizException;
}