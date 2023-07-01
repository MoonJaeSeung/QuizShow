package com.example.gp.dto;

import com.example.gp.entity.Record;
import org.modelmapper.ModelMapper;

public class RecordDto {

    private String nick;

    private int game;

    private int score;

    private static ModelMapper modelMapper = new ModelMapper();

    public Record createRecord(){
        return modelMapper.map(this, Record.class);
    }
}
