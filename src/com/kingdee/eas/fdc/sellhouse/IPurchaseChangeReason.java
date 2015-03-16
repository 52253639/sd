package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IPurchaseChangeReason extends IFDCDataBase
{
    public PurchaseChangeReasonInfo getPurchaseChangeReasonInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PurchaseChangeReasonInfo getPurchaseChangeReasonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PurchaseChangeReasonInfo getPurchaseChangeReasonInfo(String oql) throws BOSException, EASBizException;
    public PurchaseChangeReasonCollection getPurchaseChangeReasonCollection() throws BOSException;
    public PurchaseChangeReasonCollection getPurchaseChangeReasonCollection(EntityViewInfo view) throws BOSException;
    public PurchaseChangeReasonCollection getPurchaseChangeReasonCollection(String oql) throws BOSException;
}