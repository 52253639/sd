package com.kingdee.eas.fdc.finance;

/**
 * 描述：成本取数工具类 
 */
public class FDCCostRptUtils {
	public static Object[][][] getCostCloseItem(boolean isCost,String key){
		Object[][] item =null;
		if (isCost) {
			item = new Object[][] { { "合同", "合同全部完全拆分", "con" },
					{ "合同变更", "变更全部完全拆分", "change" },
					{ "结算", "结算全部完全拆分", "settle" },
					{ "工程实际投入", "工程实际投入全部完全拆分", "workload" },
					{ "无文本合同", "无文本合同全部完全审批", "conWithout" } };
		} else {
			item = new Object[][] { { "付款单", "合同全部完全拆分", "conSplit" },
					{ "无文本合同付款拆分", "无文本合同全部完全拆分", "noTextSplit" },
					{ "待处理合同", "事项处理完毕", "invalidCon" },
					{ "待处理无文本合同", "事项处理完毕", "invalidWithoutCon" } };
		}
		return null;
	}
}
