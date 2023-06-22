package com.example.capstone_be.service;

import com.example.capstone_be.dto.tour.TourViewForChatGPT;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.sql.SQLException;
import java.util.List;

public interface ChatGPTService {
    String chatGPT(String text) throws SQLException;

    void executeSQL(String sql) throws SQLException;

    List<TourViewForChatGPT> getListTourChatGPT(String prompt) throws SQLException;
}
