package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.invite.InquiryResultInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.InquiryResultCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface InquiryResultController extends FDCBillController
{
    public InquiryResultCollection getInquiryResultCollection(Context ctx) throws BOSException, RemoteException;
    public InquiryResultCollection getInquiryResultCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public InquiryResultCollection getInquiryResultCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public InquiryResultInfo getInquiryResultInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public InquiryResultInfo getInquiryResultInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public InquiryResultInfo getInquiryResultInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public boolean checkCanSubmit(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public Map fetchFilterInitData(Context ctx, Map paramMap) throws BOSException, EASBizException, RemoteException;
    public Map fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
}