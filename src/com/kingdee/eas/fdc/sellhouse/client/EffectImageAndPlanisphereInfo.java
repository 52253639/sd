package com.kingdee.eas.fdc.sellhouse.client;

import java.io.Serializable;

import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.EffectImageInfo;
import com.kingdee.eas.fdc.sellhouse.PlanisphereInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;

public class EffectImageAndPlanisphereInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8049933163490822934L;
	
	private EffectImageInfo effectImage = null;
	private PlanisphereInfo planisphere = null;
	private String name = "";
	private FullOrgUnitInfo orgUnit = null;
	private SellProjectInfo sellProject = null;
	private BuildingInfo building = null;
	
	public SellProjectInfo getSellProject() {
		return sellProject;
	}

	public void setSellProject(SellProjectInfo sellProject) {
		this.sellProject = sellProject;
	}

	public BuildingInfo getBuilding() {
		return building;
	}

	public void setBuilding(BuildingInfo building) {
		this.building = building;
	}

	public void setEffectImage(EffectImageInfo effectImage) {
		this.effectImage = effectImage;
	}
	
	public void setPlanisphere(PlanisphereInfo planisphere) {
		this.planisphere = planisphere;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setOrgUnit(FullOrgUnitInfo orgUnit) {
		this.orgUnit = orgUnit;
	}
	
	public EffectImageInfo getEffectImageInfo() {
		return this.effectImage;
	}
	
	public PlanisphereInfo getPlanisphereInfo() {
		return this.planisphere;
	}
	
	public String getName() {
		return this.name;
	}
	
	public FullOrgUnitInfo getOrgUnit() {
		return this.orgUnit;
	}

}

