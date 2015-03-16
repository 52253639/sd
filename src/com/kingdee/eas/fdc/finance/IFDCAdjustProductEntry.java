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

public interface IFDCAdjustProductEntry extends ICoreBase
{
    public FDCAdjustProductEntryInfo getFDCAdjustProductEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCAdjustProductEntryInfo getFDCAdjustProductEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCAdjustProductEntryInfo getFDCAdjustProductEntryInfo(String oql) throws BOSException, EASBizException;
    public FDCAdjustProductEntryCollection getFDCAdjustProductEntryCollection() throws BOSException;
    public FDCAdjustProductEntryCollection getFDCAdjustProductEntryCollection(EntityViewInfo view) throws BOSException;
    public FDCAdjustProductEntryCollection getFDCAdjustProductEntryCollection(String oql) throws BOSException;
}