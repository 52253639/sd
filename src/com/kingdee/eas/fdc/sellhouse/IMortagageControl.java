package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IMortagageControl extends IFDCBill
{
    public MortagageControlInfo getMortagageControlInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MortagageControlInfo getMortagageControlInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MortagageControlInfo getMortagageControlInfo(String oql) throws BOSException, EASBizException;
    public MortagageControlCollection getMortagageControlCollection() throws BOSException;
    public MortagageControlCollection getMortagageControlCollection(EntityViewInfo view) throws BOSException;
    public MortagageControlCollection getMortagageControlCollection(String oql) throws BOSException;
    public boolean antiMortagage(MortagageControlInfo model) throws BOSException, EASBizException;
    public boolean audit(MortagageControlInfo model) throws BOSException, EASBizException;
    public boolean unAudit(MortagageControlInfo model) throws BOSException, EASBizException;
    public void setAuditingStatus(MortagageControlInfo model) throws BOSException, EASBizException;
}