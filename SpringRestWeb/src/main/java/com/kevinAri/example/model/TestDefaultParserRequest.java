package com.kevinAri.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TestDefaultParserRequest {
    private Date javaDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Jakarta")
    private Date javaDate2;

    private java.sql.Date sqlDate;
}
