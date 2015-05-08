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
import com.kingdee.eas.framework.ICoreBase;

public interface IRESchTemplateTaskBizType extends ICoreBase
{
    public RESchTemplateTaskBizTypeInfo getRESchTemplateTaskBizTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RESchTemplateTaskBizTypeInfo getRESchTemplateTaskBizTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RESchTemplateTaskBizTypeInfo getRESchTemplateTaskBizTypeInfo(String oql) throws BOSException, EASBizException;
    public RESchTemplateTaskBizTypeCollection getRESchTemplateTaskBizTypeCollection() throws BOSException;
    public RESchTemplateTaskBizTypeCollection getRESchTemplateTaskBizTypeCollection(EntityViewInfo view) throws BOSException;
    public RESchTemplateTaskBizTypeCollection getRESchTemplateTaskBizTypeCollection(String oql) throws BOSException;
}