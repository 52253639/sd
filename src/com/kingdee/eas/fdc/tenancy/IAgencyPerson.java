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
import com.kingdee.eas.framework.IBillEntryBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IAgencyPerson extends IBillEntryBase
{
    public AgencyPersonInfo getAgencyPersonInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AgencyPersonInfo getAgencyPersonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AgencyPersonInfo getAgencyPersonInfo(String oql) throws BOSException, EASBizException;
    public AgencyPersonCollection getAgencyPersonCollection() throws BOSException;
    public AgencyPersonCollection getAgencyPersonCollection(EntityViewInfo view) throws BOSException;
    public AgencyPersonCollection getAgencyPersonCollection(String oql) throws BOSException;
}