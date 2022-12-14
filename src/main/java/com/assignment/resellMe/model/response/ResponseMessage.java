package com.assignment.resellMe.model.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder({"data", "statusCode"})
@Data
public class ResponseMessage {
    private Object data;
    private Integer statusCode;

    public ResponseMessage(Object data, Integer statusCode){
        this.data = data;
        this.statusCode = statusCode;
    }
}
