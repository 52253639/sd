package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;

public interface ICustomerBusinessScopeEntry extends IBizCtrl
{
    public boolean exists(IObjectPK pk) throws BOSException;
    public boolean exists(FilterInfo filter) throws BOSException;
    public boolean exists(String oql) throws BOSException;
    public CustomerBusinessScopeEntryInfo getValue(IObjectPK pk) throws BOSException;
    public CustomerBusinessScopeEntryInfo getValue(IObjectPK pk, SelectorItemCollection selector) throws BOSException;
    public CustomerBusinessScopeEntryInfo getValue(String oql) throws BOSException;
    public IObjectPK addnew(CustomerBusinessScopeEntryInfo model) throws BOSException;
    public void addnew(IObjectPK pk, CustomerBusinessScopeEntryInfo model) throws BOSException;
    public void update(IObjectPK pk, CustomerBusinessScopeEntryInfo model) throws BOSException;
    public void updatePartial(CustomerBusinessScopeEntryInfo model, SelectorItemCollection selector) throws BOSException;
    public void updateBigObject(IObjectPK pk, CustomerBusinessScopeEntryInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException;
    public IObjectPK[] getPKList() throws BOSException;
    public IObjectPK[] getPKList(String oql) throws BOSException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException;
    public CustomerBusinessScopeEntryCollection getCollection() throws BOSException;
    public CustomerBusinessScopeEntryCollection getCollection(EntityViewInfo view) throws BOSException;
    public CustomerBusinessScopeEntryCollection getCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException;
    public IObjectPK[] delete(String oql) throws BOSException;
    public void delete(IObjectPK[] arrayPK) throws BOSException;
}