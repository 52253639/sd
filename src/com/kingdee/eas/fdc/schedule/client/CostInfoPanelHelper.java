package com.kingdee.eas.fdc.schedule.client;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.schedule.TaskCostInfoInfo;

/**
 * 
 * @author guangxin_niu
 *
 */

public class CostInfoPanelHelper {
	private final TaskExtProPanelHelper taskPropHelper;
	private final KDFormattedTextField txtAimCost;
	private final KDFormattedTextField txtActCost;
	private final KDFormattedTextField txtDeviation;
	private final KDTextArea txtCostMemo;
	private TaskCostInfoInfo costInfo;
	public CostInfoPanelHelper(TaskExtProPanelHelper helper) {
		this.taskPropHelper = helper;
		txtAimCost = helper.getFDCSCHTaskPropUI().txtAimCost;
		txtActCost = helper.getFDCSCHTaskPropUI().txtActCost;
		txtDeviation = helper.getFDCSCHTaskPropUI().txtDeviation;
		txtCostMemo = helper.getFDCSCHTaskPropUI().txtCostMemo;
	}
	public void load(){
		costInfo = (TaskCostInfoInfo) taskPropHelper.getExtProperties().get("costInfo");
		if( taskPropHelper.getExtProperties().get("costInfo") == null){
			costInfo = new TaskCostInfoInfo();
		}
		txtAimCost.setValue(costInfo.getAimCost());
		txtActCost.setValue(costInfo.getActCost());
		txtDeviation.setValue(costInfo.getCostDeviation());
		txtCostMemo.setText(costInfo.getCostMemo());
		
		txtAimCost.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		txtActCost.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		txtDeviation.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		txtAimCost.setMaximumValue(FDCHelper.MAX_VALUE);
		txtAimCost.setMinimumValue(FDCHelper.MIN_VALUE);
		txtActCost.setMaximumValue(FDCHelper.MAX_VALUE);
		txtActCost.setMinimumValue(FDCHelper.MIN_VALUE);
		txtDeviation.setMaximumValue(FDCHelper.MAX_VALUE);
		txtDeviation.setMinimumValue(FDCHelper.MIN_VALUE);
		FDCClientHelper.formatKDFormattedTextField(new KDFormattedTextField[]{txtActCost,txtAimCost});
		txtCostMemo.setMaxLength(2000);
		txtActCost.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				if(eventObj.getNewValue() != null && txtAimCost.getBigDecimalValue() != null){
					txtDeviation.setValue(FDCHelper.subtract(
							txtAimCost.getBigDecimalValue(), txtActCost.getBigDecimalValue()));
				}
			}
		});
	}
	
	public Map store(){
		Map map = new HashMap();
		costInfo.setAimCost(txtAimCost.getBigDecimalValue());
		costInfo.setActCost(txtActCost.getBigDecimalValue());
		costInfo.setCostMemo(txtCostMemo.getStringValue());
		costInfo.setCostDeviation(txtDeviation.getBigDecimalValue());
		map.put("costInfo", costInfo);
		return map;
	}
	
	public void setEditStatus() {
		txtAimCost.setEnabled(true);
		txtActCost.setEnabled(false);
		txtDeviation.setEnabled(false);
		txtCostMemo.setEnabled(true);
	}
	public void setExecutingStatus() {
		txtAimCost.setEnabled(false);
		txtActCost.setEnabled(true);
		txtDeviation.setEnabled(false);
		txtCostMemo.setEnabled(false);
	}
	public void setViewStatus() {
		txtAimCost.setEnabled(false);
		txtActCost.setEnabled(false);
		txtDeviation.setEnabled(false);
		txtCostMemo.setEnabled(false);
	}
}
