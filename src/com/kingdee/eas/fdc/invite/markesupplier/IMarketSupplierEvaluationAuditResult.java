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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;

public interface IMarketSupplierEvaluationAuditResult extends ICoreBase
{
    public MarketSupplierEvaluationAuditResultInfo getMarketSupplierEvaluationAuditResultInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSupplierEvaluationAuditResultInfo getMarketSupplierEvaluationAuditResultInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSupplierEvaluationAuditResultInfo getMarketSupplierEvaluationAuditResultInfo(String oql) throws BOSException, EASBizException;
    public MarketSupplierEvaluationAuditResultCollection getMarketSupplierEvaluationAuditResultCollection(String oql) throws BOSException;
    public MarketSupplierEvaluationAuditResultCollection getMarketSupplierEvaluationAuditResultCollection(EntityViewInfo view) throws BOSException;
    public MarketSupplierEvaluationAuditResultCollection getMarketSupplierEvaluationAuditResultCollection() throws BOSException;
}