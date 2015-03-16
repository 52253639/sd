package com.kingdee.eas.fdc.contract.programming;

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

public interface IProgrammingTemplate extends IFDCDataBase
{
    public ProgrammingTemplateInfo getProgrammingTemplateInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProgrammingTemplateInfo getProgrammingTemplateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProgrammingTemplateInfo getProgrammingTemplateInfo(String oql) throws BOSException, EASBizException;
    public ProgrammingTemplateCollection getProgrammingTemplateCollection() throws BOSException;
    public ProgrammingTemplateCollection getProgrammingTemplateCollection(EntityViewInfo view) throws BOSException;
    public ProgrammingTemplateCollection getProgrammingTemplateCollection(String oql) throws BOSException;
    public void copy() throws BOSException;
}