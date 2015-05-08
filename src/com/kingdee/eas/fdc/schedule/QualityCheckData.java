package com.kingdee.eas.fdc.schedule;

import java.util.ArrayList;
import java.util.Set;

import com.kingdee.eas.fdc.pm.QualityCheckPointInfo;

public class QualityCheckData {
	private QualityCheckPointInfo qtyCheckInfo;
	private ArrayList qtyCheckEntrys;
	public QualityCheckPointInfo getQtyCheckInfo() {
		return qtyCheckInfo;
	}
	public void setQtyCheckInfo(QualityCheckPointInfo qtyCheckInfo) {
		this.qtyCheckInfo = qtyCheckInfo;
	}
	public ArrayList getQtyCheckEntrys() {
		return qtyCheckEntrys;
	}
	public void setQtyCheckEntrys(ArrayList qtyCheckEntrys) {
		this.qtyCheckEntrys = qtyCheckEntrys;
	}
}
