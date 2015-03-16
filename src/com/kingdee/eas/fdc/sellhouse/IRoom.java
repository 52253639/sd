package com.kingdee.eas.fdc.sellhouse;

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
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface IRoom extends IFDCDataBase
{
    public RoomInfo getRoomInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomInfo getRoomInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomInfo getRoomInfo(String oql) throws BOSException, EASBizException;
    public RoomCollection getRoomCollection() throws BOSException;
    public RoomCollection getRoomCollection(String oql) throws BOSException;
    public RoomCollection getRoomCollection(EntityViewInfo view) throws BOSException;
    public void doAreaAudit(List idList) throws BOSException;
    public void doActualAreaAudit(List idList) throws BOSException;
    public void doBasePriceAudit(List idList) throws BOSException;
    public void reclaimRoom(String id) throws BOSException, EASBizException;
    public Map getRoomInfoCollectionMap(RoomInfo roomInfo, String collInfoNames) throws BOSException, EASBizException;
    public void roomIpdateBatch(List idList, Map map) throws BOSException;
    public void addRoomAreaChange(List idList, RoomAreaChangeTypeEnum type) throws BOSException, EASBizException;
    public void updateAreaInfo(List roomList) throws BOSException, EASBizException;
    public void planAudit(List roomIdList) throws BOSException, EASBizException;
    public void planUnAudit(List roomIdList) throws BOSException, EASBizException;
    public void preAudit(List roomIdList) throws BOSException, EASBizException;
    public void preUnAudit(List roomIdList) throws BOSException, EASBizException;
    public void actAudit(List roomIdList) throws BOSException, EASBizException;
    public void actUnAudit(List roomIdList) throws BOSException, EASBizException;
}