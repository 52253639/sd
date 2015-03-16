package com.kingdee.eas.fdc.basecrm;

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
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASAppException;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.bos.util.BOSUuid;

public interface IFDCReceivingBill extends IFDCBill
{
    public FDCReceivingBillInfo getFDCReceivingBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCReceivingBillInfo getFDCReceivingBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCReceivingBillInfo getFDCReceivingBillInfo(String oql) throws BOSException, EASBizException;
    public FDCReceivingBillCollection getFDCReceivingBillCollection() throws BOSException;
    public FDCReceivingBillCollection getFDCReceivingBillCollection(EntityViewInfo view) throws BOSException;
    public FDCReceivingBillCollection getFDCReceivingBillCollection(String oql) throws BOSException;
    public String submitRev(IObjectValue rev, String handleClazzName) throws BOSException, EASBizException;
    public void receive(ArrayList recidList) throws BOSException, EASBizException;
    public void delete(BOSUuid fdcReceivingID, String handleClazzName) throws BOSException, EASBizException;
    public void canceReceive(ArrayList recidList) throws BOSException, EASBizException;
    public Map adjust(BOSUuid billId) throws BOSException, EASBizException;
    public void createCashBill(ArrayList idList, boolean isCreate) throws BOSException, EASAppException;
    public void adjustReceiveBill(BOSUuid billId, Map map) throws BOSException, EASBizException;
    public void receive(BOSUuid BOSUuid) throws BOSException, EASBizException;
}