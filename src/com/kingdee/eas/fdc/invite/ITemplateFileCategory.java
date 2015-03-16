package com.kingdee.eas.fdc.invite;

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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ITemplateFileCategory extends ITreeBase
{
    public TemplateFileCategoryInfo getTemplateFileCategoryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TemplateFileCategoryInfo getTemplateFileCategoryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TemplateFileCategoryInfo getTemplateFileCategoryInfo(String oql) throws BOSException, EASBizException;
    public TemplateFileCategoryCollection getTemplateFileCategoryCollection() throws BOSException;
    public TemplateFileCategoryCollection getTemplateFileCategoryCollection(EntityViewInfo view) throws BOSException;
    public TemplateFileCategoryCollection getTemplateFileCategoryCollection(String oql) throws BOSException;
}