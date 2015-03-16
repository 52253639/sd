package com.kingdee.eas.fdc.invite.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

import com.kingdee.eas.framework.CoreBaseInfo;

public class CoreBaseCollectionComparator implements Comparator, Serializable {

	/**
	 * @Author 肖飙彪
	 * @date 2007-5-29
	 * @param args
	 */
	private String param = "";// 所要比较的属性

	private boolean sortType = true;// 默认升序

	public CoreBaseCollectionComparator(String param) {
		this.param = param;
		this.sortType = true;
	}

	public CoreBaseCollectionComparator(String param, boolean sortType) {
		this.param = param;
		this.sortType = sortType;
	}

	public int compare(CoreBaseInfo arg0, CoreBaseInfo arg1) {
		Object obj0 = arg0.get(param);
		Object obj1 = arg1.get(param);
		if (obj0 instanceof BigDecimal && obj1 instanceof BigDecimal) {
			BigDecimal decimal0 = (BigDecimal) obj0;
			BigDecimal decimal1 = (BigDecimal) obj1;
			if (sortType) {
				if (decimal0.floatValue() > decimal1.floatValue()) {
					return 1;
				} else if (decimal0.floatValue() < decimal1.floatValue()) {
					return -1;
				} else {
					return 0;
				}
			} else {
				if (decimal0.floatValue() > decimal1.floatValue()) {
					return -1;
				} else if (decimal0.floatValue() < decimal1.floatValue()) {
					return 1;
				} else {
					return 0;
				}
			}

		}
		return 0;
	}

	public int compare(Object arg0, Object arg1) {
		// TODO 自动生成方法存根
		return compare((CoreBaseInfo) arg0, (CoreBaseInfo) arg1);
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public boolean isSortType() {
		return sortType;
	}

	public void setSortType(boolean sortType) {
		this.sortType = sortType;
	}

}
