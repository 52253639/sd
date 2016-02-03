package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface ICommissionApply extends IFDCBill
{
    public CommissionApplyInfo getCommissionApplyInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CommissionApplyInfo getCommissionApplyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CommissionApplyInfo getCommissionApplyInfo(String oql) throws BOSException, EASBizException;
    public CommissionApplyCollection getCommissionApplyCollection() throws BOSException;
    public CommissionApplyCollection getCommissionApplyCollection(EntityViewInfo view) throws BOSException;
    public CommissionApplyCollection getCommissionApplyCollection(String oql) throws BOSException;
    public void pay(BOSUuid id) throws BOSException, EASBizException;
}