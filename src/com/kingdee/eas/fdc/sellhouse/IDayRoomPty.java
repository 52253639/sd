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

public interface IDayRoomPty extends ICoreBase
{
    public DayRoomPtyInfo getDayRoomPtyInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DayRoomPtyInfo getDayRoomPtyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DayRoomPtyInfo getDayRoomPtyInfo(String oql) throws BOSException, EASBizException;
    public DayRoomPtyCollection getDayRoomPtyCollection() throws BOSException;
    public DayRoomPtyCollection getDayRoomPtyCollection(EntityViewInfo view) throws BOSException;
    public DayRoomPtyCollection getDayRoomPtyCollection(String oql) throws BOSException;
}