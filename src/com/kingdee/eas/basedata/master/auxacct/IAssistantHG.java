package com.kingdee.eas.basedata.master.auxacct;

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
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IObjectBase;

public interface IAssistantHG extends IObjectBase
{
    public AssistantHGCollection getAssistantHGCollection(EntityViewInfo view) throws BOSException;
    public AssistantHGCollection getAssistantHGCollection() throws BOSException;
    public AssistantHGCollection getAssistantHGCollection(String oql) throws BOSException;
    public AssistantHGInfo getAssistantHG(AssistantHGInfo assGrp, String bookId, AsstActTypeCollection items) throws BOSException, EASBizException;
    public AssistantHGInfo getAssistantHGInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AssistantHGInfo getAssistantHGInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AssistantHGInfo getAssistantHGInfo(String oql) throws BOSException, EASBizException;
    public IObjectValue getAssistantHG(IObjectPK asstHGPK, String bookId, IObjectPK[] items) throws BOSException, EASBizException;
}