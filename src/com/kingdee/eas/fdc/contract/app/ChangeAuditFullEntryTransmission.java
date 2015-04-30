/**
 * 
 */
package com.kingdee.eas.fdc.contract.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.contract.ChangeAuditBillCollection;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		
 *		
 * @author		朱俊
 * @version		EAS7.0		
 * @createDate	2011-6-9	 
 * @see						
 */
public class ChangeAuditFullEntryTransmission extends AbstractDataTransmission {

	private static String resource = "com.kingdee.eas.fdc.contract.ContractTransmissionResource";

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = ChangeAuditEntryFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		
		ChangeAuditEntryInfo info = new ChangeAuditEntryInfo();
		
		//变更单编码
		String fNumber=(String) ((DataToken) hsData.get("FNumber")).data;
		//序列号
		String fSeq=(String) ((DataToken) hsData.get("FSeq")).data;
		//变更内容
		String fChangeContent=(String) ((DataToken) hsData.get("FChangeContent")).data;
		//是否返工
		String fIsBack=(String) ((DataToken) hsData.get("FIsBack")).data;
		
		//单头ＩＤ
		if (StringUtils.isEmpty(fNumber)) {
			throw new TaskExternalException(getResource(ctx, "Import_ChangeAuditNumberIsNull"));
		}
		if (fNumber.length() > 160) {
			throw new TaskExternalException(getResource(ctx, "Import_fNumberIsTooLong"));
		}
		
	    EntityViewInfo view = new EntityViewInfo();
	    FilterInfo filter = new FilterInfo();
	    filter.getFilterItems().add(new FilterItemInfo("id","select fid from t_con_changeauditbill where fnumber='"+fNumber+"'",CompareType.INNER));
	    view.setFilter(filter);
	    try {
			ChangeAuditBillCollection coll = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillCollection(view);
			if(coll.size()>0){
				ChangeAuditBillInfo changeAuditBill = coll.get(0);
				info.setParent(changeAuditBill);
			}
			
		} catch (BOSException e) {
			throw new RuntimeException(e.getMessage(),e.getCause());
		}
		
		//序列号
		if (StringUtils.isEmpty(fSeq)) {
			throw new TaskExternalException(getResource(ctx, "Import_SeqIsNull"));
		}
		if (fSeq.length() > 4) {
			throw new TaskExternalException(getResource(ctx, "Import_fSeqIsTooLong"));
		}
		info.setSeq(Integer.parseInt(fSeq));
		
		//变更单编码
		String a [] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		for(int i=1; i<27;i++) {
			if(Integer.parseInt(fSeq) == i) {
				fNumber = a[i-1];
			}
		}
		info.setNumber(fNumber);
		
		//变更内容
		if (StringUtils.isEmpty(fChangeContent)) {
			throw new TaskExternalException(getResource(ctx, "Import_ChangeContentIsNull"));
		}
		if (fChangeContent.length() > 160) {
			throw new TaskExternalException(getResource(ctx, "Import_ChangeContentIsTooLong"));
		}
		info.setChangeContent(fChangeContent);
		
		//是否返工
		if(fIsBack.trim().equals(getResource(ctx, "Yes"))){
			info.setIsBack(true);
		}else {
			info.setIsBack(false);
		}
		
		return info;
	} 
	/**
	 * 得到资源文件
	 * @author 朱俊
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
	
	
}
