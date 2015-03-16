package com.kingdee.eas.fdc.sellhouse;

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

public interface IDayInitDataBld extends ICoreBase
{
    public DayInitDataBldInfo getDayInitDataBldInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DayInitDataBldInfo getDayInitDataBldInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DayInitDataBldInfo getDayInitDataBldInfo(String oql) throws BOSException, EASBizException;
    public DayInitDataBldCollection getDayInitDataBldCollection() throws BOSException;
    public DayInitDataBldCollection getDayInitDataBldCollection(EntityViewInfo view) throws BOSException;
    public DayInitDataBldCollection getDayInitDataBldCollection(String oql) throws BOSException;
}