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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.bos.util.BOSUuid;

public interface IReceiveGather extends IFDCBill
{
    public ReceiveGatherInfo getReceiveGatherInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ReceiveGatherInfo getReceiveGatherInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ReceiveGatherInfo getReceiveGatherInfo(String oql) throws BOSException, EASBizException;
    public ReceiveGatherCollection getReceiveGatherCollection() throws BOSException;
    public ReceiveGatherCollection getReceiveGatherCollection(EntityViewInfo view) throws BOSException;
    public ReceiveGatherCollection getReceiveGatherCollection(String oql) throws BOSException;
    public void createReceivingBill(BOSUuid billID) throws BOSException, EASBizException;
    public void delReceivingToRev(IObjectValue info) throws BOSException, EASBizException;
    public void createVoucherToRev(ArrayList revList) throws BOSException, EASBizException;
    public void delVoucherToRev(ArrayList revList) throws BOSException, EASBizException;
    public void delVoucherToRev(IObjectPK sourceBillPk) throws BOSException, EASBizException;
    public void setAudittingStatus(BOSUuid billId) throws BOSException, EASBizException;
    public void setSubmitStatus(BOSUuid billId) throws BOSException, EASBizException;
}