package com.kingdee.eas.fdc.invite.supplier;

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

public interface ISupplierLinkPerson extends ICoreBillEntryBase
{
    public SupplierLinkPersonInfo getSupplierLinkPersonInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SupplierLinkPersonInfo getSupplierLinkPersonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SupplierLinkPersonInfo getSupplierLinkPersonInfo(String oql) throws BOSException, EASBizException;
    public SupplierLinkPersonCollection getSupplierLinkPersonCollection() throws BOSException;
    public SupplierLinkPersonCollection getSupplierLinkPersonCollection(EntityViewInfo view) throws BOSException;
    public SupplierLinkPersonCollection getSupplierLinkPersonCollection(String oql) throws BOSException;
}