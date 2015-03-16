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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillBase;

public interface ILoanData extends ICoreBillBase
{
    public LoanDataInfo getLoanDataInfo(IObjectPK pk) throws BOSException, EASBizException;
    public LoanDataInfo getLoanDataInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public LoanDataInfo getLoanDataInfo(String oql) throws BOSException, EASBizException;
    public LoanDataCollection getLoanDataCollection() throws BOSException;
    public LoanDataCollection getLoanDataCollection(EntityViewInfo view) throws BOSException;
    public LoanDataCollection getLoanDataCollection(String oql) throws BOSException;
    public void enable(IObjectPK pk) throws BOSException, EASBizException;
    public void disEnable(IObjectPK pk) throws BOSException, EASBizException;
}