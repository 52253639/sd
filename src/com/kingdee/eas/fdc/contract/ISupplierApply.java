package com.kingdee.eas.fdc.contract;

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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ISupplierApply extends IFDCBill
{
    public SupplierApplyCollection getSupplierApplyCollection() throws BOSException;
    public SupplierApplyCollection getSupplierApplyCollection(EntityViewInfo view) throws BOSException;
    public SupplierApplyCollection getSupplierApplyCollection(String oql) throws BOSException;
    public SupplierApplyInfo getSupplierApplyInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SupplierApplyInfo getSupplierApplyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SupplierApplyInfo getSupplierApplyInfo(String oql) throws BOSException, EASBizException;
}