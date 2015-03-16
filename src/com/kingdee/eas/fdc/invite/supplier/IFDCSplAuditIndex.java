package com.kingdee.eas.fdc.invite.supplier;

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

public interface IFDCSplAuditIndex extends IFDCDataBase
{
    public FDCSplAuditIndexInfo getFDCSplAuditIndexInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCSplAuditIndexInfo getFDCSplAuditIndexInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCSplAuditIndexInfo getFDCSplAuditIndexInfo(String oql) throws BOSException, EASBizException;
    public FDCSplAuditIndexCollection getFDCSplAuditIndexCollection() throws BOSException;
    public FDCSplAuditIndexCollection getFDCSplAuditIndexCollection(EntityViewInfo view) throws BOSException;
    public FDCSplAuditIndexCollection getFDCSplAuditIndexCollection(String oql) throws BOSException;
}