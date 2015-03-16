package com.kingdee.eas.fdc.invite.markesupplier;

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
import com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditBaseBill;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IMarketSupplierEvaluation extends IFDCSplAuditBaseBill
{
    public MarketSupplierEvaluationInfo getMarketSupplierEvaluationInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSupplierEvaluationInfo getMarketSupplierEvaluationInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSupplierEvaluationInfo getMarketSupplierEvaluationInfo(String oql) throws BOSException, EASBizException;
    public MarketSupplierEvaluationCollection getMarketSupplierEvaluationCollection(String oql) throws BOSException;
    public MarketSupplierEvaluationCollection getMarketSupplierEvaluationCollection(EntityViewInfo view) throws BOSException;
    public MarketSupplierEvaluationCollection getMarketSupplierEvaluationCollection() throws BOSException;
}