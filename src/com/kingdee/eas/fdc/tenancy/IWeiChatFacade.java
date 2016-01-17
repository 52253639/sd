package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;

public interface IWeiChatFacade extends IBizCtrl
{
    public String synBroker(String str) throws BOSException, EASBizException;
    public String synCustomer(String str) throws BOSException, EASBizException;
    public String sysFDCCsutomer(String str) throws BOSException, EASBizException;
    public String sysTrackRecord(String str) throws BOSException, EASBizException;
}