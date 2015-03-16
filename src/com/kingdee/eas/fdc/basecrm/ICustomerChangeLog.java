package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.IFDCDataBase;
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

public interface ICustomerChangeLog extends IFDCDataBase
{
    public CustomerChangeLogInfo getCustomerChangeLogInfo(String oql) throws BOSException, EASBizException;
    public CustomerChangeLogInfo getCustomerChangeLogInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CustomerChangeLogInfo getCustomerChangeLogInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CustomerChangeLogCollection getCustomerChangeLogCollection(String oql) throws BOSException;
    public CustomerChangeLogCollection getCustomerChangeLogCollection(EntityViewInfo view) throws BOSException;
    public CustomerChangeLogCollection getCustomerChangeLogCollection() throws BOSException;
}