package handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;


import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.parser.JSONParser;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.lambda.model.*;

//import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.commons.io.FileUtils;
//import sun.nio.cs.UTF_8;


public class Hello implements RequestHandler<S3Event, String> {

    public String handleRequest(S3Event s3event, Context context) {

        S3EventNotificationRecord record = s3event.getRecords().get(0);

        String srcBucket = "shw-detection";
        String srcKey = "illegal_arg_data.json";

        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
        S3Object s3Object = s3Client.getObject(new GetObjectRequest(srcBucket, srcKey));
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();


        //s3ObjectInputStream. abort();


       // if (s3ObjectInputStream != null) {
       //     s3ObjectInputStream.abort();
       // }




        try {

            /* BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent(),"UTF-8"));
            String line = "";
            StringBuilder obj1=new StringBuilder();

            while ((line = reader.readLine()) != null) {
                    obj1.append(line);
                //s3Client.putObject(dstBucket, dstKey, line);
            }

            System.out.println(obj1.toString());

            System.out.println(); */

            System.out.println("string is printed");


            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(s3ObjectInputStream);

            ArrayNode arrayNode = (ArrayNode) jsonNode;

            Iterator<JsonNode> slaidsIterator = arrayNode.elements();
            int count=0;
            while (slaidsIterator.hasNext()) {
                JsonNode slaidNode = slaidsIterator.next();
                System.out.println(slaidNode.get("stacktrace"));
                count++;
                if(count==4)
                    break;
            }

            System.out.println("mapper is working");

            return "abc";
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "";

    }
}


