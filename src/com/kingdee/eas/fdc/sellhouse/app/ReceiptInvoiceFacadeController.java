package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.fdc.sellhouse.RecordTypeEnum;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ReceiptInvoiceFacadeController extends BizController
{
    public IObjectCollection getRecord(Context ctx, BOSUuid id) throws BOSException, RemoteException;
    public void updateRecord(Context ctx, int billType, String pk, RecordTypeEnum recordType, String content, String description) throws BOSException, RemoteException;
    public void updateReceipt(Context ctx, String pk, String fdcPK, String receiptID, String receiptNum, String oldReceiptNum, String description) throws BOSException, RemoteException;
    public void retackReceipt(Context ctx, String pk, String fdcPK, String receiptNum, String description, Map paraMap) throws BOSException, RemoteException;
    public void updateInvoice(Context ctx, String pk, String oldInvoice, String chequePK, String newInvoice, String description) throws BOSException, RemoteException;
    public List getChequebyChgID(Context ctx, String chgID) throws BOSException, RemoteException;
    public void updateChequeForChg(Context ctx, List param) throws BOSException, RemoteException;
    public List getChequeForRoom(Context ctx, String sourceID, String sourceType) throws BOSException, RemoteException;
    public void retakeChequeForRoom(Context ctx, List param) throws BOSException, RemoteException;
    public void retackInvoice(Context ctx, String pk, String number, String description) throws BOSException, RemoteException;
    public void clearInvoice(Context ctx, String pk, boolean isClearAll) throws BOSException, RemoteException;
    public FDCCustomerInfo getCustomerByRoom(Context ctx, String roomID) throws BOSException, RemoteException;
    public void retackReceiptBatch(Context ctx, Map paramMap, String fdcPK, String receiptNum, String description) throws BOSException, RemoteException;
}