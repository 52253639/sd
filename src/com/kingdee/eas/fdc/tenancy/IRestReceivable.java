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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.ICoreBillBase;

public interface IRestReceivable extends ICoreBillBase
{
    public RestReceivableCollection getRestReceivableCollection() throws BOSException;
    public RestReceivableCollection getRestReceivableCollection(EntityViewInfo view) throws BOSException;
    public RestReceivableCollection getRestReceivableCollection(String oql) throws BOSException;
    public RestReceivableInfo getRestReceivableInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RestReceivableInfo getRestReceivableInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RestReceivableInfo getRestReceivableInfo(String oql) throws BOSException, EASBizException;
    public void setAuditting(BOSUuid billId) throws BOSException, EASBizException;
    public void setSubmit(BOSUuid billId) throws BOSException, EASBizException;
    public void audit(BOSUuid billId) throws BOSException, EASBizException;
    public void unAudit(BOSUuid billId) throws BOSException, EASBizException;
}