/*
 * @(#)EffectDegreePanel.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JComboBox;

import com.kingdee.bos.ctrl.swing.KDLabelContainer;

/**
 * 影响程度Panel
 * @author RD_owen_wen  date:2013-12-3
 * @version EAS6.1
 */
public class EffectDegreePanel extends javax.swing.JPanel {
	JComboBox cbEffect;

	public EffectDegreePanel() {
		cbEffect = new JComboBox(new String[] { "所有任务", "日常工作", "一般任务", "关键任务", "里程碑任务" });
		setLayout(new FlowLayout(FlowLayout.LEFT));
		KDLabelContainer contEffect = new KDLabelContainer("影响程度：");
		contEffect.setBounds(new Rectangle(270, 19));//标准尺寸
		contEffect.setBoundEditor(cbEffect);
		this.add(contEffect);
	}
	
	/**
	 * 获取影响程度下拉comboBox
	 * @return 影响程度下拉comboBox
	 * @Author：RD_owen_wen
	 * @CreateTime：2013-12-3
	 */
	public JComboBox getCBEffect() {
		return cbEffect;
	}
}
