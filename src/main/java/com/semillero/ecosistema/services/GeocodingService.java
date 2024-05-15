package com.semillero.ecosistema.services;

import com.semillero.ecosistema.dtos.supplier.SupplierLocationNearDto;
import com.semillero.ecosistema.dtos.supplier.SupplierSearchAndFilterDto;
import com.semillero.ecosistema.exceptions.GeocodingException;
import com.semillero.ecosistema.models.SupplierModel;
import com.semillero.ecosistema.utils.GeocodingUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GeocodingService {

    private final GeocodingUtil geocodingUtil;
    private final SupplierService supplierService;
    private final ModelMapper modelMapper;

    public GeocodingService(GeocodingUtil geocodingUtil, SupplierService supplierService, ModelMapper modelMapper) {
        this.geocodingUtil = geocodingUtil;
        this.supplierService = supplierService;
        this.modelMapper = modelMapper;
    }

    public Map<String, String> getLocation(Double lat, Double lng) throws Exception {
        try {
            Map<String, String> location = geocodingUtil.getLocationFromCoordinates(lat, lng);

            if (location.isEmpty()) {
                    throw new GeocodingException(String.format("Empty location for coordinates lat %f and lng %f", lat, lng));
            }

            return location;
        } catch (GeocodingException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("An error occurred while getting the location");
            throw new Exception(e.getMessage());
        }
    }

    public List<SupplierLocationNearDto> getSupplierNearby(Double lat, Double lng, Boolean acceptedPermissions) throws Exception {

        if (acceptedPermissions){
            if (lat != null && lng != null) {
                Map<String, String> locationNearby = this.getLocation(lat, lng);

                String province = normalizeString(locationNearby.get("province"));
                String provinceNearby = province.replaceAll("Province", "").trim();
                String countryNearby = locationNearby.get("country");
                String cityNearby = locationNearby.get("city");
                //Devuelve proveedores por pais, provincia y ciudad o solo por pais y provincia
                List<SupplierLocationNearDto> nearDtoList = supplierService.getSuppliersLocation(countryNearby, provinceNearby, cityNearby);
                return nearDtoList;
            }
        }

        return supplierService.getSupplierRandom();

    }
    // Normalizar y eliminar caracteres especiales de un string
    public String normalizeString(String input) {
        // Reemplazar caracteres especiales con su equivalente ASCII
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        // Remover cualquier caracter que no sea letra, dígito o espacio
        normalized = normalized.replaceAll("[^a-zA-Z0-9\\s]", "");

        // Remover espacios adicionales
        normalized = normalized.trim();
        return normalized;
    }
}
