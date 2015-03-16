package com.kingdee.eas.fdc.sellhouse.app;

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
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.eas.fdc.sellhouse.CompensateRoomListCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateCollection;
import java.util.List;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RoomAreaCompensateController extends FDCBillController
{
    public RoomAreaCompensateInfo getRoomAreaCompensateInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RoomAreaCompensateInfo getRoomAreaCompensateInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RoomAreaCompensateInfo getRoomAreaCompensateInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public RoomAreaCompensateCollection getRoomAreaCompensateCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public RoomAreaCompensateCollection getRoomAreaCompensateCollection(Context ctx) throws BOSException, RemoteException;
    public RoomAreaCompensateCollection getRoomAreaCompensateCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void batchSave(Context ctx, List idList, Map valueMap) throws BOSException, EASBizException, RemoteException;
    public Map calcAmount(Context ctx, List idList, String schemeId) throws BOSException, EASBizException, RemoteException;
    public void submitToWorkFlow(Context ctx, String buildingId) throws BOSException, EASBizException, RemoteException;
    public IRowSet getRoomInfoList(Context ctx, String filterStr) throws BOSException, EASBizException, RemoteException;
    public IRowSet getCompenstateRoomInfo(Context ctx, String roomId) throws BOSException, EASBizException, RemoteException;
    public void setNullify(Context ctx, String idList) throws BOSException, EASBizException, RemoteException;
    public Map calcAmountForSHE(Context ctx, List roomList, String schemeId) throws BOSException, EASBizException, RemoteException;
    public void auditAndCalcSellAmount(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public void unAuditAndCalcSellAmount(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public void setAuditing(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void setSubmit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void deleteCompensateInfo(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void createBillForSign(Context ctx, CompensateRoomListCollection compColl) throws BOSException, EASBizException, RemoteException;
    public void deleteBillFromSign(Context ctx, String roomId, SignManageCollection comColl) throws BOSException, EASBizException, RemoteException;
    public void createRoomCompensateForView(Context ctx, List roomIdList, String compId) throws BOSException, EASBizException, RemoteException;
    public void deleteRoomCompensateForView(Context ctx, List roomIdList, String comId) throws BOSException, EASBizException, RemoteException;
}