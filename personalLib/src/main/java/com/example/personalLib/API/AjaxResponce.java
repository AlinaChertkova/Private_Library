package com.example.personalLib.API;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AjaxResponce<T> {

    private String status;
    private String message;
    private T data;

}