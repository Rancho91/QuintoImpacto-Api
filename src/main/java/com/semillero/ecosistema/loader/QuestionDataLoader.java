package com.semillero.ecosistema.loader;

import com.semillero.ecosistema.models.AnswerModel;
import com.semillero.ecosistema.models.QuestionModel;
import com.semillero.ecosistema.repositories.AnswerRepository;
import com.semillero.ecosistema.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class QuestionDataLoader implements CommandLineRunner {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;
    @Override

    public void run(String... args) throws Exception {
        loadAnswerData();
        loadQuestionData();
        loadOneToOne();
        loadAnswerAndChildren();
    }


    private void loadAnswerData() {
        if (answerRepository.count() == 0){

            List<AnswerModel> answerList = new ArrayList<>();
            answerList.add(new AnswerModel("No, no tiene ningún costo ser proveedor. "));
            answerList.add(new AnswerModel("La categoría la decidís al momento de cargar tu producto o servicio"));
            answerList.add(new AnswerModel("No, no apareces inmediatamente. Debes pasar por un proceso de revisión por parte del administrador para ser aceptado. En caso de que el administrador tenga alguna duda, colocará tu servicio en revisión para que puedas responder o aclarar las inquietudes del administrador. "));
            answerList.add(new AnswerModel("Si, siempre podés editar lo que subís. Tené en cuenta que vuelves a un proceso de revisión en caso de editar tu postulación"));
            answerList.add(new AnswerModel("No, no puedes cargar publicaciones. Las publicaciones las carga el administrador."));
            answerList.add(new AnswerModel("Actualmente no es posible abonar un servicio “Premium”. Los proveedores le aparecen al visitante por cercanía en caso de que hayan activado su ubicación. Así será más fácil que los usuarios cercanos a tu emprendimiento te encuentren"));
            answerList.add(new AnswerModel("Si! Podes cargar mas de un producto o servicio. Siempre por separado. "));

            answerList.add(new AnswerModel("El cambio de categoria lo puedes hacer dentro de tu perfil, editando el producto"));
            answerList.add(new AnswerModel("Puedes elegir una sola categoria por producto y servicio."));
            answerList.add(new AnswerModel("Nuestros administradores están capacitados para hacer el análisis correspondiente a cada producto. Si algún producto no coincide con la categoría te asesorarán para seleccionar la categoría correcta."));

            answerList.add(new AnswerModel("En el momento que los administradores acepten el producto se mostrará de forma inmediata"));
            answerList.add(new AnswerModel("Si, el objetivo de la plataforma yes mejorar es brindar productos y servicios ecologicos y sustentables."));
            answerList.add(new AnswerModel("Si, puedes editar tu producto las veces que necesites.El volverá a pasar al proceso de revision ser aceptado nuevamente."));

            answerRepository.saveAll(answerList);

        }
    }

    private void loadQuestionData(){
        if (questionRepository.count() == 0){
            List<QuestionModel> questionList = new ArrayList<>();

            questionList.add(new QuestionModel("¿Tiene algún costo aparecer como proveedor en ECOS? "));
            questionList.add(new QuestionModel("¿Como se decide que categoría le corresponde al servicio/producto que brindo? "));
            questionList.add(new QuestionModel("¿Una vez que cargo mi producto/servicio aparezco inmediatamente en la plataforma?"));
            questionList.add(new QuestionModel("¿Una vez que cargue mi producto/servicio puedo editar lo que subí?"));
            questionList.add(new QuestionModel("¿Siendo proveedor, puedo cargar publicaciones acerca de mi servicio/producto?"));
            questionList.add(new QuestionModel("¿Puedo pagar para aparecer primero a todos los usuarios que visiten la plataforma?"));
            questionList.add(new QuestionModel("¿Puedo cargar más de un producto o servicio?"));

            //Preguntas hijas de como se decide que categoria le corresponde al servicio/producto? 2L
            questionList.add(new QuestionModel("¿Puedo solicitar un cambio de categoría si creo que la asignación es incorrecta?"));
            questionList.add(new QuestionModel("¿Cuántas categorías puedo elegir para mis productos/servicios?"));
            questionList.add(new QuestionModel("¿Existen restricciones o requisitos para ser clasificado en ciertas categorías?"));

            //pregunta hiojas de ¿Una vez que cargo mi producto/servicio aparezco inmediatamente en la plataforma? 3L
            questionList.add(new QuestionModel("¿Cuánto tiempo lleva para que mi producto/servicio esté visible en la plataforma?"));
            questionList.add(new QuestionModel("¿Hay un proceso de revisión antes de que mis productos/servicios se publiquen?"));
            questionList.add(new QuestionModel("¿Después que el producto sea aceptado puedo volver a editarlo?"));

            questionRepository.saveAll(questionList);
        }



    }
    private void loadOneToOne() {
       List<Long> listId = new ArrayList<>(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L,8L,9L,10L,11L,12L,13L));
       for (Long id:listId){
             Optional<QuestionModel> questionOptional = questionRepository.findById(id);
           if(questionOptional.isEmpty()){
               return;
           }

             QuestionModel question = questionOptional.get();
            if(question.getAnswer() == null){
                question.setAnswer(new AnswerModel(id));
                questionRepository.save(question);
         }
       }
    }
    private void loadAnswerAndChildren(){
        List<Long> questionId = new ArrayList<>(Arrays.asList(8L, 9L, 10L));

        for (Long id:questionId){
            Optional<QuestionModel> questionOptional = questionRepository.findById(id);
            if(questionOptional.isEmpty()){
                return;
            }

            QuestionModel question = questionOptional.get();
            if(question.getAnswerFather() == null){
                question.setAnswerFather(new AnswerModel(2L));
                questionRepository.save(question);
            }
        }
        questionId = new ArrayList<>(Arrays.asList(11L, 12L, 13L));

        for (Long id:questionId){
            Optional<QuestionModel> questionOptional = questionRepository.findById(id);
            if(questionOptional.isEmpty()){
                return;
            }

            QuestionModel question = questionOptional.get();
            if(question.getAnswerFather() == null){
                question.setAnswerFather(new AnswerModel(3L));
                questionRepository.save(question);
            }
        }
    }


}
