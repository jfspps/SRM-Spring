package com.srm.controllers;

import com.srm.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

//ControllerAdvice provides (in this case) exception handling which applies to all classes across the project
//note that MockMvc tests will need setControllerAdvice(new NameofThisController) to apply tests
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    //handles ID input format, as String (to Long)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception exception){

        log.error("Handling 'number format' exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("400error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

    //note that the ResponseStatus annotation is repeated here since 'local' annotations take precedence, all other
    //class level annotations are ignored
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){

        log.error("Handling 'not found' exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}
