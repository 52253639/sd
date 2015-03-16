package com.kingdee.eas.fdc.invite.client;

import java.util.HashMap;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fm.common.FMIsqlFacadeFactory;
import com.kingdee.eas.fm.common.IFMIsqlFacade;
import com.kingdee.jdbc.rowset.IRowSet;


public class SupplierQualifyDataProvider extends  InvitePrintDataProvider {

	
	public SupplierQualifyDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}

	public IRowSet execute(BOSQueryDataSource ds) {
		//如果是供应商预审资格的分录时，则查询出该分录的供应商信息
		if ("SUPPLIERQUALIFYENTRY".equals(ds.getID().toUpperCase())) {
			return getFDCSupplierInfo();
		} else if ("AUDITINFO1".equals(ds.getID().toUpperCase())) {
			return super.getAuditInfoRowSet(ds);
		} else {
			return super.execute(ds);
		}
		 
	}

	/**
	 * 获取分录分录上供应商的信息
	 * @param id
	 * @return
	 */
	public IRowSet getFDCSupplierInfo(){
		boolean isFromFDCSupplier=false;
		HashMap param=new HashMap();
		try {
			param = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}	
		// 是否启用参数：招标立项选择供应商必须来源于房地产供应商管理生成的标准供应商
		if(param.get(FDCConstants.FDC_PARAM_INVITE_SUPPLIER_FROM_FDCSUPPLIER)!=null){
			isFromFDCSupplier = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_INVITE_SUPPLIER_FROM_FDCSUPPLIER).toString()).booleanValue();
		}
		
		StringBuffer sql=new StringBuffer();
		if(isFromFDCSupplier){
			sql.append("select entry.FSeq as seq,entry.FFDCSupplierID as supplierid,"
					+ " supplier.FNumber as suppliernumber,supplier.FName_l2 as suppliername,");
			sql.append(" supplierLevel.fname_l2 as suppliergrade,entry.fremark  as remark,entry.FDescription as description ");
			sql.append(" from T_INV_SupplierQualifyEntry entry ");
			sql.append(" left outer join T_FDC_FDCSupplier supplier on entry.FFDCSupplierID=supplier.fid ");
			sql.append(" left outer join T_FDC_SupplierLevel  supplierLevel on supplier.FEstimateLevelID=supplierLevel.fid ");
			sql.append(" where entry.FParentID=? order by seq");
		}else{
			sql.append("select entry.FSeq as seq,entry.FFDCSupplierID as supplierid,supplier.FNumber as suppliernumber,supplier.FName_l2 as suppliername,")
			.append(" supplierPurchaseInfo.fgrade as suppliergrade,entry.fremark  as remark,FDescription as description ")
			.append(" from T_INV_SupplierQualifyEntry entry left outer join T_BD_Supplier supplier on entry.FSupplierID=supplier.fid ")
			.append(" left outer join T_BD_SupplierPurchaseInfo  supplierPurchaseInfo on supplier.fid=supplierPurchaseInfo.FSupplierID ")
			.append("  and supplierPurchaseInfo.FPurchaseOrgID=? where entry.FParentID=?  order by seq");
		}
		try {
			IFMIsqlFacade facade = FMIsqlFacadeFactory.getRemoteInstance();
			if(isFromFDCSupplier){
				return facade.executeQuery(sql.toString(), new Object[]{billId});
			}else{
				PurchaseOrgUnitInfo  boid=SysContext.getSysContext().getCurrentPurchaseUnit();
				IRowSet i =  facade.executeQuery(sql.toString(), new Object[]{boid==null? null:boid.getId().toString(),billId});
				return i;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
