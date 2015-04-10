package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IInvoiceBill extends ITenBillBase
{
    public InvoiceBillInfo getInvoiceBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public InvoiceBillInfo getInvoiceBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public InvoiceBillInfo getInvoiceBillInfo(String oql) throws BOSException, EASBizException;
    public InvoiceBillCollection getInvoiceBillCollection() throws BOSException;
    public InvoiceBillCollection getInvoiceBillCollection(EntityViewInfo view) throws BOSException;
    public InvoiceBillCollection getInvoiceBillCollection(String oql) throws BOSException;
}