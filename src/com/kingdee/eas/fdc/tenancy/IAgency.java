package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IAgency extends IDataBase
{
    public AgencyInfo getAgencyInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AgencyInfo getAgencyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AgencyInfo getAgencyInfo(String oql) throws BOSException, EASBizException;
    public AgencyCollection getAgencyCollection() throws BOSException;
    public AgencyCollection getAgencyCollection(EntityViewInfo view) throws BOSException;
    public AgencyCollection getAgencyCollection(String oql) throws BOSException;
}