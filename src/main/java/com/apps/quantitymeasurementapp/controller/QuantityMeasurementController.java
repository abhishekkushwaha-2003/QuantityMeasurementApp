package com.apps.quantitymeasurementapp.controller;

import com.apps.quantitymeasurementapp.entity.QuantityDTO;
import com.apps.quantitymeasurementapp.service.IQuantityMeasurementService;
import com.apps.quantitymeasurementapp.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementController {

	private QuantityMeasurementServiceImpl quantityMeasurementService;

	//constructor
	public QuantityMeasurementController(IQuantityMeasurementService service) {
		this.quantityMeasurementService = (QuantityMeasurementServiceImpl) service;
	}
	
	public boolean performComparison(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.compare(thisQuantityDTO, thatQuantityDTO);
	}
	
	public QuantityDTO performConversion(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.convert(thisQuantityDTO, thatQuantityDTO);
	}
	
	public QuantityDTO performAddition(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.add(thisQuantityDTO, thatQuantityDTO);
	}
	
	public QuantityDTO performAddition(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO thatUnitDTO) {
		return quantityMeasurementService.add(thisQuantityDTO, thatQuantityDTO, thatUnitDTO);
	}
	
	public QuantityDTO performSubtraction(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.subtract(thisQuantityDTO, thatQuantityDTO);
	}
	
	public QuantityDTO performSubtraction(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO thatUnitDTO) {
		return quantityMeasurementService.subtract(thisQuantityDTO, thatQuantityDTO, thatUnitDTO);
	}
	
	public double performDivision(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.divide(thisQuantityDTO, thatQuantityDTO);
	}
}