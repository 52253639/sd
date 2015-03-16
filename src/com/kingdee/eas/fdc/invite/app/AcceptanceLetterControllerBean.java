package com.kingdee.eas.fdc.invite.app;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.common.AttachmentServerManager;
import com.kingdee.eas.base.botp.BOTBillOperStateEnum;
import com.kingdee.eas.base.btp.BTPManagerFactory;
import com.kingdee.eas.base.btp.BTPTransformResult;
import com.kingdee.eas.base.btp.IBTPManager;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractDetailDefCollection;
import com.kingdee.eas.fdc.basedata.ContractDetailDefFactory;
import com.kingdee.eas.fdc.basedata.ContractDetailDefInfo;
import com.kingdee.eas.fdc.basedata.ContractSourceInfo;
import com.kingdee.eas.fdc.basedata.DataTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.invite.AcceptanceLetterFactory;
import com.kingdee.eas.fdc.invite.AcceptanceLetterInfo;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.fdc.invite.InvitePreSplitCollection;
import com.kingdee.eas.fdc.invite.InvitePreSplitFactory;
import com.kingdee.eas.fdc.invite.InvitePreSplitInfo;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectStateEnum;
import com.kingdee.util.NumericExceptionSubItem;

public class AcceptanceLetterControllerBean extends AbstractAcceptanceLetterControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.AcceptanceLetterControllerBean");
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	super._audit(ctx, billId);
    	IObjectPK pk = new ObjectUuidPK(billId.toString());
    	AcceptanceLetterInfo letterInfo = AcceptanceLetterFactory.getLocalInstance(ctx).getAcceptanceLetterInfo(pk);
    	if(letterInfo.getInviteProject() != null)
    	{
    		IObjectPK tmpPK = new ObjectUuidPK(letterInfo.getInviteProject().getId().toString());
//    		InviteProjectFactory.getLocalInstance(ctx).setFixed(tmpPK);
    	}
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("update t_inv_acceptanceletter set fislastversion = 0 where fnumber = ? and fid <> ?");
    	builder.addParam(letterInfo.getNumber());
    	builder.addParam(letterInfo.getId().toString());
    	builder.executeUpdate();
    	builder.clear();
    	builder.appendSql("update t_inv_acceptanceletter set fislastversion = 1 where fid = ?");
    	builder.addParam(letterInfo.getId().toString());
    	builder.executeUpdate();
    }
    
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	IObjectPK pk = new ObjectUuidPK(billId.toString());
    	AcceptanceLetterInfo letterInfo = AcceptanceLetterFactory.getLocalInstance(ctx).getAcceptanceLetterInfo(pk);
    	if(letterInfo.isIsLastVersion() && letterInfo.getVersion()==getMaxVersion(letterInfo.getNumber(), ctx)){
    		super._unAudit(ctx, billId);
    		if(letterInfo.getInviteProject() != null)
        	{
        		IObjectPK tmpPK = new ObjectUuidPK(letterInfo.getInviteProject().getId().toString());
//        		InviteProjectFactory.getLocalInstance(ctx).setAppraising(tmpPK);
        	}
            builder.clear();
        	builder.appendSql("update t_inv_acceptanceletter set fislastversion =0 where fid = ?");
        	builder.addParam(letterInfo.getId().toString());
        	builder.executeUpdate();
        	builder.clear();
        	builder.appendSql("update t_inv_acceptanceletter set fislastversion = 1 where fid = (select fid from t_inv_acceptanceletter where fversion=(select max(fversion) from t_inv_acceptanceletter where fnumber = ? and fstate ='4AUDITTED') and fnumber =?)");
        	builder.addParam(letterInfo.getNumber());
        	builder.addParam(letterInfo.getNumber());
        	builder.executeUpdate();
    	}else{
    		throw new FDCInviteException(FDCInviteException.CANNOT_UNAUDIT);
    	}
    	
    	
    	
    	
    }

	protected void _setCreateContract(Context ctx, String billId,
			boolean paramIsCreate) throws BOSException, EASBizException {
		
		if(paramIsCreate){
			SelectorItemCollection sic = new SelectorItemCollection();

			sic.add("*");
			sic.add("inviteProject.id");
			sic.add("inviteProject.project.id");
			sic.add("inviteProject.project.isLeaf");
			sic.add("inviteProject.project.fullOrgUnit.id");
			sic.add("inviteProject.project.fullOrgUnit.number");
			sic.add("inviteProject.project.fullOrgUnit.longNumber");
			sic.add("inviteProject.project.fullOrgUnit.name");
			sic.add("inviteProject.project.landDeveloper.id");
			sic.add("inviteProject.project.landDeveloper.number");
			sic.add("inviteProject.project.landDeveloper.name");
			
			sic.add("inviteProject.project.costCenter.id");
		
			sic.add("inviteProject.invitePlan.respDept.id");
			sic.add("inviteProject.invitePlan.respDept.number");
			sic.add("inviteProject.invitePlan.respDept.name");
			sic.add("inviteProject.invitePlan.respPerson.id");
			sic.add("inviteProject.invitePlan.respPerson.number");
			sic.add("inviteProject.invitePlan.respPerson.name");
			
			sic.add("inviteProject.programmingContract.*");
			
			AcceptanceLetterInfo letterInfo  = getAcceptanceLetterInfo(ctx,new ObjectUuidPK(billId),sic);
			if(letterInfo==null||letterInfo.getInviteProject()==null
					/*||letterInfo.getInviteProject().getProject()==null*/)
			{
				
				throw new EASBizException(new NumericExceptionSubItem("100","招标立项中的工程项目为空时，不能关联生成合同"));
				
			}/*else if(!letterInfo.getInviteProject().getProject().isIsLeaf()){
				
				throw new EASBizException(new NumericExceptionSubItem("100","招标立项中的工程项目为非明细工程项目时，不能关联生成合同"));
			}
			else if(letterInfo.getInviteProject().getProject().getCostCenter() == null)
			{
				throw new EASBizException(new NumericExceptionSubItem("100","招标立项中的工程项目没有对应成本中心，不能关联生成合同"));
			}*/
			try {
				
				BOSObjectType bosType = new ContractBillInfo().getBOSType();

				IBTPManager iBTPManager = BTPManagerFactory.getLocalInstance(ctx);
				BTPTransformResult result = iBTPManager.transform(letterInfo, bosType.toString());

				IObjectCollection destBillColl = result.getBills();
				BOTRelationCollection botRelateColl = result.getBOTRelationCollection();

				ContractBillInfo destBillInfo = null;
				/*FullOrgUnitInfo org = letterInfo.getInviteProject().getProject().getFullOrgUnit();
				CompanyOrgUnitInfo company=null;	
				if(org!=null){
					String companyId=org.getId().toString();
					company=new CompanyOrgUnitInfo();
					company.setId(BOSUuid.read(companyId));
				}else{
					company=FDCHelper.getFIOrgUnit(ctx, org);
				}*/
				//合同生成后部分属性处理
				for (int i = 0, size = destBillColl.size(); i < size; i++){
					destBillInfo = (ContractBillInfo) destBillColl.getObject(i);
					//因为是新增合同，将其ID置空，否则ContractBillControllerBean._save会报断 Owen_wen 2011-03-28
					destBillInfo.setId(null); 
//					destBillInfo.setCurProject(letterInfo.getInviteProject().getProject());
					//TODO 一些必要属性的处理 ，这里实现一些BOTP不能配置的属性处理
					
					//币别
					
					CurrencyInfo currency = FDCHelper.getBaseCurrency(ctx);
					
					destBillInfo.setCurrency(currency);
					destBillInfo.setExRate(FDCHelper.ONE);
					
					//本币
					destBillInfo.setAmount(destBillInfo.getOriginalAmount());
					
					//原币
					destBillInfo.setOriginalAmount(letterInfo.getAmount());
					// 关联框架合约
					destBillInfo.setProgrammingContract(letterInfo.getInviteProject().getProgrammingContract());
					if(letterInfo.getInviteProject().getProgrammingContract()!=null){
						destBillInfo.setSrcProID(letterInfo.getInviteProject().getProgrammingContract().getSrcId());
						//根据所关联的框架合约来设置合同相关信息
						destBillInfo = autoProgToCon(ctx,destBillInfo,letterInfo.getInviteProject().getProgrammingContract());
					}
					destBillInfo.setAmount(FDCHelper.toBigDecimal(letterInfo.getInviteAmount()));
					destBillInfo.setPayPercForWarn(FDCHelper.toBigDecimal("85"));
					destBillInfo.setChgPercForWarn(FDCHelper.toBigDecimal("5"));
					destBillInfo.setGrtAmount(FDCHelper.ZERO);
					destBillInfo.setGrtLocalAmount(FDCHelper.ZERO);
					destBillInfo.setGrtRate(FDCHelper.ZERO);
					destBillInfo.setPayScale(FDCHelper.ZERO);
					
					//责任人，责任部门
					if(letterInfo.getInviteProject().getInvitePlan()!=null){
//						destBillInfo.setRespDept(letterInfo.getInviteProject().getInvitePlan().getRespDept());
//						destBillInfo.setRespPerson(letterInfo.getInviteProject().getInvitePlan().getRespPerson());
					}
					
					//甲方
//					destBillInfo.setLandDeveloper(letterInfo.getInviteProject().getProject().getLandDeveloper());
					
					//形成方式
					ContractSourceInfo contractSource = ContractSourceInfo.INVITE;
					contractSource.setId(BOSUuid.read(ContractSourceInfo.INVITE_VALUE));
					destBillInfo.setContractSourceId(contractSource);
					//合同的分录
					if (destBillInfo.getContractType() != null)
						destBillInfo.setIsCoseSplit(destBillInfo.getContractType().isIsCost());
					if(destBillInfo.getContractType()!=null){
						EntityViewInfo view = new EntityViewInfo();
						FilterInfo filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("contractType.id", destBillInfo.getContractType().getId().toString()));
						filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
						view.setFilter(filter);
						SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
						sorterItemInfo.setSortType(SortType.ASCEND);
						view.getSorter().add(sorterItemInfo);
						ContractDetailDefCollection contractDetailDefCollection = ContractDetailDefFactory
								.getLocalInstance(ctx).getContractDetailDefCollection(view);
						
						for (Iterator iter = contractDetailDefCollection
								.iterator(); iter.hasNext();) {
							ContractDetailDefInfo element = (ContractDetailDefInfo) iter
									.next();
							ContractBillEntryInfo entry = new ContractBillEntryInfo();
							entry.setDetail(element.getName());
							entry.setContent(null);
							entry.setParent(destBillInfo);
							entry.setDetailDefID(element.getId().toString());
							entry.setDataType(element.getDataTypeEnum());
							if(DataTypeEnum.DATE.equals(element.getDataTypeEnum())){
								SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
								entry.setContent(s.format(new Date()));
							}
							entry.setDesc(null);
							
							destBillInfo.getEntrys().add(entry);
						}
					}
					final IObjectPK pk = iBTPManager.saveRelations(destBillInfo, botRelateColl);
					destBillInfo.setId(BOSUuid.read(pk.toString()));	
					
				}
				
				//createContract(ctx,letterInfo);
			} catch (Exception e) {
				throw new EASBizException(new NumericExceptionSubItem("100","生成合同时出错:"+e.getMessage()),e);//new BOSException(e.getMessage());//new PayRequestBillException(PayRequestBillException.CHECKTEXTLENGTH1);
			}
		}
		
		AcceptanceLetterInfo info = new AcceptanceLetterInfo();
		info.setId(BOSUuid.read(billId));
		info.setIsCreateContract(paramIsCreate);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("isCreateContract");
		
		this.updatePartial(ctx, info, selector);
		
		IObjectPK pk = new ObjectUuidPK(billId);
		AcceptanceLetterInfo tmpInfo = AcceptanceLetterFactory.getLocalInstance(ctx).getAcceptanceLetterInfo(pk);
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("select id where inviteProject = '");
		buffer.append(tmpInfo.getInviteProject().getId().toString());
		buffer.append("'");
		buffer.append(" state = '");
		buffer.append(FDCBillStateEnum.AUDITTED_VALUE);
		buffer.append("'");
		
		InvitePreSplitCollection preSplitCols = InvitePreSplitFactory.getLocalInstance(ctx).getInvitePreSplitCollection(buffer.toString());
		if(preSplitCols.size() == 1)
		{
			InvitePreSplitInfo preInfo = preSplitCols.get(0);
			String tmpPreId = preInfo.getId().toString();
			
			InvitePreSplitFactory.getLocalInstance(ctx).setCreateContract(tmpPreId, paramIsCreate);
		}
		
		//更新招标立项的状态为已签约
		IObjectPK invitePK = new ObjectUuidPK(tmpInfo.getInviteProject().getId().toString());
