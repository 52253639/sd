package com.kingdee.eas.fdc.finance;

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
import com.kingdee.eas.framework.ICoreBase;

public interface IFDCAdjustBillAllInfo extends ICoreBase
{
    public FDCAdjustBillAllInfoInfo getFDCAdjustBillAllInfoInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCAdjustBillAllInfoInfo getFDCAdjustBillAllInfoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCAdjustBillAllInfoInfo getFDCAdjustBillAllInfoInfo(String oql) throws BOSException, EASBizException;
    public FDCAdjustBillAllInfoCollection getFDCAdjustBillAllInfoCollection() throws BOSException;
    public FDCAdjustBillAllInfoCollection getFDCAdjustBillAllInfoCollection(EntityViewInfo view) throws BOSException;
    public FDCAdjustBillAllInfoCollection getFDCAdjustBillAllInfoCollection(String oql) throws BOSException;
}