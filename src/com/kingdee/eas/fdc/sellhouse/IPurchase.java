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

public interface IPurchase extends IFDCBill
{
    public PurchaseInfo getPurchaseInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PurchaseInfo getPurchaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PurchaseInfo getPurchaseInfo(String oql) throws BOSException, EASBizException;
    public PurchaseCollection getPurchaseCollection() throws BOSException;
    public PurchaseCollection getPurchaseCollection(EntityViewInfo view) throws BOSException;
    public PurchaseCollection getPurchaseCollection(String oql) throws BOSException;
    public void checkPrePurchase(IObjectPK[] pks) throws BOSException, EASBizException;
    public void uncheckPrePurchase(IObjectPK[] pks) throws BOSException, EASBizException;
    public void submitPrePurchase(PurchaseInfo model) throws BOSException;
    public void auditPrePurchase(BOSUuid billId) throws BOSException;
    public void receiveBill(BOSUuid billId) throws BOSException, EASBizException;
    public void purDistillUpdate() throws BOSException;
    public void purAddMarket() throws BOSException, EASBizException;
    public void immediacySave(PurchaseInfo model) throws BOSException, EASBizException;
}