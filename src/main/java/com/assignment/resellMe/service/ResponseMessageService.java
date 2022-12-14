package com.assignment.resellMe.service;

import com.assignment.resellMe.model.response.ResponseMessage;
import org.springframework.stereotype.Component;

@Component
public class ResponseMessageService {
    public ResponseMessage generateMessage(Object data, Integer status) {
        return new ResponseMessage(data, status);
    }
}
