package com.kingdee.eas.fdc.invite.client;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.ListingItemInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyCollection;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyFactory;
import com.kingdee.eas.util.SysUtil;

public class InviteClientHelper {
	public static void initInviteProjectF7(KDBizPromptBox prmtInviteProject){
		FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",orgUnit.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE,CompareType.EQUALS));
		
		view.setFilter(filter);
		SelectorItemCollection selectors = new SelectorItemCollection();
    	selectors.add("*");
    	selectors.add(new SelectorItemInfo("inviteType.id"));
    	selectors.add(new SelectorItemInfo("inviteType.number"));
    	selectors.add(new SelectorItemInfo("inviteType.name"));
    	selectors.add(new SelectorItemInfo("project.id"));
    	selectors.add(new SelectorItemInfo("project.number"));
    	selectors.add(new SelectorItemInfo("project.name"));
    	prmtInviteProject.setSelectorCollection(selectors);
		prmtInviteProject.setEntityViewInfo(view);
		prmtInviteProject.getQueryAgent().resetRuntimeEntityView();
		prmtInviteProject.setRefresh(true);
	}

	/**
	 * 
	 * 描述：从第三个页签起，校验每行数据，确保同父级下子目不能重复<p>
	 * <b>仅供</b> 招标清单编制界面（NewListingEditUI）和 招标清单模板编辑界面（NewListTempletEditUI）调用
	 * @Author：owen_wen
	 * @CreateTime：2011-10-24
	 */
	public static void verifyDuplicationItem(KDTable table, String ItemName) {
		for (int j = 1; j < table.getRowCount(); j++) {
			IRow rowJ = table.getRow(j);
			for (int k = j + 1; k < table.getRowCount(); k++) {
				IRow rowK = table.getRow(k);
				if (rowJ.getTreeLevel() == rowK.getTreeLevel() && rowJ.getCell(ItemName).getValue().toString().equals(rowK.getCell(ItemName).getValue().toString())) {
					// 倒着从下往上找rowJ和rowK的父级，判断父级是否相同
					String rowJ_parent = "Parent";
					String rowK_parent = "Parent";
					for (int m = k - 1; m >= 1; m--) {
						IRow rowM = table.getRow(m);
						if ("Parent".equals(rowK_parent) && rowM.getTreeLevel() == rowK.getTreeLevel() - 1) {
							if (rowM.getCell(ItemName).getValue() instanceof ListingItemInfo) {
								ListingItemInfo item = (ListingItemInfo) rowM.getCell(ItemName).getValue();
								rowK_parent = item.getName();
							} else {
								rowK_parent = rowM.getCell(ItemName).getValue().toString();
							}
						}
						if (m < j && rowM.getTreeLevel() == rowJ.getTreeLevel() - 1) {
							if (rowM.getCell(ItemName).getValue() instanceof ListingItemInfo) {
								ListingItemInfo item = (ListingItemInfo) rowM.getCell(ItemName).getValue();
								rowJ_parent = item.getName();
							} else {
								rowJ_parent = rowM.getCell(ItemName).getValue().toString();
							}
							break;
						}
					}
					if (rowJ_parent.equals(rowK_parent)) {
						FDCMsgBox.showInfo("同父级下第" + (j + 1) + "行与第" + (k + 1) + "行子目名称重复，请修改后再保存或提交。");
						SysUtil.abort();
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * 描述：根据招标立项ID获取已审批的供应商资格预审的供应商ID
	 * @param inviteProjectId 招标立项ID
	 * @return 已审批的供应商资格预审的供应商ID集
	 * @throws BOSException
	 * @Author：owen_wen
	 * @CreateTime：2011-10-26
	 */
	public static Set getQualifiedSupplierIDs(String inviteProjectId) throws BOSException {
		Set supIds = new HashSet();

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("entry.supplier.id"));

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("inviteProject", inviteProjectId));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));

		view.setFilter(filter);
		SupplierQualifyCollection cols = SupplierQualifyFactory.getRemoteInstance().getSupplierQualifyCollection(view);

		if (cols.size() == 1) {
			for (Iterator colsIter = cols.get(0).getEntry().iterator(); colsIter.hasNext();) {
				SupplierQualifyEntryInfo sqEntryInfo = (SupplierQualifyEntryInfo) colsIter.next();
				if (sqEntryInfo.getSupplier() != null) {
					supIds.add(sqEntryInfo.getSupplier().getId().toString());
				}
			}
		}

		return supIds;
	}
}
