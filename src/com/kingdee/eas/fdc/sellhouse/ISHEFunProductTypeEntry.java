package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public interface ISHEFunProductTypeEntry extends IBizCtrl
{
    public SHEFunProductTypeEntryInfo getValue(IObjectPK pk) throws BOSException;
    public SHEFunProductTypeEntryInfo getValue(IObjectPK pk, SelectorItemCollection selector) throws BOSException;
    public SHEFunProductTypeEntryInfo getValue(String oql) throws BOSException;
    public SHEFunProductTypeEntryCollection getCollection() throws BOSException;
    public SHEFunProductTypeEntryCollection getCollection(EntityViewInfo view) throws BOSException;
    public SHEFunProductTypeEntryCollection getCollection(String oql) throws BOSException;
}