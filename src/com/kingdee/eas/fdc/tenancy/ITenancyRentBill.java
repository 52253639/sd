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

public interface ITenancyRentBill extends ITenBillBase
{
    public TenancyRentBillInfo getTenancyRentBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TenancyRentBillInfo getTenancyRentBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TenancyRentBillInfo getTenancyRentBillInfo(String oql) throws BOSException, EASBizException;
    public TenancyRentBillCollection getTenancyRentBillCollection() throws BOSException;
    public TenancyRentBillCollection getTenancyRentBillCollection(EntityViewInfo view) throws BOSException;
    public TenancyRentBillCollection getTenancyRentBillCollection(String oql) throws BOSException;
    public boolean execute(String id) throws BOSException, EASBizException;
}