/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractRoomSplitUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomSplitUI.class);
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRooms;
    protected com.kingdee.bos.ctrl.swing.KDButton btnYes;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSerialNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApportionArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGuardenArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCarbarnArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUseArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFlatArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSplitCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSerialNumber;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtApportionArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBalconyArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtGuardenArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCarbarnArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtUseArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtFlatArea;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiSplitCount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTenancyArea;
    /**
     * output class constructor
     */
    public AbstractRoomSplitUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractRoomSplitUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.tblRooms = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnYes = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnNo = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contRoomNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSerialNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApportionArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBalconyArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGuardenArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCarbarnArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contUseArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFlatArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSplitCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtSerialNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtApportionArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtBalconyArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtGuardenArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtCarbarnArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtUseArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtFlatArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.spiSplitCount = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.txtActualBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtActualRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTenancyArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.tblRooms.setName("tblRooms");
        this.btnYes.setName("btnYes");
        this.btnNo.setName("btnNo");
        this.contRoomNumber.setName("contRoomNumber");
        this.contSerialNumber.setName("contSerialNumber");
        this.contBuildingArea.setName("contBuildingArea");
        this.contRoomArea.setName("contRoomArea");
        this.contApportionArea.setName("contApportionArea");
        this.contBalconyArea.setName("contBalconyArea");
        this.contGuardenArea.setName("contGuardenArea");
        this.contCarbarnArea.setName("contCarbarnArea");
        this.contUseArea.setName("contUseArea");
        this.contFlatArea.setName("contFlatArea");
        this.contSplitCount.setName("contSplitCount");
        this.contActualBuildingArea.setName("contActualBuildingArea");
        this.contActualRoomArea.setName("contActualRoomArea");
        this.contTenancyArea.setName("contTenancyArea");
        this.txtNumber.setName("txtNumber");
        this.txtSerialNumber.setName("txtSerialNumber");
        this.txtBuildingArea.setName("txtBuildingArea");
        this.txtRoomArea.setName("txtRoomArea");
        this.txtApportionArea.setName("txtApportionArea");
        this.txtBalconyArea.setName("txtBalconyArea");
        this.txtGuardenArea.setName("txtGuardenArea");
        this.txtCarbarnArea.setName("txtCarbarnArea");
        this.txtUseArea.setName("txtUseArea");
        this.txtFlatArea.setName("txtFlatArea");
        this.spiSplitCount.setName("spiSplitCount");
        this.txtActualBuildingArea.setName("txtActualBuildingArea");
        this.txtActualRoomArea.setName("txtActualRoomArea");
        this.txtTenancyArea.setName("txtTenancyArea");
        // CoreUI		
        this.setPreferredSize(new Dimension(680,400));
        // tblRooms
		String tblRoomsStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"serialNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"roomPropNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"tenancyArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"actualBuildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"actualRoomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"apportionArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"balconyArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"guardenArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"carbarnArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"useArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"flatArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{serialNumber}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{roomNo}</t:Cell><t:Cell>$Resource{roomPropNo}</t:Cell><t:Cell>$Resource{tenancyArea}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{actualBuildingArea}</t:Cell><t:Cell>$Resource{actualRoomArea}</t:Cell><t:Cell>$Resource{apportionArea}</t:Cell><t:Cell>$Resource{balconyArea}</t:Cell><t:Cell>$Resource{guardenArea}</t:Cell><t:Cell>$Resource{carbarnArea}</t:Cell><t:Cell>$Resource{useArea}</t:Cell><t:Cell>$Resource{flatArea}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblRooms.setFormatXml(resHelper.translateString("tblRooms",tblRoomsStrXML));
        this.tblRooms.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblRooms_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // btnYes		
        this.btnYes.setText(resHelper.getString("btnYes.text"));
        this.btnYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnYes_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnNo		
        this.btnNo.setText(resHelper.getString("btnNo.text"));
        this.btnNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnNo_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contRoomNumber		
        this.contRoomNumber.setBoundLabelText(resHelper.getString("contRoomNumber.boundLabelText"));		
        this.contRoomNumber.setBoundLabelLength(100);		
        this.contRoomNumber.setBoundLabelUnderline(true);
        // contSerialNumber		
        this.contSerialNumber.setBoundLabelText(resHelper.getString("contSerialNumber.boundLabelText"));		
        this.contSerialNumber.setBoundLabelLength(100);		
        this.contSerialNumber.setBoundLabelUnderline(true);
        // contBuildingArea		
        this.contBuildingArea.setBoundLabelText(resHelper.getString("contBuildingArea.boundLabelText"));		
        this.contBuildingArea.setBoundLabelUnderline(true);		
        this.contBuildingArea.setBoundLabelLength(100);
        // contRoomArea		
        this.contRoomArea.setBoundLabelText(resHelper.getString("contRoomArea.boundLabelText"));		
        this.contRoomArea.setBoundLabelUnderline(true);		
        this.contRoomArea.setBoundLabelLength(100);
        // contApportionArea		
        this.contApportionArea.setBoundLabelText(resHelper.getString("contApportionArea.boundLabelText"));		
        this.contApportionArea.setBoundLabelUnderline(true);		
        this.contApportionArea.setBoundLabelLength(100);
        // contBalconyArea		
        this.contBalconyArea.setBoundLabelText(resHelper.getString("contBalconyArea.boundLabelText"));		
        this.contBalconyArea.setBoundLabelUnderline(true);		
        this.contBalconyArea.setBoundLabelLength(100);
        // contGuardenArea		
        this.contGuardenArea.setBoundLabelText(resHelper.getString("contGuardenArea.boundLabelText"));		
        this.contGuardenArea.setBoundLabelUnderline(true);		
        this.contGuardenArea.setBoundLabelLength(100);
        // contCarbarnArea		
        this.contCarbarnArea.setBoundLabelText(resHelper.getString("contCarbarnArea.boundLabelText"));		
        this.contCarbarnArea.setBoundLabelUnderline(true);		
        this.contCarbarnArea.setBoundLabelLength(100);
        // contUseArea		
        this.contUseArea.setBoundLabelText(resHelper.getString("contUseArea.boundLabelText"));		
        this.contUseArea.setBoundLabelUnderline(true);		
        this.contUseArea.setBoundLabelLength(100);
        // contFlatArea		
        this.contFlatArea.setBoundLabelText(resHelper.getString("contFlatArea.boundLabelText"));		
        this.contFlatArea.setBoundLabelUnderline(true);		
        this.contFlatArea.setBoundLabelLength(100);
        // contSplitCount		
        this.contSplitCount.setBoundLabelText(resHelper.getString("contSplitCount.boundLabelText"));		
        this.contSplitCount.setBoundLabelLength(100);		
        this.contSplitCount.setBoundLabelUnderline(true);
        // contActualBuildingArea		
        this.contActualBuildingArea.setBoundLabelText(resHelper.getString("contActualBuildingArea.boundLabelText"));		
        this.contActualBuildingArea.setBoundLabelUnderline(true);		
        this.contActualBuildingArea.setBoundLabelLength(100);		
        this.contActualBuildingArea.setEnabled(false);
        // contActualRoomArea		
        this.contActualRoomArea.setBoundLabelText(resHelper.getString("contActualRoomArea.boundLabelText"));		
        this.contActualRoomArea.setBoundLabelLength(100);		
        this.contActualRoomArea.setBoundLabelUnderline(true);		
        this.contActualRoomArea.setEnabled(false);
        // contTenancyArea		
        this.contTenancyArea.setBoundLabelText(resHelper.getString("contTenancyArea.boundLabelText"));		
        this.contTenancyArea.setBoundLabelLength(100);		
        this.contTenancyArea.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setEnabled(false);
        // txtSerialNumber		
        this.txtSerialNumber.setEnabled(false);
        // txtBuildingArea		
        this.txtBuildingArea.setEnabled(false);		
        this.txtBuildingArea.setDataType(1);
        // txtRoomArea		
        this.txtRoomArea.setEnabled(false);		
        this.txtRoomArea.setDataType(1);
        // txtApportionArea		
        this.txtApportionArea.setEnabled(false);		
        this.txtApportionArea.setDataType(1);
        // txtBalconyArea		
        this.txtBalconyArea.setEnabled(false);		
        this.txtBalconyArea.setDataType(1);
        // txtGuardenArea		
        this.txtGuardenArea.setEnabled(false);		
        this.txtGuardenArea.setDataType(1);
        // txtCarbarnArea		
        this.txtCarbarnArea.setEnabled(false);		
        this.txtCarbarnArea.setDataType(1);
        // txtUseArea		
        this.txtUseArea.setEnabled(false);		
        this.txtUseArea.setDataType(1);
        // txtFlatArea		
        this.txtFlatArea.setEnabled(false);		
        this.txtFlatArea.setDataType(1);
        // spiSplitCount
        this.spiSplitCount.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    spiSplitCount_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtActualBuildingArea		
        this.txtActualBuildingArea.setEnabled(false);		
        this.txtActualBuildingArea.setDataType(1);
        // txtActualRoomArea		
        this.txtActualRoomArea.setEnabled(false);		
        this.txtActualRoomArea.setDataType(1);
        // txtTenancyArea		
        this.txtTenancyArea.setEnabled(false);		
        this.txtTenancyArea.setDataType(1);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 680, 500));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 680, 500));
        tblRooms.setBounds(new Rectangle(17, 211, 643, 242));
        this.add(tblRooms, new KDLayout.Constraints(17, 211, 643, 242, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        btnYes.setBounds(new Rectangle(477, 462, 73, 21));
        this.add(btnYes, new KDLayout.Constraints(477, 462, 73, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT));
        btnNo.setBounds(new Rectangle(578, 462, 73, 21));
        this.add(btnNo, new KDLayout.Constraints(578, 462, 73, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT));
        contRoomNumber.setBounds(new Rectangle(18, 10, 270, 19));
        this.add(contRoomNumber, new KDLayout.Constraints(18, 10, 270, 19, 0));
        contSerialNumber.setBounds(new Rectangle(367, 10, 270, 19));
        this.add(contSerialNumber, new KDLayout.Constraints(367, 10, 270, 19, 0));
        contBuildingArea.setBounds(new Rectangle(18, 38, 270, 19));
        this.add(contBuildingArea, new KDLayout.Constraints(18, 38, 270, 19, 0));
        contRoomArea.setBounds(new Rectangle(367, 38, 270, 19));
        this.add(contRoomArea, new KDLayout.Constraints(367, 38, 270, 19, 0));
        contApportionArea.setBounds(new Rectangle(18, 94, 270, 19));
        this.add(contApportionArea, new KDLayout.Constraints(18, 94, 270, 19, 0));
        contBalconyArea.setBounds(new Rectangle(367, 94, 270, 19));
        this.add(contBalconyArea, new KDLayout.Constraints(367, 94, 270, 19, 0));
        contGuardenArea.setBounds(new Rectangle(18, 122, 270, 19));
        this.add(contGuardenArea, new KDLayout.Constraints(18, 122, 270, 19, 0));
        contCarbarnArea.setBounds(new Rectangle(367, 122, 270, 19));
        this.add(contCarbarnArea, new KDLayout.Constraints(367, 122, 270, 19, 0));
        contUseArea.setBounds(new Rectangle(18, 150, 270, 19));
        this.add(contUseArea, new KDLayout.Constraints(18, 150, 270, 19, 0));
        contFlatArea.setBounds(new Rectangle(367, 150, 270, 19));
        this.add(contFlatArea, new KDLayout.Constraints(367, 150, 270, 19, 0));
        contSplitCount.setBounds(new Rectangle(18, 180, 270, 19));
        this.add(contSplitCount, new KDLayout.Constraints(18, 180, 270, 19, 0));
        contActualBuildingArea.setBounds(new Rectangle(18, 66, 270, 19));
        this.add(contActualBuildingArea, new KDLayout.Constraints(18, 66, 270, 19, 0));
        contActualRoomArea.setBounds(new Rectangle(367, 66, 270, 19));
        this.add(contActualRoomArea, new KDLayout.Constraints(367, 66, 270, 19, 0));
        contTenancyArea.setBounds(new Rectangle(367, 180, 270, 19));
        this.add(contTenancyArea, new KDLayout.Constraints(367, 180, 270, 19, 0));
        //contRoomNumber
        contRoomNumber.setBoundEditor(txtNumber);
        //contSerialNumber
        contSerialNumber.setBoundEditor(txtSerialNumber);
        //contBuildingArea
        contBuildingArea.setBoundEditor(txtBuildingArea);
        //contRoomArea
        contRoomArea.setBoundEditor(txtRoomArea);
        //contApportionArea
        contApportionArea.setBoundEditor(txtApportionArea);
        //contBalconyArea
        contBalconyArea.setBoundEditor(txtBalconyArea);
        //contGuardenArea
        contGuardenArea.setBoundEditor(txtGuardenArea);
        //contCarbarnArea
        contCarbarnArea.setBoundEditor(txtCarbarnArea);
        //contUseArea
        contUseArea.setBoundEditor(txtUseArea);
        //contFlatArea
        contFlatArea.setBoundEditor(txtFlatArea);
        //contSplitCount
        contSplitCount.setBoundEditor(spiSplitCount);
        //contActualBuildingArea
        contActualBuildingArea.setBoundEditor(txtActualBuildingArea);
        //contActualRoomArea
        contActualRoomArea.setBoundEditor(txtActualRoomArea);
        //contTenancyArea
        contTenancyArea.setBoundEditor(txtTenancyArea);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnPageSetup);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomSplitUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output tblRooms_editStopped method
     */
    protected void tblRooms_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnYes_actionPerformed method
     */
    protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnNo_actionPerformed method
     */
    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output spiSplitCount_stateChanged method
     */
    protected void spiSplitCount_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomSplitUI");
    }




}