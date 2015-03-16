package com.kingdee.eas.fdc.tenancy.client;

import com.kingdee.eas.fdc.propertymgmt.ChargeTimeLimitUnit;
import com.kingdee.eas.fdc.propertymgmt.LateFeeUnit;
import com.kingdee.eas.fdc.propertymgmt.RoomChargeInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomChargeEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;

public class TenancyPropertyHelper {
	
	public static TenancyRoomChargeEntryInfo roomCharge2TenRoomCharge(RoomChargeInfo roomCharge, TenancyRoomEntryInfo tenRoom){
		if(roomCharge == null  ||  tenRoom == null){
			return null;
		}
		
		TenancyRoomChargeEntryInfo tenRoomCharge = new TenancyRoomChargeEntryInfo();
		tenRoomCharge.setTenancyRoom(tenRoom);
		
//		tenRoomCharge.setChargeItem(roomCharge.getChargeItem());
		tenRoomCharge.setChargeStandard(roomCharge.getChargeStandard());
		tenRoomCharge.setFeeQuantity(roomCharge.getFeeQuantity());
		tenRoomCharge.setUseStandard(roomCharge.isUseStandard());
		tenRoomCharge.setUnitPrice(roomCharge.getUnitPrice());
		
		tenRoomCharge.setPricePeriod(roomCharge.getPricePeriod());
		tenRoomCharge.setPriceFactor(roomCharge.getPriceFactor());
		tenRoomCharge.setChargePeriod(roomCharge.getChargePeriod());
		tenRoomCharge.setChargePeriodTp(roomCharge.getChargePeriodTp());
		tenRoomCharge.setChargeDateType(roomCharge.getChargeDateType());
		
		tenRoomCharge.setChargeTimeLimit(roomCharge.getChargeTimeLimit());
		tenRoomCharge.setChargeDateUnit(ChargeTimeLimitUnit.getEnum(roomCharge.getChargeDateUnit().getValue()));
		tenRoomCharge.setLateFeeRate(roomCharge.getLateFeeRate());
		tenRoomCharge.setLateFeeUnit(LateFeeUnit.getEnum(roomCharge.getLateFeeUnit().getValue()));
		
		return tenRoomCharge;
	}
	
	
	
	
}
