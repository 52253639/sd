package com.kingdee.eas.fdc.aimcost;

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

public interface ITemplateMeasureCost extends IFDCBill
{
    public TemplateMeasureCostInfo getTemplateMeasureCostInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TemplateMeasureCostInfo getTemplateMeasureCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TemplateMeasureCostCollection getTemplateMeasureCostCollection(EntityViewInfo view) throws BOSException;
    public TemplateMeasureCostCollection getTemplateMeasureCostCollection() throws BOSException;
    public TemplateMeasureCostCollection getTemplateMeasureCostCollection(String oql) throws BOSException;
    public void audit(BOSUuid billId) throws BOSException, EASBizException;
    public void audit(List idList) throws BOSException, EASBizException;
    public void unaudit(BOSUuid billId) throws BOSException, EASBizException;
    public void unaudit(List idList) throws BOSException, EASBizException;
}