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
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.ICoreBillBase;

public interface ITenBillBase extends ICoreBillBase
{
    public TenBillBaseInfo getTenBillBaseInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TenBillBaseInfo getTenBillBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TenBillBaseInfo getTenBillBaseInfo(String oql) throws BOSException, EASBizException;
    public TenBillBaseCollection getTenBillBaseCollection(EntityViewInfo view) throws BOSException;
    public TenBillBaseCollection getTenBillBaseCollection(String oql) throws BOSException;
    public TenBillBaseCollection getTenBillBaseCollection() throws BOSException;
    public void audit(BOSUuid billId) throws BOSException, EASBizException;
    public void unAudit(BOSUuid billId) throws BOSException, EASBizException;
    public void setAudittingStatus(BOSUuid billId) throws BOSException, EASBizException;
    public void setSubmitStatus(BOSUuid billId) throws BOSException, EASBizException;
    public boolean checkCanSubmit(String id) throws BOSException, EASBizException;
    public Map fetchInitData(Map paramMap) throws BOSException, EASBizException;
    public Map fetchFilterInitData(Map paramMap) throws BOSException, EASBizException;
}