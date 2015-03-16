package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IProductSettleBill extends IFDCBill
{
    public ProductSettleBillInfo getProductSettleBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProductSettleBillInfo getProductSettleBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProductSettleBillInfo getProductSettleBillInfo(String oql) throws BOSException, EASBizException;
    public ProductSettleBillCollection getProductSettleBillCollection() throws BOSException;
    public ProductSettleBillCollection getProductSettleBillCollection(EntityViewInfo view) throws BOSException;
    public ProductSettleBillCollection getProductSettleBillCollection(String oql) throws BOSException;
}