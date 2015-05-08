package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class WBSCodeRuleInfo extends AbstractWBSCodeRuleInfo implements
		Serializable {
	public WBSCodeRuleInfo() {
		super();
	}

	protected WBSCodeRuleInfo(String pkField) {
		super(pkField);
	}
	/**
	 * 默认零级编码规则ID
	 */
	public static final String DEFAULT_ZERO_CODERULE = "qwms/60QSfGkP5UGsP1giQW60Bs=";
	
	/**
	 * 默认编码规则ID
	 */
	public static final String DEFAULT_CODERULE = "iBSsZosBQTunJN5XjwzoagW60Bs=";
	
}