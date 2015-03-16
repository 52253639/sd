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
import com.kingdee.eas.fdc.basedata.IFDCTreeBaseData;

public interface IFDCSplServiceType extends IFDCTreeBaseData
{
    public FDCSplServiceTypeInfo getFDCSplServiceTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCSplServiceTypeInfo getFDCSplServiceTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCSplServiceTypeInfo getFDCSplServiceTypeInfo(String oql) throws BOSException, EASBizException;
    public FDCSplServiceTypeCollection getFDCSplServiceTypeCollection(String oql) throws BOSException;
    public FDCSplServiceTypeCollection getFDCSplServiceTypeCollection() throws BOSException;
    public FDCSplServiceTypeCollection getFDCSplServiceTypeCollection(EntityViewInfo view) throws BOSException;
}