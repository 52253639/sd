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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.framework.IDataBase;

public interface IFDCCustomer extends IDataBase
{
    public FDCCustomerInfo getFDCCustomerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCCustomerInfo getFDCCustomerInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCCustomerInfo getFDCCustomerInfo(String oql) throws BOSException, EASBizException;
    public FDCCustomerCollection getFDCCustomerCollection() throws BOSException;
    public FDCCustomerCollection getFDCCustomerCollection(EntityViewInfo view) throws BOSException;
    public FDCCustomerCollection getFDCCustomerCollection(String oql) throws BOSException;
    public void addToSysCustomer(String id) throws BOSException, EASBizException;
    public void blankOut(List idList) throws BOSException;
    public void pickUp(List idList) throws BOSException;
    public void modifyName(FDCCustomerInfo fdccustomer) throws BOSException;
    public void signImportantTrack(List idList) throws BOSException;
    public void cancelImportantTrack(List idList) throws BOSException;
    public void switchTo(List idList, String salesmanId) throws BOSException;
    public void addToSysCustomer(String id, List list) throws BOSException, EASBizException;
    public Map verifySave(FDCCustomerInfo model) throws BOSException, EASBizException;
    public void setStatus(List idList) throws BOSException;
    public Map verifySave(IObjectValue model, boolean isSingle) throws BOSException, EASBizException;
    public void updateTenancyBill(String fdcCustID) throws BOSException, EASBizException;
}