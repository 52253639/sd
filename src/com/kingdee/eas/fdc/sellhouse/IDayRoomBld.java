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

public interface IDayRoomBld extends ICoreBase
{
    public DayRoomBldInfo getDayRoomBldInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DayRoomBldInfo getDayRoomBldInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DayRoomBldInfo getDayRoomBldInfo(String oql) throws BOSException, EASBizException;
    public DayRoomBldCollection getDayRoomBldCollection() throws BOSException;
    public DayRoomBldCollection getDayRoomBldCollection(EntityViewInfo view) throws BOSException;
    public DayRoomBldCollection getDayRoomBldCollection(String oql) throws BOSException;
}