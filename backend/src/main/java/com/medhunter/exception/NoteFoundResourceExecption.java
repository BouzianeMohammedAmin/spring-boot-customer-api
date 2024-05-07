package com.medhunter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND)
public class NoteFoundResourceExecption extends  RuntimeException{

    public NoteFoundResourceExecption(String message) {
        super(message);
    }
}
