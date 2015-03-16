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
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IRoomSignContract extends IFDCBill
{
    public RoomSignContractInfo getRoomSignContractInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomSignContractInfo getRoomSignContractInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomSignContractInfo getRoomSignContractInfo(String oql) throws BOSException, EASBizException;
    public RoomSignContractCollection getRoomSignContractCollection(String oql) throws BOSException;
    public RoomSignContractCollection getRoomSignContractCollection(EntityViewInfo view) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public void onRecord(IObjectPK[] pks, Date RecordDate, String contractNumber) throws BOSException, EASBizException;
    public void stamp(IObjectPK[] pks, Date stampDate) throws BOSException, EASBizException;
    public void pullDown(IObjectPK[] pks, Date pullDownDate) throws BOSException, EASBizException;
    public void unOnRecord(IObjectPK[] pks) throws BOSException, EASBizException;
    public void unStamp(IObjectPK[] pks) throws BOSException, EASBizException;
    public void unPullDown(IObjectPK[] pks) throws BOSException, EASBizException;
}