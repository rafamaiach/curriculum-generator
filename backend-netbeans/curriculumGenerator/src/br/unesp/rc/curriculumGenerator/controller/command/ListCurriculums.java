package br.unesp.rc.curriculumGenerator.controller.command;

import br.unesp.rc.curriculumGenerator.controller.helper.Helper;
import br.unesp.rc.curriculumGenerator.model.Curriculum;
import br.unesp.rc.curriculumGenerator.service.CurriculumService;
import br.unesp.rc.curriculumGenerator.service.FactoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * This class is used as a command to the frontend application to get all Curriculum to a given user
 */
public class ListCurriculums implements ICommand {

    /**
     * Receives a GET Request with a "userId" parameter and returns all Curriculums owned by this user
     *
     * @param httpExchange httpExchange
     * @throws IOException IOException
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (Helper.isRequestMethodOptions(httpExchange))
            return;

        int userId = 0;
        String JSONResponse = "";

        //Find GET Request parameters from httpExchange object
        String URIQuery = httpExchange.getRequestURI().getQuery();
        if (!(URIQuery == null | URIQuery.isEmpty())) {
            //Use "queryToMap" function to create a Map object that has all parameters and and its respective value
            Map<String, String> GETRequestParams = Helper.queryToMap(URIQuery);

            //Get "userId" value and parse to int
            if (!GETRequestParams.get("userId").isEmpty())
                userId = Integer.parseInt(GETRequestParams.get("userId"));

            //SELECT user's curriculum from database and set JSONResponse
            if (userId > 0) {
                CurriculumService curriculumService = FactoryService.getCurriculumService();
                List<Curriculum> curriculumList = curriculumService.selectCurriculumByUserId(userId);

                JSONResponse = new ObjectMapper().writer().writeValueAsString(curriculumList);
            }
        }

        // Add the required response header for a JSON Response
        Headers headers = httpExchange.getResponseHeaders();
        headers.add("Content-Type", "application/json");

        httpExchange.sendResponseHeaders(200, JSONResponse.getBytes().length);
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(JSONResponse.getBytes());
        outputStream.close();
    }
}
