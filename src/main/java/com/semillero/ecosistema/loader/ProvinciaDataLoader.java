package com.semillero.ecosistema.loader;

import com.semillero.ecosistema.models.PaisModel;
import com.semillero.ecosistema.models.ProvinciaModel;
import com.semillero.ecosistema.repositories.PaisRepository;
import com.semillero.ecosistema.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProvinciaDataLoader implements CommandLineRunner {
    @Autowired
    ProvinciaRepository provinciaRepository;
    @Autowired
    PaisRepository paisRepository;
    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData(){
        if (provinciaRepository.count() == 0){
            List<ProvinciaModel> listProvincias = new ArrayList<>();

            PaisModel argentina = paisRepository.findByName("Argentina");
            if (argentina != null) {
                listProvincias.add(new ProvinciaModel("Cordoba", argentina));
                listProvincias.add(new ProvinciaModel("Buenos Aires", argentina));
                listProvincias.add(new ProvinciaModel("Corrientes", argentina));
                listProvincias.add(new ProvinciaModel("Entre Rios", argentina));
                listProvincias.add(new ProvinciaModel("Misiones", argentina));
                listProvincias.add(new ProvinciaModel("Chaco", argentina));
                listProvincias.add(new ProvinciaModel("Formosa", argentina));
                listProvincias.add(new ProvinciaModel("Santa Fe", argentina));
                listProvincias.add(new ProvinciaModel("Santiago Del Estero", argentina));
                listProvincias.add(new ProvinciaModel("Salta", argentina));
                listProvincias.add(new ProvinciaModel("Catamarca", argentina));
                listProvincias.add(new ProvinciaModel("Tucuman", argentina));
                listProvincias.add(new ProvinciaModel("La Rioja", argentina));
                listProvincias.add(new ProvinciaModel("San Luis", argentina));
                listProvincias.add(new ProvinciaModel("San Juan", argentina));
                listProvincias.add(new ProvinciaModel("Neuquen", argentina));
                listProvincias.add(new ProvinciaModel("La Pampa", argentina));
                listProvincias.add(new ProvinciaModel("Mendoza", argentina));
                listProvincias.add(new ProvinciaModel("Rio Negro", argentina));
                listProvincias.add(new ProvinciaModel("Chubut", argentina));
                listProvincias.add(new ProvinciaModel("Santa Cruz", argentina));
                listProvincias.add(new ProvinciaModel("Tierra del Fuego", argentina));
                listProvincias.add(new ProvinciaModel("Jujuy", argentina));
                listProvincias.add(new ProvinciaModel("Ciudad Autonoma de Buenos Aires", argentina));
            }
            PaisModel uruguay = paisRepository.findByName("Uruguay");
            if (uruguay != null ){
                listProvincias.add(new ProvinciaModel("Artigas", uruguay));
                listProvincias.add(new ProvinciaModel("Salto", uruguay));
                listProvincias.add(new ProvinciaModel("Paysandu", uruguay));
                listProvincias.add(new ProvinciaModel("Rio negro", uruguay));
                listProvincias.add(new ProvinciaModel("Soriano", uruguay));
                listProvincias.add(new ProvinciaModel("Colonia", uruguay));
                listProvincias.add(new ProvinciaModel("San Jose", uruguay));
                listProvincias.add(new ProvinciaModel("Flores", uruguay));
                listProvincias.add(new ProvinciaModel("Durazno", uruguay));
                listProvincias.add(new ProvinciaModel("Tacuarembo", uruguay));
                listProvincias.add(new ProvinciaModel("Rivera", uruguay));
                listProvincias.add(new ProvinciaModel("Cerro Largo", uruguay));
                listProvincias.add(new ProvinciaModel("Treinta y tres", uruguay));
                listProvincias.add(new ProvinciaModel("Florida", uruguay));
                listProvincias.add(new ProvinciaModel("Canelones", uruguay));
                listProvincias.add(new ProvinciaModel("Lavalleja", uruguay));
                listProvincias.add(new ProvinciaModel("Maldonado", uruguay));
                listProvincias.add(new ProvinciaModel("Rocha", uruguay));
                listProvincias.add(new ProvinciaModel("Montevideo", uruguay));
            }

            provinciaRepository.saveAll(listProvincias);

        }
    }
}
