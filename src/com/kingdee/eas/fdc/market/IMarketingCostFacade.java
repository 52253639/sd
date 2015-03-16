package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.base.param.ParamException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.IVirtualRptBaseFacade;
import com.kingdee.eas.framework.report.util.RptParams;

public interface IMarketingCostFacade extends IVirtualRptBaseFacade
{
    public RptParams getTableList(RptParams params, String ids) throws BOSException, ParamException;
}