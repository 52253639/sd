package com.kingdee.eas.fdc.sellhouse;

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

public interface IFamillyEarning extends IFDCDataBase
{
    public FamillyEarningInfo getFamillyEarningInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FamillyEarningInfo getFamillyEarningInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FamillyEarningInfo getFamillyEarningInfo(String oql) throws BOSException, EASBizException;
    public FamillyEarningCollection getFamillyEarningCollection(String oql) throws BOSException;
    public FamillyEarningCollection getFamillyEarningCollection(EntityViewInfo view) throws BOSException;
    public FamillyEarningCollection getFamillyEarningCollection() throws BOSException;
}