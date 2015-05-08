package com.kingdee.eas.fdc.schedule.framework.ext;

public interface KDUndoableListener {
	boolean beforeEdit();
	boolean afterEdit();

}
