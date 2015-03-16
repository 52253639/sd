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
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IPurchaseChange extends IFDCBill
{
    public PurchaseChangeInfo getPurchaseChangeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PurchaseChangeInfo getPurchaseChangeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PurchaseChangeInfo getPurchaseChangeInfo(String oql) throws BOSException, EASBizException;
    public PurchaseChangeCollection getPurchaseChangeCollection() throws BOSException;
    public PurchaseChangeCollection getPurchaseChangeCollection(EntityViewInfo view) throws BOSException;
    public PurchaseChangeCollection getPurchaseChangeCollection(String oql) throws BOSException;
    public void setAudited(BOSUuid id) throws BOSException, EASBizException;
    public void setAuditing(BOSUuid id) throws BOSException, EASBizException;
    public void setState(BOSUuid id, FDCBillStateEnum state) throws BOSException, EASBizException;
    public void purchaseChangeToPurchase(BOSUuid id) throws BOSException, EASBizException;
}