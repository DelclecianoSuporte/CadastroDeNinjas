package dev.java10x.CadastroDeNinjas.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String, String>> listaDeErros = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            Map<String, String> erroCustomizado = new HashMap<>();

            String nomeDoCampo = ((FieldError) error).getField();
            String mensagemDeErro = error.getDefaultMessage();

            // Aqui personalizamos as chaves do JSON para o português
            erroCustomizado.put("campo", nomeDoCampo);
            erroCustomizado.put("mensagem", mensagemDeErro);

            listaDeErros.add(erroCustomizado);
        });

        return ResponseEntity.badRequest().body(listaDeErros);
    }
}
