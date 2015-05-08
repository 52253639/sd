package com.kingdee.eas.fdc.schedule;

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

public interface IRESchTemplateCatagory extends IFDCTreeBaseData
{
    public RESchTemplateCatagoryInfo getRESchTemplateCatagoryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RESchTemplateCatagoryInfo getRESchTemplateCatagoryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RESchTemplateCatagoryInfo getRESchTemplateCatagoryInfo(String oql) throws BOSException, EASBizException;
    public RESchTemplateCatagoryCollection getRESchTemplateCatagoryCollection() throws BOSException;
    public RESchTemplateCatagoryCollection getRESchTemplateCatagoryCollection(EntityViewInfo view) throws BOSException;
    public RESchTemplateCatagoryCollection getRESchTemplateCatagoryCollection(String oql) throws BOSException;
}