//		InviteProjectFactory.getLocalInstance(ctx).setSignedContract(invitePK);
		
	}

	/**
	 * 根据所关联的框架合约来设置合同相关信息
	 * @param conInfo
	 * @param pcInfo
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private ContractBillInfo autoProgToCon(Context ctx,ContractBillInfo billInfo,
			ProgrammingContractInfo pcInfo) throws EASBizException, BOSException {
		//设值
		billInfo.setNumber(pcInfo.getNumber());
		billInfo.setName(pcInfo.getName());
//		billInfo.setAmount(pcInfo.getControlBalance());
		FilterInfo filter = numberDupFilter(billInfo);
		int  i = 1;
		while (ContractBillFactory.getLocalInstance(ctx).exists(filter)) {
			StringBuffer billNumberNew = new StringBuffer();
			String billNumber = billInfo.getNumber();
			int lastIndexOf = billNumber.lastIndexOf("_INV_CON$_");
			if( lastIndexOf > 0){
				String lastNumber = billNumber.substring(lastIndexOf+10, billNumber.length());
				int lastNumberInt = Integer.parseInt(lastNumber);
				lastNumberInt = lastNumberInt + 1;
				String newNumber = lastNumberInt+"";
				String beforeNumber = billNumber.substring(0,lastIndexOf+10);
				String lastNewNumber = beforeNumber.concat(newNumber);
				billNumberNew = new StringBuffer(lastNewNumber);
			}else{
				billNumberNew = billNumberNew.append(billNumber).append("_INV_CON$_"+i);
			}
			billInfo.setNumber(billNumberNew.toString());
			i++;
			filter = numberDupFilter(billInfo);
		}
		filter = namesDupFilter(billInfo);
		i = 1;
		while (ContractBillFactory.getLocalInstance(ctx).exists(filter)) {
			StringBuffer billNameNew = new StringBuffer();
			String billName = billInfo.getName();
			int lastIndexOf = billName.lastIndexOf("_INV_CON$_");
			if( lastIndexOf > 0){
				String lastName = billName.substring(lastIndexOf+10, billName.length());
				int lastNameInt = Integer.parseInt(lastName);
				lastNameInt = lastNameInt + 1;
				String newName = lastNameInt+"";
				String beforeName = billName.substring(0,lastIndexOf+10);
				String lastNewName = beforeName.concat(newName);
				billNameNew = new StringBuffer(lastNewName);
			}else{
				billNameNew = billNameNew.append(billName).append("_INV_CON$_"+i);
			}
			billInfo.setName(billNameNew.toString());
			i++;
			filter = namesDupFilter(billInfo);
		}
		return billInfo;
	}

	private FilterInfo namesDupFilter(ContractBillInfo billInfo) {
		FilterInfo filter;
		//检查名称重复
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("name", billInfo.getName()));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));		
		filter.getFilterItems()
				.add(new FilterItemInfo("curProject", billInfo.getCurProject().getId()));
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}
		return filter;
	}

	private FilterInfo numberDupFilter(ContractBillInfo billInfo) {
		//检查编码重复
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("number", billInfo.getNumber()));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));		
		filter.getFilterItems()
				.add(new FilterItemInfo("curProject", billInfo.getCurProject().getId()));
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}
		return filter;
	}

	/*private ContractBillInfo createContract(Context ctx,AcceptanceLetterInfo letterInfo) throws Exception{
		BOSObjectType bosType = new ContractBillInfo().getBOSType();

		IBTPManager iBTPManager = BTPManagerFactory.getLocalInstance(ctx);
		BTPTransformResult result = iBTPManager.transform(letterInfo, bosType.toString());

		IObjectCollection destBillColl = result.getBills();
		BOTRelationCollection botRelateColl = result.getBOTRelationCollection();

		ContractBillInfo destBillInfo = null;
		FullOrgUnitInfo org = letterInfo.getInviteProject().getProject().getFullOrgUnit();
		CompanyOrgUnitInfo company=null;	
		if(org!=null){
			String companyId=org.getId().toString();
			company=new CompanyOrgUnitInfo();
			company.setId(BOSUuid.read(companyId));
		}else{
			company=FDCHelper.getFIOrgUnit(ctx, org);
		}
		//合同生成后部分属性处理
		for (int i = 0, size = destBillColl.size(); i < size; i++){
			destBillInfo = (ContractBillInfo) destBillColl.getObject(i);
			destBillInfo.setCurProject(letterInfo.getInviteProject().getProject());
			//TODO 一些必要属性的处理 ，这里实现一些BOTP不能配置的属性处理
			CurrencyInfo currency = FDCHelper.getBaseCurrency(ctx);
			//币别
			destBillInfo.setCurrency(currency);
			//合同的分录
			if(destBillInfo.getContractType()!=null){
				
			}
			
		}
		
		return destBillInfo;
	}*/
	protected void _reverseSave(Context ctx, IObjectPK srcBillPK,
			IObjectValue srcBillVO, BOTBillOperStateEnum billOperStateEnum,
			IObjectValue relationInfo) throws BOSException, EASBizException {
		if(billOperStateEnum.equals(BOTBillOperStateEnum.DELETE)){
			AcceptanceLetterInfo letterInfo = (AcceptanceLetterInfo)srcBillVO;
			letterInfo.setIsCreateContract(false);
			IObjectPK inviteProjectPK = new ObjectUuidPK(letterInfo.getInviteProject().getId().toString());
//			InviteProjectFactory.getLocalInstance(ctx).setFixed(inviteProjectPK);
			_setCreateContract(ctx,srcBillPK.toString(),false);
		}
		
		super._reverseSave(ctx, srcBillPK, srcBillVO, billOperStateEnum, relationInfo);
		
	}
	protected void _reverseSave(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		
		super._reverseSave(ctx, pk, model);
	}

	protected IObjectValue _revise(Context ctx, BOSUuid billId)
			throws BOSException, FDCInviteException {
		// TODO Auto-generated method stub
		 SelectorItemCollection sic = new SelectorItemCollection();
	        sic.add(new SelectorItemInfo("isCreateContract"));
	        sic.add(new SelectorItemInfo("creator.*"));
	        sic.add(new SelectorItemInfo("createTime"));
	        sic.add(new SelectorItemInfo("number"));
	        sic.add(new SelectorItemInfo("auditor.*"));
	        sic.add(new SelectorItemInfo("auditTime"));
	        sic.add(new SelectorItemInfo("supplier.*"));
	        sic.add(new SelectorItemInfo("inviteProject.*"));
	        sic.add(new SelectorItemInfo("inviteAmount"));
	        sic.add(new SelectorItemInfo("quantity"));
	        sic.add(new SelectorItemInfo("inviteForm"));
	        sic.add(new SelectorItemInfo("lowestPrice"));
	        sic.add(new SelectorItemInfo("lowerPrice"));
	        sic.add(new SelectorItemInfo("middlePrice"));
	        sic.add(new SelectorItemInfo("higherPrice"));
	        sic.add(new SelectorItemInfo("highestPrice"));
	        sic.add(new SelectorItemInfo("lowestPriceUnit.*"));
	        sic.add(new SelectorItemInfo("lowerPriceUnit.*"));
	        sic.add(new SelectorItemInfo("middlePriceUnit.*"));
	        sic.add(new SelectorItemInfo("higherPriceUnit.*"));
	        sic.add(new SelectorItemInfo("highestPriceUnit.*"));
	        sic.add(new SelectorItemInfo("description"));
	        sic.add(new SelectorItemInfo("inviteProject.inviteMode"));
	        sic.add(new SelectorItemInfo("inviteProject.orgUnit.name"));
	        sic.add(new SelectorItemInfo("state"));
			sic.add(new SelectorItemInfo("orgUnit"));
			sic.add(new SelectorItemInfo("inviteProject.inviteMode.*"));
			sic.add(new SelectorItemInfo("inviteProject.orgUnit.*"));
			sic.add(new SelectorItemInfo("*"));
			AcceptanceLetterInfo info = null;
			try {
				info  = AcceptanceLetterFactory.getLocalInstance(ctx).getAcceptanceLetterInfo(new ObjectUuidPK(billId),sic);
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(info != null){
				
				if(!info.isIsLastVersion()||info.getVersion()<getMaxVersion(info.getNumber(), ctx)||!info.getState().equals(FDCBillStateEnum.AUDITTED)){
					throw new FDCInviteException(FDCInviteException.CANNOTREVERSIONFINAL);
				}
				if(info.getInviteProject()!=null && info.getInviteProject().getInviteProjectState().equals(InviteProjectStateEnum.SIGNEDCONTRACT)){
					throw new FDCInviteException(FDCInviteException.ALREADY_SIGN_CONTRACT);
				}
				BOSUuid oldId = info.getId();
				AttachmentServerManager asm = AttachmentManagerFactory.getServerManager(ctx);
				info.setId(BOSUuid.create(info.getBOSType()));
				try {
					asm.copyBizAttachmentFiles(oldId.toString(), info.getId().toString());
				} catch (EASBizException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				info.setVersion(info.getVersion()+0.1);
				info.setIsLastVersion(false);
				info.setState(FDCBillStateEnum.SAVED);
				info.setAuditor(null);
				info.setAuditTime(null);
				
				//处理一下附件
				
			}
		
		return info;
	}
	
	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		AcceptanceLetterInfo info = (AcceptanceLetterInfo) model;
		if(info.getVersion()==1.0){
			super.checkBill(ctx, model);
		}
		
	}
	
	public double getMaxVersion(String fnumber,Context ctx){
		double version = 1.0;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select max(fversion) from t_inv_acceptanceletter where fnumber =?");
		builder.addParam(fnumber);
		try {
			RowSet rs = builder.executeQuery();
			while(rs.next()){
				version =rs.getDouble(1);
			}
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}
	
	// EditUI将招标立项的名称放到中标通知书的name字段中，
	// 由于R110317-001取消一张《中标结果报审》只可以有一家供应商中标的限制，因此，会出现有多家中标通知书name相同。
	// Added by Owen_wen 2011-04-18
	protected boolean isUseName() {
		return false;
	}
}