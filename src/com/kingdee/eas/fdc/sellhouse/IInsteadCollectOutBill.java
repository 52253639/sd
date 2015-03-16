package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.propertymgmt.IPPMProjectBill;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import java.util.Set;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IInsteadCollectOutBill extends IPPMProjectBill
{
    public InsteadCollectOutBillCollection getInsteadCollectOutBillCollection() throws BOSException;
    public InsteadCollectOutBillCollection getInsteadCollectOutBillCollection(EntityViewInfo view) throws BOSException;
    public InsteadCollectOutBillCollection getInsteadCollectOutBillCollection(String oql) throws BOSException;
    public InsteadCollectOutBillInfo getInsteadCollectOutBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public InsteadCollectOutBillInfo getInsteadCollectOutBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public InsteadCollectOutBillInfo getInsteadCollectOutBillInfo(String oql) throws BOSException, EASBizException;
    public void generateNewData(PersonInfo personInfo, Date bizDate, Set rows) throws BOSException, SellHouseException;
}