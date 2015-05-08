package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IAchievementProfession extends IFDCDataBase
{
    public AchievementProfessionInfo getAchievementProfessionInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AchievementProfessionInfo getAchievementProfessionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AchievementProfessionInfo getAchievementProfessionInfo(String oql) throws BOSException, EASBizException;
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public AchievementProfessionCollection getAchievementProfessionCollection() throws BOSException;
    public AchievementProfessionCollection getAchievementProfessionCollection(EntityViewInfo view) throws BOSException;
    public AchievementProfessionCollection getAchievementProfessionCollection(String oql) throws BOSException;
}