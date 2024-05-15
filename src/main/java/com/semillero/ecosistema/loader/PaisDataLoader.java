package com.semillero.ecosistema.loader;

import com.semillero.ecosistema.models.PaisModel;
import com.semillero.ecosistema.repositories.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaisDataLoader implements CommandLineRunner {
    @Autowired
    PaisRepository paisRepository;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
    private void loadUserData(){
        if (paisRepository.count() == 0){
            List<PaisModel> listPais = new ArrayList<>();
            listPais.add(new PaisModel("Argentina"));
            listPais.add(new PaisModel("Uruguay"));

            paisRepository.saveAll(listPais);
        }
    }
}
