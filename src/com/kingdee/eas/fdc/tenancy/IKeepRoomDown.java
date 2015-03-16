package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IKeepRoomDown extends IBillBase
{
    public KeepRoomDownInfo getKeepRoomDownInfo(IObjectPK pk) throws BOSException, EASBizException;
    public KeepRoomDownInfo getKeepRoomDownInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public KeepRoomDownInfo getKeepRoomDownInfo(String oql) throws BOSException, EASBizException;
    public KeepRoomDownCollection getKeepRoomDownCollection() throws BOSException;
    public KeepRoomDownCollection getKeepRoomDownCollection(EntityViewInfo view) throws BOSException;
    public KeepRoomDownCollection getKeepRoomDownCollection(String oql) throws BOSException;
    public void cancelKeepRoom(String roomId) throws BOSException;
}