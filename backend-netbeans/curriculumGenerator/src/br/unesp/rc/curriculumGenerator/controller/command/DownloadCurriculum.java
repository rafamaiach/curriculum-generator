package br.unesp.rc.curriculumGenerator.controller.command;

import br.unesp.rc.curriculumGenerator.controller.helper.Helper;
import br.unesp.rc.curriculumGenerator.model.Curriculum;
import br.unesp.rc.curriculumGenerator.service.CurriculumService;
import br.unesp.rc.curriculumGenerator.service.FactoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used as a command to the frontend application to Download a Curriculum
 */
public class DownloadCurriculum implements ICommand {

    /**
     * Receives a POST Request with a Curriculum JSON, generate the File for the curriculum and returns to Frontend.
     *
     * @param httpExchange httpExchange
     * @throws IOException IOException
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (Helper.isRequestMethodOptions(httpExchange))
            return;

        Object curriculumModel = -1; // Used to define which model will be generated and downloaded

        String JSONRequest = Helper.getJSONfromHttpExchange(httpExchange);

        //BEGIN - Get "Model" parameter from JSON and remove it from original JSON
        ObjectMapper objectMapper = new ObjectMapper();

        //Create a Map object to parse the original JSON request
        Map<String, String> jsonMap = objectMapper.readValue(JSONRequest, HashMap.class);

        //Get the "model" value and remove it from the Map
        curriculumModel = jsonMap.get("model");
        jsonMap.remove("model");
        if (curriculumModel == null) // If model wasn't defined we will use "-1" as default
            curriculumModel = -1;

        //Generate the JSON again, but WITHOUT the "model" parameter
        JSONRequest = objectMapper.writer().writeValueAsString(jsonMap);
        //END - Get "Model" parameter from JSON and remove it from original JSON

        //JSON from String to Object
        Curriculum curriculum = new ObjectMapper().readValue(JSONRequest, Curriculum.class);

        //Generate DOC file using "Apache Poi"
        CurriculumService curriculumService = FactoryService.getCurriculumService();
        File generatedCurriculum = curriculumService.getCurriculumFile(curriculum, (int) curriculumModel);

        // Add the required response header for a Word file
        Headers headers = httpExchange.getResponseHeaders();
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        byte[] byteArray = new byte[(int) generatedCurriculum.length()];
        FileInputStream fileInputStream = new FileInputStream(generatedCurriculum);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        bufferedInputStream.read(byteArray, 0, byteArray.length);

        // Set response
        httpExchange.sendResponseHeaders(200, generatedCurriculum.length());
        OutputStream responseBody = httpExchange.getResponseBody();
        responseBody.write(byteArray, 0, byteArray.length);
        responseBody.close();
    }
}
