package com.medhunter.exception;

import java.time.LocalDateTime;

public record ApiError (
        String path ,
        String message ,
        Integer statusCode ,
        LocalDateTime localDateTime
){
}
