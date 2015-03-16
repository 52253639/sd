package com.kingdee.eas.fdc.aimcost;

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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IMeasureEntry extends IDataBase
{
    public MeasureEntryInfo getMeasureEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MeasureEntryInfo getMeasureEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MeasureEntryInfo getMeasureEntryInfo(String oql) throws BOSException, EASBizException;
    public MeasureEntryCollection getMeasureEntryCollection() throws BOSException;
    public MeasureEntryCollection getMeasureEntryCollection(EntityViewInfo view) throws BOSException;
    public MeasureEntryCollection getMeasureEntryCollection(String oql) throws BOSException;
}