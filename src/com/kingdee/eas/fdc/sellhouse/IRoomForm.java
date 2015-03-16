package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.IFDCDataBase;
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

public interface IRoomForm extends IFDCDataBase
{
    public RoomFormInfo getRoomFormInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomFormInfo getRoomFormInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomFormInfo getRoomFormInfo(String oql) throws BOSException, EASBizException;
    public RoomFormCollection getRoomFormCollection() throws BOSException;
    public RoomFormCollection getRoomFormCollection(EntityViewInfo view) throws BOSException;
    public RoomFormCollection getRoomFormCollection(String oql) throws BOSException;
}