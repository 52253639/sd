package com.kingdee.eas.fdc.contract.programming.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.service.formula.builder.bosmeta.BOSType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.programming.ProgrammingCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.fdc.invite.IInviteProject;
import com.kingdee.eas.fdc.invite.InviteProjectCollection;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherFactory;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;

public class ProgrammingControllerBean extends AbstractProgrammingControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.programming.app.ProgrammingControllerBean");
    
	private SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("version");
		sic.add("versionGroup");
		sic.add("aimCost.versionName");
		sic.add("aimCost.versionNumber");
		sic.add("state");
		sic.add("creator.name");
		sic.add("creator.createTime");
		sic.add("lastUpdateUser.name");
		sic.add("lastUpdateUser.lastUpdateTime");
		sic.add("isLatest");
		sic.add("entries.id");
		sic.add("entries.srcId");
		sic.add("entries.*");
		return sic;
	}

	protected boolean _isLastVersion(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		if (pk == null) {
			throw new BOSException("pk is empty");
    	}
		
		ProgrammingInfo info = getProgrammingInfo(ctx, pk, getSelectors());
		return info.isIsLatest();
	}
	
	protected IObjectValue _getLastVersion(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		if (pk == null) {
			throw new BOSException("pk is empty");
    	}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("versionGroup");
    	return _getLastVersion(ctx, getProgrammingInfo(ctx, pk, sic).getVersionGroup());
	}

	protected IObjectValue _getLastVersion(Context ctx, String versionGroup) throws BOSException, EASBizException {
		if (StringUtils.isEmpty(versionGroup)) {
			return new ProgrammingInfo();
		}
		
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("versionGroup", versionGroup));
    	filter.getFilterItems().add(new FilterItemInfo("isLatest", Boolean.TRUE));
    	EntityViewInfo evi = new EntityViewInfo();
    	evi.setSelector(getSelectors());
    	evi.setFilter(filter);
    	
    	ProgrammingCollection collection = getProgrammingCollection(ctx, evi);
    	return collection == null || collection.isEmpty() ? null : collection.get(0);
	}
	
	/**
	 * 得到上一版本
	 * @param ctx
	 * @param model
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private IObjectValue getPreVersion(Context ctx, IObjectValue model) 
	throws BOSException, EASBizException {
		if (model == null) {
			return new ProgrammingInfo(); 
		}
		
		ProgrammingInfo info = (ProgrammingInfo) model;
		BigDecimal version = info.getVersion();
		String versionGroup = info.getVersionGroup();
		if (version == null || versionGroup == null) {
			IObjectPK pk = new ObjectUuidPK(info.getId());
			info = getProgrammingInfo(ctx, pk, getSelectors());
			version = info.getVersion();
			versionGroup = info.getVersionGroup();
		}
		if (version.compareTo(FDCHelper.ONE) == 0) {
			return model;
		}
		
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("versionGroup", versionGroup));
    	filter.getFilterItems().add(new FilterItemInfo("version", version.subtract(FDCHelper.ONE)));
    	
    	EntityViewInfo evi = new EntityViewInfo();
    	evi.setSelector(getSelectors());
    	evi.setFilter(filter);
    	
    	ProgrammingCollection collection = getProgrammingCollection(ctx, evi);
    	return collection == null || collection.isEmpty() ? null : collection.get(0);
	}
	
	protected void _setAuttingForWF(Context ctx, BOSUuid pk) throws BOSException, EASBizException {
		ProgrammingInfo billInfo = null;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		billInfo=(ProgrammingInfo)super.getValue(ctx, new ObjectUuidPK(pk),selector);
		billInfo.setState(FDCBillStateEnum.AUDITTING);
		ProgrammingFactory.getLocalInstance(ctx).updatePartial(billInfo, selector);
	}
	
	public void setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		ProgrammingInfo billInfo = ProgrammingFactory.getLocalInstance(ctx).getProgrammingInfo(new ObjectUuidPK(billId));
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("state");
		_updatePartial(ctx, billInfo, selector);
	}
	protected void checkBill(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		
	}
    protected void trimName(FDCBillInfo fDCBillInfo) {
		if(fDCBillInfo.getName() != null) {
			fDCBillInfo.setName(fDCBillInfo.getName().trim());
		}
	}
    
	protected IObjectPK _billModify(Context ctx, String versionGroup, String curVersion) throws BOSException,
			EASBizException {
		return null;
	}
	
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ProgrammingInfo info = (ProgrammingInfo)model;
		info.getVersionGroup();
		FDCSQLBuilder sql = new FDCSQLBuilder(ctx, "select fid from t_con_programming where FVersionGroup = '"+info.getVersionGroup()+"'");
		IRowSet rs = sql.executeQuery();
		try {
			if(!rs.next()){
				info.setIsLatest(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return super._submit(ctx, model);
	}
	
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ProgrammingInfo info = (ProgrammingInfo) model;
		info.getVersionGroup();
		FDCSQLBuilder sql = new FDCSQLBuilder(ctx, "select fid from t_con_programming where FVersionGroup = '" + info.getVersionGroup() + "'");
		IRowSet rs = sql.executeQuery();
		try {
			if (!rs.next()) {
				info.setIsLatest(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return super._save(ctx, model);
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._audit(ctx, billId);
		ProgrammingInfo info = getProgrammingInfo(ctx, new ObjectUuidPK(billId), getSelectors());
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update t_con_programming set FIsLatest = 1 where fid = '").append(billId.toString()).append("'");
		fdcSB.addBatch(sql.toString());
		
		String versionGroup = info.getVersionGroup();
		BigDecimal version = info.getVersion().subtract(new BigDecimal("1"));
		sql.setLength(0);
		sql.append("update t_con_programming set FIsLatest = 0 where FVersionGroup = '");
		sql.append(versionGroup).append("' ");
		sql.append("and FVersion = ").append(version.toString());
		fdcSB.addBatch(sql.toString());
		
//		if (!info.getEntries().isEmpty()) {
//			ProgrammingContractCollection currentEntries = info.getEntries();
//			info = (ProgrammingInfo) getPreVersion(ctx, info);
//			ProgrammingContractCollection entries = info.getEntries();
//
//			if (!entries.isEmpty()) {
//				Set entriesIdSet = new HashSet();
//				for (int i = 0, size = entries.size(); i < size; i++) {
//					entriesIdSet.add(entries.get(i).getId().toString());
//				}
//				String entriesIds = FDCUtils.buildBillIds(entriesIdSet);
//				resetContractSrcPro(ctx, entriesIds, currentEntries);
//				resetInviteSrcPro(ctx, entriesIds, currentEntries);
//			}
//		}
		for(int i=0;i<info.getEntries().size();i++){
			ProgrammingContractInfo entry=info.getEntries().get(i);
			String srcId=entry.getSrcId();
			
			if(srcId!=null){
				StringBuffer idsql = new StringBuffer();
				if(!entry.getId().toString().equals(srcId)){
					String newId=BOSUuid.create(entry.getBOSType()).toString();
					idsql.append("update T_CON_ProgrammingContract set fid ='"+newId+"' where fid = '").append(srcId).append("'");
					fdcSB.addBatch(idsql.toString());
					
					idsql = new StringBuffer();
					idsql.append("update T_CON_ProgrammingContract set fparentid ='"+newId+"' where fparentid = '").append(srcId).append("'");
					fdcSB.addBatch(idsql.toString());
					
					idsql = new StringBuffer();
					idsql.append("update T_CON_ProgrammingContracCost set fcontractID ='"+newId+"' where fcontractID = '").append(srcId).append("'");
					fdcSB.addBatch(idsql.toString());
					
					idsql = new StringBuffer();
					idsql.append("update T_CON_ProgContEconomy set fcontractID ='"+newId+"' where fcontractID = '").append(srcId).append("'");
					fdcSB.addBatch(idsql.toString());
					
					idsql = new StringBuffer();
					idsql.append("update T_CON_ProgrammingContracCost set fcontractID ='"+srcId+"' where fcontractID = '").append(entry.getId().toString()).append("'");
					fdcSB.addBatch(idsql.toString());
					
					idsql = new StringBuffer();
					idsql.append("update T_CON_ProgContEconomy set fcontractID ='"+srcId+"' where fcontractID = '").append(entry.getId().toString()).append("'");
					fdcSB.addBatch(idsql.toString());
					
					idsql = new StringBuffer();
					idsql.append("update T_CON_ProgrammingContract set fparentid ='"+srcId+"' where fparentid = '").append(entry.getId().toString()).append("'");
					fdcSB.addBatch(idsql.toString());
				}
				
				ContractBillCollection col=ContractBillFactory.getLocalInstance(ctx).getContractBillCollection("select id from where programmingContract.id='"+srcId+"' and contractPropert!='SUPPLY' and state='"+FDCBillStateEnum.AUDITTED_VALUE+"'");
				
				ContractWithoutTextCollection wcol=ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextCollection("select id from where programmingContract.id='"+srcId+"' and state='"+FDCBillStateEnum.AUDITTED_VALUE+"'");
				
				if(col.size()>0){
					if(!entry.getId().toString().equals(srcId)){
						idsql = new StringBuffer();
						idsql.append("update T_CON_ProgrammingContract set fid ='"+srcId+"' where fid = '").append(entry.getId().toString()).append("'");
						fdcSB.addBatch(idsql.toString());
					}
					ContractBillFactory.getLocalInstance(ctx).synReUpdateProgramming(col.get(0).getId().toString(), entry);
					entry.setSrcId(srcId);
					ContractBillFactory.getLocalInstance(ctx).synUpdateProgramming(col.get(0).getId().toString(), entry);
				}
				if(wcol.size()>0){
					if(!entry.getId().toString().equals(srcId)){
						idsql = new StringBuffer();
						idsql.append("update T_CON_ProgrammingContract set fid ='"+srcId+"' where fid = '").append(entry.getId().toString()).append("'");
						fdcSB.addBatch(idsql.toString());
					}
					ContractWithoutTextFactory.getLocalInstance(ctx).synReUpdateProgramming(wcol.get(0).getId().toString(), entry);
					entry.setSrcId(srcId);
					ContractWithoutTextFactory.getLocalInstance(ctx).synUpdateProgramming(wcol.get(0).getId().toString(), entry);
				}
				if(col.size()==0&&wcol.size()==0&&!entry.getId().toString().equals(srcId)){
					idsql = new StringBuffer();
					idsql.append("update T_CON_ProgrammingContract set fid ='"+srcId+"',fisCiting=0,fisWTCiting=0,fbudgetAmount=famount where fid = '").append(entry.getId().toString()).append("'");
					fdcSB.addBatch(idsql.toString());
				}
			}
		}
		fdcSB.executeBatch();
	}

	/**
	 * 审批后合同引用的框架合约需要更新为修订后的新版本
	 * @param ctx
	 * @param billId
	 * @param entries
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	private void resetContractSrcPro(Context ctx, String entriesIds, ProgrammingContractCollection currentEntries)
	throws BOSException, EASBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("select id, srcproid where srcProID in ").append(entriesIds);
		IContractBill contractService = ContractBillFactory.getLocalInstance(ctx);
		ContractBillCollection contractCollection = contractService.getContractBillCollection(sql.toString());
		if (contractCollection.isEmpty()) {
			return;
		}
		
		for (int i = 0; i < currentEntries.size(); i++) {
			ProgrammingContractInfo currentEntry = currentEntries.get(i);
			for (int j = 0; j < contractCollection.size(); j++) {
				ContractBillInfo contract = contractCollection.get(j);
				if (contract.getSrcProID().equals(currentEntry.getSrcId())) {
					contract.setSrcProID(currentEntry.getId().toString());
					contractService.updatePartial(contract, getresetSic());
				}
			}
		}
	}
	
	/**
	 * 审批后招标立项引用的框架合约需要更新为修订后的新版本
	 * @param ctx
	 * @param billId
	 * @param entries
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	private void resetInviteSrcPro(Context ctx, String entriesIds, ProgrammingContractCollection currentEntries)
	throws BOSException, EASBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("select id, srcproid where srcProID in ").append(entriesIds);
		IInviteProject inviteService = InviteProjectFactory.getLocalInstance(ctx);
		InviteProjectCollection inviteCollection = inviteService.getInviteProjectCollection(sql.toString());
		if (inviteCollection.isEmpty()) {
			return;
		}
		
		for (int i = 0; i < currentEntries.size(); i++) {
			ProgrammingContractInfo currentEntrie = currentEntries.get(i);
			for (int j = 0; j < inviteCollection.size(); j++) {
				InviteProjectInfo invite = inviteCollection.get(j);
				if (invite.getSrcProID().equals(currentEntrie.getSrcId())) {
					invite.setSrcProID(currentEntrie.getId().toString());
					inviteService.updatePartial(invite, getresetSic());
				}
			}
		}
	}
	
	private SelectorItemCollection getresetSic() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("srcProID");
		return sic;
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		String sql_Query = "select fid from T_CON_ProgrammingContract where FProgrammingID = '"+billId.toString()+"' and FIsCiting = 1";
		FDCSQLBuilder checkSQL = new FDCSQLBuilder(ctx);
		checkSQL.appendSql(sql_Query);
		IRowSet rs = checkSQL.executeQuery();
		try {
			if(rs.next()){
				throw new EASBizException(new NumericExceptionSubItem("1", "存在已经被引用的框架合约，不允许反审批！"));
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		
		super._unAudit(ctx, billId);
		ProgrammingInfo info = getProgrammingInfo(ctx, new ObjectUuidPK(billId), getSelectors());
		String versionGroup = info.getVersionGroup();
		BigDecimal version = info.getVersion();
		version = version.subtract(new BigDecimal("1"));
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update t_con_programming set FIsLatest = 0 where fid = '").append(billId.toString()).append("'");
		fdcSB.addBatch(sql.toString());
		
		sql.setLength(0);
		sql.append("update t_con_programming set FIsLatest = 1 where FVersionGroup = '");
		sql.append(versionGroup).append("' ");
		sql.append("and FVersion = ").append(version.toString());
		fdcSB.addBatch(sql.toString());
		fdcSB.executeBatch();
	}
	protected void _isReferenced(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ProgrammingInfo info=this.getProgrammingInfo(ctx, pk);
		for(int i=0;i<info.getEntries().size();i++){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id", info.getEntries().get(i).getId().toString()));
			if(ContractBillFactory.getLocalInstance(ctx).exists(filter)){
				throw new EASBizException(new NumericExceptionSubItem("100","已经被合同引用，不能进行删除操作！"));
			}
			if(ContractEstimateChangeBillFactory.getLocalInstance(ctx).exists(filter)){
				throw new EASBizException(new NumericExceptionSubItem("100","已经被预估合同变动引用，不能进行删除操作！"));
			}
		}
		
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		this._isReferenced(ctx, pk);
		super._delete(ctx, pk);
	}
	protected boolean _isAddPCPass(Context ctx, BOSUuid billId, BigDecimal amount) throws BOSException, EASBizException {
		if(amount==null) return false;
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("entries.amount");
		sel.add("entries.srcId");
		ProgrammingInfo info=this.getProgrammingInfo(ctx, new ObjectUuidPK(billId),sel);
		for(int i=0;i<info.getEntries().size();i++){
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id",info.getEntries().get(i).getId().toString()));
            if(!ProgrammingContractFactory.getLocalInstance(ctx).exists(filter)&&info.getEntries().get(i).getAmount()!=null){
            	if(info.getEntries().get(i).getSrcId()==null&&info.getEntries().get(i).getAmount().compareTo(amount)>=0){
    				return true;
    			}
            }
		}
		return false;
	}
	protected boolean _isEditPCPass(Context ctx, BOSUuid billId, BigDecimal amount) throws BOSException, EASBizException {
		if(amount==null) return false;
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("entries.amount");
		sel.add("entries.srcId");
		ProgrammingInfo info=this.getProgrammingInfo(ctx, new ObjectUuidPK(billId),sel);
		for(int i=0;i<info.getEntries().size();i++){
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id",info.getEntries().get(i).getId().toString()));
            if(!ProgrammingContractFactory.getLocalInstance(ctx).exists(filter)&&info.getEntries().get(i).getAmount()!=null){
            	String srcId=info.getEntries().get(i).getSrcId();
            	if(srcId==null) continue;
    			ProgrammingContractCollection col=ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractCollection("select amount from where id='"+srcId+"'");
    			if(col.size()>0&&col.get(0).getAmount()!=null){
    				if(col.get(0).getAmount().subtract(info.getEntries().get(i).getAmount()).abs().compareTo(amount)>=0){
    					return true;
    				}
    			}
            }
		}
		return false;
	}
	
}