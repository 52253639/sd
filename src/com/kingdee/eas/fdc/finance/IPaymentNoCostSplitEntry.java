package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.IFDCNoCostSplitBillEntry;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IPaymentNoCostSplitEntry extends IFDCNoCostSplitBillEntry
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public PaymentNoCostSplitEntryInfo getPaymentNoCostSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PaymentNoCostSplitEntryInfo getPaymentNoCostSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PaymentNoCostSplitEntryInfo getPaymentNoCostSplitEntryInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(PaymentNoCostSplitEntryInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, PaymentNoCostSplitEntryInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, PaymentNoCostSplitEntryInfo model) throws BOSException, EASBizException;
    public void updatePartial(PaymentNoCostSplitEntryInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, PaymentNoCostSplitEntryInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public PaymentNoCostSplitEntryCollection getPaymentNoCostSplitEntryCollection() throws BOSException;
    public PaymentNoCostSplitEntryCollection getPaymentNoCostSplitEntryCollection(EntityViewInfo view) throws BOSException;
    public PaymentNoCostSplitEntryCollection getPaymentNoCostSplitEntryCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}