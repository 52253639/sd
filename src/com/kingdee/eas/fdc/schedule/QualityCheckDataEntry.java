package com.kingdee.eas.fdc.schedule;

import com.kingdee.eas.fdc.pm.QualityCheckPointEntryInfo;
import com.kingdee.eas.fdc.pm.QualityTaskAssignInfo;


public class QualityCheckDataEntry {
	private QualityCheckPointEntryInfo qtyCheckEntryInfo;
	private QualityTaskAssignInfo qtyAssignInfo;
	public QualityCheckPointEntryInfo getQtyCheckEntryInfo() {
		return qtyCheckEntryInfo;
	}
	public void setQtyCheckEntryInfo(QualityCheckPointEntryInfo qtyCheckEntryInfo) {
		this.qtyCheckEntryInfo = qtyCheckEntryInfo;
	}
	public QualityTaskAssignInfo getQtyAssignInfo() {
		return qtyAssignInfo;
	}
	public void setQtyAssignInfo(QualityTaskAssignInfo qtyAssignInfo) {
		this.qtyAssignInfo = qtyAssignInfo;
	}
}
