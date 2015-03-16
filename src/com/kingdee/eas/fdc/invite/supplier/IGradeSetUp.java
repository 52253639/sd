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

public interface IGradeSetUp extends IFDCDataBase
{
    public GradeSetUpInfo getGradeSetUpInfo(IObjectPK pk) throws BOSException, EASBizException;
    public GradeSetUpInfo getGradeSetUpInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public GradeSetUpInfo getGradeSetUpInfo(String oql) throws BOSException, EASBizException;
    public GradeSetUpCollection getGradeSetUpCollection() throws BOSException;
    public GradeSetUpCollection getGradeSetUpCollection(EntityViewInfo view) throws BOSException;
    public GradeSetUpCollection getGradeSetUpCollection(String oql) throws BOSException;
    public void isNdelete(String gradeName) throws BOSException, EASBizException;
}