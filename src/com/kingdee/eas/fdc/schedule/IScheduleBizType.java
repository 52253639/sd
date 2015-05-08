package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IScheduleBizType extends IFDCDataBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public ScheduleBizTypeInfo getScheduleBizTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ScheduleBizTypeInfo getScheduleBizTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ScheduleBizTypeInfo getScheduleBizTypeInfo(String oql) throws BOSException, EASBizException;
    public ScheduleBizTypeCollection getScheduleBizTypeCollection() throws BOSException;
    public ScheduleBizTypeCollection getScheduleBizTypeCollection(EntityViewInfo view) throws BOSException;
    public ScheduleBizTypeCollection getScheduleBizTypeCollection(String oql) throws BOSException;
    public String getBiggestNumber() throws BOSException;
}