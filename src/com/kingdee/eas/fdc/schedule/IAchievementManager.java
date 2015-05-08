package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IAchievementManager extends IFDCBill
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public AchievementManagerInfo getAchievementManagerInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AchievementManagerInfo getAchievementManagerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AchievementManagerInfo getAchievementManagerInfo(String oql) throws BOSException, EASBizException;
    public AchievementManagerCollection getAchievementManagerCollection() throws BOSException;
    public AchievementManagerCollection getAchievementManagerCollection(EntityViewInfo view) throws BOSException;
    public AchievementManagerCollection getAchievementManagerCollection(String oql) throws BOSException;
    public void afterschreportwriteBack(IObjectValue model) throws BOSException;
}