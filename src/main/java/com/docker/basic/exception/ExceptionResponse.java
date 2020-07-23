package com.docker.basic.exception;

import lombok.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    private Date timeStamp;

    private String message;

    private  String details;
}