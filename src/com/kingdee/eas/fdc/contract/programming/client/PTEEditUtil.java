package com.kingdee.eas.fdc.contract.programming.client;

import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo;

public class PTEEditUtil {

	/**
	 * 找出框架模版中某合约最大树长
	 * 
	 * @param pteCollection
	 * @param headNumber
	 * @param maxLevel
	 * @return 
	 */
	public static int getMaxLevel(ProgrammingTemplateEntireCollection pteCollection, String headNumber, int maxLevel) {
		for (int j = 0; j < pteCollection.size(); j++) {
			ProgrammingTemplateEntireInfo forPTEInfo = pteCollection.get(j);
			String forLN = forPTEInfo.getLongNumber();
			if (!FDCHelper.isEmpty(forLN)) {
				String[] splitLN = forLN.split("\\.");
				if (headNumber.equals(splitLN[0])) {
					if (splitLN.length > maxLevel) {
						maxLevel = splitLN.length;
					}
				}
			} else {
				maxLevel = 1;
			}
		}
		return maxLevel;
	}

	/**
	 * 更新不同层次框架合约中的长编码
	 * 
	 * @param pteCollection
	 * @param longNumber
	 * @param headNumber
	 * @param currentLevel
	 * @param maxLevel
	 */
	public static void updateLongNumber(ProgrammingTemplateEntireCollection pteCollection, String longNumber, String headNumber,
			int currentLevel, int maxLevel) {
		String[] currentNumberArry = longNumber.split("\\.");
		String endNumber = currentNumberArry[currentLevel - 1];
		StringBuffer sb = null;
		for (int i = 0; i < pteCollection.size(); i++) {
			ProgrammingTemplateEntireInfo forPTEInfo = pteCollection.get(i);
			String forLN = forPTEInfo.getLongNumber();
			int forLevel = forPTEInfo.getLevel();
			if (!FDCHelper.isEmpty(forLN)) {
				String[] splitLN = forLN.split("\\.");
				if (headNumber.equals(splitLN[0]) && forLevel > currentLevel) {
					String[] split = forLN.split("\\.");
					split[currentLevel - 1] = endNumber;
					sb = new StringBuffer();
					for (int j = 0; j < split.length; j++) {
						if (j == 0) {
							sb.append(split[j]);
						} else {
							sb.append(".");
							sb.append(split[j]);
						}
					}
					forPTEInfo.setLongNumber(sb.toString());
					pteCollection.add(forPTEInfo);
				}
			}
		}
	}
}
