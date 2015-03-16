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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public interface IBasePriceControl extends IFDCBill
{
    public BasePriceControlInfo getBasePriceControlInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BasePriceControlInfo getBasePriceControlInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BasePriceControlInfo getBasePriceControlInfo(String oql) throws BOSException, EASBizException;
    public BasePriceControlCollection getBasePriceControlCollection() throws BOSException;
    public BasePriceControlCollection getBasePriceControlCollection(EntityViewInfo view) throws BOSException;
    public BasePriceControlCollection getBasePriceControlCollection(String oql) throws BOSException;
    public void updateRoom(List roomList) throws BOSException, EASBizException;
    public List getRoomInfoList(String filterString) throws BOSException, EASBizException;
    public void auditBasePrice(BOSUuid billId) throws BOSException, EASBizException;
    public void unAuditBasePrice(BOSUuid billId) throws BOSException, EASBizException;
    public void setSubmit(BOSUuid billId) throws BOSException, EASBizException;
    public void setAuditing(BOSUuid billId) throws BOSException, EASBizException;
}