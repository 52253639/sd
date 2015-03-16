package com.kingdee.eas.fdc.basecrm;

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
import com.kingdee.eas.framework.ICoreBillBase;

public interface ISubstituteTransfOut extends ICoreBillBase
{
    public SubstituteTransfOutCollection getSubstituteTransfOutCollection() throws BOSException;
    public SubstituteTransfOutCollection getSubstituteTransfOutCollection(EntityViewInfo view) throws BOSException;
    public SubstituteTransfOutCollection getSubstituteTransfOutCollection(String oql) throws BOSException;
    public SubstituteTransfOutInfo getSubstituteTransfOutInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SubstituteTransfOutInfo getSubstituteTransfOutInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SubstituteTransfOutInfo getSubstituteTransfOutInfo(String oql) throws BOSException, EASBizException;
    public void audit(BOSUuid billId) throws BOSException, EASBizException;
    public void audit(List idList) throws BOSException, EASBizException;
    public void unAudit(BOSUuid billId) throws BOSException, EASBizException;
    public void unAudit(List idList) throws BOSException, EASBizException;
    public void setAudittingStatus(BOSUuid billId) throws BOSException, EASBizException;
    public void setSubmitStatus(BOSUuid billId) throws BOSException, EASBizException;
}