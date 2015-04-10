package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.tenancy.InvoiceBillInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.tenancy.InvoiceBillEntryInfo;
import com.kingdee.eas.fdc.tenancy.InvoiceBillFactory;
import com.kingdee.eas.fdc.tenancy.SincerPaylistEntrysFactory;
import com.kingdee.eas.fdc.tenancy.TENRevHelper;
import com.kingdee.eas.fdc.tenancy.TenBillBaseCollection;
import com.kingdee.eas.fdc.tenancy.InvoiceBillCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fi.arap.InvoiceEntryInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class InvoiceBillControllerBean extends AbstractInvoiceBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.InvoiceBillControllerBean");

	protected boolean isUseName() {
		return false;
	}
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		super._audit(ctx, billId);
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("entry.*");
		InvoiceBillInfo info=InvoiceBillFactory.getLocalInstance(ctx).getInvoiceBillInfo(new ObjectUuidPK(billId),sic);
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		for(int i=0;i<info.getEntry().size();i++){
			InvoiceBillEntryInfo entry=info.getEntry().get(i);
			String table="";
			if(RevListTypeEnum.tenRoomRev.equals(entry.getRevListType())){
				table=" T_TEN_TenancyRoomPayListEntry ";
			}else if(RevListTypeEnum.tenOtherRev.equals(entry.getRevListType())){
				table=" T_TEN_TenBillOtherPay ";
			}else if(RevListTypeEnum.sincerobligate.equals(entry.getRevListType())){
				table=" .T_TEN_SincerPaylistEntrys ";
			}
			StringBuffer sql = new StringBuffer();
			sql.append("update "+table+" revList set finvoiceAmount = (select isnull(sum(entry.famount),0) from T_TEN_InvoiceBillEntry entry left join T_TEN_InvoiceBill bill on bill.fid=entry.fheadId where bill.fstate='4AUDITTED' and entry.frevListId=revList.fid) where revList.fid='"+entry.getRevListId()+"'");
			fdcSB.addBatch(sql.toString());
		}
		fdcSB.executeBatch();
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		super._unAudit(ctx, billId);
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("entry.*");
		InvoiceBillInfo info=InvoiceBillFactory.getLocalInstance(ctx).getInvoiceBillInfo(new ObjectUuidPK(billId),sic);
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		for(int i=0;i<info.getEntry().size();i++){
			InvoiceBillEntryInfo entry=info.getEntry().get(i);
			String table="";
			if(RevListTypeEnum.tenRoomRev.equals(entry.getRevListType())){
				table=" T_TEN_TenancyRoomPayListEntry ";
			}else if(RevListTypeEnum.tenOtherRev.equals(entry.getRevListType())){
				table=" T_TEN_TenBillOtherPay ";
			}else if(RevListTypeEnum.sincerobligate.equals(entry.getRevListType())){
				table=" .T_TEN_SincerPaylistEntrys ";
			}
			StringBuffer sql = new StringBuffer();
			sql.append("update "+table+" revList set finvoiceAmount = (select isnull(sum(entry.famount),0) from T_TEN_InvoiceBillEntry entry left join T_TEN_InvoiceBill bill on bill.fid=entry.fheadId where bill.fstate='4AUDITTED' and entry.frevListId=revList.fid) where revList.fid='"+entry.getRevListId()+"'");
			fdcSB.addBatch(sql.toString());
		}
		fdcSB.executeBatch();
	}
}