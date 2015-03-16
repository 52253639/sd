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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IRevFDCCustomerEntry extends ICoreBillEntryBase
{
    public RevFDCCustomerEntryInfo getRevFDCCustomerEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RevFDCCustomerEntryInfo getRevFDCCustomerEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RevFDCCustomerEntryInfo getRevFDCCustomerEntryInfo(String oql) throws BOSException, EASBizException;
    public RevFDCCustomerEntryCollection getRevFDCCustomerEntryCollection() throws BOSException;
    public RevFDCCustomerEntryCollection getRevFDCCustomerEntryCollection(EntityViewInfo view) throws BOSException;
    public RevFDCCustomerEntryCollection getRevFDCCustomerEntryCollection(String oql) throws BOSException;
}