package com.sparkdemo.file.api;

import java.util.List;

import com.sparkdemo.model.TransactionDto;

public interface IFileReader {

	List<TransactionDto> getDataFromFile();
}
