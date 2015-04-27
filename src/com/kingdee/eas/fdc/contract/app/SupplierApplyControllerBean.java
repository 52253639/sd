package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardInfo;
import com.kingdee.eas.basedata.master.cssp.StandardTypeEnum;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.cssp.UsedStatusEnum;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitCollection;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.SupplierApplyCollection;
import com.kingdee.eas.fdc.contract.SupplierApplyFactory;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DeletedStatusEnum;
import com.kingdee.eas.framework.EffectedStatusEnum;
import com.kingdee.eas.fdc.contract.SupplierApplyInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.util.NumericExceptionSubItem;

public class SupplierApplyControllerBean extends AbstractSupplierApplyControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.SupplierApplyControllerBean");

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		super._audit(ctx, billId);
		addToSysSupplier(ctx,this.getSupplierApplyInfo(ctx, new ObjectUuidPK(billId)));
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		SupplierApplyInfo info=this.getSupplierApplyInfo(ctx, new ObjectUuidPK(billId));
		if(info.getSourceBillId()!=null&&SupplierFactory.getLocalInstance(ctx).exists(new ObjectUuidPK(info.getSourceBillId()))){
			throw new EASBizException(new NumericExceptionSubItem("101","已关联主数据供应商,不能进行反审批操作！"));
		}
		super._unAudit(ctx, billId);
	}
	protected void addToSysSupplier(Context ctx, IObjectValue objectValue) throws BOSException, EASBizException {
		SupplierApplyInfo info = (SupplierApplyInfo)objectValue;
		if(info==null){
			return;
		}
		SupplierInfo supplier = new SupplierInfo();
		supplier.setId(BOSUuid.create(supplier.getBosType()));
		setSysSupplierValue(ctx, info, supplier);
		
		CtrlUnitInfo cu = new CtrlUnitInfo();
		cu.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
		
		CSSPGroupInfo groupInfo =null;
		SupplierGroupDetailInfo Gdinfo = null;
		CSSPGroupCollection groupCol = CSSPGroupFactory.getLocalInstance(ctx).getCSSPGroupCollection("select number,name,groupStandard.id from where groupStandard.id='00000000-0000-0000-0000-000000000001BC122A7F' and cu.id='"+OrgConstants.DEF_CU_ID+"'");
		if(groupCol.size()>0){
			supplier.setBrowseGroup(groupInfo);
    		
    		Gdinfo = new SupplierGroupDetailInfo();
    		Gdinfo.setSupplierGroup(groupInfo);
    		Gdinfo.setSupplierGroupFullName(groupInfo.getName());
    		Gdinfo.setSupplierGroupStandard(groupInfo.getGroupStandard());
    		supplier.getSupplierGroupDetails().add(Gdinfo);
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("name", "房地产供应商"));
		filter.getFilterItems().add(new FilterItemInfo("CU.id", OrgConstants.DEF_CU_ID));
		
		CSSPGroupCollection sheGroupCol = CSSPGroupFactory.getLocalInstance(ctx).getCSSPGroupCollection(view);
		if(sheGroupCol.isEmpty()){
			CSSPGroupStandardInfo strd = new CSSPGroupStandardInfo();
			strd.setId(BOSUuid.create(strd.getBOSType()));
			strd.setNumber("fdcsupplierGstrd");
			strd.setName("房地产供应商分类标准");
			strd.setType(2);
			strd.setIsBasic(StandardTypeEnum.defaultStandard);
			strd.setCU(cu);
			
			CSSPGroupStandardFactory.getLocalInstance(ctx).addnew(strd);
			
			CSSPGroupInfo gr = new CSSPGroupInfo();
			gr.setDeletedStatus(DeletedStatusEnum.NORMAL);
			gr.setId(BOSUuid.create(gr.getBOSType()));
			gr.setNumber("fdcsupplierG");
			gr.setName("房地产供应商");
			gr.setCU(cu);
			gr.setGroupStandard(strd);
			
			CSSPGroupFactory.getLocalInstance(ctx).addnew(gr);
			
			groupInfo = gr;
		}else{
			groupInfo = sheGroupCol.get(0);
		}
		if(supplier.getBrowseGroup()!=null){
			supplier.setBrowseGroup(groupInfo);
		}
		Gdinfo = new SupplierGroupDetailInfo();
		Gdinfo.setSupplierGroup(groupInfo);
		Gdinfo.setSupplierGroupFullName(groupInfo.getName());
		Gdinfo.setSupplierGroupStandard(groupInfo.getGroupStandard());
		supplier.getSupplierGroupDetails().add(Gdinfo);
		
		SupplierFactory.getLocalInstance(ctx).addnew(supplier);
		
		SupplierCompanyInfoInfo com = new SupplierCompanyInfoInfo();
		CompanyOrgUnitInfo company=new CompanyOrgUnitInfo();
		company.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
		com.setCompanyOrgUnit(company);
		com.setSupplier(supplier);
		com.setCU(cu);
		SupplierCompanyInfoFactory.getLocalInstance(ctx).addnew(com);
		
		Set cuIds = getSupplierMgeCu(ctx,supplier.getAdminCU().getId().toString());
    	for(Iterator itor = cuIds.iterator(); itor.hasNext(); ){
    		String cuId = (String) itor.next();
    		SupplierFactory.getLocalInstance(ctx).assign(new ObjectUuidPK(supplier.getAdminCU().getId()), new ObjectUuidPK(supplier.getId()), new ObjectUuidPK(cuId));
    	}
    	
    	SelectorItemCollection sic=new SelectorItemCollection();
    	sic.add("sourceBillId");
    	
    	info.setSourceBillId(supplier.getId().toString());
    	SupplierApplyFactory.getLocalInstance(ctx).updatePartial(info,sic);
	}
	private Set getSupplierMgeCu(Context ctx,String cuId) throws BOSException {
		Set set = new HashSet();
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", OrgConstants.SYS_CU_ID,CompareType.NOTEQUALS));
		view.setFilter(filter);
		view.setSelector(sel);
		
		CtrlUnitCollection orgColl = CtrlUnitFactory.getLocalInstance(ctx).getCtrlUnitCollection(view);
		for (int i = 0; i < orgColl.size(); i++) {
			if(cuId.equals(orgColl.get(i).getId().toString())){
				continue;
			}
			set.add(orgColl.get(i).getId().toString());
		}
		return set;
	}
	private void setSysSupplierValue(Context ctx, SupplierApplyInfo info, SupplierInfo supplier) throws BOSException, EASBizException {
		supplier.setNumber("FDC-APPLY-" + info.getNumber());
		supplier.setName(info.getName());
		supplier.setDescription(info.getDescription());
		CtrlUnitInfo cu = new CtrlUnitInfo();
		cu.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
		supplier.setCU(cu);
		supplier.setAdminCU(cu);
		
		supplier.setVersion(1);
		supplier.setUsedStatus(UsedStatusEnum.APPROVED);
		supplier.setEffectedStatus(EffectedStatusEnum.EFFECTED);
		supplier.setIsInternalCompany(false);
	}
	protected void checkNumberDup(Context ctx, FDCBillInfo billInfo)throws BOSException, EASBizException{
		if(!isUseNumber())
			return;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", billInfo.getNumber().trim()));
		if(billInfo.getId() != null)
			filter.getFilterItems().add(new FilterItemInfo("id", billInfo.getId().toString(), CompareType.NOTEQUALS));

		if(_exists(ctx, filter))
			throw new ContractException(ContractException.NUMBER_DUP);
		else return;
	}
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo)throws BOSException, EASBizException{
		if(!isUseNumber())
			return;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", billInfo.getName().trim()));
		if(billInfo.getId() != null)
			filter.getFilterItems().add(new FilterItemInfo("id", billInfo.getId().toString(), CompareType.NOTEQUALS));

		if(_exists(ctx, filter))
			throw new ContractException(ContractException.NAME_DUP);
		else return;
	}
}