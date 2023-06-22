package com.example.capstone_be.service;

import com.example.capstone_be.dto.chat.ChatRequest;
import com.example.capstone_be.dto.chat.ChatResponse;
import com.example.capstone_be.dto.chat.Message;
//import org.apache.pdfbox.pdfparser.PDFParser;
import com.example.capstone_be.dto.tour.TourViewForChatGPT;
import com.example.capstone_be.model.Tour;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import org.apache.pdfbox.io.RandomAccessFile;


@Service
public class ChatGPTServiceImpl implements ChatGPTService{
    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EntityManager entityManager;

    @Override
    public String chatGPT(String prompt) throws SQLException {

        String context = "Here is a table containing data about tours, a table named tours and " +
                "fields as follows \n tour_id integer , \n title character , \n rating double precision, " +
                "\n city \n character varying(255), \n price_one_person double precision, \n image_main text , " +
                "\n working text, \n destination_description text , \n created_at timestamp with " +
                "\n time zone DEFAULT now(), \n updated_at timestamp with time zone DEFAULT now(), " +
                "\n latitude double precision, \n longitude double precision, \n time_slot_length " +
                "\n integer, \n time_book_start time without time zone, \n time_book_end time without time zone " +
                "\n Description of the fields: \n tour_id is the primary key of the table, \n title is the title, " +
                "the name of the tour, \n city is the name of the city of the tour, " +
                "\n rating is the rating of the tour, \n price_one_person is the price of the tour, " +
                "\n image_main is the image of the tour, \n working is the activity of the tour, " +
                "\n destination description is the tour description, \n created_at is tour creation time, " +
                "\n updated_at is tour update time, \n latitude is latitude, \n longitude is longitude, " +
                "\n time slot length is the length of time, \n time_book_start \n is the start time of the tour, " +
                "\n time_book_end is the end time of the tour " +
                "\n if the prompt has content related to the fields in the table, " +
                "then create the corresponding postgresql statement with PostgreSQL's format" +
                "and no description needed \n";
        // create a request
        prompt = context+prompt;
        String model = "gpt-3.5-turbo";
        String apiUrl = "https://api.openai.com/v1/chat/completions";
        ChatRequest request = new ChatRequest(model, prompt);
        request.setN(1);
        System.out.println(request);
        // call the API
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            ResponseEntity.ok()
                    .body(new Message("Error", "No response"));
        }
        String result = response.getChoices().get(0).getMessage().getContent();

        System.out.println(result);
        return response.getChoices().get(0).getMessage().getContent();
    }

    @Override
    public void executeSQL(String sql) throws SQLException {

    }

    @Override
    public List<TourViewForChatGPT> getListTourChatGPT(String prompt) throws SQLException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String sql = chatGPT(prompt);
        Query q = entityManager.createNativeQuery(sql, Tour.class);
        List<Tour> tourList = (List<Tour>) q.getResultList();
        List<TourViewForChatGPT> tourViewForChatGPTList = new ArrayList<>();
        TourViewForChatGPT tourViewForChatGPT= null;
        for (Tour item : tourList) {
            tourViewForChatGPT = new TourViewForChatGPT();
            tourViewForChatGPT.setTourId(item.getTourId());
            tourViewForChatGPT.setCity(item.getCity());
            tourViewForChatGPT.setWorking(item.getWorking());
            tourViewForChatGPT.setTitle(item.getTitle());
            tourViewForChatGPT.setImageMain(item.getImageMain());
            tourViewForChatGPT.setPriceOnePerson(item.getPriceOnePerson());
            tourViewForChatGPT.setDestinationDescription(item.getDestinationDescription());
            tourViewForChatGPT.setCategoryName(item.getCategories().iterator().next().getCategoryName());
            tourViewForChatGPTList.add(tourViewForChatGPT);
        }
        return tourViewForChatGPTList;
    }


}
