package com.kingdee.eas.fdc.sellhouse;

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
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public interface IReceiptInvoiceFacade extends IBizCtrl
{
    public IObjectCollection getRecord(BOSUuid id) throws BOSException;
    public void updateRecord(int billType, String pk, RecordTypeEnum recordType, String content, String description) throws BOSException;
    public void updateReceipt(String pk, String fdcPK, String receiptID, String receiptNum, String oldReceiptNum, String description) throws BOSException;
    public void retackReceipt(String pk, String fdcPK, String receiptNum, String description, Map paraMap) throws BOSException;
    public void updateInvoice(String pk, String oldInvoice, String chequePK, String newInvoice, String description) throws BOSException;
    public List getChequebyChgID(String chgID) throws BOSException;
    public void updateChequeForChg(List param) throws BOSException;
    public List getChequeForRoom(String sourceID, String sourceType) throws BOSException;
    public void retakeChequeForRoom(List param) throws BOSException;
    public void retackInvoice(String pk, String number, String description) throws BOSException;
    public void clearInvoice(String pk, boolean isClearAll) throws BOSException;
    public FDCCustomerInfo getCustomerByRoom(String roomID) throws BOSException;
    public void retackReceiptBatch(Map paramMap, String fdcPK, String receiptNum, String description) throws BOSException;
}