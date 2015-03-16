package com.kingdee.eas.fdc.invite.client;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.eas.fdc.invite.AppraiseTemplateEntryInfo;
import com.kingdee.eas.fdc.invite.AppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

public class ViewExpertAppraisePrintDataProvider implements BOSQueryDelegate {
	private Map supplierScores;
	private SupplierQualifyInfo supplierQualify;
	private AppraiseTemplateInfo template;
	private String[] des;
	
	public ViewExpertAppraisePrintDataProvider(Map supplierScores,SupplierQualifyInfo supplierQualify,
			AppraiseTemplateInfo template,String[] des) {
		this.supplierScores=supplierScores;
		this.supplierQualify=supplierQualify;
		this.template=template;
		this.des=des;
	}

	public IRowSet execute(BOSQueryDataSource ds) {
		String[] colName=new String[]{"supplierName","glTypeName","glName","weight","sorce","description"};
 		try {
 			IRowSet rowset = null;
			DynamicRowSet rs=new DynamicRowSet(colName.length);
			for(int i=0,n=colName.length;i<n;i++){
				ColInfo colInfo=new ColInfo();
				colInfo.colType = Types.VARCHAR;
				colInfo.columnName = colName[i].toString();
				rs.setColInfo(i+1, colInfo);
			}
			rowset=rs;
			if(supplierQualify!=null&&supplierQualify.getEntry()!=null){
				for(int i=0;i<supplierQualify.getEntry().size();i++){
					if(template!=null&&template.getTemplateEntry()!=null){
						int j=0;
						for(Iterator it=template.getTemplateEntry().iterator();it.hasNext();){
							AppraiseTemplateEntryInfo info = (AppraiseTemplateEntryInfo)it.next();
							String key = info.getGuideLine().getId().toString()	+ "_"+ supplierQualify.getEntry().get(i)
							.getSupplier().getName();
							Object value;
							if (supplierScores.get(key) == null) {
								continue;
							}
							rs.beforeFirst();
							rowset.moveToInsertRow();
							value= supplierQualify.getEntry().get(i).getSupplier().getName();
							rowset.updateString(colName[0],value==null? "":value.toString());
							rs.updateString(colName[1], info.getGuideLine().getGuideLineType().getName());
							rs.updateString(colName[2], info.getGuideLine().getName());
							rs.updateBigDecimal(colName[3], info.getWeight());
							value=supplierScores.get(key);
							rs.updateObject(colName[4], value==null? "":value.toString());
							rs.updateString(colName[5], des!=null&&des.length>j? des[j]:"");
							rowset.beforeFirst();
							rs.insertRow();
							j++;
						}
					}
				}
			}
			return rowset;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
