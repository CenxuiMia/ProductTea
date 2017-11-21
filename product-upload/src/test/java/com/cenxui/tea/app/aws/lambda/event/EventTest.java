package com.cenxui.tea.app.aws.lambda.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class EventTest {
    private String s3event;

    @Before
    public void setUp() {
        s3event = "{  \n" +
                "   \"Records\":[  \n" +
                "      {  \n" +
                "         \"eventVersion\":\"2.0\",\n" +
                "         \"eventSource\":\"aws:s3\",\n" +
                "         \"awsRegion\":\"us-east-1\",\n" +
                "         \"eventTime\":The time, in ISO-8601 format, for example, 1970-01-01T00:00:00.000Z, when S3 finished processing the request,\n" +
                "         \"eventName\":\"event-type\",\n" +
                "         \"userIdentity\":{  \n" +
                "            \"principalId\":\"Amazon-customer-ID-of-the-user-who-caused-the-event\"\n" +
                "         },\n" +
                "         \"requestParameters\":{  \n" +
                "            \"sourceIPAddress\":\"ip-address-where-request-came-from\"\n" +
                "         },\n" +
                "         \"responseElements\":{  \n" +
                "            \"x-amz-request-id\":\"Amazon S3 generated request ID\",\n" +
                "            \"x-amz-id-2\":\"Amazon S3 host that processed the request\"\n" +
                "         },\n" +
                "         \"s3\":{  \n" +
                "            \"s3SchemaVersion\":\"1.0\",\n" +
                "            \"configurationId\":\"ID found in the bucket notification configuration\",\n" +
                "            \"bucket\":{  \n" +
                "               \"name\":\"bucket-name\",\n" +
                "               \"ownerIdentity\":{  \n" +
                "                  \"principalId\":\"Amazon-customer-ID-of-the-bucket-owner\"\n" +
                "               },\n" +
                "               \"arn\":\"bucket-ARN\"\n" +
                "            },\n" +
                "            \"object\":{  \n" +
                "               \"key\":\"object-key\",\n" +
                "               \"size\":object-size,\n" +
                "               \"eTag\":\"object eTag\",\n" +
                "               \"versionId\":\"object version if bucket is versioning-enabled, otherwise null\",\n" +
                "               \"sequencer\": \"a string representation of a hexadecimal value used to determine event sequence, \n" +
                "                   only used with PUTs and DELETEs\"            \n" +
                "            }\n" +
                "         }\n" +
                "      } " +
                "   ]\n" +
                "}  ";
    }

    @Test
    public void testEvent() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Event event = mapper.readValue(s3event, Event.class);
        System.out.println(event);

    }

}