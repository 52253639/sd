package com.kingdee.eas.fdc.tenancy.app;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.SysContextConstant;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.tenancy.RentRemissionEntryCollection;
import com.kingdee.eas.fdc.tenancy.RentRemissionEntryInfo;
import com.kingdee.eas.fdc.tenancy.RentRemissionInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;

public class RentRemissionControllerBean extends AbstractRentRemissionControllerBean
{  
	

	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.RentRemissionControllerBean");    
   
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,	EASBizException {
        super._audit(ctx, billId);
        ObjectUuidPK pk = new ObjectUuidPK(billId);
        if(pk==null){
        	throw new IllegalArgumentException();
        }
        UserInfo currentUser = (UserInfo) ctx.get(SysContextConstant.USERINFO);
        Date currentDate = new Date();        
        boolean exist = exists(ctx, pk);
        if(!exist){
           return;
        }
        RentRemissionInfo rentRemisInfo=getRentRemissionInfo(ctx, pk);
        rentRemisInfo.setAuditor(currentUser);
        rentRemisInfo.setAuditTime(currentDate);
        rentRemisInfo.setState(FDCBillStateEnum.AUDITTED); 
        update(ctx, pk, rentRemisInfo);        
        RentRemissionEntryCollection entry =rentRemisInfo.getEntry();
        for(int i=0;i<entry.size();i++){
        	RentRemissionEntryInfo entryInfo =entry.get(i);
        	BigDecimal remisAmount = FDCHelper.toBigDecimal(entryInfo.getRemisionAmount());  
        	BigDecimal oldAppAmount=FDCHelper.ZERO;
        	BigDecimal leatestAppAmount=FDCHelper.ZERO;          	
        	String roomPayId=entryInfo.getRentRemissionRoom().getId().toString();
        	ObjectUuidPK roomPaypk = new ObjectUuidPK(roomPayId);        	      	
        	TenancyRoomPayListEntryInfo roomPayInfo=TenancyRoomPayListEntryFactory.getLocalInstance(ctx).getTenancyRoomPayListEntryInfo(roomPaypk);        	
        	if(roomPayInfo!=null){
        		oldAppAmount=FDCHelper.toBigDecimal(roomPayInfo.getAppAmount());
        		leatestAppAmount=oldAppAmount.subtract(remisAmount);  
        		roomPayInfo.setAppAmount(leatestAppAmount);
        		BigDecimal remisAmountSum = FDCHelper.toBigDecimal(roomPayInfo.getRemissionAmount());  
        		remisAmountSum=remisAmountSum.add(remisAmount);
        		roomPayInfo.setRemissionAmount(remisAmountSum); 
        		TenancyRoomPayListEntryFactory.getLocalInstance(ctx).update(roomPaypk, roomPayInfo);  
        	}   
         }
     }
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		super._unAudit(ctx, billId);
		//反写此租金减免单关联的实收明细的应收金额，已经减免金额字段 eric_wang 2010.08.30
		 ObjectUuidPK pk = new ObjectUuidPK(billId);
	        if(pk==null){
	        	throw new IllegalArgumentException();
	        }
	        UserInfo currentUser = (UserInfo) ctx.get(SysContextConstant.USERINFO);
	        Date currentDate = new Date();        
	        boolean exist = exists(ctx, pk);
	        if(!exist){
	           return;
	        }
		 RentRemissionInfo rentRemisInfo=getRentRemissionInfo(ctx, pk);
		 rentRemisInfo.setAuditor(currentUser);
	     rentRemisInfo.setAuditTime(currentDate);
	     rentRemisInfo.setState(FDCBillStateEnum.SUBMITTED); 
	     update(ctx, pk, rentRemisInfo);        
	     RentRemissionEntryCollection entry =rentRemisInfo.getEntry();
	        for(int i=0;i<entry.size();i++){
	        	RentRemissionEntryInfo entryInfo =entry.get(i);
	        	//减免金额
	        	BigDecimal remisAmount = FDCHelper.toBigDecimal(entryInfo.getRemisionAmount()); 
	        	//应收
	        	BigDecimal oldAppAmount=FDCHelper.ZERO;
	        	BigDecimal leatestAppAmount=FDCHelper.ZERO;          	
	        	String roomPayId=entryInfo.getRentRemissionRoom().getId().toString();
	        	ObjectUuidPK roomPaypk = new ObjectUuidPK(roomPayId);        	      	
	        	TenancyRoomPayListEntryInfo roomPayInfo=TenancyRoomPayListEntryFactory.getLocalInstance(ctx).getTenancyRoomPayListEntryInfo(roomPaypk);        	
	        	if(roomPayInfo!=null){
	        		oldAppAmount=FDCHelper.toBigDecimal(roomPayInfo.getAppAmount());
	        		leatestAppAmount=oldAppAmount.add(remisAmount);  
	        		roomPayInfo.setAppAmount(leatestAppAmount);
	        		BigDecimal remisAmountSum = FDCHelper.toBigDecimal(roomPayInfo.getRemissionAmount());  
	        		remisAmountSum=remisAmountSum.subtract(remisAmount);
	        		roomPayInfo.setRemissionAmount(remisAmountSum); 
	        		TenancyRoomPayListEntryFactory.getLocalInstance(ctx).update(roomPaypk, roomPayInfo);  
	        	}   
	         }
		
	}
}
