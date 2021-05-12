package main.java.ticketGenerator.clients;

import main.java.ticketGenerator.clients.*;
import main.java.ticketGenerator.core.*;
import main.java.ticketGenerator.utility.*;
import java.util.*;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;



public class ElasticsearchClient extends BaseClient {
    Scanner sc = new Scanner(System.in);
    ApplicationConfig appConfig = new ApplicationConfig();
    Slot slot = new Slot(Integer.parseInt(appConfig.getProperties().getProperty("FLOOR")), Integer.parseInt(appConfig.getProperties().getProperty("CAPACITY")));

    @Override
    public void vehicleEntry() {
        Vehicle vehicle = registerVehicle();
        boolean isSlotAvailable = true;
        if (vehicle != null && !isDuplicateVehicle(vehicle.getRegistrationNumber())) {
            try {
                SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
                sourceBuilder.query(QueryBuilders.matchQuery("registrationNumber", "NULL"));

                SearchRequest searchRequest = new SearchRequest("vehicle");
                searchRequest.source(sourceBuilder);


                SearchResponse response = appConfig.getElasticClient().search(searchRequest, RequestOptions.DEFAULT);


                Map < String, Object > map = null;
                int id = slot.getCapacity();
                for (SearchHit hit: response.getHits()) {
                    map = hit.getSourceAsMap();
                    if ((int) map.get("Slot") < id) {
                        id = (int) map.get("Slot");
                    }
                    isSlotAvailable = false;
                }

                if (!isSlotAvailable) {
                    Map < String, Object > updateMap = new HashMap < > ();
                    updateMap.put("registrationNumber", vehicle.getRegistrationNumber().toUpperCase());
                    updateMap.put("color", vehicle.getColor().toUpperCase());
                    updateMap.put("Slot", id);

                    IndexRequest request = new IndexRequest("vehicle");
                    request.id(String.valueOf(id));
                    request.source(updateMap);
                    IndexResponse indexResponseUpdate = appConfig.getElasticClient().index(request, RequestOptions.DEFAULT);

                    System.out.println("Registration Number :" + vehicle.getRegistrationNumber().toUpperCase());
                    System.out.println("Color :" + vehicle.getColor().toUpperCase());
                    System.out.println("Slot :" + indexResponseUpdate.getId());


                } else {
                    System.out.println("Sorry! Parking is full");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("No such Vehicle available");
        }

    }
    @Override
    public void vehicleExit() {
        boolean isVehicleAvailable = true;
        System.out.println("Enter Registration Number");
        String registrationNo = sc.nextLine();
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchQuery("registrationNumber", registrationNo));

            SearchRequest searchRequest = new SearchRequest("vehicle");
            searchRequest.source(sourceBuilder);

            SearchResponse response = appConfig.getElasticClient().search(searchRequest, RequestOptions.DEFAULT);

            for (SearchHit hit: response.getHits()) {
                Map < String, Object > map = hit.getSourceAsMap();
                Object registrationNumber = map.get("registrationNumber");
                String id = String.valueOf(map.get("Slot"));

                if (registrationNumber.equals(registrationNo)) {
                    Map < String, Object > updateMap = new HashMap < > ();
                    updateMap.put("registrationNumber", "NULL");
                    updateMap.put("color", "NULL");
                    UpdateRequest request = new UpdateRequest("vehicle", id).doc(updateMap);
                    UpdateResponse updateResponse = appConfig.getElasticClient().update(request, RequestOptions.DEFAULT);
                    System.out.println("Slot Number" + updateResponse.getId() + "is available");
                    isVehicleAvailable = false;
                    break;

                }
            }

            if (!isVehicleAvailable) {
                System.out.println("No vehicle with Registration Number" + registrationNo + " found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void slotNumberByRegistrationNumber() {
        System.out.println("Enter Registration Number");
        String registrationNo = sc.nextLine();
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchQuery("registrationNumber", registrationNo));

            SearchRequest searchRequest = new SearchRequest("vehicle");
            searchRequest.source(sourceBuilder);

            SearchResponse response = appConfig.getElasticClient().search(searchRequest, RequestOptions.DEFAULT);
            for (SearchHit hit: response.getHits()) {
                Map < String, Object > map = hit.getSourceAsMap();
                Object registrationNumber = map.get("registrationNumber");
                if (registrationNumber.equals(registrationNo)) {
                    System.out.println("Vehicle is at slot Number" + map.get("Slot"));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registrationNumberBycolor() {
        System.out.println("Enter Vehicle Color");
        String color = sc.nextLine().toUpperCase();
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchQuery("color", color));

            SearchRequest searchRequest = new SearchRequest("vehicle");
            searchRequest.source(sourceBuilder);

            SearchResponse response = appConfig.getElasticClient().search(searchRequest, RequestOptions.DEFAULT);
            for (SearchHit hit: response.getHits()) {
                Map < String, Object > map = hit.getSourceAsMap();
                Object vehicleColor = map.get("color");
                if (vehicleColor.equals(color)) {
                    System.out.println("Registration Number of vehicle of color " + map.get("color") + "is" + map.get("registrationNumber"));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void slotNumberByColor() {
        System.out.println("Enter Vehicle Color");
        String color = sc.nextLine().toUpperCase();
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchQuery("color", color));

            SearchRequest searchRequest = new SearchRequest("vehicle");
            searchRequest.source(sourceBuilder);

            SearchResponse response = appConfig.getElasticClient().search(searchRequest, RequestOptions.DEFAULT);
            for (SearchHit hit: response.getHits()) {
                Map < String, Object > map = hit.getSourceAsMap();
                Object vehicleColor = map.get("color");
                if (vehicleColor.equals(color)) {
                    System.out.println("Slot Number of vehicle of color " + map.get("color") + "is" + map.get("Slot"));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isDuplicateVehicle(String registrationNo) {
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchQuery("registrationNumber", registrationNo));

            SearchRequest searchRequest = new SearchRequest("vehicle");
            searchRequest.source(sourceBuilder);

            SearchResponse response = appConfig.getElasticClient().search(searchRequest, RequestOptions.DEFAULT);

            for (SearchHit hit: response.getHits()) {
                Map < String, Object > map = hit.getSourceAsMap();
                Object registrationNumber = map.get("registrationNumber");
                if (registrationNumber.equals(registrationNo)) {
                    return true;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createIndex() {
        for (int id = 1; id <= slot.getCapacity(); id++) {
            IndexRequest request = new IndexRequest("vehicle").id(String.valueOf(id)).source("registrationNumber", "NULL", "color", "NULL", "Slot", id);
            try {

                appConfig.getElasticClient().index(request, RequestOptions.DEFAULT);
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }
    }
}