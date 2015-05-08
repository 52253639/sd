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
import com.kingdee.eas.framework.ICoreBase;

public interface IScheduleTaskBizType extends ICoreBase
{
    public ScheduleTaskBizTypeInfo getScheduleTaskBizTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ScheduleTaskBizTypeInfo getScheduleTaskBizTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ScheduleTaskBizTypeInfo getScheduleTaskBizTypeInfo(String oql) throws BOSException, EASBizException;
    public ScheduleTaskBizTypeCollection getScheduleTaskBizTypeCollection() throws BOSException;
    public ScheduleTaskBizTypeCollection getScheduleTaskBizTypeCollection(EntityViewInfo view) throws BOSException;
    public ScheduleTaskBizTypeCollection getScheduleTaskBizTypeCollection(String oql) throws BOSException;
}