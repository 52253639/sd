package com.kingdee.eas.fdc.basedata.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.app.DbUtil;

public class CostAccountDataTrans extends AbstractDataTransmission{
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return CostAccountFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(),e);
		}
	}
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		CostAccountInfo acct=new CostAccountInfo();
		String longNumber=getData(hsData, "FLongNumber");
		String name=getData(hsData, "FName");
		String type=getData(hsData,"FType");
		String desc=getData(hsData,"FDescription");
		
		if(FDCHelper.isEmpty(longNumber)||FDCHelper.isEmpty(name)){
			throw new TaskExternalException("成本科目编码、名称不能为空");
		}
		acct.setDescription(desc);
		
		//find parentid
		longNumber=longNumber.replaceAll("\\.", "!");
		isExist(ctx, longNumber);
		
		int index = longNumber.lastIndexOf("!");
		if(index>0){
			//handle parent node
			if(type!=null&&type.equals("分摊科目")){
				acct.setType(CostAccountTypeEnum.SIX);
			}else if(type!=null&&type.equals("非分摊科目")){
				acct.setType(CostAccountTypeEnum.MAIN);
			}else{
				throw new TaskExternalException("子级科目的成本科目类型必须为:分摊科目、非分摊科目");
			}
			
			String parentLongNumber=longNumber.substring(0,index);
			String number=longNumber.substring(index+1);
			CostAccountInfo parentAcct = getCostAccount(ctx, parentLongNumber);
			acct.setParent(parentAcct);
			acct.setNumber(number);
			acct.setLongNumber(longNumber);
			acct.setName(name);
			acct.setDisplayName(parentAcct.getDisplayName()+"_"+name);
			acct.setLevel(parentAcct.getLevel()+1);
			acct.setIsLeaf(true);
			acct.setIsEnabled(parentAcct.isIsEnabled());
			acct.setIsSource(parentAcct.isIsSource());
			acct.setFullOrgUnit(parentAcct.getFullOrgUnit());
			acct.setIsCostAccount(parentAcct.isIsCostAccount());
			acct.setIsProgramming(parentAcct.isIsProgramming());
			acct.setIsEnterDB(parentAcct.isIsEnterDB());
		}else{
			acct.setLevel(1);
			acct.setLongNumber(longNumber);
			acct.setNumber(longNumber);
			acct.setName(name);
			acct.setDisplayName(name);
			acct.setIsLeaf(true);
			acct.setIsEnabled(false);
			acct.setIsSource(true);
			acct.setParent(null);
			acct.setIsCostAccount(true);
			acct.setIsProgramming(true);
			acct.setIsEnterDB(true);
			FullOrgUnitInfo orgUnit=new FullOrgUnitInfo();
			orgUnit.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
			acct.setFullOrgUnit(orgUnit);
//			UserInfo user=new UserInfo();
//			user.setId(BOSUuid.read("9SFtkAENEADgAjUUwKg88RO33n8="));
//			acct.setCreator(user);
//			acct.setLastUpdateUser(user);
			
		}
		
		return acct;
	}
	
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx)
			throws TaskExternalException {
		super.submit(coreBaseInfo, ctx);
		CostAccountInfo acct=(CostAccountInfo)coreBaseInfo;
		if(acct.getParent()!=null){
			reWriterCostAccount(ctx, acct.getParent());
		}
	}
	private String getData(Hashtable hsData,String key){
		Object data = ((DataToken) hsData.get(key)).data;
		if(FDCHelper.isEmpty(data)){
			return null;
		}
		return data.toString().trim();
	}
	
	private CostAccountInfo getCostAccount(Context ctx,String longNumber) throws TaskExternalException{
		CostAccountInfo acct=null;
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("fullOrgUnit.id", OrgConstants.DEF_CU_ID);
		view.getFilter().appendFilterItem("longNumber", longNumber);
		
		view.getSelector().add("*");
		view.getSelector().add("fullOrgUnit.id");
		CostAccountCollection costAccountCollection;
		try {
			costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(),e);
		}
		if(costAccountCollection.size()==1){
			return costAccountCollection.get(0);
		}else{
			throw new TaskExternalException("设置父成本科目时出错：集团内父成本科目编码："+longNumber+" 对应的成本科目不存或不唯一");
		}
	}
	
	private void isExist(Context ctx,String longNumber) throws TaskExternalException{
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("fullOrgUnit.id", OrgConstants.DEF_CU_ID);
		filter.appendFilterItem("longNumber", longNumber);
		boolean exists =false;
		try {
			exists = CostAccountFactory.getLocalInstance(ctx).exists(filter);
		} catch (Exception e) {
			throw new TaskExternalException(e.getMessage(),e);
		}
		if(exists){
			throw new TaskExternalException("集团内编码："+longNumber+"对应的成本科目已经存在");
		}
	}
	
	private void reWriterCostAccount(Context ctx,CostAccountInfo acct) throws TaskExternalException{
//		SelectorItemCollection selector=new SelectorItemCollection();
//		selector.add("isLeaf");
//		try {
//			CostAccountFactory.getLocalInstance(ctx).updatePartial(acct, selector);
//		} catch (Exception e) {
//			throw new TaskExternalException("反写父科目时出错");
//		}
		if(!acct.isIsLeaf()){
			return;
		}
		try{
			String sql="update T_FDC_CostAccount set FisLeaf=0 where fid=? and ffullorgunitid=?";
			String params[]=new String[]{acct.getId().toString(),OrgConstants.DEF_CU_ID};
			DbUtil.execute(ctx, sql, params);
		} catch (Exception e) {
			throw new TaskExternalException("反写父科目时出错");
		}
	}

}
