package com.kingdee.eas.fdc.basecrm;

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

public interface IFDCMainCustomer extends IFDCBaseCustomer
{
    public FDCMainCustomerInfo getFDCMainCustomerInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCMainCustomerInfo getFDCMainCustomerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCMainCustomerInfo getFDCMainCustomerInfo(String oql) throws BOSException, EASBizException;
    public FDCMainCustomerCollection getFDCMainCustomerCollection() throws BOSException;
    public FDCMainCustomerCollection getFDCMainCustomerCollection(EntityViewInfo view) throws BOSException;
    public FDCMainCustomerCollection getFDCMainCustomerCollection(String oql) throws BOSException;
    public void addToSysCustomer(FDCMainCustomerInfo mainCus) throws BOSException, EASBizException;
}