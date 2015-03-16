package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.framework.ICoreBase;

public interface ICostSplit extends ICoreBase
{
    public CostSplitInfo getCostSplitInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CostSplitInfo getCostSplitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CostSplitInfo getCostSplitInfo(String oql) throws BOSException, EASBizException;
    public CostSplitCollection getCostSplitCollection() throws BOSException;
    public CostSplitCollection getCostSplitCollection(EntityViewInfo view) throws BOSException;
    public CostSplitCollection getCostSplitCollection(String oql) throws BOSException;
    public void audit(BOSUuid billId) throws BOSException, EASBizException;
    public void unAudit(BOSUuid billId) throws BOSException, EASBizException;
    public void audit(List idList) throws BOSException, EASBizException;
    public void unAudit(List idList) throws BOSException, EASBizException;
}