package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import java.util.HashMap;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basecrm.IFDCBaseCustomer;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface ISHECustomer extends IFDCBaseCustomer
{
    public SHECustomerInfo getSHECustomerInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SHECustomerInfo getSHECustomerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SHECustomerInfo getSHECustomerInfo(String oql) throws BOSException, EASBizException;
    public SHECustomerCollection getSHECustomerCollection() throws BOSException;
    public SHECustomerCollection getSHECustomerCollection(EntityViewInfo view) throws BOSException;
    public SHECustomerCollection getSHECustomerCollection(String oql) throws BOSException;
    public void updateData(List ids) throws BOSException, EASBizException;
    public void mergeCustomer(List srcIds, String toId) throws BOSException, EASBizException;
    public void changeName(SHECustomerInfo model, Map map) throws BOSException, EASBizException;
    public void shareCustomer(SHECustomerCollection objectColl, Map map) throws BOSException, EASBizException;
    public void deliverCustomer(SHECustomerInfo model, Map map) throws BOSException, EASBizException;
    public void importCustomer(SHECustomerCollection res, SellProjectInfo sellProject) throws BOSException, EASBizException;
    public void submitEnterpriceCustomer(SHECustomerInfo model, String name, String phone) throws BOSException, EASBizException;
    public IObjectPK submitAll(HashMap hashMap, IObjectValue editData) throws BOSException, EASBizException;
}