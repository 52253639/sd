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
import com.kingdee.eas.framework.IBillEntryBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IAdjustRecordEntry extends IBillEntryBase
{
    public AdjustRecordEntryInfo getAdjustRecordEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AdjustRecordEntryInfo getAdjustRecordEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AdjustRecordEntryInfo getAdjustRecordEntryInfo(String oql) throws BOSException, EASBizException;
    public AdjustRecordEntryCollection getAdjustRecordEntryCollection() throws BOSException;
    public AdjustRecordEntryCollection getAdjustRecordEntryCollection(EntityViewInfo view) throws BOSException;
    public AdjustRecordEntryCollection getAdjustRecordEntryCollection(String oql) throws BOSException;
}