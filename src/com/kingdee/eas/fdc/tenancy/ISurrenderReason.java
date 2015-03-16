package com.kingdee.eas.fdc.tenancy;

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

public interface ISurrenderReason extends IFDCDataBase
{
    public SurrenderReasonInfo getSurrenderReasonInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SurrenderReasonInfo getSurrenderReasonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SurrenderReasonInfo getSurrenderReasonInfo(String oql) throws BOSException, EASBizException;
    public SurrenderReasonCollection getSurrenderReasonCollection() throws BOSException;
    public SurrenderReasonCollection getSurrenderReasonCollection(EntityViewInfo view) throws BOSException;
    public SurrenderReasonCollection getSurrenderReasonCollection(String oql) throws BOSException;
}