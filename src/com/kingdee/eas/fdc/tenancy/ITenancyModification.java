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
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ITenancyModification extends ITenBillBase
{
    public TenancyModificationInfo getTenancyModificationInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TenancyModificationInfo getTenancyModificationInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TenancyModificationInfo getTenancyModificationInfo(String oql) throws BOSException, EASBizException;
    public TenancyModificationCollection getTenancyModificationCollection() throws BOSException;
    public TenancyModificationCollection getTenancyModificationCollection(EntityViewInfo view) throws BOSException;
    public TenancyModificationCollection getTenancyModificationCollection(String oql) throws BOSException;
    public boolean incNewAddCheck(String tenBillId, Date incNewDate) throws BOSException;
    public boolean incNewEditCheck(String tenBillId, Date incNewDate) throws BOSException;
    public boolean freesNewAddCheck(String tenBillId, Date stratDate, Date endDate) throws BOSException;
    public boolean freesNewEditCheck(String tenBillId, Date startDate, Date endDate) throws BOSException;
    public Date getLeastPaidDate(String tenBillID) throws BOSException;
}