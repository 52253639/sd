package com.kingdee.eas.fdc.schedule;

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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IFDCMonthTempTaskEntry extends ICoreBillEntryBase
{
    public FDCMonthTempTaskEntryInfo getFDCMonthTempTaskEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCMonthTempTaskEntryInfo getFDCMonthTempTaskEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCMonthTempTaskEntryInfo getFDCMonthTempTaskEntryInfo(String oql) throws BOSException, EASBizException;
    public FDCMonthTempTaskEntryCollection getFDCMonthTempTaskEntryCollection() throws BOSException;
    public FDCMonthTempTaskEntryCollection getFDCMonthTempTaskEntryCollection(EntityViewInfo view) throws BOSException;
    public FDCMonthTempTaskEntryCollection getFDCMonthTempTaskEntryCollection(String oql) throws BOSException;
}