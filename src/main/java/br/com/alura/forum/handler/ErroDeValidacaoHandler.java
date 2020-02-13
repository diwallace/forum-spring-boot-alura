package br.com.alura.forum.handler;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.forum.controller.dto.ErroDeValidacaoFormularioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeValidacaoFormularioDTO> handle(MethodArgumentNotValidException exception) {
		List<ErroDeValidacaoFormularioDTO> dto = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroDeValidacaoFormularioDTO erro = new ErroDeValidacaoFormularioDTO(e.getField(), mensagem);
			dto.add(erro);
		});
		
		return dto;
	}

}
