package com.kingdee.eas.fdc.basecrm;

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
import java.util.Set;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface IFDCOrgCustomer extends IFDCBaseCustomer
{
    public FDCOrgCustomerInfo getFDCOrgCustomerInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCOrgCustomerInfo getFDCOrgCustomerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCOrgCustomerInfo getFDCOrgCustomerInfo(String oql) throws BOSException, EASBizException;
    public FDCOrgCustomerCollection getFDCOrgCustomerCollection() throws BOSException;
    public FDCOrgCustomerCollection getFDCOrgCustomerCollection(EntityViewInfo view) throws BOSException;
    public FDCOrgCustomerCollection getFDCOrgCustomerCollection(String oql) throws BOSException;
    public void changeCusName(String cusId, String newName) throws BOSException, EASBizException;
    public void updateCustomerInfo(List ids) throws BOSException, EASBizException;
    public void shareCustomer(List cusIds, String shareOrgId) throws BOSException, EASBizException;
    public void mergeCustomer(List srcIds, String toId) throws BOSException, EASBizException;
    public void importCustomer(IObjectCollection res) throws BOSException, EASBizException;
    public void submitEnterpriceCustomer(FDCOrgCustomerInfo model, String name, String phone) throws BOSException, EASBizException;
    public void updateEnterpriceCustomer(Set set, String str1, String str2, boolean isLinkMan) throws BOSException, EASBizException;
}