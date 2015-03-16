package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public interface IRoomAreaCompensate extends IFDCBill
{
    public RoomAreaCompensateInfo getRoomAreaCompensateInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomAreaCompensateInfo getRoomAreaCompensateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomAreaCompensateInfo getRoomAreaCompensateInfo(String oql) throws BOSException, EASBizException;
    public RoomAreaCompensateCollection getRoomAreaCompensateCollection(EntityViewInfo view) throws BOSException;
    public RoomAreaCompensateCollection getRoomAreaCompensateCollection() throws BOSException;
    public RoomAreaCompensateCollection getRoomAreaCompensateCollection(String oql) throws BOSException;
    public void batchSave(List idList, Map valueMap) throws BOSException, EASBizException;
    public Map calcAmount(List idList, String schemeId) throws BOSException, EASBizException;
    public void submitToWorkFlow(String buildingId) throws BOSException, EASBizException;
    public IRowSet getRoomInfoList(String filterStr) throws BOSException, EASBizException;
    public IRowSet getCompenstateRoomInfo(String roomId) throws BOSException, EASBizException;
    public void setNullify(String idList) throws BOSException, EASBizException;
    public Map calcAmountForSHE(List roomList, String schemeId) throws BOSException, EASBizException;
    public void auditAndCalcSellAmount(String id) throws BOSException, EASBizException;
    public void unAuditAndCalcSellAmount(String id) throws BOSException, EASBizException;
    public void setAuditing(BOSUuid billId) throws BOSException, EASBizException;
    public void setSubmit(BOSUuid billId) throws BOSException, EASBizException;
    public void deleteCompensateInfo(BOSUuid billId) throws BOSException, EASBizException;
    public void createBillForSign(CompensateRoomListCollection compColl) throws BOSException, EASBizException;
    public void deleteBillFromSign(String roomId, SignManageCollection comColl) throws BOSException, EASBizException;
    public void createRoomCompensateForView(List roomIdList, String compId) throws BOSException, EASBizException;
    public void deleteRoomCompensateForView(List roomIdList, String comId) throws BOSException, EASBizException;
}