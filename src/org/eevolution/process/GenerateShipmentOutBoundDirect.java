/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          * 
 * http://www.adempiere.org                                           * 
 *                                                                    * 
 * Copyright (C) Victor Perez	                                      * 
 * Copyright (C) Contributors                                         * 
 *                                                                    * 
 * This program is free software; you can redistribute it and/or      * 
 * modify it under the terms of the GNU General Public License        * 
 * as published by the Free Software Foundation; either version 2     * 
 * of the License, or (at your option) any later version.             * 
 *                                                                    * 
 * This program is distributed in the hope that it will be useful,    * 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of     * 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       * 
 * GNU General Public License for more details.                       * 
 *                                                                    * 
 * You should have received a copy of the GNU General Public License  * 
 * along with this program; if not, write to the Free Software        * 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         * 
 * MA 02110-1301, USA.                                                * 
 *                                                                    * 
 * Contributors:                                                      * 
 *  - Victor Perez (victor.perez@e-evolution.com	 )                *
 *                                                                    *
 * Sponsors:                                                          *
 *  - e-Evolution (http://www.e-evolution.com/)                       *
 **********************************************************************/

package org.eevolution.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MLocator;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MWarehouse;
import org.compiere.modelWMS.MDocType;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.eevolution.model.MWMInOutBoundLine;

/**
 *	
 *  @author victor.perez@e-evolution.com, www.e-evolution.com
 *  @version $Id: $
 */
public class GenerateShipmentOutBoundDirect extends SvrProcess
{	
	/** Record ID */
	protected int p_Record_ID = 0;	

	protected String p_DocAction = null;
	protected boolean p_IsIncludeNotAvailable = false;
	protected Timestamp p_MovementDate = null;
	private Hashtable<Integer, MInOut> m_shipments = new Hashtable<Integer, MInOut>();
	
	
	/**
	 * 	Get Parameters
	 */
	protected void prepare ()
	{
		
		p_Record_ID = getRecord_ID();
		for (ProcessInfoParameter para:getParameter())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("DocAction"))
			{
				p_DocAction = (String)para.getParameter();
			}
			else if (name.equals("IsIncludeNotAvailable"))
			{
				p_IsIncludeNotAvailable = "Y".equals(para.getParameter());
			}
			else if (name.equals("MovementDate"))
			{
				p_MovementDate = (Timestamp)para.getParameter();
			}
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	/**
	 * 	Process - Generate Export Format
	 *	@return info
	 */
	@SuppressWarnings("unchecked")
	protected String doIt () throws Exception
	{
		//red1 using preparedStatement for whole direct SQL usage from InfoView
		String SQL = "SELECT ol.QtyOrdered-ol.QtyDelivered, iobl.WM_InOutBoundLine_ID FROM WM_InOutBoundLine iobl " +
				"INNER JOIN WM_InOutBound iob ON (iob.WM_InOutBound_ID=iobl.WM_InOutBound_ID) "+ 
				"INNER JOIN C_OrderLine ol ON (ol.C_OrderLine_ID= iobl.C_OrderLine_ID) "+
				"INNER JOIN C_Order o ON (o.C_Order_ID = ol.C_Order_ID) " +
				"INNER JOIN C_BPartner bp ON (bp.C_BPartner_ID=ol.C_BPartner_ID) " +
				"INNER JOIN M_Locator ml ON (ml.M_Warehouse_ID = iob.M_Warehouse_ID)" +
				"INNER JOIN M_StorageOnHand oh ON (oh.M_Product_ID = iobl.M_Product_ID AND oh.M_Locator_ID = ml.M_Locator_ID) "+
				"WHERE NOT EXISTS (SELECT 1 FROM M_InOutLine WHERE M_InOutLine.C_OrderLine_ID = iobl.C_OrderLine_ID AND "+
				"iobl.PickedQty >= M_InOutLine.MovementQty)  AND iob.IsSOTrx='Y' AND iobl.Processed='Y' AND ol.QtyOrdered <> ol.QtyDelivered "+
				"AND iobl.IsActive = 'Y'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int seq = 10;
		try
		{
			pstmt = DB.prepareStatement (SQL, get_TrxName());
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
			 	 
			if(rs.getBigDecimal(1).signum() > 0 || p_IsIncludeNotAvailable)
				{	
				MWMInOutBoundLine iobl = new MWMInOutBoundLine (getCtx(), rs.getInt(2), null);
				createMInOut(iobl);
				seq ++;
				}		
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, SQL);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		
		Enumeration<MInOut> shipments = m_shipments.elements();
		while (shipments.hasMoreElements())
		{	
			MInOut inout = (MInOut) shipments.nextElement();
			inout.setDocAction(p_DocAction);
			inout.processIt(p_DocAction);		
			if (!inout.processIt(p_DocAction))
				log.warning("Failed: " + inout);
			inout.saveEx();
		}
		if (m_shipments.elements().hasMoreElements())
			return "Shipments created: " + (seq-10);
		else return "No Shipments created!";
	}	
	

	/**
	 * Create Shipment to Out Bound Order
	 * @param Out Bound Order Line
	 */
	public void createMInOut(MWMInOutBoundLine line)
	{
		MOrderLine oline = line.getMOrderLine();
		if(line.getPickedQty().subtract(oline.getQtyDelivered()).signum() < 0 && !p_IsIncludeNotAvailable)		
		{
			return;
		}
		
		MLocator standing = null;
		BigDecimal QtyDelivered = Env.ZERO;
		if(p_IsIncludeNotAvailable)
		{
			standing = MLocator.getDefault((MWarehouse)line.getParent().getM_Warehouse());
			QtyDelivered  = line.getQtyToPick().subtract(oline.getQtyDelivered());
		}
		else
		{	
			standing = line.getMLocator();
			QtyDelivered  = line.getPickedQty().subtract(oline.getQtyDelivered());
		}	
		
		MInOut inout = getMInOut(oline);
		inout.setIsSOTrx(true);
		inout.saveEx();
		MInOutLine shipmentLine = new MInOutLine(inout);
		shipmentLine.setM_InOut_ID(inout.getM_InOut_ID());
		shipmentLine.setM_Locator_ID(standing.getM_Locator_ID());
		shipmentLine.setM_Product_ID(line.getM_Product_ID());
		shipmentLine.setQtyEntered(QtyDelivered);
		shipmentLine.setMovementQty(QtyDelivered);
		shipmentLine.setC_OrderLine_ID(oline.getC_OrderLine_ID());
		shipmentLine.saveEx();
	}	
		
	/**
	 * get Document Type
	 * @param docBaseType
	 * @return int Document Type
	 */
	protected int getDocType(String docBaseType)
	{
		MDocType[] docs = MDocType.getOfDocBaseType(getCtx(), docBaseType);

		if (docs == null || docs.length == 0) 
		{
			String reference = Msg.getMsg(getCtx(), "SequenceDocNotFound");
			String textMsg = "Not found default document type for docbasetype "+ docBaseType;
			throw new AdempiereException(textMsg);
		} 
		else
		{
			
			for (MDocType doc : docs)
			{	
				if(doc.isSOTrx())
				{	
					log.info("Doc Type for "+docBaseType+": "+ doc.getC_DocType_ID());
					return doc.getC_DocType_ID();
				}
			}
			String textMsg = "Not found default document type for docbasetype "+ docBaseType;
			throw new AdempiereException(textMsg);
			
		}
	}
	
	/**
	 * Create Shipment header
	 * @param oline Sales Order Line
	 * @return MInOut return the Shipment header
	 */
	private MInOut getMInOut(MOrderLine oline)
	{

		MInOut inout = (MInOut) m_shipments.get(oline.getC_Order_ID());
		if(inout != null)
		{
			return inout;
		}	
		MOrder order = oline.getParent();
		inout = new MInOut(order,getDocType(MDocType.DOCBASETYPE_MaterialDelivery), p_MovementDate);
		m_shipments.put(order.getC_Order_ID(), inout);
		return inout;
	}
}
