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

public interface IOrgCustomerLinkMan extends IFDCBaseLinkMan
{
    public OrgCustomerLinkManInfo getOrgCustomerLinkManInfo(IObjectPK pk) throws BOSException, EASBizException;
    public OrgCustomerLinkManInfo getOrgCustomerLinkManInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public OrgCustomerLinkManInfo getOrgCustomerLinkManInfo(String oql) throws BOSException, EASBizException;
    public OrgCustomerLinkManCollection getOrgCustomerLinkManCollection() throws BOSException;
    public OrgCustomerLinkManCollection getOrgCustomerLinkManCollection(EntityViewInfo view) throws BOSException;
    public OrgCustomerLinkManCollection getOrgCustomerLinkManCollection(String oql) throws BOSException;
}