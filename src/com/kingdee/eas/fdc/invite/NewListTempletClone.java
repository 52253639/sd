package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.util.BOSUuid;

public class NewListTempletClone {

	/**
	 * 复制NewListTempletInfo及其的page,column,entry,value的对象
	 *
	 * @param templetInfo
	 * @return
	 */
	public static NewListTempletInfo fullClone(NewListTempletInfo templetInfo) {
		NewListTempletInfo templetClone = (NewListTempletInfo) templetInfo
				.clone();
		NewListTempletPageCollection pages = templetInfo.getPages();
		NewListTempletPageCollection pagesColne = (NewListTempletPageCollection) pages
				.clone();
		for (int j = 0, pageCount = pagesColne.size(); j < pageCount; j++) {
			NewListTempletPageInfo cpPageInfo = pagesColne.get(j);
			cpPageInfo.setId(BOSUuid.create(cpPageInfo.getBOSType()));

			NewListTempletPageInfo pageInfo = pages.get(j);
			NewListTempletColumnCollection cpCols = (NewListTempletColumnCollection) pageInfo
					.getTempletColumns().clone();
			for (int m = 0, columnCount = cpCols.size(); m < columnCount; m++) {
				NewListTempletColumnInfo cpColInfo = cpCols.get(m);
				cpColInfo.put("srcId", cpColInfo.getId()); // 设置valInfo中的col
				cpColInfo.setId(BOSUuid.create(cpColInfo.getBOSType()));
				cpColInfo.setPage(cpPageInfo);
			}
			cpPageInfo.getTempletColumns().clear();
			cpPageInfo.getTempletColumns().addCollection(cpCols);

			NewListTempletEntryCollection cpEntrys = (NewListTempletEntryCollection) pageInfo
					.getEntrys().clone();
			for (int k = 0, entrysCount = cpEntrys.size(); k < entrysCount; k++) {
				NewListTempletEntryInfo cpEntryInfo = cpEntrys.get(k);
				cpEntryInfo.setId(BOSUuid.create(cpEntryInfo.getBOSType()));
				cpEntryInfo.setHead(cpPageInfo);

				NewListTempletValueCollection cpValues = (NewListTempletValueCollection) cpEntryInfo
						.getValues().clone();
				for (int n = 0, valueCount = cpValues.size(); n < valueCount; n++) {
					NewListTempletValueInfo cpValInfo = cpValues.get(n);
					cpValInfo.setId(BOSUuid.create(cpValInfo.getBOSType()));

					// set the copied colInfo
					BOSUuid srcColId = cpValInfo.getColumn().getId();
					for (int cn = 0; cn < cpCols.size(); cn++) {
						NewListTempletColumnInfo cpColInfo = cpCols.get(cn);
						if (srcColId.equals(cpColInfo.get("srcId"))) {
							cpValInfo.setColumn(cpColInfo);
						}
					}
					cpValInfo.setEntry(cpEntryInfo);
				}
				cpEntryInfo.getValues().clear();
				cpEntryInfo.getValues().addCollection(cpValues);
			}
			cpPageInfo.getEntrys().clear();
			cpPageInfo.getEntrys().addCollection(cpEntrys);
		}
		templetClone.getPages().clear();
		templetClone.getPages().addCollection(pagesColne);

		templetClone.setId(BOSUuid.create(templetClone.getBOSType()));
		return templetClone;
	}
}
