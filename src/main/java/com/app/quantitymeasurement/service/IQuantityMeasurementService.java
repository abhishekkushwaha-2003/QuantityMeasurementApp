
package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.entity.QuantityDTO;

public interface IQuantityMeasurementService {
	public boolean compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityDTO convert (QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityDTO add (QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);
	
	public QuantityDTO subtract (QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityDTO subtract (QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);
	
	public double divide (QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
}