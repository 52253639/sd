package com.kingdee.eas.fdc.sellhouse;

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

public interface ICompensateScheme extends IFDCBill
{
    public CompensateSchemeInfo getCompensateSchemeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CompensateSchemeInfo getCompensateSchemeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CompensateSchemeInfo getCompensateSchemeInfo(String oql) throws BOSException, EASBizException;
    public CompensateSchemeCollection getCompensateSchemeCollection() throws BOSException;
    public CompensateSchemeCollection getCompensateSchemeCollection(EntityViewInfo view) throws BOSException;
    public CompensateSchemeCollection getCompensateSchemeCollection(String oql) throws BOSException;
}